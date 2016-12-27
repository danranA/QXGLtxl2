package com.cz.txl.task;

import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cz.txl.dao.DaoFactory;
import com.cz.txl.dao.schedule.ScheduleDAO;
import com.cz.txl.task.threadpool.ExecutorPool;

//具体来进行干活的
public class DoTask extends TimerTask{
	Log log = LogFactory.getLog(DoTask.class);

	private ExecutorPool pool;//需要调用DoTask来执行任务的需要传递进来一个线程池对象的引用

	public DoTask(ExecutorPool pool) {
		super();
		this.pool = pool;
	}

	@Override
	public void run(){
		// 定时器主要执行的代码块
		log.info("开始执行邮件发送任务");
		//1,读取需要发送的邮件列表
		ScheduleDAO schDao = DaoFactory.getScheduleDao();
		try {
			List<Map> mapList = schDao.getMapList();

			//2,将要发送的邮件列表构造成任务队列能接受的参数
			for (int i = 0;i<mapList.size();i++) {
				Map map = mapList.get(i);
				//构造一个sendTask对象.并且提交到线程池中
				SendTask task = new SendTask(
						(String)map.get("email"),
						(String)map.get("schName"),
						(String)map.get("schDesc")
				);
				pool.addTask(task);//向线程池中添加可执行的任务
			}
			//3,提交任务到pool中
		} catch (Exception e) {
			log.error("提交任务时发生异常"+e.getMessage());
		}

		log.info("邮件发送任务完成");
	}

}
