package com.mytv.rtzhdj.app.utils.datetime;

import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * DateUtils 时间工具类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-1-19
 * @update
 */
public class DateUtils {

    private static SimpleDateFormat DATE_FORMAT_TILL_SECOND = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat DATE_FORMAT_TILL_DAY_CURRENT_YEAR = new SimpleDateFormat(
            "MM-dd");
    private static SimpleDateFormat DATE_FORMAT_TILL_DAY_CH = new SimpleDateFormat(
            "yyyy-MM-dd");
    private static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    // 获取去年的年份
    public static int getLastYear() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, -1);
        return c.get(Calendar.YEAR);
    }

    // 获取下一年年份
    public static int getNextYear() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, 1);
        return c.get(Calendar.YEAR);
    }

    // 获取上一个月的月份
    public static int getLastMonth() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        return c.get(Calendar.MONTH) + 1;
    }

    // 获取下一个月的月份
    public static int getNextMonth() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 1);
        return c.get(Calendar.MONTH) + 1;
    }

    public static int getYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public static int getMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    public static int getCurrentMonthDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    public static int getWeekDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
    }

    public static int getHour() {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    }
    public static int getMinute() {
        return Calendar.getInstance().get(Calendar.MINUTE);
    }


    /**
     * 日期字符串转换为Date
     * @param dateStr
     * @param format
     * @return
     */
    public static Date strToDate(String dateStr, String format) {
        Date date = null;

        if (!TextUtils.isEmpty(dateStr)) {
            DateFormat df = new SimpleDateFormat(format);
            try {
                date = df.parse(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return date;
    }

    /**
     * 日期转换为字符串
     * @param timeStr
     * @param format
     * @return
     */
    public static String dateToString(String timeStr, String format) {
        // 判断是否是今年
        Date date = strToDate(timeStr, format);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // 如果是今年的话，才去“xx月xx日”日期格式
        if (calendar.get(Calendar.YEAR) == getYear()) {
            return DATE_FORMAT_TILL_DAY_CURRENT_YEAR.format(date);
        }

        return DATE_FORMAT_TILL_DAY_CH.format(date);
    }

    /**
     * 日期逻辑
     * @param dateStr 日期字符串
     * @return
     */
    public static String timeLogic(String dateStr) {
        Calendar calendar = Calendar.getInstance();
        calendar.get(Calendar.DAY_OF_MONTH);
        long now = calendar.getTimeInMillis();
        Date date = strToDate(dateStr, DATE_FORMAT);
        calendar.setTime(date);
        long past = calendar.getTimeInMillis();

        // 相差的秒数
        long time = (now - past) / 1000;

        StringBuffer sb = new StringBuffer();
        if (time > 0 && time < 60) { // 1小时内
            return sb.append(time + "秒前").toString();
        } else if (time > 60 && time < 3600) {
            return sb.append(time / 60+"分钟前").toString();
        } else if (time >= 3600 && time < 3600 * 24) {
            return sb.append(time / 3600 +"小时前").toString();
        }else if (time >= 3600 * 24 && time < 3600 * 48) {
            return sb.append("昨天").toString();
        }else if (time >= 3600 * 48 && time < 3600 * 72) {
            return sb.append("前天").toString();
        }else if (time >= 3600 * 72) {
            return dateToString(dateStr, DATE_FORMAT);
        }
        return dateToString(dateStr, DATE_FORMAT);
    }

}
