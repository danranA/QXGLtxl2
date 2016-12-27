package com.cz.txl.db;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.KeyedHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import junit.framework.TestCase;

/**
 * ResultSetHandler的各个实现类：
 ArrayHandler：把结果集中的第一行数据转成对象数组。
 ArrayListHandler：把结果集中的每一行数据都转成一个对象数组，再存放到List中。
 BeanHandler：将结果集中的第一行数据封装到一个对应的JavaBean实例中。
 BeanListHandler：将结果集中的每一行数据都封装到一个对应的JavaBean实例中，存放到List里。//重点
 MapHandler：将结果集中的第一行数据封装到一个Map里，key是列名，value就是对应的值。//重点
 MapListHandler：将结果集中的每一行数据都封装到一个Map里，然后再存放到List
 ColumnListHandler：将结果集中某一列的数据存放到List中。
 KeyedHandler(name)：将结果集中的每一行数据都封装到一个Map里(List<Map>)，再把这些map再存到一个map里，其key为指定的列。
 */
public class ResultSetHandlerTest extends TestCase{

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

    public void testArrayHandler() throws SQLException{
        QueryRunner qr = new QueryRunner(dataSource);
        String sql = "select * from t_user";
        Object result[] = (Object[]) qr.query(sql, new ArrayHandler());
        System.out.println(Arrays.asList(result));  //list  toString()
    }


    public void testArrayListHandler() throws SQLException{

        QueryRunner qr = new QueryRunner(dataSource);
        String sql = "select * from t_user";
        List<Object[]> list = (List) qr.query(sql, new ArrayListHandler());
        for(Object[] o : list){
            System.out.println(Arrays.asList(o));
        }
    }


    public void testColumnListHandler() throws SQLException{
        QueryRunner qr = new QueryRunner(dataSource);
        String sql = "select * from t_user";
        List list = (List) qr.query(sql, new ColumnListHandler("id"));
        System.out.println(list);
    }


    public void testKeyedHandler() throws Exception{
        QueryRunner qr = new QueryRunner(dataSource);
        String sql = "select * from t_user";

        Map<Integer,Map> map = (Map) qr.query(sql, new KeyedHandler("id"));
        for(Map.Entry<Integer, Map> me : map.entrySet()){
            int  id = me.getKey();
            Map<String,Object> innermap = me.getValue();
            for(Map.Entry<String, Object> innerme : innermap.entrySet()){
                String columnName = innerme.getKey();
                Object value = innerme.getValue();
                System.out.println(columnName + "=" + value);
            }
            System.out.println("----------------");
        }
    }


    public void testMapHandler() throws SQLException{

        QueryRunner qr = new QueryRunner(dataSource);
        String sql = "select * from t_user";

        Map<String,Object> map = (Map) qr.query(sql, new MapHandler());
        for(Map.Entry<String, Object> me : map.entrySet())
        {
            System.out.println(me.getKey() + "=" + me.getValue());
        }
    }



    public void testMapListHandler() throws SQLException{
        QueryRunner qr = new QueryRunner(dataSource);
        String sql = "select * from t_user";
        List<Map> list = (List) qr.query(sql, new MapListHandler());
        for(Map<String,Object> map :list){
            for(Map.Entry<String, Object> me : map.entrySet())
            {
                System.out.println(me.getKey() + "=" + me.getValue());
            }
        }
    }


    public void testScalarHandler() throws SQLException{
        QueryRunner qr = new QueryRunner(dataSource);
        String sql = "select count(*) from t_user";  //[13]  list[13]
        int count = ((Long)qr.query(sql, new ScalarHandler(1))).intValue();
        System.out.println(count);
    }

}
