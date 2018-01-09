package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：被保人信息表<br>
 * 表代码：SDInformationInsured<br>
 * 表主键：Id<br>
 */
public class SDInformationInsuredSchema extends Schema {
	private String Id;

	private Date createDate;

	private Date modifyDate;

	private String orderSn;

	private String informationSn;

	private String recognizeeSn;

	private String recognizeeAppntRelation;

	private String recognizeeAppntRelationName;

	private String recognizeeName;

	private String recognizeeEnName;

	private String recognizeeLashName;

	private String recognizeeFirstName;

	private String recognizeeIdentityType;

	private String recognizeeIdentityTypeName;

	private String recognizeeIdentityId;

	private String recognizeeSex;

	private String recognizeeSexName;

	private String recognizeeBirthday;

	private String recognizeeAge;

	private String recognizeeMobile;

	private String recognizeeTel;

	private String recognizeeMail;

	private String recognizeeOccupation1;

	private String recognizeeOccupation2;

	private String recognizeeOccupation3;

	private String recognizeeArea1;

	private String recognizeeArea2;

	private String recognizeeAddress;

	private String recognizeeZipCode;

	private String recognizeeIsMarry;

	private String schoolOrCompany;

	private String outGoingParpose;

	private String flightNo;

	private Date flightTime;

	private String flightLocation;

	private String driverSchoolName;

	private String driverNo;

	private String destinationCountry;

	private String destinationCountryText;

	private String isSelf;

	private String height;

	private String weight;

	private String overseasOccupation;

	private String travelType;

	private String travelMode;

	private String nationality;

	private String haveBuy;

	private String uwCheckFlag;

	private String remark;

	private String insuredSn;

	private String recognizeePrem;

	private String recognizeeOperate;

	private String mulInsuredFlag;

	private String sdinformation_id;

	private String recognizeeTotalPrem;

	private String recognizeeArea3;

	private String recognizeeEndID;

	private String recognizeeStartID;

	private String recognizeeKey;

	private String recognizeeMul;

	private String socialSecurity;

	private String discountPrice;

	private String carPlateNo;
	
	private String recognizeeOrigin1;
	
	private String recognizeeOrigin2;
	
	private String recognizeeOrigin3;
	
	private String recognizeeDestination1;
	
	private String recognizeeDestination2;
	
