package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

import java.util.Date;

/**
 * 表名称：微信菜单<br>
 * 表代码：MenuDefine<br>
 * 表主键：ID<br>
 */
public class MenuDefineSchema extends Schema {
	private String ID;

	private String MenuID;

	private String ExternalMenuID;

	private String ExternalMenuName;

	private String MenuAttribute;

	private String ParentMenuID;

	private String MenuStatus;

	private String MenuType;

	private String MenuLevel;

	private String MenuURL;

	private String MenuResponseType;

	private String INPUTRULEID;

	private String OUTPUTRULEID;

	private Date CreateDate;

	private Date ModifyDate;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("MenuID", DataColumn.STRING, 1, 32 , 0 , false , false),
		new SchemaColumn("ExternalMenuID", DataColumn.STRING, 2, 32 , 0 , false , false),
		new SchemaColumn("ExternalMenuName", DataColumn.STRING, 3, 32 , 0 , false , false),
		new SchemaColumn("MenuAttribute", DataColumn.STRING, 4, 32 , 0 , false , false),
		new SchemaColumn("ParentMenuID", DataColumn.STRING, 5, 32 , 0 , false , false),
		new SchemaColumn("MenuStatus", DataColumn.STRING, 6, 20 , 0 , false , false),
		new SchemaColumn("MenuType", DataColumn.STRING, 7, 20 , 0 , false , false),
		new SchemaColumn("MenuLevel", DataColumn.STRING, 8, 20 , 0 , false , false),
		new SchemaColumn("MenuURL", DataColumn.STRING, 9, 500 , 0 , false , false),
		new SchemaColumn("MenuResponseType", DataColumn.STRING, 10, 20 , 0 , false , false),
		new SchemaColumn("INPUTRULEID", DataColumn.STRING, 11, 32 , 0 , false , false),
		new SchemaColumn("OUTPUTRULEID", DataColumn.STRING, 12, 32 , 0 , false , false),
		new SchemaColumn("CreateDate", DataColumn.DATETIME, 13, 0 , 0 , false , false),
		new SchemaColumn("ModifyDate", DataColumn.DATETIME, 14, 0 , 0 , false , false)
	};

	public static final String _TableCode = "MenuDefine";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into MenuDefine values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update MenuDefine set ID=?,MenuID=?,ExternalMenuID=?,ExternalMenuName=?,MenuAttribute=?,ParentMenuID=?,MenuStatus=?,MenuType=?,MenuLevel=?,MenuURL=?,MenuResponseType=?,INPUTRULEID=?,OUTPUTRULEID=?,CreateDate=?,ModifyDate=? where ID=?";

	protected static final String _DeleteSQL = "delete from MenuDefine  where ID=?";

	protected static final String _FillAllSQL = "select * from MenuDefine  where ID=?";

	public MenuDefineSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[15];
	}

	protected Schema newInstance(){
		return new MenuDefineSchema();
	}

	protected SchemaSet newSet(){
		return new MenuDefineSet();
	}

	public MenuDefineSet query() {
		return query(null, -1, -1);
	}

	public MenuDefineSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public MenuDefineSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public MenuDefineSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (MenuDefineSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){ID = (String)v;return;}
		if (i == 1){MenuID = (String)v;return;}
		if (i == 2){ExternalMenuID = (String)v;return;}
		if (i == 3){ExternalMenuName = (String)v;return;}
		if (i == 4){MenuAttribute = (String)v;return;}
		if (i == 5){ParentMenuID = (String)v;return;}
		if (i == 6){MenuStatus = (String)v;return;}
		if (i == 7){MenuType = (String)v;return;}
		if (i == 8){MenuLevel = (String)v;return;}
		if (i == 9){MenuURL = (String)v;return;}
		if (i == 10){MenuResponseType = (String)v;return;}
		if (i == 11){INPUTRULEID = (String)v;return;}
		if (i == 12){OUTPUTRULEID = (String)v;return;}
		if (i == 13){CreateDate = (Date)v;return;}
		if (i == 14){ModifyDate = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return MenuID;}
		if (i == 2){return ExternalMenuID;}
		if (i == 3){return ExternalMenuName;}
		if (i == 4){return MenuAttribute;}
		if (i == 5){return ParentMenuID;}
		if (i == 6){return MenuStatus;}
		if (i == 7){return MenuType;}
		if (i == 8){return MenuLevel;}
		if (i == 9){return MenuURL;}
		if (i == 10){return MenuResponseType;}
		if (i == 11){return INPUTRULEID;}
		if (i == 12){return OUTPUTRULEID;}
		if (i == 13){return CreateDate;}
		if (i == 14){return ModifyDate;}
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
	* 获取字段MenuID的值，该字段的<br>
	* 字段名称 :MenuID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMenuID() {
		return MenuID;
	}

	/**
	* 设置字段MenuID的值，该字段的<br>
	* 字段名称 :MenuID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMenuID(String menuID) {
		this.MenuID = menuID;
    }

	/**
	* 获取字段ExternalMenuID的值，该字段的<br>
	* 字段名称 :ExternalMenuID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getExternalMenuID() {
		return ExternalMenuID;
	}

	/**
	* 设置字段ExternalMenuID的值，该字段的<br>
	* 字段名称 :ExternalMenuID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setExternalMenuID(String externalMenuID) {
		this.ExternalMenuID = externalMenuID;
    }

	/**
	* 获取字段ExternalMenuName的值，该字段的<br>
	* 字段名称 :ExternalMenuName<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getExternalMenuName() {
		return ExternalMenuName;
	}

	/**
	* 设置字段ExternalMenuName的值，该字段的<br>
	* 字段名称 :ExternalMenuName<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setExternalMenuName(String externalMenuName) {
		this.ExternalMenuName = externalMenuName;
    }

	/**
	* 获取字段MenuAttribute的值，该字段的<br>
	* 字段名称 :MenuAttribute<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMenuAttribute() {
		return MenuAttribute;
	}

	/**
	* 设置字段MenuAttribute的值，该字段的<br>
	* 字段名称 :MenuAttribute<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMenuAttribute(String menuAttribute) {
		this.MenuAttribute = menuAttribute;
    }

	/**
	* 获取字段ParentMenuID的值，该字段的<br>
	* 字段名称 :ParentMenuID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getParentMenuID() {
		return ParentMenuID;
	}

	/**
	* 设置字段ParentMenuID的值，该字段的<br>
	* 字段名称 :ParentMenuID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setParentMenuID(String parentMenuID) {
		this.ParentMenuID = parentMenuID;
    }

	/**
	* 获取字段MenuStatus的值，该字段的<br>
	* 字段名称 :MenuStatus<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMenuStatus() {
		return MenuStatus;
	}

	/**
	* 设置字段MenuStatus的值，该字段的<br>
	* 字段名称 :MenuStatus<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMenuStatus(String menuStatus) {
		this.MenuStatus = menuStatus;
    }

	/**
	* 获取字段MenuType的值，该字段的<br>
	* 字段名称 :MenuType<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMenuType() {
		return MenuType;
	}

	/**
	* 设置字段MenuType的值，该字段的<br>
	* 字段名称 :MenuType<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMenuType(String menuType) {
		this.MenuType = menuType;
    }

	/**
	* 获取字段MenuLevel的值，该字段的<br>
	* 字段名称 :MenuLevel<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMenuLevel() {
		return MenuLevel;
	}

	/**
	* 设置字段MenuLevel的值，该字段的<br>
	* 字段名称 :MenuLevel<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMenuLevel(String menuLevel) {
		this.MenuLevel = menuLevel;
    }

	/**
	* 获取字段MenuURL的值，该字段的<br>
	* 字段名称 :MenuURL<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMenuURL() {
		return MenuURL;
	}

	/**
	* 设置字段MenuURL的值，该字段的<br>
	* 字段名称 :MenuURL<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMenuURL(String menuURL) {
		this.MenuURL = menuURL;
    }

	/**
	* 获取字段MenuResponseType的值，该字段的<br>
	* 字段名称 :MenuResponseType<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMenuResponseType() {
		return MenuResponseType;
	}

	/**
	* 设置字段MenuResponseType的值，该字段的<br>
	* 字段名称 :MenuResponseType<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMenuResponseType(String menuResponseType) {
		this.MenuResponseType = menuResponseType;
    }

	/**
	* 获取字段INPUTRULEID的值，该字段的<br>
	* 字段名称 :INPUTRULEID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getINPUTRULEID() {
		return INPUTRULEID;
	}

	/**
	* 设置字段INPUTRULEID的值，该字段的<br>
	* 字段名称 :INPUTRULEID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setINPUTRULEID(String iNPUTRULEID) {
		this.INPUTRULEID = iNPUTRULEID;
    }

	/**
	* 获取字段OUTPUTRULEID的值，该字段的<br>
	* 字段名称 :OUTPUTRULEID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getOUTPUTRULEID() {
		return OUTPUTRULEID;
	}

	/**
	* 设置字段OUTPUTRULEID的值，该字段的<br>
	* 字段名称 :OUTPUTRULEID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setOUTPUTRULEID(String oUTPUTRULEID) {
		this.OUTPUTRULEID = oUTPUTRULEID;
    }

	/**
	* 获取字段CreateDate的值，该字段的<br>
	* 字段名称 :CreateDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getCreateDate() {
		return CreateDate;
	}

	/**
	* 设置字段CreateDate的值，该字段的<br>
	* 字段名称 :CreateDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCreateDate(Date createDate) {
		this.CreateDate = createDate;
    }

	/**
	* 获取字段ModifyDate的值，该字段的<br>
	* 字段名称 :ModifyDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getModifyDate() {
		return ModifyDate;
	}

	/**
	* 设置字段ModifyDate的值，该字段的<br>
	* 字段名称 :ModifyDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyDate(Date modifyDate) {
		this.ModifyDate = modifyDate;
    }

}