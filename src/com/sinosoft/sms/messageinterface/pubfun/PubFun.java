package com.sinosoft.sms.messageinterface.pubfun;

import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import org.jdom.Document;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/*******************************************************************************
 * <p>
 * Company: 中科软科技股份有限公司
 * </p>
 * <p>
 * WebSite: http://www.sinosoft.com.cn
 * </p>
 * 
 * @author : zhouxiang
 * @version : 1.00
 * @date : 2012-08-08
 * @direction: 短信接口公共函数类
 ******************************************************************************/
public class PubFun {

	private static final Logger logger = LoggerFactory.getLogger(PubFun.class);
	public static String docToString(Document doc) {

		OutputStream tOutputStream = new ByteArrayOutputStream();
		String str = null;
		try {
			Format format = Format.getPrettyFormat();
			format.setEncoding("gbk");// 设置xml文件的字符, 解决中文问题
			XMLOutputter tOutputter = new XMLOutputter(format);
			// tOutputter.setEncoding("UTF-8");
			// tOutputter.setIndent("    "); // 调整输出xml的缩进值
			// tOutputter.setExpandEmptyElements(true); // 是否扩展空值标签
			// tOutputter.setNewlines(true); // 是否分行输出
			tOutputter.output(doc, new PrintWriter(tOutputStream));
			// tOutputter.output(doc, System.out);
			str = tOutputStream.toString();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return str;
	}

	/**
	 * 得到当前系统时间
	 * 
	 * @return 当前时间的格式字符串，时间格式为"HH:mm:ss"
	 */
	public static String getCurrentTime() {
		String pattern = "HH:mm:ss";
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		Date today = new Date();
		String tString = df.format(today);
		return tString;
	}

	/**
	 * 得到当前系统日期
	 * 
	 * @return 当前日期的格式字符串，时间格式为"yyyy-MM-dd"
	 */
	public static String getCurrentDate() {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		Date today = new Date();
		String tString = df.format(today);
		return tString;
	}

	/**
	 * 计算日期的函数 author: HST 参照日期指当按照年月进行日期的计算的时候，参考的日期，如下例，结果返回2002-03-31
	 * <p>
	 * <b>Example: </b>
	 * <p>
	 * <p>
	 * FDate tD=new FDate();
	 * <p>
	 * <p>
	 * Date baseDate =new Date();
	 * <p>
	 * <p>
	 * baseDate=tD.getDate("2000-02-29");
	 * <p>
	 * <p>
	 * Date comDate =new Date();
	 * <p>
	 * <p>
	 * comDate=tD.getDate("1999-12-31");
	 * <p>
	 * <p>
	 * int inteval=1;
	 * <p>
	 * <p>
	 * String tUnit="M";
	 * <p>
	 * <p>
	 * Date tDate =new Date();
	 * <p>
	 * <p>
	 * tDate=PubFun.calDate(baseDate,inteval,tUnit,comDate);
	 * <p>
	 * <p>
	 * System.out.println(tDate.toString());
	 * <p>
	 * 
	 * @param baseDate
	 *            起始日期
	 * @param interval
	 *            时间间隔
	 * @param unit
	 *            时间间隔单位
	 * @param compareDate
	 *            参照日期
	 * @return Date类型变量
	 */
	public static Date calDate(Date baseDate, int interval, String unit, Date compareDate) {
		Date returnDate = null;

		GregorianCalendar mCalendar = new GregorianCalendar();
		mCalendar.setTime(baseDate);
		if (unit.equals("Y")) {
			mCalendar.add(Calendar.YEAR, interval);
		}
		if (unit.equals("M")) {
			mCalendar.add(Calendar.MONTH, interval);
		}
		if (unit.equals("D")) {
			mCalendar.add(Calendar.DATE, interval);
		}

		if (compareDate != null) {
			GregorianCalendar cCalendar = new GregorianCalendar();
			cCalendar.setTime(compareDate);

			int mYears = mCalendar.get(Calendar.YEAR);
			int mMonths = mCalendar.get(Calendar.MONTH);
			int cMonths = cCalendar.get(Calendar.MONTH);
			int cDays = cCalendar.get(Calendar.DATE);

			if (unit.equals("Y")) {
				cCalendar.set(mYears, cMonths, cDays);
				if (cCalendar.before(mCalendar)) {
					mCalendar.set(mYears + 1, cMonths, cDays);
					returnDate = mCalendar.getTime();
				} else {
					returnDate = cCalendar.getTime();
				}
			}
			if (unit.equals("M")) {
				cCalendar.set(mYears, mMonths, cDays);
				if (cCalendar.before(mCalendar)) {
					mCalendar.set(mYears, mMonths + 1, cDays);
					returnDate = mCalendar.getTime();
				} else {
					returnDate = cCalendar.getTime();
				}
			}
			if (unit.equals("D")) {
				returnDate = mCalendar.getTime();
			}
		} else {
			returnDate = mCalendar.getTime();
		}

		return returnDate;
	}

	/**
	 * 重载计算日期，参数见楼上，add by Minim
	 * 
	 * @param baseDate
	 *            String
	 * @param interval
	 *            int
	 * @param unit
	 *            String
	 * @param compareDate
	 *            String
	 * @return String
	 */
	// public static String calDate(String baseDate, int interval, String unit,
	// String compareDate)
	// {
	// try
	// {
	// FDate tFDate = new FDate();
	// Date bDate = tFDate.getDate(baseDate);
	// Date cDate = tFDate.getDate(compareDate);
	// return tFDate.getString(calDate(bDate, interval, unit, cDate));
	// }
	// catch (Exception ex)
	// {
	// ex.printStackTrace();
	// return null;
	// }
	// }

	/**
	 * 判断传入日期是星期几
	 * 
	 * @return 传入日期的格式字符串，时间格式为"yyyy-MM-dd"
	 */
	public static String getWeekDate(String InputDate) {
		final String dayNames[] = { "SUN", "MON", "TUES", "WED", "THURS", "FRI", "SAT" };

		SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		Date date = new Date();

		try {
			date = sdfInput.parse(InputDate);
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}

		calendar.setTime(date);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		String weekdate = dayNames[dayOfWeek - 1];

		return weekdate;
	}

	/**
	 * 根据传入身份证号判断称呼
	 */
	public static String getTitleByID(String tIDNo) {
		String tSex = "";
		if ((tIDNo.trim().length() != 15) && (tIDNo.trim().length() != 18)) {
			logger.info("身份证号位数错误。");
			tSex = "先生[女士]";
			return tSex;
		}
		int temp = 0;

		if (tIDNo.trim().length() == 15) {
			temp = Integer.parseInt(tIDNo.substring(0, 14));
		} else if (tIDNo.trim().length() == 18) {
			temp = Integer.parseInt(tIDNo.substring(16, 17));
		}

		temp = temp % 2;
		if (temp == 0) {
			tSex = "女士";
		} else {
			tSex = "先生";
		}

		return tSex;
	}

	/**
	 * 根据系统中性别判断称呼
	 */
	public static String getTitleBySex(String tSexCode) {
		String tSex = "";
		String temp = "";
		StringBuffer tSQL = new StringBuffer("");

		tSQL.append("select codename from ldcode ");
		tSQL.append(" where codetype = 'sex' and code ='");
		tSQL.append(tSexCode.trim());
		tSQL.append("'");

		DataTable dt = new QueryBuilder(tSQL.toString()).executeDataTable();
		if (dt.getRowCount() == 0) {
			return null;
		}

		tSex = dt.getString(0, 1);

		if (tSex.trim().equals("男")) {
			temp = "先生";
		} else if (tSex.trim().equals("女")) {
			temp = "女士";
		} else {
			temp = "先生[女士]";
		}

		return temp;
	}

	public static Date StringToDate(String str) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(str);
		return date;
	}


