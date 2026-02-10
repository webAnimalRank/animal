package com.example.animal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.example.animal.dto.LoginRequestDto;
import com.example.animal.dto.LoginResponseDto;
import com.example.animal.dto.MemberDto;
import com.example.animal.service.MemberService;
import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
@CrossOrigin(origins = "http://localhost:5173")
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
    public ResponseEntity<LoginResponseDto> login(
            @RequestBody LoginRequestDto dto) {

    try {  
        String token = memberService.login(dto);
        System.out.println("🔥🔥🔥🔥🔥🔥🔥로그인 성공, 토큰: " + token);
        return ResponseEntity.ok(new LoginResponseDto(token));
    } catch (Exception e) {
        System.out.println("🔥🔥🔥🔥🔥🔥로그인 실패: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    }
    
}