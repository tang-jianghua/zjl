package com.mfangsoft.zhuangjialong.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.fasterxml.jackson.annotation.JsonFormat;

public class DateUtils {

	static final String timeFormatter = "HH:mm";
	
	static final String datetimeFormatter0 = "yyyy-MM-dd";
	   
    static final String datetimeFormatter1 = "yyyy-MM-dd HH:mm:ss";
    
    static final String datetimeFormatter2 = "yyyyMMddHHmmss";
    
    
    static SimpleDateFormat simple0 = new SimpleDateFormat(datetimeFormatter0);
    static SimpleDateFormat simple1 = new SimpleDateFormat(datetimeFormatter1);
    static SimpleDateFormat simple2 = new SimpleDateFormat(datetimeFormatter2);
    
    static{
    	TimeZone timeZone = TimeZone.getTimeZone("GMT+8");
    	simple0.setTimeZone(timeZone);
    	simple1.setTimeZone(timeZone);
    	simple2.setTimeZone(timeZone);
    }
    
    static ThreadLocal<SimpleDateFormat> timeFormatterSafe = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(timeFormatter);
        }
    };
    
    /**
     * 格式 HH:mm
     */
    public static String formatTime(long time) {
        return timeFormatterSafe.get().format(time);
    }
    
    public static String formatDateYmd(Date date){
    	return simple0.format(date);
    }
    
    public static String formatDate_(Date date){
    	return simple1.format(date);
    }
    
    public static String formatDateNospace(Date date){
    	return simple2.format(date);
    }
    
    public static Date parseDateYmd(String date) throws ParseException{
    	return simple0.parse(date);
    }
    
    public static Date parseDate_(String date) throws ParseException{
    	return simple1.parse(date);
    }
    
    public static Date parseDateNospace(String date) throws ParseException{
    	return simple2.parse(date);
    }
}
