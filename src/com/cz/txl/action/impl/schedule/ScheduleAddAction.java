package com.cz.txl.action.impl.schedule;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cz.txl.action.Action;
import com.cz.txl.dao.DaoFactory;
import com.cz.txl.dao.schedule.ScheduleDAO;
import com.cz.txl.model.Schedule;
import com.cz.txl.model.User;
import com.cz.txl.util.RequestFormConvertModelUtil;

import net.sf.json.JSONObject;

public class ScheduleAddAction implements Action{
	Log log = LogFactory.getLog(ScheduleAddAction.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		//1,取出request中的表单,然后将表单自动转化为需要插入的对象
		Schedule schedule = null;
		try {
			schedule = RequestFormConvertModelUtil.parseRequestToEntity(request, Schedule.class);
			System.out.println(schedule.getSchTime());
		} catch (Exception e) {
			log.error("类型转换异常>>>>>>>"+e.getMessage());
		}

		//2,从daoFactory中得到ScheduleDao
		ScheduleDAO scheduleDao = DaoFactory.getScheduleDao();

		//构造需要前台返回的json信息
		JSONObject result = new JSONObject();

		try {
			int schId = scheduleDao.insert(schedule);

			//根据插入成功的schId向t_user_schedule表中插入一条记录
			User user = (User)request.getSession().getAttribute("user");
			//向关联表插入一条新纪录,删除日程的时候,需要一并删除关联表中的记录
			scheduleDao.addUserSchedul(schId, user.getId());
			//如果插入成功,需要向前台返回成功的消息
			result.put("success", true);//这个success就是前台需要接受的信息
		} catch (Exception e) {
			log.error("执行插入异常======"+e.getMessage());
		}

		try {
			response.getWriter().print(result.toString());

		} catch (IOException e) {
			log.error("输出异常......."+e.getMessage());
		}

		return null;
	}

}
