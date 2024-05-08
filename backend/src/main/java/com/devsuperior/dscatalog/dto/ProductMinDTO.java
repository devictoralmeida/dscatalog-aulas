package com.devsuperior.dscatalog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductMinDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String description;
    private Double price;
    private String imgUrl;
    private Instant date;

}
