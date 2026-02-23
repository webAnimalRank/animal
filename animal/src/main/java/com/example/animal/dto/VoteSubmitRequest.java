package com.example.animal.dto;

import lombok.Data;

import java.util.List;

@Data
public class VoteSubmitRequest {
    private List<Integer> villagerNos;
}
