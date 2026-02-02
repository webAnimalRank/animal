package com.example.animal.service;

import com.example.animal.dto.VillagerDetail;
import com.example.animal.dto.VillagerList;

import java.util.List;

public interface VillagerService {
    List<VillagerList> getVillagers();
    VillagerDetail getVillager(int villagerNo);
}
