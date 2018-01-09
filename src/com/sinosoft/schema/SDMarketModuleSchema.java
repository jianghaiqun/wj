package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

import java.util.Date;

import java.util.Date;

/**
 * 表名称：SDMarketModule<br>
 * 表代码：SDMarketModule<br>
 * 表主键：ModuleID<br>
 */
public class SDMarketModuleSchema extends Schema {
	private String ModuleID;

	private String MarketID;

	private String TxtHtml;

	private String ProductIDs;

	private Date CreateDate;

	private Date ModifyDate;

	private String Operater;

	private String Prop1;

	private String Prop2;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ModuleID", DataColumn.STRING, 0, 10 , 0 , true , true),
		new SchemaColumn("MarketID", DataColumn.STRING, 1, 10 , 0 , false , false),
		new SchemaColumn("TxtHtml", DataColumn.STRING, 2, 20000 , 0 , false , false),
		new SchemaColumn("ProductIDs", DataColumn.STRING, 3, 500 , 0 , false , false),
		new SchemaColumn("CreateDate", DataColumn.DATETIME, 4, 0 , 0 , false , false),
		new SchemaColumn("ModifyDate", DataColumn.DATETIME, 5, 0 , 0 , false , false),
		new SchemaColumn("Operater", DataColumn.STRING, 6, 20 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 7, 200 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 8, 200 , 0 , false , false)
	};

	public static final String _TableCode = "SDMarketModule";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into SDMarketModule values(?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update SDMarketModule set ModuleID=?,MarketID=?,TxtHtml=?,ProductIDs=?,CreateDate=?,ModifyDate=?,Operater=?,Prop1=?,Prop2=? where ModuleID=?";

	protected static final String _DeleteSQL = "delete from SDMarketModule  where ModuleID=?";

	protected static final String _FillAllSQL = "select * from SDMarketModule  where ModuleID=?";

	public SDMarketModuleSchema(){
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
		return new SDMarketModuleSchema();
	}

	protected SchemaSet newSet(){
		return new SDMarketModuleSet();
	}

	public SDMarketModuleSet query() {
		return query(null, -1, -1);
	}

	public SDMarketModuleSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public SDMarketModuleSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public SDMarketModuleSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (SDMarketModuleSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){ModuleID = (String)v;return;}
		if (i == 1){MarketID = (String)v;return;}
		if (i == 2){TxtHtml = (String)v;return;}
		if (i == 3){ProductIDs = (String)v;return;}
		if (i == 4){CreateDate = (Date)v;return;}
		if (i == 5){ModifyDate = (Date)v;return;}
		if (i == 6){Operater = (String)v;return;}
		if (i == 7){Prop1 = (String)v;return;}
		if (i == 8){Prop2 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ModuleID;}
		if (i == 1){return MarketID;}
		if (i == 2){return TxtHtml;}
		if (i == 3){return ProductIDs;}
		if (i == 4){return CreateDate;}
		if (i == 5){return ModifyDate;}
		if (i == 6){return Operater;}
		if (i == 7){return Prop1;}
		if (i == 8){return Prop2;}
		return null;
	}

	/**
	* 获取字段ModuleID的值，该字段的<br>
	* 字段名称 :ModuleID<br>
	* 数据类型 :VARCHAR(10)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getModuleID() {
		return ModuleID;
	}

	/**
	* 设置字段ModuleID的值，该字段的<br>
	* 字段名称 :ModuleID<br>
	* 数据类型 :VARCHAR(10)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setModuleID(String moduleID) {
		this.ModuleID = moduleID;
    }

	/**
	* 获取字段MarketID的值，该字段的<br>
	* 字段名称 :MarketID<br>
	* 数据类型 :VARCHAR(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMarketID() {
		return MarketID;
	}

	/**
	* 设置字段MarketID的值，该字段的<br>
	* 字段名称 :MarketID<br>
	* 数据类型 :VARCHAR(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMarketID(String marketID) {
		this.MarketID = marketID;
    }

	/**
	* 获取字段TxtHtml的值，该字段的<br>
	* 字段名称 :TxtHtml<br>
	* 数据类型 :VARCHAR(20000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getTxtHtml() {
		return TxtHtml;
	}

	/**
	* 设置字段TxtHtml的值，该字段的<br>
	* 字段名称 :TxtHtml<br>
	* 数据类型 :VARCHAR(20000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setTxtHtml(String txtHtml) {
		this.TxtHtml = txtHtml;
    }

	/**
	* 获取字段ProductIDs的值，该字段的<br>
	* 字段名称 :ProductIDs<br>
	* 数据类型 :VARCHAR(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProductIDs() {
		return ProductIDs;
	}

	/**
	* 设置字段ProductIDs的值，该字段的<br>
	* 字段名称 :ProductIDs<br>
	* 数据类型 :VARCHAR(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProductIDs(String productIDs) {
		this.ProductIDs = productIDs;
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