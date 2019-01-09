package com.swing.utils;

import java.io.PrintWriter;
import com.swing.utils.Signature;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.swing.entity.User;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.Jedis;

// springmvc拦截器
public class TokenInterceptor implements HandlerInterceptor {

    @Autowired
    private Jedis jedis;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(request.getRequestURI());
        response.setCharacterEncoding("utf-8");

        // 验证签名
        if (!Signature.verificationSign(request)) {
            System.out.println("签名错误");
            responseMessage(response, response.getWriter(),10002, "签名错误");
            return false;
        }

        // 验证token
        String token = request.getHeader("token");
        String uid = request.getHeader("uid");
        try {
            String savedToken = jedis.get(uid);
            if (savedToken.equals(token)) {
                String savedUid = JWT.unsign(token, String.class);
                if (uid.equals(savedUid)) {
                    return true;
                }
                System.out.println("token已失效");
                responseMessage(response, response.getWriter(),10002, "token已失效");
                return false;
            }
            System.out.println("token无效");
            responseMessage(response, response.getWriter(),10002, "token无效");
            return false;
        } catch (Exception e) {
            System.out.println("token无效");
            responseMessage(response, response.getWriter(),10002, "token无效");
            return false;
        }
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView model) throws Exception {

    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception arg3) throws Exception {

    }

    private void responseMessage(HttpServletResponse response, PrintWriter out, int code, String error) {
        response.setContentType("application/json; charset=utf-8");
        RestResult result = RestResultGenerator.genErrorResult("403，认证不通过", code,error);
        out.print(JSONObject.fromObject(result));
        out.flush();
        out.close();
    }



}
