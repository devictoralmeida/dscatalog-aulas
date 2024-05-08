package com.devsuperior.dscatalog.mappers.tomodel;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.entities.Product;
import org.springframework.beans.BeanUtils;

import java.util.Set;
import java.util.stream.Collectors;

public class CategoryMapperToModel {
    private CategoryMapperToModel() {
    }

    public static Category converter(CategoryDTO dto) {
        Category entity = new Category();
        BeanUtils.copyProperties(dto, entity, "products");

        Set<Product> products = dto.getProducts().stream().map(ProductMapperToModel::converter).collect(Collectors.toSet());
        entity.setProducts(products);

        return entity;
    }

    public static void updateFromDto(Category entity, CategoryDTO dto) {
        BeanUtils.copyProperties(dto, entity, "products", "id");
        entity.getProducts().clear();

        Set<Product> products = dto.getProducts().stream().map(ProductMapperToModel::converter).collect(Collectors.toSet());
        entity.setProducts(products);
    }
}