	private String recognizeeDestination3;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("Id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("createDate", DataColumn.DATETIME, 1, 0 , 0 , true , false),
		new SchemaColumn("modifyDate", DataColumn.DATETIME, 2, 0 , 0 , false , false),
		new SchemaColumn("orderSn", DataColumn.STRING, 3, 20 , 0 , true , false),
		new SchemaColumn("informationSn", DataColumn.STRING, 4, 32 , 0 , false , false),
		new SchemaColumn("recognizeeSn", DataColumn.STRING, 5, 32 , 0 , false , false),
		new SchemaColumn("recognizeeAppntRelation", DataColumn.STRING, 6, 20 , 0 , true , false),
		new SchemaColumn("recognizeeAppntRelationName", DataColumn.STRING, 7, 50 , 0 , true , false),
		new SchemaColumn("recognizeeName", DataColumn.STRING, 8, 50 , 0 , false , false),
		new SchemaColumn("recognizeeEnName", DataColumn.STRING, 9, 20 , 0 , false , false),
		new SchemaColumn("recognizeeLashName", DataColumn.STRING, 10, 20 , 0 , false , false),
		new SchemaColumn("recognizeeFirstName", DataColumn.STRING, 11, 20 , 0 , false , false),
		new SchemaColumn("recognizeeIdentityType", DataColumn.STRING, 12, 10 , 0 , false , false),
		new SchemaColumn("recognizeeIdentityTypeName", DataColumn.STRING, 13, 50 , 0 , false , false),
		new SchemaColumn("recognizeeIdentityId", DataColumn.STRING, 14, 20 , 0 , false , false),
		new SchemaColumn("recognizeeSex", DataColumn.STRING, 15, 2 , 0 , false , false),
		new SchemaColumn("recognizeeSexName", DataColumn.STRING, 16, 10 , 0 , false , false),
		new SchemaColumn("recognizeeBirthday", DataColumn.STRING, 17, 20 , 0 , false , false),
		new SchemaColumn("recognizeeAge", DataColumn.STRING, 18, 3 , 0 , false , false),
		new SchemaColumn("recognizeeMobile", DataColumn.STRING, 19, 13 , 0 , false , false),
		new SchemaColumn("recognizeeTel", DataColumn.STRING, 20, 20 , 0 , false , false),
		new SchemaColumn("recognizeeMail", DataColumn.STRING, 21, 50 , 0 , false , false),
		new SchemaColumn("recognizeeOccupation1", DataColumn.STRING, 22, 20 , 0 , false , false),
		new SchemaColumn("recognizeeOccupation2", DataColumn.STRING, 23, 20 , 0 , false , false),
		new SchemaColumn("recognizeeOccupation3", DataColumn.STRING, 24, 20 , 0 , false , false),
		new SchemaColumn("recognizeeArea1", DataColumn.STRING, 25, 20 , 0 , false , false),
		new SchemaColumn("recognizeeArea2", DataColumn.STRING, 26, 20 , 0 , false , false),
		new SchemaColumn("recognizeeAddress", DataColumn.STRING, 27, 200 , 0 , false , false),
		new SchemaColumn("recognizeeZipCode", DataColumn.STRING, 28, 10 , 0 , false , false),
		new SchemaColumn("recognizeeIsMarry", DataColumn.STRING, 29, 5 , 0 , false , false),
		new SchemaColumn("schoolOrCompany", DataColumn.STRING, 30, 20 , 0 , false , false),
		new SchemaColumn("outGoingParpose", DataColumn.STRING, 31, 20 , 0 , false , false),
		new SchemaColumn("flightNo", DataColumn.STRING, 32, 20 , 0 , false , false),
		new SchemaColumn("flightTime", DataColumn.DATETIME, 33, 0 , 0 , false , false),
		new SchemaColumn("flightLocation", DataColumn.STRING, 34, 20 , 0 , false , false),
		new SchemaColumn("driverSchoolName", DataColumn.STRING, 35, 20 , 0 , false , false),
		new SchemaColumn("driverNo", DataColumn.STRING, 36, 20 , 0 , false , false),
		new SchemaColumn("destinationCountry", DataColumn.STRING, 37, 30 , 0 , false , false),
		new SchemaColumn("destinationCountryText", DataColumn.STRING, 38, 30 , 0 , false , false),
		new SchemaColumn("isSelf", DataColumn.STRING, 39, 1 , 0 , false , false),
		new SchemaColumn("height", DataColumn.STRING, 40, 10 , 0 , false , false),
		new SchemaColumn("weight", DataColumn.STRING, 41, 10 , 0 , false , false),
		new SchemaColumn("overseasOccupation", DataColumn.STRING, 42, 50 , 0 , false , false),
		new SchemaColumn("travelType", DataColumn.STRING, 43, 20 , 0 , false , false),
		new SchemaColumn("travelMode", DataColumn.STRING, 44, 20 , 0 , false , false),
		new SchemaColumn("nationality", DataColumn.STRING, 45, 20 , 0 , false , false),
		new SchemaColumn("haveBuy", DataColumn.STRING, 46, 20 , 0 , false , false),
		new SchemaColumn("uwCheckFlag", DataColumn.STRING, 47, 1 , 0 , false , false),
		new SchemaColumn("remark", DataColumn.STRING, 48, 50 , 0 , false , false),
		new SchemaColumn("insuredSn", DataColumn.STRING, 49, 100 , 0 , false , false),
		new SchemaColumn("recognizeePrem", DataColumn.STRING, 50, 20 , 0 , false , false),
		new SchemaColumn("recognizeeOperate", DataColumn.STRING, 51, 4 , 0 , false , false),
		new SchemaColumn("mulInsuredFlag", DataColumn.STRING, 52, 10 , 0 , false , false),
		new SchemaColumn("sdinformation_id", DataColumn.STRING, 53, 32 , 0 , false , false),
		new SchemaColumn("recognizeeTotalPrem", DataColumn.STRING, 54, 20 , 0 , false , false),
		new SchemaColumn("recognizeeArea3", DataColumn.STRING, 55, 200 , 0 , false , false),
		new SchemaColumn("recognizeeEndID", DataColumn.STRING, 56, 200 , 0 , false , false),
		new SchemaColumn("recognizeeStartID", DataColumn.STRING, 57, 200 , 0 , false , false),
		new SchemaColumn("recognizeeKey", DataColumn.STRING, 58, 255 , 0 , false , false),
		new SchemaColumn("recognizeeMul", DataColumn.STRING, 59, 255 , 0 , false , false),
		new SchemaColumn("socialSecurity", DataColumn.STRING, 60, 255 , 0 , false , false),
		new SchemaColumn("discountPrice", DataColumn.STRING, 61, 20 , 0 , false , false),
		new SchemaColumn("carPlateNo", DataColumn.STRING, 62, 20 , 0 , false , false),
		new SchemaColumn("recognizeeOrigin1", DataColumn.STRING, 63, 255 , 0 , false , false),
		new SchemaColumn("recognizeeOrigin2", DataColumn.STRING, 64, 255 , 0 , false , false),
		new SchemaColumn("recognizeeOrigin3", DataColumn.STRING, 65, 255 , 0 , false , false),
		new SchemaColumn("recognizeeDestination1", DataColumn.STRING, 66, 255 , 0 , false , false),
		new SchemaColumn("recognizeeDestination2", DataColumn.STRING, 67, 255 , 0 , false , false),
		new SchemaColumn("recognizeeDestination3", DataColumn.STRING, 68, 255 , 0 , false , false)
	};

	public static final String _TableCode = "SDInformationInsured";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into SDInformationInsured values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update SDInformationInsured set Id=?,createDate=?,modifyDate=?,orderSn=?,informationSn=?,recognizeeSn=?,recognizeeAppntRelation=?,recognizeeAppntRelationName=?,recognizeeName=?,recognizeeEnName=?,recognizeeLashName=?,recognizeeFirstName=?,recognizeeIdentityType=?,recognizeeIdentityTypeName=?,recognizeeIdentityId=?,recognizeeSex=?,recognizeeSexName=?,recognizeeBirthday=?,recognizeeAge=?,recognizeeMobile=?,recognizeeTel=?,recognizeeMail=?,recognizeeOccupation1=?,recognizeeOccupation2=?,recognizeeOccupation3=?,recognizeeArea1=?,recognizeeArea2=?,recognizeeAddress=?,recognizeeZipCode=?,recognizeeIsMarry=?,schoolOrCompany=?,outGoingParpose=?,flightNo=?,flightTime=?,flightLocation=?,driverSchoolName=?,driverNo=?,destinationCountry=?,destinationCountryText=?,isSelf=?,height=?,weight=?,overseasOccupation=?,travelType=?,travelMode=?,nationality=?,haveBuy=?,uwCheckFlag=?,remark=?,insuredSn=?,recognizeePrem=?,recognizeeOperate=?,mulInsuredFlag=?,sdinformation_id=?,recognizeeTotalPrem=?,recognizeeArea3=?,recognizeeEndID=?,recognizeeStartID=?,recognizeeKey=?,recognizeeMul=?,socialSecurity=?,discountPrice=?,carPlateNo=?,recognizeeOrigin1=?,recognizeeOrigin2=?,recognizeeOrigin3=?,recognizeeDestination1=?,recognizeeDestination2=?,recognizeeDestination3=? where Id=?";

