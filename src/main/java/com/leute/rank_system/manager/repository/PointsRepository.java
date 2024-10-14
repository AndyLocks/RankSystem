package com.leute.rank_system.manager.repository;

import com.leute.rank_system.manager.entity.Points;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@JpaRepo
public interface PointsRepository extends JpaRepository<Points, Integer> {
    @Query(nativeQuery = true, value = "select * from points where guild_id = ?1")
    List<Points> findAllPointsByGuildId(String guildId);
}

