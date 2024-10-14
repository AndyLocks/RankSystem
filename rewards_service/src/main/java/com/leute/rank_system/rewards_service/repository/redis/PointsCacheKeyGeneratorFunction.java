package com.leute.rank_system.rewards_service.repository.redis;

import com.leute.rank_system.rewards_service.entity.redis.PointsCache;

import java.util.function.Function;

public class PointsCacheKeyGeneratorFunction implements Function<PointsCache, String> {

    @Override
    public String apply(PointsCache pointsCache) {
        return String.format("%s:%s", pointsCache.guildId(), pointsCache.memberId());
    }

}
