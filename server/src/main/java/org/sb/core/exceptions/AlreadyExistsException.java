package org.sb.core.exceptions;

public final class AlreadyExistsException extends ApplicationException {

    public AlreadyExistsException(String message) {
        super(message, 409, null);
    }

    public AlreadyExistsException(String message, Object detail) {
        super(message, 409, detail);
    }
}