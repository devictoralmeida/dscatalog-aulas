package com.devsuperior.dscatalog.dto.response;

import com.devsuperior.dscatalog.projections.IdProjection;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserResponseInsertDTO extends UserResponseDTO implements Serializable, IdProjection<Long> {
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private String password;
}
