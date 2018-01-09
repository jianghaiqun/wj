package com.sinosoft.schema;

import java.util.Date;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.orm.SchemaSet;

public class SDCouponSchema extends Schema {
	/**
	 * 表名称：优惠券表<br>
	 * 表代码：Couponinfo<br>
	 * 表主键：id<br>
	 */
	private static final long serialVersionUID = 92787011088639844L;
	private String Id;
	private String CouponSn;
	private String RiskCode;
	private String InsuranceCompany;
	private String ProvideUser;
	private String ParValue;
	private String PayAmount;
	private String UseTimes;
	private String Direction;
	private Date   StartTime;
	private Date   EndTime;
	private String Status;
	private String Purpose;
	private String OrderSn;
	private Date   PayTime;
	private String MemberId;
	private String Mail;
	private String Mobile;
	private String Prop1;
	private String Prop2;
	private String Prop3;
	private String Prop4;
	private String CreateUser;
	private Date   CreateDate;
	private String ModifyUser;
	private Date   ModifyDate;
	private String RealProvideUser;
	
	
	public static final String _TableCode = "CouponInfo";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into CouponInfo values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update CouponInfo set id=?, couponSn=?, riskCode=?, insuranceCompany=?,provideUser=?,parValue=?, payAmount=?, useTimes=?, direction=?, startTime=?, endTime=?, status=?, purpose=?, orderSn=?, payTime=?, memberId=?, mail=?, mobile=?, prop1=?, prop2=?, prop3=?, prop4=?, createUser=?, createDate=?, modifyUser=?, modifyDate=?,realprovideuser=? where id=?";

	protected static final String _DeleteSQL = "delete from CouponInfo  where id=?";

