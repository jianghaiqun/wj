package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：交易流水信息表<br>
 * 表代码：TradeInfo<br>
 * 表主键：paySn<br>
 */
public class TradeInfoSchema extends Schema {
	private String paySn;

	private String orderSn;

	private String couponSn;

	private String totalAmnout;

	private String integral;

	private String payType;

	private String remark1;

	private String remark2;

	private Date CreateDate;

	private Date ModifyDate;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("paySn", DataColumn.STRING, 0, 30 , 0 , true , true),
		new SchemaColumn("orderSn", DataColumn.STRING, 1, 2000 , 0 , false , false),
		new SchemaColumn("couponSn", DataColumn.STRING, 2, 50 , 0 , false , false),
		new SchemaColumn("totalAmnout", DataColumn.STRING, 3, 50 , 0 , false , false),
		new SchemaColumn("integral", DataColumn.STRING, 4, 10 , 0 , false , false),
		new SchemaColumn("payType", DataColumn.STRING, 5, 255 , 0 , false , false),
		new SchemaColumn("remark1", DataColumn.STRING, 6, 255 , 0 , false , false),
		new SchemaColumn("remark2", DataColumn.STRING, 7, 255 , 0 , false , false),
		new SchemaColumn("CreateDate", DataColumn.DATETIME, 8, 0 , 0 , false , false),
		new SchemaColumn("ModifyDate", DataColumn.DATETIME, 9, 0 , 0 , false , false)
	};

	public static final String _TableCode = "TradeInfo";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into TradeInfo values(?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update TradeInfo set paySn=?,orderSn=?,couponSn=?,totalAmnout=?,integral=?,payType=?,remark1=?,remark2=?,CreateDate=?,ModifyDate=? where paySn=?";

	protected static final String _DeleteSQL = "delete from TradeInfo  where paySn=?";

	protected static final String _FillAllSQL = "select * from TradeInfo  where paySn=?";

	public TradeInfoSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[10];
	}

	protected Schema newInstance(){
		return new TradeInfoSchema();
	}

	protected SchemaSet newSet(){
		return new TradeInfoSet();
	}

	public TradeInfoSet query() {
		return query(null, -1, -1);
	}

	public TradeInfoSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public TradeInfoSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public TradeInfoSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (TradeInfoSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){paySn = (String)v;return;}
		if (i == 1){orderSn = (String)v;return;}
		if (i == 2){couponSn = (String)v;return;}
		if (i == 3){totalAmnout = (String)v;return;}
		if (i == 4){integral = (String)v;return;}
		if (i == 5){payType = (String)v;return;}
		if (i == 6){remark1 = (String)v;return;}
		if (i == 7){remark2 = (String)v;return;}
		if (i == 8){CreateDate = (Date)v;return;}
		if (i == 9){ModifyDate = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return paySn;}
		if (i == 1){return orderSn;}
		if (i == 2){return couponSn;}
		if (i == 3){return totalAmnout;}
		if (i == 4){return integral;}
		if (i == 5){return payType;}
		if (i == 6){return remark1;}
		if (i == 7){return remark2;}
		if (i == 8){return CreateDate;}
		if (i == 9){return ModifyDate;}
		return null;
	}

	/**
	* 获取字段paySn的值，该字段的<br>
	* 字段名称 :交流流水号<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getpaySn() {
		return paySn;
	}

	/**
	* 设置字段paySn的值，该字段的<br>
	* 字段名称 :交流流水号<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setpaySn(String paySn) {
		this.paySn = paySn;
    }

	/**
	* 获取字段orderSn的值，该字段的<br>
	* 字段名称 :订单号数组<br>
	* 数据类型 :varchar(2000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getorderSn() {
		return orderSn;
	}

	/**
	* 设置字段orderSn的值，该字段的<br>
	* 字段名称 :订单号数组<br>
	* 数据类型 :varchar(2000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setorderSn(String orderSn) {
		this.orderSn = orderSn;
    }

	/**
	* 获取字段couponSn的值，该字段的<br>
	* 字段名称 :优惠券号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcouponSn() {
		return couponSn;
	}

	/**
	* 设置字段couponSn的值，该字段的<br>
	* 字段名称 :优惠券号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcouponSn(String couponSn) {
		this.couponSn = couponSn;
    }

	/**
	* 获取字段totalAmnout的值，该字段的<br>
	* 字段名称 :订单总金额<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String gettotalAmnout() {
		return totalAmnout;
	}

	/**
	* 设置字段totalAmnout的值，该字段的<br>
	* 字段名称 :订单总金额<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void settotalAmnout(String totalAmnout) {
		this.totalAmnout = totalAmnout;
    }

	/**
	* 获取字段integral的值，该字段的<br>
	* 字段名称 :使用积分<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getintegral() {
		return integral;
	}

	/**
	* 设置字段integral的值，该字段的<br>
	* 字段名称 :使用积分<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setintegral(String integral) {
		this.integral = integral;
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
	* 获取字段remark1的值，该字段的<br>
	* 字段名称 :支付情况说明<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getremark1() {
		return remark1;
	}

	/**
	* 设置字段remark1的值，该字段的<br>
	* 字段名称 :支付情况说明<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setremark1(String remark1) {
		this.remark1 = remark1;
    }

	/**
	* 获取字段remark2的值，该字段的<br>
	* 字段名称 :活动号<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getremark2() {
		return remark2;
	}

	/**
	* 设置字段remark2的值，该字段的<br>
	* 字段名称 :活动号<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setremark2(String remark2) {
		this.remark2 = remark2;
    }

	/**
	* 获取字段CreateDate的值，该字段的<br>
	* 字段名称 :创建日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getCreateDate() {
		return CreateDate;
	}

	/**
	* 设置字段CreateDate的值，该字段的<br>
	* 字段名称 :创建日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCreateDate(Date createDate) {
		this.CreateDate = createDate;
    }

	/**
	* 获取字段ModifyDate的值，该字段的<br>
	* 字段名称 :修改日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getModifyDate() {
		return ModifyDate;
	}

	/**
	* 设置字段ModifyDate的值，该字段的<br>
	* 字段名称 :修改日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyDate(Date modifyDate) {
		this.ModifyDate = modifyDate;
    }

}