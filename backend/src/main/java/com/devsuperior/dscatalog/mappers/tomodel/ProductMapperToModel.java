package com.devsuperior.dscatalog.mappers.tomodel;

import com.devsuperior.dscatalog.dto.request.ProductRequestDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.mappers.BaseMapper;

import java.util.Set;

public class ProductMapperToModel extends BaseMapper {
    private ProductMapperToModel() {
    }

    public static Product converter(ProductRequestDTO dto, Set<Category> categories) {
        Product entity = mapProperties(dto, new Product(), "categories");
        entity.getCategories().addAll(categories);
        return entity;
    }

    public static void updateFromDto(Product entity, ProductRequestDTO dto, Set<Category> categories) {
        mapProperties(dto, entity, "categories", "id");
        entity.getCategories().clear();
        entity.getCategories().addAll(categories);
    }
}
