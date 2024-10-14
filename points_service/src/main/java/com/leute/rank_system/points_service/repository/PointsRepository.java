package com.leute.rank_system.points_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.leute.rank_system.points_service.entity.Points;

@Repository
public interface PointsRepository extends JpaRepository<Points, Integer> {
    @Query(value = "select * from points where member_id = ?1 and guild_id = ?2", nativeQuery = true)
    Points findPointsByMemberIdAndGuildId(String memberId, String guildId);
}
