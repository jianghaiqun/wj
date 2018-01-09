package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：订单详细表（产品表）<br>
 * 表代码：SDInformation<br>
 * 表主键：Id<br>
 */
public class SDInformationSchema extends Schema { 
	private String Id;

	private Date createDate;

	private Date modifyDate;

	private String informationSn;

	private String orderSn;

	private String productId;

	private String productOutCode;

	private String productName;

	private String discountRates;

	private String productPrice;

	private String productDiscountPrice;

	private String productHtmlFilePath;

	private String productQuantity;

	private String insuranceCompany;

	private String point;

	private String pointStatus;

	private Date startDate;

	private Date endDate;

	private String ensureLimitType;

	private String ensureLimit;

	private String ensure;

	private String ensureDisplay;

	private String chargeDisplay;

	private String chargeType;

	private String chargeYear;

	private String chargeYearName;

	private String planCode;

	private String planName;

	private String appLevel;

	private String appLevelName;

	private String appType;

	private String appTypeName;

	private String riskType;

	private String subRiskType;

	private String remark;

	private String sdorder_id;

	private String supKindCode;

	private String supRiskCode;

	private String primitiveProductTitle;

	private String textAge;
	
	private String policyNum;
	
	private String  appMult;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("Id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("createDate", DataColumn.DATETIME, 1, 0 , 0 , true , false),
		new SchemaColumn("modifyDate", DataColumn.DATETIME, 2, 0 , 0 , false , false),
		new SchemaColumn("informationSn", DataColumn.STRING, 3, 25 , 0 , false , false),
		new SchemaColumn("orderSn", DataColumn.STRING, 4, 25 , 0 , true , false),
		new SchemaColumn("productId", DataColumn.STRING, 5, 20 , 0 , true , false),
		new SchemaColumn("productOutCode", DataColumn.STRING, 6, 20 , 0 , true , false),
		new SchemaColumn("productName", DataColumn.STRING, 7, 200 , 0 , true , false),
		new SchemaColumn("discountRates", DataColumn.STRING, 8, 10 , 0 , false , false),
		new SchemaColumn("productPrice", DataColumn.STRING, 9, 20 , 0 , true , false),
		new SchemaColumn("productDiscountPrice", DataColumn.STRING, 10, 20 , 0 , false , false),
		new SchemaColumn("productHtmlFilePath", DataColumn.STRING, 11, 200 , 0 , false , false),
		new SchemaColumn("productQuantity", DataColumn.STRING, 12, 5 , 0 , true , false),
		new SchemaColumn("insuranceCompany", DataColumn.STRING, 13, 5 , 0 , true , false),
		new SchemaColumn("point", DataColumn.STRING, 14, 20 , 0 , false , false),
		new SchemaColumn("pointStatus", DataColumn.STRING, 15, 5 , 0 , false , false),
		new SchemaColumn("startDate", DataColumn.DATETIME, 16, 0 , 0 , false , false),
		new SchemaColumn("endDate", DataColumn.DATETIME, 17, 0 , 0 , false , false),
		new SchemaColumn("ensureLimitType", DataColumn.STRING, 18, 5 , 0 , false , false),
		new SchemaColumn("ensureLimit", DataColumn.STRING, 19, 5 , 0 , false , false),
		new SchemaColumn("ensure", DataColumn.STRING, 20, 10 , 0 , false , false),
		new SchemaColumn("ensureDisplay", DataColumn.STRING, 21, 50 , 0 , false , false),
		new SchemaColumn("chargeDisplay", DataColumn.STRING, 22, 50 , 0 , false , false),
		new SchemaColumn("chargeType", DataColumn.STRING, 23, 10 , 0 , false , false),
		new SchemaColumn("chargeYear", DataColumn.STRING, 24, 10 , 0 , false , false),
		new SchemaColumn("chargeYearName", DataColumn.STRING, 25, 50 , 0 , false , false),
		new SchemaColumn("planCode", DataColumn.STRING, 26, 10 , 0 , false , false),
		new SchemaColumn("planName", DataColumn.STRING, 27, 50 , 0 , false , false),
		new SchemaColumn("appLevel", DataColumn.STRING, 28, 10 , 0 , false , false),
		new SchemaColumn("appLevelName", DataColumn.STRING, 29, 50 , 0 , false , false),
		new SchemaColumn("appType", DataColumn.STRING, 30, 10 , 0 , false , false),
		new SchemaColumn("appTypeName", DataColumn.STRING, 31, 50 , 0 , false , false),
		new SchemaColumn("riskType", DataColumn.STRING, 32, 20 , 0 , false , false),
		new SchemaColumn("subRiskType", DataColumn.STRING, 33, 20 , 0 , false , false),
		new SchemaColumn("remark", DataColumn.STRING, 34, 20 , 0 , false , false),
		new SchemaColumn("sdorder_id", DataColumn.STRING, 35, 32 , 0 , false , false),
		new SchemaColumn("supKindCode", DataColumn.STRING, 36, 20 , 0 , false , false),
		new SchemaColumn("supRiskCode", DataColumn.STRING, 37, 20 , 0 , false , false),
		new SchemaColumn("primitiveProductTitle", DataColumn.STRING, 38, 200 , 0 , false , false),
		new SchemaColumn("textAge", DataColumn.STRING, 39, 20 , 0 , false , false),
		new SchemaColumn("policyNum", DataColumn.STRING, 40, 10 , 0 , false , false),
		new SchemaColumn("appMult", DataColumn.STRING, 41, 10 , 0 , false , false)
	};

