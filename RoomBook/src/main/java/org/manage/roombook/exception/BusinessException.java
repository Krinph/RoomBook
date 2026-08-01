package org.manage.roombook.exception;

import org.manage.roombook.util.ErrorType;

public class BusinessException extends RuntimeException {
    private final int code;

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(ErrorType errorType) {
        super(errorType.getMessage());
        this.code = errorType.getCode();
    }

    public int getCode() { return code; }
}
