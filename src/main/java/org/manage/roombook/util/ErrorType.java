package org.manage.roombook.util;

public enum ErrorType {
    // 成功
    SUCCESS(200, "success"),

    // 参数/请求错误
    PARAM_INVALID(400, "请求参数错误"),
    UNAUTHORIZED(401, "未登录或Token已过期"),
    FORBIDDEN(403, "无权访问"),
    CONFLICT(409, "请求冲突"),

    // 业务错误 10000段
    LOGIN_FAIL(10001, "手机号或密码错误"),
    USER_EXISTS(10002, "用户已存在"),
    DB_INSERT_FAIL(10003, "数据插入失败"),
    RESERVE_CONFLICT(10004, "该时间段已被预约"),
    RESERVE_INSERT_FAIL(10005, "预约失败"),

    // 系统错误
    SYSTEM_ERROR(500, "系统繁忙，请稍后重试");

    private final int code;
    private final String message;

    ErrorType(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() { return code; }
    public String getMessage() { return message; }
}
