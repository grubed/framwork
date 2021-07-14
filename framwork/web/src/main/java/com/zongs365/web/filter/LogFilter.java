package com.zongs365.web.filter;

import com.alibaba.fastjson.JSONObject;
import com.zongs365.util.resource.RequestUtil;
import com.zongs365.web.dto.RequestInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import com.zongs365.util.resource.ResourceUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Order(1)
@WebFilter(filterName="logFilter", urlPatterns="/*")
public class LogFilter implements Filter {

    @Override
    public void init(FilterConfig config) throws ServletException {
        log.info("doFilter init");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain){
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        try {
            BodyReaderHttpServletRequestWrapper wrapper = new BodyReaderHttpServletRequestWrapper((HttpServletRequest) request);

            // ResourceUtil.readStream(request.getInputStream());
            RequestInfo rinfo = parseRequest(wrapper);
            request.setAttribute("key.req.info", rinfo);
             ResponseWrapper responseWrapper = new ResponseWrapper(response);
             chain.doFilter(wrapper, responseWrapper);

           //  chain.doFilter(wrapper, response);

            // chain.doFilter(request, response);
            if (log.isInfoEnabled()) {
                 log.info(buildLog(request, responseWrapper));
            }
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(responseWrapper.getResponseData());
            outputStream.flush();
            outputStream.close();
        } catch (Throwable e) {
            handleErr(e, request, response);
        } finally {

        }
    }
    public static String getBodyString(BufferedReader br) {
        String inputLine;
        String str = "";
        try {
            while ((inputLine = br.readLine()) != null) {
                str += inputLine;
            }
            br.close();
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
        return str;
    }
    private String buildLog(HttpServletRequest request, ResponseWrapper response) throws ServletException, IOException {
        StringBuilder builder = new StringBuilder();
        String ip = RequestUtil.getClientIP(request);
        builder.append("ip:").append(ip);
        RequestInfo reqInfo = (RequestInfo) request.getAttribute("key.req.info");
        if (reqInfo != null) {
            String reqJson = JSONObject.toJSONString(reqInfo);
            builder.append(",req=").append(reqJson);
        }

        String respJson = new String(response.getResponseData());
        if (StringUtils.isNotBlank(respJson)) {
            builder.append(",resp=").append(respJson);
        }
        return builder.toString();
    }
    /**
     * 处理请求异常
     *
     * @param t
     */
    public void handleErr(Throwable t, HttpServletRequest request, HttpServletResponse response) {
//        BizResponse responseObj = new BizResponse();
//        responseObj.setCmd(request.getParameter("cmd"));
//        responseObj.setGroup(request.getParameter("group"));
//        responseObj.setErrCode(CommonError.SERVER_ERROR.getErrCode());
//        responseObj.setErrName(CommonError.SERVER_ERROR.getErrName());
//        BizException be = BizException.find(t);
//        boolean error = true;
//        if (be != null) {
//            error = be.getErrorCode() == CommonError.SERVER_ERROR.getErrCode();
//            responseObj.setErrCode(be.getErrorCode());
//            responseObj.setErrName(be.getErrorName());
//            responseObj.setErrDesc(be.getErrorDesc());
//            responseObj.setResult(be.getAttachMent());
//        }
//        String json = JsonUtil.toJson(responseObj);
//        try {
//            ResponseUtil.sendJsonResponse(json, response);
//        } catch (Exception e) {
//            logger.warn("fail to send error to client", e);
//        }
//        request.setAttribute(HttpKeys.RESPONSE_INFO_KEY, json);
//        String logMsg = buildLog(request);
//        if (error) {
//            logger.error(logMsg, t);
//        } else {
//            logger.info(logMsg, t);
//        }
    }

    private RequestInfo parseRequest(HttpServletRequest request) {
        RequestInfo rinfo = new RequestInfo();
        String body = null;
        try {
            byte[] requestBytes = ResourceUtil.readStream(request.getInputStream());
            if (requestBytes != null && requestBytes.length > 0) {
                body = new String(requestBytes, "UTF-8");
            }
        } catch (IOException e) {
            // throw CommonError.SERVER_ERROR.toException("fail to read request body from " + request.getRequestURL(), e);
        }
        rinfo.setBody(body);
        Enumeration<String> paramNames = request.getParameterNames();
        Map<String, String> params = new HashMap<String, String>();
        while (paramNames.hasMoreElements()) {
            String pname = paramNames.nextElement();
            String pval = request.getParameter(pname);
            params.put(pname, pval);
        }
        rinfo.setParameters(params);
        Map<String, String> headers = new HashMap<>();
        Enumeration<String> hnames = request.getHeaderNames();
        while (hnames.hasMoreElements()) {
            String hname = hnames.nextElement();
            String hval = request.getHeader(hname);
            headers.put(hname, hval);
        }
        rinfo.setHeaders(headers);
        rinfo.setUrl(request.getRequestURL().toString());
        return rinfo;
    }

    @Override
    public void destroy() {
    }
}
