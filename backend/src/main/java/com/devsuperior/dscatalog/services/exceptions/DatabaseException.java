package com.devsuperior.dscatalog.services.exceptions;

import java.io.Serial;

public class DatabaseException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 5573572785382419086L;

    public DatabaseException(String msg) {
        super(msg);
    }
}
