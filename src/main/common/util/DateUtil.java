package main.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author momo
 * @time 2017年8月17日上午8:55:25
 * Date工具类
 */
public class DateUtil {
	
	// 格式：年－月－日 小时：分钟：秒
	public static final String FORMAT_yMdHms = "yyyy-MM-dd HH:mm:ss";
	// 格式：年－月－日 小时：分钟
	public static final String FORMAT_yMdHm = "yyyy-MM-dd HH:mm";
	// 格式：年－月－日
	public static final String FORMAT_yMd = "yyyy-MM-dd";
	// 格式：05/02/16
	public static final String FORMAT_Mdy = "MM/dd/yy";

	public static final String FORMAT_ymdhms ="yyyyMMddHHmmss";
	
	public static final String FORMAT_hsm = "HHmmss";
	
	public static final String FORMAT_ymd = "yyyyMMdd";
	
	/**
	 * String -- > Date
	 * @param dateStr 日期字符串
	 * @return
	 */
	public static Date stringToDate(String dateStr) {
		return stringToDate(dateStr, DateUtil.FORMAT_yMd);
	}
	
	/**
	 * Date -- > String
	 * @param date 日期  
	 * @return
	 */
	public static String dateToString(Date date) {
		return dateToString(date, DateUtil.FORMAT_yMd);
	}
	
	/**
	 * String ---> Date
	 * @param dateStr 日期字符串
	 * @param format 转换格式
	 * @return
	 */
	public static Date stringToDate(String dateStr, String format) {
		Date d = null;
		SimpleDateFormat formater = new SimpleDateFormat(format);
		try {
			formater.setLenient(false);
			d = formater.parse(dateStr);
		} catch (Exception e) {
			e.printStackTrace();
			d = null;
		}
		return d;
	}
	
	/**
	 * Date ---> String
	 * @param date 日期  
	 * @param format 转换格式
	 * @return
	 */
	public static String dateToString(Date date, String format) {
		String result = null;
		SimpleDateFormat formater = new SimpleDateFormat(format);
		try {
			result = formater.format(date);
		} catch (Exception e) {
			e.printStackTrace();
			result = "";
		}
		return result;
	}
}
