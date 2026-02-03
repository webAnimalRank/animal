package com.example.animal.controller;

import com.example.animal.service.VillagerImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/villagers")
public class VillagerImageController {

    private final VillagerImageService villagerImageService;

    public VillagerImageController(VillagerImageService villagerImageService) {
        this.villagerImageService = villagerImageService;
    }

    @PostMapping("/{villagerNo}/image")
    public ResponseEntity<?> uploadMainImage(@PathVariable int villagerNo,
                                             @RequestParam("file") MultipartFile file) throws IOException {
        String url = villagerImageService.uploadAndSaveMainImage(villagerNo, file);
        return ResponseEntity.ok(Map.of("url", url));
    }

    @PostMapping("/{villagerNo}/icon")
    public ResponseEntity<?> uploadIconImage(@PathVariable int villagerNo,
                                             @RequestParam("file") MultipartFile file) throws IOException {
        String url = villagerImageService.uploadAndSaveIconImage(villagerNo, file);
        return ResponseEntity.ok(Map.of("url", url));
    }
}
