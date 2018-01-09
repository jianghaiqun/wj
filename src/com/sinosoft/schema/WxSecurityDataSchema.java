package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：微信缺口保障数据统计表<br>
 * 表代码：WxSecurityData<br>
 * 表主键：id<br>
 */
public class WxSecurityDataSchema extends Schema {
	private String id;

	private String type;

	private String openid;

	private String mobileNo;

	private String couponSn;

	private String prop1;

	private String prop2;

	private String prop3;

	private String prop4;

	private Date createdate;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("type", DataColumn.STRING, 1, 4 , 0 , false , false),
		new SchemaColumn("openid", DataColumn.STRING, 2, 100 , 0 , false , false),
		new SchemaColumn("mobileNo", DataColumn.STRING, 3, 20 , 0 , false , false),
		new SchemaColumn("couponSn", DataColumn.STRING, 4, 96 , 0 , false , false),
		new SchemaColumn("prop1", DataColumn.STRING, 5, 32 , 0 , false , false),
		new SchemaColumn("prop2", DataColumn.STRING, 6, 32 , 0 , false , false),
		new SchemaColumn("prop3", DataColumn.STRING, 7, 32 , 0 , false , false),
		new SchemaColumn("prop4", DataColumn.STRING, 8, 100 , 0 , false , false),
		new SchemaColumn("createdate", DataColumn.DATETIME, 9, 0 , 0 , false , false)
	};

	public static final String _TableCode = "WxSecurityData";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into WxSecurityData values(?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update WxSecurityData set id=?,type=?,openid=?,mobileNo=?,couponSn=?,prop1=?,prop2=?,prop3=?,prop4=?,createdate=? where id=?";

	protected static final String _DeleteSQL = "delete from WxSecurityData  where id=?";

	protected static final String _FillAllSQL = "select * from WxSecurityData  where id=?";

	public WxSecurityDataSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[10];
	}

	protected Schema newInstance(){
		return new WxSecurityDataSchema();
	}

	protected SchemaSet newSet(){
		return new WxSecurityDataSet();
	}

	public WxSecurityDataSet query() {
		return query(null, -1, -1);
	}

	public WxSecurityDataSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public WxSecurityDataSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public WxSecurityDataSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (WxSecurityDataSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){type = (String)v;return;}
		if (i == 2){openid = (String)v;return;}
		if (i == 3){mobileNo = (String)v;return;}
		if (i == 4){couponSn = (String)v;return;}
		if (i == 5){prop1 = (String)v;return;}
		if (i == 6){prop2 = (String)v;return;}
		if (i == 7){prop3 = (String)v;return;}
		if (i == 8){prop4 = (String)v;return;}
		if (i == 9){createdate = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return type;}
		if (i == 2){return openid;}
		if (i == 3){return mobileNo;}
		if (i == 4){return couponSn;}
		if (i == 5){return prop1;}
		if (i == 6){return prop2;}
		if (i == 7){return prop3;}
		if (i == 8){return prop4;}
		if (i == 9){return createdate;}
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
	* 获取字段type的值，该字段的<br>
	* 字段名称 :数据类型<br>
	* 数据类型 :varchar(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String gettype() {
		return type;
	}

	/**
	* 设置字段type的值，该字段的<br>
	* 字段名称 :数据类型<br>
	* 数据类型 :varchar(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void settype(String type) {
		this.type = type;
    }

	/**
	* 获取字段openid的值，该字段的<br>
	* 字段名称 :openid<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getopenid() {
		return openid;
	}

	/**
	* 设置字段openid的值，该字段的<br>
	* 字段名称 :openid<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setopenid(String openid) {
		this.openid = openid;
    }

	/**
	* 获取字段mobileNo的值，该字段的<br>
	* 字段名称 :手机号<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getmobileNo() {
		return mobileNo;
	}

	/**
	* 设置字段mobileNo的值，该字段的<br>
	* 字段名称 :手机号<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
    }

	/**
	* 获取字段couponSn的值，该字段的<br>
	* 字段名称 :优惠券号<br>
	* 数据类型 :varchar(96)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcouponSn() {
		return couponSn;
	}

	/**
	* 设置字段couponSn的值，该字段的<br>
	* 字段名称 :优惠券号<br>
	* 数据类型 :varchar(96)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcouponSn(String couponSn) {
		this.couponSn = couponSn;
    }

	/**
	* 获取字段prop1的值，该字段的<br>
	* 字段名称 :prop1<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop1() {
		return prop1;
	}

	/**
	* 设置字段prop1的值，该字段的<br>
	* 字段名称 :prop1<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop1(String prop1) {
		this.prop1 = prop1;
    }

	/**
	* 获取字段prop2的值，该字段的<br>
	* 字段名称 :prop2<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop2() {
		return prop2;
	}

	/**
	* 设置字段prop2的值，该字段的<br>
	* 字段名称 :prop2<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop2(String prop2) {
		this.prop2 = prop2;
    }

	/**
	* 获取字段prop3的值，该字段的<br>
	* 字段名称 :prop3<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop3() {
		return prop3;
	}

	/**
	* 设置字段prop3的值，该字段的<br>
	* 字段名称 :prop3<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop3(String prop3) {
		this.prop3 = prop3;
    }

	/**
	* 获取字段prop4的值，该字段的<br>
	* 字段名称 :prop4<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop4() {
		return prop4;
	}

	/**
	* 设置字段prop4的值，该字段的<br>
	* 字段名称 :prop4<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop4(String prop4) {
		this.prop4 = prop4;
    }

	/**
	* 获取字段createdate的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :date<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getcreatedate() {
		return createdate;
	}

	/**
	* 设置字段createdate的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :date<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcreatedate(Date createdate) {
		this.createdate = createdate;
    }

}