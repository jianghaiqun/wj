package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

/**
 * 表名称：合作商管理<br>
 * 表代码：PartnersManage<br>
 * 表主键：PartnersCode<br>
 */
public class PartnersManageSchema extends Schema {
	private String PartnersCode;

	private String PartnersName;

	private String CookieTime;

	private String SendUrl;

	private String SendState;

	private String State;

	private String CreateDate;

	private String Prop1;

	private String Prop2;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("PartnersCode", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("PartnersName", DataColumn.STRING, 1, 32 , 0 , false , false),
		new SchemaColumn("CookieTime", DataColumn.STRING, 2, 10 , 0 , false , false),
		new SchemaColumn("SendUrl", DataColumn.STRING, 3, 200 , 0 , false , false),
		new SchemaColumn("SendState", DataColumn.STRING, 4, 2 , 0 , false , false),
		new SchemaColumn("State", DataColumn.STRING, 5, 2 , 0 , false , false),
		new SchemaColumn("CreateDate", DataColumn.STRING, 6, 20 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 7, 200 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 8, 200 , 0 , false , false)
	};

	public static final String _TableCode = "PartnersManage";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into PartnersManage values(?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update PartnersManage set PartnersCode=?,PartnersName=?,CookieTime=?,SendUrl=?,SendState=?,State=?,CreateDate=?,Prop1=?,Prop2=? where PartnersCode=?";

	protected static final String _DeleteSQL = "delete from PartnersManage  where PartnersCode=?";

	protected static final String _FillAllSQL = "select * from PartnersManage  where PartnersCode=?";

	public PartnersManageSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[9];
	}

	protected Schema newInstance(){
		return new PartnersManageSchema();
	}

	protected SchemaSet newSet(){
		return new PartnersManageSet();
	}

	public PartnersManageSet query() {
		return query(null, -1, -1);
	}

	public PartnersManageSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public PartnersManageSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public PartnersManageSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (PartnersManageSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){PartnersCode = (String)v;return;}
		if (i == 1){PartnersName = (String)v;return;}
		if (i == 2){CookieTime = (String)v;return;}
		if (i == 3){SendUrl = (String)v;return;}
		if (i == 4){SendState = (String)v;return;}
		if (i == 5){State = (String)v;return;}
		if (i == 6){CreateDate = (String)v;return;}
		if (i == 7){Prop1 = (String)v;return;}
		if (i == 8){Prop2 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return PartnersCode;}
		if (i == 1){return PartnersName;}
		if (i == 2){return CookieTime;}
		if (i == 3){return SendUrl;}
		if (i == 4){return SendState;}
		if (i == 5){return State;}
		if (i == 6){return CreateDate;}
		if (i == 7){return Prop1;}
		if (i == 8){return Prop2;}
		return null;
	}

	/**
	* 获取字段PartnersCode的值，该字段的<br>
	* 字段名称 :合作商Code<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getPartnersCode() {
		return PartnersCode;
	}

	/**
	* 设置字段PartnersCode的值，该字段的<br>
	* 字段名称 :合作商Code<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setPartnersCode(String partnersCode) {
		this.PartnersCode = partnersCode;
    }

	/**
	* 获取字段PartnersName的值，该字段的<br>
	* 字段名称 :合作商名称<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPartnersName() {
		return PartnersName;
	}

	/**
	* 设置字段PartnersName的值，该字段的<br>
	* 字段名称 :合作商名称<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPartnersName(String partnersName) {
		this.PartnersName = partnersName;
    }

	/**
	* 获取字段CookieTime的值，该字段的<br>
	* 字段名称 :Cookie有效期<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	Cookie有效期(小时)<br>
	*/
	public String getCookieTime() {
		return CookieTime;
	}

	/**
	* 设置字段CookieTime的值，该字段的<br>
	* 字段名称 :Cookie有效期<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	Cookie有效期(小时)<br>
	*/
	public void setCookieTime(String cookieTime) {
		this.CookieTime = cookieTime;
    }

	/**
	* 获取字段SendUrl的值，该字段的<br>
	* 字段名称 :推送地址<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSendUrl() {
		return SendUrl;
	}

	/**
	* 设置字段SendUrl的值，该字段的<br>
	* 字段名称 :推送地址<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSendUrl(String sendUrl) {
		this.SendUrl = sendUrl;
    }

	/**
	* 获取字段SendState的值，该字段的<br>
	* 字段名称 :推送状态<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	是 Y 否 N<br>
	*/
	public String getSendState() {
		return SendState;
	}

	/**
	* 设置字段SendState的值，该字段的<br>
	* 字段名称 :推送状态<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	是 Y 否 N<br>
	*/
	public void setSendState(String sendState) {
		this.SendState = sendState;
    }

	/**
	* 获取字段State的值，该字段的<br>
	* 字段名称 :合作状态<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	是 Y 否 N<br>
	*/
	public String getState() {
		return State;
	}

	/**
	* 设置字段State的值，该字段的<br>
	* 字段名称 :合作状态<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	是 Y 否 N<br>
	*/
	public void setState(String state) {
		this.State = state;
    }

	/**
	* 获取字段CreateDate的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCreateDate() {
		return CreateDate;
	}

	/**
	* 设置字段CreateDate的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCreateDate(String createDate) {
		this.CreateDate = createDate;
    }

	/**
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* 获取字段Prop2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* 设置字段Prop2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

}