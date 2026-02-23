package com.example.animal.dto;

import lombok.Data;

@Data
public class VoteTopItem {
    private int rank;
    private int villagerNo;
    private String villagerName;
    private String villagerImage;
    private String villagerImageIcon;
    private int votes;
}
