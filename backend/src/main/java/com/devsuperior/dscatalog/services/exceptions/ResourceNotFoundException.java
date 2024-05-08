package com.devsuperior.dscatalog.services.exceptions;

import java.io.Serial;

public class ResourceNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 5573572785382419086L;

    public ResourceNotFoundException(String msg) {
        super(msg);
    }
}
