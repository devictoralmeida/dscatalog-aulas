package com.devsuperior.dscatalog.mappers.tomodel;

import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.entities.Product;
import org.springframework.beans.BeanUtils;

import java.util.Set;
import java.util.stream.Collectors;

public class ProductMapperToModel {
    private ProductMapperToModel() {
    }

    public static Product converter(ProductDTO dto) {
        Product entity = new Product();
        BeanUtils.copyProperties(dto, entity, "categories");

        Set<Category> categories = dto.getCategories().stream().map(CategoryMapperToModel::converter).collect(Collectors.toSet());
        entity.setCategories(categories);

        return entity;
    }

    public static void updateFromDto(Product entity, ProductDTO dto) {
        BeanUtils.copyProperties(dto, entity, "categories", "id");
        entity.getCategories().clear();
        dto.getCategories().forEach(category -> entity.addCategory(CategoryMapperToModel.converter(category)));
    }
}
