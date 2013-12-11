package com.sklay.core.util;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ClassUtils extends org.springframework.util.ClassUtils {
	public static Method getMethodByName(Class<?> clazz,String methodName){
		Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			if(method.getName().equals(methodName)){
				return method;
			}
		}
		return null;
	}
	
	 public static Class getGenericParameter(Class clazz, int index) {
	        Type genType = clazz.getGenericSuperclass();
	        if (genType instanceof ParameterizedType) {
	            Type param = ((ParameterizedType) genType).getActualTypeArguments()[index];
	            if (param instanceof Class) {
	                return (Class) param;
	            }
	        }
	        return null;
	    }

	    public static Class getGenericParameter0(Class clazz) {
	        return getGenericParameter(clazz, 0);
	    }
}
