package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：微信平台二维码管理<br>
 * 表代码：WeixinPlatform<br>
 * 表主键：id<br>
 */
public class WeixinPlatformSchema extends Schema {
	private String id;

	private String sceneName;

	private String sceneId;

	private String ticket;

	private String useFlag;

	private Date AddTime;

	private String AddUser;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 100 , 0 , true , true),
		new SchemaColumn("sceneName", DataColumn.STRING, 1, 100 , 0 , false , false),
		new SchemaColumn("sceneId", DataColumn.STRING, 2, 100 , 0 , false , false),
		new SchemaColumn("ticket", DataColumn.STRING, 3, 200 , 0 , false , false),
		new SchemaColumn("useFlag", DataColumn.STRING, 4, 1 , 0 , false , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 5, 0 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 6, 100 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 7, 100 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 8, 100 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 9, 100 , 0 , false , false)
	};

	public static final String _TableCode = "WeixinPlatform";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into WeixinPlatform values(?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update WeixinPlatform set id=?,sceneName=?,sceneId=?,ticket=?,useFlag=?,AddTime=?,AddUser=?,Prop1=?,Prop2=?,Prop3=? where id=?";

	protected static final String _DeleteSQL = "delete from WeixinPlatform  where id=?";

	protected static final String _FillAllSQL = "select * from WeixinPlatform  where id=?";

	public WeixinPlatformSchema(){
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
		return new WeixinPlatformSchema();
	}

	protected SchemaSet newSet(){
		return new WeixinPlatformSet();
	}

	public WeixinPlatformSet query() {
		return query(null, -1, -1);
	}

	public WeixinPlatformSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public WeixinPlatformSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public WeixinPlatformSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (WeixinPlatformSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){sceneName = (String)v;return;}
		if (i == 2){sceneId = (String)v;return;}
		if (i == 3){ticket = (String)v;return;}
		if (i == 4){useFlag = (String)v;return;}
		if (i == 5){AddTime = (Date)v;return;}
		if (i == 6){AddUser = (String)v;return;}
		if (i == 7){Prop1 = (String)v;return;}
		if (i == 8){Prop2 = (String)v;return;}
		if (i == 9){Prop3 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return sceneName;}
		if (i == 2){return sceneId;}
		if (i == 3){return ticket;}
		if (i == 4){return useFlag;}
		if (i == 5){return AddTime;}
		if (i == 6){return AddUser;}
		if (i == 7){return Prop1;}
		if (i == 8){return Prop2;}
		if (i == 9){return Prop3;}
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
	* 获取字段sceneName的值，该字段的<br>
	* 字段名称 :场景名称<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getsceneName() {
		return sceneName;
	}

	/**
	* 设置字段sceneName的值，该字段的<br>
	* 字段名称 :场景名称<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setsceneName(String sceneName) {
		this.sceneName = sceneName;
    }

	/**
	* 获取字段sceneId的值，该字段的<br>
	* 字段名称 :场景值<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getsceneId() {
		return sceneId;
	}

	/**
	* 设置字段sceneId的值，该字段的<br>
	* 字段名称 :场景值<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setsceneId(String sceneId) {
		this.sceneId = sceneId;
    }

	/**
	* 获取字段ticket的值，该字段的<br>
	* 字段名称 :二维码链接<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getticket() {
		return ticket;
	}

	/**
	* 设置字段ticket的值，该字段的<br>
	* 字段名称 :二维码链接<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setticket(String ticket) {
		this.ticket = ticket;
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