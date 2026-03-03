package com.example.animal.mapper;

import com.example.animal.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {
    List<MemberDto> allMemberList();

    MemberDto selectMemberByNo(int memberNo);

    int insertMember(MemberDto memberDto);

    int updateMember(MemberDto memberDto);

    int deleteMember(int memberNo);

    MemberDto findByMemberId(String memberId);
}
