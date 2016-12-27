package com.cz.txl.action.impl.role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cz.txl.action.Action;

public class RoleManageAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		return "roleManage.jsp";
	}
	
}
