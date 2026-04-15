package com.example.backend.mapper;

import com.example.backend.dto.OrderDto;
import com.example.backend.dto.OrderTableRowDto;
import com.example.backend.dto.OrdersSummaryDto;
import com.example.backend.dto.VehicleRatioDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BackendMapper {
    List<OrderDto> findAll();

    OrdersSummaryDto getOrdersSummary();

    List<VehicleRatioDto> getVehicleTypeCounts();

    List<OrderTableRowDto> findOrderTableRows(
            @Param("vehicleType") String vehicleType,
            @Param("engine") String engine,
            @Param("drive") String drive,
            @Param("tire") String tire,
            @Param("seat") String seat,
            @Param("optionPartId") String optionPartId,
            @Param("offset") int offset,
            @Param("limit") int limit
    );

    int countOrderTableRows(
            @Param("vehicleType") String vehicleType,
            @Param("engine") String engine,
            @Param("drive") String drive,
            @Param("tire") String tire,
            @Param("seat") String seat,
            @Param("optionPartId") String optionPartId
    );

    List<String> findVehicleTypeOptions();

    List<String> findSequencePartOptions(@Param("sequence") String sequence);

    List<String> findOptionPartOptions();
}

