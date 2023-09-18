package com.samodule.util;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReflectionUtil {
	private static final Logger logger = LoggerFactory.getLogger(ReflectionUtil.class);
	public	static ArrayList<Method> findGettersSetters(Class<?> c) {
		ArrayList<Method> list = new ArrayList<Method>();
		Method[] methods = c.getDeclaredMethods();
		for (Method method : methods)
			if (isGetter(method) || isSetter(method))
				list.add(method);
		return list;
	}
	public static ArrayList<Method> findGetters(Class<?> c) {
		ArrayList<Method> list = new ArrayList<Method>();
		Method[] methods = c.getDeclaredMethods();
		for (Method method : methods)
			if (isGetter(method))
				list.add(method);
		return list;
	}
	
	
	public static Map<String,String> findSetters(Class<?> c) {
		Map<String,String> map = new HashMap<String,String>();
		Method[] methods = c.getDeclaredMethods();
		for (Method method : methods){
			if (isSetter(method)){
				map.put(method.getName(), method.getParameterTypes()[0].getName());
			    logger.info(method.getName()+"  <<<<<<>>>>>>   "+ method.getParameterTypes()[0].getName());}
			}
		return map;
	}

	public static boolean isGetter(Method method) {
		if (Modifier.isPublic(method.getModifiers())
				&& method.getParameterTypes().length == 0) {
			if (method.getName().matches("^get[A-Z].*")
					&& !method.getReturnType().equals(void.class))
				return true;
			if (method.getName().matches("^is[A-Z].*")
					&& method.getReturnType().equals(boolean.class))
				return true;
		}
		return false;
	}

	public static boolean isSetter(Method method) {
		return Modifier.isPublic(method.getModifiers())
				&& method.getReturnType().equals(void.class)
				&& method.getParameterTypes().length == 1
				&& method.getName().matches("^set[A-Z].*");
	}
}
