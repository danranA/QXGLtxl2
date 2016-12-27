package com.cz.txl.util;

/*
 * DateUtil.java
 *
 * Created on 2006年7月25日, 上午11:27
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

import java.sql.Timestamp;
import java.text.*;
import java.util.*;
import java.util.Date;

/**
 * 日期操作类
 */
public final class DateUtil {

    public static final long SECOND  = 1000;
    public static final long MINUTE  = SECOND * 60;
    public static final long HOUR    = MINUTE * 60;
    public static final long DAY     = HOUR * 24;
    public static final long WEEK    = DAY * 7;
    public static final long YEAR    = DAY * 365; // or 366 ???

    /**
     * This is the time difference between GMT time and Vietnamese time
     */
    public static final long GMT_VIETNAM_TIME_OFFSET = HOUR * 7;

    /**
     * RFC 822 date format
     */
    public static final String RFC_822_DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss Z";
    //public static final String RFC_822_DATE_FORMAT = "EEE, d MMM yyyy HH:mm:ss Z";

    /**
     * ISO 8601 [W3CDTF] date format
     */
    public static final String ISO_8601_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

    /**
     * UTC style date format
     */
    public static final String UTC_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    /**
     * This is the time difference between GMT time and SERVER time
     */
    //private static long SERVER_TIME_OFFSET = HOUR * (new DateOptions()).serverHourOffset;

    private static DateFormat ddMMyyyyFormat = new SimpleDateFormat("dd/MM/yyyy");
    private static DateFormat yyyyMMddFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static DateFormat rfc822Format = new SimpleDateFormat(RFC_822_DATE_FORMAT, Locale.US);
    private static DateFormat iso8601Format = new SimpleDateFormat(ISO_8601_DATE_FORMAT, Locale.US);
    private static DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT);
    private static DateFormat datetimeFormat = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT);
    private static DateFormat headerTimeFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);

    static {
        TimeZone gmt = TimeZone.getTimeZone("GMT");
        headerTimeFormat.setTimeZone(gmt);
    }

    /**
     * private constructor
     */
    private DateUtil() {// prevent instantiation
    }
    public static Date getNowDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString=formatter.format(currentTime);
        ParsePosition pos = new ParsePosition(8);
        Date currentTime_2 = formatter.parse(dateString, pos);
        return currentTime_2;
    }
    public static Date getNowDateShort() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString=formatter.format(currentTime);
        ParsePosition pos = new ParsePosition(8);
        Date currentTime_2 = formatter.parse(dateString, pos);
        return currentTime_2;
    }
    public static String getStringDate(){
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }
    public static String getStringDateShort(){
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }
    public static String getStringDateNum(){
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }
    public static Date strToDate(String strDate){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos=new ParsePosition(0);
        Date strtodate=formatter.parse(strDate,pos);
        return strtodate;
    }
    public static String dateToStr(java.util.Date dateDate){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(dateDate);
        return dateString;
    }
    public static Date strToBirthday(String strDate){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos=new ParsePosition(0);
        Date strtodate=formatter.parse(strDate,pos);
        return strtodate;
    }
    public static Date getNow(){
        Date currentTime = new Date();
        return currentTime;
    }
    public static long getS(String strDate){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos=new ParsePosition(0);
        Date strtodate=formatter.parse(strDate,pos);
        return strtodate.getTime();
    }
    public static boolean isExpire(){
        /*boolean ret=false;
        Timestamp currentTime=new Timestamp(System.currentTimeMillis());
        ret=currentTime.after(new Timestamp(Long.parseLong("1230681600000")));
        return ret;*/
        return false;
    }
    public static Date getLastDate(long day) {
        Date date=new Date();
        long date_3_hm=date.getTime()-3600000*34*day;
        Date date_3_hm_date=new Date(date_3_hm);
        return date_3_hm_date;
    }
    /**
     *method 将字符串类型的日期转换为一个timestamp（时间戳记java.sql.Timestamp）
     *@param dateString 需要转换为timestamp的字符串
     *@return dataTime timestamp
     */
    public final static java.sql.Timestamp string2Time(String dateString)
            throws java.text.ParseException {
        DateFormat dateFormat;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss.SSS", Locale.ENGLISH);//设定格式
        //dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.ENGLISH);
        dateFormat.setLenient(false);
        java.util.Date timeDate = dateFormat.parse(dateString);//util类型
        java.sql.Timestamp dateTime = new java.sql.Timestamp(timeDate.getTime());//Timestamp类型,timeDate.getTime()返回一个long型
        return dateTime;
    }
    /**
     *method 将字符串类型的日期转换为一个Date（java.sql.Date）
     *@param dateString 需要转换为Date的字符串
     *@return dataTime Date
     */
    public final static java.sql.Date string2Date(String dateString)
            throws java.lang.Exception {
        DateFormat dateFormat;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        dateFormat.setLenient(false);
        java.util.Date timeDate = dateFormat.parse(dateString);//util类型
        java.sql.Date dateTime = new java.sql.Date(timeDate.getTime());//sql类型
        return dateTime;
    }
    public static synchronized String getDateDDMMYYYY(Date date) {
        return ddMMyyyyFormat.format(date);
    }

    public static synchronized String getDateYYYYMMDD(Date date) {
        return yyyyMMddFormat.format(date);
    }

    public static synchronized String getDateRFC822(Date date) {
        return rfc822Format.format(date);
    }

    public static synchronized String getDateISO8601(Date date) {
        return iso8601Format.format(date);
    }

    public static synchronized String getHTTPHeaderTime(Date date) {
        return headerTimeFormat.format(date);
    }

    public static synchronized String formatDate(Date date) {
        return dateFormat.format(date);
    }

    public static synchronized String formatDateTime(Date date) {
        return datetimeFormat.format(date);
    }

