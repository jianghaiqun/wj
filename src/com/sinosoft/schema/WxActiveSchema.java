package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.orm.SchemaSet;

/**
 * 表名称：微信活动信息表<br>
 * 表代码：WxActive<br>
 * 表主键：ID<br>
 */
public class WxActiveSchema extends Schema {
	private String ID;

	private String Title;

	private String Summery;

	private String Author;

	private String Content;

	private String ActiveStatus;

	private String CreateDate;

	private String ModifyDate;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("Title", DataColumn.STRING, 1, 100 , 0 , false , false),
		new SchemaColumn("Summery", DataColumn.CLOB, 2, 0 , 0 , false , false),
		new SchemaColumn("Author", DataColumn.STRING, 3, 32 , 0 , false , false),
		new SchemaColumn("Content", DataColumn.CLOB, 4, 0 , 0 , false , false),
		new SchemaColumn("ActiveStatus", DataColumn.STRING, 5, 4 , 0 , false , false),
		new SchemaColumn("CreateDate", DataColumn.STRING, 6, 32 , 0 , false , false),
		new SchemaColumn("ModifyDate", DataColumn.STRING, 7, 32 , 0 , false , false)
	};

	public static final String _TableCode = "WxActive";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into WxActive values(?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update WxActive set ID=?,Title=?,Summery=?,Author=?,Content=?,ActiveStatus=?,CreateDate=?,ModifyDate=? where ID=?";

	protected static final String _DeleteSQL = "delete from WxActive  where ID=?";

	protected static final String _FillAllSQL = "select * from WxActive  where ID=?";

	public WxActiveSchema(){
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
		return new WxActiveSchema();
	}

	protected SchemaSet newSet(){
		return new WxActiveSet();
	}

	public WxActiveSet query() {
		return query(null, -1, -1);
	}

	public WxActiveSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public WxActiveSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public WxActiveSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (WxActiveSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){ID = (String)v;return;}
		if (i == 1){Title = (String)v;return;}
		if (i == 2){Summery = (String)v;return;}
		if (i == 3){Author = (String)v;return;}
		if (i == 4){Content = (String)v;return;}
		if (i == 5){ActiveStatus = (String)v;return;}
		if (i == 6){CreateDate = (String)v;return;}
		if (i == 7){ModifyDate = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return Title;}
		if (i == 2){return Summery;}
		if (i == 3){return Author;}
		if (i == 4){return Content;}
		if (i == 5){return ActiveStatus;}
		if (i == 6){return CreateDate;}
		if (i == 7){return ModifyDate;}
		return null;
	}

	/**
	* 获取字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getID() {
		return ID;
	}

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setID(String iD) {
		this.ID = iD;
    }

	/**
	* 获取字段Title的值，该字段的<br>
	* 字段名称 :标题<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getTitle() {
		return Title;
	}

	/**
	* 设置字段Title的值，该字段的<br>
	* 字段名称 :标题<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setTitle(String title) {
		this.Title = title;
    }

	/**
	* 获取字段Summery的值，该字段的<br>
	* 字段名称 :摘要<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSummery() {
		return Summery;
	}

	/**
	* 设置字段Summery的值，该字段的<br>
	* 字段名称 :摘要<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSummery(String summery) {
		this.Summery = summery;
    }

	/**
	* 获取字段Author的值，该字段的<br>
	* 字段名称 :作者<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAuthor() {
		return Author;
	}

	/**
	* 设置字段Author的值，该字段的<br>
	* 字段名称 :作者<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAuthor(String author) {
		this.Author = author;
    }

	/**
	* 获取字段Content的值，该字段的<br>
	* 字段名称 :正文<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getContent() {
		return Content;
	}

	/**
	* 设置字段Content的值，该字段的<br>
	* 字段名称 :正文<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setContent(String content) {
		this.Content = content;
    }

	/**
	* 获取字段ActiveStatus的值，该字段的<br>
	* 字段名称 :活动状态<br>
	* 数据类型 :varchar(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getActiveStatus() {
		return ActiveStatus;
	}

	/**
	* 设置字段ActiveStatus的值，该字段的<br>
	* 字段名称 :活动状态<br>
	* 数据类型 :varchar(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setActiveStatus(String activeStatus) {
		this.ActiveStatus = activeStatus;
    }

	/**
	* 获取字段CreateDate的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCreateDate() {
		return CreateDate;
	}

	/**
	* 设置字段CreateDate的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCreateDate(String createDate) {
		this.CreateDate = createDate;
    }

	/**
	* 获取字段ModifyDate的值，该字段的<br>
	* 字段名称 :修改时间<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getModifyDate() {
		return ModifyDate;
	}

	/**
	* 设置字段ModifyDate的值，该字段的<br>
	* 字段名称 :修改时间<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyDate(String modifyDate) {
		this.ModifyDate = modifyDate;
    }

}