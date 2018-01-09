package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：SDMarketComment<br>
 * 表代码：SDMarketComment<br>
 * 表主键：ID<br>
 */
public class SDMarketCommentSchema extends Schema {
	private String ID;

	private String ModuleId;

	private String MarketID;

	private String CommentContent;

	private Date RoundDate;

	private String Prop1;

	private String Prop2;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.STRING, 0, 10 , 0 , true , true),
		new SchemaColumn("ModuleId", DataColumn.STRING, 1, 10 , 0 , false , false),
		new SchemaColumn("MarketID", DataColumn.STRING, 2, 10 , 0 , false , false),
		new SchemaColumn("CommentContent", DataColumn.STRING, 3, 1000 , 0 , false , false),
		new SchemaColumn("RoundDate", DataColumn.DATETIME, 4, 0 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 5, 200 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 6, 200 , 0 , false , false)
	};

	public static final String _TableCode = "SDMarketComment";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into SDMarketComment values(?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update SDMarketComment set ID=?,ModuleId=?,MarketID=?,CommentContent=?,RoundDate=?,Prop1=?,Prop2=? where ID=?";

	protected static final String _DeleteSQL = "delete from SDMarketComment  where ID=?";

	protected static final String _FillAllSQL = "select * from SDMarketComment  where ID=?";

	public SDMarketCommentSchema(){
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
		return new SDMarketCommentSchema();
	}

	protected SchemaSet newSet(){
		return new SDMarketCommentSet();
	}

	public SDMarketCommentSet query() {
		return query(null, -1, -1);
	}

	public SDMarketCommentSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public SDMarketCommentSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public SDMarketCommentSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (SDMarketCommentSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){ID = (String)v;return;}
		if (i == 1){ModuleId = (String)v;return;}
		if (i == 2){MarketID = (String)v;return;}
		if (i == 3){CommentContent = (String)v;return;}
		if (i == 4){RoundDate = (Date)v;return;}
		if (i == 5){Prop1 = (String)v;return;}
		if (i == 6){Prop2 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return ModuleId;}
		if (i == 2){return MarketID;}
		if (i == 3){return CommentContent;}
		if (i == 4){return RoundDate;}
		if (i == 5){return Prop1;}
		if (i == 6){return Prop2;}
		return null;
	}

	/**
	* 获取字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :VARCHAR(10)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getID() {
		return ID;
	}

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :VARCHAR(10)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setID(String iD) {
		this.ID = iD;
    }

	/**
	* 获取字段ModuleId的值，该字段的<br>
	* 字段名称 :ModuleId<br>
	* 数据类型 :VARCHAR(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getModuleId() {
		return ModuleId;
	}

	/**
	* 设置字段ModuleId的值，该字段的<br>
	* 字段名称 :ModuleId<br>
	* 数据类型 :VARCHAR(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModuleId(String moduleId) {
		this.ModuleId = moduleId;
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
	* 获取字段CommentContent的值，该字段的<br>
	* 字段名称 :CommentContent<br>
	* 数据类型 :VARCHAR(1000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCommentContent() {
		return CommentContent;
	}

	/**
	* 设置字段CommentContent的值，该字段的<br>
	* 字段名称 :CommentContent<br>
	* 数据类型 :VARCHAR(1000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCommentContent(String commentContent) {
		this.CommentContent = commentContent;
    }

	/**
	* 获取字段RoundDate的值，该字段的<br>
	* 字段名称 :RoundDate<br>
	* 数据类型 :DATETIME<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getRoundDate() {
		return RoundDate;
	}

	/**
	* 设置字段RoundDate的值，该字段的<br>
	* 字段名称 :RoundDate<br>
	* 数据类型 :DATETIME<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRoundDate(Date roundDate) {
		this.RoundDate = roundDate;
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