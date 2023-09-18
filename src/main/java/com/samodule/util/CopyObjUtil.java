package com.samodule.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CopyObjUtil {
	public static Object cloneObject(Object obj, Object clone) {
		try {
			// Object clone = obj.getClass().newInstance();
			for (Field field : obj.getClass().getDeclaredFields()) {
				field.setAccessible(true);
				if (field.get(obj) == null
						|| Modifier.isFinal(field.getModifiers())) {
					continue;
				}
				if (field.getType().isPrimitive()
						|| field.getType().equals(String.class)
						|| field.getType().getSuperclass().equals(Number.class)
						|| field.getType().equals(Boolean.class)
						&& !(field.getType().equals(Date.class))) {
//					System.out.println("Before set ");
//					
//					System.out.println(field.get(obj));
//					System.out.println(field.get(clone));
					field.set(clone, field.get(obj));
//					System.out.println("==============");
//					System.out.println("After set ");				
//					System.out.println(field.get(obj));
					System.out.println(field.get(clone));
				} else if (field.getType().equals(Date.class)) {
				//	System.out.println("=====date=========");
					field.set(clone,field.get(obj));					
				}

				else {
				//	System.out.println("=====e=========");
					Object childObj = field.get(obj);
					if (childObj == obj) {
						field.set(clone, clone);
					} else {
						field.set(clone, cloneObject(field.get(obj), clone));
					}
				}
			}
			return clone;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static Date getDate(long date) {
		// Creating date format
		DateFormat simple = new SimpleDateFormat("dd MMM yyyy HH:mm:ss:SSS Z");

		// Creating date from milliseconds
		// using Date() constructor
		Date result = new Date(date);

		// Formatting Date according to the
		// given format
	//	System.out.println(simple.format(result));
		return result;
	}
}
