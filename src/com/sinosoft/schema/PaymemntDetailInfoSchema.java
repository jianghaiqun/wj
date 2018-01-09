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
 * 表名称：稿费明细表<br>
 * 表代码：PaymemntDetailInfo<br>
 * 表主键：id<br>
 */
public class PaymemntDetailInfoSchema extends Schema {
	private String id;

	private String payTime;

	private BigDecimal payPrice;

	private String isPay;

	private String authorDetailInfo_id;

	private String remark1;

	private String remark2;

	private String remark3;

	private Date createDate;

	private String createUser;

	private Date modifyDate;

	private String modifyUser;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("payTime", DataColumn.STRING, 1, 10 , 0 , false , false),
		new SchemaColumn("payPrice", DataColumn.BIGDECIMAL, 2, 10 , 0 , false , false),
		new SchemaColumn("isPay", DataColumn.STRING, 3, 2 , 0 , false , false),
		new SchemaColumn("authorDetailInfo_id", DataColumn.STRING, 4, 32 , 0 , false , false),
		new SchemaColumn("remark1", DataColumn.STRING, 5, 255 , 0 , false , false),
		new SchemaColumn("remark2", DataColumn.STRING, 6, 255 , 0 , false , false),
		new SchemaColumn("remark3", DataColumn.STRING, 7, 255 , 0 , false , false),
		new SchemaColumn("createDate", DataColumn.DATETIME, 8, 0 , 0 , false , false),
		new SchemaColumn("createUser", DataColumn.STRING, 9, 100 , 0 , false , false),
		new SchemaColumn("modifyDate", DataColumn.DATETIME, 10, 0 , 0 , false , false),
		new SchemaColumn("modifyUser", DataColumn.STRING, 11, 100 , 0 , false , false)
	};

	public static final String _TableCode = "PaymemntDetailInfo";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into PaymemntDetailInfo values(?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update PaymemntDetailInfo set id=?,payTime=?,payPrice=?,isPay=?,authorDetailInfo_id=?,remark1=?,remark2=?,remark3=?,createDate=?,createUser=?,modifyDate=?,modifyUser=? where id=?";

	protected static final String _DeleteSQL = "delete from PaymemntDetailInfo  where id=?";

	protected static final String _FillAllSQL = "select * from PaymemntDetailInfo  where id=?";

	public PaymemntDetailInfoSchema(){
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
		return new PaymemntDetailInfoSchema();
	}

	protected SchemaSet newSet(){
		return new PaymemntDetailInfoSet();
	}

	public PaymemntDetailInfoSet query() {
		return query(null, -1, -1);
	}

	public PaymemntDetailInfoSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public PaymemntDetailInfoSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public PaymemntDetailInfoSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (PaymemntDetailInfoSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){payTime = (String)v;return;}
		if (i == 2){if(v==null){payPrice = null;}else{payPrice =  ((BigDecimal)v) ;}return;}
		if (i == 3){isPay = (String)v;return;}
		if (i == 4){authorDetailInfo_id = (String)v;return;}
		if (i == 5){remark1 = (String)v;return;}
		if (i == 6){remark2 = (String)v;return;}
		if (i == 7){remark3 = (String)v;return;}
		if (i == 8){createDate = (Date)v;return;}
		if (i == 9){createUser = (String)v;return;}
		if (i == 10){modifyDate = (Date)v;return;}
		if (i == 11){modifyUser = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return payTime;}
		if (i == 2){return payPrice;}
		if (i == 3){return isPay;}
		if (i == 4){return authorDetailInfo_id;}
		if (i == 5){return remark1;}
		if (i == 6){return remark2;}
		if (i == 7){return remark3;}
		if (i == 8){return createDate;}
		if (i == 9){return createUser;}
		if (i == 10){return modifyDate;}
		if (i == 11){return modifyUser;}
		return null;
	}

	/**
	* 获取字段id的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getid() {
		return id;
	}

	/**
	* 设置字段id的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setid(String id) {
		this.id = id;
    }

	/**
	* 获取字段payTime的值，该字段的<br>
	* 字段名称 :稿费支付时间<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getpayTime() {
		return payTime;
	}

	/**
	* 设置字段payTime的值，该字段的<br>
	* 字段名称 :稿费支付时间<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpayTime(String payTime) {
		this.payTime = payTime;
    }

	/**
	* 获取字段payPrice的值，该字段的<br>
	* 字段名称 :稿费支付金额<br>
	* 数据类型 :decimal(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public BigDecimal getpayPrice() {
		return payPrice;
	}

	/**
	* 设置字段payPrice的值，该字段的<br>
	* 字段名称 :稿费支付金额<br>
	* 数据类型 :decimal(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpayPrice(BigDecimal payPrice) {
		this.payPrice = payPrice;
    }

	/**
	* 获取字段isPay的值，该字段的<br>
	* 字段名称 :是否支付<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getisPay() {
		return isPay;
	}

	/**
	* 设置字段isPay的值，该字段的<br>
	* 字段名称 :是否支付<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setisPay(String isPay) {
		this.isPay = isPay;
    }

	/**
	* 获取字段authorDetailInfo_id的值，该字段的<br>
	* 字段名称 :作者详细信息表ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getauthorDetailInfo_id() {
		return authorDetailInfo_id;
	}

	/**
	* 设置字段authorDetailInfo_id的值，该字段的<br>
	* 字段名称 :作者详细信息表ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setauthorDetailInfo_id(String authorDetailInfo_id) {
		this.authorDetailInfo_id = authorDetailInfo_id;
    }

	/**
	* 获取字段remark1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getremark1() {
		return remark1;
	}

	/**
	* 设置字段remark1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setremark1(String remark1) {
		this.remark1 = remark1;
    }

	/**
	* 获取字段remark2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getremark2() {
		return remark2;
	}

	/**
	* 设置字段remark2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setremark2(String remark2) {
		this.remark2 = remark2;
    }

	/**
	* 获取字段remark3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getremark3() {
		return remark3;
	}

	/**
	* 设置字段remark3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setremark3(String remark3) {
		this.remark3 = remark3;
    }

	/**
	* 获取字段createDate的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getcreateDate() {
		return createDate;
	}

	/**
	* 设置字段createDate的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcreateDate(Date createDate) {
		this.createDate = createDate;
    }

	/**
	* 获取字段createUser的值，该字段的<br>
	* 字段名称 :创建人<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcreateUser() {
		return createUser;
	}

	/**
	* 设置字段createUser的值，该字段的<br>
	* 字段名称 :创建人<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcreateUser(String createUser) {
		this.createUser = createUser;
    }

	/**
	* 获取字段modifyDate的值，该字段的<br>
	* 字段名称 :修改时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getmodifyDate() {
		return modifyDate;
	}

	/**
	* 设置字段modifyDate的值，该字段的<br>
	* 字段名称 :修改时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmodifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
    }

	/**
	* 获取字段modifyUser的值，该字段的<br>
	* 字段名称 :修改人<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getmodifyUser() {
		return modifyUser;
	}

	/**
	* 设置字段modifyUser的值，该字段的<br>
	* 字段名称 :修改人<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmodifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
    }

}