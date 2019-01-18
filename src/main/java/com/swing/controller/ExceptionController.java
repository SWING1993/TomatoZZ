package com.swing.controller;

import com.swing.utils.RestResult;
import com.swing.utils.RestResultGenerator;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import com.swing.utils.DingChatBot;

@ControllerAdvice
@ResponseBody
public class ExceptionController {

    //捕获异常
    @ExceptionHandler(Exception.class)
    public RestResult<String > handleError(HttpServletRequest req, Exception ex) throws Exception {
        Map map = new HashMap();
        map.put("Url",req.getRequestURI());
        map.put("Query",req.getQueryString());
        map.put("Info",ex.toString());
        String errorMsg = "服务异常\n" + map.toString();
        DingChatBot.sendMsg(errorMsg);
        return RestResultGenerator.genErrorResult("服务器异常，请稍后再试！", 10001, map.toString());
    }
}
