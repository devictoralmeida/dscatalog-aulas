package com.devsuperior.dscatalog.dto.request;

import com.devsuperior.dscatalog.services.validation.UserInsertValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@UserInsertValid
public class UserRequestInsertDTO extends UserRequestDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Campo requerido")
    @Size(min = 6, max = 20, message = "A senha do usuário deve ter entre {min} e {max} caracteres")
    private String password;
}
