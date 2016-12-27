package com.cz.txl.dao;

import com.cz.txl.dao.menu.MenuDAO;
import com.cz.txl.dao.menu.impl.MenuDaoImpl;
import com.cz.txl.dao.role.RoleDAO;
import com.cz.txl.dao.role.impl.RoleDaoImpl;
import com.cz.txl.dao.schedule.ScheduleDAO;
import com.cz.txl.dao.schedule.impl.ScheduleDaoImpl;
import com.cz.txl.dao.user.UserDAO;
import com.cz.txl.dao.user.impl.UserDaoImpl;

//dao工厂,用来生产所需要的各个dao
public class DaoFactory {

	//静态方法,返回UserDao接口的某个实现类的实例对象
	public static UserDAO getUserDao(){
		return new UserDaoImpl();
	}

	public static MenuDAO getMenuDao(){
		return new MenuDaoImpl();
	}
	public static RoleDAO getRoleDao(){
		return new RoleDaoImpl();
	}
	public static ScheduleDAO getScheduleDao(){
		return new ScheduleDaoImpl();
	}
}

