package com.leute.rank_system.bot.discord.command.container;

import com.leute.rank_system.bot.discord.command.container.entity.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Container for {@link com.leute.rank_system.bot.discord.command.RankCommand}
 *
 * Contains and manages members with points to be displayed ({@link Member})
 */
public class PointsContainer extends AbstractContainer {

    private final List<ContainerEmbed> embeds = new ArrayList<>();
    private Logger LOG = LoggerFactory.getLogger(PointsContainer.class);
    private final AtomicInteger index = new AtomicInteger(0);

    /**
     * @param members elements to display
     */
    public PointsContainer(List<Member> members) {
        for (int i = 0; ; i = i + 5) {

            var membersList = new ArrayList<Member>(members.size());
            try {
                for (int j = i; j < i + 5; j++) {
                    membersList.add(members.get(j));
                }
            } catch (IndexOutOfBoundsException e) {
                break;
            } finally {
                embeds.add(membersToEmbed(membersList));
            }

        }
    }

    private ContainerEmbed membersToEmbed(Collection<Member> members) {

        var descriptionStringBuilder = new StringBuilder()
                .append("# Rank\n\n");

        members.forEach(member -> {
            descriptionStringBuilder.append(String.format("**%s** : %s%n%n",
                    member.nickname(),
                    member.points()));
        });

        return new ContainerEmbed(descriptionStringBuilder.toString());
    }

    @Override
    public ContainerEmbed embeds() {
        return embeds.get(index.get());
    }

    @Override
    public void next() {
        if (hasNext()) index.getAndIncrement();
    }

    @Override
    public void previous() {
        if (hasPrevious()) index.getAndDecrement();
    }

    @Override
    public boolean hasNext() {
        return embeds.size() - 1 > index.get();
    }

    @Override
    public boolean hasPrevious() {
        return index.get() > 0;
    }

}
