package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：交易汇总信息表<br>
 * 表代码：TradeSummaryInfo<br>
 * 表主键：id<br>
 */
public class TradeSummaryInfoSchema extends Schema {
	private String id;

	private String PaySn;

	private String TradeSn;

	private String TradeResult;

	private String OrderSns;

	private String CouponSumAmount;

	private String ActivitySumAmount;

	private String PointSumAmount;

	private String CouponSn;

	private String PayType;

	private String PayTypeName;

	private String TotalAmount;

	private String TradeAmount;

	private Date CreateDate;

	private Date ModifyDate;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("PaySn", DataColumn.STRING, 1, 32 , 0 , false , false),
		new SchemaColumn("TradeSn", DataColumn.STRING, 2, 100 , 0 , false , false),
		new SchemaColumn("TradeResult", DataColumn.STRING, 3, 32 , 0 , false , false),
		new SchemaColumn("OrderSns", DataColumn.CLOB, 4, 0 , 0 , false , false),
		new SchemaColumn("CouponSumAmount", DataColumn.STRING, 5, 32 , 0 , false , false),
		new SchemaColumn("ActivitySumAmount", DataColumn.STRING, 6, 32 , 0 , false , false),
		new SchemaColumn("PointSumAmount", DataColumn.STRING, 7, 32 , 0 , false , false),
		new SchemaColumn("CouponSn", DataColumn.STRING, 8, 50 , 0 , false , false),
		new SchemaColumn("PayType", DataColumn.STRING, 9, 50 , 0 , false , false),
		new SchemaColumn("PayTypeName", DataColumn.STRING, 10, 50 , 0 , false , false),
		new SchemaColumn("TotalAmount", DataColumn.STRING, 11, 32 , 0 , false , false),
		new SchemaColumn("TradeAmount", DataColumn.STRING, 12, 32 , 0 , false , false),
		new SchemaColumn("CreateDate", DataColumn.DATETIME, 13, 0 , 0 , false , false),
		new SchemaColumn("ModifyDate", DataColumn.DATETIME, 14, 0 , 0 , false , false)
	};

	public static final String _TableCode = "TradeSummaryInfo";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into TradeSummaryInfo values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update TradeSummaryInfo set id=?,PaySn=?,TradeSn=?,TradeResult=?,OrderSns=?,CouponSumAmount=?,ActivitySumAmount=?,PointSumAmount=?,CouponSn=?,PayType=?,PayTypeName=?,TotalAmount=?,TradeAmount=?,CreateDate=?,ModifyDate=? where id=?";

	protected static final String _DeleteSQL = "delete from TradeSummaryInfo  where id=?";

	protected static final String _FillAllSQL = "select * from TradeSummaryInfo  where id=?";

	public TradeSummaryInfoSchema(){
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
		return new TradeSummaryInfoSchema();
	}

	protected SchemaSet newSet(){
		return new TradeSummaryInfoSet();
	}

	public TradeSummaryInfoSet query() {
		return query(null, -1, -1);
	}

	public TradeSummaryInfoSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public TradeSummaryInfoSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public TradeSummaryInfoSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (TradeSummaryInfoSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){PaySn = (String)v;return;}
		if (i == 2){TradeSn = (String)v;return;}
		if (i == 3){TradeResult = (String)v;return;}
		if (i == 4){OrderSns = (String)v;return;}
		if (i == 5){CouponSumAmount = (String)v;return;}
		if (i == 6){ActivitySumAmount = (String)v;return;}
		if (i == 7){PointSumAmount = (String)v;return;}
		if (i == 8){CouponSn = (String)v;return;}
		if (i == 9){PayType = (String)v;return;}
		if (i == 10){PayTypeName = (String)v;return;}
		if (i == 11){TotalAmount = (String)v;return;}
		if (i == 12){TradeAmount = (String)v;return;}
		if (i == 13){CreateDate = (Date)v;return;}
		if (i == 14){ModifyDate = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return PaySn;}
		if (i == 2){return TradeSn;}
		if (i == 3){return TradeResult;}
		if (i == 4){return OrderSns;}
		if (i == 5){return CouponSumAmount;}
		if (i == 6){return ActivitySumAmount;}
		if (i == 7){return PointSumAmount;}
		if (i == 8){return CouponSn;}
		if (i == 9){return PayType;}
		if (i == 10){return PayTypeName;}
		if (i == 11){return TotalAmount;}
		if (i == 12){return TradeAmount;}
		if (i == 13){return CreateDate;}
		if (i == 14){return ModifyDate;}
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
	* 获取字段PaySn的值，该字段的<br>
	* 字段名称 :交易号<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPaySn() {
		return PaySn;
	}

	/**
	* 设置字段PaySn的值，该字段的<br>
	* 字段名称 :交易号<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPaySn(String paySn) {
		this.PaySn = paySn;
    }

	/**
	* 获取字段TradeSn的值，该字段的<br>
	* 字段名称 :平台交易流水号<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getTradeSn() {
		return TradeSn;
	}

	/**
	* 设置字段TradeSn的值，该字段的<br>
	* 字段名称 :平台交易流水号<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setTradeSn(String tradeSn) {
		this.TradeSn = tradeSn;
    }

	/**
	* 获取字段TradeResult的值，该字段的<br>
	* 字段名称 :交易结果<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getTradeResult() {
		return TradeResult;
	}

	/**
	* 设置字段TradeResult的值，该字段的<br>
	* 字段名称 :交易结果<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setTradeResult(String tradeResult) {
		this.TradeResult = tradeResult;
    }

	/**
	* 获取字段OrderSns的值，该字段的<br>
	* 字段名称 :订单号数组<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getOrderSns() {
		return OrderSns;
	}

	/**
	* 设置字段OrderSns的值，该字段的<br>
	* 字段名称 :订单号数组<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setOrderSns(String orderSns) {
		this.OrderSns = orderSns;
    }

	/**
	* 获取字段CouponSumAmount的值，该字段的<br>
	* 字段名称 :优惠劵优惠总金额<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	01：满减 02：满送 03：买送 04：高倍积分 05：活动打折 06：优惠劵 07：积分抵值<br>
	*/
	public String getCouponSumAmount() {
		return CouponSumAmount;
	}

	/**
	* 设置字段CouponSumAmount的值，该字段的<br>
	* 字段名称 :优惠劵优惠总金额<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	01：满减 02：满送 03：买送 04：高倍积分 05：活动打折 06：优惠劵 07：积分抵值<br>
	*/
	public void setCouponSumAmount(String couponSumAmount) {
		this.CouponSumAmount = couponSumAmount;
    }

	/**
	* 获取字段ActivitySumAmount的值，该字段的<br>
	* 字段名称 :活动优惠总金额<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	01：满减 02：满送 03：买送 04：高倍积分 05：活动打折 06：优惠劵 07：积分抵值<br>
	*/
	public String getActivitySumAmount() {
		return ActivitySumAmount;
	}

	/**
	* 设置字段ActivitySumAmount的值，该字段的<br>
	* 字段名称 :活动优惠总金额<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	01：满减 02：满送 03：买送 04：高倍积分 05：活动打折 06：优惠劵 07：积分抵值<br>
	*/
	public void setActivitySumAmount(String activitySumAmount) {
		this.ActivitySumAmount = activitySumAmount;
    }

	/**
	* 获取字段PointSumAmount的值，该字段的<br>
	* 字段名称 :积分优惠总金额<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	01：满减 02：满送 03：买送 04：高倍积分 05：活动打折 06：优惠劵 07：积分抵值<br>
	*/
	public String getPointSumAmount() {
		return PointSumAmount;
	}

	/**
	* 设置字段PointSumAmount的值，该字段的<br>
	* 字段名称 :积分优惠总金额<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	01：满减 02：满送 03：买送 04：高倍积分 05：活动打折 06：优惠劵 07：积分抵值<br>
	*/
	public void setPointSumAmount(String pointSumAmount) {
		this.PointSumAmount = pointSumAmount;
    }

	/**
	* 获取字段CouponSn的值，该字段的<br>
	* 字段名称 :优惠券号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCouponSn() {
		return CouponSn;
	}

	/**
	* 设置字段CouponSn的值，该字段的<br>
	* 字段名称 :优惠券号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCouponSn(String couponSn) {
		this.CouponSn = couponSn;
    }

	/**
	* 获取字段PayType的值，该字段的<br>
	* 字段名称 :支付类型<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPayType() {
		return PayType;
	}

	/**
	* 设置字段PayType的值，该字段的<br>
	* 字段名称 :支付类型<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPayType(String payType) {
		this.PayType = payType;
    }

	/**
	* 获取字段PayTypeName的值，该字段的<br>
	* 字段名称 :支付类型名称<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPayTypeName() {
		return PayTypeName;
	}

	/**
	* 设置字段PayTypeName的值，该字段的<br>
	* 字段名称 :支付类型名称<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPayTypeName(String payTypeName) {
		this.PayTypeName = payTypeName;
    }

	/**
	* 获取字段TotalAmount的值，该字段的<br>
	* 字段名称 :订单总金额<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getTotalAmount() {
		return TotalAmount;
	}

	/**
	* 设置字段TotalAmount的值，该字段的<br>
	* 字段名称 :订单总金额<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setTotalAmount(String totalAmount) {
		this.TotalAmount = totalAmount;
    }

	/**
	* 获取字段TradeAmount的值，该字段的<br>
	* 字段名称 :支付总金额<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	第三方平台推荐<br>
	*/
	public String getTradeAmount() {
		return TradeAmount;
	}

	/**
	* 设置字段TradeAmount的值，该字段的<br>
	* 字段名称 :支付总金额<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	第三方平台推荐<br>
	*/
	public void setTradeAmount(String tradeAmount) {
		this.TradeAmount = tradeAmount;
    }

	/**
	* 获取字段CreateDate的值，该字段的<br>
	* 字段名称 :创建日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getCreateDate() {
		return CreateDate;
	}

	/**
	* 设置字段CreateDate的值，该字段的<br>
	* 字段名称 :创建日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCreateDate(Date createDate) {
		this.CreateDate = createDate;
    }

	/**
	* 获取字段ModifyDate的值，该字段的<br>
	* 字段名称 :修改日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getModifyDate() {
		return ModifyDate;
	}

	/**
	* 设置字段ModifyDate的值，该字段的<br>
	* 字段名称 :修改日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyDate(Date modifyDate) {
		this.ModifyDate = modifyDate;
    }

}