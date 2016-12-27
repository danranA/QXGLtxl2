package com.cz.txl.db;

import java.sql.Connection;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import junit.framework.TestCase;

public class JdbcUtilTest extends TestCase{

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
