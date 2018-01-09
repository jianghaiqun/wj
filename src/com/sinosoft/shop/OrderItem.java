package com.sinosoft.shop;

import java.util.Date;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Application;
import com.sinosoft.schema.ZDMemberSchema;
import com.sinosoft.schema.ZSOrderItemSchema;
import com.sinosoft.schema.ZSOrderItemSet;
import com.sinosoft.schema.ZSOrderSchema;


public class OrderItem extends Page {
	public static void dg1DataBind(DataGridAction dga) {
		QueryBuilder qb = new QueryBuilder("select * from ZSOrderItem where OrderID =? order by GoodsID", dga.getParam("OrderID"));
		dga.bindData(qb);
	}
	
	public static Mapx init(Mapx params){
		return params;
		
	}
	
	/*
	 * 校验输入的药品编号是否存在， 如果存在则返回1，及编号,价格，折扣，折扣价，积分
	 * 						如果不存在则返回0
	 * OrderItem里面的DiscountPrice对应goods里面的MemberPrice
	 * OrderItem里面的Price对应goods里面的Price
	 */
	public void checkSN(){
		String sn=$V("SN").trim();
		if(sn.length()==0){
			return;
		}
		DataTable dt=new QueryBuilder("select * from zsgoods where SN=?",sn).executeDataTable();
		if(dt==null||dt.getRowCount()==0){
			Response.setStatus(0);
			return;
		}else{
			Response.setStatus(1);
			Response.put("Name",dt.get(0, "Name").toString());
			Response.put("Price", dt.get(0, "Price").toString());
			Response.put("DiscountPrice", dt.get(0, "MemberPrice").toString());
			Response.put("Score", dt.get(0, "Score").toString());
			Response.put("GoodsID", dt.get(0, "ID").toString());
			Response.put("Factory", dt.get(0, "Factory").toString());
			Response.put("Standard", dt.get(0, "Standard").toString());
			float price=Float.valueOf(dt.get(0, "MemberPrice").toString()).floatValue();
			float memberPrice=Float.valueOf(dt.get(0, "Price").toString()).floatValue();
			int discount=((int)(price/memberPrice)*100)/100;
			Response.put("Discount",discount);
		}
	}
	
	/*
	 * 校验输入的药品名称是否存在，如果存在则返回1，及编号,价格，折扣，折扣价，积分
	 * 						如果不存在则返回0
	 */
	public void checkName(){
		String name=$V("Name").trim();
		if(name.length()==0){
			return;
		}
		DataTable dt=new QueryBuilder("select * from zsgoods where Name=?",name).executeDataTable();
		if(dt==null||dt.getRowCount()==0){
			Response.setStatus(0);
			return;
		}else{
			Response.setStatus(1);
			Response.put("SN",dt.get(0, "SN").toString());
			Response.put("Price", StringUtil.isEmpty(dt.getString(0, "Price")) ? "0" : dt.getString(0, "Price"));
			Response.put("DiscountPrice", StringUtil.isEmpty(dt.getString(0, "MemberPrice")) ? "0" : dt.getString(0, "MemberPrice"));
			Response.put("Score", StringUtil.isEmpty(dt.getString(0, "Score")) ? "0" : dt.getString(0, "Score"));
			Response.put("GoodsID", dt.getString(0, "ID"));
			Response.put("Factory", StringUtil.isEmpty(dt.getString(0, "Factory")) ? "" : dt.getString(0, "Factory"));
			Response.put("Standard", StringUtil.isEmpty(dt.getString(0, "Standard")) ? "" : dt.getString(0, "Standard"));
			float price=Float.valueOf(StringUtil.isEmpty(dt.getString(0, "MemberPrice")) ? "0" : dt.getString(0, "MemberPrice")).floatValue();
			float memberPrice=Float.valueOf(StringUtil.isEmpty(dt.getString(0, "Price")) ? "0" : dt.getString(0, "Price")).floatValue();
			int discount=((int)(price/memberPrice)*100)/100;
			Response.put("Discount", discount);
		}
	}
	
