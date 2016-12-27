package com.cz.txl.action.impl.role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cz.txl.action.Action;
import com.cz.txl.dao.DaoFactory;
import com.cz.txl.dao.role.RoleDAO;
import com.cz.txl.model.Role;
import com.cz.txl.util.RequestFormConvertModelUtil;
import com.cz.txl.util.ResponseUtil;

import net.sf.json.JSONObject;

public class RoleUpdateAction implements Action {
	Log log = LogFactory.getLog(RoleUpdateAction.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {

		//根据request转换生成要保存的role
		Role role = null;
		try {
			role = RequestFormConvertModelUtil.parseRequestToEntity(request, Role.class);
		} catch (Exception e) {
			log.error("类型转换异常"+e.getMessage());
		}

		//从DaoFactory获得roledao
		RoleDAO roleDao = DaoFactory.getRoleDao();

		//执行update方法

		//构造要返回给前台的结果,JSONObject 可以产生json对象
		JSONObject result = new JSONObject();
		try {
			roleDao.update(role);
			result.put("success", true);
		} catch (Exception e) {
			result.put("success", false);
			log.error("修改角色异常"+e.getMessage());
		}

		//调用统一输出对象将结果输出
		try {
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			log.error("输出结果异常:"+e.getMessage());
		}

		return null;
	}

}
