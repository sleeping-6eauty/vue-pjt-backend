package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleRatioDto {
    private String vehicleType;
    private Integer orderCount;
    private Double ratio; // 0~1
}

