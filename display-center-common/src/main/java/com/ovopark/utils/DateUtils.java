package com.ovopark.utils;

import com.ovopark.constants.CommonConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author jeecg-boot
 * @version 1.0
 * @description 描述
 * @create 2019-11-25 17:28
 */
public class DateUtils {


    /**
     * 英文简写（默认）如：2010-12-01 23:15
     */
    public static String FORMAT_NO_SECNOD = "yyyy-MM-dd HH:mm";
    /**
     * 英文简写（默认）如：2010-12-01
     */
    public static String FORMAT_SHORT = "yyyy-MM-dd";
    /**
     * 英文全称 如：23:15:06
     */
    public static String FORMAT_TIME = "HH:mm:ss";
    /**
     * 英文全称 如：23
     */
    public static String FORMAT_TIME_SHORT = "HH";
    /**
     * 英文全称 如：2010-12-01 23:15:06
     */
    public static String FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";
    /**
     * 英文斜杠全称 如：2010/12/01 23:15:06
     */
    public static String FORMAT_LONG_SLASH = "yyyy/MM/dd HH:mm:ss";
    /**
     * 英文斜杠简称 如：2010/12/01
     */
    public static String FORMAT_LONG_SECNOD = "yyyy/MM/dd";
    /**
     * 英文斜杠简称 如：12/01
     */
    public static String FORMAT_LONG_SECNOD_DAY = "MM/dd";
    /**
     * 精确到毫秒的完整时间 如：yyyy-MM-dd HH:mm:ss.S
     */
    public static String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss.S";
    /**
     * 中文简写 如：2010年12月01日
     */
    public static String FORMAT_SHORT_CN = "yyyy年MM月dd日";
    /**
     * 中文全称 如：2010年12月01日 23时15分06秒
     */
    public static String FORMAT_LONG_CN = "yyyy年MM月dd日  HH时mm分ss秒";
    /**
     * 精确到毫秒的完整中文时间
     */
    public static String FORMAT_FULL_CN = "yyyy年MM月dd日  HH时mm分ss秒SSS毫秒";

    /**
     * DataV需求的日期格式
     */
    public static String FORMAT_DATAV = "yyyy/MM/dd HH:mm:ss";

    /**
     * 英文型简写 01/10/2018 (2018年10月01日)
     */
    public static String FORMAT_SHORT_EN = "dd/MM/yyyy";

    /**
     * 用连字符-分隔的时间格式串，如yy-MM
     */
    public static final String LINK_DISPLAY_DATE_MONTH_SHORT = "yy-MM";
    /**
     * 毫秒和天的换算
     */
    public static final int ONE_DAY_TO_MS = 24 * 3600 * 1000;

    public static final int FORMAT_WEEK_CN = 1;
    public static final int FORMAT_WEEK = 0;


    public static final int TIME_MINUTE = 0;
    public static final int TIME_HOUR = 1;
    public static final int TIME_DAY = 2;
    public static final int TIME_WEEK = 3; // 周统计
    public static final int TIME_MONTH = 4;
    public static final int TIME_QUARTER = 5;
    public static final int TIME_YEAR = 6;
    public static final int WEEK_DAY = 7;  // 周日，周一 ...
    public static final int TIME_WEEK_FIRST_DAY = 8;  // 周的第一天
    public static final int TIME_DAY_FULL = 9;
    public static final int TIME_DATE= 10;
    public static final int TIME_MONTH_FULL= 11;
    public static final int TIME_QUARTER_FULL= 12;
    public static final int TIME_YEAR_FULL= 13;


    public static final String DEFAULT_TIME="1970-01-01 00:00:00";
    /**
     * 获得默认的 date pattern
     */
    public static String getDatePattern() {
        return FORMAT_LONG;
    }

    /**
     * 根据预设格式返回当前日期
     *
     * @return
     */
    public static String getNow() {
        return format(new Date());
    }

    /**
     * 根据用户格式返回当前日期
     *
     * @param format
     * @return
     */
    public static String getNow(String format) {
        return format(new Date(), format);
    }

    /**
     * 使用预设格式格式化日期
     *
     * @param date
     * @return
     */
    public static String format(Date date) {
        return format(date, getDatePattern());
    }

