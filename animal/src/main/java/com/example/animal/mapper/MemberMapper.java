package com.example.animal.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.animal.dto.MemberDto;

@Mapper
public interface MemberMapper {
    List<MemberDto> allMemberList();
}
