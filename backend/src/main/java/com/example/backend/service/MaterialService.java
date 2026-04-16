package com.example.backend.service;

import com.example.backend.dto.MaterialDto;
import com.example.backend.dto.MaterialHistoryDto;
import com.example.backend.mapper.MaterialMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MaterialService {

    private final MaterialMapper materialMapper;

    public MaterialService(MaterialMapper materialMapper) {
        this.materialMapper = materialMapper;
    }

    public List<MaterialDto> getMaterials() {
        return materialMapper.selectMaterials();
    }

    public List<MaterialHistoryDto> getMaterialHistory() {
        return materialMapper.selectMaterialHistory();
    }

    @Transactional
    public MaterialDto requestMaterialOrder(String materialId) {
        MaterialDto target = getRequiredMaterial(materialId);

        int requiredQty = safe(target.getRequiredStock());
        if (requiredQty <= 0) {
            return materialMapper.selectMaterialByMaterialId(materialId);
        }

        int nextStock = safe(target.getCurrentStock()) + requiredQty;
        materialMapper.insertStorageSnapshot(materialId, nextStock, "INBOUND");

        return materialMapper.selectMaterialByMaterialId(materialId);
    }

    @Transactional
    public MaterialDto adjustMaterialStock(String materialId, Integer nextStock, String operationType) {
        if (nextStock == null || nextStock < 0) {
            throw new IllegalArgumentException("nextStock must be 0 or greater.");
        }

        MaterialDto target = getRequiredMaterial(materialId);
        if (nextStock.equals(target.getCurrentStock())) {
            return materialMapper.selectMaterialByMaterialId(materialId);
        }

        String normalizedOperationType = normalizeOperationType(operationType);

        materialMapper.insertStorageSnapshot(materialId, nextStock, normalizedOperationType);

        return materialMapper.selectMaterialByMaterialId(materialId);
    }

    private MaterialDto getRequiredMaterial(String materialId) {
        if (materialId == null || materialId.isBlank()) {
            throw new IllegalArgumentException("materialId is required.");
        }

        MaterialDto target = materialMapper.selectMaterialByMaterialId(materialId);
        if (target == null) {
            throw new IllegalArgumentException("Material not found: " + materialId);
        }
        return target;
    }

    private int safe(Integer value) {
        return value == null ? 0 : value;
    }

    private String normalizeOperationType(String operationType) {
        if (operationType == null || operationType.isBlank()) {
            throw new IllegalArgumentException("operationType is required.");
        }

        String normalized = operationType.trim().toUpperCase();
        switch (normalized) {
            case "INBOUND":
            case "IN":
                return "INBOUND";
            case "OUTBOUND":
            case "OUT":
            case "USAGE":
                return "OUTBOUND";
            case "ADJUSTMENT":
            case "ADJUST":
                return "ADJUSTMENT";
            default:
                throw new IllegalArgumentException("Unsupported operationType: " + operationType);
        }
    }
}
