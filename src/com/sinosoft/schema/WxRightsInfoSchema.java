package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：微信维权信息表<br>
 * 表代码：WxRightsInfo<br>
 * 表主键：ID<br>
 */
public class WxRightsInfoSchema extends Schema {
	private String ID;

	private String AppSignature;

	private String OpenId;

	private String AppId;

	private String TimeStamp;

	private String MsgType;

	private String FeedBackId;

	private String TransId;

	private String Reason;

	private String Solution;

	private String ExtInfo;

	private String SignMethod;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	private String Prop4;

	private Date CreateDate;

	private Date ModifyDate;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.STRING, 0, 200 , 0 , true , true),
		new SchemaColumn("AppSignature", DataColumn.STRING, 1, 200 , 0 , false , false),
		new SchemaColumn("OpenId", DataColumn.STRING, 2, 200 , 0 , false , false),
		new SchemaColumn("AppId", DataColumn.STRING, 3, 200 , 0 , false , false),
		new SchemaColumn("TimeStamp", DataColumn.STRING, 4, 50 , 0 , false , false),
		new SchemaColumn("MsgType", DataColumn.STRING, 5, 10 , 0 , false , false),
		new SchemaColumn("FeedBackId", DataColumn.STRING, 6, 100 , 0 , false , false),
		new SchemaColumn("TransId", DataColumn.STRING, 7, 100 , 0 , false , false),
		new SchemaColumn("Reason", DataColumn.CLOB, 8, 0 , 0 , false , false),
		new SchemaColumn("Solution", DataColumn.CLOB, 9, 0 , 0 , false , false),
		new SchemaColumn("ExtInfo", DataColumn.CLOB, 10, 0 , 0 , false , false),
		new SchemaColumn("SignMethod", DataColumn.STRING, 11, 500 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 12, 255 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 13, 255 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 14, 255 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 15, 255 , 0 , false , false),
		new SchemaColumn("CreateDate", DataColumn.DATETIME, 16, 0 , 0 , false , false),
		new SchemaColumn("ModifyDate", DataColumn.DATETIME, 17, 0 , 0 , false , false)
	};

	public static final String _TableCode = "WxRightsInfo";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into WxRightsInfo values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update WxRightsInfo set ID=?,AppSignature=?,OpenId=?,AppId=?,TimeStamp=?,MsgType=?,FeedBackId=?,TransId=?,Reason=?,Solution=?,ExtInfo=?,SignMethod=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,CreateDate=?,ModifyDate=? where ID=?";

	protected static final String _DeleteSQL = "delete from WxRightsInfo  where ID=?";

	protected static final String _FillAllSQL = "select * from WxRightsInfo  where ID=?";

	public WxRightsInfoSchema(){
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
		return new WxRightsInfoSchema();
	}

	protected SchemaSet newSet(){
		return new WxRightsInfoSet();
	}

	public WxRightsInfoSet query() {
		return query(null, -1, -1);
	}

	public WxRightsInfoSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public WxRightsInfoSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public WxRightsInfoSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (WxRightsInfoSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){ID = (String)v;return;}
		if (i == 1){AppSignature = (String)v;return;}
		if (i == 2){OpenId = (String)v;return;}
		if (i == 3){AppId = (String)v;return;}
		if (i == 4){TimeStamp = (String)v;return;}
		if (i == 5){MsgType = (String)v;return;}
		if (i == 6){FeedBackId = (String)v;return;}
		if (i == 7){TransId = (String)v;return;}
		if (i == 8){Reason = (String)v;return;}
		if (i == 9){Solution = (String)v;return;}
		if (i == 10){ExtInfo = (String)v;return;}
		if (i == 11){SignMethod = (String)v;return;}
		if (i == 12){Prop1 = (String)v;return;}
		if (i == 13){Prop2 = (String)v;return;}
		if (i == 14){Prop3 = (String)v;return;}
		if (i == 15){Prop4 = (String)v;return;}
		if (i == 16){CreateDate = (Date)v;return;}
		if (i == 17){ModifyDate = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return AppSignature;}
		if (i == 2){return OpenId;}
		if (i == 3){return AppId;}
		if (i == 4){return TimeStamp;}
		if (i == 5){return MsgType;}
		if (i == 6){return FeedBackId;}
		if (i == 7){return TransId;}
		if (i == 8){return Reason;}
		if (i == 9){return Solution;}
		if (i == 10){return ExtInfo;}
		if (i == 11){return SignMethod;}
		if (i == 12){return Prop1;}
		if (i == 13){return Prop2;}
		if (i == 14){return Prop3;}
		if (i == 15){return Prop4;}
		if (i == 16){return CreateDate;}
		if (i == 17){return ModifyDate;}
		return null;
	}

	/**
	* 获取字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getID() {
		return ID;
	}

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setID(String iD) {
		this.ID = iD;
    }

	/**
	* 获取字段AppSignature的值，该字段的<br>
	* 字段名称 :唯一标识<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAppSignature() {
		return AppSignature;
	}

	/**
	* 设置字段AppSignature的值，该字段的<br>
	* 字段名称 :唯一标识<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAppSignature(String appSignature) {
		this.AppSignature = appSignature;
    }

	/**
	* 获取字段OpenId的值，该字段的<br>
	* 字段名称 :微信用户唯一标示<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getOpenId() {
		return OpenId;
	}

	/**
	* 设置字段OpenId的值，该字段的<br>
	* 字段名称 :微信用户唯一标示<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setOpenId(String openId) {
		this.OpenId = openId;
    }

	/**
	* 获取字段AppId的值，该字段的<br>
	* 字段名称 :商户标示<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAppId() {
		return AppId;
	}

	/**
	* 设置字段AppId的值，该字段的<br>
	* 字段名称 :商户标示<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAppId(String appId) {
		this.AppId = appId;
    }

	/**
	* 获取字段TimeStamp的值，该字段的<br>
	* 字段名称 :申请时间<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getTimeStamp() {
		return TimeStamp;
	}

	/**
	* 设置字段TimeStamp的值，该字段的<br>
	* 字段名称 :申请时间<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setTimeStamp(String timeStamp) {
		this.TimeStamp = timeStamp;
    }

	/**
	* 获取字段MsgType的值，该字段的<br>
	* 字段名称 :申请类型<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMsgType() {
		return MsgType;
	}

	/**
	* 设置字段MsgType的值，该字段的<br>
	* 字段名称 :申请类型<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMsgType(String msgType) {
		this.MsgType = msgType;
    }

	/**
	* 获取字段FeedBackId的值，该字段的<br>
	* 字段名称 :投诉单号<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getFeedBackId() {
		return FeedBackId;
	}

	/**
	* 设置字段FeedBackId的值，该字段的<br>
	* 字段名称 :投诉单号<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setFeedBackId(String feedBackId) {
		this.FeedBackId = feedBackId;
    }

	/**
	* 获取字段TransId的值，该字段的<br>
	* 字段名称 :交易号<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getTransId() {
		return TransId;
	}

	/**
	* 设置字段TransId的值，该字段的<br>
	* 字段名称 :交易号<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setTransId(String transId) {
		this.TransId = transId;
    }

	/**
	* 获取字段Reason的值，该字段的<br>
	* 字段名称 :维权原因<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getReason() {
		return Reason;
	}

	/**
	* 设置字段Reason的值，该字段的<br>
	* 字段名称 :维权原因<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setReason(String reason) {
		this.Reason = reason;
    }

	/**
	* 获取字段Solution的值，该字段的<br>
	* 字段名称 :微信用户希望解决方案<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSolution() {
		return Solution;
	}

	/**
	* 设置字段Solution的值，该字段的<br>
	* 字段名称 :微信用户希望解决方案<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSolution(String solution) {
		this.Solution = solution;
    }

	/**
	* 获取字段ExtInfo的值，该字段的<br>
	* 字段名称 :备注信息，电话等<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getExtInfo() {
		return ExtInfo;
	}

	/**
	* 设置字段ExtInfo的值，该字段的<br>
	* 字段名称 :备注信息，电话等<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setExtInfo(String extInfo) {
		this.ExtInfo = extInfo;
    }

	/**
	* 获取字段SignMethod的值，该字段的<br>
	* 字段名称 :签名方法<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSignMethod() {
		return SignMethod;
	}

	/**
	* 设置字段SignMethod的值，该字段的<br>
	* 字段名称 :签名方法<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSignMethod(String signMethod) {
		this.SignMethod = signMethod;
    }

	/**
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :Prop1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :Prop1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* 获取字段Prop2的值，该字段的<br>
	* 字段名称 :Prop2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* 设置字段Prop2的值，该字段的<br>
	* 字段名称 :Prop2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

	/**
	* 获取字段Prop3的值，该字段的<br>
	* 字段名称 :Prop3<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp3() {
		return Prop3;
	}

	/**
	* 设置字段Prop3的值，该字段的<br>
	* 字段名称 :Prop3<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp3(String prop3) {
		this.Prop3 = prop3;
    }

	/**
	* 获取字段Prop4的值，该字段的<br>
	* 字段名称 :Prop4<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp4() {
		return Prop4;
	}

	/**
	* 设置字段Prop4的值，该字段的<br>
	* 字段名称 :Prop4<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp4(String prop4) {
		this.Prop4 = prop4;
    }

	/**
	* 获取字段CreateDate的值，该字段的<br>
	* 字段名称 :CreateDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getCreateDate() {
		return CreateDate;
	}

	/**
	* 设置字段CreateDate的值，该字段的<br>
	* 字段名称 :CreateDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCreateDate(Date createDate) {
		this.CreateDate = createDate;
    }

	/**
	* 获取字段ModifyDate的值，该字段的<br>
	* 字段名称 :ModifyDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getModifyDate() {
		return ModifyDate;
	}

	/**
	* 设置字段ModifyDate的值，该字段的<br>
	* 字段名称 :ModifyDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyDate(Date modifyDate) {
		this.ModifyDate = modifyDate;
    }

}