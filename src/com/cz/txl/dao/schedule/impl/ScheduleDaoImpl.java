package com.cz.txl.dao.schedule.impl;

import java.math.BigInteger;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.cz.txl.dao.schedule.ScheduleDAO;
import com.cz.txl.db.JdbcUtil;
import com.cz.txl.model.Schedule;
import com.cz.txl.util.DateUtil;

public class ScheduleDaoImpl implements ScheduleDAO{

	//内置私有全局变量dataSource
	private DataSource dataSource = JdbcUtil.getDataSource();

	@Override
	public int insert(Schedule schedule) throws Exception {

		Connection conn = JdbcUtil.getConn();
		QueryRunner qr = new QueryRunner();
		String sql = "INSERT INTO t_schedule(sch_name,sch_time,sch_desc,"
				+ "is_finish,is_delete,is_send_mail,create_time,update_time) "
				+ " VALUES(?,?,?,?,?,?,?,?)";
		Object[] params = {
				schedule.getSchName(),
				DateUtil.getS(schedule.getSchTime()),//将"yyyy-MM-dd"转换为毫秒数
				schedule.getSchDesc(),
				schedule.getIsFinish(),
				schedule.getIsDelete(),
				schedule.getIsSendMail(),
				DateUtil.getNow().getTime(),
				DateUtil.getNow().getTime()
		};
		qr.update(conn,sql, params);//执行插入

		String sql2 = "SELECT LAST_INSERT_ID() FROM t_schedule";
		BigInteger lastId = (BigInteger)
				qr.query(conn,sql2,
						new ScalarHandler(1));
		System.out.println(lastId+"----000000");
		JdbcUtil.close(conn);
		return lastId.intValue();
	}

	@Override
	public void update(Schedule schedule) throws Exception {
		QueryRunner qr = new QueryRunner(dataSource);
		String sql = "UPDATE t_schedule set sch_name=?,sch_time=?,sch_desc=?,"
				+" is_send_mail=?,update_time=? where id=?";
		Object[] params = {schedule.getSchName(),
				DateUtil.getS(schedule.getSchTime()),//将"yyyy-MM-dd"转换为毫秒数
				schedule.getSchDesc(),
				schedule.getIsSendMail(),
				DateUtil.getNow().getTime(),
				schedule.getId()};
		qr.update(sql,params);//执行修改
	}

	@Override
	public void delete(int scheduleid) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public Schedule queryById(int scheduleid) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Schedule> queryAll() throws Exception {
		QueryRunner qr = new QueryRunner(dataSource);
		String sql = "select id,sch_name as schName,sch_time as schTime,sch_desc as schDesc,"
				+ "is_finish as isFinish,is_delete as isDelete,is_send_mail as isSendMail,create_time as createTime,update_time as updateTime from t_schedule";
		List<Schedule> scheduleList =
				(List<Schedule>) qr.query(sql, new BeanListHandler<>(Schedule.class));

		return scheduleList;
	}

	@Override
	public Schedule queryByName(String schedulename) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Schedule> queryPage(int start, int pageSize) throws Exception {
		QueryRunner qr = new QueryRunner(dataSource);
		String sql = "select id,sch_name as schName,sch_time as schTime,sch_desc as schDesc,"
				+ "is_finish as isFinish,is_delete as isDelete,is_send_mail as isSendMail,create_time as createTime,update_time as updateTime from t_schedule"
				+ " limit ?,?";
		Object[] params = {start,pageSize};
		List<Schedule> scheduleList =
				(List<Schedule>) qr.query(sql,params, new BeanListHandler<>(Schedule.class));

		return scheduleList;
	}

	@Override
	public int queryTotal() throws Exception {
		QueryRunner qr = new QueryRunner(dataSource);
		String sql = "select count(*) from t_schedule";  //[13]  list[13]
		int count = ((Long)qr.query(sql, new ScalarHandler(1))).intValue();
		return count;
	}

	@Override
	public void deleteBatch(String[] scheduleids) throws Exception {
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		String sql = "DELETE FROM t_schedule where id=?";
		String sql2 = "DELETE FROM t_user_schedule where sch_id=?";
		Object params[][] = new Object[scheduleids.length][];
		for (int i = 0; i < scheduleids.length; i++) {
			params[i] = new Object[] {scheduleids[i]};
		}

		qr.batch(sql, params);
		qr.batch(sql2, params);
	}

	@Override
	public void addUserSchedul(int scheduleid, int userId) throws Exception {
		QueryRunner qr = new QueryRunner(dataSource);
		String sql = "INSERT INTO t_user_schedule(sch_id,user_id) "
				+ " VALUES(?,?)";
		Object[] params = {scheduleid,userId};

		qr.update(sql, params);
	}

	@Override
	public List<Map> getMapList() throws Exception {
		QueryRunner qr = new QueryRunner(dataSource);
		String sql = "SELECT u.username,u.email,s.sch_name AS schName,s.sch_time AS schTime,s.sch_desc AS schDesc FROM t_user_schedule us,t_user u ,t_schedule s WHERE s.is_finish=0 AND us.user_id = u.id AND s.id = us.sch_id";
		List<Map> list = (List) qr.query(sql, new MapListHandler());
		return list;
	}

}
