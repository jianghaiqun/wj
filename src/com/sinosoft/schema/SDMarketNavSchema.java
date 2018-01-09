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
 * 表名称：SDMarketNav<br>
 * 表代码：SDMarketNav<br>
 * 表主键：MarketID<br>
 */
public class SDMarketNavSchema extends Schema {
	private String MarketID;

	private String NavDetail;

	private Date CreateDate;

	private Date ModifyDate;

	private String Operater;

	private String Prop1;

	private String Prop2;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("MarketID", DataColumn.STRING, 0, 10 , 0 , true , true),
		new SchemaColumn("NavDetail", DataColumn.STRING, 1, 5000 , 0 , false , false),
		new SchemaColumn("CreateDate", DataColumn.DATETIME, 2, 0 , 0 , false , false),
		new SchemaColumn("ModifyDate", DataColumn.DATETIME, 3, 0 , 0 , false , false),
		new SchemaColumn("Operater", DataColumn.STRING, 4, 20 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 5, 200 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 6, 200 , 0 , false , false)
	};

	public static final String _TableCode = "SDMarketNav";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into SDMarketNav values(?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update SDMarketNav set MarketID=?,NavDetail=?,CreateDate=?,ModifyDate=?,Operater=?,Prop1=?,Prop2=? where MarketID=?";

	protected static final String _DeleteSQL = "delete from SDMarketNav  where MarketID=?";

	protected static final String _FillAllSQL = "select * from SDMarketNav  where MarketID=?";

	public SDMarketNavSchema(){
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
		return new SDMarketNavSchema();
	}

	protected SchemaSet newSet(){
		return new SDMarketNavSet();
	}

	public SDMarketNavSet query() {
		return query(null, -1, -1);
	}

	public SDMarketNavSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public SDMarketNavSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public SDMarketNavSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (SDMarketNavSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){MarketID = (String)v;return;}
		if (i == 1){NavDetail = (String)v;return;}
		if (i == 2){CreateDate = (Date)v;return;}
		if (i == 3){ModifyDate = (Date)v;return;}
		if (i == 4){Operater = (String)v;return;}
		if (i == 5){Prop1 = (String)v;return;}
		if (i == 6){Prop2 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return MarketID;}
		if (i == 1){return NavDetail;}
		if (i == 2){return CreateDate;}
		if (i == 3){return ModifyDate;}
		if (i == 4){return Operater;}
		if (i == 5){return Prop1;}
		if (i == 6){return Prop2;}
		return null;
	}

	/**
	* 获取字段MarketID的值，该字段的<br>
	* 字段名称 :MarketID<br>
	* 数据类型 :VARCHAR(10)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getMarketID() {
		return MarketID;
	}

	/**
	* 设置字段MarketID的值，该字段的<br>
	* 字段名称 :MarketID<br>
	* 数据类型 :VARCHAR(10)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setMarketID(String marketID) {
		this.MarketID = marketID;
    }

	/**
	* 获取字段NavDetail的值，该字段的<br>
	* 字段名称 :NavDetail<br>
	* 数据类型 :VARCHAR(5000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getNavDetail() {
		return NavDetail;
	}

	/**
	* 设置字段NavDetail的值，该字段的<br>
	* 字段名称 :NavDetail<br>
	* 数据类型 :VARCHAR(5000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setNavDetail(String navDetail) {
		this.NavDetail = navDetail;
    }

	/**
	* 获取字段CreateDate的值，该字段的<br>
	* 字段名称 :CreateDate<br>
	* 数据类型 :DATETIME<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getCreateDate() {
		return CreateDate;
	}

	/**
	* 设置字段CreateDate的值，该字段的<br>
	* 字段名称 :CreateDate<br>
	* 数据类型 :DATETIME<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCreateDate(Date createDate) {
		this.CreateDate = createDate;
    }

	/**
	* 获取字段ModifyDate的值，该字段的<br>
	* 字段名称 :ModifyDate<br>
	* 数据类型 :DATETIME<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getModifyDate() {
		return ModifyDate;
	}

	/**
	* 设置字段ModifyDate的值，该字段的<br>
	* 字段名称 :ModifyDate<br>
	* 数据类型 :DATETIME<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyDate(Date modifyDate) {
		this.ModifyDate = modifyDate;
    }

	/**
	* 获取字段Operater的值，该字段的<br>
	* 字段名称 :Operater<br>
	* 数据类型 :VARCHAR(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getOperater() {
		return Operater;
	}

	/**
	* 设置字段Operater的值，该字段的<br>
	* 字段名称 :Operater<br>
	* 数据类型 :VARCHAR(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setOperater(String operater) {
		this.Operater = operater;
    }

	/**
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :Prop1<br>
	* 数据类型 :VARCHAR(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :Prop1<br>
	* 数据类型 :VARCHAR(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* 获取字段Prop2的值，该字段的<br>
	* 字段名称 :Prop2<br>
	* 数据类型 :VARCHAR(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* 设置字段Prop2的值，该字段的<br>
	* 字段名称 :Prop2<br>
	* 数据类型 :VARCHAR(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

}