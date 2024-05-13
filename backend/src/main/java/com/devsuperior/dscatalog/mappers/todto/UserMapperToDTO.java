package com.devsuperior.dscatalog.mappers.todto;

import com.devsuperior.dscatalog.dto.response.UserResponseDTO;
import com.devsuperior.dscatalog.dto.response.UserResponseInsertDTO;
import com.devsuperior.dscatalog.entities.User;
import org.springframework.beans.BeanUtils;

public class UserMapperToDTO {
    private UserMapperToDTO() {
    }

    public static UserResponseDTO converter(User entity) {
        UserResponseDTO dto = new UserResponseDTO();
        BeanUtils.copyProperties(entity, dto, "roles", "password");
//        Set<RoleResponseDTO> roles = entity.getRoles().stream().map(RoleMapperToDTO::converter).collect(Collectors.toSet());
//        dto.setRoles(roles);
        entity.getRoles().stream().map(RoleMapperToDTO::converter).forEach(dto::addRole);
        return dto;
    }

    public static UserResponseInsertDTO converterInsert(User entity) {
        UserResponseInsertDTO dto = new UserResponseInsertDTO();
        BeanUtils.copyProperties(entity, dto, "roles", "password");
//        Set<RoleResponseDTO> roles = entity.getRoles().stream().map(RoleMapperToDTO::converter).collect(Collectors.toSet());
//        dto.setRoles(roles);
        entity.getRoles().stream().map(RoleMapperToDTO::converter).forEach(dto::addRole);
        return dto;
    }
}
