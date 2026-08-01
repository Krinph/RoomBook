package org.manage.roombook.exception;

public class ParamException extends BusinessException {
    public ParamException(String message) {
        super(400, message);
    }
}
