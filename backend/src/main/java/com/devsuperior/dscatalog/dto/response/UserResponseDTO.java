package com.devsuperior.dscatalog.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserResponseDTO {
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private List<RoleResponseDTO> roles = new ArrayList<>();

    public void addRole(RoleResponseDTO role) {
        roles.add(role);
    }
}
