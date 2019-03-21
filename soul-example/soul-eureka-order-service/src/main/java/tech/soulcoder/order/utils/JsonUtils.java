package tech.soulcoder.order.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * json工具类
 */
public class JsonUtils {


    private static final ObjectMapper objectMapper = new ObjectMapper();


    /**
     * json字符串转JsonNode对象的方法
     */
    public static JsonNode str2JsonNode(String str){
        try {
            return  objectMapper.readTree(str);
        } catch (IOException e) {
            return null;
        }
    }



}
