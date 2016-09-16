package com.bstek.bdf3.dorado.jpa;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Field通用工具类。
 * 
 *@author Kevin.yang
 *@since 2015年5月24日
 */
public class FieldUtils {

	
	public static List<Field> getFields(Class<?> clazz){
		if (clazz == null) {
	        throw new IllegalArgumentException("The class must not be null");
	    }
		List<Field> fieldList=new ArrayList<Field>();
		while(clazz!=null){
			Field[] fields=clazz.getDeclaredFields();
			for(Field field:fields){
				fieldList.add(field);
			}
			clazz=clazz.getSuperclass();
		}
		return fieldList;
	}
	
	public static Field getField(Class<?> clazz,String name){
		String[] ps = name.split("\\.");
		Class<?> cls = clazz;
		Field field = null;
		for (int i = 0; i < ps.length; i++) {
			field = doGetField(cls, ps[i]);
			cls = field.getType();
		}
		
		return field;
	}
	
	private static Field doGetField(Class<?> clazz,String name){
		while(clazz!=null){
			Field[] fields=clazz.getDeclaredFields();
			for(Field field:fields){
				if(field.getName().equals(name)){
					return field;
				}
			}
			clazz=clazz.getSuperclass();
		}
		return null;
	}
			
}
