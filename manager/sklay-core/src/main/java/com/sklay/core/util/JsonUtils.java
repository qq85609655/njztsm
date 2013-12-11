package com.sklay.core.util;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.alibaba.fastjson.JSON;

public class JsonUtils {
	
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	public static final String parseJSON(Object object){
		try {
			return MAPPER.writeValueAsString(object);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return StringUtils.EMPTY;
	}
	
	public static final String toJSON(Object object) {
		return JSON.toJSONString(object);
	}

	public static final <T> T toType(String jsonString, Class<T> clazz) {
		return JSON.parseObject(jsonString, clazz);
	}

}
