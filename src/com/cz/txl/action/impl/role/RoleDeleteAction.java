package com.cz.txl.action.impl.role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cz.txl.action.Action;
import com.cz.txl.dao.DaoFactory;
import com.cz.txl.dao.role.RoleDAO;
import com.cz.txl.util.ResponseUtil;

import net.sf.json.JSONObject;

public class RoleDeleteAction implements Action {
	Log log = LogFactory.getLog(RoleDeleteAction.class);
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {

		//获得前台传入的需要删除数据的id列表
		String ids = request.getParameter("ids");

		String[] idArray = ids.split(",");//将前台传入的用","号分隔的字符串转换为id数组

		//从DaoFactory 获得roleDao
		RoleDAO roleDao = DaoFactory.getRoleDao();

		//构造要返回给前台的json数据
		JSONObject result = new JSONObject();
		try {
			roleDao.deleteBatch(idArray);
			result.put("success", true);
		} catch (Exception e) {
			result.put("success", false);
			log.error("批量删除异常"+e.getMessage());
		}

		//调用统一输出工具将结果返回到前台
		try {
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			log.error("输出结果异常"+e.getMessage());
		}

		return null;
	}

}
