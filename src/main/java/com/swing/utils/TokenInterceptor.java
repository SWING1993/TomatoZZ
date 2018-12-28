package com.swing.utils;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.swing.entity.User;
import net.sf.json.JSONObject;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

// springmvc拦截器
public class TokenInterceptor implements HandlerInterceptor {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(request.getRequestURI());
        response.setCharacterEncoding("utf-8");
        String token = request.getParameter("token");
        if (null != token) {
            User user = JWT.unsign(token, User.class);
            if (null != user) {
                return true;
            }
            responseMessage(response, response.getWriter(),10002, "token已失效");
            System.out.println("token无效");
            return false;
        }
        System.out.println("token不存在");
        responseMessage(response, response.getWriter(),10002, "token不存在");
        return false;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView model) throws Exception {

    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception arg3) throws Exception {

    }

    //请求不通过，返回错误信息给客户端
    private void responseMessage(HttpServletResponse response, PrintWriter out, int code, String error) {
        response.setContentType("application/json; charset=utf-8");
        RestResult result = RestResultGenerator.genErrorResult("403，认证不通过", code,error);
        out.print(JSONObject.fromObject(result));
        out.flush();
        out.close();
    }
}
