package com.baomidou.springboot.domain.dto;


import com.baomidou.springboot.domain.SuperEntity;
import lombok.Data;

/**
* @Description:    申请
* @Author:         LiHaitao
* @CreateDate:     2018/9/18 18:46
* @UpdateUser:
* @UpdateDate:
* @UpdateRemark:
* @Version:        1.0.0
*/
@Data
public class Apply extends SuperEntity<Apply> {

    private String avatar;
    private String name;
    private boolean isRead;
    private int result;
    private int type;
    private long toId;
    private String remark;
    private long group;



}
