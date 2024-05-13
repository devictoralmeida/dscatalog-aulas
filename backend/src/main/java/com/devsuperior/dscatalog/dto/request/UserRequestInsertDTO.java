package com.devsuperior.dscatalog.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserRequestInsertDTO extends UserRequestDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String password;
}
