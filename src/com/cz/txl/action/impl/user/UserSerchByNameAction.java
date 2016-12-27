package com.cz.txl.action.impl.user;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cz.txl.action.Action;
import com.cz.txl.dao.DaoFactory;
import com.cz.txl.dao.user.UserDAO;
import com.cz.txl.model.PageBean;
import com.cz.txl.model.User;
import com.cz.txl.util.RequestFormConvertModelUtil;
import com.cz.txl.util.ResponseUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class UserSerchByNameAction implements Action {
	Log log = LogFactory.getLog(UserSerchByNameAction.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String username = request.getParameter("username");
		System.out.println(username);

		PageBean pageBean = new PageBean(1,10);//分页的默认设置,从第一开始,每页十行
		try {
			pageBean = RequestFormConvertModelUtil.parseRequestToEntity(request, PageBean.class);
		} catch (Exception e) {
			log.error("转换生成pageBean时异常"+e.getMessage());
		}

		//调用userDao 获取userList
		UserDAO userDao = DaoFactory.getUserDao();

		//声明一个长度为0的list防止前台转换时因为空指针抛出异常
		List<User> userList = new ArrayList<User>();
		try {
			String[] fieldList={"username"};
			String[] fieldValue={username};
			userList = userDao.queryPage(fieldList,fieldValue,pageBean.getStart(), pageBean.getRows());
		} catch (Exception e) {
			log.error("获取菜单列表异常>>>>>>>"+e.getMessage());
		}

		//查询出总记录数
		int total = 0;
		try {
			total = userDao.queryTotal();
		} catch (Exception e) {
			log.error("获取总记录数异常>>>>>>>"+e.getMessage());
		}

		//构造要发送到前段的json格式数据
		JSONObject result = new JSONObject();
		JSONArray  jsonArray = JSONArray.fromObject(userList);
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
