package com.sinosoft.platform;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataCollection;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.Errorx;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.SDTbsdInsuredSchema;
import com.sinosoft.schema.SDTbsdInsuredSet;

import java.util.Date;

public class TBSDInsureds extends Page {
	
	public static Mapx initEditDialog(Mapx params) {
		String id = params.getString("ID");
		SDTbsdInsuredSchema tbsdInsured = new SDTbsdInsuredSchema();
		tbsdInsured.setID(id);
		tbsdInsured.fill();
		params = tbsdInsured.toMapx();
		return params;
	}
	
	public static Mapx init(Mapx params) {
		params.put("createDate", PubFun.getPrevMonthDay(DateUtil.getCurrentDate()));
		params.put("endCreateDate", DateUtil.getCurrentDate());
		params.put("screateDate", PubFun.getPrevMonthDay(DateUtil.getCurrentDate()));
		params.put("sendCreateDate", DateUtil.getCurrentDate());
		params.put("channelSn", HtmlUtil.codeToOptions("TB.channel", true));
		params.put("OrderStatus", HtmlUtil.codeToOptions("TB.orderstatus", true));
		return params;
	}
	
	public static void dg1DataBind(DataGridAction dga) {
		String createDate = dga.getParams().getString("createDate");
		String endCreateDate = dga.getParams().getString("endCreateDate");
		String screateDate = dga.getParams().getString("screateDate");
		String sendCreateDate = dga.getParams().getString("sendCreateDate");
		String tbTradeSeriNo = dga.getParams().getString("tbTradeSeriNo");
		String orderSn = dga.getParams().getString("orderSn");
		String OrderStatus = dga.getParams().getString("OrderStatus");
		String channelsn = dga.getParams().getString("channelSn");
		String channelinfo="";
		if(StringUtil.isNotEmpty(channelsn)){
		   channelinfo = " and a.channelsn = '"+channelsn+"'";
		}else{
			 channelinfo = " and a.channelsn in ('tb_ht','TBSD')";
		}
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT d.recognizeeName,d.recognizeeIdentityId,i.receiveDate,a.ordersn,(SELECT codename FROM zdcode WHERE codetype='appStatus' AND codevalue = e.appstatus ) as appstatus,DATE_FORMAT(e.svalidate,'%Y-%m-%d') AS svalidate, ");
		sb.append("(SELECT ChannelName FROM channelinfo WHERE channelcode=a.channelsn) channelsn,  ");
		sb.append("a.tbTradeSeriNo,b.ProductName,a.CreateDate,e.PayPrice,e.timePrem ");
		sb.append(" FROM sdorders a,sdinformation b,sdinformationinsured d,sdinformationrisktype e,tradeinformation i ");
		sb.append(" WHERE a.ordersn = b.ordersn AND a.ordersn = d.ordersn  AND a.ordersn = e.ordersn AND d.recognizeeSn = e.recognizeeSn and i.ordid = a.ordersn ");
		if (StringUtil.isNotEmpty(createDate)) {
			sb.append(" AND i.receiveDate >='" + createDate + " 00:00:00' ");
		} 
		if (StringUtil.isNotEmpty(endCreateDate)) {
			sb.append(" AND i.receiveDate <='" + endCreateDate + " 23:59:59' ");
		} 
		if (StringUtil.isNotEmpty(screateDate)) {
			sb.append(" AND e.svaliDate >='" + screateDate + " 00:00:00' ");
		} 
		if (StringUtil.isNotEmpty(sendCreateDate)) {
			sb.append(" AND e.svaliDate <='" + sendCreateDate + " 23:59:59' ");
		}
		// 渠道订单号

		if (StringUtil.isNotEmpty(tbTradeSeriNo)) {
			sb.append(" and a.tbTradeSeriNo like '%" + tbTradeSeriNo.trim() + "%'");
		}
		//渠道
		sb.append(channelinfo);
		// 订单号
		if (StringUtil.isNotEmpty(orderSn)) {
			sb.append(" and a.orderSn like '%" + orderSn.trim() + "%'");
		}
		
		if (StringUtil.isNotEmpty(OrderStatus)) {
			sb.append(" and a.orderstatus = " + OrderStatus.trim() + "");
		}
		
		// 产品名称
		String productName = dga.getParams().getString("productName");
		if (StringUtil.isNotEmpty(productName)) {
			sb.append(" and b.productName like '%" + productName.trim() + "%'");
		}

		// 投保人姓名 手机号 E-mail 证件号码 关联 informationappnt表
		String applicantName = dga.getParams().getString("applicantName");
		String idNO = dga.getParams().getString("idNO");
		if (StringUtil.isNotEmpty(applicantName)) {
			sb.append(" and d.RecognizeeName like '%" + applicantName.trim() + "%'");
		}
		if (StringUtil.isNotEmpty(idNO)) {
			sb.append(" and d.RecognizeeIdentityId like '%" + idNO.trim() + "%'");
		}
		sb.append(" GROUP BY e.policyNo order by a.modifydate desc,a.ordersn desc");

		// 测试输出结果SQL
		QueryBuilder qb = new QueryBuilder(sb.toString());
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		logger.info("淘包渠道订单数据查询sql:{}", qb.getSQL());
		dga.setTotal(qb);
		for (int i = 0; i < dt.getRowCount(); i++) {
			String str = (String)dt.get(i, 1);
			if (StringUtil.isEmpty(str)) {
			}else if (str.length() >=6) {
				String mStr = str.substring(0, str.length() - 6) + "******";
				dt.set(i, 1,mStr);
			}else if(str.length() >=3 && str.length() <6){
				String mStr = str.substring(0, str.length() - 3) + "***";
				dt.set(i, 1,mStr);
			}else{
				
			}
		}
		dga.bindData(dt);
	}
	
