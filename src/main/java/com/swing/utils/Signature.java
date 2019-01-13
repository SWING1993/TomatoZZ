package com.swing.utils;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class Signature {

    // 签名参数的key
    private static final String SIGN_KEY = "signature_secret";
    // ACCESS_KEY
    private static final String ACCESS_KEY = "accessKey";
    // ACCESS_SECRET
    private static final String ACCESS_SECRET = "s7*&6f";
    // md5盐值，用于混淆
    private final static String signSalt = "1s&^%gsa";

    // 验证签名
    public static boolean verificationSign(HttpServletRequest request) throws Exception {
        Enumeration<?> pNames = request.getParameterNames();
        Map<String, Object> params = new HashMap<String, Object>();
        while (pNames.hasMoreElements()) {
            String pName = (String) pNames.nextElement();
            Object pValue = request.getParameter(pName);
            params.put(pName, pValue);
        }
        String originSign = request.getHeader(SIGN_KEY);
        String sign = createSign(params);
        return sign.equals(originSign);
    }

    // 签名
    private static String createSign(Map<String, Object> params) throws Exception {
        System.out.println("签名的参数" + params);
        Set<String> keysSet = params.keySet();
        Object[] keys = keysSet.toArray();
        Arrays.sort(keys);
        StringBuilder temp = new StringBuilder();
        boolean first = true;
        for (Object key : keys) {
            if (first) {
                first = false;
            } else {
                temp.append("&");
            }
            temp.append(key).append("=");
            Object value = params.get(key);
            String valueString = "";
            if (null != value) {
                valueString = String.valueOf(value);
            }
            temp.append(valueString);
        }
        temp.append("&").append(ACCESS_KEY).append("=").append(ACCESS_SECRET);
        System.out.println("temp：" + temp);

        String sign = Md5.getMd5(temp.toString(), signSalt);
        System.out.println("签名：" + sign);
        return sign;
    }
}
