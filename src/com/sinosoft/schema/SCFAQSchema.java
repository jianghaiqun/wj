package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

import java.util.Date;

/**
 * 表名称：SCFAQ<br>
 * 表代码：SCFAQ<br>
 * 表主键：Id<br>
 */
public class SCFAQSchema extends Schema {
	private String Id;

	private String FAQName;

	private String FAQContent;

	private Date AddTime;

	private Date ModifyTime;

	private String RelaId;

	private String AddUser;

	private String ModifyUser;

	private String OrderFlag;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("Id", DataColumn.STRING, 0, 16 , 0 , true , true),
		new SchemaColumn("FAQName", DataColumn.STRING, 1, 255 , 0 , true , false),
		new SchemaColumn("FAQContent", DataColumn.STRING, 2, 255 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 3, 0 , 0 , true , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 4, 0 , 0 , false , false),
		new SchemaColumn("RelaId", DataColumn.STRING, 5, 20 , 0 , true , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 6, 255 , 0 , false , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 7, 255 , 0 , false , false),
		new SchemaColumn("OrderFlag", DataColumn.STRING, 8, 255 , 0 , false , false)
	};

	public static final String _TableCode = "SCFAQ";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into SCFAQ values(?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update SCFAQ set Id=?,FAQName=?,FAQContent=?,AddTime=?,ModifyTime=?,RelaId=?,AddUser=?,ModifyUser=?,OrderFlag=? where Id=?";

	protected static final String _DeleteSQL = "delete from SCFAQ  where Id=?";

	protected static final String _FillAllSQL = "select * from SCFAQ  where Id=?";

	public SCFAQSchema(){
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
		return new SCFAQSchema();
	}

	protected SchemaSet newSet(){
		return new SCFAQSet();
	}

	public SCFAQSet query() {
		return query(null, -1, -1);
	}

	public SCFAQSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public SCFAQSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public SCFAQSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (SCFAQSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){Id = (String)v;return;}
		if (i == 1){FAQName = (String)v;return;}
		if (i == 2){FAQContent = (String)v;return;}
		if (i == 3){AddTime = (Date)v;return;}
		if (i == 4){ModifyTime = (Date)v;return;}
		if (i == 5){RelaId = (String)v;return;}
		if (i == 6){AddUser = (String)v;return;}
		if (i == 7){ModifyUser = (String)v;return;}
		if (i == 8){OrderFlag = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return Id;}
		if (i == 1){return FAQName;}
		if (i == 2){return FAQContent;}
		if (i == 3){return AddTime;}
		if (i == 4){return ModifyTime;}
		if (i == 5){return RelaId;}
		if (i == 6){return AddUser;}
		if (i == 7){return ModifyUser;}
		if (i == 8){return OrderFlag;}
		return null;
	}

	/**
	* 获取字段Id的值，该字段的<br>
	* 字段名称 :常见问题ID<br>
	* 数据类型 :varchar(16)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getId() {
		return Id;
	}

	/**
	* 设置字段Id的值，该字段的<br>
	* 字段名称 :常见问题ID<br>
	* 数据类型 :varchar(16)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setId(String id) {
		this.Id = id;
    }

	/**
	* 获取字段FAQName的值，该字段的<br>
	* 字段名称 :常见问题名称<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getFAQName() {
		return FAQName;
	}

	/**
	* 设置字段FAQName的值，该字段的<br>
	* 字段名称 :常见问题名称<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setFAQName(String fAQName) {
		this.FAQName = fAQName;
    }

	/**
	* 获取字段FAQContent的值，该字段的<br>
	* 字段名称 :常见问题内容<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getFAQContent() {
		return FAQContent;
	}

	/**
	* 设置字段FAQContent的值，该字段的<br>
	* 字段名称 :常见问题内容<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setFAQContent(String fAQContent) {
		this.FAQContent = fAQContent;
    }

	/**
	* 获取字段AddTime的值，该字段的<br>
	* 字段名称 :创建开始时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public Date getAddTime() {
		return AddTime;
	}

	/**
	* 设置字段AddTime的值，该字段的<br>
	* 字段名称 :创建开始时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setAddTime(Date addTime) {
		this.AddTime = addTime;
    }

	/**
	* 获取字段ModifyTime的值，该字段的<br>
	* 字段名称 :修改时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getModifyTime() {
		return ModifyTime;
	}

	/**
	* 设置字段ModifyTime的值，该字段的<br>
	* 字段名称 :修改时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyTime(Date modifyTime) {
		this.ModifyTime = modifyTime;
    }

	/**
	* 获取字段RelaId的值，该字段的<br>
	* 字段名称 :关联ID<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getRelaId() {
		return RelaId;
	}

	/**
	* 设置字段RelaId的值，该字段的<br>
	* 字段名称 :关联ID<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setRelaId(String relaId) {
		this.RelaId = relaId;
    }

	/**
	* 获取字段AddUser的值，该字段的<br>
	* 字段名称 :添加人<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAddUser() {
		return AddUser;
	}

	/**
	* 设置字段AddUser的值，该字段的<br>
	* 字段名称 :添加人<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAddUser(String addUser) {
		this.AddUser = addUser;
    }

	/**
	* 获取字段ModifyUser的值，该字段的<br>
	* 字段名称 :修改人<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getModifyUser() {
		return ModifyUser;
	}

	/**
	* 设置字段ModifyUser的值，该字段的<br>
	* 字段名称 :修改人<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyUser(String modifyUser) {
		this.ModifyUser = modifyUser;
    }

	/**
	* 获取字段OrderFlag的值，该字段的<br>
	* 字段名称 :顺序值<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getOrderFlag() {
		return OrderFlag;
	}

	/**
	* 设置字段OrderFlag的值，该字段的<br>
	* 字段名称 :顺序值<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setOrderFlag(String orderFlag) {
		this.OrderFlag = orderFlag;
    }

}