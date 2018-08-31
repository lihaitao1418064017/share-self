package com.baomidou.springboot.util;

/**
 * @Description StringUitls:
 * @Author LiHaitao
 * @Date 2018/8/28 17:47
 * @UpdateUser
 * @UpdateDescrip
 * @UpdateDate
 * @Version 1.0.0
 **/
public class StringUitls {

    public static Integer stringToInteger(String in) {
        if (in == null) {
            return null;
        } else {
            in = in.trim();
            if (in.length() == 0) {
                return null;
            } else {
                try {
                    return Integer.parseInt(in);
                } catch (NumberFormatException var2) {
                    return null;
                }
            }
        }
    }

    public static boolean equals(String a, String b) {
        if (a == null) {
            return b == null;
        } else {
            return a.equals(b);
        }
    }


}
