package com.zongs365.rpc;

import com.alibaba.dubbo.rpc.*;
import com.alibaba.dubbo.rpc.service.GenericService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.common.utils.ReflectUtils;
import org.apache.dubbo.common.utils.StringUtils;
import org.apache.dubbo.rpc.AppResponse;

import java.lang.reflect.Method;

@Activate
@Slf4j
public class DubboExceptionFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        try {
            Result result = invoker.invoke(invocation);
            if (result.hasException() && GenericService.class != invoker.getInterface()) {
                try {
                    Throwable exception = result.getException();

                    // directly throw if it's checked exception,or BizException
                    if (!(exception instanceof RuntimeException) && (exception instanceof Exception)) {
                        return result;
                    }
                    // directly throw if the exception appears in the signature
                    try {
                        Method method = invoker.getInterface().getMethod(invocation.getMethodName(),
                                invocation.getParameterTypes());
                        Class<?>[] exceptionClassses = method.getExceptionTypes();
                        for (Class<?> exceptionClass : exceptionClassses) {
                            if (exception.getClass().equals(exceptionClass)) {
                                return result;
                            }
                        }
                    } catch (NoSuchMethodException e) {
                        return result;
                    }

                    // for the exception not found in method's signature, print ERROR message in
                    // server's log.
                    log.error(
                            "Got unchecked and undeclared exception which called by "
                                    + RpcContext.getContext().getRemoteHost() + ". service: "
                                    + invoker.getInterface().getName() + ", method: " + invocation.getMethodName()
                                    + ", exception: " + exception.getClass().getName() + ": " + exception.getMessage(),
                            exception);

                    // directly throw if exception class and interface class are in the same jar
                    // file.
                    String serviceFile = ReflectUtils.getCodeBase(invoker.getInterface());
                    String exceptionFile = ReflectUtils.getCodeBase(exception.getClass());
                    if (serviceFile == null || exceptionFile == null || serviceFile.equals(exceptionFile)) {
                        return result;
                    }
                    // directly throw if it's JDK exception
                    String className = exception.getClass().getName();
                    if (className.startsWith("java.") || className.startsWith("javax.")) {
                        return result;
                    }
                    // directly throw if it's dubbo exception
                    if (exception instanceof RpcException) {
                        return result;
                    }

                    // otherwise, wrap with RuntimeException and throw back to the client
                    return (Result) new AppResponse(new RuntimeException(StringUtils.toString(exception)));
                } catch (Throwable e) {
                    log.warn("Fail to ExceptionFilter when called by " + RpcContext.getContext().getRemoteHost()
                            + ". service: " + invoker.getInterface().getName() + ", method: "
                            + invocation.getMethodName() + ", exception: " + e.getClass().getName() + ": "
                            + e.getMessage(), e);
                    return result;
                }
            }
            return result;
        } catch (RuntimeException e) {
            log.error("Got unchecked and undeclared exception which called by "
                    + RpcContext.getContext().getRemoteHost() + ". service: " + invoker.getInterface().getName()
                    + ", method: " + invocation.getMethodName() + ", exception: " + e.getClass().getName() + ": "
                    + e.getMessage(), e);
            throw e;
        }
    }
}
