package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：合作方退保申请<br>
 * 表代码：PartnerRefundApply<br>
 * 表主键：id<br>
 */
public class PartnerRefundApplySchema extends Schema {
	private String id;

	private String POrderSn;

	private String SerialNo;

	private String RiskCode;

	private String ApplyType;

	private Date ApplyDate;

	private String CancelResult;

	private String CancelFailReason;

	private Date CreateDate;
	//channelsn
	private String Prop1;

	private String Prop2;

	private String Prop3;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("POrderSn", DataColumn.STRING, 1, 32 , 0 , false , false),
		new SchemaColumn("SerialNo", DataColumn.STRING, 2, 32 , 0 , false , false),
		new SchemaColumn("RiskCode", DataColumn.STRING, 3, 32 , 0 , false , false),
		new SchemaColumn("ApplyType", DataColumn.STRING, 4, 32 , 0 , false , false),
		new SchemaColumn("ApplyDate", DataColumn.DATETIME, 5, 0 , 0 , false , false),
		new SchemaColumn("CancelResult", DataColumn.STRING, 6, 1 , 0 , false , false),
		new SchemaColumn("CancelFailReason", DataColumn.STRING, 7, 255 , 0 , false , false),
		new SchemaColumn("CreateDate", DataColumn.DATETIME, 8, 0 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 9, 50 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 10, 50 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 11, 100 , 0 , false , false)
	};

	public static final String _TableCode = "PartnerRefundApply";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into PartnerRefundApply values(?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update PartnerRefundApply set id=?,POrderSn=?,SerialNo=?,RiskCode=?,ApplyType=?,ApplyDate=?,CancelResult=?,CancelFailReason=?,CreateDate=?,Prop1=?,Prop2=?,Prop3=? where id=?";

	protected static final String _DeleteSQL = "delete from PartnerRefundApply  where id=?";

	protected static final String _FillAllSQL = "select * from PartnerRefundApply  where id=?";

	public PartnerRefundApplySchema(){
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
		return new PartnerRefundApplySchema();
	}

	protected SchemaSet newSet(){
		return new PartnerRefundApplySet();
	}

	public PartnerRefundApplySet query() {
		return query(null, -1, -1);
	}

	public PartnerRefundApplySet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public PartnerRefundApplySet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public PartnerRefundApplySet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (PartnerRefundApplySet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){POrderSn = (String)v;return;}
		if (i == 2){SerialNo = (String)v;return;}
		if (i == 3){RiskCode = (String)v;return;}
		if (i == 4){ApplyType = (String)v;return;}
		if (i == 5){ApplyDate = (Date)v;return;}
		if (i == 6){CancelResult = (String)v;return;}
		if (i == 7){CancelFailReason = (String)v;return;}
		if (i == 8){CreateDate = (Date)v;return;}
		if (i == 9){Prop1 = (String)v;return;}
		if (i == 10){Prop2 = (String)v;return;}
		if (i == 11){Prop3 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return POrderSn;}
		if (i == 2){return SerialNo;}
		if (i == 3){return RiskCode;}
		if (i == 4){return ApplyType;}
		if (i == 5){return ApplyDate;}
		if (i == 6){return CancelResult;}
		if (i == 7){return CancelFailReason;}
		if (i == 8){return CreateDate;}
		if (i == 9){return Prop1;}
		if (i == 10){return Prop2;}
		if (i == 11){return Prop3;}
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
	* 获取字段POrderSn的值，该字段的<br>
	* 字段名称 :POrderSn<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPOrderSn() {
		return POrderSn;
	}

	/**
	* 设置字段POrderSn的值，该字段的<br>
	* 字段名称 :POrderSn<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPOrderSn(String pOrderSn) {
		this.POrderSn = pOrderSn;
    }

	/**
	* 获取字段SerialNo的值，该字段的<br>
	* 字段名称 :SerialNo<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSerialNo() {
		return SerialNo;
	}

	/**
	* 设置字段SerialNo的值，该字段的<br>
	* 字段名称 :SerialNo<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSerialNo(String serialNo) {
		this.SerialNo = serialNo;
    }

	/**
	* 获取字段RiskCode的值，该字段的<br>
	* 字段名称 :RiskCode<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getRiskCode() {
		return RiskCode;
	}

	/**
	* 设置字段RiskCode的值，该字段的<br>
	* 字段名称 :RiskCode<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRiskCode(String riskCode) {
		this.RiskCode = riskCode;
    }

	/**
	* 获取字段ApplyType的值，该字段的<br>
	* 字段名称 :ApplyType<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getApplyType() {
		return ApplyType;
	}

	/**
	* 设置字段ApplyType的值，该字段的<br>
	* 字段名称 :ApplyType<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setApplyType(String applyType) {
		this.ApplyType = applyType;
    }

	/**
	* 获取字段ApplyDate的值，该字段的<br>
	* 字段名称 :ApplyDate<br>
	* 数据类型 :Datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getApplyDate() {
		return ApplyDate;
	}

	/**
	* 设置字段ApplyDate的值，该字段的<br>
	* 字段名称 :ApplyDate<br>
	* 数据类型 :Datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setApplyDate(Date applyDate) {
		this.ApplyDate = applyDate;
    }

	/**
	* 获取字段CancelResult的值，该字段的<br>
	* 字段名称 :CancelResult<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCancelResult() {
		return CancelResult;
	}

	/**
	* 设置字段CancelResult的值，该字段的<br>
	* 字段名称 :CancelResult<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCancelResult(String cancelResult) {
		this.CancelResult = cancelResult;
    }

	/**
	* 获取字段CancelFailReason的值，该字段的<br>
	* 字段名称 :CancelFailReason<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCancelFailReason() {
		return CancelFailReason;
	}

	/**
	* 设置字段CancelFailReason的值，该字段的<br>
	* 字段名称 :CancelFailReason<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCancelFailReason(String cancelFailReason) {
		this.CancelFailReason = cancelFailReason;
    }

	/**
	* 获取字段CreateDate的值，该字段的<br>
	* 字段名称 :CreateDate<br>
	* 数据类型 :Datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getCreateDate() {
		return CreateDate;
	}

	/**
	* 设置字段CreateDate的值，该字段的<br>
	* 字段名称 :CreateDate<br>
	* 数据类型 :Datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCreateDate(Date createDate) {
		this.CreateDate = createDate;
    }

	/**
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :Prop1<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :Prop1<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* 获取字段Prop2的值，该字段的<br>
	* 字段名称 :Prop2<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* 设置字段Prop2的值，该字段的<br>
	* 字段名称 :Prop2<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

	/**
	* 获取字段Prop3的值，该字段的<br>
	* 字段名称 :Prop3<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp3() {
		return Prop3;
	}

	/**
	* 设置字段Prop3的值，该字段的<br>
	* 字段名称 :Prop3<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp3(String prop3) {
		this.Prop3 = prop3;
    }

}