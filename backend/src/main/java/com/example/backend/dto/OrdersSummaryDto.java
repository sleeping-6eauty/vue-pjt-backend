package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdersSummaryDto {
    private Integer totalOrders;
    private Integer pendingOrders;
    private Integer completedOrders;
}

