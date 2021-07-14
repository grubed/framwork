package com.zong365.test;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Slf4j
public class CheckTokenAspect {
    @Pointcut("@within(com.zongs365.web.annotation.CheckToken)")
    public void pointCutClass() {}

    @Pointcut("@annotation(com.zongs365.web.annotation.CheckToken)")
    public void pointCutMethod() {}
    /**
     * 权限处理
     * @param joinPoint
     */
    @Before("pointCutClass()")
    public void beforePointCutClass(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String token = request.getHeader("authToken");
        log.info(token);
    }
    @Before("pointCutMethod()")
    public void beforePointCutMethod(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String token = request.getHeader("authToken");
        log.info(token);
    }



}
