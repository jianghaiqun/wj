package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

import java.util.Date;

/**
 * 表名称：促销详细产品内容表<br>
 * 表代码：SDPromotionDetailProduct<br>
 * 表主键：Id<br>
 */
public class SDPromotionDetailProductSchema extends Schema {
	private String Id;

	private String ProductId;

	private String ProductName;

	private String BuyURL;

	private Long OrderFlag;

	private String ModuleId;

	private String CreateUser;

	private Date CreateDate;

	private String ModifyUser;

	private Date ModifyDate;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("Id", DataColumn.STRING, 0, 255 , 0 , true , true),
		new SchemaColumn("ProductId", DataColumn.STRING, 1, 255 , 0 , false , false),
		new SchemaColumn("ProductName", DataColumn.STRING, 2, 255 , 0 , false , false),
		new SchemaColumn("BuyURL", DataColumn.STRING, 3, 255 , 0 , false , false),
		new SchemaColumn("OrderFlag", DataColumn.LONG, 4, 20 , 0 , false , false),
		new SchemaColumn("ModuleId", DataColumn.STRING, 5, 255 , 0 , false , false),
		new SchemaColumn("CreateUser", DataColumn.STRING, 6, 255 , 0 , false , false),
		new SchemaColumn("CreateDate", DataColumn.DATETIME, 7, 0 , 0 , false , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 8, 255 , 0 , false , false),
		new SchemaColumn("ModifyDate", DataColumn.DATETIME, 9, 0 , 0 , false , false)
	};

	public static final String _TableCode = "SDPromotionDetailProduct";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into SDPromotionDetailProduct values(?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update SDPromotionDetailProduct set Id=?,ProductId=?,ProductName=?,BuyURL=?,OrderFlag=?,ModuleId=?,CreateUser=?,CreateDate=?,ModifyUser=?,ModifyDate=? where Id=?";

	protected static final String _DeleteSQL = "delete from SDPromotionDetailProduct  where Id=?";

	protected static final String _FillAllSQL = "select * from SDPromotionDetailProduct  where Id=?";

	public SDPromotionDetailProductSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[10];
	}

	protected Schema newInstance(){
		return new SDPromotionDetailProductSchema();
	}

	protected SchemaSet newSet(){
		return new SDPromotionDetailProductSet();
	}

	public SDPromotionDetailProductSet query() {
		return query(null, -1, -1);
	}

	public SDPromotionDetailProductSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public SDPromotionDetailProductSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public SDPromotionDetailProductSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (SDPromotionDetailProductSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){Id = (String)v;return;}
		if (i == 1){ProductId = (String)v;return;}
		if (i == 2){ProductName = (String)v;return;}
		if (i == 3){BuyURL = (String)v;return;}
		if (i == 4){if(v==null){OrderFlag = null;}else{OrderFlag = new Long(v.toString());}return;}
		if (i == 5){ModuleId = (String)v;return;}
		if (i == 6){CreateUser = (String)v;return;}
		if (i == 7){CreateDate = (Date)v;return;}
		if (i == 8){ModifyUser = (String)v;return;}
		if (i == 9){ModifyDate = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return Id;}
		if (i == 1){return ProductId;}
		if (i == 2){return ProductName;}
		if (i == 3){return BuyURL;}
		if (i == 4){return OrderFlag;}
		if (i == 5){return ModuleId;}
		if (i == 6){return CreateUser;}
		if (i == 7){return CreateDate;}
		if (i == 8){return ModifyUser;}
		if (i == 9){return ModifyDate;}
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
	* 获取字段ProductId的值，该字段的<br>
	* 字段名称 :产品id<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProductId() {
		return ProductId;
	}

	/**
	* 设置字段ProductId的值，该字段的<br>
	* 字段名称 :产品id<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProductId(String productId) {
		this.ProductId = productId;
    }

	/**
	* 获取字段ProductName的值，该字段的<br>
	* 字段名称 :产品名称<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProductName() {
		return ProductName;
	}

	/**
	* 设置字段ProductName的值，该字段的<br>
	* 字段名称 :产品名称<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProductName(String productName) {
		this.ProductName = productName;
    }

	/**
	* 获取字段BuyURL的值，该字段的<br>
	* 字段名称 :立即购买URL<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getBuyURL() {
		return BuyURL;
	}

	/**
	* 设置字段BuyURL的值，该字段的<br>
	* 字段名称 :立即购买URL<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setBuyURL(String buyURL) {
		this.BuyURL = buyURL;
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
	* 获取字段ModuleId的值，该字段的<br>
	* 字段名称 :所属模块Id<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getModuleId() {
		return ModuleId;
	}

	/**
	* 设置字段ModuleId的值，该字段的<br>
	* 字段名称 :所属模块Id<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModuleId(String moduleId) {
		this.ModuleId = moduleId;
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