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

/**
 * 表名称：抓取记录表<br>
 * 表代码：SDDREmailExtractRecode<br>
 * 表主键：Id<br>
 */
public class SDDREmailExtractRecodeSchema extends Schema {
	private String Id;

	private String Link;

	private Date AddTime;

	private String AddUser;

	private String Remark;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	private String Prop4;

	private String Prop5;

	private String Prop6;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("Id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("Link", DataColumn.STRING, 1, 500 , 0 , false , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 2, 0 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 3, 50 , 0 , false , false),
		new SchemaColumn("Remark", DataColumn.STRING, 4, 500 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 5, 100 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 6, 100 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 7, 100 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 8, 100 , 0 , false , false),
		new SchemaColumn("Prop5", DataColumn.STRING, 9, 100 , 0 , false , false),
		new SchemaColumn("Prop6", DataColumn.STRING, 10, 500 , 0 , false , false)
	};

	public static final String _TableCode = "SDDREmailExtractRecode";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into SDDREmailExtractRecode values(?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update SDDREmailExtractRecode set Id=?,Link=?,AddTime=?,AddUser=?,Remark=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,Prop5=?,Prop6=? where Id=?";

	protected static final String _DeleteSQL = "delete from SDDREmailExtractRecode  where Id=?";

	protected static final String _FillAllSQL = "select * from SDDREmailExtractRecode  where Id=?";

	public SDDREmailExtractRecodeSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[11];
	}

	protected Schema newInstance(){
		return new SDDREmailExtractRecodeSchema();
	}

	protected SchemaSet newSet(){
		return new SDDREmailExtractRecodeSet();
	}

	public SDDREmailExtractRecodeSet query() {
		return query(null, -1, -1);
	}

	public SDDREmailExtractRecodeSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public SDDREmailExtractRecodeSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public SDDREmailExtractRecodeSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (SDDREmailExtractRecodeSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){Id = (String)v;return;}
		if (i == 1){Link = (String)v;return;}
		if (i == 2){AddTime = (Date)v;return;}
		if (i == 3){AddUser = (String)v;return;}
		if (i == 4){Remark = (String)v;return;}
		if (i == 5){Prop1 = (String)v;return;}
		if (i == 6){Prop2 = (String)v;return;}
		if (i == 7){Prop3 = (String)v;return;}
		if (i == 8){Prop4 = (String)v;return;}
		if (i == 9){Prop5 = (String)v;return;}
		if (i == 10){Prop6 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return Id;}
		if (i == 1){return Link;}
		if (i == 2){return AddTime;}
		if (i == 3){return AddUser;}
		if (i == 4){return Remark;}
		if (i == 5){return Prop1;}
		if (i == 6){return Prop2;}
		if (i == 7){return Prop3;}
		if (i == 8){return Prop4;}
		if (i == 9){return Prop5;}
		if (i == 10){return Prop6;}
		return null;
	}

	/**
	* 获取字段Id的值，该字段的<br>
	* 字段名称 :Id<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getId() {
		return Id;
	}

	/**
	* 设置字段Id的值，该字段的<br>
	* 字段名称 :Id<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setId(String id) {
		this.Id = id;
    }

	/**
	* 获取字段Link的值，该字段的<br>
	* 字段名称 :帖子地址<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getLink() {
		return Link;
	}

	/**
	* 设置字段Link的值，该字段的<br>
	* 字段名称 :帖子地址<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setLink(String link) {
		this.Link = link;
    }

	/**
	* 获取字段AddTime的值，该字段的<br>
	* 字段名称 :增加时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getAddTime() {
		return AddTime;
	}

	/**
	* 设置字段AddTime的值，该字段的<br>
	* 字段名称 :增加时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAddTime(Date addTime) {
		this.AddTime = addTime;
    }

	/**
	* 获取字段AddUser的值，该字段的<br>
	* 字段名称 :增加人<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAddUser() {
		return AddUser;
	}

	/**
	* 设置字段AddUser的值，该字段的<br>
	* 字段名称 :增加人<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAddUser(String addUser) {
		this.AddUser = addUser;
    }

	/**
	* 获取字段Remark的值，该字段的<br>
	* 字段名称 :备注<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getRemark() {
		return Remark;
	}

	/**
	* 设置字段Remark的值，该字段的<br>
	* 字段名称 :备注<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRemark(String remark) {
		this.Remark = remark;
    }

	/**
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :Prop1<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :Prop1<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* 获取字段Prop2的值，该字段的<br>
	* 字段名称 :Prop2<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* 设置字段Prop2的值，该字段的<br>
	* 字段名称 :Prop2<br>
	* 数据类型 :varchar(100)<br>
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

	/**
	* 获取字段Prop4的值，该字段的<br>
	* 字段名称 :Prop4<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp4() {
		return Prop4;
	}

	/**
	* 设置字段Prop4的值，该字段的<br>
	* 字段名称 :Prop4<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp4(String prop4) {
		this.Prop4 = prop4;
    }

	/**
	* 获取字段Prop5的值，该字段的<br>
	* 字段名称 :Prop5<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp5() {
		return Prop5;
	}

	/**
	* 设置字段Prop5的值，该字段的<br>
	* 字段名称 :Prop5<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp5(String prop5) {
		this.Prop5 = prop5;
    }

	/**
	* 获取字段Prop6的值，该字段的<br>
	* 字段名称 :Prop6<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp6() {
		return Prop6;
	}

	/**
	* 设置字段Prop6的值，该字段的<br>
	* 字段名称 :Prop6<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp6(String prop6) {
		this.Prop6 = prop6;
    }

}