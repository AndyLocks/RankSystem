package com.leute.rank_system.bot.service;

import com.leute.rank_system.bot.entity.dto.RewardDTO;
import com.leute.rank_system.bot.entity.dto.RewardGoalDTO;
import org.jetbrains.annotations.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.util.List;

/**
 * Interfaces with the rewards microservice that handles all rewards and reward goals.
 */
@Service
public class RewardsService {

    private RestClient restClient;

    @Autowired
    public RewardsService(RestClient restClient) {
        this.restClient = restClient;
    }

    public List<RewardGoalDTO> allRewardGoals() {
        var responseEntity = restClient.get()
                .uri("rewards_service/goals")
                .retrieve()
                .toEntity(RewardGoalDTO[].class);

        return List.of(responseEntity.getBody());
    }

    /**
     * All reward goals that was find by a discord guild id
     *
     * @param guildId discord guild id
     * @return a list of goals
     * @throws IllegalArgumentException if guild id is null
     */
    @Contract("null -> fail")
    public List<RewardGoalDTO> allRewardGoals(String guildId) {
        Assert.notNull(guildId, "Guild id cannot be null");

        var responseEntity = restClient.get()
                .uri("rewards_service/goals/{guildId}", guildId)
                .retrieve()
                .toEntity(RewardGoalDTO[].class);

        return List.of(responseEntity.getBody());
    }

    public List<RewardDTO> allRewards() {
        var responseEntity = restClient.get()
                .uri("rewards_service/rewards")
                .retrieve()
                .toEntity(RewardDTO[].class);

        return List.of(responseEntity.getBody());
    }

    /**
     * All rewards that was find by a discord guild id
     *
     * @param guildId discord guild id
     * @return a list of rewards
     * @throws IllegalArgumentException if guild id is null
     */
    @Contract("null -> fail")
    public List<RewardDTO> allRewards(String guildId) {
        Assert.notNull(guildId, "Guild id cannot be null");

        var responseEntity = restClient.get()
                .uri("rewards_service/rewards/{guildId}", guildId)
                .retrieve()
                .toEntity(RewardDTO[].class);

        return List.of(responseEntity.getBody());
    }

    /**
     * Saves a new reward goal
     *
     * @param rewardGoal reward goal to save
     * @return the {@link ResponseEntity} with the decoded body
     * @throws HttpSendException        if status code is not 2xx
     * @throws IllegalArgumentException if {@code rewardGoal} is null
     */
    @Contract("null -> fail")
    public ResponseEntity<RewardGoalDTO> addNewRewardGoal(RewardGoalDTO rewardGoal) {
        Assert.notNull(rewardGoal, "Reward goal cannot be null");

        var responseEntity = restClient.post()
                .uri("rewards_service/new_goal")
                .body(rewardGoal)
                .retrieve()
                .toEntity(RewardGoalDTO.class);

        if (!responseEntity.getStatusCode().is2xxSuccessful())
            throw new HttpSendException("Send error. Http status", responseEntity.getStatusCode());

        return responseEntity;
    }

    /**
     * Deletes the reward goal
     *
     * @param rewardGoalDTO reward goal to delete
     * @return the {@link ResponseEntity} with the decoded body
     * @throws HttpSendException        if status code is not 2xx
     * @throws IllegalArgumentException if {@code rewardGoal} is null
     */
    public ResponseEntity<Void> deleteRewardGoal(RewardGoalDTO rewardGoalDTO) {
        Assert.notNull(rewardGoalDTO, "Reward goal cannot be null");

        return this.deleteRewardGoal(rewardGoalDTO.id());
    }

    /**
     * Deletes the reward goal
     *
     * @param id database id of reward goal to delete
     * @return the {@link ResponseEntity} with the decoded body
     * @throws IllegalArgumentException if {@code rewardGoal} is null
     */
    public ResponseEntity<Void> deleteRewardGoal(int id) {
        Assert.notNull(id, "Id cannot be null");

        return restClient.delete()
                .uri("rewards_service/reward_goal/{id}", id)
                .retrieve()
                .onStatus(httpStatusCode -> httpStatusCode.value() == 404, new RestClient.ResponseSpec.ErrorHandler() {
                    @Override
                    public void handle(HttpRequest request, ClientHttpResponse response) throws IOException {}
                })
                .toBodilessEntity();
    }

}
