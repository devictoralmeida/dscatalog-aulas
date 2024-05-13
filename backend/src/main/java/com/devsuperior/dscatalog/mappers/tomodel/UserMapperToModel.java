package com.devsuperior.dscatalog.mappers.tomodel;

import com.devsuperior.dscatalog.dto.request.UserRequestDTO;
import com.devsuperior.dscatalog.dto.request.UserRequestInsertDTO;
import com.devsuperior.dscatalog.entities.Role;
import com.devsuperior.dscatalog.entities.User;
import com.devsuperior.dscatalog.mappers.BaseMapper;

import java.util.Set;

public class UserMapperToModel extends BaseMapper {
    private UserMapperToModel() {
    }

    public static User converter(UserRequestDTO dto, Set<Role> roles) {
        User entity = new User();
        mapProperties(dto, entity, "roles", "password");
        roles.forEach(role -> entity.getRoles().add(role));
        return entity;
    }

    public static void updateFromDto(User entity, UserRequestDTO dto, Set<Role> roles) {
        mapProperties(dto, entity, "roles", "password");
        entity.getRoles().clear();
        roles.forEach(role -> entity.getRoles().add(role));
    }

    public static User converterInsert(UserRequestInsertDTO dto, Set<Role> roles) {
        User entity = new User();
        mapProperties(dto, entity, "roles", "password");
        roles.forEach(role -> entity.getRoles().add(role));
        return entity;
    }


}
