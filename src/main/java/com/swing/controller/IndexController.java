package com.swing.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 异常增强，以JSON的形式返回给客服端
 * 异常增强类型：NullPointerException,RunTimeException,ClassCastException,
 　　　　　　　　 NoSuchMethodException,IOException,IndexOutOfBoundsException
 　　　　　　　　 以及springmvc自定义异常等，如下：
 SpringMVC自定义异常对应的status code
 Exception                       HTTP Status Code
 ConversionNotSupportedException         500 (Internal Server Error)
 HttpMessageNotWritableException         500 (Internal Server Error)
 HttpMediaTypeNotSupportedException      415 (Unsupported Media Type)
 HttpMediaTypeNotAcceptableException     406 (Not Acceptable)
 HttpRequestMethodNotSupportedException  405 (Method Not Allowed)
 NoSuchRequestHandlingMethodException    404 (Not Found)
 TypeMismatchException                   400 (Bad Request)
 HttpMessageNotReadableException         400 (Bad Request)
 MissingServletRequestParameterException 400 (Bad Request)

 */


@Controller
@ResponseBody
public class IndexController {

    @RequestMapping("/")
    public String index() {
        return "Hello World!";
    }

//    // 运行时异常
//    @ExceptionHandler(RuntimeException.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public ErrorResponse run
}