	public static final String _TableCode = "SDInformation";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into SDInformation values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update SDInformation set Id=?,createDate=?,modifyDate=?,informationSn=?,orderSn=?,productId=?,productOutCode=?,productName=?,discountRates=?,productPrice=?,productDiscountPrice=?,productHtmlFilePath=?,productQuantity=?,insuranceCompany=?,point=?,pointStatus=?,startDate=?,endDate=?,ensureLimitType=?,ensureLimit=?,ensure=?,ensureDisplay=?,chargeDisplay=?,chargeType=?,chargeYear=?,chargeYearName=?,planCode=?,planName=?,appLevel=?,appLevelName=?,appType=?,appTypeName=?,riskType=?,subRiskType=?,remark=?,sdorder_id=?,supKindCode=?,supRiskCode=?,primitiveProductTitle=?,textAge=?,policyNum=?,appMult=?  where Id=?";

	protected static final String _DeleteSQL = "delete from SDInformation  where Id=?";

	protected static final String _FillAllSQL = "select * from SDInformation  where Id=?";

	public SDInformationSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[40];
	}

	protected Schema newInstance(){
		return new SDInformationSchema();
	}

	protected SchemaSet newSet(){
		return new SDInformationSet();
	}

	public SDInformationSet query() {
		return query(null, -1, -1);
	}

	public SDInformationSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public SDInformationSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public SDInformationSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (SDInformationSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){Id = (String)v;return;}
		if (i == 1){createDate = (Date)v;return;}
		if (i == 2){modifyDate = (Date)v;return;}
		if (i == 3){informationSn = (String)v;return;}
		if (i == 4){orderSn = (String)v;return;}
		if (i == 5){productId = (String)v;return;}
		if (i == 6){productOutCode = (String)v;return;}
		if (i == 7){productName = (String)v;return;}
		if (i == 8){discountRates = (String)v;return;}
		if (i == 9){productPrice = (String)v;return;}
		if (i == 10){productDiscountPrice = (String)v;return;}
		if (i == 11){productHtmlFilePath = (String)v;return;}
		if (i == 12){productQuantity = (String)v;return;}
		if (i == 13){insuranceCompany = (String)v;return;}
		if (i == 14){point = (String)v;return;}
		if (i == 15){pointStatus = (String)v;return;}
		if (i == 16){startDate = (Date)v;return;}
		if (i == 17){endDate = (Date)v;return;}
		if (i == 18){ensureLimitType = (String)v;return;}
		if (i == 19){ensureLimit = (String)v;return;}
		if (i == 20){ensure = (String)v;return;}
		if (i == 21){ensureDisplay = (String)v;return;}
		if (i == 22){chargeDisplay = (String)v;return;}
		if (i == 23){chargeType = (String)v;return;}
		if (i == 24){chargeYear = (String)v;return;}
		if (i == 25){chargeYearName = (String)v;return;}
		if (i == 26){planCode = (String)v;return;}
		if (i == 27){planName = (String)v;return;}
		if (i == 28){appLevel = (String)v;return;}
		if (i == 29){appLevelName = (String)v;return;}
		if (i == 30){appType = (String)v;return;}
		if (i == 31){appTypeName = (String)v;return;}
		if (i == 32){riskType = (String)v;return;}
		if (i == 33){subRiskType = (String)v;return;}
		if (i == 34){remark = (String)v;return;}
		if (i == 35){sdorder_id = (String)v;return;}
		if (i == 36){supKindCode = (String)v;return;}
		if (i == 37){supRiskCode = (String)v;return;}
		if (i == 38){primitiveProductTitle = (String)v;return;}
		if (i == 39){textAge = (String)v;return;}
		if (i == 40){policyNum = (String)v;return;}
		if (i == 41){appMult = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return Id;}
		if (i == 1){return createDate;}
		if (i == 2){return modifyDate;}
		if (i == 3){return informationSn;}
		if (i == 4){return orderSn;}
		if (i == 5){return productId;}
		if (i == 6){return productOutCode;}
		if (i == 7){return productName;}
		if (i == 8){return discountRates;}
		if (i == 9){return productPrice;}
		if (i == 10){return productDiscountPrice;}
		if (i == 11){return productHtmlFilePath;}
		if (i == 12){return productQuantity;}
		if (i == 13){return insuranceCompany;}
		if (i == 14){return point;}
		if (i == 15){return pointStatus;}
		if (i == 16){return startDate;}
		if (i == 17){return endDate;}
		if (i == 18){return ensureLimitType;}
		if (i == 19){return ensureLimit;}
		if (i == 20){return ensure;}
		if (i == 21){return ensureDisplay;}
		if (i == 22){return chargeDisplay;}
		if (i == 23){return chargeType;}
		if (i == 24){return chargeYear;}
		if (i == 25){return chargeYearName;}
		if (i == 26){return planCode;}
		if (i == 27){return planName;}
		if (i == 28){return appLevel;}
		if (i == 29){return appLevelName;}
		if (i == 30){return appType;}
		if (i == 31){return appTypeName;}
		if (i == 32){return riskType;}
		if (i == 33){return subRiskType;}
		if (i == 34){return remark;}
		if (i == 35){return sdorder_id;}
		if (i == 36){return supKindCode;}
		if (i == 37){return supRiskCode;}
		if (i == 38){return primitiveProductTitle;}
		if (i == 39){return textAge;}
		if (i == 40){return policyNum;}
		if (i == 41){return appMult;}
		return null;
	}

	/**
	* 获取字段Id的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getId() {
		return Id;
	}

	/**
	* 设置字段Id的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setId(String id) {
		this.Id = id;
    }

	/**
	* 获取字段createDate的值，该字段的<br>
	* 字段名称 :创建日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public Date getcreateDate() {
		return createDate;
	}

	/**
	* 设置字段createDate的值，该字段的<br>
	* 字段名称 :创建日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setcreateDate(Date createDate) {
		this.createDate = createDate;
    }

	/**
	* 获取字段modifyDate的值，该字段的<br>
	* 字段名称 :修改日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getmodifyDate() {
		return modifyDate;
	}

	/**
	* 设置字段modifyDate的值，该字段的<br>
	* 字段名称 :修改日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmodifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
    }

	/**
	* 获取字段informationSn的值，该字段的<br>
	* 字段名称 :订单明细表编号<br>
	* 数据类型 :varchar(25)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getinformationSn() {
		return informationSn;
	}

	/**
	* 设置字段informationSn的值，该字段的<br>
	* 字段名称 :订单明细表编号<br>
	* 数据类型 :varchar(25)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setinformationSn(String informationSn) {
		this.informationSn = informationSn;
    }

	/**
	* 获取字段orderSn的值，该字段的<br>
	* 字段名称 :订单编号<br>
	* 数据类型 :varchar(25)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getorderSn() {
		return orderSn;
	}

	/**
	* 设置字段orderSn的值，该字段的<br>
	* 字段名称 :订单编号<br>
	* 数据类型 :varchar(25)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setorderSn(String orderSn) {
		this.orderSn = orderSn;
    }

	/**
	* 获取字段productId的值，该字段的<br>
	* 字段名称 :产品编码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getproductId() {
		return productId;
	}

	/**
	* 设置字段productId的值，该字段的<br>
	* 字段名称 :产品编码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setproductId(String productId) {
		this.productId = productId;
    }

	/**
	* 获取字段productOutCode的值，该字段的<br>
	* 字段名称 :产品外部编码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getproductOutCode() {
		return productOutCode;
	}

	/**
	* 设置字段productOutCode的值，该字段的<br>
	* 字段名称 :产品外部编码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setproductOutCode(String productOutCode) {
		this.productOutCode = productOutCode;
    }

	/**
	* 获取字段productName的值，该字段的<br>
	* 字段名称 :产品名称<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getproductName() {
		return productName;
	}

	/**
	* 设置字段productName的值，该字段的<br>
	* 字段名称 :产品名称<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setproductName(String productName) {
		this.productName = productName;
    }

	/**
	* 获取字段discountRates的值，该字段的<br>
	* 字段名称 :产品折扣率<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getdiscountRates() {
		return discountRates;
	}

	/**
	* 设置字段discountRates的值，该字段的<br>
	* 字段名称 :产品折扣率<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setdiscountRates(String discountRates) {
		this.discountRates = discountRates;
    }

	/**
	* 获取字段productPrice的值，该字段的<br>
	* 字段名称 :产���标准价<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getproductPrice() {
		return productPrice;
	}

	/**
	* 设置字段productPrice的值，该字段的<br>
	* 字段名称 :产���标准价<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setproductPrice(String productPrice) {
		this.productPrice = productPrice;
    }

	/**
	* 获取字段productDiscountPrice的值，该字段的<br>
	* 字段名称 :产品折扣价<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getproductDiscountPrice() {
		return productDiscountPrice;
	}

	/**
	* 设置字段productDiscountPrice的值，该字段的<br>
	* 字段名称 :产品折扣价<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setproductDiscountPrice(String productDiscountPrice) {
		this.productDiscountPrice = productDiscountPrice;
    }

	/**
	* 获取字段productHtmlFilePath的值，该字段的<br>
	* 字段名称 :产品HTML静态文件路径<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getproductHtmlFilePath() {
		return productHtmlFilePath;
	}

	/**
	* 设置字段productHtmlFilePath的值，该字段的<br>
	* 字段名称 :产品HTML静态文件路径<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setproductHtmlFilePath(String productHtmlFilePath) {
		this.productHtmlFilePath = productHtmlFilePath;
    }

	/**
	* 获取字段productQuantity的值，该字段的<br>
	* 字段名称 :产品数量<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getproductQuantity() {
		return productQuantity;
	}

	/**
	* 设置字段productQuantity的值，该字段的<br>
	* 字段名称 :产品数量<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setproductQuantity(String productQuantity) {
		this.productQuantity = productQuantity;
    }

	/**
	* 获取字段insuranceCompany的值，该字段的<br>
	* 字段名称 :保险公司<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getinsuranceCompany() {
		return insuranceCompany;
	}

	/**
	* 设置字段insuranceCompany的值，该字段的<br>
	* 字段名称 :保险公司<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setinsuranceCompany(String insuranceCompany) {
		this.insuranceCompany = insuranceCompany;
    }

	/**
	* 获取字段point的值，该字段的<br>
	* 字段名称 :积分<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getpoint() {
		return point;
	}

	/**
	* 设置字段point的值，该字段的<br>
	* 字段名称 :积分<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpoint(String point) {
		this.point = point;
    }

	/**
	* 获取字段pointStatus的值，该字段的<br>
	* 字段名称 :积分状态<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getpointStatus() {
		return pointStatus;
	}

	/**
	* 设置字段pointStatus的值，该字段的<br>
	* 字段名称 :积分状态<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpointStatus(String pointStatus) {
		this.pointStatus = pointStatus;
    }

	/**
	* 获取字段startDate的值，该字段的<br>
	* 字段名称 :保险起期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getstartDate() {
		return startDate;
	}

	/**
	* 设置字段startDate的值，该字段的<br>
	* 字段名称 :保险起期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setstartDate(Date startDate) {
		this.startDate = startDate;
    }

	/**
	* 获取字段endDate的值，该字段的<br>
	* 字段名称 :保险止期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getendDate() {
		return endDate;
	}

	/**
	* 设置字段endDate的值，该字段的<br>
	* 字段名称 :保险止期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setendDate(Date endDate) {
		this.endDate = endDate;
    }

	/**
	* 获取字段ensureLimitType的值，该字段的<br>
	* 字段名称 :保障期限类型<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getensureLimitType() {
		return ensureLimitType;
	}

	/**
	* 设置字段ensureLimitType的值，该字段的<br>
	* 字段名称 :保障期限类型<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setensureLimitType(String ensureLimitType) {
		this.ensureLimitType = ensureLimitType;
    }

	/**
	* 获取字段ensureLimit的值，该字段的<br>
	* 字段名称 :保障期限<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getensureLimit() {
		return ensureLimit;
	}

	/**
	* 设置字段ensureLimit的值，该字段的<br>
	* 字段名称 :保障期限<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setensureLimit(String ensureLimit) {
		this.ensureLimit = ensureLimit;
    }

	/**
	* 获取字段ensure的值，该字段的<br>
	* 字段名称 :保障(产品中心传过来的值)<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getensure() {
		return ensure;
	}

	/**
	* 设置字段ensure的值，该字段的<br>
	* 字段名称 :保障(产品中心传过来的值)<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setensure(String ensure) {
		this.ensure = ensure;
    }

	/**
	* 获取字段ensureDisplay的值，该字段的<br>
	* 字段名称 :保障期限显示值<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getensureDisplay() {
		return ensureDisplay;
	}

	/**
	* 设置字段ensureDisplay的值，该字段的<br>
	* 字段名称 :保障期限显示值<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setensureDisplay(String ensureDisplay) {
		this.ensureDisplay = ensureDisplay;
    }

	/**
	* 获取字段chargeDisplay的值，该字段的<br>
	* 字段名称 :缴费类型显示值<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getchargeDisplay() {
		return chargeDisplay;
	}

	/**
	* 设置字段chargeDisplay的值，该字段的<br>
	* 字段名称 :缴费类型显示值<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setchargeDisplay(String chargeDisplay) {
		this.chargeDisplay = chargeDisplay;
    }

	/**
	* 获取字段chargeType的值，该字段的<br>
	* 字段名称 :缴费类型<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getchargeType() {
		return chargeType;
	}

	/**
	* 设置字段chargeType的值，该字段的<br>
	* 字段名称 :缴费类型<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setchargeType(String chargeType) {
		this.chargeType = chargeType;
    }

	/**
	* 获取字段chargeYear的值，该字段的<br>
	* 字段名称 :缴费年期<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getchargeYear() {
		return chargeYear;
	}

	/**
	* 设置字段chargeYear的值，该字段的<br>
	* 字段名称 :缴费年期<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setchargeYear(String chargeYear) {
		this.chargeYear = chargeYear;
    }

	/**
	* 获取字段chargeYearName的值，该字段的<br>
	* 字段名称 :缴费年期名称<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getchargeYearName() {
		return chargeYearName;
	}

	/**
	* 设置字段chargeYearName的值，该字段的<br>
	* 字段名称 :缴费年期名称<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setchargeYearName(String chargeYearName) {
		this.chargeYearName = chargeYearName;
    }

	/**
	* 获取字段planCode的值，该字段的<br>
	* 字段名称 :计划编码<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getplanCode() {
		return planCode;
	}

	/**
	* 设置字段planCode的值，该字段的<br>
	* 字段名称 :计划编码<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setplanCode(String planCode) {
		this.planCode = planCode;
    }

	/**
	* 获取字段planName的值，该字段的<br>
	* 字段名称 :计划名称<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getplanName() {
		return planName;
	}

	/**
	* 设置字段planName的值，该字段的<br>
	* 字段名称 :计划名称<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setplanName(String planName) {
		this.planName = planName;
    }

	/**
	* 获取字段appLevel的值，该字段的<br>
	* 字段名称 :缴费方式<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getappLevel() {
		return appLevel;
	}

	/**
	* 设置字段appLevel的值，该字段的<br>
	* 字段名称 :缴费方式<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setappLevel(String appLevel) {
		this.appLevel = appLevel;
    }

	/**
	* 获取字段appLevelName的值，该字段的<br>
	* 字段名称 :缴费方式名称<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getappLevelName() {
		return appLevelName;
	}

	/**
	* 设置字段appLevelName的值，该字段的<br>
	* 字段名称 :缴费方式名称<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setappLevelName(String appLevelName) {
		this.appLevelName = appLevelName;
    }

	/**
	* 获取字段appType的值，该字段的<br>
	* 字段名称 :投保类别<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getappType() {
		return appType;
	}

	/**
	* 设置字段appType的值，该字段的<br>
	* 字段名称 :投保类别<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setappType(String appType) {
		this.appType = appType;
    }

	/**
	* 获取字段appTypeName的值，该字段的<br>
	* 字段名称 :投保类别名称<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getappTypeName() {
		return appTypeName;
	}

	/**
	* 设置字段appTypeName的值，该字段的<br>
	* 字段名称 :投保类别名称<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setappTypeName(String appTypeName) {
		this.appTypeName = appTypeName;
    }

	/**
	* 获取字段riskType的值，该字段的<br>
	* 字段名称 :险种类别<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getriskType() {
		return riskType;
	}

	/**
	* 设置字段riskType的值，该字段的<br>
	* 字段名称 :险种类别<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setriskType(String riskType) {
		this.riskType = riskType;
    }

	/**
	* 获取字段subRiskType的值，该字段的<br>
	* 字段名称 :险种小类<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getsubRiskType() {
		return subRiskType;
	}

	/**
	* 设置字段subRiskType的值，该字段的<br>
	* 字段名称 :险种小类<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setsubRiskType(String subRiskType) {
		this.subRiskType = subRiskType;
    }

	/**
	* 获取字段remark的值，该字段的<br>
	* 字段名称 :备用字段<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getremark() {
		return remark;
	}

	/**
	* 设置字段remark的值，该字段的<br>
	* 字段名称 :备用字段<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setremark(String remark) {
		this.remark = remark;
    }

	/**
	* 获取字段sdorder_id的值，该字段的<br>
	* 字段名称 :订单表ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getsdorder_id() {
		return sdorder_id;
	}

	/**
	* 设置字段sdorder_id的值，该字段的<br>
	* 字段名称 :订单表ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setsdorder_id(String sdorder_id) {
		this.sdorder_id = sdorder_id;
    }

	/**
	* 获取字段supKindCode的值，该字段的<br>
	* 字段名称 :保险公司险种编码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getsupKindCode() {
		return supKindCode;
	}

	/**
	* 设置字段supKindCode的值，该字段的<br>
	* 字段名称 :保险公司险种编码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setsupKindCode(String supKindCode) {
		this.supKindCode = supKindCode;
    }

	/**
	* 获取字段supRiskCode的值，该字段的<br>
	* 字段名称 :保险公司计划编码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getsupRiskCode() {
		return supRiskCode;
	}

	/**
	* 设置字段supRiskCode的值，该字段的<br>
	* 字段名称 :保险公司计划编码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setsupRiskCode(String supRiskCode) {
		this.supRiskCode = supRiskCode;
    }

	/**
	* 获取字段primitiveProductTitle的值，该字段的<br>
	* 字段名称 :产品原始名称<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprimitiveProductTitle() {
		return primitiveProductTitle;
	}

	/**
	* 设置字段primitiveProductTitle的值，该字段的<br>
	* 字段名称 :产品原始名称<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprimitiveProductTitle(String primitiveProductTitle) {
		this.primitiveProductTitle = primitiveProductTitle;
    }

	/**
	* 获取字段textAge的值，该字段的<br>
	* 字段名称 :投保年龄<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String gettextAge() {
		return textAge;
	}

	/**
	* 设置字段textAge的值，该字段的<br>
	* 字段名称 :投保年龄<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void settextAge(String textAge) {
		this.textAge = textAge;
    }
	/**
	 * 获取字段policyNum的值，该字段的<br>
	 * 字段名称 :保单个数<br>
	 * 数据类型 :varchar(20)<br>
	 * 是否主键 :false<br>
	 * 是否必填 :false<br>
	 */
	public String getpolicyNum() {
		return policyNum;
	}
	
	/**
	 * 设置字段policyNum的值，该字段的<br>
	 * 字段名称 :保单个数<br>
	 * 数据类型 :varchar(20)<br>
	 * 是否主键 :false<br>
	 * 是否必填 :false<br>
	 */
	public void setpolicyNum(String policyNum) {
		this.policyNum = policyNum;
	}
	
	/**
	 * 获取字段appMult的值，该字段的<br>
	 * 字段名称 :购买份数<br>
	 * 数据类型 :varchar(20)<br>
	 * 是否主键 :false<br>
	 * 是否必填 :false<br>
	 */
	public String getappMult() {
		return appMult;
	}
	
	/**
	 * 设置字段appMult的值，该字段的<br>
	 * 字段名称 :购买份数<br>
	 * 数据类型 :varchar(20)<br>
	 * 是否主键 :false<br>
	 * 是否必填 :false<br>
	 */
	public void setappMult(String appMult) {
		this.appMult = appMult;
	}

}