package com.example.animal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class VoteRankingResponse {
    private String voteMonth;
    private List<VoteTopItem> items;
}
