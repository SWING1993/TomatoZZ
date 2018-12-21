package com.swing.controller;

import com.swing.utils.RestResult;
import com.swing.utils.RestResultGenerator;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        map.put("RemoteAddr",req.getRemoteAddr());
        map.put("RemotePort",req.getRemotePort());
        map.put("Query",req.getQueryString());
        map.put("Info",ex.toString());
        return RestResultGenerator.genErrorResult("服务器异常，请稍后再试！", 10001, map.toString());
    }

//    //运行时异常
//    @ExceptionHandler(RuntimeException.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public RestResult<String > runtimeExceptionHandler(RuntimeException runtimeException) throws Exception {
//        return RestResultGenerator.genErrorResult(runtimeException.getMessage());
//    }
//
//    //空指针异常
//    @ExceptionHandler(NullPointerException.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public RestResult<String > nullPointerExceptionHandler(NullPointerException ex) throws Exception {
//        return RestResultGenerator.genErrorResult(ex.getMessage());
//    }
//
//    //400错误
//    @ExceptionHandler({HttpMessageNotReadableException.class})
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public RestResult<String > requestNotReadable(HttpMessageNotReadableException ex) throws Exception {
//        String message = ex.getMessage() +  HttpStatus.BAD_REQUEST;
//        return RestResultGenerator.genErrorResult(message);
//    }
//
//    //400错误
//    @ExceptionHandler({TypeMismatchException.class})
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public RestResult<String > requestTypeMismatch(TypeMismatchException ex) throws Exception {
//        String message = ex.getMessage() +  HttpStatus.BAD_REQUEST;
//        return RestResultGenerator.genErrorResult(ex.getMessage());
//    }
//
//    //400错误
//    @ExceptionHandler({MissingServletRequestParameterException.class})
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public RestResult<String> requestMissingServletRequest(MissingServletRequestParameterException ex, HttpServletRequest req) throws Exception {
//        ex.printStackTrace();
//        String error = "URL:" + req.getRequestURI() + "\n q请求方法" + req.getMethod() + "\n" + ex.toString();
//        return RestResultGenerator.genErrorResult("400", 400, error);
//    }
//
//    //404错误
//    @ExceptionHandler({NoHandlerFoundException.class})
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public RestResult<String > request404(NoHandlerFoundException ex) throws Exception {
//        return RestResultGenerator.genErrorResult(ex.getMessage());
//    }
//
//    //405错误
//    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
//    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
//    public RestResult<String > request405(HttpServletRequest req) throws Exception {
//        String error = "URL:" + req.getRequestURI() + "\n q请求方法" + req.getMethod();
//        return RestResultGenerator.genErrorResult("请求方法错误", 405, error);
//    }
//
//    //500的异常会被这个方法捕获
//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public RestResult<String > handleError(HttpServletRequest req, HttpServletResponse rsp, Exception ex) throws Exception {
//        Map map = new HashMap();
//        map.put("Url",req.getRequestURI());
//        map.put("Method",req.getMethod());
//        map.put("Info",ex.toString());
//        return RestResultGenerator.genErrorResult("服务器异常，请稍后再试！", 10001, map.toString());
//    }
}
