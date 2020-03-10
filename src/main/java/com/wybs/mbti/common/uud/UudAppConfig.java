package com.wybs.mbti.common.uud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 创建时间：2017年12月2日
 * <p>修改时间：2017年12月2日
 * <p>类说明：类初始化
 * 
 * @author Mumus
 * @version 1.0
 */
@Configuration
@ConfigurationProperties
public class UudAppConfig {
    private static final Logger logger = LoggerFactory.getLogger(UudAppConfig.class);

    private UudConfig uud;

    public UudConfig getUud() {
        return uud;
    }

    public void setUud(UudConfig uud) {
        this.uud = uud;
    }

    @Bean
    public UudAppClient createUudAppClient() {
        logger.debug("createUudAppClient() - init UudAppClient use config - uud={}", uud);

        return new UudAppClient(uud.getAccessId(), uud.getSecretKey(), uud.getHost());
    }
}
