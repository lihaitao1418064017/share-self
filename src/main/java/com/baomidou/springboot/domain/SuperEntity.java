package com.baomidou.springboot.domain;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;

/**
* @Description:   通用实体父类
* @Author:
* @UpdateUser:
* @UpdateRemark:
* @Version:        1.0.0
*/
public class SuperEntity<T extends Model> extends Model<T> {

    /**
     * 主键ID
     */
    @TableId("id")
    private Long id;
    private Long tenantId;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public SuperEntity setTenantId(Long tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
