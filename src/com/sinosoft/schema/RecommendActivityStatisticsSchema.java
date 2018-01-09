package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：推荐活动统计表<br>
 * 表代码：RecommendActivityStatistics<br>
 * 表主键：id<br>
 */
public class RecommendActivityStatisticsSchema extends Schema {
	private String id;

	private String activitySn;

	private String referrerMemberId;

	private String recommendedMemberId;

	private String referrerType;

	private String couponSn;

	private String backup1;

	private String backup2;

	private String backup3;

	private String backup4;

	private String backup5;

	private Date createDate;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("activitySn", DataColumn.STRING, 1, 50 , 0 , false , false),
		new SchemaColumn("referrerMemberId", DataColumn.STRING, 2, 32 , 0 , false , false),
		new SchemaColumn("recommendedMemberId", DataColumn.STRING, 3, 32 , 0 , false , false),
		new SchemaColumn("referrerType", DataColumn.STRING, 4, 2 , 0 , false , false),
		new SchemaColumn("couponSn", DataColumn.STRING, 5, 96 , 0 , false , false),
		new SchemaColumn("backup1", DataColumn.STRING, 6, 500 , 0 , false , false),
		new SchemaColumn("backup2", DataColumn.STRING, 7, 500 , 0 , false , false),
		new SchemaColumn("backup3", DataColumn.STRING, 8, 500 , 0 , false , false),
		new SchemaColumn("backup4", DataColumn.STRING, 9, 500 , 0 , false , false),
		new SchemaColumn("backup5", DataColumn.STRING, 10, 500 , 0 , false , false),
		new SchemaColumn("createDate", DataColumn.DATETIME, 11, 0 , 0 , false , false)
	};

	public static final String _TableCode = "RecommendActivityStatistics";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into RecommendActivityStatistics values(?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update RecommendActivityStatistics set id=?,activitySn=?,referrerMemberId=?,recommendedMemberId=?,referrerType=?,couponSn=?,backup1=?,backup2=?,backup3=?,backup4=?,backup5=?,createDate=? where id=?";

	protected static final String _DeleteSQL = "delete from RecommendActivityStatistics  where id=?";

	protected static final String _FillAllSQL = "select * from RecommendActivityStatistics  where id=?";

	public RecommendActivityStatisticsSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[12];
	}

	protected Schema newInstance(){
		return new RecommendActivityStatisticsSchema();
	}

	protected SchemaSet newSet(){
		return new RecommendActivityStatisticsSet();
	}

	public RecommendActivityStatisticsSet query() {
		return query(null, -1, -1);
	}

	public RecommendActivityStatisticsSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public RecommendActivityStatisticsSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public RecommendActivityStatisticsSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (RecommendActivityStatisticsSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){activitySn = (String)v;return;}
		if (i == 2){referrerMemberId = (String)v;return;}
		if (i == 3){recommendedMemberId = (String)v;return;}
		if (i == 4){referrerType = (String)v;return;}
		if (i == 5){couponSn = (String)v;return;}
		if (i == 6){backup1 = (String)v;return;}
		if (i == 7){backup2 = (String)v;return;}
		if (i == 8){backup3 = (String)v;return;}
		if (i == 9){backup4 = (String)v;return;}
		if (i == 10){backup5 = (String)v;return;}
		if (i == 11){createDate = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return activitySn;}
		if (i == 2){return referrerMemberId;}
		if (i == 3){return recommendedMemberId;}
		if (i == 4){return referrerType;}
		if (i == 5){return couponSn;}
		if (i == 6){return backup1;}
		if (i == 7){return backup2;}
		if (i == 8){return backup3;}
		if (i == 9){return backup4;}
		if (i == 10){return backup5;}
		if (i == 11){return createDate;}
		return null;
	}

	/**
	* 获取字段id的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getid() {
		return id;
	}

	/**
	* 设置字段id的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setid(String id) {
		this.id = id;
    }

	/**
	* 获取字段activitySn的值，该字段的<br>
	* 字段名称 :活动编码<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getactivitySn() {
		return activitySn;
	}

	/**
	* 设置字段activitySn的值，该字段的<br>
	* 字段名称 :活动编码<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setactivitySn(String activitySn) {
		this.activitySn = activitySn;
    }

	/**
	* 获取字段referrerMemberId的值，该字段的<br>
	* 字段名称 :会员ID(推荐人)<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getreferrerMemberId() {
		return referrerMemberId;
	}

	/**
	* 设置字段referrerMemberId的值，该字段的<br>
	* 字段名称 :会员ID(推荐人)<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setreferrerMemberId(String referrerMemberId) {
		this.referrerMemberId = referrerMemberId;
    }

	/**
	* 获取字段recommendedMemberId的值，该字段的<br>
	* 字段名称 :会员ID(被推荐人)<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecommendedMemberId() {
		return recommendedMemberId;
	}

	/**
	* 设置字段recommendedMemberId的值，该字段的<br>
	* 字段名称 :会员ID(被推荐人)<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecommendedMemberId(String recommendedMemberId) {
		this.recommendedMemberId = recommendedMemberId;
    }

	/**
	* 获取字段referrerType的值，该字段的<br>
	* 字段名称 :优惠券所属类型<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getreferrerType() {
		return referrerType;
	}

	/**
	* 设置字段referrerType的值，该字段的<br>
	* 字段名称 :优惠券所属类型<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setreferrerType(String referrerType) {
		this.referrerType = referrerType;
    }

	/**
	* 获取字段couponSn的值，该字段的<br>
	* 字段名称 :优惠券编码<br>
	* 数据类型 :varchar(96)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcouponSn() {
		return couponSn;
	}

	/**
	* 设置字段couponSn的值，该字段的<br>
	* 字段名称 :优惠券编码<br>
	* 数据类型 :varchar(96)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcouponSn(String couponSn) {
		this.couponSn = couponSn;
    }

	/**
	* 获取字段backup1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getbackup1() {
		return backup1;
	}

	/**
	* 设置字段backup1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setbackup1(String backup1) {
		this.backup1 = backup1;
    }

	/**
	* 获取字段backup2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getbackup2() {
		return backup2;
	}

	/**
	* 设置字段backup2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setbackup2(String backup2) {
		this.backup2 = backup2;
    }

	/**
	* 获取字段backup3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getbackup3() {
		return backup3;
	}

	/**
	* 设置字段backup3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setbackup3(String backup3) {
		this.backup3 = backup3;
    }

	/**
	* 获取字段backup4的值，该字段的<br>
	* 字段名称 :备用字段4<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getbackup4() {
		return backup4;
	}

	/**
	* 设置字段backup4的值，该字段的<br>
	* 字段名称 :备用字段4<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setbackup4(String backup4) {
		this.backup4 = backup4;
    }

	/**
	* 获取字段backup5的值，该字段的<br>
	* 字段名称 :备用字段5<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getbackup5() {
		return backup5;
	}

	/**
	* 设置字段backup5的值，该字段的<br>
	* 字段名称 :备用字段5<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setbackup5(String backup5) {
		this.backup5 = backup5;
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

}