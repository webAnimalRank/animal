package com.example.animal.controller;

import com.example.animal.service.NookipediaSyncService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/nookipedia")
public class NookipediaSyncController {

    private final NookipediaSyncService syncService;

    public NookipediaSyncController(NookipediaSyncService syncService) {
        this.syncService = syncService;
    }

    /**
     * 한번 실행하면 DB의 villager_image / villager_image_icon 채워짐
     */
    @PostMapping("/villagers/sync-images")
    public ResponseEntity<?> syncImages() {
        return ResponseEntity.ok(syncService.syncVillagerImagesToDb());
    }
}
