/**
 * 
 */
package com.sinosoft.cms.dataservice;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.sinosoft.action.shop.BillDetailAction;
import cn.com.sinosoft.common.email.MessageConstant;
import cn.com.sinosoft.entity.LogisticsInfo;
import cn.com.sinosoft.entity.LogisticsInfo.Item;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.schema.SDRemarkSchema;
import com.sinosoft.schema.SDRemarkSet;
import com.sinosoft.schema.memberSchema;
import com.sinosoft.sms.messageinterface.parse.MessageCode;

/**
 * @author Administrator
 *
 */
public class BillInfoManage extends Page {

	
	
	@SuppressWarnings("rawtypes")
	public static Mapx initDialog(Mapx params) {
		String id = params.getString("id");
		if (StringUtil.isNotEmpty(id)) {
			StringBuilder sb = new StringBuilder();
			sb.append("select DeliverName, DeliverTel, BillTitle, BillAmount, DeliverZipCode, ");
			sb.append("(select CodeName from zdcode where CodeType='billType' and BillType=CodeValue) as BillTypeName, ");
			sb.append("LogisticsId, WayBillNo, CONCAT(if(DeliverProvince is null, '', DeliverProvince), ");
			sb.append("if(DeliverCity is null, '', DeliverCity), if(DeliverSection is null, '', DeliverSection), ");
			sb.append("if(DeliverDetailAddr is null, '', DeliverDetailAddr)) as DeliverAddr from sdBillInfo where Id = ? ");
			DataTable dt = new QueryBuilder(sb.toString(), id).executeDataTable();
			if (dt != null && dt.getRowCount() > 0) {
				params.put("DeliverName", dt.getString(0, "DeliverName"));
				params.put("DeliverTel", dt.getString(0, "DeliverTel"));
				params.put("BillTitle", dt.getString(0, "BillTitle"));
				params.put("BillAmount", dt.getString(0, "BillAmount"));
				params.put("DeliverZipCode", dt.getString(0, "DeliverZipCode"));
				params.put("BillTypeName", dt.getString(0, "BillTypeName"));
				params.put("WayBillNo", dt.getString(0, "WayBillNo"));
				params.put("DeliverAddr", dt.getString(0, "DeliverAddr"));
				params.put("Logistics", HtmlUtil.codeToOptions("LogisticsCom", dt.getString(0, "LogisticsId"), true));
			}
		}
		return params;
	}
	
	public static Mapx init(Mapx params) {
		params.put("Status", HtmlUtil.codeToOptions("billStatus", true));
		return params;
	}
	
