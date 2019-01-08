package com.swing.utils;

import org.springframework.util.DigestUtils;

/*Md5加密工具类*/
public class Md5 {

    public static String getMd5(String value ,String salt) {
        String base = value + "/" + salt;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }

}
