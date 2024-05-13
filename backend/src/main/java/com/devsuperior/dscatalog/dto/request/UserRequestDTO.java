package com.devsuperior.dscatalog.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserRequestDTO {
    @NotBlank(message = "Campo requerido")
    @Size(min = 3, max = 20, message = "O nome do usuário deve ter entre {min} e {max} caracteres")
    private String firstName;

    @NotBlank(message = "Campo requerido")
    @Size(min = 3, max = 50, message = "O sobrenome do usuário deve ter entre {min} e {max} caracteres")
    private String lastName;

    @NotBlank(message = "Campo requerido")
    @Email(message = "Formato de e-mail inválido")
    private String email;

    private Set<RoleRequestDTO> roles = new HashSet<>();
}
