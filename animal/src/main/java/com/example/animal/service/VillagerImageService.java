package com.example.animal.service;

import com.example.animal.mapper.VillagerImageMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class VillagerImageService {

    private final CloudinaryService cloudinaryService;
    private final VillagerImageMapper villagerImageMapper;

    public VillagerImageService(CloudinaryService cloudinaryService,
                                VillagerImageMapper villagerImageMapper) {
        this.cloudinaryService = cloudinaryService;
        this.villagerImageMapper = villagerImageMapper;
    }

    public String uploadAndSaveMainImage(int villagerNo, MultipartFile file) throws IOException {
        Map result = cloudinaryService.uploadImage(file, "animal/villager");
        String url = (String) result.get("secure_url");

        int updated = villagerImageMapper.updateVillagerImage(villagerNo, url);
        if (updated != 1) {
            throw new IllegalStateException("DB 업데이트 실패: villager_no=" + villagerNo);
        }
        return url;
    }

    public String uploadAndSaveIconImage(int villagerNo, MultipartFile file) throws IOException {
        Map result = cloudinaryService.uploadImage(file, "animal/villager_icon");
        String url = (String) result.get("secure_url");

        int updated = villagerImageMapper.updateVillagerIcon(villagerNo, url);
        if (updated != 1) {
            throw new IllegalStateException("DB 업데이트 실패: villager_no=" + villagerNo);
        }
        return url;
    }
}
