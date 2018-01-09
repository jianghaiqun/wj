package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：筛选产品信息表<br>
 * 表代码：SDSearchProductInfo<br>
 * 表主键：id<br>
 */
public class SDSearchProductInfoSchema extends Schema {
	private Long id;

	private String SearchID;

	private String Title;

	private String FEMRiskBrightSpot;

	private String AdaptPeopleInfo;

	private String SupplierCode2;

	private String URL;

	private String CalHTML2;

	private String DutyHTML;

	private String prodcutMarkPrice;

	private String SalesVolume;

	private String Popular;

	private String RiskCode;

	private String InitPrem;

	private String DynamicHTML;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	private String Prop4;

	private Date createDate;

	private Date ModifyDate;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.LONG, 0, 20 , 0 , true , true),
		new SchemaColumn("SearchID", DataColumn.STRING, 1, 500 , 0 , false , false),
		new SchemaColumn("Title", DataColumn.STRING, 2, 200 , 0 , false , false),
		new SchemaColumn("FEMRiskBrightSpot", DataColumn.STRING, 3, 1000 , 0 , false , false),
		new SchemaColumn("AdaptPeopleInfo", DataColumn.STRING, 4, 1000 , 0 , false , false),
		new SchemaColumn("SupplierCode2", DataColumn.STRING, 5, 20 , 0 , false , false),
		new SchemaColumn("URL", DataColumn.STRING, 6, 200 , 0 , false , false),
		new SchemaColumn("CalHTML2", DataColumn.CLOB, 7, 0 , 0 , false , false),
		new SchemaColumn("DutyHTML", DataColumn.CLOB, 8, 0 , 0 , false , false),
		new SchemaColumn("prodcutMarkPrice", DataColumn.STRING, 9, 500 , 0 , false , false),
		new SchemaColumn("SalesVolume", DataColumn.STRING, 10, 20 , 0 , false , false),
		new SchemaColumn("Popular", DataColumn.STRING, 11, 20 , 0 , false , false),
		new SchemaColumn("RiskCode", DataColumn.STRING, 12, 20 , 0 , false , false),
		new SchemaColumn("InitPrem", DataColumn.STRING, 13, 20 , 0 , false , false),
		new SchemaColumn("DynamicHTML", DataColumn.CLOB, 14, 0 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 15, 255 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 16, 255 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 17, 255 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 18, 255 , 0 , false , false),
		new SchemaColumn("createDate", DataColumn.DATETIME, 19, 0 , 0 , true , false),
		new SchemaColumn("ModifyDate", DataColumn.DATETIME, 20, 0 , 0 , false , false)
	};

	public static final String _TableCode = "SDSearchProductInfo";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into SDSearchProductInfo values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update SDSearchProductInfo set id=?,SearchID=?,Title=?,FEMRiskBrightSpot=?,AdaptPeopleInfo=?,SupplierCode2=?,URL=?,CalHTML2=?,DutyHTML=?,prodcutMarkPrice=?,SalesVolume=?,Popular=?,RiskCode=?,InitPrem=?,DynamicHTML=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,createDate=?,ModifyDate=? where id=?";

	protected static final String _DeleteSQL = "delete from SDSearchProductInfo  where id=?";

	protected static final String _FillAllSQL = "select * from SDSearchProductInfo  where id=?";

	public SDSearchProductInfoSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[21];
	}

	protected Schema newInstance(){
		return new SDSearchProductInfoSchema();
	}

	protected SchemaSet newSet(){
		return new SDSearchProductInfoSet();
	}

	public SDSearchProductInfoSet query() {
		return query(null, -1, -1);
	}

	public SDSearchProductInfoSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public SDSearchProductInfoSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public SDSearchProductInfoSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (SDSearchProductInfoSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (Long)v;return;}
		if (i == 1){SearchID = (String)v;return;}
		if (i == 2){Title = (String)v;return;}
		if (i == 3){FEMRiskBrightSpot = (String)v;return;}
		if (i == 4){AdaptPeopleInfo = (String)v;return;}
		if (i == 5){SupplierCode2 = (String)v;return;}
		if (i == 6){URL = (String)v;return;}
		if (i == 7){CalHTML2 = (String)v;return;}
		if (i == 8){DutyHTML = (String)v;return;}
		if (i == 9){prodcutMarkPrice = (String)v;return;}
		if (i == 10){SalesVolume = (String)v;return;}
		if (i == 11){Popular = (String)v;return;}
		if (i == 12){RiskCode = (String)v;return;}
		if (i == 13){InitPrem = (String)v;return;}
		if (i == 14){DynamicHTML = (String)v;return;}
		if (i == 15){Prop1 = (String)v;return;}
		if (i == 16){Prop2 = (String)v;return;}
		if (i == 17){Prop3 = (String)v;return;}
		if (i == 18){Prop4 = (String)v;return;}
		if (i == 19){createDate = (Date)v;return;}
		if (i == 20){ModifyDate = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return SearchID;}
		if (i == 2){return Title;}
		if (i == 3){return FEMRiskBrightSpot;}
		if (i == 4){return AdaptPeopleInfo;}
		if (i == 5){return SupplierCode2;}
		if (i == 6){return URL;}
		if (i == 7){return CalHTML2;}
		if (i == 8){return DutyHTML;}
		if (i == 9){return prodcutMarkPrice;}
		if (i == 10){return SalesVolume;}
		if (i == 11){return Popular;}
		if (i == 12){return RiskCode;}
		if (i == 13){return InitPrem;}
		if (i == 14){return DynamicHTML;}
		if (i == 15){return Prop1;}
		if (i == 16){return Prop2;}
		if (i == 17){return Prop3;}
		if (i == 18){return Prop4;}
		if (i == 19){return createDate;}
		if (i == 20){return ModifyDate;}
		return null;
	}

	/**
	* 获取字段id的值，该字段的<br>
	* 字段名称 :流水ID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public long getid() {
		if(id==null){return 0;}
		return id.longValue();
	}

	/**
	* 设置字段id的值，该字段的<br>
	* 字段名称 :流水ID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setid(long id) {
		this.id = new Long(id);
    }

	/**
	* 设置字段id的值，该字段的<br>
	* 字段名称 :流水ID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setid(String id) {
		if (id == null){
			this.id = null;
			return;
		}
		this.id = new Long(id);
    }

	/**
	* 获取字段SearchID的值，该字段的<br>
	* 字段名称 :筛选ID<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSearchID() {
		return SearchID;
	}

	/**
	* 设置字段SearchID的值，该字段的<br>
	* 字段名称 :筛选ID<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSearchID(String searchID) {
		this.SearchID = searchID;
    }

	/**
	* 获取字段Title的值，该字段的<br>
	* 字段名称 :产品标题<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getTitle() {
		return Title;
	}

	/**
	* 设置字段Title的值，该字段的<br>
	* 字段名称 :产品标题<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setTitle(String title) {
		this.Title = title;
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
	* 获取字段AdaptPeopleInfo的值，该字段的<br>
	* 字段名称 :适合人群<br>
	* 数据类型 :varchar(1000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAdaptPeopleInfo() {
		return AdaptPeopleInfo;
	}

	/**
	* 设置字段AdaptPeopleInfo的值，该字段的<br>
	* 字段名称 :适合人群<br>
	* 数据类型 :varchar(1000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAdaptPeopleInfo(String adaptPeopleInfo) {
		this.AdaptPeopleInfo = adaptPeopleInfo;
    }

	/**
	* 获取字段SupplierCode2的值，该字段的<br>
	* 字段名称 :保险公司编码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSupplierCode2() {
		return SupplierCode2;
	}

	/**
	* 设置字段SupplierCode2的值，该字段的<br>
	* 字段名称 :保险公司编码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSupplierCode2(String supplierCode2) {
		this.SupplierCode2 = supplierCode2;
    }

	/**
	* 获取字段URL的值，该字段的<br>
	* 字段名称 :产品URL<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getURL() {
		return URL;
	}

	/**
	* 设置字段URL的值，该字段的<br>
	* 字段名称 :产品URL<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setURL(String uRL) {
		this.URL = uRL;
    }

	/**
	* 获取字段CalHTML2的值，该字段的<br>
	* 字段名称 :投保要素<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCalHTML2() {
		return CalHTML2;
	}

	/**
	* 设置字段CalHTML2的值，该字段的<br>
	* 字段名称 :投保要素<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCalHTML2(String calHTML2) {
		this.CalHTML2 = calHTML2;
    }

	/**
	* 获取字段DutyHTML的值，该字段的<br>
	* 字段名称 :责任要素<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getDutyHTML() {
		return DutyHTML;
	}

	/**
	* 设置字段DutyHTML的值，该字段的<br>
	* 字段名称 :责任要素<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDutyHTML(String dutyHTML) {
		this.DutyHTML = dutyHTML;
    }

	/**
	* 获取字段prodcutMarkPrice的值，该字段的<br>
	* 字段名称 :产品市场价格<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprodcutMarkPrice() {
		return prodcutMarkPrice;
	}

	/**
	* 设置字段prodcutMarkPrice的值，该字段的<br>
	* 字段名称 :产品市场价格<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprodcutMarkPrice(String prodcutMarkPrice) {
		this.prodcutMarkPrice = prodcutMarkPrice;
    }

	/**
	* 获取字段SalesVolume的值，该字段的<br>
	* 字段名称 :销量<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSalesVolume() {
		return SalesVolume;
	}

	/**
	* 设置字段SalesVolume的值，该字段的<br>
	* 字段名称 :销量<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSalesVolume(String salesVolume) {
		this.SalesVolume = salesVolume;
    }

	/**
	* 获取字段Popular的值，该字段的<br>
	* 字段名称 :人气<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPopular() {
		return Popular;
	}

	/**
	* 设置字段Popular的值，该字段的<br>
	* 字段名称 :人气<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPopular(String popular) {
		this.Popular = popular;
    }

	/**
	* 获取字段RiskCode的值，该字段的<br>
	* 字段名称 :险种编码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getRiskCode() {
		return RiskCode;
	}

	/**
	* 设置字段RiskCode的值，该字段的<br>
	* 字段名称 :险种编码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRiskCode(String riskCode) {
		this.RiskCode = riskCode;
    }

	/**
	* 获取字段InitPrem的值，该字段的<br>
	* 字段名称 :价格<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getInitPrem() {
		return InitPrem;
	}

	/**
	* 设置字段InitPrem的值，该字段的<br>
	* 字段名称 :价格<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setInitPrem(String initPrem) {
		this.InitPrem = initPrem;
    }

	/**
	* 获取字段DynamicHTML的值，该字段的<br>
	* 字段名称 :拼装HTML<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getDynamicHTML() {
		return DynamicHTML;
	}

	/**
	* 设置字段DynamicHTML的值，该字段的<br>
	* 字段名称 :拼装HTML<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDynamicHTML(String dynamicHTML) {
		this.DynamicHTML = dynamicHTML;
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
	* 获取字段createDate的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public Date getcreateDate() {
		return createDate;
	}

	/**
	* 设置字段createDate的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setcreateDate(Date createDate) {
		this.createDate = createDate;
    }

	/**
	* 获取字段ModifyDate的值，该字段的<br>
	* 字段名称 :修改时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getModifyDate() {
		return ModifyDate;
	}

	/**
	* 设置字段ModifyDate的值，该字段的<br>
	* 字段名称 :修改时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyDate(Date modifyDate) {
		this.ModifyDate = modifyDate;
    }

}