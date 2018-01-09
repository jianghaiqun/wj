package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：银行卡绑定表<br>
 * 表代码：SDCardBind<br>
 * 表主键：Id<br>
 */
public class SDCardBindSchema extends Schema {
	private String Id;

	private String MemberId;

	private String Channelsn;

	private String AccName;

	private String BankAccNo;

	private String BankCode;

	private String BussNo;

	private String ValidateStatus;

	private Date ValidatedDate;

	private String ValidateMoney;

	private String BindFlag;

	private String SendCheckCount;

	private String CheckCount;

	private Date CreateDate;

	private Date ModifyDate;

	private String UseFlag;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	private String Prop4;

	private String Prop5;

	private String Prop6;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("Id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("MemberId", DataColumn.STRING, 1, 50 , 0 , false , false),
		new SchemaColumn("Channelsn", DataColumn.STRING, 2, 50 , 0 , false , false),
		new SchemaColumn("AccName", DataColumn.STRING, 3, 50 , 0 , false , false),
		new SchemaColumn("BankAccNo", DataColumn.STRING, 4, 32 , 0 , false , false),
		new SchemaColumn("BankCode", DataColumn.STRING, 5, 20 , 0 , false , false),
		new SchemaColumn("BussNo", DataColumn.STRING, 6, 32 , 0 , false , false),
		new SchemaColumn("ValidateStatus", DataColumn.STRING, 7, 2 , 0 , false , false),
		new SchemaColumn("ValidatedDate", DataColumn.DATETIME, 8, 0 , 0 , false , false),
		new SchemaColumn("ValidateMoney", DataColumn.STRING, 9, 4 , 0 , false , false),
		new SchemaColumn("BindFlag", DataColumn.STRING, 10, 2 , 0 , false , false),
		new SchemaColumn("SendCheckCount", DataColumn.STRING, 11, 2 , 0 , false , false),
		new SchemaColumn("CheckCount", DataColumn.STRING, 12, 2 , 0 , false , false),
		new SchemaColumn("CreateDate", DataColumn.DATETIME, 13, 0 , 0 , false , false),
		new SchemaColumn("ModifyDate", DataColumn.DATETIME, 14, 0 , 0 , false , false),
		new SchemaColumn("UseFlag", DataColumn.STRING, 15, 2 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 16, 100 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 17, 100 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 18, 100 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 19, 100 , 0 , false , false),
		new SchemaColumn("Prop5", DataColumn.STRING, 20, 100 , 0 , false , false),
		new SchemaColumn("Prop6", DataColumn.STRING, 21, 500 , 0 , false , false)
	};

	public static final String _TableCode = "SDCardBind";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into SDCardBind values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update SDCardBind set Id=?,MemberId=?,Channelsn=?,AccName=?,BankAccNo=?,BankCode=?,BussNo=?,ValidateStatus=?,ValidatedDate=?,ValidateMoney=?,BindFlag=?,SendCheckCount=?,CheckCount=?,CreateDate=?,ModifyDate=?,UseFlag=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,Prop5=?,Prop6=? where Id=?";

	protected static final String _DeleteSQL = "delete from SDCardBind  where Id=?";

	protected static final String _FillAllSQL = "select * from SDCardBind  where Id=?";

