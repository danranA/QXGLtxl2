package com.cz.txl.dao.user;

import java.util.List;

import com.cz.txl.model.QueryCondtion;
import com.cz.txl.model.Schedule;
import com.cz.txl.model.User;
import com.cz.txl.type.QueryConditionType;

public interface UserDAO {
    public int insert(User user) throws Exception;
    public void update(User user) throws Exception;
    public void updateBase(User user) throws Exception;
    public void delete(int userid) throws Exception;
    public User queryById(int userid) throws Exception;
    public List<User> queryAll() throws Exception;
    public User queryByName(String username) throws Exception;
    public List<User> queryPage(int start,int pageSize) throws Exception;
    public int queryTotal() throws Exception;
    public void deleteBatch(String[] userids) throws Exception;

    //TODO 如下两个方式应该是一个原子事务,不应该分开写.
    public void deleteUserRole(int userId) throws Exception;
    public void addUserRole(int userId,int roleId) throws Exception;

    public int getRoleNo(int userId) throws Exception;

    public void updatePass(int userId,String password) throws Exception;

    public List<Schedule> queryByUserId(int userId) throws Exception;

    public int reg(User user) throws Exception;


    //根据角色编号查找管理员列表
    public List<User> queryAllAdmin(int roleNO)throws Exception;

    //根据不同的值进行用户查找
    public List<User> queryPage(String[] fiedList,String [] fieldValue,int start,int pageSize) throws Exception;

    //激活用户
    public void active(String[] idArray) throws Exception;



    //更加通用的方法
    public List<User> queryPage(List<QueryCondtion> qcList,int start,int pageSize) throws Exception;

}






