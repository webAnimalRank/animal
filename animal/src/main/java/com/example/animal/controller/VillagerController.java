package com.example.animal.controller;

import com.example.animal.dto.VillagerDetail;
import com.example.animal.dto.VillagerList;
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

    @GetMapping("/{villagerNo}")
    public VillagerDetail getVillager(@PathVariable int villagerNo) {
        return villagerService.getVillager(villagerNo);
    }
}
