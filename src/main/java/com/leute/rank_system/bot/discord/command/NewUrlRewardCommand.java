package com.leute.rank_system.bot.discord.command;

import com.leute.rank_system.bot.entity.dto.RewardGoalDTO;
import com.leute.rank_system.bot.entity.kafka.RewardTypes;
import com.leute.rank_system.bot.service.HttpSendException;
import com.leute.rank_system.bot.service.RewardsService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 * New url reward goal command realization
 */
@Component
public class NewUrlRewardCommand extends ListenerAdapter implements AbstractCommandData {

    private final String commandName = "new_url_goal";
    private final String pointsOptionName = "points";
    private final String urlOptionName = "url";
    private RewardsService rewardsService;
    private final Logger LOG = LoggerFactory.getLogger(NewUrlRewardCommand.class);

    @Autowired
    public NewUrlRewardCommand(RewardsService rewardsService) {
        this.rewardsService = rewardsService;
    }

    @Override
    public CommandData getCommandData() {
        var pointsOption = new OptionData(
                OptionType.INTEGER,
                pointsOptionName,
                "Required points to get the reward",
                true
        );

        var urlOption = new OptionData(
                OptionType.STRING,
                urlOptionName,
                "Url as reward",
                true
        );

        return Commands.slash(commandName, "Add new url goal")
                .addOptions(urlOption, pointsOption);
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals(commandName)) return;

        LOG.info("New command request: {}", event);

        if (event.getGuild() == null) {
            event.reply("This command is for guild.")
                    .setEphemeral(true)
                    .queue();

            return;
        }

        var url = event.getOption(urlOptionName).getAsString();
        var points = event.getOption(pointsOptionName).getAsInt();

        if (!Pattern.compile("(https:\\/\\/www\\.|http:\\/\\/www\\.|https:\\/\\/|http:\\/\\/)?[a-zA-Z0-9]{2,}(\\.[a-zA-Z0-9]{2,})(\\.[a-zA-Z0-9]{2,})?")
                .matcher(url)
                .matches()) {

            event.reply("This url is not supported")
                    .setEphemeral(true)
                    .queue();

            return;
        }

        try {
            rewardsService.addNewRewardGoal(new RewardGoalDTO(
                    null,
                    event.getGuild().getId(),
                    url,
                    RewardTypes.URL,
                    points
            ));
        } catch (HttpSendException e) {
            LOG.error(e.getMessage());
        }

        event.reply("New url reward was added")
                .setEphemeral(true)
                .queue();
    }
}
