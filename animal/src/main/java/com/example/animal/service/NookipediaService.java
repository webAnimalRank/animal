package com.example.animal.service;

import com.example.animal.dto.VillagerImageDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class NookipediaService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${nookipedia.api.key}")
    private String apiKey;

    public List<VillagerImageDto> getVillagerImages() {

        String url = "https://api.nookipedia.com/villagers?game=nh&nhdetails=true";

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-API-KEY", apiKey);
        headers.set("Accept-Version", "1.7.0");
 
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response =
                restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        String body = response.getBody();
        if (body == null || body.isBlank()) {
            return List.of();
        }

        try {
            JsonNode root = objectMapper.readTree(body);

            List<VillagerImageDto> list = new ArrayList<>();
           for (JsonNode node : root) {
    String name = node.path("name").asText(null);

    // nh_details 안에 아이콘/이미지 등이 들어갈 수 있음
    JsonNode nh = node.path("nh_details");

    // 아이콘은 보통 nh_details.icon_url
    String iconUrl = nh.path("icon_url").asText(null);

    // 대표 이미지는 nh_details.image_url이 있으면 그걸 우선 사용
    String imageUrl = nh.path("image_url").asText(null);
    if (imageUrl == null || imageUrl.isBlank()) {
        imageUrl = node.path("image_url").asText(null); // fallback
    }

    if (name == null) continue;

    list.add(new VillagerImageDto(name, imageUrl, iconUrl));
}

        return list;

        } catch (Exception e) {
            // 파싱 실패 시 빈 리스트 반환 (원하면 RuntimeException으로 던져도 됨)
            return List.of();
        }
    }
}
