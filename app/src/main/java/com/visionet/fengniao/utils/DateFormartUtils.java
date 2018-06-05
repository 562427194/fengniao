package com.visionet.fengniao.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by zhenghai on 2016/9/5.
 */
public class DateFormartUtils {
    public static String DEF_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static String format(long l,String format){
        SimpleDateFormat f = new SimpleDateFormat(format);
       return  f.format(new Date(l));
    }
    public static String format(long l){
       return format(l,DEF_FORMAT);
    }
    public static String getMsgDateTimeString(long l){
       String format = DEF_FORMAT;
        Calendar createdAtDate = Calendar.getInstance();
        Date createDate = new Date( l );
        createdAtDate.setTime( createDate );
        Calendar current = Calendar.getInstance();
        String strDaySection = getDaySection( createdAtDate );
        if (createdAtDate.get( Calendar.YEAR )==current.get( Calendar.YEAR )) {
            if (createdAtDate.get( Calendar.DAY_OF_YEAR )==current.get( Calendar.DAY_OF_YEAR )) { // 今天
                format = String.format("%sHH:mm",strDaySection );
            } else if (current.get( Calendar.DAY_OF_YEAR )-createdAtDate.get( Calendar.DAY_OF_YEAR )==1) { // 昨天
                format = String.format("昨天 %sHH:mm",strDaySection );
            } else { // 今年的其他时间
                format = String.format("MM月dd日 %sHH:mm",strDaySection );
            }
        } else { // 非今年
            format = String.format("yyyy年MM月dd日 %sHH:mm",strDaySection );
        }
        DateFormat df =  new SimpleDateFormat(format);
        return df.format( createDate );
    }

    public static String getDaySection(Calendar calendar){
        String strResult = "";
        int nHour = calendar.get( Calendar.HOUR_OF_DAY );
        if (nHour >= 0 && nHour < 6) {
            strResult = "凌晨";
        } else if (nHour >= 6 && nHour < 8){
            strResult = "早晨";
        } else if (nHour >= 8 && nHour < 12){
            strResult = "上午";
        } else if (nHour >= 12 && nHour < 13){
            strResult = "中午";
        } else if (nHour >= 13 && nHour < 18){
            strResult = "下午";
        } else if (nHour >= 18){
            strResult = "晚上";
        }
        return strResult;
    }
}
