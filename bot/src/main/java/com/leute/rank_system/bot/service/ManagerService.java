package com.leute.rank_system.bot.service;

import com.leute.rank_system.bot.entity.dto.AllPointsDTO;
import com.leute.rank_system.bot.entity.dto.MemberDTO;
import com.leute.rank_system.bot.entity.dto.PointsDTO;
import org.jetbrains.annotations.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestClient;

import java.util.List;

/**
 * Interfaces with the manager microservice that handles points.
 */
@Service
public class ManagerService {

    private RestClient restClient;

    @Autowired
    public ManagerService(RestClient restClient) {
        this.restClient = restClient;
    }

    /**
     * All points that was find by a discord guild id
     *
     * @param guildId discord guild id
     * @return a list of points
     * @throws IllegalArgumentException if guild id is null
     */
    @Contract("null -> fail")
    public List<MemberDTO> getAllPointsByGuildId(String guildId) {
        Assert.notNull(guildId, "Guild id cannot be null");

        ResponseEntity<PointsDTO> responseEntity = restClient.get()
                .uri("rank/{guildId}", guildId)
                .retrieve()
                .toEntity(PointsDTO.class);

        return responseEntity.getBody().members();
    }

    /**
     * All points that was found.
     *
     * @return all points
     */
    public List<MemberDTO> getAllPoints() {
        var responseEntity = restClient.get()
                .uri("rank")
                .retrieve()
                .toEntity(AllPointsDTO.class);

        return responseEntity.getBody().members();
    }

}
