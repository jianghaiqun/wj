package com.sinosoft.datachannel;

import cn.com.sinosoft.common.email.MessageConstant;
import cn.com.sinosoft.entity.Member;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.platform.pub.ConfigEanbleTaskManager;
import com.sinosoft.points.IntegralConstant;
import com.sinosoft.points.PointsCalculate;
import com.sinosoft.schema.MemberGradeSchema;
import com.sinosoft.schema.MemberGradeSet;
import com.sinosoft.schema.MemberGroupSchema;
import com.sinosoft.schema.MemberGroupSet;
import com.sinosoft.schema.memberSchema;
import org.jfree.util.Log;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PointServiceManager extends ConfigEanbleTaskManager {

	public static final String CODE = "com.sinosoft.datachannel.PointServiceManager";

	public boolean isRunning(long id) {

		return false;
	}

	@Override
	public Mapx<String, String> getConfigEnableTasks() {

		Mapx<String, String> map = new Mapx<String, String>();
		map.put("1", "会员积分统计冻结转可用");
		map.put("2", "统计会员团单数定时任务");
		map.put("3", "会员等级升级定时任务");
		map.put("4", "会员升级(指定日期)定时任务");
		return map;

	}

	/**
	 * 会员积分冻结转可用
	 * 
	 * @return
	 */
	private boolean calculatePoint() {

		try {
			String sql = "  select s1.ID ,s1.MemberId,s1.Integral ,s2.orderSn,s2.Id SDID,s1.Source "
					   + " from SDIntCalendar s1, SDinformation s2  "
					   + " where UNIX_TIMESTAMP( DATE_FORMAT(DATE_ADD(s2.StartDate, INTERVAL  "
					   + " IFNULL((  SELECT  CASE hesitation WHEN ''  THEN '0'  ELSE hesitation END  FROM fmrisk fm WHERE fm.riskperiod ='L' AND s2.productId=fm.RiskCode ) ,0) DAY),'%Y-%m-%d') ) "
					   + " <= UNIX_TIMESTAMP(DATE_FORMAT(now(),'%Y-%m-%d')) "
					   + " and s1.status = '1' and s1.businessid=s2.ordersn  ";
			QueryBuilder qb = new QueryBuilder(sql);
			DataTable dt = qb.executeDataTable();
			if (dt != null && dt.getRowCount() != 0) {
				for (int i = 0; i < dt.getRowCount(); i++) {
					memberSchema tmemberSchema = new memberSchema();
					tmemberSchema.setid(dt.getString(i, "MemberId"));
					if (StringUtil.isNotEmpty(dt.getString(i, "Integral"))
							&& Double.parseDouble(dt.getString(i, "Integral")) > 0) {
						if (tmemberSchema.fill() && tmemberSchema != null
								&& tmemberSchema.getpoint() - Integer.parseInt(dt.getString(i, "Integral")) >= 0) {
							Transaction transaction = new Transaction();
							tmemberSchema.setpoint(tmemberSchema.getpoint() - Integer.parseInt(dt.getString(i, "Integral")));
							tmemberSchema.setcurrentValidatePoint(tmemberSchema.getcurrentValidatePoint()
									+ Integer.parseInt(dt.getString(i, "Integral")));
							// 送推荐人积分
							if ("21".equals(dt.getString(i, "Source"))) {
								tmemberSchema.setrecommendBuyPoints(tmemberSchema.getrecommendBuyPoints()
										+ Integer.parseInt(dt.getString(i, "Integral")));
							} else {
								tmemberSchema.setisBuy("Y");
							}
							transaction.add(tmemberSchema, Transaction.UPDATE);
							transaction.add(new QueryBuilder("update SDIntCalendar set status = '0' where id = ?", dt
									.getString(i, "ID")));
							transaction.add(new QueryBuilder("update SDinformation set pointstatus='2' where id= ?", dt
									.getString(i, "SDID")));
							transaction.commit();
						}
					}
				}
			}
			return true;
		} catch (Exception e) {
			logger.error("类PointServiceManager执行方法calculatePoint()发生异常！" + e.getMessage(), e);
			return false;
		}
	}

	public static void main(String[] args) {

		PointServiceManager PM = new PointServiceManager();
		PM.upGrade("4");

	}

	
    
	/**
	 * 新会员升级逻辑
	 * 每次查找昨天(或者某天)
	 * @return
	 */
    private boolean upEveryGradeNew( Map<String, MemberGradeSchema> gradeMap, String K1BirPoints, String K2BirPoints, String K2BuyPoints ,String currentDate) throws Exception {
    	String removeUpedGrade ="";
    	if(StringUtil.isEmpty(currentDate)){
    		currentDate = DateUtil.getYesterday();//当某天执行失败需要重新执行时，计算升级会员信息时
    		removeUpedGrade = " AND (m.accuEndDate IS NULL OR m.accuEndDate='' OR DATE_FORMAT(m.accuEndDate, '%Y-%m-%d') <  DATE_FORMAT('"+currentDate+"','%Y-%m-%d') ) ";
    	}
 		//查找昨天(或指定的某天)订单生效且未升级的用户数据
 		String sql_select = " SELECT  m.id,m.grade ,m.consumeAmount,m.buyCount ,m.email,m.FromWap, m.accuEndDate, "
 				+ " ROUND (SUM(r.payPrice),2) AS SumPayPrice FROM sdorders o,sdinformationrisktype r ,member m ,tradeinformation t  "
 				+ " WHERE  o.orderSn = r.orderSn AND m.id=o.memberId  AND  o.orderSn = t.ordID  AND r.appStatus IN ('1', '3')  " //AND r.payPrice IS NOT NULL AND r.payPrice != ''
 				+ " AND DATE_FORMAT(r.svaliDate, '%Y-%m-%d') = DATE_FORMAT(DATE_ADD('"+currentDate+"', INTERVAL  "
 				+ "- IFNULL((  SELECT  CASE hesitation WHEN ''  THEN '0'  ELSE hesitation END  FROM fmrisk fm WHERE fm.riskperiod ='L' AND r.riskCode=fm.RiskCode ) ,0) DAY),'%Y-%m-%d') ";
 		if(StringUtil.isNotEmpty(removeUpedGrade)){
 			sql_select = sql_select +  removeUpedGrade;
 		}
 		sql_select = sql_select + " GROUP BY m.id ,DATE_FORMAT(t.receiveDate,'%Y-%m-%d')  ";
 		DataTable dt = new QueryBuilder(sql_select).executeDataTable();
 		// 会员升级
 		if(dt != null && dt.getRowCount() > 0){
 			StringBuffer MembertSetSql = null;
 	 		
 			// 升级到K1的邮件提醒数据
 			List<Map<String, Object>> k1EmailDatas = new ArrayList<Map<String, Object>>();
 			// 升级到K2的邮件提醒数据
 			List<Map<String, Object>> k2EmailDatas = new ArrayList<Map<String, Object>>();
 			
 			// 订单有效数限制
 			String orderCount = "";
 			// 累计保费限制
 			String sumPrem = "";
 			// 记录会员有效订单数
 			int ordCountTemp = 0;
 			int addCountTemp = 1;
 			// 记录会员累计保费
 			BigDecimal sumPremTemp = new BigDecimal(0);
 			BigDecimal addSumPremTemp = new BigDecimal(0);
 			String grade = "";//用户等级
 			boolean upgradeFlag = false;
 	 		// 事务
 	 		Transaction tran = new Transaction();
 			upgradeFlag = false;
 			for (int i = 0; i < dt.getRowCount(); i++) {
 				MembertSetSql = new StringBuffer(" UPDATE member SET ");
 				grade = dt.getString(i, "grade");
 	 			if(StringUtil.isEmpty(grade)){
 	 				logger.error("无等级{}的升级信息", grade);
 	 				return true;
 	 			}
 	 			MemberGradeSchema schema = gradeMap.get(grade.toUpperCase());
 	 			if(schema == null){
					logger.error("无等级{}的升级信息", grade);
 	 				return true;
 	 			}
 	 			if(i+1 != dt.getRowCount() && dt.getString(i, "id").equals(dt.getString(i+1, "id")) ){
 	 				++addCountTemp;
 	 				addSumPremTemp = addSumPremTemp.add(new BigDecimal(dt.getDouble(i, "SumPayPrice"))).setScale(2,BigDecimal.ROUND_HALF_UP);
 	 				continue;
 	 			}
 				orderCount = schema.getorderCount();
 				sumPrem = schema.getsumPrem();
 				try{
 					sumPremTemp = new BigDecimal(dt.getDouble(i, "consumeAmount"));
 				}catch(Exception e){
 					sumPremTemp = new BigDecimal("0.0");
 				}
 				
 				if(addCountTemp!=1){
 					sumPremTemp = sumPremTemp.add(addSumPremTemp).setScale(2,BigDecimal.ROUND_HALF_UP)  ;
 					addSumPremTemp =  new BigDecimal(0);
 				}
 				
 				// 累计会员总有效订单数
 				if(StringUtil.isEmpty( dt.getString(i, "buyCount"))){//付日期，一天内购买多少单都算一单
 					ordCountTemp = addCountTemp;
 				}else{
 					ordCountTemp = dt.getInt(i, "buyCount") + addCountTemp;
 				}
 				addCountTemp = 1;
 				
				// 累计会员总保费
				sumPremTemp = sumPremTemp.add(new BigDecimal(dt.getDouble(i, "SumPayPrice"))).setScale(2,BigDecimal.ROUND_HALF_UP)  ;


				// 更新会员累计有效订单数、累计保费、统计截止时间
				MembertSetSql.append(" buyCount = '" + ordCountTemp +"',consumeAmount = '" + sumPremTemp.toString() +"' ");
				if(StringUtil.isEmpty(dt.getString(i, "accuEndDate")) || (StringUtil.isNotEmpty(dt.getString(i, "accuEndDate")) && DateUtil.compare(currentDate,  dt.getString(i, "accuEndDate") ) > 0)){
					MembertSetSql.append(" , accuEndDate = '" + currentDate +"'");
				}
				// 有订单有效数限制时
				if (StringUtil.isNotEmpty(orderCount) && !"0".equals(orderCount)) {
					// 判断会员是否达到订单有效数限制,达到升级Flag置为true
					if (ordCountTemp >= Integer.valueOf(orderCount)) {
						upgradeFlag = true;
					}
				} else {
					upgradeFlag = true;
				}
				// 达到订单有效数限制
				if (upgradeFlag) {
					// 有累计保费限制时
					if (StringUtil.isNotEmpty(sumPrem) && Double.valueOf(sumPrem) != 0) {
						// 判断会员是否达到累计保费限制,达到升级Flag置为true
						if (sumPremTemp.compareTo(new BigDecimal(sumPrem)) >= 0) {
							upgradeFlag = true;
						} else {
							// 未达到限制 置为false
							upgradeFlag = false;
						}
					}
				}

				// 该会员可升级
				if (upgradeFlag) {
					grade = schema.getgradeCode();
					if (StringUtil.isNotEmpty(dt.getString(i, "email")) && (StringUtil.isEmpty(dt.getString(i, "FromWap")) || !dt.getString(i, "FromWap").contains("tb"))) {
						Map<String, Object> data = new HashMap<String, Object>();
						Member dataMember = new Member();
						dataMember.setEmail(dt.getString(i, "email"));
						data.put("Member", dataMember);
						data.put("K1BirPoints", StringUtil.subZeroAndDot(K1BirPoints));
						data.put("K2BirPoints", StringUtil.subZeroAndDot(K2BirPoints));
						data.put("K2BuyPoints", StringUtil.subZeroAndDot(K2BuyPoints));
						if ("K1".equals(schema.getgradeCode())) {

							MemberGradeSchema schema1 = gradeMap.get("K1");
							boolean ungraFlag = true;
							if (StringUtil.isNotEmpty(schema1.getorderCount())) {
								if (ordCountTemp >= Integer.valueOf(schema1.getorderCount())) {
									data.put("BuyCount", "");
								} else {
									data.put("BuyCount", Integer.valueOf(schema1.getorderCount()) - ordCountTemp);
									ungraFlag = false;
								}
							}
							if (StringUtil.isNotEmpty(schema1.getsumPrem())) {
								if (sumPremTemp.compareTo(new BigDecimal(schema1.getsumPrem())) >= 0) {
									data.put("BuyAmount", "");
								} else {
									data.put("BuyAmount",StringUtil.subZeroAndDot(new BigDecimal(schema1.getsumPrem()).subtract(sumPremTemp).setScale(2, BigDecimal.ROUND_HALF_UP).toString()));
									ungraFlag = false;
								}
							}

							if (ungraFlag) {
								grade = schema1.getgradeCode();
								k2EmailDatas.add(data);
							} else {
								k1EmailDatas.add(data);
							}

						} else if ("K2".equals(schema.getgradeCode())) {
							k2EmailDatas.add(data);
						}
					}
					MembertSetSql.append(" , grade = '" + grade +"' ");
				}
				MembertSetSql.append("  WHERE id='"+ dt.getString(i, "id") +"' ");
				tran.add(new QueryBuilder(MembertSetSql.toString()));
			}
 			if (tran.commit()) {
 				logger.info("执行会员升级定时任务，会员升级提交事务成功！");
 				sendGradEmail(k1EmailDatas, k2EmailDatas);
 			} else {
 				logger.error("执行会员升级定时任务，会员升级提交事务发生错误！");
 				return false;
 			}
 		}else{
 			logger.info("执行会员升级定时任务时发现昨天没有用户下单！");
 		}
 		
 		return true;
     }
     
    
    
    
    private void sendGradEmail(List<Map<String, Object>> k1EmailDatas, List<Map<String, Object>> k2EmailDatas) {
    	try { 
    		Member member = null;
			String toMail = null;
    		if (k1EmailDatas.size() > 0) {
				for (Map<String, Object> data : k1EmailDatas) {
					if(data.get("Member") != null){
						member = (Member)data.get("Member");
						toMail = member.getEmail();
						if(StringUtil.isNotEmpty(toMail)){
							data.remove("Member");
							data.put(MessageConstant.PARAM_SHOW_ACTIVITY_PRODUCT, "1");
							ActionUtil.sendMail("wj00127", toMail, data);
						}
					}
				}
			}
			if (k2EmailDatas.size() > 0) {
				for (Map<String, Object> data : k2EmailDatas) {
					if(data.get("Member") != null){
						member = (Member)data.get("Member");
						toMail = member.getEmail();
						if(StringUtil.isNotEmpty(toMail)){
							data.remove("Member");
							data.put(MessageConstant.PARAM_SHOW_ACTIVITY_PRODUCT, "1");
							ActionUtil.sendMail("wj00128", toMail, data);
						}
					}
				}
			}
    	} catch(Exception e) {
    		logger.error("发送升级邮件异常！"+e.getMessage(), e);
    	}
    }
    
	/**
	 * 会员等级升级
	 * 
	 * @return
	 */
	public boolean upGrade(String UseId) {
		String K1BirPoints = "";
		String K2BirPoints = "";
		// K2会员购买积分加成数
		String K2BuyPoints = "";
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("MemberGrade", "K1");
		try {
			Map<String, Object> map = new PointsCalculate().pointsManage(IntegralConstant.POINT_SEARCH,IntegralConstant.POINT_SOURCE_BIRTH_MONTH, param);
			if (IntegralConstant.SUCCESS.equals(String.valueOf(map.get(IntegralConstant.STATUS)))) {
				if (StringUtil.isNotEmpty((String) map.get(IntegralConstant.ACTION_POINTS))) {
					K1BirPoints = new BigDecimal(1).add(new BigDecimal((String) map.get(IntegralConstant.ACTION_POINTS))).toString();
				} else {
					K1BirPoints = "";
				}
			}
			
			param = new HashMap<String, Object>();
			param.put("MemberGrade", "K2");
			map = new PointsCalculate().pointsManage(IntegralConstant.POINT_SEARCH,IntegralConstant.POINT_SOURCE_BIRTH_MONTH, param);
			if (IntegralConstant.SUCCESS.equals(String.valueOf(map.get(IntegralConstant.STATUS)))) {
				if (StringUtil.isNotEmpty((String) map.get(IntegralConstant.ACTION_POINTS))) {
					K2BirPoints = new BigDecimal(1).add(new BigDecimal((String) map.get(IntegralConstant.ACTION_POINTS))).toString();
				} else {
					K2BirPoints = "";
				}
			}
	
			param = new HashMap<String, Object>();
			param.put("MemberGrade", "K2");
			map = new PointsCalculate().pointsManage(IntegralConstant.POINT_SEARCH,IntegralConstant.POINT_SOURCE_BUY, param);
			if (IntegralConstant.SUCCESS.equals(String.valueOf(map.get(IntegralConstant.STATUS)))) {
				if (StringUtil.isNotEmpty((String) map.get(IntegralConstant.ACTION_POINTS))) {
					K2BuyPoints = StringUtil.subZeroAndDot(new BigDecimal(100).multiply(new BigDecimal((String) map.get(IntegralConstant.ACTION_POINTS))).toString())+ "%";
				} else {
					K2BuyPoints = "";
				}
			}
		}catch (Exception e) {
			logger.error("类PointServiceManager执行方法upGrade()发生异常！取K1、K2会员生日月、购买加成错误！" + e.getMessage(), e);
			return false;
		}
		// 取出所有的等级对应上一等级的信息
		Map<String, MemberGradeSchema> gradeMap = new HashMap<String, MemberGradeSchema>();
		MemberGradeSet set = new MemberGradeSet();
		MemberGradeSchema schema = new MemberGradeSchema();
		set = schema.query(new QueryBuilder("where preGradeCode is not null and preGradeCode != ''"));
		if (set != null && set.size() > 0) {
			int size = set.size();
			for (int i = 0; i < size; i++) {
				schema = new MemberGradeSchema();
				schema = set.get(i);
				gradeMap.put(schema.getpreGradeCode(), schema);
			}
			MemberGradeSchema schema_k2 = new MemberGradeSchema();
			schema_k2.setorderCount("99999");
			schema_k2.setsumPrem("999999999");
			gradeMap.put("K2", schema_k2);//k2会员升级VIP需手操作员动执行
		} else {
			logger.error("类PointServiceManager执行方法upGrade()未取到会员等级信息！");
			return false;
		}
		
		String UpGradeDate =new QueryBuilder("SELECT description FROM zdschedule WHERE typecode='com.sinosoft.datachannel.PointServiceManager' AND sourceid='4' LIMIT 1 ").executeString();
		 
		if(UseId.equals("4") && StringUtil.isNotEmpty(UpGradeDate)){
			try {
				String dates[] =UpGradeDate.split("/");
				DateUtil.parse(dates[0]);
				if(dates.length == 1){
					if (!upEveryGradeNew(gradeMap, K1BirPoints, K2BirPoints, K2BuyPoints,UpGradeDate)) {
						logger.error("类PointServiceManager执行方法upGrade(),固定日期({})等级升级失败！", UpGradeDate);
					}
				}else{
					for(int i=0;i<=Integer.parseInt(dates[1]);i++){
						UpGradeDate = DateUtil.toString(DateUtil.addDay( DateUtil.parse(dates[0]),i)) ;
						if (!upEveryGradeNew(gradeMap, K1BirPoints, K2BirPoints, K2BuyPoints,UpGradeDate)) {
							logger.error("类PointServiceManager执行方法upGrade(),固定日期({})等级升级失败！", UpGradeDate);
						}
					}
				}
				Transaction tran = new Transaction();
				//tran.add( new QueryBuilder("UPDATE zdconfig SET VALUE='' WHERE TYPE='UpGradeDate'"));
				tran.add( new QueryBuilder("UPDATE zdschedule SET description='' WHERE  typecode='com.sinosoft.datachannel.PointServiceManager' AND sourceid='4' "));
				tran.commit();
			} catch (Exception e) {
				logger.error("类PointServiceManager执行方法upGrade(),固定日期会员等级升级发生异常！" + e.getMessage(), e);
			}
		}

		if(UseId.equals("3")){
			try {
				if (!upEveryGradeNew(gradeMap, K1BirPoints, K2BirPoints, K2BuyPoints,"")) {
					logger.error("类PointServiceManager执行方法upGrade(),每日定时任务等级升级失败！");
				}
	
			} catch (Exception e) {
				logger.error("类PointServiceManager执行方法upGrade(),每日定时任务会员等级升级发生异常！" + e.getMessage(), e);
			}
		}
    	return true;
	}

	/**
	 * 统计会员团单数
	 * 
	 * @return
	 */
	private boolean groupCount() {

		String currentDate = DateUtil.getCurrentDateTime();
		Date date = new Date();
		boolean commitFlag = false;
		// 事务
		Transaction tran = new Transaction();
		// 查询未统计过的会员id
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT m.id,DATE_FORMAT(t.receiveDate,'%Y-%m-%d'),i.recognizeeIdentityId ");
		sb.append("FROM member m,sdorders s,sdinformationrisktype r,tradeinformation t,sdinformationinsured i ");
		sb.append("WHERE m.id=s.memberid and s.ordersn=t.ordid AND r.appStatus='1' AND DATE_FORMAT(r.svaliDate,'%Y-%m-%d %H:%i:%s') < ? ");
		sb.append("AND s.ordersn = r.orderSn AND i.orderSn=r.orderSn AND r.recognizeeSn=i.recognizeeSn ");
		sb.append("AND NOT EXISTS (SELECT memberId FROM MemberGroup WHERE memberId = m.id) ");
		sb.append("ORDER BY m.id ASC,DATE_FORMAT(t.receiveDate,'%Y-%m-%d') ASC,i.recognizeeIdentityId ASC");

		QueryBuilder qb = new QueryBuilder(sb.toString(), currentDate);
		DataTable dt = qb.executeDataTable();
		MemberGroupSet insertSet = new MemberGroupSet();
		MemberGroupSchema schema = new MemberGroupSchema();
		if (dt != null && dt.getRowCount() > 0) {
			int rowCount = dt.getRowCount();
			String memberId = dt.getString(0, 0);
			String receiveDate = dt.getString(0, 1);
			Map<String, String> idenMap = new HashMap<String, String>();
			// 记录会员团单数
			int groCount = 0;
			for (int i = 0; i < rowCount; i++) {
				if (!memberId.equals(dt.getString(i, 0))) {
					if (idenMap.size() >= 5) {
						groCount += 1;
					}
					schema = new MemberGroupSchema();
					schema.setmemberId(memberId);
					schema.setgroupCount(groCount);
					schema.setstatisticsEndDate(DateUtil
							.parseDateTime(currentDate));
					schema.setcreateDate(new Date());
					schema.setcreateUser("system");
					insertSet.add(schema);
					memberId = dt.getString(i, 0);
					groCount = 0;
					receiveDate = dt.getString(i, 1);
					idenMap = new HashMap<String, String>();
				} else if (!receiveDate.equals(dt.getString(i, 1))) {
					if (idenMap.size() >= 5) {
						groCount += 1;
					}
					idenMap = new HashMap<String, String>();
					receiveDate = dt.getString(i, 1);
				}
				idenMap.put(dt.getString(i, 2), "1");
			}
			schema = new MemberGroupSchema();
			schema.setmemberId(memberId);
			schema.setgroupCount(groCount);
			schema.setstatisticsEndDate(DateUtil.parseDateTime(currentDate));
			schema.setcreateDate(date);
			schema.setcreateUser("system");
			insertSet.add(schema);
			tran.add(insertSet, Transaction.INSERT);
			commitFlag = true;
		}

		// 查询统计过的会员 累计统计
		MemberGroupSet updateSet = new MemberGroupSchema()
				.query(new QueryBuilder(
						"where (statisticsEndDate is null or DATE_FORMAT(statisticsEndDate ,'%Y-%m-%d %H:%i:%s') < ?)",
						currentDate));
		if (updateSet != null && updateSet.size() > 0) {
			int size = updateSet.size();
			sb = new StringBuffer();
			sb.append("SELECT DATE_FORMAT(t.receiveDate,'%Y-%m-%d'),i.recognizeeIdentityId ");
			sb.append("FROM sdorders s,sdinformationrisktype r,tradeinformation t,sdinformationinsured i ");
			sb.append("WHERE s.memberId = ? and s.orderSn = r.orderSn AND r.appStatus='1' AND DATE_FORMAT(r.svaliDate,'%Y-%m-%d %H:%i:%s') < ? ");
			sb.append("AND t.ordID = r.orderSn AND i.orderSn=r.orderSn AND r.recognizeeSn=i.recognizeeSn ");

			String ordSql = "ORDER BY DATE_FORMAT(t.receiveDate,'%Y-%m-%d') ASC,i.recognizeeIdentityId ASC";
			String whereSql = "";
			for (int i = 0; i < size; i++) {
				schema = updateSet.get(i);
				whereSql = "";
				if (schema.getstatisticsEndDate() != null) {
					whereSql = "AND DATE_FORMAT(r.svaliDate,'%Y-%m-%d %H:%i:%s') >= '"
							+ DateUtil.toDateTimeString(schema
									.getstatisticsEndDate()) + "' ";
				}
				DataTable dt1 = new QueryBuilder(sb.toString() + whereSql
						+ ordSql, schema.getmemberId(), currentDate)
						.executeDataTable();
				if (dt1 != null && dt1.getRowCount() > 0) {
					int rowCount = dt1.getRowCount();
					Map<String, String> idenMap = new HashMap<String, String>();
					String receiveDate = dt1.getString(0, 0);
					int groCount = schema.getgroupCount();
					for (int j = 0; j < rowCount; j++) {
						if (!receiveDate.equals(dt1.getString(j, 0))) {
							if (idenMap.size() >= 5) {
								groCount += 1;
							}
							receiveDate = dt1.getString(j, 0);
							idenMap = new HashMap<String, String>();
						}
						idenMap.put(dt1.getString(j, 1), "1");
					}
					if (idenMap.size() >= 5) {
						groCount += 1;
					}
					updateSet.get(i).setgroupCount(groCount);
				}
				updateSet.get(i).setstatisticsEndDate(
						DateUtil.parseDateTime(currentDate));
				updateSet.get(i).setmodifyDate(date);
				updateSet.get(i).setmodifyUser("System");
			}
			tran.add(updateSet, Transaction.UPDATE);
			commitFlag = true;
		}

		if (commitFlag) {
			if (tran.commit()) {
				logger.info("执行统计会员团单数定时任务，提交事务成功！");
			} else {
				logger.error("执行统计会员团单数定时任务，提交事务发生错误！");
				return false;
			}
		}
		return true;
	}

	
	

	/**
	 * 定时任务调用主方法
	 * 
	 * @param id
	 */
	public void execute(long id) {

		// 积分流水表冻结可用转化调用
		if ("1".equals(id + "")) {
			if (!calculatePoint()) {
				logger.error("会员积分统计冻结转可用定时任务发生异常！");
			}

		} else if ("2".equals(id + "")) {
			// 统计会员团单数
			if (!groupCount()) {
				logger.error("统计会员团单数定时任务发生异常！");
			}
		} else if ("3".equals(id + "")) {
			if (!upGrade("3")) {
				logger.error("会员等级升级定时任务发生异常！");
			}
		} else if ("4".equals(id + "")) {
			if (!upGrade("4")) {
				logger.error("会员等级升级定时任务发生异常！");
			}
		}
	}

	public String getCode() {

		return CODE;
	}

	public String getName() {

		return "积分统计冻结转可用定时任务";
	}

	@Override
	public void execute(String paramString) {

		execute(Long.parseLong(paramString));
	}

	@Override
	public boolean isRunning(String paramString) {

		return false;
	}

	@Override
	public String getID() {

		return "com.sinosoft.datachannel.PointServiceManager";
	}
}
