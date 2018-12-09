package com.baomidou.springboot.im.vo;

import com.baomidou.springboot.im.entity.GroupClient;
import lombok.Data;

/**
 * Description:
 *
 * @Author lht
 * @Date 2018/12/8 下午7:30
 **/
@Data
public class GroupUserVO {

    private Long id;

    private Long user;

    private GroupClient groupClient;
}
