/**
 * @CopyRight:Sinosoft
 * @Project:ZKING-PORTAL
 * @File:DictType.java
 * @CreateTime:2012-3-2 上午9:14:05
 * @Package:com.sinosoft.util
 * @Author:LiuXin
 * @Version:1.0
 */
package cn.com.sinosoft.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Tool {

	/**
	 * 日期格式化对象，精确到日
	 */
	public final static SimpleDateFormat SDF_DAY = new SimpleDateFormat(
			"yyyy-MM-dd", Locale.CHINA);
	/**
	 * 日期格式化对象，精确到秒
	 */
	public final static SimpleDateFormat SDF_SEND = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss", Locale.CHINA);

	/**
	 * 日期格式化对象，精确到毫秒
	 */
	public final static SimpleDateFormat SDF_MINSEND = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss.SSSZ", Locale.CHINA);
	/**
	 * 将日期格式为字符串
	 */
	public final static SimpleDateFormat SDF_TOSTRING = new SimpleDateFormat("yyMMddhhssmmSSS");
	
	/**
	 * 获取服务器当前时间
	 * 
	 * @return
	 */
	public static Date getCurrentTime() {
		Calendar c = Calendar.getInstance();
		return c.getTime();
	}

	/**
	 * 获取服务器当前日期
	 * 
	 * @return
	 */
	public static Date getCurrentDay() {
		Calendar c = Calendar.getInstance();
		return c.getTime();
	}
	
}
