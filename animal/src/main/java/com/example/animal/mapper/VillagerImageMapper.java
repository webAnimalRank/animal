package com.example.animal.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface VillagerImageMapper {

    int updateVillagerImage(
            @Param("villagerNo") int villagerNo,
            @Param("url") String url
    );

    int updateVillagerIcon(
            @Param("villagerNo") int villagerNo,
            @Param("url") String url
    );
}
