package com.devsuperior.dscatalog.factory;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.mappers.todto.CategoryMapperToDTO;
import com.devsuperior.dscatalog.mappers.todto.ProductMapperToDTO;

import java.time.Instant;

public class Factory {
    public static Product createProduct() {
        Product product = new Product(1L, "Phone", "Good phone", 800.0, "https://img.com/img.png", Instant.parse("2020-10-20T03:00:00Z"));
        product.getCategories().add(Factory.createCategory());
        return product;
    }

    public static Category createCategory() {
        return new Category(2L, "Electronics");
    }

    public static ProductDTO createProductDTO() {
        Product product = createProduct();
        return ProductMapperToDTO.converter(product, true);
    }

    public static CategoryDTO createCategoryDTO() {
        Category category = createCategory();
        return CategoryMapperToDTO.converter(category, true);
    }
}
