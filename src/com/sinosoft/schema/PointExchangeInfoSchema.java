package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

import java.util.Date;

import java.util.Date;

/**
 * 表名称：积分兑换表<br>
 * 表代码：PointExchangeInfo<br>
 * 表主键：id<br>
 */
public class PointExchangeInfoSchema extends Schema {
	private String id;

	private String productName;

	private String productid;

	private String type;

	private String points;

	private String memberid;

	private String status;

	private String orderSn;

	private String mobileNo;

	private String fuLuOrderSn;

	private String fuLuGoodsID;

	private String exchangeQuantity;

	private String cardNo;

	private String cardKey;

	private String cardDeadline;

	private String purchasePrice;

	private String fuLuOrderStatus;

	private String goodsStockID;

	private String giftClassifyID;

	private String wrongMassage;

	private String prop1;//订单已取消状态：Y

	private String prop2;//渠道

	private String prop3;//福禄二次充值订单号  ordersn_N

	private String prop4;

	private String prop5;

	private String prop6;

	private String prop7;

	private String createUser;

	private Date createDate;

	private String modifyUser;

	private Date modifyDate;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("productName", DataColumn.STRING, 1, 100 , 0 , false , false),
		new SchemaColumn("productid", DataColumn.STRING, 2, 100 , 0 , false , false),
		new SchemaColumn("type", DataColumn.STRING, 3, 100 , 0 , false , false),
		new SchemaColumn("points", DataColumn.STRING, 4, 255 , 0 , false , false),
		new SchemaColumn("memberid", DataColumn.STRING, 5, 255 , 0 , false , false),
		new SchemaColumn("status", DataColumn.STRING, 6, 100 , 0 , false , false),
		new SchemaColumn("orderSn", DataColumn.STRING, 7, 25 , 0 , false , false),
		new SchemaColumn("mobileNo", DataColumn.STRING, 8, 25 , 0 , false , false),
		new SchemaColumn("fuLuOrderSn", DataColumn.STRING, 9, 25 , 0 , false , false),
		new SchemaColumn("fuLuGoodsID", DataColumn.STRING, 10, 25 , 0 , false , false),
		new SchemaColumn("exchangeQuantity", DataColumn.STRING, 11, 25 , 0 , false , false),
		new SchemaColumn("cardNo", DataColumn.STRING, 12, 25 , 0 , false , false),
		new SchemaColumn("cardKey", DataColumn.STRING, 13, 25 , 0 , false , false),
		new SchemaColumn("cardDeadline", DataColumn.STRING, 14, 20 , 0 , false , false),
		new SchemaColumn("purchasePrice", DataColumn.STRING, 15, 25 , 0 , false , false),
		new SchemaColumn("fuLuOrderStatus", DataColumn.STRING, 16, 25 , 0 , false , false),
		new SchemaColumn("goodsStockID", DataColumn.STRING, 17, 30 , 0 , false , false),
		new SchemaColumn("giftClassifyID", DataColumn.STRING, 18, 30 , 0 , false , false),
		new SchemaColumn("wrongMassage", DataColumn.STRING, 19, 255 , 0 , false , false),
		new SchemaColumn("prop1", DataColumn.STRING, 20, 255 , 0 , false , false),
		new SchemaColumn("prop2", DataColumn.STRING, 21, 255 , 0 , false , false),
		new SchemaColumn("prop3", DataColumn.STRING, 22, 255 , 0 , false , false),
		new SchemaColumn("prop4", DataColumn.STRING, 23, 255 , 0 , false , false),
		new SchemaColumn("prop5", DataColumn.STRING, 24, 255 , 0 , false , false),
		new SchemaColumn("prop6", DataColumn.STRING, 25, 255 , 0 , false , false),
		new SchemaColumn("prop7", DataColumn.STRING, 26, 255 , 0 , false , false),
		new SchemaColumn("createUser", DataColumn.STRING, 27, 255 , 0 , false , false),
		new SchemaColumn("createDate", DataColumn.DATETIME, 28, 0 , 0 , false , false),
		new SchemaColumn("modifyUser", DataColumn.STRING, 29, 255 , 0 , false , false),
		new SchemaColumn("modifyDate", DataColumn.DATETIME, 30, 0 , 0 , false , false)
	};

	public static final String _TableCode = "PointExchangeInfo";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into PointExchangeInfo values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update PointExchangeInfo set id=?,productName=?,productid=?,type=?,points=?,memberid=?,status=?,orderSn=?,mobileNo=?,fuLuOrderSn=?,fuLuGoodsID=?,exchangeQuantity=?,cardNo=?,cardKey=?,cardDeadline=?,purchasePrice=?,fuLuOrderStatus=?,goodsStockID=?,giftClassifyID=?,wrongMassage=?,prop1=?,prop2=?,prop3=?,prop4=?,prop5=?,prop6=?,prop7=?,createUser=?,createDate=?,modifyUser=?,modifyDate=? where id=?";

	protected static final String _DeleteSQL = "delete from PointExchangeInfo  where id=?";

	protected static final String _FillAllSQL = "select * from PointExchangeInfo  where id=?";

	public PointExchangeInfoSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[31];
	}

	protected Schema newInstance(){
		return new PointExchangeInfoSchema();
	}

	protected SchemaSet newSet(){
		return new PointExchangeInfoSet();
	}

	public PointExchangeInfoSet query() {
		return query(null, -1, -1);
	}

	public PointExchangeInfoSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public PointExchangeInfoSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public PointExchangeInfoSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (PointExchangeInfoSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){productName = (String)v;return;}
		if (i == 2){productid = (String)v;return;}
		if (i == 3){type = (String)v;return;}
		if (i == 4){points = (String)v;return;}
		if (i == 5){memberid = (String)v;return;}
		if (i == 6){status = (String)v;return;}
		if (i == 7){orderSn = (String)v;return;}
		if (i == 8){mobileNo = (String)v;return;}
		if (i == 9){fuLuOrderSn = (String)v;return;}
		if (i == 10){fuLuGoodsID = (String)v;return;}
		if (i == 11){exchangeQuantity = (String)v;return;}
		if (i == 12){cardNo = (String)v;return;}
		if (i == 13){cardKey = (String)v;return;}
		if (i == 14){cardDeadline = (String)v;return;}
		if (i == 15){purchasePrice = (String)v;return;}
		if (i == 16){fuLuOrderStatus = (String)v;return;}
		if (i == 17){goodsStockID = (String)v;return;}
		if (i == 18){giftClassifyID = (String)v;return;}
		if (i == 19){wrongMassage = (String)v;return;}
		if (i == 20){prop1 = (String)v;return;}
		if (i == 21){prop2 = (String)v;return;}
		if (i == 22){prop3 = (String)v;return;}
		if (i == 23){prop4 = (String)v;return;}
		if (i == 24){prop5 = (String)v;return;}
		if (i == 25){prop6 = (String)v;return;}
		if (i == 26){prop7 = (String)v;return;}
		if (i == 27){createUser = (String)v;return;}
		if (i == 28){createDate = (Date)v;return;}
		if (i == 29){modifyUser = (String)v;return;}
		if (i == 30){modifyDate = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return productName;}
		if (i == 2){return productid;}
		if (i == 3){return type;}
		if (i == 4){return points;}
		if (i == 5){return memberid;}
		if (i == 6){return status;}
		if (i == 7){return orderSn;}
		if (i == 8){return mobileNo;}
		if (i == 9){return fuLuOrderSn;}
		if (i == 10){return fuLuGoodsID;}
		if (i == 11){return exchangeQuantity;}
		if (i == 12){return cardNo;}
		if (i == 13){return cardKey;}
		if (i == 14){return cardDeadline;}
		if (i == 15){return purchasePrice;}
		if (i == 16){return fuLuOrderStatus;}
		if (i == 17){return goodsStockID;}
		if (i == 18){return giftClassifyID;}
		if (i == 19){return wrongMassage;}
		if (i == 20){return prop1;}
		if (i == 21){return prop2;}
		if (i == 22){return prop3;}
		if (i == 23){return prop4;}
		if (i == 24){return prop5;}
		if (i == 25){return prop6;}
		if (i == 26){return prop7;}
		if (i == 27){return createUser;}
		if (i == 28){return createDate;}
		if (i == 29){return modifyUser;}
		if (i == 30){return modifyDate;}
		return null;
	}

	/**
	* 获取字段id的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getid() {
		return id;
	}

	/**
	* 设置字段id的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setid(String id) {
		this.id = id;
    }

	/**
	* 获取字段productName的值，该字段的<br>
	* 字段名称 :产品名称<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getproductName() {
		return productName;
	}

	/**
	* 设置字段productName的值，该字段的<br>
	* 字段名称 :产品名称<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setproductName(String productName) {
		this.productName = productName;
    }

	/**
	* 获取字段productid的值，该字段的<br>
	* 字段名称 :产品ID<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getproductid() {
		return productid;
	}

	/**
	* 设置字段productid的值，该字段的<br>
	* 字段名称 :产品ID<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setproductid(String productid) {
		this.productid = productid;
    }

	/**
	* 获取字段type的值，该字段的<br>
	* 字段名称 :订单类型<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String gettype() {
		return type;
	}

	/**
	* 设置字段type的值，该字段的<br>
	* 字段名称 :订单类型<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void settype(String type) {
		this.type = type;
    }

	/**
	* 获取字段points的值，该字段的<br>
	* 字段名称 :兑换积分<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getpoints() {
		return points;
	}

	/**
	* 设置字段points的值，该字段的<br>
	* 字段名称 :兑换积分<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpoints(String points) {
		this.points = points;
    }

	/**
	* 获取字段memberid的值，该字段的<br>
	* 字段名称 :会员id<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getmemberid() {
		return memberid;
	}

	/**
	* 设置字段memberid的值，该字段的<br>
	* 字段名称 :会员id<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmemberid(String memberid) {
		this.memberid = memberid;
    }

	/**
	* 获取字段status的值，该字段的<br>
	* 字段名称 :状态<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getstatus() {
		return status;
	}

	/**
	* 设置字段status的值，该字段的<br>
	* 字段名称 :状态<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setstatus(String status) {
		this.status = status;
    }

	/**
	* 获取字段orderSn的值，该字段的<br>
	* 字段名称 :订单号<br>
	* 数据类型 :varchar(25)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getorderSn() {
		return orderSn;
	}

	/**
	* 设置字段orderSn的值，该字段的<br>
	* 字段名称 :订单号<br>
	* 数据类型 :varchar(25)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setorderSn(String orderSn) {
		this.orderSn = orderSn;
    }

	/**
	* 获取字段mobileNo的值，该字段的<br>
	* 字段名称 :兑换手机号<br>
	* 数据类型 :varchar(25)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getmobileNo() {
		return mobileNo;
	}

	/**
	* 设置字段mobileNo的值，该字段的<br>
	* 字段名称 :兑换手机号<br>
	* 数据类型 :varchar(25)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
    }

	/**
	* 获取字段fuLuOrderSn的值，该字段的<br>
	* 字段名称 :福禄订单号<br>
	* 数据类型 :varchar(25)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getfuLuOrderSn() {
		return fuLuOrderSn;
	}

	/**
	* 设置字段fuLuOrderSn的值，该字段的<br>
	* 字段名称 :福禄订单号<br>
	* 数据类型 :varchar(25)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setfuLuOrderSn(String fuLuOrderSn) {
		this.fuLuOrderSn = fuLuOrderSn;
    }

	/**
	* 获取字段fuLuGoodsID的值，该字段的<br>
	* 字段名称 :福禄商品编号<br>
	* 数据类型 :varchar(25)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getfuLuGoodsID() {
		return fuLuGoodsID;
	}

	/**
	* 设置字段fuLuGoodsID的值，该字段的<br>
	* 字段名称 :福禄商品编号<br>
	* 数据类型 :varchar(25)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setfuLuGoodsID(String fuLuGoodsID) {
		this.fuLuGoodsID = fuLuGoodsID;
    }

	/**
	* 获取字段exchangeQuantity的值，该字段的<br>
	* 字段名称 :购买数量<br>
	* 数据类型 :varchar(25)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getexchangeQuantity() {
		return exchangeQuantity;
	}

	/**
	* 设置字段exchangeQuantity的值，该字段的<br>
	* 字段名称 :购买数量<br>
	* 数据类型 :varchar(25)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setexchangeQuantity(String exchangeQuantity) {
		this.exchangeQuantity = exchangeQuantity;
    }

	/**
	* 获取字段cardNo的值，该字段的<br>
	* 字段名称 :卡号<br>
	* 数据类型 :varchar(25)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcardNo() {
		return cardNo;
	}

	/**
	* 设置字段cardNo的值，该字段的<br>
	* 字段名称 :卡号<br>
	* 数据类型 :varchar(25)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcardNo(String cardNo) {
		this.cardNo = cardNo;
    }

	/**
	* 获取字段cardKey的值，该字段的<br>
	* 字段名称 :卡密<br>
	* 数据类型 :varchar(25)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcardKey() {
		return cardKey;
	}

	/**
	* 设置字段cardKey的值，该字段的<br>
	* 字段名称 :卡密<br>
	* 数据类型 :varchar(25)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcardKey(String cardKey) {
		this.cardKey = cardKey;
    }

	/**
	* 获取字段cardDeadline的值，该字段的<br>
	* 字段名称 :有效期限<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcardDeadline() {
		return cardDeadline;
	}

	/**
	* 设置字段cardDeadline的值，该字段的<br>
	* 字段名称 :有效期限<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcardDeadline(String cardDeadline) {
		this.cardDeadline = cardDeadline;
    }

	/**
	* 获取字段purchasePrice的值，该字段的<br>
	* 字段名称 :进价<br>
	* 数据类型 :varchar(25)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getpurchasePrice() {
		return purchasePrice;
	}

	/**
	* 设置字段purchasePrice的值，该字段的<br>
	* 字段名称 :进价<br>
	* 数据类型 :varchar(25)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpurchasePrice(String purchasePrice) {
		this.purchasePrice = purchasePrice;
    }

	/**
	* 获取字段fuLuOrderStatus的值，该字段的<br>
	* 字段名称 :福禄订单状态<br>
	* 数据类型 :varchar(25)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getfuLuOrderStatus() {
		return fuLuOrderStatus;
	}

	/**
	* 设置字段fuLuOrderStatus的值，该字段的<br>
	* 字段名称 :福禄订单状态<br>
	* 数据类型 :varchar(25)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setfuLuOrderStatus(String fuLuOrderStatus) {
		this.fuLuOrderStatus = fuLuOrderStatus;
    }

	/**
	* 获取字段goodsStockID的值，该字段的<br>
	* 字段名称 :库存表ID<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getgoodsStockID() {
		return goodsStockID;
	}

	/**
	* 设置字段goodsStockID的值，该字段的<br>
	* 字段名称 :库存表ID<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setgoodsStockID(String goodsStockID) {
		this.goodsStockID = goodsStockID;
    }

	/**
	* 获取字段giftClassifyID的值，该字段的<br>
	* 字段名称 :礼品表ID<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getgiftClassifyID() {
		return giftClassifyID;
	}

	/**
	* 设置字段giftClassifyID的值，该字段的<br>
	* 字段名称 :礼品表ID<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setgiftClassifyID(String giftClassifyID) {
		this.giftClassifyID = giftClassifyID;
    }

	/**
	* 获取字段wrongMassage的值，该字段的<br>
	* 字段名称 :错误信息<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getwrongMassage() {
		return wrongMassage;
	}

	/**
	* 设置字段wrongMassage的值，该字段的<br>
	* 字段名称 :错误信息<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setwrongMassage(String wrongMassage) {
		this.wrongMassage = wrongMassage;
    }

	/**
	* 获取字段prop1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop1() {
		return prop1;
	}

	/**
	* 设置字段prop1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop1(String prop1) {
		this.prop1 = prop1;
    }

	/**
	* 获取字段prop2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop2() {
		return prop2;
	}

	/**
	* 设置字段prop2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop2(String prop2) {
		this.prop2 = prop2;
    }

	/**
	* 获取字段prop3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop3() {
		return prop3;
	}

	/**
	* 设置字段prop3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop3(String prop3) {
		this.prop3 = prop3;
    }

	/**
	* 获取字段prop4的值，该字段的<br>
	* 字段名称 :备用字段4<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop4() {
		return prop4;
	}

	/**
	* 设置字段prop4的值，该字段的<br>
	* 字段名称 :备用字段4<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop4(String prop4) {
		this.prop4 = prop4;
    }

	/**
	* 获取字段prop5的值，该字段的<br>
	* 字段名称 :备用字段5<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop5() {
		return prop5;
	}

	/**
	* 设置字段prop5的值，该字段的<br>
	* 字段名称 :备用字段5<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop5(String prop5) {
		this.prop5 = prop5;
    }

	/**
	* 获取字段prop6的值，该字段的<br>
	* 字段名称 :备用字段6<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop6() {
		return prop6;
	}

	/**
	* 设置字段prop6的值，该字段的<br>
	* 字段名称 :备用字段6<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop6(String prop6) {
		this.prop6 = prop6;
    }

	/**
	* 获取字段prop7的值，该字段的<br>
	* 字段名称 :备用字段7<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop7() {
		return prop7;
	}

	/**
	* 设置字段prop7的值，该字段的<br>
	* 字段名称 :备用字段7<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop7(String prop7) {
		this.prop7 = prop7;
    }

	/**
	* 获取字段createUser的值，该字段的<br>
	* 字段名称 :创建人<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcreateUser() {
		return createUser;
	}

	/**
	* 设置字段createUser的值，该字段的<br>
	* 字段名称 :创建人<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcreateUser(String createUser) {
		this.createUser = createUser;
    }

	/**
	* 获取字段createDate的值，该字段的<br>
	* 字段名称 :创建日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getcreateDate() {
		return createDate;
	}

	/**
	* 设置字段createDate的值，该字段的<br>
	* 字段名称 :创建日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcreateDate(Date createDate) {
		this.createDate = createDate;
    }

	/**
	* 获取字段modifyUser的值，该字段的<br>
	* 字段名称 :修改人<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getmodifyUser() {
		return modifyUser;
	}

	/**
	* 设置字段modifyUser的值，该字段的<br>
	* 字段名称 :修改人<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmodifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
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

}