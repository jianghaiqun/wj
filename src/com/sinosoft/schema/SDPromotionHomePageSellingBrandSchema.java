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

/**
 * 表名称：促销频道首页热卖品牌<br>
 * 表代码：SDPromotionHomePageSellingBrand<br>
 * 表主键：Id<br>
 */
public class SDPromotionHomePageSellingBrandSchema extends Schema {
	private String Id;

	private String BrandName;

	private String LogoUrl;

	private String count;

	private String buynum;

	private String URL;

	private Long OrderFlag;

	private String isShow;

	private String CreateUser;

	private Date CreateDate;

	private String ModifyUser;

	private Date ModifyDate;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("Id", DataColumn.STRING, 0, 255 , 0 , true , true),
		new SchemaColumn("BrandName", DataColumn.STRING, 1, 255 , 0 , false , false),
		new SchemaColumn("LogoUrl", DataColumn.STRING, 2, 500 , 0 , false , false),
		new SchemaColumn("count", DataColumn.STRING, 3, 10 , 0 , false , false),
		new SchemaColumn("buynum", DataColumn.STRING, 4, 500 , 0 , false , false),
		new SchemaColumn("URL", DataColumn.STRING, 5, 500 , 0 , false , false),
		new SchemaColumn("OrderFlag", DataColumn.LONG, 6, 20 , 0 , false , false),
		new SchemaColumn("isShow", DataColumn.STRING, 7, 10 , 0 , false , false),
		new SchemaColumn("CreateUser", DataColumn.STRING, 8, 255 , 0 , false , false),
		new SchemaColumn("CreateDate", DataColumn.DATETIME, 9, 0 , 0 , false , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 10, 255 , 0 , false , false),
		new SchemaColumn("ModifyDate", DataColumn.DATETIME, 11, 0 , 0 , false , false)
	};

	public static final String _TableCode = "SDPromotionHomePageSellingBrand";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into SDPromotionHomePageSellingBrand values(?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update SDPromotionHomePageSellingBrand set Id=?,BrandName=?,LogoUrl=?,count=?,buynum=?,URL=?,OrderFlag=?,isShow=?,CreateUser=?,CreateDate=?,ModifyUser=?,ModifyDate=? where Id=?";

	protected static final String _DeleteSQL = "delete from SDPromotionHomePageSellingBrand  where Id=?";

	protected static final String _FillAllSQL = "select * from SDPromotionHomePageSellingBrand  where Id=?";

	public SDPromotionHomePageSellingBrandSchema(){
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
		return new SDPromotionHomePageSellingBrandSchema();
	}

	protected SchemaSet newSet(){
		return new SDPromotionHomePageSellingBrandSet();
	}

	public SDPromotionHomePageSellingBrandSet query() {
		return query(null, -1, -1);
	}

	public SDPromotionHomePageSellingBrandSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public SDPromotionHomePageSellingBrandSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public SDPromotionHomePageSellingBrandSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (SDPromotionHomePageSellingBrandSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){Id = (String)v;return;}
		if (i == 1){BrandName = (String)v;return;}
		if (i == 2){LogoUrl = (String)v;return;}
		if (i == 3){count = (String)v;return;}
		if (i == 4){buynum = (String)v;return;}
		if (i == 5){URL = (String)v;return;}
		if (i == 6){if(v==null){OrderFlag = null;}else{OrderFlag = new Long(v.toString());}return;}
		if (i == 7){isShow = (String)v;return;}
		if (i == 8){CreateUser = (String)v;return;}
		if (i == 9){CreateDate = (Date)v;return;}
		if (i == 10){ModifyUser = (String)v;return;}
		if (i == 11){ModifyDate = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return Id;}
		if (i == 1){return BrandName;}
		if (i == 2){return LogoUrl;}
		if (i == 3){return count;}
		if (i == 4){return buynum;}
		if (i == 5){return URL;}
		if (i == 6){return OrderFlag;}
		if (i == 7){return isShow;}
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
	* 获取字段BrandName的值，该字段的<br>
	* 字段名称 :品牌名称<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getBrandName() {
		return BrandName;
	}

	/**
	* 设置字段BrandName的值，该字段的<br>
	* 字段名称 :品牌名称<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setBrandName(String brandName) {
		this.BrandName = brandName;
    }

	/**
	* 获取字段LogoUrl的值，该字段的<br>
	* 字段名称 :品牌图片<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getLogoUrl() {
		return LogoUrl;
	}

	/**
	* 设置字段LogoUrl的值，该字段的<br>
	* 字段名称 :品牌图片<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setLogoUrl(String logoUrl) {
		this.LogoUrl = logoUrl;
    }

	/**
	* 获取字段count的值，该字段的<br>
	* 字段名称 :数量<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcount() {
		return count;
	}

	/**
	* 设置字段count的值，该字段的<br>
	* 字段名称 :数量<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcount(String count) {
		this.count = count;
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
	* 获取字段isShow的值，该字段的<br>
	* 字段名称 :是否展示<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getisShow() {
		return isShow;
	}

	/**
	* 设置字段isShow的值，该字段的<br>
	* 字段名称 :是否展示<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setisShow(String isShow) {
		this.isShow = isShow;
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