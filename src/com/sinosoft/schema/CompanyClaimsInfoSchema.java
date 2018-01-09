package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：保险公司理赔进度信息表<br>
 * 表代码：CompanyClaimsInfo<br>
 * 表主键：claimsNo<br>
 */
public class CompanyClaimsInfoSchema extends Schema {
	private String claimsNo;

	private String policyNo;

	private String notificationNo;

	private String claimRef;

	private String claimStatus;

	private String claimDescribe;

	private String claimTime;

	private String isUpload;

	private String remark1;

	private String remark2;

	private String remark3;

	private String remark4;

	private String remark5;

	private String createUser;

	private Date createDate;

	private String modifyUser;

	private Date modifyDate;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("claimsNo", DataColumn.STRING, 0, 100 , 0 , true , true),
		new SchemaColumn("policyNo", DataColumn.STRING, 1, 100 , 0 , false , false),
		new SchemaColumn("notificationNo", DataColumn.STRING, 2, 100 , 0 , false , false),
		new SchemaColumn("claimRef", DataColumn.STRING, 3, 100 , 0 , false , false),
		new SchemaColumn("claimStatus", DataColumn.STRING, 4, 2 , 0 , false , false),
		new SchemaColumn("claimDescribe", DataColumn.STRING, 5, 1000 , 0 , false , false),
		new SchemaColumn("claimTime", DataColumn.STRING, 6, 20 , 0 , false , false),
		new SchemaColumn("isUpload", DataColumn.STRING, 7, 2 , 0 , false , false),
		new SchemaColumn("remark1", DataColumn.STRING, 8, 255 , 0 , false , false),
		new SchemaColumn("remark2", DataColumn.STRING, 9, 255 , 0 , false , false),
		new SchemaColumn("remark3", DataColumn.STRING, 10, 255 , 0 , false , false),
		new SchemaColumn("remark4", DataColumn.STRING, 11, 255 , 0 , false , false),
		new SchemaColumn("remark5", DataColumn.STRING, 12, 255 , 0 , false , false),
		new SchemaColumn("createUser", DataColumn.STRING, 13, 255 , 0 , false , false),
		new SchemaColumn("createDate", DataColumn.DATETIME, 14, 0 , 0 , false , false),
		new SchemaColumn("modifyUser", DataColumn.STRING, 15, 255 , 0 , false , false),
		new SchemaColumn("modifyDate", DataColumn.DATETIME, 16, 0 , 0 , false , false)
	};

	public static final String _TableCode = "CompanyClaimsInfo";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into CompanyClaimsInfo values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update CompanyClaimsInfo set claimsNo=?,policyNo=?,notificationNo=?,claimRef=?,claimStatus=?,claimDescribe=?,claimTime=?,isUpload=?,remark1=?,remark2=?,remark3=?,remark4=?,remark5=?,createUser=?,createDate=?,modifyUser=?,modifyDate=? where claimsNo=?";

	protected static final String _DeleteSQL = "delete from CompanyClaimsInfo  where claimsNo=?";

	protected static final String _FillAllSQL = "select * from CompanyClaimsInfo  where claimsNo=?";

	public CompanyClaimsInfoSchema(){
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
		return new CompanyClaimsInfoSchema();
	}

	protected SchemaSet newSet(){
		return new CompanyClaimsInfoSet();
	}

	public CompanyClaimsInfoSet query() {
		return query(null, -1, -1);
	}

	public CompanyClaimsInfoSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public CompanyClaimsInfoSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public CompanyClaimsInfoSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (CompanyClaimsInfoSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){claimsNo = (String)v;return;}
		if (i == 1){policyNo = (String)v;return;}
		if (i == 2){notificationNo = (String)v;return;}
		if (i == 3){claimRef = (String)v;return;}
		if (i == 4){claimStatus = (String)v;return;}
		if (i == 5){claimDescribe = (String)v;return;}
		if (i == 6){claimTime = (String)v;return;}
		if (i == 7){isUpload = (String)v;return;}
		if (i == 8){remark1 = (String)v;return;}
		if (i == 9){remark2 = (String)v;return;}
		if (i == 10){remark3 = (String)v;return;}
		if (i == 11){remark4 = (String)v;return;}
		if (i == 12){remark5 = (String)v;return;}
		if (i == 13){createUser = (String)v;return;}
		if (i == 14){createDate = (Date)v;return;}
		if (i == 15){modifyUser = (String)v;return;}
		if (i == 16){modifyDate = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return claimsNo;}
		if (i == 1){return policyNo;}
		if (i == 2){return notificationNo;}
		if (i == 3){return claimRef;}
		if (i == 4){return claimStatus;}
		if (i == 5){return claimDescribe;}
		if (i == 6){return claimTime;}
		if (i == 7){return isUpload;}
		if (i == 8){return remark1;}
		if (i == 9){return remark2;}
		if (i == 10){return remark3;}
		if (i == 11){return remark4;}
		if (i == 12){return remark5;}
		if (i == 13){return createUser;}
		if (i == 14){return createDate;}
		if (i == 15){return modifyUser;}
		if (i == 16){return modifyDate;}
		return null;
	}

	/**
	* 获取字段claimsNo的值，该字段的<br>
	* 字段名称 :理赔单号<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getclaimsNo() {
		return claimsNo;
	}

	/**
	* 设置字段claimsNo的值，该字段的<br>
	* 字段名称 :理赔单号<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setclaimsNo(String claimsNo) {
		this.claimsNo = claimsNo;
    }

	/**
	* 获取字段policyNo的值，该字段的<br>
	* 字段名称 :保单号<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getpolicyNo() {
		return policyNo;
	}

	/**
	* 设置字段policyNo的值，该字段的<br>
	* 字段名称 :保单号<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpolicyNo(String policyNo) {
		this.policyNo = policyNo;
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
	* 获取字段claimStatus的值，该字段的<br>
	* 字段名称 :理赔状态<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getclaimStatus() {
		return claimStatus;
	}

	/**
	* 设置字段claimStatus的值，该字段的<br>
	* 字段名称 :理赔状态<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setclaimStatus(String claimStatus) {
		this.claimStatus = claimStatus;
    }

	/**
	* 获取字段claimDescribe的值，该字段的<br>
	* 字段名称 :理赔状态描述<br>
	* 数据类型 :varchar(1000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getclaimDescribe() {
		return claimDescribe;
	}

	/**
	* 设置字段claimDescribe的值，该字段的<br>
	* 字段名称 :理赔状态描述<br>
	* 数据类型 :varchar(1000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setclaimDescribe(String claimDescribe) {
		this.claimDescribe = claimDescribe;
    }

	/**
	* 获取字段claimTime的值，该字段的<br>
	* 字段名称 :处理时间<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getclaimTime() {
		return claimTime;
	}

	/**
	* 设置字段claimTime的值，该字段的<br>
	* 字段名称 :处理时间<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setclaimTime(String claimTime) {
		this.claimTime = claimTime;
    }

	/**
	* 获取字段isUpload的值，该字段的<br>
	* 字段名称 :是否已上传文件<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getisUpload() {
		return isUpload;
	}

	/**
	* 设置字段isUpload的值，该字段的<br>
	* 字段名称 :是否已上传文件<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setisUpload(String isUpload) {
		this.isUpload = isUpload;
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
	* 获取字段remark4的值，该字段的<br>
	* 字段名称 :备用字段4<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getremark4() {
		return remark4;
	}

	/**
	* 设置字段remark4的值，该字段的<br>
	* 字段名称 :备用字段4<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setremark4(String remark4) {
		this.remark4 = remark4;
    }

	/**
	* 获取字段remark5的值，该字段的<br>
	* 字段名称 :备用字段5<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getremark5() {
		return remark5;
	}

	/**
	* 设置字段remark5的值，该字段的<br>
	* 字段名称 :备用字段5<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setremark5(String remark5) {
		this.remark5 = remark5;
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