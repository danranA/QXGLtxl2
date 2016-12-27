package com.cz.txl.action.impl.user;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cz.txl.action.Action;
import com.cz.txl.common.Constants;
import com.cz.txl.dao.DaoFactory;
import com.cz.txl.dao.role.RoleDAO;
import com.cz.txl.dao.user.UserDAO;
import com.cz.txl.model.Menu;
import com.cz.txl.model.Role;
import com.cz.txl.model.Schedule;
import com.cz.txl.model.User;
import com.cz.txl.util.CheckLoginUtil;
import com.cz.txl.util.DateUtil;
import com.cz.txl.util.EncryptUtil;

/**
 * 处理用户登录的action类
 * @author Administrator
 *
 */
public class LoginAction implements Action {
	Log log = LogFactory.getLog(LoginAction.class);
	@Override
	public String execute(HttpServletRequest request,
						  HttpServletResponse response) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String imageCode = request.getParameter("imageCode");

		//为了防止有人绕过前台的js检验进行恶意提交
		//首先判断是否是已登录用户发送的请求,如果是,直接forward至main.jsp
		if (CheckLoginUtil.isLogin(request)) {
			return "main.jsp";//跳转到管理主页面
		}else if (null == imageCode || "".equals(imageCode)) {
			request.setAttribute("errorMsg", "验证码不能为空");
			return "login.jsp";//验证码为空返回到登录页面
		}  else {
			//从session中获得验证码,并与用户上传的验证码进行比较
			String sessionCode =
					(String)request.getSession().getAttribute("imageCode");
			if (!imageCode.equals(sessionCode)) {
				request.setAttribute("errorMsg", "验证码填写错误,请重新输入");
				return "login.jsp";//验证码错误,返回到登录页面重新登录s
			} else {
				//根据用户名从数据库中查询User,然后根据获得user与用户上传的password进行比较
				UserDAO userDao = DaoFactory.getUserDao();

				try {
					User user = userDao.queryByName(username);

					if (user == null) {
						request.setAttribute("errorMsg", "用户不存在");
						return "login.jsp";//返回到登录页面
					} else {
						//1,将用户传入的明文密码进行加密
						String encryptPass = EncryptUtil.encryptMd5(password);
						//2,比较加密后的密码与数据库中存储的密码是否一致
						if (encryptPass.equals(user.getPassword())) {
							//查询用户的角色及权限信息
							//根据用户id查询角色编号
							int roleNo = userDao.getRoleNo(user.getId());
							//根据角色编号,查询角色
							RoleDAO roleDao = DaoFactory.getRoleDao();
							Role role = roleDao.queryByNo(roleNo);
							//根据角色id,查询角色与菜单的关联表,得到menu_no,
							//进而根据menu_no 获得menu列表
							List<Menu> menuList = roleDao.queryMenusByRoleId(role.getId());

							//设置当前登录用户对应的角色的功能列表
							role.setMenuList(menuList);
							user.setRole(role);//设值当前登录用户的角色
							//1,先把登录成功的用户信息(user)设置到session中
							request.getSession().setAttribute(Constants.SESSEION_USER, user);

							//根据登录用户的userId 查询t_user_schedule
							//收集要显示在首页上的信息,set到request中
							List<Schedule> schList = userDao.queryByUserId(user.getId());

							//通过后台修改数据苦库中保存的毫秒日程时间为"YYYY-mm-dd"
							for (int i = 0;i<schList.size();i++) {
								Date date = new Date(Long.parseLong(schList.get(i).getSchTime()));
								schList.get(i).setSchTime(DateUtil.getDateDDMMYYYY(date));
							}

							request.setAttribute("schList", schList);
							return "main.jsp";//跳转到管理主页面
						} else {
							request.setAttribute("errorMsg", "密码错误");
							return "login.jsp";//跳转到登录界面
						}
					}

				} catch (Exception e) {
					log.error("查询用户异常:"+e.getMessage());
				}

			}
		}

		return null;
	}

}
