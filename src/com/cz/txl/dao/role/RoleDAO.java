package com.cz.txl.dao.role;

import java.util.List;

import com.cz.txl.model.Menu;
import com.cz.txl.model.Role;

public interface RoleDAO {
    public void insert(Role role) throws Exception;
    public void update(Role role) throws Exception;
    public void delete(int roleid) throws Exception;
    public Role queryById(int roleid) throws Exception;
    public List<Role> queryAll() throws Exception;
    public Role queryByName(String rolename) throws Exception;
    public List<Role> queryPage(int start,int pageSize) throws Exception;
    public int queryTotal() throws Exception;
    public void deleteBatch(String[] menuids) throws Exception;
    public void deleteRoleMenu(int roleId) throws Exception;
    public void addRoleMenuBatch(int roleId,String[] menuIds) throws Exception;
    //根据角色ID查询某角色所对应的菜单的编号列表
    public List<Integer> queryMenus(int roleId) throws Exception;;
    //根据角色编号查询某角色所对应的菜单列表
    public List<Menu> queryMenusByRoleNo(int roleNo) throws Exception;
    public Role queryByNo(int roleno) throws Exception;
    //根据角色ID查询某角色所对应的菜单列表
    public List<Menu> queryMenusByRoleId(int roleId) throws Exception;;
}
