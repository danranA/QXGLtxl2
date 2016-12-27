package com.cz.txl.util;

import javax.servlet.http.HttpServletRequest;

public class CheckLoginUtil {
	public static boolean isLogin(HttpServletRequest request){
		boolean login = false;
		
		login = request.getSession().getAttribute("user") != null ? true : false;
		
		return login;
	}
}
