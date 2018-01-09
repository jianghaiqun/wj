package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;
import java.util.Date;

/**
 * 表名称：栏目类型表<br>
 * 表代码：SCCatalogType<br>
 * 表主键：Id<br>
 */
public class SCCatalogTypeSchema extends Schema {
	private String Id;

	private String TypeName;

	private String TypeCode;

	private Date AddTime;

	private String AddUser;

	private Date ModifyTime;

	private String ModifyUser;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("Id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("TypeName", DataColumn.STRING, 1, 50 , 0 , true , false),
		new SchemaColumn("TypeCode", DataColumn.STRING, 2, 20 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 3, 0 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 4, 50 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 5, 0 , 0 , false , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 6, 50 , 0 , false , false)
	};

	public static final String _TableCode = "SCCatalogType";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into SCCatalogType values(?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update SCCatalogType set Id=?,TypeName=?,TypeCode=?,AddTime=?,AddUser=?,ModifyTime=?,ModifyUser=? where Id=?";

	protected static final String _DeleteSQL = "delete from SCCatalogType  where Id=?";

	protected static final String _FillAllSQL = "select * from SCCatalogType  where Id=?";

	public SCCatalogTypeSchema(){
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
		return new SCCatalogTypeSchema();
	}

	protected SchemaSet newSet(){
		return new SCCatalogTypeSet();
	}

	public SCCatalogTypeSet query() {
		return query(null, -1, -1);
	}

	public SCCatalogTypeSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public SCCatalogTypeSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public SCCatalogTypeSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (SCCatalogTypeSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){Id = (String)v;return;}
		if (i == 1){TypeName = (String)v;return;}
		if (i == 2){TypeCode = (String)v;return;}
		if (i == 3){AddTime = (Date)v;return;}
		if (i == 4){AddUser = (String)v;return;}
		if (i == 5){ModifyTime = (Date)v;return;}
		if (i == 6){ModifyUser = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return Id;}
		if (i == 1){return TypeName;}
		if (i == 2){return TypeCode;}
		if (i == 3){return AddTime;}
		if (i == 4){return AddUser;}
		if (i == 5){return ModifyTime;}
		if (i == 6){return ModifyUser;}
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
	* 获取字段TypeName的值，该字段的<br>
	* 字段名称 :栏目类型名称<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getTypeName() {
		return TypeName;
	}

	/**
	* 设置字段TypeName的值，该字段的<br>
	* 字段名称 :栏目类型名称<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setTypeName(String typeName) {
		this.TypeName = typeName;
    }

	/**
	* 获取字段TypeCode的值，该字段的<br>
	* 字段名称 :栏目类型代码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getTypeCode() {
		return TypeCode;
	}

	/**
	* 设置字段TypeCode的值，该字段的<br>
	* 字段名称 :栏目类型代码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setTypeCode(String typeCode) {
		this.TypeCode = typeCode;
    }

	/**
	* 获取字段AddTime的值，该字段的<br>
	* 字段名称 :添加时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getAddTime() {
		return AddTime;
	}

	/**
	* 设置字段AddTime的值，该字段的<br>
	* 字段名称 :添加时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAddTime(Date addTime) {
		this.AddTime = addTime;
    }

	/**
	* 获取字段AddUser的值，该字段的<br>
	* 字段名称 :添加人<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAddUser() {
		return AddUser;
	}

	/**
	* 设置字段AddUser的值，该字段的<br>
	* 字段名称 :添加人<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAddUser(String addUser) {
		this.AddUser = addUser;
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
	* 获取字段ModifyUser的值，该字段的<br>
	* 字段名称 :修改人<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getModifyUser() {
		return ModifyUser;
	}

	/**
	* 设置字段ModifyUser的值，该字段的<br>
	* 字段名称 :修改人<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyUser(String modifyUser) {
		this.ModifyUser = modifyUser;
    }

}