package com.devsuperior.dscatalog.dto.request;

import com.devsuperior.dscatalog.services.validation.UserUpdateValid;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@UserUpdateValid
public class UserRequestUpdateDTO extends UserRequestDTO implements Serializable {
    private static final long serialVersionUID = 1L;
}
