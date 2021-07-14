package com.zongs365.web.filter;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Configuration
public class WebMvcRegistrationAdapter implements WebMvcRegistrations {

    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        return new FeignRequestMappingHandlerMapping();
    }

    public class FeignRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

        @Override
        protected boolean isHandler(Class<?> beanType) {


            //去掉RequestMapping
            return (AnnotatedElementUtils.hasAnnotation(beanType, Controller.class) ||
                    ( AnnotatedElementUtils.hasAnnotation(beanType, RequestMapping.class) && beanType.isInterface()==false )
            );
        }
    }
}
