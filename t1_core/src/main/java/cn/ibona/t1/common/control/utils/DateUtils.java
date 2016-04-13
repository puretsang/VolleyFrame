package cn.ibona.t1.common.control.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2015/9/16+
 */
public class DateUtils {

    /**
     * 根据毫秒值返回指定日期格式字符串
     *
     * @param mills 毫秒值
     * @param simpleDateFormat
     * @return 返回日期格式  simpleDateFormat指定的日期字符串
     */
    public static String getFromMills(long mills,String simpleDateFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(simpleDateFormat);
        Date date = new Date(mills);
        return formatter.format(date);
    }
    /**
     * 根据毫秒值返回指定日期格式字符串
     *
     * @param mills 毫秒值
     * @return 返回日期格式  yyyy-MM-dd的日期字符串
     */
    public static String get_yyyy_MM_dd_FromMills(long mills) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(mills);
        return formatter.format(date);
    }

    /**
     * 根据毫秒值返回指定日期格式字符串
     *
     * @param mills 毫秒值
     * @return 返回日期格式  yyyy年MM月dd日的日期字符串
     */
    public static String get_yyyy_MM_DD_FromMills(long mills) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = new Date(mills);
        return formatter.format(date);
    }
    /**
     * 根据毫秒值返回指定日期格式字符串
     *
     * @param mills 毫秒值
     * @return 返回日期格式  yyyy年MM月的日期字符串
     */
    public static String get_YYYY_MM_FromMills(long mills) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月");
        Date date = new Date(mills);
        return formatter.format(date);
    }
    /**
     * 根据毫秒值返回指定日期格式字符串
     *
     * @param mills 毫秒值
     * @return 返回日期格式  yyyy-MM的日期字符串
     */
    public static String get_yyyy_mm_FromMills(long mills) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        Date date = new Date(mills);
        return formatter.format(date);
    }
    /**
     * 根据毫秒值返回指定日期格式字符串
     *
     * @param mills 毫秒值
     * @return 返回日期格式  yyyy年期字符串
     */
    public static String get_YYYY_FromMills(long mills) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年");
        Date date = new Date(mills);
        return formatter.format(date);
    }

    /**
     * 根据毫秒值返回指定日期格式字符串
     *
     * @param mills 毫秒值
     * @return 返回日期格式  MM-dd的日期字符串
     */
    public static String get_MM_dd_FromMills(long mills) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd");
        return formatter.format(mills);
    }
    /**
     * 根据毫秒值返回指定日期格式字符串
     *
     * @param mills 毫秒值
     * @return 返回日期格式  MM月dd日的日期字符串
     */
    public static String get_MM_DD_FromMills(long mills) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM月dd日");
        return formatter.format(mills);
    }

    /**
     * 根据毫秒值返回指定日期格式字符串
     *
     * @param mills 毫秒值
     * @return 返回日期格式  HH:mm的日期字符串
     */
    public static String get_HH_mm_FromMills(long mills) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        return formatter.format(mills);
    }




    /**
     * 判断时候今年的返回
     * @param mytime
     * @return
     */
    public static String isCurrentYear(String mytime) {
        long mills = Long.parseLong(mytime);
        String year=get_YYYY_FromMills(mills*1000);
        String currentYear=get_YYYY_FromMills(System.currentTimeMillis());
        if (!currentYear.equals(year)){
            return year;
        }
        return "";
    }


    /**
     * 提交时间的时间戳转换
     * @param mytime
     * @return
     */
    public static String getCommitTime(String mytime) {
        long mills = Long.parseLong(mytime);
        if (!"".equals(isCurrentYear(mytime))){
            return get_yyyy_MM_dd_FromMills(mills*1000)+" "+get_HH_mm_FromMills(mills*1000);
        }
        //在新建评论时返回的时间时差为0，为了保证除数>0时差+1
        long diff = (System.currentTimeMillis() / 1000) - mills;
        while (diff<=0){
            diff++;
        }
        //  提交时间 正常是mm-dd HH:mm；超过1年，显示“yyyy-mm-dd HH:mm"

        int[]time={60,3600,86400,604800,31536000,(int)mills} ;
        String[] data={"刚刚","分钟前","小时前","天前","一周之后","超过1年"};

        for (int i = 0; i <time.length ; i++) {
            if((time[i]/diff)!=0){
                switch (i) {
                    case 5:
                        return get_yyyy_MM_dd_FromMills(mills*1000)+" "+get_HH_mm_FromMills(mills*1000);
                    default:
                        return get_MM_dd_FromMills(mills *1000)+" "+get_HH_mm_FromMills(mills*1000);
                }
            }
        }
        return null;
    }
    /**
     * 评论时间的时间戳转换
     * @param mytime
     * @return
     */
    public static String getTime(String mytime) {
        long mills = Long.parseLong(mytime);
        if (!"".equals(isCurrentYear(mytime))){
            return get_yyyy_MM_dd_FromMills(mills*1000)+" "+get_HH_mm_FromMills(mills*1000);
        }
        //在新建评论时返回的时间时差为0，为了保证除数>0时差+1
        long diff = (System.currentTimeMillis() / 1000) - mills;
        while (diff<=0){
            diff++;
        }
        //  评论时间在1分钟内显示“刚刚”，之后为“2~59分钟前”“1~23小时前”“2~7天前”一周之后，按日期“xx-xx”， 超过1年，显示“yyyy-mm-dd

        int[]time={60,3600,86400,604800,31536000,(int)mills} ;
        String[] data={"刚刚","分钟前","小时前","天前","一周之后","超过1年"};

        for (int i = 0; i <time.length ; i++) {
            if((time[i]/diff)!=0){
                switch (i) {
                    case 0:
                        return data[i];
                    case 4:
                        return get_MM_dd_FromMills(mills*1000);
                    case 5:
                        return get_yyyy_MM_dd_FromMills(mills*1000);
                    default:
                        return (diff/time[i-1])+data[i];
                }
            }
        }
        return null;
    }

    /**
     *把 yyyy年MM月dd日 转换成时间戳
     * 精确到秒级别
     * @param date
     * @return
     */
    public static long get_long_fromDate(String date) throws ParseException {
        date = date + " 00:00:00";
        DateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        long second = (formatter.parse(date).getTime())/1000;
        return second;
    }
    /**
     *把 yyyy-MM-dd 转换成时间戳
     * 精确到秒级别
     * @param date
     * @return
     */
    public static long getLongFromDate(String date) throws ParseException {
        date = date + " 00:00:00";
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long second = (formatter.parse(date).getTime())/1000;
        return second;
    }
    /**
     *把 yyyy-MM-dd HH:mm:ss 转换成时间戳
     * 精确到秒级别
     * @param date
     * @return
     */
    public static long getLongFromActivityDate(String date) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long second = (formatter.parse(date).getTime())/1000;
        return second;
    }

    /**
     * x正数为获取往后num天的日期
     * num负数为获取往前num天的日期
     * @param x
     * @return
     * @throws ParseException
     */
    public static long guessByDate(int x) throws ParseException {
        Calendar cal   =   Calendar.getInstance();
        cal.add(Calendar.DATE, x);
        DateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
        String guessByDate = df.format(cal.getTime());
        return get_long_fromDate(guessByDate);
    }

    /**
     * 传yyyy-MM-dd 或时间戳，判断是不是过期日期
     * @param date
     * @return
     * @throws ParseException
     */
    public static boolean isExpiryDate(String date)throws ParseException{
        if (date.contains("年") && get_long_fromDate(date)<getTodayBeginTimestamp()){
            return true;
        }else if (Long.parseLong(date)<getTodayBeginTimestamp()){
            return true;
        }
        return false;
    }

    /**
     *  当天0点时间戳
     * @return
     * @throws ParseException
     */
    public static long getTodayBeginTimestamp() throws ParseException {
        return getLongFromDate(get_yyyy_MM_dd_FromMills(System.currentTimeMillis()));
    }

    /**
     * 时间不能早于当前时间
     * @param date
     * @return
     * @throws ParseException
     */

    public static boolean isExceedCurrentTime(String date) throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日");
        Date a = sdf.parse(date);
        Date currentDate = new Date();
        if(currentDate.before(a)){
            return true;
        }
        return false;
    }

    /**
     * 获取上一周时间
     * */
    public static long getLastWeekEndTimestamp(Calendar calendar){
        calendar.add(Calendar.DAY_OF_WEEK,-7);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTimeInMillis() / 1000;
    }

    /**
     * 获取上一个月时间
     * */
    public static long getLastMonthEndTimestamp(Calendar calendar){
        calendar.add(Calendar.MONTH,-1);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTimeInMillis() / 1000;
    }

    /**
     * 获取这个星期的星期一
     * */
    public static long getThisWeekMondayTimestamp(Calendar calendar) {
        calendar.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        return calendar.getTimeInMillis() / 1000;
    }

    /**
     * 获取这个本月的第一天
     * */
    public static long getThisMonthFirstDayTimestamp(Calendar calendar){
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        return calendar.getTimeInMillis() / 1000;
    }


    /***
     * 获取当前时间戳
     */
    public static long getCurrentTimestamp() {
        return Calendar.getInstance().getTimeInMillis() / 1000;
    }

}
