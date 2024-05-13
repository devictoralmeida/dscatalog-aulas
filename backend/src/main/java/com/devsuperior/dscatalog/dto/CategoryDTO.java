package com.devsuperior.dscatalog.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "Campo requerido")
    @Size(min = 5, max = 20, message = "O nome da categoria deve ter entre {min} e {max} caracteres")
    private String name;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<ProductDTO> products = new HashSet<>();
}
