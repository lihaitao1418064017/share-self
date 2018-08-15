package com.baomidou.springboot.pojo;

import java.io.Serializable;

/**
 * 消息bean对象.
 *
 * @author : huanglin
 * @version : 1.0
 * @since :2018/5/10 上午9:36
 */
public class Msg implements Serializable {
    private String id;
    private int action;
    private String msg;
    private String from;
    private String to;
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Msg{" +
                "id='" + id + '\'' +
                ", action=" + action +
                ", msg='" + msg + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
