package com.leute.rank_system.rewards_service.repository.jpa;

import com.leute.rank_system.rewards_service.config.annotations.JpaRepo;
import com.leute.rank_system.rewards_service.entity.jpa.Points;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

@Repository
@JpaRepo
public interface PointsRepository extends JpaRepository<Points, Integer> {

    @Query(value = "select * from points where member_id = ?1 and guild_id = ?2", nativeQuery = true)
    Points findPointsByMemberIdAndGuildId(String memberId, String guildId);

}
