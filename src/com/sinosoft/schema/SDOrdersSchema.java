package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：订单表<br>
 * 表代码：SDOrders<br>
 * 表主键：id<br>
 */
public class SDOrdersSchema extends Schema {
	private String id;

	private Date createDate;

	private Date modifyDate;

	private String orderSn;

	private String memberId;

	private Integer orderStatus;

	private String payType;

	private Integer payStatus;

	private String productNum;

	private String productTotalPrice;

	private String discountRates;

	private String discountAmount;

	private String totalAmount;

	private String payAmount;

	private String paySn;

	private String remark;

	private String tbTradeSeriNo;

	private String tbComCode;

	private String couponSn;

	private String offsetPoint;

	private Long commentId;

	private String orderCoupon;

	private String orderIntegral;

	private String sumCoupon;

	private String sumIntegral;

	private String activitySn;

	private String orderActivity;

	private String payPrice;

	private String sumActivity;

	private String channelsn;
	
	private String dellFlag;
	
	private String diyActivitySn;
	
	private String diyActivityDescription;
	
	private String mark;
	
	private String renewalId;
	
	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("createDate", DataColumn.DATETIME, 1, 0 , 0 , true , false),
		new SchemaColumn("modifyDate", DataColumn.DATETIME, 2, 0 , 0 , false , false),
		new SchemaColumn("orderSn", DataColumn.STRING, 3, 25 , 0 , true , false),
		new SchemaColumn("memberId", DataColumn.STRING, 4, 32 , 0 , false , false),
		new SchemaColumn("orderStatus", DataColumn.INTEGER, 5, 11 , 0 , true , false),
		new SchemaColumn("payType", DataColumn.STRING, 6, 10 , 0 , false , false),
		new SchemaColumn("payStatus", DataColumn.INTEGER, 7, 11 , 0 , false , false),
		new SchemaColumn("productNum", DataColumn.STRING, 8, 5 , 0 , true , false),
		new SchemaColumn("productTotalPrice", DataColumn.STRING, 9, 20 , 0 , true , false),
		new SchemaColumn("discountRates", DataColumn.STRING, 10, 10 , 0 , false , false),
		new SchemaColumn("discountAmount", DataColumn.STRING, 11, 20 , 0 , false , false),
		new SchemaColumn("totalAmount", DataColumn.STRING, 12, 20 , 0 , true , false),
		new SchemaColumn("payAmount", DataColumn.STRING, 13, 20 , 0 , false , false),
		new SchemaColumn("paySn", DataColumn.STRING, 14, 25 , 0 , false , false),
		new SchemaColumn("remark", DataColumn.STRING, 15, 20 , 0 , false , false),
		new SchemaColumn("tbTradeSeriNo", DataColumn.STRING, 16, 30 , 0 , false , false),
		new SchemaColumn("tbComCode", DataColumn.STRING, 17, 255 , 0 , false , false),
		new SchemaColumn("couponSn", DataColumn.STRING, 18, 32 , 0 , false , false),
		new SchemaColumn("offsetPoint", DataColumn.STRING, 19, 255 , 0 , false , false),
		new SchemaColumn("commentId", DataColumn.LONG, 20, 20 , 0 , false , false),
		new SchemaColumn("orderCoupon", DataColumn.STRING, 21, 10 , 0 , false , false),
		new SchemaColumn("orderIntegral", DataColumn.STRING, 22, 10 , 0 , false , false),
		new SchemaColumn("sumCoupon", DataColumn.STRING, 23, 10 , 0 , false , false),
		new SchemaColumn("sumIntegral", DataColumn.STRING, 24, 10 , 0 , false , false),
		new SchemaColumn("activitySn", DataColumn.STRING, 25, 50 , 0 , false , false),
		new SchemaColumn("orderActivity", DataColumn.STRING, 26, 10 , 0 , false , false),
		new SchemaColumn("payPrice", DataColumn.STRING, 27, 10 , 0 , false , false),
		new SchemaColumn("sumActivity", DataColumn.STRING, 28, 10 , 0 , false , false),
		new SchemaColumn("channelsn", DataColumn.STRING, 29, 20 , 0 , false , false),
		new SchemaColumn("dellFlag", DataColumn.STRING, 30, 5 , 0 , false , false),
		new SchemaColumn("diyActivitySn", DataColumn.STRING, 31, 5 , 0 , false , false),
		new SchemaColumn("diyActivityDescription", DataColumn.STRING, 32, 5 , 0 , false , false),
		new SchemaColumn("mark", DataColumn.STRING, 33, 5 , 0 , false , false),
		new SchemaColumn("renewalId", DataColumn.STRING, 34, 50 , 0 , false , false)
	};

	public static final String _TableCode = "SDOrders";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into SDOrders values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update SDOrders set id=?,createDate=?,modifyDate=?,orderSn=?,memberId=?,orderStatus=?,payType=?,payStatus=?,productNum=?,productTotalPrice=?,discountRates=?,discountAmount=?,totalAmount=?,payAmount=?,paySn=?,remark=?,tbTradeSeriNo=?,tbComCode=?,couponSn=?,offsetPoint=?,commentId=?,orderCoupon=?,orderIntegral=?,sumCoupon=?,sumIntegral=?,activitySn=?,orderActivity=?,payPrice=?,sumActivity=?,channelsn=?,dellFlag=?,diyActivitySn=?,diyActivityDescription=?,mark=?,renewalId=? where id=?";

	protected static final String _DeleteSQL = "delete from SDOrders  where id=?";

	protected static final String _FillAllSQL = "select * from SDOrders  where id=?";

	public SDOrdersSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[34];
	}

	protected Schema newInstance(){
		return new SDOrdersSchema();
	}

	protected SchemaSet newSet(){
		return new SDOrdersSet();
	}

	public SDOrdersSet query() {
		return query(null, -1, -1);
	}

	public SDOrdersSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public SDOrdersSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public SDOrdersSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (SDOrdersSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){createDate = (Date)v;return;}
		if (i == 2){modifyDate = (Date)v;return;}
		if (i == 3){orderSn = (String)v;return;}
		if (i == 4){memberId = (String)v;return;}
		if (i == 5){if(v==null){orderStatus = null;}else{orderStatus = new Integer(v.toString());}return;}
		if (i == 6){payType = (String)v;return;}
		if (i == 7){if(v==null){payStatus = null;}else{payStatus = new Integer(v.toString());}return;}
		if (i == 8){productNum = (String)v;return;}
		if (i == 9){productTotalPrice = (String)v;return;}
		if (i == 10){discountRates = (String)v;return;}
		if (i == 11){discountAmount = (String)v;return;}
		if (i == 12){totalAmount = (String)v;return;}
		if (i == 13){payAmount = (String)v;return;}
		if (i == 14){paySn = (String)v;return;}
		if (i == 15){remark = (String)v;return;}
		if (i == 16){tbTradeSeriNo = (String)v;return;}
		if (i == 17){tbComCode = (String)v;return;}
		if (i == 18){couponSn = (String)v;return;}
		if (i == 19){offsetPoint = (String)v;return;}
		if (i == 20){if(v==null){commentId = null;}else{commentId = new Long(v.toString());}return;}
		if (i == 21){orderCoupon = (String)v;return;}
		if (i == 22){orderIntegral = (String)v;return;}
		if (i == 23){sumCoupon = (String)v;return;}
		if (i == 24){sumIntegral = (String)v;return;}
		if (i == 25){activitySn = (String)v;return;}
		if (i == 26){orderActivity = (String)v;return;}
		if (i == 27){payPrice = (String)v;return;}
		if (i == 28){sumActivity = (String)v;return;}
		if (i == 29){channelsn = (String)v;return;}
		if (i == 30){dellFlag = (String)v;return;}
		if (i == 31){diyActivitySn = (String)v;return;}
		if (i == 32){diyActivityDescription = (String)v;return;}
		if (i == 33){mark = (String)v;return;}
		if (i == 34){renewalId = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return createDate;}
		if (i == 2){return modifyDate;}
		if (i == 3){return orderSn;}
		if (i == 4){return memberId;}
		if (i == 5){return orderStatus;}
		if (i == 6){return payType;}
		if (i == 7){return payStatus;}
		if (i == 8){return productNum;}
		if (i == 9){return productTotalPrice;}
		if (i == 10){return discountRates;}
		if (i == 11){return discountAmount;}
		if (i == 12){return totalAmount;}
		if (i == 13){return payAmount;}
		if (i == 14){return paySn;}
		if (i == 15){return remark;}
		if (i == 16){return tbTradeSeriNo;}
		if (i == 17){return tbComCode;}
		if (i == 18){return couponSn;}
		if (i == 19){return offsetPoint;}
		if (i == 20){return commentId;}
		if (i == 21){return orderCoupon;}
		if (i == 22){return orderIntegral;}
		if (i == 23){return sumCoupon;}
		if (i == 24){return sumIntegral;}
		if (i == 25){return activitySn;}
		if (i == 26){return orderActivity;}
		if (i == 27){return payPrice;}
		if (i == 28){return sumActivity;}
		if (i == 29){return channelsn;}
		if (i == 30){return dellFlag;}
		if (i == 31){return diyActivitySn;}
		if (i == 32){return diyActivityDescription;}
		if (i == 33){return mark;}
		if (i == 34){return renewalId;}

		return null;
	}

	/**
	* 获取字段id的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getid() {
		return id;
	}

	/**
	* 设置字段id的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setid(String id) {
		this.id = id;
    }

	/**
	* 获取字段createDate的值，该字段的<br>
	* 字段名称 :创建日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public Date getcreateDate() {
		return createDate;
	}

	/**
	* 设置字段createDate的值，该字段的<br>
	* 字段名称 :创建日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setcreateDate(Date createDate) {
		this.createDate = createDate;
    }

	/**
	* 获取字段modifyDate的值，该字段的<br>
	* 字段名称 :修改日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getmodifyDate() {
		return modifyDate;
	}

	/**
	* 设置字段modifyDate的值，该字段的<br>
	* 字段名称 :修改日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmodifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
    }

	/**
	* 获取字段orderSn的值，该字段的<br>
	* 字段名称 :订单编号<br>
	* 数据类型 :varchar(25)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getorderSn() {
		return orderSn;
	}

	/**
	* 设置字段orderSn的值，该字段的<br>
	* 字段名称 :订单编号<br>
	* 数据类型 :varchar(25)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setorderSn(String orderSn) {
		this.orderSn = orderSn;
    }

	/**
	* 获取字段memberId的值，该字段的<br>
	* 字段名称 :会员编号<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getmemberId() {
		return memberId;
	}

	/**
	* 设置字段memberId的值，该字段的<br>
	* 字段名称 :会员编号<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmemberId(String memberId) {
		this.memberId = memberId;
    }

	/**
	* 获取字段orderStatus的值，该字段的<br>
	* 字段名称 :订单状态<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public int getorderStatus() {
		if(orderStatus==null){return 0;}
		return orderStatus.intValue();
	}

	/**
	* 设置字段orderStatus的值，该字段的<br>
	* 字段名称 :订单状态<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setorderStatus(int orderStatus) {
		this.orderStatus = new Integer(orderStatus);
    }

	/**
	* 设置字段orderStatus的值，该字段的<br>
	* 字段名称 :订单状态<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setorderStatus(String orderStatus) {
		if (orderStatus == null){
			this.orderStatus = null;
			return;
		}
		this.orderStatus = new Integer(orderStatus);
    }

	/**
	* 获取字段payType的值，该字段的<br>
	* 字段名称 :支付类型<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getpayType() {
		return payType;
	}

	/**
	* 设置字段payType的值，该字段的<br>
	* 字段名称 :支付类型<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpayType(String payType) {
		this.payType = payType;
    }

	/**
	* 获取字段payStatus的值，该字段的<br>
	* 字段名称 :支付状态<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getpayStatus() {
		if(payStatus==null){return 0;}
		return payStatus.intValue();
	}

	/**
	* 设置字段payStatus的值，该字段的<br>
	* 字段名称 :支付状态<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpayStatus(int payStatus) {
		this.payStatus = new Integer(payStatus);
    }

	/**
	* 设置字段payStatus的值，该字段的<br>
	* 字段名称 :支付状态<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpayStatus(String payStatus) {
		if (payStatus == null){
			this.payStatus = null;
			return;
		}
		this.payStatus = new Integer(payStatus);
    }

	/**
	* 获取字段productNum的值，该字段的<br>
	* 字段名称 :商品总数<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getproductNum() {
		return productNum;
	}

	/**
	* 设置字段productNum的值，该字段的<br>
	* 字段名称 :商品总数<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setproductNum(String productNum) {
		this.productNum = productNum;
    }

	/**
	* 获取字段productTotalPrice的值，该字段的<br>
	* 字段名称 :商品总价格<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getproductTotalPrice() {
		return productTotalPrice;
	}

	/**
	* 设置字段productTotalPrice的值，该字段的<br>
	* 字段名称 :商品总价格<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setproductTotalPrice(String productTotalPrice) {
		this.productTotalPrice = productTotalPrice;
    }

	/**
	* 获取字段discountRates的值，该字段的<br>
	* 字段名称 :订单折扣费率<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getdiscountRates() {
		return discountRates;
	}

	/**
	* 设置字段discountRates的值，该字段的<br>
	* 字段名称 :订单折扣费率<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setdiscountRates(String discountRates) {
		this.discountRates = discountRates;
    }

	/**
	* 获取字段discountAmount的值，该字段的<br>
	* 字段名称 :订单优惠金额<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getdiscountAmount() {
		return discountAmount;
	}

	/**
	* 设置字段discountAmount的值，该字段的<br>
	* 字段名称 :订单优惠金额<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setdiscountAmount(String discountAmount) {
		this.discountAmount = discountAmount;
    }

	/**
	* 获取字段totalAmount的值，该字段的<br>
	* 字段名称 :订单总额<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String gettotalAmount() {
		return totalAmount;
	}

	/**
	* 设置字段totalAmount的值，该字段的<br>
	* 字段名称 :订单总额<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void settotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
    }

	/**
	* 获取字段payAmount的值，该字段的<br>
	* 字段名称 :已付金额<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getpayAmount() {
		return payAmount;
	}

	/**
	* 设置字段payAmount的值，该字段的<br>
	* 字段名称 :已付金额<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpayAmount(String payAmount) {
		this.payAmount = payAmount;
    }

	/**
	* 获取字段paySn的值，该字段的<br>
	* 字段名称 :支付流水号<br>
	* 数据类型 :varchar(25)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getpaySn() {
		return paySn;
	}

	/**
	* 设置字段paySn的值，该字段的<br>
	* 字段名称 :支付流水号<br>
	* 数据类型 :varchar(25)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpaySn(String paySn) {
		this.paySn = paySn;
    }

	/**
	* 获取字段remark的值，该字段的<br>
	* 字段名称 :备用字段<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getremark() {
		return remark;
	}

	/**
	* 设置字段remark的值，该字段的<br>
	* 字段名称 :备用字段<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setremark(String remark) {
		this.remark = remark;
    }

	/**
	* 获取字段tbTradeSeriNo的值，该字段的<br>
	* 字段名称 :淘宝交易流水号（TB）<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String gettbTradeSeriNo() {
		return tbTradeSeriNo;
	}

	/**
	* 设置字段tbTradeSeriNo的值，该字段的<br>
	* 字段名称 :淘宝交易流水号（TB）<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void settbTradeSeriNo(String tbTradeSeriNo) {
		this.tbTradeSeriNo = tbTradeSeriNo;
    }

	/**
	* 获取字段tbComCode的值，该字段的<br>
	* 字段名称 :淘宝店铺号<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String gettbComCode() {
		return tbComCode;
	}

	/**
	* 设置字段tbComCode的值，该字段的<br>
	* 字段名称 :淘宝店铺号<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void settbComCode(String tbComCode) {
		this.tbComCode = tbComCode;
    }

	/**
	* 获取字段couponSn的值，该字段的<br>
	* 字段名称 :优惠劵号<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcouponSn() {
		return couponSn;
	}

	/**
	* 设置字段couponSn的值，该字段的<br>
	* 字段名称 :优惠劵号<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcouponSn(String couponSn) {
		this.couponSn = couponSn;
    }

	/**
	* 获取字段offsetPoint的值，该字段的<br>
	* 字段名称 :积分<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getoffsetPoint() {
		return offsetPoint;
	}

	/**
	* 设置字段offsetPoint的值，该字段的<br>
	* 字段名称 :积分<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setoffsetPoint(String offsetPoint) {
		this.offsetPoint = offsetPoint;
    }

	/**
	* 获取字段commentId的值，该字段的<br>
	* 字段名称 :评论Id<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getcommentId() {
		if(commentId==null){return 0;}
		return commentId.longValue();
	}

	/**
	* 设置字段commentId的值，该字段的<br>
	* 字段名称 :评论Id<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcommentId(long commentId) {
		this.commentId = new Long(commentId);
    }

	/**
	* 设置字段commentId的值，该字段的<br>
	* 字段名称 :评论Id<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcommentId(String commentId) {
		if (commentId == null){
			this.commentId = null;
			return;
		}
		this.commentId = new Long(commentId);
    }

	/**
	* 获取字段orderCoupon的值，该字段的<br>
	* 字段名称 :订单优惠券优惠金额<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getorderCoupon() {
		return orderCoupon;
	}

	/**
	* 设置字段orderCoupon的值，该字段的<br>
	* 字段名称 :订单优惠券优惠金额<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setorderCoupon(String orderCoupon) {
		this.orderCoupon = orderCoupon;
    }

	/**
	* 获取字段orderIntegral的值，该字段的<br>
	* 字段名称 :订单积分抵值金额<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getorderIntegral() {
		return orderIntegral;
	}

	/**
	* 设置字段orderIntegral的值，该字段的<br>
	* 字段名称 :订单积分抵值金额<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setorderIntegral(String orderIntegral) {
		this.orderIntegral = orderIntegral;
    }

	/**
	* 获取字段sumCoupon的值，该字段的<br>
	* 字段名称 :交易总优惠金额<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getsumCoupon() {
		return sumCoupon;
	}

	/**
	* 设置字段sumCoupon的值，该字段的<br>
	* 字段名称 :交易总优惠金额<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setsumCoupon(String sumCoupon) {
		this.sumCoupon = sumCoupon;
    }

	/**
	* 获取字段sumIntegral的值，该字段的<br>
	* 字段名称 :交易总积分抵值金额<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getsumIntegral() {
		return sumIntegral;
	}

	/**
	* 设置字段sumIntegral的值，该字段的<br>
	* 字段名称 :交易总积分抵值金额<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setsumIntegral(String sumIntegral) {
		this.sumIntegral = sumIntegral;
    }

	/**
	* 获取字段activitySn的值，该字段的<br>
	* 字段名称 :活动编码<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getactivitySn() {
		return activitySn;
	}

	/**
	* 设置字段activitySn的值，该字段的<br>
	* 字段名称 :活动编码<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setactivitySn(String activitySn) {
		this.activitySn = activitySn;
    }

	/**
	* 获取字段orderActivity的值，该字段的<br>
	* 字段名称 :订单活动优惠金额<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getorderActivity() {
		return orderActivity;
	}

	/**
	* 设置字段orderActivity的值，该字段的<br>
	* 字段名称 :订单活动优惠金额<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setorderActivity(String orderActivity) {
		this.orderActivity = orderActivity;
    }

	/**
	* 获取字段payPrice的值，该字段的<br>
	* 字段名称 :订单实际支付金额<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getpayPrice() {
		return payPrice;
	}

	/**
	* 设置字段payPrice的值，该字段的<br>
	* 字段名称 :订单实际支付金额<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpayPrice(String payPrice) {
		this.payPrice = payPrice;
    }

	/**
	* 获取字段sumActivity的值，该字段的<br>
	* 字段名称 :活动总优惠金额<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getsumActivity() {
		return sumActivity;
	}

	/**
	* 设置字段sumActivity的值，该字段的<br>
	* 字段名称 :活动总优惠金额<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setsumActivity(String sumActivity) {
		this.sumActivity = sumActivity;
    }

	/**
	* 获取字段channelsn的值，该字段的<br>
	* 字段名称 :渠道<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getchannelsn() {
		return channelsn;
	}

	/**
	* 设置字段channelsn的值，该字段的<br>
	* 字段名称 :渠道<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setchannelsn(String channelsn) {
		this.channelsn = channelsn;
    }
	/**
	 * 获取字段dellFlag的值，该字段的<br>
	 * 字段名称 :订单是否删除标志位<br>
	 * 数据类型 :varchar(5)<br>
	 * 是否主键 :false<br>
	 * 是否必填 :false<br>
	 */
	public String getDellFlag() {
		return dellFlag;
	}
	/**
	 * 设置字段dellFlag的值，该字段的<br>
	 * 字段名称 :订单是否删除标志位<br>
	 * 数据类型 :varchar(5)<br>
	 * 是否主键 :false<br>
	 * 是否必填 :false<br>
	 */
	public void setDellFlag(String dellFlag) {
		this.dellFlag = dellFlag;
	}
	/**
	 * 获取字段diyActivitySn的值，该字段的<br>
	 * 字段名称 :自定义活动号<br>
	 * 数据类型 :varchar(50)<br>
	 * 是否主键 :false<br>
	 * 是否必填 :false<br>
	 */
	public String getDiyActivitySn() {
		return diyActivitySn;
	}
	/**
	 * 设置字段diyActivitySn的值，该字段的<br>
	 * 字段名称 :自定义活动号<br>
	 * 数据类型 :varchar(50)<br>
	 * 是否主键 :false<br>
	 * 是否必填 :false<br>
	 */
	public void setDiyActivitySn(String diyActivitySn) {
		this.diyActivitySn = diyActivitySn;
	}
	/**
	 * 获取字段diyActivityDescription的值，该字段的<br>
	 * 字段名称 :自定义活动描述<br>
	 * 数据类型 :varchar(100)<br>   
	 * 是否主键 :false<br>
	 * 是否必填 :false<br>
	 */
	public String getDiyActivityDescription() {
		return diyActivityDescription;
	}
	/**
	 * 设置字段diyActivityDescription的值，该字段的<br>
	 * 字段名称 :自定义活动描述<br>
	 * 数据类型 :varchar(100)<br>
	 * 是否主键 :false<br>
	 * 是否必填 :false<br>
	 */
	public void setDiyActivityDescription(String diyActivityDescription) {
		this.diyActivityDescription = diyActivityDescription;
	}
	
	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getRenewalId() {
		return renewalId;
	}

	public void setRenewalId(String renewalId) {
		this.renewalId = renewalId;
	}
	
	
}