	public SDCardBindSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[22];
	}

	protected Schema newInstance(){
		return new SDCardBindSchema();
	}

	protected SchemaSet newSet(){
		return new SDCardBindSet();
	}

	public SDCardBindSet query() {
		return query(null, -1, -1);
	}

	public SDCardBindSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public SDCardBindSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public SDCardBindSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (SDCardBindSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){Id = (String)v;return;}
		if (i == 1){MemberId = (String)v;return;}
		if (i == 2){Channelsn = (String)v;return;}
		if (i == 3){AccName = (String)v;return;}
		if (i == 4){BankAccNo = (String)v;return;}
		if (i == 5){BankCode = (String)v;return;}
		if (i == 6){BussNo = (String)v;return;}
		if (i == 7){ValidateStatus = (String)v;return;}
		if (i == 8){ValidatedDate = (Date)v;return;}
		if (i == 9){ValidateMoney = (String)v;return;}
		if (i == 10){BindFlag = (String)v;return;}
		if (i == 11){SendCheckCount = (String)v;return;}
		if (i == 12){CheckCount = (String)v;return;}
		if (i == 13){CreateDate = (Date)v;return;}
		if (i == 14){ModifyDate = (Date)v;return;}
		if (i == 15){UseFlag = (String)v;return;}
		if (i == 16){Prop1 = (String)v;return;}
		if (i == 17){Prop2 = (String)v;return;}
		if (i == 18){Prop3 = (String)v;return;}
		if (i == 19){Prop4 = (String)v;return;}
		if (i == 20){Prop5 = (String)v;return;}
		if (i == 21){Prop6 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return Id;}
		if (i == 1){return MemberId;}
		if (i == 2){return Channelsn;}
		if (i == 3){return AccName;}
		if (i == 4){return BankAccNo;}
		if (i == 5){return BankCode;}
		if (i == 6){return BussNo;}
		if (i == 7){return ValidateStatus;}
		if (i == 8){return ValidatedDate;}
		if (i == 9){return ValidateMoney;}
		if (i == 10){return BindFlag;}
		if (i == 11){return SendCheckCount;}
		if (i == 12){return CheckCount;}
		if (i == 13){return CreateDate;}
		if (i == 14){return ModifyDate;}
		if (i == 15){return UseFlag;}
		if (i == 16){return Prop1;}
		if (i == 17){return Prop2;}
		if (i == 18){return Prop3;}
		if (i == 19){return Prop4;}
		if (i == 20){return Prop5;}
		if (i == 21){return Prop6;}
		return null;
	}

	/**
	* 获取字段Id的值，该字段的<br>
	* 字段名称 :Id<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getId() {
		return Id;
	}

	/**
	* 设置字段Id的值，该字段的<br>
	* 字段名称 :Id<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setId(String id) {
		this.Id = id;
    }

	/**
	* 获取字段MemberId的值，该字段的<br>
	* 字段名称 :会员号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMemberId() {
		return MemberId;
	}

	/**
	* 设置字段MemberId的值，该字段的<br>
	* 字段名称 :会员号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMemberId(String memberId) {
		this.MemberId = memberId;
    }

	/**
	* 获取字段Channelsn的值，该字段的<br>
	* 字段名称 :渠道<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getChannelsn() {
		return Channelsn;
	}

	/**
	* 设置字段Channelsn的值，该字段的<br>
	* 字段名称 :渠道<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setChannelsn(String channelsn) {
		this.Channelsn = channelsn;
    }

	/**
	* 获取字段AccName的值，该字段的<br>
	* 字段名称 :银行账户名<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAccName() {
		return AccName;
	}

	/**
	* 设置字段AccName的值，该字段的<br>
	* 字段名称 :银行账户名<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAccName(String accName) {
		this.AccName = accName;
    }

	/**
	* 获取字段BankAccNo的值，该字段的<br>
	* 字段名称 :银行账号<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getBankAccNo() {
		return BankAccNo;
	}

	/**
	* 设置字段BankAccNo的值，该字段的<br>
	* 字段名称 :银行账号<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setBankAccNo(String bankAccNo) {
		this.BankAccNo = bankAccNo;
    }

	/**
	* 获取字段BankCode的值，该字段的<br>
	* 字段名称 :银行编码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getBankCode() {
		return BankCode;
	}

	/**
	* 设置字段BankCode的值，该字段的<br>
	* 字段名称 :银行编码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setBankCode(String bankCode) {
		this.BankCode = bankCode;
    }

	/**
	* 获取字段BussNo的值，该字段的<br>
	* 字段名称 :支付验证编码<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getBussNo() {
		return BussNo;
	}

	/**
	* 设置字段BussNo的值，该字段的<br>
	* 字段名称 :支付验证编码<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setBussNo(String bussNo) {
		this.BussNo = bussNo;
    }

	/**
	* 获取字段ValidateStatus的值，该字段的<br>
	* 字段名称 :支付验证状态<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getValidateStatus() {
		return ValidateStatus;
	}

	/**
	* 设置字段ValidateStatus的值，该字段的<br>
	* 字段名称 :支付验证状态<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setValidateStatus(String validateStatus) {
		this.ValidateStatus = validateStatus;
    }

	/**
	* 获取字段ValidatedDate的值，该字段的<br>
	* 字段名称 :支付验证时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getValidatedDate() {
		return ValidatedDate;
	}

	/**
	* 设置字段ValidatedDate的值，该字段的<br>
	* 字段名称 :支付验证时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setValidatedDate(Date validatedDate) {
		this.ValidatedDate = validatedDate;
    }

	/**
	* 获取字段ValidateMoney的值，该字段的<br>
	* 字段名称 :验证金额<br>
	* 数据类型 :varchar(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getValidateMoney() {
		return ValidateMoney;
	}

	/**
	* 设置字段ValidateMoney的值，该字段的<br>
	* 字段名称 :验证金额<br>
	* 数据类型 :varchar(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setValidateMoney(String validateMoney) {
		this.ValidateMoney = validateMoney;
    }

	/**
	* 获取字段BindFlag的值，该字段的<br>
	* 字段名称 :绑定区分<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getBindFlag() {
		return BindFlag;
	}

	/**
	* 设置字段BindFlag的值，该字段的<br>
	* 字段名称 :绑定区分<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setBindFlag(String bindFlag) {
		this.BindFlag = bindFlag;
    }

	/**
	* 获取字段SendCheckCount的值，该字段的<br>
	* 字段名称 :发送验证次数<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSendCheckCount() {
		return SendCheckCount;
	}

	/**
	* 设置字段SendCheckCount的值，该字段的<br>
	* 字段名称 :发送验证次数<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSendCheckCount(String sendCheckCount) {
		this.SendCheckCount = sendCheckCount;
    }

	/**
	* 获取字段CheckCount的值，该字段的<br>
	* 字段名称 :验证次数<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCheckCount() {
		return CheckCount;
	}

	/**
	* 设置字段CheckCount的值，该字段的<br>
	* 字段名称 :验证次数<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCheckCount(String checkCount) {
		this.CheckCount = checkCount;
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
	* 获取字段UseFlag的值，该字段的<br>
	* 字段名称 :是否使用<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getUseFlag() {
		return UseFlag;
	}

	/**
	* 设置字段UseFlag的值，该字段的<br>
	* 字段名称 :是否使用<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setUseFlag(String useFlag) {
		this.UseFlag = useFlag;
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

	/**
	* 获取字段Prop4的值，该字段的<br>
	* 字段名称 :Prop4<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp4() {
		return Prop4;
	}

	/**
	* 设置字段Prop4的值，该字段的<br>
	* 字段名称 :Prop4<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp4(String prop4) {
		this.Prop4 = prop4;
    }

	/**
	* 获取字段Prop5的值，该字段的<br>
	* 字段名称 :Prop5<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp5() {
		return Prop5;
	}

	/**
	* 设置字段Prop5的值，该字段的<br>
	* 字段名称 :Prop5<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp5(String prop5) {
		this.Prop5 = prop5;
    }

	/**
	* 获取字段Prop6的值，该字段的<br>
	* 字段名称 :Prop6<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp6() {
		return Prop6;
	}

	/**
	* 设置字段Prop6的值，该字段的<br>
	* 字段名称 :Prop6<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp6(String prop6) {
		this.Prop6 = prop6;
    }

}