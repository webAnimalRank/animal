package com.example.animal.service;

import com.example.animal.dto.VillagerImageDto;
import com.example.animal.mapper.VillagerMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NookipediaSyncService {

    private final NookipediaService nookipediaService;
    private final VillagerMapper villagerMapper;

    public NookipediaSyncService(NookipediaService nookipediaService,
                                 VillagerMapper villagerMapper) {
        this.nookipediaService = nookipediaService;
        this.villagerMapper = villagerMapper;
    }

    /**
     * Nookipedia의 name(영문) -> DB villager_name_en 매칭해서
     * villager_image / villager_image_icon을 업데이트
     */
    public SyncResult syncVillagerImagesToDb() {
        List<VillagerImageDto> list = nookipediaService.getVillagerImages();

        int total = list.size();
        int updated = 0;
        int skipped = 0;

        for (VillagerImageDto v : list) {
            String name = v.getName();
            String imageUrl = v.getImageUrl();
            String iconUrl = v.getIconUrl();

            if (name == null || name.isBlank()) {
                skipped++;
                continue;
            }

            // image/icon 둘 다 없으면 의미 없으니 스킵
            if ((imageUrl == null || imageUrl.isBlank()) && (iconUrl == null || iconUrl.isBlank())) {
                skipped++;
                continue;
            }

            int r = villagerMapper.updateImagesByEnglishName(name, imageUrl, iconUrl);
            updated += r; // 매칭되면 1, 아니면 0
        }

        int notMatched = total - updated - skipped;
        return new SyncResult(total, updated, skipped, notMatched);
    }

    // 결과 DTO(간단 응답용)
    public static class SyncResult {
        public int total;
        public int updated;
        public int skipped;
        public int notMatched;

        public SyncResult(int total, int updated, int skipped, int notMatched) {
            this.total = total;
            this.updated = updated;
            this.skipped = skipped;
            this.notMatched = notMatched;
        }
    }
}
