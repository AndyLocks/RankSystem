package com.leute.rank_system.bot.discord.event;

import com.leute.rank_system.bot.discord.command.container.AbstractContainer;
import com.leute.rank_system.bot.discord.command.container.Buttons;
import com.leute.rank_system.bot.discord.command.container.ContainerRedisKeyGeneratorFunction;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * Manages {@link Buttons#BACKWARD_BUTTON} and {@link Buttons#FORWARD_BUTTON}
 */
@Component
public class OnForwardAndBackwardButtonsInteractionEvent extends ListenerAdapter {

    private final Function<String, String> keyGenerator = new ContainerRedisKeyGeneratorFunction();
    private Logger LOG = LoggerFactory.getLogger(OnForwardAndBackwardButtonsInteractionEvent.class);
    private RedisTemplate<String, AbstractContainer> abstractContainerRedisTemplate;

    @Autowired
    public OnForwardAndBackwardButtonsInteractionEvent(RedisTemplate<String, AbstractContainer> abstractContainerRedisTemplate) {
        this.abstractContainerRedisTemplate = abstractContainerRedisTemplate;
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {

        var key = keyGenerator.apply(event.getMessage().getInteraction().getId());

        var abstractContainer = abstractContainerRedisTemplate.opsForValue().get(key);

        if (abstractContainer == null) {
            LOG.warn("Null abstract container with key [{}]", key);
            event.reply("This message is old. I cant interact with it.")
                    .setEphemeral(true)
                    .queue();

            return;
        }

        switch (Buttons.fromName(event.getComponentId())) {
            case FORWARD_BUTTON -> abstractContainer.next();
            case BACKWARD_BUTTON -> abstractContainer.previous();
            case null -> {
                LOG.error("Unknown button with name [{}]", event.getComponentId());
                return;
            }
        }

        event.editMessageEmbeds(new EmbedBuilder()
                        .setDescription(abstractContainer.embeds().description())
                        .build())
                .setActionRow(abstractContainer.buttons())
                .queue(message -> {
                    abstractContainerRedisTemplate.opsForValue()
                            .set(key, abstractContainer, 5, TimeUnit.MINUTES);
                });
    }

}
