package com.baomidou.springboot.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("msg")
public class Msg extends SuperEntity<Msg> {

    /**
     * 消息类型，0：私发，1：群发
     */
    private int type;
    /**
     * 消息内容
     */
    private String msg;
    /**
     * 发送者
     */
    private String from;
    /**
     * 接受者/群组标识
     */
    private String to;
    /**
     * 时间
     */
    private Long time;


}
