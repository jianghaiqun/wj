package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.orm.SchemaSet;

import java.util.Date;

/**
 * 表名称：优惠劵详细表<br>
 * 表代码：CouponInfo<br>
 * 表主键：Id<br>
 */
public class SDCouponInfoSchema extends Schema {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3449334532963058531L;

	private String Id;

	private String CouponSn;

	private String Batch;

	private String RiskCode;

	private String InsuranceCompany;

	private String ProvideUser;

	private String ParValue;

	private String PayAmount;

	private String UseTimes;

	private String Direction;

	private Date StartTime;

	private Date EndTime;

	private String ActivitySn;

	private String ChannelSn;

	private String Status;

	private String Purpose;

	private String OrderSn;

	private Date PayTime;

	private String MemberId;

	private String Mail;

	private String Mobile;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	private String Prop4;

	private String CreateUser;

	private Date CreateDate;

	private String ModifyUser;

	private Date ModifyDate;

	private String RealProvideUser;
	
	private String product;
	
	private String shortName;
	
	private String issuechannel;

	private String remindFlag;

	private String usedUrl;
	
	private String maxDeduction;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("Id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("CouponSn", DataColumn.STRING, 1, 20 , 0 , true , false),
		new SchemaColumn("Batch", DataColumn.STRING, 2, 20 , 0 , false , false),
		new SchemaColumn("RiskCode", DataColumn.STRING, 3, 255 , 0 , false , false),
		new SchemaColumn("InsuranceCompany", DataColumn.STRING, 4, 255 , 0 , false , false),
		new SchemaColumn("ProvideUser", DataColumn.STRING, 5, 255 , 0 , false , false),
		new SchemaColumn("ParValue", DataColumn.STRING, 6, 20 , 0 , true , false),
		new SchemaColumn("PayAmount", DataColumn.STRING, 7, 20 , 0 , false , false),
		new SchemaColumn("UseTimes", DataColumn.STRING, 8, 11 , 0 , false , false),
		new SchemaColumn("Direction", DataColumn.STRING, 9, 100 , 0 , true , false),
		new SchemaColumn("StartTime", DataColumn.DATETIME, 10, 0 , 0 , true , false),
		new SchemaColumn("EndTime", DataColumn.DATETIME, 11, 0 , 0 , true , false),
		new SchemaColumn("ActivitySn", DataColumn.STRING, 12, 10 , 0 , false , false),
		new SchemaColumn("ChannelSn", DataColumn.STRING, 13, 10 , 0 , false , false),
		new SchemaColumn("Status", DataColumn.STRING, 14, 10 , 0 , false , false),
		new SchemaColumn("Purpose", DataColumn.STRING, 15, 5 , 0 , false , false),
		new SchemaColumn("OrderSn", DataColumn.STRING, 16, 20 , 0 , false , false),
		new SchemaColumn("PayTime", DataColumn.DATETIME, 17, 0 , 0 , false , false),
		new SchemaColumn("MemberId", DataColumn.STRING, 18, 32 , 0 , false , false),
		new SchemaColumn("Mail", DataColumn.STRING, 19, 50 , 0 , false , false),
		new SchemaColumn("Mobile", DataColumn.STRING, 20, 12 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 21, 255 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 22, 255 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 23, 255 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 24, 255 , 0 , false , false),
		new SchemaColumn("CreateUser", DataColumn.STRING, 25, 50 , 0 , true , false),
		new SchemaColumn("CreateDate", DataColumn.DATETIME, 26, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 27, 50 , 0 , false , false),
		new SchemaColumn("ModifyDate", DataColumn.DATETIME, 28, 0 , 0 , false , false),
		new SchemaColumn("RealProvideUser", DataColumn.STRING, 29, 255 , 0 , false , false),
		new SchemaColumn("product", DataColumn.CLOB, 30, 0 , 0 , false , false),
		new SchemaColumn("shortName", DataColumn.STRING, 31, 50 , 0 , false , false),
		new SchemaColumn("issuechannel", DataColumn.STRING, 32, 50 , 0 , false , false),
		new SchemaColumn("remindFlag", DataColumn.STRING, 33, 2 , 0 , true , false),
		new SchemaColumn("usedUrl", DataColumn.STRING, 34, 255 , 0 , false , false),
		new SchemaColumn("maxDeduction", DataColumn.STRING, 35, 20 , 0 , false , false)
	};

