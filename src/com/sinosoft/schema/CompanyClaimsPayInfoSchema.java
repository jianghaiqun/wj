package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

import java.util.Date;

import java.util.Date;

/**
 * 表名称：保险公司赔付信息<br>
 * 表代码：CompanyClaimsPayInfo<br>
 * 表主键：id<br>
 */
public class CompanyClaimsPayInfoSchema extends Schema {
	private String id;

	private String notificationNo;

	private String claimRef;

	private String payItem;

	private String payAmount;

	private String payAccountName;

	private String payTime;

	private String remark1;

	private String remark2;

	private String remark3;

	private String createUser;

	private Date createDate;

	private String modifyUser;

	private Date modifyDate;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("notificationNo", DataColumn.STRING, 1, 100 , 0 , false , false),
		new SchemaColumn("claimRef", DataColumn.STRING, 2, 100 , 0 , false , false),
		new SchemaColumn("payItem", DataColumn.STRING, 3, 100 , 0 , false , false),
		new SchemaColumn("payAmount", DataColumn.STRING, 4, 20 , 0 , false , false),
		new SchemaColumn("payAccountName", DataColumn.STRING, 5, 255 , 0 , false , false),
		new SchemaColumn("payTime", DataColumn.STRING, 6, 20 , 0 , false , false),
		new SchemaColumn("remark1", DataColumn.STRING, 7, 255 , 0 , false , false),
		new SchemaColumn("remark2", DataColumn.STRING, 8, 255 , 0 , false , false),
		new SchemaColumn("remark3", DataColumn.STRING, 9, 255 , 0 , false , false),
		new SchemaColumn("createUser", DataColumn.STRING, 10, 255 , 0 , false , false),
		new SchemaColumn("createDate", DataColumn.DATETIME, 11, 0 , 0 , false , false),
		new SchemaColumn("modifyUser", DataColumn.STRING, 12, 255 , 0 , false , false),
		new SchemaColumn("modifyDate", DataColumn.DATETIME, 13, 0 , 0 , false , false)
	};

	public static final String _TableCode = "CompanyClaimsPayInfo";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into CompanyClaimsPayInfo values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update CompanyClaimsPayInfo set id=?,notificationNo=?,claimRef=?,payItem=?,payAmount=?,payAccountName=?,payTime=?,remark1=?,remark2=?,remark3=?,createUser=?,createDate=?,modifyUser=?,modifyDate=? where id=?";

	protected static final String _DeleteSQL = "delete from CompanyClaimsPayInfo  where id=?";

	protected static final String _FillAllSQL = "select * from CompanyClaimsPayInfo  where id=?";

	public CompanyClaimsPayInfoSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[14];
	}

	protected Schema newInstance(){
		return new CompanyClaimsPayInfoSchema();
	}

	protected SchemaSet newSet(){
		return new CompanyClaimsPayInfoSet();
	}

	public CompanyClaimsPayInfoSet query() {
		return query(null, -1, -1);
	}

	public CompanyClaimsPayInfoSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public CompanyClaimsPayInfoSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public CompanyClaimsPayInfoSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (CompanyClaimsPayInfoSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){notificationNo = (String)v;return;}
		if (i == 2){claimRef = (String)v;return;}
		if (i == 3){payItem = (String)v;return;}
		if (i == 4){payAmount = (String)v;return;}
		if (i == 5){payAccountName = (String)v;return;}
		if (i == 6){payTime = (String)v;return;}
		if (i == 7){remark1 = (String)v;return;}
		if (i == 8){remark2 = (String)v;return;}
		if (i == 9){remark3 = (String)v;return;}
		if (i == 10){createUser = (String)v;return;}
		if (i == 11){createDate = (Date)v;return;}
		if (i == 12){modifyUser = (String)v;return;}
		if (i == 13){modifyDate = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return notificationNo;}
		if (i == 2){return claimRef;}
		if (i == 3){return payItem;}
		if (i == 4){return payAmount;}
		if (i == 5){return payAccountName;}
		if (i == 6){return payTime;}
		if (i == 7){return remark1;}
		if (i == 8){return remark2;}
		if (i == 9){return remark3;}
		if (i == 10){return createUser;}
		if (i == 11){return createDate;}
		if (i == 12){return modifyUser;}
		if (i == 13){return modifyDate;}
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
	* 获取字段notificationNo的值，该字段的<br>
	* 字段名称 :保险公司报案号<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getnotificationNo() {
		return notificationNo;
	}

	/**
	* 设置字段notificationNo的值，该字段的<br>
	* 字段名称 :保险公司报案号<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setnotificationNo(String notificationNo) {
		this.notificationNo = notificationNo;
    }

	/**
	* 获取字段claimRef的值，该字段的<br>
	* 字段名称 :立案号<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getclaimRef() {
		return claimRef;
	}

	/**
	* 设置字段claimRef的值，该字段的<br>
	* 字段名称 :立案号<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setclaimRef(String claimRef) {
		this.claimRef = claimRef;
    }

	/**
	* 获取字段payItem的值，该字段的<br>
	* 字段名称 :赔付保障项目<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getpayItem() {
		return payItem;
	}

	/**
	* 设置字段payItem的值，该字段的<br>
	* 字段名称 :赔付保障项目<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpayItem(String payItem) {
		this.payItem = payItem;
    }

	/**
	* 获取字段payAmount的值，该字段的<br>
	* 字段名称 :赔付金额<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getpayAmount() {
		return payAmount;
	}

	/**
	* 设置字段payAmount的值，该字段的<br>
	* 字段名称 :赔付金额<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpayAmount(String payAmount) {
		this.payAmount = payAmount;
    }

	/**
	* 获取字段payAccountName的值，该字段的<br>
	* 字段名称 :支付账户名称<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getpayAccountName() {
		return payAccountName;
	}

	/**
	* 设置字段payAccountName的值，该字段的<br>
	* 字段名称 :支付账户名称<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpayAccountName(String payAccountName) {
		this.payAccountName = payAccountName;
    }

	/**
	* 获取字段payTime的值，该字段的<br>
	* 字段名称 :支付日期<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getpayTime() {
		return payTime;
	}

	/**
	* 设置字段payTime的值，该字段的<br>
	* 字段名称 :支付日期<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpayTime(String payTime) {
		this.payTime = payTime;
    }

	/**
	* 获取字段remark1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getremark1() {
		return remark1;
	}

	/**
	* 设置字段remark1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setremark1(String remark1) {
		this.remark1 = remark1;
    }

	/**
	* 获取字段remark2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getremark2() {
		return remark2;
	}

	/**
	* 设置字段remark2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setremark2(String remark2) {
		this.remark2 = remark2;
    }

	/**
	* 获取字段remark3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getremark3() {
		return remark3;
	}

	/**
	* 设置字段remark3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setremark3(String remark3) {
		this.remark3 = remark3;
    }

	/**
	* 获取字段createUser的值，该字段的<br>
	* 字段名称 :创建人<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcreateUser() {
		return createUser;
	}

	/**
	* 设置字段createUser的值，该字段的<br>
	* 字段名称 :创建人<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcreateUser(String createUser) {
		this.createUser = createUser;
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
	* 获取字段modifyUser的值，该字段的<br>
	* 字段名称 :修改人<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getmodifyUser() {
		return modifyUser;
	}

	/**
	* 设置字段modifyUser的值，该字段的<br>
	* 字段名称 :修改人<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmodifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
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

}