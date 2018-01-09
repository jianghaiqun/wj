package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：责任投保要素表<br> 
 * 表代码：SDInformationDuty<br>
 * 表主键：Id<br>
 */
public class SDInformationDutySchema extends Schema {
	private String Id;

	private Date createDate;

	private Date modifyDate;

	private String dutySn;

	private String informationSn;

	private String orderSn;

	private String dutyFullName;

	private String dutyName;

	private String coverage;

	private String riskCode;

	private String premium;

	private String showAmnt;

	private String amt;

	private String mainRiskFlag;

	private String supplierDutyCode;

	private String sdinformation_id;

	private String dutyEnName;

	private String EnCoverage;

	private String orderFlag;
	
	private String isDisplay;
	
	private String discountRates;
	
	private String discountPrice;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("Id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("createDate", DataColumn.DATETIME, 1, 0 , 0 , true , false),
		new SchemaColumn("modifyDate", DataColumn.DATETIME, 2, 0 , 0 , false , false),
		new SchemaColumn("dutySn", DataColumn.STRING, 3, 25 , 0 , true , false),
		new SchemaColumn("informationSn", DataColumn.STRING, 4, 25 , 0 , false , false),
		new SchemaColumn("orderSn", DataColumn.STRING, 5, 25 , 0 , false , false),
		new SchemaColumn("dutyFullName", DataColumn.STRING, 6, 200 , 0 , false , false),
		new SchemaColumn("dutyName", DataColumn.STRING, 7, 200 , 0 , false , false),
		new SchemaColumn("coverage", DataColumn.STRING, 8, 500 , 0 , false , false),
		new SchemaColumn("riskCode", DataColumn.STRING, 9, 30 , 0 , false , false),
		new SchemaColumn("premium", DataColumn.STRING, 10, 20 , 0 , false , false),
		new SchemaColumn("showAmnt", DataColumn.STRING, 11, 20 , 0 , true , false),
		new SchemaColumn("amt", DataColumn.STRING, 12, 20 , 0 , false , false),
		new SchemaColumn("mainRiskFlag", DataColumn.STRING, 13, 5 , 0 , false , false),
		new SchemaColumn("supplierDutyCode", DataColumn.STRING, 14, 10 , 0 , false , false),
		new SchemaColumn("sdinformation_id", DataColumn.STRING, 15, 32 , 0 , false , false),
		new SchemaColumn("dutyEnName", DataColumn.STRING, 16, 200 , 0 , false , false),
		new SchemaColumn("EnCoverage", DataColumn.STRING, 17, 500 , 0 , false , false),
		new SchemaColumn("orderFlag", DataColumn.STRING, 18, 20 , 0 , false , false),
		new SchemaColumn("isDisplay", DataColumn.STRING, 19, 2 , 0 , false , false),
		new SchemaColumn("discountRates", DataColumn.STRING, 20, 10 , 0 , false , false),
		new SchemaColumn("discountPrice", DataColumn.STRING, 21, 20 , 0 , false , false)
	};

	public static final String _TableCode = "SDInformationDuty";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into SDInformationDuty values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update SDInformationDuty set Id=?,createDate=?,modifyDate=?,dutySn=?,informationSn=?,orderSn=?,dutyFullName=?,dutyName=?,coverage=?,riskCode=?,premium=?,showAmnt=?,amt=?,mainRiskFlag=?,supplierDutyCode=?,sdinformation_id=?,dutyEnName=?,EnCoverage=?,orderFlag=?,isDisplay=?,discountRates=?,discountPrice=? where Id=?";

	protected static final String _DeleteSQL = "delete from SDInformationDuty  where Id=?";

	protected static final String _FillAllSQL = "select * from SDInformationDuty  where Id=?";

