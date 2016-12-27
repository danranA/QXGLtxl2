package com.cz.txl.action.impl.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cz.txl.action.Action;

public class UserManageAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		return "userManage.jsp";
	}
	
}
