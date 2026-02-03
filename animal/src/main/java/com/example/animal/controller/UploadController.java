package com.example.animal.controller;

import com.example.animal.service.CloudinaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UploadController {

    private final CloudinaryService cloudinaryService;

    public UploadController(CloudinaryService cloudinaryService) {
        this.cloudinaryService = cloudinaryService;
    }

    /**
     * 이미지 업로드 API
     * POST /api/images
     * form-data: file=<이미지>
     */
    @PostMapping("/images")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        // 폴더는 원하는대로 정해도 됨
        Map result = cloudinaryService.uploadImage(file, "animal/images");

        // 프론트에 필요한 값만 내려주기 (중요)
        Map<String, Object> response = new HashMap<>();
        response.put("publicId", result.get("public_id"));
        response.put("url", result.get("secure_url")); // https URL 권장
        response.put("width", result.get("width"));
        response.put("height", result.get("height"));
        response.put("format", result.get("format"));

        return ResponseEntity.ok(response);
    }
}
