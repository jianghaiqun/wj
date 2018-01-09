package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：会员特权<br>
 * 表代码：MemberPrivileges<br>
 * 表主键：id<br>
 */
public class MemberPrivilegesSchema extends Schema {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1525303380497021956L;

	private String id;

	private String PrivilegesName;

	private String MemberLevel;

	private String PgLink;

	private String useFlag;

	private String orderFlag;

	private String content;

	private String description;

	private Date AddTime;

	private String AddUser;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 100 , 0 , true , true),
		new SchemaColumn("PrivilegesName", DataColumn.STRING, 1, 100 , 0 , false , false),
		new SchemaColumn("MemberLevel", DataColumn.STRING, 2, 30 , 0 , false , false),
		new SchemaColumn("PgLink", DataColumn.STRING, 3, 200 , 0 , false , false),
		new SchemaColumn("useFlag", DataColumn.STRING, 4, 1 , 0 , false , false),
		new SchemaColumn("orderFlag", DataColumn.STRING, 5, 3 , 0 , false , false),
		new SchemaColumn("content", DataColumn.STRING, 6, 200 , 0 , false , false),
		new SchemaColumn("description", DataColumn.STRING, 7, 500 , 0 , false , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 8, 0 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 9, 100 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 10, 100 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 11, 100 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 12, 100 , 0 , false , false)
	};

	public static final String _TableCode = "MemberPrivileges";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into MemberPrivileges values(?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update MemberPrivileges set id=?,PrivilegesName=?,MemberLevel=?,PgLink=?,useFlag=?,orderFlag=?,content=?,description=?,AddTime=?,AddUser=?,Prop1=?,Prop2=?,Prop3=? where id=?";

	protected static final String _DeleteSQL = "delete from MemberPrivileges  where id=?";

	protected static final String _FillAllSQL = "select * from MemberPrivileges  where id=?";

	public MemberPrivilegesSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[13];
	}

	protected Schema newInstance(){
		return new MemberPrivilegesSchema();
	}

	protected SchemaSet newSet(){
		return new MemberPrivilegesSet();
	}

	public MemberPrivilegesSet query() {
		return query(null, -1, -1);
	}

	public MemberPrivilegesSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public MemberPrivilegesSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public MemberPrivilegesSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (MemberPrivilegesSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){PrivilegesName = (String)v;return;}
		if (i == 2){MemberLevel = (String)v;return;}
		if (i == 3){PgLink = (String)v;return;}
		if (i == 4){useFlag = (String)v;return;}
		if (i == 5){orderFlag = (String)v;return;}
		if (i == 6){content = (String)v;return;}
		if (i == 7){description = (String)v;return;}
		if (i == 8){AddTime = (Date)v;return;}
		if (i == 9){AddUser = (String)v;return;}
		if (i == 10){Prop1 = (String)v;return;}
		if (i == 11){Prop2 = (String)v;return;}
		if (i == 12){Prop3 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return PrivilegesName;}
		if (i == 2){return MemberLevel;}
		if (i == 3){return PgLink;}
		if (i == 4){return useFlag;}
		if (i == 5){return orderFlag;}
		if (i == 6){return content;}
		if (i == 7){return description;}
		if (i == 8){return AddTime;}
		if (i == 9){return AddUser;}
		if (i == 10){return Prop1;}
		if (i == 11){return Prop2;}
		if (i == 12){return Prop3;}
		return null;
	}

	/**
	* 获取字段id的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getid() {
		return id;
	}

	/**
	* 设置字段id的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setid(String id) {
		this.id = id;
    }

	/**
	* 获取字段PrivilegesName的值，该字段的<br>
	* 字段名称 :特权名称<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPrivilegesName() {
		return PrivilegesName;
	}

	/**
	* 设置字段PrivilegesName的值，该字段的<br>
	* 字段名称 :特权名称<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPrivilegesName(String privilegesName) {
		this.PrivilegesName = privilegesName;
    }

	/**
	* 获取字段MemberLevel的值，该字段的<br>
	* 字段名称 :会员级别<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMemberLevel() {
		return MemberLevel;
	}

	/**
	* 设置字段MemberLevel的值，该字段的<br>
	* 字段名称 :会员级别<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMemberLevel(String memberLevel) {
		this.MemberLevel = memberLevel;
    }

	/**
	* 获取字段PgLink的值，该字段的<br>
	* 字段名称 :展示图片链接<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPgLink() {
		return PgLink;
	}

	/**
	* 设置字段PgLink的值，该字段的<br>
	* 字段名称 :展示图片链接<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPgLink(String pgLink) {
		this.PgLink = pgLink;
    }

	/**
	* 获取字段useFlag的值，该字段的<br>
	* 字段名称 :启用标志<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getuseFlag() {
		return useFlag;
	}

	/**
	* 设置字段useFlag的值，该字段的<br>
	* 字段名称 :启用标志<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setuseFlag(String useFlag) {
		this.useFlag = useFlag;
    }

	/**
	* 获取字段orderFlag的值，该字段的<br>
	* 字段名称 :展示顺序<br>
	* 数据类型 :varchar(3)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getorderFlag() {
		return orderFlag;
	}

	/**
	* 设置字段orderFlag的值，该字段的<br>
	* 字段名称 :展示顺序<br>
	* 数据类型 :varchar(3)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setorderFlag(String orderFlag) {
		this.orderFlag = orderFlag;
    }

	/**
	* 获取字段content的值，该字段的<br>
	* 字段名称 :使用展示内容<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcontent() {
		return content;
	}

	/**
	* 设置字段content的值，该字段的<br>
	* 字段名称 :使用展示内容<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcontent(String content) {
		this.content = content;
    }

	/**
	* 获取字段description的值，该字段的<br>
	* 字段名称 :描述<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getdescription() {
		return description;
	}

	/**
	* 设置字段description的值，该字段的<br>
	* 字段名称 :描述<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setdescription(String description) {
		this.description = description;
    }

	/**
	* 获取字段AddTime的值，该字段的<br>
	* 字段名称 :AddTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getAddTime() {
		return AddTime;
	}

	/**
	* 设置字段AddTime的值，该字段的<br>
	* 字段名称 :AddTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAddTime(Date addTime) {
		this.AddTime = addTime;
    }

	/**
	* 获取字段AddUser的值，该字段的<br>
	* 字段名称 :AddUser<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAddUser() {
		return AddUser;
	}

	/**
	* 设置字段AddUser的值，该字段的<br>
	* 字段名称 :AddUser<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAddUser(String addUser) {
		this.AddUser = addUser;
    }

	/**
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :备用1<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :备用1<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* 获取字段Prop2的值，该字段的<br>
	* 字段名称 :备用2<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* 设置字段Prop2的值，该字段的<br>
	* 字段名称 :备用2<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

	/**
	* 获取字段Prop3的值，该字段的<br>
	* 字段名称 :备用3<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp3() {
		return Prop3;
	}

	/**
	* 设置字段Prop3的值，该字段的<br>
	* 字段名称 :备用3<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp3(String prop3) {
		this.Prop3 = prop3;
    }

}