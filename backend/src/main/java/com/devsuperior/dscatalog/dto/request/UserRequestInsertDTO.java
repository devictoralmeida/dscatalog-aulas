package com.devsuperior.dscatalog.dto.request;

import com.devsuperior.dscatalog.services.validation.UserInsertValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@UserInsertValid
public class UserRequestInsertDTO extends UserRequestDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Campo requerido")
    @Size(min = 8, message = "A senha do usuário deve ter no mínimo {min} caracteres")
    private String password;
}
