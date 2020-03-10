package com.wybs.mbti.common.util;

import java.lang.reflect.Array;

/**
 * <p>对象工具</p>
 *
 * <p>Date：2017-12-17</p>
 *
 * @author Mumus
 */
public class ObjectUtil {
    private ObjectUtil() {
    }

    public static Object[] toObjectArray(Object source) {
        if (source == null) {
            return new Object[0];
        }
        if (source instanceof Object[]) {
            return (Object[]) source;
        }

        if (!source.getClass().isArray()) {
            throw new IllegalArgumentException("Source is not an array: " + source);
        }

        int length = Array.getLength(source);
        if (length == 0) {
            return new Object[0];
        }

        Class<? extends Object> wrapperType = Array.get(source, 0).getClass();
        Object[] newArray = (Object[]) Array.newInstance(wrapperType, length);
        for (int i = 0; i < length; i++) {
            newArray[i] = Array.get(source, i);
        }
        return newArray;
    }
}
