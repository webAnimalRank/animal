package com.example.animal.service;

import com.example.animal.dto.VillagerDetail;
import com.example.animal.dto.VillagerList;

import java.util.List;

public interface VillagerService {
    List<VillagerList> getVillagers();
    VillagerDetail getVillager(int villagerNo);

    // 검색 기능을 위한 메서드 추가
    List<VillagerList> searchVillagers(Integer type, Integer sex, String birthMonth);
}
