package com.example.animal.controller;

import com.example.animal.service.VillagerSyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/nookipedia")
public class NookipediaAdminController {

    private final VillagerSyncService villagerSyncService;

    /**
     * ✅ 누키피디아 주민 전체 정보(이미지 포함) → DB 동기화
     * 단 1번 호출로 끝.
     */
    @PostMapping("/sync")
    public ResponseEntity<?> syncAll() {
        // 반환 타입이 int면 count, Map이면 summary 등 네 서비스에 맞춰서 그대로 반환하면 됨
        int count = villagerSyncService.syncAllVillagersFromNookipedia();
        return ResponseEntity.ok(count);
    }
}
