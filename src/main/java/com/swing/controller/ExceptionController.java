package com.swing.controller;

import com.swing.utils.RestResult;
import com.swing.utils.RestResultGenerator;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@ResponseBody
public class ExceptionController {

    //捕获异常
    @ExceptionHandler(Exception.class)
    public RestResult<String > handleError(HttpServletRequest req, Exception ex) throws Exception {
        Map map = new HashMap();
        map.put("Url",req.getRequestURL());
        map.put("Method",req.getMethod());
//        map.put("RemoteAddr",req.getRemoteAddr());
//        map.put("RemotePort",req.getRemotePort());
        map.put("Query",req.getQueryString());
        map.put("Info",ex.toString());
        return RestResultGenerator.genErrorResult("服务器异常，请稍后再试！", 10001, map.toString());
    }
}
