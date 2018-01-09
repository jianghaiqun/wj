package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

import java.util.Date;

/**
 * 表名称：InformationInsured<br>
 * 表代码：InformationInsured<br>
 * 表主键：id<br>
 */
public class InformationInsuredSchema extends Schema {
	private String id;

	private Date createDate;

	private Date modifyDate;

	private String occupation1;

	private String occupation2;

	private String occupation3;

	private String recognizeeAppntRelation;

	private String recognizeeArea;

	private String recognizeeBirthday;

	private String recognizeeEnName;

	private String recognizeeIdentityId;

	private String recognizeeIdentityType;

	private String recognizeeIsMarry;

	private String recognizeeMail;

	private String recognizeeName;

	private String recognizeeSex;

	private String recognizeeTel;

	private String recognizeeZipCode;

	private String information_id;

	private String outGoingParpose;

	private String recognizeeFirstEnName;

	private String recognizeeLastEnName;

	private String recognizeeMobile;

	private String schoolOrCompany;

	private String occupation;

	private String recognizeeAddress;

	private String recognizeeLiveAddress;

	private String recognizeeArea1;

	private String recognizeeArea2;

	private String flightNo;

	private Date flightTime;

	private String recognizeeAppntRelationName;

	private String recognizeeIdentityTypeName;

	private String recognizeeSexName;

	private String destinationCountry;

	private String driverNo;

	private String driverSchoolName;

