package com.leute.rank_system.bot.discord.command;

import com.leute.rank_system.bot.discord.command.container.AbstractContainer;
import com.leute.rank_system.bot.discord.command.container.ContainerRedisKeyGeneratorFunction;
import com.leute.rank_system.bot.discord.command.container.GoalsContainer;
import com.leute.rank_system.bot.discord.command.container.entity.RewardGoal;
import com.leute.rank_system.bot.entity.kafka.RewardTypes;
import com.leute.rank_system.bot.service.RewardsService;
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
 * Goals command realization
 */
@Component
public class GoalsCommand extends ListenerAdapter implements AbstractCommandData {

    private final String commandName = "goals";
    private RewardsService rewardsService;
    private final Function<String, String> keyGenerator = new ContainerRedisKeyGeneratorFunction();
    private RedisTemplate<String, AbstractContainer> abstractContainerRedisTemplate;

    @Autowired
    public GoalsCommand(RewardsService rewardsService, RedisTemplate<String, AbstractContainer> abstractContainerRedisTemplate) {
        this.abstractContainerRedisTemplate = abstractContainerRedisTemplate;
        this.rewardsService = rewardsService;
    }

    @Override
    public CommandData getCommandData() {
        return Commands.slash(commandName, "Show all goals");
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals(commandName)) return;

        var guild = event.getGuild();

        if (guild == null) {
            event.reply("This command is for guild").setEphemeral(true).queue();
            return;
        }

        var container = new GoalsContainer(
                rewardsService.allRewardGoals(guild.getId()).stream()
                        .map(rewardGoalDTO -> new RewardGoal(
                                rewardGoalDTO.id(),
                                rewardGoalDTO.guildId(),
                                rewardGoalDTO.reward(),
                                rewardGoalDTO.type(),
                                rewardGoalDTO.points(),
                                event.getJDA().getGuildById(rewardGoalDTO.guildId()).getName(),
                                rewardGoalDTO.type() == RewardTypes.ROLE ?
                                        event.getJDA().getRoleById(rewardGoalDTO.reward()).getName() : null
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
