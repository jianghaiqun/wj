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
 * 表名称：SDMarketProduct<br>
 * 表代码：SDMarketProduct<br>
 * 表主键：ProductID<br>
 */
public class SDMarketProductSchema extends Schema {
	private String ProductID;

	private String ProDetailHtml;

	private String Prop1;

	private String Prop2;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ProductID", DataColumn.STRING, 0, 20 , 0 , true , true),
		new SchemaColumn("ProDetailHtml", DataColumn.STRING, 1, 0 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 2, 200 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 3, 200 , 0 , false , false)
	};

	public static final String _TableCode = "SDMarketProduct";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into SDMarketProduct values(?,?,?,?)";

	protected static final String _UpdateAllSQL = "update SDMarketProduct set ProductID=?,ProDetailHtml=?,Prop1=?,Prop2=? where ProductID=?";

	protected static final String _DeleteSQL = "delete from SDMarketProduct  where ProductID=?";

	protected static final String _FillAllSQL = "select * from SDMarketProduct  where ProductID=?";

	public SDMarketProductSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[4];
	}

	protected Schema newInstance(){
		return new SDMarketProductSchema();
	}

	protected SchemaSet newSet(){
		return new SDMarketProductSet();
	}

	public SDMarketProductSet query() {
		return query(null, -1, -1);
	}

	public SDMarketProductSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public SDMarketProductSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public SDMarketProductSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (SDMarketProductSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){ProductID = (String)v;return;}
		if (i == 1){ProDetailHtml = (String)v;return;}
		if (i == 2){Prop1 = (String)v;return;}
		if (i == 3){Prop2 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ProductID;}
		if (i == 1){return ProDetailHtml;}
		if (i == 2){return Prop1;}
		if (i == 3){return Prop2;}
		return null;
	}

	/**
	* 获取字段ProductID的值，该字段的<br>
	* 字段名称 :ProductID<br>
	* 数据类型 :VARCHAR(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getProductID() {
		return ProductID;
	}

	/**
	* 设置字段ProductID的值，该字段的<br>
	* 字段名称 :ProductID<br>
	* 数据类型 :VARCHAR(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setProductID(String productID) {
		this.ProductID = productID;
    }

	/**
	* 获取字段ProDetailHtml的值，该字段的<br>
	* 字段名称 :ProDetailHtml<br>
	* 数据类型 :LONGTEXT<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProDetailHtml() {
		return ProDetailHtml;
	}

	/**
	* 设置字段ProDetailHtml的值，该字段的<br>
	* 字段名称 :ProDetailHtml<br>
	* 数据类型 :LONGTEXT<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProDetailHtml(String proDetailHtml) {
		this.ProDetailHtml = proDetailHtml;
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