	public static final String _TableCode = "CouponInfo";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into CouponInfo values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update CouponInfo set Id=?,CouponSn=?,Batch=?,RiskCode=?,InsuranceCompany=?,ProvideUser=?,ParValue=?,PayAmount=?,UseTimes=?,Direction=?,StartTime=?,EndTime=?,ActivitySn=?,ChannelSn=?,Status=?,Purpose=?,OrderSn=?,PayTime=?,MemberId=?,Mail=?,Mobile=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,CreateUser=?,CreateDate=?,ModifyUser=?,ModifyDate=?,RealProvideUser=?,product=?,shortName=?,issuechannel=?,remindFlag=?,usedUrl=?,maxDeduction=? where Id=?";

	protected static final String _DeleteSQL = "delete from CouponInfo  where Id=?";

	protected static final String _FillAllSQL = "select * from CouponInfo  where Id=?";

	public SDCouponInfoSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[36];
	}

	protected Schema newInstance(){
		return new SDCouponInfoSchema();
	}

	protected SchemaSet newSet(){
		return new SDCouponInfoSet();
	}

	public SDCouponInfoSet query() {
		return query(null, -1, -1);
	}

	public SDCouponInfoSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public SDCouponInfoSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public SDCouponInfoSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (SDCouponInfoSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){Id = (String)v;return;}
		if (i == 1){CouponSn = (String)v;return;}
		if (i == 2){Batch = (String)v;return;}
		if (i == 3){RiskCode = (String)v;return;}
		if (i == 4){InsuranceCompany = (String)v;return;}
		if (i == 5){ProvideUser = (String)v;return;}
		if (i == 6){ParValue = (String)v;return;}
		if (i == 7){PayAmount = (String)v;return;}
		if (i == 8){UseTimes = (String)v;return;}
		if (i == 9){Direction = (String)v;return;}
		if (i == 10){StartTime = (Date)v;return;}
		if (i == 11){EndTime = (Date)v;return;}
		if (i == 12){ActivitySn = (String)v;return;}
		if (i == 13){ChannelSn = (String)v;return;}
		if (i == 14){Status = String.valueOf(v);return;}
		if (i == 15){Purpose = (String)v;return;}
		if (i == 16){OrderSn = (String)v;return;}
		if (i == 17){PayTime = (Date)v;return;}
		if (i == 18){MemberId = (String)v;return;}
		if (i == 19){Mail = (String)v;return;}
		if (i == 20){Mobile = (String)v;return;}
		if (i == 21){Prop1 = (String)v;return;}
		if (i == 22){Prop2 = (String)v;return;}
		if (i == 23){Prop3 = (String)v;return;}
		if (i == 24){Prop4 = (String)v;return;}
		if (i == 25){CreateUser = (String)v;return;}
		if (i == 26){CreateDate = (Date)v;return;}
		if (i == 27){ModifyUser = (String)v;return;}
		if (i == 28){ModifyDate = (Date)v;return;}
		if (i == 29){RealProvideUser = (String)v;return;}
		if (i == 30){product = (String)v;return;}
		if (i == 31){shortName = (String)v;return;}
		if (i == 32){issuechannel = (String)v;return;}
		if (i == 33){remindFlag = (String)v;return;}
		if (i == 34){usedUrl = (String)v;return;}
		if (i == 35){maxDeduction = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return Id;}
		if (i == 1){return CouponSn;}
		if (i == 2){return Batch;}
		if (i == 3){return RiskCode;}
		if (i == 4){return InsuranceCompany;}
		if (i == 5){return ProvideUser;}
		if (i == 6){return ParValue;}
		if (i == 7){return PayAmount;}
		if (i == 8){return UseTimes;}
		if (i == 9){return Direction;}
		if (i == 10){return StartTime;}
		if (i == 11){return EndTime;}
		if (i == 12){return ActivitySn;}
		if (i == 13){return ChannelSn;}
		if (i == 14){return Status;}
		if (i == 15){return Purpose;}
		if (i == 16){return OrderSn;}
		if (i == 17){return PayTime;}
		if (i == 18){return MemberId;}
		if (i == 19){return Mail;}
		if (i == 20){return Mobile;}
		if (i == 21){return Prop1;}
		if (i == 22){return Prop2;}
		if (i == 23){return Prop3;}
		if (i == 24){return Prop4;}
		if (i == 25){return CreateUser;}
		if (i == 26){return CreateDate;}
		if (i == 27){return ModifyUser;}
		if (i == 28){return ModifyDate;}
		if (i == 29){return RealProvideUser;}
		if (i == 30){return product;}
		if (i == 31){return shortName;}
		if (i == 32){return issuechannel;}
		if (i == 33){return remindFlag;}
		if (i == 34){return usedUrl;}
		if (i == 35){return maxDeduction;}
		return null;
	}

	/**
	* 获取字段Id的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getId() {
		return Id;
	}

	/**
	* 设置字段Id的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setId(String id) {
		this.Id = id;
    }

	/**
	* 获取字段CouponSn的值，该字段的<br>
	* 字段名称 :优惠券号<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getCouponSn() {
		return CouponSn;
	}

	/**
	* 设置字段CouponSn的值，该字段的<br>
	* 字段名称 :优惠券号<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setCouponSn(String couponSn) {
		this.CouponSn = couponSn;
    }

	/**
	* 获取字段Batch的值，该字段的<br>
	* 字段名称 :批次<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getBatch() {
		return Batch;
	}

	/**
	* 设置字段Batch的值，该字段的<br>
	* 字段名称 :批次<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setBatch(String batch) {
		this.Batch = batch;
    }

	/**
	* 获取字段RiskCode的值，该字段的<br>
	* 字段名称 :优惠险种<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getRiskCode() {
		return RiskCode;
	}

	/**
	* 设置字段RiskCode的值，该字段的<br>
	* 字段名称 :优惠险种<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRiskCode(String riskCode) {
		this.RiskCode = riskCode;
    }

	/**
	* 获取字段InsuranceCompany的值，该字段的<br>
	* 字段名称 :优惠公司<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getInsuranceCompany() {
		return InsuranceCompany;
	}

	/**
	* 设置字段InsuranceCompany的值，该字段的<br>
	* 字段名称 :优惠公司<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setInsuranceCompany(String insuranceCompany) {
		this.InsuranceCompany = insuranceCompany;
    }

	/**
	* 获取字段ProvideUser的值，该字段的<br>
	* 字段名称 :发放人<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProvideUser() {
		return ProvideUser;
	}

	/**
	* 设置字段ProvideUser的值，该字段的<br>
	* 字段名称 :发放人<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProvideUser(String provideUser) {
		this.ProvideUser = provideUser;
    }

	/**
	* 获取字段ParValue的值，该字段的<br>
	* 字段名称 :面值<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getParValue() {
		return ParValue;
	}

	/**
	* 设置字段ParValue的值，该字段的<br>
	* 字段名称 :面值<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setParValue(String parValue) {
		this.ParValue = parValue;
    }

	/**
	* 获取字段PayAmount的值，该字段的<br>
	* 字段名称 :消费金额<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPayAmount() {
		return PayAmount;
	}

	/**
	* 设置字段PayAmount的值，该字段的<br>
	* 字段名称 :消费金额<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPayAmount(String payAmount) {
		this.PayAmount = payAmount;
    }

	/**
	* 获取字段UseTimes的值，该字段的<br>
	* 字段名称 :使用次数<br>
	* 数据类型 :varchar(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getUseTimes() {
		return UseTimes;
	}

	/**
	* 设置字段UseTimes的值，该字段的<br>
	* 字段名称 :使用次数<br>
	* 数据类型 :varchar(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setUseTimes(String useTimes) {
		this.UseTimes = useTimes;
    }

	/**
	* 获取字段Direction的值，该字段的<br>
	* 字段名称 :使用说明<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getDirection() {
		return Direction;
	}

	/**
	* 设置字段Direction的值，该字段的<br>
	* 字段名称 :使用说明<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setDirection(String direction) {
		this.Direction = direction;
    }

	/**
	* 获取字段StartTime的值，该字段的<br>
	* 字段名称 :起始时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public Date getStartTime() {
		return StartTime;
	}

	/**
	* 设置字段StartTime的值，该字段的<br>
	* 字段名称 :起始时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setStartTime(Date startTime) {
		this.StartTime = startTime;
    }

	/**
	* 获取字段EndTime的值，该字段的<br>
	* 字段名称 :终止时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public Date getEndTime() {
		return EndTime;
	}

	/**
	* 设置字段EndTime的值，该字段的<br>
	* 字段名称 :终止时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setEndTime(Date endTime) {
		this.EndTime = endTime;
    }

	/**
	* 获取字段ActivitySn的值，该字段的<br>
	* 字段名称 :活动编码<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getActivitySn() {
		return ActivitySn;
	}

	/**
	* 设置字段ActivitySn的值，该字段的<br>
	* 字段名称 :活动编码<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setActivitySn(String activitySn) {
		this.ActivitySn = activitySn;
    }

	/**
	* 获取字段ChannelSn的值，该字段的<br>
	* 字段名称 :渠道编码<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getChannelSn() {
		return ChannelSn;
	}

	/**
	* 设置字段ChannelSn的值，该字段的<br>
	* 字段名称 :渠道编码<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setChannelSn(String channelSn) {
		this.ChannelSn = channelSn;
    }

	/**
	* 获取字段Status的值，该字段的<br>
	* 字段名称 :优惠劵状态<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getStatus() {
		return Status;
	}

	/**
	* 设置字段Status的值，该字段的<br>
	* 字段名称 :优惠劵状态<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setStatus(String status) {
		this.Status = status;
    }

	/**
	* 获取字段Purpose的值，该字段的<br>
	* 字段名称 :服务对象<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPurpose() {
		return Purpose;
	}

	/**
	* 设置字段Purpose的值，该字段的<br>
	* 字段名称 :服务对象<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPurpose(String purpose) {
		this.Purpose = purpose;
    }

	/**
	* 获取字段OrderSn的值，该字段的<br>
	* 字段名称 :使用单号<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getOrderSn() {
		return OrderSn;
	}

	/**
	* 设置字段OrderSn的值，该字段的<br>
	* 字段名称 :使用单号<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setOrderSn(String orderSn) {
		this.OrderSn = orderSn;
    }

	/**
	* 获取字段PayTime的值，该字段的<br>
	* 字段名称 :使用时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getPayTime() {
		return PayTime;
	}

	/**
	* 设置字段PayTime的值，该字段的<br>
	* 字段名称 :使用时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPayTime(Date payTime) {
		this.PayTime = payTime;
    }

	/**
	* 获取字段MemberId的值，该字段的<br>
	* 字段名称 :所属会员<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMemberId() {
		return MemberId;
	}

	/**
	* 设置字段MemberId的值，该字段的<br>
	* 字段名称 :所属会员<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMemberId(String memberId) {
		this.MemberId = memberId;
    }

	/**
	* 获取字段Mail的值，该字段的<br>
	* 字段名称 :所属邮箱<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMail() {
		return Mail;
	}

	/**
	* 设置字段Mail的值，该字段的<br>
	* 字段名称 :所属邮箱<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMail(String mail) {
		this.Mail = mail;
    }

	/**
	* 获取字段Mobile的值，该字段的<br>
	* 字段名称 :所属手机<br>
	* 数据类型 :varchar(12)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMobile() {
		return Mobile;
	}

	/**
	* 设置字段Mobile的值，该字段的<br>
	* 字段名称 :所属手机<br>
	* 数据类型 :varchar(12)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMobile(String mobile) {
		this.Mobile = mobile;
    }

	/**
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* 获取字段Prop2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* 设置字段Prop2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

	/**
	* 获取字段Prop3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp3() {
		return Prop3;
	}

	/**
	* 设置字段Prop3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp3(String prop3) {
		this.Prop3 = prop3;
    }

	/**
	* 获取字段Prop4的值，该字段的<br>
	* 字段名称 :备用字段4<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp4() {
		return Prop4;
	}

	/**
	* 设置字段Prop4的值，该字段的<br>
	* 字段名称 :备用字段4<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp4(String prop4) {
		this.Prop4 = prop4;
    }

	/**
	* 获取字段CreateUser的值，该字段的<br>
	* 字段名称 :申请人<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getCreateUser() {
		return CreateUser;
	}

	/**
	* 设置字段CreateUser的值，该字段的<br>
	* 字段名称 :申请人<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setCreateUser(String createUser) {
		this.CreateUser = createUser;
    }

	/**
	* 获取字段CreateDate的值，该字段的<br>
	* 字段名称 :申请时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public Date getCreateDate() {
		return CreateDate;
	}

	/**
	* 设置字段CreateDate的值，该字段的<br>
	* 字段名称 :申请时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setCreateDate(Date createDate) {
		this.CreateDate = createDate;
    }

	/**
	* 获取字段ModifyUser的值，该字段的<br>
	* 字段名称 :修改人<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getModifyUser() {
		return ModifyUser;
	}

	/**
	* 设置字段ModifyUser的值，该字段的<br>
	* 字段名称 :修改人<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyUser(String modifyUser) {
		this.ModifyUser = modifyUser;
    }

	/**
	* 获取字段ModifyDate的值，该字段的<br>
	* 字段名称 :修改时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getModifyDate() {
		return ModifyDate;
	}

	/**
	* 设置字段ModifyDate的值，该字段的<br>
	* 字段名称 :修改时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyDate(Date modifyDate) {
		this.ModifyDate = modifyDate;
    }

	/**
	* 获取字段RealProvideUser的值，该字段的<br>
	* 字段名称 :实际发放人<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getRealProvideUser() {
		return RealProvideUser;
	}

	/**
	* 设置字段RealProvideUser的值，该字段的<br>
	* 字段名称 :实际发放人<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRealProvideUser(String realProvideUser) {
		this.RealProvideUser = realProvideUser;
    }
	
	/**
	* 获取字段product的值，该字段的<br>
	* 字段名称 :优惠产品<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getproduct() {
		return product;
	}

	/**
	* 设置字段product的值，该字段的<br>
	* 字段名称 :优惠产品<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setproduct(String product) {
		this.product = product;
    }
	
	/**
	* 获取字段shortName的值，该字段的<br>
	* 字段名称 :简短描述<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getshortName() {
		return shortName;
	}

	/**
	* 设置字段shortName的值，该字段的<br>
	* 字段名称 :简短描述<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setshortName(String shortName) {
		this.shortName = shortName;
    }

	public String getIssuechannel() {
		return issuechannel;
	}

	public void setIssuechannel(String issuechannel) {
		this.issuechannel = issuechannel;
	}

	/**
	 * 获取字段remindFlag的值，该字段的<br>
	 * 字段名称 :提醒标识<br>
	 * 数据类型 :varchar(2)<br>
	 * 是否主键 :false<br>
	 * 是否必填 :true<br>
	 */
	public String getRemindFlag() {
		return remindFlag;
	}
	/**
	 * 设置字段remindFlag的值，该字段的<br>
	 * 字段名称 :提醒标识<br>
	 * 数据类型 :varchar(2)<br>
	 * 是否主键 :false<br>
	 * 是否必填 :true<br>
	 */
	public void setRemindFlag(String remindFlag) {
		this.remindFlag = remindFlag;
	}

	/**
	 * 获取字段usedUrl的值，该字段的<br>
	 * 字段名称 :立即使用URL<br>
	 * 数据类型 :varchar(255)<br>
	 * 是否主键 :false<br>
	 * 是否必填 :true<br>
	 */
	public String getusedUrl() {
		return usedUrl;
	}
	/**
	 * 设置字段usedUrl的值，该字段的<br>
	 * 字段名称 :立即使用URL<br>
	 * 数据类型 :varchar(255)<br>
	 * 是否主键 :false<br>
	 * 是否必填 :true<br>
	 */
	public void setUsedUrl(String usedUrl) {
		this.usedUrl = usedUrl;
	}

	/**
	 * 获取字段maxDeduction的值，该字段的<br>
	 * 字段名称 :折扣券-最高抵扣<br>
	 * 数据类型 :varchar(20)<br>
	 * 是否主键 :false<br>
	 * 是否必填 :false<br>
	 */
	public String getMaxDeduction() {
		return maxDeduction;
	}

	/**
	 * 设置字段maxDeduction的值，该字段的<br>
	 * 字段名称 :折扣券-最高抵扣<br>
	 * 数据类型 :varchar(20)<br>
	 * 是否主键 :false<br>
	 * 是否必填 :false<br>
	 */
	public void setMaxDeduction(String maxDeduction) {
		this.maxDeduction = maxDeduction;
	}
	
}