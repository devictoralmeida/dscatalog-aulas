package com.devsuperior.dscatalog.mappers;

import org.springframework.beans.BeanUtils;

public abstract class BaseMapper {
    protected static <S, T> T mapProperties(S source, T target, String... ignoreProperties) {
        BeanUtils.copyProperties(source, target, ignoreProperties);
        return target;
    }
}
