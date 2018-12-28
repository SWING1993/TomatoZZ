package com.swing.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RestResult<T> {

    private boolean success;
    private String message;
    private String error;
    private String time;
    private T result;
    private int code;

    public static <T> RestResult newInstance() {
        return new RestResult<>();
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getTime() {
        SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss" );
        return sdf.format(new Date());
    }

//    public void setTime(String time) {
//        this.time = time;
//    }

    @Override
    public String toString() {
        return "RestResult{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", error='" + error + '\'' +
                ", time=" + time +
                ", result=" + result +
                ", code=" + code +
                '}';
    }
}
