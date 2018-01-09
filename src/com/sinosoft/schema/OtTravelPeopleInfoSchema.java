package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

import java.util.Date;

import java.util.Date;

import java.util.Date;

import java.util.Date;

import java.util.Date;

/**
 * 表名称：出行人信息<br>
 * 表代码：OtTravelPeopleInfo<br>
 * 表主键：id<br>
 */
public class OtTravelPeopleInfoSchema extends Schema {
	private String id;

	private String orderSn;

	private String travelName;

	private String travelEnName;

	private String identityType;

	private String identityTypeName;

	private String identityId;

	private String identityStartDate;

	private String identityEndDate;

	private String sex;

	private String sexName;

	private String birthday;

	private String mobile;

	private String email;

	private String prop1;

	private String prop2;

	private String prop3;

	private String createUser;

	private Date createDate;

	private String modifyUser;

	private Date modifyDate;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("orderSn", DataColumn.STRING, 1, 20 , 0 , false , false),
		new SchemaColumn("travelName", DataColumn.STRING, 2, 50 , 0 , false , false),
		new SchemaColumn("travelEnName", DataColumn.STRING, 3, 200 , 0 , false , false),
		new SchemaColumn("identityType", DataColumn.STRING, 4, 10 , 0 , false , false),
		new SchemaColumn("identityTypeName", DataColumn.STRING, 5, 50 , 0 , false , false),
		new SchemaColumn("identityId", DataColumn.STRING, 6, 255 , 0 , false , false),
		new SchemaColumn("identityStartDate", DataColumn.STRING, 7, 50 , 0 , false , false),
		new SchemaColumn("identityEndDate", DataColumn.STRING, 8, 50 , 0 , false , false),
		new SchemaColumn("sex", DataColumn.STRING, 9, 2 , 0 , false , false),
		new SchemaColumn("sexName", DataColumn.STRING, 10, 10 , 0 , false , false),
		new SchemaColumn("birthday", DataColumn.STRING, 11, 20 , 0 , false , false),
		new SchemaColumn("mobile", DataColumn.STRING, 12, 13 , 0 , false , false),
		new SchemaColumn("email", DataColumn.STRING, 13, 50 , 0 , false , false),
		new SchemaColumn("prop1", DataColumn.STRING, 14, 255 , 0 , false , false),
		new SchemaColumn("prop2", DataColumn.STRING, 15, 255 , 0 , false , false),
		new SchemaColumn("prop3", DataColumn.STRING, 16, 255 , 0 , false , false),
		new SchemaColumn("createUser", DataColumn.STRING, 17, 255 , 0 , false , false),
		new SchemaColumn("createDate", DataColumn.DATETIME, 18, 0 , 0 , false , false),
		new SchemaColumn("modifyUser", DataColumn.STRING, 19, 255 , 0 , false , false),
		new SchemaColumn("modifyDate", DataColumn.DATETIME, 20, 0 , 0 , false , false)
	};

	public static final String _TableCode = "OtTravelPeopleInfo";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into OtTravelPeopleInfo values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update OtTravelPeopleInfo set id=?,orderSn=?,travelName=?,travelEnName=?,identityType=?,identityTypeName=?,identityId=?,identityStartDate=?,identityEndDate=?,sex=?,sexName=?,birthday=?,mobile=?,email=?,prop1=?,prop2=?,prop3=?,createUser=?,createDate=?,modifyUser=?,modifyDate=? where id=?";

	protected static final String _DeleteSQL = "delete from OtTravelPeopleInfo  where id=?";

	protected static final String _FillAllSQL = "select * from OtTravelPeopleInfo  where id=?";

	public OtTravelPeopleInfoSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[21];
	}

	protected Schema newInstance(){
		return new OtTravelPeopleInfoSchema();
	}

	protected SchemaSet newSet(){
		return new OtTravelPeopleInfoSet();
	}

	public OtTravelPeopleInfoSet query() {
		return query(null, -1, -1);
	}

	public OtTravelPeopleInfoSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public OtTravelPeopleInfoSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public OtTravelPeopleInfoSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (OtTravelPeopleInfoSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){orderSn = (String)v;return;}
		if (i == 2){travelName = (String)v;return;}
		if (i == 3){travelEnName = (String)v;return;}
		if (i == 4){identityType = (String)v;return;}
		if (i == 5){identityTypeName = (String)v;return;}
		if (i == 6){identityId = (String)v;return;}
		if (i == 7){identityStartDate = (String)v;return;}
		if (i == 8){identityEndDate = (String)v;return;}
		if (i == 9){sex = (String)v;return;}
		if (i == 10){sexName = (String)v;return;}
		if (i == 11){birthday = (String)v;return;}
		if (i == 12){mobile = (String)v;return;}
		if (i == 13){email = (String)v;return;}
		if (i == 14){prop1 = (String)v;return;}
		if (i == 15){prop2 = (String)v;return;}
		if (i == 16){prop3 = (String)v;return;}
		if (i == 17){createUser = (String)v;return;}
		if (i == 18){createDate = (Date)v;return;}
		if (i == 19){modifyUser = (String)v;return;}
		if (i == 20){modifyDate = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return orderSn;}
		if (i == 2){return travelName;}
		if (i == 3){return travelEnName;}
		if (i == 4){return identityType;}
		if (i == 5){return identityTypeName;}
		if (i == 6){return identityId;}
		if (i == 7){return identityStartDate;}
		if (i == 8){return identityEndDate;}
		if (i == 9){return sex;}
		if (i == 10){return sexName;}
		if (i == 11){return birthday;}
		if (i == 12){return mobile;}
		if (i == 13){return email;}
		if (i == 14){return prop1;}
		if (i == 15){return prop2;}
		if (i == 16){return prop3;}
		if (i == 17){return createUser;}
		if (i == 18){return createDate;}
		if (i == 19){return modifyUser;}
		if (i == 20){return modifyDate;}
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
	* 获取字段orderSn的值，该字段的<br>
	* 字段名称 :订单编码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getorderSn() {
		return orderSn;
	}

	/**
	* 设置字段orderSn的值，该字段的<br>
	* 字段名称 :订单编码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setorderSn(String orderSn) {
		this.orderSn = orderSn;
    }

	/**
	* 获取字段travelName的值，该字段的<br>
	* 字段名称 :中文名<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String gettravelName() {
		return travelName;
	}

	/**
	* 设置字段travelName的值，该字段的<br>
	* 字段名称 :中文名<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void settravelName(String travelName) {
		this.travelName = travelName;
    }

	/**
	* 获取字段travelEnName的值，该字段的<br>
	* 字段名称 :英文名<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String gettravelEnName() {
		return travelEnName;
	}

	/**
	* 设置字段travelEnName的值，该字段的<br>
	* 字段名称 :英文名<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void settravelEnName(String travelEnName) {
		this.travelEnName = travelEnName;
    }

	/**
	* 获取字段identityType的值，该字段的<br>
	* 字段名称 :证件类型编码<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getidentityType() {
		return identityType;
	}

	/**
	* 设置字段identityType的值，该字段的<br>
	* 字段名称 :证件类型编码<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setidentityType(String identityType) {
		this.identityType = identityType;
    }

	/**
	* 获取字段identityTypeName的值，该字段的<br>
	* 字段名称 :证件类型名<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getidentityTypeName() {
		return identityTypeName;
	}

	/**
	* 设置字段identityTypeName的值，该字段的<br>
	* 字段名称 :证件类型名<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setidentityTypeName(String identityTypeName) {
		this.identityTypeName = identityTypeName;
    }

	/**
	* 获取字段identityId的值，该字段的<br>
	* 字段名称 :证件号<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getidentityId() {
		return identityId;
	}

	/**
	* 设置字段identityId的值，该字段的<br>
	* 字段名称 :证件号<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setidentityId(String identityId) {
		this.identityId = identityId;
    }

	/**
	* 获取字段identityStartDate的值，该字段的<br>
	* 字段名称 :证件有效起期<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getidentityStartDate() {
		return identityStartDate;
	}

	/**
	* 设置字段identityStartDate的值，该字段的<br>
	* 字段名称 :证件有效起期<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setidentityStartDate(String identityStartDate) {
		this.identityStartDate = identityStartDate;
    }

	/**
	* 获取字段identityEndDate的值，该字段的<br>
	* 字段名称 :证件有效止期<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getidentityEndDate() {
		return identityEndDate;
	}

	/**
	* 设置字段identityEndDate的值，该字段的<br>
	* 字段名称 :证件有效止期<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setidentityEndDate(String identityEndDate) {
		this.identityEndDate = identityEndDate;
    }

	/**
	* 获取字段sex的值，该字段的<br>
	* 字段名称 :性别编码<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getsex() {
		return sex;
	}

	/**
	* 设置字段sex的值，该字段的<br>
	* 字段名称 :性别编码<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setsex(String sex) {
		this.sex = sex;
    }

	/**
	* 获取字段sexName的值，该字段的<br>
	* 字段名称 :性别名称<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getsexName() {
		return sexName;
	}

	/**
	* 设置字段sexName的值，该字段的<br>
	* 字段名称 :性别名称<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setsexName(String sexName) {
		this.sexName = sexName;
    }

	/**
	* 获取字段birthday的值，该字段的<br>
	* 字段名称 :出生日期<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getbirthday() {
		return birthday;
	}

	/**
	* 设置字段birthday的值，该字段的<br>
	* 字段名称 :出生日期<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setbirthday(String birthday) {
		this.birthday = birthday;
    }

	/**
	* 获取字段mobile的值，该字段的<br>
	* 字段名称 :手机号<br>
	* 数据类型 :varchar(13)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getmobile() {
		return mobile;
	}

	/**
	* 设置字段mobile的值，该字段的<br>
	* 字段名称 :手机号<br>
	* 数据类型 :varchar(13)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmobile(String mobile) {
		this.mobile = mobile;
    }

	/**
	* 获取字段email的值，该字段的<br>
	* 字段名称 :邮箱<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getemail() {
		return email;
	}

	/**
	* 设置字段email的值，该字段的<br>
	* 字段名称 :邮箱<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setemail(String email) {
		this.email = email;
    }

	/**
	* 获取字段prop1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop1() {
		return prop1;
	}

	/**
	* 设置字段prop1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop1(String prop1) {
		this.prop1 = prop1;
    }

	/**
	* 获取字段prop2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop2() {
		return prop2;
	}

	/**
	* 设置字段prop2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop2(String prop2) {
		this.prop2 = prop2;
    }

	/**
	* 获取字段prop3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop3() {
		return prop3;
	}

	/**
	* 设置字段prop3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop3(String prop3) {
		this.prop3 = prop3;
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