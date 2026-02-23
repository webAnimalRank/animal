package com.example.animal.service;

import com.example.animal.dto.VillagerDetail;
import com.example.animal.dto.VillagerList;
import com.example.animal.mapper.VillagerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VillagerServiceImpl implements VillagerService {

    private final VillagerMapper villagerMapper;

    @Override
    public List<VillagerList> getVillagers() {
        return villagerMapper.selectVillagers();
    }

    @Override
    public VillagerDetail getVillager(int villagerNo) {
        return villagerMapper.selectVillagerByNo(villagerNo);
    }

    @Override
    public List<VillagerList> searchVillagers(Integer type, Integer sex, String birthMonth, String keyword) {
        // birthMonth: "2" 같은 값이 오면 "02"로 보정 (안전장치)
        if (birthMonth != null && !birthMonth.isBlank()) {
            String trimmed = birthMonth.trim();
            if (trimmed.length() == 1) birthMonth = "0" + trimmed;
            else birthMonth = trimmed;
        }
        if (keyword != null) {
            keyword = keyword.trim();
            if (keyword.isEmpty()) keyword = null;
        }
        return villagerMapper.searchVillagers(type, sex, birthMonth, keyword);
    }
}
