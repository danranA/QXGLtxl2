package com.cz.txl.task.threadpool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

//线程池类,需要两个属性,一个是任务队列,一个是线程池服务接口
public class ExecutorPool {
	Log log = LogFactory.getLog(ExecutorPool.class);

	BlockingQueue<Runnable> taskQueue;//任务队列,用来接收提交的任务,形成一个缓冲队列
	ExecutorService service;//线程池服务接口,

	private static ExecutorPool instance;

	private ExecutorPool(BlockingQueue<Runnable> taskQueue, ExecutorService service) {
		this.taskQueue = taskQueue;
		this.service = service;
	}
	//该方法第一次必须是被监听器调用，确保后续的调用已经被初始化。
	public static synchronized ExecutorPool getInstance(BlockingQueue<Runnable> taskQueue, ExecutorService service){
		if(null==instance){
			instance=new ExecutorPool(taskQueue, service);
		}
		return instance;
	}
	//因为上面的方法在应用启动时，通过监听器进行初始化，所以该方法可以放心调用
	public static ExecutorPool getInstance(){
		return instance;
	}

	//异步添加任务的方法,所谓异步添加.就是将任务添加到了任务队列.并没有立即执行
	public void addTask(Runnable task){
		log.info("向线程池中添加任务");
		service.submit(task);//该方法不会立即执行task
	}

	public void close() throws InterruptedException{
		log.info("线程池收到了关闭的命令...开始关闭");
		//当应用关闭的时候,需要将线程池进行安全的关闭
		//首先需要判断是taskQueue中是否还有未完成的任务
		if (taskQueue != null && taskQueue.size() > 0) {
			//进到if语句表示还有未完成的任务.
			log.info("线程池中还有("+taskQueue.size()+")个未完成的任务");
			log.info("等五秒让未完成的任务尽量完成......");
			Thread.sleep(5000);//给五秒的时间让线程继续执行
		}

		log.info("不管了...开始关闭");
		//然后开始关闭线程池
		if (service != null) {
			log.info("...正在关闭");
			service.shutdown();//关闭线程池
			log.info("关闭完...");
		}
		taskQueue = null;//清空任务队列
		log.info("线程池关闭完成");
	}




}