	/*
	 * 为一个具体订单添加药品项
	 */
	public void add() {
		Transaction trans=new Transaction();
		//添加药品项
		ZSOrderItemSchema ZSOrderItem = new ZSOrderItemSchema();
		ZSOrderItem.setValue(Request);
		ZSOrderItem.setSiteID(Application.getCurrentSiteID());
		ZSOrderItem.setAddUser(User.getUserName());
		ZSOrderItem.setAddTime(new Date());
		trans.add(ZSOrderItem, Transaction.INSERT);
		//修改订单金额
		String orderID=	$V("OrderID");
		if(StringUtil.isEmpty(orderID)){
			Response.setLogInfo(1, "订单号不能为空！");
			return;
		}
		ZSOrderSchema order=new ZSOrderSchema();
		order.setID(orderID);
		if(order.fill()){
			String amount = Request.getString("Amount");
			order.setAmount(order.getAmount() + Float.parseFloat(amount));
			order.setOrderAmount(order.getOrderAmount() + Float.parseFloat(amount));
			trans.add(order, Transaction.UPDATE);
		}else{
			Response.setLogInfo(1, "订单号不存在！");
			return;
		}
		//修改用户积分
		String memberID=Request.getString("MemberID");
		String score=Request.getString("Score");
		//接受到的memberID不为空,且不为0(非会员结算的记录,MemberID记录为0)
		if(StringUtil.isNotEmpty(memberID)&&!memberID.equalsIgnoreCase("0")){
			ZDMemberSchema member=new ZDMemberSchema();
			member.setUserName(memberID);
			if(member.fill()){
				member.setScore(member.getScore()+Integer.parseInt(score));
				trans.add(member, Transaction.UPDATE);
			}
		}
		if (trans.commit()) {
			Response.setLogInfo(1, "新增订单详细项成功！");
		} else {
			Response.setLogInfo(0, "发生错误!");
		}
	}
	
	/*
	 * 删除一个具体订单中的药品项
	 */
	public void del() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setLogInfo(0,"传入ID时发生错误!");
			return;
		}
		Transaction trans = new Transaction();
		//在修改积分之后再删除订单药品项
		//修改用户积分
		String memberID=$V("MemberID");
		String sqlScore="select sum(score) from zsorderitem where GoodsID in ('" + ids + "')";
		Object obj=new QueryBuilder(sqlScore).executeOneValue();
		String score=obj==null?"0":obj.toString();
		//接受到的memberID不为空,且不为0(非会员结算的记录,MemberID记录为0)
		if(StringUtil.isNotEmpty(memberID)&&!memberID.equalsIgnoreCase("0")){
			ZDMemberSchema member=new ZDMemberSchema();
			member.setUserName(memberID);
			if(member.fill()){
				member.setScore(String.valueOf(Integer.parseInt(member.getScore()) - Integer.parseInt(score)));
				trans.add(member, Transaction.UPDATE);
			}
		}
		
		//删除药品项
		ZSOrderItemSchema ZSOrderItem = new ZSOrderItemSchema();
		ZSOrderItemSet set = ZSOrderItem.query(new QueryBuilder("where GoodsID in ('" + ids + "')"));
		trans.add(set, Transaction.DELETE);
		//修改订单金额
		String orderID=$V("OrderID");
		String sqlAmount="select sum(Amount) from zsorderitem where GoodsID in ('" + ids + "')";
		String amount=new QueryBuilder(sqlAmount).executeString();
		ZSOrderSchema order=new ZSOrderSchema();
		order.setID(orderID);
		order.fill();
		order.setAmount(order.getAmount()-Float.parseFloat(amount));
		trans.add(order, Transaction.UPDATE);
		if (trans.commit()) {
			Response.setLogInfo(1, "删除成功");
		} else {
			Response.setLogInfo(0, "删除失败");
		}
	}

	public void dg1Edit() {
		DataTable dt = Request.getDataTable("DT");
		Transaction trans = new Transaction();
		for (int i = 0; i < dt.getRowCount(); i++) {
			ZSOrderItemSchema item = new ZSOrderItemSchema();
			item.setValue(dt.getDataRow(i));
			item.fill();
			item.setValue(dt.getDataRow(i));
			
			item.setModifyTime(new Date());
			item.setModifyUser(User.getUserName());

			trans.add(item, Transaction.UPDATE);
		}
		if (trans.commit()) {
			Response.setStatus(1);
			Response.setMessage("修改成功!");
		} else {
			Response.setStatus(0);
			Response.setMessage("修改失败!");
		}
	}
}
