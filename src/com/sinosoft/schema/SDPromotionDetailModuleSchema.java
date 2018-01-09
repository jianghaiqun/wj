package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：促销频道模块详细表<br>
 * 表代码：SDPromotionDetailModule<br>
 * 表主键：Id<br>
 */
public class SDPromotionDetailModuleSchema extends Schema {
	private String Id;

	private String DocumentId;

	private String ModuleName;

	private String ModuleType;

	private String ModuleColor;

	private String ModuleTheme;

	private String ModuleNameColor;

	private String MoreUrl;

	private String MoreColor;

	private Long OrderFlag;

	private String CreateUser;

	private Date CreateDate;

	private String ModifyUser;

	private Date ModifyDate;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("Id", DataColumn.STRING, 0, 255 , 0 , true , true),
		new SchemaColumn("DocumentId", DataColumn.STRING, 1, 255 , 0 , false , false),
		new SchemaColumn("ModuleName", DataColumn.STRING, 2, 255 , 0 , false , false),
		new SchemaColumn("ModuleType", DataColumn.STRING, 3, 10 , 0 , false , false),
		new SchemaColumn("ModuleColor", DataColumn.STRING, 4, 30 , 0 , false , false),
		new SchemaColumn("ModuleTheme", DataColumn.STRING, 5, 100 , 0 , false , false),
		new SchemaColumn("ModuleNameColor", DataColumn.STRING, 6, 30 , 0 , false , false),
		new SchemaColumn("MoreUrl", DataColumn.STRING, 7, 255 , 0 , false , false),
		new SchemaColumn("MoreColor", DataColumn.STRING, 8, 30 , 0 , false , false),
		new SchemaColumn("OrderFlag", DataColumn.LONG, 9, 20 , 0 , false , false),
		new SchemaColumn("CreateUser", DataColumn.STRING, 10, 255 , 0 , false , false),
		new SchemaColumn("CreateDate", DataColumn.DATETIME, 11, 0 , 0 , false , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 12, 255 , 0 , false , false),
		new SchemaColumn("ModifyDate", DataColumn.DATETIME, 13, 0 , 0 , false , false)
	};

	public static final String _TableCode = "SDPromotionDetailModule";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into SDPromotionDetailModule values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update SDPromotionDetailModule set Id=?,DocumentId=?,ModuleName=?,ModuleType=?,ModuleColor=?,ModuleTheme=?,ModuleNameColor=?,MoreUrl=?,MoreColor=?,OrderFlag=?,CreateUser=?,CreateDate=?,ModifyUser=?,ModifyDate=? where Id=?";

	protected static final String _DeleteSQL = "delete from SDPromotionDetailModule  where Id=?";

	protected static final String _FillAllSQL = "select * from SDPromotionDetailModule  where Id=?";

	public SDPromotionDetailModuleSchema(){
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
		return new SDPromotionDetailModuleSchema();
	}

	protected SchemaSet newSet(){
		return new SDPromotionDetailModuleSet();
	}

	public SDPromotionDetailModuleSet query() {
		return query(null, -1, -1);
	}

	public SDPromotionDetailModuleSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public SDPromotionDetailModuleSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public SDPromotionDetailModuleSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (SDPromotionDetailModuleSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){Id = (String)v;return;}
		if (i == 1){DocumentId = (String)v;return;}
		if (i == 2){ModuleName = (String)v;return;}
		if (i == 3){ModuleType = (String)v;return;}
		if (i == 4){ModuleColor = (String)v;return;}
		if (i == 5){ModuleTheme = (String)v;return;}
		if (i == 6){ModuleNameColor = (String)v;return;}
		if (i == 7){MoreUrl = (String)v;return;}
		if (i == 8){MoreColor = (String)v;return;}
		if (i == 9){if(v==null){OrderFlag = null;}else{OrderFlag = new Long(v.toString());}return;}
		if (i == 10){CreateUser = (String)v;return;}
		if (i == 11){CreateDate = (Date)v;return;}
		if (i == 12){ModifyUser = (String)v;return;}
		if (i == 13){ModifyDate = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return Id;}
		if (i == 1){return DocumentId;}
		if (i == 2){return ModuleName;}
		if (i == 3){return ModuleType;}
		if (i == 4){return ModuleColor;}
		if (i == 5){return ModuleTheme;}
		if (i == 6){return ModuleNameColor;}
		if (i == 7){return MoreUrl;}
		if (i == 8){return MoreColor;}
		if (i == 9){return OrderFlag;}
		if (i == 10){return CreateUser;}
		if (i == 11){return CreateDate;}
		if (i == 12){return ModifyUser;}
		if (i == 13){return ModifyDate;}
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
	* 获取字段DocumentId的值，该字段的<br>
	* 字段名称 :文档id<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getDocumentId() {
		return DocumentId;
	}

	/**
	* 设置字段DocumentId的值，该字段的<br>
	* 字段名称 :文档id<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDocumentId(String documentId) {
		this.DocumentId = documentId;
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
	* 获取字段ModuleType的值，该字段的<br>
	* 字段名称 :模块类型<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getModuleType() {
		return ModuleType;
	}

	/**
	* 设置字段ModuleType的值，该字段的<br>
	* 字段名称 :模块类型<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModuleType(String moduleType) {
		this.ModuleType = moduleType;
    }

	/**
	* 获取字段ModuleColor的值，该字段的<br>
	* 字段名称 :模块背景色<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getModuleColor() {
		return ModuleColor;
	}

	/**
	* 设置字段ModuleColor的值，该字段的<br>
	* 字段名称 :模块背景色<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModuleColor(String moduleColor) {
		this.ModuleColor = moduleColor;
    }

	/**
	* 获取字段ModuleTheme的值，该字段的<br>
	* 字段名称 :模块主题<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getModuleTheme() {
		return ModuleTheme;
	}

	/**
	* 设置字段ModuleTheme的值，该字段的<br>
	* 字段名称 :模块主题<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModuleTheme(String moduleTheme) {
		this.ModuleTheme = moduleTheme;
    }

	/**
	* 获取字段ModuleNameColor的值，该字段的<br>
	* 字段名称 :模块名称颜色<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getModuleNameColor() {
		return ModuleNameColor;
	}

	/**
	* 设置字段ModuleNameColor的值，该字段的<br>
	* 字段名称 :模块名称颜色<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModuleNameColor(String moduleNameColor) {
		this.ModuleNameColor = moduleNameColor;
    }

	/**
	* 获取字段MoreUrl的值，该字段的<br>
	* 字段名称 :更多URL地址<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMoreUrl() {
		return MoreUrl;
	}

	/**
	* 设置字段MoreUrl的值，该字段的<br>
	* 字段名称 :更多URL地址<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMoreUrl(String moreUrl) {
		this.MoreUrl = moreUrl;
    }

	/**
	* 获取字段MoreColor的值，该字段的<br>
	* 字段名称 :更多字体颜色<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMoreColor() {
		return MoreColor;
	}

	/**
	* 设置字段MoreColor的值，该字段的<br>
	* 字段名称 :更多字体颜色<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMoreColor(String moreColor) {
		this.MoreColor = moreColor;
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