package com.artqiyi.game.core;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GameUtil {
	private final static int THRESHOLD = 5;
	
	public static Date getThisWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 获得当前日期是一个星期的第几天
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        return cal.getTime();
    }
	
	/**
     * 获取游戏结算时间
     * @param awardTime
     * @return
     */
    public static Date getEndTime(String awardTime){
        String[] split = awardTime.split(":");
        int h = Integer.parseInt(split[0]);
        int m = Integer.parseInt(split[1]);

        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        c.set(Calendar.HOUR_OF_DAY,Integer.valueOf(split[0]));
        c.set(Calendar.MINUTE,Integer.valueOf(split[1]));
        c.set(Calendar.SECOND,0);
        c.set(Calendar.MILLISECOND,0);
        //如果当前时间小于定时任务配置的时间,在当天结算
        if (hour < h || (hour == h && m - minute > THRESHOLD)) {
            return c.getTime();
        }

        //否则第二天结算
        c.add(Calendar.DAY_OF_MONTH,1);
        return c.getTime();
    }
	/**
     * 根据gameKey生成一天一次的游戏场次编号。如DHS_BREAK_MODEL_2018_07_14_10_34
     *
     * @param gameKey
     * @return
     */
    public static String dailyGameNo(String gameKey) {
        String dateFormat = "YYYY_MM_dd_HH_mm";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return gameKey + "_" + sdf.format(new Date());
    }
    
    
    public static void main(String[] args) {
    	String dateFormat = "YYYY_MM_dd_HH_mm";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		System.out.println(sdf.format(getEndTime("15:18")));
	}
}
