package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：手动发起退款<br>
 * 表代码：InitiateRefund<br>
 * 表主键：id<br>
 */
public class InitiateRefundSchema extends Schema {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1828479042689132244L;

	private String id;

	private String RefundType;

	private String OrderSn;

	private String PolicyNo;

	private String RefundAmount;

	private String Approval;

	private Date ApprovalTime;

	private String Remark;

	private Date AddTime;

	private String AddUser;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 100 , 0 , true , true),
		new SchemaColumn("RefundType", DataColumn.STRING, 1, 10 , 0 , false , false),
		new SchemaColumn("OrderSn", DataColumn.STRING, 2, 50 , 0 , false , false),
		new SchemaColumn("PolicyNo", DataColumn.STRING, 3, 50 , 0 , false , false),
		new SchemaColumn("RefundAmount", DataColumn.STRING, 4, 20 , 0 , false , false),
		new SchemaColumn("Approval", DataColumn.STRING, 5, 1 , 0 , false , false),
		new SchemaColumn("ApprovalTime", DataColumn.DATETIME, 6, 0 , 0 , false , false),
		new SchemaColumn("Remark", DataColumn.STRING, 7, 256 , 0 , false , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 8, 0 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 9, 100 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 10, 100 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 11, 100 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 12, 100 , 0 , false , false)
	};

	public static final String _TableCode = "InitiateRefund";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into InitiateRefund values(?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update InitiateRefund set id=?,RefundType=?,OrderSn=?,PolicyNo=?,RefundAmount=?,Approval=?,ApprovalTime=?,Remark=?,AddTime=?,AddUser=?,Prop1=?,Prop2=?,Prop3=? where id=?";

	protected static final String _DeleteSQL = "delete from InitiateRefund  where id=?";

	protected static final String _FillAllSQL = "select * from InitiateRefund  where id=?";

	public InitiateRefundSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[13];
	}

	protected Schema newInstance(){
		return new InitiateRefundSchema();
	}

	protected SchemaSet newSet(){
		return new InitiateRefundSet();
	}

	public InitiateRefundSet query() {
		return query(null, -1, -1);
	}

	public InitiateRefundSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public InitiateRefundSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public InitiateRefundSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (InitiateRefundSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){RefundType = (String)v;return;}
		if (i == 2){OrderSn = (String)v;return;}
		if (i == 3){PolicyNo = (String)v;return;}
		if (i == 4){RefundAmount = (String)v;return;}
		if (i == 5){Approval = (String)v;return;}
		if (i == 6){ApprovalTime = (Date)v;return;}
		if (i == 7){Remark = (String)v;return;}
		if (i == 8){AddTime = (Date)v;return;}
		if (i == 9){AddUser = (String)v;return;}
		if (i == 10){Prop1 = (String)v;return;}
		if (i == 11){Prop2 = (String)v;return;}
		if (i == 12){Prop3 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return RefundType;}
		if (i == 2){return OrderSn;}
		if (i == 3){return PolicyNo;}
		if (i == 4){return RefundAmount;}
		if (i == 5){return Approval;}
		if (i == 6){return ApprovalTime;}
		if (i == 7){return Remark;}
		if (i == 8){return AddTime;}
		if (i == 9){return AddUser;}
		if (i == 10){return Prop1;}
		if (i == 11){return Prop2;}
		if (i == 12){return Prop3;}
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
	* 获取字段RefundType的值，该字段的<br>
	* 字段名称 :返现类型<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getRefundType() {
		return RefundType;
	}

	/**
	* 设置字段RefundType的值，该字段的<br>
	* 字段名称 :返现类型<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRefundType(String refundType) {
		this.RefundType = refundType;
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
	* 获取字段PolicyNo的值，该字段的<br>
	* 字段名称 :保单号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPolicyNo() {
		return PolicyNo;
	}

	/**
	* 设置字段PolicyNo的值，该字段的<br>
	* 字段名称 :保单号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPolicyNo(String policyNo) {
		this.PolicyNo = policyNo;
    }

	/**
	* 获取字段RefundAmount的值，该字段的<br>
	* 字段名称 :返现金额<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getRefundAmount() {
		return RefundAmount;
	}

	/**
	* 设置字段RefundAmount的值，该字段的<br>
	* 字段名称 :返现金额<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRefundAmount(String refundAmount) {
		this.RefundAmount = refundAmount;
    }

	/**
	* 获取字段Approval的值，该字段的<br>
	* 字段名称 :审批<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getApproval() {
		return Approval;
	}

	/**
	* 设置字段Approval的值，该字段的<br>
	* 字段名称 :审批<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setApproval(String approval) {
		this.Approval = approval;
    }

	/**
	* 获取字段ApprovalTime的值，该字段的<br>
	* 字段名称 :审批时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getApprovalTime() {
		return ApprovalTime;
	}

	/**
	* 设置字段ApprovalTime的值，该字段的<br>
	* 字段名称 :审批时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setApprovalTime(Date approvalTime) {
		this.ApprovalTime = approvalTime;
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