package com.zongs365.common.util;

import com.zongs365.common.vo.LoginInfoVO;
import lombok.Data;


import javax.servlet.http.HttpServletRequest;

@Data
public class LoginInfoUtil {

    private final static String APP_LOGIN_INFO = "appLoginInfo";
    private static final String TOKEN = "token";
    private static final String DEVICE_ID = "deviceId";
    private static final String SOURCE_TYPE = "sourcetype";
    private static final String SOURCE_TYPE_01 = "sourceType";
    private static final String SOURCE_TYPE_02 = "SOURCETYPE";

    public static LoginInfoVO getLoginInfo() {
        return (LoginInfoVO) ServletUtil.getCurrentRequest().getAttribute(APP_LOGIN_INFO);
    }



    public static String getToken() {
        return ServletUtil.getCurrentRequest().getHeader(TOKEN);
    }
    public static String getDeviceId() {
        return ServletUtil.getCurrentRequest().getHeader(DEVICE_ID);
    }

    public static String getSourceType() {
        String requestHeader = ServletUtil.getCurrentRequest().getHeader(SOURCE_TYPE);
        if(StringUtils.isBlank(requestHeader)){
            requestHeader = ServletUtil.getCurrentRequest().getHeader(SOURCE_TYPE_01);
            if (StringUtils.isBlank(requestHeader)){
                requestHeader = ServletUtil.getCurrentRequest().getHeader(SOURCE_TYPE_02);
            }
        }
        return requestHeader;
    }

    /**
     * 获取自定义token实体
     * @param c
     * @param <T>
     * @return
     */
    public static <T extends LoginInfoVO> T  getLoginInfo(Class<T> c){
        return c.cast(ServletUtil.getCurrentRequest().getAttribute(APP_LOGIN_INFO));
    }


    /**
     * 获取ip地址
     * @param request
     * @return
     */
    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        System.out.println("x-forwarded-for ip: " + ip);
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if( ip.indexOf(",")!=-1 ){
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            System.out.println("Proxy-Client-IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            System.out.println("WL-Proxy-Client-IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
            System.out.println("HTTP_CLIENT_IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            System.out.println("HTTP_X_FORWARDED_FOR ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
            System.out.println("X-Real-IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            System.out.println("getRemoteAddr ip: " + ip);
        }
        System.out.println("获取客户端ip: " + ip);
        return ip;
    }
}
