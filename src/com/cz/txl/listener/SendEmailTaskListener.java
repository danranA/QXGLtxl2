package com.cz.txl.listener;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cz.txl.task.ScheduleTask;

/**
 * Application Lifecycle Listener implementation class SendEmailTaskListener
 *
 */
//@WebListener
public class SendEmailTaskListener implements ServletContextListener {
    Log log = LogFactory.getLog(SendEmailTaskListener.class);
    // 定时器实例
    Timer t = new Timer();
    /**
     * Default constructor.
     */
    public SendEmailTaskListener() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  {
        //应用关闭的时候,终止定时器
        t.cancel();
    }

    /**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0)  {
        // 时间类
        Calendar startDate = Calendar.getInstance();

        //设置开始执行的时间为 某年-某月-某月 00:00:00
        startDate.set(startDate.get(Calendar.YEAR), startDate.get(Calendar.MONTH), startDate.get(Calendar.DATE), 18, 14, 0);

        // 1小时的毫秒设定
        long timeInterval = 60 * 60 * 1000 * 24;


        t.schedule(new TimerTask() {

            public void run() {

                // 定时器主要执行的代码块
                log.info("开始执行邮件发送任务");

                //开始调用邮件发送任务
                ScheduleTask task = new ScheduleTask();
                try {
                    task.execute();
                } catch (Exception e) {
                    log.error("邮件发送失败"+e.getMessage());
                }
                log.info("邮件发送任务完成");

            }

            // 设定的定时器在15:10分开始执行,每隔 1小时执行一次.
        }, startDate.getTime(), timeInterval ); //timeInterval 是一天的毫秒数，也是执行间隔
    }

}
