package com.cz.txl.dao;

import com.cz.txl.db.JdbcUtil;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import junit.framework.TestCase;

public class DaoTest extends TestCase{
	
	private ComboPooledDataSource dataSource;
	
    @Override
	protected void setUp() throws Exception {
		super.setUp();
		dataSource = JdbcUtil.getDataSource();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		dataSource.close();
	}
}
