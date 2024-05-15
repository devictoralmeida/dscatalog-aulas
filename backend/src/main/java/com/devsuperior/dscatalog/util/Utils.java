package com.devsuperior.dscatalog.util;

import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.projections.ProductProjection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {
    private Utils() {
    }

    public static List<Product> replace(List<ProductProjection> ordered, List<Product> unordered) {
        // Vamos criar um map a partir da lista unordered, onde a chave será o id e o valor será o objeto.
        // Isso é feito para podermos acessar os objetos de forma mais eficiente e rápida.
        Map<Long, Product> map = new HashMap<>();
        for (Product obj : unordered) {
            map.put(obj.getId(), obj);
        }

        List<Product> result = new ArrayList<>();
        for (ProductProjection obj : ordered) {
            Product actualProduct = map.get(obj.getId());
            result.add(actualProduct);
        }
        return result;
    }
}
