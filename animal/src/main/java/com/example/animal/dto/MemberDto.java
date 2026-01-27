package com.example.animal.dto;

import java.time.LocalDate;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("member")
public class MemberDto {
    private int member_no;
    private String member_id;
    private String member_pw;
    private String member_name;
    private String member_email;
    private int isactive;
    private LocalDate create_date;
    private LocalDate update_date;
}
