package com.cz.txl.action.impl.user;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cz.txl.action.Action;
import com.cz.txl.common.Constants;
import com.cz.txl.dao.DaoFactory;
import com.cz.txl.dao.user.UserDAO;
import com.cz.txl.model.User;
import com.cz.txl.task.SendTask;
import com.cz.txl.task.threadpool.ExecutorPool;
import com.cz.txl.util.EncryptUtil;
import com.cz.txl.util.RequestFormConvertModelUtil;

import net.sf.json.JSONObject;

public class UserRegAction implements Action {
	Log log = LogFactory.getLog(UserRegAction.class);
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

			user.setUserStatus(2);//新注册的用户状态默认是2,审核

			//返回新插入数据的id
			int userId = userDao.reg(user);

			//新增用户成功后,在用户与角色关联表中,执行
			//1,先解除原来的用户-角色关系
			//新增的时候因为用户没有角色,所以不用删除
			//userDao.deleteUserRole(userId);

			result.put("success", true);//这个success就是前台需要接受的信息

			// 向管理员发送一封提醒邮件,告知其需要审核新用户
			//1、向新注册的用户发送一封邮件
			ExecutorPool pool=ExecutorPool.getInstance();
			SendTask task=new SendTask(user.getEmail(), "通讯录管理系统注册成功消息，请勿回复！", "恭喜你，成功注册通讯录管理系统，你的用户名是:"+user.getUsername()+"\n您的密码是:"+user.getPassword());
			pool.addTask(task);
			//2、向管理员发送一封邮件，提醒管理员有新用户来了
			List<User> adminUserList=userDao.queryAllAdmin(Constants.SUPER_ADMIN_ROLE_NO);//根据角色查询管理员列表
			for (int i = 0; i < adminUserList.size(); i++) {
				User adminUser = adminUserList.get(i);
				SendTask tiptask=new SendTask(adminUser.getEmail(), "新用户注册通知", "有新用户:"+user.getUsername()+"注册，请您及时审核!!");
				pool.addTask(tiptask);
			}
			//TODO 向管理员发送一个消息，告知其有新用户注册
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
