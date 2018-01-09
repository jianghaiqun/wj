package com.sinosoft.ibrms;

import com.sinosoft.platform.pub.NoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class IBRMSPubFun {
	private static final Logger logger = LoggerFactory.getLogger(IBRMSPubFun.class);

	/**
	 * 得到当前系统日期 author: YT
	 * @return 当前日期的格式字符串,日期格式为"yyyy-MM-dd"
	 */
	public static String getCurrentDate() {
//		String pattern = "yyyy-MM-dd";
//		SimpleDateFormat df = new SimpleDateFormat(pattern);
//		df.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
//		Date today = new Date();
//		String tString = df.format(today);
//		return tString;
        GregorianCalendar tGCalendar = new GregorianCalendar();
        StringBuffer tStringBuffer = new StringBuffer(10);
        int sYears = tGCalendar.get(Calendar.YEAR);
        tStringBuffer.append(sYears);
        tStringBuffer.append('-');
        int sMonths = tGCalendar.get(Calendar.MONTH)+1;
        if(sMonths < 10){
            tStringBuffer.append('0');
        }
        tStringBuffer.append(sMonths);
        tStringBuffer.append('-');
        int sDays = tGCalendar.get(Calendar.DAY_OF_MONTH);
        if(sDays < 10){
            tStringBuffer.append('0');
        }
        tStringBuffer.append(sDays);
        String tString = tStringBuffer.toString();
        return tString;


	}

	/**
	 * 得到当前系统时间 author: YT
	 * @return 当前时间的格式字符串，时间格式为"HH:mm:ss"
	 */
	public static String getCurrentTime() {
//		String pattern = "HH:mm:ss";
//		SimpleDateFormat df = new SimpleDateFormat(pattern);
//		df.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
//		Date today = new Date();
//		String tString = df.format(today);
//		return tString;
	       GregorianCalendar tGCalendar = new GregorianCalendar();
	        StringBuffer tStringBuffer = new StringBuffer(8);
	        int sHOUR = tGCalendar.get(Calendar.HOUR_OF_DAY);
	        if(sHOUR < 10){
	            tStringBuffer.append('0');
	        }
	        tStringBuffer.append(sHOUR);
	        tStringBuffer.append(':');
	        int sMINUTE = tGCalendar.get(Calendar.MINUTE);
	        if(sMINUTE < 10){
	            tStringBuffer.append('0');
	        }
	        tStringBuffer.append(sMINUTE);
	        tStringBuffer.append(':');
	        int sSECOND = tGCalendar.get(Calendar.SECOND);
	        if(sSECOND < 10){
	            tStringBuffer.append('0');
	        }
	        tStringBuffer.append(sSECOND);
	        String tString = tStringBuffer.toString();
	        return tString;
	}
	
	/*
	如果一个字符串数字中小数点后全为零，则去掉小数点及零
	 */
	public static String getInt(String Value) {
		String result = "";
		boolean mflag = true;
		int m = 0;
		m = Value.lastIndexOf(".");
		if (m == -1) {
			result = Value;
		} else {
			for (int i = m + 1; i <= Value.length() - 1; i++) {
				if (Value.charAt(i) != '0') {
					result = Value;
					mflag = false;
					break;
				}
			}
			if (mflag == true) {
				result = Value.substring(0, m);
			}
		}
		return result;
	}
	
	public static String getLRTemplateID(){
		
		return NoUtil.getMaxNo("ibrmsTemplateID", 20);
		
	}
	
	public static String getLRRuleTemplateID(){
	
		return "TMP"+NoUtil.getMaxNo("ruleTemplateID", 17);
	}
	
	public static String getDTTableSerialNumber(){
		return NoUtil.getMaxNo("ibrmsDTTNo", 4);
	}

	public static String getIbrmsResultNo(){
		return NoUtil.getMaxNo("ibrmsResultNo", 10);
	}
	public static Date getDate(String dateString)
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date tDate = null;
		try
		{
			tDate = df.parse(dateString);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
		}
		return tDate;
	}
}
