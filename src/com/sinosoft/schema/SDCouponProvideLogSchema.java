package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.orm.SchemaSet;

/**
 * 表名称：SDCouponProvideLog<br>
 * 表代码：SDCouponProvideLog<br>
 * 表主键：id<br>
 */
public class SDCouponProvideLogSchema extends Schema {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2929382468778560413L;

	private String couponsn;

	private String mail;

	private String type;

	private String describe;

	private String prop1;

	private String prop2;

	private String prop3;

	private String modifyDate;

	private String createDate;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("couponsn", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("mail", DataColumn.STRING, 1, 100 , 0 , false , false),
		new SchemaColumn("type", DataColumn.STRING, 2, 10 , 0 , false , false),
		new SchemaColumn("describe", DataColumn.STRING, 3, 100 , 0 , false , false),
		new SchemaColumn("prop1", DataColumn.STRING, 4, 255 , 0 , false , false),
		new SchemaColumn("prop2", DataColumn.STRING, 5, 255 , 0 , false , false),
		new SchemaColumn("prop3", DataColumn.STRING, 6, 255 , 0 , false , false),
		new SchemaColumn("modifyDate", DataColumn.STRING, 7, 20 , 0 , false , false),
		new SchemaColumn("createDate", DataColumn.STRING, 8, 20 , 0 , false , false)
	};

	public static final String _TableCode = "SDCouponProvideLog";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into SDCouponProvideLog values(?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update SDCouponProvideLog set couponsn=?,mail=?,type=?,describe=?,prop1=?,prop2=?,prop3=?,modifyDate=?,createDate=? where couponsn=?";

	protected static final String _DeleteSQL = "delete from SDCouponProvideLog  where couponsn=?";

	protected static final String _FillAllSQL = "select * from SDCouponProvideLog  where couponsn=?";

	public SDCouponProvideLogSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[9];
	}

	protected Schema newInstance(){
		return new SDCouponProvideLogSchema();
	}

	protected SchemaSet newSet(){
		return new SDCouponProvideLogSet();
	}

	public SDCouponProvideLogSet query() {
		return query(null, -1, -1);
	}

	public SDCouponProvideLogSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public SDCouponProvideLogSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public SDCouponProvideLogSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (SDCouponProvideLogSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){couponsn = (String)v;return;}
		if (i == 1){mail = (String)v;return;}
		if (i == 2){type = (String)v;return;}
		if (i == 3){describe = (String)v;return;}
		if (i == 4){prop1 = (String)v;return;}
		if (i == 5){prop2 = (String)v;return;}
		if (i == 6){prop3 = (String)v;return;}
		if (i == 7){modifyDate = (String)v;return;}
		if (i == 8){createDate = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return couponsn;}
		if (i == 1){return mail;}
		if (i == 2){return type;}
		if (i == 3){return describe;}
		if (i == 4){return prop1;}
		if (i == 5){return prop2;}
		if (i == 6){return prop3;}
		if (i == 7){return modifyDate;}
		if (i == 8){return createDate;}
		return null;
	}

	/**
	* 获取字段couponsn的值，该字段的<br>
	* 字段名称 :couponsn<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getcouponsn() {
		return couponsn;
	}

	/**
	* 设置字段id的值，该字段的<br>
	* 字段名称 :couponsn<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setcouponsn(String couponsn) {
		this.couponsn = couponsn;
    }

	/**
	* 获取字段mail的值，该字段的<br>
	* 字段名称 :邮箱<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getmail() {
		return mail;
	}

	/**
	* 设置字段mail的值，该字段的<br>
	* 字段名称 :邮箱<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmail(String mail) {
		this.mail = mail;
    }

	/**
	* 获取字段type的值，该字段的<br>
	* 字段名称 :发放方式<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String gettype() {
		return type;
	}

	/**
	* 设置字段type的值，该字段的<br>
	* 字段名称 :发放方式<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void settype(String type) {
		this.type = type;
    }

	/**
	* 获取字段describe的值，该字段的<br>
	* 字段名称 :发放方式描述<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getdescribe() {
		return describe;
	}

	/**
	* 设置字段describe的值，该字段的<br>
	* 字段名称 :发放方式描述<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setdescribe(String describe) {
		this.describe = describe;
    }

	/**
	* 获取字段prop1的值，该字段的<br>
	* 字段名称 :备注1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop1() {
		return prop1;
	}

	/**
	* 设置字段prop1的值，该字段的<br>
	* 字段名称 :备注1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop1(String prop1) {
		this.prop1 = prop1;
    }

	/**
	* 获取字段prop2的值，该字段的<br>
	* 字段名称 :备注2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop2() {
		return prop2;
	}

	/**
	* 设置字段prop2的值，该字段的<br>
	* 字段名称 :备注2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop2(String prop2) {
		this.prop2 = prop2;
    }

	/**
	* 获取字段prop3的值，该字段的<br>
	* 字段名称 :备注3<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop3() {
		return prop3;
	}

	/**
	* 设置字段prop3的值，该字段的<br>
	* 字段名称 :备注3<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop3(String prop3) {
		this.prop3 = prop3;
    }

	/**
	* 获取字段modifyDate的值，该字段的<br>
	* 字段名称 :更改时间<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getmodifyDate() {
		return modifyDate;
	}

	/**
	* 设置字段modifyDate的值，该字段的<br>
	* 字段名称 :更改时间<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmodifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
    }

	/**
	* 获取字段createDate的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcreateDate() {
		return createDate;
	}

	/**
	* 设置字段createDate的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcreateDate(String createDate) {
		this.createDate = createDate;
    }

}