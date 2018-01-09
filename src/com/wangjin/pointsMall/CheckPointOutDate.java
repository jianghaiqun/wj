package com.wangjin.pointsMall;

import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.LogUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.ConfigEanbleTaskManager;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.SDIntCalendarSchema;
import com.sinosoft.schema.memberSchema;
import com.sinosoft.schema.memberSet;

import java.math.BigDecimal;

public class CheckPointOutDate extends ConfigEanbleTaskManager {
	public static final String CODE = "com.wangjin.activity.CheckPointOutDate";

	public boolean isRunning(long id) {
		return false;
	}
	/**
	 * 
	* @Title: pastDate 
	* @Description: TODO(积分即将过期检查) 
	* @return boolean    返回类型 
	* @author
	 */
	public boolean pastDate() {
		String CurrentDate = DateUtil.getCurrentDate();
		String CurrentMonth = CurrentDate.substring(0,7);
	
		memberSet membertset=new memberSet();
		memberSchema memberschema=new memberSchema();
		membertset=memberschema.query(new QueryBuilder("where aboutToExpirePoints is not null and aboutToExpirePoints != 0 and aboutToExpireDate < ? ", CurrentDate));
		//事务 
		Transaction tran=new Transaction();
		//过期积分清零
		if (membertset != null && membertset.size() > 0) {
			for (int i = 0; i <membertset.size(); i++) {
				memberschema=membertset.get(i);
				int aboutToExpirePoints=memberschema.getaboutToExpirePoints();
				int overduePoints=memberschema.getoverduePoints();
				//更改用户的可用积分
				int currentValidatePoint=memberschema.getcurrentValidatePoint();
				if(currentValidatePoint>aboutToExpirePoints){
					currentValidatePoint=currentValidatePoint-aboutToExpirePoints;
				}else{
					aboutToExpirePoints = currentValidatePoint;
					currentValidatePoint = 0;
				}
				overduePoints=aboutToExpirePoints+overduePoints;
				tran.add(new QueryBuilder("update member set aboutToExpirePoints=0,aboutToExpireDate=null,currentValidatePoint="+currentValidatePoint+",overduePoints="+overduePoints+" where id='"+memberschema.getid()+"'"));
				
				//积分轨迹
				SDIntCalendarSchema tSDIntCalendarSchema = new SDIntCalendarSchema();
				tSDIntCalendarSchema.setID(NoUtil.getMaxID("IntID") + "");
				tSDIntCalendarSchema.setMemberId(memberschema.getid());// 会员号
				tSDIntCalendarSchema.setIntegral(String.valueOf(aboutToExpirePoints));// 积分值
				tSDIntCalendarSchema.setSource("22");//表示过期积分
				tSDIntCalendarSchema.setManner("1");//支出
				tSDIntCalendarSchema.setStatus("0"); //正常
				tSDIntCalendarSchema.setCreateDate(PubFun.getCurrentDate());
				tSDIntCalendarSchema.setCreateTime(PubFun.getCurrentTime());
				tSDIntCalendarSchema.setDescription("积分过期");
				tran.add(tSDIntCalendarSchema,Transaction.INSERT);
				
				if (i > 0 && i%200 == 0) {
					if(tran.commit()){
						logger.info("执行积分即将过期检查定时任务，过期积分清零提交事务成功！");
					}else{
						logger.error("执行积分即将过期检查定时任务，过期积分清零提交事务发生错误！");
						return false;
					}
					tran=new Transaction();
				}
			}
			
			if(tran.commit()){
				logger.info("执行积分即将过期检查定时任务，过期积分清零提交事务成功！");
			}else{
				logger.error("执行积分即将过期检查定时任务，过期积分清零提交事务发生错误！");
				return false;
			}
		}
		tran=new Transaction();
		//当前月的第一天
		String firstdate=DateUtil.getFirstDayOfMonth(DateUtil.getCurrentDate());
		//当前月的最后一天
		String lastdate=DateUtil.toString(DateUtil.getLastDayOfMonth(DateUtil.getCurrentDate() + " 23:59:59"));
		//过期期限
		String  PointValidityPeriod=Config.getValue("PointValidityPeriod");
		String sql_time="";
		if(StringUtil.isNotEmpty(PointValidityPeriod)&&PointValidityPeriod.length()>1){
			if(PointValidityPeriod.trim().endsWith("Y")){
				String num=PointValidityPeriod.substring(0,PointValidityPeriod.indexOf("Y"));
				try {
					int months=Integer.parseInt(num)*12;
					sql_time=months+" MONTH";
				} catch (Exception e) {
					logger.error("执行积分即将过期检查定时任务，过期期间设置设置的Y前面的数值非数字,值为："+num + e.getMessage(), e);
					return false;
				}
				
			}else if(PointValidityPeriod.trim().endsWith("M")){
				String num=PointValidityPeriod.substring(0,PointValidityPeriod.indexOf("M"));
				try {
					int months=Integer.parseInt(num);
					sql_time=months+" MONTH";
				} catch (Exception e) {
					logger.error("执行积分即将过期检查定时任务，过期期间设置设置的M前面的数值非数字,值为："+num + e.getMessage(), e);
					return false;
				}
			}else if(PointValidityPeriod.trim().endsWith("D")){
				String num=PointValidityPeriod.substring(0,PointValidityPeriod.indexOf("D"));
				try {
					int days=Integer.parseInt(num);
					sql_time=days+" DAY";
				} catch (Exception e) {
					logger.error("执行积分即将过期检查定时任务，过期期间设置设置的D前面的数值非数字,值为："+num + e.getMessage(), e);
					return false;
				}
			}else{
				logger.error("执行积分即将过期检查定时任务，过期期间设置有误，格式应为数字+Y或M或D");
				return false;
			}
			
		}else{
			logger.error("执行积分即将过期检查定时任务，过期期间设置为空或格式错误！");
			return false;
		}
		//筛选sql
		String sql=" select c.MemberId, round(sum(c.Integral),0) from sdintcalendar c where c.status='0' and c.Manner='0' "
				+ "and DATE_FORMAT(DATE_ADD(c.svaliDate,INTERVAL "+sql_time+"),'%Y-%m-%d') >= DATE_FORMAT('"+firstdate+"','%Y-%m-%d') "
				+ "and DATE_FORMAT(DATE_ADD(c.svaliDate,INTERVAL "+sql_time+"),'%Y-%m-%d') <= DATE_FORMAT('"+lastdate+"','%Y-%m-%d') "
				+ "and not exists(select p.memberId from pointOverTimeRecord p where p.memberId=c.MemberId and DATE_FORMAT(p.operationDate, '%Y-%m') = ?) "
				+ "group by c.MemberId having round(sum(c.Integral),0)> 0 ";
		DataTable dt=new QueryBuilder(sql, CurrentMonth).executeDataTable();
		
		for (int i = 0; i < dt.getRowCount(); i++) {
			String memberid=dt.getString(i, 0);
			int integral=dt.getInt(i, 1);
			if(StringUtil.isEmpty(memberid)){
				logger.error("执行积分即将过期检查定时任务，会员id为空，积分流水表id为：{}", dt.getString(i, 3));
				continue;
			}
			
			DataTable overDt = new QueryBuilder("select usedPoints, operationDate from pointOverTimeRecord where memberId = ?", memberid).executeDataTable();
			if (overDt != null && overDt.getRowCount() > 0) {
				// 使用的积分
				String newUsedPoints = new QueryBuilder("select FLOOR(sum(Integral)) from sdintcalendar where status='0' and Manner='1' and memberid=? and source!='22' and CONCAT(createdate, ' ',  createTime) > ?", memberid, overDt.getString(0, 1)).executeString();
				if(StringUtil.isEmpty(newUsedPoints)){
					newUsedPoints="0";
				}
				int totalUsedPoint = new BigDecimal(newUsedPoints).intValue() + overDt.getInt(0, 0);
				// 即将过期积分>=使用积分
				if (integral >= totalUsedPoint) {
					tran.add(new QueryBuilder("update pointOverTimeRecord set usedPoints=0, operationDate = now() where memberid = ?",  memberid));
					integral = integral - totalUsedPoint;
				} else {
					// 即将过期积分<使用积分
					tran.add(new QueryBuilder("update pointOverTimeRecord set usedPoints=?, operationDate = now() where memberid = ?", (totalUsedPoint-integral), memberid));
					integral = 0;
				}
			} else {
				// 使用的积分
				String newUsedPoints = new QueryBuilder("select FLOOR(sum(Integral)) from sdintcalendar where status='0' and Manner='1' and memberid=?  and source!='22' and createdate > '2015-07-30'", memberid).executeString();
				if(StringUtil.isEmpty(newUsedPoints)){
					newUsedPoints="0";
				}
				int totalUsedPoint =  new BigDecimal(newUsedPoints).intValue();
				// 即将过期积分>=使用积分
				if (integral >= totalUsedPoint) {
					tran.add(new QueryBuilder("insert into pointOverTimeRecord (memberid, usedPoints, operationDate) values(?, 0, now())", memberid));
					integral = integral - totalUsedPoint;
				} else {
					// 即将过期积分<使用积分
					tran.add(new QueryBuilder("insert into pointOverTimeRecord (memberid, usedPoints, operationDate) values(?, ?, now())", memberid, (totalUsedPoint-integral)));
					integral = 0;
				}
			}
			if (integral > 0) {
				tran.add(new QueryBuilder("update member set aboutToExpirePoints=?, aboutToExpireDate = ? where id = '"+memberid+"'", integral, lastdate));
			} else {
				tran.add(new QueryBuilder("update member set aboutToExpirePoints=null, aboutToExpireDate = null where id = ?", memberid));
			}
			
			
			if (i > 0 && i%200 == 0) {
				if(tran.commit()){
					logger.info("执行积分即将过期检查定时任务，提交事务成功！");
				}else{
					logger.error("执行积分即将过期检查定时任务，提交事务发生错误！");
					return false; 
				}
				tran=new Transaction();
			}
		}
		if(tran.commit()){
			logger.info("执行积分即将过期检查定时任务，提交事务成功！");
		}else{
			logger.error("执行积分即将过期检查定时任务，提交事务发生错误！");
			return false;
		}
		return true;
	}
	
	/**
	 * 
	* @Title: execute 
	* @Description: TODO(定时任务调用主方法) 
	* @return void    返回类型 
	* @author
	 */
	public void execute(long id) {
		// 积分过期提醒
		if ("0".equals(id + "")) {
			if (pastDate()) {
				logger.info("积分过期检测成功！");
			}
		}
	}

	@Override
	public Mapx<String, String> getConfigEnableTasks() {
		Mapx<String, String> map = new Mapx<String, String>();
		map.put("0", "积分过期定时任务");
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
		return "积分过期定时任务";
	}

	@Override
	public boolean isRunning(String paramString) {
		return false;
	}

	@Override
	public String getID() {
		return "com.wangjin.activity.CheckPointOutDate";
	}
}
