package com.pingxun.daishangqianbao.data;


import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/2.
 */

public class BaseBean<T> implements Serializable {
    private static final long serialVersionUID = 5213230387175987834L;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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

    public boolean success;
    public String code;
    public String message;
    public T data;



}
