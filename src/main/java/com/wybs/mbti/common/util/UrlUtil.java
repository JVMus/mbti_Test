package com.wybs.mbti.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * <p>url地址</p>
 *
 * <p>Date：2017-12-17</p>
 *
 * @author Mumus
 */
public class UrlUtil {
    private static final Logger logger = LoggerFactory.getLogger(UrlUtil.class);

    private UrlUtil() {
    }

    public static String buildUrl(final String endpoint, final Map<String, ? extends Object> params) {
         StringBuilder builder = new StringBuilder(endpoint);
        String queryString = buildQueryString(params);
        if (!"".equals(queryString)) {
            builder.append("?").append(queryString);
        }

        return builder.toString();
    }

    public static String buildQueryString(final Map<String, ? extends Object> params) {
        if (params == null || params.isEmpty()) {
            return "";
        }
        return buildQueryString(params, false, true);
    }
    
    public static String buildOriginalQueryString(final Map<String, ? extends Object> params) {
        if (params == null || params.isEmpty()) {
            return "";
        }
        return buildQueryString(params, false, false);
    }

    public static String buildSortOriginalQueryString(Map<String, ? extends Object> params) {
        if (params == null || params.isEmpty()) {
            return "";
        }

        Map<String, ? extends Object> sortParams = new TreeMap<>(params);
        return buildQueryString(sortParams, true, false);
    }

    private static String buildQueryString(final Map<String, ? extends Object> params, final boolean isSort, final boolean isEncode) {
        StringBuilder builder = new StringBuilder();
        params.entrySet().stream().filter(entry -> entry.getValue() != null).forEach(entry -> {
            String key = entry.getKey();
            Object obj = entry.getValue();

            if (obj.getClass().isArray()) {
                Object[] values = ObjectUtil.toObjectArray(obj);
                if (isSort) {
                    values = values.clone();
                    Arrays.sort(values);
                }
                Arrays.stream(values).filter(Objects::nonNull).forEach(val -> builder.append(key).append("=").append(toStringValue(val, isEncode)).append("&"));
            } else if (obj instanceof Collection) {
                Collection<?> coll = (Collection<?>) obj;
                Object[] values = coll.toArray();
                if (isSort) {
                    Arrays.sort(values);
                }
                Arrays.stream(values).filter(Objects::nonNull).forEach(val -> builder.append(key).append("=").append(toStringValue(val, isEncode)).append("&"));
            } else {
                builder.append(key).append("=").append(toStringValue(obj, isEncode)).append("&");
            }
        });

        if (builder.length() == 0) {
            return "";
        }

        return builder.substring(0, builder.length() - 1);
    }

    private static String toStringValue(Object obj, boolean isEncode) {
        if (obj == null) {
            return null;
        }

        String value = String.valueOf(obj);
        if (isEncode) {
            try {
                return URLEncoder.encode(value, StandardCharsets.UTF_8.name());
            } catch (UnsupportedEncodingException e) {
                logger.error("encode() - url encode error - obj={}", obj, e);
                return null;
            }
        }

        return value;

    }
}
