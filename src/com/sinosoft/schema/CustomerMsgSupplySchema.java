package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：CustomerMsgSupply<br>
 * 表代码：CustomerMsgSupply<br>
 * 表主键：ID<br>
 */
public class CustomerMsgSupplySchema extends Schema {
	private Long ID;

	private Date createDate;

	private Date modifyDate;

	private String applicantName;

	private String applicantSex;

	private String applicantIdentityType;

	private String applicantIdentityId;

	private String applicantStartID;

	private String applicantEndID;

	private String applicantMail;

	private String bankUserName;

	private String bankCode;

	private String bankNo;

	private String recognizeeName;

	private String recognizeeSex;

	private String recognizeeIdentityType;

	private String recognizeeIdentityId;

	private String recognizeeStartID;

	private String recognizeeEndID;

	private String recognizeeMobile;

	private String recognizeeMail;

	private String recognizeeAddress;

	private String tbCustomerChannel;

	private String insuredRelation;

	private String msgValid;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 0 , 0 , false , true),
		new SchemaColumn("createDate", DataColumn.DATETIME, 1, 0 , 0 , false , false),
		new SchemaColumn("modifyDate", DataColumn.DATETIME, 2, 0 , 0 , false , false),
		new SchemaColumn("applicantName", DataColumn.STRING, 3, 50 , 0 , false , false),
		new SchemaColumn("applicantSex", DataColumn.STRING, 4, 2 , 0 , false , false),
		new SchemaColumn("applicantIdentityType", DataColumn.STRING, 5, 10 , 0 , false , false),
		new SchemaColumn("applicantIdentityId", DataColumn.STRING, 6, 50 , 0 , false , false),
		new SchemaColumn("applicantStartID", DataColumn.STRING, 7, 255 , 0 , false , false),
		new SchemaColumn("applicantEndID", DataColumn.STRING, 8, 255 , 0 , false , false),
		new SchemaColumn("applicantMail", DataColumn.STRING, 9, 50 , 0 , false , false),
		new SchemaColumn("bankUserName", DataColumn.STRING, 10, 100 , 0 , false , false),
		new SchemaColumn("bankCode", DataColumn.STRING, 11, 100 , 0 , false , false),
		new SchemaColumn("bankNo", DataColumn.STRING, 12, 100 , 0 , false , false),
		new SchemaColumn("recognizeeName", DataColumn.STRING, 13, 50 , 0 , false , false),
		new SchemaColumn("recognizeeSex", DataColumn.STRING, 14, 2 , 0 , false , false),
		new SchemaColumn("recognizeeIdentityType", DataColumn.STRING, 15, 10 , 0 , false , false),
		new SchemaColumn("recognizeeIdentityId", DataColumn.STRING, 16, 50 , 0 , false , false),
		new SchemaColumn("recognizeeStartID", DataColumn.STRING, 17, 255 , 0 , false , false),
		new SchemaColumn("recognizeeEndID", DataColumn.STRING, 18, 255 , 0 , false , false),
		new SchemaColumn("recognizeeMobile", DataColumn.STRING, 19, 13 , 0 , false , false),
		new SchemaColumn("recognizeeMail", DataColumn.STRING, 20, 50 , 0 , false , false),
		new SchemaColumn("recognizeeAddress", DataColumn.STRING, 21, 255 , 0 , false , false),
		new SchemaColumn("tbCustomerChannel", DataColumn.STRING, 22, 20 , 0 , false , false),
		new SchemaColumn("insuredRelation", DataColumn.STRING, 23, 20 , 0 , false , false),
		new SchemaColumn("msgValid", DataColumn.STRING, 24, 2 , 0 , false , false)
	};

	public static final String _TableCode = "CustomerMsgSupply";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into CustomerMsgSupply values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update CustomerMsgSupply set ID=?,createDate=?,modifyDate=?,applicantName=?,applicantSex=?,applicantIdentityType=?,applicantIdentityId=?,applicantStartID=?,applicantEndID=?,applicantMail=?,bankUserName=?,bankCode=?,bankNo=?,recognizeeName=?,recognizeeSex=?,recognizeeIdentityType=?,recognizeeIdentityId=?,recognizeeStartID=?,recognizeeEndID=?,recognizeeMobile=?,recognizeeMail=?,recognizeeAddress=?,tbCustomerChannel=?,insuredRelation=?,msgValid=? where ID=?";

	protected static final String _DeleteSQL = "delete from CustomerMsgSupply  where ID=?";

	protected static final String _FillAllSQL = "select * from CustomerMsgSupply  where ID=?";

	public CustomerMsgSupplySchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[25];
	}

	protected Schema newInstance(){
		return new CustomerMsgSupplySchema();
	}

	protected SchemaSet newSet(){
		return new CustomerMsgSupplySet();
	}

	public CustomerMsgSupplySet query() {
		return query(null, -1, -1);
	}

	public CustomerMsgSupplySet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public CustomerMsgSupplySet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public CustomerMsgSupplySet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (CustomerMsgSupplySet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){createDate = (Date)v;return;}
		if (i == 2){modifyDate = (Date)v;return;}
		if (i == 3){applicantName = (String)v;return;}
		if (i == 4){applicantSex = (String)v;return;}
		if (i == 5){applicantIdentityType = (String)v;return;}
		if (i == 6){applicantIdentityId = (String)v;return;}
		if (i == 7){applicantStartID = (String)v;return;}
		if (i == 8){applicantEndID = (String)v;return;}
		if (i == 9){applicantMail = (String)v;return;}
		if (i == 10){bankUserName = (String)v;return;}
		if (i == 11){bankCode = (String)v;return;}
		if (i == 12){bankNo = (String)v;return;}
		if (i == 13){recognizeeName = (String)v;return;}
		if (i == 14){recognizeeSex = (String)v;return;}
		if (i == 15){recognizeeIdentityType = (String)v;return;}
		if (i == 16){recognizeeIdentityId = (String)v;return;}
		if (i == 17){recognizeeStartID = (String)v;return;}
		if (i == 18){recognizeeEndID = (String)v;return;}
		if (i == 19){recognizeeMobile = (String)v;return;}
		if (i == 20){recognizeeMail = (String)v;return;}
		if (i == 21){recognizeeAddress = (String)v;return;}
		if (i == 22){tbCustomerChannel = (String)v;return;}
		if (i == 23){insuredRelation = (String)v;return;}
		if (i == 24){msgValid = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return createDate;}
		if (i == 2){return modifyDate;}
		if (i == 3){return applicantName;}
		if (i == 4){return applicantSex;}
		if (i == 5){return applicantIdentityType;}
		if (i == 6){return applicantIdentityId;}
		if (i == 7){return applicantStartID;}
		if (i == 8){return applicantEndID;}
		if (i == 9){return applicantMail;}
		if (i == 10){return bankUserName;}
		if (i == 11){return bankCode;}
		if (i == 12){return bankNo;}
		if (i == 13){return recognizeeName;}
		if (i == 14){return recognizeeSex;}
		if (i == 15){return recognizeeIdentityType;}
		if (i == 16){return recognizeeIdentityId;}
		if (i == 17){return recognizeeStartID;}
		if (i == 18){return recognizeeEndID;}
		if (i == 19){return recognizeeMobile;}
		if (i == 20){return recognizeeMail;}
		if (i == 21){return recognizeeAddress;}
		if (i == 22){return tbCustomerChannel;}
		if (i == 23){return insuredRelation;}
		if (i == 24){return msgValid;}
		return null;
	}

	/**
	* 获取字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :true<br>
	* 是否必填 :false<br>
	*/
	public long getID() {
		if(ID==null){return 0;}
		return ID.longValue();
	}

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :true<br>
	* 是否必填 :false<br>
	*/
	public void setID(long iD) {
		this.ID = new Long(iD);
    }

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :true<br>
	* 是否必填 :false<br>
	*/
	public void setID(String iD) {
		if (iD == null){
			this.ID = null;
			return;
		}
		this.ID = new Long(iD);
    }

	/**
	* 获取字段createDate的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getcreateDate() {
		return createDate;
	}

	/**
	* 设置字段createDate的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcreateDate(Date createDate) {
		this.createDate = createDate;
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

	/**
	* 获取字段applicantName的值，该字段的<br>
	* 字段名称 :投保人姓名<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getapplicantName() {
		return applicantName;
	}

	/**
	* 设置字段applicantName的值，该字段的<br>
	* 字段名称 :投保人姓名<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setapplicantName(String applicantName) {
		this.applicantName = applicantName;
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
	* 获取字段applicantIdentityType的值，该字段的<br>
	* 字段名称 :投保人证件类型<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getapplicantIdentityType() {
		return applicantIdentityType;
	}

	/**
	* 设置字段applicantIdentityType的值，该字段的<br>
	* 字段名称 :投保人证件类型<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setapplicantIdentityType(String applicantIdentityType) {
		this.applicantIdentityType = applicantIdentityType;
    }

	/**
	* 获取字段applicantIdentityId的值，该字段的<br>
	* 字段名称 :投保人证件号码<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getapplicantIdentityId() {
		return applicantIdentityId;
	}

	/**
	* 设置字段applicantIdentityId的值，该字段的<br>
	* 字段名称 :投保人证件号码<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setapplicantIdentityId(String applicantIdentityId) {
		this.applicantIdentityId = applicantIdentityId;
    }

	/**
	* 获取字段applicantStartID的值，该字段的<br>
	* 字段名称 :投保人证件有效起期<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getapplicantStartID() {
		return applicantStartID;
	}

	/**
	* 设置字段applicantStartID的值，该字段的<br>
	* 字段名称 :投保人证件有效起期<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setapplicantStartID(String applicantStartID) {
		this.applicantStartID = applicantStartID;
    }

	/**
	* 获取字段applicantEndID的值，该字段的<br>
	* 字段名称 :投保人证件有效止期<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getapplicantEndID() {
		return applicantEndID;
	}

	/**
	* 设置字段applicantEndID的值，该字段的<br>
	* 字段名称 :投保人证件有效止期<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setapplicantEndID(String applicantEndID) {
		this.applicantEndID = applicantEndID;
    }

	/**
	* 获取字段applicantMail的值，该字段的<br>
	* 字段名称 :投保人邮箱<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getapplicantMail() {
		return applicantMail;
	}

	/**
	* 设置字段applicantMail的值，该字段的<br>
	* 字段名称 :投保人邮箱<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setapplicantMail(String applicantMail) {
		this.applicantMail = applicantMail;
    }

	/**
	* 获取字段bankUserName的值，该字段的<br>
	* 字段名称 :续期银行账户名<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getbankUserName() {
		return bankUserName;
	}

	/**
	* 设置字段bankUserName的值，该字段的<br>
	* 字段名称 :续期银行账户名<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setbankUserName(String bankUserName) {
		this.bankUserName = bankUserName;
    }

	/**
	* 获取字段bankCode的值，该字段的<br>
	* 字段名称 :续期银行编码<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getbankCode() {
		return bankCode;
	}

	/**
	* 设置字段bankCode的值，该字段的<br>
	* 字段名称 :续期银行编码<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setbankCode(String bankCode) {
		this.bankCode = bankCode;
    }

	/**
	* 获取字段bankNo的值，该字段的<br>
	* 字段名称 :续期银行账号<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getbankNo() {
		return bankNo;
	}

	/**
	* 设置字段bankNo的值，该字段的<br>
	* 字段名称 :续期银行账号<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setbankNo(String bankNo) {
		this.bankNo = bankNo;
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
	* 获取字段recognizeeIdentityId的值，该字段的<br>
	* 字段名称 :被保人证件号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeIdentityId() {
		return recognizeeIdentityId;
	}

	/**
	* 设置字段recognizeeIdentityId的值，该字段的<br>
	* 字段名称 :被保人证件号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeIdentityId(String recognizeeIdentityId) {
		this.recognizeeIdentityId = recognizeeIdentityId;
    }

	/**
	* 获取字段recognizeeStartID的值，该字段的<br>
	* 字段名称 :被保人证件有效起期<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeStartID() {
		return recognizeeStartID;
	}

	/**
	* 设置字段recognizeeStartID的值，该字段的<br>
	* 字段名称 :被保人证件有效起期<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeStartID(String recognizeeStartID) {
		this.recognizeeStartID = recognizeeStartID;
    }

	/**
	* 获取字段recognizeeEndID的值，该字段的<br>
	* 字段名称 :被保人证件有效止期<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeEndID() {
		return recognizeeEndID;
	}

	/**
	* 设置字段recognizeeEndID的值，该字段的<br>
	* 字段名称 :被保人证件有效止期<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeEndID(String recognizeeEndID) {
		this.recognizeeEndID = recognizeeEndID;
    }

	/**
	* 获取字段recognizeeMobile的值，该字段的<br>
	* 字段名称 :被保人手机号<br>
	* 数据类型 :varchar(13)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeMobile() {
		return recognizeeMobile;
	}

	/**
	* 设置字段recognizeeMobile的值，该字段的<br>
	* 字段名称 :被保人手机号<br>
	* 数据类型 :varchar(13)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeMobile(String recognizeeMobile) {
		this.recognizeeMobile = recognizeeMobile;
    }

	/**
	* 获取字段recognizeeMail的值，该字段的<br>
	* 字段名称 :被保人邮箱<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeMail() {
		return recognizeeMail;
	}

	/**
	* 设置字段recognizeeMail的值，该字段的<br>
	* 字段名称 :被保人邮箱<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeMail(String recognizeeMail) {
		this.recognizeeMail = recognizeeMail;
    }

	/**
	* 获取字段recognizeeAddress的值，该字段的<br>
	* 字段名称 :被保人地址<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeAddress() {
		return recognizeeAddress;
	}

	/**
	* 设置字段recognizeeAddress的值，该字段的<br>
	* 字段名称 :被保人地址<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeAddress(String recognizeeAddress) {
		this.recognizeeAddress = recognizeeAddress;
    }

	/**
	* 获取字段tbCustomerChannel的值，该字段的<br>
	* 字段名称 :淘宝康惠保完善数据渠道<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String gettbCustomerChannel() {
		return tbCustomerChannel;
	}

	/**
	* 设置字段tbCustomerChannel的值，该字段的<br>
	* 字段名称 :淘宝康惠保完善数据渠道<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void settbCustomerChannel(String tbCustomerChannel) {
		this.tbCustomerChannel = tbCustomerChannel;
    }

	/**
	* 获取字段insuredRelation的值，该字段的<br>
	* 字段名称 :与投保人关系<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getinsuredRelation() {
		return insuredRelation;
	}

	/**
	* 设置字段insuredRelation的值，该字段的<br>
	* 字段名称 :与投保人关系<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setinsuredRelation(String insuredRelation) {
		this.insuredRelation = insuredRelation;
    }

	/**
	* 获取字段msgValid的值，该字段的<br>
	* 字段名称 :完善状态<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getmsgValid() {
		return msgValid;
	}

	/**
	* 设置字段msgValid的值，该字段的<br>
	* 字段名称 :完善状态<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmsgValid(String msgValid) {
		this.msgValid = msgValid;
    }

}