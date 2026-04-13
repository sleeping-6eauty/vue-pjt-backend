package com.example.backend.dto;

import java.util.List;

public class BomPageResponse {
    private List<BomItemDto> bomList;
    private List<PriceItemDto> priceList;

    public BomPageResponse() {
    }

    public BomPageResponse(List<BomItemDto> bomList, List<PriceItemDto> priceList) {
        this.bomList = bomList;
        this.priceList = priceList;
    }

    public List<BomItemDto> getBomList() {
        return bomList;
    }

    public void setBomList(List<BomItemDto> bomList) {
        this.bomList = bomList;
    }

    public List<PriceItemDto> getPriceList() {
        return priceList;
    }

    public void setPriceList(List<PriceItemDto> priceList) {
        this.priceList = priceList;
    }
}