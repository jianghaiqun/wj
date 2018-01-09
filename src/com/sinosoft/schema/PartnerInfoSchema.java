package com.sinosoft.schema;

import java.util.Date;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.orm.SchemaSet;

/**
 * 表名称：合作方新表<br>
 * 表代码：PartnerInfo<br>
 * 表主键：id<br>
 */
public class PartnerInfoSchema extends Schema {
	/**
	 * serialVersionUID:.
	 */
	private static final long serialVersionUID = 4246613178022534432L;

	private String id;

	private String partnerId;

	private String partnerName;

	private String partnerStatus;

	private String partnerKey;

	private String balance;

	private String remark;

	private String telphone;

	private String operUserId;

	private Date createTime;

	private Date updateTime;

	private String payType;

	private String channelSn;

	private String returnUrl;

	private String returnErrorUrl;

	private String bgReturnUrl;
	
	private String bgPolicyReturnUrl;
	
	private String bgCancelResultUrl;
	
	private String type;
	
	private String ftpPath;
	
	private String prop1;
	
	private String prop2;
	
	private String prop3;
	
	private String prop4; 

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 20 , 0 , true , true),
		new SchemaColumn("partnerId", DataColumn.STRING, 1, 20 , 0 , false , false),
		new SchemaColumn("partnerName", DataColumn.STRING, 2, 50 , 0 , false , false),
		new SchemaColumn("partnerStatus", DataColumn.STRING, 3, 1 , 0 , false , false),
		new SchemaColumn("partnerKey", DataColumn.STRING, 4, 50 , 0 , false , false),
		new SchemaColumn("balance", DataColumn.STRING, 5, 20 , 0 , false , false),
		new SchemaColumn("remark", DataColumn.STRING, 6, 150 , 0 , false , false),
		new SchemaColumn("telphone", DataColumn.STRING, 7, 15 , 0 , false , false),
		new SchemaColumn("operUserId", DataColumn.STRING, 8, 20 , 0 , false , false),
		new SchemaColumn("createTime", DataColumn.DATETIME, 9, 0 , 0 , false , false),
		new SchemaColumn("updateTime", DataColumn.DATETIME, 10, 0 , 0 , false , false),
		new SchemaColumn("payType", DataColumn.STRING, 11, 1 , 0 , false , false),
		new SchemaColumn("channelSn", DataColumn.STRING, 12, 20 , 0 , false , false),
		new SchemaColumn("returnUrl", DataColumn.STRING, 13, 255 , 0 , false , false),
		new SchemaColumn("returnErrorUrl", DataColumn.STRING, 14, 255 , 0 , false , false),
		new SchemaColumn("bgReturnUrl", DataColumn.STRING, 15, 255 , 0 , false , false),
		new SchemaColumn("bgPolicyReturnUrl", DataColumn.STRING, 16, 255 , 0 , false , false),
		new SchemaColumn("bgCancelResultUrl", DataColumn.STRING, 17, 255 , 0 , false , false),
		new SchemaColumn("type", DataColumn.STRING, 18, 2 , 0 , false , false),
		new SchemaColumn("ftpPath", DataColumn.STRING, 19, 100 , 0 , false , false),
		new SchemaColumn("prop1", DataColumn.STRING, 20, 20 , 0 , false , false),
		new SchemaColumn("prop2", DataColumn.STRING, 21, 20 , 0 , false , false),
		new SchemaColumn("prop3", DataColumn.STRING, 22, 100 , 0 , false , false),
		new SchemaColumn("prop4", DataColumn.STRING, 23, 100 , 0 , false , false)
	};

	public static final String _TableCode = "PartnerInfo";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into PartnerInfo values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update PartnerInfo set id=?,partnerId=?,partnerName=?,partnerStatus=?,partnerKey=?,balance=?,remark=?,telphone=?,operUserId=?,createTime=?,updateTime=?,payType=?,channelSn=?,returnUrl=?,returnErrorUrl=?,bgReturnUrl=?,bgPolicyReturnUrl=?,bgCancelResultUrl=?,type=?,ftpPath=?,prop1=?,prop2=?,prop3=?,prop4=? where id=?";

	protected static final String _DeleteSQL = "delete from PartnerInfo  where id=?";

	protected static final String _FillAllSQL = "select * from PartnerInfo  where id=?";

	public PartnerInfoSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[24];
	}

	protected Schema newInstance(){
		return new PartnerInfoSchema();
	}

	protected SchemaSet newSet(){
		return new PartnerInfoSet();
	}

	public PartnerInfoSet query() {
		return query(null, -1, -1);
	}

	public PartnerInfoSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public PartnerInfoSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public PartnerInfoSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (PartnerInfoSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){partnerId = (String)v;return;}
		if (i == 2){partnerName = (String)v;return;}
		if (i == 3){partnerStatus = (String)v;return;}
		if (i == 4){partnerKey = (String)v;return;}
		if (i == 5){balance = (String)v;return;}
		if (i == 6){remark = (String)v;return;}
		if (i == 7){telphone = (String)v;return;}
		if (i == 8){operUserId = (String)v;return;}
		if (i == 9){createTime = (Date)v;return;}
		if (i == 10){updateTime = (Date)v;return;}
		if (i == 11){payType = (String)v;return;}
		if (i == 12){channelSn = (String)v;return;}
		if (i == 13){returnUrl = (String)v;return;}
		if (i == 14){returnErrorUrl = (String)v;return;}
		if (i == 15){bgReturnUrl = (String)v;return;}
		if (i == 16){bgPolicyReturnUrl = (String)v;return;}
		if (i == 17){bgCancelResultUrl = (String)v;return;}
		if (i == 18){type = (String)v;return;}
		if (i == 19){ftpPath = (String)v;return;}
		if (i == 20){prop1 = (String)v;return;}
		if (i == 21){prop2 = (String)v;return;}
		if (i == 22){prop3 = (String)v;return;}
		if (i == 23){prop4 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return partnerId;}
		if (i == 2){return partnerName;}
		if (i == 3){return partnerStatus;}
		if (i == 4){return partnerKey;}
		if (i == 5){return balance;}
		if (i == 6){return remark;}
		if (i == 7){return telphone;}
		if (i == 8){return operUserId;}
		if (i == 9){return createTime;}
		if (i == 10){return updateTime;}
		if (i == 11){return payType;}
		if (i == 12){return channelSn;}
		if (i == 13){return returnUrl;}
		if (i == 14){return returnErrorUrl;}
		if (i == 15){return bgReturnUrl;}
		if (i == 16){return bgPolicyReturnUrl;}
		if (i == 17){return bgCancelResultUrl;}
		if (i == 18){return type;}
		if (i == 19){return ftpPath;}
		if (i == 20){return prop1;}
		if (i == 21){return prop2;}
		if (i == 22){return prop3;}
		if (i == 23){return prop4;}
		return null;
	}

	/**
	* 获取字段id的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getid() {
		return id;
	}

	/**
	* 设置字段id的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setid(String id) {
		this.id = id;
    }

	/**
	* 获取字段partnerId的值，该字段的<br>
	* 字段名称 :合作方id<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getpartnerId() {
		return partnerId;
	}

	/**
	* 设置字段partnerId的值，该字段的<br>
	* 字段名称 :合作方id<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpartnerId(String partnerId) {
		this.partnerId = partnerId;
    }

	/**
	* 获取字段partnerName的值，该字段的<br>
	* 字段名称 :合作方名称<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getpartnerName() {
		return partnerName;
	}

	/**
	* 设置字段partnerName的值，该字段的<br>
	* 字段名称 :合作方名称<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpartnerName(String partnerName) {
		this.partnerName = partnerName;
    }

	/**
	* 获取字段partnerStatus的值，该字段的<br>
	* 字段名称 :合作方状态<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getpartnerStatus() {
		return partnerStatus;
	}

	/**
	* 设置字段partnerStatus的值，该字段的<br>
	* 字段名称 :合作方状态<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpartnerStatus(String partnerStatus) {
		this.partnerStatus = partnerStatus;
    }

	/**
	* 获取字段partnerKey的值，该字段的<br>
	* 字段名称 :合作方私钥<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getpartnerKey() {
		return partnerKey;
	}

	/**
	* 设置字段partnerKey的值，该字段的<br>
	* 字段名称 :合作方私钥<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpartnerKey(String partnerKey) {
		this.partnerKey = partnerKey;
    }

	/**
	* 获取字段balance的值，该字段的<br>
	* 字段名称 :金额<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getbalance() {
		return balance;
	}

	/**
	* 设置字段balance的值，该字段的<br>
	* 字段名称 :金额<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setbalance(String balance) {
		this.balance = balance;
    }

	/**
	* 获取字段remark的值，该字段的<br>
	* 字段名称 :备注<br>
	* 数据类型 :varchar(150)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getremark() {
		return remark;
	}

	/**
	* 设置字段remark的值，该字段的<br>
	* 字段名称 :备注<br>
	* 数据类型 :varchar(150)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setremark(String remark) {
		this.remark = remark;
    }

	/**
	* 获取字段telphone的值，该字段的<br>
	* 字段名称 :联系电话<br>
	* 数据类型 :varchar(15)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String gettelphone() {
		return telphone;
	}

	/**
	* 设置字段telphone的值，该字段的<br>
	* 字段名称 :联系电话<br>
	* 数据类型 :varchar(15)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void settelphone(String telphone) {
		this.telphone = telphone;
    }

	/**
	* 获取字段operUserId的值，该字段的<br>
	* 字段名称 :操作人<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getoperUserId() {
		return operUserId;
	}

	/**
	* 设置字段operUserId的值，该字段的<br>
	* 字段名称 :操作人<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setoperUserId(String operUserId) {
		this.operUserId = operUserId;
    }

	/**
	* 获取字段createTime的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getcreateTime() {
		return createTime;
	}

	/**
	* 设置字段createTime的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcreateTime(Date createTime) {
		this.createTime = createTime;
    }

	/**
	* 获取字段updateTime的值，该字段的<br>
	* 字段名称 :更新时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getupdateTime() {
		return updateTime;
	}

	/**
	* 设置字段updateTime的值，该字段的<br>
	* 字段名称 :更新时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setupdateTime(Date updateTime) {
		this.updateTime = updateTime;
    }

	/**
	* 获取字段payType的值，该字段的<br>
	* 字段名称 :支付方式<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getpayType() {
		return payType;
	}

	/**
	* 设置字段payType的值，该字段的<br>
	* 字段名称 :支付方式<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpayType(String payType) {
		this.payType = payType;
    }

	/**
	* 获取字段channelSn的值，该字段的<br>
	* 字段名称 :渠道编号<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getchannelSn() {
		return channelSn;
	}

	/**
	* 设置字段channelSn的值，该字段的<br>
	* 字段名称 :渠道编号<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setchannelSn(String channelSn) {
		this.channelSn = channelSn;
    }

	/**
	* 获取字段returnUrl的值，该字段的<br>
	* 字段名称 :前台成功回调地址<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getreturnUrl() {
		return returnUrl;
	}

	/**
	* 设置字段returnUrl的值，该字段的<br>
	* 字段名称 :前台成功回调地址<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setreturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
    }

	/**
	* 获取字段returnErrorUrl的值，该字段的<br>
	* 字段名称 :前台失败回调地址<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getreturnErrorUrl() {
		return returnErrorUrl;
	}

	/**
	* 设置字段returnErrorUrl的值，该字段的<br>
	* 字段名称 :前台失败回调地址<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setreturnErrorUrl(String returnErrorUrl) {
		this.returnErrorUrl = returnErrorUrl;
    }

	/**
	* 获取字段bgReturnUrl的值，该字段的<br>
	* 字段名称 :后台回调地址<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getbgReturnUrl() {
		return bgReturnUrl;
	}

	/**
	* 设置字段bgReturnUrl的值，该字段的<br>
	* 字段名称 :后台回调地址<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setbgReturnUrl(String bgReturnUrl) {
		this.bgReturnUrl = bgReturnUrl;
    }
	
	public String getBgPolicyReturnUrl() {
	
		return bgPolicyReturnUrl;
	}

	public void setBgPolicyReturnUrl(String bgPolicyReturnUrl) {
	
		this.bgPolicyReturnUrl = bgPolicyReturnUrl;
	}

	/**
	* 获取字段bgCancelResultUrl的值，该字段的<br>
	* 字段名称 :退保结果回调地址<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getBgCancelResultUrl() {
		return bgCancelResultUrl;
	}
	/**
	* 设置字段bgCancelResultUrl的值，该字段的<br>
	* 字段名称 :退保结果回调地址<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setBgCancelResultUrl(String bgCancelResultUrl) {
		this.bgCancelResultUrl = bgCancelResultUrl;
	}
	
	/**
	* 获取字段type的值，该字段的<br>
	* 字段名称 :退保结果回调地址<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getType() {
		return type;
	}

	/**
	* 设置字段type的值，该字段的<br>
	* 字段名称 :退保结果回调地址<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setType(String type) {
		this.type = type;
	}

	/**
	* 获取字段ftpPath的值，该字段的<br>
	* 字段名称 :退保结果回调地址<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getFtpPath() {
		return ftpPath;
	}
	
	/**
	* 设置字段ftpPath的值，该字段的<br>
	* 字段名称 :退保结果回调地址<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setFtpPath(String ftpPath) {
		this.ftpPath = ftpPath;
	}

	/**
	* 获取字段prop1的值，该字段的<br>
	* 字段名称 :退保结果回调地址<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return prop1;
	}
	
	/**
	* 设置字段prop1的值，该字段的<br>
	* 字段名称 :退保结果回调地址<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.prop1 = prop1;
	}

	/**
	* 获取字段prop2的值，该字段的<br>
	* 字段名称 :退保结果回调地址<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return prop2;
	}

	/**
	* 设置字段prop2的值，该字段的<br>
	* 字段名称 :退保结果回调地址<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.prop2 = prop2;
	}

	/**
	* 获取字段prop3的值，该字段的<br>
	* 字段名称 :退保结果回调地址<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp3() {
		return prop3;
	}
	
	/**
	* 设置字段prop3的值，该字段的<br>
	* 字段名称 :退保结果回调地址<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp3(String prop3) {
		this.prop3 = prop3;
	}

	/**
	* 获取字段prop4的值，该字段的<br>
	* 字段名称 :退保结果回调地址<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp4() {
		return prop4;
	}
	
	/**
	* 设置字段prop4的值，该字段的<br>
	* 字段名称 :退保结果回调地址<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp4(String prop4) {
		this.prop4 = prop4;
	}
}