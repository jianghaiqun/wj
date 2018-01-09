package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

/**
 * 表名称：SDActivityPayamount<br>
 * 表代码：SDActivityPayamount<br>
 * 表主键：id<br>
 */
public class SDActivityPayamountSchema extends Schema {
	private String id;

	private String activitysn;

	private String payamount;

	private String prop1;

	private String prop2;

	private String prop3;

	private String modifyDate;

	private String createDate;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("activitysn", DataColumn.STRING, 1, 50 , 0 , false , false),
		new SchemaColumn("payamount", DataColumn.STRING, 2, 10 , 0 , false , false),
		new SchemaColumn("prop1", DataColumn.STRING, 3, 10 , 0 , false , false),
		new SchemaColumn("prop2", DataColumn.STRING, 4, 255 , 0 , false , false),
		new SchemaColumn("prop3", DataColumn.STRING, 5, 255 , 0 , false , false),
		new SchemaColumn("modifyDate", DataColumn.STRING, 6, 20 , 0 , false , false),
		new SchemaColumn("createDate", DataColumn.STRING, 7, 20 , 0 , false , false)
	};

	public static final String _TableCode = "SDActivityPayamount";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into SDActivityPayamount values(?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update SDActivityPayamount set id=?,activitysn=?,payamount=?,prop1=?,prop2=?,prop3=?,modifyDate=?,createDate=? where id=?";

	protected static final String _DeleteSQL = "delete from SDActivityPayamount  where id=?";

	protected static final String _FillAllSQL = "select * from SDActivityPayamount  where id=?";

	public SDActivityPayamountSchema(){
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
		return new SDActivityPayamountSchema();
	}

	protected SchemaSet newSet(){
		return new SDActivityPayamountSet();
	}

	public SDActivityPayamountSet query() {
		return query(null, -1, -1);
	}

	public SDActivityPayamountSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public SDActivityPayamountSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public SDActivityPayamountSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (SDActivityPayamountSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){activitysn = (String)v;return;}
		if (i == 2){payamount = (String)v;return;}
		if (i == 3){prop1 = (String)v;return;}
		if (i == 4){prop2 = (String)v;return;}
		if (i == 5){prop3 = (String)v;return;}
		if (i == 6){modifyDate = (String)v;return;}
		if (i == 7){createDate = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return activitysn;}
		if (i == 2){return payamount;}
		if (i == 3){return prop1;}
		if (i == 4){return prop2;}
		if (i == 5){return prop3;}
		if (i == 6){return modifyDate;}
		if (i == 7){return createDate;}
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
	* 获取字段activitysn的值，该字段的<br>
	* 字段名称 :活动号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getactivitysn() {
		return activitysn;
	}

	/**
	* 设置字段activitysn的值，该字段的<br>
	* 字段名称 :活动号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setactivitysn(String activitysn) {
		this.activitysn = activitysn;
    }

	/**
	* 获取字段payamount的值，该字段的<br>
	* 字段名称 :支付金额<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getpayamount() {
		return payamount;
	}

	/**
	* 设置字段payamount的值，该字段的<br>
	* 字段名称 :支付金额<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpayamount(String payamount) {
		this.payamount = payamount;
    }

	/**
	* 获取字段prop1的值，该字段的<br>
	* 字段名称 :抵消金额<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop1() {
		return prop1;
	}

	/**
	* 设置字段prop1的值，该字段的<br>
	* 字段名称 :抵消金额<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop1(String prop1) {
		this.prop1 = prop1;
    }

	/**
	* 获取字段prop2的值，该字段的<br>
	* 字段名称 :备注2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop2() {
		return prop2;
	}

	/**
	* 设置字段prop2的值，该字段的<br>
	* 字段名称 :备注2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop2(String prop2) {
		this.prop2 = prop2;
    }

	/**
	* 获取字段prop3的值，该字段的<br>
	* 字段名称 :备注3<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop3() {
		return prop3;
	}

	/**
	* 设置字段prop3的值，该字段的<br>
	* 字段名称 :备注3<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop3(String prop3) {
		this.prop3 = prop3;
    }

	/**
	* 获取字段modifyDate的值，该字段的<br>
	* 字段名称 :更改时间<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getmodifyDate() {
		return modifyDate;
	}

	/**
	* 设置字段modifyDate的值，该字段的<br>
	* 字段名称 :更改时间<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmodifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
    }

	/**
	* 获取字段createDate的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcreateDate() {
		return createDate;
	}

	/**
	* 设置字段createDate的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcreateDate(String createDate) {
		this.createDate = createDate;
    }

}