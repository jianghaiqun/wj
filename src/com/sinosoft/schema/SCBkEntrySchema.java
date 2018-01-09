package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;
import java.util.Date;

/**
 * 表名称：内容管理-百科词条表<br>
 * 表代码：SCBkEntry<br>
 * 表主键：Id<br>
 */
public class SCBkEntrySchema extends Schema {
	private String Id;

	private String EntryId;

	private String EntryName;

	private String EntryType;

	private String EntryContent;

	private String UserId;

	private String AddUserIP;

	private Date CreateDate;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("Id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("EntryId", DataColumn.STRING, 1, 32 , 0 , false , false),
		new SchemaColumn("EntryName", DataColumn.STRING, 2, 255 , 0 , true , false),
		new SchemaColumn("EntryType", DataColumn.STRING, 3, 20 , 0 , true , false),
		new SchemaColumn("EntryContent", DataColumn.CLOB, 4, 0 , 0 , false , false),
		new SchemaColumn("UserId", DataColumn.STRING, 5, 32 , 0 , true , false),
		new SchemaColumn("AddUserIP", DataColumn.STRING, 6, 20 , 0 , true , false),
		new SchemaColumn("CreateDate", DataColumn.DATETIME, 7, 0 , 0 , true , false)
	};

	public static final String _TableCode = "SCBkEntry";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into SCBkEntry values(?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update SCBkEntry set Id=?,EntryId=?,EntryName=?,EntryType=?,EntryContent=?,UserId=?,AddUserIP=?,CreateDate=? where Id=?";

	protected static final String _DeleteSQL = "delete from SCBkEntry  where Id=?";

	protected static final String _FillAllSQL = "select * from SCBkEntry  where Id=?";

	public SCBkEntrySchema(){
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
		return new SCBkEntrySchema();
	}

	protected SchemaSet newSet(){
		return new SCBkEntrySet();
	}

	public SCBkEntrySet query() {
		return query(null, -1, -1);
	}

	public SCBkEntrySet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public SCBkEntrySet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public SCBkEntrySet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (SCBkEntrySet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){Id = (String)v;return;}
		if (i == 1){EntryId = (String)v;return;}
		if (i == 2){EntryName = (String)v;return;}
		if (i == 3){EntryType = (String)v;return;}
		if (i == 4){EntryContent = (String)v;return;}
		if (i == 5){UserId = (String)v;return;}
		if (i == 6){AddUserIP = (String)v;return;}
		if (i == 7){CreateDate = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return Id;}
		if (i == 1){return EntryId;}
		if (i == 2){return EntryName;}
		if (i == 3){return EntryType;}
		if (i == 4){return EntryContent;}
		if (i == 5){return UserId;}
		if (i == 6){return AddUserIP;}
		if (i == 7){return CreateDate;}
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
	* 获取字段EntryId的值，该字段的<br>
	* 字段名称 :词条编码<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getEntryId() {
		return EntryId;
	}

	/**
	* 设置字段EntryId的值，该字段的<br>
	* 字段名称 :词条编码<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setEntryId(String entryId) {
		this.EntryId = entryId;
    }

	/**
	* 获取字段EntryName的值，该字段的<br>
	* 字段名称 :词条题目<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getEntryName() {
		return EntryName;
	}

	/**
	* 设置字段EntryName的值，该字段的<br>
	* 字段名称 :词条题目<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setEntryName(String entryName) {
		this.EntryName = entryName;
    }

	/**
	* 获取字段EntryType的值，该字段的<br>
	* 字段名称 :词条分类<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getEntryType() {
		return EntryType;
	}

	/**
	* 设置字段EntryType的值，该字段的<br>
	* 字段名称 :词条分类<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setEntryType(String entryType) {
		this.EntryType = entryType;
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

}