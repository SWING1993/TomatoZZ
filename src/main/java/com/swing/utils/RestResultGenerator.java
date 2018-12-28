package com.swing.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class RestResultGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestResultGenerator.class);


    /**
     * normal
     * @param success
     * @param data
     * @param message
     * @param <T>
     * @return
     */
    public static <T> RestResult<T> genResult(boolean success, T data, String message, int code, String error) {
        RestResult result = RestResult.newInstance();
        result.setSuccess(success);
        result.setResult(data);
        result.setCode(code);
        result.setMessage(message);
        result.setError(error);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("generate rest result:{}",result);
        }
        return result;
    }


    /**
     * success no message
     * @return
     */
    public static RestResult genSuccessResult() {
        return genSuccessResult(null);
    }

    /**
     * success
     * @param data
     * @param <T>
     * @return
     */
    public static <T> RestResult<T> genSuccessResult(T data) {
        return genResult(true,data,"处理成功", 10000, "");
    }



    /**
     * error message
     * @param message error message
     * @param <T>
     * @return
     */
    public static <T> RestResult<T> genErrorResult(String message) {
        return genResult(false,null, message, 10001, "");
    }

    /**
     * error message
     * @param message error message
     * @param <T>
     * @return
     */
    public static <T> RestResult<T> genErrorResult(String message, int code, String error) {
        return genResult(false,null, message, code, error);
    }
}
