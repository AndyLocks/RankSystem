package com.leute.rank_system.bot.discord.command;

import com.leute.rank_system.bot.entity.dto.RewardGoalDTO;
import com.leute.rank_system.bot.entity.kafka.RewardTypes;
import com.leute.rank_system.bot.service.RewardsService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * new_role_goal command realization
 */
@Component
public class NewRoleRewardGoal extends ListenerAdapter implements AbstractCommandData {

    private final String commandName = "new_role_goal";
    private final String roleOptionName = "reward";
    private final String pointsOptionName = "points";
    private RewardsService rewardsService;

    @Autowired
    public NewRoleRewardGoal(RewardsService rewardsService) {
        this.rewardsService = rewardsService;
    }

    @Override
    public CommandData getCommandData() {
        var roleOption = new OptionData(
                OptionType.ROLE,
                roleOptionName,
                "reward",
                true
        );

        var pointsOption = new OptionData(
                OptionType.INTEGER,
                pointsOptionName,
                "Required points to get the reward",
                true
        );

        return Commands.slash(commandName, "Add new role goal")
                .addOptions(roleOption, pointsOption);
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals(commandName)) return;

        if (event.getGuild() == null) {
            event.reply("This command is for guild.")
                    .queue();

            return;
        }

        var role = event.getOption(roleOptionName).getAsRole();
        int points = event.getOption(pointsOptionName).getAsInt();

        rewardsService.addNewRewardGoal(new RewardGoalDTO(
                null,
                event.getGuild().getId(),
                role.getId(),
                RewardTypes.ROLE,
                points
        ));

        event.reply(String.format("New reward for %s points was added: %s",
                        points, role.getName()))
                .setEphemeral(true)
                .queue();
    }
}
