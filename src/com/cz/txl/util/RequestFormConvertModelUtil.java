package com.cz.txl.util;

import java.lang.reflect.Field;

import javax.servlet.http.HttpServletRequest;

import com.cz.txl.model.User;

public class RequestFormConvertModelUtil {
	/**
	 * 解析表单并转换成对象
	 * @param request
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static <T> T parseRequestToEntity(HttpServletRequest request, Class<T> clazz) throws Exception{
		T entity = clazz.newInstance();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			String fieldName = field.getName();
			String fieldType = field.getType().getName();
			String value = request.getParameter(fieldName);

			if(value == null || value.trim().length() == 0){
//				if(isFile(fieldName, request)){//是上传文件，并且不为空
//					//如果是上传文件，保存并返回可直接访问的html路径
//					field.set(entity, saveFile(request, request.getPart(fieldName)));
//				}
				continue;
			}
			if(fieldType.equalsIgnoreCase("java.lang.String")){
				field.set(entity, value);
			}else if(fieldType.equalsIgnoreCase("byte")){
				field.setByte(entity, Byte.parseByte(value));
			}else if(fieldType.equalsIgnoreCase("short")){
				field.setShort(entity, Short.parseShort(value));
			}else if(fieldType.equalsIgnoreCase("int")){
				field.setInt(entity, Integer.parseInt(value));
			}else if(fieldType.equalsIgnoreCase("long")){
				field.setLong(entity, Long.parseLong(value));
			}else if(fieldType.equalsIgnoreCase("float")){
				field.setFloat(entity, Float.parseFloat(value));
			}else if(fieldType.equalsIgnoreCase("double")){
				field.setDouble(entity, Double.parseDouble(value));
			}else if(fieldType.equalsIgnoreCase("boolean")){
				field.setBoolean(entity, Boolean.parseBoolean(value));
			}else{
				field.set(entity, value);
			}
		}
		return entity;
	}


	public static void main(String[] args) {
		HttpServletRequest request=null;
		try {
			User user=RequestFormConvertModelUtil.parseRequestToEntity(request, User.class);
			System.out.println(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