	/**
	 * 计算日期的函数 author: HST 参照日期指当按照年月进行日期的计算的时候，参考的日期，如下例，结果返回2002-03-31
	 * <p>
	 * <b>Example: </b>
	 * <p>
	 * <p>
	 * FDate tD=new FDate();
	 * <p>
	 * <p>
	 * Date baseDate =new Date();
	 * <p>
	 * <p>
	 * baseDate=tD.getDate("2000-02-29");
	 * <p>
	 * <p>
	 * Date comDate =new Date();
	 * <p>
	 * <p>
	 * comDate=tD.getDate("1999-12-31");
	 * <p>
	 * <p>
	 * int inteval=1;
	 * <p>
	 * <p>
	 * String tUnit="M";
	 * <p>
	 * <p>
	 * Date tDate =new Date();
	 * <p>
	 * <p>
	 * tDate=PubFun.calDate(baseDate,inteval,tUnit,comDate);
	 * <p>
	 * <p>
	 * System.out.println(tDate.toString());
	 * <p>
	 * <p>
	 * 未测试,请业务处理人员增加
	 * <p>
	 * 
	 * @param baseDate
	 *            起始日期
	 * @param interval
	 *            时间间隔
	 * @param unit
	 *            时间间隔单位
	 * @param compareDate
	 *            参照日期
	 * @return String类型变量
	 */
	public static String calSDate(String tbaseDate, int interval, String unit) {
		Date returnDate = null;
		String ReturnDate = null;

		Date baseDate = new Date();
		try {
			baseDate = StringToDate(tbaseDate);
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}

		GregorianCalendar mCalendar = new GregorianCalendar();
		mCalendar.setTime(baseDate);
		if (unit.equals("Y"))
			mCalendar.add(Calendar.YEAR, interval);
		if (unit.equals("M"))
			mCalendar.add(Calendar.MONTH, interval);
		if (unit.equals("D"))
			mCalendar.add(Calendar.DATE, interval);

		returnDate = mCalendar.getTime();

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		ReturnDate = df.format(returnDate);

		return ReturnDate;
	}

