package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：关联orderItem,以及用户订单界面公共属性的信息表<br>
 * 表代码：information<br>
 * 表主键：id<br>
 */
public class informationSchema extends Schema {
	private String id;

	private Date createDate;

	private Date modifyDate;

	private String applicantArea;

	private String applicantBirthday;

	private String applicantIdentityId;

	private String applicantIdentityType;

	private String applicantMail;

	private String applicantName;

	private String applicantSex;

	private String applicantTel;

	private String coutNo;

	private String electronicCout;

	private String occupation1;

	private String occupation2;

	private String occupation3;

	private String recognizeeArea;

	private String recognizeeBirthday;

	private String recognizeeIdentityId;

	private String recognizeeIdentityType;

	private String recognizeeMail;

	private String recognizeeName;

	private String recognizeeSex;

	private String recognizeeTel;

	private String recognizeeZipCode;

	private String orderItem_id;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("createDate", DataColumn.DATETIME, 1, 0 , 0 , false , false),
		new SchemaColumn("modifyDate", DataColumn.DATETIME, 2, 0 , 0 , false , false),
		new SchemaColumn("applicantArea", DataColumn.STRING, 3, 255 , 0 , false , false),
		new SchemaColumn("applicantBirthday", DataColumn.STRING, 4, 255 , 0 , false , false),
		new SchemaColumn("applicantIdentityId", DataColumn.STRING, 5, 255 , 0 , false , false),
		new SchemaColumn("applicantIdentityType", DataColumn.STRING, 6, 255 , 0 , false , false),
		new SchemaColumn("applicantMail", DataColumn.STRING, 7, 255 , 0 , false , false),
		new SchemaColumn("applicantName", DataColumn.STRING, 8, 255 , 0 , false , false),
		new SchemaColumn("applicantSex", DataColumn.STRING, 9, 255 , 0 , false , false),
		new SchemaColumn("applicantTel", DataColumn.STRING, 10, 255 , 0 , false , false),
		new SchemaColumn("coutNo", DataColumn.STRING, 11, 255 , 0 , false , false),
		new SchemaColumn("electronicCout", DataColumn.STRING, 12, 255 , 0 , false , false),
		new SchemaColumn("occupation1", DataColumn.STRING, 13, 255 , 0 , false , false),
		new SchemaColumn("occupation2", DataColumn.STRING, 14, 255 , 0 , false , false),
		new SchemaColumn("occupation3", DataColumn.STRING, 15, 255 , 0 , false , false),
		new SchemaColumn("recognizeeArea", DataColumn.STRING, 16, 255 , 0 , false , false),
		new SchemaColumn("recognizeeBirthday", DataColumn.STRING, 17, 255 , 0 , false , false),
		new SchemaColumn("recognizeeIdentityId", DataColumn.STRING, 18, 255 , 0 , false , false),
		new SchemaColumn("recognizeeIdentityType", DataColumn.STRING, 19, 255 , 0 , false , false),
		new SchemaColumn("recognizeeMail", DataColumn.STRING, 20, 255 , 0 , false , false),
		new SchemaColumn("recognizeeName", DataColumn.STRING, 21, 255 , 0 , false , false),
		new SchemaColumn("recognizeeSex", DataColumn.STRING, 22, 255 , 0 , false , false),
		new SchemaColumn("recognizeeTel", DataColumn.STRING, 23, 255 , 0 , false , false),
		new SchemaColumn("recognizeeZipCode", DataColumn.STRING, 24, 255 , 0 , false , false),
		new SchemaColumn("orderItem_id", DataColumn.STRING, 25, 32 , 0 , false , false)
	};

	public static final String _TableCode = "information";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into information values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update information set id=?,createDate=?,modifyDate=?,applicantArea=?,applicantBirthday=?,applicantIdentityId=?,applicantIdentityType=?,applicantMail=?,applicantName=?,applicantSex=?,applicantTel=?,coutNo=?,electronicCout=?,occupation1=?,occupation2=?,occupation3=?,recognizeeArea=?,recognizeeBirthday=?,recognizeeIdentityId=?,recognizeeIdentityType=?,recognizeeMail=?,recognizeeName=?,recognizeeSex=?,recognizeeTel=?,recognizeeZipCode=?,orderItem_id=? where id=?";

	protected static final String _DeleteSQL = "delete from information  where id=?";

	protected static final String _FillAllSQL = "select * from information  where id=?";

	public informationSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[26];
	}

	protected Schema newInstance(){
		return new informationSchema();
	}

	protected SchemaSet newSet(){
		return new informationSet();
	}

	public informationSet query() {
		return query(null, -1, -1);
	}

	public informationSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public informationSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public informationSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (informationSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){createDate = (Date)v;return;}
		if (i == 2){modifyDate = (Date)v;return;}
		if (i == 3){applicantArea = (String)v;return;}
		if (i == 4){applicantBirthday = (String)v;return;}
		if (i == 5){applicantIdentityId = (String)v;return;}
		if (i == 6){applicantIdentityType = (String)v;return;}
		if (i == 7){applicantMail = (String)v;return;}
		if (i == 8){applicantName = (String)v;return;}
		if (i == 9){applicantSex = (String)v;return;}
		if (i == 10){applicantTel = (String)v;return;}
		if (i == 11){coutNo = (String)v;return;}
		if (i == 12){electronicCout = (String)v;return;}
		if (i == 13){occupation1 = (String)v;return;}
		if (i == 14){occupation2 = (String)v;return;}
		if (i == 15){occupation3 = (String)v;return;}
		if (i == 16){recognizeeArea = (String)v;return;}
		if (i == 17){recognizeeBirthday = (String)v;return;}
		if (i == 18){recognizeeIdentityId = (String)v;return;}
		if (i == 19){recognizeeIdentityType = (String)v;return;}
		if (i == 20){recognizeeMail = (String)v;return;}
		if (i == 21){recognizeeName = (String)v;return;}
		if (i == 22){recognizeeSex = (String)v;return;}
		if (i == 23){recognizeeTel = (String)v;return;}
		if (i == 24){recognizeeZipCode = (String)v;return;}
		if (i == 25){orderItem_id = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return createDate;}
		if (i == 2){return modifyDate;}
		if (i == 3){return applicantArea;}
		if (i == 4){return applicantBirthday;}
		if (i == 5){return applicantIdentityId;}
		if (i == 6){return applicantIdentityType;}
		if (i == 7){return applicantMail;}
		if (i == 8){return applicantName;}
		if (i == 9){return applicantSex;}
		if (i == 10){return applicantTel;}
		if (i == 11){return coutNo;}
		if (i == 12){return electronicCout;}
		if (i == 13){return occupation1;}
		if (i == 14){return occupation2;}
		if (i == 15){return occupation3;}
		if (i == 16){return recognizeeArea;}
		if (i == 17){return recognizeeBirthday;}
		if (i == 18){return recognizeeIdentityId;}
		if (i == 19){return recognizeeIdentityType;}
		if (i == 20){return recognizeeMail;}
		if (i == 21){return recognizeeName;}
		if (i == 22){return recognizeeSex;}
		if (i == 23){return recognizeeTel;}
		if (i == 24){return recognizeeZipCode;}
		if (i == 25){return orderItem_id;}
		return null;
	}

	/**
	* 获取字段id的值，该字段的<br>
	* 字段名称 :记录ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getid() {
		return id;
	}

	/**
	* 设置字段id的值，该字段的<br>
	* 字段名称 :记录ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setid(String id) {
		this.id = id;
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
	* 获取字段applicantArea的值，该字段的<br>
	* 字段名称 :投保人所在地<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getapplicantArea() {
		return applicantArea;
	}

	/**
	* 设置字段applicantArea的值，该字段的<br>
	* 字段名称 :投保人所在地<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setapplicantArea(String applicantArea) {
		this.applicantArea = applicantArea;
    }

	/**
	* 获取字段applicantBirthday的值，该字段的<br>
	* 字段名称 :投保人出生日期<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getapplicantBirthday() {
		return applicantBirthday;
	}

	/**
	* 设置字段applicantBirthday的值，该字段的<br>
	* 字段名称 :投保人出生日期<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setapplicantBirthday(String applicantBirthday) {
		this.applicantBirthday = applicantBirthday;
    }

	/**
	* 获取字段applicantIdentityId的值，该字段的<br>
	* 字段名称 :投保人证件号码<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getapplicantIdentityId() {
		return applicantIdentityId;
	}

	/**
	* 设置字段applicantIdentityId的值，该字段的<br>
	* 字段名称 :投保人证件号码<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setapplicantIdentityId(String applicantIdentityId) {
		this.applicantIdentityId = applicantIdentityId;
    }

	/**
	* 获取字段applicantIdentityType的值，该字段的<br>
	* 字段名称 :投保人证件类型<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getapplicantIdentityType() {
		return applicantIdentityType;
	}

	/**
	* 设置字段applicantIdentityType的值，该字段的<br>
	* 字段名称 :投保人证件类型<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setapplicantIdentityType(String applicantIdentityType) {
		this.applicantIdentityType = applicantIdentityType;
    }

	/**
	* 获取字段applicantMail的值，该字段的<br>
	* 字段名称 :投保人电子邮箱<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getapplicantMail() {
		return applicantMail;
	}

	/**
	* 设置字段applicantMail的值，该字段的<br>
	* 字段名称 :投保人电子邮箱<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setapplicantMail(String applicantMail) {
		this.applicantMail = applicantMail;
    }

	/**
	* 获取字段applicantName的值，该字段的<br>
	* 字段名称 :投保人姓名<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getapplicantName() {
		return applicantName;
	}

	/**
	* 设置字段applicantName的值，该字段的<br>
	* 字段名称 :投保人姓名<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setapplicantName(String applicantName) {
		this.applicantName = applicantName;
    }

	/**
	* 获取字段applicantSex的值，该字段的<br>
	* 字段名称 :投保人性别<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getapplicantSex() {
		return applicantSex;
	}

	/**
	* 设置字段applicantSex的值，该字段的<br>
	* 字段名称 :投保人性别<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setapplicantSex(String applicantSex) {
		this.applicantSex = applicantSex;
    }

	/**
	* 获取字段applicantTel的值，该字段的<br>
	* 字段名称 :投保人电话<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getapplicantTel() {
		return applicantTel;
	}

	/**
	* 设置字段applicantTel的值，该字段的<br>
	* 字段名称 :投保人电话<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setapplicantTel(String applicantTel) {
		this.applicantTel = applicantTel;
    }

	/**
	* 获取字段coutNo的值，该字段的<br>
	* 字段名称 :保单号<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcoutNo() {
		return coutNo;
	}

	/**
	* 设置字段coutNo的值，该字段的<br>
	* 字段名称 :保单号<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcoutNo(String coutNo) {
		this.coutNo = coutNo;
    }

	/**
	* 获取字段electronicCout的值，该字段的<br>
	* 字段名称 :上传电子保单<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getelectronicCout() {
		return electronicCout;
	}

	/**
	* 设置字段electronicCout的值，该字段的<br>
	* 字段名称 :上传电子保单<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setelectronicCout(String electronicCout) {
		this.electronicCout = electronicCout;
    }

	/**
	* 获取字段occupation1的值，该字段的<br>
	* 字段名称 :职业1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getoccupation1() {
		return occupation1;
	}

	/**
	* 设置字段occupation1的值，该字段的<br>
	* 字段名称 :职业1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setoccupation1(String occupation1) {
		this.occupation1 = occupation1;
    }

	/**
	* 获取字段occupation2的值，该字段的<br>
	* 字段名称 :职业2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getoccupation2() {
		return occupation2;
	}

	/**
	* 设置字段occupation2的值，该字段的<br>
	* 字段名称 :职业2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setoccupation2(String occupation2) {
		this.occupation2 = occupation2;
    }

	/**
	* 获取字段occupation3的值，该字段的<br>
	* 字段名称 :职业3<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getoccupation3() {
		return occupation3;
	}

	/**
	* 设置字段occupation3的值，该字段的<br>
	* 字段名称 :职业3<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setoccupation3(String occupation3) {
		this.occupation3 = occupation3;
    }

	/**
	* 获取字段recognizeeArea的值，该字段的<br>
	* 字段名称 :被保人所在地<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeArea() {
		return recognizeeArea;
	}

	/**
	* 设置字段recognizeeArea的值，该字段的<br>
	* 字段名称 :被保人所在地<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeArea(String recognizeeArea) {
		this.recognizeeArea = recognizeeArea;
    }

	/**
	* 获取字段recognizeeBirthday的值，该字段的<br>
	* 字段名称 :被保人出生日期<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeBirthday() {
		return recognizeeBirthday;
	}

	/**
	* 设置字段recognizeeBirthday的值，该字段的<br>
	* 字段名称 :被保人出生日期<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeBirthday(String recognizeeBirthday) {
		this.recognizeeBirthday = recognizeeBirthday;
    }

	/**
	* 获取字段recognizeeIdentityId的值，该字段的<br>
	* 字段名称 :被保人证件号码<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeIdentityId() {
		return recognizeeIdentityId;
	}

	/**
	* 设置字段recognizeeIdentityId的值，该字段的<br>
	* 字段名称 :被保人证件号码<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeIdentityId(String recognizeeIdentityId) {
		this.recognizeeIdentityId = recognizeeIdentityId;
    }

	/**
	* 获取字段recognizeeIdentityType的值，该字段的<br>
	* 字段名称 :被保人证件类型<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeIdentityType() {
		return recognizeeIdentityType;
	}

	/**
	* 设置字段recognizeeIdentityType的值，该字段的<br>
	* 字段名称 :被保人证件类型<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeIdentityType(String recognizeeIdentityType) {
		this.recognizeeIdentityType = recognizeeIdentityType;
    }

	/**
	* 获取字段recognizeeMail的值，该字段的<br>
	* 字段名称 :被保人电子邮箱<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeMail() {
		return recognizeeMail;
	}

	/**
	* 设置字段recognizeeMail的值，该字段的<br>
	* 字段名称 :被保人电子邮箱<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeMail(String recognizeeMail) {
		this.recognizeeMail = recognizeeMail;
    }

	/**
	* 获取字段recognizeeName的值，该字段的<br>
	* 字段名称 :被保人姓名<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeName() {
		return recognizeeName;
	}

	/**
	* 设置字段recognizeeName的值，该字段的<br>
	* 字段名称 :被保人姓名<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeName(String recognizeeName) {
		this.recognizeeName = recognizeeName;
    }

	/**
	* 获取字段recognizeeSex的值，该字段的<br>
	* 字段名称 :被保人性别<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeSex() {
		return recognizeeSex;
	}

	/**
	* 设置字段recognizeeSex的值，该字段的<br>
	* 字段名称 :被保人性别<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeSex(String recognizeeSex) {
		this.recognizeeSex = recognizeeSex;
    }

	/**
	* 获取字段recognizeeTel的值，该字段的<br>
	* 字段名称 :被保人电话<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeTel() {
		return recognizeeTel;
	}

	/**
	* 设置字段recognizeeTel的值，该字段的<br>
	* 字段名称 :被保人电话<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeTel(String recognizeeTel) {
		this.recognizeeTel = recognizeeTel;
    }

	/**
	* 获取字段recognizeeZipCode的值，该字段的<br>
	* 字段名称 :被保人邮政编码<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeZipCode() {
		return recognizeeZipCode;
	}

	/**
	* 设置字段recognizeeZipCode的值，该字段的<br>
	* 字段名称 :被保人邮政编码<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeZipCode(String recognizeeZipCode) {
		this.recognizeeZipCode = recognizeeZipCode;
    }

	/**
	* 获取字段orderItem_id的值，该字段的<br>
	* 字段名称 :订单<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getorderItem_id() {
		return orderItem_id;
	}

	/**
	* 设置字段orderItem_id的值，该字段的<br>
	* 字段名称 :订单<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setorderItem_id(String orderItem_id) {
		this.orderItem_id = orderItem_id;
    }

}