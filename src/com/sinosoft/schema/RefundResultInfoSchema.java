package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

import java.util.Date;

import java.util.Date;

import java.util.Date;

import java.util.Date;

/**
 * 表名称：退款结果信息<br>
 * 表代码：RefundResultInfo<br>
 * 表主键：id<br>
 */
public class RefundResultInfoSchema extends Schema {
	private String id;

	private String OrderSn;

	private String PaySn;

	private String RefundAmount;

	private String status;

	private String Result;

	private Date RefundDate;

	private String Payee;

	private String RefundPlatform;

	private String Remark;

	private Date AddTime;

	private String AddUser;

	private Date ModifyTime;

	private String ModifyUser;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 100 , 0 , true , true),
		new SchemaColumn("OrderSn", DataColumn.STRING, 1, 50 , 0 , false , false),
		new SchemaColumn("PaySn", DataColumn.STRING, 2, 50 , 0 , false , false),
		new SchemaColumn("RefundAmount", DataColumn.STRING, 3, 20 , 0 , false , false),
		new SchemaColumn("status", DataColumn.STRING, 4, 20 , 0 , false , false),
		new SchemaColumn("Result", DataColumn.STRING, 5, 50 , 0 , false , false),
		new SchemaColumn("RefundDate", DataColumn.DATETIME, 6, 0 , 0 , false , false),
		new SchemaColumn("Payee", DataColumn.STRING, 7, 50 , 0 , false , false),
		new SchemaColumn("RefundPlatform", DataColumn.STRING, 8, 20 , 0 , false , false),
		new SchemaColumn("Remark", DataColumn.STRING, 9, 256 , 0 , false , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 10, 0 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 11, 100 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 12, 0 , 0 , false , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 13, 100 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 14, 100 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 15, 100 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 16, 100 , 0 , false , false)
	};

	public static final String _TableCode = "RefundResultInfo";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into RefundResultInfo values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update RefundResultInfo set id=?,OrderSn=?,PaySn=?,RefundAmount=?,status=?,Result=?,RefundDate=?,Payee=?,RefundPlatform=?,Remark=?,AddTime=?,AddUser=?,ModifyTime=?,ModifyUser=?,Prop1=?,Prop2=?,Prop3=? where id=?";

	protected static final String _DeleteSQL = "delete from RefundResultInfo  where id=?";

	protected static final String _FillAllSQL = "select * from RefundResultInfo  where id=?";

	public RefundResultInfoSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[17];
	}

	protected Schema newInstance(){
		return new RefundResultInfoSchema();
	}

	protected SchemaSet newSet(){
		return new RefundResultInfoSet();
	}

	public RefundResultInfoSet query() {
		return query(null, -1, -1);
	}

	public RefundResultInfoSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public RefundResultInfoSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public RefundResultInfoSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (RefundResultInfoSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){OrderSn = (String)v;return;}
		if (i == 2){PaySn = (String)v;return;}
		if (i == 3){RefundAmount = (String)v;return;}
		if (i == 4){status = (String)v;return;}
		if (i == 5){Result = (String)v;return;}
		if (i == 6){RefundDate = (Date)v;return;}
		if (i == 7){Payee = (String)v;return;}
		if (i == 8){RefundPlatform = (String)v;return;}
		if (i == 9){Remark = (String)v;return;}
		if (i == 10){AddTime = (Date)v;return;}
		if (i == 11){AddUser = (String)v;return;}
		if (i == 12){ModifyTime = (Date)v;return;}
		if (i == 13){ModifyUser = (String)v;return;}
		if (i == 14){Prop1 = (String)v;return;}
		if (i == 15){Prop2 = (String)v;return;}
		if (i == 16){Prop3 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return OrderSn;}
		if (i == 2){return PaySn;}
		if (i == 3){return RefundAmount;}
		if (i == 4){return status;}
		if (i == 5){return Result;}
		if (i == 6){return RefundDate;}
		if (i == 7){return Payee;}
		if (i == 8){return RefundPlatform;}
		if (i == 9){return Remark;}
		if (i == 10){return AddTime;}
		if (i == 11){return AddUser;}
		if (i == 12){return ModifyTime;}
		if (i == 13){return ModifyUser;}
		if (i == 14){return Prop1;}
		if (i == 15){return Prop2;}
		if (i == 16){return Prop3;}
		return null;
	}

	/**
	* 获取字段id的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getid() {
		return id;
	}

	/**
	* 设置字段id的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setid(String id) {
		this.id = id;
    }

	/**
	* 获取字段OrderSn的值，该字段的<br>
	* 字段名称 :订单号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getOrderSn() {
		return OrderSn;
	}

	/**
	* 设置字段OrderSn的值，该字段的<br>
	* 字段名称 :订单号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setOrderSn(String orderSn) {
		this.OrderSn = orderSn;
    }

	/**
	* 获取字段PaySn的值，该字段的<br>
	* 字段名称 :商户流水号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPaySn() {
		return PaySn;
	}

	/**
	* 设置字段PaySn的值，该字段的<br>
	* 字段名称 :商户流水号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPaySn(String paySn) {
		this.PaySn = paySn;
    }

	/**
	* 获取字段RefundAmount的值，该字段的<br>
	* 字段名称 :退款金额<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getRefundAmount() {
		return RefundAmount;
	}

	/**
	* 设置字段RefundAmount的值，该字段的<br>
	* 字段名称 :退款金额<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRefundAmount(String refundAmount) {
		this.RefundAmount = refundAmount;
    }

	/**
	* 获取字段status的值，该字段的<br>
	* 字段名称 :退款状态<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getstatus() {
		return status;
	}

	/**
	* 设置字段status的值，该字段的<br>
	* 字段名称 :退款状态<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setstatus(String status) {
		this.status = status;
    }

	/**
	* 获取字段Result的值，该字段的<br>
	* 字段名称 :处理结果<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getResult() {
		return Result;
	}

	/**
	* 设置字段Result的值，该字段的<br>
	* 字段名称 :处理结果<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setResult(String result) {
		this.Result = result;
    }

	/**
	* 获取字段RefundDate的值，该字段的<br>
	* 字段名称 :退款日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getRefundDate() {
		return RefundDate;
	}

	/**
	* 设置字段RefundDate的值，该字段的<br>
	* 字段名称 :退款日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRefundDate(Date refundDate) {
		this.RefundDate = refundDate;
    }

	/**
	* 获取字段Payee的值，该字段的<br>
	* 字段名称 :收款方<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPayee() {
		return Payee;
	}

	/**
	* 设置字段Payee的值，该字段的<br>
	* 字段名称 :收款方<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPayee(String payee) {
		this.Payee = payee;
    }

	/**
	* 获取字段RefundPlatform的值，该字段的<br>
	* 字段名称 :退款平台<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getRefundPlatform() {
		return RefundPlatform;
	}

	/**
	* 设置字段RefundPlatform的值，该字段的<br>
	* 字段名称 :退款平台<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRefundPlatform(String refundPlatform) {
		this.RefundPlatform = refundPlatform;
    }

	/**
	* 获取字段Remark的值，该字段的<br>
	* 字段名称 :备注<br>
	* 数据类型 :varchar(256)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getRemark() {
		return Remark;
	}

	/**
	* 设置字段Remark的值，该字段的<br>
	* 字段名称 :备注<br>
	* 数据类型 :varchar(256)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRemark(String remark) {
		this.Remark = remark;
    }

	/**
	* 获取字段AddTime的值，该字段的<br>
	* 字段名称 :AddTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getAddTime() {
		return AddTime;
	}

	/**
	* 设置字段AddTime的值，该字段的<br>
	* 字段名称 :AddTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAddTime(Date addTime) {
		this.AddTime = addTime;
    }

	/**
	* 获取字段AddUser的值，该字段的<br>
	* 字段名称 :AddUser<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAddUser() {
		return AddUser;
	}

	/**
	* 设置字段AddUser的值，该字段的<br>
	* 字段名称 :AddUser<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAddUser(String addUser) {
		this.AddUser = addUser;
    }

	/**
	* 获取字段ModifyTime的值，该字段的<br>
	* 字段名称 :ModifyTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getModifyTime() {
		return ModifyTime;
	}

	/**
	* 设置字段ModifyTime的值，该字段的<br>
	* 字段名称 :ModifyTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyTime(Date modifyTime) {
		this.ModifyTime = modifyTime;
    }

	/**
	* 获取字段ModifyUser的值，该字段的<br>
	* 字段名称 :ModifyUser<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getModifyUser() {
		return ModifyUser;
	}

	/**
	* 设置字段ModifyUser的值，该字段的<br>
	* 字段名称 :ModifyUser<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyUser(String modifyUser) {
		this.ModifyUser = modifyUser;
    }

	/**
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :备用1<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :备用1<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* 获取字段Prop2的值，该字段的<br>
	* 字段名称 :备用2<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* 设置字段Prop2的值，该字段的<br>
	* 字段名称 :备用2<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

	/**
	* 获取字段Prop3的值，该字段的<br>
	* 字段名称 :备用3<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp3() {
		return Prop3;
	}

	/**
	* 设置字段Prop3的值，该字段的<br>
	* 字段名称 :备用3<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp3(String prop3) {
		this.Prop3 = prop3;
    }

}