package com.baomidou.springboot.im.mapper;

import com.baomidou.springboot.SuperMapper;
import com.baomidou.springboot.im.entity.Friend;
import com.baomidou.springboot.im.vo.FriendVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Description:
 *
 * @Author lht
 * @Date 2018/12/1 下午6:57
 **/
public interface FriendMapper extends SuperMapper<Friend> {


    List<FriendVO> selectFriendsByUserId(@Param("userId")String userId);

}
