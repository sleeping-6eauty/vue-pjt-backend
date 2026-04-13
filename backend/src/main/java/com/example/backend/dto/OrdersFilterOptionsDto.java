package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdersFilterOptionsDto {
    private List<String> vehicleTypes;
    private List<String> engines;
    private List<String> drives;
    private List<String> tires;
    private List<String> seats;
    private List<String> options;
}

