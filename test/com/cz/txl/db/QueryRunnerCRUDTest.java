package com.cz.txl.db;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.cz.txl.model.User;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import junit.framework.TestCase;

public class QueryRunnerCRUDTest extends TestCase{

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

    public void testAdd() throws SQLException {
        //将数据源传递给QueryRunner，QueryRunner内部通过数据源获取数据库连接
        QueryRunner qr = new QueryRunner(dataSource);
        String sql = "insert into t_user(username,password,email) values(?,?,?)";
        Object params[] = {"roger","123", "gacl@sina.com"};
        qr.update(sql, params);
    }

    public void testDelete() throws SQLException {

        QueryRunner qr = new QueryRunner(dataSource);
        String sql = "delete from t_user where id=?";
        qr.update(sql, 1);

    }

    public void testUpdate() throws SQLException {
        QueryRunner qr = new QueryRunner(dataSource);
        String sql = "update t_user set username=? where id=?";
        Object params[] = { "dfdddd", 1};
        qr.update(sql, params);
    }

    public void testFind() throws SQLException {
        QueryRunner qr = new QueryRunner(dataSource);
        String sql = "select * from t_user where id=?";
        Object params[] = {1};
        User user = (User) qr.query(sql, params, new BeanHandler(User.class));
        System.out.println(user);
    }

    public void testGetAll() throws SQLException {
        QueryRunner qr = new QueryRunner(dataSource);
        String sql = "select * from t_user";
        List list = (List) qr.query(sql, new BeanListHandler(User.class));
        System.out.println(list);
    }

    public void testBatch() throws SQLException {
        QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
        String sql = "insert into t_user(username,password,email) values(?,?,?)";
        Object params[][] = new Object[10][];
        for (int i = 0; i < 10; i++) {
            params[i] = new Object[] { "aa" + i, "123", "aa@sina.com"};
        }
        qr.batch(sql, params);
    }
}
