package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

import java.util.Date;

import java.util.Date;

import java.util.Date;

import java.util.Date;

import java.util.Date;

import java.util.Date;

import java.util.Date;

import java.util.Date;

import java.util.Date;

import java.util.Date;

import java.util.Date;

/**
 * 表名称：抽奖活动表<br>
 * 表代码：LotteryAct<br>
 * 表主键：id<br>
 */
public class LotteryActSchema extends Schema {
	private String id;

	private Date createDate;

	private Date modifyDate;

	private String actCode;

	private String adress;

	private String awards;

	private String bussNo;

	private String eMail;

	private String memberId;

	private String mobile;

	private String orderNo;

	private String realName;

	private String recordType;

	private String type;

	private String useType;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 20 , 0 , true , true),
		new SchemaColumn("createDate", DataColumn.DATETIME, 1, 0 , 0 , false , false),
		new SchemaColumn("modifyDate", DataColumn.DATETIME, 2, 0 , 0 , false , false),
		new SchemaColumn("actCode", DataColumn.STRING, 3, 50 , 0 , false , false),
		new SchemaColumn("adress", DataColumn.STRING, 4, 200 , 0 , false , false),
		new SchemaColumn("awards", DataColumn.STRING, 5, 2 , 0 , false , false),
		new SchemaColumn("bussNo", DataColumn.STRING, 6, 20 , 0 , false , false),
		new SchemaColumn("eMail", DataColumn.STRING, 7, 50 , 0 , false , false),
		new SchemaColumn("memberId", DataColumn.STRING, 8, 50 , 0 , false , false),
		new SchemaColumn("mobile", DataColumn.STRING, 9, 11 , 0 , false , false),
		new SchemaColumn("orderNo", DataColumn.STRING, 10, 40 , 0 , false , false),
		new SchemaColumn("realName", DataColumn.STRING, 11, 20 , 0 , false , false),
		new SchemaColumn("recordType", DataColumn.STRING, 12, 10 , 0 , false , false),
		new SchemaColumn("type", DataColumn.STRING, 13, 2 , 0 , false , false),
		new SchemaColumn("useType", DataColumn.STRING, 14, 2 , 0 , false , false)
	};

	public static final String _TableCode = "LotteryAct";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into LotteryAct values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update LotteryAct set id=?,createDate=?,modifyDate=?,actCode=?,adress=?,awards=?,bussNo=?,eMail=?,memberId=?,mobile=?,orderNo=?,realName=?,recordType=?,type=?,useType=? where id=?";

	protected static final String _DeleteSQL = "delete from LotteryAct  where id=?";

	protected static final String _FillAllSQL = "select * from LotteryAct  where id=?";

	public LotteryActSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[15];
	}

	protected Schema newInstance(){
		return new LotteryActSchema();
	}

	protected SchemaSet newSet(){
		return new LotteryActSet();
	}

	public LotteryActSet query() {
		return query(null, -1, -1);
	}

	public LotteryActSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public LotteryActSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public LotteryActSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (LotteryActSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){createDate = (Date)v;return;}
		if (i == 2){modifyDate = (Date)v;return;}
		if (i == 3){actCode = (String)v;return;}
		if (i == 4){adress = (String)v;return;}
		if (i == 5){awards = (String)v;return;}
		if (i == 6){bussNo = (String)v;return;}
		if (i == 7){eMail = (String)v;return;}
		if (i == 8){memberId = (String)v;return;}
		if (i == 9){mobile = (String)v;return;}
		if (i == 10){orderNo = (String)v;return;}
		if (i == 11){realName = (String)v;return;}
		if (i == 12){recordType = (String)v;return;}
		if (i == 13){type = (String)v;return;}
		if (i == 14){useType = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return createDate;}
		if (i == 2){return modifyDate;}
		if (i == 3){return actCode;}
		if (i == 4){return adress;}
		if (i == 5){return awards;}
		if (i == 6){return bussNo;}
		if (i == 7){return eMail;}
		if (i == 8){return memberId;}
		if (i == 9){return mobile;}
		if (i == 10){return orderNo;}
		if (i == 11){return realName;}
		if (i == 12){return recordType;}
		if (i == 13){return type;}
		if (i == 14){return useType;}
		return null;
	}

	/**
	* 获取字段id的值，该字段的<br>
	* 字段名称 :流水号<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getid() {
		return id;
	}

	/**
	* 设置字段id的值，该字段的<br>
	* 字段名称 :流水号<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setid(String id) {
		this.id = id;
    }

	/**
	* 获取字段createDate的值，该字段的<br>
	* 字段名称 :createDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getcreateDate() {
		return createDate;
	}

	/**
	* 设置字段createDate的值，该字段的<br>
	* 字段名称 :createDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcreateDate(Date createDate) {
		this.createDate = createDate;
    }

	/**
	* 获取字段modifyDate的值，该字段的<br>
	* 字段名称 :modifyDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getmodifyDate() {
		return modifyDate;
	}

	/**
	* 设置字段modifyDate的值，该字段的<br>
	* 字段名称 :modifyDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmodifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
    }

	/**
	* 获取字段actCode的值，该字段的<br>
	* 字段名称 :活动编码<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getactCode() {
		return actCode;
	}

	/**
	* 设置字段actCode的值，该字段的<br>
	* 字段名称 :活动编码<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setactCode(String actCode) {
		this.actCode = actCode;
    }

	/**
	* 获取字段adress的值，该字段的<br>
	* 字段名称 :联系地址<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getadress() {
		return adress;
	}

	/**
	* 设置字段adress的值，该字段的<br>
	* 字段名称 :联系地址<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setadress(String adress) {
		this.adress = adress;
    }

	/**
	* 获取字段awards的值，该字段的<br>
	* 字段名称 :奖项<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getawards() {
		return awards;
	}

	/**
	* 设置字段awards的值，该字段的<br>
	* 字段名称 :奖项<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setawards(String awards) {
		this.awards = awards;
    }

	/**
	* 获取字段bussNo的值，该字段的<br>
	* 字段名称 :业务号<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getbussNo() {
		return bussNo;
	}

	/**
	* 设置字段bussNo的值，该字段的<br>
	* 字段名称 :业务号<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setbussNo(String bussNo) {
		this.bussNo = bussNo;
    }

	/**
	* 获取字段eMail的值，该字段的<br>
	* 字段名称 :邮箱<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String geteMail() {
		return eMail;
	}

	/**
	* 设置字段eMail的值，该字段的<br>
	* 字段名称 :邮箱<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void seteMail(String eMail) {
		this.eMail = eMail;
    }

	/**
	* 获取字段memberId的值，该字段的<br>
	* 字段名称 :会员id<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getmemberId() {
		return memberId;
	}

	/**
	* 设置字段memberId的值，该字段的<br>
	* 字段名称 :会员id<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmemberId(String memberId) {
		this.memberId = memberId;
    }

	/**
	* 获取字段mobile的值，该字段的<br>
	* 字段名称 :手机号<br>
	* 数据类型 :varchar(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getmobile() {
		return mobile;
	}

	/**
	* 设置字段mobile的值，该字段的<br>
	* 字段名称 :手机号<br>
	* 数据类型 :varchar(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmobile(String mobile) {
		this.mobile = mobile;
    }

	/**
	* 获取字段orderNo的值，该字段的<br>
	* 字段名称 :订单号<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getorderNo() {
		return orderNo;
	}

	/**
	* 设置字段orderNo的值，该字段的<br>
	* 字段名称 :订单号<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setorderNo(String orderNo) {
		this.orderNo = orderNo;
    }

	/**
	* 获取字段realName的值，该字段的<br>
	* 字段名称 :真实姓名<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrealName() {
		return realName;
	}

	/**
	* 设置字段realName的值，该字段的<br>
	* 字段名称 :真实姓名<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrealName(String realName) {
		this.realName = realName;
    }

	/**
	* 获取字段recordType的值，该字段的<br>
	* 字段名称 :记录类型<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	参与抽奖JOIN 抽奖结果RESULT<br>
	*/
	public String getrecordType() {
		return recordType;
	}

	/**
	* 设置字段recordType的值，该字段的<br>
	* 字段名称 :记录类型<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	参与抽奖JOIN 抽奖结果RESULT<br>
	*/
	public void setrecordType(String recordType) {
		this.recordType = recordType;
    }

	/**
	* 获取字段type的值，该字段的<br>
	* 字段名称 :中奖状态<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String gettype() {
		return type;
	}

	/**
	* 设置字段type的值，该字段的<br>
	* 字段名称 :中奖状态<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void settype(String type) {
		this.type = type;
    }

	/**
	* 获取字段useType的值，该字段的<br>
	* 字段名称 :使用状态<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getuseType() {
		return useType;
	}

	/**
	* 设置字段useType的值，该字段的<br>
	* 字段名称 :使用状态<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setuseType(String useType) {
		this.useType = useType;
    }

}