package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

import java.util.Date;

import java.math.BigDecimal;

import java.util.Date;

import java.math.BigDecimal;

import java.util.Date;

import java.math.BigDecimal;

/**
 * 表名称：月目标保费信息表<br>
 * 表代码：MonthGoalsInfo<br>
 * 表主键：id<br>
 */
public class MonthGoalsInfoSchema extends Schema {
	private String id;

	private String month;

	private BigDecimal goalsPrem;

	private String branchInnerCode;

	private String remark1;

	private String remark2;

	private String remark3;

	private Date createDate;

	private String createUser;

	private Date modifyDate;

	private String modifyUser;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("month", DataColumn.STRING, 1, 10 , 0 , false , false),
		new SchemaColumn("goalsPrem", DataColumn.BIGDECIMAL, 2, 10 , 0 , false , false),
		new SchemaColumn("branchInnerCode", DataColumn.STRING, 3, 255 , 0 , false , false),
		new SchemaColumn("remark1", DataColumn.STRING, 4, 255 , 0 , false , false),
		new SchemaColumn("remark2", DataColumn.STRING, 5, 255 , 0 , false , false),
		new SchemaColumn("remark3", DataColumn.STRING, 6, 255 , 0 , false , false),
		new SchemaColumn("createDate", DataColumn.DATETIME, 7, 0 , 0 , false , false),
		new SchemaColumn("createUser", DataColumn.STRING, 8, 100 , 0 , false , false),
		new SchemaColumn("modifyDate", DataColumn.DATETIME, 9, 0 , 0 , false , false),
		new SchemaColumn("modifyUser", DataColumn.STRING, 10, 100 , 0 , false , false)
	};

	public static final String _TableCode = "MonthGoalsInfo";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into MonthGoalsInfo values(?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update MonthGoalsInfo set id=?,month=?,goalsPrem=?,branchInnerCode=?,remark1=?,remark2=?,remark3=?,createDate=?,createUser=?,modifyDate=?,modifyUser=? where id=?";

	protected static final String _DeleteSQL = "delete from MonthGoalsInfo  where id=?";

	protected static final String _FillAllSQL = "select * from MonthGoalsInfo  where id=?";

	public MonthGoalsInfoSchema(){
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
		return new MonthGoalsInfoSchema();
	}

	protected SchemaSet newSet(){
		return new MonthGoalsInfoSet();
	}

	public MonthGoalsInfoSet query() {
		return query(null, -1, -1);
	}

	public MonthGoalsInfoSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public MonthGoalsInfoSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public MonthGoalsInfoSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (MonthGoalsInfoSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){month = (String)v;return;}
		if (i == 2){if(v==null){goalsPrem = null;}else{goalsPrem =  ((BigDecimal)v) ;}return;}
		if (i == 3){branchInnerCode = (String)v;return;}
		if (i == 4){remark1 = (String)v;return;}
		if (i == 5){remark2 = (String)v;return;}
		if (i == 6){remark3 = (String)v;return;}
		if (i == 7){createDate = (Date)v;return;}
		if (i == 8){createUser = (String)v;return;}
		if (i == 9){modifyDate = (Date)v;return;}
		if (i == 10){modifyUser = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return month;}
		if (i == 2){return goalsPrem;}
		if (i == 3){return branchInnerCode;}
		if (i == 4){return remark1;}
		if (i == 5){return remark2;}
		if (i == 6){return remark3;}
		if (i == 7){return createDate;}
		if (i == 8){return createUser;}
		if (i == 9){return modifyDate;}
		if (i == 10){return modifyUser;}
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
	* 获取字段month的值，该字段的<br>
	* 字段名称 :年月<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getmonth() {
		return month;
	}

	/**
	* 设置字段month的值，该字段的<br>
	* 字段名称 :年月<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmonth(String month) {
		this.month = month;
    }

	/**
	* 获取字段goalsPrem的值，该字段的<br>
	* 字段名称 :计划完成保费<br>
	* 数据类型 :decimal(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public BigDecimal getgoalsPrem() {
		return goalsPrem;
	}

	/**
	* 设置字段goalsPrem的值，该字段的<br>
	* 字段名称 :计划完成保费<br>
	* 数据类型 :decimal(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setgoalsPrem(BigDecimal goalsPrem) {
		this.goalsPrem = goalsPrem;
    }

	/**
	* 获取字段branchInnerCode的值，该字段的<br>
	* 字段名称 :联系人所属机构编码<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getbranchInnerCode() {
		return branchInnerCode;
	}

	/**
	* 设置字段branchInnerCode的值，该字段的<br>
	* 字段名称 :联系人所属机构编码<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setbranchInnerCode(String branchInnerCode) {
		this.branchInnerCode = branchInnerCode;
    }

	/**
	* 获取字段remark1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getremark1() {
		return remark1;
	}

	/**
	* 设置字段remark1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setremark1(String remark1) {
		this.remark1 = remark1;
    }

	/**
	* 获取字段remark2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getremark2() {
		return remark2;
	}

	/**
	* 设置字段remark2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setremark2(String remark2) {
		this.remark2 = remark2;
    }

	/**
	* 获取字段remark3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getremark3() {
		return remark3;
	}

	/**
	* 设置字段remark3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setremark3(String remark3) {
		this.remark3 = remark3;
    }

	/**
	* 获取字段createDate的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getcreateDate() {
		return createDate;
	}

	/**
	* 设置字段createDate的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcreateDate(Date createDate) {
		this.createDate = createDate;
    }

	/**
	* 获取字段createUser的值，该字段的<br>
	* 字段名称 :创建人<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcreateUser() {
		return createUser;
	}

	/**
	* 设置字段createUser的值，该字段的<br>
	* 字段名称 :创建人<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcreateUser(String createUser) {
		this.createUser = createUser;
    }

	/**
	* 获取字段modifyDate的值，该字段的<br>
	* 字段名称 :修改时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getmodifyDate() {
		return modifyDate;
	}

	/**
	* 设置字段modifyDate的值，该字段的<br>
	* 字段名称 :修改时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmodifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
    }

	/**
	* 获取字段modifyUser的值，该字段的<br>
	* 字段名称 :修改人<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getmodifyUser() {
		return modifyUser;
	}

	/**
	* 设置字段modifyUser的值，该字段的<br>
	* 字段名称 :修改人<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmodifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
    }

}