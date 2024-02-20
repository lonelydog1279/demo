package com.example.demo.exception;

public enum ExceptionEnum implements BaseErrorInfoInterface{

    SUCCESS("0", "Success"),
    INTERNAL_SERVER_ERROR("500", "Internal Error!");

    private final String resultCode;

    private final String resultMsg;

    ExceptionEnum(String resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

    @Override
    public String getResultCode() {
        return resultCode;
    }

    @Override
    public String getResultMsg() {
        return resultMsg;
    }
}

