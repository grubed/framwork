package com.zong365.test;

import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@ResponseBody
@Order(1)
public class GlobalExceptionHandler implements ResponseBodyAdvice<Object>, RequestBodyAdvice {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     *  dubbo 服务异常
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(RpcException.class)
    public String exceptionHandel(RpcException e, HttpServletRequest request) {
        logger.error(request.getRequestURI() + "接口请求异常:exception:{}", e.getMessage());
        logger.error("接口请求异常:",e);
        return  e.getMessage();
    }

    /***
     * @Description: Exception处理
     * @Param: []
     * @return: io.tooko.core.entity.ResponseMessage
     * @Author: Mr.ShangGuan
     * @Date: 2019/3/13
     */
    @ExceptionHandler(Exception.class)
    public String exceptionHandel(Exception e, HttpServletRequest request) {
        logger.error(request.getRequestURI() + "接口请求异常:exception:{}", e.getStackTrace(), e);
        return e.getMessage();
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override

    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        return body;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        return inputMessage;
    }

    @Override

    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {

        return body;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }
}
