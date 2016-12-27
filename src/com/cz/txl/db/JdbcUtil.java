package com.cz.txl.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JdbcUtil {
    private static Log log = LogFactory.getLog(JdbcUtil.class);

    private static ComboPooledDataSource dataSource;
    //本地线程管理对象，用于实现: 同一个线程获取的连接是同一个
    private static ThreadLocal<Connection> t = new ThreadLocal<Connection>();

    static{
        try {
            dataSource=new ComboPooledDataSource();//使用默认配置项创建数据源
//            pool=new ComboPooledDataSource("czjy");这句使用的配置文件里的name-config
        } catch (Exception e) {
            log.error("C3P0数据源初始化错误"+e.getMessage());
        }
    }

    public static Connection getConn() {
        //先从t中拿，如果有就拿出去，如果没有再到池中拿且把该对象放到t中
        Connection conn = t.get();
        if(conn==null){
            try {
                conn=dataSource.getConnection();
                t.set(conn); //放到t中
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }
        log.info("获取一个连接:"+conn);
        return conn;
    }

    //
    public static ComboPooledDataSource getDataSource(){
        return dataSource;
    }

    // 关闭链接
    public static void close(Connection conn) throws SQLException {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            }
        }
    }

    //关闭预处理
    public static void close(PreparedStatement pstate) throws SQLException {
        if(pstate!=null){
            pstate.close();
        }
    }

    //关闭结果集
    public static void close(ResultSet rs) throws SQLException {
        if(rs!=null){
            rs.close();
        }
    }


}
