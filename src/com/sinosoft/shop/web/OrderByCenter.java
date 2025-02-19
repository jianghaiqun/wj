package com.sinosoft.shop.web;

import java.text.DecimalFormat;
import java.util.Date;

import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Ajax;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.User;
import com.sinosoft.framework.cache.CacheManager;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.controls.DataListAction;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Application;
import com.sinosoft.platform.Code;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.ZDMemberAddrSchema;
import com.sinosoft.schema.ZSGoodsSchema;
import com.sinosoft.schema.ZSOrderItemSchema;
import com.sinosoft.schema.ZSOrderSchema;


public class OrderByCenter extends Ajax {

	public static void dg1DataBind(DataListAction dla) {
		String shopCart = dla.getParam("Cookie.ShopCart");
		Mapx map = new Mapx();
		if(StringUtil.isNotEmpty(shopCart)) {
			String[] cartGoods = shopCart.split("X");
			for(int i = 0; i < cartGoods.length; i++) {
				map.put(cartGoods[i].split("-")[0], cartGoods[i].split("-")[1]);
			}
		}
		if (map.size() < 1) {
			return;
		}
		Object[] ids = map.keyArray();

		ZSGoodsSchema goods = new ZSGoodsSchema();
		DecimalFormat fmt = new DecimalFormat("0.00");
		DataTable dt = new DataTable();
		for (int i = 0; i < ids.length; i++) {
			if (ids[i] != null) {
				goods.setID(ids[i].toString());
				goods.fill();
				int count = map.getInt(ids[i]);
				float Price = goods.getPrice();
				float sumPrice = goods.getPrice() * count;
				DataRow dr = goods.toDataRow();
				dr.insertColumn("Count", count + "");
				dr.insertColumn("Price", fmt.format(Price));
				dr.insertColumn("sumPrice", fmt.format(sumPrice));
				dr.insertColumn("RowNo", (i + 1) + "");
				dt.insertRow(dr);
			}
		}
		dla.bindData(dt);
	}

	public static void dg2DataBind(DataListAction dla) {

		String shopCart = dla.getParam("Cookie.ShopCart");
		Mapx map = new Mapx();
		if(StringUtil.isNotEmpty(shopCart)) {
			String[] cartGoods = shopCart.split("X");
			for(int i = 0; i < cartGoods.length; i++) {
				map.put(cartGoods[i].split("-")[0], cartGoods[i].split("-")[1]);
			}
		}
		Object[] ids = map.keyArray();
		ZSGoodsSchema goods = new ZSGoodsSchema();
		DecimalFormat fmt = new DecimalFormat("0.00");
		DataTable dt = new DataTable();
		float sumPrices = 0.0f;

		for (int i = 0; i < ids.length; i++) {

			goods.setID(ids[i].toString());
			goods.fill();
			int count = map.getInt(ids[i]);
			float sumPrice = goods.getPrice() * count;
			sumPrices += sumPrice;
		}
		DataRow dr = goods.toDataRow();
		dr.insertColumn("SalePrice", fmt.format(sumPrices));
		DataTable sendTypeDT = new QueryBuilder("select name,price from zssend order by id").executeDataTable();
		dr.insertColumn("SendPrice", sendTypeDT.get(0, "price"));
		dr.insertColumn("PayPrice", fmt.format((Float.parseFloat(sendTypeDT.getString(0, "price")) + sumPrices)));
		dt.insertRow(dr);
		dla.bindData(dt);
	}

	public static void dg1DataList(DataListAction dla) {
		QueryBuilder qb = new QueryBuilder("select * from zdmemberaddr where UserName = ?  Order by AddTime Desc", User.getUserName());
		dla.setTotal(qb);
		DataTable dt = qb.executeDataTable();
		dt.insertColumn("ProvinceName");
		dt.insertColumn("CityName");
		dt.insertColumn("DistrictName");
		dt.insertColumn("IsDefaultName");
		Mapx DistrictMap = PubFun.getDistrictMap();
		for (int i = 0; i < dt.getRowCount(); i++) {
			if (StringUtil.isNotEmpty(dt.getString(i, "Province"))) {
				dt.set(i, "ProvinceName", DistrictMap.getString(dt.getString(i, "Province")));
			}
			if (StringUtil.isNotEmpty(dt.getString(i, "City"))) {
				dt.set(i, "CityName", DistrictMap.getString(dt.getString(i, "City")));
			}
			if (StringUtil.isNotEmpty(dt.getString(i, "District"))) {
				dt.set(i, "DistrictName", DistrictMap.getString(dt.getString(i, "District")));
			}
			if (dt.getString(i, "IsDefault").equals("Y")) {
				dt.set(i, "IsDefaultName", " checked");
			}
		}
		dla.bindData(dt);
	}

