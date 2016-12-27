package com.cz.txl.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cz.txl.db.JdbcUtil;

@WebListener
public class InitListener implements ServletContextListener {
	Log log = LogFactory.getLog(InitListener.class);//引入log
	public InitListener() {
	}

	public void contextDestroyed(ServletContextEvent arg0)  {
		log.warn("--系统即将关闭,需要关闭数据源--");
		//判断数据源是否为空
		if (JdbcUtil.getDataSource() != null) {
			try {
				//利用java的反射机制手工强制关闭数据源
				JdbcUtil.getDataSource().close();
			} catch (Exception e) {
				log.error("--关闭数据源时发生异常:--"+e.getMessage());
			}
		}

	}

	public void contextInitialized(ServletContextEvent arg0)  {
		log.info("--系统启动中--");
		//在启动的时候调用一次该方法,生成单例放到内存中,以备后续使用时无需再次执行
		JdbcUtil.getDataSource();
	}

}
