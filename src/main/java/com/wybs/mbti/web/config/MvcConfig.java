package com.wybs.mbti.web.config;

import com.wybs.mbti.web.interceptor.AuthInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

/**
 * 创建时间：2016年12月5日
 * <p>修改时间：2016年12月5日
 * <p>类说明：Web配置信息
 * 
 * @author mumus
 * @version 1.0
 */
@Configuration
public class MvcConfig extends BaseMvcConfig {
    private static final Logger logger = LoggerFactory.getLogger(MvcConfig.class);

    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        logger.info("addInterceptors() - add the auth filter");

        InterceptorRegistration bregistration = registry.addInterceptor(authInterceptor);
        bregistration.addPathPatterns("/**");
        bregistration.excludePathPatterns("/", "/notify/**", "/error");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*").allowCredentials(true).allowedMethods("GET", "POST", "DELETE", "PUT");
        super.addCorsMappings(registry);
    }
    
}