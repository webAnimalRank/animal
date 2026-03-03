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
    private static final String MEMBER_NO_REQUIRED = "memberNo is required.";

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
        String normalizedBirthMonth = normalizeBirthMonth(birthMonth);
        String normalizedDebut = normalizeOptionalText(debut);
        String normalizedKeyword = normalizeOptionalText(keyword);

        return villagerMapper.searchVillagers(type, sex, normalizedBirthMonth, normalizedDebut, normalizedKeyword);
    }

    @Override
    @Transactional
    public VoteStatusResponse submitVotes(int memberNo, List<Integer> villagerNos) {
        int validatedMemberNo = requireMemberNo(memberNo);

        if (villagerNos == null || villagerNos.isEmpty()) {
            throw new IllegalArgumentException("At least one villager must be selected.");
        }

        List<Integer> voteTargets = distinctVoteTargets(villagerNos);
        if (voteTargets.size() != villagerNos.size()) {
            throw new IllegalArgumentException("Duplicate villager selections are not allowed.");
        }

        String voteMonth = currentVoteMonth();
        int usedVotes = villagerMapper.countMonthlyVotesByMember(validatedMemberNo, voteMonth);
        int remainingVotes = MAX_MONTHLY_VOTES - usedVotes;

        if (remainingVotes <= 0) {
            throw new IllegalStateException("No remaining votes for this month.");
        }
        if (voteTargets.size() > remainingVotes) {
            throw new IllegalStateException("Requested votes exceed remaining votes.");
        }

        int validVillagerCount = villagerMapper.countExistingVillagers(voteTargets);
        if (validVillagerCount != voteTargets.size()) {
            throw new IllegalArgumentException("Some villagers are invalid.");
        }

        villagerMapper.insertVoteHistories(validatedMemberNo, voteMonth, voteTargets);
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
    public VoteStatusResponse getMyVoteStatus(int memberNo) {
        int validatedMemberNo = requireMemberNo(memberNo);
        String voteMonth = currentVoteMonth();
        int usedVotes = villagerMapper.countMonthlyVotesByMember(validatedMemberNo, voteMonth);
        return new VoteStatusResponse(voteMonth, usedVotes, Math.max(0, MAX_MONTHLY_VOTES - usedVotes));
    }

    private String currentVoteMonth() {
        return YearMonth.now().toString();
    }

    @Override
    public List<VillagerList> getMyVotedVillagers(int memberNo) {
        int validatedMemberNo = requireMemberNo(memberNo);
        String voteMonth = currentVoteMonth();
        return villagerMapper.selectMyVotedVillagers(validatedMemberNo, voteMonth);
    }

    private int requireMemberNo(int memberNo) {
        if (memberNo <= 0) {
            throw new IllegalArgumentException(MEMBER_NO_REQUIRED);
        }
        return memberNo;
    }

    private String normalizeBirthMonth(String birthMonth) {
        if (birthMonth == null || birthMonth.isBlank()) {
            return null;
        }

        String trimmed = birthMonth.trim();
        return trimmed.length() == 1 ? "0" + trimmed : trimmed;
    }

    private String normalizeOptionalText(String value) {
        if (value == null) {
            return null;
        }

        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    private List<Integer> distinctVoteTargets(List<Integer> villagerNos) {
        Set<Integer> distinctNos = new LinkedHashSet<>(villagerNos);
        return new ArrayList<>(distinctNos);
    }
}
