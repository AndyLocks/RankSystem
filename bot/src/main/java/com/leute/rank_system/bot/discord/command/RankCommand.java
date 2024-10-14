package com.leute.rank_system.bot.discord.command;

import com.leute.rank_system.bot.discord.command.container.AbstractContainer;
import com.leute.rank_system.bot.discord.command.container.ContainerRedisKeyGeneratorFunction;
import com.leute.rank_system.bot.discord.command.container.PointsContainer;
import com.leute.rank_system.bot.discord.command.container.entity.Member;
import com.leute.rank_system.bot.service.ManagerService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * rank command realization
 */
@Component
public class RankCommand extends ListenerAdapter implements AbstractCommandData {

    private ManagerService managerService;
    private final String commandName = "rank";
    private final Function<String, String> keyGenerator = new ContainerRedisKeyGeneratorFunction();
    private RedisTemplate<String, AbstractContainer> abstractContainerRedisTemplate;

    @Autowired
    public RankCommand(ManagerService managerService, RedisTemplate<String, AbstractContainer> abstractContainerRedisTemplate) {
        this.managerService = managerService;
        this.abstractContainerRedisTemplate = abstractContainerRedisTemplate;
    }

    @Override
    public CommandData getCommandData() {
        return Commands.slash(commandName, "all points");
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals(commandName)) return;

        var guild = event.getGuild();

        if (guild == null) {
            event.reply("This command is for guild").setEphemeral(true).queue();
            return;
        }

        var container = new PointsContainer(
                managerService.getAllPointsByGuildId(guild.getId()).stream()
                        .map(memberDTO -> new Member(
                                memberDTO.memberId(),
                                memberDTO.guildId(),
                                memberDTO.points(),
                                event.getJDA().getUserById(memberDTO.memberId()).getName()
                        ))
                        .toList());

        event.replyEmbeds(new EmbedBuilder()
                        .setDescription(container.embeds().description())
                        .build())
                .addActionRow(container.buttons())
                .queue(interactionHook -> {
                            abstractContainerRedisTemplate.opsForValue()
                            .set(keyGenerator.apply(interactionHook.getInteraction().getId()), container,
                                    5, TimeUnit.MINUTES);
                });
    }

}
