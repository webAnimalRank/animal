package com.example.animal.controller;

import com.example.animal.dto.MemberDto;
import com.example.animal.dto.VillagerDetail;
import com.example.animal.dto.VillagerList;
import com.example.animal.dto.VillagerTypeOption;
import com.example.animal.dto.VoteStatusResponse;
import com.example.animal.dto.VoteSubmitRequest;
import com.example.animal.dto.VoteTopResponse;
import com.example.animal.service.VillagerService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.function.Supplier;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/villagers")
@CrossOrigin(
        origins = "http://localhost:5173",
        allowCredentials = "true"
)
public class VillagerController {

    private final VillagerService villagerService;

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
            HttpSession session,
            @RequestBody VoteSubmitRequest request
    ) {
        String memberId = resolveMemberId(session);
        return handleBadRequestAndConflict(
                () -> villagerService.submitVotes(memberId, request.getVillagerNos())
        );
    }

    @GetMapping("/votes/me")
    public VoteStatusResponse getMyVoteStatus(
            HttpSession session
    ) {
        String memberId = resolveMemberId(session);
        return handleBadRequest(() -> villagerService.getMyVoteStatus(memberId));
    }

    @GetMapping("/votes/top")
    public VoteTopResponse getMonthlyTop3() {
        return villagerService.getMonthlyTop3();
    }

    private String resolveMemberId(HttpSession session) {
        MemberDto member = (MemberDto) session.getAttribute("loginMember");
        if (member == null || member.getMemberId() == null || member.getMemberId().isBlank()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login required.");
        }
        return member.getMemberId();
    }

    private <T> T handleBadRequest(Supplier<T> action) {
        try {
            return action.get();
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    private <T> T handleBadRequestAndConflict(Supplier<T> action) {
        try {
            return action.get();
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (IllegalStateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }
    }

    // mypage mypick 호출시 해당 회원이 현재 달에 투표한 동물들의 list 반환
    @GetMapping("/votes/me/list")
    public List<VillagerList> getMyVotedVillagers(
            @RequestHeader(value = "Authorization", required = false) String authorization
    ) {
        String memberId = resolveMemberId(authorization);
        return handleBadRequest(() -> villagerService.getMyVotedVillagers(memberId));
    }
}
