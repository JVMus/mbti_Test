package com.wybs.mbti.web.json.ser;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.wybs.mbti.web.json.annotation.JsonDyncFilter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.util.*;

/**
 * <p>属性过滤</p>
 *
 * <p>Date：2018-04-02</p>
 * 
 * @author Mumus
 */
public class ExceptPropertyFilter implements PropertyFilter {
    private final Map<Class<?>, Set<String>> includes = new HashMap<>(16);
    private final Map<Class<?>, Set<String>> excludes = new HashMap<>(16);

    public ExceptPropertyFilter(JsonDyncFilter... filters) {
        for (JsonDyncFilter filter : filters) {
            Set<String> shows = parseAllFields(Arrays.asList(filter.includes()));
            Set<String> hides = parseAllFields(Arrays.asList(filter.excludes()));
            shows.removeAll(hides);

            this.includes.put(filter.target(), shows);
            this.excludes.put(filter.target(), hides);
        }
    }

    @Override
    public void serializeAsField(Object pojo, JsonGenerator jgen, SerializerProvider prov, PropertyWriter writer) throws Exception {
        if (isFilter(pojo, writer)) {
            writer.serializeAsField(pojo, jgen, prov);
        } else if (!jgen.canOmitFields()) {
            writer.serializeAsOmittedField(pojo, jgen, prov);
        }
    }

    @Override
    public void serializeAsElement(Object elementValue, JsonGenerator jgen, SerializerProvider prov, PropertyWriter writer) throws Exception {
        writer.serializeAsElement(elementValue, jgen, prov);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void depositSchemaProperty(PropertyWriter writer, ObjectNode propertiesNode, SerializerProvider provider) throws JsonMappingException {
        writer.depositSchemaProperty(propertiesNode, provider);
    }

    @Override
    public void depositSchemaProperty(PropertyWriter writer, JsonObjectFormatVisitor objectVisitor, SerializerProvider provider) throws JsonMappingException {
        writer.depositSchemaProperty(objectVisitor, provider);
    }

    protected boolean isFilter(Object object, PropertyWriter writer) {
        Validate.notNull(object);

        Set<String> shows = getIncludeProperties(object);
        Set<String> hides = getExcludeProperties(object);

        return (shows.isEmpty() && hides.isEmpty()) || shows.contains(writer.getName()) || !(hides.isEmpty() || hides.contains(writer.getName()));
    }

    private Set<String> getIncludeProperties(Object object) {
        for (Class<?> cls = object.getClass(); cls != Object.class; cls = cls.getSuperclass()) {
            Set<String> fields = includes.get(cls);
            if (fields != null) {
                return fields;
            }
        }
        return new HashSet<>(0);
    }

    private Set<String> getExcludeProperties(Object object) {
        for (Class<?> cls = object.getClass(); cls != Object.class; cls = cls.getSuperclass()) {
            Set<String> fields = excludes.get(cls);
            if (fields != null) {
                return fields;
            }
        }
        return new HashSet<>(0);
    }

    private Set<String> parseAllFields(List<String> fields) {
        if (fields.isEmpty()) {
            return new HashSet<>(0);
        }

        Set<String> snakes = new HashSet<>(fields.size() * 2);
        snakes.addAll(fields);
        fields.forEach(f -> snakes.add(camelToSnake(f)));
        return snakes;
    }

    private String camelToSnake(final String camel) {
        if (StringUtils.isEmpty(camel)) {
            return camel;
        }
        final StringBuilder sb = new StringBuilder(camel.length() + camel.length());
        for (int i = 0; i < camel.length(); i++) {
            final char c = camel.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append(sb.length() != 0 ? '_' : "").append(Character.toLowerCase(c));
            } else {
                sb.append(Character.toLowerCase(c));
            }
        }
        return sb.toString();
    }
}
