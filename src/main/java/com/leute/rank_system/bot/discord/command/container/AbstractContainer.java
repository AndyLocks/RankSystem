package com.leute.rank_system.bot.discord.command.container;

import com.leute.rank_system.bot.discord.Bot;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.io.Serializable;
import java.util.List;

/**
 * Represents an abstract entity for storing,
 * displaying, and managing a list of data to be displayed in discord.
 * <p>
 * Examples: List of awards for a particular server,
 * rank of participants,
 * in general anything that can be scrolled with arrows and
 * anything that can contain information for display.
 *
 * @see ContainerData
 * @see PageNavigable
 */
public abstract class AbstractContainer implements ContainerData, PageNavigable, Serializable {

    private static final Button backwardButton = Button.secondary(Buttons.BACKWARD_BUTTON.getName(),
            Emoji.fromFormatted("<:arrow_backward_lts:1258861195385901138>"));

    private static final Button forwardButton = Button.secondary(Buttons.FORWARD_BUTTON.getName(),
            Emoji.fromFormatted("<:arrow_forward_lts:1258861075022086145>"));

    @Override
    public List<Button> buttons() {
        return List.of(
                this.hasPrevious() ? backwardButton : backwardButton.asDisabled(),
                this.hasNext() ? forwardButton : forwardButton.asDisabled()
        );
    }

}
