package com.cz.txl.dao.schedule;

import java.util.List;
import java.util.Map;

import com.cz.txl.model.Schedule;

public interface ScheduleDAO {
	public int insert(Schedule schedule) throws Exception;
    public void update(Schedule schedule) throws Exception;
    public void delete(int scheduleid) throws Exception;
    public Schedule queryById(int scheduleid) throws Exception;
    public List<Schedule> queryAll() throws Exception;
    public Schedule queryByName(String schedulename) throws Exception;
    public List<Schedule> queryPage(int start, int pageSize) throws Exception;
    public int queryTotal() throws Exception;
    public void deleteBatch(String[] scheduleids) throws Exception;
    public void addUserSchedul(int scheduleid, int userId) throws Exception;
//    public void deleteUserSchedul(int scheduleid,int userId) throws Exception;
    public List<Map> getMapList() throws Exception;
}
