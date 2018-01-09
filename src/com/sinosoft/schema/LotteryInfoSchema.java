package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：抽奖明细表<br>
 * 表代码：LotteryInfo<br>
 * 表主键：MemberId, GotType, CurrentDay<br>
 */
public class LotteryInfoSchema extends Schema {
	private Date LottTime;

	private String MemberId;

	private String ClickTimes;

	private String ShareTimes;

	private String GotValue;

	private String GotType;

	private String CurrentDay;

	private String Prop1;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("LottTime", DataColumn.DATETIME, 0, 0 , 0 , false , false),
		new SchemaColumn("MemberId", DataColumn.STRING, 1, 32 , 0 , true , true),
		new SchemaColumn("ClickTimes", DataColumn.STRING, 2, 2 , 0 , false , false),
		new SchemaColumn("ShareTimes", DataColumn.STRING, 3, 2 , 0 , false , false),
		new SchemaColumn("GotValue", DataColumn.STRING, 4, 3 , 0 , false , false),
		new SchemaColumn("GotType", DataColumn.STRING, 5, 10 , 0 , true , true),
		new SchemaColumn("CurrentDay", DataColumn.STRING, 6, 3 , 0 , true , true),
		new SchemaColumn("Prop1", DataColumn.STRING, 7, 50 , 0 , false , false)
	};

	public static final String _TableCode = "LotteryInfo";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into LotteryInfo values(?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update LotteryInfo set LottTime=?,MemberId=?,ClickTimes=?,ShareTimes=?,GotValue=?,GotType=?,CurrentDay=?,Prop1=? where MemberId=? and GotType=? and CurrentDay=?";

	protected static final String _DeleteSQL = "delete from LotteryInfo  where MemberId=? and GotType=? and CurrentDay=?";

	protected static final String _FillAllSQL = "select * from LotteryInfo  where MemberId=? and GotType=? and CurrentDay=?";

	public LotteryInfoSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[8];
	}

	protected Schema newInstance(){
		return new LotteryInfoSchema();
	}

	protected SchemaSet newSet(){
		return new LotteryInfoSet();
	}

	public LotteryInfoSet query() {
		return query(null, -1, -1);
	}

	public LotteryInfoSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public LotteryInfoSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public LotteryInfoSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (LotteryInfoSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){LottTime = (Date)v;return;}
		if (i == 1){MemberId = (String)v;return;}
		if (i == 2){ClickTimes = (String)v;return;}
		if (i == 3){ShareTimes = (String)v;return;}
		if (i == 4){GotValue = (String)v;return;}
		if (i == 5){GotType = (String)v;return;}
		if (i == 6){CurrentDay = (String)v;return;}
		if (i == 7){Prop1 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return LottTime;}
		if (i == 1){return MemberId;}
		if (i == 2){return ClickTimes;}
		if (i == 3){return ShareTimes;}
		if (i == 4){return GotValue;}
		if (i == 5){return GotType;}
		if (i == 6){return CurrentDay;}
		if (i == 7){return Prop1;}
		return null;
	}

	/**
	* 获取字段LottTime的值，该字段的<br>
	* 字段名称 :抽奖时间<br>
	* 数据类型 :DATETIME<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getLottTime() {
		return LottTime;
	}

	/**
	* 设置字段LottTime的值，该字段的<br>
	* 字段名称 :抽奖时间<br>
	* 数据类型 :DATETIME<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setLottTime(Date lottTime) {
		this.LottTime = lottTime;
    }

	/**
	* 获取字段MemberId的值，该字段的<br>
	* 字段名称 :抽奖人ID<br>
	* 数据类型 :VARCHAR(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getMemberId() {
		return MemberId;
	}

	/**
	* 设置字段MemberId的值，该字段的<br>
	* 字段名称 :抽奖人ID<br>
	* 数据类型 :VARCHAR(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setMemberId(String memberId) {
		this.MemberId = memberId;
    }

	/**
	* 获取字段ClickTimes的值，该字段的<br>
	* 字段名称 :操作次数<br>
	* 数据类型 :VARCHAR(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getClickTimes() {
		return ClickTimes;
	}

	/**
	* 设置字段ClickTimes的值，该字段的<br>
	* 字段名称 :操作次数<br>
	* 数据类型 :VARCHAR(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setClickTimes(String clickTimes) {
		this.ClickTimes = clickTimes;
    }

	/**
	* 获取字段ShareTimes的值，该字段的<br>
	* 字段名称 :分享次数<br>
	* 数据类型 :VARCHAR(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getShareTimes() {
		return ShareTimes;
	}

	/**
	* 设置字段ShareTimes的值，该字段的<br>
	* 字段名称 :分享次数<br>
	* 数据类型 :VARCHAR(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setShareTimes(String shareTimes) {
		this.ShareTimes = shareTimes;
    }

	/**
	* 获取字段GotValue的值，该字段的<br>
	* 字段名称 :获取内容<br>
	* 数据类型 :VARCHAR(3)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getGotValue() {
		return GotValue;
	}

	/**
	* 设置字段GotValue的值，该字段的<br>
	* 字段名称 :获取内容<br>
	* 数据类型 :VARCHAR(3)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setGotValue(String gotValue) {
		this.GotValue = gotValue;
    }

	/**
	* 获取字段GotType的值，该字段的<br>
	* 字段名称 :获取类型<br>
	* 数据类型 :VARCHAR(10)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getGotType() {
		return GotType;
	}

	/**
	* 设置字段GotType的值，该字段的<br>
	* 字段名称 :获取类型<br>
	* 数据类型 :VARCHAR(10)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setGotType(String gotType) {
		this.GotType = gotType;
    }

	/**
	* 获取字段CurrentDay的值，该字段的<br>
	* 字段名称 :当天<br>
	* 数据类型 :VARCHAR(3)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getCurrentDay() {
		return CurrentDay;
	}

	/**
	* 设置字段CurrentDay的值，该字段的<br>
	* 字段名称 :当天<br>
	* 数据类型 :VARCHAR(3)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setCurrentDay(String currentDay) {
		this.CurrentDay = currentDay;
    }

	/**
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :prop1<br>
	* 数据类型 :VARCHAR(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :prop1<br>
	* 数据类型 :VARCHAR(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

}