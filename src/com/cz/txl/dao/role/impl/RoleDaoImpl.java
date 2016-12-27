package com.cz.txl.dao.role.impl;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.cz.txl.dao.role.RoleDAO;
import com.cz.txl.db.JdbcUtil;
import com.cz.txl.model.Menu;
import com.cz.txl.model.Role;

public class RoleDaoImpl implements RoleDAO{

	//内置私有全局变量dataSource
	private DataSource dataSource = JdbcUtil.getDataSource();

	@Override
	public void insert(Role role) throws Exception {
		QueryRunner qr = new QueryRunner(dataSource);

		String sql = "INSERT INTO t_role(role_no,role_name) "
				+ " VALUES(?,?)";
		Object[] params = {role.getRoleNo(),role.getRoleName()};
		qr.update(sql, params);//执行插入
	}

	@Override
	public void update(Role role) throws Exception {
		QueryRunner qr = new QueryRunner(dataSource);
		String sql = "UPDATE t_role set role_no=?,role_name=? where id=?";
		Object[] params = {role.getRoleNo(),
				role.getRoleName(),
				role.getId()};
		qr.update(sql,params);//执行修改
	}

	@Override
	public void delete(int roleid) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public Role queryById(int roleid) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Role> queryAll() throws Exception {
		QueryRunner qr = new QueryRunner(dataSource);
		String sql = "select id,role_no as roleNo,role_name as roleName "
				+ " from t_role";
		List<Role> roleList =
				(List<Role>) qr.query(sql, new BeanListHandler<>(Role.class));

		return roleList;
	}

	@Override
	public Role queryByName(String rolename) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Role> queryPage(int start, int pageSize) throws Exception {
		QueryRunner qr = new QueryRunner(dataSource);
		String sql = "select id,role_no as roleNo,role_name as roleName "
				+ " from t_role "
				+ " limit ?,?";
		Object[] params = {start,pageSize};
		List<Role> roleList =
				(List<Role>) qr.query(sql,params, new BeanListHandler<>(Role.class));

		return roleList;
	}

	@Override
	public int queryTotal() throws Exception {
		QueryRunner qr = new QueryRunner(dataSource);
		String sql = "select count(*) from t_role";  //[13]  list[13]
		int count = ((Long)qr.query(sql, new ScalarHandler(1))).intValue();
		return count;
	}

	@Override
	public void deleteBatch(String[] roleids) throws Exception {
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		String sql = "DELETE FROM t_role where id=?";
		Object params[][] = new Object[roleids.length][];
		for (int i = 0; i < roleids.length; i++) {
			params[i] = new Object[] {roleids[i]};
		}
		qr.batch(sql, params);
	}

	@Override
	public void deleteRoleMenu(int roleId) throws Exception {
		QueryRunner qr = new QueryRunner(dataSource);
		String sql = "DELETE FROM t_role_menu where role_id=?";
		Object[] param = {roleId};
		qr.update(sql, param);

	}

	@Override
	public void addRoleMenuBatch(int roleId, String[] menuIds) throws Exception {
		QueryRunner qr = new QueryRunner(dataSource);
		String sql = "INSERT INTO t_role_menu(role_id,menu_no) "
				+ " VALUES(?,?)";
		Object[][] param = new Object[menuIds.length][];
		for (int i = 0;i<menuIds.length;i++) {
			param[i] = new Object[] {roleId,menuIds[i]};
		}

		qr.batch(sql, param);
	}

	@Override
	public List<Integer> queryMenus(int roleId) throws Exception {
		QueryRunner qr = new QueryRunner(dataSource);

		String sql = "SELECT menu_no FROM t_role_menu WHERE role_id=?";

		Object[] param = {roleId};
		List<Object[]> list = (List) qr.query(sql,param, new ArrayListHandler());

		System.out.println(list+"-----------------");
		List<Integer> result = new ArrayList<Integer>();
		for (Object[] o : list) {
			result.add(Integer.parseInt(o[0].toString()));
		}
		return result;
	}

	//根据角色编号进行关联查询获得用户所具备的权限列表
	@Override
	public List<Menu> queryMenusByRoleNo(int roleNo) throws Exception {
		QueryRunner qr = new QueryRunner(dataSource);

		String sql = "SELECT id,menu_no AS menuNo,menu_name AS menuName,ACTION,sort_no AS sortNo FROM t_menu WHERE menu_no IN (SELECT rm.menu_no FROM 	t_role AS r,	t_role_menu AS rm WHERE r.id = rm.role_id AND r.role_no=?)";

		Object[] params = {roleNo};

		List<Menu> menuList = qr.query(sql, params, new BeanListHandler<>(Menu.class));

		return menuList;
	}

	//根据角色编号查询角色
	@Override
	public Role queryByNo(int roleno) throws Exception {
		QueryRunner qr = new QueryRunner(dataSource);
		String sql = "select id,role_no as roleNo,role_name as roleName "
				+ " from t_role Where role_no=?";
		Object[] params = {roleno};
		Role role = qr.query(sql, params,new BeanHandler<>(Role.class));
		return role;
	}

	@Override
	public List<Menu> queryMenusByRoleId(int roleId) throws Exception {
		QueryRunner qr = new QueryRunner(dataSource);
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT m.id,m.menu_no AS menuNo,m.menu_name AS menuName,m.action,m.sort_no AS sortNo")
				.append(" FROM t_menu AS m,")
				.append(" t_role_menu AS rm ")
				.append(" WHERE ")
				.append(" m.menu_no = rm.menu_no ")
				.append(" AND rm.role_id=?");
		Object[] params = {roleId};
		List<Menu> menuList = qr.query(sb.toString(), params,new BeanListHandler<>(Menu.class));
		return menuList;
	}
}
