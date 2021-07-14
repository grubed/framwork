package com.zongs365.web.dto;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * http请求信息
 *
 */
public class RequestInfo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public static String DEFAULT_GROUP_NAME = "default";
    /**
     * 客户端ip
     */
    private String clientIP;
    /**
     * 请求url
     */
    private String url;
    /**
     * 请求参数
     */
    private Map<String, String> parameters;
    /**
     * 请求头
     */
    private Map<String, String> headers;
    /**
     * 请求体
     */
    private String body;

    public String getClientIP() {
        return clientIP;
    }

    public void setClientIP(String clientIP) {
        this.clientIP = clientIP;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public String getParameter(String name) {
        return parameters == null ? "" : parameters.get(name);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getHeader(String name) {
        return headers == null ? "" : headers.get(name);
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCMD() {
        return parameters == null ? "" : parameters.get("cmd");
    }

    public String getGroup() {
        String group = parameters == null ? DEFAULT_GROUP_NAME : parameters.get("group");
        if (StringUtils.isBlank(group)) {
            return DEFAULT_GROUP_NAME;
        }
        return group;
    }
}

