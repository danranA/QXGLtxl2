package com.cz.txl.action.impl.schedule;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cz.txl.action.Action;

//跳转行程计划管理Action
public class ScheduleManageAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		return "scheduleManage.jsp";
	}

}
