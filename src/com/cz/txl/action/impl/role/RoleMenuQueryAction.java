package com.cz.txl.action.impl.role;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cz.txl.action.Action;
import com.cz.txl.dao.DaoFactory;
import com.cz.txl.dao.menu.MenuDAO;
import com.cz.txl.dao.role.RoleDAO;
import com.cz.txl.model.Menu;
import com.cz.txl.model.MenuTree;
import com.cz.txl.util.ResponseUtil;

import net.sf.json.JSONArray;

//根据传入的角色id,
//动态生成该角色所对应的权限列表树形菜单
public class RoleMenuQueryAction implements Action {
	Log log = LogFactory.getLog(RoleMenuQueryAction.class);
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		//第一步,获得传入的角色id
		String roleid = request.getParameter("id");
		//查询对应id的角色所具备的菜单权限列表
		RoleDAO roleDao = DaoFactory.getRoleDao();
		List<Integer> menuNos = null;
		try {
			menuNos = (List<Integer>)roleDao.queryMenus(
					Integer.parseInt(roleid));
		} catch (Exception e) {
			log.error("查询权限列表异常"+e.getMessage());
		}
//		System.out.println(menuNos+"--------=====");

		//第二步,查询目前系统中的所有菜单形成一个list
		MenuDAO menuDao = DaoFactory.getMenuDao();
		List<Menu> menuList = null;

		try {
			menuList = menuDao.queryAll();
		} catch (Exception e) {
			log.error("查询所有菜单列表出错"+e.getMessage());
		}


//		JSONObject result = new JSONObject();

		List<MenuTree> menuTreeList = new ArrayList<MenuTree>();

		//将menuList 按照tree-datajson格式进行转换为menuTreeList
//		主要转换父子关系
		//找所有权限
		MenuTree mt_zuzong = new MenuTree();

		for (int i = 0;i<menuList.size();i++) {
			Menu temp = menuList.get(i);
			//==1 表示是所有权限这个根节点
			if (temp.getMenuNo() == 1) {
				mt_zuzong.setId(temp.getMenuNo());
				mt_zuzong.setText(temp.getMenuName());
				//判断根节点也就是所有权限,是否在当前
				//要编辑得这个角色的权限列表中
				if (menuNos.contains(temp.getMenuNo())) {
					mt_zuzong.setChecked(true);
				}

				mt_zuzong.setState("open");
				break;
			}
		}

		//找一级菜单列表
		List<MenuTree> menuTreeList_Die = new ArrayList<MenuTree>();

		for (int i = 0;i<menuList.size();i++) {
			Menu temp = menuList.get(i);
			if (temp.getParentId() == 0 && temp.getMenuNo()!=1) {
				MenuTree mt_die = new MenuTree();
				mt_die.setId(temp.getMenuNo());
				//如果二级菜单编号在当前要修改的这个角色的菜单列表中
				//设值为选中
				if (menuNos.contains(temp.getMenuNo())) {
					mt_die.setChecked(true);
				}

				mt_die.setText(temp.getMenuName());
				mt_die.setState("open");
				menuTreeList_Die.add(mt_die);
			}
		}
		//将一级菜单列表设值为根节点的子项
		mt_zuzong.setChildren(menuTreeList_Die);

		//找孙子,构造第三级菜单
		for (int i = 0;i<menuTreeList_Die.size();i++) {

			MenuTree mt_die = menuTreeList_Die.get(i);
			//tempList就是三级节点
			List<MenuTree> tempList = new ArrayList<>();
			for (int j = 0;j<menuList.size();j++) {
				Menu menu_sun = menuList.get(j);
				if (mt_die.getId() == menu_sun.getParentId()) {
					MenuTree mt_sun = new MenuTree();
					mt_sun.setId(menu_sun.getMenuNo());
					mt_sun.setText(menu_sun.getMenuName());

					//如果三级菜单在当前要修改的这个角色的菜单列表中
					//则设值为选中
					if (menuNos.contains(menu_sun.getMenuNo())) {
						mt_sun.setChecked(true);
					}

					tempList.add(mt_sun);
				}
			}

			//对三级节点tempList开始查找其四级节点
			for (int k=0;k<tempList.size();k++) {
				MenuTree mt_sun = tempList.get(k);

				List<MenuTree> siji = new ArrayList<>();
				for (int j = 0;j<menuList.size();j++) {
					Menu menu_chongsun = menuList.get(j);
					if (mt_sun.getId() == menu_chongsun.getParentId()) {
						MenuTree mt_chongsun = new MenuTree();
						mt_chongsun.setId(menu_chongsun.getMenuNo());
						mt_chongsun.setText(menu_chongsun.getMenuName());

						//如果四级菜单在当前要修改的这个角色的菜单列表中
						//则设值为选中
						if (menuNos.contains(menu_chongsun.getMenuNo())) {
							mt_chongsun.setChecked(true);
						}

						siji.add(mt_chongsun);
					}
				}
				//给每一个三级菜单设值子项
				mt_sun.setChildren(siji);
			}
			//给每一个二级菜单设置子项
			mt_die.setChildren(tempList);
		}

		//将根节点添加到menuTreeList中
		menuTreeList.add(mt_zuzong);

		JSONArray jsonArray = JSONArray.fromObject(menuTreeList);
		System.out.println(jsonArray);
		//直接将json数据输出
		try {
			ResponseUtil.write(response, jsonArray);
		} catch (Exception e) {
			log.error("数据输出异常");
		}

		return null;
	}

}
