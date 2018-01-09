package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

/**
 * 表名称：SDTBTradeRecord<br>
 * 表代码：SDTBTradeRecord<br>
 * 表主键：Id<br>
 */
public class SDTBTradeRecordSchema extends Schema {
	private String Id;

	private String AppntName;

	private String CertificateType;

	private String CertificateTypeName;

	private String CertificateId;

	private String TradeDate;

	private String TradeSum;

	private String UseSum;

	private String orderSn;

	private String appntMobileNo;

	private String channelSn;

	private String activitySn;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("Id", DataColumn.STRING, 0, 30 , 0 , true , true),
		new SchemaColumn("AppntName", DataColumn.STRING, 1, 50 , 0 , false , false),
		new SchemaColumn("CertificateType", DataColumn.STRING, 2, 10 , 0 , false , false),
		new SchemaColumn("CertificateTypeName", DataColumn.STRING, 3, 20 , 0 , false , false),
		new SchemaColumn("CertificateId", DataColumn.STRING, 4, 50 , 0 , false , false),
		new SchemaColumn("TradeDate", DataColumn.STRING, 5, 20 , 0 , false , false),
		new SchemaColumn("TradeSum", DataColumn.STRING, 6, 10 , 0 , false , false),
		new SchemaColumn("UseSum", DataColumn.STRING, 7, 10 , 0 , false , false),
		new SchemaColumn("orderSn", DataColumn.STRING, 8, 50 , 0 , false , false),
		new SchemaColumn("appntMobileNo", DataColumn.STRING, 9, 20 , 0 , false , false),
		new SchemaColumn("channelSn", DataColumn.STRING, 10, 20 , 0 , false , false),
		new SchemaColumn("activitySn", DataColumn.STRING, 11, 50 , 0 , false , false)
	};

	public static final String _TableCode = "SDTBTradeRecord";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into SDTBTradeRecord values(?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update SDTBTradeRecord set Id=?,AppntName=?,CertificateType=?,CertificateTypeName=?,CertificateId=?,TradeDate=?,TradeSum=?,UseSum=?,orderSn=?,appntMobileNo=?,channelSn=?,activitySn=? where Id=?";

	protected static final String _DeleteSQL = "delete from SDTBTradeRecord  where Id=?";

	protected static final String _FillAllSQL = "select * from SDTBTradeRecord  where Id=?";

	public SDTBTradeRecordSchema(){
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
		return new SDTBTradeRecordSchema();
	}

	protected SchemaSet newSet(){
		return new SDTBTradeRecordSet();
	}

	public SDTBTradeRecordSet query() {
		return query(null, -1, -1);
	}

	public SDTBTradeRecordSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public SDTBTradeRecordSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public SDTBTradeRecordSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (SDTBTradeRecordSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){Id = (String)v;return;}
		if (i == 1){AppntName = (String)v;return;}
		if (i == 2){CertificateType = (String)v;return;}
		if (i == 3){CertificateTypeName = (String)v;return;}
		if (i == 4){CertificateId = (String)v;return;}
		if (i == 5){TradeDate = (String)v;return;}
		if (i == 6){TradeSum = (String)v;return;}
		if (i == 7){UseSum = (String)v;return;}
		if (i == 8){orderSn = (String)v;return;}
		if (i == 9){appntMobileNo = (String)v;return;}
		if (i == 10){channelSn = (String)v;return;}
		if (i == 11){activitySn = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return Id;}
		if (i == 1){return AppntName;}
		if (i == 2){return CertificateType;}
		if (i == 3){return CertificateTypeName;}
		if (i == 4){return CertificateId;}
		if (i == 5){return TradeDate;}
		if (i == 6){return TradeSum;}
		if (i == 7){return UseSum;}
		if (i == 8){return orderSn;}
		if (i == 9){return appntMobileNo;}
		if (i == 10){return channelSn;}
		if (i == 11){return activitySn;}
		return null;
	}

	/**
	* 获取字段Id的值，该字段的<br>
	* 字段名称 :Id<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getId() {
		return Id;
	}

	/**
	* 设置字段Id的值，该字段的<br>
	* 字段名称 :Id<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setId(String id) {
		this.Id = id;
    }

	/**
	* 获取字段AppntName的值，该字段的<br>
	* 字段名称 :AppntName<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAppntName() {
		return AppntName;
	}

	/**
	* 设置字段AppntName的值，该字段的<br>
	* 字段名称 :AppntName<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAppntName(String appntName) {
		this.AppntName = appntName;
    }

	/**
	* 获取字段CertificateType的值，该字段的<br>
	* 字段名称 :CertificateType<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCertificateType() {
		return CertificateType;
	}

	/**
	* 设置字段CertificateType的值，该字段的<br>
	* 字段名称 :CertificateType<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCertificateType(String certificateType) {
		this.CertificateType = certificateType;
    }

	/**
	* 获取字段CertificateTypeName的值，该字段的<br>
	* 字段名称 :CertificateTypeName<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCertificateTypeName() {
		return CertificateTypeName;
	}

	/**
	* 设置字段CertificateTypeName的值，该字段的<br>
	* 字段名称 :CertificateTypeName<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCertificateTypeName(String certificateTypeName) {
		this.CertificateTypeName = certificateTypeName;
    }

	/**
	* 获取字段CertificateId的值，该字段的<br>
	* 字段名称 :CertificateId<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCertificateId() {
		return CertificateId;
	}

	/**
	* 设置字段CertificateId的值，该字段的<br>
	* 字段名称 :CertificateId<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCertificateId(String certificateId) {
		this.CertificateId = certificateId;
    }

	/**
	* 获取字段TradeDate的值，该字段的<br>
	* 字段名称 :TradeDate<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getTradeDate() {
		return TradeDate;
	}

	/**
	* 设置字段TradeDate的值，该字段的<br>
	* 字段名称 :TradeDate<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setTradeDate(String tradeDate) {
		this.TradeDate = tradeDate;
    }

	/**
	* 获取字段TradeSum的值，该字段的<br>
	* 字段名称 :TradeSum<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getTradeSum() {
		return TradeSum;
	}

	/**
	* 设置字段TradeSum的值，该字段的<br>
	* 字段名称 :TradeSum<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setTradeSum(String tradeSum) {
		this.TradeSum = tradeSum;
    }

	/**
	* 获取字段UseSum的值，该字段的<br>
	* 字段名称 :UseSum<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getUseSum() {
		return UseSum;
	}

	/**
	* 设置字段UseSum的值，该字段的<br>
	* 字段名称 :UseSum<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setUseSum(String useSum) {
		this.UseSum = useSum;
    }

	/**
	* 获取字段orderSn的值，该字段的<br>
	* 字段名称 :订单号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getorderSn() {
		return orderSn;
	}

	/**
	* 设置字段orderSn的值，该字段的<br>
	* 字段名称 :订单号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setorderSn(String orderSn) {
		this.orderSn = orderSn;
    }

	/**
	* 获取字段appntMobileNo的值，该字段的<br>
	* 字段名称 :投保人手机号<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getappntMobileNo() {
		return appntMobileNo;
	}

	/**
	* 设置字段appntMobileNo的值，该字段的<br>
	* 字段名称 :投保人手机号<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setappntMobileNo(String appntMobileNo) {
		this.appntMobileNo = appntMobileNo;
    }

	/**
	* 获取字段channelSn的值，该字段的<br>
	* 字段名称 :渠道<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getchannelSn() {
		return channelSn;
	}

	/**
	* 设置字段channelSn的值，该字段的<br>
	* 字段名称 :渠道<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setchannelSn(String channelSn) {
		this.channelSn = channelSn;
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

}