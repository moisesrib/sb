package org.sb.core.exceptions;

public final class UnauthorizedException extends ApplicationException {

    public UnauthorizedException(String message) {
        super(message, 401, null);
    }

    public UnauthorizedException(String message, Object detail) {
        super(message, 401, detail);
    }
}