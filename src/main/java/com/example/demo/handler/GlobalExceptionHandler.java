package com.example.demo.handler;

import com.example.demo.dto.Response;
import com.example.demo.exception.BizException;
import com.example.demo.exception.ExceptionEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = BizException.class)
    @ResponseBody
    public Response bizExceptionHandler(HttpServletRequest req, BizException e){
        logger.error("BizException: {}",e.getErrorMsg());
        return Response.error(e.getErrorCode(),e.getErrorMsg());
    }

    @ExceptionHandler(value =Exception.class)
    @ResponseBody
    public Response exceptionHandler(HttpServletRequest req, Exception e){
        logger.error("unknown exception :",e);
        return Response.error(ExceptionEnum.INTERNAL_SERVER_ERROR);
    }
}

