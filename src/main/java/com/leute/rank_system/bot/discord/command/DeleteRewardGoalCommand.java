package com.leute.rank_system.bot.discord.command;

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

@Component
public class DeleteRewardGoalCommand extends ListenerAdapter implements AbstractCommandData {

    private final String idOptionName = "id";
    private final String commandName = "delete_reward_goal";
    private RewardsService rewardsService;
    private final Logger LOG = LoggerFactory.getLogger(DeleteRewardGoalCommand.class);

    @Autowired
    public DeleteRewardGoalCommand(RewardsService rewardsService) {
        this.rewardsService = rewardsService;
    }

    @Override
    public CommandData getCommandData() {
        var idOption = new OptionData(
                OptionType.INTEGER,
                idOptionName,
                "Id of a reward goal",
                true
        );

        return Commands.slash(commandName, "Delete a reward goal by id")
                .addOptions(idOption);
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals(commandName)) return;


        var id = event.getOption(idOptionName).getAsInt();

        LOG.info("New reward goal delete request with id [{}]: {}", id, event);

        var responseEntity = rewardsService.deleteRewardGoal(id);

        if (responseEntity.getStatusCode().value() == 404) {
            LOG.info("Reward goal with id [{}] was not deleted. Event: [{}]", id, event);
            event.reply("Reward goal was not found")
                    .setEphemeral(true)
                    .queue();

            return;
        }

        event.reply("Reward goal was deleted")
                .setEphemeral(true)
                .queue();
    }
}
