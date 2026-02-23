package com.example.animal.service;

import com.example.animal.dto.VillagerDetail;
import com.example.animal.dto.VillagerList;
import com.example.animal.dto.VillagerTypeOption;
import com.example.animal.dto.VoteStatusResponse;
import com.example.animal.dto.VoteTopResponse;

import java.util.List;

public interface VillagerService {
    List<VillagerList> getVillagers();

    List<VillagerTypeOption> getVillagerTypes();

    VillagerDetail getVillager(int villagerNo);

    List<VillagerList> searchVillagers(Integer type, Integer sex, String birthMonth, String debut, String keyword);

    VoteStatusResponse submitVotes(String memberId, List<Integer> villagerNos);

    VoteTopResponse getMonthlyTop3();

    VoteStatusResponse getMyVoteStatus(String memberId);
}
