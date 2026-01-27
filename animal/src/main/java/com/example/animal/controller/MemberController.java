package com.example.animal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.animal.dto.MemberDto;
import com.example.animal.service.MemberService;


@RestController
@RequestMapping("/members")
public class MemberController {
    @Autowired
    private MemberService ms;
    
    @GetMapping
    public List<MemberDto> allMemberList() {
        return ms.allMemberList();
    }
    
}
