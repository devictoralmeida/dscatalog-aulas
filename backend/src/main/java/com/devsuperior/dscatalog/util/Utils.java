package com.devsuperior.dscatalog.util;

import com.devsuperior.dscatalog.projections.IdProjection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {
    private Utils() {
    }

    // Método que retorna uma lista de qualquer classe que implemente a interface IdProjection, e também irá receber 2 parâmetros que implementa a mesma interface
    // <ID> é o nome genérico do id, que pode ser Long, UUID, etc...
    public static <ID> List<? extends IdProjection<ID>> replace(List<? extends IdProjection<ID>> ordered,
                                                                List<? extends IdProjection<ID>> unordered) {
        // Vamos criar um map a partir da lista unordered, onde a chave será o id e o valor será o objeto.
        // Isso é feito para podermos acessar os objetos de forma mais eficiente e rápida.
        Map<ID, IdProjection<ID>> map = new HashMap<>();
        for (IdProjection<ID> obj : unordered) {
            map.put(obj.getId(), obj);
        }

        List<IdProjection<ID>> result = new ArrayList<>();
        for (IdProjection<ID> obj : ordered) {
            result.add(map.get(obj.getId()));
        }
        return result;
    }
}
