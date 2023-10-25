package com.okay.testcenter.domain;

import java.io.Serializable;


public class RetResult<T> implements Serializable {

    private static final long serialVersionUID = 6840297812400989387L;

    private T data;
    public int code;
    private String msg;

    public RetResult<T> setCode(RetCodeEnum retCode) {
        this.code = retCode.code;
        return this;
    }

    public int getCode() {
        return code;
    }

    public RetResult<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public RetResult<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return data;
    }

    public RetResult<T> setData(T data) {
        this.data = data;
        return this;
    }
}
