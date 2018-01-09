package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：分支机构保单信息<br>
 * 表代码：LineTeamPolicyInfo<br>
 * 表主键：id<br>
 */
public class LineTeamPolicyInfoSchema extends Schema {
	private String id;

	private String branchInnercode;

	private String companyCode;

	private String riskType;

	private String prem;

	private String policyNo;

	private String agentRate;

	private String startDate;

	private String insureDate;

	private String customName;

	private String customMobileNo;

	private String customIdentityType;

	private String customIdentityId;

	private String agentName;

	private String agentMobileNo;

	private String agentPrice;

	private Date cancelTime;

	private String plateNumber;

	private String status;

	private String remark1;

	private String remark2;

	private String remark3;

	private String userBranchInnercode;

	private Date createDate;

	private String createUser;

	private Date modifyDate;

	private String modifyUser;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("branchInnercode", DataColumn.STRING, 1, 100 , 0 , false , false),
		new SchemaColumn("companyCode", DataColumn.STRING, 2, 20 , 0 , false , false),
		new SchemaColumn("riskType", DataColumn.STRING, 3, 100 , 0 , false , false),
		new SchemaColumn("prem", DataColumn.STRING, 4, 10 , 0 , false , false),
		new SchemaColumn("policyNo", DataColumn.STRING, 5, 100 , 0 , false , false),
		new SchemaColumn("agentRate", DataColumn.STRING, 6, 10 , 0 , false , false),
		new SchemaColumn("startDate", DataColumn.STRING, 7, 20 , 0 , false , false),
		new SchemaColumn("insureDate", DataColumn.STRING, 8, 20 , 0 , false , false),
		new SchemaColumn("customName", DataColumn.STRING, 9, 100 , 0 , false , false),
		new SchemaColumn("customMobileNo", DataColumn.STRING, 10, 11 , 0 , false , false),
		new SchemaColumn("customIdentityType", DataColumn.STRING, 11, 2 , 0 , false , false),
		new SchemaColumn("customIdentityId", DataColumn.STRING, 12, 100 , 0 , false , false),
		new SchemaColumn("agentName", DataColumn.STRING, 13, 100 , 0 , false , false),
		new SchemaColumn("agentMobileNo", DataColumn.STRING, 14, 11 , 0 , false , false),
		new SchemaColumn("agentPrice", DataColumn.STRING, 15, 10 , 0 , false , false),
		new SchemaColumn("cancelTime", DataColumn.DATETIME, 16, 0 , 0 , false , false),
		new SchemaColumn("plateNumber", DataColumn.STRING, 17, 100 , 0 , false , false),
		new SchemaColumn("status", DataColumn.STRING, 18, 2 , 0 , false , false),
		new SchemaColumn("remark1", DataColumn.STRING, 19, 255 , 0 , false , false),
		new SchemaColumn("remark2", DataColumn.STRING, 20, 255 , 0 , false , false),
		new SchemaColumn("remark3", DataColumn.STRING, 21, 255 , 0 , false , false),
		new SchemaColumn("userBranchInnercode", DataColumn.STRING, 22, 100 , 0 , false , false),
		new SchemaColumn("createDate", DataColumn.DATETIME, 23, 0 , 0 , false , false),
		new SchemaColumn("createUser", DataColumn.STRING, 24, 100 , 0 , false , false),
		new SchemaColumn("modifyDate", DataColumn.DATETIME, 25, 0 , 0 , false , false),
		new SchemaColumn("modifyUser", DataColumn.STRING, 26, 100 , 0 , false , false)
	};

	public static final String _TableCode = "LineTeamPolicyInfo";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into LineTeamPolicyInfo values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update LineTeamPolicyInfo set id=?,branchInnercode=?,companyCode=?,riskType=?,prem=?,policyNo=?,agentRate=?,startDate=?,insureDate=?,customName=?,customMobileNo=?,customIdentityType=?,customIdentityId=?,agentName=?,agentMobileNo=?,agentPrice=?,cancelTime=?,plateNumber=?,status=?,remark1=?,remark2=?,remark3=?,userBranchInnercode=?,createDate=?,createUser=?,modifyDate=?,modifyUser=? where id=?";

	protected static final String _DeleteSQL = "delete from LineTeamPolicyInfo  where id=?";

	protected static final String _FillAllSQL = "select * from LineTeamPolicyInfo  where id=?";

	public LineTeamPolicyInfoSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[27];
	}

	protected Schema newInstance(){
		return new LineTeamPolicyInfoSchema();
	}

	protected SchemaSet newSet(){
		return new LineTeamPolicyInfoSet();
	}

	public LineTeamPolicyInfoSet query() {
		return query(null, -1, -1);
	}

	public LineTeamPolicyInfoSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public LineTeamPolicyInfoSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public LineTeamPolicyInfoSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (LineTeamPolicyInfoSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){branchInnercode = (String)v;return;}
		if (i == 2){companyCode = (String)v;return;}
		if (i == 3){riskType = (String)v;return;}
		if (i == 4){prem = (String)v;return;}
		if (i == 5){policyNo = (String)v;return;}
		if (i == 6){agentRate = (String)v;return;}
		if (i == 7){startDate = (String)v;return;}
		if (i == 8){insureDate = (String)v;return;}
		if (i == 9){customName = (String)v;return;}
		if (i == 10){customMobileNo = (String)v;return;}
		if (i == 11){customIdentityType = (String)v;return;}
		if (i == 12){customIdentityId = (String)v;return;}
		if (i == 13){agentName = (String)v;return;}
		if (i == 14){agentMobileNo = (String)v;return;}
		if (i == 15){agentPrice = (String)v;return;}
		if (i == 16){cancelTime = (Date)v;return;}
		if (i == 17){plateNumber = (String)v;return;}
		if (i == 18){status = (String)v;return;}
		if (i == 19){remark1 = (String)v;return;}
		if (i == 20){remark2 = (String)v;return;}
		if (i == 21){remark3 = (String)v;return;}
		if (i == 22){userBranchInnercode = (String)v;return;}
		if (i == 23){createDate = (Date)v;return;}
		if (i == 24){createUser = (String)v;return;}
		if (i == 25){modifyDate = (Date)v;return;}
		if (i == 26){modifyUser = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return branchInnercode;}
		if (i == 2){return companyCode;}
		if (i == 3){return riskType;}
		if (i == 4){return prem;}
		if (i == 5){return policyNo;}
		if (i == 6){return agentRate;}
		if (i == 7){return startDate;}
		if (i == 8){return insureDate;}
		if (i == 9){return customName;}
		if (i == 10){return customMobileNo;}
		if (i == 11){return customIdentityType;}
		if (i == 12){return customIdentityId;}
		if (i == 13){return agentName;}
		if (i == 14){return agentMobileNo;}
		if (i == 15){return agentPrice;}
		if (i == 16){return cancelTime;}
		if (i == 17){return plateNumber;}
		if (i == 18){return status;}
		if (i == 19){return remark1;}
		if (i == 20){return remark2;}
		if (i == 21){return remark3;}
		if (i == 22){return userBranchInnercode;}
		if (i == 23){return createDate;}
		if (i == 24){return createUser;}
		if (i == 25){return modifyDate;}
		if (i == 26){return modifyUser;}
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
	* 获取字段branchInnercode的值，该字段的<br>
	* 字段名称 :机构编码<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getbranchInnercode() {
		return branchInnercode;
	}

	/**
	* 设置字段branchInnercode的值，该字段的<br>
	* 字段名称 :机构编码<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setbranchInnercode(String branchInnercode) {
		this.branchInnercode = branchInnercode;
    }

	/**
	* 获取字段companyCode的值，该字段的<br>
	* 字段名称 :保险公司编码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcompanyCode() {
		return companyCode;
	}

	/**
	* 设置字段companyCode的值，该字段的<br>
	* 字段名称 :保险公司编码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcompanyCode(String companyCode) {
		this.companyCode = companyCode;
    }

	/**
	* 获取字段riskType的值，该字段的<br>
	* 字段名称 :险种<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getriskType() {
		return riskType;
	}

	/**
	* 设置字段riskType的值，该字段的<br>
	* 字段名称 :险种<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setriskType(String riskType) {
		this.riskType = riskType;
    }

	/**
	* 获取字段prem的值，该字段的<br>
	* 字段名称 :保费<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprem() {
		return prem;
	}

	/**
	* 设置字段prem的值，该字段的<br>
	* 字段名称 :保费<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprem(String prem) {
		this.prem = prem;
    }

	/**
	* 获取字段policyNo的值，该字段的<br>
	* 字段名称 :保单号<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getpolicyNo() {
		return policyNo;
	}

	/**
	* 设置字段policyNo的值，该字段的<br>
	* 字段名称 :保单号<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpolicyNo(String policyNo) {
		this.policyNo = policyNo;
    }

	/**
	* 获取字段agentRate的值，该字段的<br>
	* 字段名称 :代理人手续费率<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getagentRate() {
		return agentRate;
	}

	/**
	* 设置字段agentRate的值，该字段的<br>
	* 字段名称 :代理人手续费率<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setagentRate(String agentRate) {
		this.agentRate = agentRate;
    }

	/**
	* 获取字段startDate的值，该字段的<br>
	* 字段名称 :保单起期<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getstartDate() {
		return startDate;
	}

	/**
	* 设置字段startDate的值，该字段的<br>
	* 字段名称 :保单起期<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setstartDate(String startDate) {
		this.startDate = startDate;
    }

	/**
	* 获取字段insureDate的值，该字段的<br>
	* 字段名称 :出单日期<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getinsureDate() {
		return insureDate;
	}

	/**
	* 设置字段insureDate的值，该字段的<br>
	* 字段名称 :出单日期<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setinsureDate(String insureDate) {
		this.insureDate = insureDate;
    }

	/**
	* 获取字段customName的值，该字段的<br>
	* 字段名称 :客户姓名<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcustomName() {
		return customName;
	}

	/**
	* 设置字段customName的值，该字段的<br>
	* 字段名称 :客户姓名<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcustomName(String customName) {
		this.customName = customName;
    }

	/**
	* 获取字段customMobileNo的值，该字段的<br>
	* 字段名称 :客户手机号<br>
	* 数据类型 :varchar(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcustomMobileNo() {
		return customMobileNo;
	}

	/**
	* 设置字段customMobileNo的值，该字段的<br>
	* 字段名称 :客户手机号<br>
	* 数据类型 :varchar(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcustomMobileNo(String customMobileNo) {
		this.customMobileNo = customMobileNo;
    }

	/**
	* 获取字段customIdentityType的值，该字段的<br>
	* 字段名称 :客户证件类型<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcustomIdentityType() {
		return customIdentityType;
	}

	/**
	* 设置字段customIdentityType的值，该字段的<br>
	* 字段名称 :客户证件类型<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcustomIdentityType(String customIdentityType) {
		this.customIdentityType = customIdentityType;
    }

	/**
	* 获取字段customIdentityId的值，该字段的<br>
	* 字段名称 :客户证件号<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcustomIdentityId() {
		return customIdentityId;
	}

	/**
	* 设置字段customIdentityId的值，该字段的<br>
	* 字段名称 :客户证件号<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcustomIdentityId(String customIdentityId) {
		this.customIdentityId = customIdentityId;
    }

	/**
	* 获取字段agentName的值，该字段的<br>
	* 字段名称 :代理人姓名<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getagentName() {
		return agentName;
	}

	/**
	* 设置字段agentName的值，该字段的<br>
	* 字段名称 :代理人姓名<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setagentName(String agentName) {
		this.agentName = agentName;
    }

	/**
	* 获取字段agentMobileNo的值，该字段的<br>
	* 字段名称 :代理人手机号<br>
	* 数据类型 :varchar(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getagentMobileNo() {
		return agentMobileNo;
	}

	/**
	* 设置字段agentMobileNo的值，该字段的<br>
	* 字段名称 :代理人手机号<br>
	* 数据类型 :varchar(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setagentMobileNo(String agentMobileNo) {
		this.agentMobileNo = agentMobileNo;
    }

	/**
	* 获取字段agentPrice的值，该字段的<br>
	* 字段名称 :代理人手续费<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getagentPrice() {
		return agentPrice;
	}

	/**
	* 设置字段agentPrice的值，该字段的<br>
	* 字段名称 :代理人手续费<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setagentPrice(String agentPrice) {
		this.agentPrice = agentPrice;
    }

	/**
	* 获取字段cancelTime的值，该字段的<br>
	* 字段名称 :撤单时间<br>
	* 数据类型 :date<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getcancelTime() {
		return cancelTime;
	}

	/**
	* 设置字段cancelTime的值，该字段的<br>
	* 字段名称 :撤单时间<br>
	* 数据类型 :date<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
    }

	/**
	* 获取字段plateNumber的值，该字段的<br>
	* 字段名称 :车牌<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getplateNumber() {
		return plateNumber;
	}

	/**
	* 设置字段plateNumber的值，该字段的<br>
	* 字段名称 :车牌<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setplateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
    }

	/**
	* 获取字段status的值，该字段的<br>
	* 字段名称 :状态<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getstatus() {
		return status;
	}

	/**
	* 设置字段status的值，该字段的<br>
	* 字段名称 :状态<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setstatus(String status) {
		this.status = status;
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
	* 获取字段userBranchInnercode的值，该字段的<br>
	* 字段名称 :用户所属机构<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getuserBranchInnercode() {
		return userBranchInnercode;
	}

	/**
	* 设置字段userBranchInnercode的值，该字段的<br>
	* 字段名称 :用户所属机构<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setuserBranchInnercode(String userBranchInnercode) {
		this.userBranchInnercode = userBranchInnercode;
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