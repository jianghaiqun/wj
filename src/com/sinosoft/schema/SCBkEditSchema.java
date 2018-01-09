package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;
import java.util.Date;

/**
 * 表名称：内容管理-百科词条用户修改表<br>
 * 表代码：SCBkEdit<br>
 * 表主键：Id<br>
 */
public class SCBkEditSchema extends Schema {
	private String Id;

	private String TitleId;

	private String EntryContent;

	private String UserId;

	private String AddUserIP;

	private Date CreateDate;

	private String aState;

	private Date aDate;

	private String Operator;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("Id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("TitleId", DataColumn.STRING, 1, 32 , 0 , false , false),
		new SchemaColumn("EntryContent", DataColumn.CLOB, 2, 0 , 0 , false , false),
		new SchemaColumn("UserId", DataColumn.STRING, 3, 32 , 0 , true , false),
		new SchemaColumn("AddUserIP", DataColumn.STRING, 4, 20 , 0 , true , false),
		new SchemaColumn("CreateDate", DataColumn.DATETIME, 5, 0 , 0 , true , false),
		new SchemaColumn("aState", DataColumn.STRING, 6, 2 , 0 , false , false),
		new SchemaColumn("aDate", DataColumn.DATETIME, 7, 0 , 0 , false , false),
		new SchemaColumn("Operator", DataColumn.STRING, 8, 100 , 0 , false , false)
	};

	public static final String _TableCode = "SCBkEdit";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into SCBkEdit values(?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update SCBkEdit set Id=?,TitleId=?,EntryContent=?,UserId=?,AddUserIP=?,CreateDate=?,aState=?,aDate=?,Operator=? where Id=?";

	protected static final String _DeleteSQL = "delete from SCBkEdit  where Id=?";

	protected static final String _FillAllSQL = "select * from SCBkEdit  where Id=?";

	public SCBkEditSchema(){
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
		return new SCBkEditSchema();
	}

	protected SchemaSet newSet(){
		return new SCBkEditSet();
	}

	public SCBkEditSet query() {
		return query(null, -1, -1);
	}

	public SCBkEditSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public SCBkEditSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public SCBkEditSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (SCBkEditSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){Id = (String)v;return;}
		if (i == 1){TitleId = (String)v;return;}
		if (i == 2){EntryContent = (String)v;return;}
		if (i == 3){UserId = (String)v;return;}
		if (i == 4){AddUserIP = (String)v;return;}
		if (i == 5){CreateDate = (Date)v;return;}
		if (i == 6){aState = (String)v;return;}
		if (i == 7){aDate = (Date)v;return;}
		if (i == 8){Operator = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return Id;}
		if (i == 1){return TitleId;}
		if (i == 2){return EntryContent;}
		if (i == 3){return UserId;}
		if (i == 4){return AddUserIP;}
		if (i == 5){return CreateDate;}
		if (i == 6){return aState;}
		if (i == 7){return aDate;}
		if (i == 8){return Operator;}
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
	* 获取字段TitleId的值，该字段的<br>
	* 字段名称 :内部编码<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getTitleId() {
		return TitleId;
	}

	/**
	* 设置字段TitleId的值，该字段的<br>
	* 字段名称 :内部编码<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setTitleId(String titleId) {
		this.TitleId = titleId;
    }

	/**
	* 获取字段EntryContent的值，该字段的<br>
	* 字段名称 :词条内容<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getEntryContent() {
		return EntryContent;
	}

	/**
	* 设置字段EntryContent的值，该字段的<br>
	* 字段名称 :词条内容<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setEntryContent(String entryContent) {
		this.EntryContent = entryContent;
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
	* 获取字段CreateDate的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public Date getCreateDate() {
		return CreateDate;
	}

	/**
	* 设置字段CreateDate的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setCreateDate(Date createDate) {
		this.CreateDate = createDate;
    }

	/**
	* 获取字段aState的值，该字段的<br>
	* 字段名称 :审核状态<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
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

}