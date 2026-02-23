package com.example.animal.mapper;

import com.example.animal.dto.VillagerDetail;
import com.example.animal.dto.VillagerList;
import com.example.animal.dto.VillagerTypeOption;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VillagerMapper {
    List<VillagerList> selectVillagers();
    List<VillagerTypeOption> selectVillagerTypes();
    VillagerDetail selectVillagerByNo(int villagerNo);

    // 엔드포인트 1개 sync에 필요한 업서트 1방 메서드
    int upsertFromNookipedia(
    @Param("category") int category,
    @Param("type") int type,
    @Param("name") String name,
    @Param("nameEn") String nameEn,
    @Param("imageUrl") String imageUrl,
    @Param("iconUrl") String iconUrl,
    @Param("birth") String birth,
    @Param("debut") String debut,
    @Param("sex") Integer sex
);

    // (선택) 타입 매핑까지 VillagerMapper로 합치고 싶으면 이 메서드도 여기로
    Integer findTypeByEnglishName(@Param("typeNameEn") String typeNameEn);

    // 검색 기능을 위한 메서드 추가
    List<VillagerList> searchVillagers(
        @Param("type") Integer type,
        @Param("sex") Integer sex,
        @Param("birthMonth") String birthMonth,
        @Param("keyword") String keyword
    );
}
