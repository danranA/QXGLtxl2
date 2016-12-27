package com.cz.txl.common;

import java.util.HashMap;
import java.util.Map;

//放置静态常量,以供全局使用
public final class Constants {
	public static final String PACKAGE_PATH = "com.cz.txl.action.impl.";
	//超级管理员的角色编号
	public static final int SUPER_ADMIN_ROLE_NO =1;
	public static final String SESSEION_USER = "user";
	//类中属性名与表中字段名的映射
	public static final Map<String,String> fieldTocolumMap = new HashMap<>();

	static{
		fieldTocolumMap.put("userStatus", "user_status");
		fieldTocolumMap.put("menuName", "menu_name");
		fieldTocolumMap.put("menuNo", "menu_no");
		fieldTocolumMap.put("menuDesc", "menu_desc");
	}
}
