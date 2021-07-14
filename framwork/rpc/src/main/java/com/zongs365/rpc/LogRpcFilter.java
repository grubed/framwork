package com.zongs365.rpc;

import com.zongs365.util.resource.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import com.alibaba.dubbo.rpc.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.common.extension.Activate;


@Activate
@Slf4j
public class LogRpcFilter implements Filter {


    public LogRpcFilter() {

    }

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        try {
            long start = System.currentTimeMillis();
            Object[] args = invocation.getArguments();
            Result result = invoker.invoke(invocation);
            long used = System.currentTimeMillis() - start;
            if (log.isInfoEnabled()) {

                StringBuilder builder = new StringBuilder();

                if ( args != null && args.length > 0) {
                    builder.append("params:").append(JsonUtil.toJson(args));
                }
                Object value = result.getValue();
                if ( value != null ) {
                    builder.append("|result:").append(JsonUtil.toJson(value));
                }
                if (!result.hasException()) {
                    log.info(builder.toString());
                } else {
                    Throwable t = result.getException();
                    log.error(builder.toString(), t);
                }
            }
            return result;
        } finally {

        }
    }
}