//    public static Timestamp getCurrentGMTTimestamp() {
//        return new Timestamp(System.currentTimeMillis() - (HOUR * Sysconfig.getServerHourOffset()));
//    }
//    
//    public static void updateCurrentGMTTimestamp(Timestamp timeToUpdate) {
//        timeToUpdate.setTime(System.currentTimeMillis() - (HOUR * Sysconfig.getServerHourOffset()));
//    }

    public static Date getVietnamDateFromGMTDate(Date date) {
        return new Date(date.getTime() + GMT_VIETNAM_TIME_OFFSET);
    }

    public static Date convertGMTDate(Date gmtDate, int hourOffset) {
        return new Date(gmtDate.getTime() + hourOffset*HOUR);
    }

    public static Timestamp convertGMTTimestamp(Timestamp gmtTimestamp, int hourOffset) {
        return new Timestamp(gmtTimestamp.getTime() + hourOffset*HOUR);
    }

    public static Timestamp getCurrentGMTTimestampExpiredYear(int offsetYear) {
        //return new Timestamp(System.currentTimeMillis() + offsetYear*(365*24*60*60*1000));
        Calendar now = Calendar.getInstance();
        now.add(Calendar.YEAR, offsetYear);
        return new Timestamp(now.getTime().getTime());
    }

    public static Timestamp getCurrentGMTTimestampExpiredMonth(int offsetMonth) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MONTH, offsetMonth);
        return new Timestamp(now.getTime().getTime());
    }

    public static Timestamp getCurrentGMTTimestampExpiredDay(int offsetDay) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.DATE,offsetDay);
        return new Timestamp(now.getTime().getTime());
    }

    public static String format(Date date, String pattern) {
        DateFormat formatter = new SimpleDateFormat(pattern, Locale.US);
        return formatter.format(date);
    }

    public static String formatDuration(long duration, String pattern) {
        DurationFormater time = new DurationFormater(duration, pattern);
        return time.toString();
    }

    public static String formatDuration(long duration) {
        DurationFormater time = new DurationFormater(duration, null);
        return time.toString();
    }
    public static int getDay(){
        GregorianCalendar currentDay = new GregorianCalendar();
        int today=currentDay.get(Calendar.DAY_OF_MONTH);
        return today;
    }
    public static int getMonth(){
        GregorianCalendar currentDay = new GregorianCalendar();
        int month=currentDay.get(Calendar.MONTH)+1;
        return month;
    }
    public static int getYear(){
        GregorianCalendar currentDay = new GregorianCalendar();
        int year=currentDay.get(Calendar.YEAR);
        return year;
    }
    public static void main(String[] agrs) {
        long duration = (long)1000 * 60 * 60 *24 * 130 + (long)1000 * 60 * 80;
        System.out.println(duration);
        System.out.println("Duration of " + duration + " duration = " + formatDuration(duration));
    }
}

