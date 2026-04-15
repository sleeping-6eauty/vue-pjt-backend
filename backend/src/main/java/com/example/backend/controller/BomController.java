package com.example.backend.controller;

import com.example.backend.dto.BomPageResponse;
import com.example.backend.service.BomService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bom")
public class BomController {

    private final BomService bomService;

    public BomController(BomService bomService) {
        this.bomService = bomService;
    }

    @GetMapping("/page")
    public BomPageResponse getBomPage() {
        return bomService.getBomPage();
    }
}