	protected static final String _DeleteSQL = "delete from SDInformationInsured  where Id=?";

	protected static final String _FillAllSQL = "select * from SDInformationInsured  where Id=?";

	public SDInformationInsuredSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[69];
	}

	protected Schema newInstance(){
		return new SDInformationInsuredSchema();
	}

	protected SchemaSet newSet(){
		return new SDInformationInsuredSet();
	}

	public SDInformationInsuredSet query() {
		return query(null, -1, -1);
	}

	public SDInformationInsuredSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public SDInformationInsuredSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public SDInformationInsuredSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (SDInformationInsuredSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){Id = (String)v;return;}
		if (i == 1){createDate = (Date)v;return;}
		if (i == 2){modifyDate = (Date)v;return;}
		if (i == 3){orderSn = (String)v;return;}
		if (i == 4){informationSn = (String)v;return;}
		if (i == 5){recognizeeSn = (String)v;return;}
		if (i == 6){recognizeeAppntRelation = (String)v;return;}
		if (i == 7){recognizeeAppntRelationName = (String)v;return;}
		if (i == 8){recognizeeName = (String)v;return;}
		if (i == 9){recognizeeEnName = (String)v;return;}
		if (i == 10){recognizeeLashName = (String)v;return;}
		if (i == 11){recognizeeFirstName = (String)v;return;}
		if (i == 12){recognizeeIdentityType = (String)v;return;}
		if (i == 13){recognizeeIdentityTypeName = (String)v;return;}
		if (i == 14){recognizeeIdentityId = (String)v;return;}
		if (i == 15){recognizeeSex = (String)v;return;}
		if (i == 16){recognizeeSexName = (String)v;return;}
		if (i == 17){recognizeeBirthday = (String)v;return;}
		if (i == 18){recognizeeAge = (String)v;return;}
		if (i == 19){recognizeeMobile = (String)v;return;}
		if (i == 20){recognizeeTel = (String)v;return;}
		if (i == 21){recognizeeMail = (String)v;return;}
		if (i == 22){recognizeeOccupation1 = (String)v;return;}
		if (i == 23){recognizeeOccupation2 = (String)v;return;}
		if (i == 24){recognizeeOccupation3 = (String)v;return;}
		if (i == 25){recognizeeArea1 = (String)v;return;}
		if (i == 26){recognizeeArea2 = (String)v;return;}
		if (i == 27){recognizeeAddress = (String)v;return;}
		if (i == 28){recognizeeZipCode = (String)v;return;}
		if (i == 29){recognizeeIsMarry = (String)v;return;}
		if (i == 30){schoolOrCompany = (String)v;return;}
		if (i == 31){outGoingParpose = (String)v;return;}
		if (i == 32){flightNo = (String)v;return;}
		if (i == 33){flightTime = (Date)v;return;}
		if (i == 34){flightLocation = (String)v;return;}
		if (i == 35){driverSchoolName = (String)v;return;}
		if (i == 36){driverNo = (String)v;return;}
		if (i == 37){destinationCountry = (String)v;return;}
		if (i == 38){destinationCountryText = (String)v;return;}
		if (i == 39){isSelf = (String)v;return;}
		if (i == 40){height = (String)v;return;}
		if (i == 41){weight = (String)v;return;}
		if (i == 42){overseasOccupation = (String)v;return;}
		if (i == 43){travelType = (String)v;return;}
		if (i == 44){travelMode = (String)v;return;}
		if (i == 45){nationality = (String)v;return;}
		if (i == 46){haveBuy = (String)v;return;}
		if (i == 47){uwCheckFlag = (String)v;return;}
		if (i == 48){remark = (String)v;return;}
		if (i == 49){insuredSn = (String)v;return;}
		if (i == 50){recognizeePrem = (String)v;return;}
		if (i == 51){recognizeeOperate = (String)v;return;}
		if (i == 52){mulInsuredFlag = (String)v;return;}
		if (i == 53){sdinformation_id = (String)v;return;}
		if (i == 54){recognizeeTotalPrem = (String)v;return;}
		if (i == 55){recognizeeArea3 = (String)v;return;}
		if (i == 56){recognizeeEndID = (String)v;return;}
		if (i == 57){recognizeeStartID = (String)v;return;}
		if (i == 58){recognizeeKey = (String)v;return;}
		if (i == 59){recognizeeMul = (String)v;return;}
		if (i == 60){socialSecurity = (String)v;return;}
		if (i == 61){discountPrice = (String)v;return;}
		if (i == 62){carPlateNo = (String)v;return;}
		if (i == 63){recognizeeOrigin1 = (String)v;return;}
		if (i == 64){recognizeeOrigin2 = (String)v;return;}
		if (i == 65){recognizeeOrigin3 = (String)v;return;}
		if (i == 66){recognizeeDestination1 = (String)v;return;}
		if (i == 67){recognizeeDestination2 = (String)v;return;}
		if (i == 68){recognizeeDestination3 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return Id;}
		if (i == 1){return createDate;}
		if (i == 2){return modifyDate;}
		if (i == 3){return orderSn;}
		if (i == 4){return informationSn;}
		if (i == 5){return recognizeeSn;}
		if (i == 6){return recognizeeAppntRelation;}
		if (i == 7){return recognizeeAppntRelationName;}
		if (i == 8){return recognizeeName;}
		if (i == 9){return recognizeeEnName;}
		if (i == 10){return recognizeeLashName;}
		if (i == 11){return recognizeeFirstName;}
		if (i == 12){return recognizeeIdentityType;}
		if (i == 13){return recognizeeIdentityTypeName;}
		if (i == 14){return recognizeeIdentityId;}
		if (i == 15){return recognizeeSex;}
		if (i == 16){return recognizeeSexName;}
		if (i == 17){return recognizeeBirthday;}
		if (i == 18){return recognizeeAge;}
		if (i == 19){return recognizeeMobile;}
		if (i == 20){return recognizeeTel;}
		if (i == 21){return recognizeeMail;}
		if (i == 22){return recognizeeOccupation1;}
		if (i == 23){return recognizeeOccupation2;}
		if (i == 24){return recognizeeOccupation3;}
		if (i == 25){return recognizeeArea1;}
		if (i == 26){return recognizeeArea2;}
		if (i == 27){return recognizeeAddress;}
		if (i == 28){return recognizeeZipCode;}
		if (i == 29){return recognizeeIsMarry;}
		if (i == 30){return schoolOrCompany;}
		if (i == 31){return outGoingParpose;}
		if (i == 32){return flightNo;}
		if (i == 33){return flightTime;}
		if (i == 34){return flightLocation;}
		if (i == 35){return driverSchoolName;}
		if (i == 36){return driverNo;}
		if (i == 37){return destinationCountry;}
		if (i == 38){return destinationCountryText;}
		if (i == 39){return isSelf;}
		if (i == 40){return height;}
		if (i == 41){return weight;}
		if (i == 42){return overseasOccupation;}
		if (i == 43){return travelType;}
		if (i == 44){return travelMode;}
		if (i == 45){return nationality;}
		if (i == 46){return haveBuy;}
		if (i == 47){return uwCheckFlag;}
		if (i == 48){return remark;}
		if (i == 49){return insuredSn;}
		if (i == 50){return recognizeePrem;}
		if (i == 51){return recognizeeOperate;}
		if (i == 52){return mulInsuredFlag;}
		if (i == 53){return sdinformation_id;}
		if (i == 54){return recognizeeTotalPrem;}
		if (i == 55){return recognizeeArea3;}
		if (i == 56){return recognizeeEndID;}
		if (i == 57){return recognizeeStartID;}
		if (i == 58){return recognizeeKey;}
		if (i == 59){return recognizeeMul;}
		if (i == 60){return socialSecurity;}
		if (i == 61){return discountPrice;}
		if (i == 62){return carPlateNo;}
		if (i == 63){return recognizeeOrigin1;}
		if (i == 64){return recognizeeOrigin2;}
		if (i == 65){return recognizeeOrigin3;}
		if (i == 66){return recognizeeDestination1;}
		if (i == 67){return recognizeeDestination2;}
		if (i == 68){return recognizeeDestination3;}
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
	* 获取字段orderSn的值，该字段的<br>
	* 字段名称 :订单编号<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getorderSn() {
		return orderSn;
	}

	/**
	* 设置字段orderSn的值，该字段的<br>
	* 字段名称 :订单编号<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setorderSn(String orderSn) {
		this.orderSn = orderSn;
    }

	/**
	* 获取字段informationSn的值，该字段的<br>
	* 字段名称 :订单详细编号<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getinformationSn() {
		return informationSn;
	}

	/**
	* 设置字段informationSn的值，该字段的<br>
	* 字段名称 :订单详细编号<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setinformationSn(String informationSn) {
		this.informationSn = informationSn;
    }

	/**
	* 获取字段recognizeeSn的值，该字段的<br>
	* 字段名称 :被保人编号<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeSn() {
		return recognizeeSn;
	}

	/**
	* 设置字段recognizeeSn的值，该字段的<br>
	* 字段名称 :被保人编号<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeSn(String recognizeeSn) {
		this.recognizeeSn = recognizeeSn;
    }

	/**
	* 获取字段recognizeeAppntRelation的值，该字段的<br>
	* 字段名称 :与投保人关系<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getrecognizeeAppntRelation() {
		return recognizeeAppntRelation;
	}

	/**
	* 设置字段recognizeeAppntRelation的值，该字段的<br>
	* 字段名称 :与投保人关系<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setrecognizeeAppntRelation(String recognizeeAppntRelation) {
		this.recognizeeAppntRelation = recognizeeAppntRelation;
    }

	/**
	* 获取字段recognizeeAppntRelationName的值，该字段的<br>
	* 字段名称 :与投保人关系显示<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getrecognizeeAppntRelationName() {
		return recognizeeAppntRelationName;
	}

	/**
	* 设置字段recognizeeAppntRelationName的值，该字段的<br>
	* 字段名称 :与投保人关系显示<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setrecognizeeAppntRelationName(String recognizeeAppntRelationName) {
		this.recognizeeAppntRelationName = recognizeeAppntRelationName;
    }

	/**
	* 获取字段recognizeeName的值，该字段的<br>
	* 字段名称 :被保人姓名<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeName() {
		return recognizeeName;
	}

	/**
	* 设置字段recognizeeName的值，该字段的<br>
	* 字段名称 :被保人姓名<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeName(String recognizeeName) {
		this.recognizeeName = recognizeeName;
    }

	/**
	* 获取字段recognizeeEnName的值，该字段的<br>
	* 字段名称 :被保人英文姓名或拼音<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeEnName() {
		return recognizeeEnName;
	}

	/**
	* 设置字段recognizeeEnName的值，该字段的<br>
	* 字段名称 :被保人英文姓名或拼音<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeEnName(String recognizeeEnName) {
		this.recognizeeEnName = recognizeeEnName;
    }

	/**
	* 获取字段recognizeeLashName的值，该字段的<br>
	* 字段名称 :被保人姓氏拼音<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeLashName() {
		return recognizeeLashName;
	}

	/**
	* 设置字段recognizeeLashName的值，该字段的<br>
	* 字段名称 :被保人姓氏拼音<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeLashName(String recognizeeLashName) {
		this.recognizeeLashName = recognizeeLashName;
    }

	/**
	* 获取字段recognizeeFirstName的值，该字段的<br>
	* 字段名称 :被保人名字拼音<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeFirstName() {
		return recognizeeFirstName;
	}

	/**
	* 设置字段recognizeeFirstName的值，该字段的<br>
	* 字段名称 :被保人名字拼音<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeFirstName(String recognizeeFirstName) {
		this.recognizeeFirstName = recognizeeFirstName;
    }

	/**
	* 获取字段recognizeeIdentityType的值，该字段的<br>
	* 字段名称 :被保人证件类型<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeIdentityType() {
		return recognizeeIdentityType;
	}

	/**
	* 设置字段recognizeeIdentityType的值，该字段的<br>
	* 字段名称 :被保人证件类型<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeIdentityType(String recognizeeIdentityType) {
		this.recognizeeIdentityType = recognizeeIdentityType;
    }

	/**
	* 获取字段recognizeeIdentityTypeName的值，该字段的<br>
	* 字段名称 :被保人证件类型显示<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeIdentityTypeName() {
		return recognizeeIdentityTypeName;
	}

	/**
	* 设置字段recognizeeIdentityTypeName的值，该字段的<br>
	* 字段名称 :被保人证件类型显示<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeIdentityTypeName(String recognizeeIdentityTypeName) {
		this.recognizeeIdentityTypeName = recognizeeIdentityTypeName;
    }

	/**
	* 获取字段recognizeeIdentityId的值，该字段的<br>
	* 字段名称 :被保人证件号码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeIdentityId() {
		return recognizeeIdentityId;
	}

	/**
	* 设置字段recognizeeIdentityId的值，该字段的<br>
	* 字段名称 :被保人证件号码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeIdentityId(String recognizeeIdentityId) {
		this.recognizeeIdentityId = recognizeeIdentityId;
    }

	/**
	* 获取字段recognizeeSex的值，该字段的<br>
	* 字段名称 :被保人性别<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeSex() {
		return recognizeeSex;
	}

	/**
	* 设置字段recognizeeSex的值，该字段的<br>
	* 字段名称 :被保人性别<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeSex(String recognizeeSex) {
		this.recognizeeSex = recognizeeSex;
    }

	/**
	* 获取字段recognizeeSexName的值，该字段的<br>
	* 字段名称 :被保人性别显示<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeSexName() {
		return recognizeeSexName;
	}

	/**
	* 设置字段recognizeeSexName的值，该字段的<br>
	* 字段名称 :被保人性别显示<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeSexName(String recognizeeSexName) {
		this.recognizeeSexName = recognizeeSexName;
    }

	/**
	* 获取字段recognizeeBirthday的值，该字段的<br>
	* 字段名称 :被保人出生日期<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeBirthday() {
		return recognizeeBirthday;
	}

	/**
	* 设置字段recognizeeBirthday的值，该字段的<br>
	* 字段名称 :被保人出生日期<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeBirthday(String recognizeeBirthday) {
		this.recognizeeBirthday = recognizeeBirthday;
    }

	/**
	* 获取字段recognizeeAge的值，该字段的<br>
	* 字段名称 :被保人年龄<br>
	* 数据类型 :varchar(3)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeAge() {
		return recognizeeAge;
	}

	/**
	* 设置字段recognizeeAge的值，该字段的<br>
	* 字段名称 :被保人年龄<br>
	* 数据类型 :varchar(3)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeAge(String recognizeeAge) {
		this.recognizeeAge = recognizeeAge;
    }

	/**
	* 获取字段recognizeeMobile的值，该字段的<br>
	* 字段名称 :被保人手机<br>
	* 数据类型 :varchar(13)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeMobile() {
		return recognizeeMobile;
	}

	/**
	* 设置字段recognizeeMobile的值，该字段的<br>
	* 字段名称 :被保人手机<br>
	* 数据类型 :varchar(13)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeMobile(String recognizeeMobile) {
		this.recognizeeMobile = recognizeeMobile;
    }

	/**
	* 获取字段recognizeeTel的值，该字段的<br>
	* 字段名称 :被保人电话<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeTel() {
		return recognizeeTel;
	}

	/**
	* 设置字段recognizeeTel的值，该字段的<br>
	* 字段名称 :被保人电话<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeTel(String recognizeeTel) {
		this.recognizeeTel = recognizeeTel;
    }

	/**
	* 获取字段recognizeeMail的值，该字段的<br>
	* 字段名称 :被保人电子邮箱<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeMail() {
		return recognizeeMail;
	}

	/**
	* 设置字段recognizeeMail的值，该字段的<br>
	* 字段名称 :被保人电子邮箱<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeMail(String recognizeeMail) {
		this.recognizeeMail = recognizeeMail;
    }

	/**
	* 获取字段recognizeeOccupation1的值，该字段的<br>
	* 字段名称 :职业一级<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeOccupation1() {
		return recognizeeOccupation1;
	}

	/**
	* 设置字段recognizeeOccupation1的值，该字段的<br>
	* 字段名称 :职业一级<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeOccupation1(String recognizeeOccupation1) {
		this.recognizeeOccupation1 = recognizeeOccupation1;
    }

	/**
	* 获取字段recognizeeOccupation2的值，该字段的<br>
	* 字段名称 :职业二级<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeOccupation2() {
		return recognizeeOccupation2;
	}

	/**
	* 设置字段recognizeeOccupation2的值，该字段的<br>
	* 字段名称 :职业二级<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeOccupation2(String recognizeeOccupation2) {
		this.recognizeeOccupation2 = recognizeeOccupation2;
    }

	/**
	* 获取字段recognizeeOccupation3的值，该字段的<br>
	* 字段名称 :职业三级<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeOccupation3() {
		return recognizeeOccupation3;
	}

	/**
	* 设置字段recognizeeOccupation3的值，该字段的<br>
	* 字段名称 :职业三级<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeOccupation3(String recognizeeOccupation3) {
		this.recognizeeOccupation3 = recognizeeOccupation3;
    }

	/**
	* 获取字段recognizeeArea1的值，该字段的<br>
	* 字段名称 :被保人所在地一级<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeArea1() {
		return recognizeeArea1;
	}

	/**
	* 设置字段recognizeeArea1的值，该字段的<br>
	* 字段名称 :被保人所在地一级<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeArea1(String recognizeeArea1) {
		this.recognizeeArea1 = recognizeeArea1;
    }

	/**
	* 获取字段recognizeeArea2的值，该字段的<br>
	* 字段名称 :被保人所在地二级<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeArea2() {
		return recognizeeArea2;
	}

	/**
	* 设置字段recognizeeArea2的值，该字段的<br>
	* 字段名称 :被保人所在地二级<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeArea2(String recognizeeArea2) {
		this.recognizeeArea2 = recognizeeArea2;
    }

	/**
	* 获取字段recognizeeAddress的值，该字段的<br>
	* 字段名称 :被保人地址<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeAddress() {
		return recognizeeAddress;
	}

	/**
	* 设置字段recognizeeAddress的值，该字段的<br>
	* 字段名称 :被保人地址<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeAddress(String recognizeeAddress) {
		this.recognizeeAddress = recognizeeAddress;
    }

	/**
	* 获取字段recognizeeZipCode的值，该字段的<br>
	* 字段名称 :被保人邮政编码<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeZipCode() {
		return recognizeeZipCode;
	}

	/**
	* 设置字段recognizeeZipCode的值，该字段的<br>
	* 字段名称 :被保人邮政编码<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeZipCode(String recognizeeZipCode) {
		this.recognizeeZipCode = recognizeeZipCode;
    }

	/**
	* 获取字段recognizeeIsMarry的值，该字段的<br>
	* 字段名称 :婚否<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeIsMarry() {
		return recognizeeIsMarry;
	}

	/**
	* 设置字段recognizeeIsMarry的值，该字段的<br>
	* 字段名称 :婚否<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeIsMarry(String recognizeeIsMarry) {
		this.recognizeeIsMarry = recognizeeIsMarry;
    }

	/**
	* 获取字段schoolOrCompany的值，该字段的<br>
	* 字段名称 :被保人机构<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	境外留学学校或境外工作公司<br>
	*/
	public String getschoolOrCompany() {
		return schoolOrCompany;
	}

	/**
	* 设置字段schoolOrCompany的值，该字段的<br>
	* 字段名称 :被保人机构<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	境外留学学校或境外工作公司<br>
	*/
	public void setschoolOrCompany(String schoolOrCompany) {
		this.schoolOrCompany = schoolOrCompany;
    }

	/**
	* 获取字段outGoingParpose的值，该字段的<br>
	* 字段名称 :出行目的<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getoutGoingParpose() {
		return outGoingParpose;
	}

	/**
	* 设置字段outGoingParpose的值，该字段的<br>
	* 字段名称 :出行目的<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setoutGoingParpose(String outGoingParpose) {
		this.outGoingParpose = outGoingParpose;
    }

	/**
	* 获取字段flightNo的值，该字段的<br>
	* 字段名称 :航班號<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getflightNo() {
		return flightNo;
	}

	/**
	* 设置字段flightNo的值，该字段的<br>
	* 字段名称 :航班號<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setflightNo(String flightNo) {
		this.flightNo = flightNo;
    }

	/**
	* 获取字段flightTime的值，该字段的<br>
	* 字段名称 :起飛時間<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getflightTime() {
		return flightTime;
	}

	/**
	* 设置字段flightTime的值，该字段的<br>
	* 字段名称 :起飛時間<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setflightTime(Date flightTime) {
		this.flightTime = flightTime;
    }

	/**
	* 获取字段flightLocation的值，该字段的<br>
	* 字段名称 :起飛地点<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getflightLocation() {
		return flightLocation;
	}

	/**
	* 设置字段flightLocation的值，该字段的<br>
	* 字段名称 :起飛地点<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setflightLocation(String flightLocation) {
		this.flightLocation = flightLocation;
    }

	/**
	* 获取字段driverSchoolName的值，该字段的<br>
	* 字段名称 :驾校名称<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getdriverSchoolName() {
		return driverSchoolName;
	}

	/**
	* 设置字段driverSchoolName的值，该字段的<br>
	* 字段名称 :驾校名称<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setdriverSchoolName(String driverSchoolName) {
		this.driverSchoolName = driverSchoolName;
    }

	/**
	* 获取字段driverNo的值，该字段的<br>
	* 字段名称 :学员编号<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getdriverNo() {
		return driverNo;
	}

	/**
	* 设置字段driverNo的值，该字段的<br>
	* 字段名称 :学员编号<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setdriverNo(String driverNo) {
		this.driverNo = driverNo;
    }

	/**
	* 获取字段destinationCountry的值，该字段的<br>
	* 字段名称 :目的地1<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	用于境外产品<br>
	*/
	public String getdestinationCountry() {
		return destinationCountry;
	}

	/**
	* 设置字段destinationCountry的值，该字段的<br>
	* 字段名称 :目的地1<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	用于境外产品<br>
	*/
	public void setdestinationCountry(String destinationCountry) {
		this.destinationCountry = destinationCountry;
    }

	/**
	* 获取字段destinationCountryText的值，该字段的<br>
	* 字段名称 :目的地2<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	用于境外产品文字<br>
	*/
	public String getdestinationCountryText() {
		return destinationCountryText;
	}

	/**
	* 设置字段destinationCountryText的值，该字段的<br>
	* 字段名称 :目的地2<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	用于境外产品文字<br>
	*/
	public void setdestinationCountryText(String destinationCountryText) {
		this.destinationCountryText = destinationCountryText;
    }

	/**
	* 获取字段isSelf的值，该字段的<br>
	* 字段名称 :是否是投保人本人<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	Y=是；N=否。<br>
	*/
	public String getisSelf() {
		return isSelf;
	}

	/**
	* 设置字段isSelf的值，该字段的<br>
	* 字段名称 :是否是投保人本人<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	Y=是；N=否。<br>
	*/
	public void setisSelf(String isSelf) {
		this.isSelf = isSelf;
    }

	/**
	* 获取字段height的值，该字段的<br>
	* 字段名称 :身高<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getheight() {
		return height;
	}

	/**
	* 设置字段height的值，该字段的<br>
	* 字段名称 :身高<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setheight(String height) {
		this.height = height;
    }

	/**
	* 获取字段weight的值，该字段的<br>
	* 字段名称 :体重<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getweight() {
		return weight;
	}

	/**
	* 设置字段weight的值，该字段的<br>
	* 字段名称 :体重<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setweight(String weight) {
		this.weight = weight;
    }

	/**
	* 获取字段overseasOccupation的值，该字段的<br>
	* 字段名称 :境外工作职业<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getoverseasOccupation() {
		return overseasOccupation;
	}

	/**
	* 设置字段overseasOccupation的值，该字段的<br>
	* 字段名称 :境外工作职业<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setoverseasOccupation(String overseasOccupation) {
		this.overseasOccupation = overseasOccupation;
    }

	/**
	* 获取字段travelType的值，该字段的<br>
	* 字段名称 :旅行种类<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String gettravelType() {
		return travelType;
	}

	/**
	* 设置字段travelType的值，该字段的<br>
	* 字段名称 :旅行种类<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void settravelType(String travelType) {
		this.travelType = travelType;
    }

	/**
	* 获取字段travelMode的值，该字段的<br>
	* 字段名称 :旅行方式<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String gettravelMode() {
		return travelMode;
	}

	/**
	* 设置字段travelMode的值，该字段的<br>
	* 字段名称 :旅行方式<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void settravelMode(String travelMode) {
		this.travelMode = travelMode;
    }

	/**
	* 获取字段nationality的值，该字段的<br>
	* 字段名称 :国籍<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getnationality() {
		return nationality;
	}

	/**
	* 设置字段nationality的值，该字段的<br>
	* 字段名称 :国籍<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setnationality(String nationality) {
		this.nationality = nationality;
    }

	/**
	* 获取字段haveBuy的值，该字段的<br>
	* 字段名称 :其他身故保险金额<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	已经或正在购买其他保险公司的身故保险金额<br>
	*/
	public String gethaveBuy() {
		return haveBuy;
	}

	/**
	* 设置字段haveBuy的值，该字段的<br>
	* 字段名称 :其他身故保险金额<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	已经或正在购买其他保险公司的身故保险金额<br>
	*/
	public void sethaveBuy(String haveBuy) {
		this.haveBuy = haveBuy;
    }

	/**
	* 获取字段uwCheckFlag的值，该字段的<br>
	* 字段名称 :核保标记<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	'Y'表示曾经核保通过<br>
	*/
	public String getuwCheckFlag() {
		return uwCheckFlag;
	}

	/**
	* 设置字段uwCheckFlag的值，该字段的<br>
	* 字段名称 :核保标记<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	'Y'表示曾经核保通过<br>
	*/
	public void setuwCheckFlag(String uwCheckFlag) {
		this.uwCheckFlag = uwCheckFlag;
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
	* 获取字段insuredSn的值，该字段的<br>
	* 字段名称 :被保人流水号<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getinsuredSn() {
		return insuredSn;
	}

	/**
	* 设置字段insuredSn的值，该字段的<br>
	* 字段名称 :被保人流水号<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setinsuredSn(String insuredSn) {
		this.insuredSn = insuredSn;
    }

	/**
	* 获取字段recognizeePrem的值，该字段的<br>
	* 字段名称 :被保人保费<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeePrem() {
		return recognizeePrem;
	}

	/**
	* 设置字段recognizeePrem的值，该字段的<br>
	* 字段名称 :被保人保费<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeePrem(String recognizeePrem) {
		this.recognizeePrem = recognizeePrem;
    }

	/**
	* 获取字段recognizeeOperate的值，该字段的<br>
	* 字段名称 :操作标示<br>
	* 数据类型 :varchar(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeOperate() {
		return recognizeeOperate;
	}

	/**
	* 设置字段recognizeeOperate的值，该字段的<br>
	* 字段名称 :操作标示<br>
	* 数据类型 :varchar(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeOperate(String recognizeeOperate) {
		this.recognizeeOperate = recognizeeOperate;
    }

	/**
	* 获取字段mulInsuredFlag的值，该字段的<br>
	* 字段名称 :操作标示2<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getmulInsuredFlag() {
		return mulInsuredFlag;
	}

	/**
	* 设置字段mulInsuredFlag的值，该字段的<br>
	* 字段名称 :操作标示2<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmulInsuredFlag(String mulInsuredFlag) {
		this.mulInsuredFlag = mulInsuredFlag;
    }

	/**
	* 获取字段sdinformation_id的值，该字段的<br>
	* 字段名称 :sdinformation_id<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getsdinformation_id() {
		return sdinformation_id;
	}

	/**
	* 设置字段sdinformation_id的值，该字段的<br>
	* 字段名称 :sdinformation_id<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setsdinformation_id(String sdinformation_id) {
		this.sdinformation_id = sdinformation_id;
    }

	/**
	* 获取字段recognizeeTotalPrem的值，该字段的<br>
	* 字段名称 :原保费<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeTotalPrem() {
		return recognizeeTotalPrem;
	}

	/**
	* 设置字段recognizeeTotalPrem的值，该字段的<br>
	* 字段名称 :原保费<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeTotalPrem(String recognizeeTotalPrem) {
		this.recognizeeTotalPrem = recognizeeTotalPrem;
    }

	/**
	* 获取字段recognizeeArea3的值，该字段的<br>
	* 字段名称 :recognizeeArea3<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeArea3() {
		return recognizeeArea3;
	}

	/**
	* 设置字段recognizeeArea3的值，该字段的<br>
	* 字段名称 :recognizeeArea3<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeArea3(String recognizeeArea3) {
		this.recognizeeArea3 = recognizeeArea3;
    }

	/**
	* 获取字段recognizeeEndID的值，该字段的<br>
	* 字段名称 :recognizeeEndID<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeEndID() {
		return recognizeeEndID;
	}

	/**
	* 设置字段recognizeeEndID的值，该字段的<br>
	* 字段名称 :recognizeeEndID<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeEndID(String recognizeeEndID) {
		this.recognizeeEndID = recognizeeEndID;
    }

	/**
	* 获取字段recognizeeStartID的值，该字段的<br>
	* 字段名称 :recognizeeStartID<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeStartID() {
		return recognizeeStartID;
	}

	/**
	* 设置字段recognizeeStartID的值，该字段的<br>
	* 字段名称 :recognizeeStartID<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeStartID(String recognizeeStartID) {
		this.recognizeeStartID = recognizeeStartID;
    }

	/**
	* 获取字段recognizeeKey的值，该字段的<br>
	* 字段名称 :同一被保人标志--多份<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeKey() {
		return recognizeeKey;
	}

	/**
	* 设置字段recognizeeKey的值，该字段的<br>
	* 字段名称 :同一被保人标志--多份<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeKey(String recognizeeKey) {
		this.recognizeeKey = recognizeeKey;
    }

	/**
	* 获取字段recognizeeMul的值，该字段的<br>
	* 字段名称 :被保人购买份数<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeMul() {
		return recognizeeMul;
	}

	/**
	* 设置字段recognizeeMul的值，该字段的<br>
	* 字段名称 :被保人购买份数<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeMul(String recognizeeMul) {
		this.recognizeeMul = recognizeeMul;
    }

	/**
	* 获取字段socialSecurity的值，该字段的<br>
	* 字段名称 :是否有医保<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getsocialSecurity() {
		return socialSecurity;
	}

	/**
	* 设置字段socialSecurity的值，该字段的<br>
	* 字段名称 :是否有医保<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setsocialSecurity(String socialSecurity) {
		this.socialSecurity = socialSecurity;
    }

	/**
	* 获取字段discountPrice的值，该字段的<br>
	* 字段名称 :被保人保费折后价<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getdiscountPrice() {
		return discountPrice;
	}

	/**
	* 设置字段discountPrice的值，该字段的<br>
	* 字段名称 :被保人保费折后价<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setdiscountPrice(String discountPrice) {
		this.discountPrice = discountPrice;
    }

	/**
	* 获取字段carPlateNo的值，该字段的<br>
	* 字段名称 :车牌号<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcarPlateNo() {
		return carPlateNo;
	}

	/**
	* 设置字段carPlateNo的值，该字段的<br>
	* 字段名称 :车牌号<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcarPlateNo(String carPlateNo) {
		this.carPlateNo = carPlateNo;
    }
	
	/**
	* 获取字段recognizeeOrigin1的值，该字段的<br>
	* 字段名称 :被保人出发地一级<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getRecognizeeOrigin1() {
		return recognizeeOrigin1;
	}

	/**
	* 设置字段recognizeeOrigin1的值，该字段的<br>
	* 字段名称 :被保人出发地一级<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRecognizeeOrigin1(String recognizeeOrigin1) {
		this.recognizeeOrigin1 = recognizeeOrigin1;
	}

	/**
	* 获取字段recognizeeOrigin2的值，该字段的<br>
	* 字段名称 :被保人出发地二级<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getRecognizeeOrigin2() {
		return recognizeeOrigin2;
	}

	/**
	* 设置字段recognizeeOrigin2的值，该字段的<br>
	* 字段名称 :被保人出发地二级<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRecognizeeOrigin2(String recognizeeOrigin2) {
		this.recognizeeOrigin2 = recognizeeOrigin2;
	}

	/**
	* 获取字段recognizeeOrigin3的值，该字段的<br>
	* 字段名称 :被保人出发地三级<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getRecognizeeOrigin3() {
		return recognizeeOrigin3;
	}

	/**
	* 设置字段recognizeeOrigin3的值，该字段的<br>
	* 字段名称 :被保人出发地三级<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRecognizeeOrigin3(String recognizeeOrigin3) {
		this.recognizeeOrigin3 = recognizeeOrigin3;
	}

	/**
	* 获取字段recognizeeDestination1的值，该字段的<br>
	* 字段名称 :被保人目的地一级<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getRecognizeeDestination1() {
		return recognizeeDestination1;
	}

	/**
	* 设置字段recognizeeDestination1的值，该字段的<br>
	* 字段名称 :被保人目的地一级<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRecognizeeDestination1(String recognizeeDestination1) {
		this.recognizeeDestination1 = recognizeeDestination1;
	}

	/**
	* 获取字段recognizeeDestination2的值，该字段的<br>
	* 字段名称 :被保人目的地二级<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getRecognizeeDestination2() {
		return recognizeeDestination2;
	}

	/**
	* 设置字段recognizeeDestination2的值，该字段的<br>
	* 字段名称 :被保人目的地二级<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRecognizeeDestination2(String recognizeeDestination2) {
		this.recognizeeDestination2 = recognizeeDestination2;
	}

	/**
	* 获取字段recognizeeDestination3的值，该字段的<br>
	* 字段名称 :被保人目的地三级<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getRecognizeeDestination3() {
		return recognizeeDestination3;
	}

	/**
	* 获取字段recognizeeDestination3的值，该字段的<br>
	* 字段名称 :被保人目的地三级<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRecognizeeDestination3(String recognizeeDestination3) {
		this.recognizeeDestination3 = recognizeeDestination3;
	}

}