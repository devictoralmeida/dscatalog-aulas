package com.devsuperior.dscatalog.mappers.todto;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.dto.CategoryMinDTO;
import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.entities.Category;
import org.springframework.beans.BeanUtils;

import java.util.Set;
import java.util.stream.Collectors;

public class CategoryMapperToDTO {
    private CategoryMapperToDTO() {
    }

    public static CategoryDTO converter(Category entity, boolean includeProducts) {
        CategoryDTO dto = new CategoryDTO();
        BeanUtils.copyProperties(entity, dto, "products", "createdAt", "updatedAt");

        if (includeProducts) {
            Set<ProductDTO> productsDTO = entity.getProducts().stream()
                    .map(x -> ProductMapperToDTO.converter(x, false))
                    .collect(Collectors.toSet());
            dto.setProducts(productsDTO);
        }

        return dto;
    }

    public static CategoryMinDTO converterToMin(Category entity) {
        CategoryMinDTO dto = new CategoryMinDTO();
        BeanUtils.copyProperties(entity, dto, "products", "createdAt", "updatedAt");
        return dto;
    }

    public static CategoryMinDTO converterToMin(CategoryDTO dto) {
        CategoryMinDTO minDto = new CategoryMinDTO();
        BeanUtils.copyProperties(dto, minDto, "products");
        return minDto;
    }
}
