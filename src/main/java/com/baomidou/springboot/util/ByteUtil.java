package com.baomidou.springboot.util;

import org.tio.utils.json.Json;

import java.io.IOException;

/**
* @Description:
* @Author:         LiHaitao
* @CreateDate:     2018/9/18 18:53
* @UpdateUser:
* @UpdateDate:
* @UpdateRemark:
* @Version:        1.0.0
*/
public class ByteUtil {
    /**
     * 转换成byte[]
     * */
    public static byte[] toBytes(Object obj) throws IOException{
        if(obj == null) {
            return null;
        }
        if(obj instanceof String){
            return ((String) obj).getBytes("utf-8");
        }
        return Json.toJson(obj).getBytes("utf-8");
    }

    /**
     * byte[] 转换成string
     * */
    public static String toText(byte[] bytes) throws IOException{
        if(bytes == null|| bytes.length == 0){
            return null;
        }
        return new String(bytes,"utf-8");
    }
}
