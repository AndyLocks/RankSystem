package com.leute.rank_system.manager.controller;

import com.leute.rank_system.manager.entity.AllPointsDTO;
import com.leute.rank_system.manager.entity.MemberDTO;
import com.leute.rank_system.manager.entity.Points;
import com.leute.rank_system.manager.entity.PointsDTO;
import com.leute.rank_system.manager.repository.PointsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PointsController {

    private PointsRepository pointsRepository;
    private Logger LOG = LoggerFactory.getLogger(PointsController.class);

    @Autowired
    public PointsController(PointsRepository pointsRepository) {
        this.pointsRepository = pointsRepository;
    }

    @GetMapping("rank/{guildId}")
    public ResponseEntity<PointsDTO> getRank(@PathVariable String guildId) {

        LOG.info("New rank request with guild id {}", guildId);

        var example = new Points();
        example.setGuildId(guildId);

        var points = pointsRepository.findAllPointsByGuildId(guildId);

        var members = points.stream()
                .map(points1 -> new MemberDTO(points1.getMemberId(),
                        points1.getGuildId(),
                        points1.getPoints()))
                .toList();

        var pointsDTO = new PointsDTO(guildId, members);

        return ResponseEntity.ok(pointsDTO);
    }

    @GetMapping("rank")
    public ResponseEntity<AllPointsDTO> getAllRank() {

        var members = pointsRepository.findAll().stream()
                .map(points1 -> new MemberDTO(points1.getMemberId(),
                        points1.getGuildId(),
                        points1.getPoints()))
                .toList();

        var pointsDTO = new AllPointsDTO(members);

        return ResponseEntity.ok(pointsDTO);
    }

}
