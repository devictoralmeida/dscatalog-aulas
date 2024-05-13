package com.devsuperior.dscatalog.dto.response;

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
public class UserResponseDTO {
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private Set<RoleResponseDTO> roles = new HashSet<>();

    public void addRole(RoleResponseDTO role) {
        roles.add(role);
    }
}
