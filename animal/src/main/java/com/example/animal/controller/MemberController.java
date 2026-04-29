package com.example.animal.controller;

import com.example.animal.config.AuthenticatedMemberProvider;
import com.example.animal.config.JwtTokenProvider;
import com.example.animal.dto.MemberDto;
import com.example.animal.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
@CrossOrigin(
    origins = {
        "http://localhost:5173",
        "https://animal-2g13.onrender.com"
    },
    allowCredentials = "true"
)
public class MemberController {
    private final AuthenticatedMemberProvider authenticatedMemberProvider;
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping
    public List<MemberDto> allMemberList() {
        return memberService.allMemberList();
    }

    @GetMapping("/{memberNo}")
    public MemberDto memberDetail(@PathVariable int memberNo) {
        return memberService.getMemberByNo(memberNo);
    }

    @PostMapping
    public int memberInsert(@RequestBody MemberDto memberDto) {
        return memberService.createMember(memberDto);
    }

    @PutMapping("/{memberNo}")
    public ResponseEntity<?> updateMember(
            @PathVariable int memberNo,
            @RequestBody MemberDto memberDto) {
        MemberDto loginMember = authenticatedMemberProvider.getRequiredMember();
        validateOwnershipOrAdmin(loginMember, memberNo);

        MemberDto existing = memberService.getMemberByNo(memberNo);
        if (existing == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Member not found."));
        }

        if (memberDto.getCurrentPw() == null || !existing.getMemberPw().equals(memberDto.getCurrentPw())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Current password does not match."));
        }

        if (memberDto.getMemberPw() != null && !memberDto.getMemberPw().isEmpty()) {
            existing.setMemberPw(memberDto.getMemberPw());
        }

        existing.setMemberName(memberDto.getMemberName());
        existing.setMemberEmail(memberDto.getMemberEmail());
        existing.setProfileVillagerNo(memberDto.getProfileVillagerNo());

        int result = memberService.updateMember(existing);
        if (result > 0) {
            return ResponseEntity.ok(memberService.getMemberByNo(memberNo));
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("message", "Failed to update member."));
    }

    @DeleteMapping("/{memberNo}")
    public int memberDelete(@PathVariable int memberNo) {
        MemberDto loginMember = authenticatedMemberProvider.getRequiredMember();
        validateOwnershipOrAdmin(loginMember, memberNo);
        return memberService.deleteMember(memberNo);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberDto dto) {
        try {
            MemberDto member = memberService.login(dto);

            Map<String, String> response = new HashMap<>();
            response.put("accessToken", jwtTokenProvider.createToken(member.getMemberId()));

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/me")
    public ResponseEntity<MemberDto> myPage() {
        return ResponseEntity.ok(authenticatedMemberProvider.getRequiredMember());
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok().build();
    }

    private void validateOwnershipOrAdmin(MemberDto loginMember, int targetMemberNo) {
        if (loginMember.getMemberNo() == targetMemberNo) {
            return;
        }

        if (loginMember.getIsAdmin() != null && loginMember.getIsAdmin() == 1) {
            return;
        }

        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to access this member.");
    }
}
