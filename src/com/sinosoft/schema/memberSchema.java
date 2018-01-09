package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;
import java.math.BigDecimal;

/**
 * 表名称：会员表<br>
 * 表代码：member<br>
 * 表主键：id<br> 
 */ 
public class memberSchema extends Schema {
	private String id; 

	private Date createDate;

	private Date modifyDate;

	private BigDecimal deposit;

	private String email = "";

	private String isAccountEnabled;

	private String isAccountLocked; 

	private Date lockedDate;

	private Date loginDate;

	private Integer loginFailureCount;

	private String loginIp;

	private String password;

	private String passwordRecoverKey;

	private Integer point;

	private String registerIp;

	private String safeAnswer;

	private String safeQuestion;

	private String username;

	private String memberRank_id;

	private String szMemberUserId;

	private String IDNO;

	private String IDType;

	private String QQNO;

	private String VIPChannel;

	private String VIPFrom;

	private String VIPType;

	private String address;

	private String birthday;

	private Integer expiricalValue;

	private String faxNO;

	private String hobby;

	private String industryType;

	private String location;

	private String marriageStatus;

	private String mobileNO = "";

	private String personalURL;

	private String position;

	private String realName;

	private String registerType;

	private String sex;

	private String telephoneNO;

	private String zipcode;

	private String isEmailBinding;

	private String isMobileNOBinding;

	private Integer currentValidatePoint;

	private String pictruePath;

	private Integer usedPoint;

	private String fullDegree;

	private Date retrieveEmailSendDate;

	private Date verifyEmailSendDate;

	private String retrieveEmailvalid;

	private String headPicPath;

	private String mBindInfoForLogin_id = "";
	
	private String fromWap;
	
	private String isfirstMC;
	
	private String hasUpdate;
	
	private Integer preLoginPoints;
	
	private String recommendMemId;
	
	private String recommendFlag;
	
    private Integer recommendRegPoints;
    
    private Integer recommendBuyPoints;
    
    private Integer overduePoints;
    
    private Integer aboutToExpirePoints;
    
    private String aboutToExpireDate;
    
    private String grade;
    
    private String consumeAmount;
    
    private Integer buyCount;
    
    private Date loginDateAfterUngrade;
    
    private String isBuy;
    
    private String accuEndDate;
    
    private String birthYear;
    
    private String vipFlag;
    