class DurationFormater {
    public static final long MILISECONDS_PER_SECOND = 1000;
    public static final long SECONDS_PER_MINUTE = 60;
    public static final long MINUTES_PER_HOUR = 60;
    public static final long HOURS_PER_DAY = 24;

    public static final int MILISECOND  = 0;
    public static final int SECOND      = 1;
    public static final int MINUTE      = 2;
    public static final int HOUR        = 3;
    public static final int DAY         = 4;


    public static final String PATTERNS[] = {
            "@ms", "@s", "@m", "@h", "@d"
    };
    private static final long[] AMOUNTS = {
            MILISECONDS_PER_SECOND,
            SECONDS_PER_MINUTE,
            MINUTES_PER_HOUR,
            HOURS_PER_DAY
    };
    private static long[] times = new long[5];
    private long time;
    private String pattern;
    private boolean detail = false;

    public DurationFormater() {
    }

    public DurationFormater(long time, String pattern) {
        this.time = time;
        this.pattern = pattern;
        update();
    }

    public DurationFormater(long time) {
        this.time = time;
        update();
    }

    private void update() {
        long remain = time;
        for (int i = 0; i < AMOUNTS.length; i++) {
            times[i] = remain % AMOUNTS[i];
            remain = remain / AMOUNTS[i];
        }
        times[DAY] = (int) remain;
    }

    /*  @h
     *  @M  --> Month
     *  @m  --> minute
     *  @ms --> milisecond
     *  @s  --> second
     */
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long duration) {
        time = duration;
        update();
    }

    public long getMiliseconds() {
        return times[MILISECOND];
    }

    public long getSeconds() {
        return times[SECOND];
    }

    public long getMinutes() {
        return times[MINUTE];
    }

    public long getHours() {
        return times[HOUR];
    }

    public long getDays() {
        return times[DAY];
    }

    public void setDetail(boolean detail) {
        this.detail = detail;
    }

    public String getString() {
        StringBuffer buffer = new StringBuffer(1024);
        buffer.append(pattern);
        for (int i = 0; i < PATTERNS.length; i++) {
            int start = -1;
            int end = -1;
            // Note, in JDK 1.3, StringBuffer does not have method indexOf
            while ((start = buffer.toString().indexOf(PATTERNS[i])) > -1) {
                end = start + PATTERNS[i].length();
                buffer.replace(start, end, String.valueOf(times[i]));
            }
        }
        return buffer.toString();
    }

    public String toString() {
        if (pattern != null) {
            return getString();
        }

        StringBuffer desc = new StringBuffer(256);
        if (times[DAY] > 0) {
            desc.append(checkPlural(times[DAY], "day"));
        }
        if (times[HOUR] > 0) {
            desc.append(checkPlural(times[HOUR], "hour"));
        }
        if ((times[MINUTE] > 0) || (times[DAY] == 0 && times[MINUTE] == 0)) {
            desc.append(checkPlural(times[MINUTE], "minute"));
        }
        if (detail) {
            desc.append(checkPlural(times[SECOND], "second"));
            desc.append(checkPlural(times[MILISECOND], "milisecond"));
        }
        return desc.toString();
    }

    private static String checkPlural(long amount, String unit) {
        StringBuffer desc = new StringBuffer(20);
        desc.append(amount).append(" ").append(unit);
        if (amount > 1) {
            desc.append("s");
        }
        return desc.append(" ").toString();
    }

}