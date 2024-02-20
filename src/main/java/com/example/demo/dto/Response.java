package com.example.demo.dto;

import com.alibaba.fastjson2.JSONObject;
import com.example.demo.exception.BaseErrorInfoInterface;
import com.example.demo.exception.ExceptionEnum;

import java.io.Serializable;

public class Response implements Serializable {
    private String code = "0";
    private String message = "SUCCESS";
    private Object data;

    public Response() {
    }

    public Response(BaseErrorInfoInterface errorInfo) {
        this.code = errorInfo.getResultCode();
        this.message = errorInfo.getResultMsg();
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static Response success() {
        return success(null);
    }

    /**
     * 成功
     * @param data
     * @return
     */
    public static Response success(Object data) {
        Response rb = new Response();
        rb.setCode(ExceptionEnum.SUCCESS.getResultCode());
        rb.setMessage(ExceptionEnum.SUCCESS.getResultMsg());
        rb.setData(data);
        return rb;
    }

    /**
     * 失败
     */
    public static Response error(BaseErrorInfoInterface errorInfo) {
        Response rb = new Response();
        rb.setCode(errorInfo.getResultCode());
        rb.setMessage(errorInfo.getResultMsg());
        rb.setData(null);
        return rb;
    }

    /**
     * 失败
     */
    public static Response error(String code, String message) {
        Response rb = new Response();
        rb.setCode(code);
        rb.setMessage(message);
        rb.setData(null);
        return rb;
    }

    /**
     * 失败
     */
    public static Response error( String message) {
        Response rb = new Response();
        rb.setCode("-1");
        rb.setMessage(message);
        rb.setData(null);
        return rb;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
