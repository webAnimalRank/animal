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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.function.Supplier;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/villagers")
@CrossOrigin(
        origins = {"http://localhost:5173", "https://animalcrossingrank.netlify.app", "https://0ef352f5.animal-guide.pages.dev"},
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
        int memberNo = requireMemberNo(session);
        return handleBadRequestAndConflict(
                () -> villagerService.submitVotes(memberNo, request.getVillagerNos())
        );
    }

    @GetMapping("/votes/me")
    public VoteStatusResponse getMyVoteStatus(HttpSession session) {
        int memberNo = requireMemberNo(session);
        return handleBadRequest(() -> villagerService.getMyVoteStatus(memberNo));
    }

    @GetMapping("/votes/top")
    public VoteTopResponse getMonthlyTop3() {
        return villagerService.getMonthlyTop3();
    }

    // @GetMapping("/votes/me/list")
    // public List<VillagerList> getMyVotedVillagers(HttpSession session) {
    //     int memberNo = requireMemberNo(session);
    //     return handleBadRequest(() -> villagerService.getMyVotedVillagers(memberNo));
    // }

    @GetMapping("/votes/me/list")
    public List<VillagerList> getMyVotedVillagers(
            HttpSession session,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month
    ) {
        int memberNo = requireMemberNo(session);
        return handleBadRequest(() -> villagerService.getMyVotedVillagers(memberNo, year, month));
    }

    private int requireMemberNo(HttpSession session) {
        MemberDto member = (MemberDto) session.getAttribute("loginMember");
        if (member == null || member.getMemberNo() <= 0) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login required.");
        }
        return member.getMemberNo();
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
}
