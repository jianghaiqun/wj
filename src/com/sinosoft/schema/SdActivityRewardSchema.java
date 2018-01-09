package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：活动奖励表<br>
 * 表代码：SdActivityReward<br>
 * 表主键：activitySn<br>
 */
public class SdActivityRewardSchema extends Schema {
	private String id;

	private String activitySn;

	private String rewardType;

	private String value1;

	private String value2;

	private String backup1;

	private String backup2;

	private String backup3;

	private String backup4;

	private String backup5;

	private String backup6;

	private String backup7;

	private String backup8;

	private String backup9;

	private String backup10;

	private Date createTime;

	private Date modifyTime;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 32 , 0 , false , false),
		new SchemaColumn("activitySn", DataColumn.STRING, 1, 50 , 0 , true , true),
		new SchemaColumn("rewardType", DataColumn.STRING, 2, 2 , 0 , false , false),
		new SchemaColumn("value1", DataColumn.STRING, 3, 255 , 0 , false , false),
		new SchemaColumn("value2", DataColumn.STRING, 4, 255 , 0 , false , false),
		new SchemaColumn("backup1", DataColumn.STRING, 5, 255 , 0 , false , false),
		new SchemaColumn("backup2", DataColumn.STRING, 6, 255 , 0 , false , false),
		new SchemaColumn("backup3", DataColumn.STRING, 7, 255 , 0 , false , false),
		new SchemaColumn("backup4", DataColumn.STRING, 8, 255 , 0 , false , false),
		new SchemaColumn("backup5", DataColumn.STRING, 9, 255 , 0 , false , false),
		new SchemaColumn("backup6", DataColumn.STRING, 10, 255 , 0 , false , false),
		new SchemaColumn("backup7", DataColumn.STRING, 11, 255 , 0 , false , false),
		new SchemaColumn("backup8", DataColumn.STRING, 12, 255 , 0 , false , false),
		new SchemaColumn("backup9", DataColumn.STRING, 13, 255 , 0 , false , false),
		new SchemaColumn("backup10", DataColumn.STRING, 14, 255 , 0 , false , false),
		new SchemaColumn("createTime", DataColumn.DATETIME, 15, 0 , 0 , false , false),
		new SchemaColumn("modifyTime", DataColumn.DATETIME, 16, 0 , 0 , false , false)
	};

	public static final String _TableCode = "SdActivityReward";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into SdActivityReward values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update SdActivityReward set id=?,activitySn=?,rewardType=?,value1=?,value2=?,backup1=?,backup2=?,backup3=?,backup4=?,backup5=?,backup6=?,backup7=?,backup8=?,backup9=?,backup10=?,createTime=?,modifyTime=? where activitySn=?";

	protected static final String _DeleteSQL = "delete from SdActivityReward  where activitySn=?";

	protected static final String _FillAllSQL = "select * from SdActivityReward  where activitySn=?";

	public SdActivityRewardSchema(){
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
		return new SdActivityRewardSchema();
	}

	protected SchemaSet newSet(){
		return new SdActivityRewardSet();
	}

	public SdActivityRewardSet query() {
		return query(null, -1, -1);
	}

	public SdActivityRewardSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public SdActivityRewardSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public SdActivityRewardSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (SdActivityRewardSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){activitySn = (String)v;return;}
		if (i == 2){rewardType = (String)v;return;}
		if (i == 3){value1 = (String)v;return;}
		if (i == 4){value2 = (String)v;return;}
		if (i == 5){backup1 = (String)v;return;}
		if (i == 6){backup2 = (String)v;return;}
		if (i == 7){backup3 = (String)v;return;}
		if (i == 8){backup4 = (String)v;return;}
		if (i == 9){backup5 = (String)v;return;}
		if (i == 10){backup6 = (String)v;return;}
		if (i == 11){backup7 = (String)v;return;}
		if (i == 12){backup8 = (String)v;return;}
		if (i == 13){backup9 = (String)v;return;}
		if (i == 14){backup10 = (String)v;return;}
		if (i == 15){createTime = (Date)v;return;}
		if (i == 16){modifyTime = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return activitySn;}
		if (i == 2){return rewardType;}
		if (i == 3){return value1;}
		if (i == 4){return value2;}
		if (i == 5){return backup1;}
		if (i == 6){return backup2;}
		if (i == 7){return backup3;}
		if (i == 8){return backup4;}
		if (i == 9){return backup5;}
		if (i == 10){return backup6;}
		if (i == 11){return backup7;}
		if (i == 12){return backup8;}
		if (i == 13){return backup9;}
		if (i == 14){return backup10;}
		if (i == 15){return createTime;}
		if (i == 16){return modifyTime;}
		return null;
	}

	/**
	* 获取字段id的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getid() {
		return id;
	}

	/**
	* 设置字段id的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setid(String id) {
		this.id = id;
    }

	/**
	* 获取字段activitySn的值，该字段的<br>
	* 字段名称 :活动编码<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getactivitySn() {
		return activitySn;
	}

	/**
	* 设置字段activitySn的值，该字段的<br>
	* 字段名称 :活动编码<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setactivitySn(String activitySn) {
		this.activitySn = activitySn;
    }

	/**
	* 获取字段rewardType的值，该字段的<br>
	* 字段名称 :奖励类型<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrewardType() {
		return rewardType;
	}

	/**
	* 设置字段rewardType的值，该字段的<br>
	* 字段名称 :奖励类型<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrewardType(String rewardType) {
		this.rewardType = rewardType;
    }

	/**
	* 获取字段value1的值，该字段的<br>
	* 字段名称 :通用字段1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getvalue1() {
		return value1;
	}

	/**
	* 设置字段value1的值，该字段的<br>
	* 字段名称 :通用字段1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setvalue1(String value1) {
		this.value1 = value1;
    }

	/**
	* 获取字段value2的值，该字段的<br>
	* 字段名称 :通用字段2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getvalue2() {
		return value2;
	}

	/**
	* 设置字段value2的值，该字段的<br>
	* 字段名称 :通用字段2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setvalue2(String value2) {
		this.value2 = value2;
    }

	/**
	* 获取字段backup1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getbackup1() {
		return backup1;
	}

	/**
	* 设置字段backup1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setbackup1(String backup1) {
		this.backup1 = backup1;
    }

	/**
	* 获取字段backup2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getbackup2() {
		return backup2;
	}

	/**
	* 设置字段backup2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setbackup2(String backup2) {
		this.backup2 = backup2;
    }

	/**
	* 获取字段backup3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getbackup3() {
		return backup3;
	}

	/**
	* 设置字段backup3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setbackup3(String backup3) {
		this.backup3 = backup3;
    }

	/**
	* 获取字段backup4的值，该字段的<br>
	* 字段名称 :备用字段4<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getbackup4() {
		return backup4;
	}

	/**
	* 设置字段backup4的值，该字段的<br>
	* 字段名称 :备用字段4<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setbackup4(String backup4) {
		this.backup4 = backup4;
    }

	/**
	* 获取字段backup5的值，该字段的<br>
	* 字段名称 :备用字段5<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getbackup5() {
		return backup5;
	}

	/**
	* 设置字段backup5的值，该字段的<br>
	* 字段名称 :备用字段5<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setbackup5(String backup5) {
		this.backup5 = backup5;
    }

	/**
	* 获取字段backup6的值，该字段的<br>
	* 字段名称 :备用字段6<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getbackup6() {
		return backup6;
	}

	/**
	* 设置字段backup6的值，该字段的<br>
	* 字段名称 :备用字段6<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setbackup6(String backup6) {
		this.backup6 = backup6;
    }

	/**
	* 获取字段backup7的值，该字段的<br>
	* 字段名称 :备用字段7<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getbackup7() {
		return backup7;
	}

	/**
	* 设置字段backup7的值，该字段的<br>
	* 字段名称 :备用字段7<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setbackup7(String backup7) {
		this.backup7 = backup7;
    }

	/**
	* 获取字段backup8的值，该字段的<br>
	* 字段名称 :备用字段8<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getbackup8() {
		return backup8;
	}

	/**
	* 设置字段backup8的值，该字段的<br>
	* 字段名称 :备用字段8<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setbackup8(String backup8) {
		this.backup8 = backup8;
    }

	/**
	* 获取字段backup9的值，该字段的<br>
	* 字段名称 :备用字段9<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getbackup9() {
		return backup9;
	}

	/**
	* 设置字段backup9的值，该字段的<br>
	* 字段名称 :备用字段9<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setbackup9(String backup9) {
		this.backup9 = backup9;
    }

	/**
	* 获取字段backup10的值，该字段的<br>
	* 字段名称 :备用字段10<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getbackup10() {
		return backup10;
	}

	/**
	* 设置字段backup10的值，该字段的<br>
	* 字段名称 :备用字段10<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setbackup10(String backup10) {
		this.backup10 = backup10;
    }

	/**
	* 获取字段createTime的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getcreateTime() {
		return createTime;
	}

	/**
	* 设置字段createTime的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcreateTime(Date createTime) {
		this.createTime = createTime;
    }

	/**
	* 获取字段modifyTime的值，该字段的<br>
	* 字段名称 :修改时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getmodifyTime() {
		return modifyTime;
	}

	/**
	* 设置字段modifyTime的值，该字段的<br>
	* 字段名称 :修改时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmodifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
    }

}