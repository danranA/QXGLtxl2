package com.cz.txl.dao;

import java.util.List;

import com.cz.txl.dao.menu.MenuDAO;
import com.cz.txl.dao.menu.impl.MenuDaoImpl;
import com.cz.txl.model.Menu;

public class MenuDaoTest extends DaoTest{

	MenuDAO menuDao = new MenuDaoImpl();

	public void testInsert(){
		Menu menu = new Menu(1,"新增菜单",0,"#",1,"无");
		try {
			menuDao.insert(menu);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void testQueryAll() throws Exception{
		List<Menu> menuList = menuDao.queryAll();

		System.out.println(menuList);
	}

	public void testQueryTotal() throws Exception {
		int count = menuDao.queryTotal();
		System.out.println(count);
		assert(count>0);
	}

	public void testQueryPage() throws Exception{
		List<Menu> menuList = menuDao.queryPage(0, 10);
		assertEquals(menuList.size(), 10);
	}

}
