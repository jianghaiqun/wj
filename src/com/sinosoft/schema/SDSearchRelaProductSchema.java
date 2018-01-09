package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：产品筛选表<br>
 * 表代码：SDSearchRelaProduct<br>
 * 表主键：ProductID<br>
 */
public class SDSearchRelaProductSchema extends Schema {
	private String ProductID;

	private String ProductName;

	private Long SiteID;

	private String URL;

	private String logo;

	private String SalesVolume;

	private String CalHTML2;

	private String AdaptPeopleInfo;

	private String FEMRiskBrightSpot;

	private String DutyHTML;

	private String prodcutMarkPrice;

	private String SupplierCode2;

	private String Popular;

	private String InitPrem;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	private String Prop4;

	private String FEMRiskBrightSpotNew;

	private String DutyHTML2;

	private String ProductActive;

	private String LogoLink;

	private String BasePrem;

	private String BasePremV3;

	private String AdaptPeopleInfoV3;

	private String AdaptPeopleInfoListV3;

	private String DutyHTMLV3;

	private String RecomDutyV3;

	private String InitPremCPS;

	private String prodcutMarkPriceCPS;

	private String BasePremValue;

	private Date CreateDate;

	private Date ModifyDate;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ProductID", DataColumn.STRING, 0, 50 , 0 , true , true),
		new SchemaColumn("ProductName", DataColumn.STRING, 1, 200 , 0 , false , false),
		new SchemaColumn("SiteID", DataColumn.LONG, 2, 20 , 0 , false , false),
		new SchemaColumn("URL", DataColumn.STRING, 3, 200 , 0 , false , false),
		new SchemaColumn("logo", DataColumn.STRING, 4, 100 , 0 , false , false),
		new SchemaColumn("SalesVolume", DataColumn.STRING, 5, 100 , 0 , false , false),
		new SchemaColumn("CalHTML2", DataColumn.CLOB, 6, 0 , 0 , false , false),
		new SchemaColumn("AdaptPeopleInfo", DataColumn.STRING, 7, 1000 , 0 , false , false),
		new SchemaColumn("FEMRiskBrightSpot", DataColumn.STRING, 8, 1000 , 0 , false , false),
		new SchemaColumn("DutyHTML", DataColumn.CLOB, 9, 0 , 0 , false , false),
		new SchemaColumn("prodcutMarkPrice", DataColumn.STRING, 10, 500 , 0 , false , false),
		new SchemaColumn("SupplierCode2", DataColumn.STRING, 11, 20 , 0 , false , false),
		new SchemaColumn("Popular", DataColumn.STRING, 12, 20 , 0 , false , false),
		new SchemaColumn("InitPrem", DataColumn.STRING, 13, 20 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 14, 255 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 15, 255 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 16, 255 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 17, 255 , 0 , false , false),
		new SchemaColumn("FEMRiskBrightSpotNew", DataColumn.CLOB, 18, 0 , 0 , false , false),
		new SchemaColumn("DutyHTML2", DataColumn.CLOB, 19, 0 , 0 , false , false),
		new SchemaColumn("ProductActive", DataColumn.CLOB, 20, 0 , 0 , false , false),
		new SchemaColumn("LogoLink", DataColumn.CLOB, 21, 0 , 0 , false , false),
		new SchemaColumn("BasePrem", DataColumn.CLOB, 22, 0 , 0 , false , false),
		new SchemaColumn("BasePremV3", DataColumn.CLOB, 23, 0 , 0 , false , false),
		new SchemaColumn("AdaptPeopleInfoV3", DataColumn.CLOB, 24, 0 , 0 , false , false),
		new SchemaColumn("AdaptPeopleInfoListV3", DataColumn.CLOB, 25, 0 , 0 , false , false),
		new SchemaColumn("DutyHTMLV3", DataColumn.CLOB, 26, 0 , 0 , false , false),
		new SchemaColumn("RecomDutyV3", DataColumn.CLOB, 27, 0 , 0 , false , false),
		new SchemaColumn("InitPremCPS", DataColumn.STRING, 28, 20 , 0 , false , false),
		new SchemaColumn("prodcutMarkPriceCPS", DataColumn.STRING, 29, 20 , 0 , false , false),
		new SchemaColumn("BasePremValue", DataColumn.STRING, 30, 20 , 0 , false , false),
		new SchemaColumn("CreateDate", DataColumn.DATETIME, 31, 0 , 0 , true , false),
		new SchemaColumn("ModifyDate", DataColumn.DATETIME, 32, 0 , 0 , false , false)
	};

	public static final String _TableCode = "SDSearchRelaProduct";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into SDSearchRelaProduct values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update SDSearchRelaProduct set ProductID=?,ProductName=?,SiteID=?,URL=?,logo=?,SalesVolume=?,CalHTML2=?,AdaptPeopleInfo=?,FEMRiskBrightSpot=?,DutyHTML=?,prodcutMarkPrice=?,SupplierCode2=?,Popular=?,InitPrem=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,FEMRiskBrightSpotNew=?,DutyHTML2=?,ProductActive=?,LogoLink=?,BasePrem=?,BasePremV3=?,AdaptPeopleInfoV3=?,AdaptPeopleInfoListV3=?,DutyHTMLV3=?,RecomDutyV3=?,InitPremCPS=?,prodcutMarkPriceCPS=?,BasePremValue=?,CreateDate=?,ModifyDate=? where ProductID=?";

	protected static final String _DeleteSQL = "delete from SDSearchRelaProduct  where ProductID=?";

	protected static final String _FillAllSQL = "select * from SDSearchRelaProduct  where ProductID=?";

	public SDSearchRelaProductSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[33];
	}

	protected Schema newInstance(){
		return new SDSearchRelaProductSchema();
	}

	protected SchemaSet newSet(){
		return new SDSearchRelaProductSet();
	}

	public SDSearchRelaProductSet query() {
		return query(null, -1, -1);
	}

	public SDSearchRelaProductSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public SDSearchRelaProductSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public SDSearchRelaProductSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (SDSearchRelaProductSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){ProductID = (String)v;return;}
		if (i == 1){ProductName = (String)v;return;}
		if (i == 2){if(v==null){SiteID = null;}else{SiteID = new Long(v.toString());}return;}
		if (i == 3){URL = (String)v;return;}
		if (i == 4){logo = (String)v;return;}
		if (i == 5){SalesVolume = (String)v;return;}
		if (i == 6){CalHTML2 = (String)v;return;}
		if (i == 7){AdaptPeopleInfo = (String)v;return;}
		if (i == 8){FEMRiskBrightSpot = (String)v;return;}
		if (i == 9){DutyHTML = (String)v;return;}
		if (i == 10){prodcutMarkPrice = (String)v;return;}
		if (i == 11){SupplierCode2 = (String)v;return;}
		if (i == 12){Popular = (String)v;return;}
		if (i == 13){InitPrem = (String)v;return;}
		if (i == 14){Prop1 = (String)v;return;}
		if (i == 15){Prop2 = (String)v;return;}
		if (i == 16){Prop3 = (String)v;return;}
		if (i == 17){Prop4 = (String)v;return;}
		if (i == 18){FEMRiskBrightSpotNew = (String)v;return;}
		if (i == 19){DutyHTML2 = (String)v;return;}
		if (i == 20){ProductActive = (String)v;return;}
		if (i == 21){LogoLink = (String)v;return;}
		if (i == 22){BasePrem = (String)v;return;}
		if (i == 23){BasePremV3 = (String)v;return;}
		if (i == 24){AdaptPeopleInfoV3 = (String)v;return;}
		if (i == 25){AdaptPeopleInfoListV3 = (String)v;return;}
		if (i == 26){DutyHTMLV3 = (String)v;return;}
		if (i == 27){RecomDutyV3 = (String)v;return;}
		if (i == 28){InitPremCPS = (String)v;return;}
		if (i == 29){prodcutMarkPriceCPS = (String)v;return;}
		if (i == 30){BasePremValue = (String)v;return;}
		if (i == 31){CreateDate = (Date)v;return;}
		if (i == 32){ModifyDate = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ProductID;}
		if (i == 1){return ProductName;}
		if (i == 2){return SiteID;}
		if (i == 3){return URL;}
		if (i == 4){return logo;}
		if (i == 5){return SalesVolume;}
		if (i == 6){return CalHTML2;}
		if (i == 7){return AdaptPeopleInfo;}
		if (i == 8){return FEMRiskBrightSpot;}
		if (i == 9){return DutyHTML;}
		if (i == 10){return prodcutMarkPrice;}
		if (i == 11){return SupplierCode2;}
		if (i == 12){return Popular;}
		if (i == 13){return InitPrem;}
		if (i == 14){return Prop1;}
		if (i == 15){return Prop2;}
		if (i == 16){return Prop3;}
		if (i == 17){return Prop4;}
		if (i == 18){return FEMRiskBrightSpotNew;}
		if (i == 19){return DutyHTML2;}
		if (i == 20){return ProductActive;}
		if (i == 21){return LogoLink;}
		if (i == 22){return BasePrem;}
		if (i == 23){return BasePremV3;}
		if (i == 24){return AdaptPeopleInfoV3;}
		if (i == 25){return AdaptPeopleInfoListV3;}
		if (i == 26){return DutyHTMLV3;}
		if (i == 27){return RecomDutyV3;}
		if (i == 28){return InitPremCPS;}
		if (i == 29){return prodcutMarkPriceCPS;}
		if (i == 30){return BasePremValue;}
		if (i == 31){return CreateDate;}
		if (i == 32){return ModifyDate;}
		return null;
	}

	/**
	* 获取字段ProductID的值，该字段的<br>
	* 字段名称 :产品ID<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getProductID() {
		return ProductID;
	}

	/**
	* 设置字段ProductID的值，该字段的<br>
	* 字段名称 :产品ID<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setProductID(String productID) {
		this.ProductID = productID;
    }

	/**
	* 获取字段ProductName的值，该字段的<br>
	* 字段名称 :产品名称<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProductName() {
		return ProductName;
	}

	/**
	* 设置字段ProductName的值，该字段的<br>
	* 字段名称 :产品名称<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProductName(String productName) {
		this.ProductName = productName;
    }

	/**
	* 获取字段SiteID的值，该字段的<br>
	* 字段名称 :站点ID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getSiteID() {
		if(SiteID==null){return 0;}
		return SiteID.longValue();
	}

	/**
	* 设置字段SiteID的值，该字段的<br>
	* 字段名称 :站点ID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSiteID(long siteID) {
		this.SiteID = new Long(siteID);
    }

	/**
	* 设置字段SiteID的值，该字段的<br>
	* 字段名称 :站点ID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSiteID(String siteID) {
		if (siteID == null){
			this.SiteID = null;
			return;
		}
		this.SiteID = new Long(siteID);
    }

	/**
	* 获取字段URL的值，该字段的<br>
	* 字段名称 :详细页地址<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getURL() {
		return URL;
	}

	/**
	* 设置字段URL的值，该字段的<br>
	* 字段名称 :详细页地址<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setURL(String uRL) {
		this.URL = uRL;
    }

	/**
	* 获取字段logo的值，该字段的<br>
	* 字段名称 :产品logo<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getlogo() {
		return logo;
	}

	/**
	* 设置字段logo的值，该字段的<br>
	* 字段名称 :产品logo<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setlogo(String logo) {
		this.logo = logo;
    }

	/**
	* 获取字段SalesVolume的值，该字段的<br>
	* 字段名称 :产品累计销量<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSalesVolume() {
		return SalesVolume;
	}

	/**
	* 设置字段SalesVolume的值，该字段的<br>
	* 字段名称 :产品累计销量<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSalesVolume(String salesVolume) {
		this.SalesVolume = salesVolume;
    }

	/**
	* 获取字段CalHTML2的值，该字段的<br>
	* 字段名称 :投保要素HTML2<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCalHTML2() {
		return CalHTML2;
	}

	/**
	* 设置字段CalHTML2的值，该字段的<br>
	* 字段名称 :投保要素HTML2<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCalHTML2(String calHTML2) {
		this.CalHTML2 = calHTML2;
    }

	/**
	* 获取字段AdaptPeopleInfo的值，该字段的<br>
	* 字段名称 :产品适合人群<br>
	* 数据类型 :varchar(1000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAdaptPeopleInfo() {
		return AdaptPeopleInfo;
	}

	/**
	* 设置字段AdaptPeopleInfo的值，该字段的<br>
	* 字段名称 :产品适合人群<br>
	* 数据类型 :varchar(1000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAdaptPeopleInfo(String adaptPeopleInfo) {
		this.AdaptPeopleInfo = adaptPeopleInfo;
    }

	/**
	* 获取字段FEMRiskBrightSpot的值，该字段的<br>
	* 字段名称 :产品亮点<br>
	* 数据类型 :varchar(1000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getFEMRiskBrightSpot() {
		return FEMRiskBrightSpot;
	}

	/**
	* 设置字段FEMRiskBrightSpot的值，该字段的<br>
	* 字段名称 :产品亮点<br>
	* 数据类型 :varchar(1000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setFEMRiskBrightSpot(String fEMRiskBrightSpot) {
		this.FEMRiskBrightSpot = fEMRiskBrightSpot;
    }

	/**
	* 获取字段DutyHTML的值，该字段的<br>
	* 字段名称 :责任信HTML<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getDutyHTML() {
		return DutyHTML;
	}

	/**
	* 设置字段DutyHTML的值，该字段的<br>
	* 字段名称 :责任信HTML<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDutyHTML(String dutyHTML) {
		this.DutyHTML = dutyHTML;
    }

	/**
	* 获取字段prodcutMarkPrice的值，该字段的<br>
	* 字段名称 :列表页面价格标签<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprodcutMarkPrice() {
		return prodcutMarkPrice;
	}

	/**
	* 设置字段prodcutMarkPrice的值，该字段的<br>
	* 字段名称 :列表页面价格标签<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprodcutMarkPrice(String prodcutMarkPrice) {
		this.prodcutMarkPrice = prodcutMarkPrice;
    }

	/**
	* 获取字段SupplierCode2的值，该字段的<br>
	* 字段名称 :保险公司总部编码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSupplierCode2() {
		return SupplierCode2;
	}

	/**
	* 设置字段SupplierCode2的值，该字段的<br>
	* 字段名称 :保险公司总部编码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSupplierCode2(String supplierCode2) {
		this.SupplierCode2 = supplierCode2;
    }

	/**
	* 获取字段Popular的值，该字段的<br>
	* 字段名称 :产品人气<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPopular() {
		return Popular;
	}

	/**
	* 设置字段Popular的值，该字段的<br>
	* 字段名称 :产品人气<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPopular(String popular) {
		this.Popular = popular;
    }

	/**
	* 获取字段InitPrem的值，该字段的<br>
	* 字段名称 :初始定价<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getInitPrem() {
		return InitPrem;
	}

	/**
	* 设置字段InitPrem的值，该字段的<br>
	* 字段名称 :初始定价<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setInitPrem(String initPrem) {
		this.InitPrem = initPrem;
    }

	/**
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :Prop1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :Prop1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* 获取字段Prop2的值，该字段的<br>
	* 字段名称 :Prop2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* 设置字段Prop2的值，该字段的<br>
	* 字段名称 :Prop2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

	/**
	* 获取字段Prop3的值，该字段的<br>
	* 字段名称 :Prop3<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp3() {
		return Prop3;
	}

	/**
	* 设置字段Prop3的值，该字段的<br>
	* 字段名称 :Prop3<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp3(String prop3) {
		this.Prop3 = prop3;
    }

	/**
	* 获取字段Prop4的值，该字段的<br>
	* 字段名称 :Prop4<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp4() {
		return Prop4;
	}

	/**
	* 设置字段Prop4的值，该字段的<br>
	* 字段名称 :Prop4<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp4(String prop4) {
		this.Prop4 = prop4;
    }

	/**
	* 获取字段FEMRiskBrightSpotNew的值，该字段的<br>
	* 字段名称 :类淘宝列表页产品亮点<br>
	* 数据类型 :mediumtext<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getFEMRiskBrightSpotNew() {
		return FEMRiskBrightSpotNew;
	}

	/**
	* 设置字段FEMRiskBrightSpotNew的值，该字段的<br>
	* 字段名称 :类淘宝列表页产品亮点<br>
	* 数据类型 :mediumtext<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setFEMRiskBrightSpotNew(String fEMRiskBrightSpotNew) {
		this.FEMRiskBrightSpotNew = fEMRiskBrightSpotNew;
    }

	/**
	* 获取字段DutyHTML2的值，该字段的<br>
	* 字段名称 :类淘宝列表页责任信HTML<br>
	* 数据类型 :mediumtext<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getDutyHTML2() {
		return DutyHTML2;
	}

	/**
	* 设置字段DutyHTML2的值，该字段的<br>
	* 字段名称 :类淘宝列表页责任信HTML<br>
	* 数据类型 :mediumtext<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDutyHTML2(String dutyHTML2) {
		this.DutyHTML2 = dutyHTML2;
    }

	/**
	* 获取字段ProductActive的值，该字段的<br>
	* 字段名称 :类淘宝列表页产品活动<br>
	* 数据类型 :mediumtext<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProductActive() {
		return ProductActive;
	}

	/**
	* 设置字段ProductActive的值，该字段的<br>
	* 字段名称 :类淘宝列表页产品活动<br>
	* 数据类型 :mediumtext<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProductActive(String productActive) {
		this.ProductActive = productActive;
    }

	/**
	* 获取字段LogoLink的值，该字段的<br>
	* 字段名称 :类淘宝列表页产品图片链接<br>
	* 数据类型 :mediumtext<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getLogoLink() {
		return LogoLink;
	}

	/**
	* 设置字段LogoLink的值，该字段的<br>
	* 字段名称 :类淘宝列表页产品图片链接<br>
	* 数据类型 :mediumtext<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setLogoLink(String logoLink) {
		this.LogoLink = logoLink;
    }

	/**
	* 获取字段BasePrem的值，该字段的<br>
	* 字段名称 :折扣价<br>
	* 数据类型 :mediumtext<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getBasePrem() {
		return BasePrem;
	}

	/**
	* 设置字段BasePrem的值，该字段的<br>
	* 字段名称 :折扣价<br>
	* 数据类型 :mediumtext<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setBasePrem(String basePrem) {
		this.BasePrem = basePrem;
    }

	/**
	* 获取字段BasePremV3的值，该字段的<br>
	* 字段名称 :列表页V3折扣价<br>
	* 数据类型 :mediumtext<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getBasePremV3() {
		return BasePremV3;
	}

	/**
	* 设置字段BasePremV3的值，该字段的<br>
	* 字段名称 :列表页V3折扣价<br>
	* 数据类型 :mediumtext<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setBasePremV3(String basePremV3) {
		this.BasePremV3 = basePremV3;
    }

	/**
	* 获取字段AdaptPeopleInfoV3的值，该字段的<br>
	* 字段名称 :列表页V3推荐产品适用人群<br>
	* 数据类型 :mediumtext<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAdaptPeopleInfoV3() {
		return AdaptPeopleInfoV3;
	}

	/**
	* 设置字段AdaptPeopleInfoV3的值，该字段的<br>
	* 字段名称 :列表页V3推荐产品适用人群<br>
	* 数据类型 :mediumtext<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAdaptPeopleInfoV3(String adaptPeopleInfoV3) {
		this.AdaptPeopleInfoV3 = adaptPeopleInfoV3;
    }

	/**
	* 获取字段AdaptPeopleInfoListV3的值，该字段的<br>
	* 字段名称 :列表页V3产品列表适用人群<br>
	* 数据类型 :mediumtext<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAdaptPeopleInfoListV3() {
		return AdaptPeopleInfoListV3;
	}

	/**
	* 设置字段AdaptPeopleInfoListV3的值，该字段的<br>
	* 字段名称 :列表页V3产品列表适用人群<br>
	* 数据类型 :mediumtext<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAdaptPeopleInfoListV3(String adaptPeopleInfoListV3) {
		this.AdaptPeopleInfoListV3 = adaptPeopleInfoListV3;
    }

	/**
	* 获取字段DutyHTMLV3的值，该字段的<br>
	* 字段名称 :列表页V3产品列表责任<br>
	* 数据类型 :mediumtext<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getDutyHTMLV3() {
		return DutyHTMLV3;
	}

	/**
	* 设置字段DutyHTMLV3的值，该字段的<br>
	* 字段名称 :列表页V3产品列表责任<br>
	* 数据类型 :mediumtext<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDutyHTMLV3(String dutyHTMLV3) {
		this.DutyHTMLV3 = dutyHTMLV3;
    }

	/**
	* 获取字段RecomDutyV3的值，该字段的<br>
	* 字段名称 :列表页V3推荐产品责任<br>
	* 数据类型 :mediumtext<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getRecomDutyV3() {
		return RecomDutyV3;
	}

	/**
	* 设置字段RecomDutyV3的值，该字段的<br>
	* 字段名称 :列表页V3推荐产品责任<br>
	* 数据类型 :mediumtext<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRecomDutyV3(String recomDutyV3) {
		this.RecomDutyV3 = recomDutyV3;
    }

	/**
	* 获取字段InitPremCPS的值，该字段的<br>
	* 字段名称 :分销联盟原保费<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getInitPremCPS() {
		return InitPremCPS;
	}

	/**
	* 设置字段InitPremCPS的值，该字段的<br>
	* 字段名称 :分销联盟原保费<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setInitPremCPS(String initPremCPS) {
		this.InitPremCPS = initPremCPS;
    }

	/**
	* 获取字段prodcutMarkPriceCPS的值，该字段的<br>
	* 字段名称 :分销联盟保费<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprodcutMarkPriceCPS() {
		return prodcutMarkPriceCPS;
	}

	/**
	* 设置字段prodcutMarkPriceCPS的值，该字段的<br>
	* 字段名称 :分销联盟保费<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprodcutMarkPriceCPS(String prodcutMarkPriceCPS) {
		this.prodcutMarkPriceCPS = prodcutMarkPriceCPS;
    }

	/**
	* 获取字段BasePremValue的值，该字段的<br>
	* 字段名称 :原价<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getBasePremValue() {
		return BasePremValue;
	}

	/**
	* 设置字段BasePremValue的值，该字段的<br>
	* 字段名称 :原价<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setBasePremValue(String basePremValue) {
		this.BasePremValue = basePremValue;
    }

	/**
	* 获取字段CreateDate的值，该字段的<br>
	* 字段名称 :CreateDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public Date getCreateDate() {
		return CreateDate;
	}

	/**
	* 设置字段CreateDate的值，该字段的<br>
	* 字段名称 :CreateDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
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