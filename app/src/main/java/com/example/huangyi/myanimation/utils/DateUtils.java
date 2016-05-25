package com.example.huangyi.myanimation.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 关 on 2016/4/21.
 */
public class DateUtils {
    /**
     * 将字符串日期转换成短格式的字符串
     *
     * @param str
     * @return
     */
    public static String StrDateToSortStrDate(String str) {
        Date date = StrToDate(str);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);

    }

    /**
     * 将字符串装换成年月
     *
     * @param str
     * @return
     */
    public static String StrDateToYearAndMonthDate(String str) {
        if ("不限".equals(str)|| "请选择日期".equals(str)) {
            return "";
        } else {
            SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月");
            Date date = null;
            try {
                if (str != null) {
                    date = format.parse(str);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            format = new SimpleDateFormat("yyyy-MM");
            return format.format(date);
        }

    }

    /**
     * 字符串转日期
     *
     * @param str
     * @return
     */

    public static Date StrToDate(String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 日期转换成字符串
     *
     * @param date
     * @return str
     */
    public static String DateToStr(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = format.format(date);
        return str;
    }

    /**
     * 获取当前日期字符串
     */
    public static String getCurrentDateStr() {
        Date dt = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(dt);
    }
}
//    /**
//     * 日期转毫秒值
//     */
//    public static long StrTOMillionSeconds(String str) {
//        // 日期转毫秒
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
//        long millionSeconds = 0;//毫秒
//        try {
//            millionSeconds = sdf.parse(str).getTime();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return millionSeconds;
//    }

