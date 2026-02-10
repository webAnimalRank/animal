package com.example.animal.service;

import com.example.animal.dto.NookipediaVillagerDto;
import com.example.animal.mapper.VillagerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class VillagerSyncService {

    private final VillagerMapper villagerMapper;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${nookipedia.api.key}")
    private String apiKey;

    public int syncAllVillagersFromNookipedia() {
        NookipediaVillagerDto[] villagers = fetchVillagers();
        if (villagers == null || villagers.length == 0) return 0;

        int affected = 0;

        for (NookipediaVillagerDto v : villagers) {
            String nameEn = v.getName();       // Ace
            if (nameEn == null || nameEn.isBlank()) continue;

            // 한글명 없음 → 영문명 그대로 사용
            String name = v.getName();

            // 종족 (Bird, Cat ...)
            String species = v.getSpecies();

            // 성별 (Male / Female)
            String gender = v.getGender();

            // villager_type 매핑
            Integer type = villagerMapper.findTypeByEnglishName(species);
            if (type == null) type = 0; // Other

            // 성별 숫자 변환
            Integer sex = null;
            if ("Male".equalsIgnoreCase(gender)) sex = 1;
            else if ("Female".equalsIgnoreCase(gender)) sex = 0;

            // 이미지 / 아이콘
            String imageUrl = v.getNhDetails() != null ? v.getNhDetails().getImageUrl() : null;
            String iconUrl  = v.getNhDetails() != null ? v.getNhDetails().getIconUrl() : null;

            // 생일 MM-DD
            String birth = toMmDd(v.getBirthdayMonth(), v.getBirthdayDay());

            // 🔥 여기서 upsert (NOT NULL 컬럼 포함)
            affected += villagerMapper.upsertFromNookipedia(
                1,          // villager_category (기본값)
                type,       // villager_type
                name,       // villager_name (NOT NULL)
                nameEn,     // villager_name_en (UNIQUE)
                imageUrl,
                iconUrl,
                birth,
                v.getDebut(),
                sex
            );
        }
        return affected;
    }

    private NookipediaVillagerDto[] fetchVillagers() {
        String url = "https://api.nookipedia.com/villagers?game=nh&nhdetails=true";

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-API-KEY", apiKey);
        headers.set("Accept-Version", "1.7.0");

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<NookipediaVillagerDto[]> res =
            restTemplate.exchange(url, HttpMethod.GET, entity, NookipediaVillagerDto[].class);

        return res.getBody();
    }

    private String toMmDd(String monthEn, Integer day) {
        if (monthEn == null || day == null) return null;

        int month = switch (monthEn) {
            case "January" -> 1;
            case "February" -> 2;
            case "March" -> 3;
            case "April" -> 4;
            case "May" -> 5;
            case "June" -> 6;
            case "July" -> 7;
            case "August" -> 8;
            case "September" -> 9;
            case "October" -> 10;
            case "November" -> 11;
            case "December" -> 12;
            default -> 0;
        };

        if (month == 0) return null;
        return String.format("%02d-%02d", month, day);
    }
}
