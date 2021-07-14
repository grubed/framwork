package com.zongs365.util.resource;


import java.nio.charset.Charset;

import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;



/**
 * 响应工具类
 *
 *
 */
@Slf4j
public class ResponseUtil {

    /**
     * 一些http头
     */
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_LENGTH = "Content-Length";
    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    public static void sendJsonResponse(String s, HttpServletResponse response) {
        if (s == null) {
            return;
        }
        sendResponse(s.getBytes(DEFAULT_CHARSET), response, "text/html");
    }

//    public static String sendResponse(RequestInfo rinfo, Object result, HttpServletResponse response) {
//        BizResponse respObj = new BizResponse();
//        respObj.setCmd(rinfo.getCMD());
//        respObj.setGroup(rinfo.getGroup());
//        respObj.setErrCode(CommonError.OK.getErrCode());
//        respObj.setErrName(CommonError.OK.getErrName());
//        respObj.setResult(result);
//        String json = JsonUtil.toJson(respObj);
//        sendJsonResponse(json, response);
//        return json;
//    }

    private static void sendResponse(byte[] bytes, HttpServletResponse response, String contentType) {
        if (bytes == null || bytes.length == 0) {
            return;
        }
        response.setContentLength(bytes.length);
        response.addHeader("version", "HTTP/1.1");
        if (StringUtils.isNotBlank(contentType)) {
            response.setContentType(contentType);
        }
        try {
            response.getOutputStream().write(bytes);
            response.getOutputStream().flush();
        } catch (Exception e) {
            log.warn("fail to send response", e);
        }
    }
}
