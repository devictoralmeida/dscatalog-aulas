package com.devsuperior.dscatalog.mappers.todto;

import com.devsuperior.dscatalog.dto.response.CategoryResponseDTO;
import com.devsuperior.dscatalog.entities.Category;
import org.springframework.beans.BeanUtils;

public class CategoryMapperToDTO {
    private CategoryMapperToDTO() {
    }

    public static CategoryResponseDTO converter(Category entity) {
        CategoryResponseDTO dto = new CategoryResponseDTO();
        BeanUtils.copyProperties(entity, dto, "products", "createdAt", "updatedAt");
        return dto;
    }
}
