package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：合作方退保资金划拨结果记录表<br>
 * 表代码：PartnerRefundFundsRecord<br>
 * 表主键：POrderSn<br>
 */
public class PartnerRefundFundsRecordSchema extends Schema {
	private String POrderSn;

	private String RefundAplaySn;

	private String ResultRemarkSn;

	private String AplaySn;

	private String Riskcode;

	private String Total;

	private String Principal;

	private String Income;

	private String Fee;

	private String TradeDate;

	private String DealResult;

	private String DealResultDesc;

	private String Channelsn;

	private Date CreateDate;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("POrderSn", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("RefundAplaySn", DataColumn.STRING, 1, 32 , 0 , false , false),
		new SchemaColumn("ResultRemarkSn", DataColumn.STRING, 2, 32 , 0 , false , false),
		new SchemaColumn("AplaySn", DataColumn.STRING, 3, 32 , 0 , false , false),
		new SchemaColumn("Riskcode", DataColumn.STRING, 4, 32 , 0 , false , false),
		new SchemaColumn("Total", DataColumn.STRING, 5, 12 , 0 , false , false),
		new SchemaColumn("Principal", DataColumn.STRING, 6, 12 , 0 , false , false),
		new SchemaColumn("Income", DataColumn.STRING, 7, 10 , 0 , false , false),
		new SchemaColumn("Fee", DataColumn.STRING, 8, 10 , 0 , false , false),
		new SchemaColumn("TradeDate", DataColumn.STRING, 9, 20 , 0 , false , false),
		new SchemaColumn("DealResult", DataColumn.STRING, 10, 2 , 0 , false , false),
		new SchemaColumn("DealResultDesc", DataColumn.STRING, 11, 300 , 0 , false , false),
		new SchemaColumn("Channelsn", DataColumn.STRING, 12, 25 , 0 , false , false),
		new SchemaColumn("CreateDate", DataColumn.DATETIME, 13, 0 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 14, 100 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 15, 100 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 16, 100 , 0 , false , false)
	};

	public static final String _TableCode = "PartnerRefundFundsRecord";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into PartnerRefundFundsRecord values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update PartnerRefundFundsRecord set POrderSn=?,RefundAplaySn=?,ResultRemarkSn=?,AplaySn=?,Riskcode=?,Total=?,Principal=?,Income=?,Fee=?,TradeDate=?,DealResult=?,DealResultDesc=?,Channelsn=?,CreateDate=?,Prop1=?,Prop2=?,Prop3=? where POrderSn=?";

	protected static final String _DeleteSQL = "delete from PartnerRefundFundsRecord  where POrderSn=?";

	protected static final String _FillAllSQL = "select * from PartnerRefundFundsRecord  where POrderSn=?";

	public PartnerRefundFundsRecordSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[17];
	}

	protected Schema newInstance(){
		return new PartnerRefundFundsRecordSchema();
	}

	protected SchemaSet newSet(){
		return new PartnerRefundFundsRecordSet();
	}

	public PartnerRefundFundsRecordSet query() {
		return query(null, -1, -1);
	}

	public PartnerRefundFundsRecordSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public PartnerRefundFundsRecordSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public PartnerRefundFundsRecordSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (PartnerRefundFundsRecordSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){POrderSn = (String)v;return;}
		if (i == 1){RefundAplaySn = (String)v;return;}
		if (i == 2){ResultRemarkSn = (String)v;return;}
		if (i == 3){AplaySn = (String)v;return;}
		if (i == 4){Riskcode = (String)v;return;}
		if (i == 5){Total = (String)v;return;}
		if (i == 6){Principal = (String)v;return;}
		if (i == 7){Income = (String)v;return;}
		if (i == 8){Fee = (String)v;return;}
		if (i == 9){TradeDate = (String)v;return;}
		if (i == 10){DealResult = (String)v;return;}
		if (i == 11){DealResultDesc = (String)v;return;}
		if (i == 12){Channelsn = (String)v;return;}
		if (i == 13){CreateDate = (Date)v;return;}
		if (i == 14){Prop1 = (String)v;return;}
		if (i == 15){Prop2 = (String)v;return;}
		if (i == 16){Prop3 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return POrderSn;}
		if (i == 1){return RefundAplaySn;}
		if (i == 2){return ResultRemarkSn;}
		if (i == 3){return AplaySn;}
		if (i == 4){return Riskcode;}
		if (i == 5){return Total;}
		if (i == 6){return Principal;}
		if (i == 7){return Income;}
		if (i == 8){return Fee;}
		if (i == 9){return TradeDate;}
		if (i == 10){return DealResult;}
		if (i == 11){return DealResultDesc;}
		if (i == 12){return Channelsn;}
		if (i == 13){return CreateDate;}
		if (i == 14){return Prop1;}
		if (i == 15){return Prop2;}
		if (i == 16){return Prop3;}
		return null;
	}

	/**
	* 获取字段POrderSn的值，该字段的<br>
	* 字段名称 :投保订单号<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getPOrderSn() {
		return POrderSn;
	}

	/**
	* 设置字段POrderSn的值，该字段的<br>
	* 字段名称 :投保订单号<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setPOrderSn(String pOrderSn) {
		this.POrderSn = pOrderSn;
    }

	/**
	* 获取字段RefundAplaySn的值，该字段的<br>
	* 字段名称 :退保申请流水<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getRefundAplaySn() {
		return RefundAplaySn;
	}

	/**
	* 设置字段RefundAplaySn的值，该字段的<br>
	* 字段名称 :退保申请流水<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRefundAplaySn(String refundAplaySn) {
		this.RefundAplaySn = refundAplaySn;
    }

	/**
	* 获取字段ResultRemarkSn的值，该字段的<br>
	* 字段名称 :退保结果保全号<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getResultRemarkSn() {
		return ResultRemarkSn;
	}

	/**
	* 设置字段ResultRemarkSn的值，该字段的<br>
	* 字段名称 :退保结果保全号<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setResultRemarkSn(String resultRemarkSn) {
		this.ResultRemarkSn = resultRemarkSn;
    }

	/**
	* 获取字段AplaySn的值，该字段的<br>
	* 字段名称 :资金划拨请求流水号<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAplaySn() {
		return AplaySn;
	}

	/**
	* 设置字段AplaySn的值，该字段的<br>
	* 字段名称 :资金划拨请求流水号<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAplaySn(String aplaySn) {
		this.AplaySn = aplaySn;
    }

	/**
	* 获取字段Riskcode的值，该字段的<br>
	* 字段名称 :机构产品代码<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getRiskcode() {
		return Riskcode;
	}

	/**
	* 设置字段Riskcode的值，该字段的<br>
	* 字段名称 :机构产品代码<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRiskcode(String riskcode) {
		this.Riskcode = riskcode;
    }

	/**
	* 获取字段Total的值，该字段的<br>
	* 字段名称 :还款总金额<br>
	* 数据类型 :varchar(12)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getTotal() {
		return Total;
	}

	/**
	* 设置字段Total的值，该字段的<br>
	* 字段名称 :还款总金额<br>
	* 数据类型 :varchar(12)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setTotal(String total) {
		this.Total = total;
    }

	/**
	* 获取字段Principal的值，该字段的<br>
	* 字段名称 :还款本金<br>
	* 数据类型 :varchar(12)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPrincipal() {
		return Principal;
	}

	/**
	* 设置字段Principal的值，该字段的<br>
	* 字段名称 :还款本金<br>
	* 数据类型 :varchar(12)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPrincipal(String principal) {
		this.Principal = principal;
    }

	/**
	* 获取字段Income的值，该字段的<br>
	* 字段名称 :还款利息<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getIncome() {
		return Income;
	}

	/**
	* 设置字段Income的值，该字段的<br>
	* 字段名称 :还款利息<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setIncome(String income) {
		this.Income = income;
    }

	/**
	* 获取字段Fee的值，该字段的<br>
	* 字段名称 :还款手续费<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getFee() {
		return Fee;
	}

	/**
	* 设置字段Fee的值，该字段的<br>
	* 字段名称 :还款手续费<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setFee(String fee) {
		this.Fee = fee;
    }

	/**
	* 获取字段TradeDate的值，该字段的<br>
	* 字段名称 :交易确认时间<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getTradeDate() {
		return TradeDate;
	}

	/**
	* 设置字段TradeDate的值，该字段的<br>
	* 字段名称 :交易确认时间<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setTradeDate(String tradeDate) {
		this.TradeDate = tradeDate;
    }

	/**
	* 获取字段DealResult的值，该字段的<br>
	* 字段名称 :处理结果<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getDealResult() {
		return DealResult;
	}

	/**
	* 设置字段DealResult的值，该字段的<br>
	* 字段名称 :处理结果<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDealResult(String dealResult) {
		this.DealResult = dealResult;
    }

	/**
	* 获取字段DealResultDesc的值，该字段的<br>
	* 字段名称 :处理结果说明<br>
	* 数据类型 :varchar(300)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getDealResultDesc() {
		return DealResultDesc;
	}

	/**
	* 设置字段DealResultDesc的值，该字段的<br>
	* 字段名称 :处理结果说明<br>
	* 数据类型 :varchar(300)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDealResultDesc(String dealResultDesc) {
		this.DealResultDesc = dealResultDesc;
    }

	/**
	* 获取字段Channelsn的值，该字段的<br>
	* 字段名称 :合作方渠道<br>
	* 数据类型 :varchar(25)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getChannelsn() {
		return Channelsn;
	}

	/**
	* 设置字段Channelsn的值，该字段的<br>
	* 字段名称 :合作方渠道<br>
	* 数据类型 :varchar(25)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setChannelsn(String channelsn) {
		this.Channelsn = channelsn;
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