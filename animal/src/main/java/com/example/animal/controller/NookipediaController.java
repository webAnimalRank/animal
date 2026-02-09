package com.example.animal.controller;

import com.example.animal.dto.VillagerImageDto;
import com.example.animal.service.NookipediaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nookipedia")
public class NookipediaController {

    private final NookipediaService nookipediaService;

    public NookipediaController(NookipediaService nookipediaService) {
        this.nookipediaService = nookipediaService;
    }

    @GetMapping("/villagers/images")
    public List<VillagerImageDto> getVillagerImages() {
        return nookipediaService.getVillagerImages();
    }
}
