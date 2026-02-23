package com.example.animal.controller;

import java.util.List;

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

import com.example.animal.dto.MemberDto;
import com.example.animal.service.MemberService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
@CrossOrigin(
    origins = "http://localhost:5173",
    allowCredentials = "true"
)
public class MemberController {
    // @Autowired
    // private MemberService ms;

    private final MemberService memberService;

    // 모든 회원 조회
    @GetMapping
    public List<MemberDto> allMemberList() {
        return memberService.allMemberList();
    }

        // 회원 단건 조회
    @GetMapping("/{memberNo}")
    public MemberDto memberDetail(@PathVariable int memberNo) {
        return memberService.getMemberByNo(memberNo);
    }

    // 회원 등록
    @PostMapping
    public int memberInsert(@RequestBody MemberDto memberDto) {
        return memberService.createMember(memberDto);
    }

    // 회원 수정
    @PutMapping("/{memberNo}")
    public int memberUpdate(
            @PathVariable int memberNo,
            @RequestBody MemberDto memberDto) {

        memberDto.setMemberNo(memberNo);
        return memberService.updateMember(memberDto);
    }

    // 회원 삭제
    @DeleteMapping("/{memberNo}")
    public int memberDelete(@PathVariable int memberNo) {
        return memberService.deleteMember(memberNo);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(
        @RequestBody MemberDto dto,
        HttpSession session) {

    try {
        MemberDto member = memberService.login(dto);

        session.setAttribute("loginMember", member);

        return ResponseEntity.ok().build();

    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    }

    // mypage api
    @GetMapping("/me")
    public ResponseEntity<MemberDto> myPage(HttpSession session) {
        MemberDto member = (MemberDto) session.getAttribute("loginMember");
        if (member == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(member);
    }

    // logout api
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
    session.invalidate(); // 세션 삭제
    return ResponseEntity.ok().build();
}
    
}