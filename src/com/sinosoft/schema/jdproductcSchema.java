package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

/**
 * 表名称：淘宝产品配置表<br>
 * 表代码：jdproductc<br>
 * 表主键：ERiskID, CustomCode<br>
 */
public class jdproductcSchema extends Schema {
	private String ERiskID;

	private String CustomCode;

	private String InsureCode; 

	private String ProductName;

	private String PlanCode;

	private String CoverageYear;

	private String AgeIssue;

	private String Occupation;

	private String Gender;

	private String PaymentMethods;

	private String MakeDate;

	private String MakeTime;

	private String ProductXmlHybrid;

	private String PlanXmlHybrid;

	private String InsuranceXmlHybrid;

	private String DutyCodeXmlHybrid;

	private String ProductType;

	private String Period;

	private String TextAge;

	private String DiscountPrice;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ERiskID", DataColumn.STRING, 0, 30 , 0 , true , true),
		new SchemaColumn("CustomCode", DataColumn.STRING, 1, 200 , 0 , true , true),
		new SchemaColumn("InsureCode", DataColumn.STRING, 2, 30 , 0 , false , false),
		new SchemaColumn("ProductName", DataColumn.STRING, 3, 30 , 0 , false , false),
		new SchemaColumn("PlanCode", DataColumn.STRING, 4, 30 , 0 , false , false),
		new SchemaColumn("CoverageYear", DataColumn.STRING, 5, 30 , 0 , false , false),
		new SchemaColumn("AgeIssue", DataColumn.STRING, 6, 30 , 0 , false , false),
		new SchemaColumn("Occupation", DataColumn.STRING, 7, 30 , 0 , false , false),
		new SchemaColumn("Gender", DataColumn.STRING, 8, 30 , 0 , false , false),
		new SchemaColumn("PaymentMethods", DataColumn.STRING, 9, 30 , 0 , false , false),
		new SchemaColumn("MakeDate", DataColumn.STRING, 10, 30 , 0 , false , false),
		new SchemaColumn("MakeTime", DataColumn.STRING, 11, 30 , 0 , false , false),
		new SchemaColumn("ProductXmlHybrid", DataColumn.STRING, 12, 30 , 0 , false , false),
		new SchemaColumn("PlanXmlHybrid", DataColumn.STRING, 13, 30 , 0 , false , false),
		new SchemaColumn("InsuranceXmlHybrid", DataColumn.STRING, 14, 30 , 0 , false , false),
		new SchemaColumn("DutyCodeXmlHybrid", DataColumn.STRING, 15, 30 , 0 , false , false),
		new SchemaColumn("ProductType", DataColumn.STRING, 16, 30 , 0 , false , false),
		new SchemaColumn("Period", DataColumn.STRING, 17, 30 , 0 , false , false),
		new SchemaColumn("TextAge", DataColumn.STRING, 18, 30 , 0 , false , false),
		new SchemaColumn("DiscountPrice", DataColumn.STRING, 19, 20 , 0 , false , false)
	};

	public static final String _TableCode = "jdproductc";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into jdproductc values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update jdproductc set ERiskID=?,CustomCode=?,InsureCode=?,ProductName=?,PlanCode=?,CoverageYear=?,AgeIssue=?,Occupation=?,Gender=?,PaymentMethods=?,MakeDate=?,MakeTime=?,ProductXmlHybrid=?,PlanXmlHybrid=?,InsuranceXmlHybrid=?,DutyCodeXmlHybrid=?,ProductType=?,Period=?,TextAge=?,DiscountPrice=? where ERiskID=? and CustomCode=?";

	protected static final String _DeleteSQL = "delete from jdproductc  where ERiskID=? and CustomCode=?";

	protected static final String _FillAllSQL = "select * from jdproductc  where ERiskID=? and CustomCode=?";

	public jdproductcSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[20];
	}

	protected Schema newInstance(){
		return new jdproductcSchema();
	}

	protected SchemaSet newSet(){
		return new jdproductcSet();
	}

	public jdproductcSet query() {
		return query(null, -1, -1);
	}

	public jdproductcSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public jdproductcSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public jdproductcSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (jdproductcSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){ERiskID = (String)v;return;}
		if (i == 1){CustomCode = (String)v;return;}
		if (i == 2){InsureCode = (String)v;return;}
		if (i == 3){ProductName = (String)v;return;}
		if (i == 4){PlanCode = (String)v;return;}
		if (i == 5){CoverageYear = (String)v;return;}
		if (i == 6){AgeIssue = (String)v;return;}
		if (i == 7){Occupation = (String)v;return;}
		if (i == 8){Gender = (String)v;return;}
		if (i == 9){PaymentMethods = (String)v;return;}
		if (i == 10){MakeDate = (String)v;return;}
		if (i == 11){MakeTime = (String)v;return;}
		if (i == 12){ProductXmlHybrid = (String)v;return;}
		if (i == 13){PlanXmlHybrid = (String)v;return;}
		if (i == 14){InsuranceXmlHybrid = (String)v;return;}
		if (i == 15){DutyCodeXmlHybrid = (String)v;return;}
		if (i == 16){ProductType = (String)v;return;}
		if (i == 17){Period = (String)v;return;}
		if (i == 18){TextAge = (String)v;return;}
		if (i == 19){DiscountPrice = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ERiskID;}
		if (i == 1){return CustomCode;}
		if (i == 2){return InsureCode;}
		if (i == 3){return ProductName;}
		if (i == 4){return PlanCode;}
		if (i == 5){return CoverageYear;}
		if (i == 6){return AgeIssue;}
		if (i == 7){return Occupation;}
		if (i == 8){return Gender;}
		if (i == 9){return PaymentMethods;}
		if (i == 10){return MakeDate;}
		if (i == 11){return MakeTime;}
		if (i == 12){return ProductXmlHybrid;}
		if (i == 13){return PlanXmlHybrid;}
		if (i == 14){return InsuranceXmlHybrid;}
		if (i == 15){return DutyCodeXmlHybrid;}
		if (i == 16){return ProductType;}
		if (i == 17){return Period;}
		if (i == 18){return TextAge;}
		if (i == 19){return DiscountPrice;}
		return null;
	}

	/**
	* 获取字段ERiskID的值，该字段的<br>
	* 字段名称 :ERiskID<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getERiskID() {
		return ERiskID;
	}

	/**
	* 设置字段ERiskID的值，该字段的<br>
	* 字段名称 :ERiskID<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setERiskID(String eRiskID) {
		this.ERiskID = eRiskID;
    }

	/**
	* 获取字段CustomCode的值，该字段的<br>
	* 字段名称 :CustomCode<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getCustomCode() {
		return CustomCode;
	}

	/**
	* 设置字段CustomCode的值，该字段的<br>
	* 字段名称 :CustomCode<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setCustomCode(String customCode) {
		this.CustomCode = customCode;
    }

	/**
	* 获取字段InsureCode的值，该字段的<br>
	* 字段名称 :InsureCode<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getInsureCode() {
		return InsureCode;
	}

	/**
	* 设置字段InsureCode的值，该字段的<br>
	* 字段名称 :InsureCode<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setInsureCode(String insureCode) {
		this.InsureCode = insureCode;
    }

	/**
	* 获取字段ProductName的值，该字段的<br>
	* 字段名称 :ProductName<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProductName() {
		return ProductName;
	}

	/**
	* 设置字段ProductName的值，该字段的<br>
	* 字段名称 :ProductName<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProductName(String productName) {
		this.ProductName = productName;
    }

	/**
	* 获取字段PlanCode的值，该字段的<br>
	* 字段名称 :PlanCode<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPlanCode() {
		return PlanCode;
	}

	/**
	* 设置字段PlanCode的值，该字段的<br>
	* 字段名称 :PlanCode<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPlanCode(String planCode) {
		this.PlanCode = planCode;
    }

	/**
	* 获取字段CoverageYear的值，该字段的<br>
	* 字段名称 :CoverageYear<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCoverageYear() {
		return CoverageYear;
	}

	/**
	* 设置字段CoverageYear的值，该字段的<br>
	* 字段名称 :CoverageYear<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCoverageYear(String coverageYear) {
		this.CoverageYear = coverageYear;
    }

	/**
	* 获取字段AgeIssue的值，该字段的<br>
	* 字段名称 :AgeIssue<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAgeIssue() {
		return AgeIssue;
	}

	/**
	* 设置字段AgeIssue的值，该字段的<br>
	* 字段名称 :AgeIssue<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAgeIssue(String ageIssue) {
		this.AgeIssue = ageIssue;
    }

	/**
	* 获取字段Occupation的值，该字段的<br>
	* 字段名称 :Occupation<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getOccupation() {
		return Occupation;
	}

	/**
	* 设置字段Occupation的值，该字段的<br>
	* 字段名称 :Occupation<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setOccupation(String occupation) {
		this.Occupation = occupation;
    }

	/**
	* 获取字段Gender的值，该字段的<br>
	* 字段名称 :Gender<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getGender() {
		return Gender;
	}

	/**
	* 设置字段Gender的值，该字段的<br>
	* 字段名称 :Gender<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setGender(String gender) {
		this.Gender = gender;
    }

	/**
	* 获取字段PaymentMethods的值，该字段的<br>
	* 字段名称 :PaymentMethods<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPaymentMethods() {
		return PaymentMethods;
	}

	/**
	* 设置字段PaymentMethods的值，该字段的<br>
	* 字段名称 :PaymentMethods<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPaymentMethods(String paymentMethods) {
		this.PaymentMethods = paymentMethods;
    }

	/**
	* 获取字段MakeDate的值，该字段的<br>
	* 字段名称 :MakeDate<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMakeDate() {
		return MakeDate;
	}

	/**
	* 设置字段MakeDate的值，该字段的<br>
	* 字段名称 :MakeDate<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMakeDate(String makeDate) {
		this.MakeDate = makeDate;
    }

	/**
	* 获取字段MakeTime的值，该字段的<br>
	* 字段名称 :MakeTime<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMakeTime() {
		return MakeTime;
	}

	/**
	* 设置字段MakeTime的值，该字段的<br>
	* 字段名称 :MakeTime<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMakeTime(String makeTime) {
		this.MakeTime = makeTime;
    }

	/**
	* 获取字段ProductXmlHybrid的值，该字段的<br>
	* 字段名称 :ProductXmlHybrid<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProductXmlHybrid() {
		return ProductXmlHybrid;
	}

	/**
	* 设置字段ProductXmlHybrid的值，该字段的<br>
	* 字段名称 :ProductXmlHybrid<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProductXmlHybrid(String productXmlHybrid) {
		this.ProductXmlHybrid = productXmlHybrid;
    }

	/**
	* 获取字段PlanXmlHybrid的值，该字段的<br>
	* 字段名称 :PlanXmlHybrid<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPlanXmlHybrid() {
		return PlanXmlHybrid;
	}

	/**
	* 设置字段PlanXmlHybrid的值，该字段的<br>
	* 字段名称 :PlanXmlHybrid<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPlanXmlHybrid(String planXmlHybrid) {
		this.PlanXmlHybrid = planXmlHybrid;
    }

	/**
	* 获取字段InsuranceXmlHybrid的值，该字段的<br>
	* 字段名称 :InsuranceXmlHybrid<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getInsuranceXmlHybrid() {
		return InsuranceXmlHybrid;
	}

	/**
	* 设置字段InsuranceXmlHybrid的值，该字段的<br>
	* 字段名称 :InsuranceXmlHybrid<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setInsuranceXmlHybrid(String insuranceXmlHybrid) {
		this.InsuranceXmlHybrid = insuranceXmlHybrid;
    }

	/**
	* 获取字段DutyCodeXmlHybrid的值，该字段的<br>
	* 字段名称 :DutyCodeXmlHybrid<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getDutyCodeXmlHybrid() {
		return DutyCodeXmlHybrid;
	}

	/**
	* 设置字段DutyCodeXmlHybrid的值，该字段的<br>
	* 字段名称 :DutyCodeXmlHybrid<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDutyCodeXmlHybrid(String dutyCodeXmlHybrid) {
		this.DutyCodeXmlHybrid = dutyCodeXmlHybrid;
    }

	/**
	* 获取字段ProductType的值，该字段的<br>
	* 字段名称 :ProductType<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProductType() {
		return ProductType;
	}

	/**
	* 设置字段ProductType的值，该字段的<br>
	* 字段名称 :ProductType<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProductType(String productType) {
		this.ProductType = productType;
    }

	/**
	* 获取字段Period的值，该字段的<br>
	* 字段名称 :Period<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPeriod() {
		return Period;
	}

	/**
	* 设置字段Period的值，该字段的<br>
	* 字段名称 :Period<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPeriod(String period) {
		this.Period = period;
    }

	/**
	* 获取字段TextAge的值，该字段的<br>
	* 字段名称 :TextAge<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getTextAge() {
		return TextAge;
	}

	/**
	* 设置字段TextAge的值，该字段的<br>
	* 字段名称 :TextAge<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setTextAge(String textAge) {
		this.TextAge = textAge;
    }

	/**
	* 获取字段DiscountPrice的值，该字段的<br>
	* 字段名称 :DiscountPrice<br>
	* 数据类型 :VARCHAR(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getDiscountPrice() {
		return DiscountPrice;
	}

	/**
	* 设置字段DiscountPrice的值，该字段的<br>
	* 字段名称 :DiscountPrice<br>
	* 数据类型 :VARCHAR(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDiscountPrice(String discountPrice) {
		this.DiscountPrice = discountPrice;
    }

}