	/**
	 * 订单成功提交页面
	 * 
	 * @param dga
	 */
	public static void dg1PrintDataBind(DataGridAction dga) {
		String sql1 = "select * from ZSOrderItem where OrderID = ? order by GoodsID";
		DataTable dt = new QueryBuilder(sql1, dga.getParam("OrderID")).executeDataTable();
		Mapx factoryMap = new QueryBuilder("select id,Factory from zsgoods where exists(select * from zsorderitem where orderID = ? and GoodsID = zsgoods.ID)", dga.getParam("OrderID")).executeDataTable().toMapx(0, 1);
		dt.insertColumn("Factory");
		for (int i = 0; dt != null && i < dt.getRowCount(); i++) {
			dt.set(i, "Factory", factoryMap.getString(dt.getString(i, "GoodsID")));
		}
		dga.bindData(dt);
	}

	public static Mapx init(Mapx params) {
		params.put("IsValid", HtmlUtil.codeToRadios("IsValid", "Order.IsValid", "Y"));
		params.put("HasInvoice", HtmlUtil.codeToRadios("HasInvoice", "Order.HasInvoice", "Y"));
		params.put("Status", HtmlUtil.codeToOptions("Order.Status"));
		params.put("SendTimeSlice", HtmlUtil.codeToOptions("Order.SendTimeSlice"));
		DataTable sendTypeDT = new QueryBuilder("select name, id , price, info from zssend where siteid = ? order by id", params.getString("SiteID")).executeDataTable();
		for (int i = 0; i < sendTypeDT.getRowCount(); i++) {
			sendTypeDT.set(i, "name", "<span class='sendName'>" + sendTypeDT.getString(i, "name") + "</span>&nbsp;<span class='sendPrice'>￥" + sendTypeDT.getString(i, "price") + "</span>&nbsp;<span class='sendInfo'>备注：" + sendTypeDT.getString(i, "info") + "</span>");
		}
		sendTypeDT.deleteColumn("info");
		sendTypeDT.deleteColumn("price");
		params.put("SendTypeRadio", HtmlUtil.dataTableToRadios("SendTypeRadio", sendTypeDT, true));
		params.put("SendTimeSlice", HtmlUtil.codeToRadios("SendTimeSlice", "Order.SendTimeSlice"));
		DataTable paymentTypeDT = new QueryBuilder("select name,id, info from zspayment where siteid = ? order by id", params.getString("SiteID")).executeDataTable();
		for (int i = 0; i < paymentTypeDT.getRowCount(); i++) {
			paymentTypeDT.set(i, "name", "<span class='paymentName'>" + paymentTypeDT.getString(i, "name") + "</span>&nbsp;<span class='paymentInfo'>备注：" + paymentTypeDT.getString(i, "info") + "</span>");
		}
		params.put("PaymentTypeRadio", HtmlUtil.dataTableToRadios("PaymentTypeRadio", paymentTypeDT, true));
		return params;
	}

	public static Mapx initOrderResult(Mapx params) {
		String ID = params.getString("OrderID");
		if (ID != null) {
			ZSOrderSchema order = new ZSOrderSchema();
			order.setID(ID);
			order.fill();
			params.putAll(order.toMapx());
			params.put("HasInvoice", HtmlUtil.codeToMapx("Order.HasInvoice").getString(order.getHasInvoice()));
			Mapx sendTypeMap = new QueryBuilder("select name,id from zssend order by id").executeDataTable().toMapx(1, 0);
			Mapx paymentTypeMap = new QueryBuilder("select name,id from zspayment order by id").executeDataTable().toMapx(1, 0);
			params.put("SendType", sendTypeMap.getString(order.getSendType()));
			params.put("SendTimeSlice", CacheManager.get("Code", "Order.SendTimeSlice", order.getSendTimeSlice()));
			params.put("PaymentType", paymentTypeMap.getString(order.getPaymentType()));
			Mapx districtMap = new QueryBuilder("select code,name from zddistrict").executeDataTable().toMapx(0, 1);
			String province = params.getString("Province");
			String city = params.getString("City");
			params.put("Province", districtMap.getString(province));
			params.put("City", districtMap.getString(city));
			params.put("District", districtMap.getString(params.getString("District")));
			params.put("OrderID", ID);
			return params;
		} else {
			return params;
		}
	}

