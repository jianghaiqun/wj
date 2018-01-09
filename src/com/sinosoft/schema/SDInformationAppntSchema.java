package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：投保人信息表<br>
 * 表代码：SDInformationAppnt<br>
 * 表主键：Id<br>
 */
public class SDInformationAppntSchema extends Schema { 
	private String Id;

	private Date createDate;

	private Date modifyDate;

	private String informationSn;

	private String applicantSn;

	private String applicantName;

	private String applicantEnName;

	private String applicantLastName;

	private String applicantFirstName;

	private String applicantIdentityType;

	private String applicantIdentityTypeName;

	private String applicantIdentityId;

	private String applicantSex;

	private String applicantSexName;

	private String applicantBirthday;

	private String applicantAge;

	private String applicantMail;

	private String applicantArea1;

	private String applicantArea2;

	private String applicantAddress;

	private String applicantZipCode;

	private String applicantMobile;

	private String applicantTel;

	private String applicantOccupation1;

	private String applicantOccupation2;

	private String applicantOccupation3;

	private String invoiceHeading;

	private String bankCode;

	private String bankAccNo;

	private String accName;

	private String remark;

	private String sdinformaiton_id;

	private String applicantArea3;

	private String applicantStartID;

	private String applicantEndID;

	private String socialSecurity;

	private String heightA ;
	
	private String weightB ;
	
	private String applicantIncome;


	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("Id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("createDate", DataColumn.DATETIME, 1, 0 , 0 , false , false),
		new SchemaColumn("modifyDate", DataColumn.DATETIME, 2, 0 , 0 , false , false),
		new SchemaColumn("informationSn", DataColumn.STRING, 3, 20 , 0 , false , false),
		new SchemaColumn("applicantSn", DataColumn.STRING, 4, 32 , 0 , true , false),
		new SchemaColumn("applicantName", DataColumn.STRING, 5, 50 , 0 , true , false),
		new SchemaColumn("applicantEnName", DataColumn.STRING, 6, 20 , 0 , false , false),
		new SchemaColumn("applicantLastName", DataColumn.STRING, 7, 20 , 0 , false , false),
		new SchemaColumn("applicantFirstName", DataColumn.STRING, 8, 20 , 0 , false , false),
		new SchemaColumn("applicantIdentityType", DataColumn.STRING, 9, 10 , 0 , true , false),
		new SchemaColumn("applicantIdentityTypeName", DataColumn.STRING, 10, 50 , 0 , true , false),
		new SchemaColumn("applicantIdentityId", DataColumn.STRING, 11, 20 , 0 , false , false),
		new SchemaColumn("applicantSex", DataColumn.STRING, 12, 2 , 0 , false , false),
		new SchemaColumn("applicantSexName", DataColumn.STRING, 13, 10 , 0 , false , false),
		new SchemaColumn("applicantBirthday", DataColumn.STRING, 14, 20 , 0 , false , false),
		new SchemaColumn("applicantAge", DataColumn.STRING, 15, 3 , 0 , false , false),
		new SchemaColumn("applicantMail", DataColumn.STRING, 16, 50 , 0 , false , false),
		new SchemaColumn("applicantArea1", DataColumn.STRING, 17, 20 , 0 , false , false),
		new SchemaColumn("applicantArea2", DataColumn.STRING, 18, 20 , 0 , false , false),
		new SchemaColumn("applicantAddress", DataColumn.STRING, 19, 200 , 0 , false , false),
		new SchemaColumn("applicantZipCode", DataColumn.STRING, 20, 10 , 0 , false , false),
		new SchemaColumn("applicantMobile", DataColumn.STRING, 21, 13 , 0 , false , false),
		new SchemaColumn("applicantTel", DataColumn.STRING, 22, 13 , 0 , false , false),
		new SchemaColumn("applicantOccupation1", DataColumn.STRING, 23, 20 , 0 , false , false),
		new SchemaColumn("applicantOccupation2", DataColumn.STRING, 24, 20 , 0 , false , false),
		new SchemaColumn("applicantOccupation3", DataColumn.STRING, 25, 20 , 0 , false , false),
		new SchemaColumn("invoiceHeading", DataColumn.STRING, 26, 100 , 0 , false , false),
		new SchemaColumn("bankCode", DataColumn.STRING, 27, 25 , 0 , false , false),
		new SchemaColumn("bankAccNo", DataColumn.STRING, 28, 25 , 0 , false , false),
		new SchemaColumn("accName", DataColumn.STRING, 29, 50 , 0 , false , false),
		new SchemaColumn("remark", DataColumn.STRING, 30, 50 , 0 , false , false),
		new SchemaColumn("sdinformaiton_id", DataColumn.STRING, 31, 32 , 0 , false , false),
		new SchemaColumn("applicantArea3", DataColumn.STRING, 32, 200 , 0 , false , false),
		new SchemaColumn("applicantStartID", DataColumn.STRING, 33, 200 , 0 , false , false),
		new SchemaColumn("applicantEndID", DataColumn.STRING, 34, 200 , 0 , false , false),
		new SchemaColumn("socialSecurity", DataColumn.STRING, 35, 4 , 0 , false , false),
		new SchemaColumn("heightA", DataColumn.STRING, 36, 3 , 0 , false , false),
		new SchemaColumn("weightB", DataColumn.STRING, 37, 3 , 0 , false , false),
		new SchemaColumn("applicantIncome", DataColumn.STRING, 38, 20 , 0 , false , false)

	};

