package com.example.animal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.animal.dto.LoginRequestDto;
import com.example.animal.dto.MemberDto;
import com.example.animal.mapper.MemberMapper;
import com.example.animal.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberSerivceImpl implements MemberService {
    private final MemberMapper memberMapper;
    private final JwtUtil jwtUtil; // JWT 유틸

    // @Autowired
    // private MemberMapper mm;

    @Override
    public List<MemberDto> allMemberList() {
        System.out.println(memberMapper.allMemberList());
        return memberMapper.allMemberList();
    }

    @Override
    public MemberDto getMemberByNo(int memberNo) {
        return memberMapper.selectMemberByNo(memberNo);
    }

    @Override
    public int createMember(MemberDto memberDto) {
        return memberMapper.insertMember(memberDto);
    }

    @Override
    public int updateMember(MemberDto memberDto) {
        return memberMapper.updateMember(memberDto);
    }

    @Override
    public int deleteMember(int memberNo) {
        return memberMapper.deleteMember(memberNo);
    }
    


    @Override
    public String login(LoginRequestDto dto) {

        // 1. 아이디로 회원 조회
        MemberDto member = memberMapper.findByMemberId(dto.getMemberId());

        if (member == null) {
            throw new RuntimeException("존재하지 않는 아이디");
        }

        // 2. 비밀번호 비교
        if (!member.getMemberPw().equals(dto.getMemberPw())) {
            throw new RuntimeException("비밀번호 불일치");
        }

        // 3. JWT 생성
        return jwtUtil.createToken(member.getMemberId());
    }
}
