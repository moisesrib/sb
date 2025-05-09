package org.sb.core.exceptions;

public final class ForbiddenException extends ApplicationException {

    public ForbiddenException(String message) {
        super(message, 403, null);
    }

    public ForbiddenException(String message, Object detail) {
        super(message, 403, detail);
    }
}