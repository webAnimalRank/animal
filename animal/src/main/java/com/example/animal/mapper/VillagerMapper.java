package com.example.animal.mapper;

import com.example.animal.dto.VillagerDetail;
import com.example.animal.dto.VillagerList;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VillagerMapper {
    List<VillagerList> selectVillagers();
    VillagerDetail selectVillagerByNo(int villagerNo);
}