    /**
     * 使用用户格式格式化日期
     *
     * @param date
     *            日期
     * @param pattern
     *            日期格式
     * @return
     */
    public static String format(Date date, String pattern) {
        String returnValue = "";
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            returnValue = df.format(date);
        }
        return (returnValue);
    }

    /**
     * 使用预设格式提取字符串日期
     *
     * @param strDate
     *            日期字符串
     * @return
     */
    public static Date parse(String strDate) {
        return parse(strDate, getDatePattern());
    }

    /**
     * 取得当前日期所在周的第一天(周一为起始天)
     * @param date
     * @return
     * @author huanglt
     */
    public static Date getFirstDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK,
                calendar.getFirstDayOfWeek()); // Sunday
        return calendar.getTime();
    }

    /**
     * 取得当前日期所在周的最后一天(周一为起始天)
     * @param date
     * @return
     * @author huanglt
     */
    public static Date getLastDayOfWeek(Date date) {
        Date d = getFirstDayOfWeek(date);

        return DateUtils.addDay(d, 6);
    }


    /**
     * 取得当前日期所在月的第一天
     * @param date
     * @return
     */
    public static Date getFirstDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), 1);
        return getStartTimeOfDay(calendar.getTime());
    }

    /**
     * 取得当前日期所在月的最后一天
     * @param date
     * @return
     */
    public static Date getLastDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.getActualMaximum(Calendar.DATE));
        return getEndTimeOfDay(calendar.getTime());
    }

    /**
     * 使用用户格式提取字符串日期
     *
     * @param strDate
     *            日期字符串
     * @param pattern
     *            日期格式
     * @return
     */
    public static Date parse(String strDate, String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        try {
            return df.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 在日期上增加数个整年
     *
     * @param date
     *            日期
     * @param n
     *            要增加的年数
     * @return
     */
    public static Date addYear(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, n);
        return cal.getTime();
    }

    /**
     * 在日期上增加数个整月
     *
     * @param date
     *            日期
     * @param n
     *            要增加的月数
     * @return
     */
    public static Date addMonth(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, n);
        return cal.getTime();
    }

    /**
     * 在日期上增加天数
     *
     * @param date
     *            日期
     * @param n
     *            要增加的天数
     * @return
     */
    public static Date addDay(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, n);
        return cal.getTime();
    }

    public static Date addHour(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, n);
        return cal.getTime();
    }

    public static Date addMinute(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, n);
        return cal.getTime();
    }

    public static Date addSecond(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.SECOND, n);
        return cal.getTime();
    }

    /**
     * 获取时间戳
     */
    public static String getTimeString() {
        SimpleDateFormat df = new SimpleDateFormat(FORMAT_FULL);
        Calendar calendar = Calendar.getInstance();
        return df.format(calendar.getTime());
    }

    /**
     * 获取日期年份
     *
     * @param date
     *            日期
     * @return
     */
    public static String getYear(Date date) {
        return format(date).substring(0, 4);
    }

    /**
     * 2天之间的天数(基础方法)
     * @param
     * @return
     */
    public static int countDays(Date date1,Date date2) {
        int days = (int) ((date2.getTime() - date1.getTime()) / ONE_DAY_TO_MS);
        return days;
    }

    /**
     * 按默认格式的字符串距离今天的天数
     *
     * @param date
     *            日期字符串
     * @return
     */
    public static int countDays(String date) {
        return countDays(date, getDatePattern());
    }

    /**
     * 在指定日期的基础上添加天数，返回添加后的日期
     *
     * @param date
     * @param days
     * @return
     */
    public static Date addDays(Date date, int days) {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.setTime(date);
        todayEnd.add(Calendar.DATE, days);
        return todayEnd.getTime();
    }

    /**
     * 按用户格式字符串距离今天的天数
     *
     * @param date
     *            日期字符串
     * @param format
     *            日期格式
     * @return
     */
    public static int countDays(String date, String format) {
        Date t = Calendar.getInstance().getTime();
        Calendar c = Calendar.getInstance();
        c.setTime(parse(date, format));
        Date t1 = c.getTime();
        return countDays(t1, t);
    }

    /**
     * 获取一天的开始时间
     * @param date
     * @return
     */
    public static Date getStartTimeOfDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取一天的开始时间
     * @param startTime
     * @param pattern
     * @return
     */
    public static Date getZoneTimeOfDay(String startTime,String pattern) {
        if(!org.springframework.util.StringUtils.isEmpty(startTime)){
            Calendar cal = Calendar.getInstance();
            cal.setTime(parse(startTime, pattern));
            cal.set(Calendar.HOUR_OF_DAY, 8);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            return cal.getTime();
        }else{
            return null;
        }
    }



    /**
     * 获取一天的开始时间
     * @param startTime
     * @param pattern
     * @return
     */
    public static Date getStartTimeOfDay(String startTime,String pattern) {
        if(!org.springframework.util.StringUtils.isEmpty(startTime)){
            Calendar cal = Calendar.getInstance();
            cal.setTime(parse(startTime, pattern));
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            return cal.getTime();
        }else{
            return null;
        }
    }
    public static String getStartTimeOfDay(Calendar cal) {
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_LONG_SLASH);
        return  sdf.format(cal.getTime());
    }

    public static Date getStartOfDay(Calendar cal) {
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return  cal.getTime();
    }

    /**
     * 获取一天的结束时间
     * @param date
     * @return
     */
    public static Date getEndTimeOfDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND,0);
        return cal.getTime();
    }

    /**
     * 获取一天的结束时间
     * @param endTime
     * @param pattern
     * @return
     */
    public static Date getEndTimeOfDay(String endTime,String pattern) {
        if(!org.springframework.util.StringUtils.isEmpty(endTime)){
            Calendar cal = Calendar.getInstance();
            cal.setTime(parse(endTime, pattern));
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            cal.set(Calendar.MILLISECOND,0);
            return cal.getTime();
        }else{
            return null;
        }
    }

    /**
     * 获取一天的结束时间
     * @param cal
     * @return
     */
    public static String getEndTimeOfDay(Calendar cal) {
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_LONG_SLASH);
        return  sdf.format(cal.getTime());
    }

    /**
     * 获取从输入日期，到今天为止的，每天的日期列表
     * @param startDate 开始日期
     */
    public static List<Date> getDateListUntilToday(Date startDate) {
        List<Date> result = new ArrayList<>();
        Date start = getStartTimeOfDay(startDate);
        long startTime = start.getTime();
        int days = countDays(start, new Date());
        for (int i = 0; i < days; i++) {
            if(i > 0)
                startTime += ONE_DAY_TO_MS;
            result.add(new Date(startTime));
        }

        return result;
    }



    /**
     * 获取两个时间点之间的日期
     * @param dBegin	包括开始日期
     * @param dEnd	包括结束日期
     * @return
     */
    public static List<Date> findDates(Date dBegin, Date dEnd)
    {
        List<Date> lDate = new ArrayList<Date>();
        lDate.add(dBegin);
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime()))
        {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            lDate.add(calBegin.getTime());
        }
        return lDate;
    }

    /**
     * 获取两个时间点之间的日期
     * @param dBegin 包括开始日期
     * @param dEnd	 包括结束日期
     * @return
     */
    public static List<String> findDatesContainStartAndEnd(Date dBegin, Date dEnd, String format)
    {
        List<String> lDate = new ArrayList<>();
        lDate.add(format(dBegin, format));

        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后

//	  the value 0 if the argument Date is equal to this Date;
//	  a value less than 0 if this Date is before the Date argument;
//	  and a value greater than 0 if this Date is after the Date argument.
        while (dEnd.after(calBegin.getTime()))
        {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            lDate.add(format(calBegin.getTime(), format));
        }
        return lDate;
    }

    /**
     * 获取当前日期是星期几<br> (星期天，返回0)
     * @param dt
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate(Date dt) {
        return getWeekOfDate(dt, FORMAT_WEEK_CN);
    }

    public static String getWeekOfDate(Date dt, int type) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        if(type == FORMAT_WEEK_CN) {
            return weekDays[w];
        }else {
            return w+"";
        }
    }

    public static int getMonth(Date dt) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.MONTH) + 1;
        return w;
    }

    public static String getDayOfMonth(Date dt) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_MONTH);
        return w + "";
    }

    public static Date changeStr2StartDate(String time){
        if(org.springframework.util.StringUtils.isEmpty(time)) return null;
        return getStartTimeOfDay(changeStr2Date(time));
    }

    public static Date changeStr2EndDate(String time){
        if(org.springframework.util.StringUtils.isEmpty(time)) return null;
        return getEndTimeOfDay(changeStr2Date(time));
    }

    public static Date changeStr2Date(String time) {
        if(org.springframework.util.StringUtils.isEmpty(time)) return null;

        final int shortLen = FORMAT_SHORT.length();
        final int longLen = FORMAT_LONG.length();
        final int noSecondLen = FORMAT_NO_SECNOD.length();

        if(time.length() == shortLen) {
            return DateUtils.parse(time, DateUtils.FORMAT_SHORT);
        }else if(time.length() == longLen){
            return DateUtils.parse(time, DateUtils.FORMAT_LONG);
        }else if(time.length() == noSecondLen) {
            return DateUtils.parse(time, DateUtils.FORMAT_NO_SECNOD);
        }else if(time.length() == 13) {
            return new Date(Long.valueOf(time));
        }

        return null;
    }

    public static int getQuarterOfYear(Date date){
        return (getMonth(date) - 1) / 3 + 1;
    }

    public static String getQuarter(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        switch (month) {
            case 0:
            case 1:
            case 2:
                return year+ "Q1";
            case 3:
            case 4:
            case 5:
                return year+ "Q2";
            case 6:
            case 7:
            case 8:
                return year+ "Q3";
            case 9:
            case 10:
            case 11:
                return year+ "Q4";

            default:
                return "";
        }
    }

    /**
     * 获取日期在第几周（周一起始）
     * @param date
     * @return
     */
    public static int getWeekOfYear (Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);
        int result = calendar.get(Calendar.WEEK_OF_YEAR);
        if (result == 1 && date.after(DateUtils.parse(DateUtils.getYear(date) + "-12-01", "yyyy-MM-dd"))) {
            calendar.setTime(DateUtils.addDay(date, -7));
            result = calendar.get(Calendar.WEEK_OF_YEAR) + 1;
        }
        return result;
    }

    /**
     * 根据第几周获取周起始、结束时间
     * @param year
     * @param i
     * @return
     */
    public static String getDatePeriodByWeekOfYear (int year, int i) {
        Calendar cal=Calendar.getInstance();

        // 设置每周的开始日期
        cal.setFirstDayOfWeek(Calendar.MONDAY);

        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.WEEK_OF_YEAR, i);

        SimpleDateFormat sdf=new SimpleDateFormat(FORMAT_SHORT);

        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        String beginDate = sdf.format(cal.getTime());

        cal.add(Calendar.DAY_OF_WEEK, 6);
        String endDate = sdf.format(cal.getTime());

        return beginDate + "~" + endDate;
    }

    public static Date getYearBeginDate (int year) {
        Calendar cal=Calendar.getInstance();
        cal.set(year, 0, 1, 0, 0, 0);
        SimpleDateFormat sdf=new SimpleDateFormat(FORMAT_SHORT);
        String beginDate = sdf.format(cal.getTime());
        return DateUtils.parse(beginDate, FORMAT_SHORT);
    }

    public static Date getYearEndDate (int year) {
        Calendar cal=Calendar.getInstance();
        cal.set(year, 11, 31, 0 ,0 ,0);
        SimpleDateFormat sdf=new SimpleDateFormat(FORMAT_SHORT);
        String beginDate = sdf.format(cal.getTime());
        return DateUtils.getEndTimeOfDay(DateUtils.parse(beginDate, FORMAT_SHORT));
    }

    /**
     * 获取上一日
     * @param date
     * @return
     * @author huanglt
     */
    public static Date getBeforeDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1);
        date = calendar.getTime();
        return date;
    }


    /**
     * 获取参数日期的上月第一天
     * @param date
     * @return
     */
    public static Date getLastMonthFirstDay(Date date){
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return getStartTimeOfDay(calendar.getTime());
    }

    /**
     * 获取参数日期的上月最后一天
     * @param date
     * @return
     */
    public static Date getLastMonthLastDay(Date date){
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        int month=calendar.get(Calendar.MONTH);
        calendar.set(Calendar.MONTH, month-1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return getEndTimeOfDay(calendar.getTime());
    }

    /**
     * 获取两时间范围内的数据
     * @param stime
     * @param etime
     * @param timeType
     * @return
     * @throws
     * @author huanglt
     */
    public static List<String> findAllTimesInTwoDates( String stime, String etime, Integer timeType) {
        List<String> times = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = sdf.parse(stime);
            endDate = sdf.parse(etime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(timeType==TIME_MONTH){
            startDate = getFirstDayOfMonth(startDate);
        }
        if(timeType==TIME_WEEK){
            startDate = getFirstDayOfWeek(startDate);
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        while(cal.getTime().before(endDate)) {
            String dateStr = sdf.format(cal.getTime());
            String timeStr = getShowTimeStr(dateStr, timeType);
            times.add(timeStr);
            switch(timeType) {
                case DateUtils.TIME_MINUTE:
                    cal.add(Calendar.MINUTE,1);
                    break;
                case DateUtils.TIME_HOUR:
                    cal.add(Calendar.HOUR,1);
                    break;
                case DateUtils.TIME_DAY:
                case DateUtils.TIME_DAY_FULL:
                case DateUtils.TIME_DATE:
                    cal.add(Calendar.DATE,1);
                    break;
                case DateUtils.TIME_WEEK_FIRST_DAY:
                case DateUtils.TIME_WEEK:
                    cal.add(Calendar.DATE,7);
                    break;
                case DateUtils.TIME_MONTH:
                case DateUtils.TIME_MONTH_FULL:
                    cal.add(Calendar.MONTH,1);
                    break;
                case DateUtils.TIME_QUARTER:
                    cal.add(Calendar.MONTH,3);
                    break;
                case DateUtils.TIME_YEAR:
                case DateUtils.TIME_YEAR_FULL:
                    cal.add(Calendar.YEAR,1);
            }
        }
        return times;
    }


    /**
     * 获取各种不同时间的显示格式
     * @param dateStr
     * @param timeType
     * @return
     * @author huanglt
     */
    public static String getShowTimeStr(String dateStr, int timeType) {
        String output = null;
        SimpleDateFormat sdf = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        switch (timeType) {
            case DateUtils.TIME_MINUTE:
                // 以分钟为单位
                output = dateStr.substring(11, 16);
                break;
            case DateUtils.TIME_HOUR:
                // 以小时为时间单位综合
                output = dateStr.substring(11, 16);
                break;
            case DateUtils.TIME_DAY:
                output = dateStr.substring(5, 10);
                break;
            case DateUtils.TIME_DAY_FULL:
                output = dateStr;
                break;
            case DateUtils.TIME_DATE:
                output = dateStr.substring(0, 10);
                break;
            case DateUtils.TIME_WEEK:
                if(cal.get(Calendar.WEEK_OF_YEAR)==1){
                    output = cal.get(Calendar.YEAR)+1 +"年第" + cal.get(Calendar.WEEK_OF_YEAR) + "周";
                }else{
                    output = cal.get(Calendar.YEAR) +"年第" + cal.get(Calendar.WEEK_OF_YEAR) + "周";
                }

                break;
            case DateUtils.TIME_MONTH:
                // 以月为单位综合
                int month = cal.get(Calendar.MONTH) + 1;
                String year = cal.get(Calendar.YEAR) + "";
                String monthStr = month < 10 ? "0" + month : "" + month;
                output =year.substring(2, year.length())+"-"+monthStr;
                break;
            case DateUtils.TIME_MONTH_FULL:
                // 以月为单位综合
                output = format(getFirstDayOfMonth(date),DateUtils.FORMAT_LONG);
                break;
            case DateUtils.TIME_QUARTER:
                output =  cal.get(Calendar.YEAR) +"年第" + (cal.get(Calendar.MONTH) / 3 + 1) + "季度";
                break;
            case DateUtils.TIME_YEAR:
                output = cal.get(Calendar.YEAR) + "";
                break;
            case DateUtils.TIME_YEAR_FULL:
                output = cal.get(Calendar.YEAR) + "";
                break;
            case DateUtils.TIME_WEEK_FIRST_DAY:
                // 以年为单位综合
                output = format(getFirstDayOfWeek(cal.getTime()),DateUtils.FORMAT_SHORT);
                break;
            default:
                // 以小时为时间单位综合
                output = dateStr.substring(11, 16);
        }
        return output;
    }

    /**
     * 获取时间差
     * @param stime
     * @param etime
     * @return
     * @author huanglt
     */
    public static long getTimeDif(Date stime,Date etime ) {
        return stime.getTime()-etime.getTime();
    }

    /**
     * 获取第二天八点半
     * @param time
     * @return
     */
    public static Date getNextDayWorkTime(Date time){
        Calendar c = Calendar.getInstance();
        c.setTime(time);
        c.add(Calendar.DAY_OF_MONTH, 1);
        return parse(format(c.getTime(),FORMAT_SHORT) + " 08:30:00");
    }
    /**
     * 得到格式化时间串
     *
     * @param date
     *            指定时间
     * @param formatStr
     *            时间格式的类型
     * @return 指定时间的格式化时间串
     * @author huanglt
     */
    public static String getDateStr(Date date, String formatStr) {
        SimpleDateFormat fomate = new SimpleDateFormat(formatStr);
        return fomate.format(date);
    }

    /**
     * 计算2个日期差
     * @param smdate
     * @param bdate
     * @return
     * @throws ParseException
     */
    public static Integer daysBetween(Date smdate,Date bdate){
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();

        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 获取两个时间点之间的日期
     * @param dBegin	包括开始日期
     * @param dEnd	包括结束日期
     * @return
     */
    public static List<Date> findDayTimes(Date dBegin, Date dEnd)
    {
        List<Date> lDate = new ArrayList<Date>();
        lDate.add(dBegin);
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime()))
        {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.HOUR_OF_DAY, 1);
            lDate.add(calBegin.getTime());
        }
        return lDate;
    }
    /**
     * 获取传入时间段的前一个相等时间段
     * @return
     */
    public static List<Date> getLastIntervalDate(Date startDate,Date endDate){
        List<Date> list=new ArrayList<>();
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.set(Calendar.SECOND,calendar.get(Calendar.SECOND)-1);
        Date preEndDate = calendar.getTime();
        Calendar c1=Calendar.getInstance();
        c1.setTime(startDate);
        Calendar c2=Calendar.getInstance();
        c2.setTime(endDate);
        long t3 = calendar.getTimeInMillis();
        long t1=c1.getTimeInMillis();
        long t2=c2.getTimeInMillis();
        long t4 = t3-(t2 - t1);
        Date preStartDate = new Date(t4);
        list.add(preStartDate);
        list.add(preEndDate);
        return list;
    }

    /**
     * 比较两个字符串的时间大小
     * @param sourceTime
     * @param targetTime
     * @param format
     * @return
     */
    public static Boolean compareTime(String sourceTime,String targetTime,String format ) throws ParseException {

        if(org.springframework.util.StringUtils.isEmpty(format)){
            format =FORMAT_LONG;
        }

        Date sourceDate = parse(sourceTime, format);

        Date targetDate = parse(targetTime, format);

        if(sourceDate==null ||targetDate ==null){
            throw new ParseException("解析时间错误",0);
        }

        return sourceDate.getTime()<targetDate.getTime();
    }


    public static Boolean compareTime(String sourceTime,Date targetTime,String format ) throws ParseException {

        if(StringUtils.isEmpty(format)){
            format =FORMAT_LONG;
        }

        Date sourceDate = parse(sourceTime, format);

        if(sourceDate==null ||targetTime ==null){
            throw new ParseException("解析时间错误",0);
        }

        return sourceDate.getTime()<targetTime.getTime();
    }

    public static Date StringToDate(String strDate, String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date;
        try {
            return date = sdf.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date getNowDate() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_SHORT);
            Date nowDate = sdf.parse(sdf.format(new Date()));
            return nowDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date getDateEnd(Date date) {
        String dateEndStr =  DateUtils.getDateStr(date, DateUtils.FORMAT_SHORT)+ " 23:59:59";
        return DateUtils.StringToDate(dateEndStr, DateUtils.FORMAT_SHORT);
    }

    /**
     * date转cron
     *
     * @param date
     * @return
     */
    public static String dateToCron(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("ss mm HH dd MM ? yyyy");
        String formatTimeStr = null;
        if (Objects.nonNull(date)) {
            formatTimeStr = sdf.format(date);
        }
        return formatTimeStr;
    }





}