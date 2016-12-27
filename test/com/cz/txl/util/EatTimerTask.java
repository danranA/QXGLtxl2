package com.cz.txl.util;


import java.util.Timer;
import java.util.TimerTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

/*
 * 定时吃饭
 * */
public class EatTimerTask extends TimerTask {

    //吃饭时间
    private static List<Integer> eatTimes;
    /*
     * 静态初始化
     * */
    static {
        initEatTimes();
    }

    /*
     * 初始化吃饭时间
     * */
    private static void initEatTimes(){
        eatTimes = new ArrayList<Integer>();
        eatTimes.add(8);
        eatTimes.add(12);
        eatTimes.add(17);
    }

    /*
     * 执行
     * */
    @Override
    public void run() {
        // TODO Auto-generated method stub
        Calendar calendar = Calendar.getInstance();
        System.out.println("检查是否到了吃饭的点");
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if(eatTimes.contains(hour))
        {
            System.out.println("饿了，吃饭...");
        }
    }

    public static void main(String[] arg){
        TimerTask task = new EatTimerTask();
        Calendar  calendar = Calendar.getInstance();


        Date firstTime = calendar.getTime();
        //间隔：1小时
        long period = 1000;
        //测试时间每分钟一次
        //period = 1000 * 60;        

        Timer timer = new Timer();
        timer.schedule(task, firstTime, period);
    }
}