package com.leute.rank_system.rewards_service.repository.redis;

import com.leute.rank_system.rewards_service.entity.redis.GuildRewardGoalsCache;
import com.leute.rank_system.rewards_service.entity.redis.RewardGoalCache;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.leute.rank_system.rewards_service.entity.RewardTypes;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class GuildRewardGoalsCacheRepositoryTest {

    @Autowired
    private GuildRewardGoalsCacheRepository repository;

    @Test
    public void test() {
        if (true) return;

        var guildRewardGoalsCache = new GuildRewardGoalsCache("aboba", List.of(
                new RewardGoalCache("aboba", RewardTypes.ROLE, "role1", 45),
                new RewardGoalCache("aboba", RewardTypes.ROLE, "role2", 23),
                new RewardGoalCache("aboba", RewardTypes.ROLE, "role3", 4325),
                new RewardGoalCache("aboba", RewardTypes.URL, "https://google.com", 342534),
                new RewardGoalCache("aboba", RewardTypes.ROLE, "role4", 995),
                new RewardGoalCache("aboba", RewardTypes.PROMO_CODE, "alkdsjfebjkf", 324)
        ));

        repository.save(guildRewardGoalsCache);
        assertEquals(guildRewardGoalsCache, repository.findById("aboba").get());
    }

}