	protected static final String _FillAllSQL = "select * from CouponInfo  where id=?";
	public SDCouponSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[25];
	}
	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("Id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("CouponSn", DataColumn.STRING, 1, 32 , 0 , true , false),
		new SchemaColumn("RiskCode", DataColumn.STRING, 2, 255 , 0 , false , false),
		new SchemaColumn("InsuranceCompany", DataColumn.STRING, 3, 255 , 0 , false , false),
		new SchemaColumn("ProvideUser", DataColumn.STRING, 4, 255 , 0 , false , false),
		new SchemaColumn("ParValue", DataColumn.STRING, 5, 20 , 0 , true , false),
		new SchemaColumn("PayAmount", DataColumn.STRING, 6, 20 , 0 , false , false),
		new SchemaColumn("UseTimes", DataColumn.INTEGER, 7, 11 , 0 , false , false),
		new SchemaColumn("Direction", DataColumn.STRING, 8, 100 , 0 , true , false),
		new SchemaColumn("StartTime", DataColumn.DATETIME, 9, 0 , 0 , false , false),
		new SchemaColumn("EndTime", DataColumn.DATETIME, 10, 0 , 0 , false , false),
		new SchemaColumn("Status", DataColumn.STRING, 11, 10 , 0 , false , false),
		new SchemaColumn("Purpose", DataColumn.STRING, 12, 5 , 0 , true , false),
		new SchemaColumn("OrderSn", DataColumn.STRING, 13, 32 , 0 , false , false),
		new SchemaColumn("PayTime", DataColumn.DATETIME, 14, 0 , 0 , false , false),
		new SchemaColumn("MemberId", DataColumn.STRING, 15, 32 , 0 , false , false),
		new SchemaColumn("Mail", DataColumn.STRING, 16, 50 , 0 , false , false),
		new SchemaColumn("Mobile", DataColumn.STRING, 17, 12 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 18, 255 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 19, 255 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 20, 255 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 21, 255 , 0 , false , false),
		new SchemaColumn("CreateUser", DataColumn.STRING, 22, 50 , 0 , true , false),
		new SchemaColumn("CreateDate", DataColumn.DATETIME, 23, 0 , 0 , false , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 24, 50 , 0 , false , false),
		new SchemaColumn("ModifyDate", DataColumn.DATETIME, 25, 0 , 0 , false , false),
		new SchemaColumn("RealProvideUser", DataColumn.STRING, 26, 255 , 0 , false , false)
	};
	@Override
	public Object getV(int i) {
		if (i ==0 ){return  Id;}               
		if (i ==1 ){return  CouponSn;}         
		if (i ==2 ){return  RiskCode;}         
		if (i ==3 ){return  InsuranceCompany;} 
		if (i ==4 ){return  ProvideUser;} 
		if (i ==5 ){return  ParValue;}         
		if (i ==6 ){return  PayAmount;}        
		if (i ==7 ){return  UseTimes;}         
		if (i ==8 ){return  Direction;}        
		if (i ==9 ){return  StartTime;}        
		if (i ==10 ){return  EndTime;}          
		if (i ==11 ){return  Status;}          
		if (i ==12 ){return  Purpose;}         
		if (i ==13 ){return  OrderSn;}         
		if (i ==14 ){return  PayTime;}         
		if (i ==15 ){return  MemberId;}        
		if (i ==16 ){return  Mail;}            
		if (i ==17 ){return  Mobile;}          
		if (i ==18 ){return  Prop1;}           
		if (i ==19 ){return  Prop2;}           
		if (i ==20 ){return  Prop3;}           
		if (i ==21 ){return  Prop4;}           
		if (i ==22 ){return  CreateUser;}      
		if (i ==23 ){return  CreateDate;}      
		if (i ==24 ){return  ModifyUser;}      
		if (i ==25 ){return  ModifyDate;}      
		if (i ==26 ){return  RealProvideUser;}      
		return null;
	}
	@Override
	public void setV(int i, Object v) {
		if (i == 0){if(v==null){Id = null;}else{Id = (String)v;}return;}
		if (i == 1){if(v==null){CouponSn = null;}else{CouponSn = (String)v;}return;}
		if (i == 2){RiskCode = (String)v;return;}
		if (i == 3){InsuranceCompany = (String)v;return;}
		if (i == 4){ProvideUser = (String)v;return;}
		if (i == 5){if(v==null){ParValue = null;}else{ParValue = (String)v;}return;}
		if (i == 6){PayAmount = (String)v;return;}
		if (i == 7){UseTimes =  (String)v;return;}
		if (i == 8){if(v==null){Direction = null;}else{Direction = String.valueOf(v);}return;}
		if (i == 9){StartTime = (Date)v;return;}
		if (i == 10){EndTime = (Date)v;return;}
		if (i == 11){Status = String.valueOf(v);return;}
		if (i == 12){if(v==null){Purpose = null;}else{Purpose = (String)v;}return;}
		if (i == 13){OrderSn= (String)v;return;}
		if (i == 14){PayTime= (Date)v;return;}
		if (i == 15){MemberId= (String)v;return;}
		if (i == 16){Mail= (String)v;return;}
		if (i == 17){Mobile= (String)v;return;}
		if (i == 18){Prop1= (String)v;return;}
		if (i == 19){Prop2= (String)v;return;}
		if (i == 20){Prop3= (String)v;return;}
		if (i == 21){Prop4= (String)v;return;}
		if (i == 22){if(v==null){CreateUser = null;}else{CreateUser = (String)v;}return;}
		if (i == 23){if(v==null){CreateDate = null;}else{CreateDate = (Date)v;}return;}
		if (i == 24){ModifyUser= (String)v;return;}
		if (i == 25){ModifyDate= (Date)v;return;}
		if (i == 26){RealProvideUser= (String)v;return;}
	}                                       

	@Override
	protected Schema newInstance() {
		return new SDCouponSchema();
	}

	@Override
	protected SchemaSet newSet() {
		return null;
	}
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getCouponSn() {
		return CouponSn;
	}
	public void setCouponSn(String couponSn) {
		CouponSn = couponSn;
	}
	public String getRiskCode() {
		return RiskCode;
	}
	public void setRiskCode(String riskCode) {
		RiskCode = riskCode;
	}
	public String getInsuranceCompany() {
		return InsuranceCompany;
	}
	public void setInsuranceCompany(String insuranceCompany) {
		InsuranceCompany = insuranceCompany;
	}
	
	public String getProvideUser() {
		return ProvideUser;
	}
	public void setProvideUser(String provideUser) {
		ProvideUser = provideUser;
	}
	public String getParValue() {
		return ParValue;
	}
	public void setParValue(String parValue) {
		ParValue = parValue;
	}
	public String getPayAmount() {
		return PayAmount;
	}
	public void setPayAmount(String payAmount) {
		PayAmount = payAmount;
	}
	public String getUseTimes() {
		return UseTimes;
	}
	public void setUseTimes(String useTimes) {
		UseTimes = useTimes;
	}
	public String getDirection() {
		return Direction;
	}
	public void setDirection(String direction) {
		Direction = direction;
	}
	public Date getStartTime() {
		return StartTime;
	}
	public void setStartTime(Date startTime) {
		StartTime = startTime;
	}
	public Date getEndTime() {
		return EndTime;
	}
	public void setEndTime(Date endTime) {
		EndTime = endTime;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getPurpose() {
		return Purpose;
	}
	public void setPurpose(String purpose) {
		Purpose = purpose;
	}
	public String getOrderSn() {
		return OrderSn;
	}
	public void setOrderSn(String orderSn) {
		OrderSn = orderSn;
	}
	public Date getPayTime() {
		return PayTime;
	}
	public void setPayTime(Date payTime) {
		PayTime = payTime;
	}
	public String getMemberId() {
		return MemberId;
	}
	public void setMemberId(String memberId) {
		MemberId = memberId;
	}
	public String getMail() {
		return Mail;
	}
	public void setMail(String mail) {
		Mail = mail;
	}
	public String getMobile() {
		return Mobile;
	}
	public void setMobile(String mobile) {
		Mobile = mobile;
	}
	public String getProp1() {
		return Prop1;
	}
	public void setProp1(String prop1) {
		Prop1 = prop1;
	}
	public String getProp2() {
		return Prop2;
	}
	public void setProp2(String prop2) {
		Prop2 = prop2;
	}
	public String getProp3() {
		return Prop3;
	}
	public void setProp3(String prop3) {
		Prop3 = prop3;
	}
	public String getProp4() {
		return Prop4;
	}
	public void setProp4(String prop4) {
		Prop4 = prop4;
	}
	public String getCreateUser() {
		return CreateUser;
	}
	public void setCreateUser(String createUser) {
		CreateUser = createUser;
	}
	public String getModifyUser() {
		return ModifyUser;
	}
	public void setModifyUser(String modifyUser) {
		ModifyUser = modifyUser;
	}
	public Date getCreateDate() {
		return CreateDate;
	}
	public void setCreateDate(Date createDate) {
		CreateDate = createDate;
	}
	public Date getModifyDate() {
		return ModifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		ModifyDate = modifyDate;
	}
	public String getRealProvideUser() {
		return RealProvideUser;
	}
	public void setRealProvideUser(String realProvideUser) {
		RealProvideUser = realProvideUser;
	}
	
}
