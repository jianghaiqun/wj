package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：交易信息表<br>
 * 表代码：TradeInformation<br>
 * 表主键：id<br>
 */
public class TradeInformationSchema extends Schema {
	private String id;

	private Date createDate;

	private Date modifyDate;

	private String errorMsg;

	private String merId;

	private String ordAmt;

	private String ordID;

	private String payStatus;

	private String payType;

	private String returnSign;

	private String sendSign;

	private String tradeBank;

	private String tradeDate;

	private String tradeResult;

	private String tradeSeriNO;

	private String tradeType;

	private String errorMsg2;

	private String receiveDate;

	private String receiveDate2;

	private String refundId;

	private String returnSign2;

	private String sendDate;

	private String sendDate2;

	private String sendSign2;

	private String receiveRefundId;

	private String tradeCheckSeriNo;

	private String weixicookie;
	
	private String billOrderNo;
	
	private String kqPaySn;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("createDate", DataColumn.DATETIME, 1, 0 , 0 , false , false),
		new SchemaColumn("modifyDate", DataColumn.DATETIME, 2, 0 , 0 , false , false),
		new SchemaColumn("errorMsg", DataColumn.STRING, 3, 255 , 0 , false , false),
		new SchemaColumn("merId", DataColumn.STRING, 4, 255 , 0 , false , false),
		new SchemaColumn("ordAmt", DataColumn.STRING, 5, 255 , 0 , false , false),
		new SchemaColumn("ordID", DataColumn.STRING, 6, 255 , 0 , false , false),
		new SchemaColumn("payStatus", DataColumn.STRING, 7, 255 , 0 , false , false),
		new SchemaColumn("payType", DataColumn.STRING, 8, 255 , 0 , false , false),
		new SchemaColumn("returnSign", DataColumn.CLOB, 9, 0 , 0 , false , false),
		new SchemaColumn("sendSign", DataColumn.CLOB, 10, 0 , 0 , false , false),
		new SchemaColumn("tradeBank", DataColumn.STRING, 11, 255 , 0 , false , false),
		new SchemaColumn("tradeDate", DataColumn.STRING, 12, 255 , 0 , false , false),
		new SchemaColumn("tradeResult", DataColumn.STRING, 13, 255 , 0 , false , false),
		new SchemaColumn("tradeSeriNO", DataColumn.STRING, 14, 255 , 0 , false , false),
		new SchemaColumn("tradeType", DataColumn.STRING, 15, 255 , 0 , false , false),
		new SchemaColumn("errorMsg2", DataColumn.STRING, 16, 255 , 0 , false , false),
		new SchemaColumn("receiveDate", DataColumn.STRING, 17, 255 , 0 , false , false),
		new SchemaColumn("receiveDate2", DataColumn.STRING, 18, 255 , 0 , false , false),
		new SchemaColumn("refundId", DataColumn.STRING, 19, 255 , 0 , false , false),
		new SchemaColumn("returnSign2", DataColumn.CLOB, 20, 0 , 0 , false , false),
		new SchemaColumn("sendDate", DataColumn.STRING, 21, 255 , 0 , false , false),
		new SchemaColumn("sendDate2", DataColumn.STRING, 22, 255 , 0 , false , false),
		new SchemaColumn("sendSign2", DataColumn.CLOB, 23, 0 , 0 , false , false),
		new SchemaColumn("receiveRefundId", DataColumn.STRING, 24, 255 , 0 , false , false),
		new SchemaColumn("tradeCheckSeriNo", DataColumn.STRING, 25, 255 , 0 , false , false),
		new SchemaColumn("weixicookie", DataColumn.STRING, 26, 255 , 0 , false , false),
		new SchemaColumn("billOrderNo", DataColumn.STRING, 27, 255 , 0 , false , false),
		new SchemaColumn("kqPaySn", DataColumn.STRING, 28, 255 , 0 , false , false)
	};

	public static final String _TableCode = "TradeInformation";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into TradeInformation values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update TradeInformation set id=?,createDate=?,modifyDate=?,errorMsg=?,merId=?,ordAmt=?,ordID=?,payStatus=?,payType=?,returnSign=?,sendSign=?,tradeBank=?,tradeDate=?,tradeResult=?,tradeSeriNO=?,tradeType=?,errorMsg2=?,receiveDate=?,receiveDate2=?,refundId=?,returnSign2=?,sendDate=?,sendDate2=?,sendSign2=?,receiveRefundId=?,tradeCheckSeriNo=?,weixicookie=?,billOrderNo=?,kqPaySn=? where id=?";

	protected static final String _DeleteSQL = "delete from TradeInformation  where id=?";

	protected static final String _FillAllSQL = "select * from TradeInformation  where id=?";

	public TradeInformationSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[29];
	}

	protected Schema newInstance(){
		return new TradeInformationSchema();
	}

	protected SchemaSet newSet(){
		return new TradeInformationSet();
	}

	public TradeInformationSet query() {
		return query(null, -1, -1);
	}

	public TradeInformationSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public TradeInformationSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public TradeInformationSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (TradeInformationSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){createDate = (Date)v;return;}
		if (i == 2){modifyDate = (Date)v;return;}
		if (i == 3){errorMsg = (String)v;return;}
		if (i == 4){merId = (String)v;return;}
		if (i == 5){ordAmt = (String)v;return;}
		if (i == 6){ordID = (String)v;return;}
		if (i == 7){payStatus = (String)v;return;}
		if (i == 8){payType = (String)v;return;}
		if (i == 9){returnSign = (String)v;return;}
		if (i == 10){sendSign = (String)v;return;}
		if (i == 11){tradeBank = (String)v;return;}
		if (i == 12){tradeDate = (String)v;return;}
		if (i == 13){tradeResult = (String)v;return;}
		if (i == 14){tradeSeriNO = (String)v;return;}
		if (i == 15){tradeType = (String)v;return;}
		if (i == 16){errorMsg2 = (String)v;return;}
		if (i == 17){receiveDate = (String)v;return;}
		if (i == 18){receiveDate2 = (String)v;return;}
		if (i == 19){refundId = (String)v;return;}
		if (i == 20){returnSign2 = (String)v;return;}
		if (i == 21){sendDate = (String)v;return;}
		if (i == 22){sendDate2 = (String)v;return;}
		if (i == 23){sendSign2 = (String)v;return;}
		if (i == 24){receiveRefundId = (String)v;return;}
		if (i == 25){tradeCheckSeriNo = (String)v;return;}
		if (i == 26){weixicookie = (String)v;return;}
		if (i == 27){billOrderNo = (String)v;return;}
		if (i == 28){kqPaySn = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return createDate;}
		if (i == 2){return modifyDate;}
		if (i == 3){return errorMsg;}
		if (i == 4){return merId;}
		if (i == 5){return ordAmt;}
		if (i == 6){return ordID;}
		if (i == 7){return payStatus;}
		if (i == 8){return payType;}
		if (i == 9){return returnSign;}
		if (i == 10){return sendSign;}
		if (i == 11){return tradeBank;}
		if (i == 12){return tradeDate;}
		if (i == 13){return tradeResult;}
		if (i == 14){return tradeSeriNO;}
		if (i == 15){return tradeType;}
		if (i == 16){return errorMsg2;}
		if (i == 17){return receiveDate;}
		if (i == 18){return receiveDate2;}
		if (i == 19){return refundId;}
		if (i == 20){return returnSign2;}
		if (i == 21){return sendDate;}
		if (i == 22){return sendDate2;}
		if (i == 23){return sendSign2;}
		if (i == 24){return receiveRefundId;}
		if (i == 25){return tradeCheckSeriNo;}
		if (i == 26){return weixicookie;}
		if (i == 27){return billOrderNo;}
		if (i == 28){return kqPaySn;}
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
	* 字段名称 :结束日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getmodifyDate() {
		return modifyDate;
	}

	/**
	* 设置字段modifyDate的值，该字段的<br>
	* 字段名称 :结束日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmodifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
    }

	/**
	* 获取字段errorMsg的值，该字段的<br>
	* 字段名称 :支付错误信息<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String geterrorMsg() {
		return errorMsg;
	}

	/**
	* 设置字段errorMsg的值，该字段的<br>
	* 字段名称 :支付错误信息<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void seterrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
    }

	/**
	* 获取字段merId的值，该字段的<br>
	* 字段名称 :商户号<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getmerId() {
		return merId;
	}

	/**
	* 设置字段merId的值，该字段的<br>
	* 字段名称 :商户号<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmerId(String merId) {
		this.merId = merId;
    }

	/**
	* 获取字段ordAmt的值，该字段的<br>
	* 字段名称 :订单金额<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getordAmt() {
		return ordAmt;
	}

	/**
	* 设置字段ordAmt的值，该字段的<br>
	* 字段名称 :订单金额<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setordAmt(String ordAmt) {
		this.ordAmt = ordAmt;
    }

	/**
	* 获取字段ordID的值，该字段的<br>
	* 字段名称 :订单号<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getordID() {
		return ordID;
	}

	/**
	* 设置字段ordID的值，该字段的<br>
	* 字段名称 :订单号<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setordID(String ordID) {
		this.ordID = ordID;
    }

	/**
	* 获取字段payStatus的值，该字段的<br>
	* 字段名称 :订单状态<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getpayStatus() {
		return payStatus;
	}

	/**
	* 设置字段payStatus的值，该字段的<br>
	* 字段名称 :订单状态<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpayStatus(String payStatus) {
		this.payStatus = payStatus;
    }

	/**
	* 获取字段payType的值，该字段的<br>
	* 字段名称 :支付方式<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getpayType() {
		return payType;
	}

	/**
	* 设置字段payType的值，该字段的<br>
	* 字段名称 :支付方式<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpayType(String payType) {
		this.payType = payType;
    }

	/**
	* 获取字段returnSign的值，该字段的<br>
	* 字段名称 :支付返回签名<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getreturnSign() {
		return returnSign;
	}

	/**
	* 设置字段returnSign的值，该字段的<br>
	* 字段名称 :支付返回签名<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setreturnSign(String returnSign) {
		this.returnSign = returnSign;
    }

	/**
	* 获取字段sendSign的值，该字段的<br>
	* 字段名称 :支付发送签名<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getsendSign() {
		return sendSign;
	}

	/**
	* 设置字段sendSign的值，该字段的<br>
	* 字段名称 :支付发送签名<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setsendSign(String sendSign) {
		this.sendSign = sendSign;
    }

	/**
	* 获取字段tradeBank的值，该字段的<br>
	* 字段名称 :支付付款银行<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String gettradeBank() {
		return tradeBank;
	}

	/**
	* 设置字段tradeBank的值，该字段的<br>
	* 字段名称 :支付付款银行<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void settradeBank(String tradeBank) {
		this.tradeBank = tradeBank;
    }

	/**
	* 获取字段tradeDate的值，该字段的<br>
	* 字段名称 :tradeDate<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String gettradeDate() {
		return tradeDate;
	}

	/**
	* 设置字段tradeDate的值，该字段的<br>
	* 字段名称 :tradeDate<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void settradeDate(String tradeDate) {
		this.tradeDate = tradeDate;
    }

	/**
	* 获取字段tradeResult的值，该字段的<br>
	* 字段名称 :交易结果<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String gettradeResult() {
		return tradeResult;
	}

	/**
	* 设置字段tradeResult的值，该字段的<br>
	* 字段名称 :交易结果<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void settradeResult(String tradeResult) {
		this.tradeResult = tradeResult;
    }

	/**
	* 获取字段tradeSeriNO的值，该字段的<br>
	* 字段名称 :支付平台返回订单号针对支付<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String gettradeSeriNO() {
		return tradeSeriNO;
	}

	/**
	* 设置字段tradeSeriNO的值，该字段的<br>
	* 字段名称 :支付平台返回订单号针对支付<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void settradeSeriNO(String tradeSeriNO) {
		this.tradeSeriNO = tradeSeriNO;
    }

	/**
	* 获取字段tradeType的值，该字段的<br>
	* 字段名称 :tradeType<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String gettradeType() {
		return tradeType;
	}

	/**
	* 设置字段tradeType的值，该字段的<br>
	* 字段名称 :tradeType<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void settradeType(String tradeType) {
		this.tradeType = tradeType;
    }

	/**
	* 获取字段errorMsg2的值，该字段的<br>
	* 字段名称 :退款错误信息<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String geterrorMsg2() {
		return errorMsg2;
	}

	/**
	* 设置字段errorMsg2的值，该字段的<br>
	* 字段名称 :退款错误信息<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void seterrorMsg2(String errorMsg2) {
		this.errorMsg2 = errorMsg2;
    }

	/**
	* 获取字段receiveDate的值，该字段的<br>
	* 字段名称 :支付接受时间<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getreceiveDate() {
		return receiveDate;
	}

	/**
	* 设置字段receiveDate的值，该字段的<br>
	* 字段名称 :支付接受时间<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setreceiveDate(String receiveDate) {
		this.receiveDate = receiveDate;
    }

	/**
	* 获取字段receiveDate2的值，该字段的<br>
	* 字段名称 :退款接受时间<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getreceiveDate2() {
		return receiveDate2;
	}

	/**
	* 设置字段receiveDate2的值，该字段的<br>
	* 字段名称 :退款接受时间<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setreceiveDate2(String receiveDate2) {
		this.receiveDate2 = receiveDate2;
    }

	/**
	* 获取字段refundId的值，该字段的<br>
	* 字段名称 :退款单号<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrefundId() {
		return refundId;
	}

	/**
	* 设置字段refundId的值，该字段的<br>
	* 字段名称 :退款单号<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrefundId(String refundId) {
		this.refundId = refundId;
    }

	/**
	* 获取字段returnSign2的值，该字段的<br>
	* 字段名称 :退款返回签名<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getreturnSign2() {
		return returnSign2;
	}

	/**
	* 设置字段returnSign2的值，该字段的<br>
	* 字段名称 :退款返回签名<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setreturnSign2(String returnSign2) {
		this.returnSign2 = returnSign2;
    }

	/**
	* 获取字段sendDate的值，该字段的<br>
	* 字段名称 :支付发送时间<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getsendDate() {
		return sendDate;
	}

	/**
	* 设置字段sendDate的值，该字段的<br>
	* 字段名称 :支付发送时间<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setsendDate(String sendDate) {
		this.sendDate = sendDate;
    }

	/**
	* 获取字段sendDate2的值，该字段的<br>
	* 字段名称 :退款发送时间<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getsendDate2() {
		return sendDate2;
	}

	/**
	* 设置字段sendDate2的值，该字段的<br>
	* 字段名称 :退款发送时间<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setsendDate2(String sendDate2) {
		this.sendDate2 = sendDate2;
    }

	/**
	* 获取字段sendSign2的值，该字段的<br>
	* 字段名称 :退款发送签名<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getsendSign2() {
		return sendSign2;
	}

	/**
	* 设置字段sendSign2的值，该字段的<br>
	* 字段名称 :退款发送签名<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setsendSign2(String sendSign2) {
		this.sendSign2 = sendSign2;
    }

	/**
	* 获取字段receiveRefundId的值，该字段的<br>
	* 字段名称 :支付平台返回退款单号<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getreceiveRefundId() {
		return receiveRefundId;
	}

	/**
	* 设置字段receiveRefundId的值，该字段的<br>
	* 字段名称 :支付平台返回退款单号<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setreceiveRefundId(String receiveRefundId) {
		this.receiveRefundId = receiveRefundId;
    }

	/**
	* 获取字段tradeCheckSeriNo的值，该字段的<br>
	* 字段名称 :淘宝支付宝对账用单号(TB)<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String gettradeCheckSeriNo() {
		return tradeCheckSeriNo;
	}

	/**
	* 设置字段tradeCheckSeriNo的值，该字段的<br>
	* 字段名称 :淘宝支付宝对账用单号(TB)<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void settradeCheckSeriNo(String tradeCheckSeriNo) {
		this.tradeCheckSeriNo = tradeCheckSeriNo;
    }

	/**
	* 获取字段weixicookie的值，该字段的<br>
	* 字段名称 :维析cookie<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getweixicookie() {
		return weixicookie;
	}

	/**
	* 设置字段weixicookie的值，该字段的<br>
	* 字段名称 :维析cookie<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setweixicookie(String weixicookie) {
		this.weixicookie = weixicookie;
    }

	
	public String getBillOrderNo() {
	
		return billOrderNo;
	}

	
	public void setBillOrderNo(String billOrderNo) {
	
		this.billOrderNo = billOrderNo;
	}

	
	public String getKqPaySn() {
	
		return kqPaySn;
	}

	
	public void setKqPaySn(String kqPaySn) {
	
		this.kqPaySn = kqPaySn;
	}
	
	

}