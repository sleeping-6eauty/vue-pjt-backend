package com.example.backend.dto;

public class MaterialDto {

    private Long id;
    private String materialId;
    private String name;
    private Integer currentStock;
    private Integer safetyStock;
    private Integer demandQty;
    private Integer unitPrice;
    private Integer requiredStock;
    private String status;
    private Integer additionalPurchaseCost;

    public MaterialDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCurrentStock() {
        return currentStock;
    }

    public void setCurrentStock(Integer currentStock) {
        this.currentStock = currentStock;
    }

    public Integer getSafetyStock() {
        return safetyStock;
    }

    public void setSafetyStock(Integer safetyStock) {
        this.safetyStock = safetyStock;
    }

    public Integer getDemandQty() {
        return demandQty;
    }

    public void setDemandQty(Integer demandQty) {
        this.demandQty = demandQty;
    }

    public Integer getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Integer unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getRequiredStock() {
        return requiredStock;
    }

    public void setRequiredStock(Integer requiredStock) {
        this.requiredStock = requiredStock;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getAdditionalPurchaseCost() {
        return additionalPurchaseCost;
    }

    public void setAdditionalPurchaseCost(Integer additionalPurchaseCost) {
        this.additionalPurchaseCost = additionalPurchaseCost;
    }
}
