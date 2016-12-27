package com.cz.txl.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action 接口，所有的业务action类都需要实现此接口
 * execute方法返回view资源的引用字符串，比如“admin/login.jsp”
 * 所谓的业务action是指,对业务的复杂处理计算及对表的增删改查
 * eg:登录业务  由LoginAction类里处理
 * 注册业务  由RegisteAction类处理
 * 新增用户 由AddUserAction类处理
 * 修改用户 由UpdateUserAction 类处理
 * ...
 */
public interface Action {
	public String execute(HttpServletRequest request, HttpServletResponse response);
}
