package com.devsuperior.dscatalog.mappers.tomodel;

import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.mappers.BaseMapper;

import java.util.stream.Collectors;

public class ProductMapperToModel extends BaseMapper {
    private ProductMapperToModel() {
    }

    public static Product converter(ProductDTO dto) {
        Product entity = mapProperties(dto, new Product(), "categories");
        entity.setCategories(dto.getCategories().stream()
                .map(CategoryMapperToModel::converter)
                .collect(Collectors.toSet()));
        return entity;
    }

    public static void updateFromDto(Product entity, ProductDTO dto) {
        mapProperties(dto, entity, "categories", "id");
        entity.getCategories().clear();
        dto.getCategories().forEach(category ->
                entity.addCategory(CategoryMapperToModel.converter(category)));
    }
}
