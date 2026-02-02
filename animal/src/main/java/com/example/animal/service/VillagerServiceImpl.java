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
}
