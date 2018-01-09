package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：InformationAppnt<br>
 * 表代码：InformationAppnt<br>
 * 表主键：id<br>
 */
public class InformationAppntSchema extends Schema {
	private String id;

	private Date createDate;

	private Date modifyDate;

	private String applicantArea;

	private String applicantBirthday;

	private String applicantHomePhone;

	private String applicantIdentityId;

	private String applicantIdentityType;

	private String applicantMail;

	private String applicantMobile;

	private String applicantMobile2;

	private String applicantName;

	private String applicantSex;

	private String applicantTel;

	private String applicantZipCode;

	private String information_id;

	private String applicantAddress;

	private String applicantEnName;

	private String applicantFirstEnName;

	private String applicantLastEnName;

	private String applicantLiveAddress;

	private String occupation;

	private String applicantArea1;

	private String applicantArea2;

	private String applicantOccupation1;

	private String applicantOccupation2;

	private String applicantOccupation3;

	private String applicantIdentityTypeName;

	private String applicantSexName;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("createDate", DataColumn.DATETIME, 1, 0 , 0 , false , false),
		new SchemaColumn("modifyDate", DataColumn.DATETIME, 2, 0 , 0 , false , false),
		new SchemaColumn("applicantArea", DataColumn.STRING, 3, 255 , 0 , false , false),
		new SchemaColumn("applicantBirthday", DataColumn.STRING, 4, 255 , 0 , false , false),
		new SchemaColumn("applicantHomePhone", DataColumn.STRING, 5, 255 , 0 , false , false),
		new SchemaColumn("applicantIdentityId", DataColumn.STRING, 6, 255 , 0 , false , false),
		new SchemaColumn("applicantIdentityType", DataColumn.STRING, 7, 255 , 0 , false , false),
		new SchemaColumn("applicantMail", DataColumn.STRING, 8, 255 , 0 , false , false),
		new SchemaColumn("applicantMobile", DataColumn.STRING, 9, 255 , 0 , false , false),
		new SchemaColumn("applicantMobile2", DataColumn.STRING, 10, 255 , 0 , false , false),
		new SchemaColumn("applicantName", DataColumn.STRING, 11, 255 , 0 , false , false),
		new SchemaColumn("applicantSex", DataColumn.STRING, 12, 255 , 0 , false , false),
		new SchemaColumn("applicantTel", DataColumn.STRING, 13, 255 , 0 , false , false),
		new SchemaColumn("applicantZipCode", DataColumn.STRING, 14, 255 , 0 , false , false),
		new SchemaColumn("information_id", DataColumn.STRING, 15, 32 , 0 , false , false),
		new SchemaColumn("applicantAddress", DataColumn.STRING, 16, 255 , 0 , false , false),
		new SchemaColumn("applicantEnName", DataColumn.STRING, 17, 255 , 0 , false , false),
		new SchemaColumn("applicantFirstEnName", DataColumn.STRING, 18, 255 , 0 , false , false),
		new SchemaColumn("applicantLastEnName", DataColumn.STRING, 19, 255 , 0 , false , false),
		new SchemaColumn("applicantLiveAddress", DataColumn.STRING, 20, 255 , 0 , false , false),
		new SchemaColumn("occupation", DataColumn.STRING, 21, 255 , 0 , false , false),
		new SchemaColumn("applicantArea1", DataColumn.STRING, 22, 255 , 0 , false , false),
		new SchemaColumn("applicantArea2", DataColumn.STRING, 23, 255 , 0 , false , false),
		new SchemaColumn("applicantOccupation1", DataColumn.STRING, 24, 255 , 0 , false , false),
		new SchemaColumn("applicantOccupation2", DataColumn.STRING, 25, 255 , 0 , false , false),
		new SchemaColumn("applicantOccupation3", DataColumn.STRING, 26, 255 , 0 , false , false),
		new SchemaColumn("applicantIdentityTypeName", DataColumn.STRING, 27, 255 , 0 , false , false),
		new SchemaColumn("applicantSexName", DataColumn.STRING, 28, 255 , 0 , false , false)
	};

	public static final String _TableCode = "InformationAppnt";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into InformationAppnt values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update InformationAppnt set id=?,createDate=?,modifyDate=?,applicantArea=?,applicantBirthday=?,applicantHomePhone=?,applicantIdentityId=?,applicantIdentityType=?,applicantMail=?,applicantMobile=?,applicantMobile2=?,applicantName=?,applicantSex=?,applicantTel=?,applicantZipCode=?,information_id=?,applicantAddress=?,applicantEnName=?,applicantFirstEnName=?,applicantLastEnName=?,applicantLiveAddress=?,occupation=?,applicantArea1=?,applicantArea2=?,applicantOccupation1=?,applicantOccupation2=?,applicantOccupation3=?,applicantIdentityTypeName=?,applicantSexName=? where id=?";

	protected static final String _DeleteSQL = "delete from InformationAppnt  where id=?";

	protected static final String _FillAllSQL = "select * from InformationAppnt  where id=?";

	public InformationAppntSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[29];
	}

	protected Schema newInstance(){
		return new InformationAppntSchema();
	}

	protected SchemaSet newSet(){
		return new InformationAppntSet();
	}

	public InformationAppntSet query() {
		return query(null, -1, -1);
	}

	public InformationAppntSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public InformationAppntSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public InformationAppntSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (InformationAppntSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){createDate = (Date)v;return;}
		if (i == 2){modifyDate = (Date)v;return;}
		if (i == 3){applicantArea = (String)v;return;}
		if (i == 4){applicantBirthday = (String)v;return;}
		if (i == 5){applicantHomePhone = (String)v;return;}
		if (i == 6){applicantIdentityId = (String)v;return;}
		if (i == 7){applicantIdentityType = (String)v;return;}
		if (i == 8){applicantMail = (String)v;return;}
		if (i == 9){applicantMobile = (String)v;return;}
		if (i == 10){applicantMobile2 = (String)v;return;}
		if (i == 11){applicantName = (String)v;return;}
		if (i == 12){applicantSex = (String)v;return;}
		if (i == 13){applicantTel = (String)v;return;}
		if (i == 14){applicantZipCode = (String)v;return;}
		if (i == 15){information_id = (String)v;return;}
		if (i == 16){applicantAddress = (String)v;return;}
		if (i == 17){applicantEnName = (String)v;return;}
		if (i == 18){applicantFirstEnName = (String)v;return;}
		if (i == 19){applicantLastEnName = (String)v;return;}
		if (i == 20){applicantLiveAddress = (String)v;return;}
		if (i == 21){occupation = (String)v;return;}
		if (i == 22){applicantArea1 = (String)v;return;}
		if (i == 23){applicantArea2 = (String)v;return;}
		if (i == 24){applicantOccupation1 = (String)v;return;}
		if (i == 25){applicantOccupation2 = (String)v;return;}
		if (i == 26){applicantOccupation3 = (String)v;return;}
		if (i == 27){applicantIdentityTypeName = (String)v;return;}
		if (i == 28){applicantSexName = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return createDate;}
		if (i == 2){return modifyDate;}
		if (i == 3){return applicantArea;}
		if (i == 4){return applicantBirthday;}
		if (i == 5){return applicantHomePhone;}
		if (i == 6){return applicantIdentityId;}
		if (i == 7){return applicantIdentityType;}
		if (i == 8){return applicantMail;}
		if (i == 9){return applicantMobile;}
		if (i == 10){return applicantMobile2;}
		if (i == 11){return applicantName;}
		if (i == 12){return applicantSex;}
		if (i == 13){return applicantTel;}
		if (i == 14){return applicantZipCode;}
		if (i == 15){return information_id;}
		if (i == 16){return applicantAddress;}
		if (i == 17){return applicantEnName;}
		if (i == 18){return applicantFirstEnName;}
		if (i == 19){return applicantLastEnName;}
		if (i == 20){return applicantLiveAddress;}
		if (i == 21){return occupation;}
		if (i == 22){return applicantArea1;}
		if (i == 23){return applicantArea2;}
		if (i == 24){return applicantOccupation1;}
		if (i == 25){return applicantOccupation2;}
		if (i == 26){return applicantOccupation3;}
		if (i == 27){return applicantIdentityTypeName;}
		if (i == 28){return applicantSexName;}
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
	* 获取字段applicantArea的值，该字段的<br>
	* 字段名称 :applicantArea<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getapplicantArea() {
		return applicantArea;
	}

	/**
	* 设置字段applicantArea的值，该字段的<br>
	* 字段名称 :applicantArea<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setapplicantArea(String applicantArea) {
		this.applicantArea = applicantArea;
    }

	/**
	* 获取字段applicantBirthday的值，该字段的<br>
	* 字段名称 :applicantBirthday<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getapplicantBirthday() {
		return applicantBirthday;
	}

	/**
	* 设置字段applicantBirthday的值，该字段的<br>
	* 字段名称 :applicantBirthday<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setapplicantBirthday(String applicantBirthday) {
		this.applicantBirthday = applicantBirthday;
    }

	/**
	* 获取字段applicantHomePhone的值，该字段的<br>
	* 字段名称 :applicantHomePhone<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getapplicantHomePhone() {
		return applicantHomePhone;
	}

	/**
	* 设置字段applicantHomePhone的值，该字段的<br>
	* 字段名称 :applicantHomePhone<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setapplicantHomePhone(String applicantHomePhone) {
		this.applicantHomePhone = applicantHomePhone;
    }

	/**
	* 获取字段applicantIdentityId的值，该字段的<br>
	* 字段名称 :applicantIdentityId<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getapplicantIdentityId() {
		return applicantIdentityId;
	}

	/**
	* 设置字段applicantIdentityId的值，该字段的<br>
	* 字段名称 :applicantIdentityId<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setapplicantIdentityId(String applicantIdentityId) {
		this.applicantIdentityId = applicantIdentityId;
    }

	/**
	* 获取字段applicantIdentityType的值，该字段的<br>
	* 字段名称 :applicantIdentityType<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getapplicantIdentityType() {
		return applicantIdentityType;
	}

	/**
	* 设置字段applicantIdentityType的值，该字段的<br>
	* 字段名称 :applicantIdentityType<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setapplicantIdentityType(String applicantIdentityType) {
		this.applicantIdentityType = applicantIdentityType;
    }

	/**
	* 获取字段applicantMail的值，该字段的<br>
	* 字段名称 :applicantMail<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getapplicantMail() {
		return applicantMail;
	}

	/**
	* 设置字段applicantMail的值，该字段的<br>
	* 字段名称 :applicantMail<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setapplicantMail(String applicantMail) {
		this.applicantMail = applicantMail;
    }

	/**
	* 获取字段applicantMobile的值，该字段的<br>
	* 字段名称 :applicantMobile<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getapplicantMobile() {
		return applicantMobile;
	}

	/**
	* 设置字段applicantMobile的值，该字段的<br>
	* 字段名称 :applicantMobile<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setapplicantMobile(String applicantMobile) {
		this.applicantMobile = applicantMobile;
    }

	/**
	* 获取字段applicantMobile2的值，该字段的<br>
	* 字段名称 :applicantMobile2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getapplicantMobile2() {
		return applicantMobile2;
	}

	/**
	* 设置字段applicantMobile2的值，该字段的<br>
	* 字段名称 :applicantMobile2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setapplicantMobile2(String applicantMobile2) {
		this.applicantMobile2 = applicantMobile2;
    }

	/**
	* 获取字段applicantName的值，该字段的<br>
	* 字段名称 :applicantName<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getapplicantName() {
		return applicantName;
	}

	/**
	* 设置字段applicantName的值，该字段的<br>
	* 字段名称 :applicantName<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setapplicantName(String applicantName) {
		this.applicantName = applicantName;
    }

	/**
	* 获取字段applicantSex的值，该字段的<br>
	* 字段名称 :applicantSex<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getapplicantSex() {
		return applicantSex;
	}

	/**
	* 设置字段applicantSex的值，该字段的<br>
	* 字段名称 :applicantSex<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setapplicantSex(String applicantSex) {
		this.applicantSex = applicantSex;
    }

	/**
	* 获取字段applicantTel的值，该字段的<br>
	* 字段名称 :applicantTel<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getapplicantTel() {
		return applicantTel;
	}

	/**
	* 设置字段applicantTel的值，该字段的<br>
	* 字段名称 :applicantTel<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setapplicantTel(String applicantTel) {
		this.applicantTel = applicantTel;
    }

	/**
	* 获取字段applicantZipCode的值，该字段的<br>
	* 字段名称 :applicantZipCode<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getapplicantZipCode() {
		return applicantZipCode;
	}

	/**
	* 设置字段applicantZipCode的值，该字段的<br>
	* 字段名称 :applicantZipCode<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setapplicantZipCode(String applicantZipCode) {
		this.applicantZipCode = applicantZipCode;
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
	* 获取字段applicantAddress的值，该字段的<br>
	* 字段名称 :applicantAddress<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getapplicantAddress() {
		return applicantAddress;
	}

	/**
	* 设置字段applicantAddress的值，该字段的<br>
	* 字段名称 :applicantAddress<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setapplicantAddress(String applicantAddress) {
		this.applicantAddress = applicantAddress;
    }

	/**
	* 获取字段applicantEnName的值，该字段的<br>
	* 字段名称 :applicantEnName<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getapplicantEnName() {
		return applicantEnName;
	}

	/**
	* 设置字段applicantEnName的值，该字段的<br>
	* 字段名称 :applicantEnName<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setapplicantEnName(String applicantEnName) {
		this.applicantEnName = applicantEnName;
    }

	/**
	* 获取字段applicantFirstEnName的值，该字段的<br>
	* 字段名称 :applicantFirstEnName<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getapplicantFirstEnName() {
		return applicantFirstEnName;
	}

	/**
	* 设置字段applicantFirstEnName的值，该字段的<br>
	* 字段名称 :applicantFirstEnName<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setapplicantFirstEnName(String applicantFirstEnName) {
		this.applicantFirstEnName = applicantFirstEnName;
    }

	/**
	* 获取字段applicantLastEnName的值，该字段的<br>
	* 字段名称 :applicantLastEnName<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getapplicantLastEnName() {
		return applicantLastEnName;
	}

	/**
	* 设置字段applicantLastEnName的值，该字段的<br>
	* 字段名称 :applicantLastEnName<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setapplicantLastEnName(String applicantLastEnName) {
		this.applicantLastEnName = applicantLastEnName;
    }

	/**
	* 获取字段applicantLiveAddress的值，该字段的<br>
	* 字段名称 :applicantLiveAddress<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getapplicantLiveAddress() {
		return applicantLiveAddress;
	}

	/**
	* 设置字段applicantLiveAddress的值，该字段的<br>
	* 字段名称 :applicantLiveAddress<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setapplicantLiveAddress(String applicantLiveAddress) {
		this.applicantLiveAddress = applicantLiveAddress;
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
	* 获取字段applicantArea1的值，该字段的<br>
	* 字段名称 :applicantArea1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getapplicantArea1() {
		return applicantArea1;
	}

	/**
	* 设置字段applicantArea1的值，该字段的<br>
	* 字段名称 :applicantArea1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setapplicantArea1(String applicantArea1) {
		this.applicantArea1 = applicantArea1;
    }

	/**
	* 获取字段applicantArea2的值，该字段的<br>
	* 字段名称 :applicantArea2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getapplicantArea2() {
		return applicantArea2;
	}

	/**
	* 设置字段applicantArea2的值，该字段的<br>
	* 字段名称 :applicantArea2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setapplicantArea2(String applicantArea2) {
		this.applicantArea2 = applicantArea2;
    }

	/**
	* 获取字段applicantOccupation1的值，该字段的<br>
	* 字段名称 :applicantOccupation1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getapplicantOccupation1() {
		return applicantOccupation1;
	}

	/**
	* 设置字段applicantOccupation1的值，该字段的<br>
	* 字段名称 :applicantOccupation1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setapplicantOccupation1(String applicantOccupation1) {
		this.applicantOccupation1 = applicantOccupation1;
    }

	/**
	* 获取字段applicantOccupation2的值，该字段的<br>
	* 字段名称 :applicantOccupation2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getapplicantOccupation2() {
		return applicantOccupation2;
	}

	/**
	* 设置字段applicantOccupation2的值，该字段的<br>
	* 字段名称 :applicantOccupation2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setapplicantOccupation2(String applicantOccupation2) {
		this.applicantOccupation2 = applicantOccupation2;
    }

	/**
	* 获取字段applicantOccupation3的值，该字段的<br>
	* 字段名称 :applicantOccupation3<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getapplicantOccupation3() {
		return applicantOccupation3;
	}

	/**
	* 设置字段applicantOccupation3的值，该字段的<br>
	* 字段名称 :applicantOccupation3<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setapplicantOccupation3(String applicantOccupation3) {
		this.applicantOccupation3 = applicantOccupation3;
    }

	/**
	* 获取字段applicantIdentityTypeName的值，该字段的<br>
	* 字段名称 :applicantIdentityTypeName<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getapplicantIdentityTypeName() {
		return applicantIdentityTypeName;
	}

	/**
	* 设置字段applicantIdentityTypeName的值，该字段的<br>
	* 字段名称 :applicantIdentityTypeName<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setapplicantIdentityTypeName(String applicantIdentityTypeName) {
		this.applicantIdentityTypeName = applicantIdentityTypeName;
    }

	/**
	* 获取字段applicantSexName的值，该字段的<br>
	* 字段名称 :applicantSexName<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getapplicantSexName() {
		return applicantSexName;
	}

	/**
	* 设置字段applicantSexName的值，该字段的<br>
	* 字段名称 :applicantSexName<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setapplicantSexName(String applicantSexName) {
		this.applicantSexName = applicantSexName;
    }

}