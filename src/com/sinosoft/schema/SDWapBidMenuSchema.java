package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：WAP竞价页标题菜单表<br>
 * 表代码：SDWapBidMenu<br>
 * 表主键：ID<br>
 */
public class SDWapBidMenuSchema extends Schema {
	private String ID;

	private String DocumentId;

	private String MenuName;

	private String Description;

	private String OrderFlag;

	private String prop1;

	private String prop2;

	private String prop3;

	private String CreateUser;

	private Date CreateDate;

	private String ModifyUser;

	private Date ModifyDate;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.STRING, 0, 30 , 0 , true , true),
		new SchemaColumn("DocumentId", DataColumn.STRING, 1, 50 , 0 , false , false),
		new SchemaColumn("MenuName", DataColumn.STRING, 2, 50 , 0 , false , false),
		new SchemaColumn("Description", DataColumn.STRING, 3, 225 , 0 , false , false),
		new SchemaColumn("OrderFlag", DataColumn.STRING, 4, 30 , 0 , false , false),
		new SchemaColumn("prop1", DataColumn.STRING, 5, 50 , 0 , false , false),
		new SchemaColumn("prop2", DataColumn.STRING, 6, 50 , 0 , false , false),
		new SchemaColumn("prop3", DataColumn.STRING, 7, 50 , 0 , false , false),
		new SchemaColumn("CreateUser", DataColumn.STRING, 8, 50 , 0 , false , false),
		new SchemaColumn("CreateDate", DataColumn.DATETIME, 9, 0 , 0 , false , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 10, 50 , 0 , false , false),
		new SchemaColumn("ModifyDate", DataColumn.DATETIME, 11, 0 , 0 , false , false)
	};

	public static final String _TableCode = "SDWapBidMenu";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into SDWapBidMenu values(?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update SDWapBidMenu set ID=?,DocumentId=?,MenuName=?,Description=?,OrderFlag=?,prop1=?,prop2=?,prop3=?,CreateUser=?,CreateDate=?,ModifyUser=?,ModifyDate=? where ID=?";

	protected static final String _DeleteSQL = "delete from SDWapBidMenu  where ID=?";

	protected static final String _FillAllSQL = "select * from SDWapBidMenu  where ID=?";

	public SDWapBidMenuSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[12];
	}

	protected Schema newInstance(){
		return new SDWapBidMenuSchema();
	}

	protected SchemaSet newSet(){
		return new SDWapBidMenuSet();
	}

	public SDWapBidMenuSet query() {
		return query(null, -1, -1);
	}

	public SDWapBidMenuSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public SDWapBidMenuSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public SDWapBidMenuSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (SDWapBidMenuSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){ID = (String)v;return;}
		if (i == 1){DocumentId = (String)v;return;}
		if (i == 2){MenuName = (String)v;return;}
		if (i == 3){Description = (String)v;return;}
		if (i == 4){OrderFlag = (String)v;return;}
		if (i == 5){prop1 = (String)v;return;}
		if (i == 6){prop2 = (String)v;return;}
		if (i == 7){prop3 = (String)v;return;}
		if (i == 8){CreateUser = (String)v;return;}
		if (i == 9){CreateDate = (Date)v;return;}
		if (i == 10){ModifyUser = (String)v;return;}
		if (i == 11){ModifyDate = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return DocumentId;}
		if (i == 2){return MenuName;}
		if (i == 3){return Description;}
		if (i == 4){return OrderFlag;}
		if (i == 5){return prop1;}
		if (i == 6){return prop2;}
		if (i == 7){return prop3;}
		if (i == 8){return CreateUser;}
		if (i == 9){return CreateDate;}
		if (i == 10){return ModifyUser;}
		if (i == 11){return ModifyDate;}
		return null;
	}

	/**
	* 获取字段ID的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getID() {
		return ID;
	}

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setID(String iD) {
		this.ID = iD;
    }

	/**
	* 获取字段DocumentId的值，该字段的<br>
	* 字段名称 :文档id<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getDocumentId() {
		return DocumentId;
	}

	/**
	* 设置字段DocumentId的值，该字段的<br>
	* 字段名称 :文档id<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDocumentId(String documentId) {
		this.DocumentId = documentId;
    }

	/**
	* 获取字段MenuName的值，该字段的<br>
	* 字段名称 :标题菜单名称<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMenuName() {
		return MenuName;
	}

	/**
	* 设置字段MenuName的值，该字段的<br>
	* 字段名称 :标题菜单名称<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMenuName(String menuName) {
		this.MenuName = menuName;
    }

	/**
	* 获取字段Description的值，该字段的<br>
	* 字段名称 :备注<br>
	* 数据类型 :varchar(225)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getDescription() {
		return Description;
	}

	/**
	* 设置字段Description的值，该字段的<br>
	* 字段名称 :备注<br>
	* 数据类型 :varchar(225)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDescription(String description) {
		this.Description = description;
    }

	/**
	* 获取字段OrderFlag的值，该字段的<br>
	* 字段名称 :顺序<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getOrderFlag() {
		return OrderFlag;
	}

	/**
	* 设置字段OrderFlag的值，该字段的<br>
	* 字段名称 :顺序<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setOrderFlag(String orderFlag) {
		this.OrderFlag = orderFlag;
    }

	/**
	* 获取字段prop1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop1() {
		return prop1;
	}

	/**
	* 设置字段prop1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop1(String prop1) {
		this.prop1 = prop1;
    }

	/**
	* 获取字段prop2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop2() {
		return prop2;
	}

	/**
	* 设置字段prop2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop2(String prop2) {
		this.prop2 = prop2;
    }

	/**
	* 获取字段prop3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop3() {
		return prop3;
	}

	/**
	* 设置字段prop3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop3(String prop3) {
		this.prop3 = prop3;
    }

	/**
	* 获取字段CreateUser的值，该字段的<br>
	* 字段名称 :创建人<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCreateUser() {
		return CreateUser;
	}

	/**
	* 设置字段CreateUser的值，该字段的<br>
	* 字段名称 :创建人<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCreateUser(String createUser) {
		this.CreateUser = createUser;
    }

	/**
	* 获取字段CreateDate的值，该字段的<br>
	* 字段名称 :创建日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getCreateDate() {
		return CreateDate;
	}

	/**
	* 设置字段CreateDate的值，该字段的<br>
	* 字段名称 :创建日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCreateDate(Date createDate) {
		this.CreateDate = createDate;
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

	/**
	* 获取字段ModifyDate的值，该字段的<br>
	* 字段名称 :修改日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getModifyDate() {
		return ModifyDate;
	}

	/**
	* 设置字段ModifyDate的值，该字段的<br>
	* 字段名称 :修改日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyDate(Date modifyDate) {
		this.ModifyDate = modifyDate;
    }

}