	public static void dg1DataBind(DataGridAction dga) {
		StringBuilder sb = new StringBuilder();
		sb.append("select b.Id as id, b.MemberId, if(m.vipFlag = 'Y', 'VIP会员', g.gradeName) as gradeName, ");
		sb.append("b.DeliverTel, b.DeliverName, b.BillTitle, b.BillAmount, b.DeliverZipCode, b.BillReqUrl, ");
		sb.append("(select CodeName from zdcode where CodeType='billStatus' and b.Status=CodeValue) as statusName, ");
		sb.append("(select CodeName from zdcode where CodeType='billType' and b.BillType=CodeValue) as BillTypeName, ");
		sb.append("(select CodeName from zdcode where CodeType='LogisticsCom' and b.LogisticsId=CodeValue) as Logistics, ");
		sb.append("CONCAT(if(b.DeliverProvince is null, '', b.DeliverProvince), if(b.DeliverCity is null, '', b.DeliverCity), ");
		sb.append("if(b.DeliverSection is null, '', b.DeliverSection), if(b.DeliverDetailAddr is null, '', b.DeliverDetailAddr)) as DeliverAddr, ");
		sb.append("b.WayBillNo, b.CreateDate, b.UpdateUser, b.UpdateDate, b.Status, b.LogisticsId, '下载' as BillReqUrlName ");
		sb.append("from sdBillInfo b, member m left join MemberGrade g on g.gradeCode = m.grade ");
		sb.append("where m.id= b.MemberId  ");
		
		QueryBuilder qb = new QueryBuilder(sb.toString());
		
		// 会员ID
		String memberId = dga.getParams().getString("memberId");
		if (StringUtil.isNotEmpty(memberId)) {
			qb.append(" AND b.MemberId=?", memberId.trim());
		}
		// 会员邮箱
		String email = dga.getParams().getString("email");
		if (StringUtil.isNotEmpty(email)) {
			qb.append(" and m.email like " + "'%" + email + "%'");
		}
		// 会员电话
		String mobileNO = dga.getParams().getString("mobileNO");
		if (StringUtil.isNotEmpty(mobileNO)) {
			qb.append(" and m.mobileNO like " + "'%" + mobileNO + "%'");
		}
		// 收件人姓名
		String deliverName = dga.getParams().getString("deliverName");
		if (StringUtil.isNotEmpty(deliverName)) {
			qb.append(" AND b.DeliverName=?", deliverName.trim());
		}
		
		// 收件人电话
		String deliverTel = dga.getParams().getString("deliverTel");
		if (StringUtil.isNotEmpty(deliverTel)) {
			qb.append(" AND b.DeliverTel=?", deliverTel.trim());
		}
		
		// 处理人
		String updateUser = dga.getParams().getString("updateUser");
		if (StringUtil.isNotEmpty(updateUser)) {
			qb.append(" AND b.UpdateUser=?", updateUser.trim());
		}
		
		// 发票状态
		String status = dga.getParams().getString("status");
		if (StringUtil.isNotEmpty(status)) {
			qb.append(" AND b.Status=?", status.trim());
		}
		
		qb.append(" order by  b.Status asc,b.CreateDate desc , gradeName desc ");
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		if (dt != null && dt.getRowCount() > 0) {
			int count = dt.getRowCount();
			for (int i = 0; i < count; i++) {
				if (StringUtil.isEmpty(dt.getString(i, "BillReqUrl"))) {
					dt.set(i, "BillReqUrl", "javascript:void(0);");
					dt.set(i, "BillReqUrlName", "");
				}
			}
		}
		
		dga.bindData(dt);
	}
	
	public void billRelateOrder(DataGridAction dga) {
		String id = dga.getParams().getString("id");
		QueryBuilder qb = new QueryBuilder("select a.OrderSn,i.productName,(select codeName from zdcode where CodeType='orderstatus' AND codevalue=o.orderStatus) orderStatusName,r.policyNo,o.totalAmount,o.payPrice from sdBillOrderRef a,sdorders o, sdinformation i, sdinformationrisktype r where billId = ? and a.Ordersn = o.orderSn and a.Ordersn = i.orderSn and i.orderSn = r.orderSn", id);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.setTotal(qb);
		dga.bindData(dt);
	}
	
	public void dg1Edit() {
		String id = $V("id");
		String Logistics = $V("Logistics");
		String WayBillNo = $V("WayBillNo");
		String LogisticsName = new QueryBuilder("select CodeName from zdcode where CodeType = 'LogisticsCom' and CodeValue = ?", Logistics).executeString();
		Transaction trans = new Transaction();
		String sql = "update sdBillInfo set LogisticsId = ?, WayBillNo = ?, Status='03', UpdateUser = ?, UpdateDate = ? where Id = ? ";
		QueryBuilder qb = new QueryBuilder(sql);
		qb.add(Logistics);
		qb.add(WayBillNo);
		qb.add(User.getUserName());
		qb.add(DateUtil.getCurrentDateTime());
		qb.add(id);
		trans.add(qb);
		if (trans.commit()) {
			Response.setLogInfo(1, "修改成功！");
			// 取得会员信息
			String memberid = new QueryBuilder(
					"select MemberId from sdBillInfo where Id = ?", id)
					.executeString();
			if (StringUtil.isNotEmpty(memberid)) {

				memberSchema memberschema = new memberSchema();
				memberschema.setid(memberid);
				if (memberschema.fill()) {
					// 发邮件/短信提示用户发票已邮寄
					Map<String, Object> map = new HashMap<String, Object>();
					map.put(MessageCode.LOGISTICS_COMPANY, LogisticsName);
					map.put(MessageCode.WAY_BILL_NO, WayBillNo);
					map.put(MessageConstant.PARAM_SHOW_ACTIVITY_PRODUCT, "1");
					map.put("memberId", memberid);
					if (!"1".equals(memberschema.getregisterType())
							&& StringUtil.isNotEmpty(memberschema.getemail())) {
						if (ActionUtil.sendMail("wj00126", memberschema.getemail(), map)) {
							Response.setLogInfo(1, "修改成功,已发送邮件提醒用户！");
						} else {
							Response.setLogInfo(1, "修改成功,发送提醒用户邮件失败！");
						}
					} else if (StringUtil.isNotEmpty(memberschema.getmobileNO())) {
						String memName = "会员";
						if (StringUtil.isNotEmpty(memberschema.getrealName())) {
							memName = memberschema.getrealName();
						}
						if (ActionUtil.sendSms("wj00123", memberschema.getmobileNO()
								, memName + ";" + LogisticsName + ";" + WayBillNo)) {
							Response.setLogInfo(1, "修改成功,已发送短信提醒用户！");
						} else {
							Response.setLogInfo(1, "修改成功,发送提醒用户短信失败！");
						}

					}
				}
			}
			
			String remark = "【快递寄出】"+LogisticsName+","+WayBillNo+",自助申请";
			if (addRemak(id, remark, User.getUserName())) {
				Response.setMessage(Response.getMessage() + " 保全记录添加成功！");
			} else {
				Response.setMessage(Response.getMessage() + " 保全记录添加失败！");
			}
			
		} else {
			Response.setLogInfo(0, "修改失败!");
		}
	}
	
