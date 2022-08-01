package com.example.demo.util;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 时间工具类
 *
 * @author xiaoyang
 * @date 2022/7/23
 */
@Slf4j
public class DateUtil {

    /**
     * 将时间转换为日期格式不带分隔符号
     * 例: 20201030
     *
     * @param date 需要转换的时间
     * @return 日期
     */
    public static String getDateStr(Date date) {
        return toStr(date, "yyyyMMdd");
    }

    /**
     * 将时间转为Str串
     *
     * @param date 时间
     * @param form 转换模板
     * @return 串
     */
    public static String toStr(Date date, String form) {
        String str = "";
        try {
            str = new SimpleDateFormat(form).format(date);
        } catch (Exception e) {
            log.error("时间格式转换失败: {}", e.getMessage());
        }
        return str;
    }

    /**
     * 获取日期
     *
     * @param date 带时间的date
     * @return 不带时间的
     */
    public static Date getDate(Date date) {
        String form = "yyyy-MM-dd HH:mm:ss";
        try {
            return new SimpleDateFormat(form).parse(toStr(date, "yyyy-MM-dd") + " 00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 获取今天星期几
     * 星期天为1
     * ...
     * 星期六为7
     *
     * @return int
     */
    public static int getWeekInt() {
        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date());
        return instance.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 增加日期的天数。失败返回null。
     *
     * @param date      日期
     * @param dayAmount 增加数量。可为负数
     * @return 增加天数后的日期
     */
    public static Date addDay(Date date, int dayAmount) {
        return addInteger(date, dayAmount);
    }

    /**
     * 减少日期的天数。失败返回null
     */
    public static Date reduceDay(Date date, int dayAmount) {
        return addInteger(date, dayAmount);
    }

    /**
     * 增加日期中某类型的某数值。如增加日期
     *
     * @param date   日期
     * @param amount 数值
     * @return 计算后日期
     */
    private static Date addInteger(Date date, int amount) {
        Date myDate = null;
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DATE, amount);
            myDate = calendar.getTime();
        }
        return myDate;
    }

    public static void main(String[] args) {
        
    }

    public Map<String, String> weekDateMap() {
        HashMap<String, String> weekDateMap = new HashMap<>();
        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cld = Calendar.getInstance(Locale.CHINA);
        //以周一为首日
        cld.setFirstDayOfWeek(Calendar.MONDAY);
        cld.setTimeInMillis(System.currentTimeMillis());
        //周一
        cld.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        weekDateMap.put("Mon", df.format(cld.getTime()));
        cld.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
        weekDateMap.put("Tue", df.format(cld.getTime()));
        cld.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
        weekDateMap.put("Wed", df.format(cld.getTime()));
        cld.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
        weekDateMap.put("Thu", df.format(cld.getTime()));
        cld.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        weekDateMap.put("Sat", df.format(cld.getTime()));
        cld.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        weekDateMap.put("Fri", df.format(cld.getTime()));
        //周日
        cld.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        weekDateMap.put("Sun", df.format(cld.getTime()));

        return weekDateMap;
    }
}