	public SDInformationDutySchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[22];
	}

	protected Schema newInstance(){
		return new SDInformationDutySchema();
	}

	protected SchemaSet newSet(){
		return new SDInformationDutySet();
	}

	public SDInformationDutySet query() {
		return query(null, -1, -1);
	}

	public SDInformationDutySet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public SDInformationDutySet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public SDInformationDutySet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (SDInformationDutySet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){Id = (String)v;return;}
		if (i == 1){createDate = (Date)v;return;}
		if (i == 2){modifyDate = (Date)v;return;}
		if (i == 3){dutySn = (String)v;return;}
		if (i == 4){informationSn = (String)v;return;}
		if (i == 5){orderSn = (String)v;return;}
		if (i == 6){dutyFullName = (String)v;return;}
		if (i == 7){dutyName = (String)v;return;}
		if (i == 8){coverage = (String)v;return;}
		if (i == 9){riskCode = (String)v;return;}
		if (i == 10){premium = (String)v;return;}
		if (i == 11){showAmnt = (String)v;return;}
		if (i == 12){amt = (String)v;return;}
		if (i == 13){mainRiskFlag = (String)v;return;}
		if (i == 14){supplierDutyCode = (String)v;return;}
		if (i == 15){sdinformation_id = (String)v;return;}
		if (i == 16){dutyEnName = (String)v;return;}
		if (i == 17){EnCoverage = (String)v;return;}
		if (i == 18){orderFlag = (String)v;return;}
		if (i == 19){isDisplay = (String)v;return;}
		if (i == 20){discountRates = (String)v;return;}
		if (i == 21){discountPrice = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return Id;}
		if (i == 1){return createDate;}
		if (i == 2){return modifyDate;}
		if (i == 3){return dutySn;}
		if (i == 4){return informationSn;}
		if (i == 5){return orderSn;}
		if (i == 6){return dutyFullName;}
		if (i == 7){return dutyName;}
		if (i == 8){return coverage;}
		if (i == 9){return riskCode;}
		if (i == 10){return premium;}
		if (i == 11){return showAmnt;}
		if (i == 12){return amt;}
		if (i == 13){return mainRiskFlag;}
		if (i == 14){return supplierDutyCode;}
		if (i == 15){return sdinformation_id;}
		if (i == 16){return dutyEnName;}
		if (i == 17){return EnCoverage;}
		if (i == 18){return orderFlag;}
		if (i == 19){return isDisplay;}
		if (i == 20){return discountRates;}
		if (i == 21){return discountPrice;}
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
	* 获取字段dutySn的值，该字段的<br>
	* 字段名称 :责任编码<br>
	* 数据类型 :varchar(25)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getdutySn() {
		return dutySn;
	}

	/**
	* 设置字段dutySn的值，该字段的<br>
	* 字段名称 :责任编码<br>
	* 数据类型 :varchar(25)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setdutySn(String dutySn) {
		this.dutySn = dutySn;
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
	* 获取字段dutyFullName的值，该字段的<br>
	* 字段名称 :责任全称<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getdutyFullName() {
		return dutyFullName;
	}

	/**
	* 设置字段dutyFullName的值，该字段的<br>
	* 字段名称 :责任全称<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setdutyFullName(String dutyFullName) {
		this.dutyFullName = dutyFullName;
    }

	/**
	* 获取字段dutyName的值，该字段的<br>
	* 字段名称 :责任简称<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getdutyName() {
		return dutyName;
	}

	/**
	* 设置字段dutyName的值，该字段的<br>
	* 字段名称 :责任简称<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setdutyName(String dutyName) {
		this.dutyName = dutyName;
    }

	/**
	* 获取字段coverage的值，该字段的<br>
	* 字段名称 :保障内容<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcoverage() {
		return coverage;
	}

	/**
	* 设置字段coverage的值，该字段的<br>
	* 字段名称 :保障内容<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcoverage(String coverage) {
		this.coverage = coverage;
    }

	/**
	* 获取字段riskCode的值，该字段的<br>
	* 字段名称 :险种编码<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getriskCode() {
		return riskCode;
	}

	/**
	* 设置字段riskCode的值，该字段的<br>
	* 字段名称 :险种编码<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setriskCode(String riskCode) {
		this.riskCode = riskCode;
    }

	/**
	* 获取字段premium的值，该字段的<br>
	* 字段名称 :保费<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getpremium() {
		return premium;
	}

	/**
	* 设置字段premium的值，该字段的<br>
	* 字段名称 :保费<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setpremium(String premium) {
		this.premium = premium;
    }

	/**
	* 获取字段showAmnt的值，该字段的<br>
	* 字段名称 :显示值<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getshowAmnt() {
		return showAmnt;
	}

	/**
	* 设置字段showAmnt的值，该字段的<br>
	* 字段名称 :显示值<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setshowAmnt(String showAmnt) {
		this.showAmnt = showAmnt;
    }

	/**
	* 获取字段amt的值，该字段的<br>
	* 字段名称 :计算值保额<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getamt() {
		return amt;
	}

	/**
	* 设置字段amt的值，该字段的<br>
	* 字段名称 :计算值保额<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setamt(String amt) {
		this.amt = amt;
    }

	/**
	* 获取字段mainRiskFlag的值，该字段的<br>
	* 字段名称 :是否主险标志<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getmainRiskFlag() {
		return mainRiskFlag;
	}

	/**
	* 设置字段mainRiskFlag的值，该字段的<br>
	* 字段名称 :是否主险标志<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmainRiskFlag(String mainRiskFlag) {
		this.mainRiskFlag = mainRiskFlag;
    }

	/**
	* 获取字段supplierDutyCode的值，该字段的<br>
	* 字段名称 :保险公司责任/险别编码<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getsupplierDutyCode() {
		return supplierDutyCode;
	}

	/**
	* 设置字段supplierDutyCode的值，该字段的<br>
	* 字段名称 :保险公司责任/险别编码<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setsupplierDutyCode(String supplierDutyCode) {
		this.supplierDutyCode = supplierDutyCode;
    }

	/**
	* 获取字段sdinformation_id的值，该字段的<br>
	* 字段名称 :产品信息表ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getsdinformation_id() {
		return sdinformation_id;
	}

	/**
	* 设置字段sdinformation_id的值，该字段的<br>
	* 字段名称 :产品信息表ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setsdinformation_id(String sdinformation_id) {
		this.sdinformation_id = sdinformation_id;
    }

	/**
	* 获取字段dutyEnName的值，该字段的<br>
	* 字段名称 :责任英文名<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getdutyEnName() {
		return dutyEnName;
	}

	/**
	* 设置字段dutyEnName的值，该字段的<br>
	* 字段名称 :责任英文名<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setdutyEnName(String dutyEnName) {
		this.dutyEnName = dutyEnName;
    }

	/**
	* 获取字段EnCoverage的值，该字段的<br>
	* 字段名称 :责任英文说明<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getEnCoverage() {
		return EnCoverage;
	}

	/**
	* 设置字段EnCoverage的值，该字段的<br>
	* 字段名称 :责任英文说明<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setEnCoverage(String enCoverage) {
		this.EnCoverage = enCoverage;
    }

	/**
	* 获取字段orderFlag的值，该字段的<br>
	* 字段名称 :责任排序<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getorderFlag() {
		return orderFlag;
	}

	/**
	* 设置字段orderFlag的值，该字段的<br>
	* 字段名称 :责任排序<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setorderFlag(String orderFlag) {
		this.orderFlag = orderFlag;
    }
	
	/**
	* 获取字段isDisplay的值，该字段的<br>
	* 字段名称 :是否显示<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getisDisplay() {
		return isDisplay;
	}

	/**
	* 设置字段isDisplay的值，该字段的<br>
	* 字段名称 :是否显示<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setisDisplay(String isDisplay) {
		this.isDisplay = isDisplay;
    }
	
	/**
	* 获取字段discountRates的值，该字段的<br>
	* 字段名称 :折扣<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getdiscountRates() {
		return discountRates;
	}

	/**
	* 设置字段discountRates的值，该字段的<br>
	* 字段名称 :折扣<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setdiscountRates(String discountRates) {
		this.discountRates = discountRates;
    }
	
	/**
	* 获取字段discountPrice的值，该字段的<br>
	* 字段名称 :折扣保费<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getdiscountPrice() {
		return discountPrice;
	}

	/**
	* 设置字段discountPrice的值，该字段的<br>
	* 字段名称 :折扣保费<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setdiscountPrice(String discountPrice) {
		this.discountPrice = discountPrice;
    }

}