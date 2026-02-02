package com.example.animal.dto;

import lombok.Data;

@Data
public class VillagerDetail {
    private int villagerNo;

    private int villagerCategory;
    private int villagerType;
    private String villagerTypeName;

    private String villagerName;
    private String villagerNameEn;
    private String villagerNameJp;

    private String villagerImage;      // villager_image (모달)
    private String villagerImageIcon;  // villager_image_icon (리스트)

    private String villagerBirth;      // MM-DD
    private String villagerDebut;      // 데뷔작
    private Integer villagerSex;

    private int villagerVote;
}
