package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

/**
 * 表名称：ZDHealthyInfo<br>
 * 表代码：ZDHealthyInfo<br>
 * 表主键：ID<br>
 */
public class ZDHealthyInfoSchema extends Schema {
	private static final long serialVersionUID = -4965858136114971622L;

	private String RiskCode;

	private String ID;

	private String UIInfoID;

	private String UIInfoValueID;

	private String HealthyType;

	private String IsVerify;

	private String HealthyInfo;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("RiskCode", DataColumn.STRING, 0, 30 , 0 , true , false),
		new SchemaColumn("ID", DataColumn.STRING, 1, 30 , 0 , true , true),
		new SchemaColumn("UIInfoID", DataColumn.STRING, 2, 30 , 0 , false , false),
		new SchemaColumn("UIInfoValueID", DataColumn.STRING, 3, 30 , 0 , false , false),
		new SchemaColumn("HealthyType", DataColumn.STRING, 4, 30 , 0 , false , false),
		new SchemaColumn("IsVerify", DataColumn.STRING, 5, 30 , 0 , false , false),
		new SchemaColumn("HealthyInfo", DataColumn.STRING, 6, 500 , 0 , false , false)
	};

	public static final String _TableCode = "ZDHealthyInfo";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into ZDHealthyInfo values(?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZDHealthyInfo set RiskCode=?,ID=?,UIInfoID=?,UIInfoValueID=?,HealthyType=?,IsVerify=?,HealthyInfo=? where ID=?";

	protected static final String _DeleteSQL = "delete from ZDHealthyInfo  where ID=?";

	protected static final String _FillAllSQL = "select * from ZDHealthyInfo  where ID=?";

	public ZDHealthyInfoSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[7];
	}

	protected Schema newInstance(){
		return new ZDHealthyInfoSchema();
	}

	protected SchemaSet newSet(){
		return new ZDHealthyInfoSet();
	}

	public ZDHealthyInfoSet query() {
		return query(null, -1, -1);
	}

	public ZDHealthyInfoSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZDHealthyInfoSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZDHealthyInfoSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZDHealthyInfoSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){RiskCode = (String)v;return;}
		if (i == 1){ID = (String)v;return;}
		if (i == 2){UIInfoID = (String)v;return;}
		if (i == 3){UIInfoValueID = (String)v;return;}
		if (i == 4){HealthyType = (String)v;return;}
		if (i == 5){IsVerify = (String)v;return;}
		if (i == 6){HealthyInfo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return RiskCode;}
		if (i == 1){return ID;}
		if (i == 2){return UIInfoID;}
		if (i == 3){return UIInfoValueID;}
		if (i == 4){return HealthyType;}
		if (i == 5){return IsVerify;}
		if (i == 6){return HealthyInfo;}
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
	* 获取字段ID的值，该字段的<br>
	* 字段名称 :流水号<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getID() {
		return ID;
	}

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :流水号<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setID(String iD) {
		this.ID = iD;
    }

	/**
	* 获取字段UIInfoID的值，该字段的<br>
	* 字段名称 :界面元素流水号<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getUIInfoID() {
		return UIInfoID;
	}

	/**
	* 设置字段UIInfoID的值，该字段的<br>
	* 字段名称 :界面元素流水号<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
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

	/**
	* 获取字段HealthyType的值，该字段的<br>
	* 字段名称 :健康告知数据类型<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getHealthyType() {
		return HealthyType;
	}

	/**
	* 设置字段HealthyType的值，该字段的<br>
	* 字段名称 :健康告知数据类型<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setHealthyType(String healthyType) {
		this.HealthyType = healthyType;
    }

	/**
	* 获取字段IsVerify的值，该字段的<br>
	* 字段名称 :是否特殊校验<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getIsVerify() {
		return IsVerify;
	}

	/**
	* 设置字段IsVerify的值，该字段的<br>
	* 字段名称 :是否特殊校验<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setIsVerify(String isVerify) {
		this.IsVerify = isVerify;
    }

	/**
	* 获取字段HealthyInfo的值，该字段的<br>
	* 字段名称 :健康告知详细信息<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getHealthyInfo() {
		return HealthyInfo;
	}

	/**
	* 设置字段HealthyInfo的值，该字段的<br>
	* 字段名称 :健康告知详细信息<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setHealthyInfo(String healthyInfo) {
		this.HealthyInfo = healthyInfo;
    }

}