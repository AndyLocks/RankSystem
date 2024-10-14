package com.leute.rank_system.rewards_service.repository.redis;

import com.leute.rank_system.rewards_service.entity.redis.PointsCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.function.BiFunction;
import java.util.function.Function;

@Repository
public class PointsCacheRepository {

    private RedisTemplate<String, PointsCache> pointsCacheRedisTemplate;
    private Logger LOG = LoggerFactory.getLogger(PointsCacheRepository.class);
    private Function<PointsCache, String> keyGenerator = new PointsCacheKeyGeneratorFunction();
    private BiFunction<String, String, String> biKeyGenerator = new PointsCacheKeyGeneratorBiFunction();

    @Autowired
    public PointsCacheRepository(RedisTemplate<String, PointsCache> pointsCacheRedisTemplate) {
        this.pointsCacheRedisTemplate = pointsCacheRedisTemplate;
    }

    public void save(PointsCache pointsCache) {
        pointsCacheRedisTemplate.opsForValue().set(keyGenerator.apply(pointsCache), pointsCache);
        LOG.info("Saved points cache {}", pointsCache);
    }

    public PointsCache get(String guildId, String memberId) {
        return pointsCacheRedisTemplate.opsForValue().get(biKeyGenerator.apply(guildId, memberId));
    }

    public boolean exists(String guildId, String memberId) {
        return pointsCacheRedisTemplate.opsForValue().get(biKeyGenerator.apply(guildId, memberId)) != null;
    }

}
