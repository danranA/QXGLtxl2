package com.cz.txl.dao;

import com.cz.txl.dao.user.UserDAO;
import com.cz.txl.dao.user.impl.UserDaoImpl;
import com.cz.txl.model.User;

public class UserDaoTest extends DaoTest{

	public void testQueryByName() throws Exception{
		String username = "admin";
		
		UserDAO userDao = new UserDaoImpl();
		
		User user = userDao.queryByName(username);
		
		assertEquals(username, user.getUsername());
		
	}
	
}
