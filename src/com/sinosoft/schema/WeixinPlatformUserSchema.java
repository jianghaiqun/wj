package com.sinosoft.schema;

import java.util.Date;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.orm.SchemaSet;

/**
 * 表名称：微信平台用户表<br>
 * 表代码：WeixinPlatformUser<br>
 * 表主键：openid<br>
 */
@SuppressWarnings("serial")
public class WeixinPlatformUserSchema extends Schema {
	private String openid;

	private String nickname;

	private String sex;

	private String language;

	private String city;

	private String province;

	private String country;

	private String headimgurl;

	private Date subscribe_time;

	private String remark;

	private String groupid;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("openid", DataColumn.STRING, 0, 100 , 0 , true , true),
		new SchemaColumn("nickname", DataColumn.STRING, 1, 100 , 0 , false , false),
		new SchemaColumn("sex", DataColumn.STRING, 2, 2 , 0 , false , false),
		new SchemaColumn("language", DataColumn.STRING, 3, 10 , 0 , false , false),
		new SchemaColumn("city", DataColumn.STRING, 4, 100 , 0 , false , false),
		new SchemaColumn("province", DataColumn.STRING, 5, 100 , 0 , false , false),
		new SchemaColumn("country", DataColumn.STRING, 6, 100 , 0 , false , false),
		new SchemaColumn("headimgurl", DataColumn.STRING, 7, 500 , 0 , false , false),
		new SchemaColumn("subscribe_time", DataColumn.DATETIME, 8, 0 , 0 , false , false),
		new SchemaColumn("remark", DataColumn.STRING, 9, 100 , 0 , false , false),
		new SchemaColumn("groupid", DataColumn.STRING, 10, 100 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 11, 100 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 12, 100 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 13, 100 , 0 , false , false)
	};

	public static final String _TableCode = "WeixinPlatformUser";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into WeixinPlatformUser values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update WeixinPlatformUser set openid=?,nickname=?,sex=?,language=?,city=?,province=?,country=?,headimgurl=?,subscribe_time=?,remark=?,groupid=?,Prop1=?,Prop2=?,Prop3=? where openid=?";

	protected static final String _DeleteSQL = "delete from WeixinPlatformUser  where openid=?";

	protected static final String _FillAllSQL = "select * from WeixinPlatformUser  where openid=?";

	public WeixinPlatformUserSchema(){
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
		return new WeixinPlatformUserSchema();
	}

	protected SchemaSet newSet(){
		return new WeixinPlatformUserSet();
	}

	public WeixinPlatformUserSet query() {
		return query(null, -1, -1);
	}

	public WeixinPlatformUserSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public WeixinPlatformUserSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public WeixinPlatformUserSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (WeixinPlatformUserSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){openid = (String)v;return;}
		if (i == 1){nickname = (String)v;return;}
		if (i == 2){sex = (String)v;return;}
		if (i == 3){language = (String)v;return;}
		if (i == 4){city = (String)v;return;}
		if (i == 5){province = (String)v;return;}
		if (i == 6){country = (String)v;return;}
		if (i == 7){headimgurl = (String)v;return;}
		if (i == 8){subscribe_time = (Date)v;return;}
		if (i == 9){remark = (String)v;return;}
		if (i == 10){groupid = (String)v;return;}
		if (i == 11){Prop1 = (String)v;return;}
		if (i == 12){Prop2 = (String)v;return;}
		if (i == 13){Prop3 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return openid;}
		if (i == 1){return nickname;}
		if (i == 2){return sex;}
		if (i == 3){return language;}
		if (i == 4){return city;}
		if (i == 5){return province;}
		if (i == 6){return country;}
		if (i == 7){return headimgurl;}
		if (i == 8){return subscribe_time;}
		if (i == 9){return remark;}
		if (i == 10){return groupid;}
		if (i == 11){return Prop1;}
		if (i == 12){return Prop2;}
		if (i == 13){return Prop3;}
		return null;
	}

	/**
	* 获取字段openid的值，该字段的<br>
	* 字段名称 :openid<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getopenid() {
		return openid;
	}

	/**
	* 设置字段openid的值，该字段的<br>
	* 字段名称 :openid<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setopenid(String openid) {
		this.openid = openid;
    }

	/**
	* 获取字段nickname的值，该字段的<br>
	* 字段名称 :nickname<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getnickname() {
		return nickname;
	}

	/**
	* 设置字段nickname的值，该字段的<br>
	* 字段名称 :nickname<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setnickname(String nickname) {
		this.nickname = nickname;
    }

	/**
	* 获取字段sex的值，该字段的<br>
	* 字段名称 :sex<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getsex() {
		return sex;
	}

	/**
	* 设置字段sex的值，该字段的<br>
	* 字段名称 :sex<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setsex(String sex) {
		this.sex = sex;
    }

	/**
	* 获取字段language的值，该字段的<br>
	* 字段名称 :language<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getlanguage() {
		return language;
	}

	/**
	* 设置字段language的值，该字段的<br>
	* 字段名称 :language<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setlanguage(String language) {
		this.language = language;
    }

	/**
	* 获取字段city的值，该字段的<br>
	* 字段名称 :city<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcity() {
		return city;
	}

	/**
	* 设置字段city的值，该字段的<br>
	* 字段名称 :city<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcity(String city) {
		this.city = city;
    }

	/**
	* 获取字段province的值，该字段的<br>
	* 字段名称 :province<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprovince() {
		return province;
	}

	/**
	* 设置字段province的值，该字段的<br>
	* 字段名称 :province<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprovince(String province) {
		this.province = province;
    }

	/**
	* 获取字段country的值，该字段的<br>
	* 字段名称 :country<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcountry() {
		return country;
	}

	/**
	* 设置字段country的值，该字段的<br>
	* 字段名称 :country<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcountry(String country) {
		this.country = country;
    }

	/**
	* 获取字段headimgurl的值，该字段的<br>
	* 字段名称 :headimgurl<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getheadimgurl() {
		return headimgurl;
	}

	/**
	* 设置字段headimgurl的值，该字段的<br>
	* 字段名称 :headimgurl<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setheadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
    }

	/**
	* 获取字段subscribe_time的值，该字段的<br>
	* 字段名称 :subscribe_time<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getsubscribe_time() {
		return subscribe_time;
	}

	/**
	* 设置字段subscribe_time的值，该字段的<br>
	* 字段名称 :subscribe_time<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setsubscribe_time(Date subscribe_time) {
		this.subscribe_time = subscribe_time;
    }

	/**
	* 获取字段remark的值，该字段的<br>
	* 字段名称 :remark<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getremark() {
		return remark;
	}

	/**
	* 设置字段remark的值，该字段的<br>
	* 字段名称 :remark<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setremark(String remark) {
		this.remark = remark;
    }

	/**
	* 获取字段groupid的值，该字段的<br>
	* 字段名称 :groupid<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getgroupid() {
		return groupid;
	}

	/**
	* 设置字段groupid的值，该字段的<br>
	* 字段名称 :groupid<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setgroupid(String groupid) {
		this.groupid = groupid;
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