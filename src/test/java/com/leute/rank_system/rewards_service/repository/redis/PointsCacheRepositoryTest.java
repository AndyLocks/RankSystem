package com.leute.rank_system.rewards_service.repository.redis;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.leute.rank_system.rewards_service.entity.redis.PointsCache;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class PointsCacheRepositoryTest {

    @Autowired
    private PointsCacheRepository pointsCacheRepository;

    @Test
    public void test() {
        if (true) return;

        var pointsCache = new PointsCache("memberid", "guildid", 89);

        pointsCacheRepository.save(pointsCache);

        assertTrue(pointsCacheRepository.exists("guildid", "memberid"));
        assertEquals(pointsCache, pointsCacheRepository.get("guildid", "memberid"));

        assertFalse(pointsCacheRepository.exists("aboba", "aboba"));
        assertNotEquals(pointsCache, pointsCacheRepository.get("aboba", "aboba"));
    }
}
