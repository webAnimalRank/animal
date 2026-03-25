package com.example.animal.dto;
import java.sql.Timestamp;
import org.apache.ibatis.type.Alias;
import lombok.Data;

@Data
@Alias("member")
public class MemberDto {
    private int memberNo;
    private String memberId;
    private String memberPw;
    private String memberName;
    private String memberEmail;
    private int isActive;
    private Timestamp createDate;
    private Timestamp updateDate;

    private String currentPw; // 기존 비밀번호 확인용
    private Integer profileVillagerNo;
}
