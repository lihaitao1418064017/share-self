package com.baomidou.springboot.im.mapper;


import com.baomidou.springboot.SuperMapper;
import com.baomidou.springboot.im.entity.GroupUser;
import com.baomidou.springboot.im.vo.GroupUserVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @Description:
* @Author:         LiHaitao
* @CreateDate:     2018/9/27 9:45
* @UpdateUser:
* @UpdateDate:
* @UpdateRemark:
* @Version:        1.0.0
*/
public interface GroupUserMapper extends SuperMapper<GroupUser> {


    List<GroupUserVO> selectGroupsByUserId(@Param("userId")Long userId);



}