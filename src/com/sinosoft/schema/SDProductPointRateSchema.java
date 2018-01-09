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
 * 表名称：产品积分表<br>
 * 表代码：SDProductPointRate<br>
 * 表主键：ProductID<br>
 */
public class SDProductPointRateSchema extends Schema {
	private String ProductID;

	private String GivePoints;

	private String BuyPoints;

	private String prop1;

	private String prop2;

	private String prop3;

	private String MakeUser;

	private Date MakeDate;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ProductID", DataColumn.STRING, 0, 50 , 0 , true , true),
		new SchemaColumn("GivePoints", DataColumn.STRING, 1, 10 , 0 , false , false),
		new SchemaColumn("BuyPoints", DataColumn.STRING, 2, 10 , 0 , false , false),
		new SchemaColumn("prop1", DataColumn.STRING, 3, 50 , 0 , false , false),
		new SchemaColumn("prop2", DataColumn.STRING, 4, 50 , 0 , false , false),
		new SchemaColumn("prop3", DataColumn.STRING, 5, 50 , 0 , false , false),
		new SchemaColumn("MakeUser", DataColumn.STRING, 6, 50 , 0 , false , false),
		new SchemaColumn("MakeDate", DataColumn.DATETIME, 7, 0 , 0 , false , false)
	};

	public static final String _TableCode = "SDProductPointRate";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into SDProductPointRate values(?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update SDProductPointRate set ProductID=?,GivePoints=?,BuyPoints=?,prop1=?,prop2=?,prop3=?,MakeUser=?,MakeDate=? where ProductID=?";

	protected static final String _DeleteSQL = "delete from SDProductPointRate  where ProductID=?";

	protected static final String _FillAllSQL = "select * from SDProductPointRate  where ProductID=?";

	public SDProductPointRateSchema(){
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
		return new SDProductPointRateSchema();
	}

	protected SchemaSet newSet(){
		return new SDProductPointRateSet();
	}

	public SDProductPointRateSet query() {
		return query(null, -1, -1);
	}

	public SDProductPointRateSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public SDProductPointRateSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public SDProductPointRateSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (SDProductPointRateSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){ProductID = (String)v;return;}
		if (i == 1){GivePoints = (String)v;return;}
		if (i == 2){BuyPoints = (String)v;return;}
		if (i == 3){prop1 = (String)v;return;}
		if (i == 4){prop2 = (String)v;return;}
		if (i == 5){prop3 = (String)v;return;}
		if (i == 6){MakeUser = (String)v;return;}
		if (i == 7){MakeDate = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ProductID;}
		if (i == 1){return GivePoints;}
		if (i == 2){return BuyPoints;}
		if (i == 3){return prop1;}
		if (i == 4){return prop2;}
		if (i == 5){return prop3;}
		if (i == 6){return MakeUser;}
		if (i == 7){return MakeDate;}
		return null;
	}

	/**
	* 获取字段ProductID的值，该字段的<br>
	* 字段名称 :产品ID<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getProductID() {
		return ProductID;
	}

	/**
	* 设置字段ProductID的值，该字段的<br>
	* 字段名称 :产品ID<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setProductID(String productID) {
		this.ProductID = productID;
    }

	/**
	* 获取字段GivePoints的值，该字段的<br>
	* 字段名称 :产品赠送积分<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getGivePoints() {
		return GivePoints;
	}

	/**
	* 设置字段GivePoints的值，该字段的<br>
	* 字段名称 :产品赠送积分<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setGivePoints(String givePoints) {
		this.GivePoints = givePoints;
    }

	/**
	* 获取字段BuyPoints的值，该字段的<br>
	* 字段名称 :产品购买抵制积分<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getBuyPoints() {
		return BuyPoints;
	}

	/**
	* 设置字段BuyPoints的值，该字段的<br>
	* 字段名称 :产品购买抵制积分<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setBuyPoints(String buyPoints) {
		this.BuyPoints = buyPoints;
    }

	/**
	* 获取字段prop1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop1() {
		return prop1;
	}

	/**
	* 设置字段prop1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop1(String prop1) {
		this.prop1 = prop1;
    }

	/**
	* 获取字段prop2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop2() {
		return prop2;
	}

	/**
	* 设置字段prop2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop2(String prop2) {
		this.prop2 = prop2;
    }

	/**
	* 获取字段prop3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop3() {
		return prop3;
	}

	/**
	* 设置字段prop3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop3(String prop3) {
		this.prop3 = prop3;
    }

	/**
	* 获取字段MakeUser的值，该字段的<br>
	* 字段名称 :操作人<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMakeUser() {
		return MakeUser;
	}

	/**
	* 设置字段MakeUser的值，该字段的<br>
	* 字段名称 :操作人<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMakeUser(String makeUser) {
		this.MakeUser = makeUser;
    }

	/**
	* 获取字段MakeDate的值，该字段的<br>
	* 字段名称 :操作日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getMakeDate() {
		return MakeDate;
	}

	/**
	* 设置字段MakeDate的值，该字段的<br>
	* 字段名称 :操作日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMakeDate(Date makeDate) {
		this.MakeDate = makeDate;
    }

}