package com.wybs.mbti.web.json.introspect;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.type.TypeFactory;

/**
 * <p>输出过滤器</p>
 *
 * <p>Date：2018-04-02</p>
 * 
 * @author Mumus
 */
public class JsonFilterAnnotationIntrospector extends JacksonAnnotationIntrospector {
    public static final String DEFAULT_FILTER_ID = JsonFilterAnnotationIntrospector.class.getName() + "#DYNAMIC_FILTER";

    private static final long serialVersionUID = -8129952358135781660L;

    private final String filterId;

    public JsonFilterAnnotationIntrospector() {
        this(DEFAULT_FILTER_ID);
    }

    public JsonFilterAnnotationIntrospector(String filterId) {
        this.filterId = filterId;
    }

    @Override
    public Object findFilterId(Annotated ann) {
        Object id = super.findFilterId(ann);
        if (id == null) {
            JavaType javaType = TypeFactory.defaultInstance().constructType(ann.getRawType());
            if (!javaType.isContainerType()) {
                id = filterId;
            }
        }
        return id;
    }
}
