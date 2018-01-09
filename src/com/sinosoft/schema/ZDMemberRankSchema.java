package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：会员等级配置表<br>
 * 表代码：ZDMemberRank<br>
 * 表主键：MconfigCode<br>
 */
public class ZDMemberRankSchema extends Schema {
	private String MconfigCode;

	private String MconfigName;

	private String MconfigValue;

	private Date AddTime;

	private String AddUser;

	private Date ModifyTime;

	private String ModifyUser;

	private String Prop1;

	private String Prop2;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("MconfigCode", DataColumn.STRING, 0, 40 , 0 , true , true),
		new SchemaColumn("MconfigName", DataColumn.STRING, 1, 100 , 0 , false , false),
		new SchemaColumn("MconfigValue", DataColumn.STRING, 2, 200 , 0 , false , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 3, 0 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 4, 200 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 5, 0 , 0 , false , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 6, 200 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 7, 50 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 8, 50 , 0 , false , false)
	};

	public static final String _TableCode = "ZDMemberRank";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into ZDMemberRank values(?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZDMemberRank set MconfigCode=?,MconfigName=?,MconfigValue=?,AddTime=?,AddUser=?,ModifyTime=?,ModifyUser=?,Prop1=?,Prop2=? where MconfigCode=?";

	protected static final String _DeleteSQL = "delete from ZDMemberRank  where MconfigCode=?";

	protected static final String _FillAllSQL = "select * from ZDMemberRank  where MconfigCode=?";

	public ZDMemberRankSchema(){
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
		return new ZDMemberRankSchema();
	}

	protected SchemaSet newSet(){
		return new ZDMemberRankSet();
	}

	public ZDMemberRankSet query() {
		return query(null, -1, -1);
	}

	public ZDMemberRankSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZDMemberRankSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZDMemberRankSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZDMemberRankSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){MconfigCode = (String)v;return;}
		if (i == 1){MconfigName = (String)v;return;}
		if (i == 2){MconfigValue = (String)v;return;}
		if (i == 3){AddTime = (Date)v;return;}
		if (i == 4){AddUser = (String)v;return;}
		if (i == 5){ModifyTime = (Date)v;return;}
		if (i == 6){ModifyUser = (String)v;return;}
		if (i == 7){Prop1 = (String)v;return;}
		if (i == 8){Prop2 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return MconfigCode;}
		if (i == 1){return MconfigName;}
		if (i == 2){return MconfigValue;}
		if (i == 3){return AddTime;}
		if (i == 4){return AddUser;}
		if (i == 5){return ModifyTime;}
		if (i == 6){return ModifyUser;}
		if (i == 7){return Prop1;}
		if (i == 8){return Prop2;}
		return null;
	}

	/**
	* 获取字段MconfigCode的值，该字段的<br>
	* 字段名称 :会员配置代码<br>
	* 数据类型 :VARCHAR(40)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getMconfigCode() {
		return MconfigCode;
	}

	/**
	* 设置字段MconfigCode的值，该字段的<br>
	* 字段名称 :会员配置代码<br>
	* 数据类型 :VARCHAR(40)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setMconfigCode(String mconfigCode) {
		this.MconfigCode = mconfigCode;
    }

	/**
	* 获取字段MconfigName的值，该字段的<br>
	* 字段名称 :会员配置名称<br>
	* 数据类型 :VARCHAR(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMconfigName() {
		return MconfigName;
	}

	/**
	* 设置字段MconfigName的值，该字段的<br>
	* 字段名称 :会员配置名称<br>
	* 数据类型 :VARCHAR(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMconfigName(String mconfigName) {
		this.MconfigName = mconfigName;
    }

	/**
	* 获取字段MconfigValue的值，该字段的<br>
	* 字段名称 :会员配置值<br>
	* 数据类型 :VARCHAR(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMconfigValue() {
		return MconfigValue;
	}

	/**
	* 设置字段MconfigValue的值，该字段的<br>
	* 字段名称 :会员配置值<br>
	* 数据类型 :VARCHAR(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMconfigValue(String mconfigValue) {
		this.MconfigValue = mconfigValue;
    }

	/**
	* 获取字段AddTime的值，该字段的<br>
	* 字段名称 :添加时间<br>
	* 数据类型 :DATETIME<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getAddTime() {
		return AddTime;
	}

	/**
	* 设置字段AddTime的值，该字段的<br>
	* 字段名称 :添加时间<br>
	* 数据类型 :DATETIME<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAddTime(Date addTime) {
		this.AddTime = addTime;
    }

	/**
	* 获取字段AddUser的值，该字段的<br>
	* 字段名称 :添加用户<br>
	* 数据类型 :VARCHAR(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAddUser() {
		return AddUser;
	}

	/**
	* 设置字段AddUser的值，该字段的<br>
	* 字段名称 :添加用户<br>
	* 数据类型 :VARCHAR(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAddUser(String addUser) {
		this.AddUser = addUser;
    }

	/**
	* 获取字段ModifyTime的值，该字段的<br>
	* 字段名称 :修改时间<br>
	* 数据类型 :DATETIME<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getModifyTime() {
		return ModifyTime;
	}

	/**
	* 设置字段ModifyTime的值，该字段的<br>
	* 字段名称 :修改时间<br>
	* 数据类型 :DATETIME<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyTime(Date modifyTime) {
		this.ModifyTime = modifyTime;
    }

	/**
	* 获取字段ModifyUser的值，该字段的<br>
	* 字段名称 :修改用户<br>
	* 数据类型 :VARCHAR(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getModifyUser() {
		return ModifyUser;
	}

	/**
	* 设置字段ModifyUser的值，该字段的<br>
	* 字段名称 :修改用户<br>
	* 数据类型 :VARCHAR(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyUser(String modifyUser) {
		this.ModifyUser = modifyUser;
    }

	/**
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :备用1<br>
	* 数据类型 :VARCHAR(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :备用1<br>
	* 数据类型 :VARCHAR(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* 获取字段Prop2的值，该字段的<br>
	* 字段名称 :备用2<br>
	* 数据类型 :VARCHAR(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* 设置字段Prop2的值，该字段的<br>
	* 字段名称 :备用2<br>
	* 数据类型 :VARCHAR(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

}