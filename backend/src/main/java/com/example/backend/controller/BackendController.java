package com.example.backend.controller;

import com.example.backend.dto.OrderDto;
import com.example.backend.dto.OrdersFilterOptionsDto;
import com.example.backend.dto.OrderTableRowDto;
import com.example.backend.dto.OrdersSummaryDto;
import com.example.backend.dto.VehicleRatioDto;
import com.example.backend.service.BackendService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class BackendController {

    private final BackendService backendService;

    public BackendController(BackendService backendService) {
        this.backendService = backendService;
    }

    @GetMapping("/test/orders")
    public List<OrderDto> testOrders() {
        return backendService.getAllOrders();
    }

    @GetMapping("/orders/summary")
    public OrdersSummaryDto getOrdersSummary() {
        return backendService.getOrdersSummary();
    }

    @GetMapping("/orders/vehicle-ratio")
    public List<VehicleRatioDto> getVehicleRatio() {
        return backendService.getVehicleRatio();
    }

    @GetMapping("/orders/table")
    public List<OrderTableRowDto> getOrdersTable(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String vehicleType,
            @RequestParam(required = false) String engine,
            @RequestParam(required = false) String drive,
            @RequestParam(required = false) String tire,
            @RequestParam(required = false) String seat,
            @RequestParam(required = false) String option
    ) {
        return backendService.getOrderTableRows(page, size, vehicleType, engine, drive, tire, seat, option);
    }

    @GetMapping("/orders/filter-options")
    public OrdersFilterOptionsDto getFilterOptions() {
        return backendService.getFilterOptions();
    }

    @GetMapping("/orders/table/count")
    public int getOrdersTableCount(
            @RequestParam(required = false) String vehicleType,
            @RequestParam(required = false) String engine,
            @RequestParam(required = false) String drive,
            @RequestParam(required = false) String tire,
            @RequestParam(required = false) String seat,
            @RequestParam(required = false) String option
    ) {
        return backendService.countOrderTableRows(vehicleType, engine, drive, tire, seat, option);
    }
}

