package com.wybs.mbti.web.json.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <p>json字段输出配置，多属性</p>
 *
 * <p>Date：2018-04-02</p>
 * 
 * @author Mumus
 */
@Documented
@Target(METHOD)
@Retention(RUNTIME)
public @interface JsonDyncFilters {
    JsonDyncFilter[] value() default {};
}
