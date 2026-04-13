package com.example.backend.mapper;

import com.example.backend.dto.BomItemDto;
import com.example.backend.dto.PriceItemDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BomMapper {

    @Select("""
        SELECT
            b.part_id AS id,
            CASE
                WHEN b.sequence = 'ENGINE' THEN '엔진'
                WHEN b.sequence = 'DRIVE' THEN '구동'
                WHEN b.sequence = 'TIRE' THEN '타이어'
                WHEN b.sequence = 'SEAT' THEN '시트'
                WHEN b.sequence = 'OPTION' THEN '옵션'
                ELSE '기타'
            END AS category,
            b.part_id AS partNo,
            b.part_name AS name,
            b.qty AS quantity,
            b.unit AS unit,
            b.sequence AS process,
            b.vehicle_type AS car
        FROM bom b
        ORDER BY b.vehicle_type ASC, b.part_id ASC
    """)
    List<BomItemDto> selectBomList();

    @Select("""
        SELECT
            p.part_id AS id,
            CASE
                WHEN b.sequence = 'ENGINE' THEN '엔진'
                WHEN b.sequence = 'DRIVE' THEN '구동'
                WHEN b.sequence = 'TIRE' THEN '타이어'
                WHEN b.sequence = 'SEAT' THEN '시트'
                WHEN b.sequence = 'OPTION' THEN '옵션'
                ELSE '기타'
            END AS category,
            b.part_name AS name,
            CONCAT(
                b.vehicle_type,
                ' / ',
                CASE
                    WHEN b.sequence = 'ENGINE' THEN '엔진'
                    WHEN b.sequence = 'DRIVE' THEN '구동'
                    WHEN b.sequence = 'TIRE' THEN '타이어'
                    WHEN b.sequence = 'SEAT' THEN '시트'
                    WHEN b.sequence = 'OPTION' THEN '옵션'
                    ELSE b.sequence
                END
            ) AS description,
            p.part_price AS price
        FROM price p
        JOIN bom b ON p.part_id = b.part_id
        ORDER BY b.vehicle_type ASC, p.part_id ASC
    """)
    List<PriceItemDto> selectPriceList();
}