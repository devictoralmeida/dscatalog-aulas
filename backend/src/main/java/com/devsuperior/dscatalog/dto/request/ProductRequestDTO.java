package com.devsuperior.dscatalog.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Campo requerido")
    @Size(min = 10, max = 60, message = "O nome do produto deve ter entre {min} e {max} caracteres")
    private String name;

    @NotBlank(message = "Campo requerido")
    @Size(min = 20, max = 200, message = "A descrição do produto deve ter entre {min} e {max} caracteres")
    private String description;

    @NotNull(message = "Campo requerido")
    @Positive(message = "O preço deve ser um valor positivo")
    private Double price;

    @NotEmpty(message = "Esse campo não pode ser vazio")
    private String imgUrl;

    private Instant date;

    @NotNull(message = "Campo requerido")
    @NotEmpty(message = "Esse campo não pode ser vazio")
    private List<CategoryIdRequestDTO> categories = new ArrayList<>();
}
