package com.example.animal.mapper;

import com.example.animal.dto.VillagerDetail;
import com.example.animal.dto.VillagerList;
import com.example.animal.dto.VillagerTypeOption;
import com.example.animal.dto.VoteTopItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VillagerMapper {
    List<VillagerList> selectVillagers();

    List<VillagerTypeOption> selectVillagerTypes();

    VillagerDetail selectVillagerByNo(int villagerNo);

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

    Integer findTypeByEnglishName(@Param("typeNameEn") String typeNameEn);

    List<VillagerList> searchVillagers(
            @Param("type") Integer type,
            @Param("sex") Integer sex,
            @Param("birthMonth") String birthMonth,
            @Param("debut") String debut,
            @Param("keyword") String keyword
    );

    int countMonthlyVotesByMember(
            @Param("memberNo") int memberNo,
            @Param("voteMonth") String voteMonth
    );

    int countExistingVillagers(@Param("villagerNos") List<Integer> villagerNos);

    int insertVoteHistories(
            @Param("memberNo") int memberNo,
            @Param("voteMonth") String voteMonth,
            @Param("villagerNos") List<Integer> villagerNos
    );

    int increaseVillagerVotes(@Param("villagerNos") List<Integer> villagerNos);

    List<VoteTopItem> selectMonthlyTop3(@Param("voteMonth") String voteMonth);

    List<VillagerList> selectMyVotedVillagers(
            @Param("memberNo") int memberNo,
            @Param("voteMonth") String voteMonth
    );
}
