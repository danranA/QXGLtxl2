package com.cz.txl.action.impl.menu;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cz.txl.action.Action;

public class MenuManageAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		return "menuManage.jsp";
	}

}
