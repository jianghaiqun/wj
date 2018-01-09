package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：内容管理-提问表<br>
 * 表代码：SCQuestion<br>
 * 表主键：Id<br>
 */
public class SCQuestionSchema extends Schema {
	private String Id;

	private String QuestionId;

	private String UserId;

	private String AddUserIP;

	private String Title;

	private String Content;

	private String State;

	private Date AddDate;

	private String Type;

	private String aState;

	private Date aDate;

	private String Operator;

	private String url;

	private String CatalogId;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("Id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("QuestionId", DataColumn.STRING, 1, 32 , 0 , false , false),
		new SchemaColumn("UserId", DataColumn.STRING, 2, 32 , 0 , true , false),
		new SchemaColumn("AddUserIP", DataColumn.STRING, 3, 20 , 0 , true , false),
		new SchemaColumn("Title", DataColumn.STRING, 4, 255 , 0 , true , false),
		new SchemaColumn("Content", DataColumn.STRING, 5, 255 , 0 , false , false),
		new SchemaColumn("State", DataColumn.STRING, 6, 2 , 0 , true , false),
		new SchemaColumn("AddDate", DataColumn.DATETIME, 7, 0 , 0 , true , false),
		new SchemaColumn("Type", DataColumn.STRING, 8, 2 , 0 , true , false),
		new SchemaColumn("aState", DataColumn.STRING, 9, 2 , 0 , false , false),
		new SchemaColumn("aDate", DataColumn.DATETIME, 10, 0 , 0 , false , false),
		new SchemaColumn("Operator", DataColumn.STRING, 11, 100 , 0 , false , false),
		new SchemaColumn("url", DataColumn.STRING, 12, 255 , 0 , false , false),
		new SchemaColumn("CatalogId", DataColumn.STRING, 13, 20 , 0 , false , false)
	};

	public static final String _TableCode = "SCQuestion";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into SCQuestion values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update SCQuestion set Id=?,QuestionId=?,UserId=?,AddUserIP=?,Title=?,Content=?,State=?,AddDate=?,Type=?,aState=?,aDate=?,Operator=?,url=?,CatalogId=? where Id=?";

	protected static final String _DeleteSQL = "delete from SCQuestion  where Id=?";

	protected static final String _FillAllSQL = "select * from SCQuestion  where Id=?";

