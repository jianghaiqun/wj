package com.sinosoft.jdt;

import com.sinosoft.dubbo.interfaces.CancelCont;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.LogUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.jdt.tb.TBDealInterfaceNew;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.schema.SDInformationRiskTypeSchema;
import com.sinosoft.schema.SDInformationRiskTypeSet;
import com.sinosoft.webservice.FCContServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class CancelContService implements CancelCont{
	private static final Logger logger = LoggerFactory.getLogger(CancelContService.class);

	private String ErrMsg;

	/**
	 * 提供外部调用经代通接口,并返回结果 为了解决交易的调用问题，采取先Insert(appStatus=-1，特殊标记)，后Update的形式，提供appStatus状态变化 ，作为判断条件
	 * 
	 * @param insureTypeCode
	 * @param orderID
	 */
	public String callInsTransInterface(String comCode,String ordersn, String insuredSn,String riskTypeId) {
		HashMap<String, Object> toMap = new HashMap<String, Object>();
		try {
			String sql = "select Memo from zdcode where CodeType=? and ParentCode=? and CodeValue=?";
			QueryBuilder qb = new QueryBuilder(sql);
			qb.add("TBDealCancelClassName");
			qb.add("TBDealCancelClassName");
			qb.add(comCode);
			String className = qb.executeString();
			if (StringUtil.isNotEmpty(className)) {
				Class<?> tbDeal = Class.forName(className);
				TBDealInterfaceNew tbDI = (TBDealInterfaceNew) tbDeal.newInstance();
				tbDI.dealCancelData(toMap, comCode,ordersn, insuredSn);
				// 后更新，将保险公司返回结果保存至数据库
				this.updateResultMap(toMap, riskTypeId);
				String passFlag = toMap.get("passFlag").toString();
				if("pass".equals(passFlag)){
					return "SUCCESS";
				}else{
					return "ERROR|"+toMap.get("rtnMessage");
				}
			}else{
				return "NOCONFIG";
			}

		} catch (Exception e) {
			logger.error("调用经代通接口出现异常" + e.getMessage(), e);
			ErrMsg = "调用经代通接口出现异常";
			ActionUtil.sendInsureAlarmMailByInsuredSn(insuredSn, ErrMsg);
			return "ERROR";
		}
	}

	/**
	 * 修改数据方法
	 * 
	 * @param reMap
	 * @param insureTypeCode
	 * @param orderID
	 * @param ID
	 */
	private void updateResultMap(HashMap<String, Object> reMap, String ID) {
		try {
			Transaction transaction = new Transaction();
			String sql = "where ID = '" + ID + "'";// 当前时间之前未发生结算交互
			QueryBuilder qb = new QueryBuilder(sql);
			SDInformationRiskTypeSchema sdRiskTypeSchema = new SDInformationRiskTypeSchema();
			SDInformationRiskTypeSet  sdRiskTypeSet = new SDInformationRiskTypeSet();
			sdRiskTypeSet = sdRiskTypeSchema.query(qb);
			if (!sdRiskTypeSet.isEmpty() && sdRiskTypeSet.size() == 1) {
				sdRiskTypeSchema = sdRiskTypeSet.get(0);
				String passFlag = reMap.get("passFlag") + "";
				boolean isGroup = PubFun.getGroupTypeByOrdersn(sdRiskTypeSchema.getorderSn());
				if(isGroup){
					String appstatus = "";
					if("pass".equals(passFlag)){
						appstatus = "4";//4=与保险对接撤单成功
					}else{
						appstatus = "3";//3=与保险对接撤单失败
					}
					QueryBuilder qb_update = new QueryBuilder("update SDInformationRiskType set appStatus=?,insureMsg=?,balanceMsg=? where policyno = ?");
					qb_update.add(appstatus);
					qb_update.add(reMap.get("rtnMessage").toString());
					qb_update.add(reMap.get("rtnMessage").toString());
					qb_update.add(sdRiskTypeSchema.getpolicyNo());
					transaction.add(qb_update);
				}else{
					if("pass".equals(passFlag)){
						sdRiskTypeSchema.setappStatus("4");//4=与保险对接撤单成功
					}else{
						sdRiskTypeSchema.setappStatus("3");//3=与保险对接撤单失败
					}
					sdRiskTypeSchema.setinsureMsg(reMap.get("rtnMessage").toString());
//					sdRiskTypeSchema.setbalanceMsg(reMap.get("rtnMessage").toString());
					transaction.add(sdRiskTypeSchema, Transaction.UPDATE);
				}
				transaction.commit();
				
			}
		} catch (Exception e) {
			logger.error("类CancelContService方法updateResultMap()出现异常" + e.getMessage(), e);
		}
	}

	/**
	 * 
	 * @param orderID
	 *            订单id
	 * @param fee
	 *            冲销手续费
	 * @param date
	 *            发起时间
	 * @param flag
	 *            撤单or退保(02 or 03)
	 * @param cancelReturnDataID
	 */

	public boolean callProductInterface(String ordersn, String fee, String date, String flag,String policyNo,String riskTypeId,String returnIntegral,String returnActivity) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 调用结算中心
		map.put("OrderNo", ordersn);
		map.put("SXFSum", fee);
		map.put("SettleDate", date);
		map.put("OpType", flag);
		map.put("ContNo", policyNo);
		map.put("IntegralValue", returnIntegral);
		map.put("ActivityValue", returnActivity);
		Map<String, String> param = new HashMap<String, String>();
		try {
			param = FCContServiceImpl.FCContCancelService(map);
			String returnCode = (String) param.get("ResultCode");
			String returnMag = (String) param.get("ResultInfoDesc");
			SDInformationRiskTypeSchema sdRiskTypeSchema = new SDInformationRiskTypeSchema();
			sdRiskTypeSchema.setId(riskTypeId);
			if(sdRiskTypeSchema.fill()){
				
				boolean isGroup = com.sinosoft.lis.pubfun.PubFun.getGroupTypeByOrdersn(sdRiskTypeSchema.getorderSn());
				if(isGroup){
					String appstatus = "";
					String changestatus = "";
					if("0".equals(returnCode)){
						appstatus = "appstatus = '2',";
						if ("2".equals(sdRiskTypeSchema.getchangeStatus())) {
							changestatus = "1";
						} else if ("4".equals(sdRiskTypeSchema.getchangeStatus())) {
							changestatus = "3";
						}
					}
					QueryBuilder qb_update = new QueryBuilder("update SDInformationRiskType set "+appstatus+"changeStatus=?,balanceStatus=?,balanceMsg=? where policyno = ?");
					qb_update.add(changestatus);
					qb_update.add(returnCode);
					qb_update.add(returnMag);
					qb_update.add(sdRiskTypeSchema.getpolicyNo());
					qb_update.executeNoQuery();
				}else{
					if("0".equals(returnCode)){
						sdRiskTypeSchema.setappStatus("2");//撤单成功后状态更新为2
						if ("2".equals(sdRiskTypeSchema.getchangeStatus())) {
							sdRiskTypeSchema.setchangeStatus("1");
						} else if ("4".equals(sdRiskTypeSchema.getchangeStatus())) {
							sdRiskTypeSchema.setchangeStatus("3");
						}
					}
					sdRiskTypeSchema.setbalanceStatus(returnCode);
					sdRiskTypeSchema.setbalanceMsg(returnMag);
					sdRiskTypeSchema.update();
				}
			}
			if("0".equals(returnCode)){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * 更改订单表中状态
	 */
	public boolean changeSDOrders(String ordersn) {
		try {
			String countPoliciesSql = "select count(1) from sdinformationrisktype where ordersn='"+ordersn+"'";
			String canelContSql = "select count(1) from sdinformationrisktype where appStatus!='1' and ordersn='"+ordersn+"'";
			DataTable dt= new QueryBuilder(countPoliciesSql).executeDataTable();
			int countPolicies = 0;
			int cancelCont = 0;
			if(dt.getRowCount()>=1){
				countPolicies = Integer.parseInt(dt.getString(0, 0));
			}
			dt= new QueryBuilder(canelContSql).executeDataTable();
			if(dt.getRowCount()>=1){
				cancelCont = Integer.parseInt(dt.getString(0, 0));
			}
			int orderStatus = -1;
			if(cancelCont>0){
				if(countPolicies-cancelCont==0){
					orderStatus = 9;
				}else{
					orderStatus = 10;
				}
			}
			if(orderStatus!=-1){
				String sql = "update sdorders set orderStatus = ?,modifydate=now() where ordersn = ?";
				QueryBuilder qbProduct = new QueryBuilder(sql);
				qbProduct.add(orderStatus);
				qbProduct.add(ordersn);
				qbProduct.executeNoQuery();
			}
			return true;
		} catch (NumberFormatException e) {
			logger.error("========CancelContService类修改订单表状态失败======" + e.getMessage(), e);
			return false;
		}
		
	}

	public static void main(String[] args) {
		try {
			CancelContService cs = new CancelContService();
			//cs.callProductInterface("2013000000008243","",PubFun.getCurrentDate(), "02", "ABEJ170JN213Z000980Y","");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}
}
