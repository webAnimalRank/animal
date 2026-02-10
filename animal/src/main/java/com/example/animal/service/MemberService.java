package com.example.animal.service;

import java.lang.reflect.Member;
import java.util.List;

import com.example.animal.dto.LoginRequestDto;
import com.example.animal.dto.MemberDto;


public interface MemberService {
    List<MemberDto> allMemberList();
    
    MemberDto getMemberByNo(int memberNo);

    int createMember(MemberDto memberDto);

    int updateMember(MemberDto memberDto);

    int deleteMember(int memberNo);

    String login(LoginRequestDto dto);

}
