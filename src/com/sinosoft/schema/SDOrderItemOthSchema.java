package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：订单项表2<br>
 * 表代码：SDOrderItemOth<br>
 * 表主键：id<br>
 */
public class SDOrderItemOthSchema extends Schema {
	private String id;

	private Date createDate;

	private Date modifyDate;

	private String orderitemSn;

	private String orderSn;

	private String recognizeeSn;

	private String tpySn;

	private String tpySysSn;

	private String tpySysPaySn;

	private String sdinformationinsured_id;
	
	private String Itinerary;
	
	private String AgencyNo;
	
	private String GroupType;
	
	private String AppntType;
	
	private String Remark;
	
	private String branchInnerCode;
	
	private String outComCode;
	
	private String VoucherType;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("createDate", DataColumn.DATETIME, 1, 0 , 0 , false , false),
		new SchemaColumn("modifyDate", DataColumn.DATETIME, 2, 0 , 0 , false , false),
		new SchemaColumn("orderitemSn", DataColumn.STRING, 3, 20 , 0 , false , false),
		new SchemaColumn("orderSn", DataColumn.STRING, 4, 20 , 0 , false , false),
		new SchemaColumn("recognizeeSn", DataColumn.STRING, 5, 20 , 0 , false , false),
		new SchemaColumn("tpySn", DataColumn.STRING, 6, 30 , 0 , false , false),
		new SchemaColumn("tpySysSn", DataColumn.STRING, 7, 30 , 0 , false , false),
		new SchemaColumn("tpySysPaySn", DataColumn.STRING, 8, 31 , 0 , false , false),
		new SchemaColumn("sdinformationinsured_id", DataColumn.STRING, 9, 32 , 0 , false , false),
		new SchemaColumn("Itinerary", DataColumn.STRING, 10, 256 , 0 , false , false),
		new SchemaColumn("AgencyNo", DataColumn.STRING, 11, 50 , 0 , false , false),
		new SchemaColumn("GroupType", DataColumn.STRING, 12, 20 , 0 , false , false),
		new SchemaColumn("AppntType", DataColumn.STRING, 13, 5 , 0 , false , false),
		new SchemaColumn("Remark", DataColumn.STRING, 14, 200 , 0 , false , false),
		new SchemaColumn("branchInnerCode", DataColumn.STRING, 15, 100 , 0 , false , false),
		new SchemaColumn("outComCode", DataColumn.STRING, 16, 32 , 0 , false , false),
		new SchemaColumn("VoucherType", DataColumn.STRING, 17, 20 , 0 , false , false)
	};

	public static final String _TableCode = "SDOrderItemOth";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into SDOrderItemOth values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update SDOrderItemOth set id=?,createDate=?,modifyDate=?,orderitemSn=?,orderSn=?,recognizeeSn=?,tpySn=?,tpySysSn=?,tpySysPaySn=?,sdinformationinsured_id=?,Itinerary=?,AgencyNo=?,GroupType=?,AppntType=?,Remark=?,branchInnerCode=?,outComCode=?,VoucherType=? where id=?";

	protected static final String _DeleteSQL = "delete from SDOrderItemOth  where id=?";

	protected static final String _FillAllSQL = "select * from SDOrderItemOth  where id=?";

	public SDOrderItemOthSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[18];
	}

	protected Schema newInstance(){
		return new SDOrderItemOthSchema();
	}

	protected SchemaSet newSet(){
		return new SDOrderItemOthSet();
	}

	public SDOrderItemOthSet query() {
		return query(null, -1, -1);
	}

	public SDOrderItemOthSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public SDOrderItemOthSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public SDOrderItemOthSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (SDOrderItemOthSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){createDate = (Date)v;return;}
		if (i == 2){modifyDate = (Date)v;return;}
		if (i == 3){orderitemSn = (String)v;return;}
		if (i == 4){orderSn = (String)v;return;}
		if (i == 5){recognizeeSn = (String)v;return;}
		if (i == 6){tpySn = (String)v;return;}
		if (i == 7){tpySysSn = (String)v;return;}
		if (i == 8){tpySysPaySn = (String)v;return;}
		if (i == 9){sdinformationinsured_id = (String)v;return;}
		if (i == 10){Itinerary = (String)v;return;}
		if (i == 11){AgencyNo = (String)v;return;}
		if (i == 12){GroupType = (String)v;return;}
		if (i == 13){AppntType = (String)v;return;}
		if (i == 14){Remark = (String)v;return;}
		if (i == 15){branchInnerCode = (String)v;return;}
		if (i == 16){outComCode = (String)v;return;}
		if (i == 17){VoucherType = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return createDate;}
		if (i == 2){return modifyDate;}
		if (i == 3){return orderitemSn;}
		if (i == 4){return orderSn;}
		if (i == 5){return recognizeeSn;}
		if (i == 6){return tpySn;}
		if (i == 7){return tpySysSn;}
		if (i == 8){return tpySysPaySn;}
		if (i == 9){return sdinformationinsured_id;}
		if (i == 10){return Itinerary;}
		if (i == 11){return AgencyNo;}
		if (i == 12){return GroupType;}
		if (i == 13){return AppntType;}
		if (i == 14){return Remark;}
		if (i == 15){return branchInnerCode;}
		if (i == 16){return outComCode;}
		if (i == 17){return VoucherType;}
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

	/**
	* 获取字段modifyDate的值，该字段的<br>
	* 字段名称 :修改日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getmodifyDate() {
		return modifyDate;
	}

	/**
	* 设置字段modifyDate的值，该字段的<br>
	* 字段名称 :修改日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmodifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
    }

	/**
	* 获取字段orderitemSn的值，该字段的<br>
	* 字段名称 :订单项目编号<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getorderitemSn() {
		return orderitemSn;
	}

	/**
	* 设置字段orderitemSn的值，该字段的<br>
	* 字段名称 :订单项目编号<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setorderitemSn(String orderitemSn) {
		this.orderitemSn = orderitemSn;
    }

	/**
	* 获取字段orderSn的值，该字段的<br>
	* 字段名称 :订单编号<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getorderSn() {
		return orderSn;
	}

	/**
	* 设置字段orderSn的值，该字段的<br>
	* 字段名称 :订单编号<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setorderSn(String orderSn) {
		this.orderSn = orderSn;
    }

	/**
	* 获取字段recognizeeSn的值，该字段的<br>
	* 字段名称 :被保人编号<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeSn() {
		return recognizeeSn;
	}

	/**
	* 设置字段recognizeeSn的值，该字段的<br>
	* 字段名称 :被保人编号<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeSn(String recognizeeSn) {
		this.recognizeeSn = recognizeeSn;
    }

	/**
	* 获取字段tpySn的值，该字段的<br>
	* 字段名称 :外部流水号<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String gettpySn() {
		return tpySn;
	}

	/**
	* 设置字段tpySn的值，该字段的<br>
	* 字段名称 :外部流水号<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void settpySn(String tpySn) {
		this.tpySn = tpySn;
    }

	/**
	* 获取字段tpySysSn的值，该字段的<br>
	* 字段名称 :外部系统订单号<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	太平洋<br>
	*/
	public String gettpySysSn() {
		return tpySysSn;
	}

	/**
	* 设置字段tpySysSn的值，该字段的<br>
	* 字段名称 :外部系统订单号<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	太平洋<br>
	*/
	public void settpySysSn(String tpySysSn) {
		this.tpySysSn = tpySysSn;
    }

	/**
	* 获取字段tpySysPaySn的值，该字段的<br>
	* 字段名称 :外部系统支付流水号<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	太平洋<br>
	*/
	public String gettpySysPaySn() {
		return tpySysPaySn;
	}

	/**
	* 设置字段tpySysPaySn的值，该字段的<br>
	* 字段名称 :外部系统支付流水号<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	太平洋<br>
	*/
	public void settpySysPaySn(String tpySysPaySn) {
		this.tpySysPaySn = tpySysPaySn;
    }

	/**
	* 获取字段sdinformationinsured_id的值，该字段的<br>
	* 字段名称 :被保人信息表ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getsdinformationinsured_id() {
		return sdinformationinsured_id;
	}

	/**
	* 设置字段sdinformationinsured_id的值，该字段的<br>
	* 字段名称 :被保人信息表ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setsdinformationinsured_id(String sdinformationinsured_id) {
		this.sdinformationinsured_id = sdinformationinsured_id;
    }
	
	public String getItinerary() {
		return Itinerary;
	}

	public String getAgencyNo() {
		return AgencyNo;
	}

	public String getGroupType() {
		return GroupType;
	}

	public String getAppntType() {
		return AppntType;
	}

	public String getRemark() {
		return Remark;
	}

	public void setItinerary(String itinerary) {
		Itinerary = itinerary;
	}

	public void setAgencyNo(String agencyNo) {
		AgencyNo = agencyNo;
	}

	public void setGroupType(String groupType) {
		GroupType = groupType;
	}

	public void setAppntType(String appntType) {
		AppntType = appntType;
	}

	public void setRemark(String remark) {
		Remark = remark;
	}

	/**
	* 获取字段branchInnerCode的值，该字段的<br>
	* 字段名称 :机构编码<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	美行保<br>
	*/
	public String getBranchInnerCode() {
		return branchInnerCode;
	}

	/**
	* 设置字段branchInnerCode的值，该字段的<br>
	* 字段名称 :机构编码<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	美行保<br>
	*/
	public void setBranchInnerCode(String branchInnerCode) {
		this.branchInnerCode = branchInnerCode;
	}

	
	public String getOutComCode() {
	
		return outComCode;
	}
	
	public void setOutComCode(String outComCode) {
	
		this.outComCode = outComCode;
	}

	/**
	* 获取字段VoucherType的值，该字段的<br>
	* 字段名称 :凭证类型<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	美行保<br>
	*/
	public String getVoucherType() {
		return VoucherType;
	}

	/**
	* 设置字段VoucherType的值，该字段的<br>
	* 字段名称 :凭证类型<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	美行保<br>
	*/
	public void setVoucherType(String voucherType) {
		VoucherType = voucherType;
	}

}