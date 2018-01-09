package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：理赔清单<br>
 * 表代码：claimstatisticsdetails<br>
 * 表主键：id<br>
 */
public class claimstatisticsdetailsSchema extends Schema {
	private String id;

	private String policyNo;

	private String claimsItemsName;

	private String claimsAmount;

	private Date claimsDate;

	private Date claimsTime;

	private String comment;

	private String prop1;

	private String prop2;

	private Date createDateTime;

	private Date modifyDateTime;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("policyNo", DataColumn.STRING, 1, 400 , 0 , false , false),
		new SchemaColumn("claimsItemsName", DataColumn.STRING, 2, 255 , 0 , false , false),
		new SchemaColumn("claimsAmount", DataColumn.STRING, 3, 20 , 0 , false , false),
		new SchemaColumn("claimsDate", DataColumn.DATETIME, 4, 0 , 0 , false , false),
		new SchemaColumn("claimsTime", DataColumn.DATETIME, 5, 0 , 0 , false , false),
		new SchemaColumn("comment", DataColumn.STRING, 6, 400 , 0 , false , false),
		new SchemaColumn("prop1", DataColumn.STRING, 7, 255 , 0 , false , false),
		new SchemaColumn("prop2", DataColumn.STRING, 8, 255 , 0 , false , false),
		new SchemaColumn("createDateTime", DataColumn.DATETIME, 9, 0 , 0 , false , false),
		new SchemaColumn("modifyDateTime", DataColumn.DATETIME, 10, 0 , 0 , false , false)
	};

	public static final String _TableCode = "claimstatisticsdetails";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into claimstatisticsdetails values(?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update claimstatisticsdetails set id=?,policyNo=?,claimsItemsName=?,claimsAmount=?,claimsDate=?,claimsTime=?,comment=?,prop1=?,prop2=?,createDateTime=?,modifyDateTime=? where id=?";

	protected static final String _DeleteSQL = "delete from claimstatisticsdetails  where id=?";

	protected static final String _FillAllSQL = "select * from claimstatisticsdetails  where id=?";

	public claimstatisticsdetailsSchema(){
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
		return new claimstatisticsdetailsSchema();
	}

	protected SchemaSet newSet(){
		return new claimstatisticsdetailsSet();
	}

	public claimstatisticsdetailsSet query() {
		return query(null, -1, -1);
	}

	public claimstatisticsdetailsSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public claimstatisticsdetailsSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public claimstatisticsdetailsSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (claimstatisticsdetailsSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){policyNo = (String)v;return;}
		if (i == 2){claimsItemsName = (String)v;return;}
		if (i == 3){claimsAmount = (String)v;return;}
		if (i == 4){claimsDate = (Date)v;return;}
		if (i == 5){claimsTime = (Date)v;return;}
		if (i == 6){comment = (String)v;return;}
		if (i == 7){prop1 = (String)v;return;}
		if (i == 8){prop2 = (String)v;return;}
		if (i == 9){createDateTime = (Date)v;return;}
		if (i == 10){modifyDateTime = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return policyNo;}
		if (i == 2){return claimsItemsName;}
		if (i == 3){return claimsAmount;}
		if (i == 4){return claimsDate;}
		if (i == 5){return claimsTime;}
		if (i == 6){return comment;}
		if (i == 7){return prop1;}
		if (i == 8){return prop2;}
		if (i == 9){return createDateTime;}
		if (i == 10){return modifyDateTime;}
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
	* 获取字段policyNo的值，该字段的<br>
	* 字段名称 :保单号<br>
	* 数据类型 :varchar(400)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getpolicyNo() {
		return policyNo;
	}

	/**
	* 设置字段policyNo的值，该字段的<br>
	* 字段名称 :保单号<br>
	* 数据类型 :varchar(400)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpolicyNo(String policyNo) {
		this.policyNo = policyNo;
    }

	/**
	* 获取字段claimsItemsName的值，该字段的<br>
	* 字段名称 :理赔项目<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getclaimsItemsName() {
		return claimsItemsName;
	}

	/**
	* 设置字段claimsItemsName的值，该字段的<br>
	* 字段名称 :理赔项目<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setclaimsItemsName(String claimsItemsName) {
		this.claimsItemsName = claimsItemsName;
    }

	/**
	* 获取字段claimsAmount的值，该字段的<br>
	* 字段名称 :理赔金额<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getclaimsAmount() {
		return claimsAmount;
	}

	/**
	* 设置字段claimsAmount的值，该字段的<br>
	* 字段名称 :理赔金额<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setclaimsAmount(String claimsAmount) {
		this.claimsAmount = claimsAmount;
    }

	/**
	* 获取字段claimsDate的值，该字段的<br>
	* 字段名称 :理赔日期<br>
	* 数据类型 :date<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getclaimsDate() {
		return claimsDate;
	}

	/**
	* 设置字段claimsDate的值，该字段的<br>
	* 字段名称 :理赔日期<br>
	* 数据类型 :date<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setclaimsDate(Date claimsDate) {
		this.claimsDate = claimsDate;
    }

	/**
	* 获取字段claimsTime的值，该字段的<br>
	* 字段名称 :理赔时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getclaimsTime() {
		return claimsTime;
	}

	/**
	* 设置字段claimsTime的值，该字段的<br>
	* 字段名称 :理赔时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setclaimsTime(Date claimsTime) {
		this.claimsTime = claimsTime;
    }

	/**
	* 获取字段comment的值，该字段的<br>
	* 字段名称 :备注<br>
	* 数据类型 :varchar(400)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcomment() {
		return comment;
	}

	/**
	* 设置字段comment的值，该字段的<br>
	* 字段名称 :备注<br>
	* 数据类型 :varchar(400)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcomment(String comment) {
		this.comment = comment;
    }

	/**
	* 获取字段prop1的值，该字段的<br>
	* 字段名称 :prop1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop1() {
		return prop1;
	}

	/**
	* 设置字段prop1的值，该字段的<br>
	* 字段名称 :prop1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop1(String prop1) {
		this.prop1 = prop1;
    }

	/**
	* 获取字段prop2的值，该字段的<br>
	* 字段名称 :prop2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop2() {
		return prop2;
	}

	/**
	* 设置字段prop2的值，该字段的<br>
	* 字段名称 :prop2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop2(String prop2) {
		this.prop2 = prop2;
    }

	/**
	* 获取字段createDateTime的值，该字段的<br>
	* 字段名称 :createDateTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getcreateDateTime() {
		return createDateTime;
	}

	/**
	* 设置字段createDateTime的值，该字段的<br>
	* 字段名称 :createDateTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
    }

	/**
	* 获取字段modifyDateTime的值，该字段的<br>
	* 字段名称 :modifyDateTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getmodifyDateTime() {
		return modifyDateTime;
	}

	/**
	* 设置字段modifyDateTime的值，该字段的<br>
	* 字段名称 :modifyDateTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmodifyDateTime(Date modifyDateTime) {
		this.modifyDateTime = modifyDateTime;
    }

}