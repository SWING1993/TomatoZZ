package com.swing.controller;

import com.swing.utils.RestResult;
import com.swing.utils.RestResultGenerator;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@ResponseBody
@Controller
public class IndexController {

    @RequestMapping("/")
    public String index() {
        return "Hello World!";
    }


    //运行时异常
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)

    public RestResult<String > runtimeExceptionHandler(RuntimeException runtimeException) throws Exception {
        return RestResultGenerator.genErrorResult(runtimeException.getMessage());
    }

    //空指针异常
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestResult<String > nullPointerExceptionHandler(NullPointerException ex) throws Exception {
        return RestResultGenerator.genErrorResult(ex.getMessage());
    }

    /*----- REQUEST ERROR -----*/

    //400错误
    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestResult<String > requestNotReadable(HttpMessageNotReadableException ex) throws Exception {
//        System.out.println("400..requestNotReadable");
//        ex.printStackTrace();
//        return ErrorResponse.of("Request Not Readable", ErrorCode.BAD_REQUEST, HttpStatus.BAD_REQUEST);
        return RestResultGenerator.genErrorResult(ex.getMessage());

    }

    //400错误
    @ExceptionHandler({TypeMismatchException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestResult<String > requestTypeMismatch(TypeMismatchException ex) throws Exception {
//        System.out.println("400..TypeMismatchException");
//        ex.printStackTrace();
//        return ErrorResponse.of("Type Mismatch Exception", ErrorCode.BAD_REQUEST, HttpStatus.BAD_REQUEST);
//        return RestResultGenerator.genErrorResult("Type Mismatch Exception", ex.getErrorCode(), HttpStatus.BAD_REQUEST);
        return RestResultGenerator.genErrorResult(ex.getMessage());
    }

    //400错误
    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestResult<String > requestMissingServletRequest(MissingServletRequestParameterException ex) throws Exception {
        ex.printStackTrace();
        //return ErrorResponse.of("Missing Servlet Request", ErrorCode.BAD_REQUEST, HttpStatus.BAD_REQUEST);
        return RestResultGenerator.genErrorResult(ex.getMessage());
    }

    //405错误
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public RestResult<String > request405() throws Exception {
//        return ErrorResponse.of("Method Not Allowed", ErrorCode.METHOD_NOT_ALLOWED, HttpStatus.METHOD_NOT_ALLOWED);
        return RestResultGenerator.genErrorResult("Method Not Allowed");
    }

    //500的异常会被这个方法捕获
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestResult<String > handleError(HttpServletRequest req, HttpServletResponse rsp, Exception e) throws Exception {
        return RestResultGenerator.genErrorResult(e.getMessage());
    }


}
