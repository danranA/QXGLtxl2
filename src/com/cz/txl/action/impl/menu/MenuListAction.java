package com.cz.txl.action.impl.menu;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cz.txl.action.Action;
import com.cz.txl.dao.DaoFactory;
import com.cz.txl.dao.menu.MenuDAO;
import com.cz.txl.model.Menu;
import com.cz.txl.model.PageBean;
import com.cz.txl.util.RequestFormConvertModelUtil;
import com.cz.txl.util.ResponseUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

//这个Action是用来进行返回menu列表的
public class MenuListAction implements Action{
	Log log = LogFactory.getLog(MenuListAction.class);
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		//从request中获得分页的参数
		PageBean pageBean = new PageBean(1,10);//分页的默认设置,从第一开始,每页十行
		try {
			pageBean = RequestFormConvertModelUtil.parseRequestToEntity(request, PageBean.class);
		} catch (Exception e) {
			log.error("转换生成pageBean时异常"+e.getMessage());
		}

		//调用menuDao 获取menuList
		MenuDAO menuDao = DaoFactory.getMenuDao();

		//声明一个长度为0的list防止前台转换时因为空指针抛出异常
		List<Menu> menuList = new ArrayList<Menu>();
		try {
			menuList = menuDao.queryPage(pageBean.getStart(), pageBean.getRows());
		} catch (Exception e) {
			log.error("获取菜单列表异常>>>>>>>"+e.getMessage());
		}

		//查询出总记录数
		int total = 0;
		try {
			total = menuDao.queryTotal();
		} catch (Exception e) {
			log.error("获取总记录数异常>>>>>>>"+e.getMessage());
		}

		//构造要发送到前段的json格式数据
		JSONObject result = new JSONObject();
		JSONArray  jsonArray = JSONArray.fromObject(menuList);
		result.put("rows", jsonArray);//前段会自动从result中读出rows显示成列表
		result.put("total", total);//将总记录数传递到前台页面
		//调用输出工具类进行统一输出
		try {
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			log.error("向页面输出结果时发生异常:"+e.getMessage());
		}
		return null;
	}

}
