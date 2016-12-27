package com.cz.txl.action.impl.user;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cz.txl.action.Action;
import com.cz.txl.dao.DaoFactory;
import com.cz.txl.dao.user.UserDAO;
import com.cz.txl.model.User;
import com.cz.txl.util.EncryptUtil;
import com.cz.txl.util.RequestFormConvertModelUtil;

import net.sf.json.JSONObject;

public class UserAddAction implements Action{
	Log log = LogFactory.getLog(UserAddAction.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		//1,取出request中的表单,然后将表单自动转化为需要插入的对象
		User user = null;
		try {
			user = RequestFormConvertModelUtil.parseRequestToEntity(request, User.class);
		} catch (Exception e) {
			log.error("类型转换异常>>>>>>>"+e.getMessage());
		}

		//2,从daoFactory中得到UserDao
		UserDAO userDao = DaoFactory.getUserDao();

		//构造需要前台返回的json信息
		JSONObject result = new JSONObject();

		try {
			//向数据库中添加用户前,需要对其密码进行加密
			user.setPassword(EncryptUtil.encryptMd5(user.getPassword()));

			//返回新插入数据的id
			int userId = userDao.insert(user);

			//新增用户成功后,在用户与角色关联表中,执行
			//1,先解除原来的用户-角色关系
			//新增的时候因为用户没有角色,所以不用删除
			//userDao.deleteUserRole(userId);

			//2,建立新的用户角色关系
			String roleId = request.getParameter("roleno");
			int roleNo = Integer.parseInt(roleId);
			System.out.println(userId+"-=======");;
			userDao.addUserRole(userId, roleNo);
			//如果插入成功,需要向前台返回成功的消息
			result.put("success", true);//这个success就是前台需要接受的信息
		} catch (Exception e) {
			log.error("执行插入异常======"+e.getMessage());
		}

		try {
			response.getWriter().print(result.toString());

		} catch (IOException e) {
			log.error("输出异常......."+e.getMessage());
		}

		return null;
	}

}
