package com.devsuperior.dscatalog.dto.response;

import com.devsuperior.dscatalog.projections.IdProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponseDTO implements Serializable, IdProjection<Long> {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
}
