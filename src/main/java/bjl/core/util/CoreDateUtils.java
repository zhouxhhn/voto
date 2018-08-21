package bjl.core.util;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by pengyi on 2016/3/1.
 */
public class CoreDateUtils {
    private static final Logger log = LoggerFactory.getLogger(CoreDateUtils.class);

    public static final String DATETIME = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE = "yyyy-MM-dd";
    private static final String DATE_YYYYMMMDD = "yyyyMMdd";

    public static String formatDateYMD(){
        return formatDate(new Date(),DATE_YYYYMMMDD);
    }

    public static String getNowDate() {
        return getNowDate(DATETIME);
    }

    public static String getNowDate(String pattern) {
        return formatDate(new Date(), pattern);
    }

    public static String formatDate(Date date) {
        return formatDate(date, DATE);
    }

    public static String formatDateTime(Date date) {
        return formatDate(date, DATETIME);
    }

    public static String formatDate(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        return DateFormatUtils.format(date, pattern, Locale.CHINA);
    }

    public static String formatDate(String dateStr, String srcPattern, String desPattern) {
        Date date = parseDate(dateStr, srcPattern);
        if (date == null) {
            return null;
        }
        return formatDate(date, desPattern);
    }

    public static Date parseDate(String dateStr) {
        return parseDate(dateStr, DATE);
    }

    public static Date parseLongDate(String dateStr) {
        return parseDate(dateStr, new String[]{
                DATETIME,
                "yyyy-MM-dd HH:mm:ss.SSS",
        });
    }

    public static Date parseDate(String dateStr, String pattern) {
        return parseDate(CoreStringUtils.trim(dateStr), new String[]{pattern});
    }

    public static Date parseDate(String dateStr, String[] patterns) {
        if (dateStr == null) {
            return null;
        }
        try {
            return DateUtils.parseDateStrictly(dateStr, patterns);
        } catch (ParseException e) {
            log.error("日期转换错误, dateStr={}, pattern={}", dateStr, CoreStringUtils.join(",", (Object[]) patterns));
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public static Date parseDateStart(String dateStr) {
        if (dateStr == null) {
            return null;
        }
        dateStr += " 00:00:00";
        return parseDate(dateStr, DATETIME);
    }

    public static Date parseDateEnd(String dateStr) {
        if (dateStr == null) {
            return null;
        }
        dateStr += " 23:59:59";
        return parseDate(dateStr, DATETIME);
    }

    /**
     * 两个时间相隔天数 time1-time2
     *
     * @param time1
     * @param time2
     * @return
     */
    public static long diffDays(Date time1, Date time2) {
        if (time1 == null || time2 == null) {
            return 0;
        }
        return (time1.getTime() - time2.getTime()) / 1000 / 60 / 60 / 24;
    }

    /**
     * 两个时间的间隔天数
     * @param newTime
     * @param oldTime
     * @return
     */
    public static int intervalDays(Date newTime, Date oldTime){
        Calendar calNew = Calendar.getInstance();
        calNew.setTime(newTime);

        Calendar calOld = Calendar.getInstance();
        calOld.setTime(oldTime);
        int dayNew= calNew.get(Calendar.DAY_OF_YEAR);
        int dayOld = calOld.get(Calendar.DAY_OF_YEAR);

        int yearNew = calNew.get(Calendar.YEAR);
        int yearOld = calOld.get(Calendar.YEAR);
        if(yearNew != yearOld) //不是同一年
        {
            int timeDistance = 0 ;
            for(int i = yearOld ; i < yearNew ; i ++)
            {
                if(i%4==0 && i%100!=0 || i%400==0) //闰年
                {
                    timeDistance += 366;
                }
                else //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (dayNew-dayOld) ;
        }
        else //同年
        {
            return dayNew-dayOld;
        }
    }

    /**
     * 一个日期对象＋ n天
     *
     * @param date 日期
     * @param day  天数
     * @return
     */
    public static Date addDay(Date date, int day) {
        return DateUtils.addDays(date, day);
    }

    /**
     * 判断是不是同一天
     *
     * @param src
     * @param des
     * @return
     */
    public static boolean isSameDay(Date src, Date des) {
        return DateUtils.isSameDay(src, des);
    }
}
