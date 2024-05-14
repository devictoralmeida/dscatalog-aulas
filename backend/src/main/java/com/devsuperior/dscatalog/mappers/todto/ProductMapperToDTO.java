package com.devsuperior.dscatalog.mappers.todto;

import com.devsuperior.dscatalog.dto.response.ProductResponseDTO;
import com.devsuperior.dscatalog.entities.Product;
import org.springframework.beans.BeanUtils;

public class ProductMapperToDTO {
    private ProductMapperToDTO() {
    }

    public static ProductResponseDTO converter(Product entity) {
        ProductResponseDTO dto = new ProductResponseDTO();
        BeanUtils.copyProperties(entity, dto, "categories", "createdAt", "updatedAt");
        entity.getCategories().forEach(category -> dto.getCategories().add(CategoryMapperToDTO.converter(category)));
        return dto;
    }
}
