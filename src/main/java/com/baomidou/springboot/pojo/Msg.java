package com.baomidou.springboot.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Msg implements Serializable {
    private String id;
    private int action;
    private String msg;
    private String from;
    private String to;
    private String status;

}