	public boolean addRemak(String id, String remark, String operateName) {
		DataTable dt = new QueryBuilder("select OrderSn from sdBillOrderRef where billId = ?", id).executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			int count = dt.getRowCount();
			SDRemarkSet set = new SDRemarkSet();
			SDRemarkSchema schema = new SDRemarkSchema();
			Date modifydate = new Date();
			for (int i = 0; i < count; i++) {
				schema = new SDRemarkSchema();
				schema.setid(PubFun.GetNRemarkId());
				schema.setremark(remark);
				schema.setorderSn(dt.getString(i, 0));
				schema.setOperateName(operateName);
				schema.setOperateTime(modifydate);
				schema.setOperateType("add");
				schema.setprop1("");
				schema.setprop2("");
				schema.setupperId("");
				set.add(schema);
			}
			
			return set.insert();
		}
		return true;
	}
	
	public void dealing() {
		String ids = $V("IDs");
		if (StringUtil.isEmpty(ids)) {
			Response.setStatus(0);
			Response.setMessage("传入ID不能为空!");
			return;
		}
		ids = "'" + ids.replace(",", "','") + "'";
		Transaction trans = new Transaction();
		QueryBuilder qb = new QueryBuilder("update sdBillInfo set Status='02', UpdateUser = ?, UpdateDate = ? where Id in (" + ids + ")", User.getUserName(), DateUtil.getCurrentDateTime());
		trans.add(qb);
		if (trans.commit()) {
			Response.setLogInfo(1, "发票状态修改成待邮寄，修改成功！");
			
		} else {
			Response.setLogInfo(0, "发票状态修改成待邮寄，修改失败!");
		}
	}
	
	public static Mapx lookLogistics(Mapx params) {
		String WayBillNo = params.getString("WayBillNo");
		String LogisticsId = params.getString("LogisticsId");
		String table = "<table width=\"100%\" cellpadding=\"2\" cellspacing=\"0\" class=\"dataTable\" style=\"text-align: center; table-layout: fixed;\">";
		BillDetailAction BillDetailAction = new BillDetailAction();
		String info = BillDetailAction.getLogisticsInfo(LogisticsId, WayBillNo);
		if (StringUtil.isEmpty(info)) {
			table += "<tr><td>暂无物流信息</td></tr></table>";
			
		} else {
			LogisticsInfo logisticsInfo = BillDetailAction.parseLogisticsInfo(info);
			if (logisticsInfo != null && logisticsInfo.getData() != null && logisticsInfo.getData().size() > 0) {
				int size = logisticsInfo.getData().size();
				List<Item> list = logisticsInfo.getData();
				table += "<tr class=\"dataTableHead\"><td width=\"120\"><b>时间</b></td><td width=\"270\"><b>物流信息</b></td></tr>";
				for (int i = 0; i < size; i++) {
					table += "<tr><td>"+list.get(i).getTime()+"</td><td>"+list.get(i).getContext()+"</td></tr>";
				}
				table += "</table>";
			} else {
				table += "<tr><td>暂无物流信息</td></tr></table>";
			}
		}
		params.put("table", table);
		
		return params;
	}
}
