package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

import java.util.Date;

import java.math.BigDecimal;

/**
 * 表名称：发起活动表<br>
 * 表代码：ActivityLaunch<br>
 * 表主键：serialNo<br>
 */
public class ActivityLaunchSchema extends Schema {
	private String serialNo;

	private String activityId;

	private String initiatorOpenid;

	private String memberId;

	private String imageUrl;

	private Date beginDate;

	private Date endDate;

	private BigDecimal totalAmount;

	private String status;

	private String prop1;

	private String prop2;

	private String prop3;

	private String prop4;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("serialNo", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("activityId", DataColumn.STRING, 1, 20 , 0 , false , false),
		new SchemaColumn("initiatorOpenid", DataColumn.STRING, 2, 50 , 0 , false , false),
		new SchemaColumn("memberId", DataColumn.STRING, 3, 32 , 0 , false , false),
		new SchemaColumn("imageUrl", DataColumn.STRING, 4, 100 , 0 , false , false),
		new SchemaColumn("beginDate", DataColumn.DATETIME, 5, 0 , 0 , false , false),
		new SchemaColumn("endDate", DataColumn.DATETIME, 6, 0 , 0 , false , false),
		new SchemaColumn("totalAmount", DataColumn.BIGDECIMAL, 7, 10 , 0 , false , false),
		new SchemaColumn("status", DataColumn.STRING, 8, 5 , 0 , false , false),
		new SchemaColumn("prop1", DataColumn.STRING, 9, 50 , 0 , false , false),
		new SchemaColumn("prop2", DataColumn.STRING, 10, 50 , 0 , false , false),
		new SchemaColumn("prop3", DataColumn.STRING, 11, 50 , 0 , false , false),
		new SchemaColumn("prop4", DataColumn.STRING, 12, 100 , 0 , false , false)
	};

	public static final String _TableCode = "ActivityLaunch";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into ActivityLaunch values(?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ActivityLaunch set serialNo=?,activityId=?,initiatorOpenid=?,memberId=?,imageUrl=?,beginDate=?,endDate=?,totalAmount=?,status=?,prop1=?,prop2=?,prop3=?,prop4=? where serialNo=?";

	protected static final String _DeleteSQL = "delete from ActivityLaunch  where serialNo=?";

	protected static final String _FillAllSQL = "select * from ActivityLaunch  where serialNo=?";

	public ActivityLaunchSchema(){
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
		return new ActivityLaunchSchema();
	}

	protected SchemaSet newSet(){
		return new ActivityLaunchSet();
	}

	public ActivityLaunchSet query() {
		return query(null, -1, -1);
	}

	public ActivityLaunchSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ActivityLaunchSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ActivityLaunchSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ActivityLaunchSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){serialNo = (String)v;return;}
		if (i == 1){activityId = (String)v;return;}
		if (i == 2){initiatorOpenid = (String)v;return;}
		if (i == 3){memberId = (String)v;return;}
		if (i == 4){imageUrl = (String)v;return;}
		if (i == 5){beginDate = (Date)v;return;}
		if (i == 6){endDate = (Date)v;return;}
		if (i == 7){if(v==null){totalAmount = null;}else{totalAmount =  ((BigDecimal)v) ;}return;}
		if (i == 8){status = (String)v;return;}
		if (i == 9){prop1 = (String)v;return;}
		if (i == 10){prop2 = (String)v;return;}
		if (i == 11){prop3 = (String)v;return;}
		if (i == 12){prop4 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return serialNo;}
		if (i == 1){return activityId;}
		if (i == 2){return initiatorOpenid;}
		if (i == 3){return memberId;}
		if (i == 4){return imageUrl;}
		if (i == 5){return beginDate;}
		if (i == 6){return endDate;}
		if (i == 7){return totalAmount;}
		if (i == 8){return status;}
		if (i == 9){return prop1;}
		if (i == 10){return prop2;}
		if (i == 11){return prop3;}
		if (i == 12){return prop4;}
		return null;
	}

	/**
	* 获取字段serialNo的值，该字段的<br>
	* 字段名称 :流水号<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getserialNo() {
		return serialNo;
	}

	/**
	* 设置字段serialNo的值，该字段的<br>
	* 字段名称 :流水号<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setserialNo(String serialNo) {
		this.serialNo = serialNo;
    }

	/**
	* 获取字段activityId的值，该字段的<br>
	* 字段名称 :活动编码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getactivityId() {
		return activityId;
	}

	/**
	* 设置字段activityId的值，该字段的<br>
	* 字段名称 :活动编码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setactivityId(String activityId) {
		this.activityId = activityId;
    }

	/**
	* 获取字段initiatorOpenid的值，该字段的<br>
	* 字段名称 :发起者openid<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getinitiatorOpenid() {
		return initiatorOpenid;
	}

	/**
	* 设置字段initiatorOpenid的值，该字段的<br>
	* 字段名称 :发起者openid<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setinitiatorOpenid(String initiatorOpenid) {
		this.initiatorOpenid = initiatorOpenid;
    }

	/**
	* 获取字段memberId的值，该字段的<br>
	* 字段名称 :发起者会员编码<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getmemberId() {
		return memberId;
	}

	/**
	* 设置字段memberId的值，该字段的<br>
	* 字段名称 :发起者会员编码<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmemberId(String memberId) {
		this.memberId = memberId;
    }

	/**
	* 获取字段imageUrl的值，该字段的<br>
	* 字段名称 :二维码地址<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getimageUrl() {
		return imageUrl;
	}

	/**
	* 设置字段imageUrl的值，该字段的<br>
	* 字段名称 :二维码地址<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setimageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
    }

	/**
	* 获取字段beginDate的值，该字段的<br>
	* 字段名称 :发起时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getbeginDate() {
		return beginDate;
	}

	/**
	* 设置字段beginDate的值，该字段的<br>
	* 字段名称 :发起时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setbeginDate(Date beginDate) {
		this.beginDate = beginDate;
    }

	/**
	* 获取字段endDate的值，该字段的<br>
	* 字段名称 :截止时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getendDate() {
		return endDate;
	}

	/**
	* 设置字段endDate的值，该字段的<br>
	* 字段名称 :截止时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setendDate(Date endDate) {
		this.endDate = endDate;
    }

	/**
	* 获取字段totalAmount的值，该字段的<br>
	* 字段名称 :累计砍价金额<br>
	* 数据类型 :decimal(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public BigDecimal gettotalAmount() {
		return totalAmount;
	}

	/**
	* 设置字段totalAmount的值，该字段的<br>
	* 字段名称 :累计砍价金额<br>
	* 数据类型 :decimal(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void settotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
    }

	/**
	* 获取字段status的值，该字段的<br>
	* 字段名称 :状态<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getstatus() {
		return status;
	}

	/**
	* 设置字段status的值，该字段的<br>
	* 字段名称 :状态<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setstatus(String status) {
		this.status = status;
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
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop3() {
		return prop3;
	}

	/**
	* 设置字段prop3的值，该字段的<br>
	* 字段名称 :prop3<br>
	* 数据类型 :varchar(50)<br>
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

}