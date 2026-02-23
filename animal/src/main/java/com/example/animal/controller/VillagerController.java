package com.example.animal.controller;

import com.example.animal.dto.VillagerDetail;
import com.example.animal.dto.VillagerList;
import com.example.animal.dto.VillagerTypeOption;
import com.example.animal.dto.VoteStatusResponse;
import com.example.animal.dto.VoteSubmitRequest;
import com.example.animal.dto.VoteTopResponse;
import com.example.animal.service.VillagerService;
import com.example.animal.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/villagers")
@CrossOrigin(origins = "http://localhost:5173")
public class VillagerController {

    private final VillagerService villagerService;
    private final JwtUtil jwtUtil;

    @GetMapping
    public List<VillagerList> getVillagers() {
        return villagerService.getVillagers();
    }

    @GetMapping("/types")
    public List<VillagerTypeOption> getVillagerTypes() {
        return villagerService.getVillagerTypes();
    }

    @GetMapping("/{villagerNo}")
    public VillagerDetail getVillager(@PathVariable int villagerNo) {
        return villagerService.getVillager(villagerNo);
    }

    @GetMapping("/search")
    public List<VillagerList> searchVillagers(
            @RequestParam(required = false) Integer type,
            @RequestParam(required = false) Integer sex,
            @RequestParam(required = false) String birthMonth,
            @RequestParam(required = false) String debut,
            @RequestParam(required = false) String keyword
    ) {
        return villagerService.searchVillagers(type, sex, birthMonth, debut, keyword);
    }

    @PostMapping("/votes")
    public VoteStatusResponse submitVotes(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestBody VoteSubmitRequest request
    ) {
        String memberId = extractMemberIdFromBearerToken(authorization);
        try {
            return villagerService.submitVotes(memberId, request.getVillagerNos());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (IllegalStateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }
    }

    @GetMapping("/votes/me")
    public VoteStatusResponse getMyVoteStatus(
            @RequestHeader(value = "Authorization", required = false) String authorization
    ) {
        String memberId = extractMemberIdFromBearerToken(authorization);
        try {
            return villagerService.getMyVoteStatus(memberId);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @GetMapping("/votes/top")
    public VoteTopResponse getMonthlyTop3() {
        return villagerService.getMonthlyTop3();
    }

    private String extractMemberIdFromBearerToken(String authorization) {
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing bearer token.");
        }
        String token = authorization.substring(7);
        if (!jwtUtil.isValidToken(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token.");
        }
        return jwtUtil.extractMemberId(token);
    }
}
