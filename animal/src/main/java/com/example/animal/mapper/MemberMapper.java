package com.example.animal.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.animal.dto.MemberDto;

@Mapper
public interface MemberMapper {
    List<MemberDto> allMemberList();

    // 회원 단건 조회
    MemberDto selectMemberByNo(int memberNo);

    // 회원 등록
    int insertMember(MemberDto memberDto);

    // 회원 수정
    int updateMember(MemberDto memberDto);

    // 회원 삭제 (논리 삭제)
    int deleteMember(int memberNo);

    // 로그인 회원 조회
    MemberDto findByMemberId(String memberId);
}
