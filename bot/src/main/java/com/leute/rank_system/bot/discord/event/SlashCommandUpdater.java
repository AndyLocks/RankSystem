package com.leute.rank_system.bot.discord.event;

import com.leute.rank_system.bot.discord.command.AbstractCommandData;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Collects all {@link AbstractCommandData} and gives
 * it to discord api as soon as the bot is ready to work
 */
@Component
public class SlashCommandUpdater extends ListenerAdapter {

    private List<AbstractCommandData> commandDataList;
    private final Logger LOG = LoggerFactory.getLogger(SlashCommandUpdater.class);

    @Autowired
    public SlashCommandUpdater(List<AbstractCommandData> commandDataList) {
        this.commandDataList = commandDataList;
    }

    @Override
    public void onReady(ReadyEvent event) {
        event.getJDA()
                .updateCommands()
                .addCommands(commandDataList.stream()
                        .map(AbstractCommandData::getCommandData)
                        .toList())
                .queue();

        commandDataList.forEach(abstractCommandData ->
                LOG.info("New command was added: {}", abstractCommandData.getCommandData().getName()));
    }

}
