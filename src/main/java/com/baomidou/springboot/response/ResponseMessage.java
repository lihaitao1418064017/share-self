package com.baomidou.springboot.response;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.feilong.core.lang.StringUtil;

import java.io.Serializable;
import java.util.LinkedHashSet;

public class ResponseMessage<T> implements Serializable {
    private static final long serialVersionUID = 8992436576262574064L;
    protected String message;//返回信息
    protected T result;//响应结果
    private static final String UNKNOW="-1";
    private static final String SUCCESS="0";
    protected int status;//状态
    protected String code;//状态码
    protected Long timestamp;//时间戳



    public ResponseMessage() {
    }

    public static <T> ResponseMessage<T> error(String message) {
        return error(500, UNKNOW, (String)message);
    }

    public static <T> ResponseMessage<T> error(String message, Object... args) {
        return error(500, message, (Object[])args);
    }

    public static <T> ResponseMessage<T> error(int status, String message) {
        return error(status, UNKNOW, message);
    }

    public static <T> ResponseMessage<T> error(int status, String message, Object... args) {
        return error(status, UNKNOW, message, args);
    }
    public static <T> ResponseMessage<T> error(int status, String code, String message, Object... args) {
        ResponseMessage<T> msg = new ResponseMessage();
        msg.message = StringUtil.format(message, args);
        msg.status(status);
        msg.code(code);
        return msg.putTimeStamp();
    }
    public static <T> ResponseMessage<T> error(int status, String code, String message) {
        return error(status, code, message, (Object[])null);
    }

    public static <T> ResponseMessage<T> ok() {
        return ok(null);
    }

    public static <T> ResponseMessage<T> ok(T result) {

        return (new ResponseMessage()).result(result).putTimeStamp().code(SUCCESS).status(200);
    }

    private ResponseMessage<T> putTimeStamp() {
        this.timestamp = System.currentTimeMillis();
        return this;
    }

    public ResponseMessage<T> result(T result) {
        this.result = result;
        return this;
    }

    public String toString() {
        return JSON.toJSONStringWithDateFormat(this, "yyyy-MM-dd HH:mm:ss", new SerializerFeature[0]);
    }

    public ResponseMessage<T> status(int status) {
        this.status = status;
        return this;
    }

    public ResponseMessage<T> code(String code) {
        this.code = code;
        return this;
    }





    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return this.result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public Long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }


}

