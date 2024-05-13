package com.devsuperior.dscatalog.mappers.todto;

import com.devsuperior.dscatalog.dto.response.RoleResponseDTO;
import com.devsuperior.dscatalog.entities.Role;
import org.springframework.beans.BeanUtils;

public class RoleMapperToDTO {
    private RoleMapperToDTO() {
    }

    public static RoleResponseDTO converter(Role entity) {
        RoleResponseDTO dto = new RoleResponseDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }
}
