package com.trendcote.web.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    private static String defaultDatePattern = "yyyy-MM-dd HH:mm:ss";

    /**
     * 获得默认的 date pattern
     */
    public static String getDatePattern() {
        return defaultDatePattern;
    }

    /**
     * 返回预设Format的当前日期字符串
     */
    public static String getToday() {
        Date today = new Date();
        return format(today);
    }

    /**
     * 使用预设Format格式化Date成字符串
     */
    public static String format(Date date) {
        return date == null ? " " : format(date, getDatePattern());
    }

    /**
     * 使用参数Format格式化Date成字符串
     */
    public static String format(Date date, String pattern) {
        return date == null ? " " : new SimpleDateFormat(pattern).format(date);
    }

    /**
     * 使用预设格式将字符串转为Date
     */
    public static Date parse(String strDate) throws ParseException {
        return DataUtil.isStirngBlank(strDate) ? null : parse(strDate,
                getDatePattern());
    }

    /**
     * 使用参数Format将字符串转为Date
     */
    public static Date parse(String strDate, String pattern)
            throws ParseException {
        return DataUtil.isStirngBlank(strDate) ? null : new SimpleDateFormat(
                pattern).parse(strDate);
    }

    /**
     * 在日期上增加数个整月
     */
    public static Date addMonth(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, n);
        return cal.getTime();
    }

    public static Date getDate(String year, String month, String day)
            throws ParseException {
        String result = year + "- "
                + (month.length() == 1 ? ("0 " + month) : month) + "- "
                + (day.length() == 1 ? ("0 " + day) : day);
        return parse(result);
    }

    /**
     * 月第一天
     *
     * @param nowDate
     * @param format
     * @return
     * @throws ParseException
     */
    public static Date getFirstDayOfMonth(Date nowDate, String format) throws ParseException {
        Calendar cal = Calendar.getInstance();//获取当前日期
        cal.setTime(nowDate);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return parse(format(cal.getTime(), format), format);
    }

    /**
     * 相对当前日期几个月
     *
     * @param nowDate
     * @param index
     * @return
     */
    public static Date getHalfYear(Date nowDate, int index) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(nowDate);
        cal.add(Calendar.MONTH, index);
        return cal.getTime();
    }

    /**
     * 昨天
     *
     * @param nowDate
     * @return
     */
    public static Date getYesterday(Date nowDate) {

        Calendar cal = Calendar.getInstance();//获取当前日期

        cal.setTime(nowDate);
        cal.add(Calendar.DAY_OF_MONTH, -1);//-1今天的时间减一天
        return cal.getTime();
    }

    public static Date getAllMonth(String format, int year, int month) throws ParseException {
        Calendar dd = Calendar.getInstance();//定义日期实例
        dd.set(Calendar.YEAR, year);
        dd.set(Calendar.MONTH, month);
        Date date = dd.getTime();
        String str = format(date, format);
        return parse(str, format);
    }

    /**
     * 获取年份
     *
     * @return
     */
    public static int getYear() {
        Calendar dd = Calendar.getInstance();//定义日期实例
        return dd.get(Calendar.YEAR);
    }

    /**
     * 获取月份时间
     *
     * @param date
     * @param format
     * @param index
     * @return
     * @throws ParseException
     */
    public static Date getDayOfMOnth(Date date, String format, int index) throws ParseException {
        Calendar dd = Calendar.getInstance();//定义日期实例
        dd.setTime(date);
        dd.add(Calendar.DAY_OF_YEAR, index);
        date = dd.getTime();
        String str = format(date, format);
        return parse(str, format);
    }

    /**
     * 获取某月的最后一天
     *
     * @throws
     * @Title:getLastDayOfMonth
     * @Description:
     * @param:@param year
     * @param:@param month
     * @param:@return
     * @return:String
     */
    public static Date getLastDayOfMonth(int year, int month) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        // 设置时间,当前时间不用设置
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month-1);

        // System.out.println(calendar.getTime());

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        String str = format(calendar.getTime(),"yyyy-MM-dd")+" 23:59:59";
        return parse(str,"yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 获取年份
     *
     * @return
     */
    public static Date getYear(int year) {
        Calendar dd = Calendar.getInstance();//定义日期实例
        dd.set(Calendar.YEAR, year);
        return dd.getTime();
    }

    public static Date getYearAndMonth(int year, int month) {
        Calendar dd = Calendar.getInstance();//定义日期实例
        dd.set(Calendar.YEAR, year);
        dd.set(Calendar.MONTH, month);
        return dd.getTime();
    }

    /**
     * 获取某月天数
     */
    public static int getDayNumByMonth(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 比较两个日期间差天数
     */
    public static int differentDays(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2)   //同一年
        {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0)    //闰年
                {
                    timeDistance += 366;
                } else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2 - day1);
        } else    //不同年
        {
            System.out.println("判断day2 - day1 : " + (day2 - day1));
            return day2 - day1;
        }
    }

    /**
     * 获取某年某月
     *
     * @param year
     * @param month
     * @return
     */
    public static Date getFisrtDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR, year);
        //设置月份
        cal.set(Calendar.MONTH, month - 1);
        //获取某月最小天数
        int firstDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最小天数
        cal.set(Calendar.DAY_OF_MONTH, firstDay);
        //格式化日期
        return cal.getTime();
    }

    public static void main(String[] args) throws ParseException {
        System.out.println(format(getFisrtDayOfMonth(2018,3),"yyyy-MM-dd HH:mm:ss"));
        System.out.println(format(getLastDayOfMonth(2008,4),"yyyy-MM-dd HH:mm:ss"));
    }
}
