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

import java.util.Date;

import java.util.Date;

/**
 * 表名称：SDProductHighlights<br>
 * 表代码：SDProductHighlights<br>
 * 表主键：Id<br>
 */
public class SDProductHighlightsSchema extends Schema {
	private String Id;

	private String ProductID;

	private String Detail;

	private Date CreateDate;

	private Date ModifyDate;

	private String Operater;

	private String Prop1;

	private String Prop2;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("Id", DataColumn.STRING, 0, 10 , 0 , true , true),
		new SchemaColumn("ProductID", DataColumn.STRING, 1, 10 , 0 , false , false),
		new SchemaColumn("Detail", DataColumn.STRING, 2, 100 , 0 , false , false),
		new SchemaColumn("CreateDate", DataColumn.DATETIME, 3, 0 , 0 , false , false),
		new SchemaColumn("ModifyDate", DataColumn.DATETIME, 4, 0 , 0 , false , false),
		new SchemaColumn("Operater", DataColumn.STRING, 5, 20 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 6, 200 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 7, 200 , 0 , false , false)
	};

	public static final String _TableCode = "SDProductHighlights";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into SDProductHighlights values(?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update SDProductHighlights set Id=?,ProductID=?,Detail=?,CreateDate=?,ModifyDate=?,Operater=?,Prop1=?,Prop2=? where Id=?";

	protected static final String _DeleteSQL = "delete from SDProductHighlights  where Id=?";

	protected static final String _FillAllSQL = "select * from SDProductHighlights  where Id=?";

	public SDProductHighlightsSchema(){
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
		return new SDProductHighlightsSchema();
	}

	protected SchemaSet newSet(){
		return new SDProductHighlightsSet();
	}

	public SDProductHighlightsSet query() {
		return query(null, -1, -1);
	}

	public SDProductHighlightsSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public SDProductHighlightsSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public SDProductHighlightsSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (SDProductHighlightsSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){Id = (String)v;return;}
		if (i == 1){ProductID = (String)v;return;}
		if (i == 2){Detail = (String)v;return;}
		if (i == 3){CreateDate = (Date)v;return;}
		if (i == 4){ModifyDate = (Date)v;return;}
		if (i == 5){Operater = (String)v;return;}
		if (i == 6){Prop1 = (String)v;return;}
		if (i == 7){Prop2 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return Id;}
		if (i == 1){return ProductID;}
		if (i == 2){return Detail;}
		if (i == 3){return CreateDate;}
		if (i == 4){return ModifyDate;}
		if (i == 5){return Operater;}
		if (i == 6){return Prop1;}
		if (i == 7){return Prop2;}
		return null;
	}

	/**
	* 获取字段Id的值，该字段的<br>
	* 字段名称 :Id<br>
	* 数据类型 :VARCHAR(10)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getId() {
		return Id;
	}

	/**
	* 设置字段Id的值，该字段的<br>
	* 字段名称 :Id<br>
	* 数据类型 :VARCHAR(10)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setId(String id) {
		this.Id = id;
    }

	/**
	* 获取字段ProductID的值，该字段的<br>
	* 字段名称 :ProductID<br>
	* 数据类型 :VARCHAR(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProductID() {
		return ProductID;
	}

	/**
	* 设置字段ProductID的值，该字段的<br>
	* 字段名称 :ProductID<br>
	* 数据类型 :VARCHAR(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProductID(String productID) {
		this.ProductID = productID;
    }

	/**
	* 获取字段Detail的值，该字段的<br>
	* 字段名称 :Detail<br>
	* 数据类型 :VARCHAR(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getDetail() {
		return Detail;
	}

	/**
	* 设置字段Detail的值，该字段的<br>
	* 字段名称 :Detail<br>
	* 数据类型 :VARCHAR(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDetail(String detail) {
		this.Detail = detail;
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