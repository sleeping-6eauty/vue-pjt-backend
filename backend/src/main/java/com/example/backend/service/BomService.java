package com.example.backend.service;

import com.example.backend.dto.BomPageResponse;
import com.example.backend.mapper.BomMapper;
import org.springframework.stereotype.Service;

@Service
public class BomService {

    private final BomMapper bomMapper;

    public BomService(BomMapper bomMapper) {
        this.bomMapper = bomMapper;
    }

    public BomPageResponse getBomPage() {
        return new BomPageResponse(
                bomMapper.selectBomList(),
                bomMapper.selectPriceList()
        );
    }
}