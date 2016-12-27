package com.cz.txl.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.cz.txl.model.QueryCondtion;
import com.cz.txl.type.QueryConditionType;

public class RequestToQueryCondtionUtil {
	/**
	 * 解析表单动态构造查询条件列表
	 * @param request
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static  <T> List<QueryCondtion>  parseRequestToQueryCondtion
	(HttpServletRequest request, Class<T> clazz) throws Exception{

		List<QueryCondtion> qcList=new ArrayList<>();

		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			String fieldName = field.getName();
			String fieldType = field.getType().getName();
			String value = request.getParameter(fieldName);

			if(value == null || value.trim().length() == 0){
				continue;
			}

			QueryCondtion qc=new QueryCondtion(fieldName, value);

			if(fieldType.equalsIgnoreCase("java.lang.String")){
				qc.setConditionType(QueryConditionType.Like);
			}else if(fieldType.equalsIgnoreCase("int")){
				qc.setConditionType(QueryConditionType.Equals);
			}
			qcList.add(qc);

		}
		return qcList;
	}



}
