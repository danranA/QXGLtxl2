package com.cz.txl.listener;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cz.txl.task.DoTask;
import com.cz.txl.task.ScheduleTask;
import com.cz.txl.task.threadpool.ExecutorPool;

/**
 * Application Lifecycle Listener implementation class ThreadPoolSendMailListener
 *
 */
@WebListener
public class ThreadPoolSendMailListener implements ServletContextListener {
    Log log = LogFactory.getLog(ThreadPoolSendMailListener.class);
    private ExecutorPool pool;
    // 定时器实例
    Timer t = new Timer();
    public ThreadPoolSendMailListener() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  {
        // TODO Auto-generated method stub
    }

    /**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0)  {
        BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<Runnable>();

        //获得当前电脑的cup个数
        int cupCounts = Runtime.getRuntime().availableProcessors();
        //TODO 4,8,30这些数据应该从web.xml或者properties文件读取.
        ExecutorService service = new ThreadPoolExecutor(
                cupCounts, //4表示新建的线程池里始终有四个活动的线程
                cupCounts * 2, //8表示该线程池最大支持的线程并发数量
                30, //30秒.超过4个线程时,这些线程的存活时间
                TimeUnit.SECONDS, //定义上面这个参数30的单位,这里是秒
                taskQueue,//这个线程池需要执行的任务所在的任务队列
                new ThreadPoolExecutor.CallerRunsPolicy()//失败时采用的策略,这里是重新运行
        );
        //根据上面的两个对象构建线程池
        pool =ExecutorPool.getInstance(taskQueue, service);

        //调用DoTask构建要执行任务队列并且提交任务
        DoTask dt = new DoTask(pool);

        // 时间类
        Calendar startDate = Calendar.getInstance();
        //设置开始执行的时间为 某年-某月-某月 00:00:00
        startDate.set(startDate.get(Calendar.YEAR),
                startDate.get(Calendar.MONTH),
                startDate.get(Calendar.DATE),
                12, 26, 0);//分别表示18点,14分0秒. TODO 这三个数字应该从配置文件读取
        // 1小时的毫秒设定
        long timeInterval = 60 * 60 * 1000 * 24;
        // 设定的定时器在15:10分开始执行,每隔 1小时执行一次.
        //timeInterval 是一天的毫秒数，也是执行间隔
        //调度定时器进行任务执行
        t.schedule(dt, startDate.getTime(),timeInterval);
    }

}
