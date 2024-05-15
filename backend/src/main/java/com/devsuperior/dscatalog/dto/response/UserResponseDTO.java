package com.devsuperior.dscatalog.dto.response;

import com.devsuperior.dscatalog.projections.IdProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserResponseDTO implements IdProjection<Long>, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<RoleResponseDTO> roles = new ArrayList<>();

    public void addRole(RoleResponseDTO role) {
        roles.add(role);
    }
}
