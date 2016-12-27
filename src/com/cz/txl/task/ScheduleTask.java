package com.cz.txl.task;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cz.txl.dao.DaoFactory;
import com.cz.txl.dao.schedule.ScheduleDAO;

public class ScheduleTask {
	Log log = LogFactory.getLog(ScheduleTask.class);
	public void execute() throws Exception{
		//1,查询所有需要发送的用户邮箱列表
		ScheduleDAO schDao = DaoFactory.getScheduleDao();
		List<Map> mapList = schDao.getMapList();
		SendMailTask task = new SendMailTask();
		for (int i = 0;i<mapList.size();i++) {
			Map map = mapList.get(i);
			log.info("当前正在向用户["+map.get("username")+"]发送邮件...");

			task.send_email((String)map.get("email"), (String)map.get("schName"), (String)map.get("schDesc"));
		}
	}
}
