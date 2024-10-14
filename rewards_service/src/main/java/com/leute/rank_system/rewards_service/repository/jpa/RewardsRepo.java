package com.leute.rank_system.rewards_service.repository.jpa;

import com.leute.rank_system.rewards_service.config.annotations.JpaRepo;
import com.leute.rank_system.rewards_service.entity.jpa.Reward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@JpaRepo
public interface RewardsRepo extends JpaRepository<Reward, Integer> {
}
