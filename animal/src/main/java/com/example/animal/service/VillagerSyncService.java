package com.example.animal.service;

import com.example.animal.dto.NookipediaVillagerDto;
import com.example.animal.mapper.VillagerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class VillagerSyncService {

    private static final String NOOKIPEDIA_VILLAGER_API =
            "https://api.nookipedia.com/villagers?game=nh&nhdetails=true";
    private static final String API_ACCEPT_VERSION = "1.7.0";
    private static final int DEFAULT_VILLAGER_CATEGORY = 1;
    private static final int OTHER_VILLAGER_TYPE = 0;

    private final VillagerMapper villagerMapper;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${nookipedia.api.key}")
    private String apiKey;

    public int syncAllVillagersFromNookipedia() {
        NookipediaVillagerDto[] villagers = fetchVillagers();
        if (villagers == null || villagers.length == 0) return 0;

        int affected = 0;
        for (NookipediaVillagerDto dto : villagers) {
            String nameEn = dto.getName();
            if (nameEn == null || nameEn.isBlank()) continue;

            affected += villagerMapper.upsertFromNookipedia(
                    DEFAULT_VILLAGER_CATEGORY,
                    toVillagerType(dto.getSpecies()),
                    dto.getName(),
                    nameEn,
                    extractImageUrl(dto),
                    extractIconUrl(dto),
                    toMmDd(dto.getBirthdayMonth(), dto.getBirthdayDay()),
                    dto.getDebut(),
                    toSex(dto.getGender())
            );
        }
        return affected;
    }

    private NookipediaVillagerDto[] fetchVillagers() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-API-KEY", apiKey);
        headers.set("Accept-Version", API_ACCEPT_VERSION);

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<NookipediaVillagerDto[]> response =
                restTemplate.exchange(NOOKIPEDIA_VILLAGER_API, HttpMethod.GET, entity, NookipediaVillagerDto[].class);
        return response.getBody();
    }

    private Integer toVillagerType(String species) {
        Integer type = villagerMapper.findTypeByEnglishName(species);
        return type == null ? OTHER_VILLAGER_TYPE : type;
    }

    private Integer toSex(String gender) {
        if ("Male".equalsIgnoreCase(gender)) return 1;
        if ("Female".equalsIgnoreCase(gender)) return 0;
        return null;
    }

    private String extractImageUrl(NookipediaVillagerDto dto) {
        return dto.getNhDetails() != null ? dto.getNhDetails().getImageUrl() : null;
    }

    private String extractIconUrl(NookipediaVillagerDto dto) {
        return dto.getNhDetails() != null ? dto.getNhDetails().getIconUrl() : null;
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
