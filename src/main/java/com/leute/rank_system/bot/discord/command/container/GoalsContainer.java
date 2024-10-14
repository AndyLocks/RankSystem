package com.leute.rank_system.bot.discord.command.container;

import com.leute.rank_system.bot.discord.command.container.entity.RewardGoal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Container for {@link com.leute.rank_system.bot.discord.command.GoalsCommand}
 *
 * Contains and manages rewards to be displayed ({@link RewardGoal})
 */
public class GoalsContainer extends AbstractContainer {

    private final List<ContainerEmbed> embeds = new ArrayList<>();
    private Logger LOG = LoggerFactory.getLogger(GoalsContainer.class);
    private final AtomicInteger index = new AtomicInteger(0);

    /**
     * @param membersList elements to display
     */
    public GoalsContainer(List<RewardGoal> membersList) {
        var members = membersList.stream()
                .sorted(Comparator.comparing(RewardGoal::points))
                .toList();

        for (int i = 0; ; i = i + 5) {

            var rewardGoalDTOS = new ArrayList<RewardGoal>(members.size());
            try {
                for (int j = i; j < i + 5; j++) {
                    rewardGoalDTOS.add(members.get(j));
                }
            } catch (IndexOutOfBoundsException e) {
                break;
            } finally {
                embeds.add(membersToEmbed(rewardGoalDTOS));
            }

        }

    }

    private ContainerEmbed membersToEmbed(Collection<RewardGoal> rewards) {

        var descriptionStringBuilder = new StringBuilder()
                .append("# All Reward Goals\n\n");

        rewards.forEach(rewardGoal -> {
            var description = switch (rewardGoal.type()) {
                case ROLE -> {
                    yield String.format("**%s.** **%s** (%s) : %s%n%n",
                            rewardGoal.id(),
                            rewardGoal.roleName(),
                            "Role",
                            rewardGoal.points());
                }
                case URL -> String.format("**%s.** **%s** : %s%n%n",
                        rewardGoal.id(),
                        "Secret url reward",
                        rewardGoal.points());
                case PROMO_CODE -> String.format("**%s.** **%s** : %s%n%n",
                        rewardGoal.id(),
                        "Promo code",
                        rewardGoal.points());
            };

            descriptionStringBuilder.append(description);
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
