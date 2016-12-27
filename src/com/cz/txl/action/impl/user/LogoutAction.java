package com.cz.txl.action.impl.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cz.txl.action.Action;

public class LogoutAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {

		//注销的动作
		//从session中清除已经登录的用户.如果存在的话
		request.getSession().removeAttribute("user");
		return "login.jsp";
	}

}
