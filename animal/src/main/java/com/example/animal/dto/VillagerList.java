package com.example.animal.dto;

import lombok.Data;
@Data

public class VillagerList {
    private int villagerNo;
    private String villagerName;
    private String villagerImageIcon; // villager_image_icon
    private String villagerImage; // villager_image
    private String villagerBirth; // MM-DD

    private int memberNo; // 투표한 회원 번호
}
