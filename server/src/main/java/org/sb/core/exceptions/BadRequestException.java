package org.sb.core.exceptions;

public final class BadRequestException extends ApplicationException {

    public BadRequestException(String message) {
        super(message, 400, null);
    }

    public BadRequestException(String message, Object detail) {
        super(message, 400, detail);
    }
}