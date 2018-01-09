package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

import java.util.Date;

/**
 * 表名称：用户搜索意见<br>
 * 表代码：UserSearchOpinion<br>
 * 表主键：id<br>
 */
public class UserSearchOpinionSchema extends Schema {
	private String id;

	private String memberId;

	private String userIp;

	private Date createTime;

	private String investigationStatus;

	private String reason;

	private String insuranceName;

	private String telephone;

	private String searchName;

	private String channel;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 100 , 0 , true , true),
		new SchemaColumn("memberId", DataColumn.STRING, 1, 255 , 0 , false , false),
		new SchemaColumn("userIp", DataColumn.STRING, 2, 255 , 0 , false , false),
		new SchemaColumn("createTime", DataColumn.DATETIME, 3, 0 , 0 , false , false),
		new SchemaColumn("investigationStatus", DataColumn.STRING, 4, 100 , 0 , false , false),
		new SchemaColumn("reason", DataColumn.STRING, 5, 500 , 0 , false , false),
		new SchemaColumn("insuranceName", DataColumn.STRING, 6, 100 , 0 , false , false),
		new SchemaColumn("telephone", DataColumn.STRING, 7, 100 , 0 , false , false),
		new SchemaColumn("searchName", DataColumn.STRING, 8, 255 , 0 , false , false),
		new SchemaColumn("channel", DataColumn.STRING, 9, 100 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 10, 255 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 11, 255 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 12, 255 , 0 , false , false)
	};

	public static final String _TableCode = "UserSearchOpinion";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into UserSearchOpinion values(?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update UserSearchOpinion set id=?,memberId=?,userIp=?,createTime=?,investigationStatus=?,reason=?,insuranceName=?,telephone=?,searchName=?,channel=?,Prop1=?,Prop2=?,Prop3=? where id=?";

	protected static final String _DeleteSQL = "delete from UserSearchOpinion  where id=?";

	protected static final String _FillAllSQL = "select * from UserSearchOpinion  where id=?";

	public UserSearchOpinionSchema(){
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
		return new UserSearchOpinionSchema();
	}

	protected SchemaSet newSet(){
		return new UserSearchOpinionSet();
	}

	public UserSearchOpinionSet query() {
		return query(null, -1, -1);
	}

	public UserSearchOpinionSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public UserSearchOpinionSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public UserSearchOpinionSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (UserSearchOpinionSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){memberId = (String)v;return;}
		if (i == 2){userIp = (String)v;return;}
		if (i == 3){createTime = (Date)v;return;}
		if (i == 4){investigationStatus = (String)v;return;}
		if (i == 5){reason = (String)v;return;}
		if (i == 6){insuranceName = (String)v;return;}
		if (i == 7){telephone = (String)v;return;}
		if (i == 8){searchName = (String)v;return;}
		if (i == 9){channel = (String)v;return;}
		if (i == 10){Prop1 = (String)v;return;}
		if (i == 11){Prop2 = (String)v;return;}
		if (i == 12){Prop3 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return memberId;}
		if (i == 2){return userIp;}
		if (i == 3){return createTime;}
		if (i == 4){return investigationStatus;}
		if (i == 5){return reason;}
		if (i == 6){return insuranceName;}
		if (i == 7){return telephone;}
		if (i == 8){return searchName;}
		if (i == 9){return channel;}
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
	* 获取字段memberId的值，该字段的<br>
	* 字段名称 :会员ID<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getmemberId() {
		return memberId;
	}

	/**
	* 设置字段memberId的值，该字段的<br>
	* 字段名称 :会员ID<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmemberId(String memberId) {
		this.memberId = memberId;
    }

	/**
	* 获取字段userIp的值，该字段的<br>
	* 字段名称 :匿名ip<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getuserIp() {
		return userIp;
	}

	/**
	* 设置字段userIp的值，该字段的<br>
	* 字段名称 :匿名ip<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setuserIp(String userIp) {
		this.userIp = userIp;
    }

	/**
	* 获取字段createTime的值，该字段的<br>
	* 字段名称 :提出时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getcreateTime() {
		return createTime;
	}

	/**
	* 设置字段createTime的值，该字段的<br>
	* 字段名称 :提出时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcreateTime(Date createTime) {
		this.createTime = createTime;
    }

	/**
	* 获取字段investigationStatus的值，该字段的<br>
	* 字段名称 :调查状态<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getinvestigationStatus() {
		return investigationStatus;
	}

	/**
	* 设置字段investigationStatus的值，该字段的<br>
	* 字段名称 :调查状态<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setinvestigationStatus(String investigationStatus) {
		this.investigationStatus = investigationStatus;
    }

	/**
	* 获取字段reason的值，该字段的<br>
	* 字段名称 :原因<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getreason() {
		return reason;
	}

	/**
	* 设置字段reason的值，该字段的<br>
	* 字段名称 :原因<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setreason(String reason) {
		this.reason = reason;
    }

	/**
	* 获取字段insuranceName的值，该字段的<br>
	* 字段名称 :保险名称<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getinsuranceName() {
		return insuranceName;
	}

	/**
	* 设置字段insuranceName的值，该字段的<br>
	* 字段名称 :保险名称<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setinsuranceName(String insuranceName) {
		this.insuranceName = insuranceName;
    }

	/**
	* 获取字段telephone的值，该字段的<br>
	* 字段名称 :电话<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String gettelephone() {
		return telephone;
	}

	/**
	* 设置字段telephone的值，该字段的<br>
	* 字段名称 :电话<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void settelephone(String telephone) {
		this.telephone = telephone;
    }

	/**
	* 获取字段searchName的值，该字段的<br>
	* 字段名称 :搜索名称<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getsearchName() {
		return searchName;
	}

	/**
	* 设置字段searchName的值，该字段的<br>
	* 字段名称 :搜索名称<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setsearchName(String searchName) {
		this.searchName = searchName;
    }

	/**
	* 获取字段channel的值，该字段的<br>
	* 字段名称 :渠道<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getchannel() {
		return channel;
	}

	/**
	* 设置字段channel的值，该字段的<br>
	* 字段名称 :渠道<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setchannel(String channel) {
		this.channel = channel;
    }

	/**
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :备用1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :备用1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* 获取字段Prop2的值，该字段的<br>
	* 字段名称 :备用2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* 设置字段Prop2的值，该字段的<br>
	* 字段名称 :备用2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

	/**
	* 获取字段Prop3的值，该字段的<br>
	* 字段名称 :备用3<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp3() {
		return Prop3;
	}

	/**
	* 设置字段Prop3的值，该字段的<br>
	* 字段名称 :备用3<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp3(String prop3) {
		this.Prop3 = prop3;
    }

}