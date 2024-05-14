package com.devsuperior.dscatalog.dto.request;

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
public class RoleIdRequestDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
}
