package com.baomidou.springboot.response;

import com.baomidou.mybatisplus.extension.api.IErrorCode;

public enum ErrorCode implements IErrorCode {
    FAILE("1000", "失败"),
    SUCCESS("1001","成功");

    private String code;
    private String msg;

    ErrorCode(final String code, final String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
