package org.manage.roombook.entity;

public class Result<T> {
    private Integer code;
    private String message;
    private T data;

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<T>();
        result.setCode(200);
        result.setMessage("success");
        result.setData(data);
        return result;
    }

    public static <T> Result<T> error(String data) {
        Result<T> result = new Result<T> ();
        result.setCode(500);
        result.setMessage(data);
        return result;
    }
    public static <T> Result<T> error(int code, String data) {
        Result<T> result = new Result<T> ();
        result.setCode(code);
        result.setMessage(data);
        return result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
