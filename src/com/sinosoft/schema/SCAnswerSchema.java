package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

import java.util.Date;

/**
 * 表名称：内容管理-解答表<br>
 * 表代码：SCAnswer<br>
 * 表主键：Id<br>
 */
public class SCAnswerSchema extends Schema {
	private String Id;

	private String QuestionId;

	private String QuestionTitle;

	private String UserId;

	private String AddUserIP;

	private String Content;

	private Date AddDate;

	private String aState;

	private Date aDate;

	private String Operator;

	private String url;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("Id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("QuestionId", DataColumn.STRING, 1, 32 , 0 , true , false),
		new SchemaColumn("QuestionTitle", DataColumn.STRING, 2, 255 , 0 , false , false),
		new SchemaColumn("UserId", DataColumn.STRING, 3, 32 , 0 , true , false),
		new SchemaColumn("AddUserIP", DataColumn.STRING, 4, 20 , 0 , true , false),
		new SchemaColumn("Content", DataColumn.STRING, 5, 255 , 0 , true , false),
		new SchemaColumn("AddDate", DataColumn.DATETIME, 6, 0 , 0 , true , false),
		new SchemaColumn("aState", DataColumn.STRING, 7, 2 , 0 , false , false),
		new SchemaColumn("aDate", DataColumn.DATETIME, 8, 0 , 0 , false , false),
		new SchemaColumn("Operator", DataColumn.STRING, 9, 100 , 0 , false , false),
		new SchemaColumn("url", DataColumn.STRING, 10, 255 , 0 , false , false)
	};

	public static final String _TableCode = "SCAnswer";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into SCAnswer values(?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update SCAnswer set Id=?,QuestionId=?,QuestionTitle=?,UserId=?,AddUserIP=?,Content=?,AddDate=?,aState=?,aDate=?,Operator=?,url=? where Id=?";

	protected static final String _DeleteSQL = "delete from SCAnswer  where Id=?";

	protected static final String _FillAllSQL = "select * from SCAnswer  where Id=?";

	public SCAnswerSchema(){
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
		return new SCAnswerSchema();
	}

	protected SchemaSet newSet(){
		return new SCAnswerSet();
	}

	public SCAnswerSet query() {
		return query(null, -1, -1);
	}

	public SCAnswerSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public SCAnswerSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public SCAnswerSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (SCAnswerSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){Id = (String)v;return;}
		if (i == 1){QuestionId = (String)v;return;}
		if (i == 2){QuestionTitle = (String)v;return;}
		if (i == 3){UserId = (String)v;return;}
		if (i == 4){AddUserIP = (String)v;return;}
		if (i == 5){Content = (String)v;return;}
		if (i == 6){AddDate = (Date)v;return;}
		if (i == 7){aState = (String)v;return;}
		if (i == 8){aDate = (Date)v;return;}
		if (i == 9){Operator = (String)v;return;}
		if (i == 10){url = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return Id;}
		if (i == 1){return QuestionId;}
		if (i == 2){return QuestionTitle;}
		if (i == 3){return UserId;}
		if (i == 4){return AddUserIP;}
		if (i == 5){return Content;}
		if (i == 6){return AddDate;}
		if (i == 7){return aState;}
		if (i == 8){return aDate;}
		if (i == 9){return Operator;}
		if (i == 10){return url;}
		return null;
	}

	/**
	* 获取字段Id的值，该字段的<br>
	* 字段名称 :答案编码<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getId() {
		return Id;
	}

	/**
	* 设置字段Id的值，该字段的<br>
	* 字段名称 :答案编码<br>
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
	* 是否必填 :true<br>
	*/
	public String getQuestionId() {
		return QuestionId;
	}

	/**
	* 设置字段QuestionId的值，该字段的<br>
	* 字段名称 :问题编码<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setQuestionId(String questionId) {
		this.QuestionId = questionId;
    }

	/**
	* 获取字段QuestionTitle的值，该字段的<br>
	* 字段名称 :问题题目<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getQuestionTitle() {
		return QuestionTitle;
	}

	/**
	* 设置字段QuestionTitle的值，该字段的<br>
	* 字段名称 :问题题目<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setQuestionTitle(String questionTitle) {
		this.QuestionTitle = questionTitle;
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
	* 获取字段Content的值，该字段的<br>
	* 字段名称 :答案内容<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getContent() {
		return Content;
	}

	/**
	* 设置字段Content的值，该字段的<br>
	* 字段名称 :答案内容<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setContent(String content) {
		this.Content = content;
    }

	/**
	* 获取字段AddDate的值，该字段的<br>
	* 字段名称 :回答时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public Date getAddDate() {
		return AddDate;
	}

	/**
	* 设置字段AddDate的值，该字段的<br>
	* 字段名称 :回答时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setAddDate(Date addDate) {
		this.AddDate = addDate;
    }

	/**
	* 获取字段aState的值，该字段的<br>
	* 字段名称 :采纳状态<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	00 未采纳 01 已采纳<br>
	*/
	public String getaState() {
		return aState;
	}

	/**
	* 设置字段aState的值，该字段的<br>
	* 字段名称 :采纳状态<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	00 未采纳 01 已采纳<br>
	*/
	public void setaState(String aState) {
		this.aState = aState;
    }

	/**
	* 获取字段aDate的值，该字段的<br>
	* 字段名称 :采纳时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getaDate() {
		return aDate;
	}

	/**
	* 设置字段aDate的值，该字段的<br>
	* 字段名称 :采纳时间<br>
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
	* 字段名称 :Column_11<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String geturl() {
		return url;
	}

	/**
	* 设置字段url的值，该字段的<br>
	* 字段名称 :Column_11<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void seturl(String url) {
		this.url = url;
    }

}