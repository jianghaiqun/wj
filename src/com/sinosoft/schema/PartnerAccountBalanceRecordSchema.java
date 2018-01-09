package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

import java.util.Date;

/**
 * 表名称：合作方承保资金的对账记录表<br>
 * 表代码：PartnerAccountBalanceRecord<br>
 * 表主键：id<br>
 */
public class PartnerAccountBalanceRecordSchema extends Schema {
	private String id;

	private String Custermor;

	private String BillingTime;

	private String FundsSn;

	private String FundTransferSn;

	private String TradeTime;

	private String TradeType;

	private String Scene;

	private String PayAmount;

	private String IncomeAmount;

	private String AccountBalance;

	private String AccountType;

	private String MerchantSn;

	private String ChannelSn;

	private Date CreateDate;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("Custermor", DataColumn.STRING, 1, 30 , 0 , false , false),
		new SchemaColumn("BillingTime", DataColumn.STRING, 2, 14 , 0 , false , false),
		new SchemaColumn("FundsSn", DataColumn.STRING, 3, 32 , 0 , false , false),
		new SchemaColumn("POrderSn", DataColumn.STRING, 4, 32 , 0 , false , false),
		new SchemaColumn("TradeTime", DataColumn.STRING, 5, 14 , 0 , false , false),
		new SchemaColumn("TradeType", DataColumn.STRING, 6, 4 , 0 , false , false),
		new SchemaColumn("Scene", DataColumn.STRING, 7, 20 , 0 , false , false),
		new SchemaColumn("PayAmount", DataColumn.STRING, 8, 12 , 0 , false , false),
		new SchemaColumn("IncomeAmount", DataColumn.STRING, 9, 12 , 0 , false , false),
		new SchemaColumn("AccountBalance", DataColumn.STRING, 10, 20 , 0 , false , false),
		new SchemaColumn("AccountType", DataColumn.STRING, 11, 4 , 0 , false , false),
		new SchemaColumn("MerchantSn", DataColumn.STRING, 12, 32 , 0 , false , false),
		new SchemaColumn("ChannelSn", DataColumn.STRING, 13, 20 , 0 , false , false),
		new SchemaColumn("CreateDate", DataColumn.DATETIME, 14, 0 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 15, 100 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 16, 100 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 17, 100 , 0 , false , false)
	};

	public static final String _TableCode = "PartnerAccountBalanceRecord";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into PartnerAccountBalanceRecord values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update PartnerAccountBalanceRecord set id=?,Custermor=?,BillingTime=?,FundsSn=?,POrderSn=?,TradeTime=?,TradeType=?,Scene=?,PayAmount=?,IncomeAmount=?,AccountBalance=?,AccountType=?,MerchantSn=?,ChannelSn=?,CreateDate=?,Prop1=?,Prop2=?,Prop3=? where id=?";

	protected static final String _DeleteSQL = "delete from PartnerAccountBalanceRecord  where id=?";

	protected static final String _FillAllSQL = "select * from PartnerAccountBalanceRecord  where id=?";

	public PartnerAccountBalanceRecordSchema(){
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
		return new PartnerAccountBalanceRecordSchema();
	}

	protected SchemaSet newSet(){
		return new PartnerAccountBalanceRecordSet();
	}

	public PartnerAccountBalanceRecordSet query() {
		return query(null, -1, -1);
	}

	public PartnerAccountBalanceRecordSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public PartnerAccountBalanceRecordSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public PartnerAccountBalanceRecordSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (PartnerAccountBalanceRecordSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){Custermor = (String)v;return;}
		if (i == 2){BillingTime = (String)v;return;}
		if (i == 3){FundsSn = (String)v;return;}
		if (i == 4){FundTransferSn = (String)v;return;}
		if (i == 5){TradeTime = (String)v;return;}
		if (i == 6){TradeType = (String)v;return;}
		if (i == 7){Scene = (String)v;return;}
		if (i == 8){PayAmount = (String)v;return;}
		if (i == 9){IncomeAmount = (String)v;return;}
		if (i == 10){AccountBalance = (String)v;return;}
		if (i == 11){AccountType = (String)v;return;}
		if (i == 12){MerchantSn = (String)v;return;}
		if (i == 13){ChannelSn = (String)v;return;}
		if (i == 14){CreateDate = (Date)v;return;}
		if (i == 15){Prop1 = (String)v;return;}
		if (i == 16){Prop2 = (String)v;return;}
		if (i == 17){Prop3 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return Custermor;}
		if (i == 2){return BillingTime;}
		if (i == 3){return FundsSn;}
		if (i == 4){return FundTransferSn;}
		if (i == 5){return TradeTime;}
		if (i == 6){return TradeType;}
		if (i == 7){return Scene;}
		if (i == 8){return PayAmount;}
		if (i == 9){return IncomeAmount;}
		if (i == 10){return AccountBalance;}
		if (i == 11){return AccountType;}
		if (i == 12){return MerchantSn;}
		if (i == 13){return ChannelSn;}
		if (i == 14){return CreateDate;}
		if (i == 15){return Prop1;}
		if (i == 16){return Prop2;}
		if (i == 17){return Prop3;}
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
	* 获取字段Custermor的值，该字段的<br>
	* 字段名称 :客户姓名<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCustermor() {
		return Custermor;
	}

	/**
	* 设置字段Custermor的值，该字段的<br>
	* 字段名称 :客户姓名<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCustermor(String custermor) {
		this.Custermor = custermor;
    }

	/**
	* 获取字段BillingTime的值，该字段的<br>
	* 字段名称 :记账时间<br>
	* 数据类型 :varchar(14)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getBillingTime() {
		return BillingTime;
	}

	/**
	* 设置字段BillingTime的值，该字段的<br>
	* 字段名称 :记账时间<br>
	* 数据类型 :varchar(14)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setBillingTime(String billingTime) {
		this.BillingTime = billingTime;
    }

	/**
	* 获取字段FundsSn的值，该字段的<br>
	* 字段名称 :资金流水号<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getFundsSn() {
		return FundsSn;
	}

	/**
	* 设置字段FundsSn的值，该字段的<br>
	* 字段名称 :资金流水号<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setFundsSn(String fundsSn) {
		this.FundsSn = fundsSn;
    }

	/**
	* 获取字段POrderSn的值，该字段的<br>
	* 字段名称 :商户订单号<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getFundTransferSn() {
		return FundTransferSn;
	}

	/**
	* 设置字段POrderSn的值，该字段的<br>
	* 字段名称 :商户订单号<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setFundTransferSn(String fundTransferSn) {
		this.FundTransferSn = fundTransferSn;
    }

	/**
	* 获取字段TradeTime的值，该字段的<br>
	* 字段名称 :交易完成时间<br>
	* 数据类型 :varchar(14)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getTradeTime() {
		return TradeTime;
	}

	/**
	* 设置字段TradeTime的值，该字段的<br>
	* 字段名称 :交易完成时间<br>
	* 数据类型 :varchar(14)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setTradeTime(String tradeTime) {
		this.TradeTime = tradeTime;
    }

	/**
	* 获取字段TradeType的值，该字段的<br>
	* 字段名称 :交易类型<br>
	* 数据类型 :varchar(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getTradeType() {
		return TradeType;
	}

	/**
	* 设置字段TradeType的值，该字段的<br>
	* 字段名称 :交易类型<br>
	* 数据类型 :varchar(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setTradeType(String tradeType) {
		this.TradeType = tradeType;
    }

	/**
	* 获取字段Scene的值，该字段的<br>
	* 字段名称 :场景<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getScene() {
		return Scene;
	}

	/**
	* 设置字段Scene的值，该字段的<br>
	* 字段名称 :场景<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setScene(String scene) {
		this.Scene = scene;
    }

	/**
	* 获取字段PayAmount的值，该字段的<br>
	* 字段名称 :支出<br>
	* 数据类型 :varchar(12)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPayAmount() {
		return PayAmount;
	}

	/**
	* 设置字段PayAmount的值，该字段的<br>
	* 字段名称 :支出<br>
	* 数据类型 :varchar(12)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPayAmount(String payAmount) {
		this.PayAmount = payAmount;
    }

	/**
	* 获取字段IncomeAmount的值，该字段的<br>
	* 字段名称 :收入<br>
	* 数据类型 :varchar(12)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getIncomeAmount() {
		return IncomeAmount;
	}

	/**
	* 设置字段IncomeAmount的值，该字段的<br>
	* 字段名称 :收入<br>
	* 数据类型 :varchar(12)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setIncomeAmount(String incomeAmount) {
		this.IncomeAmount = incomeAmount;
    }

	/**
	* 获取字段AccountBalance的值，该字段的<br>
	* 字段名称 :账户余额<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAccountBalance() {
		return AccountBalance;
	}

	/**
	* 设置字段AccountBalance的值，该字段的<br>
	* 字段名称 :账户余额<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAccountBalance(String accountBalance) {
		this.AccountBalance = accountBalance;
    }

	/**
	* 获取字段AccountType的值，该字段的<br>
	* 字段名称 :账户类型<br>
	* 数据类型 :varchar(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAccountType() {
		return AccountType;
	}

	/**
	* 设置字段AccountType的值，该字段的<br>
	* 字段名称 :账户类型<br>
	* 数据类型 :varchar(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAccountType(String accountType) {
		this.AccountType = accountType;
    }

	/**
	* 获取字段MerchantSn的值，该字段的<br>
	* 字段名称 :商户号<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMerchantSn() {
		return MerchantSn;
	}

	/**
	* 设置字段MerchantSn的值，该字段的<br>
	* 字段名称 :商户号<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMerchantSn(String merchantSn) {
		this.MerchantSn = merchantSn;
    }

	/**
	* 获取字段ChannelSn的值，该字段的<br>
	* 字段名称 :渠道<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getChannelSn() {
		return ChannelSn;
	}

	/**
	* 设置字段ChannelSn的值，该字段的<br>
	* 字段名称 :渠道<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setChannelSn(String channelSn) {
		this.ChannelSn = channelSn;
    }

	/**
	* 获取字段CreateDate的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getCreateDate() {
		return CreateDate;
	}

	/**
	* 设置字段CreateDate的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCreateDate(Date createDate) {
		this.CreateDate = createDate;
    }

	/**
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :Prop1<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :Prop1<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* 获取字段Prop2的值，该字段的<br>
	* 字段名称 :Prop2<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* 设置字段Prop2的值，该字段的<br>
	* 字段名称 :Prop2<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

	/**
	* 获取字段Prop3的值，该字段的<br>
	* 字段名称 :Prop3<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp3() {
		return Prop3;
	}

	/**
	* 设置字段Prop3的值，该字段的<br>
	* 字段名称 :Prop3<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp3(String prop3) {
		this.Prop3 = prop3;
    }

}