package com.cz.txl.action.impl.user;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cz.txl.action.Action;
import com.cz.txl.dao.DaoFactory;
import com.cz.txl.dao.role.RoleDAO;
import com.cz.txl.dao.user.UserDAO;
import com.cz.txl.model.ComboBoxData;
import com.cz.txl.model.Role;
import com.cz.txl.util.ResponseUtil;

import net.sf.json.JSONArray;

public class UserRoleQueryAction implements Action{
	Log log = LogFactory.getLog(UserRoleQueryAction.class);
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {

		RoleDAO roleDao = DaoFactory.getRoleDao();
		try {
			List<Role> roleList = roleDao.queryAll();

			int roleNo = 0;
			//判断是否request中有参数id
			if (null != request.getParameter("id")) {
				int userId = Integer.parseInt(request.getParameter("id"));
				//根据userId到t_user_role表中查询出要修改的用户当前的角色
				UserDAO userDao = DaoFactory.getUserDao();
				roleNo = userDao.getRoleNo(userId);
			}
			System.out.println("===="+roleNo);
			List<ComboBoxData> cbdList = new ArrayList<ComboBoxData>();
			cbdList.add(new ComboBoxData(0, null, "--请选择角色--", true));
			for (int i = 0;i<roleList.size();i++) {
				ComboBoxData cbd = new ComboBoxData();
				Role role = roleList.get(i);
				cbd.setId(role.getRoleNo());
				cbd.setText(role.getRoleName());
				//需要根据用户的当前角色来判定是否是选中
				if (roleNo>0 && role.getRoleNo() == roleNo) {
					cbd.setSelected(true);
				}
				cbdList.add(cbd);
			}

			JSONArray result = JSONArray.fromObject(cbdList);
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			log.error("发生异常"+e.getMessage());
		}

		return null;
	}

}
