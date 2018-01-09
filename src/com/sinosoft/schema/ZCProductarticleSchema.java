package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：产品沉淀表<br>
 * 表代码：ZCProductarticle<br>
 * 表主键：ID<br>
 */
public class ZCProductarticleSchema extends Schema {
	private Long ID;

	private Long SiteID;

	private Long CatalogID;

	private String CatalogInnerCode;

	private String BranchInnerCode;

	private String Title;

	private String Type;

	private String URL;

	private String Status;

	private String TopFlag;

	private Long OrderFlag;

	private String Logo;

	private String RiskType;

	private String productId;

	private String SalesVolume;

	private String CalHTML2;

	private String AdaptPeopleInfo;

	private String fEMRiskBrightSpot;

	private String DutyHTML;

	private String prodcutMarkPrice;

	private String SupplierCode2;

	private String Popular;

	private String InitPrem;

	private Date modifyDate;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 20 , 0 , true , true),
		new SchemaColumn("SiteID", DataColumn.LONG, 1, 20 , 0 , false , false),
		new SchemaColumn("CatalogID", DataColumn.LONG, 2, 20 , 0 , true , false),
		new SchemaColumn("CatalogInnerCode", DataColumn.STRING, 3, 100 , 0 , true , false),
		new SchemaColumn("BranchInnerCode", DataColumn.STRING, 4, 100 , 0 , false , false),
		new SchemaColumn("Title", DataColumn.STRING, 5, 200 , 0 , false , false),
		new SchemaColumn("Type", DataColumn.STRING, 6, 2 , 0 , false , false),
		new SchemaColumn("URL", DataColumn.STRING, 7, 200 , 0 , false , false),
		new SchemaColumn("Status", DataColumn.STRING, 8, 20 , 0 , false , false),
		new SchemaColumn("TopFlag", DataColumn.STRING, 9, 2 , 0 , false , false),
		new SchemaColumn("OrderFlag", DataColumn.LONG, 10, 20 , 0 , true , false),
		new SchemaColumn("Logo", DataColumn.STRING, 11, 200 , 0 , false , false),
		new SchemaColumn("RiskType", DataColumn.STRING, 12, 2000 , 0 , false , false),
		new SchemaColumn("productId", DataColumn.STRING, 13, 50 , 0 , true , false),
		new SchemaColumn("SalesVolume", DataColumn.CLOB, 14, 0 , 0 , false , false),
		new SchemaColumn("CalHTML2", DataColumn.CLOB, 15, 0 , 0 , false , false),
		new SchemaColumn("AdaptPeopleInfo", DataColumn.CLOB, 16, 0 , 0 , false , false),
		new SchemaColumn("fEMRiskBrightSpot", DataColumn.CLOB, 17, 0 , 0 , false , false),
		new SchemaColumn("DutyHTML", DataColumn.CLOB, 18, 0 , 0 , false , false),
		new SchemaColumn("prodcutMarkPrice", DataColumn.CLOB, 19, 0 , 0 , false , false),
		new SchemaColumn("SupplierCode2", DataColumn.CLOB, 20, 0 , 0 , false , false),
		new SchemaColumn("Popular", DataColumn.CLOB, 21, 0 , 0 , false , false),
		new SchemaColumn("InitPrem", DataColumn.CLOB, 22, 0 , 0 , false , false),
		new SchemaColumn("modifyDate", DataColumn.DATETIME, 23, 0 , 0 , false , false)
	};

	public static final String _TableCode = "ZCProductarticle";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into ZCProductarticle values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZCProductarticle set ID=?,SiteID=?,CatalogID=?,CatalogInnerCode=?,BranchInnerCode=?,Title=?,Type=?,URL=?,Status=?,TopFlag=?,OrderFlag=?,Logo=?,RiskType=?,productId=?,SalesVolume=?,CalHTML2=?,AdaptPeopleInfo=?,fEMRiskBrightSpot=?,DutyHTML=?,prodcutMarkPrice=?,SupplierCode2=?,Popular=?,InitPrem=?,modifyDate=? where ID=?";

	protected static final String _DeleteSQL = "delete from ZCProductarticle  where ID=?";

	protected static final String _FillAllSQL = "select * from ZCProductarticle  where ID=?";

	public ZCProductarticleSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[24];
	}

	protected Schema newInstance(){
		return new ZCProductarticleSchema();
	}

	protected SchemaSet newSet(){
		return new ZCProductarticleSet();
	}

	public ZCProductarticleSet query() {
		return query(null, -1, -1);
	}

	public ZCProductarticleSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZCProductarticleSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZCProductarticleSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZCProductarticleSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){SiteID = null;}else{SiteID = new Long(v.toString());}return;}
		if (i == 2){if(v==null){CatalogID = null;}else{CatalogID = new Long(v.toString());}return;}
		if (i == 3){CatalogInnerCode = (String)v;return;}
		if (i == 4){BranchInnerCode = (String)v;return;}
		if (i == 5){Title = (String)v;return;}
		if (i == 6){Type = (String)v;return;}
		if (i == 7){URL = (String)v;return;}
		if (i == 8){Status = (String)v;return;}
		if (i == 9){TopFlag = (String)v;return;}
		if (i == 10){if(v==null){OrderFlag = null;}else{OrderFlag = new Long(v.toString());}return;}
		if (i == 11){Logo = (String)v;return;}
		if (i == 12){RiskType = (String)v;return;}
		if (i == 13){productId = (String)v;return;}
		if (i == 14){SalesVolume = (String)v;return;}
		if (i == 15){CalHTML2 = (String)v;return;}
		if (i == 16){AdaptPeopleInfo = (String)v;return;}
		if (i == 17){fEMRiskBrightSpot = (String)v;return;}
		if (i == 18){DutyHTML = (String)v;return;}
		if (i == 19){prodcutMarkPrice = (String)v;return;}
		if (i == 20){SupplierCode2 = (String)v;return;}
		if (i == 21){Popular = (String)v;return;}
		if (i == 22){InitPrem = (String)v;return;}
		if (i == 23){modifyDate = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return SiteID;}
		if (i == 2){return CatalogID;}
		if (i == 3){return CatalogInnerCode;}
		if (i == 4){return BranchInnerCode;}
		if (i == 5){return Title;}
		if (i == 6){return Type;}
		if (i == 7){return URL;}
		if (i == 8){return Status;}
		if (i == 9){return TopFlag;}
		if (i == 10){return OrderFlag;}
		if (i == 11){return Logo;}
		if (i == 12){return RiskType;}
		if (i == 13){return productId;}
		if (i == 14){return SalesVolume;}
		if (i == 15){return CalHTML2;}
		if (i == 16){return AdaptPeopleInfo;}
		if (i == 17){return fEMRiskBrightSpot;}
		if (i == 18){return DutyHTML;}
		if (i == 19){return prodcutMarkPrice;}
		if (i == 20){return SupplierCode2;}
		if (i == 21){return Popular;}
		if (i == 22){return InitPrem;}
		if (i == 23){return modifyDate;}
		return null;
	}

	/**
	* 获取字段ID的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public long getID() {
		if(ID==null){return 0;}
		return ID.longValue();
	}

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setID(long iD) {
		this.ID = new Long(iD);
    }

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setID(String iD) {
		if (iD == null){
			this.ID = null;
			return;
		}
		this.ID = new Long(iD);
    }

	/**
	* 获取字段SiteID的值，该字段的<br>
	* 字段名称 :站点id<br>
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
	* 字段名称 :站点id<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSiteID(long siteID) {
		this.SiteID = new Long(siteID);
    }

	/**
	* 设置字段SiteID的值，该字段的<br>
	* 字段名称 :站点id<br>
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
	* 获取字段CatalogID的值，该字段的<br>
	* 字段名称 :栏目id<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getCatalogID() {
		if(CatalogID==null){return 0;}
		return CatalogID.longValue();
	}

	/**
	* 设置字段CatalogID的值，该字段的<br>
	* 字段名称 :栏目id<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setCatalogID(long catalogID) {
		this.CatalogID = new Long(catalogID);
    }

	/**
	* 设置字段CatalogID的值，该字段的<br>
	* 字段名称 :栏目id<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setCatalogID(String catalogID) {
		if (catalogID == null){
			this.CatalogID = null;
			return;
		}
		this.CatalogID = new Long(catalogID);
    }

	/**
	* 获取字段CatalogInnerCode的值，该字段的<br>
	* 字段名称 :栏目内部编码<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getCatalogInnerCode() {
		return CatalogInnerCode;
	}

	/**
	* 设置字段CatalogInnerCode的值，该字段的<br>
	* 字段名称 :栏目内部编码<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setCatalogInnerCode(String catalogInnerCode) {
		this.CatalogInnerCode = catalogInnerCode;
    }

	/**
	* 获取字段BranchInnerCode的值，该字段的<br>
	* 字段名称 :栏目父级编码<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getBranchInnerCode() {
		return BranchInnerCode;
	}

	/**
	* 设置字段BranchInnerCode的值，该字段的<br>
	* 字段名称 :栏目父级编码<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setBranchInnerCode(String branchInnerCode) {
		this.BranchInnerCode = branchInnerCode;
    }

	/**
	* 获取字段Title的值，该字段的<br>
	* 字段名称 :文章头<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getTitle() {
		return Title;
	}

	/**
	* 设置字段Title的值，该字段的<br>
	* 字段名称 :文章头<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setTitle(String title) {
		this.Title = title;
    }

	/**
	* 获取字段Type的值，该字段的<br>
	* 字段名称 :文章类型<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getType() {
		return Type;
	}

	/**
	* 设置字段Type的值，该字段的<br>
	* 字段名称 :文章类型<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setType(String type) {
		this.Type = type;
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
	* 获取字段Status的值，该字段的<br>
	* 字段名称 :状态<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getStatus() {
		return Status;
	}

	/**
	* 设置字段Status的值，该字段的<br>
	* 字段名称 :状态<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setStatus(String status) {
		this.Status = status;
    }

	/**
	* 获取字段TopFlag的值，该字段的<br>
	* 字段名称 :置顶标记<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getTopFlag() {
		return TopFlag;
	}

	/**
	* 设置字段TopFlag的值，该字段的<br>
	* 字段名称 :置顶标记<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setTopFlag(String topFlag) {
		this.TopFlag = topFlag;
    }

	/**
	* 获取字段OrderFlag的值，该字段的<br>
	* 字段名称 :排序字段<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getOrderFlag() {
		if(OrderFlag==null){return 0;}
		return OrderFlag.longValue();
	}

	/**
	* 设置字段OrderFlag的值，该字段的<br>
	* 字段名称 :排序字段<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setOrderFlag(long orderFlag) {
		this.OrderFlag = new Long(orderFlag);
    }

	/**
	* 设置字段OrderFlag的值，该字段的<br>
	* 字段名称 :排序字段<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setOrderFlag(String orderFlag) {
		if (orderFlag == null){
			this.OrderFlag = null;
			return;
		}
		this.OrderFlag = new Long(orderFlag);
    }

	/**
	* 获取字段Logo的值，该字段的<br>
	* 字段名称 :产品logo<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getLogo() {
		return Logo;
	}

	/**
	* 设置字段Logo的值，该字段的<br>
	* 字段名称 :产品logo<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setLogo(String logo) {
		this.Logo = logo;
    }

	/**
	* 获取字段RiskType的值，该字段的<br>
	* 字段名称 :产品类型<br>
	* 数据类型 :varchar(2000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getRiskType() {
		return RiskType;
	}

	/**
	* 设置字段RiskType的值，该字段的<br>
	* 字段名称 :产品类型<br>
	* 数据类型 :varchar(2000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRiskType(String riskType) {
		this.RiskType = riskType;
    }

	/**
	* 获取字段productId的值，该字段的<br>
	* 字段名称 :产品id<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getproductId() {
		return productId;
	}

	/**
	* 设置字段productId的值，该字段的<br>
	* 字段名称 :产品id<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setproductId(String productId) {
		this.productId = productId;
    }

	/**
	* 获取字段SalesVolume的值，该字段的<br>
	* 字段名称 :产品累计销量<br>
	* 数据类型 :mediumtext<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSalesVolume() {
		return SalesVolume;
	}

	/**
	* 设置字段SalesVolume的值，该字段的<br>
	* 字段名称 :产品累计销量<br>
	* 数据类型 :mediumtext<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSalesVolume(String salesVolume) {
		this.SalesVolume = salesVolume;
    }

	/**
	* 获取字段CalHTML2的值，该字段的<br>
	* 字段名称 :投保要素HTML2<br>
	* 数据类型 :mediumtext<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCalHTML2() {
		return CalHTML2;
	}

	/**
	* 设置字段CalHTML2的值，该字段的<br>
	* 字段名称 :投保要素HTML2<br>
	* 数据类型 :mediumtext<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCalHTML2(String calHTML2) {
		this.CalHTML2 = calHTML2;
    }

	/**
	* 获取字段AdaptPeopleInfo的值，该字段的<br>
	* 字段名称 :产品适合人群<br>
	* 数据类型 :mediumtext<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAdaptPeopleInfo() {
		return AdaptPeopleInfo;
	}

	/**
	* 设置字段AdaptPeopleInfo的值，该字段的<br>
	* 字段名称 :产品适合人群<br>
	* 数据类型 :mediumtext<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAdaptPeopleInfo(String adaptPeopleInfo) {
		this.AdaptPeopleInfo = adaptPeopleInfo;
    }

	/**
	* 获取字段fEMRiskBrightSpot的值，该字段的<br>
	* 字段名称 :产品亮点<br>
	* 数据类型 :mediumtext<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getfEMRiskBrightSpot() {
		return fEMRiskBrightSpot;
	}

	/**
	* 设置字段fEMRiskBrightSpot的值，该字段的<br>
	* 字段名称 :产品亮点<br>
	* 数据类型 :mediumtext<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setfEMRiskBrightSpot(String fEMRiskBrightSpot) {
		this.fEMRiskBrightSpot = fEMRiskBrightSpot;
    }

	/**
	* 获取字段DutyHTML的值，该字段的<br>
	* 字段名称 :责任信HTML<br>
	* 数据类型 :mediumtext<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getDutyHTML() {
		return DutyHTML;
	}

	/**
	* 设置字段DutyHTML的值，该字段的<br>
	* 字段名称 :责任信HTML<br>
	* 数据类型 :mediumtext<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDutyHTML(String dutyHTML) {
		this.DutyHTML = dutyHTML;
    }

	/**
	* 获取字段prodcutMarkPrice的值，该字段的<br>
	* 字段名称 :列表页面价格标签<br>
	* 数据类型 :mediumtext<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprodcutMarkPrice() {
		return prodcutMarkPrice;
	}

	/**
	* 设置字段prodcutMarkPrice的值，该字段的<br>
	* 字段名称 :列表页面价格标签<br>
	* 数据类型 :mediumtext<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprodcutMarkPrice(String prodcutMarkPrice) {
		this.prodcutMarkPrice = prodcutMarkPrice;
    }

	/**
	* 获取字段SupplierCode2的值，该字段的<br>
	* 字段名称 :保险公司总部编码<br>
	* 数据类型 :mediumtext<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSupplierCode2() {
		return SupplierCode2;
	}

	/**
	* 设置字段SupplierCode2的值，该字段的<br>
	* 字段名称 :保险公司总部编码<br>
	* 数据类型 :mediumtext<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSupplierCode2(String supplierCode2) {
		this.SupplierCode2 = supplierCode2;
    }

	/**
	* 获取字段Popular的值，该字段的<br>
	* 字段名称 :产品人气<br>
	* 数据类型 :mediumtext<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPopular() {
		return Popular;
	}

	/**
	* 设置字段Popular的值，该字段的<br>
	* 字段名称 :产品人气<br>
	* 数据类型 :mediumtext<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPopular(String popular) {
		this.Popular = popular;
    }

	/**
	* 获取字段InitPrem的值，该字段的<br>
	* 字段名称 :初始定价<br>
	* 数据类型 :mediumtext<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getInitPrem() {
		return InitPrem;
	}

	/**
	* 设置字段InitPrem的值，该字段的<br>
	* 字段名称 :初始定价<br>
	* 数据类型 :mediumtext<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setInitPrem(String initPrem) {
		this.InitPrem = initPrem;
    }

	/**
	* 获取字段modifyDate的值，该字段的<br>
	* 字段名称 :修改时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getmodifyDate() {
		return modifyDate;
	}

	/**
	* 设置字段modifyDate的值，该字段的<br>
	* 字段名称 :修改时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmodifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
    }

}