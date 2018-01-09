package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

import java.util.Date;

import java.util.Date;

import java.util.Date;

/**
 * 表名称：促销频道首页模块表<br>
 * 表代码：SDPromotionHomePageModule<br>
 * 表主键：Id<br>
 */
public class SDPromotionHomePageModuleSchema extends Schema {
	private String Id;

	private String ShortTitle;

	private String ModuleName;

	private String isShow;

	private Long OrderFlag;

	private String CreateUser;

	private Date CreateDate;

	private String ModifyUser;

	private Date ModifyDate;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("Id", DataColumn.STRING, 0, 255 , 0 , true , true),
		new SchemaColumn("ShortTitle", DataColumn.STRING, 1, 255 , 0 , false , false),
		new SchemaColumn("ModuleName", DataColumn.STRING, 2, 255 , 0 , false , false),
		new SchemaColumn("isShow", DataColumn.STRING, 3, 5 , 0 , false , false),
		new SchemaColumn("OrderFlag", DataColumn.LONG, 4, 20 , 0 , false , false),
		new SchemaColumn("CreateUser", DataColumn.STRING, 5, 255 , 0 , false , false),
		new SchemaColumn("CreateDate", DataColumn.DATETIME, 6, 0 , 0 , false , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 7, 255 , 0 , false , false),
		new SchemaColumn("ModifyDate", DataColumn.DATETIME, 8, 0 , 0 , false , false)
	};

	public static final String _TableCode = "SDPromotionHomePageModule";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into SDPromotionHomePageModule values(?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update SDPromotionHomePageModule set Id=?,ShortTitle=?,ModuleName=?,isShow=?,OrderFlag=?,CreateUser=?,CreateDate=?,ModifyUser=?,ModifyDate=? where Id=?";

	protected static final String _DeleteSQL = "delete from SDPromotionHomePageModule  where Id=?";

	protected static final String _FillAllSQL = "select * from SDPromotionHomePageModule  where Id=?";

	public SDPromotionHomePageModuleSchema(){
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
		return new SDPromotionHomePageModuleSchema();
	}

	protected SchemaSet newSet(){
		return new SDPromotionHomePageModuleSet();
	}

	public SDPromotionHomePageModuleSet query() {
		return query(null, -1, -1);
	}

	public SDPromotionHomePageModuleSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public SDPromotionHomePageModuleSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public SDPromotionHomePageModuleSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (SDPromotionHomePageModuleSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){Id = (String)v;return;}
		if (i == 1){ShortTitle = (String)v;return;}
		if (i == 2){ModuleName = (String)v;return;}
		if (i == 3){isShow = (String)v;return;}
		if (i == 4){if(v==null){OrderFlag = null;}else{OrderFlag = new Long(v.toString());}return;}
		if (i == 5){CreateUser = (String)v;return;}
		if (i == 6){CreateDate = (Date)v;return;}
		if (i == 7){ModifyUser = (String)v;return;}
		if (i == 8){ModifyDate = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return Id;}
		if (i == 1){return ShortTitle;}
		if (i == 2){return ModuleName;}
		if (i == 3){return isShow;}
		if (i == 4){return OrderFlag;}
		if (i == 5){return CreateUser;}
		if (i == 6){return CreateDate;}
		if (i == 7){return ModifyUser;}
		if (i == 8){return ModifyDate;}
		return null;
	}

	/**
	* 获取字段Id的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getId() {
		return Id;
	}

	/**
	* 设置字段Id的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setId(String id) {
		this.Id = id;
    }

	/**
	* 获取字段ShortTitle的值，该字段的<br>
	* 字段名称 :短标题<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getShortTitle() {
		return ShortTitle;
	}

	/**
	* 设置字段ShortTitle的值，该字段的<br>
	* 字段名称 :短标题<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setShortTitle(String shortTitle) {
		this.ShortTitle = shortTitle;
    }

	/**
	* 获取字段ModuleName的值，该字段的<br>
	* 字段名称 :模块名称<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getModuleName() {
		return ModuleName;
	}

	/**
	* 设置字段ModuleName的值，该字段的<br>
	* 字段名称 :模块名称<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModuleName(String moduleName) {
		this.ModuleName = moduleName;
    }

	/**
	* 获取字段isShow的值，该字段的<br>
	* 字段名称 :是否显示<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getisShow() {
		return isShow;
	}

	/**
	* 设置字段isShow的值，该字段的<br>
	* 字段名称 :是否显示<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setisShow(String isShow) {
		this.isShow = isShow;
    }

	/**
	* 获取字段OrderFlag的值，该字段的<br>
	* 字段名称 :顺序<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getOrderFlag() {
		if(OrderFlag==null){return 0;}
		return OrderFlag.longValue();
	}

	/**
	* 设置字段OrderFlag的值，该字段的<br>
	* 字段名称 :顺序<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setOrderFlag(long orderFlag) {
		this.OrderFlag = new Long(orderFlag);
    }

	/**
	* 设置字段OrderFlag的值，该字段的<br>
	* 字段名称 :顺序<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setOrderFlag(String orderFlag) {
		if (orderFlag == null){
			this.OrderFlag = null;
			return;
		}
		this.OrderFlag = new Long(orderFlag);
    }

	/**
	* 获取字段CreateUser的值，该字段的<br>
	* 字段名称 :创建人<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCreateUser() {
		return CreateUser;
	}

	/**
	* 设置字段CreateUser的值，该字段的<br>
	* 字段名称 :创建人<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCreateUser(String createUser) {
		this.CreateUser = createUser;
    }

	/**
	* 获取字段CreateDate的值，该字段的<br>
	* 字段名称 :创建日<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getCreateDate() {
		return CreateDate;
	}

	/**
	* 设置字段CreateDate的值，该字段的<br>
	* 字段名称 :创建日<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCreateDate(Date createDate) {
		this.CreateDate = createDate;
    }

	/**
	* 获取字段ModifyUser的值，该字段的<br>
	* 字段名称 :更新人<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getModifyUser() {
		return ModifyUser;
	}

	/**
	* 设置字段ModifyUser的值，该字段的<br>
	* 字段名称 :更新人<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyUser(String modifyUser) {
		this.ModifyUser = modifyUser;
    }

	/**
	* 获取字段ModifyDate的值，该字段的<br>
	* 字段名称 :更新日<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getModifyDate() {
		return ModifyDate;
	}

	/**
	* 设置字段ModifyDate的值，该字段的<br>
	* 字段名称 :更新日<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyDate(Date modifyDate) {
		this.ModifyDate = modifyDate;
    }

}