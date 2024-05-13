package com.devsuperior.dscatalog.mappers.tomodel;

import com.devsuperior.dscatalog.dto.request.RoleRequestDTO;
import com.devsuperior.dscatalog.entities.Role;
import com.devsuperior.dscatalog.mappers.BaseMapper;

public class RoleMapperToModel extends BaseMapper {
    private RoleMapperToModel() {
    }

    public static Role converter(RoleRequestDTO dto) {
        return mapProperties(dto, new Role());
    }
}