	public void add() {
		String sendType = $V("SendTypeRadio");
		String sendTimeSlice = $V("SendTimeSlice");
		String sendInfo = $V("SendInfo");
		String amount = $V("Amount");
		String shopCart = Request.getString("Cookie.ShopCart");
		Mapx map = new Mapx();
		if(StringUtil.isNotEmpty(shopCart)) {
			String[] cartGoods = shopCart.split("X");
			for(int i = 0; i < cartGoods.length; i++) {
				map.put(cartGoods[i].split("-")[0], cartGoods[i].split("-")[1]);
			}
		}
		if (Double.parseDouble(amount) <= 0 || map.isEmpty()) {
			Response.setLogInfo(0, "请先添加商品！");
			return;
		}
		Transaction trans = new Transaction();
		ZSOrderSchema order = new ZSOrderSchema();
		String UserName = User.getUserName();
		String date = DateUtil.getCurrentDate("yyyyMMdd");
		String OrderID = NoUtil.getMaxNo("OrderID", date, 4);
		String sendFee = $V("SendFee");
		String orderAmount = $V("OrderAmount");
		order.setID(OrderID);
		order.setValue(Request);
		order.setName($V("RealName"));
		order.setSendType(sendType);
		order.setSendTimeSlice(sendTimeSlice);
		order.setSendInfo(sendInfo);
		order.setPaymentType($V("PaymentTypeRadio"));
		if ($V("SendAddr").equals("Other")) {
			ZDMemberAddrSchema addr = new ZDMemberAddrSchema();
			addr.setID(NoUtil.getMaxID("MemberAddr"));
			addr.setValue(Request);
			addr.setUserName(StringUtil.isNotEmpty(User.getUserName()) ? User.getUserName() : UserName);
			addr.setAddUser(StringUtil.isNotEmpty(User.getUserName()) ? User.getUserName() : UserName);
			addr.setAddTime(new Date());
			trans.add(new QueryBuilder("update zdmemberaddr set IsDefault = 'N' where UserName = ?", StringUtil.isNotEmpty(User.getUserName()) ? User.getUserName()
					: UserName));
			trans.add(addr, Transaction.INSERT);
		}
		order.setAddUser(StringUtil.isNotEmpty(User.getUserName()) ? User.getUserName() : UserName);
		order.setAddTime(new Date());
		order.setUserName(UserName);
		// order.setSiteID(Application.getCurrentSiteID());
		order.setAmount(amount);
		order.setSendFee(sendFee);
		order.setOrderAmount(orderAmount);
		trans.add(order, Transaction.INSERT);

		// 添加订单详细项
		Object[] ids = map.keyArray();
		ZSGoodsSchema goods = new ZSGoodsSchema();
		DecimalFormat fmt = new DecimalFormat("0.00");
		for (int i = 0; i < ids.length; i++) {
			goods.setID(ids[i].toString());
			if (goods.fill()) {
				ZSOrderItemSchema orderItem = new ZSOrderItemSchema();
				orderItem.setOrderID(OrderID);
				orderItem.setGoodsID(goods.getID());
				orderItem.setSiteID($V("SiteID"));
				orderItem.setUserName(UserName);
				orderItem.setSN(goods.getSN());
				orderItem.setName(goods.getName());
				orderItem.setPrice(goods.getPrice());
				orderItem.setDiscount(1);
				orderItem.setDiscountPrice(fmt.format(goods.getPrice() * orderItem.getDiscount()));
				orderItem.setCount(map.getInt(ids[i]));
				orderItem.setAmount(orderItem.getCount() * orderItem.getDiscountPrice());
				orderItem.setScore(goods.getScore() * orderItem.getCount());
				orderItem.setAddTime(new Date());
				orderItem.setAddUser(UserName);
				trans.add(orderItem, Transaction.INSERT);
			}
		}
		if (trans.commit()) {
			Response.setLogInfo(1, String.valueOf(order.getID()));
			this.Cookie.setCookie("ShopCart", "", -1);
		} else {
			Response.setLogInfo(0, "发生错误!");
		}
	}
}
