package com.leute.rank_system.rewards_service.repository.redis;

import java.util.function.BiFunction;

public class PointsCacheKeyGeneratorBiFunction implements BiFunction<String, String, String> {

    @Override
    public String apply(String guildId, String memberId) {
        return String.format("%s:%s", guildId, memberId);
    }

}
