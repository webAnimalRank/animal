package com.example.animal.controller;

import com.example.animal.dto.VillagerDetail;
import com.example.animal.dto.VillagerList;
import com.example.animal.dto.VillagerTypeOption;
import com.example.animal.service.VillagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/villagers")
@CrossOrigin(origins = "http://localhost:5173")
public class VillagerController {

    private final VillagerService villagerService;

    @GetMapping
    public List<VillagerList> getVillagers() {
        return villagerService.getVillagers();
    }

    @GetMapping("/types")
    public List<VillagerTypeOption> getVillagerTypes() {
        return villagerService.getVillagerTypes();
    }

    @GetMapping("/{villagerNo}")
    public VillagerDetail getVillager(@PathVariable int villagerNo) {
        return villagerService.getVillager(villagerNo);
    }

    // 검색 기능을 위한 엔드포인트 추가
     @GetMapping("/search")
    public List<VillagerList> searchVillagers(
        @RequestParam(required = false) Integer type,
        @RequestParam(required = false) Integer sex,
        @RequestParam(required = false) String birthMonth,
        @RequestParam(required = false) String keyword
    ) {
        return villagerService.searchVillagers(type, sex, birthMonth, keyword);
    }
}
