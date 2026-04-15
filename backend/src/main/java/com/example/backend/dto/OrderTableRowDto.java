package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderTableRowDto {
    private Integer orderId;
    private String vehicleType;
    private String engine;
    private String drive;
    private String tire;
    private String seat;
    private String option; // comma-separated or null
    private String orderDate;
    private String orderStatus;
}

