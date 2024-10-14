package com.leute.rank_system.rewards_service.repository.redis;

import com.leute.rank_system.rewards_service.config.annotations.RedisRepo;
import com.leute.rank_system.rewards_service.entity.redis.GuildRewardGoalsCache;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@RedisRepo
public interface GuildRewardGoalsCacheRepository extends CrudRepository<GuildRewardGoalsCache, String> {
}
