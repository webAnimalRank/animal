package com.example.animal.controller;

import java.util.List;
import java.util.Map;

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
    origins = {
        "http://localhost:5173",
        "https://animal-2g13.onrender.com"
    },
    allowCredentials = "true"
)
public class MemberController {
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

    // 회원 수정 260311
    // 회원 수정 (기존 비밀번호 확인 + 새 비밀번호 적용)
    @PutMapping("/{memberNo}")
    public ResponseEntity<?> updateMember(
            @PathVariable int memberNo,
            @RequestBody MemberDto memberDto,
            HttpSession session) {

        // ✅ 여기서 들어오는 값 확인
        System.out.println("✅수정 요청 memberName: " + memberDto.getMemberName());
        System.out.println("✅수정 요청 memberEmail: " + memberDto.getMemberEmail());
        System.out.println("✅수정 요청 currentPw: " + memberDto.getCurrentPw());
        System.out.println("✅수정 요청 profileVillagerNo: " + memberDto.getProfileVillagerNo());

        // 기존 회원 정보 조회
        MemberDto existing = memberService.getMemberByNo(memberNo);

        if (existing == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "회원 정보가 존재하지 않습니다"));
        }

        // 기존 비밀번호 확인
        if (memberDto.getCurrentPw() == null ||
            !existing.getMemberPw().equals(memberDto.getCurrentPw())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "기존 비밀번호가 틀립니다"));
        }

        // 새 비밀번호 적용 (있으면)
        if (memberDto.getMemberPw() != null && !memberDto.getMemberPw().isEmpty()) {
            existing.setMemberPw(memberDto.getMemberPw());
        }

        // 이름, 이메일, 프로필 아이콘 업데이트
        existing.setMemberName(memberDto.getMemberName());
        existing.setMemberEmail(memberDto.getMemberEmail());
        existing.setProfileVillagerNo(memberDto.getProfileVillagerNo());

        int result = memberService.updateMember(existing);

        if (result > 0) {
            session.setAttribute("loginMember", existing);  // 세션 업데이트
            return ResponseEntity.ok(existing);
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("message", "회원정보 수정 실패"));

        
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