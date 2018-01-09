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

import java.util.Date;

import java.util.Date;

import java.util.Date;

/**
 * 表名称：促销频道首页特别促销产品<br>
 * 表代码：SDPromotionHomePageSpecialProduct<br>
 * 表主键：Id<br>
 */
public class SDPromotionHomePageSpecialProductSchema extends Schema {
	private String Id;

	private String ProductName;

	private String LogoUrl;

	private String buynum;

	private String productid;

	private String initprice;

	private String URL;

	private Date endtime;

	private String CreateUser;

	private Date CreateDate;

	private String ModifyUser;

	private Date ModifyDate;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("Id", DataColumn.STRING, 0, 255 , 0 , true , true),
		new SchemaColumn("ProductName", DataColumn.STRING, 1, 255 , 0 , false , false),
		new SchemaColumn("LogoUrl", DataColumn.STRING, 2, 500 , 0 , false , false),
		new SchemaColumn("buynum", DataColumn.STRING, 3, 500 , 0 , false , false),
		new SchemaColumn("productid", DataColumn.STRING, 4, 255 , 0 , false , false),
		new SchemaColumn("initprice", DataColumn.STRING, 5, 255 , 0 , false , false),
		new SchemaColumn("URL", DataColumn.STRING, 6, 500 , 0 , false , false),
		new SchemaColumn("endtime", DataColumn.DATETIME, 7, 0 , 0 , false , false),
		new SchemaColumn("CreateUser", DataColumn.STRING, 8, 255 , 0 , false , false),
		new SchemaColumn("CreateDate", DataColumn.DATETIME, 9, 0 , 0 , false , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 10, 255 , 0 , false , false),
		new SchemaColumn("ModifyDate", DataColumn.DATETIME, 11, 0 , 0 , false , false)
	};

	public static final String _TableCode = "SDPromotionHomePageSpecialProduct";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into SDPromotionHomePageSpecialProduct values(?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update SDPromotionHomePageSpecialProduct set Id=?,ProductName=?,LogoUrl=?,buynum=?,productid=?,initprice=?,URL=?,endtime=?,CreateUser=?,CreateDate=?,ModifyUser=?,ModifyDate=? where Id=?";

	protected static final String _DeleteSQL = "delete from SDPromotionHomePageSpecialProduct  where Id=?";

	protected static final String _FillAllSQL = "select * from SDPromotionHomePageSpecialProduct  where Id=?";

	public SDPromotionHomePageSpecialProductSchema(){
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
		return new SDPromotionHomePageSpecialProductSchema();
	}

	protected SchemaSet newSet(){
		return new SDPromotionHomePageSpecialProductSet();
	}

	public SDPromotionHomePageSpecialProductSet query() {
		return query(null, -1, -1);
	}

	public SDPromotionHomePageSpecialProductSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public SDPromotionHomePageSpecialProductSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public SDPromotionHomePageSpecialProductSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (SDPromotionHomePageSpecialProductSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){Id = (String)v;return;}
		if (i == 1){ProductName = (String)v;return;}
		if (i == 2){LogoUrl = (String)v;return;}
		if (i == 3){buynum = (String)v;return;}
		if (i == 4){productid = (String)v;return;}
		if (i == 5){initprice = (String)v;return;}
		if (i == 6){URL = (String)v;return;}
		if (i == 7){endtime = (Date)v;return;}
		if (i == 8){CreateUser = (String)v;return;}
		if (i == 9){CreateDate = (Date)v;return;}
		if (i == 10){ModifyUser = (String)v;return;}
		if (i == 11){ModifyDate = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return Id;}
		if (i == 1){return ProductName;}
		if (i == 2){return LogoUrl;}
		if (i == 3){return buynum;}
		if (i == 4){return productid;}
		if (i == 5){return initprice;}
		if (i == 6){return URL;}
		if (i == 7){return endtime;}
		if (i == 8){return CreateUser;}
		if (i == 9){return CreateDate;}
		if (i == 10){return ModifyUser;}
		if (i == 11){return ModifyDate;}
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
	* 获取字段ProductName的值，该字段的<br>
	* 字段名称 :产品描述<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProductName() {
		return ProductName;
	}

	/**
	* 设置字段ProductName的值，该字段的<br>
	* 字段名称 :产品描述<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProductName(String productName) {
		this.ProductName = productName;
    }

	/**
	* 获取字段LogoUrl的值，该字段的<br>
	* 字段名称 :产品图片<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getLogoUrl() {
		return LogoUrl;
	}

	/**
	* 设置字段LogoUrl的值，该字段的<br>
	* 字段名称 :产品图片<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setLogoUrl(String logoUrl) {
		this.LogoUrl = logoUrl;
    }

	/**
	* 获取字段buynum的值，该字段的<br>
	* 字段名称 :购买人数<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getbuynum() {
		return buynum;
	}

	/**
	* 设置字段buynum的值，该字段的<br>
	* 字段名称 :购买人数<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setbuynum(String buynum) {
		this.buynum = buynum;
    }

	/**
	* 获取字段productid的值，该字段的<br>
	* 字段名称 :产品id<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getproductid() {
		return productid;
	}

	/**
	* 设置字段productid的值，该字段的<br>
	* 字段名称 :产品id<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setproductid(String productid) {
		this.productid = productid;
    }

	/**
	* 获取字段initprice的值，该字段的<br>
	* 字段名称 :产品价格<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getinitprice() {
		return initprice;
	}

	/**
	* 设置字段initprice的值，该字段的<br>
	* 字段名称 :产品价格<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setinitprice(String initprice) {
		this.initprice = initprice;
    }

	/**
	* 获取字段URL的值，该字段的<br>
	* 字段名称 :跳转地址<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getURL() {
		return URL;
	}

	/**
	* 设置字段URL的值，该字段的<br>
	* 字段名称 :跳转地址<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setURL(String uRL) {
		this.URL = uRL;
    }

	/**
	* 获取字段endtime的值，该字段的<br>
	* 字段名称 :结束时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getendtime() {
		return endtime;
	}

	/**
	* 设置字段endtime的值，该字段的<br>
	* 字段名称 :结束时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setendtime(Date endtime) {
		this.endtime = endtime;
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