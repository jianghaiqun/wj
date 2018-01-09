package com.sinosoft.datachannel;

import com.sinosoft.framework.cache.CacheManager;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.schedule.CronExpressionException;
import com.sinosoft.framework.schedule.CronMonitor;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.platform.pub.ConfigEanbleTaskManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class NoPaySendEmail extends ConfigEanbleTaskManager {
	public static final String CODE = "com.sinosoft.datachannel.NoPaySendEmail";

	public boolean isRunning(long id) {
		return false;
	}

	/**
	 * 
	* @Title: sendMail 
	* @Description: TODO(向客服发送待支付邮件) 
	* @return boolean    返回类型 
	* @author zhangjing
	 */ 
	public boolean sendMail() {
		QueryBuilder qb = new QueryBuilder(" select CronExpression from zdSchedule where TypeCode=? and SourceID=? ");
		qb.add(CODE);
		qb.add("0");
		String CronExpression = qb.executeOneValue() + "";
		if (StringUtil.isEmpty(CronExpression)) {
			logger.warn("定时计划{}时间表达式为空.", CODE);
		}
		try {
			//上次定时任务执行的时间
			String PreviousRunTime = DateUtil.toDateTimeString(CronMonitor.getPreviousRunTime(CronExpression));
			String NextRunTime = DateUtil.toDateTimeString(CronMonitor.getNextRunTime(CronExpression));
			SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化时间
			if(DateUtil.compare(PreviousRunTime,NextRunTime,"yyyy-MM-dd HH:mm:ss")==0){
				logger.info("客服发送的待支付邮件两次定时任务执行的时间相同，分别为：{}", PreviousRunTime+","+NextRunTime);
			}else if(DateUtil.compare(PreviousRunTime,NextRunTime,"yyyy-MM-dd HH:mm:ss")>0){
				logger.info("客服发送的待支付邮件下次执行定时任务时间小于上次定时任务执行的时间，分别为：{}", PreviousRunTime+","+NextRunTime);
			}
			logger.info("客服发送待支付邮件：上次时间：{}下次时间：{}", PreviousRunTime, NextRunTime);
			//两次定时任务的时间差
			long sub_minits=0;
			 try {
				 sub_minits = (d.parse(NextRunTime).getTime()-d.parse(PreviousRunTime).getTime())/60000;
			} catch (ParseException e) {
				 logger.error(e.getMessage(), e);
			}
			//int  存储的最大范围是 2147483647
			if(sub_minits>2147483647){
				logger.warn("客服发送的待支付邮件时间设置过长,请检查代码设置!");
				return true;
			}
			//获取配置的邮箱信息
			Mapx map_NoPayMail = CacheManager.getMapx("Code", "NoPayMail");
			if(map_NoPayMail==null||map_NoPayMail.size()==0){
				logger.warn("待支付邮件发送，没有配置客服邮箱！");
				return false;
			}
//			//添加设定的日期
//			String endtime=DateUtil.toDateTimeString(DateUtil.addMinute(CronMonitor.getPreviousRunTime(CronExpression),(int)(sub_minits)));
			//订单编号，产品，计划，保费，订单创建时间，投保人姓名，性别，年龄，被保险人数，电话，邮箱
			QueryBuilder qb_order=new QueryBuilder("SELECT a.orderSn,a.productName,a.planName,a.productprice,a.createDate,c.applicantName,c.applicantsexname," +
					"c.applicantAge,COUNT(b.ordersn),c.applicantMobile,c.applicantMail FROM sdinformation a ,sdinformationinsured  b ,SDInformationAppnt c,sdorders d WHERE a.ordersn=b.ordersn AND a.informationsn=c.informationSn and a.ordersn=d.ordersn and d.orderstatus='5'  AND UNIX_TIMESTAMP(DATE_FORMAT(a.createDate,'%Y-%m-%d %H:%i:%s')) >=UNIX_TIMESTAMP(DATE_FORMAT(?,'%Y-%m-%d %H:%i:%s'))  AND UNIX_TIMESTAMP(DATE_FORMAT(a.createDate,'%Y-%m-%d %H:%i:%s')) < UNIX_TIMESTAMP(DATE_FORMAT(?,'%Y-%m-%d %H:%i:%s')) GROUP BY b.ordersn  ");
			qb_order.add(PreviousRunTime);
			qb_order.add(NextRunTime);
			DataTable dt=qb_order.executeDataTable();
			if(dt.getRowCount()<1){
				logger.warn("待支付邮件发送，没有匹配的订单数据！");
				return false;
			}
			for (int i = 0; i < dt.getRowCount(); i++) {
				Map map=map_NoPayMail;
				Iterator it = map.entrySet().iterator();
				Map<String,Object> map_mail=new HashMap<String,Object>();
				String orderSn=dt.getString(i,0);
				map_mail.put("orderSn",orderSn);
				String productName=dt.getString(i,1);
				map_mail.put("productName",productName);
				String planName=dt.getString(i,2);
				if(StringUtil.isEmpty(planName)){
					map_mail.put("planName","无");
				}else{
					map_mail.put("planName",planName);
				}
				String productprice=dt.getString(i,3);
				map_mail.put("productprice",productprice);
				String createDate=dt.getString(i,4);
				map_mail.put("createDate",createDate);
				String applicantName=dt.getString(i,5);
				map_mail.put("applicantName",applicantName);
				String applicantSex=dt.getString(i,6);
				map_mail.put("applicantSex",applicantSex);
				String applicantAge=dt.getString(i,7);
				map_mail.put("applicantAge",applicantAge);
				String num=dt.getString(i,8);
				map_mail.put("num",num);
				String applicantMobile=dt.getString(i,9);
				map_mail.put("applicantMobile",applicantMobile);
				String applicantMail=dt.getString(i,10);
				map_mail.put("applicantMail",applicantMail);
				while (it.hasNext()) {
					Entry entry = (Entry) it.next();
					Object mail = entry.getKey();
					Object name = entry.getValue();
					Map<String, Object> map_data=new HashMap<String, Object>();
					map_data.putAll(map_mail);
					map_data.put("name",String.valueOf(name));
					if (ActionUtil.sendMail("wj00094", String.valueOf(mail), map_data)) {
						logger.info("客服发送的待支付邮件发送成功！!");
					} else {
						logger.error("客服发送的待支付邮件发送失败！!");
					}
				}
			}
		} catch (CronExpressionException e) {
			logger.error(e.getMessage(), e);
		}
		return true;
	}

	/**
	 * 定时任务调用主方法
	 * 
	 * @param id
	 */
	public void execute(long id) {
		// 向客服发送待支付邮件
		if ("0".equals(id + "")) {
			if (sendMail()) {
				logger.info("向客服人员发送待支付邮件！");
			}
		}
	}

	@Override
	public Mapx<String, String> getConfigEnableTasks() {
		Mapx<String, String> map = new Mapx<String, String>();
		map.put("0", "向客服人员发送待支付邮件定时任务");
		return map;
	}

	@Override
	public void execute(String paramString) {
		execute(Long.parseLong(paramString));
	}

	public String getCode() {
		return CODE;
	}

	public String getName() {
		return "待支付邮件定时任务";
	}

	@Override
	public boolean isRunning(String paramString) {
		return false;
	}

	@Override
	public String getID() {
		return "com.sinosoft.datachannel.NoPaySendEmail";
	}
}