	private String overseasOccupation;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("createDate", DataColumn.DATETIME, 1, 0 , 0 , false , false),
		new SchemaColumn("modifyDate", DataColumn.DATETIME, 2, 0 , 0 , false , false),
		new SchemaColumn("occupation1", DataColumn.STRING, 3, 255 , 0 , false , false),
		new SchemaColumn("occupation2", DataColumn.STRING, 4, 255 , 0 , false , false),
		new SchemaColumn("occupation3", DataColumn.STRING, 5, 255 , 0 , false , false),
		new SchemaColumn("recognizeeAppntRelation", DataColumn.STRING, 6, 255 , 0 , false , false),
		new SchemaColumn("recognizeeArea", DataColumn.STRING, 7, 255 , 0 , false , false),
		new SchemaColumn("recognizeeBirthday", DataColumn.STRING, 8, 255 , 0 , false , false),
		new SchemaColumn("recognizeeEnName", DataColumn.STRING, 9, 255 , 0 , false , false),
		new SchemaColumn("recognizeeIdentityId", DataColumn.STRING, 10, 255 , 0 , false , false),
		new SchemaColumn("recognizeeIdentityType", DataColumn.STRING, 11, 255 , 0 , false , false),
		new SchemaColumn("recognizeeIsMarry", DataColumn.STRING, 12, 255 , 0 , false , false),
		new SchemaColumn("recognizeeMail", DataColumn.STRING, 13, 255 , 0 , false , false),
		new SchemaColumn("recognizeeName", DataColumn.STRING, 14, 255 , 0 , false , false),
		new SchemaColumn("recognizeeSex", DataColumn.STRING, 15, 255 , 0 , false , false),
		new SchemaColumn("recognizeeTel", DataColumn.STRING, 16, 255 , 0 , false , false),
		new SchemaColumn("recognizeeZipCode", DataColumn.STRING, 17, 255 , 0 , false , false),
		new SchemaColumn("information_id", DataColumn.STRING, 18, 32 , 0 , false , false),
		new SchemaColumn("outGoingParpose", DataColumn.STRING, 19, 255 , 0 , false , false),
		new SchemaColumn("recognizeeFirstEnName", DataColumn.STRING, 20, 255 , 0 , false , false),
		new SchemaColumn("recognizeeLastEnName", DataColumn.STRING, 21, 255 , 0 , false , false),
		new SchemaColumn("recognizeeMobile", DataColumn.STRING, 22, 255 , 0 , false , false),
		new SchemaColumn("schoolOrCompany", DataColumn.STRING, 23, 255 , 0 , false , false),
		new SchemaColumn("occupation", DataColumn.STRING, 24, 255 , 0 , false , false),
		new SchemaColumn("recognizeeAddress", DataColumn.STRING, 25, 255 , 0 , false , false),
		new SchemaColumn("recognizeeLiveAddress", DataColumn.STRING, 26, 255 , 0 , false , false),
		new SchemaColumn("recognizeeArea1", DataColumn.STRING, 27, 255 , 0 , false , false),
		new SchemaColumn("recognizeeArea2", DataColumn.STRING, 28, 255 , 0 , false , false),
		new SchemaColumn("flightNo", DataColumn.STRING, 29, 255 , 0 , false , false),
		new SchemaColumn("flightTime", DataColumn.DATETIME, 30, 0 , 0 , false , false),
		new SchemaColumn("recognizeeAppntRelationName", DataColumn.STRING, 31, 255 , 0 , false , false),
		new SchemaColumn("recognizeeIdentityTypeName", DataColumn.STRING, 32, 255 , 0 , false , false),
		new SchemaColumn("recognizeeSexName", DataColumn.STRING, 33, 255 , 0 , false , false),
		new SchemaColumn("destinationCountry", DataColumn.STRING, 34, 255 , 0 , false , false),
		new SchemaColumn("driverNo", DataColumn.STRING, 35, 255 , 0 , false , false),
		new SchemaColumn("driverSchoolName", DataColumn.STRING, 36, 255 , 0 , false , false),
		new SchemaColumn("overseasOccupation", DataColumn.STRING, 37, 255 , 0 , false , false)
	};

	public static final String _TableCode = "InformationInsured";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into InformationInsured values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update InformationInsured set id=?,createDate=?,modifyDate=?,occupation1=?,occupation2=?,occupation3=?,recognizeeAppntRelation=?,recognizeeArea=?,recognizeeBirthday=?,recognizeeEnName=?,recognizeeIdentityId=?,recognizeeIdentityType=?,recognizeeIsMarry=?,recognizeeMail=?,recognizeeName=?,recognizeeSex=?,recognizeeTel=?,recognizeeZipCode=?,information_id=?,outGoingParpose=?,recognizeeFirstEnName=?,recognizeeLastEnName=?,recognizeeMobile=?,schoolOrCompany=?,occupation=?,recognizeeAddress=?,recognizeeLiveAddress=?,recognizeeArea1=?,recognizeeArea2=?,flightNo=?,flightTime=?,recognizeeAppntRelationName=?,recognizeeIdentityTypeName=?,recognizeeSexName=?,destinationCountry=?,driverNo=?,driverSchoolName=?,overseasOccupation=? where id=?";

	protected static final String _DeleteSQL = "delete from InformationInsured  where id=?";

	protected static final String _FillAllSQL = "select * from InformationInsured  where id=?";

	public InformationInsuredSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[38];
	}

	protected Schema newInstance(){
		return new InformationInsuredSchema();
	}

	protected SchemaSet newSet(){
		return new InformationInsuredSet();
	}

	public InformationInsuredSet query() {
		return query(null, -1, -1);
	}

	public InformationInsuredSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public InformationInsuredSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public InformationInsuredSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (InformationInsuredSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){createDate = (Date)v;return;}
		if (i == 2){modifyDate = (Date)v;return;}
		if (i == 3){occupation1 = (String)v;return;}
		if (i == 4){occupation2 = (String)v;return;}
		if (i == 5){occupation3 = (String)v;return;}
		if (i == 6){recognizeeAppntRelation = (String)v;return;}
		if (i == 7){recognizeeArea = (String)v;return;}
		if (i == 8){recognizeeBirthday = (String)v;return;}
		if (i == 9){recognizeeEnName = (String)v;return;}
		if (i == 10){recognizeeIdentityId = (String)v;return;}
		if (i == 11){recognizeeIdentityType = (String)v;return;}
		if (i == 12){recognizeeIsMarry = (String)v;return;}
		if (i == 13){recognizeeMail = (String)v;return;}
		if (i == 14){recognizeeName = (String)v;return;}
		if (i == 15){recognizeeSex = (String)v;return;}
		if (i == 16){recognizeeTel = (String)v;return;}
		if (i == 17){recognizeeZipCode = (String)v;return;}
		if (i == 18){information_id = (String)v;return;}
		if (i == 19){outGoingParpose = (String)v;return;}
		if (i == 20){recognizeeFirstEnName = (String)v;return;}
		if (i == 21){recognizeeLastEnName = (String)v;return;}
		if (i == 22){recognizeeMobile = (String)v;return;}
		if (i == 23){schoolOrCompany = (String)v;return;}
		if (i == 24){occupation = (String)v;return;}
		if (i == 25){recognizeeAddress = (String)v;return;}
		if (i == 26){recognizeeLiveAddress = (String)v;return;}
		if (i == 27){recognizeeArea1 = (String)v;return;}
		if (i == 28){recognizeeArea2 = (String)v;return;}
		if (i == 29){flightNo = (String)v;return;}
		if (i == 30){flightTime = (Date)v;return;}
		if (i == 31){recognizeeAppntRelationName = (String)v;return;}
		if (i == 32){recognizeeIdentityTypeName = (String)v;return;}
		if (i == 33){recognizeeSexName = (String)v;return;}
		if (i == 34){destinationCountry = (String)v;return;}
		if (i == 35){driverNo = (String)v;return;}
		if (i == 36){driverSchoolName = (String)v;return;}
		if (i == 37){overseasOccupation = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return createDate;}
		if (i == 2){return modifyDate;}
		if (i == 3){return occupation1;}
		if (i == 4){return occupation2;}
		if (i == 5){return occupation3;}
		if (i == 6){return recognizeeAppntRelation;}
		if (i == 7){return recognizeeArea;}
		if (i == 8){return recognizeeBirthday;}
		if (i == 9){return recognizeeEnName;}
		if (i == 10){return recognizeeIdentityId;}
		if (i == 11){return recognizeeIdentityType;}
		if (i == 12){return recognizeeIsMarry;}
		if (i == 13){return recognizeeMail;}
		if (i == 14){return recognizeeName;}
		if (i == 15){return recognizeeSex;}
		if (i == 16){return recognizeeTel;}
		if (i == 17){return recognizeeZipCode;}
		if (i == 18){return information_id;}
		if (i == 19){return outGoingParpose;}
		if (i == 20){return recognizeeFirstEnName;}
		if (i == 21){return recognizeeLastEnName;}
		if (i == 22){return recognizeeMobile;}
		if (i == 23){return schoolOrCompany;}
		if (i == 24){return occupation;}
		if (i == 25){return recognizeeAddress;}
		if (i == 26){return recognizeeLiveAddress;}
		if (i == 27){return recognizeeArea1;}
		if (i == 28){return recognizeeArea2;}
		if (i == 29){return flightNo;}
		if (i == 30){return flightTime;}
		if (i == 31){return recognizeeAppntRelationName;}
		if (i == 32){return recognizeeIdentityTypeName;}
		if (i == 33){return recognizeeSexName;}
		if (i == 34){return destinationCountry;}
		if (i == 35){return driverNo;}
		if (i == 36){return driverSchoolName;}
		if (i == 37){return overseasOccupation;}
		return null;
	}

	/**
	* 获取字段id的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getid() {
		return id;
	}

	/**
	* 设置字段id的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setid(String id) {
		this.id = id;
    }

	/**
	* 获取字段createDate的值，该字段的<br>
	* 字段名称 :createDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getcreateDate() {
		return createDate;
	}

	/**
	* 设置字段createDate的值，该字段的<br>
	* 字段名称 :createDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcreateDate(Date createDate) {
		this.createDate = createDate;
    }

	/**
	* 获取字段modifyDate的值，该字段的<br>
	* 字段名称 :modifyDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getmodifyDate() {
		return modifyDate;
	}

	/**
	* 设置字段modifyDate的值，该字段的<br>
	* 字段名称 :modifyDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmodifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
    }

	/**
	* 获取字段occupation1的值，该字段的<br>
	* 字段名称 :occupation1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getoccupation1() {
		return occupation1;
	}

	/**
	* 设置字段occupation1的值，该字段的<br>
	* 字段名称 :occupation1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setoccupation1(String occupation1) {
		this.occupation1 = occupation1;
    }

	/**
	* 获取字段occupation2的值，该字段的<br>
	* 字段名称 :occupation2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getoccupation2() {
		return occupation2;
	}

	/**
	* 设置字段occupation2的值，该字段的<br>
	* 字段名称 :occupation2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setoccupation2(String occupation2) {
		this.occupation2 = occupation2;
    }

	/**
	* 获取字段occupation3的值，该字段的<br>
	* 字段名称 :occupation3<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getoccupation3() {
		return occupation3;
	}

	/**
	* 设置字段occupation3的值，该字段的<br>
	* 字段名称 :occupation3<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setoccupation3(String occupation3) {
		this.occupation3 = occupation3;
    }

	/**
	* 获取字段recognizeeAppntRelation的值，该字段的<br>
	* 字段名称 :recognizeeAppntRelation<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeAppntRelation() {
		return recognizeeAppntRelation;
	}

	/**
	* 设置字段recognizeeAppntRelation的值，该字段的<br>
	* 字段名称 :recognizeeAppntRelation<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeAppntRelation(String recognizeeAppntRelation) {
		this.recognizeeAppntRelation = recognizeeAppntRelation;
    }

	/**
	* 获取字段recognizeeArea的值，该字段的<br>
	* 字段名称 :recognizeeArea<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeArea() {
		return recognizeeArea;
	}

	/**
	* 设置字段recognizeeArea的值，该字段的<br>
	* 字段名称 :recognizeeArea<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeArea(String recognizeeArea) {
		this.recognizeeArea = recognizeeArea;
    }

	/**
	* 获取字段recognizeeBirthday的值，该字段的<br>
	* 字段名称 :recognizeeBirthday<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeBirthday() {
		return recognizeeBirthday;
	}

	/**
	* 设置字段recognizeeBirthday的值，该字段的<br>
	* 字段名称 :recognizeeBirthday<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeBirthday(String recognizeeBirthday) {
		this.recognizeeBirthday = recognizeeBirthday;
    }

	/**
	* 获取字段recognizeeEnName的值，该字段的<br>
	* 字段名称 :recognizeeEnName<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeEnName() {
		return recognizeeEnName;
	}

	/**
	* 设置字段recognizeeEnName的值，该字段的<br>
	* 字段名称 :recognizeeEnName<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeEnName(String recognizeeEnName) {
		this.recognizeeEnName = recognizeeEnName;
    }

	/**
	* 获取字段recognizeeIdentityId的值，该字段的<br>
	* 字段名称 :recognizeeIdentityId<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeIdentityId() {
		return recognizeeIdentityId;
	}

	/**
	* 设置字段recognizeeIdentityId的值，该字段的<br>
	* 字段名称 :recognizeeIdentityId<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeIdentityId(String recognizeeIdentityId) {
		this.recognizeeIdentityId = recognizeeIdentityId;
    }

	/**
	* 获取字段recognizeeIdentityType的值，该字段的<br>
	* 字段名称 :recognizeeIdentityType<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeIdentityType() {
		return recognizeeIdentityType;
	}

	/**
	* 设置字段recognizeeIdentityType的值，该字段的<br>
	* 字段名称 :recognizeeIdentityType<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeIdentityType(String recognizeeIdentityType) {
		this.recognizeeIdentityType = recognizeeIdentityType;
    }

	/**
	* 获取字段recognizeeIsMarry的值，该字段的<br>
	* 字段名称 :recognizeeIsMarry<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeIsMarry() {
		return recognizeeIsMarry;
	}

	/**
	* 设置字段recognizeeIsMarry的值，该字段的<br>
	* 字段名称 :recognizeeIsMarry<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeIsMarry(String recognizeeIsMarry) {
		this.recognizeeIsMarry = recognizeeIsMarry;
    }

	/**
	* 获取字段recognizeeMail的值，该字段的<br>
	* 字段名称 :recognizeeMail<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeMail() {
		return recognizeeMail;
	}

	/**
	* 设置字段recognizeeMail的值，该字段的<br>
	* 字段名称 :recognizeeMail<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeMail(String recognizeeMail) {
		this.recognizeeMail = recognizeeMail;
    }

	/**
	* 获取字段recognizeeName的值，该字段的<br>
	* 字段名称 :recognizeeName<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeName() {
		return recognizeeName;
	}

	/**
	* 设置字段recognizeeName的值，该字段的<br>
	* 字段名称 :recognizeeName<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeName(String recognizeeName) {
		this.recognizeeName = recognizeeName;
    }

	/**
	* 获取字段recognizeeSex的值，该字段的<br>
	* 字段名称 :recognizeeSex<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeSex() {
		return recognizeeSex;
	}

	/**
	* 设置字段recognizeeSex的值，该字段的<br>
	* 字段名称 :recognizeeSex<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeSex(String recognizeeSex) {
		this.recognizeeSex = recognizeeSex;
    }

	/**
	* 获取字段recognizeeTel的值，该字段的<br>
	* 字段名称 :recognizeeTel<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeTel() {
		return recognizeeTel;
	}

	/**
	* 设置字段recognizeeTel的值，该字段的<br>
	* 字段名称 :recognizeeTel<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeTel(String recognizeeTel) {
		this.recognizeeTel = recognizeeTel;
    }

	/**
	* 获取字段recognizeeZipCode的值，该字段的<br>
	* 字段名称 :recognizeeZipCode<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeZipCode() {
		return recognizeeZipCode;
	}

	/**
	* 设置字段recognizeeZipCode的值，该字段的<br>
	* 字段名称 :recognizeeZipCode<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeZipCode(String recognizeeZipCode) {
		this.recognizeeZipCode = recognizeeZipCode;
    }

	/**
	* 获取字段information_id的值，该字段的<br>
	* 字段名称 :information_id<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getinformation_id() {
		return information_id;
	}

	/**
	* 设置字段information_id的值，该字段的<br>
	* 字段名称 :information_id<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setinformation_id(String information_id) {
		this.information_id = information_id;
    }

	/**
	* 获取字段outGoingParpose的值，该字段的<br>
	* 字段名称 :outGoingParpose<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getoutGoingParpose() {
		return outGoingParpose;
	}

	/**
	* 设置字段outGoingParpose的值，该字段的<br>
	* 字段名称 :outGoingParpose<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setoutGoingParpose(String outGoingParpose) {
		this.outGoingParpose = outGoingParpose;
    }

	/**
	* 获取字段recognizeeFirstEnName的值，该字段的<br>
	* 字段名称 :recognizeeFirstEnName<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeFirstEnName() {
		return recognizeeFirstEnName;
	}

	/**
	* 设置字段recognizeeFirstEnName的值，该字段的<br>
	* 字段名称 :recognizeeFirstEnName<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeFirstEnName(String recognizeeFirstEnName) {
		this.recognizeeFirstEnName = recognizeeFirstEnName;
    }

	/**
	* 获取字段recognizeeLastEnName的值，该字段的<br>
	* 字段名称 :recognizeeLastEnName<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeLastEnName() {
		return recognizeeLastEnName;
	}

	/**
	* 设置字段recognizeeLastEnName的值，该字段的<br>
	* 字段名称 :recognizeeLastEnName<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeLastEnName(String recognizeeLastEnName) {
		this.recognizeeLastEnName = recognizeeLastEnName;
    }

	/**
	* 获取字段recognizeeMobile的值，该字段的<br>
	* 字段名称 :recognizeeMobile<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeMobile() {
		return recognizeeMobile;
	}

	/**
	* 设置字段recognizeeMobile的值，该字段的<br>
	* 字段名称 :recognizeeMobile<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeMobile(String recognizeeMobile) {
		this.recognizeeMobile = recognizeeMobile;
    }

	/**
	* 获取字段schoolOrCompany的值，该字段的<br>
	* 字段名称 :schoolOrCompany<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getschoolOrCompany() {
		return schoolOrCompany;
	}

	/**
	* 设置字段schoolOrCompany的值，该字段的<br>
	* 字段名称 :schoolOrCompany<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setschoolOrCompany(String schoolOrCompany) {
		this.schoolOrCompany = schoolOrCompany;
    }

	/**
	* 获取字段occupation的值，该字段的<br>
	* 字段名称 :occupation<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getoccupation() {
		return occupation;
	}

	/**
	* 设置字段occupation的值，该字段的<br>
	* 字段名称 :occupation<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setoccupation(String occupation) {
		this.occupation = occupation;
    }

	/**
	* 获取字段recognizeeAddress的值，该字段的<br>
	* 字段名称 :recognizeeAddress<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeAddress() {
		return recognizeeAddress;
	}

	/**
	* 设置字段recognizeeAddress的值，该字段的<br>
	* 字段名称 :recognizeeAddress<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeAddress(String recognizeeAddress) {
		this.recognizeeAddress = recognizeeAddress;
    }

	/**
	* 获取字段recognizeeLiveAddress的值，该字段的<br>
	* 字段名称 :recognizeeLiveAddress<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeLiveAddress() {
		return recognizeeLiveAddress;
	}

	/**
	* 设置字段recognizeeLiveAddress的值，该字段的<br>
	* 字段名称 :recognizeeLiveAddress<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeLiveAddress(String recognizeeLiveAddress) {
		this.recognizeeLiveAddress = recognizeeLiveAddress;
    }

	/**
	* 获取字段recognizeeArea1的值，该字段的<br>
	* 字段名称 :recognizeeArea1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeArea1() {
		return recognizeeArea1;
	}

	/**
	* 设置字段recognizeeArea1的值，该字段的<br>
	* 字段名称 :recognizeeArea1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeArea1(String recognizeeArea1) {
		this.recognizeeArea1 = recognizeeArea1;
    }

	/**
	* 获取字段recognizeeArea2的值，该字段的<br>
	* 字段名称 :recognizeeArea2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeArea2() {
		return recognizeeArea2;
	}

	/**
	* 设置字段recognizeeArea2的值，该字段的<br>
	* 字段名称 :recognizeeArea2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeArea2(String recognizeeArea2) {
		this.recognizeeArea2 = recognizeeArea2;
    }

	/**
	* 获取字段flightNo的值，该字段的<br>
	* 字段名称 :flightNo<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getflightNo() {
		return flightNo;
	}

	/**
	* 设置字段flightNo的值，该字段的<br>
	* 字段名称 :flightNo<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setflightNo(String flightNo) {
		this.flightNo = flightNo;
    }

	/**
	* 获取字段flightTime的值，该字段的<br>
	* 字段名称 :flightTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getflightTime() {
		return flightTime;
	}

	/**
	* 设置字段flightTime的值，该字段的<br>
	* 字段名称 :flightTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setflightTime(Date flightTime) {
		this.flightTime = flightTime;
    }

	/**
	* 获取字段recognizeeAppntRelationName的值，该字段的<br>
	* 字段名称 :recognizeeAppntRelationName<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeAppntRelationName() {
		return recognizeeAppntRelationName;
	}

	/**
	* 设置字段recognizeeAppntRelationName的值，该字段的<br>
	* 字段名称 :recognizeeAppntRelationName<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeAppntRelationName(String recognizeeAppntRelationName) {
		this.recognizeeAppntRelationName = recognizeeAppntRelationName;
    }

	/**
	* 获取字段recognizeeIdentityTypeName的值，该字段的<br>
	* 字段名称 :recognizeeIdentityTypeName<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeIdentityTypeName() {
		return recognizeeIdentityTypeName;
	}

	/**
	* 设置字段recognizeeIdentityTypeName的值，该字段的<br>
	* 字段名称 :recognizeeIdentityTypeName<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeIdentityTypeName(String recognizeeIdentityTypeName) {
		this.recognizeeIdentityTypeName = recognizeeIdentityTypeName;
    }

	/**
	* 获取字段recognizeeSexName的值，该字段的<br>
	* 字段名称 :recognizeeSexName<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeSexName() {
		return recognizeeSexName;
	}

	/**
	* 设置字段recognizeeSexName的值，该字段的<br>
	* 字段名称 :recognizeeSexName<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeSexName(String recognizeeSexName) {
		this.recognizeeSexName = recognizeeSexName;
    }

	/**
	* 获取字段destinationCountry的值，该字段的<br>
	* 字段名称 :destinationCountry<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getdestinationCountry() {
		return destinationCountry;
	}

	/**
	* 设置字段destinationCountry的值，该字段的<br>
	* 字段名称 :destinationCountry<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setdestinationCountry(String destinationCountry) {
		this.destinationCountry = destinationCountry;
    }

	/**
	* 获取字段driverNo的值，该字段的<br>
	* 字段名称 :driverNo<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getdriverNo() {
		return driverNo;
	}

	/**
	* 设置字段driverNo的值，该字段的<br>
	* 字段名称 :driverNo<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setdriverNo(String driverNo) {
		this.driverNo = driverNo;
    }

	/**
	* 获取字段driverSchoolName的值，该字段的<br>
	* 字段名称 :driverSchoolName<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getdriverSchoolName() {
		return driverSchoolName;
	}

	/**
	* 设置字段driverSchoolName的值，该字段的<br>
	* 字段名称 :driverSchoolName<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setdriverSchoolName(String driverSchoolName) {
		this.driverSchoolName = driverSchoolName;
    }

	/**
	* 获取字段overseasOccupation的值，该字段的<br>
	* 字段名称 :overseasOccupation<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getoverseasOccupation() {
		return overseasOccupation;
	}

	/**
	* 设置字段overseasOccupation的值，该字段的<br>
	* 字段名称 :overseasOccupation<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setoverseasOccupation(String overseasOccupation) {
		this.overseasOccupation = overseasOccupation;
    }

}