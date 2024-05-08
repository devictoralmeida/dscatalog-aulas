package com.devsuperior.dscatalog.mappers.todto;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.dto.ProductMinDTO;
import com.devsuperior.dscatalog.entities.Product;
import org.springframework.beans.BeanUtils;

import java.util.Set;
import java.util.stream.Collectors;

public class ProductMapperToDTO {
    private ProductMapperToDTO() {
    }

    public static ProductDTO converter(Product entity, boolean includeCategories) {
        ProductDTO dto = new ProductDTO();
        BeanUtils.copyProperties(entity, dto, "categories", "createdAt", "updatedAt");

        if (includeCategories) {
            Set<CategoryDTO> categoriesDTO = entity.getCategories().stream()
                    .map(x -> CategoryMapperToDTO.converter(x, false))
                    .collect(Collectors.toSet());
            dto.setCategories(categoriesDTO);
        }

        return dto;
    }

    public static ProductMinDTO convertToMin(Product entity) {
        ProductMinDTO dto = new ProductMinDTO();
        BeanUtils.copyProperties(entity, dto, "categories", "createdAt", "updatedAt");
        return dto;
    }

    public static ProductMinDTO convertToMin(ProductDTO dto) {
        ProductMinDTO minDto = new ProductMinDTO();
        BeanUtils.copyProperties(dto, minDto, "categories", "createdAt", "updatedAt");
        return minDto;
    }
}
