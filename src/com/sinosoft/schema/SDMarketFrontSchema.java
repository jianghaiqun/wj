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
 * 表名称：SDMarketFront<br>
 * 表代码：SDMarketFront<br>
 * 表主键：MarketID<br>
 */
public class SDMarketFrontSchema extends Schema {
	private String MarketID;

	private String HtmlDetail;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("MarketID", DataColumn.STRING, 0, 10 , 0 , true , true),
		new SchemaColumn("HtmlDetail", DataColumn.STRING, 1, 18000 , 0 , false , false)
	};

	public static final String _TableCode = "SDMarketFront";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into SDMarketFront values(?,?)";

	protected static final String _UpdateAllSQL = "update SDMarketFront set MarketID=?,HtmlDetail=? where MarketID=?";

	protected static final String _DeleteSQL = "delete from SDMarketFront  where MarketID=?";

	protected static final String _FillAllSQL = "select * from SDMarketFront  where MarketID=?";

	public SDMarketFrontSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[2];
	}

	protected Schema newInstance(){
		return new SDMarketFrontSchema();
	}

	protected SchemaSet newSet(){
		return new SDMarketFrontSet();
	}

	public SDMarketFrontSet query() {
		return query(null, -1, -1);
	}

	public SDMarketFrontSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public SDMarketFrontSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public SDMarketFrontSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (SDMarketFrontSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){MarketID = (String)v;return;}
		if (i == 1){HtmlDetail = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return MarketID;}
		if (i == 1){return HtmlDetail;}
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
	* 获取字段HtmlDetail的值，该字段的<br>
	* 字段名称 :HtmlDetail<br>
	* 数据类型 :VARCHAR(18000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getHtmlDetail() {
		return HtmlDetail;
	}

	/**
	* 设置字段HtmlDetail的值，该字段的<br>
	* 字段名称 :HtmlDetail<br>
	* 数据类型 :VARCHAR(18000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setHtmlDetail(String htmlDetail) {
		this.HtmlDetail = htmlDetail;
    }

}