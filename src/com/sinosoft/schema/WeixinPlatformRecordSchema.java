package com.sinosoft.schema;

import java.util.Date;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.orm.SchemaSet;

/**
 * 表名称：微信平台二维码扫描记录<br>
 * 表代码：WeixinPlatformRecord<br>
 * 表主键：id<br>
 */
@SuppressWarnings("serial")
public class WeixinPlatformRecordSchema extends Schema {
	private String id;

	private String sceneId;

	private String status;

	private Date CreateTime;

	private String FromUserName;

	private String ToUserName;

	private Date AddTime;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 100 , 0 , true , true),
		new SchemaColumn("sceneId", DataColumn.STRING, 1, 100 , 0 , false , false),
		new SchemaColumn("status", DataColumn.STRING, 2, 1 , 0 , false , false),
		new SchemaColumn("CreateTime", DataColumn.DATETIME, 3, 0 , 0 , false , false),
		new SchemaColumn("FromUserName", DataColumn.STRING, 4, 100 , 0 , false , false),
		new SchemaColumn("ToUserName", DataColumn.STRING, 5, 100 , 0 , false , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 6, 0 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 7, 100 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 8, 100 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 9, 100 , 0 , false , false)
	};

	public static final String _TableCode = "WeixinPlatformRecord";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into WeixinPlatformRecord values(?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update WeixinPlatformRecord set id=?,sceneId=?,status=?,CreateTime=?,FromUserName=?,ToUserName=?,AddTime=?,Prop1=?,Prop2=?,Prop3=? where id=?";

	protected static final String _DeleteSQL = "delete from WeixinPlatformRecord  where id=?";

	protected static final String _FillAllSQL = "select * from WeixinPlatformRecord  where id=?";

	public WeixinPlatformRecordSchema(){
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
		return new WeixinPlatformRecordSchema();
	}

	protected SchemaSet newSet(){
		return new WeixinPlatformRecordSet();
	}

	public WeixinPlatformRecordSet query() {
		return query(null, -1, -1);
	}

	public WeixinPlatformRecordSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public WeixinPlatformRecordSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public WeixinPlatformRecordSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (WeixinPlatformRecordSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){sceneId = (String)v;return;}
		if (i == 2){status = (String)v;return;}
		if (i == 3){CreateTime = (Date)v;return;}
		if (i == 4){FromUserName = (String)v;return;}
		if (i == 5){ToUserName = (String)v;return;}
		if (i == 6){AddTime = (Date)v;return;}
		if (i == 7){Prop1 = (String)v;return;}
		if (i == 8){Prop2 = (String)v;return;}
		if (i == 9){Prop3 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return sceneId;}
		if (i == 2){return status;}
		if (i == 3){return CreateTime;}
		if (i == 4){return FromUserName;}
		if (i == 5){return ToUserName;}
		if (i == 6){return AddTime;}
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
	* 获取字段status的值，该字段的<br>
	* 字段名称 :关注时状态<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getstatus() {
		return status;
	}

	/**
	* 设置字段status的值，该字段的<br>
	* 字段名称 :关注时状态<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setstatus(String status) {
		this.status = status;
    }

	/**
	* 获取字段CreateTime的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getCreateTime() {
		return CreateTime;
	}

	/**
	* 设置字段CreateTime的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCreateTime(Date createTime) {
		this.CreateTime = createTime;
    }

	/**
	* 获取字段FromUserName的值，该字段的<br>
	* 字段名称 :关注人<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getFromUserName() {
		return FromUserName;
	}

	/**
	* 设置字段FromUserName的值，该字段的<br>
	* 字段名称 :关注人<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setFromUserName(String fromUserName) {
		this.FromUserName = fromUserName;
    }

	/**
	* 获取字段ToUserName的值，该字段的<br>
	* 字段名称 :公众号<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getToUserName() {
		return ToUserName;
	}

	/**
	* 设置字段ToUserName的值，该字段的<br>
	* 字段名称 :公众号<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setToUserName(String toUserName) {
		this.ToUserName = toUserName;
    }

	/**
	* 获取字段AddTime的值，该字段的<br>
	* 字段名称 :增加时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getAddTime() {
		return AddTime;
	}

	/**
	* 设置字段AddTime的值，该字段的<br>
	* 字段名称 :增加时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAddTime(Date addTime) {
		this.AddTime = addTime;
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