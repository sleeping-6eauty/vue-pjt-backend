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
        materialMapper.insertStorageSnapshot(materialId, nextStock);

        return materialMapper.selectMaterialByMaterialId(materialId);
    }

    @Transactional
    public MaterialDto useMaterialStock(String materialId) {
        MaterialDto target = getRequiredMaterial(materialId);

        int currentStock = safe(target.getCurrentStock());
        int pendingDemandQty = safe(target.getDemandQty());
        if (currentStock <= 0 || pendingDemandQty <= 0) {
            return materialMapper.selectMaterialByMaterialId(materialId);
        }

        int bomQty = safe(materialMapper.selectBomQtyByMaterialId(materialId));
        if (bomQty <= 0) {
            bomQty = 1;
        }

        int pendingOrderCount = pendingDemandQty / bomQty;
        int fulfillableOrderCount = Math.min(pendingOrderCount, currentStock / bomQty);
        if (fulfillableOrderCount <= 0) {
            return materialMapper.selectMaterialByMaterialId(materialId);
        }

        int updatedOrderCount = materialMapper.updatePendingOrdersToCompleted(materialId, fulfillableOrderCount);
        if (updatedOrderCount <= 0) {
            return materialMapper.selectMaterialByMaterialId(materialId);
        }

        int usedQty = updatedOrderCount * bomQty;
        int nextStock = Math.max(0, currentStock - usedQty);
        materialMapper.insertStorageSnapshot(materialId, nextStock);

        return materialMapper.selectMaterialByMaterialId(materialId);
    }

    @Transactional
    public MaterialDto adjustMaterialStock(String materialId, Integer nextStock) {
        if (nextStock == null || nextStock < 0) {
            throw new IllegalArgumentException("nextStock must be 0 or greater.");
        }

        MaterialDto target = getRequiredMaterial(materialId);
        if (nextStock.equals(target.getCurrentStock())) {
            return materialMapper.selectMaterialByMaterialId(materialId);
        }

        materialMapper.insertStorageSnapshot(materialId, nextStock);

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
}
