package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：活动发送优惠券记录表<br>
 * 表代码：ActivitySendCouponLog<br>
 * 表主键：id<br>
 */
public class ActivitySendCouponLogSchema extends Schema {
	private String id;

	private String activitySn;

	private String batchs;

	private String couponSns;

	private String memberId;

	private String paySn;

	private String orderSns;

	private String channelSn;

	private Date createDate;

	private String prop1;

	private String prop2;

	private String prop3;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("activitySn", DataColumn.STRING, 1, 50 , 0 , true , false),
		new SchemaColumn("batchs", DataColumn.STRING, 2, 255 , 0 , false , false),
		new SchemaColumn("couponSns", DataColumn.STRING, 3, 2000 , 0 , false , false),
		new SchemaColumn("memberId", DataColumn.STRING, 4, 32 , 0 , true , false),
		new SchemaColumn("paySn", DataColumn.STRING, 5, 100 , 0 , false , false),
		new SchemaColumn("orderSns", DataColumn.STRING, 6, 255 , 0 , false , false),
		new SchemaColumn("channelSn", DataColumn.STRING, 7, 255 , 0 , false , false),
		new SchemaColumn("createDate", DataColumn.DATETIME, 8, 0 , 0 , false , false),
		new SchemaColumn("prop1", DataColumn.STRING, 9, 255 , 0 , false , false),
		new SchemaColumn("prop2", DataColumn.STRING, 10, 255 , 0 , false , false),
		new SchemaColumn("prop3", DataColumn.STRING, 11, 255 , 0 , false , false)
	};

	public static final String _TableCode = "ActivitySendCouponLog";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into ActivitySendCouponLog values(?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ActivitySendCouponLog set id=?,activitySn=?,batchs=?,couponSns=?,memberId=?,paySn=?,orderSns=?,channelSn=?,createDate=?,prop1=?,prop2=?,prop3=? where id=?";

	protected static final String _DeleteSQL = "delete from ActivitySendCouponLog  where id=?";

	protected static final String _FillAllSQL = "select * from ActivitySendCouponLog  where id=?";

	public ActivitySendCouponLogSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[12];
	}

	protected Schema newInstance(){
		return new ActivitySendCouponLogSchema();
	}

	protected SchemaSet newSet(){
		return new ActivitySendCouponLogSet();
	}

	public ActivitySendCouponLogSet query() {
		return query(null, -1, -1);
	}

	public ActivitySendCouponLogSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ActivitySendCouponLogSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ActivitySendCouponLogSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ActivitySendCouponLogSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){activitySn = (String)v;return;}
		if (i == 2){batchs = (String)v;return;}
		if (i == 3){couponSns = (String)v;return;}
		if (i == 4){memberId = (String)v;return;}
		if (i == 5){paySn = (String)v;return;}
		if (i == 6){orderSns = (String)v;return;}
		if (i == 7){channelSn = (String)v;return;}
		if (i == 8){createDate = (Date)v;return;}
		if (i == 9){prop1 = (String)v;return;}
		if (i == 10){prop2 = (String)v;return;}
		if (i == 11){prop3 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return activitySn;}
		if (i == 2){return batchs;}
		if (i == 3){return couponSns;}
		if (i == 4){return memberId;}
		if (i == 5){return paySn;}
		if (i == 6){return orderSns;}
		if (i == 7){return channelSn;}
		if (i == 8){return createDate;}
		if (i == 9){return prop1;}
		if (i == 10){return prop2;}
		if (i == 11){return prop3;}
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
	* 获取字段activitySn的值，该字段的<br>
	* 字段名称 :活动编码<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getactivitySn() {
		return activitySn;
	}

	/**
	* 设置字段activitySn的值，该字段的<br>
	* 字段名称 :活动编码<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setactivitySn(String activitySn) {
		this.activitySn = activitySn;
    }

	/**
	* 获取字段batchs的值，该字段的<br>
	* 字段名称 :优惠券批次<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getbatchs() {
		return batchs;
	}

	/**
	* 设置字段batchs的值，该字段的<br>
	* 字段名称 :优惠券批次<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setbatchs(String batchs) {
		this.batchs = batchs;
    }

	/**
	* 获取字段couponSns的值，该字段的<br>
	* 字段名称 :优惠券编码<br>
	* 数据类型 :varchar(2000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcouponSns() {
		return couponSns;
	}

	/**
	* 设置字段couponSns的值，该字段的<br>
	* 字段名称 :优惠券编码<br>
	* 数据类型 :varchar(2000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcouponSns(String couponSns) {
		this.couponSns = couponSns;
    }

	/**
	* 获取字段memberId的值，该字段的<br>
	* 字段名称 :会员ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getmemberId() {
		return memberId;
	}

	/**
	* 设置字段memberId的值，该字段的<br>
	* 字段名称 :会员ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setmemberId(String memberId) {
		this.memberId = memberId;
    }

	/**
	* 获取字段paySn的值，该字段的<br>
	* 字段名称 :商家订单号<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getpaySn() {
		return paySn;
	}

	/**
	* 设置字段paySn的值，该字段的<br>
	* 字段名称 :商家订单号<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpaySn(String paySn) {
		this.paySn = paySn;
    }

	/**
	* 获取字段orderSns的值，该字段的<br>
	* 字段名称 :订单号<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getorderSns() {
		return orderSns;
	}

	/**
	* 设置字段orderSns的值，该字段的<br>
	* 字段名称 :订单号<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setorderSns(String orderSns) {
		this.orderSns = orderSns;
    }

	/**
	* 获取字段channelSn的值，该字段的<br>
	* 字段名称 :订单渠道<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getchannelSn() {
		return channelSn;
	}

	/**
	* 设置字段channelSn的值，该字段的<br>
	* 字段名称 :订单渠道<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setchannelSn(String channelSn) {
		this.channelSn = channelSn;
    }

	/**
	* 获取字段createDate的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getcreateDate() {
		return createDate;
	}

	/**
	* 设置字段createDate的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcreateDate(Date createDate) {
		this.createDate = createDate;
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

}