package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Integer rowId;
    private Integer orderId;
    private String vehicleType;
    private String sequence;
    private String partId;
    private String orderDate; //    private LocalDate orderDate;
    private String orderStatus;
}