	public static final String _TableCode = "SDInformationAppnt";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into SDInformationAppnt values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update SDInformationAppnt set Id=?,createDate=?,modifyDate=?,informationSn=?,applicantSn=?,applicantName=?,applicantEnName=?,applicantLastName=?,applicantFirstName=?,applicantIdentityType=?,applicantIdentityTypeName=?,applicantIdentityId=?,applicantSex=?,applicantSexName=?,applicantBirthday=?,applicantAge=?,applicantMail=?,applicantArea1=?,applicantArea2=?,applicantAddress=?,applicantZipCode=?,applicantMobile=?,applicantTel=?,applicantOccupation1=?,applicantOccupation2=?,applicantOccupation3=?,invoiceHeading=?,bankCode=?,bankAccNo=?,accName=?,remark=?,sdinformaiton_id=?,applicantArea3=?,applicantStartID=?,applicantEndID=?,socialSecurity=?,heightA=?,weightB=?,applicantIncome=? where Id=?";

	protected static final String _DeleteSQL = "delete from SDInformationAppnt  where Id=?";

	protected static final String _FillAllSQL = "select * from SDInformationAppnt  where Id=?";

	public SDInformationAppntSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[39];
	}

	protected Schema newInstance(){
		return new SDInformationAppntSchema();
	}

	protected SchemaSet newSet(){
		return new SDInformationAppntSet();
	}

	public SDInformationAppntSet query() {
		return query(null, -1, -1);
	}

	public SDInformationAppntSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public SDInformationAppntSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public SDInformationAppntSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (SDInformationAppntSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){Id = (String)v;return;}
		if (i == 1){createDate = (Date)v;return;}
		if (i == 2){modifyDate = (Date)v;return;}
		if (i == 3){informationSn = (String)v;return;}
		if (i == 4){applicantSn = (String)v;return;}
		if (i == 5){applicantName = (String)v;return;}
		if (i == 6){applicantEnName = (String)v;return;}
		if (i == 7){applicantLastName = (String)v;return;}
		if (i == 8){applicantFirstName = (String)v;return;}
		if (i == 9){applicantIdentityType = (String)v;return;}
		if (i == 10){applicantIdentityTypeName = (String)v;return;}
		if (i == 11){applicantIdentityId = (String)v;return;}
		if (i == 12){applicantSex = (String)v;return;}
		if (i == 13){applicantSexName = (String)v;return;}
		if (i == 14){applicantBirthday = (String)v;return;}
		if (i == 15){applicantAge = (String)v;return;}
		if (i == 16){applicantMail = (String)v;return;}
		if (i == 17){applicantArea1 = (String)v;return;}
		if (i == 18){applicantArea2 = (String)v;return;}
		if (i == 19){applicantAddress = (String)v;return;}
		if (i == 20){applicantZipCode = (String)v;return;}
		if (i == 21){applicantMobile = (String)v;return;}
		if (i == 22){applicantTel = (String)v;return;}
		if (i == 23){applicantOccupation1 = (String)v;return;}
		if (i == 24){applicantOccupation2 = (String)v;return;}
		if (i == 25){applicantOccupation3 = (String)v;return;}
		if (i == 26){invoiceHeading = (String)v;return;}
		if (i == 27){bankCode = (String)v;return;}
		if (i == 28){bankAccNo = (String)v;return;}
		if (i == 29){accName = (String)v;return;}
		if (i == 30){remark = (String)v;return;}
		if (i == 31){sdinformaiton_id = (String)v;return;}
		if (i == 32){applicantArea3 = (String)v;return;}
		if (i == 33){applicantStartID = (String)v;return;}
		if (i == 34){applicantEndID = (String)v;return;}
		if (i == 35){socialSecurity = (String)v;return;}
		if (i == 36){heightA = (String)v;return;}
		if (i == 37){weightB = (String)v;return;}
		if (i == 38){applicantIncome = (String)v;return;}

	}

	public Object getV(int i) {
		if (i == 0){return Id;}
		if (i == 1){return createDate;}
		if (i == 2){return modifyDate;}
		if (i == 3){return informationSn;}
		if (i == 4){return applicantSn;}
		if (i == 5){return applicantName;}
		if (i == 6){return applicantEnName;}
		if (i == 7){return applicantLastName;}
		if (i == 8){return applicantFirstName;}
		if (i == 9){return applicantIdentityType;}
		if (i == 10){return applicantIdentityTypeName;}
		if (i == 11){return applicantIdentityId;}
		if (i == 12){return applicantSex;}
		if (i == 13){return applicantSexName;}
		if (i == 14){return applicantBirthday;}
		if (i == 15){return applicantAge;}
		if (i == 16){return applicantMail;}
		if (i == 17){return applicantArea1;}
		if (i == 18){return applicantArea2;}
		if (i == 19){return applicantAddress;}
		if (i == 20){return applicantZipCode;}
		if (i == 21){return applicantMobile;}
		if (i == 22){return applicantTel;}
		if (i == 23){return applicantOccupation1;}
		if (i == 24){return applicantOccupation2;}
		if (i == 25){return applicantOccupation3;}
		if (i == 26){return invoiceHeading;}
		if (i == 27){return bankCode;}
		if (i == 28){return bankAccNo;}
		if (i == 29){return accName;}
		if (i == 30){return remark;}
		if (i == 31){return sdinformaiton_id;}
		if (i == 32){return applicantArea3;}
		if (i == 33){return applicantStartID;}
		if (i == 34){return applicantEndID;}
		if (i == 35){return socialSecurity;}
		if (i == 36){return heightA;}
		if (i == 37){return weightB;}
		if (i == 38){return applicantIncome;}
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
	* 是否必填 :false<br>
	*/
	public Date getcreateDate() {
		return createDate;
	}

	/**
	* 设置字段createDate的值，该字段的<br>
	* 字段名称 :创建日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
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
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getinformationSn() {
		return informationSn;
	}

	/**
	* 设置字段informationSn的值，该字段的<br>
	* 字段名称 :订单明细表编号<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setinformationSn(String informationSn) {
		this.informationSn = informationSn;
    }

	/**
	* 获取字段applicantSn的值，该字段的<br>
	* 字段名称 :投保人编码<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getapplicantSn() {
		return applicantSn;
	}

	/**
	* 设置字段applicantSn的值，该字段的<br>
	* 字段名称 :投保人编码<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setapplicantSn(String applicantSn) {
		this.applicantSn = applicantSn;
    }

	/**
	* 获取字段applicantName的值，该字段的<br>
	* 字段名称 :投保人姓名<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getapplicantName() {
		return applicantName;
	}

	/**
	* 设置字段applicantName的值，该字段的<br>
	* 字段名称 :投保人姓名<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setapplicantName(String applicantName) {
		this.applicantName = applicantName;
    }

	/**
	* 获取字段applicantEnName的值，该字段的<br>
	* 字段名称 :投保人英文名<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getapplicantEnName() {
		return applicantEnName;
	}

	/**
	* 设置字段applicantEnName的值，该字段的<br>
	* 字段名称 :投保人英文名<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setapplicantEnName(String applicantEnName) {
		this.applicantEnName = applicantEnName;
    }

	/**
	* 获取字段applicantLastName的值，该字段的<br>
	* 字段名称 :投保人姓氏拼音<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getapplicantLastName() {
		return applicantLastName;
	}

	/**
	* 设置字段applicantLastName的值，该字段的<br>
	* 字段名称 :投保人姓氏拼音<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setapplicantLastName(String applicantLastName) {
		this.applicantLastName = applicantLastName;
    }

	/**
	* 获取字段applicantFirstName的值，该字段的<br>
	* 字段名称 :投保人名字拼音<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getapplicantFirstName() {
		return applicantFirstName;
	}

	/**
	* 设置字段applicantFirstName的值，该字段的<br>
	* 字段名称 :投保人名字拼音<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setapplicantFirstName(String applicantFirstName) {
		this.applicantFirstName = applicantFirstName;
    }

	/**
	* 获取字段applicantIdentityType的值，该字段的<br>
	* 字段名称 :投保人证件类型<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getapplicantIdentityType() {
		return applicantIdentityType;
	}

	/**
	* 设置字段applicantIdentityType的值，该字段的<br>
	* 字段名称 :投保人证件类型<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setapplicantIdentityType(String applicantIdentityType) {
		this.applicantIdentityType = applicantIdentityType;
    }

	/**
	* 获取字段applicantIdentityTypeName的值，该字段的<br>
	* 字段名称 :投保人证件类型显示<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getapplicantIdentityTypeName() {
		return applicantIdentityTypeName;
	}

	/**
	* 设置字段applicantIdentityTypeName的值，该字段的<br>
	* 字段名称 :投保人证件类型显示<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setapplicantIdentityTypeName(String applicantIdentityTypeName) {
		this.applicantIdentityTypeName = applicantIdentityTypeName;
    }

	/**
	* 获取字段applicantIdentityId的值，该字段的<br>
	* 字段名称 :投保人证件号码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getapplicantIdentityId() {
		return applicantIdentityId;
	}

	/**
	* 设置字段applicantIdentityId的值，该字段的<br>
	* 字段名称 :投保人证件号码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setapplicantIdentityId(String applicantIdentityId) {
		this.applicantIdentityId = applicantIdentityId;
    }

	/**
	* 获取字段applicantSex的值，该字段的<br>
	* 字段名称 :投保人性别<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getapplicantSex() {
		return applicantSex;
	}

	/**
	* 设置字段applicantSex的值，该字段的<br>
	* 字段名称 :投保人性别<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setapplicantSex(String applicantSex) {
		this.applicantSex = applicantSex;
    }

	/**
	* 获取字段applicantSexName的值，该字段的<br>
	* 字段名称 :投保人性别显示<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getapplicantSexName() {
		return applicantSexName;
	}

	/**
	* 设置字段applicantSexName的值，该字段的<br>
	* 字段名称 :投保人性别显示<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setapplicantSexName(String applicantSexName) {
		this.applicantSexName = applicantSexName;
    }

	/**
	* 获取字段applicantBirthday的值，该字段的<br>
	* 字段名称 :投保人出生日期<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getapplicantBirthday() {
		return applicantBirthday;
	}

	/**
	* 设置字段applicantBirthday的值，该字段的<br>
	* 字段名称 :投保人出生日期<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setapplicantBirthday(String applicantBirthday) {
		this.applicantBirthday = applicantBirthday;
    }

	/**
	* 获取字段applicantAge的值，该字段的<br>
	* 字段名称 :投保人年龄<br>
	* 数据类型 :varchar(3)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getapplicantAge() {
		return applicantAge;
	}

	/**
	* 设置字段applicantAge的值，该字段的<br>
	* 字段名称 :投保人年龄<br>
	* 数据类型 :varchar(3)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setapplicantAge(String applicantAge) {
		this.applicantAge = applicantAge;
    }

	/**
	* 获取字段applicantMail的值，该字段的<br>
	* 字段名称 :投保人电子邮箱<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getapplicantMail() {
		return applicantMail;
	}

	/**
	* 设置字段applicantMail的值，该字段的<br>
	* 字段名称 :投保人电子邮箱<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setapplicantMail(String applicantMail) {
		this.applicantMail = applicantMail;
    }

	/**
	* 获取字段applicantArea1的值，该字段的<br>
	* 字段名称 :投保人地区一级<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getapplicantArea1() {
		return applicantArea1;
	}

	/**
	* 设置字段applicantArea1的值，该字段的<br>
	* 字段名称 :投保人地区一级<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setapplicantArea1(String applicantArea1) {
		this.applicantArea1 = applicantArea1;
    }

	/**
	* 获取字段applicantArea2的值，该字段的<br>
	* 字段名称 :投保人地区二级<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getapplicantArea2() {
		return applicantArea2;
	}

	/**
	* 设置字段applicantArea2的值，该字段的<br>
	* 字段名称 :投保人地区二级<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setapplicantArea2(String applicantArea2) {
		this.applicantArea2 = applicantArea2;
    }

	/**
	* 获取字段applicantAddress的值，该字段的<br>
	* 字段名称 :投保人地址<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getapplicantAddress() {
		return applicantAddress;
	}

	/**
	* 设置字段applicantAddress的值，该字段的<br>
	* 字段名称 :投保人地址<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setapplicantAddress(String applicantAddress) {
		this.applicantAddress = applicantAddress;
    }

	/**
	* 获取字段applicantZipCode的值，该字段的<br>
	* 字段名称 :邮政编码<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getapplicantZipCode() {
		return applicantZipCode;
	}

	/**
	* 设置字段applicantZipCode的值，该字段的<br>
	* 字段名称 :邮政编码<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setapplicantZipCode(String applicantZipCode) {
		this.applicantZipCode = applicantZipCode;
    }

	/**
	* 获取字段applicantMobile的值，该字段的<br>
	* 字段名称 :投保人移动电话<br>
	* 数据类型 :varchar(12)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getapplicantMobile() {
		return applicantMobile;
	}

	/**
	* 设置字段applicantMobile的值，该字段的<br>
	* 字段名称 :投保人移动电话<br>
	* 数据类型 :varchar(12)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setapplicantMobile(String applicantMobile) {
		this.applicantMobile = applicantMobile;
    }

	/**
	* 获取字段applicantTel的值，该字段的<br>
	* 字段名称 :投保人电话<br>
	* 数据类型 :varchar(12)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getapplicantTel() {
		return applicantTel;
	}

	/**
	* 设置字段applicantTel的值，该字段的<br>
	* 字段名称 :投保人电话<br>
	* 数据类型 :varchar(12)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setapplicantTel(String applicantTel) {
		this.applicantTel = applicantTel;
    }

	/**
	* 获取字段applicantOccupation1的值，该字段的<br>
	* 字段名称 :投保人职业一级<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getapplicantOccupation1() {
		return applicantOccupation1;
	}

	/**
	* 设置字段applicantOccupation1的值，该字段的<br>
	* 字段名称 :投保人职业一级<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setapplicantOccupation1(String applicantOccupation1) {
		this.applicantOccupation1 = applicantOccupation1;
    }

	/**
	* 获取字段applicantOccupation2的值，该字段的<br>
	* 字段名称 :投保人职业二级<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getapplicantOccupation2() {
		return applicantOccupation2;
	}

	/**
	* 设置字段applicantOccupation2的值，该字段的<br>
	* 字段名称 :投保人职业二级<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setapplicantOccupation2(String applicantOccupation2) {
		this.applicantOccupation2 = applicantOccupation2;
    }

	/**
	* 获取字段applicantOccupation3的值，该字段的<br>
	* 字段名称 :投保人职业三级<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getapplicantOccupation3() {
		return applicantOccupation3;
	}

	/**
	* 设置字段applicantOccupation3的值，该字段的<br>
	* 字段名称 :投保人职业三级<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setapplicantOccupation3(String applicantOccupation3) {
		this.applicantOccupation3 = applicantOccupation3;
    }

	/**
	* 获取字段invoiceHeading的值，该字段的<br>
	* 字段名称 :发票台头<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getinvoiceHeading() {
		return invoiceHeading;
	}

	/**
	* 设置字段invoiceHeading的值，该字段的<br>
	* 字段名称 :发票台头<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setinvoiceHeading(String invoiceHeading) {
		this.invoiceHeading = invoiceHeading;
    }

	/**
	* 获取字段bankCode的值，该字段的<br>
	* 字段名称 :银行编码<br>
	* 数据类型 :varchar(25)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getbankCode() {
		return bankCode;
	}

	/**
	* 设置字段bankCode的值，该字段的<br>
	* 字段名称 :银行编码<br>
	* 数据类型 :varchar(25)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setbankCode(String bankCode) {
		this.bankCode = bankCode;
    }

	/**
	* 获取字段bankAccNo的值，该字段的<br>
	* 字段名称 :银行帐号<br>
	* 数据类型 :varchar(25)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getbankAccNo() {
		return bankAccNo;
	}

	/**
	* 设置字段bankAccNo的值，该字段的<br>
	* 字段名称 :银行帐号<br>
	* 数据类型 :varchar(25)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setbankAccNo(String bankAccNo) {
		this.bankAccNo = bankAccNo;
    }

	/**
	* 获取字段accName的值，该字段的<br>
	* 字段名称 :银行户名<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getaccName() {
		return accName;
	}

	/**
	* 设置字段accName的值，该字段的<br>
	* 字段名称 :银行户名<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setaccName(String accName) {
		this.accName = accName;
    }

	/**
	* 获取字段remark的值，该字段的<br>
	* 字段名称 :备用字段<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getremark() {
		return remark;
	}

	/**
	* 设置字段remark的值，该字段的<br>
	* 字段名称 :备用字段<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setremark(String remark) {
		this.remark = remark;
    }

	/**
	* 获取字段sdinformaiton_id的值，该字段的<br>
	* 字段名称 :sdinformaiton_id<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getsdinformaiton_id() {
		return sdinformaiton_id;
	}

	/**
	* 设置字段sdinformaiton_id的值，该字段的<br>
	* 字段名称 :sdinformaiton_id<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setsdinformaiton_id(String sdinformaiton_id) {
		this.sdinformaiton_id = sdinformaiton_id;
    }

	/**
	* 获取字段applicantArea3的值，该字段的<br>
	* 字段名称 :applicantArea3<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getapplicantArea3() {
		return applicantArea3;
	}

	/**
	* 设置字段applicantArea3的值，该字段的<br>
	* 字段名称 :applicantArea3<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setapplicantArea3(String applicantArea3) {
		this.applicantArea3 = applicantArea3;
    }

	/**
	* 获取字段applicantStartID的值，该字段的<br>
	* 字段名称 :applicantStartID<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getapplicantStartID() {
		return applicantStartID;
	}

	/**
	* 设置字段applicantStartID的值，该字段的<br>
	* 字段名称 :applicantStartID<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setapplicantStartID(String applicantStartID) {
		this.applicantStartID = applicantStartID;
    }

	/**
	* 获取字段applicantEndID的值，该字段的<br>
	* 字段名称 :applicantEndID<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getapplicantEndID() {
		return applicantEndID;
	}

	/**
	* 设置字段applicantEndID的值，该字段的<br>
	* 字段名称 :applicantEndID<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setapplicantEndID(String applicantEndID) {
		this.applicantEndID = applicantEndID;
    }

	/**
	* 获取字段socialSecurity的值，该字段的<br>
	* 字段名称 :是否有医保<br>
	* 数据类型 :varchar(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getsocialSecurity() {
		return socialSecurity;
	}

	/**
	* 设置字段socialSecurity的值，该字段的<br>
	* 字段名称 :是否有医保<br>
	* 数据类型 :varchar(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setsocialSecurity(String socialSecurity) {
		this.socialSecurity = socialSecurity;
    }
	
	
	public String getHeightA() {
		return heightA;
	}

	public void setHeightA(String heightA) {
		this.heightA = heightA;
	}

	public String getWeightB() {
		return weightB;
	}

	public void setWeightB(String weightB) {
		this.weightB = weightB;
	}
	
	public String getApplicantIncome() {
		return applicantIncome;
	}

	public void setApplicantIncome(String applicantIncome) {
		this.applicantIncome = applicantIncome;
	}
}