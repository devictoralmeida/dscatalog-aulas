package com.devsuperior.dscatalog.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FileResponseDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 400692222948944888L;

    private String uri;
}
