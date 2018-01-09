package com.wangjin.infoseeker;

import com.sinosoft.cms.site.FileList;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DialogInfo extends Page {

	private static String today = DateUtil.toString(new Date(), "yyyy-MM-dd");
	private static String yesterday = DateUtil.toString(DateUtil.addDay(new Date(), -1), "yyyy-MM-dd");
	
	//插入操作的事务
	private Transaction tranInsert  = new Transaction();
	//删除操作的事务
	private Transaction tranDelete  = new Transaction();
	
	private InputStreamReader isr = null;
	private InputStream in = null;
	
	/**
	 * 初始化查询日期
	 * 
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Mapx initStaff(Mapx params) {
		
		Mapx map = new Mapx();
		
		map.put("displayFlag", HtmlUtil.codeToOptions("talkstatistics.displayFlag", true));
		map.put("yesterday", yesterday);
		map.put("today", today);
		
		return map;
	}

	/**
	 * 获得会话表的相关数据
	 * 
	 * @param dga
	 */
	public void dg1DataBind(DataGridAction dga) {
		String startDate = convertNull(dga.getParam("startDate")).trim();
		String endDate = convertNull(dga.getParam("endDate")).trim();
		String startTime = convertNull(dga.getParam("startTime")).trim();
		if(startTime.length() == 7){
			startTime = "0"+startTime;
		}
		String endTime = convertNull(dga.getParam("endTime")).trim();
		if(endTime.length() == 7){
			endTime = "0"+endTime;
		}
		String averageResponseTimeStart = convertNull(dga.getParam("averageResponseTimeStart")).trim();
		String averageResponseTimeEnd = convertNull(dga.getParam("averageResponseTimeEnd")).trim();
		String reps = convertNull(dga.getParam("reps")).trim();
		String dialogId = convertNull(dga.getParam("dialogId")).trim();
		String parameter = "";
		
		if(StringUtil.isNotEmpty(endDate) && !StringUtil.isNotEmpty(startDate)){
			parameter = " WHERE DATE_FORMAT(ds.dialogEndTime,'%Y-%m-%d %k:%i:%s')<='" + endDate + " " + endTime + "'";
		}else{
			if (StringUtil.isNotEmpty(startDate)) {
				parameter = " WHERE DATE_FORMAT(ds.dialogStartTime,'%Y-%m-%d %k:%i:%s')>='" + startDate + " " + startTime + "'";
			} else {
				parameter = " WHERE DATE_FORMAT(ds.dialogStartTime,'%Y-%m-%d %H:%i:%s')>='" + yesterday + "'";
			}
			if (StringUtil.isNotEmpty(endDate)) {
				parameter = parameter + " AND DATE_FORMAT(ds.dialogEndTime,'%Y-%m-%d %k:%i:%s')<='" + endDate + " " + endTime + "'";
			} else {
				parameter = parameter + " AND DATE_FORMAT(ds.dialogEndTime,'%Y-%m-%d %H:%i:%s')<='" + today + "'";
			}
		}

		StringBuffer sql = new StringBuffer();
		sql.append("select * from dialogstatistics ds ");
		sql.append(parameter);
		if(StringUtil.isNotEmpty(averageResponseTimeStart)){
			sql.append(" AND averageResponseTime >= ");
			sql.append(averageResponseTimeStart);
		}
		if(StringUtil.isNotEmpty(averageResponseTimeEnd)){
			sql.append(" AND averageResponseTime <= ");
			sql.append(averageResponseTimeEnd);
		}
		if(StringUtil.isNotEmpty(reps)){
			if(reps.matches("\\d+")){
				sql.append(" AND oid = '");
				sql.append(reps);
				sql.append("'");
			}else{
				sql.append(" AND ona = '");
				sql.append(reps);
				sql.append("'");
			}
		}
		if(StringUtil.isNotEmpty(dialogId)){
			sql.append(" AND dialogId = '");
			sql.append(dialogId);
			sql.append("'");
		}
		sql.append(" order by ds.averageResponseTime DESC ,ds.dialogStartTime DESC");
		
		QueryBuilder qb = new QueryBuilder(sql.toString());
		DataTable dt = qb.executeDataTable();
		
		dga.bindData(dt);
	}

	/**
	 * 获得对话表的相关数据
	 * 
	 * @param dga
	 */
	public void dg2DataBind(DataGridAction dga) {
		String startDate = convertNull(dga.getParam("startDate")).trim();
		String endDate = convertNull(dga.getParam("endDate")).trim();
		String startTime = convertNull(dga.getParam("startTime")).trim();
		if(startTime.length() == 7){
			startTime = "0"+startTime;
		}
		String endTime = convertNull(dga.getParam("endTime")).trim();
		if(endTime.length() == 7){
			endTime = "0"+endTime;
		}
		String responseTimeStart = convertNull(dga.getParam("responseTimeStart")).trim();
		String responseTimeEnd = convertNull(dga.getParam("responseTimeEnd")).trim();
		String reps = convertNull(dga.getParam("reps")).trim();
		String displayFlag = dga.getParam("displayFlag");
		String dialogId = convertNull(dga.getParam("dialogId")).trim();
		String parameter = "";
		
		if(StringUtil.isNotEmpty(endDate) && !StringUtil.isNotEmpty(startDate)){
			parameter = " WHERE DATE_FORMAT(ts.questionEndTime,'%Y-%m-%d %k:%i:%s')<='" + endDate + " " + endTime + "'";
		}else{
			if (StringUtil.isNotEmpty(startDate)) {
				parameter = " WHERE DATE_FORMAT(ts.questionStartTime,'%Y-%m-%d %k:%i:%s')>='" + startDate + " " + startTime + "'";
			} else {
				parameter = " WHERE DATE_FORMAT(ts.questionStartTime,'%Y-%m-%d %H:%i:%s')>='" + yesterday + "'";
			}
			if (StringUtil.isNotEmpty(endDate)) {
				parameter = parameter + " AND DATE_FORMAT(ts.questionEndTime,'%Y-%m-%d %k:%i:%s')<='" + endDate + " " + endTime + "'";
			} else {
				parameter = parameter + " AND DATE_FORMAT(ts.questionEndTime,'%Y-%m-%d %H:%i:%s')<='" + today + "'";
			}
		}
		
		StringBuffer sql = new StringBuffer();
		sql.append("select * from talkstatistics ts ");
		sql.append(parameter);
		if(StringUtil.isNotEmpty(responseTimeStart)){
			sql.append(" AND responseTime >= ");
			sql.append(responseTimeStart);
		}
		if(StringUtil.isNotEmpty(responseTimeEnd)){
			sql.append(" AND responseTime <= ");
			sql.append(responseTimeEnd);
		}
		if(StringUtil.isNotEmpty(reps)){
			if(reps.matches("\\d+")){
				sql.append(" AND oid = '");
				sql.append(reps);
				sql.append("'");
			}else{
				sql.append(" AND ona = '");
				sql.append(reps);
				sql.append("'");
			}
		}
		if(StringUtil.isNotEmpty(displayFlag)){
			sql.append(" AND displayFlag = '");
			sql.append(displayFlag);
			sql.append("'");
		}
		if(StringUtil.isNotEmpty(dialogId)){
			sql.append(" AND dialogId = '");
			sql.append(dialogId);
			sql.append("'");
		}
		sql.append(" order by ts.responseTime DESC, ts.questionStartTime DESC");
		
		QueryBuilder qb = new QueryBuilder(sql.toString());
		DataTable dt = qb.executeDataTable();
		
		dga.bindData(dt);
	}
	
	/**
	 * 解压缩文件，并将相关数据插入数据库
	 * 
	 * @param uploadPath 文件存放的物理地址
	 * 
	 * @return 如果出错返回出错的文件名，否则将返回一个""
	 */
	@SuppressWarnings("unchecked")
	public String resolveZipFile(String uploadPath) {
		
		String zipEntryFileName = "";
		try {
			ArrayList fileList =  FileList.getAllFiles(uploadPath);
			for (int i = 0; i < fileList.size(); i++) {
				String fileName = (String)fileList.get(i);
				ZipFile zipFile = new ZipFile(fileName);
				Enumeration e = zipFile.getEntries();
				ZipEntry zipEntry = null;
				while (e.hasMoreElements()) {
					zipEntry = (ZipEntry) e.nextElement();
					zipEntryFileName = zipEntry.getName();
					if(zipEntryFileName.indexOf(".xml")>-1){
						//只能通过字节流的方式实现不通过解压操作来获取压缩文件中的子文件
						in=(zipFile.getInputStream(zipEntry));
						iterateWholeXML(in,zipEntryFileName);
						
					}
				}
			}
			//删除重复插入的会话数据
			String deleteDialogStatisticsSQL = "delete dialogstatistics as a from dialogstatistics as a, " + 
											   "(select max(id) as id,dialogId,transferFlag from dialogstatistics group by dialogId,transferFlag having count(*)>1) as b " + 
											   "where a.dialogId=b.dialogId and a.transferFlag=b.transferFlag and a.id < b.id";
			QueryBuilder dsDelBuilder = new QueryBuilder(deleteDialogStatisticsSQL);
			tranDelete.add(dsDelBuilder);
			
			//删除重复插入的对话数据
			String deleteTalkStatisticsSQL = "delete talkstatistics as a from talkstatistics as a, "+
											 "(select max(id) as id,dialogId,oid,questionStartTime,questionEndTime from talkstatistics group by dialogId,oid,questionStartTime,questionEndTime having count(*)>1) as b " +
											 "where a.dialogId=b.dialogId and a.oid=b.oid and a.questionStartTime=b.questionStartTime and a.questionEndTime=b.questionEndTime and a.id < b.id ";
			QueryBuilder tsDelBuilder = new QueryBuilder(deleteTalkStatisticsSQL);
			tranDelete.add(tsDelBuilder);
			
			tranInsert.commit();
			tranDelete.commit();
			
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			tranInsert.clear();
			tranDelete.clear();
			return zipEntryFileName;
		}finally{
			try {
				//关闭流
				if(isr != null){
					isr.close();
				}
				if(in != null){
					in.close();
				}
			} catch (IOException e) {
				
			}
		}
		return "";
	}
	
	/**
	 * 遍历整个XML文件，获取所有节点的值与其属性的值，并插入到数据库中
	 * 
	 * @param filename String 待遍历的XML文件
	 * @throws DocumentException 
	 * @throws ParseException 
	 * @throws UnsupportedEncodingException 
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void iterateWholeXML(InputStream in,String fileName) throws Exception {
		
		SAXReader saxReader = new SAXReader();
		//设置解析XML文件时候的编码
		saxReader.setEncoding("GBK");
		
		//获取dialogId
		String dialogId = fileName.split("/")[1].substring(0, 8);
		
		//读取子文件中的编码转换
		isr = new InputStreamReader(in,"UTF-8");
		
		Document document = saxReader.read(isr);
		Element root = document.getRootElement();
		
		List detailList = root.elements();
		// 遍历根结点（detail）的所有子节点
		for (int i = 0; i < detailList.size(); i++) {
		//用于分析问题响应时间
		ArrayList timeList = new ArrayList();
		//用于问题内容
		ArrayList questionList = new ArrayList();
		//oid
		String oid = "";
		//ona
		String ona = "";
		
		Element element = (Element) detailList.get(i);
			
		// 获取history节点的oid属性的值
		Attribute ageAttr = null;
		ageAttr = element.attribute("oid");
		if (ageAttr != null) {
			oid = ageAttr.getValue();
		}
		
		// 获取history节点的ona属性的值
		ageAttr = element.attribute("ona");
		if (ageAttr != null) {
			ona = ageAttr.getValue();
		}
		
		//closed节点的tm属性的值
		String closedTime = "";
			
		// 遍历history结点的所有子节点,并进行处理
		List historyList = element.elements();
		for (int j = 0; j < historyList.size(); j++) {
			HashMap hm = new HashMap();
			Element elementInner = (Element) (historyList.get(j));
			
			if (elementInner.getName().equals("closed")) {
				// 获取closed节点的tm属性的值
				Attribute tmAttr = elementInner.attribute("tm");
				if (tmAttr != null) {
					String tm = tmAttr.getValue();
					if (tm != null && !tm.equals("")) {
						closedTime = tm;
					} 
				} 
			}
			
			if (elementInner.getName().equals("I")) {
				// 获取I节点的tm属性的值
				Attribute tmAttr = elementInner.attribute("tm");
				if (tmAttr != null) {
					String tm = tmAttr.getValue();
					if (tm != null && !tm.equals("")) {
						hm.put("I", tm);
						timeList.add(hm);
					} 
				} 
				//设置问题内容的拼装标记
				questionList.add("I");
			}
			
			if (elementInner.getName().equals("he")) {
				// 获取he节点的tm属性的值
				Attribute tmAttr = elementInner.attribute("tm");
				if (tmAttr != null) {
					String tm = tmAttr.getValue();
					if (tm != null && !tm.equals("")) {
						hm.put("he",tm);
						timeList.add(hm);
					} 
				}
				
				// 获取he节点的值
				String tempText = elementInner.getTextTrim();
				Pattern p = Pattern.compile("//s*|/t|/r|/n|[ ]");
				tempText = tempText.replaceAll("<br />", "");
				Matcher m = p.matcher(tempText);
				if("".equals(m.replaceAll("")) || tempText == null){
					questionList.add("【客户的一次无效操作】");
				}else{
					questionList.add(tempText);
				}
			}
		}
			
		//判断是否是有效会话
		if(timeList.size()>1){
			
			boolean flag = false;
			
			//判断会话中有无客户对话
			for(int k =0;k<timeList.size();k++){
				if((String)(((HashMap) timeList.get(k)).get("he")) != null){
					flag = true;
					break;
				}
			}
			
			if(flag){
				
				//计算对话开始时间
				String dialogStartTime = (String)(((HashMap) timeList.get(0)).get("I"));
				if(dialogStartTime == null){
					dialogStartTime = timeFormat((String)(((HashMap) timeList.get(0)).get("he")));
				}else{
					dialogStartTime = timeFormat(dialogStartTime);
				}
				
				//计算对话结束时间
				String dialogEndTime = "";
				if("".equals(closedTime)){
					closedTime = (String)(((HashMap) timeList.get(timeList.size()-1)).get("I"));
					if(closedTime == null){
						closedTime = timeFormat((String)(((HashMap) timeList.get(timeList.size()-1)).get("he")));
					}else{
						closedTime = timeFormat(closedTime);
					}
					dialogEndTime = closedTime;
				}else{
					dialogEndTime = dialogEndTimeFormat(dialogStartTime,closedTime);
				}
				
				ArrayList talkStartTimeList = getTalkStartTimeList(timeList);
				ArrayList talkEndTimeList = getTalkEndTimeList(timeList,timeFormatConverse(dialogEndTime));
				ArrayList questionContextList = getQuestionContextList(questionList);
				
				//操作dialogstatistics表的SQL语句
				String insertDialogStatisticsSQL = "insert into dialogstatistics values (LAST_INSERT_ID(id) , ? , ? , ? , ? , ? , ? , ? , ? )";
				QueryBuilder dsBuilder = new QueryBuilder(insertDialogStatisticsSQL);
				//DialogStatistics表dialogId字段
				dsBuilder.add(dialogId);
				//DialogStatistics表responseCount字段
				int responseCount = talkStartTimeList.size();
				dsBuilder.add(responseCount);
				//DialogStatistics表averageResponseTime字段
				int averageResponseTime = getAverageResponseTime(talkStartTimeList,talkEndTimeList);
				dsBuilder.add(averageResponseTime);
				//DialogStatistics表oid字段
				dsBuilder.add(oid);
				//DialogStatistics表ona字段
				dsBuilder.add(ona);
				//DialogStatistics表transferFlag字段
				dsBuilder.add(String.valueOf(i));
				//DialogStatistics表dialogStartTime字段
				dsBuilder.add(dialogStartTime);
				//DialogStatistics表dialogEndTime字段
				dsBuilder.add(dialogEndTime);
				
				tranInsert.add(dsBuilder);
				
				//操作talkstatistics表的SQL语句
				String insertTalkStatisticsSQL = "insert into talkstatistics values (LAST_INSERT_ID(id) , ? , ? , ? , ? , ? , ? , ? , ? )";
				
				for(int k = 0; k < talkStartTimeList.size(); k++){
					QueryBuilder tsBuilder = new QueryBuilder(insertTalkStatisticsSQL);
					//TalkStatistics表dialogId字段
					tsBuilder.add(dialogId);
					//TalkStatistics表questionContext字段
					String questionContext = (String) questionContextList.get(k);
					tsBuilder.add(questionContext);
					//TalkStatistics表responseTime字段
					int responseTime = (int) ((Long.parseLong((String) talkEndTimeList.get(k)) - Long.parseLong((String) talkStartTimeList.get(k)))/1000);
					tsBuilder.add(responseTime);
					//TalkStatistics表oid字段
					tsBuilder.add(oid);
					//TalkStatistics表ona字段
					tsBuilder.add(ona);
					//TalkStatistics表displayFlag字段
					if(k == 0){
						//设置首问标记
						tsBuilder.add("0");
					}else if(k == talkStartTimeList.size()-1){
						//设置末问标记
						tsBuilder.add("2");
					}else{
						//设置正问标记
						tsBuilder.add("1");
					}
					//TalkStatistics表questionStartTime字段
					tsBuilder.add(timeFormat((String)talkStartTimeList.get(k)));
					//TalkStatistics表questionEndTime字段
					tsBuilder.add(timeFormat((String)talkEndTimeList.get(k)));
					
					tranInsert.add(tsBuilder);
					
					}
				}
			}
		}
	}
	
	/**
	 * 时间格式 From 秒数 To yyyy-MM-dd HH:mm:ss
	 * 
	 * @param String time 日期的秒数
	 * 
	 * @return 转换后的时间
	 * 
	 */
	public String timeFormat (String time){
		Date date=new Date(Long.parseLong(time));
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		time=formatter.format(date);
		return time;
	}
	
	/**
	 * 时间格式 From yyyy-MM-dd HH:mm:ss To 秒数
	 * 
	 * @param String time yyyy-MM-dd HH:mm:ss
	 * 
	 * @return 转换后的时间
	 * @throws ParseException 
	 * 
	 */
	public static String timeFormatConverse (String time) throws ParseException{
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = formatter.parse(time);
		String result = String.valueOf(d.getTime());
		return result;
	}
	
	/**
	 * 会话结束时间格式转换
	 * 
	 * @param String startTime 会话开始时间
	 * @param String endTime 会话结束时间
	 * 
	 * @return 会话结束时间 yyyy-MM-dd HH:mm:ss
	 * 
	 */
	public String dialogEndTimeFormat (String dialogStartTime,String closedTime){
		String[] startTimeTemp = dialogStartTime.split(" ");
		String[] closedTimeTemp = closedTime.split(" ");
		String dialogEndTime = startTimeTemp[0]+" "+closedTimeTemp[3];
		return dialogEndTime;
	}
	
	/**
	 * 算出每问的开始时间
	 * 
	 * @param ArrayList timeList 时间集合
	 * 
	 * @return 每问开始时间集合
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList getTalkStartTimeList (ArrayList timeList){
		
		ArrayList result = new ArrayList();
		
		for(int i=0; i<timeList.size();i++){
			String temp = (String) ((HashMap) timeList.get(i)).get("he");
			if(i != timeList.size()-1){
				if(temp == null){
					continue;
				}else{
					String tempNext = (String) ((HashMap) timeList.get(i+1)).get("he");
					if(tempNext == null){
						result.add(temp);
					}else{
						continue;
					}
				}
			}else{
				if(temp != null){
					result.add(temp);
				}
			}
		}
		
		return result;
	}
	
	/**
	 * 算出每问的结束时间
	 * 
	 * @param ArrayList timeList 时间集合
	 * @param String closeTime 对话关闭的时间
	 * 
	 * @return 每问结束时间集合
	 * 
	 */
	@SuppressWarnings("unchecked")
	public ArrayList getTalkEndTimeList (ArrayList timeList,String dialogEndTime){
		
		ArrayList result = new ArrayList();
		
		for(int i=0; i<timeList.size();i++){
			String temp = (String) ((HashMap) timeList.get(i)).get("he");
			if(i != timeList.size()-1){
				if(temp != null){
					String tempNext = (String) ((HashMap) timeList.get(i+1)).get("he");
					if(tempNext != null){
						continue;
					}else{
						result.add((String) ((HashMap) timeList.get(i+1)).get("I"));
					}
				}else{
					continue;
				}
			}else{
				if(temp != null){
					result.add(dialogEndTime);
				}
			}
		}
		
		return result;
	}
	
	/**
	 * 每问内容拼装
	 * 
	 * @param ArrayList questionContext 问题内容集合
	 * 
	 * @return 拼接后的每问内容
	 * 
	 */
	@SuppressWarnings("unchecked")
	public ArrayList getQuestionContextList (ArrayList questionList){
		ArrayList result = new ArrayList();
		StringBuffer str = new StringBuffer();
		
		for(int i=0; i<questionList.size();i++){
			String temp = (String)questionList.get(i);
			if(i<questionList.size()-1){
				if("I".equals(temp)){
					continue;
				}else{
					String formatQuestion = questionFormat(temp);
					if(!"".equals(formatQuestion)){
						str.append(formatQuestion);
					}
					String tempNext = (String)questionList.get(i+1);
					if("I".equals(tempNext)){
						result.add(str.toString());
						str = new StringBuffer();
					}else{
						//拼接内容的分隔符
						str.append("<br/>");
					}
				}
			}else{
				if(!"I".equals(temp)){
					String formatQuestion = questionFormat(temp);
					if(!"".equals(formatQuestion)){
						str.append(formatQuestion);
						result.add(str.toString());
					}
				}
			}
		}
		
		return result;
	}
	
	/**
	 * 问题内容表情替换
	 * 
	 * @param String questionContext 问题内容
	 * 
	 * @return 转换后内容
	 * 
	 */
	public String questionFormat (String questionContext){
		StringBuffer result = new StringBuffer();
		//img三个字母大小写不确定
		String temp = questionContext.replaceAll("<img", "<IMG");
		String[] tempArr = temp.split("<IMG src=\"http://chat10.live800.com/live800/chatClient/emotion/images/");
		if(tempArr.length>0){
			for(int i=0;i<tempArr.length;i++){
				if(tempArr[i].indexOf(".gif\"/>")>-1){
					//result.append(tempArr[i].substring(tempArr[i].indexOf(".gif\"/>")+7,tempArr[i].length()));
					result.append(tempArr[i].replace(".gif\"/>", "【表情】"));
				}else{
					result.append(tempArr[i]);
				}
			}
			if(!"".equals(result.toString())){
				return result.toString();
			}else{
				return "【表情】";
			}
		}else{
			return questionContext;
		}
	}
	
	/**
	 * 算出平均响应时间
	 * 
	 * @param ArrayList talkStartTimeList 对话开始时间集合
	 * @param ArrayList talkStartTimeList 对话结束时间集合
	 * 
	 * @return 平均响应时间
	 * 
	 */
	@SuppressWarnings("unchecked")
	public int getAverageResponseTime (ArrayList talkStartTimeList,ArrayList talkEndTimeList){
		
		long result = 0;
		
		for(int i=0;i<talkStartTimeList.size();i++){
			result = (Long.parseLong((String)talkEndTimeList.get(i))-Long.parseLong((String)talkStartTimeList.get(i))) + result;
		}
		
		result = (result/talkEndTimeList.size())/1000;
		
		return (int) result;
	}
	
	/**
	 * 把NULL转换成空字符串
	 * 
	 * @param 需要转换的字符串
	 * 
	 * @return 转换后结果
	 * 
	 */
	private String convertNull(String str) {
		if (null == str) {
			return "";
		} else {
			return str;
		}
	}
}
