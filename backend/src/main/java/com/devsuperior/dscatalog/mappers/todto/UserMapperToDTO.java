package com.devsuperior.dscatalog.mappers.todto;

import com.devsuperior.dscatalog.dto.response.UserResponseDTO;
import com.devsuperior.dscatalog.entities.User;
import org.springframework.beans.BeanUtils;

public class UserMapperToDTO {
    private UserMapperToDTO() {
    }

    public static UserResponseDTO converter(User entity) {
        UserResponseDTO dto = new UserResponseDTO();
        BeanUtils.copyProperties(entity, dto, "roles", "password");
        entity.getRoles().stream().map(RoleMapperToDTO::converter).forEach(dto::addRole);
        return dto;
    }
}
