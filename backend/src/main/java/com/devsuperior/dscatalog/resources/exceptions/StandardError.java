package com.devsuperior.dscatalog.resources.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StandardError implements Serializable {
    @Serial
    private static final long serialVersionUID = 5573572785382419087L;

    private Instant timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
}
