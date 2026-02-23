package com.example.animal.service;

import com.example.animal.dto.VillagerDetail;
import com.example.animal.dto.VillagerList;
import com.example.animal.dto.VillagerTypeOption;
import com.example.animal.dto.VoteStatusResponse;
import com.example.animal.dto.VoteTopItem;
import com.example.animal.dto.VoteTopResponse;
import com.example.animal.mapper.VillagerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class VillagerServiceImpl implements VillagerService {

    private static final int MAX_MONTHLY_VOTES = 3;

    private final VillagerMapper villagerMapper;

    @Override
    public List<VillagerList> getVillagers() {
        return villagerMapper.selectVillagers();
    }

    @Override
    public List<VillagerTypeOption> getVillagerTypes() {
        return villagerMapper.selectVillagerTypes();
    }

    @Override
    public VillagerDetail getVillager(int villagerNo) {
        return villagerMapper.selectVillagerByNo(villagerNo);
    }

    @Override
    public List<VillagerList> searchVillagers(Integer type, Integer sex, String birthMonth, String debut, String keyword) {
        if (birthMonth != null && !birthMonth.isBlank()) {
            String trimmed = birthMonth.trim();
            if (trimmed.length() == 1) {
                birthMonth = "0" + trimmed;
            } else {
                birthMonth = trimmed;
            }
        }
        if (keyword != null) {
            keyword = keyword.trim();
            if (keyword.isEmpty()) {
                keyword = null;
            }
        }
        if (debut != null) {
            debut = debut.trim();
            if (debut.isEmpty()) {
                debut = null;
            }
        }
        return villagerMapper.searchVillagers(type, sex, birthMonth, debut, keyword);
    }

    @Override
    @Transactional
    public VoteStatusResponse submitVotes(String memberId, List<Integer> villagerNos) {
        if (memberId == null || memberId.isBlank()) {
            throw new IllegalArgumentException("memberId is required.");
        }

        if (villagerNos == null || villagerNos.isEmpty()) {
            throw new IllegalArgumentException("At least one villager must be selected.");
        }

        Set<Integer> distinctNos = new LinkedHashSet<>(villagerNos);
        if (distinctNos.size() != villagerNos.size()) {
            throw new IllegalArgumentException("Duplicate villager selections are not allowed.");
        }

        String voteMonth = currentVoteMonth();
        int usedVotes = villagerMapper.countMonthlyVotesByMember(memberId, voteMonth);
        int remainingVotes = MAX_MONTHLY_VOTES - usedVotes;

        if (remainingVotes <= 0) {
            throw new IllegalStateException("No remaining votes for this month.");
        }
        if (distinctNos.size() > remainingVotes) {
            throw new IllegalStateException("Requested votes exceed remaining votes.");
        }

        List<Integer> voteTargets = new ArrayList<>(distinctNos);
        int validVillagerCount = villagerMapper.countExistingVillagers(voteTargets);
        if (validVillagerCount != voteTargets.size()) {
            throw new IllegalArgumentException("Some villagers are invalid.");
        }

        villagerMapper.insertVoteHistories(memberId, voteMonth, voteTargets);
        villagerMapper.increaseVillagerVotes(voteTargets);

        int totalUsedVotes = usedVotes + voteTargets.size();
        return new VoteStatusResponse(voteMonth, totalUsedVotes, MAX_MONTHLY_VOTES - totalUsedVotes);
    }

    @Override
    public VoteTopResponse getMonthlyTop3() {
        String voteMonth = currentVoteMonth();
        List<VoteTopItem> items = villagerMapper.selectMonthlyTop3(voteMonth);
        for (int i = 0; i < items.size(); i++) {
            items.get(i).setRank(i + 1);
        }
        return new VoteTopResponse(voteMonth, items);
    }

    @Override
    public VoteStatusResponse getMyVoteStatus(String memberId) {
        if (memberId == null || memberId.isBlank()) {
            throw new IllegalArgumentException("memberId is required.");
        }
        String voteMonth = currentVoteMonth();
        int usedVotes = villagerMapper.countMonthlyVotesByMember(memberId, voteMonth);
        return new VoteStatusResponse(voteMonth, usedVotes, Math.max(0, MAX_MONTHLY_VOTES - usedVotes));
    }

    private String currentVoteMonth() {
        return YearMonth.now().toString();
    }
}
