package com.wangjin.cms.payment;

import cn.com.sinosoft.entity.Member;
import com.opensymphony.xwork2.ActionContext;
import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.schema.PaymentInfoSchema;
import com.sinosoft.schema.SDRemarkSchema;
import com.sinosoft.utility.ExeSQL;
import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class MemberPayment extends Page {

	public static Mapx init(Mapx params) {
		params.put("createDate",
				PubFun.getPrevMonthDay(getFormat("yyyy-MM-dd", new Date())));
		params.put("endCreateDate", getFormat("yyyy-MM-dd", new Date()));
		return params;
	}
	
	private static String getFormat(String format, Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	public void paymentInquery(DataGridAction dga) {
		// 提交时间起期
		String createDate = dga.getParams().getString("createDate");
		// 提交时间止期
		String endCreateDate = dga.getParams().getString("endCreateDate");
		// 被保险人姓名
		String insureName = dga.getParams().getString("insureName");
		// 被保险人证件号
		String idNO = dga.getParams().getString("idNO");
		// 联系人姓名
		String contactName = dga.getParams().getString("contactName");
		// 联系人电话
		String contactMobile = dga.getParams().getString("contactMobile");
		// 联系人邮箱
		String contactMail = dga.getParams().getString("contactMail");
		// 订单号
		String orderSn = dga.getParams().getString("orderSn");
		// 处理状态 0：未处理 1：处理中 2：已处理
		String state = dga.getParams().getString("state");
		
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT a.id, a.insureName, a.insureIdentityId, '' remark, a.state, ");
		sb.append("a.contactName, a.contactMobile, a.contactMail, a.ensureType, a.orderSn, ");
		sb.append("(select codename from zdcode where CodeType = 'Payment.Status' and codevalue=a.state) as stateName, ");
		sb.append("a.happenTime, a.happenArea, a.happenDesc, a.createDate, a.modifyDate ");
		sb.append("FROM paymentInfo a ");
		sb.append("WHERE 1=1 ");
		
		if(StringUtil.isNotEmpty(createDate)) {
			sb.append("and a.createDate >='" + createDate + " 00:00:00' ");
		}
		
		if(StringUtil.isNotEmpty(endCreateDate)) {
			sb.append("and a.createDate <='" + endCreateDate + " 23:59:59' ");
		}
		
		if(StringUtil.isNotEmpty(insureName)) {
			sb.append("and a.insureName like '%" + insureName + "%' ");
		}
		
		if(StringUtil.isNotEmpty(idNO)) {
			sb.append("and a.insureIdentityId like '%" + idNO + "%' ");
		}
		
		if(StringUtil.isNotEmpty(contactName)) {
			sb.append("and a.contactName like '%" + contactName + "%' ");
		}
		
		if(StringUtil.isNotEmpty(contactMobile)) {
			sb.append("and a.contactMobile like '%" + contactMobile + "%' ");
		}
		
		if(StringUtil.isNotEmpty(contactMail)) {
			sb.append("and a.contactMail like '%" + contactMail + "%' ");
		}
		
		if(StringUtil.isNotEmpty(state)) {
			sb.append("and a.state = '" + state + "' ");
		}
		
		if(StringUtil.isNotEmpty(orderSn)) {
			sb.append("and a.orderSn like '%" + orderSn + "%' ");
		}
		
		QueryBuilder qb = new QueryBuilder(sb.toString());
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(),
				dga.getPageIndex());
		dt.insertColumn("KID");
		if (dt != null) {
			for (int i = 0; i < dt.getRowCount(); i++) {
				dt.set(i,
						"KID",
						StringUtil.md5Hex(com.sinosoft.cms.pub.PubFun.getKeyValue() + dt.getString(i, "id")));
				// 保全记录查询
				String queryRemark = "SELECT remark, OperateTime, OperateName FROM sdremark WHERE prop1='"
						+ dt.getString(i, "id")
						+ "' ORDER BY OperateTime DESC";
				QueryBuilder qbr = new QueryBuilder(queryRemark);
				DataTable dtr = qbr.executeDataTable();
				String remark = "";
				if (dtr != null && dtr.getRowCount() > 0) {
					for (int j = 0; j < dtr.getRowCount(); j++) {
						int a = j + 1;
						remark += a + ", " + dtr.getString(j, "remark") + "  "
								+ dtr.getString(j, "OperateTime") + "  "
								+ dtr.getString(j, "OperateName") + " && ";
					}
					dt.set(i, "remark", remark);
				}
			}
		}
		dga.setTotal(qb);
		dga.bindData(dt);
	}
	
	public void baoquanInquery(DataGridAction dga) {
		String prop1 = $V("prop1");

		QueryBuilder qb = new QueryBuilder(
				"SELECT remark,operateType,operateName,operateTime FROM sdremark WHERE prop1='" + prop1
						+ "' ORDER BY OperateTime DESC ");
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(),
				dga.getPageIndex());
		if (dt != null && dt.getRowCount() > 0) {
			for (int i = 0; i < dt.getRowCount(); i++) {
				if (dt.getString(i, "operateType").equals("add")) {
					dt.set(i, "operateType", "添加");
				} else {
					dt.set(i, "operateType", "修改");
				}
			}
		}
		dga.setTotal(qb);
		dga.bindData(dt);
	}
	
	public static Mapx baoquanModi(Mapx<String, Object> params) {
		String prop1 = params.getString("prop1");
		if (StringUtil.isNotEmpty(prop1)) {
			String sql = "SELECT OperateTime FROM sdremark WHERE prop1='"
					+ prop1 + "' ORDER BY OperateTime DESC ";
			QueryBuilder qb = new QueryBuilder(sql);
			DataTable dt = qb.executeDataTable();
			if (dt != null && dt.getRowCount() > 0) {
				params.put("remarkdefault",dt.getString(0, "OperateTime"));
			}
		}
		return params;
	}
	
	/**
	 * 校验保全记录是否有重复
	 */
	public void checkRemark(){
		String prop1 = $V("prop1");
		String remark = $V("rem");
		String sql = "select OperateName,id,remark from sdremark where prop1='"
				+ prop1 + "'" + " AND remark IN ('"+remark+"') ORDER BY OperateTime DESC";
		QueryBuilder qb = new QueryBuilder(sql);
		DataTable dt = qb.executeDataTable();
		if(dt!=null && dt.getRowCount()>0){
			Response.setStatus(2);
		}
	}
	
	/**
	 * 保全记录添加
	 */
	public void save() {
		SDRemarkSchema comment = new SDRemarkSchema();
		PaymentInfoSchema paymentInfo = new PaymentInfoSchema();
		String prop1 = $V("prop1");
		String orderSn = $V("orderSn");
		String id = PubFun.GetNRemarkId();
		String remark = $V("rem");
		String operateType = $V("hidoperateType");
		String CurrentDateTime = DateUtil.getCurrentDateTime();
		Date modifydate=PubFun.StringToDate(CurrentDateTime,"yyyy-MM-dd HH:mm:ss");
		try {
			comment.setid(id);
			comment.setremark(remark);
			comment.setOperateName(User.getUserName());
			comment.setOperateTime(modifydate);
			comment.setOperateType(operateType);
			comment.setorderSn(orderSn);
			comment.setprop1(prop1);
			comment.setprop2("");
			comment.setupperId("");
			comment.insert();

			// 更新理赔表的修改时间
			paymentInfo.setid(prop1);
			if(paymentInfo.fill()){
				paymentInfo.setmodifyDate(modifydate);
				paymentInfo.update();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setLogInfo(0, "保存发生错误!");
		}
		String sql = "SELECT OperateTime FROM sdremark WHERE id='"
						+ id + "' ORDER BY OperateTime DESC ";
		QueryBuilder qb = new QueryBuilder(sql);
		DataTable dt = qb.executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			Response.setLogInfo(1, "保存成功!");
			Response.put("Group",dt.getString(0, "OperateTime"));
		} else{
			Response.setLogInfo(0, "保存发生错误!");
		}
	}
	
	/**
	 * 保全记录修改
	 */
	public void update() {
		String prop1 = $V("prop1");
		String orderSn = $V("orderSn");
		String remark = $V("rem");
		SDRemarkSchema comment = new SDRemarkSchema();
		PaymentInfoSchema paymentInfo = new PaymentInfoSchema();
		String CurrentDateTime = DateUtil.getCurrentDateTime();
		String operateType = $V("hidoperateType");
		Date modifydate=PubFun.StringToDate(CurrentDateTime,"yyyy-MM-dd HH:mm:ss");
		
		String sql = "select OperateName,id,remark from sdremark where prop1='"
				+ prop1 + "'" + " ORDER BY OperateTime DESC";
		QueryBuilder qb = new QueryBuilder(sql);

		DataTable dt = qb.executeDataTable();
		String operateName="";
		String pid="";
		if(dt!=null && dt.getRowCount()>0){
			operateName = dt.getString(0, "operateName");
			pid = dt.getString(0, "id");
			if(remark.equals(dt.getString(0, "remark"))){
				Response.setLogInfo(0, "您未做任何修改！");
				return;
			}
		}
		if (!operateName.equals(User.getUserName())) {
			Response.setLogInfo(0, "抱歉，您不能修改此记录！");
			return;
		}
		try {
			comment.setid(pid);
			if(comment.fill()){
				comment.setremark(remark);
				comment.setOperateName(User.getUserName());
				comment.setOperateTime(modifydate);
				comment.setOperateType(operateType);
				comment.setorderSn(orderSn);
				comment.setprop1(prop1);
				comment.setprop2("");
				comment.setupperId(pid);
				comment.update();
			}
		
			// 更新理赔表的修改时间
			paymentInfo.setid(prop1);
			if(paymentInfo.fill()){
				paymentInfo.setmodifyDate(modifydate);
				paymentInfo.update();
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setLogInfo(0, "保存发生错误!");
		}
		sql = "SELECT OperateTime FROM sdremark WHERE prop1='"
							+ prop1 + "' ORDER BY OperateTime DESC ";
		qb = new QueryBuilder(sql);
		dt = qb.executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			Response.setLogInfo(1, "保存成功!");
			Response.put("Group",dt.getString(0, "OperateTime"));
		} else {
			Response.setLogInfo(0, "保存发生错误!");
		}
	}
	
	/**
	 * 保全记录删除
	 */
	public void del() {
		PaymentInfoSchema paymentInfo = new PaymentInfoSchema();
		String prop1 = $V("prop1");
		String sql = "select OperateName,id from sdremark where prop1='"
				+ prop1 + "'" + " ORDER BY OperateTime DESC";
		QueryBuilder qb = new QueryBuilder(sql);

		DataTable dt = qb.executeDataTable();
		String operateName="";
		String id="";
		if(dt!=null && dt.getRowCount()>0){
			operateName = dt.getString(0, "operateName");
			id = dt.getString(0, "id");
		}
		if (!operateName.equals(User.getUserName())) {
			Response.setLogInfo(0, "抱歉，您不能删除此记录！");
			return;
		}
		ExeSQL tExeSQL = new ExeSQL();
		String sqlb = "insert into bsdremark select * from sdremark where id='"
				+ id + "'";
		tExeSQL.execUpdateSQL(sqlb);
		SDRemarkSchema remark = new SDRemarkSchema();
		try{
			remark.setValue(Request);
			remark.setid(id);
			remark.fill();
            if (remark.delete()) {
                Response.setLogInfo(1, "删除成功!");
            }
			// 更新理赔表修改时间
			paymentInfo.setid(prop1);
			if(paymentInfo.fill()){
				paymentInfo.setmodifyDate(new Date());
				paymentInfo.update();	
			}
			
			String sqlo = "SELECT OperateTime FROM sdremark WHERE prop1='"
						+ prop1 + "' ORDER BY OperateTime DESC ";
			QueryBuilder qbo = new QueryBuilder(sqlo);
			DataTable dto = qbo.executeDataTable();
			if (dto != null && dto.getRowCount() > 0) {
				Response.put("Group",dto.getString(0, "OperateTime"));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setLogInfo(0, "删除失败");
		}
	}
	
	public Object getSession(String name) {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		return session.get(name);
	}
	
	public void paymentInsert(DataGridAction dga) {
		String memberId = (String) getSession(Member.LOGIN_MEMBER_ID_SESSION_NAME);
		if (StringUtils.isEmpty(memberId)) {
			Response.setLogInfo(0, "未登录，请登录后在操作！");
		}
		// 提交时间
		String CurrentDateTime = DateUtil.getCurrentDateTime();
		Date createDate = PubFun.StringToDate(CurrentDateTime,"yyyy-MM-dd HH:mm:ss");
		// 被保险人姓名
		String insureName = dga.getParams().getString("insureName");
		// 被保险人证件号
		String idNO = dga.getParams().getString("idNO");
		// 联系人姓名
		String contactName = dga.getParams().getString("contactName");
		// 联系人电话
		String contactMobile = dga.getParams().getString("contactMobile");
		// 联系人邮箱
		String contactMail = dga.getParams().getString("contactMail");
		// 保障类型
		String ensureType = dga.getParams().getString("ensureType");
		// 事故发生时间
		String happenTime = dga.getParams().getString("happenTime");
		// 事故发生地点
		String happenArea = dga.getParams().getString("happenArea");
		// 事故描述
		String happenDesc = dga.getParams().getString("happenDesc");
		
		PaymentInfoSchema paymentInfo = new PaymentInfoSchema();
		paymentInfo.setid(com.sinosoft.cms.pub.PubFun.GetPaymentNO());
		paymentInfo.setcreateDate(createDate);
		paymentInfo.setmodifyDate(createDate);
		paymentInfo.setinsureName(insureName);
		paymentInfo.setinsureIdentityId(idNO);
		paymentInfo.setcontactName(contactName);
		paymentInfo.setcontactMail(contactMail);
		paymentInfo.setcontactMobile(contactMobile);
		paymentInfo.setensureType(ensureType);
		paymentInfo.sethappenTime(happenTime);
		paymentInfo.sethappenArea(happenArea);
		paymentInfo.sethappenDesc(happenDesc);
		paymentInfo.setmemberId(memberId);
		paymentInfo.setstate("0");
		paymentInfo.insert();
		
	}
	
	/**
	 * 受理处理
	 */
	public void paymentAccess() {
		String id = $V("id");
		if (StringUtil.isEmpty(id)) {
			Response.setLogInfo(0, "请选择一条数据!");
		}
		GetDBdata db = new GetDBdata();
		String updateSql="update paymentInfo set state = '1' where id = '" + id + "'";

		try {
			db.execUpdateSQL(updateSql, null);
			Response.setLogInfo(1, "受理成功!");
		} catch (Exception e) {
			Response.setLogInfo(0, "受理失败!");
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 处理完成
	 */
	public void paymentDealEnd() {
		String id = $V("id");
		if (StringUtil.isEmpty(id)) {
			Response.setLogInfo(0, "请选择一条数据!");
		}
		GetDBdata db = new GetDBdata();
		String updateSql="update paymentInfo set state = '2' where id = '" + id + "'";

		try {
			db.execUpdateSQL(updateSql, null);
			Response.setLogInfo(1, "处理完成成功!");
		} catch (Exception e) {
			Response.setLogInfo(0, "处理完成失败!");
			logger.error(e.getMessage(), e);
		}
	}
}
