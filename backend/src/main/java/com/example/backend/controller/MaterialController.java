package com.example.backend.controller;

import com.example.backend.dto.AdjustMaterialStockRequest;
import com.example.backend.dto.MaterialDto;
import com.example.backend.dto.MaterialHistoryDto;
import com.example.backend.service.MaterialService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/materials")
public class MaterialController {

    private final MaterialService materialService;

    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    @GetMapping
    public List<MaterialDto> getMaterials() {
        return materialService.getMaterials();
    }

    @GetMapping("/history")
    public List<MaterialHistoryDto> getMaterialHistory() {
        return materialService.getMaterialHistory();
    }

    @PostMapping("/{materialId}/order")
    public ResponseEntity<?> requestMaterialOrder(@PathVariable String materialId) {
        try {
            return ResponseEntity.ok(materialService.requestMaterialOrder(materialId));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of("message", ex.getMessage()));
        }
    }

    @PutMapping("/{materialId}/stock")
    public ResponseEntity<?> adjustMaterialStock(
            @PathVariable String materialId,
            @RequestBody AdjustMaterialStockRequest request
    ) {
        try {
            Integer nextStock = request == null ? null : request.getNextStock();
            return ResponseEntity.ok(materialService.adjustMaterialStock(materialId, nextStock));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of("message", ex.getMessage()));
        }
    }
}
