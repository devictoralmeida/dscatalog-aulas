package com.devsuperior.dscatalog.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<ProductDTO> products = new HashSet<>();
}
