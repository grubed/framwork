package com.zongs365.util.resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Configuration
public class NativeAsyncTaskExecutePool implements AsyncConfigurer {


    //注入配置类
    @Autowired
    TaskThreadPoolConfig config;

    @Override
    public Executor getAsyncExecutor() {
        if (config == null) {
            ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 20, 1, java.util.concurrent.TimeUnit.MINUTES, new LinkedBlockingDeque<>());
            return executor;
        } else {
            ThreadPoolExecutor executor = new ThreadPoolExecutor(config.getMaxPoolSize(), config.getMaxPoolSize(), config.getKeepAliveSeconds(), java.util.concurrent.TimeUnit.MINUTES, new LinkedBlockingDeque<>());
            return executor;
        }
    }


    /**
     *  异步任务中异常处理
     * @return
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new AsyncUncaughtExceptionHandler() {

            @Override
            public void handleUncaughtException(Throwable arg0, Method arg1, Object... arg2) {
                log.error("=========================="+arg0.getMessage()+"=======================", arg0);
                log.error("exception method:"+arg1.getName());
            }
        };
    }
}