	/**
	 * 添加淘宝SD专用被保人
	 */
	public void add() {
		Transaction trans = new Transaction();
		QueryBuilder qb = new QueryBuilder("select max(InsertBatch) from SDTbsdInsured");
		long insertBatch = qb.executeLong() + 1;
		Request.put("InsertBatch", insertBatch);
		if (!add(trans, Request)) {
			Response.setLogInfo(0, Errorx.printString());
			return;
		}
		if (trans.commit()) {
			Response.setLogInfo(1, "添加淘宝SD专用被保人成功!");
		} else {
			Response.setLogInfo(0, "添加淘宝SD专用被保人失败!");
		}
	}
	
	/**
	 * 添加淘宝SD专用被保人，供外部调用
	 * 
	 * @param trans
	 * @param dc
	 * @return
	 */
	public static boolean add(Transaction trans, DataCollection dc) {
		SDTbsdInsuredSchema tbsdInsured = new SDTbsdInsuredSchema();
		tbsdInsured.setValue(dc);
		tbsdInsured.setID(NoUtil.getMaxNo("TBSD"));
		if (tbsdInsured.fill()) {
			Errorx.addError("姓名为" + dc.getString("Name") + "的淘宝SD专用被保人信息已经存在!");
			return false;
		}
		tbsdInsured.setCreateDate(new Date());
		tbsdInsured.setCreateUser(User.getUserName());
		trans.add(tbsdInsured, Transaction.INSERT);
		return true;
	}
	
	/**
	 * 删除淘宝SD专用被保人
	 */
	public void del() {
		Transaction trans = new Transaction();
		if (!del(trans, Request)) {
			Response.setLogInfo(0, Errorx.printString());
			return;
		}
		if (trans.commit()) {
			Response.setLogInfo(1, "删除淘宝SD专用被保人成功!");
		} else {
			Response.setLogInfo(0, "删除淘宝SD专用被保人失败!");
		}
	}
	
	/**
	 * 删除淘宝SD专用被保人，供外部使用
	 * @param trans
	 * @param dc
	 * @return
	 */
	public static boolean del(Transaction trans, DataCollection dc) {
		String TBSDInsuredIDs = dc.getString("TBSDInsuredIDs");
		SDTbsdInsuredSchema tbsdInsured = new SDTbsdInsuredSchema();
		SDTbsdInsuredSet userSet = tbsdInsured.query(new QueryBuilder(" where ID in ('" + TBSDInsuredIDs.replaceAll(",", "','") + "')"));
		trans.add(userSet, Transaction.DELETE_AND_BACKUP);
		return true;
	}
	
	/**
	 * 修改淘宝SD专用被保人
	 */
	public void save() {
		Transaction trans = new Transaction();
		if (!save(trans, Request)) {
			Response.setLogInfo(0, Errorx.printString());
			return;
		}
		if (trans.commit()) {
			Response.setLogInfo(1, "修改成功");
		} else {
			Response.setLogInfo(0, "修改失败");
		}
	}

	/**
	 * 修改淘宝SD专用被保人，供外部使用
	 * @param trans
	 * @param dc
	 * @return
	 */
	public boolean save(Transaction trans, DataCollection dc) {
		SDTbsdInsuredSchema tbsdInsured = new SDTbsdInsuredSchema();
		tbsdInsured.setID(dc.getString("ID"));
		if (!tbsdInsured.fill()) {
			Errorx.addError("姓名为" + dc.getString("Name") + "的淘宝SD专用被保人信息重复，不能保存!");
			return false;
		}
		tbsdInsured.setValue(dc);
		tbsdInsured.setModifyDate(new Date());
		tbsdInsured.setModifyUser(User.getUserName());
		trans.add(tbsdInsured, Transaction.UPDATE);
		return true;
	}
}
