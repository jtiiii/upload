package com.funeral.upload.util;

import com.funeral.upload.entity.TimePeriod;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 时间工具类
 * @author FuneralObjects
 * CreateTime 2017-12-14
 */
public class TimeUtils {
    /**
     * 刷新频率毫秒
     */
    private long rate;
    /**
     * 当前时间
     */
    private long now;

    private static final String MYSQL_DATE_FORMAT="yyyy-MM-dd HH:mm:ss";

    private static TimeUtils timeUtils = new TimeUtils(10);

    private TimeUtils(final long rate){
        this.rate = rate;
        this.now = System.currentTimeMillis();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                refresh();
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }


    /**
     * 无限刷新
     */
    private void refresh(){
        boolean flag = true;
        while (flag) {
            try {
                Thread.sleep(rate);
            } catch (InterruptedException e) {
                flag = false;
            }
            now = System.currentTimeMillis();
        }
    }

    public static long getNow() {
        return timeUtils.now;
    }

    public static long getRate() {
        return timeUtils.rate;
    }

    /**
     * 校验开始时间与结束时间是否符合要求
     * @param start
     * @param end
     */
    public static TimePeriod validateStartAndEnd(Date start, Date end){
        if(start == null || end == null){
            throw new RuntimeException("start or end is Blank!");
        }
        if(start.compareTo(end) >= 0){
            throw new RuntimeException("Param error:start later than end!");
        }
        return new TimePeriod(start,end);
    }

    /**
     * 通过Calendar精度为依据格式化之间所有时间
     * @param start
     * @param end
     * @param format 格式化格式
     * @param accuracy Calendar的精度，比如Calendar.DATE（日）,Calendar.MONTH（月）
     * @return
     */
    public static List<String> getFormatDates(Date start,Date end,String format,Integer accuracy){
        validateStartAndEnd(start,end);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        List<String> result = new ArrayList<String>();
        calendar.setTime(start);
        Long endTime = end.getTime();
        while(calendar.getTimeInMillis() < endTime){
            result.add(sdf.format(calendar.getTime()));
            calendar.add(accuracy,1);
        }
        return result;
    }

    /**
     * 格式化时间，用于mysql数据库中的时间查询
     * @param time 时间
     * @return 被格式化的时间字串
     */
    public static String formatForMysqlDate(Date time){
        if(time == null){
            throw new RuntimeException("time is Blank!");
        }
        return new SimpleDateFormat(MYSQL_DATE_FORMAT).format(time);
    }

}
