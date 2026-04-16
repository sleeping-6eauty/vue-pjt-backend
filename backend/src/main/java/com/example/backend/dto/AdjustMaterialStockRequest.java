package com.example.backend.dto;

public class AdjustMaterialStockRequest {

    private Integer nextStock;
    private String operationType;

    public Integer getNextStock() {
        return nextStock;
    }

    public void setNextStock(Integer nextStock) {
        this.nextStock = nextStock;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }
}
