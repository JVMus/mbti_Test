package com.wybs.mbti;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * <p>接口启动器</p>
 *
 * <p>Date: 2018-05-03</p>
 *
 * @author Mumus
 */
@Configuration
@ComponentScan(basePackages = { "com.wybs.mbti" })
@EnableConfigurationProperties
@MapperScan("com.wybs.mbti")
@EnableAutoConfiguration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableAsync
@EnableScheduling
@PropertySource(value = "classpath:application.properties")
public class Application extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}