	public SCQuestionSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[14];
	}

	protected Schema newInstance(){
		return new SCQuestionSchema();
	}

	protected SchemaSet newSet(){
		return new SCQuestionSet();
	}

	public SCQuestionSet query() {
		return query(null, -1, -1);
	}

	public SCQuestionSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public SCQuestionSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public SCQuestionSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (SCQuestionSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){Id = (String)v;return;}
		if (i == 1){QuestionId = (String)v;return;}
		if (i == 2){UserId = (String)v;return;}
		if (i == 3){AddUserIP = (String)v;return;}
		if (i == 4){Title = (String)v;return;}
		if (i == 5){Content = (String)v;return;}
		if (i == 6){State = (String)v;return;}
		if (i == 7){AddDate = (Date)v;return;}
		if (i == 8){Type = (String)v;return;}
		if (i == 9){aState = (String)v;return;}
		if (i == 10){aDate = (Date)v;return;}
		if (i == 11){Operator = (String)v;return;}
		if (i == 12){url = (String)v;return;}
		if (i == 13){CatalogId = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return Id;}
		if (i == 1){return QuestionId;}
		if (i == 2){return UserId;}
		if (i == 3){return AddUserIP;}
		if (i == 4){return Title;}
		if (i == 5){return Content;}
		if (i == 6){return State;}
		if (i == 7){return AddDate;}
		if (i == 8){return Type;}
		if (i == 9){return aState;}
		if (i == 10){return aDate;}
		if (i == 11){return Operator;}
		if (i == 12){return url;}
		if (i == 13){return CatalogId;}
		return null;
	}

	/**
	* 获取字段Id的值，该字段的<br>
	* 字段名称 :流水号<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getId() {
		return Id;
	}

	/**
	* 设置字段Id的值，该字段的<br>
	* 字段名称 :流水号<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setId(String id) {
		this.Id = id;
    }

	/**
	* 获取字段QuestionId的值，该字段的<br>
	* 字段名称 :问题编码<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getQuestionId() {
		return QuestionId;
	}

	/**
	* 设置字段QuestionId的值，该字段的<br>
	* 字段名称 :问题编码<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setQuestionId(String questionId) {
		this.QuestionId = questionId;
    }

	/**
	* 获取字段UserId的值，该字段的<br>
	* 字段名称 :用户id<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getUserId() {
		return UserId;
	}

	/**
	* 设置字段UserId的值，该字段的<br>
	* 字段名称 :用户id<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setUserId(String userId) {
		this.UserId = userId;
    }

	/**
	* 获取字段AddUserIP的值，该字段的<br>
	* 字段名称 :用户ip<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getAddUserIP() {
		return AddUserIP;
	}

	/**
	* 设置字段AddUserIP的值，该字段的<br>
	* 字段名称 :用户ip<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setAddUserIP(String addUserIP) {
		this.AddUserIP = addUserIP;
    }

	/**
	* 获取字段Title的值，该字段的<br>
	* 字段名称 :问题题目<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getTitle() {
		return Title;
	}

	/**
	* 设置字段Title的值，该字段的<br>
	* 字段名称 :问题题目<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setTitle(String title) {
		this.Title = title;
    }

	/**
	* 获取字段Content的值，该字段的<br>
	* 字段名称 :问题内容<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getContent() {
		return Content;
	}

	/**
	* 设置字段Content的值，该字段的<br>
	* 字段名称 :问题内容<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setContent(String content) {
		this.Content = content;
    }

	/**
	* 获取字段State的值，该字段的<br>
	* 字段名称 :提问状态<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getState() {
		return State;
	}

	/**
	* 设置字段State的值，该字段的<br>
	* 字段名称 :提问状态<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setState(String state) {
		this.State = state;
    }

	/**
	* 获取字段AddDate的值，该字段的<br>
	* 字段名称 :提问时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public Date getAddDate() {
		return AddDate;
	}

	/**
	* 设置字段AddDate的值，该字段的<br>
	* 字段名称 :提问时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setAddDate(Date addDate) {
		this.AddDate = addDate;
    }

	/**
	* 获取字段Type的值，该字段的<br>
	* 字段名称 :问题分类<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getType() {
		return Type;
	}

	/**
	* 设置字段Type的值，该字段的<br>
	* 字段名称 :问题分类<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setType(String type) {
		this.Type = type;
    }

	/**
	* 获取字段aState的值，该字段的<br>
	* 字段名称 :审核状态<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	00 未审核 01通过 02未通过 03删除<br>
	*/
	public String getaState() {
		return aState;
	}

	/**
	* 设置字段aState的值，该字段的<br>
	* 字段名称 :审核状态<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	00 未审核 01通过 02未通过 03删除<br>
	*/
	public void setaState(String aState) {
		this.aState = aState;
    }

	/**
	* 获取字段aDate的值，该字段的<br>
	* 字段名称 :审核时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getaDate() {
		return aDate;
	}

	/**
	* 设置字段aDate的值，该字段的<br>
	* 字段名称 :审核时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setaDate(Date aDate) {
		this.aDate = aDate;
    }

	/**
	* 获取字段Operator的值，该字段的<br>
	* 字段名称 :操作员<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getOperator() {
		return Operator;
	}

	/**
	* 设置字段Operator的值，该字段的<br>
	* 字段名称 :操作员<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setOperator(String operator) {
		this.Operator = operator;
    }

	/**
	* 获取字段url的值，该字段的<br>
	* 字段名称 :Column_13<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String geturl() {
		return url;
	}

	/**
	* 设置字段url的值，该字段的<br>
	* 字段名称 :Column_13<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void seturl(String url) {
		this.url = url;
    }

	/**
	* 获取字段CatalogId的值，该字段的<br>
	* 字段名称 :栏目id<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCatalogId() {
		return CatalogId;
	}

	/**
	* 设置字段CatalogId的值，该字段的<br>
	* 字段名称 :栏目id<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	
	public void setCatalogId(String catalogId) {
		this.CatalogId = catalogId;
    }

}