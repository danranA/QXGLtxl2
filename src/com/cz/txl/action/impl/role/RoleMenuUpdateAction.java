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

public class RoleMenuUpdateAction implements Action {
	Log log = LogFactory.getLog(RoleMenuUpdateAction.class);
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		//得到勾选的权限列表id
		String menuIds = request.getParameter("menuIds");

		//得到要修改的角色的id
		String roleId = request.getParameter("id");

		//构造要返回给前台的结果,JSONObject 可以产生json对象
		JSONObject result = new JSONObject();
		///1,在t_role_menu 关联表中,根据roleId删除该角色所关联的所有权限
		RoleDAO roleDao = DaoFactory.getRoleDao();
		try {
			roleDao.deleteRoleMenu(Integer.parseInt(roleId));
			//2,根据新的权限列表,重新插入新数据
			String[] ids = menuIds.split(",");
			roleDao.addRoleMenuBatch(Integer.parseInt(roleId), ids);
			result.put("success", true);
		} catch (Exception e) {
			log.error("编辑角色菜单关联表异常"+e.getMessage());
		}
		try {
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			log.error("数据输出异常");
		}
		return null;
	}

}