    private String version;
    
	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("createDate", DataColumn.DATETIME, 1, 0 , 0 , false , false),
		new SchemaColumn("modifyDate", DataColumn.DATETIME, 2, 0 , 0 , false , false),
		new SchemaColumn("deposit", DataColumn.BIGDECIMAL, 3, 15 , 0 , true , false),
		new SchemaColumn("email", DataColumn.STRING, 4, 255 , 0 , false , false),
		new SchemaColumn("isAccountEnabled", DataColumn.STRING, 5, 255 , 0 , true , false),
		new SchemaColumn("isAccountLocked", DataColumn.STRING, 6, 255 , 0 , true , false),
		new SchemaColumn("lockedDate", DataColumn.DATETIME, 7, 0 , 0 , false , false),
		new SchemaColumn("loginDate", DataColumn.DATETIME, 8, 0 , 0 , false , false),
		new SchemaColumn("loginFailureCount", DataColumn.INTEGER, 9, 11 , 0 , true , false),
		new SchemaColumn("loginIp", DataColumn.STRING, 10, 255 , 0 , false , false),
		new SchemaColumn("password", DataColumn.STRING, 11, 255 , 0 , true , false),
		new SchemaColumn("passwordRecoverKey", DataColumn.STRING, 12, 255 , 0 , false , false),
		new SchemaColumn("point", DataColumn.INTEGER, 13, 11 , 0 , true , false),
		new SchemaColumn("registerIp", DataColumn.STRING, 14, 255 , 0 , true , false),
		new SchemaColumn("safeAnswer", DataColumn.STRING, 15, 255 , 0 , false , false),
		new SchemaColumn("safeQuestion", DataColumn.STRING, 16, 255 , 0 , false , false),
		new SchemaColumn("username", DataColumn.STRING, 17, 100 , 0 , false , false),
		new SchemaColumn("memberRank_id", DataColumn.STRING, 18, 32 , 0 , true , false),
		new SchemaColumn("szMemberUserId", DataColumn.STRING, 19, 255 , 0 , false , false),
		new SchemaColumn("IDNO", DataColumn.STRING, 20, 255 , 0 , false , false),
		new SchemaColumn("IDType", DataColumn.STRING, 21, 255 , 0 , false , false),
		new SchemaColumn("QQNO", DataColumn.STRING, 22, 255 , 0 , false , false),
		new SchemaColumn("VIPChannel", DataColumn.STRING, 23, 255 , 0 , false , false),
		new SchemaColumn("VIPFrom", DataColumn.STRING, 24, 255 , 0 , false , false),
		new SchemaColumn("VIPType", DataColumn.STRING, 25, 255 , 0 , false , false),
		new SchemaColumn("address", DataColumn.STRING, 26, 255 , 0 , false , false),
		new SchemaColumn("birthday", DataColumn.STRING, 27, 50 , 0 , false , false),
		new SchemaColumn("expiricalValue", DataColumn.INTEGER, 28, 11 , 0 , false , false),
		new SchemaColumn("faxNO", DataColumn.STRING, 29, 255 , 0 , false , false),
		new SchemaColumn("hobby", DataColumn.STRING, 30, 255 , 0 , false , false),
		new SchemaColumn("industryType", DataColumn.STRING, 31, 255 , 0 , false , false),
		new SchemaColumn("location", DataColumn.STRING, 32, 255 , 0 , false , false),
		new SchemaColumn("marriageStatus", DataColumn.STRING, 33, 255 , 0 , false , false),
		new SchemaColumn("mobileNO", DataColumn.STRING, 34, 255 , 0 , false , false),
		new SchemaColumn("personalURL", DataColumn.STRING, 35, 255 , 0 , false , false),
		new SchemaColumn("position", DataColumn.STRING, 36, 255 , 0 , false , false),
		new SchemaColumn("realName", DataColumn.STRING, 37, 255 , 0 , false , false),
		new SchemaColumn("registerType", DataColumn.STRING, 38, 255 , 0 , false , false),
		new SchemaColumn("sex", DataColumn.STRING, 39, 255 , 0 , false , false),
		new SchemaColumn("telephoneNO", DataColumn.STRING, 40, 255 , 0 , false , false),
		new SchemaColumn("zipcode", DataColumn.STRING, 41, 255 , 0 , false , false),
		new SchemaColumn("isEmailBinding", DataColumn.STRING, 42, 255 , 0 , false , false),
		new SchemaColumn("isMobileNOBinding", DataColumn.STRING, 43, 255 , 0 , false , false),
		new SchemaColumn("currentValidatePoint", DataColumn.INTEGER, 44, 11 , 0 , false , false),
		new SchemaColumn("pictruePath", DataColumn.STRING, 45, 255 , 0 , false , false),
		new SchemaColumn("usedPoint", DataColumn.INTEGER, 46, 11 , 0 , false , false),
		new SchemaColumn("fullDegree", DataColumn.STRING, 47, 255 , 0 , false , false),
		new SchemaColumn("retrieveEmailSendDate", DataColumn.DATETIME, 48, 0 , 0 , false , false),
		new SchemaColumn("verifyEmailSendDate", DataColumn.DATETIME, 49, 0 , 0 , false , false),
		new SchemaColumn("retrieveEmailvalid", DataColumn.STRING, 50, 255 , 0 , false , false),
		new SchemaColumn("headPicPath", DataColumn.STRING, 51, 200 , 0 , false , false),
		new SchemaColumn("mBindInfoForLogin_id", DataColumn.STRING, 52, 32 , 0 , false , false),
		new SchemaColumn("fromWap", DataColumn.STRING, 53, 255 , 0 , false , false),
		new SchemaColumn("isfirstMC", DataColumn.STRING, 54, 5 , 0 , false , false),
		new SchemaColumn("hasUpdate", DataColumn.STRING, 55, 200 , 0 , false , false),
		new SchemaColumn("preLoginPoints", DataColumn.INTEGER, 56, 11 , 0 , false , false),
		new SchemaColumn("recommendMemId", DataColumn.STRING, 57, 32 , 0 , false , false),
		new SchemaColumn("recommendFlag", DataColumn.STRING, 58, 10 , 0 , false , false),
		new SchemaColumn("recommendRegPoints", DataColumn.INTEGER, 59, 11 , 0 , false , false),
		new SchemaColumn("recommendBuyPoints", DataColumn.INTEGER, 60, 11 , 0 , false , false),
		new SchemaColumn("overduePoints", DataColumn.INTEGER, 61, 11 , 0 , false , false),
		new SchemaColumn("aboutToExpirePoints", DataColumn.INTEGER, 62, 11 , 0 , false , false),
		new SchemaColumn("aboutToExpireDate", DataColumn.STRING, 63, 20 , 0 , false , false),
		new SchemaColumn("grade", DataColumn.STRING, 64, 20 , 0 , false , false),
		new SchemaColumn("consumeAmount", DataColumn.STRING, 65, 20 , 0 , false , false),
		new SchemaColumn("buyCount", DataColumn.INTEGER, 66, 11 , 0 , false , false),
		new SchemaColumn("loginDateAfterUngrade", DataColumn.DATETIME, 67, 0 , 0 , false , false),
		new SchemaColumn("isBuy", DataColumn.STRING, 68, 10 , 0 , false , false),
		new SchemaColumn("accuEndDate", DataColumn.STRING, 69, 20 , 0 , false , false),
		new SchemaColumn("birthYear", DataColumn.STRING, 70, 10 , 0 , false , false),
		new SchemaColumn("vipFlag", DataColumn.STRING, 71, 10 , 0 , false , false),
		new SchemaColumn("version", DataColumn.STRING, 72, 255 , 0 , false , false)
	};
	
	public static final String _TableCode = "member";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into member values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update member set id=?,createDate=?,modifyDate=?,deposit=?,email=?,isAccountEnabled=?,isAccountLocked=?,lockedDate=?,loginDate=?,loginFailureCount=?,loginIp=?,password=?,passwordRecoverKey=?,point=?,registerIp=?,safeAnswer=?,safeQuestion=?,username=?,memberRank_id=?,szMemberUserId=?,IDNO=?,IDType=?,QQNO=?,VIPChannel=?,VIPFrom=?,VIPType=?,address=?,birthday=?,expiricalValue=?,faxNO=?,hobby=?,industryType=?,location=?,marriageStatus=?,mobileNO=?,personalURL=?,position=?,realName=?,registerType=?,sex=?,telephoneNO=?,zipcode=?,isEmailBinding=?,isMobileNOBinding=?,currentValidatePoint=?,pictruePath=?,usedPoint=?,fullDegree=?,retrieveEmailSendDate=?,verifyEmailSendDate=?,retrieveEmailvalid=?,headPicPath=?,mBindInfoForLogin_id=?,fromWap=?,isfirstMC=?,hasUpdate=?,preLoginPoints=?,recommendMemId=?,recommendFlag=?,recommendRegPoints=?,recommendBuyPoints=?,overduePoints=?,aboutToExpirePoints=?,aboutToExpireDate=?,grade=?,consumeAmount=?,buyCount=?,loginDateAfterUngrade=?,isBuy=?,accuEndDate=?,birthYear=?,vipFlag=?,version=? where id=?";

	protected static final String _DeleteSQL = "delete from member  where id=?";

	protected static final String _FillAllSQL = "select * from member  where id=?";

	public memberSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[73];
	}

	protected Schema newInstance(){
		return new memberSchema();
	}

	protected SchemaSet newSet(){
		return new memberSet();
	}

	public memberSet query() {
		return query(null, -1, -1);
	}

	public memberSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public memberSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public memberSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (memberSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){createDate = (Date)v;return;}
		if (i == 2){modifyDate = (Date)v;return;}
		if (i == 3){if(v==null){deposit = null;}else{deposit =  ((BigDecimal)v) ;}return;}
		if (i == 4){email = (String)v;return;}
		if (i == 5){isAccountEnabled = (String)v;return;}
		if (i == 6){isAccountLocked = (String)v;return;}
		if (i == 7){lockedDate = (Date)v;return;}
		if (i == 8){loginDate = (Date)v;return;}
		if (i == 9){if(v==null){loginFailureCount = null;}else{loginFailureCount = new Integer(v.toString());}return;}
		if (i == 10){loginIp = (String)v;return;}
		if (i == 11){password = (String)v;return;}
		if (i == 12){passwordRecoverKey = (String)v;return;}
		if (i == 13){if(v==null){point = null;}else{point = new Integer(v.toString());}return;}
		if (i == 14){registerIp = (String)v;return;}
		if (i == 15){safeAnswer = (String)v;return;}
		if (i == 16){safeQuestion = (String)v;return;}
		if (i == 17){username = (String)v;return;}
		if (i == 18){memberRank_id = (String)v;return;}
		if (i == 19){szMemberUserId = (String)v;return;}
		if (i == 20){IDNO = (String)v;return;}
		if (i == 21){IDType = (String)v;return;}
		if (i == 22){QQNO = (String)v;return;}
		if (i == 23){VIPChannel = (String)v;return;}
		if (i == 24){VIPFrom = (String)v;return;}
		if (i == 25){VIPType = (String)v;return;}
		if (i == 26){address = (String)v;return;}
		if (i == 27){birthday = (String)v;return;}
		if (i == 28){if(v==null){expiricalValue = null;}else{expiricalValue = new Integer(v.toString());}return;}
		if (i == 29){faxNO = (String)v;return;}
		if (i == 30){hobby = (String)v;return;}
		if (i == 31){industryType = (String)v;return;}
		if (i == 32){location = (String)v;return;}
		if (i == 33){marriageStatus = (String)v;return;}
		if (i == 34){mobileNO = (String)v;return;}
		if (i == 35){personalURL = (String)v;return;}
		if (i == 36){position = (String)v;return;}
		if (i == 37){realName = (String)v;return;}
		if (i == 38){registerType = (String)v;return;}
		if (i == 39){sex = (String)v;return;}
		if (i == 40){telephoneNO = (String)v;return;}
		if (i == 41){zipcode = (String)v;return;}
		if (i == 42){isEmailBinding = (String)v;return;}
		if (i == 43){isMobileNOBinding = (String)v;return;}
		if (i == 44){if(v==null){currentValidatePoint = null;}else{currentValidatePoint = new Integer(v.toString());}return;}
		if (i == 45){pictruePath = (String)v;return;}
		if (i == 46){if(v==null){usedPoint = null;}else{usedPoint = new Integer(v.toString());}return;}
		if (i == 47){fullDegree = (String)v;return;}
		if (i == 48){retrieveEmailSendDate = (Date)v;return;}
		if (i == 49){verifyEmailSendDate = (Date)v;return;}
		if (i == 50){retrieveEmailvalid = (String)v;return;}
		if (i == 51){headPicPath = (String)v;return;}
		if (i == 52){mBindInfoForLogin_id = (String)v;return;}
		if (i == 53){fromWap = (String)v;return;}
		if (i == 54){isfirstMC = (String)v;return;}
		if (i == 55){hasUpdate = (String)v;return;}
		if (i == 56){if(v==null){preLoginPoints = null;}else{preLoginPoints = new Integer(v.toString());}return;}
		if (i == 57){recommendMemId = (String)v;return;}
		if (i == 58){recommendFlag = (String)v;return;}
		if (i == 59){if(v==null){recommendRegPoints = null;}else{recommendRegPoints = new Integer(v.toString());}return;}
		if (i == 60){if(v==null){recommendBuyPoints = null;}else{recommendBuyPoints = new Integer(v.toString());}return;}
		if (i == 61){if(v==null){overduePoints = null;}else{overduePoints = new Integer(v.toString());}return;}
		if (i == 62){if(v==null){aboutToExpirePoints = null;}else{aboutToExpirePoints = new Integer(v.toString());}return;}
		if (i == 63){aboutToExpireDate = (String)v;return;}
		if (i == 64){grade = (String)v;return;}
		if (i == 65){consumeAmount = (String)v;return;}
		if (i == 66){if(v==null){buyCount = null;}else{buyCount = new Integer(v.toString());}return;}
		if (i == 67){loginDateAfterUngrade = (Date)v;return;}
		if (i == 68){isBuy = (String)v;return;}
		if (i == 69){accuEndDate = (String)v;return;}
		if (i == 70){birthYear = (String)v;return;}
		if (i == 71){vipFlag = (String)v;return;}
		if (i == 72){version = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return createDate;}
		if (i == 2){return modifyDate;}
		if (i == 3){return deposit;}
		if (i == 4){return email;}
		if (i == 5){return isAccountEnabled;}
		if (i == 6){return isAccountLocked;}
		if (i == 7){return lockedDate;}
		if (i == 8){return loginDate;}
		if (i == 9){return loginFailureCount;}
		if (i == 10){return loginIp;}
		if (i == 11){return password;}
		if (i == 12){return passwordRecoverKey;}
		if (i == 13){return point;}
		if (i == 14){return registerIp;}
		if (i == 15){return safeAnswer;}
		if (i == 16){return safeQuestion;}
		if (i == 17){return username;}
		if (i == 18){return memberRank_id;}
		if (i == 19){return szMemberUserId;}
		if (i == 20){return IDNO;}
		if (i == 21){return IDType;}
		if (i == 22){return QQNO;}
		if (i == 23){return VIPChannel;}
		if (i == 24){return VIPFrom;}
		if (i == 25){return VIPType;}
		if (i == 26){return address;}
		if (i == 27){return birthday;}
		if (i == 28){return expiricalValue;}
		if (i == 29){return faxNO;}
		if (i == 30){return hobby;}
		if (i == 31){return industryType;}
		if (i == 32){return location;}
		if (i == 33){return marriageStatus;}
		if (i == 34){return mobileNO;}
		if (i == 35){return personalURL;}
		if (i == 36){return position;}
		if (i == 37){return realName;}
		if (i == 38){return registerType;}
		if (i == 39){return sex;}
		if (i == 40){return telephoneNO;}
		if (i == 41){return zipcode;}
		if (i == 42){return isEmailBinding;}
		if (i == 43){return isMobileNOBinding;}
		if (i == 44){return currentValidatePoint;}
		if (i == 45){return pictruePath;}
		if (i == 46){return usedPoint;}
		if (i == 47){return fullDegree;}
		if (i == 48){return retrieveEmailSendDate;}
		if (i == 49){return verifyEmailSendDate;}
		if (i == 50){return retrieveEmailvalid;}
		if (i == 51){return headPicPath;}
		if (i == 52){return mBindInfoForLogin_id;}
		if (i == 53){return fromWap;}
		if (i == 54){return isfirstMC;}
		if (i == 55){return hasUpdate;}
		if (i == 56){return preLoginPoints;}
		if (i == 57){return recommendMemId;}
		if (i == 58){return recommendFlag;}
		if (i == 59){return recommendRegPoints;}
		if (i == 60){return recommendBuyPoints;}
		if (i == 61){return overduePoints;}
		if (i == 62){return aboutToExpirePoints;}
		if (i == 63){return aboutToExpireDate;}
		if (i == 64){return grade;}
		if (i == 65){return consumeAmount;}
		if (i == 66){return buyCount;}
		if (i == 67){return loginDateAfterUngrade;}
		if (i == 68){return isBuy;}
		if (i == 69){return accuEndDate;}
		if (i == 70){return birthYear;}
		if (i == 71){return vipFlag;}
		if (i == 72){return version;}
		return null;
	}

	/**
	* 获取字段id的值，该字段的<br>
	* 字段名称 :会员ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getid() {
		return id;
	}

	/**
	* 设置字段id的值，该字段的<br>
	* 字段名称 :会员ID<br>
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
	* 获取字段deposit的值，该字段的<br>
	* 字段名称 :预存款<br>
	* 数据类型 :decimal(15)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public BigDecimal getdeposit() {
		return deposit;
	}

	/**
	* 设置字段deposit的值，该字段的<br>
	* 字段名称 :预存款<br>
	* 数据类型 :decimal(15)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setdeposit(BigDecimal deposit) {
		this.deposit = deposit;
    }

	/**
	* 获取字段email的值，该字段的<br>
	* 字段名称 :邮箱地址<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getemail() {
		return email;
	}

	/**
	* 设置字段email的值，该字段的<br>
	* 字段名称 :邮箱地址<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setemail(String email) {
		this.email = email;
    }

	/**
	* 获取字段isAccountEnabled的值，该字段的<br>
	* 字段名称 :账号是否启用<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getisAccountEnabled() {
		return isAccountEnabled;
	}

	/**
	* 设置字段isAccountEnabled的值，该字段的<br>
	* 字段名称 :账号是否启用<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setisAccountEnabled(String isAccountEnabled) {
		this.isAccountEnabled = isAccountEnabled;
    }

	/**
	* 获取字段isAccountLocked的值，该字段的<br>
	* 字段名称 :账号是否锁定<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getisAccountLocked() {
		return isAccountLocked;
	}

	/**
	* 设置字段isAccountLocked的值，该字段的<br>
	* 字段名称 :账号是否锁定<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setisAccountLocked(String isAccountLocked) {
		this.isAccountLocked = isAccountLocked;
    }

	/**
	* 获取字段lockedDate的值，该字段的<br>
	* 字段名称 :账号锁定日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getlockedDate() {
		return lockedDate;
	}

	/**
	* 设置字段lockedDate的值，该字段的<br>
	* 字段名称 :账号锁定日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setlockedDate(Date lockedDate) {
		this.lockedDate = lockedDate;
    }

	/**
	* 获取字段loginDate的值，该字段的<br>
	* 字段名称 :最后登录日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getloginDate() {
		return loginDate;
	}

	/**
	* 设置字段loginDate的值，该字段的<br>
	* 字段名称 :最后登录日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setloginDate(Date loginDate) {
		this.loginDate = loginDate;
    }

	/**
	* 获取字段loginFailureCount的值，该字段的<br>
	* 字段名称 :连续登录失败的次数<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public int getloginFailureCount() {
		if(loginFailureCount==null){return 0;}
		return loginFailureCount.intValue();
	}

	/**
	* 设置字段loginFailureCount的值，该字段的<br>
	* 字段名称 :连续登录失败的次数<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setloginFailureCount(int loginFailureCount) {
		this.loginFailureCount = new Integer(loginFailureCount);
    }

	/**
	* 设置字段loginFailureCount的值，该字段的<br>
	* 字段名称 :连续登录失败的次数<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setloginFailureCount(String loginFailureCount) {
		if (loginFailureCount == null){
			this.loginFailureCount = null;
			return;
		}
		this.loginFailureCount = new Integer(loginFailureCount);
    }

	/**
	* 获取字段loginIp的值，该字段的<br>
	* 字段名称 :最后登录IP<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getloginIp() {
		return loginIp;
	}

	/**
	* 设置字段loginIp的值，该字段的<br>
	* 字段名称 :最后登录IP<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setloginIp(String loginIp) {
		this.loginIp = loginIp;
    }

	/**
	* 获取字段password的值，该字段的<br>
	* 字段名称 :密码<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getpassword() {
		return password;
	}

	/**
	* 设置字段password的值，该字段的<br>
	* 字段名称 :密码<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setpassword(String password) {
		this.password = password;
    }

	/**
	* 获取字段passwordRecoverKey的值，该字段的<br>
	* 字段名称 :密码找回Key<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getpasswordRecoverKey() {
		return passwordRecoverKey;
	}

	/**
	* 设置字段passwordRecoverKey的值，该字段的<br>
	* 字段名称 :密码找回Key<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpasswordRecoverKey(String passwordRecoverKey) {
		this.passwordRecoverKey = passwordRecoverKey;
    }

	/**
	* 获取字段point的值，该字段的<br>
	* 字段名称 :积分<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public int getpoint() {
		if(point==null){return 0;}
		return point.intValue();
	}

	/**
	* 设置字段point的值，该字段的<br>
	* 字段名称 :积分<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setpoint(int point) {
		this.point = new Integer(point);
    }

	/**
	* 设置字段point的值，该字段的<br>
	* 字段名称 :积分<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setpoint(String point) {
		if (point == null){
			this.point = null;
			return;
		}
		this.point = new Integer(point);
    }

	/**
	* 获取字段registerIp的值，该字段的<br>
	* 字段名称 :注册IP<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getregisterIp() {
		return registerIp;
	}

	/**
	* 设置字段registerIp的值，该字段的<br>
	* 字段名称 :注册IP<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setregisterIp(String registerIp) {
		this.registerIp = registerIp;
    }

	/**
	* 获取字段safeAnswer的值，该字段的<br>
	* 字段名称 :密码保护问题答案<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getsafeAnswer() {
		return safeAnswer;
	}

	/**
	* 设置字段safeAnswer的值，该字段的<br>
	* 字段名称 :密码保护问题答案<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setsafeAnswer(String safeAnswer) {
		this.safeAnswer = safeAnswer;
    }

	/**
	* 获取字段safeQuestion的值，该字段的<br>
	* 字段名称 :密码保护问题<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getsafeQuestion() {
		return safeQuestion;
	}

	/**
	* 设置字段safeQuestion的值，该字段的<br>
	* 字段名称 :密码保护问题<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setsafeQuestion(String safeQuestion) {
		this.safeQuestion = safeQuestion;
    }

	/**
	* 获取字段username的值，该字段的<br>
	* 字段名称 :用户名<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getusername() {
		return username;
	}

	/**
	* 设置字段username的值，该字段的<br>
	* 字段名称 :用户名<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setusername(String username) {
		this.username = username;
    }

	/**
	* 获取字段memberRank_id的值，该字段的<br>
	* 字段名称 :会员等级_id<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getmemberRank_id() {
		return memberRank_id;
	}

	/**
	* 设置字段memberRank_id的值，该字段的<br>
	* 字段名称 :会员等级_id<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setmemberRank_id(String memberRank_id) {
		this.memberRank_id = memberRank_id;
    }

	/**
	* 获取字段szMemberUserId的值，该字段的<br>
	* 字段名称 :移动商城用户id<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getszMemberUserId() {
		return szMemberUserId;
	}

	/**
	* 设置字段szMemberUserId的值，该字段的<br>
	* 字段名称 :移动商城用户id<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setszMemberUserId(String szMemberUserId) {
		this.szMemberUserId = szMemberUserId;
    }

	/**
	* 获取字段IDNO的值，该字段的<br>
	* 字段名称 :ID号<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getIDNO() {
		return IDNO;
	}

	/**
	* 设置字段IDNO的值，该字段的<br>
	* 字段名称 :ID号<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setIDNO(String iDNO) {
		this.IDNO = iDNO;
    }

	/**
	* 获取字段IDType的值，该字段的<br>
	* 字段名称 :ID类型<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getIDType() {
		return IDType;
	}

	/**
	* 设置字段IDType的值，该字段的<br>
	* 字段名称 :ID类型<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setIDType(String iDType) {
		this.IDType = iDType;
    }

	/**
	* 获取字段QQNO的值，该字段的<br>
	* 字段名称 :QQ号<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getQQNO() {
		return QQNO;
	}

	/**
	* 设置字段QQNO的值，该字段的<br>
	* 字段名称 :QQ号<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setQQNO(String qQNO) {
		this.QQNO = qQNO;
    }

	/**
	* 获取字段VIPChannel的值，该字段的<br>
	* 字段名称 :会员渠道<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getVIPChannel() {
		return VIPChannel;
	}

	/**
	* 设置字段VIPChannel的值，该字段的<br>
	* 字段名称 :会员渠道<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setVIPChannel(String vIPChannel) {
		this.VIPChannel = vIPChannel;
    }

	/**
	* 获取字段VIPFrom的值，该字段的<br>
	* 字段名称 :会员来源<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getVIPFrom() {
		return VIPFrom;
	}

	/**
	* 设置字段VIPFrom的值，该字段的<br>
	* 字段名称 :会员来源<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setVIPFrom(String vIPFrom) {
		this.VIPFrom = vIPFrom;
    }

	/**
	* 获取字段VIPType的值，该字段的<br>
	* 字段名称 :会员类型<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getVIPType() {
		return VIPType;
	}

	/**
	* 设置字段VIPType的值，该字段的<br>
	* 字段名称 :会员类型<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setVIPType(String vIPType) {
		this.VIPType = vIPType;
    }

	/**
	* 获取字段address的值，该字段的<br>
	* 字段名称 :联系地址<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getaddress() {
		return address;
	}

	/**
	* 设置字段address的值，该字段的<br>
	* 字段名称 :联系地址<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setaddress(String address) {
		this.address = address;
    }

	/**
	* 获取字段birthday的值，该字段的<br>
	* 字段名称 :生日<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getbirthday() {
		return birthday;
	}

	/**
	* 设置字段birthday的值，该字段的<br>
	* 字段名称 :生日<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setbirthday(String birthday) {
		this.birthday = birthday;
    }

	/**
	* 获取字段expiricalValue的值，该字段的<br>
	* 字段名称 :经验值<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getexpiricalValue() {
		if(expiricalValue==null){return 0;}
		return expiricalValue.intValue();
	}

	/**
	* 设置字段expiricalValue的值，该字段的<br>
	* 字段名称 :经验值<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setexpiricalValue(int expiricalValue) {
		this.expiricalValue = new Integer(expiricalValue);
    }

	/**
	* 设置字段expiricalValue的值，该字段的<br>
	* 字段名称 :经验值<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setexpiricalValue(String expiricalValue) {
		if (expiricalValue == null){
			this.expiricalValue = null;
			return;
		}
		this.expiricalValue = new Integer(expiricalValue);
    }

	/**
	* 获取字段faxNO的值，该字段的<br>
	* 字段名称 :传真号<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getfaxNO() {
		return faxNO;
	}

	/**
	* 设置字段faxNO的值，该字段的<br>
	* 字段名称 :传真号<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setfaxNO(String faxNO) {
		this.faxNO = faxNO;
    }

	/**
	* 获取字段hobby的值，该字段的<br>
	* 字段名称 :爱好<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String gethobby() {
		return hobby;
	}

	/**
	* 设置字段hobby的值，该字段的<br>
	* 字段名称 :爱好<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void sethobby(String hobby) {
		this.hobby = hobby;
    }

	/**
	* 获取字段industryType的值，该字段的<br>
	* 字段名称 :行业类型<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getindustryType() {
		return industryType;
	}

	/**
	* 设置字段industryType的值，该字段的<br>
	* 字段名称 :行业类型<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setindustryType(String industryType) {
		this.industryType = industryType;
    }

	/**
	* 获取字段location的值，该字段的<br>
	* 字段名称 :所在地<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getlocation() {
		return location;
	}

	/**
	* 设置字段location的值，该字段的<br>
	* 字段名称 :所在地<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setlocation(String location) {
		this.location = location;
    }

	/**
	* 获取字段marriageStatus的值，该字段的<br>
	* 字段名称 :婚姻状况<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getmarriageStatus() {
		return marriageStatus;
	}

	/**
	* 设置字段marriageStatus的值，该字段的<br>
	* 字段名称 :婚姻状况<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmarriageStatus(String marriageStatus) {
		this.marriageStatus = marriageStatus;
    }

	/**
	* 获取字段mobileNO的值，该字段的<br>
	* 字段名称 :手机号<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getmobileNO() {
		return mobileNO;
	}

	/**
	* 设置字段mobileNO的值，该字段的<br>
	* 字段名称 :手机号<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmobileNO(String mobileNO) {
		this.mobileNO = mobileNO;
    }

	/**
	* 获取字段personalURL的值，该字段的<br>
	* 字段名称 :个人网址<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getpersonalURL() {
		return personalURL;
	}

	/**
	* 设置字段personalURL的值，该字段的<br>
	* 字段名称 :个人网址<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpersonalURL(String personalURL) {
		this.personalURL = personalURL;
    }

	/**
	* 获取字段position的值，该字段的<br>
	* 字段名称 :职位<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getposition() {
		return position;
	}

	/**
	* 设置字段position的值，该字段的<br>
	* 字段名称 :职位<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setposition(String position) {
		this.position = position;
    }

	/**
	* 获取字段realName的值，该字段的<br>
	* 字段名称 :真实姓名<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrealName() {
		return realName;
	}

	/**
	* 设置字段realName的值，该字段的<br>
	* 字段名称 :真实姓名<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrealName(String realName) {
		this.realName = realName;
    }

	/**
	* 获取字段registerType的值，该字段的<br>
	* 字段名称 :注册方式<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getregisterType() {
		return registerType;
	}

	/**
	* 设置字段registerType的值，该字段的<br>
	* 字段名称 :注册方式<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setregisterType(String registerType) {
		this.registerType = registerType;
    }

	/**
	* 获取字段sex的值，该字段的<br>
	* 字段名称 :性别<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getsex() {
		return sex;
	}

	/**
	* 设置字段sex的值，该字段的<br>
	* 字段名称 :性别<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setsex(String sex) {
		this.sex = sex;
    }

	/**
	* 获取字段telephoneNO的值，该字段的<br>
	* 字段名称 :电话号码<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String gettelephoneNO() {
		return telephoneNO;
	}

	/**
	* 设置字段telephoneNO的值，该字段的<br>
	* 字段名称 :电话号码<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void settelephoneNO(String telephoneNO) {
		this.telephoneNO = telephoneNO;
    }

	/**
	* 获取字段zipcode的值，该字段的<br>
	* 字段名称 :邮政编码<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getzipcode() {
		return zipcode;
	}

	/**
	* 设置字段zipcode的值，该字段的<br>
	* 字段名称 :邮政编码<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setzipcode(String zipcode) {
		this.zipcode = zipcode;
    }

	/**
	* 获取字段isEmailBinding的值，该字段的<br>
	* 字段名称 :邮箱是否绑定<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getisEmailBinding() {
		return isEmailBinding;
	}

	/**
	* 设置字段isEmailBinding的值，该字段的<br>
	* 字段名称 :邮箱是否绑定<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setisEmailBinding(String isEmailBinding) {
		this.isEmailBinding = isEmailBinding;
    }

	/**
	* 获取字段isMobileNOBinding的值，该字段的<br>
	* 字段名称 :手机是否绑定<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getisMobileNOBinding() {
		return isMobileNOBinding;
	}

	/**
	* 设置字段isMobileNOBinding的值，该字段的<br>
	* 字段名称 :手机是否绑定<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setisMobileNOBinding(String isMobileNOBinding) {
		this.isMobileNOBinding = isMobileNOBinding;
    }

	/**
	* 获取字段currentValidatePoint的值，该字段的<br>
	* 字段名称 :当前可用积分<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getcurrentValidatePoint() {
		if(currentValidatePoint==null){return 0;}
		return currentValidatePoint.intValue();
	}

	/**
	* 设置字段currentValidatePoint的值，该字段的<br>
	* 字段名称 :当前可用积分<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcurrentValidatePoint(int currentValidatePoint) {
		this.currentValidatePoint = new Integer(currentValidatePoint);
    }

	/**
	* 设置字段currentValidatePoint的值，该字段的<br>
	* 字段名称 :当前可用积分<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcurrentValidatePoint(String currentValidatePoint) {
		if (currentValidatePoint == null){
			this.currentValidatePoint = null;
			return;
		}
		this.currentValidatePoint = new Integer(currentValidatePoint);
    }

	/**
	* 获取字段pictruePath的值，该字段的<br>
	* 字段名称 :图片路径<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getpictruePath() {
		return pictruePath;
	}

	/**
	* 设置字段pictruePath的值，该字段的<br>
	* 字段名称 :图片路径<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpictruePath(String pictruePath) {
		this.pictruePath = pictruePath;
    }

	/**
	* 获取字段usedPoint的值，该字段的<br>
	* 字段名称 :已用积分<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getusedPoint() {
		if(usedPoint==null){return 0;}
		return usedPoint.intValue();
	}

	/**
	* 设置字段usedPoint的值，该字段的<br>
	* 字段名称 :已用积分<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setusedPoint(int usedPoint) {
		this.usedPoint = new Integer(usedPoint);
    }

	/**
	* 设置字段usedPoint的值，该字段的<br>
	* 字段名称 :已用积分<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setusedPoint(String usedPoint) {
		if (usedPoint == null){
			this.usedPoint = null;
			return;
		}
		this.usedPoint = new Integer(usedPoint);
    }

	/**
	* 获取字段fullDegree的值，该字段的<br>
	* 字段名称 :信息完整度<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getfullDegree() {
		return fullDegree;
	}

	/**
	* 设置字段fullDegree的值，该字段的<br>
	* 字段名称 :信息完整度<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setfullDegree(String fullDegree) {
		this.fullDegree = fullDegree;
    }

	/**
	* 获取字段retrieveEmailSendDate的值，该字段的<br>
	* 字段名称 :找回邮件发送时间 用于判断邮件有效期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getretrieveEmailSendDate() {
		return retrieveEmailSendDate;
	}

	/**
	* 设置字段retrieveEmailSendDate的值，该字段的<br>
	* 字段名称 :找回邮件发送时间 用于判断邮件有效期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setretrieveEmailSendDate(Date retrieveEmailSendDate) {
		this.retrieveEmailSendDate = retrieveEmailSendDate;
    }

	/**
	* 获取字段verifyEmailSendDate的值，该字段的<br>
	* 字段名称 :验证邮件发送时间 用于判断邮件有效期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getverifyEmailSendDate() {
		return verifyEmailSendDate;
	}

	/**
	* 设置字段verifyEmailSendDate的值，该字段的<br>
	* 字段名称 :验证邮件发送时间 用于判断邮件有效期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setverifyEmailSendDate(Date verifyEmailSendDate) {
		this.verifyEmailSendDate = verifyEmailSendDate;
    }

	/**
	* 获取字段retrieveEmailvalid的值，该字段的<br>
	* 字段名称 :找回密码有效性连接不能超过两次<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getretrieveEmailvalid() {
		return retrieveEmailvalid;
	}

	/**
	* 设置字段retrieveEmailvalid的值，该字段的<br>
	* 字段名称 :找回密码有效性连接不能超过两次<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setretrieveEmailvalid(String retrieveEmailvalid) {
		this.retrieveEmailvalid = retrieveEmailvalid;
    }

	/**
	* 获取字段headPicPath的值，该字段的<br>
	* 字段名称 :headPicPath<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getheadPicPath() {
		return headPicPath;
	}

	/**
	* 设置字段headPicPath的值，该字段的<br>
	* 字段名称 :headPicPath<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setheadPicPath(String headPicPath) {
		this.headPicPath = headPicPath;
    }

	/**
	* 获取字段mBindInfoForLogin_id的值，该字段的<br>
	* 字段名称 :mBindInfoForLogin_id<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getmBindInfoForLogin_id() {
		return mBindInfoForLogin_id;
	}

	/**
	* 设置字段mBindInfoForLogin_id的值，该字段的<br>
	* 字段名称 :mBindInfoForLogin_id<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmBindInfoForLogin_id(String mBindInfoForLogin_id) {
		this.mBindInfoForLogin_id = mBindInfoForLogin_id;
    }
	
	/**
	* 获取字段fromWap的值，该字段的<br>
	* 字段名称 :fromWap<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getFromWap() {
		return fromWap;
	}
	
	/**
	* 设置字段fromWap的值，该字段的<br>
	* 字段名称 :fromWap<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setFromWap(String fromWap) {
		this.fromWap = fromWap;
	}

	/**
	* 获取字段IsfirstMC的值，该字段的<br>
	* 字段名称 :IsfirstMC<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getIsfirstMC() {
		return isfirstMC;
	}

	/**
	* 设置字段IsfirstMC的值，该字段的<br>
	* 字段名称 :IsfirstMC<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setIsfirstMC(String isfirstMC) {
		this.isfirstMC = isfirstMC;
	}
	/**
	* 获取字段hasUpdate的值，该字段的<br>
	* 字段名称 :hasUpdate<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getHasUpdate() {
		return hasUpdate;
	}
	/**
	* 设置字段hasUpdate的值，该字段的<br>
	* 字段名称 :hasUpdate<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setHasUpdate(String hasUpdate) {
		this.hasUpdate = hasUpdate;
	}
	
	/**
	* 获取字段preLoginPoints的值，该字段的<br>
	* 字段名称 :上次登录积分<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getpreLoginPoints() {
		if(preLoginPoints==null){return 0;}
		return preLoginPoints.intValue();
	}

	/**
	* 设置字段preLoginPoints的值，该字段的<br>
	* 字段名称 :上次登录积分<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpreLoginPoints(int preLoginPoints) {
		this.preLoginPoints = new Integer(preLoginPoints);
    }

	/**
	* 获取字段recommendMemId的值，该字段的<br>
	* 字段名称 :recommendMemId<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecommendMemId() {
		return recommendMemId;
	}
	/**
	* 设置字段recommendMemId的值，该字段的<br>
	* 字段名称 :recommendMemId<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecommendMemId(String recommendMemId) {
		this.recommendMemId = recommendMemId;
	}
	
	/**
	* 获取字段recommendFlag的值，该字段的<br>
	* 字段名称 :recommendFlag<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecommendFlag() {
		return recommendFlag;
	}
	
	/**
	* 设置字段recommendFlag的值，该字段的<br>
	* 字段名称 :recommendFlag<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecommendFlag(String recommendFlag) {
		this.recommendFlag = recommendFlag;
	}
	
	/**
	* 获取字段recommendRegPoints的值，该字段的<br>
	* 字段名称 :推荐好友注册得积分数<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getrecommendRegPoints() {
		if(recommendRegPoints==null){return 0;}
		return recommendRegPoints.intValue();
	}

	/**
	* 设置字段recommendRegPoints的值，该字段的<br>
	* 字段名称 :推荐好友注册得积分数<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecommendRegPoints(int recommendRegPoints) {
		this.recommendRegPoints = new Integer(recommendRegPoints);
    }
	
	/**
	* 获取字段recommendBuyPoints的值，该字段的<br>
	* 字段名称 :推荐好友购买得积分数<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getrecommendBuyPoints() {
		if(recommendBuyPoints==null){return 0;}
		return recommendBuyPoints.intValue();
	}

	/**
	* 设置字段recommendBuyPoints的值，该字段的<br>
	* 字段名称 :推荐好友购买得积分数<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecommendBuyPoints(int recommendBuyPoints) {
		this.recommendBuyPoints = new Integer(recommendBuyPoints);
    }
	
	/**
	* 获取字段overduePoints的值，该字段的<br>
	* 字段名称 :过期的积分数<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getoverduePoints() {
		if(overduePoints==null){return 0;}
		return overduePoints.intValue();
	}

	/**
	* 设置字段overduePoints的值，该字段的<br>
	* 字段名称 :过期的积分数<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setoverduePoints(int overduePoints) {
		this.overduePoints = new Integer(overduePoints);
    }
	
	/**
	* 获取字段aboutToExpirePoints的值，该字段的<br>
	* 字段名称 :即将过期的积分数<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getaboutToExpirePoints() {
		if(aboutToExpirePoints==null){return 0;}
		return aboutToExpirePoints.intValue();
	}

	/**
	* 设置字段aboutToExpirePoints的值，该字段的<br>
	* 字段名称 :即将过期的积分数<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setaboutToExpirePoints(int aboutToExpirePoints) {
		this.aboutToExpirePoints = new Integer(aboutToExpirePoints);
    }
	
	/**
	* 获取字段aboutToExpireDate的值，该字段的<br>
	* 字段名称 :过期日期<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getaboutToExpireDate() {
		return aboutToExpireDate;
	}

	/**
	* 设置字段aboutToExpireDate的值，该字段的<br>
	* 字段名称 :过期日期<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setaboutToExpireDate(String aboutToExpireDate) {
		this.aboutToExpireDate = aboutToExpireDate;
    }
	
	/**
	* 获取字段grade的值，该字段的<br>
	* 字段名称 :会员等级<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getgrade() {
		return grade;
	}

	/**
	* 设置字段grade的值，该字段的<br>
	* 字段名称 :会员等级<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setgrade(String grade) {
		this.grade = grade;
    }
	
	/**
	* 获取字段consumeAmount的值，该字段的<br>
	* 字段名称 :消费金额<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getconsumeAmount() {
		return consumeAmount;
	}

	/**
	* 设置字段consumeAmount的值，该字段的<br>
	* 字段名称 :消费金额<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setconsumeAmount(String consumeAmount) {
		this.consumeAmount = consumeAmount;
    }
	
	/**
	* 获取字段buyCount的值，该字段的<br>
	* 字段名称 :购买次数<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getbuyCount() {
		if(buyCount==null){return 0;}
		return buyCount.intValue();
	}

	/**
	* 设置字段buyCount的值，该字段的<br>
	* 字段名称 :购买次数<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setbuyCount(int buyCount) {
		this.buyCount = new Integer(buyCount);
    }
	
	/**
	* 获取字段loginDateAfterUngrade的值，该字段的<br>
	* 字段名称 :升级后初次登录日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getloginDateAfterUngrade() {
		return loginDateAfterUngrade;
	}

	/**
	* 设置字段loginDateAfterUngrade的值，该字段的<br>
	* 字段名称 :升级后初次登录日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setloginDateAfterUngrade(Date loginDateAfterUngrade) {
		this.loginDateAfterUngrade = loginDateAfterUngrade;
    }
	
	/**
	* 获取字段isBuy的值，该字段的<br>
	* 字段名称 :是否购买<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getisBuy() {
		return isBuy;
	}

	/**
	* 设置字段isBuy的值，该字段的<br>
	* 字段名称 :是否购买<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setisBuy(String isBuy) {
		this.isBuy = isBuy;
    }
	
	/**
	* 获取字段accuEndDate的值，该字段的<br>
	* 字段名称 :统计会员有效订单数截止时间<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getaccuEndDate() {
		return accuEndDate;
	}

	/**
	* 设置字段accuEndDate的值，该字段的<br>
	* 字段名称 :统计会员有效订单数截止时间<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setaccuEndDate(String accuEndDate) {
		this.accuEndDate = accuEndDate;
    }
	
	/**
	* 获取字段birthYear的值，该字段的<br>
	* 字段名称 :已享受生日月最新年份<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getbirthYear() {
		return birthYear;
	}

	/**
	* 设置字段birthYear的值，该字段的<br>
	* 字段名称 :已享受生日月最新年份<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setbirthYear(String birthYear) {
		this.birthYear = birthYear;
    }
	
	/**
	* 获取字段vipFlag的值，该字段的<br>
	* 字段名称 :vip标识<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getvipFlag() {
		return vipFlag;
	}

	/**
	* 设置字段vipFlag的值，该字段的<br>
	* 字段名称 :vip标识<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setvipFlag(String vipFlag) {
		this.vipFlag = vipFlag;
    }
    
	/**
	* 获取字段version的值，该字段的<br>
	* 字段名称 :版本号<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getversion() {
		return version;
	}

	/**
	* 设置字段version的值，该字段的<br>
	* 字段名称 :版本号<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setversion(String version) {
		this.version = version;
    }
}