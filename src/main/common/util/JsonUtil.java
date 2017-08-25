package main.common.util;

import java.util.List;

import org.json.JSONArray;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
	private static ObjectMapper mapper;
	
	/**
	 * 获取ObjectMapper实例
	 * @param createNew 方式：true，新实例；false,存在的mapper实例
	 * @return
	 */
	public static synchronized ObjectMapper getMapperInstance(boolean createNew) {   
        if (createNew) {   
            return new ObjectMapper();   
        } else if (mapper == null) {   
            mapper = new ObjectMapper();   
        }   
        return mapper;   
    }
	
	/**
	 * 将java对象转换成json字符串
	 * @param obj 准备转换的对象
	 * @return json字符串
	 * @throws Exception 
	 */
	public static String toJsonString(Object o) throws Exception{
		return getMapperInstance(false).writeValueAsString(o);
	}
	
	/**
	 * 将json字符串转换成java对象
	 * @param json 准备转换的json字符串
	 * @param cls  准备转换的类
	 * @return 
	 * @throws Exception 
	 */
	public static Object toJsonObject(String json, Class<?> cls) throws Exception {
		try {
			ObjectMapper objectMapper = getMapperInstance(false);
			Object vo = objectMapper.readValue(json, cls);
			return vo;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}	
	}
	
	public static JSONArray toJsonArray(String json) {
		return json!=null?new JSONArray(json):new JSONArray();
	}
	
	/**
	 * @param jsonString
	 *           
	 * @param cls
	 *         
	 * @return
	 * @throws Exception
	 */
	public static <T> T parseObject(String jsonString, Class<T> cls)
			throws Exception {
		T t = JSON.parseObject(jsonString, cls);
		return t;
	}

	/**
	 * @param <T>
	 * @param jsonString
	 * @param cls
	 * @return
	 * @throws Exception
	 */
	public static <T> List<T> parseList(String jsonString, Class<T> cls)
			throws Exception {
		List<T> list = JSON.parseArray(jsonString, cls);
		return list;
	}
	
	/**
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public static String toJSONString(Object object) throws Exception{
		String jsonString = JSON.toJSONString(object);
		return jsonString;
	}
	
}