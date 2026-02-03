package com.example.animal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.animal.dto.MemberDto;
import com.example.animal.mapper.MemberMapper;

@Service
public class MemberSerivceImpl implements MemberService {

    @Autowired
    private MemberMapper mm;

    @Override
    public List<MemberDto> allMemberList() {
        System.out.println(mm.allMemberList());
        return mm.allMemberList();
    }

    @Override
    public MemberDto getMemberByNo(int memberNo) {
        return mm.selectMemberByNo(memberNo);
    }

    @Override
    public int createMember(MemberDto memberDto) {
        return mm.insertMember(memberDto);
    }

    @Override
    public int updateMember(MemberDto memberDto) {
        return mm.updateMember(memberDto);
    }

    @Override
    public int deleteMember(int memberNo) {
        return mm.deleteMember(memberNo);
    }
    

    
}
