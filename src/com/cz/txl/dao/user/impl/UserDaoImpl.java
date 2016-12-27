package com.cz.txl.dao.user.impl;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.cz.txl.common.Constants;
import com.cz.txl.dao.user.UserDAO;
import com.cz.txl.db.JdbcUtil;
import com.cz.txl.model.QueryCondtion;
import com.cz.txl.model.Schedule;
import com.cz.txl.model.User;
import com.cz.txl.type.QueryConditionType;

public class UserDaoImpl implements UserDAO {

	// 内置私有全局变量dataSource
	private DataSource dataSource = JdbcUtil.getDataSource();

	@Override
	public int insert(User user) throws Exception {
		QueryRunner qr = new QueryRunner();
		Connection conn = dataSource.getConnection();
		String sql = "INSERT INTO t_user(username,password,age,gender,phone,email,create_time,update_time) "
				+ " VALUES(?,?,?,?,?,?,?,?)";
		Object[] params = { user.getUsername(), user.getPassword(), user.getAge(), user.getGender(), user.getPhone(),
				user.getEmail(), user.getCreateTime(), user.getUpdateTime() };
		qr.insert(conn, sql, new ResultSetHandler<User>() {
			@Override
			public User handle(ResultSet rs) throws SQLException {
				return null;
			}
		}, params);
		String sql2 = "SELECT LAST_INSERT_ID() FROM t_user";
		BigInteger lastId = (BigInteger) qr.query(conn, sql2, new ScalarHandler(1));
		System.out.println(lastId + "----000000");
		JdbcUtil.close(conn);
		return lastId.intValue();
	}

	@Override
	public void update(User user) throws Exception {
		QueryRunner qr = new QueryRunner(dataSource);
		String sql = "UPDATE t_user set username=?,password=?,age=?,"
				+ " gender=?,phone=?,email=?,update_time=? where id=?";
		Object[] params = { user.getUsername(), user.getPassword(), user.getAge(), user.getGender(), user.getPhone(),
				user.getEmail(), user.getUpdateTime(), user.getId() };
		qr.update(sql, params);// 执行修改
	}

	@Override
	public void updateBase(User user) throws Exception {
		QueryRunner qr = new QueryRunner(dataSource);
		String sql = "UPDATE t_user set username=?,age=?," + " gender=?,phone=?,email=?,update_time=? where id=?";
		Object[] params = { user.getUsername(), user.getAge(), user.getGender(), user.getPhone(), user.getEmail(),
				user.getUpdateTime(), user.getId() };
		qr.update(sql, params);// 执行修改
	}

	@Override
	public void delete(int userid) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public User queryById(int userid) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> queryAll() throws Exception {
		QueryRunner qr = new QueryRunner(dataSource);
		String sql = "select id,username,password,age,gender,phone,email,"
				+ "is_deleted as isDelete,create_time as createTime," + "update_time as updateTime" + " from t_user";
		List<User> userList = (List<User>) qr.query(sql, new BeanListHandler<>(User.class));

		return userList;
	}

	@Override
	public User queryByName(String username) throws Exception {
		QueryRunner queryRunner = new QueryRunner(dataSource);

		String sql = "SELECT * FROM t_user WHERE username=?";
		Object[] params = { username };

		User user = queryRunner.query(sql, params, new BeanHandler<>(User.class));

		return user;
	}

	@Override
	public List<User> queryPage(int start, int pageSize) throws Exception {
		QueryRunner qr = new QueryRunner(dataSource);
		String sql = "select id,username,password,age,gender,phone,email,"
				+ "is_deleted as isDelete,create_time as createTime," + "update_time as updateTime" + " from t_user"
				+ " limit ?,?";
		Object[] params = { start, pageSize };
		List<User> userList = (List<User>) qr.query(sql, params, new BeanListHandler<>(User.class));

		return userList;
	}

	@Override
	public int queryTotal() throws Exception {
		QueryRunner qr = new QueryRunner(dataSource);
		String sql = "select count(*) from t_user"; // [13] list[13]
		int count = ((Long) qr.query(sql, new ScalarHandler(1))).intValue();
		return count;
	}

	@Override
	public void deleteBatch(String[] userids) throws Exception {
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		String sql = "DELETE FROM t_user where id=?";
		Object params[][] = new Object[userids.length][];
		for (int i = 0; i < userids.length; i++) {
			params[i] = new Object[] { userids[i] };
		}
		qr.batch(sql, params);
	}

	@Override
	public void deleteUserRole(int userId) throws Exception {
		QueryRunner qr = new QueryRunner(dataSource);
		String sql = "DELETE FROM t_user_role WHERE user_id=?";
		Object[] param = { userId };
		qr.update(sql, param);
	}

	@Override
	public void addUserRole(int userId, int roleId) throws Exception {
		QueryRunner qr = new QueryRunner(dataSource);
		String sql = "INSERT INTO t_user_role(user_id,role_no)" + " VALUES(?,?)";
		Object[] param = { userId, roleId };
		qr.update(sql, param);
	}

