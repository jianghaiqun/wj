package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

/**
 * 表名称：ZDFEUIInfoValue<br>
 * 表代码：ZDFEUIInfoValue<br>
 * 表主键：UIInfoID<br>
 */
public class ZDFEUIInfoValueSchema extends Schema {
	private static final long serialVersionUID = -9092940108519681657L;

	private String RiskCode;

	private Integer ValueOrder;

	private String ValueCode;

	private String ValueName;

	private String Remark;

	private String IsDefault;

	private String UIInfoID;

	private String UIInfoValueID;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("RiskCode", DataColumn.STRING, 0, 30 , 0 , true , false),
		new SchemaColumn("ValueOrder", DataColumn.INTEGER, 1, 0 , 0 , false , false),
		new SchemaColumn("ValueCode", DataColumn.STRING, 2, 30 , 0 , false , false),
		new SchemaColumn("ValueName", DataColumn.STRING, 3, 40 , 0 , false , false),
		new SchemaColumn("Remark", DataColumn.STRING, 4, 100 , 0 , false , false),
		new SchemaColumn("IsDefault", DataColumn.STRING, 5, 2 , 0 , false , false),
		new SchemaColumn("UIInfoID", DataColumn.STRING, 6, 30 , 0 , true , true),
		new SchemaColumn("UIInfoValueID", DataColumn.STRING, 7, 30 , 0 , false , false)
	};

	public static final String _TableCode = "ZDFEUIInfoValue";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into ZDFEUIInfoValue values(?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZDFEUIInfoValue set RiskCode=?,ValueOrder=?,ValueCode=?,ValueName=?,Remark=?,IsDefault=?,UIInfoID=?,UIInfoValueID=? where UIInfoID=?";

	protected static final String _DeleteSQL = "delete from ZDFEUIInfoValue  where UIInfoID=?";

	protected static final String _FillAllSQL = "select * from ZDFEUIInfoValue  where UIInfoID=?";

	public ZDFEUIInfoValueSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[8];
	}

	protected Schema newInstance(){
		return new ZDFEUIInfoValueSchema();
	}

	protected SchemaSet newSet(){
		return new ZDFEUIInfoValueSet();
	}

	public ZDFEUIInfoValueSet query() {
		return query(null, -1, -1);
	}

	public ZDFEUIInfoValueSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZDFEUIInfoValueSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZDFEUIInfoValueSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZDFEUIInfoValueSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){RiskCode = (String)v;return;}
		if (i == 1){if(v==null){ValueOrder = null;}else{ValueOrder = new Integer(v.toString());}return;}
		if (i == 2){ValueCode = (String)v;return;}
		if (i == 3){ValueName = (String)v;return;}
		if (i == 4){Remark = (String)v;return;}
		if (i == 5){IsDefault = (String)v;return;}
		if (i == 6){UIInfoID = (String)v;return;}
		if (i == 7){UIInfoValueID = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return RiskCode;}
		if (i == 1){return ValueOrder;}
		if (i == 2){return ValueCode;}
		if (i == 3){return ValueName;}
		if (i == 4){return Remark;}
		if (i == 5){return IsDefault;}
		if (i == 6){return UIInfoID;}
		if (i == 7){return UIInfoValueID;}
		return null;
	}

	/**
	* 获取字段RiskCode的值，该字段的<br>
	* 字段名称 :产品编码<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getRiskCode() {
		return RiskCode;
	}

	/**
	* 设置字段RiskCode的值，该字段的<br>
	* 字段名称 :产品编码<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setRiskCode(String riskCode) {
		this.RiskCode = riskCode;
    }

	/**
	* 获取字段ValueOrder的值，该字段的<br>
	* 字段名称 :值序号<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getValueOrder() {
		if(ValueOrder==null){return 0;}
		return ValueOrder.intValue();
	}

	/**
	* 设置字段ValueOrder的值，该字段的<br>
	* 字段名称 :值序号<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setValueOrder(int valueOrder) {
		this.ValueOrder = new Integer(valueOrder);
    }

	/**
	* 设置字段ValueOrder的值，该字段的<br>
	* 字段名称 :值序号<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setValueOrder(String valueOrder) {
		if (valueOrder == null){
			this.ValueOrder = null;
			return;
		}
		this.ValueOrder = new Integer(valueOrder);
    }

	/**
	* 获取字段ValueCode的值，该字段的<br>
	* 字段名称 :显示值编码<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getValueCode() {
		return ValueCode;
	}

	/**
	* 设置字段ValueCode的值，该字段的<br>
	* 字段名称 :显示值编码<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setValueCode(String valueCode) {
		this.ValueCode = valueCode;
    }

	/**
	* 获取字段ValueName的值，该字段的<br>
	* 字段名称 :显示值名称<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getValueName() {
		return ValueName;
	}

	/**
	* 设置字段ValueName的值，该字段的<br>
	* 字段名称 :显示值名称<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setValueName(String valueName) {
		this.ValueName = valueName;
    }

	/**
	* 获取字段Remark的值，该字段的<br>
	* 字段名称 :是否关联健康告知<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getRemark() {
		return Remark;
	}

	/**
	* 设置字段Remark的值，该字段的<br>
	* 字段名称 :是否关联健康告知<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRemark(String remark) {
		this.Remark = remark;
    }

	/**
	* 获取字段IsDefault的值，该字段的<br>
	* 字段名称 :是否默认显示值<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getIsDefault() {
		return IsDefault;
	}

	/**
	* 设置字段IsDefault的值，该字段的<br>
	* 字段名称 :是否默认显示值<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setIsDefault(String isDefault) {
		this.IsDefault = isDefault;
    }

	/**
	* 获取字段UIInfoID的值，该字段的<br>
	* 字段名称 :界面元素流水号<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getUIInfoID() {
		return UIInfoID;
	}

	/**
	* 设置字段UIInfoID的值，该字段的<br>
	* 字段名称 :界面元素流水号<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setUIInfoID(String uIInfoID) {
		this.UIInfoID = uIInfoID;
    }

	/**
	* 获取字段UIInfoValueID的值，该字段的<br>
	* 字段名称 :显示值流水号<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getUIInfoValueID() {
		return UIInfoValueID;
	}

	/**
	* 设置字段UIInfoValueID的值，该字段的<br>
	* 字段名称 :显示值流水号<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setUIInfoValueID(String uIInfoValueID) {
		this.UIInfoValueID = uIInfoValueID;
    }

}