	public static int getAge(String birthDay,Date effective) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return getAge(sdf.parse(birthDay),effective);
		} catch (Exception e) {
			return 0;
		}
	}
	public static int getAge(String birthDay) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return getAge(sdf.parse(birthDay));
		} catch (Exception  e) {
			return 0;
		}
	}
	/**
	 * 
	* @Title: getAge 
	* @Description: TODO(获取年龄，小于1岁，获取天数，格式：1Y、1D) 
	* @return int    返回类型 
	* @author
	 */
	public static String getAgeNum(String birthDay) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return getAgeNum(sdf.parse(birthDay));
		} catch (Exception  e) {
			return "0D";
		}
	}
	
   //根据起保日期计算被保人年龄
	public static int getAge(Date birthDay,Date effective) throws Exception {
		Calendar cal = Calendar.getInstance();
		if (cal.before(birthDay)) {
			return 0;
		}
		
		cal.setTime(effective);
		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH);
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
		cal.setTime(birthDay);

		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;
		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				if (dayOfMonthNow < dayOfMonthBirth) {
					age--;
				}
			} else {
				age--;
			}
		}
		return age;
	}
	//根据当前日期计算被保人年龄
	public static int getAge(Date birthDay) throws Exception {
		Calendar cal = Calendar.getInstance();
		if (cal.before(birthDay)) {
			return 0;
		}
		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH);
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
		cal.setTime(birthDay);

		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;
		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				if (dayOfMonthNow < dayOfMonthBirth) {
					age--;
				}
			} else {
				age--;
			}
		}
		return age;
	}
	/**
	 * 
	* @Title: getAgeNum 
	* @Description: TODO(获取年龄，区分年、日，格式：1Y、1D;) 
	* 计算规则：2015-04-25至2015-04-27 为3天，即3D；2014-04-27至2015-04-27 为一年，即1Y；2014-04-28至2015-04-27 为364天，即364D
	* @return int    返回类型 
	* @author
	 */
	public static String getAgeNum(Date birthDay) throws Exception {
		Calendar cal = Calendar.getInstance();
		if (cal.before(birthDay)) {
			return "0D";
		}
		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH);
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
		cal.setTime(birthDay);

		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;
		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				if (dayOfMonthNow < dayOfMonthBirth) {
					age--;
				}
			} else {
				age--;
			}
		}
		if(age==0){
			int days=daysBetween(birthDay, new Date());
			if(days==364){
				return days+"D";
			}else{
				return (days+1)+"D";
			}
		}else if(age<0){
			return "0D";
		}else{
			return age+"Y";
		}
	}
	/**
	 * 根据年龄获取天数间隔
	* @Title: getDay 
	* @author XXX
	 */
	public static int getSumDay(int age, String ageFlag){
		Calendar cal = Calendar.getInstance(); 
		Calendar nowCal = Calendar.getInstance();
		if ("max".equals(ageFlag)) {
			cal.add(Calendar.YEAR, -(age + 1));
			cal.add(Calendar.DAY_OF_MONTH, 1);
		} else {
			cal.add(Calendar.YEAR, -age);
		}
		Date ageTime = cal.getTime();
		Date nowTiem = nowCal.getTime();
		try {
			return daysBetween(ageTime,nowTiem);
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
			if(age==18){
				return 18*365;
			}else{
				return 65*365;
			}
		}
	}
	 /**  
     * 计算两个日期之间相差的天数  
     * @param smdate 较小的时间 
     * @param bdate  较大的时间 
     * @return 相差天数 
     * @throws ParseException  
     */    
    public static int daysBetween(Date smdate,Date bdate) throws ParseException    
    {    
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        smdate=sdf.parse(sdf.format(smdate));  
        bdate=sdf.parse(sdf.format(bdate));  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(smdate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bdate);    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
       return Integer.parseInt(String.valueOf(between_days));           
    }
    /**
	 * 得到产品保险期间
	 * @param femrList
	 * @return
	 */
	public static String getInsureDate(String valueParem){
		
		List<Integer> arrList = new ArrayList<Integer>();
		String tPeriodInsurance = "";
		//保险期间已D、M、Y为单位，
		String[] arrValues = null;
		if(valueParem.indexOf("|")!=-1){
			String[] valueParems = valueParem.split("\\|");
			int lLen = valueParems.length;
			for(int j=0;j<lLen;j++){
				String values = valueParems[j];
				if(values.indexOf("-")!=-1){
					arrValues = values.split("-");
				}else{
					arrValues = new String[]{values};
				}
				int tLen = arrValues.length;
				for(int k=0;k<tLen;k++){
					String ageFlag = "min";
					if(k==tLen-1){
						ageFlag = "max";
					}
					arrList.add(getOneDate(arrValues[k],ageFlag));
				}
			}
		}else{
			String values = valueParem;
			if(values.indexOf("-")!=-1){
				arrValues = values.split("-");
			}else{
				arrValues = new String[]{values};
			}
			int tLen = arrValues.length;
			for(int j=0;j<tLen;j++){
				String ageFlag = "min";
				if(j==tLen-1){
					ageFlag = "max";
				}
				arrList.add(getOneDate(arrValues[j],ageFlag));
			}
		}
		//排序处理
		if(arrList.size()==1){
			arrList.add(1);
		}
		if(arrList.get(0)==0){
			arrList.set(0, 0);
		}
		Collections.sort(arrList);
		tPeriodInsurance = arrList.get(0)+"-"+arrList.get(arrList.size()-1);
		return tPeriodInsurance;
	}
	/**
	 * 把保险期间统一为D
	 * @param period
	 * @return
	 */
	public static int getOneDate(String period,String ageFlag){
		int tPeriod=1;
		try {
			Calendar nowCal = Calendar.getInstance();
			Calendar cal = Calendar.getInstance();   
			if(ageFlag.equals("max")){
				cal.add(Calendar.DAY_OF_MONTH, 1);
			}
			if(period.indexOf("D")!=-1){
				tPeriod = Integer.parseInt(period.substring(0, period.length()-1));
			}else if(period.indexOf("M")!=-1){
				if(ageFlag.equals("max")){
					cal.add(Calendar.MONTH,-(Integer.parseInt(period.substring(0, period.length()-1))+1));
				}else{
					cal.add(Calendar.MONTH,-Integer.parseInt(period.substring(0, period.length()-1)));
				}
				tPeriod = daysBetween(cal.getTime(),nowCal.getTime());
			}else if(period.indexOf("Y")!=-1){
				if(ageFlag.equals("max")){
					cal.add(Calendar.YEAR,-(Integer.parseInt(period.substring(0, period.length()-1))+1));
				}else{
					cal.add(Calendar.YEAR,-Integer.parseInt(period.substring(0, period.length()-1)));
				}
				tPeriod = daysBetween(cal.getTime(),nowCal.getTime());
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
		}
		
		return tPeriod;
	}
}
