package org.sb.core.exceptions;

public final class NotFoundException extends ApplicationException {

    public NotFoundException(String message) {
        super(message, 404, null);
    }

    public NotFoundException(String message, Object detail) {
        super(message, 404, detail);
    }
}