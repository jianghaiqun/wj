package com.sinosoft.inter;

import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.LogUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.jdt.CancelContService;
import com.sinosoft.schema.SDInformationRiskTypeSchema;
import com.sinosoft.schema.SDInformationRiskTypeSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TBSDAction {
	private static final Logger logger = LoggerFactory.getLogger(TBSDAction.class);

	/**
	 * 判断指定订单号的订单的被保人是否在淘宝SD信息表中
	 * @param orderSn
	 * @return
	 */
	public boolean isTbsdInsured(String orderSn) {
		String sql = "select * from sdInformationInsured where orderSn = ?";
		QueryBuilder qb = new QueryBuilder(sql, orderSn);
		DataTable dt = qb.executeDataTable();
		if (dt.getRowCount() == 0) {
			logger.warn("找不到被保人！订单号：{}", orderSn);
			return false;
		} else {
			for (int i = 0; i < dt.getRowCount(); i++) {
				String identityTypeName = dt.getString(i, "recognizeeIdentityTypeName");
				if (identityTypeName.indexOf("身份证") != -1) {
					return false;
				}
				String name = dt.getString(i, "recognizeeName");
				if (StringUtil.isEmpty(name)) {
					logger.warn("被保人姓名为空！订单号：{}", orderSn);
					return false;
				}
				String passportId = dt.getString(i, "recognizeeIdentityId");
				if (StringUtil.isEmpty(passportId)) {
					logger.warn("被保人证件号码为空！订单号：{}", orderSn);
					return false;
				}
				String headStr=passportId.substring(0, 2);
				if((!"my".equalsIgnoreCase(headStr))&&(!passportId.startsWith("CN") && !passportId.startsWith("cn") && !passportId.startsWith("Cn") && !passportId.startsWith("cN"))){
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 将指定订单号的订单渠道改为淘宝SD
	 * @param orderSn
	 * @return
	 */
	public int turnChannelToTbsd(String orderSn) {
		logger.info("=========开始修改淘宝SD订单渠道==========");
		String sql = "update sdOrders set channelsn = 'tbsd' where orderSn = ?";
		QueryBuilder qb = new QueryBuilder(sql);
		qb.add(orderSn);
		return qb.executeNoQuery();
	}

	/**
	 * 获取指定订单号的起保日期
	 * @param orderSn
	 * @return
	 */
	public Date getSvaliDate(String orderSn){
		String sql = "select svaliDate from sdInformationRiskType where orderSn = ?";
		QueryBuilder qb = new QueryBuilder(sql, orderSn);
		DataTable dt = qb.executeDataTable();
		if (dt.getRowCount() == 0) {
			logger.warn("找不到保单！订单号：{}", orderSn);
			return null;
		} else {
			return dt.getDate(0, 0);
		}
	}
	
	/**
	 * 当承保时间与起保时间相距过近时，发送淘宝SD报警邮件
	 * @param ordsn
	 * @param errMsg
	 */
	public void sendWarnMail(String ordsn, String errMsg, Map<String,Object> tMap) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			String sql = "select paySn,tbTradeSeriNo from sdOrders where orderSn = ?";
			map.put("OrderSn", ordsn);
			DataTable dt = new QueryBuilder(sql, ordsn).executeDataTable();
			if(dt.getRowCount() != 0){
				map.put("PaySn", dt.getString(0, 0));
				map.put("TBTradeSeriNo", dt.getString(0, 1));
			}
			if (tMap != null && tMap.get("DealDate") != null){
				map.put("DealDate", tMap.get("DealDate"));
			}
			if (tMap != null && tMap.get("SvaliDate") != null){
				map.put("SvaliDate", tMap.get("SvaliDate"));
			}
			map.put("ErrMsg", errMsg);
			String toEmail = Config.getValue("TBSDWarnMail");
			if(StringUtil.isNotEmpty(toEmail)){
				ActionUtil.sendMail("wj00114", toEmail, map);
			}
		} catch (Exception e) {
			logger.error("出现异常:sendWarnMail方法出现异常" + e.getMessage(), e);
		}
	}
	
	/**
	 * 获取所有需要自动撤单的淘宝SD订单信息
	 * @return
	 */
	public List<Map<String, String>> getToCanceledOrders() {
		List<Map<String, String>> params = new ArrayList<Map<String,String>>();
		String sql1 = "SELECT a.orderSn,b.insuranceCompany,c.insuredSn,d.id,d.appStatus,d.balanceStatus "
				+ "FROM sdOrders a,sdInformation b,sdInformationInsured c,sdInformationRiskType d,tradeInformation e "
				+ "WHERE a.orderSn = b.orderSn AND a.orderSn = c.orderSn AND a.orderSn = d.orderSn AND a.orderSn = e.ordId "
				+ "AND a.orderStatus = '7' AND a.channelsn = 'tbsd' AND e.receiveDate < DATE_SUB(NOW(), INTERVAL 1 DAY) "
				+ "AND d.svaliDate > NOW()";
		DataTable dt = new QueryBuilder(sql1).executeDataTable();
		if (dt.getRowCount() != 0) {
			for (int i = 0; i < dt.getRowCount(); i++) {
				Map<String,String> map = new HashMap<String, String>();
				map.put("ordersn", dt.getString(i, 0));
				map.put("comCode", dt.getString(i, 1));
				map.put("insuredSn", dt.getString(i, 2));
				map.put("riskTypeId", dt.getString(i, 3));
				map.put("appStatus", dt.getString(i, 4));
				map.put("balanceStatus", dt.getString(i, 5));
				params.add(map);
			}
		}
		return params;
	}
	
	/**
	 * 批量执行淘宝SD撤单
	 * @param params
	 */
	public void dealCancel(List<Map<String, String>> params) {
		for (Map<String, String> param : params) {
			String insuredSn = param.get("insuredSn");
			String comCode = param.get("comCode");
			String appStatus = param.get("appStatus");
			String ordersn = param.get("ordersn");
			String riskTypeId = param.get("riskTypeId");
			String balanceStatus = param.get("balanceStatus");
			String flag = "";
			String errMsg;

			if (!"0".equals(balanceStatus) && "1".equals(appStatus)) {
				errMsg = "淘宝SD自动撤单定时任务：订单未结算，不能撤单！";
				logger.warn("{}订单号：{}", errMsg, ordersn);
				sendErrorMail(ordersn, errMsg, insuredSn);
				continue;
			}
			if (("1".equals(appStatus) || "3".equals(appStatus))
					&& StringUtil.isNotEmpty(insuredSn)
					&& StringUtil.isNotEmpty(comCode)) {
				CancelContService tCancelContService = new CancelContService();
				flag = tCancelContService.callInsTransInterface(comCode,
						ordersn, insuredSn, riskTypeId);
				// 保险公司撤单成功后 更新系统数据状态
				if ("SUCCESS".equals(flag)) {
					// 更新保单表的撤单信息
					SDInformationRiskTypeSchema sdRiskTypeSchema = new SDInformationRiskTypeSchema();
					sdRiskTypeSchema.setId(riskTypeId);
					if (sdRiskTypeSchema.fill()) {
						sdRiskTypeSchema.setappStatus("4");// 撤单成功后状态更新为2
						sdRiskTypeSchema.setcancelDate(new Date());
						sdRiskTypeSchema.update();
					}
					// 更新订单表的订单状态
					tCancelContService.changeSDOrders(ordersn);
				}
			} else if ("2".equals(appStatus)) {
				errMsg = "淘宝SD自动撤单定时任务：此订单已经撤单！";
				logger.warn("{}订单号：{}", errMsg, ordersn);
				sendErrorMail(ordersn, errMsg, insuredSn);
				continue;
				// 网金结算中心撤单失败时，可以重新撤单
			} else if ("4".equals(appStatus)) {
				flag = "SUCCESS";
			} else {
				errMsg = "淘宝SD自动撤单定时任务：订单有误！";
				logger.warn("{}订单号：{}", errMsg, ordersn);
				sendErrorMail(ordersn, errMsg, insuredSn);
				continue;
			}
			if ("SUCCESS".equals(flag)) {
				if (StringUtil.isNotEmpty(riskTypeId)) {
					CancelContService tCancelContService = new CancelContService();
					SDInformationRiskTypeSchema sdRiskTypeSchema = new SDInformationRiskTypeSchema();
					SDInformationRiskTypeSet sdRiskTypeSet = new SDInformationRiskTypeSet();
					sdRiskTypeSet = sdRiskTypeSchema.query(new QueryBuilder("where ID = '" + riskTypeId + "'"));
					if (!sdRiskTypeSet.isEmpty() && sdRiskTypeSet.size() == 1) {
						sdRiskTypeSchema = sdRiskTypeSet.get(0);
						if ("4".equals(sdRiskTypeSchema.getappStatus())) {// 表示与保险公司对接退保成功
							String returnActivity = "";
							if (sdRiskTypeSchema.getorderSn().indexOf("TB") == 0) {
								returnActivity = sdRiskTypeSchema.getactivityValue();
							}
							if (tCancelContService.callProductInterface(
									ordersn, "", PubFun.getCurrentDate(), "02",
									sdRiskTypeSchema.getpolicyNo(), riskTypeId, "", returnActivity)) {
								continue;
							} else {
								errMsg = "淘宝SD自动撤单定时任务：撤单结算中心调用失败！";
								logger.warn("{}订单号：{}", errMsg, ordersn);
								sendErrorMail(ordersn, errMsg, insuredSn);
								continue;
							}
						}
					}
				} else {
					errMsg = "淘宝SD自动撤单定时任务：订单有误！";
					logger.warn("{}订单号：{}", errMsg, ordersn);
					sendErrorMail(ordersn, errMsg, insuredSn);
					continue;
				}
			} else if ("NOCONFIG".equals(flag)) {
				errMsg = "淘宝SD自动撤单定时任务：此公司的订单未开放撤单功能！请联系技术人员。";
				logger.warn("{}订单号：{}", errMsg, ordersn);
				sendErrorMail(ordersn, errMsg, insuredSn);
				continue;
			} else {
				errMsg = "淘宝SD自动撤单定时任务：撤单经代通调用失败！";
				logger.warn("{}订单号：{}", errMsg, ordersn);
				sendErrorMail(ordersn, errMsg, insuredSn);
				continue;
			}
		}
	}
	
	/**
	 * 发送撤单失败报警邮件
	 * @param ordsn
	 * @param ErrMsg
	 * @param insuredSn
	 */
	private void sendErrorMail(String ordsn, String ErrMsg, String insuredSn) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			String sql = "select a.productName , c.timePrem , a.startDate " +
					"from sdinformation a, sdinformationinsured b , sdinformationrisktype c " +
					"where a.informationsn = b. informationsn and b.recognizeeSn = c.recognizeeSn and insuredSn = ?";
			map.put("OrderSn", ordsn);
			map.put("InsuredSn", insuredSn);
			map.put("ErrMsg", ErrMsg);
			DataTable dt = new QueryBuilder(sql, insuredSn).executeDataTable();
			if (dt.getRowCount() > 0) {
				map.put("ProductName", dt.getString(0, 0));
				map.put("TotalAmount", dt.getString(0, 1));
				map.put("Effective", dt.getString(0, 2).substring(0, 11));
			} else {
				logger.warn("未查询到订单相关信息");
				map.put("ProductName", "无订单信息");
				map.put("TotalAmount", "无订单信息");
				map.put("Effective", "无订单信息");
			}
			String toEmail = Config.getValue("InsureErrorMail");
			if(StringUtil.isNotEmpty(toEmail)){
				ActionUtil.sendMail("wj00115", toEmail, map);
			}
		} catch (Exception e) {
			logger.error("出现异常:" + "sendErrorMail方法出现异常" + e.getMessage(), e);
		}
	}
	
	public static void main(String[] args) {
		TBSDAction action = new TBSDAction();
		action.sendErrorMail("2013000000013664", "测试邮件" , "2013000000013664_1");
	}
}
