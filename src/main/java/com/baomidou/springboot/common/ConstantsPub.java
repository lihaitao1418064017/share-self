package com.baomidou.springboot.common;

/**
* @Description:    ConstantsPub:公共常量
* @Author:         LiHaitao
* @CreateDate:     2018/8/19 21:01
* @UpdateUser:
* @UpdateDate:     2018/8/19 21:01
* @UpdateRemark:
* @Version:        1.0.0
*/

public class ConstantsPub {
    /**
     * 加密算法名
     */
    public static final String hashAlgorithmName = "MD5";

    /**
     * hash次数（两次-再hash）
     */
    public static final int hashIterations = 1;

    /**
     * hex解密支持 （false默认就是base64)
     */
    public static final boolean hexEncodedEnabled = true;

    /**
     * salt 撒盐加密
     */
    public static final String salt = "shareSelf";


    /**
     * 用于群聊的group id
     */
    public static final String GROUP_ID = "showcase-websocket";

}
