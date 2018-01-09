package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：理赔信息表<br>
 * 表代码：PaymentInfo<br>
 * 表主键：id<br>
 */
public class PaymentInfoSchema extends Schema {
	private String id;

	private Date createDate;

	private Date modifyDate;
	
	private String orderSn;

	private String insureName;

	private String insureIdentityId;

	private String contactName;

	private String contactMail;

	private String contactMobile;

	private String ensureType;

	private String happenTime;

	private String happenArea;

	private String happenDesc;

	private String memberId;

	private String state;

	private String remark1;

	private String remark2;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("createDate", DataColumn.DATETIME, 1, 0 , 0 , true , false),
		new SchemaColumn("modifyDate", DataColumn.DATETIME, 2, 0 , 0 , true , false),
		new SchemaColumn("orderSn", DataColumn.STRING, 3, 50 , 0 , false , false),
		new SchemaColumn("insureName", DataColumn.STRING, 4, 2000 , 0 , false , false),
		new SchemaColumn("insureIdentityId", DataColumn.STRING, 5, 5000 , 0 , false , false),
		new SchemaColumn("contactName", DataColumn.STRING, 6, 255 , 0 , false , false),
		new SchemaColumn("contactMail", DataColumn.STRING, 7, 255 , 0 , false , false),
		new SchemaColumn("contactMobile", DataColumn.STRING, 8, 255 , 0 , false , false),
		new SchemaColumn("ensureType", DataColumn.STRING, 9, 1000 , 0 , false , false),
		new SchemaColumn("happenTime", DataColumn.STRING, 10, 20 , 0 , false , false),
		new SchemaColumn("happenArea", DataColumn.STRING, 11, 1000 , 0 , false , false),
		new SchemaColumn("happenDesc", DataColumn.STRING, 12, 5000 , 0 , false , false),
		new SchemaColumn("memberId", DataColumn.STRING, 13, 32 , 0 , false , false),
		new SchemaColumn("state", DataColumn.STRING, 14, 2 , 0 , false , false),
		new SchemaColumn("remark1", DataColumn.STRING, 15, 255 , 0 , false , false),
		new SchemaColumn("remark2", DataColumn.STRING, 16, 255 , 0 , false , false)
	};

	public static final String _TableCode = "PaymentInfo";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into PaymentInfo values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update PaymentInfo set id=?,createDate=?,modifyDate=?,orderSn=?,insureName=?,insureIdentityId=?,contactName=?,contactMail=?,contactMobile=?,ensureType=?,happenTime=?,happenArea=?,happenDesc=?,memberId=?,state=?,remark1=?,remark2=? where id=?";

	protected static final String _DeleteSQL = "delete from PaymentInfo  where id=?";

	protected static final String _FillAllSQL = "select * from PaymentInfo  where id=?";

	public PaymentInfoSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[17];
	}

	protected Schema newInstance(){
		return new PaymentInfoSchema();
	}

	protected SchemaSet newSet(){
		return new PaymentInfoSet();
	}

	public PaymentInfoSet query() {
		return query(null, -1, -1);
	}

	public PaymentInfoSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public PaymentInfoSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public PaymentInfoSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (PaymentInfoSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){createDate = (Date)v;return;}
		if (i == 2){modifyDate = (Date)v;return;}
		if (i == 3){orderSn = (String)v;return;}
		if (i == 4){insureName = (String)v;return;}
		if (i == 5){insureIdentityId = (String)v;return;}
		if (i == 6){contactName = (String)v;return;}
		if (i == 7){contactMail = (String)v;return;}
		if (i == 8){contactMobile = (String)v;return;}
		if (i == 9){ensureType = (String)v;return;}
		if (i == 10){happenTime = (String)v;return;}
		if (i == 11){happenArea = (String)v;return;}
		if (i == 12){happenDesc = (String)v;return;}
		if (i == 13){memberId = (String)v;return;}
		if (i == 14){state = (String)v;return;}
		if (i == 15){remark1 = (String)v;return;}
		if (i == 16){remark2 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return createDate;}
		if (i == 2){return modifyDate;}
		if (i == 3){return orderSn;}
		if (i == 4){return insureName;}
		if (i == 5){return insureIdentityId;}
		if (i == 6){return contactName;}
		if (i == 7){return contactMail;}
		if (i == 8){return contactMobile;}
		if (i == 9){return ensureType;}
		if (i == 10){return happenTime;}
		if (i == 11){return happenArea;}
		if (i == 12){return happenDesc;}
		if (i == 13){return memberId;}
		if (i == 14){return state;}
		if (i == 15){return remark1;}
		if (i == 16){return remark2;}
		return null;
	}

	/**
	* 获取字段id的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getid() {
		return id;
	}

	/**
	* 设置字段id的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setid(String id) {
		this.id = id;
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
	* 获取字段modifyDate的值，该字段的<br>
	* 字段名称 :更新时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public Date getmodifyDate() {
		return modifyDate;
	}

	/**
	* 设置字段modifyDate的值，该字段的<br>
	* 字段名称 :更新时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setmodifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
    }

	/**
	* 获取字段orderSn的值，该字段的<br>
	* 字段名称 :订单号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getorderSn() {
		return orderSn;
	}

	/**
	* 设置字段orderSn的值，该字段的<br>
	* 字段名称 :订单号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setorderSn(String orderSn) {
		this.orderSn = orderSn;
    }
	
	/**
	* 获取字段insureName的值，该字段的<br>
	* 字段名称 :被保险人姓名<br>
	* 数据类型 :varchar(2000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getinsureName() {
		return insureName;
	}

	/**
	* 设置字段insureName的值，该字段的<br>
	* 字段名称 :被保险人姓名<br>
	* 数据类型 :varchar(2000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setinsureName(String insureName) {
		this.insureName = insureName;
    }

	/**
	* 获取字段insureIdentityId的值，该字段的<br>
	* 字段名称 :被保人证件号<br>
	* 数据类型 :varchar(5000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getinsureIdentityId() {
		return insureIdentityId;
	}

	/**
	* 设置字段insureIdentityId的值，该字段的<br>
	* 字段名称 :被保人证件号<br>
	* 数据类型 :varchar(5000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setinsureIdentityId(String insureIdentityId) {
		this.insureIdentityId = insureIdentityId;
    }

	/**
	* 获取字段contactName的值，该字段的<br>
	* 字段名称 :联系人姓名<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcontactName() {
		return contactName;
	}

	/**
	* 设置字段contactName的值，该字段的<br>
	* 字段名称 :联系人姓名<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcontactName(String contactName) {
		this.contactName = contactName;
    }

	/**
	* 获取字段contactMail的值，该字段的<br>
	* 字段名称 :联系人邮箱<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcontactMail() {
		return contactMail;
	}

	/**
	* 设置字段contactMail的值，该字段的<br>
	* 字段名称 :联系人邮箱<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcontactMail(String contactMail) {
		this.contactMail = contactMail;
    }

	/**
	* 获取字段contactMobile的值，该字段的<br>
	* 字段名称 :联系人手机<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcontactMobile() {
		return contactMobile;
	}

	/**
	* 设置字段contactMobile的值，该字段的<br>
	* 字段名称 :联系人手机<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcontactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
    }

	/**
	* 获取字段ensureType的值，该字段的<br>
	* 字段名称 :保障类型<br>
	* 数据类型 :varchar(1000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getensureType() {
		return ensureType;
	}

	/**
	* 设置字段ensureType的值，该字段的<br>
	* 字段名称 :保障类型<br>
	* 数据类型 :varchar(1000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setensureType(String ensureType) {
		this.ensureType = ensureType;
    }

	/**
	* 获取字段happenTime的值，该字段的<br>
	* 字段名称 :事故发生时间<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String gethappenTime() {
		return happenTime;
	}

	/**
	* 设置字段happenTime的值，该字段的<br>
	* 字段名称 :事故发生时间<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void sethappenTime(String happenTime) {
		this.happenTime = happenTime;
    }

	/**
	* 获取字段happenArea的值，该字段的<br>
	* 字段名称 :事故发生地点<br>
	* 数据类型 :varchar(1000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String gethappenArea() {
		return happenArea;
	}

	/**
	* 设置字段happenArea的值，该字段的<br>
	* 字段名称 :事故发生地点<br>
	* 数据类型 :varchar(1000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void sethappenArea(String happenArea) {
		this.happenArea = happenArea;
    }

	/**
	* 获取字段happenDesc的值，该字段的<br>
	* 字段名称 :事故描述<br>
	* 数据类型 :varchar(5000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String gethappenDesc() {
		return happenDesc;
	}

	/**
	* 设置字段happenDesc的值，该字段的<br>
	* 字段名称 :事故描述<br>
	* 数据类型 :varchar(5000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void sethappenDesc(String happenDesc) {
		this.happenDesc = happenDesc;
    }

	/**
	* 获取字段memberId的值，该字段的<br>
	* 字段名称 :会员id<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getmemberId() {
		return memberId;
	}

	/**
	* 设置字段memberId的值，该字段的<br>
	* 字段名称 :会员id<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setmemberId(String memberId) {
		this.memberId = memberId;
    }

	/**
	* 获取字段state的值，该字段的<br>
	* 字段名称 :处理状态<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getstate() {
		return state;
	}

	/**
	* 设置字段state的值，该字段的<br>
	* 字段名称 :处理状态<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setstate(String state) {
		this.state = state;
    }

	/**
	* 获取字段remark1的值，该字段的<br>
	* 字段名称 :备用1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getremark1() {
		return remark1;
	}

	/**
	* 设置字段remark1的值，该字段的<br>
	* 字段名称 :备用1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setremark1(String remark1) {
		this.remark1 = remark1;
    }

	/**
	* 获取字段remark2的值，该字段的<br>
	* 字段名称 :备用2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getremark2() {
		return remark2;
	}

	/**
	* 设置字段remark2的值，该字段的<br>
	* 字段名称 :备用2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setremark2(String remark2) {
		this.remark2 = remark2;
    }

}