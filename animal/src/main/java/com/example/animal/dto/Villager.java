package com.example.animal.dto;

import com.example.animal.type.VillagerType; // Import the new Enum
import lombok.Data;

@Data
public class Villager {
    private Integer villagerNo;
    private Integer villagerCategory;
    private VillagerType villagerType; // Changed to VillagerType enum
    private String villagerName;
    private String villagerNameEn;
    private String villagerNameJp;
    private String villagerImage;
    private String villagerBirth;
    private Integer villagerSex;
    private Integer villagerVote;

}
