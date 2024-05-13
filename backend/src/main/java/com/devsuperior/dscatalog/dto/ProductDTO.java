package com.devsuperior.dscatalog.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotBlank(message = "Campo requerido")
    @Size(min = 10, max = 60, message = "O nome do produto deve ter entre {min} e {max} caracteres")
    private String name;

    @NotBlank(message = "Campo requerido")
    @Size(min = 20, max = 200, message = "A descrição do produto deve ter entre {min} e {max} caracteres")
    private String description;

    @Positive(message = "O preço deve ser um valor positivo")
    private Double price;

    @NotEmpty(message = "Esse campo não pode ser vazio")
    @URL(message = "Esse campo deve ser uma URL")
    private String imgUrl;

    @PastOrPresent(message = "A data do produto não pode ser futura")
    private Instant date;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<CategoryDTO> categories = new HashSet<>();

}
