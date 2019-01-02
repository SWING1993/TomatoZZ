package com.swing.utils;

import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.internal.com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
public class JWT {

    private static final String SECRET = "xzc32hf";

    private static final String EXP = "exp";

    private static final String PAYLOAD = "payload";

    // 过期时间是120分钟即两个小时
    private static final long EXPIRATION = 60L * 1000L * 120L;

    // 加密，传入一个对象
    public static <T> String sign (T obj) {
        try {
            final JWTSigner signer = new JWTSigner(SECRET);
            final Map<String, Object> claims = new HashMap<String, Object>();
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(obj);
            claims.put(PAYLOAD, jsonString);
            claims.put(EXP, System.currentTimeMillis() + EXPIRATION);
            String token = signer.sign(claims);
            return token;
        } catch (Exception e) {
            return null;
        }
    }

    // 解密，传入一个加密后的token字符串和解密后的类型
    public static <T> T unsign(String jwt, Class<T> classT) {
        final JWTVerifier verifier = new JWTVerifier(SECRET);
        try {
            final Map<String, Object> claims = verifier.verify(jwt);
            if (claims.containsKey(EXP) && claims.containsKey(PAYLOAD)) {
                long exp = (Long)claims.get(EXP);
                long currentTimeMillis = System.currentTimeMillis();
                if (exp > currentTimeMillis) {
                    String json = (String )claims.get(PAYLOAD);
                    ObjectMapper objectMapper = new ObjectMapper();
                    return objectMapper.readValue(json, classT);
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}
