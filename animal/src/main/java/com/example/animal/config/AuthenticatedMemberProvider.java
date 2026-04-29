package com.example.animal.config;

import com.example.animal.dto.MemberDto;
import com.example.animal.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@RequiredArgsConstructor
public class AuthenticatedMemberProvider {

    private final MemberService memberService;

    public MemberDto getRequiredMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null
                || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login is required.");
        }

        String memberId = authentication.getName();
        if (memberId == null || memberId.isBlank()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid authentication.");
        }

        MemberDto member = memberService.getMemberById(memberId);
        if (member == null || member.getIsActive() != 1) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Member not found.");
        }

        return member;
    }
}
