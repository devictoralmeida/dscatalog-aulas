package com.devsuperior.dscatalog.repositories;

import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.projections.ProductProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // 1º vamos buscar os dados paginados dos produtos, pegando id e name (p/ ordenar) e depois vamos buscar as categorias deles.
    // Vamos precisar usar o count query, pois estamos utilizando uma query nativa com paginação (Pageable).
    @Query(nativeQuery = true, value = """
            SELECT * FROM (
            SELECT DISTINCT tb_product.id, tb_product.name
            FROM tb_product
            INNER JOIN tb_product_category ON tb_product_category.product_id = tb_product.id
            WHERE (:categoryIds IS NULL OR tb_product_category.category_id IN :categoryIds)
            AND (LOWER(tb_product.name) LIKE LOWER(CONCAT('%',:name,'%')))
            ) AS tb_result
            """,
            countQuery = """
                    SELECT COUNT(*) FROM (
                    SELECT DISTINCT tb_product.id, tb_product.name
                    FROM tb_product
                    INNER JOIN tb_product_category ON tb_product_category.product_id = tb_product.id
                    WHERE (:categoryIds IS NULL OR tb_product_category.category_id IN :categoryIds)
                    AND (LOWER(tb_product.name) LIKE LOWER(CONCAT('%',:name,'%')))
                    ) AS tb_result
                    """)
    Page<ProductProjection> searchProducts(List<Long> categoryIds, String name, Pageable pageable);

    @Query(value = "SELECT obj FROM Product obj JOIN FETCH obj.categories WHERE obj.id IN :productIds")
    List<Product> searchProductsWithCategories(List<Long> productIds);
}
