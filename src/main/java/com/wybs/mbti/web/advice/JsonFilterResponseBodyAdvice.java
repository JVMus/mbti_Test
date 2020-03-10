package com.wybs.mbti.web.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.wybs.mbti.web.json.annotation.JsonDyncFilter;
import com.wybs.mbti.web.json.annotation.JsonDyncFilters;
import com.wybs.mbti.web.json.introspect.JsonFilterAnnotationIntrospector;
import com.wybs.mbti.web.json.ser.ExceptPropertyFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMappingJacksonResponseBodyAdvice;

/**
 * <p>属性过滤拦截</p>
 *
 * <p>Date：2018-04-02</p>
 * 
 * @author mumus
 */
@ControllerAdvice
public class JsonFilterResponseBodyAdvice extends AbstractMappingJacksonResponseBodyAdvice {

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        objectMapper.setAnnotationIntrospector(new JsonFilterAnnotationIntrospector());
        objectMapper.setFilterProvider(new SimpleFilterProvider().setFailOnUnknownId(false));
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return super.supports(returnType, converterType)
            && (returnType.getMethodAnnotation(JsonDyncFilter.class) != null || returnType.getMethodAnnotation(JsonDyncFilters.class) != null);
    }

    @Override
    protected void beforeBodyWriteInternal(MappingJacksonValue bodyContainer, MediaType contentType, MethodParameter returnType, ServerHttpRequest request,
        ServerHttpResponse response) {
        ExceptPropertyFilter filter;
        JsonDyncFilter annotation = returnType.getMethodAnnotation(JsonDyncFilter.class);
        if (annotation != null) {
            filter = new ExceptPropertyFilter(annotation);
        } else {
            filter = new ExceptPropertyFilter(returnType.getMethodAnnotation(JsonDyncFilters.class).value());
        }

        SimpleFilterProvider filters = new SimpleFilterProvider();
        filters.addFilter(JsonFilterAnnotationIntrospector.DEFAULT_FILTER_ID, filter);
        bodyContainer.setFilters(filters);
    }
}
