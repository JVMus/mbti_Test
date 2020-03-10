package com.wybs.mbti.common.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * <p>Json转换工具类</p>
 *
 * <p>Date：2016-12-08</p>
 *
 * @author Mumus
 */
public class JsonUtil {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        MAPPER.setSerializationInclusion(Include.NON_NULL);
    }

    /**
     * 转换成json字符串
     * 
     * @param obj 转换对象
     * @return json字符串
     */
    public static String toJson(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            return MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            logger.error("toJson() - to json string error", e);
            return null;
        }
    }

    /**
     * 转换对象
     * 
     * @param json json字符串
     * @param valueType 对象类型
     * @return 对象
     */
    public static <T> T fromJson(String json, Class<T> valueType) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        try {
            return MAPPER.readValue(json, valueType);
        } catch (Exception e) {
            logger.error("fromJson() - to object error", e);
            return null;
        }
    }

    /**
     * 转换对象
     * 
     * @param json json字符串
     * @param ref 类型引用
     * @return 对象
     */
    public static <T> T fromJson(String json, TypeReference<T> ref) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        try {
            return MAPPER.readValue(json, ref);
        } catch (Exception e) {
            logger.error("fromJson() - to object error", e);
            return null;
        }
    }

    public static Map<String, String> convertToMap(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            return MAPPER.convertValue(obj, new TypeReference<Map<String, String>>() {
            });
        } catch (Exception e) {
            logger.error("convertToMap() - object convert to map error", e);
            return null;
        }
    }

    public static <T> T convertToObj(Map<String, String> map, Class<T> valueType) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        try {
            return MAPPER.convertValue(map, valueType);
        } catch (Exception e) {
            logger.error("convertToMap() - object convert to map error", e);
            return null;
        }
    }

    private JsonUtil() {
    }
}
