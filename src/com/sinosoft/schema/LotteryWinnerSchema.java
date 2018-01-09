package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

import java.util.Date;

/**
 * 表名称：中奖信息表<br>
 * 表代码：LotteryWinner<br>
 * 表主键：WinTime<br>
 */
public class LotteryWinnerSchema extends Schema {
	private String WinnerId;

	private String WinnerName;

	private String WinnerEmail;

	private String WinnerMobil;

	private String Award;

	private Date WinTime;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("WinnerId", DataColumn.STRING, 0, 32 , 0 , false , false),
		new SchemaColumn("WinnerName", DataColumn.STRING, 1, 100 , 0 , false , false),
		new SchemaColumn("WinnerEmail", DataColumn.STRING, 2, 255 , 0 , false , false),
		new SchemaColumn("WinnerMobil", DataColumn.STRING, 3, 50 , 0 , false , false),
		new SchemaColumn("Award", DataColumn.STRING, 4, 255 , 0 , false , false),
		new SchemaColumn("WinTime", DataColumn.DATETIME, 5, 0 , 0 , true , true)
	};

	public static final String _TableCode = "LotteryWinner";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into LotteryWinner values(?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update LotteryWinner set WinnerId=?,WinnerName=?,WinnerEmail=?,WinnerMobil=?,Award=?,WinTime=? where WinTime=?";

	protected static final String _DeleteSQL = "delete from LotteryWinner  where WinTime=?";

	protected static final String _FillAllSQL = "select * from LotteryWinner  where WinTime=?";

	public LotteryWinnerSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[6];
	}

	protected Schema newInstance(){
		return new LotteryWinnerSchema();
	}

	protected SchemaSet newSet(){
		return new LotteryWinnerSet();
	}

	public LotteryWinnerSet query() {
		return query(null, -1, -1);
	}

	public LotteryWinnerSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public LotteryWinnerSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public LotteryWinnerSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (LotteryWinnerSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){WinnerId = (String)v;return;}
		if (i == 1){WinnerName = (String)v;return;}
		if (i == 2){WinnerEmail = (String)v;return;}
		if (i == 3){WinnerMobil = (String)v;return;}
		if (i == 4){Award = (String)v;return;}
		if (i == 5){WinTime = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return WinnerId;}
		if (i == 1){return WinnerName;}
		if (i == 2){return WinnerEmail;}
		if (i == 3){return WinnerMobil;}
		if (i == 4){return Award;}
		if (i == 5){return WinTime;}
		return null;
	}

	/**
	* 获取字段WinnerId的值，该字段的<br>
	* 字段名称 :中奖人ID<br>
	* 数据类型 :VARCHAR(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getWinnerId() {
		return WinnerId;
	}

	/**
	* 设置字段WinnerId的值，该字段的<br>
	* 字段名称 :中奖人ID<br>
	* 数据类型 :VARCHAR(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setWinnerId(String winnerId) {
		this.WinnerId = winnerId;
    }

	/**
	* 获取字段WinnerName的值，该字段的<br>
	* 字段名称 :中奖人姓名<br>
	* 数据类型 :VARCHAR(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getWinnerName() {
		return WinnerName;
	}

	/**
	* 设置字段WinnerName的值，该字段的<br>
	* 字段名称 :中奖人姓名<br>
	* 数据类型 :VARCHAR(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setWinnerName(String winnerName) {
		this.WinnerName = winnerName;
    }

	/**
	* 获取字段WinnerEmail的值，该字段的<br>
	* 字段名称 :中奖人邮箱<br>
	* 数据类型 :VARCHAR(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getWinnerEmail() {
		return WinnerEmail;
	}

	/**
	* 设置字段WinnerEmail的值，该字段的<br>
	* 字段名称 :中奖人邮箱<br>
	* 数据类型 :VARCHAR(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setWinnerEmail(String winnerEmail) {
		this.WinnerEmail = winnerEmail;
    }

	/**
	* 获取字段WinnerMobil的值，该字段的<br>
	* 字段名称 :中奖人电话<br>
	* 数据类型 :VARCHAR(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getWinnerMobil() {
		return WinnerMobil;
	}

	/**
	* 设置字段WinnerMobil的值，该字段的<br>
	* 字段名称 :中奖人电话<br>
	* 数据类型 :VARCHAR(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setWinnerMobil(String winnerMobil) {
		this.WinnerMobil = winnerMobil;
    }

	/**
	* 获取字段Award的值，该字段的<br>
	* 字段名称 :所中奖项<br>
	* 数据类型 :VARCHAR(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAward() {
		return Award;
	}

	/**
	* 设置字段Award的值，该字段的<br>
	* 字段名称 :所中奖项<br>
	* 数据类型 :VARCHAR(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAward(String award) {
		this.Award = award;
    }

	/**
	* 获取字段WinTime的值，该字段的<br>
	* 字段名称 :中奖时间<br>
	* 数据类型 :DATETIME<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public Date getWinTime() {
		return WinTime;
	}

	/**
	* 设置字段WinTime的值，该字段的<br>
	* 字段名称 :中奖时间<br>
	* 数据类型 :DATETIME<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setWinTime(Date winTime) {
		this.WinTime = winTime;
    }

}