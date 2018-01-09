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
 * 表名称：订单信息表<br>
 * 表代码：OtOrderInfo<br>
 * 表主键：id<br>
 */
public class OtOrderInfoSchema extends Schema {
	private String id;

	private String orderSn;

	private Integer orderStatus;

	private String payType;

	private Integer payStatus;

	private String totalPrice;

	private Integer travelNum;

	private String tradeSeriNO;

	private String memberId;

	private String productId;

	private String productName;

	private String productType;

	private String productPrice;

	private Date travelDate;

	private String birthland;

	private String destination;

	private String contactName;

	private String contactPhone;

	private String contactEmail;

	private String comment;

	private String prop1;

	private String prop2;

	private String prop3;

	private Date createDate;

	private Date modifyDate;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("orderSn", DataColumn.STRING, 1, 20 , 0 , true , false),
		new SchemaColumn("orderStatus", DataColumn.INTEGER, 2, 11 , 0 , false , false),
		new SchemaColumn("payType", DataColumn.STRING, 3, 10 , 0 , false , false),
		new SchemaColumn("payStatus", DataColumn.INTEGER, 4, 11 , 0 , false , false),
		new SchemaColumn("totalPrice", DataColumn.STRING, 5, 20 , 0 , false , false),
		new SchemaColumn("travelNum", DataColumn.INTEGER, 6, 11 , 0 , false , false),
		new SchemaColumn("tradeSeriNO", DataColumn.STRING, 7, 255 , 0 , false , false),
		new SchemaColumn("memberId", DataColumn.STRING, 8, 32 , 0 , false , false),
		new SchemaColumn("productId", DataColumn.STRING, 9, 32 , 0 , false , false),
		new SchemaColumn("productName", DataColumn.STRING, 10, 255 , 0 , false , false),
		new SchemaColumn("productType", DataColumn.STRING, 11, 2 , 0 , false , false),
		new SchemaColumn("productPrice", DataColumn.STRING, 12, 20 , 0 , false , false),
		new SchemaColumn("travelDate", DataColumn.DATETIME, 13, 0 , 0 , false , false),
		new SchemaColumn("birthland", DataColumn.STRING, 14, 255 , 0 , false , false),
		new SchemaColumn("destination", DataColumn.STRING, 15, 255 , 0 , false , false),
		new SchemaColumn("contactName", DataColumn.STRING, 16, 100 , 0 , false , false),
		new SchemaColumn("contactPhone", DataColumn.STRING, 17, 20 , 0 , false , false),
		new SchemaColumn("contactEmail", DataColumn.STRING, 18, 100 , 0 , false , false),
		new SchemaColumn("comment", DataColumn.CLOB, 19, 0 , 0 , false , false),
		new SchemaColumn("prop1", DataColumn.STRING, 20, 255 , 0 , false , false),
		new SchemaColumn("prop2", DataColumn.STRING, 21, 255 , 0 , false , false),
		new SchemaColumn("prop3", DataColumn.STRING, 22, 255 , 0 , false , false),
		new SchemaColumn("createDate", DataColumn.DATETIME, 23, 0 , 0 , false , false),
		new SchemaColumn("modifyDate", DataColumn.DATETIME, 24, 0 , 0 , false , false)
	};

	public static final String _TableCode = "OtOrderInfo";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into OtOrderInfo values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update OtOrderInfo set id=?,orderSn=?,orderStatus=?,payType=?,payStatus=?,totalPrice=?,travelNum=?,tradeSeriNO=?,memberId=?,productId=?,productName=?,productType=?,productPrice=?,travelDate=?,birthland=?,destination=?,contactName=?,contactPhone=?,contactEmail=?,comment=?,prop1=?,prop2=?,prop3=?,createDate=?,modifyDate=? where id=?";

	protected static final String _DeleteSQL = "delete from OtOrderInfo  where id=?";

	protected static final String _FillAllSQL = "select * from OtOrderInfo  where id=?";

	public OtOrderInfoSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[25];
	}

	protected Schema newInstance(){
		return new OtOrderInfoSchema();
	}

	protected SchemaSet newSet(){
		return new OtOrderInfoSet();
	}

	public OtOrderInfoSet query() {
		return query(null, -1, -1);
	}

	public OtOrderInfoSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public OtOrderInfoSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public OtOrderInfoSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (OtOrderInfoSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){orderSn = (String)v;return;}
		if (i == 2){if(v==null){orderStatus = null;}else{orderStatus = new Integer(v.toString());}return;}
		if (i == 3){payType = (String)v;return;}
		if (i == 4){if(v==null){payStatus = null;}else{payStatus = new Integer(v.toString());}return;}
		if (i == 5){totalPrice = (String)v;return;}
		if (i == 6){if(v==null){travelNum = null;}else{travelNum = new Integer(v.toString());}return;}
		if (i == 7){tradeSeriNO = (String)v;return;}
		if (i == 8){memberId = (String)v;return;}
		if (i == 9){productId = (String)v;return;}
		if (i == 10){productName = (String)v;return;}
		if (i == 11){productType = (String)v;return;}
		if (i == 12){productPrice = (String)v;return;}
		if (i == 13){travelDate = (Date)v;return;}
		if (i == 14){birthland = (String)v;return;}
		if (i == 15){destination = (String)v;return;}
		if (i == 16){contactName = (String)v;return;}
		if (i == 17){contactPhone = (String)v;return;}
		if (i == 18){contactEmail = (String)v;return;}
		if (i == 19){comment = (String)v;return;}
		if (i == 20){prop1 = (String)v;return;}
		if (i == 21){prop2 = (String)v;return;}
		if (i == 22){prop3 = (String)v;return;}
		if (i == 23){createDate = (Date)v;return;}
		if (i == 24){modifyDate = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return orderSn;}
		if (i == 2){return orderStatus;}
		if (i == 3){return payType;}
		if (i == 4){return payStatus;}
		if (i == 5){return totalPrice;}
		if (i == 6){return travelNum;}
		if (i == 7){return tradeSeriNO;}
		if (i == 8){return memberId;}
		if (i == 9){return productId;}
		if (i == 10){return productName;}
		if (i == 11){return productType;}
		if (i == 12){return productPrice;}
		if (i == 13){return travelDate;}
		if (i == 14){return birthland;}
		if (i == 15){return destination;}
		if (i == 16){return contactName;}
		if (i == 17){return contactPhone;}
		if (i == 18){return contactEmail;}
		if (i == 19){return comment;}
		if (i == 20){return prop1;}
		if (i == 21){return prop2;}
		if (i == 22){return prop3;}
		if (i == 23){return createDate;}
		if (i == 24){return modifyDate;}
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
	* 获取字段orderSn的值，该字段的<br>
	* 字段名称 :订单编码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getorderSn() {
		return orderSn;
	}

	/**
	* 设置字段orderSn的值，该字段的<br>
	* 字段名称 :订单编码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setorderSn(String orderSn) {
		this.orderSn = orderSn;
    }

	/**
	* 获取字段orderStatus的值，该字段的<br>
	* 字段名称 :订单状态<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
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
	* 是否必填 :false<br>
	*/
	public void setorderStatus(int orderStatus) {
		this.orderStatus = new Integer(orderStatus);
    }

	/**
	* 设置字段orderStatus的值，该字段的<br>
	* 字段名称 :订单状态<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
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
	* 字段名称 :支付方式<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getpayType() {
		return payType;
	}

	/**
	* 设置字段payType的值，该字段的<br>
	* 字段名称 :支付方式<br>
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
	* 获取字段totalPrice的值，该字段的<br>
	* 字段名称 :订单价格<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String gettotalPrice() {
		return totalPrice;
	}

	/**
	* 设置字段totalPrice的值，该字段的<br>
	* 字段名称 :订单价格<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void settotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
    }

	/**
	* 获取字段travelNum的值，该字段的<br>
	* 字段名称 :出行人数<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int gettravelNum() {
		if(travelNum==null){return 0;}
		return travelNum.intValue();
	}

	/**
	* 设置字段travelNum的值，该字段的<br>
	* 字段名称 :出行人数<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void settravelNum(int travelNum) {
		this.travelNum = new Integer(travelNum);
    }

	/**
	* 设置字段travelNum的值，该字段的<br>
	* 字段名称 :出行人数<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void settravelNum(String travelNum) {
		if (travelNum == null){
			this.travelNum = null;
			return;
		}
		this.travelNum = new Integer(travelNum);
    }

	/**
	* 获取字段tradeSeriNO的值，该字段的<br>
	* 字段名称 :交易流水号<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String gettradeSeriNO() {
		return tradeSeriNO;
	}

	/**
	* 设置字段tradeSeriNO的值，该字段的<br>
	* 字段名称 :交易流水号<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void settradeSeriNO(String tradeSeriNO) {
		this.tradeSeriNO = tradeSeriNO;
    }

	/**
	* 获取字段memberId的值，该字段的<br>
	* 字段名称 :会员ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getmemberId() {
		return memberId;
	}

	/**
	* 设置字段memberId的值，该字段的<br>
	* 字段名称 :会员ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmemberId(String memberId) {
		this.memberId = memberId;
    }

	/**
	* 获取字段productId的值，该字段的<br>
	* 字段名称 :产品编码<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getproductId() {
		return productId;
	}

	/**
	* 设置字段productId的值，该字段的<br>
	* 字段名称 :产品编码<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setproductId(String productId) {
		this.productId = productId;
    }

	/**
	* 获取字段productName的值，该字段的<br>
	* 字段名称 :产品名称<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getproductName() {
		return productName;
	}

	/**
	* 设置字段productName的值，该字段的<br>
	* 字段名称 :产品名称<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setproductName(String productName) {
		this.productName = productName;
    }

	/**
	* 获取字段productType的值，该字段的<br>
	* 字段名称 :产品类型<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getproductType() {
		return productType;
	}

	/**
	* 设置字段productType的值，该字段的<br>
	* 字段名称 :产品类型<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setproductType(String productType) {
		this.productType = productType;
    }

	/**
	* 获取字段productPrice的值，该字段的<br>
	* 字段名称 :产品单价<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getproductPrice() {
		return productPrice;
	}

	/**
	* 设置字段productPrice的值，该字段的<br>
	* 字段名称 :产品单价<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setproductPrice(String productPrice) {
		this.productPrice = productPrice;
    }

	/**
	* 获取字段travelDate的值，该字段的<br>
	* 字段名称 :出发日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date gettravelDate() {
		return travelDate;
	}

	/**
	* 设置字段travelDate的值，该字段的<br>
	* 字段名称 :出发日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void settravelDate(Date travelDate) {
		this.travelDate = travelDate;
    }

	/**
	* 获取字段birthland的值，该字段的<br>
	* 字段名称 :出发地<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getbirthland() {
		return birthland;
	}

	/**
	* 设置字段birthland的值，该字段的<br>
	* 字段名称 :出发地<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setbirthland(String birthland) {
		this.birthland = birthland;
    }

	/**
	* 获取字段destination的值，该字段的<br>
	* 字段名称 :目的地<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getdestination() {
		return destination;
	}

	/**
	* 设置字段destination的值，该字段的<br>
	* 字段名称 :目的地<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setdestination(String destination) {
		this.destination = destination;
    }

	/**
	* 获取字段contactName的值，该字段的<br>
	* 字段名称 :联系人姓名<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcontactName() {
		return contactName;
	}

	/**
	* 设置字段contactName的值，该字段的<br>
	* 字段名称 :联系人姓名<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcontactName(String contactName) {
		this.contactName = contactName;
    }

	/**
	* 获取字段contactPhone的值，该字段的<br>
	* 字段名称 :联系人电话<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcontactPhone() {
		return contactPhone;
	}

	/**
	* 设置字段contactPhone的值，该字段的<br>
	* 字段名称 :联系人电话<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcontactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
    }

	/**
	* 获取字段contactEmail的值，该字段的<br>
	* 字段名称 :联系人邮箱<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcontactEmail() {
		return contactEmail;
	}

	/**
	* 设置字段contactEmail的值，该字段的<br>
	* 字段名称 :联系人邮箱<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcontactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
    }

	/**
	* 获取字段comment的值，该字段的<br>
	* 字段名称 :备注<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcomment() {
		return comment;
	}

	/**
	* 设置字段comment的值，该字段的<br>
	* 字段名称 :备注<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcomment(String comment) {
		this.comment = comment;
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