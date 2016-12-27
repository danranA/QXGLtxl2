package com.cz.txl.util;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class GuDingTimer {

    public void testTimer() throws Exception  {

        // 时间类
        Calendar startDate = Calendar.getInstance();

        //设置开始执行的时间为 某年-某月-某月 00:00:00
        startDate.set(startDate.get(Calendar.YEAR), startDate.get(Calendar.MONTH), startDate.get(Calendar.DATE), 17, 20, 0);

        // 1小时的毫秒设定
        long timeInterval = 60 * 60 * 1000;

        // 定时器实例
        Timer t = new Timer();

        t.schedule(new TimerTask() {

            public void run() {

                // 定时器主要执行的代码块
                System.out.println("定时器主要执行的代码!"+DateUtil.getStringDate());
            }

            // 设定的定时器在15:10分开始执行,每隔 1小时执行一次.
        }, startDate.getTime(), timeInterval ); //timeInterval 是一天的毫秒数，也是执行间隔

    }

    public static void main(String[] args) throws Exception {
        GuDingTimer gd = new GuDingTimer();
        gd.testTimer();
    }
}
