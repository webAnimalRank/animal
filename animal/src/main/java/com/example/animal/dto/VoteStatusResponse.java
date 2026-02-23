package com.example.animal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VoteStatusResponse {
    private String voteMonth;
    private int usedVotes;
    private int remainingVotes;
}
