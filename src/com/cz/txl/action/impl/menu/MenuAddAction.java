package com.cz.txl.action.impl.menu;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cz.txl.action.Action;
import com.cz.txl.dao.DaoFactory;
import com.cz.txl.dao.menu.MenuDAO;
import com.cz.txl.model.Menu;
import com.cz.txl.util.RequestFormConvertModelUtil;

import net.sf.json.JSONObject;

public class MenuAddAction implements Action{
	Log log = LogFactory.getLog(MenuAddAction.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		//1,取出request中的表单,然后将表单自动转化为需要插入的对象
		Menu menu = null;
		try {
			menu = RequestFormConvertModelUtil.parseRequestToEntity(request, Menu.class);
		} catch (Exception e) {
			log.error("类型转换异常>>>>>>>"+e.getMessage());
		}

		//2,从daoFactory中得到MenuDao
		MenuDAO menuDao = DaoFactory.getMenuDao();

		//构造需要前台返回的json信息
		JSONObject result = new JSONObject();

		try {
			menuDao.insert(menu);
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
