package com.devsuperior.dscatalog.mappers.tomodel;

import com.devsuperior.dscatalog.dto.request.CategoryRequestDTO;
import com.devsuperior.dscatalog.entities.Category;
import org.springframework.beans.BeanUtils;

public class CategoryMapperToModel {
    private CategoryMapperToModel() {
    }

    public static Category converter(CategoryRequestDTO dto) {
        Category entity = new Category();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

    public static void updateFromDto(Category entity, CategoryRequestDTO dto) {
        BeanUtils.copyProperties(dto, entity, "id");
    }
}
