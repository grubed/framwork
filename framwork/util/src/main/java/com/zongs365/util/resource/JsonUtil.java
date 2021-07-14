package com.zongs365.util.resource;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.type.JavaType;

public class JsonUtil {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        // 为保持对象版本兼容性,忽略未知的属性
        MAPPER.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 序列化的时候，跳过null值
        // MAPPER.getSerializationConfig().withSerializationInclusion(Inclusion.NON_NULL);
        MAPPER.getSerializationConfig().setSerializationInclusion(Inclusion.NON_NULL);
        // 设置时间类型的格式
        MAPPER.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 将一个对象编码为json字符串
     *
     * @param obj ,if null return "null" 要编码的字符串
     * @return json字符串
     * @throws RuntimeException 若对象不能被编码为json串
     */
    public static String toJson(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            return MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("error encode json for " + obj, e);
        }
    }

    /**
     * 将一个对象编码成字节
     *
     * @param obj
     * @return
     */
    public static byte[] toBytes(Object obj) {
        try {
            return MAPPER.writeValueAsBytes(obj);
        } catch (Exception e) {
            throw new RuntimeException("error encode json for " + obj, e);
        }
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> toMap(String json) {
        return toObject(json, HashMap.class);
    }

    /**
     * 将一个json字符串解码为java对象
     * <p/>
     * 注意：如果传入的字符串为null，那么返回的对象也为null
     *
     * @param json json字符串
     * @param cls  对象类型
     * @return 解析后的java对象
     * @throws RuntimeException 若解析json过程中发生了异常
     */
    public static <T> T toObject(String json, Class<T> cls) {
        if (json == null) {
            return null;
        }
        try {
            return MAPPER.readValue(json, cls);
        } catch (Exception e) {
            throw new RuntimeException("error decode json to " + cls, e);
        }
    }

    /**
     * 将json字节解码为java对象
     *
     * @param jsonBytes json字节
     * @param cls       对象类型
     * @return 解码后的对象
     */
    public static <T> T toObject(byte[] jsonBytes, Class<T> cls) {
        try {
            return MAPPER.readValue(jsonBytes, cls);
        } catch (Exception e) {
            throw new RuntimeException("error decode json to " + cls);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> toList(String json) {
        return (List<T>) toList(json, Object.class);
    }

    public static <T> List<T> toList(String json, Class<T> elementType) {
        JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, elementType);
        try {
            return MAPPER.readValue(json, javaType);
        } catch (Exception e) {
            throw new RuntimeException("error decode json to " + elementType + " List", e);
        }
    }

    /**
     * 将json字符串解码为带有泛型的集合类
     *
     * @param json            json字符串
     * @param collectionClass 集合类
     * @param elementClass    泛型类
     * @return 集合类对象
     */
    public static Object toObject(String json, Class<?> collectionClass, Class<?> elementClass) {
        JavaType javaType = MAPPER.getTypeFactory().constructParametricType(collectionClass, elementClass);
        try {
            return MAPPER.readValue(json, javaType);
        } catch (Exception e) {
            throw new RuntimeException("error decode json to " + javaType, e);
        }
    }
}

