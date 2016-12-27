package com.cz.txl.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cz.txl.common.Constants;
import com.cz.txl.model.Menu;
import com.cz.txl.model.User;
import com.cz.txl.util.CheckLoginUtil;

public class SecurityFilter implements Filter {
	Log log = LogFactory.getLog(SecurityFilter.class);
	//不需验证权限的Action,免检列表
	final String[] needNotCheckDo = {"LoginAction","imageCode","LogoutAction","UserRegCheckAction",
			"UserRegAction"};
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		HttpServletResponse httpServletResponse = (HttpServletResponse)response;

		//在这个过滤器里面我们规定所有用户不能直接通过请求.jsp来进行对页面的访问,这样就增加了安全性
		//为了过滤掉除了login.jsp及error.jsp之外的请求,首先需要判断用户发送的请求路径
		String requestPath = httpServletRequest.getServletPath();
		log.info("过滤器接收到了用户请求:"+requestPath);
		//如果你的请求路径是localhost:8080/txl/login.jsp 那么上面返回的是login.jsp

		//还有一个方法是httpServletRequest.getRequestURI();这个返回的结果是txl/login.jsp
		//这里我们使用getServletPath();方法
		//*******************第一级校验Start,防止用户直接通过.jsp访问到页面******************
		//如果用户请求的路径不是null,并且是以.jsp结尾
		if (requestPath != null && requestPath.endsWith(".jsp")) {
			int index = requestPath.indexOf(".");//得到"."在该字符串的位置
			String requestName = requestPath.substring(1,index);//截取字符串去掉.及后面的jsp
			//如果不是login也不是error
			if (!(requestName.equals("login")||requestName.equals("error")||requestName.equals("index"))) {
				if (CheckLoginUtil.isLogin(httpServletRequest)) {
					//将用户要访问的.jsp页面做一个映射
					//比如已登录用户访问main.jsp 那么就让其forward到LoginAction.do
					//当已登录用户二次跳转到LoginAction.do 的时候.可以不用进行验证码的判断
					switch(requestName){
						case "main":
							httpServletResponse.sendRedirect("/user/LoginAction.do");
							break;
						case "userManage":
							httpServletRequest.getRequestDispatcher("/user/UserManageAction.do")
									.forward(httpServletRequest, httpServletResponse);
							break;
					}
					//如果访问userManage.jsp 那么让其forwar到UserManageAction.do
				} else{
					//如果目录下没有默认的显示页面,例如:index.jsp等.则用户会看到404页面
					//httpServletRequest.setAttribute("errorMsg", "无效请求");
					httpServletResponse.sendRedirect("404.html");
					return;
				}
			}
		}
		//*******************第一级校验END******************



		//*******************第二级校验Start,对用户的Action请求做校验******************
		if (requestPath.endsWith(".do")){
			//如果用户发送的是.do 的action请求,例如:AdduserAction等.需要进行权限判断
			//所谓权限判断,即判断用户所有请求的业务Action是否在其权限列表中
			boolean flag = false;//默认表示用户不具备该权限


			//分别获得pathName 和 actionName
			String pathName = httpServletRequest.getServletPath();
			if (pathName.contains("imageCode")) {
				flag = true;
			} else {
				int theSecondXie = pathName.lastIndexOf("/");
				int index = pathName.indexOf(".");
				String actionName = pathName.substring(theSecondXie+1,index);
				//actionName 就是用户要请求的某个实现了Action接口的实现类
				//这个名称与数据库中t_menu表的action列一一对应

				//***********************===========*************************
				//1,如果是LoginAction.do等无需拦截的 请求 无需判断用户是否登录即可放行
				//判断actionName是否在needNotCheckDo数组中
				if (Arrays.asList(needNotCheckDo).contains(actionName)) {
					flag = true;
				} else {//如果请求的Action不在免检列表
					//2,如果不是LoginAction.do 需要对用户的操作权限进行判断
					//2.1首先判断用户是否登录,如果没有登录,跳转到登录页面并发送errormsg,需要登录才能访问
					User user = null;
					if (CheckLoginUtil.isLogin(httpServletRequest)) {
						user = (User)httpServletRequest.getSession().getAttribute("user");
					} else {//如果用户未登录
						//用户登录后,其信息保存在session中,session是一个有时间有效期的存在于后台
						//应用服务器内存中的一个变量.如果已登录用户在前台长时间未进行操作(没有在网页上执行
						//任何与后台的交互).后台应用服务器因为内存有限,所以会定时清除session里面保存的
						//长时间没有活动的用户信息.
						//因此,前台的用户虽然还停留在登录成功的页面,但是后台已经将session中保存的该用户信息注销.
						//对于这样的用户,当期点击某些菜单时,我们就提醒它登录已失效,重新登录.
						//TODO 前台效果应该写成弹出一个小窗口(让用户填写用户名密码,然后提交,登录成功后,可以继续其
						//之前的访问)
						//,而不是跳转到login.jsp进行重新登录.
						//如果是Ajax操作请求的过程中发生了session过期,则弹框提示用户重新登录
						String requestType = httpServletRequest.getHeader("X-Requested-With");

						if (requestType != null &&
								"XMLHttpRequest".equalsIgnoreCase(requestType)) {
							httpServletResponse.setStatus(911);//session超时设置状态码911
							httpServletResponse.setHeader("sessionstatus", "timeout");
							System.out.println("过期了=======");
							//						httpServletResponse.addHeader("loginPath", "");
							return;
						} else {
							httpServletRequest.setAttribute("errorMsg", "您还没有登录,请先登录!");
							httpServletRequest.getRequestDispatcher("login.jsp").
									forward(httpServletRequest, httpServletResponse);
							return;
						}
					}

					//对超级管理员的所有请求进行放行
					if(user.getRole().getRoleNo() == Constants.SUPER_ADMIN_ROLE_NO){
						flag=true;
					}else{

						//下面的逻辑是基于用户已登录
						//获得当前已登录用户的功能列表
						List<Menu> menuList = user.getRole().getMenuList();
						//System.out.println(menuList);
						//2.2 如果是已经登录,则判断用户的权限列表里是否包含所请求的Action,如果已经包含,放行
						for (Menu menu : menuList) {
							if (actionName.equals(menu.getAction())) {
								flag = true;//表示用户具备对该请求的权限
								break;
							}
						}
					}
				}
			}
//	        System.out.println("&&&&&"+flag);
			//***********************===========*************************
			//如果用户的权限列表中没有包含当前其所请求的这个Action,告知用户其不具备该权限
			if (!flag) {
				//2.2.1 如果不包含,返回错误页面,并发送errormsg,告知用户没有此权限.(或者为了安全,不想让
				//不法分子知道你的应用有这个功能,直接跳转到404页面)
				httpServletRequest.setAttribute("errorMsg", "您没有此权限");
				//重定向到根目录,如果目录下没有默认的显示页面,例如:index.jsp等.则用户会看到404页面
				httpServletRequest.getRequestDispatcher("error.jsp").forward(httpServletRequest, httpServletResponse);
				return;
			}
		}
		//*******************第二级校验END******************

		//如果经过前面的两级校验能通过,则继续执行
		chain.doFilter(request, response);//请求向后传递
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
