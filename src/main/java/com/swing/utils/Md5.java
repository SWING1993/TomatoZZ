package com.swing.utils;

import org.springframework.util.DigestUtils;

/*Md5加密工具类*/
public class Md5 {

    // 盐值，用于混淆
    private final static String salt = "tomato00zz321mmf";

    public static String getMd5(String value) {
        String base = value + "/" + salt;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }

}
