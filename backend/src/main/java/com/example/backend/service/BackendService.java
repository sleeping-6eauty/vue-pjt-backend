package com.example.backend.service;

import com.example.backend.dto.OrderDto;
import com.example.backend.dto.OrdersFilterOptionsDto;
import com.example.backend.dto.OrderTableRowDto;
import com.example.backend.dto.OrdersSummaryDto;
import com.example.backend.dto.VehicleRatioDto;
import com.example.backend.mapper.BackendMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BackendService {

    private final BackendMapper backendMapper;

    public BackendService(BackendMapper backendMapper) {
        this.backendMapper = backendMapper;
    }

    public List<OrderDto> getAllOrders() {
        return backendMapper.findAll();
    }

    public OrdersSummaryDto getOrdersSummary() {
        return backendMapper.getOrdersSummary();
    }

    public List<VehicleRatioDto> getVehicleRatio() {
        List<VehicleRatioDto> counts = backendMapper.getVehicleTypeCounts();
        int total = counts.stream().mapToInt(VehicleRatioDto::getOrderCount).sum();
        if (total == 0) {
            return counts;
        }

        List<VehicleRatioDto> result = new ArrayList<>();
        for (VehicleRatioDto dto : counts) {
            double ratio = (double) dto.getOrderCount() / total;
            result.add(new VehicleRatioDto(dto.getVehicleType(), dto.getOrderCount(), ratio));
        }
        return result;
    }

    public List<OrderTableRowDto> getOrderTableRows(int page, int size) {
        return getOrderTableRows(page, size, null, null, null, null, null, null);
    }

    public List<OrderTableRowDto> getOrderTableRows(
            int page,
            int size,
            String vehicleType,
            String engine,
            String drive,
            String tire,
            String seat,
            String optionPartId
    ) {
        int safePage = Math.max(page, 1);
        int safeSize = Math.max(size, 1);
        int offset = (safePage - 1) * safeSize;
        return backendMapper.findOrderTableRows(
                normalize(vehicleType),
                normalize(engine),
                normalize(drive),
                normalize(tire),
                normalize(seat),
                normalize(optionPartId),
                offset,
                safeSize
        );
    }

    public OrdersFilterOptionsDto getFilterOptions() {
        return new OrdersFilterOptionsDto(
                backendMapper.findVehicleTypeOptions(),
                backendMapper.findSequencePartOptions("ENGINE"),
                backendMapper.findSequencePartOptions("DRIVE"),
                backendMapper.findSequencePartOptions("TIRE"),
                backendMapper.findSequencePartOptions("SEAT"),
                backendMapper.findOptionPartOptions()
        );
    }

    public int countOrderTableRows(
            String vehicleType,
            String engine,
            String drive,
            String tire,
            String seat,
            String optionPartId
    ) {
        return backendMapper.countOrderTableRows(
                normalize(vehicleType),
                normalize(engine),
                normalize(drive),
                normalize(tire),
                normalize(seat),
                normalize(optionPartId)
        );
    }

    private String normalize(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}