	@Override
	public int getRoleNo(int userId) throws Exception {
		QueryRunner qr = new QueryRunner(dataSource);
		String sql = "SELECT role_no FROM t_user_role WHERE user_id=?"; // [13]
		// list[13]
		Object[] param = { userId };
		int roleNo = ((Integer) qr.query(sql, param, new ScalarHandler(1))).intValue();
		return roleNo;
	}

	@Override
	public void updatePass(int userId, String password) throws Exception {
		QueryRunner qr = new QueryRunner(dataSource);
		String sql = "UPDATE t_user SET password=? WHERE id=?";
		Object[] param = { password, userId };
		qr.update(sql, param);
	}

	@Override
	public List<Schedule> queryByUserId(int userId) throws Exception {
		QueryRunner qr = new QueryRunner(dataSource);
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT sch_name AS schName ,").append(" sch_desc AS schDesc ,sch_time AS schTime")
				.append(" FROM t_schedule").append(" WHERE").append(" is_finish = 0").append(" AND id IN ")
				.append("(SELECT sch_id FROM t_user_schedule WHERE user_id = ?)");
		Object[] params = { userId };
		List<Schedule> schList = qr.query(sb.toString(), params, new BeanListHandler<>(Schedule.class));
		return schList;
	}

	@Override
	public int reg(User user) throws Exception {
		QueryRunner qr = new QueryRunner();
		Connection conn = dataSource.getConnection();
		String sql = "INSERT INTO t_user(username,password,age,gender,phone,email,create_time,update_time,user_info,user_status) "
				+ " VALUES(?,?,?,?,?,?,?,?,?,?)";
		Object[] params = { user.getUsername(), user.getPassword(), user.getAge(), user.getGender(), user.getPhone(),
				user.getEmail(), user.getCreateTime(), user.getUpdateTime(), user.getUserInfo(), user.getUserStatus() };
		qr.insert(conn, sql, new ResultSetHandler<User>() {
			@Override
			public User handle(ResultSet rs) throws SQLException {
				return null;
			}
		}, params);
		String sql2 = "SELECT LAST_INSERT_ID() FROM t_user";
		BigInteger lastId = (BigInteger) qr.query(conn, sql2, new ScalarHandler<Object>(1));
		JdbcUtil.close(conn);
		return lastId.intValue();
	}

	@Override
	public List<User> queryAllAdmin(int roleNO) throws Exception {
		QueryRunner qr = new QueryRunner(dataSource);
		String sql = "SELECT * from t_user where id in (SELECT user_id FROM t_user_role WHERE role_no=?)";
		Object[] param = { roleNO };
		List<User> userList = qr.query(sql, param, new BeanListHandler<>(User.class));
		return userList;
	}

	@Override
	public List<User> queryPage(String[] fiedList, String[] fieldValue, int start, int pageSize) throws Exception {

		QueryRunner qr = new QueryRunner(dataSource);
		String sql = "select id,username,password,age,gender,phone,email,"
				+ "is_deleted as isDelete,create_time as createTime," + "update_time as updateTime"
				+ " from t_user where 1=1";
		if (fiedList != null && fiedList.length > 0) {
			for (int i = 0; i < fiedList.length; i++) {
				if (fiedList[i].equalsIgnoreCase("user_status")) {
					if (Integer.parseInt(fieldValue[i]) > 0 && Integer.parseInt(fieldValue[i]) <= 3) {
						sql += " and user_status=" + Integer.parseInt(fieldValue[i]);
					}
				} else {
					sql += " and "+fiedList[i] + "='" + fieldValue[i]+"'";
				}
			}
		}
		sql += " limit ?,?";
		System.out.println(sql);
		Object[] params = { start, pageSize };
		List<User> userList = (List<User>) qr.query(sql, params, new BeanListHandler<>(User.class));
		System.out.println(userList);
		return userList;

	}

	@Override
	public void active(String []userids) throws Exception {
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		String sql = "UPDATE t_user SET user_status=0 where id=?";
		Object params[][] = new Object[userids.length][];
		for (int i = 0; i < userids.length; i++) {
			params[i] = new Object[] { userids[i] };
		}
		qr.batch(sql, params);

	}

	@Override
	public List<User> queryPage(List<QueryCondtion> qcList, int start, int pageSize) throws Exception {
		QueryRunner qr = new QueryRunner(dataSource);
		String sql = "select id,username,password,age,gender,phone,email,"
				+ "is_deleted as isDelete,create_time as createTime," + "update_time as updateTime"
				+ " from t_user where 1=1";
		for(QueryCondtion qc:qcList){

			String filedName=Constants.fieldTocolumMap.get(qc.getFieldName())
					==null?qc.getFieldName():Constants.fieldTocolumMap.get(qc.getFieldName());
			sql+=" and "+
					filedName+" "+
					qc.getConditionType().getName();

			if(qc.getConditionType().getName().equals(QueryConditionType.Like.getName())){
				sql+="'%"+qc.getFieldValue()+"%'";
			}else{

				sql+=qc.getFieldValue();
			}
		}
		sql += " limit ?,?";
		System.out.println(sql);
		Object[] params = { start, pageSize };
		List<User> userList = (List<User>) qr.query(sql, params, new BeanListHandler<>(User.class));
		System.out.println(userList);
		return userList;
	}

}
