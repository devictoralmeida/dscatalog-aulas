package com.devsuperior.dscatalog.dto.request;

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
    private String firstName;
    private String lastName;
    private String email;
    private Set<RoleRequestDTO> roles = new HashSet<>();
}
