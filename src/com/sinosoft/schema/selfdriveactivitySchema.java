package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：selfdriveactivity<br>
 * 表代码：selfdriveactivity<br>
 * 表主键：id<br>
 */
public class selfdriveactivitySchema extends Schema {
	private String id;

	private String realname;

	private String mobileno;

	private String email;

	private String idcode;

	private Date createdate;

	private Date modifydate;

	private String prop1;

	private String prop2;

	private String prop3;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("realname", DataColumn.STRING, 1, 20 , 0 , false , false),
		new SchemaColumn("mobileno", DataColumn.STRING, 2, 20 , 0 , false , false),
		new SchemaColumn("email", DataColumn.STRING, 3, 32 , 0 , false , false),
		new SchemaColumn("idcode", DataColumn.STRING, 4, 20 , 0 , false , false),
		new SchemaColumn("createdate", DataColumn.DATETIME, 5, 0 , 0 , false , false),
		new SchemaColumn("modifydate", DataColumn.DATETIME, 6, 0 , 0 , false , false),
		new SchemaColumn("prop1", DataColumn.STRING, 7, 50 , 0 , false , false),
		new SchemaColumn("prop2", DataColumn.STRING, 8, 50 , 0 , false , false),
		new SchemaColumn("prop3", DataColumn.STRING, 9, 100 , 0 , false , false)
	};

	public static final String _TableCode = "selfdriveactivity";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into selfdriveactivity values(?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update selfdriveactivity set id=?,realname=?,mobileno=?,email=?,idcode=?,createdate=?,modifydate=?,prop1=?,prop2=?,prop3=? where id=?";

	protected static final String _DeleteSQL = "delete from selfdriveactivity  where id=?";

	protected static final String _FillAllSQL = "select * from selfdriveactivity  where id=?";

	public selfdriveactivitySchema(){
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
		return new selfdriveactivitySchema();
	}

	protected SchemaSet newSet(){
		return new selfdriveactivitySet();
	}

	public selfdriveactivitySet query() {
		return query(null, -1, -1);
	}

	public selfdriveactivitySet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public selfdriveactivitySet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public selfdriveactivitySet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (selfdriveactivitySet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){realname = (String)v;return;}
		if (i == 2){mobileno = (String)v;return;}
		if (i == 3){email = (String)v;return;}
		if (i == 4){idcode = (String)v;return;}
		if (i == 5){createdate = (Date)v;return;}
		if (i == 6){modifydate = (Date)v;return;}
		if (i == 7){prop1 = (String)v;return;}
		if (i == 8){prop2 = (String)v;return;}
		if (i == 9){prop3 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return realname;}
		if (i == 2){return mobileno;}
		if (i == 3){return email;}
		if (i == 4){return idcode;}
		if (i == 5){return createdate;}
		if (i == 6){return modifydate;}
		if (i == 7){return prop1;}
		if (i == 8){return prop2;}
		if (i == 9){return prop3;}
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
	* 获取字段realname的值，该字段的<br>
	* 字段名称 :姓名<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrealname() {
		return realname;
	}

	/**
	* 设置字段realname的值，该字段的<br>
	* 字段名称 :姓名<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrealname(String realname) {
		this.realname = realname;
    }

	/**
	* 获取字段mobileno的值，该字段的<br>
	* 字段名称 :电话<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getmobileno() {
		return mobileno;
	}

	/**
	* 设置字段mobileno的值，该字段的<br>
	* 字段名称 :电话<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmobileno(String mobileno) {
		this.mobileno = mobileno;
    }

	/**
	* 获取字段email的值，该字段的<br>
	* 字段名称 :邮箱<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getemail() {
		return email;
	}

	/**
	* 设置字段email的值，该字段的<br>
	* 字段名称 :邮箱<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setemail(String email) {
		this.email = email;
    }

	/**
	* 获取字段idcode的值，该字段的<br>
	* 字段名称 :身份证号<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getidcode() {
		return idcode;
	}

	/**
	* 设置字段idcode的值，该字段的<br>
	* 字段名称 :身份证号<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setidcode(String idcode) {
		this.idcode = idcode;
    }

	/**
	* 获取字段createdate的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getcreatedate() {
		return createdate;
	}

	/**
	* 设置字段createdate的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcreatedate(Date createdate) {
		this.createdate = createdate;
    }

	/**
	* 获取字段modifydate的值，该字段的<br>
	* 字段名称 :修改时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getmodifydate() {
		return modifydate;
	}

	/**
	* 设置字段modifydate的值，该字段的<br>
	* 字段名称 :修改时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmodifydate(Date modifydate) {
		this.modifydate = modifydate;
    }

	/**
	* 获取字段prop1的值，该字段的<br>
	* 字段名称 :prop1<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop1() {
		return prop1;
	}

	/**
	* 设置字段prop1的值，该字段的<br>
	* 字段名称 :prop1<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop1(String prop1) {
		this.prop1 = prop1;
    }

	/**
	* 获取字段prop2的值，该字段的<br>
	* 字段名称 :prop2<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop2() {
		return prop2;
	}

	/**
	* 设置字段prop2的值，该字段的<br>
	* 字段名称 :prop2<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop2(String prop2) {
		this.prop2 = prop2;
    }

	/**
	* 获取字段prop3的值，该字段的<br>
	* 字段名称 :prop3<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop3() {
		return prop3;
	}

	/**
	* 设置字段prop3的值，该字段的<br>
	* 字段名称 :prop3<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop3(String prop3) {
		this.prop3 = prop3;
    }

}