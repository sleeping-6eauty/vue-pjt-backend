package com.example.backend.mapper;

import com.example.backend.dto.MaterialDto;
import com.example.backend.dto.MaterialHistoryDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MaterialMapper {

    List<MaterialDto> selectMaterials();

    MaterialDto selectMaterialByMaterialId(String materialId);

    List<MaterialHistoryDto> selectMaterialHistory();

    int insertStorageSnapshot(
            @Param("materialId") String materialId,
            @Param("stockQty") Integer stockQty,
            @Param("operationType") String operationType
    );
}
