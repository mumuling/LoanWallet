package com.pingxun.daishangqianbao.data;


import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/2.
 */

public class BaseBean<T> implements Serializable {
    private static final long serialVersionUID = 5213230387175987834L;

    public boolean success;
    public String code;
    public String message;
    public T data;



}
