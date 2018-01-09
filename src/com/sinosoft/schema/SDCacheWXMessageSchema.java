package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：微信模版消息发送管理表<br>
 * 表代码：SDCacheWXMessage<br>
 * 表主键：id<br>
 */
public class SDCacheWXMessageSchema extends Schema {
	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 */
	private static final long serialVersionUID = 9217021252767476591L;

	private String id;

	private String messageType;

	private String openId;

	private String orderSn;

	private String policyNo;

	private String sendDate;

	private Integer sendCount;

	private String remark1;

	private String remark2;

	private String remark3;

	private String createUser;

	private Date createDate;

	private String modifyUser;

	private Date modifyDate;

	private String url;

	private String first;

	private String keyword1;

	private String keyword2;

	private String keyword3;

	private String keyword4;

	private String remark;

	private String templateId;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("messageType", DataColumn.STRING, 1, 20 , 0 , false , false),
		new SchemaColumn("openId", DataColumn.STRING, 2, 100 , 0 , false , false),
		new SchemaColumn("orderSn", DataColumn.STRING, 3, 100 , 0 , false , false),
		new SchemaColumn("policyNo", DataColumn.STRING, 4, 100 , 0 , false , false),
		new SchemaColumn("sendDate", DataColumn.STRING, 5, 20 , 0 , false , false),
		new SchemaColumn("sendCount", DataColumn.INTEGER, 6, 11 , 0 , false , false),
		new SchemaColumn("remark1", DataColumn.STRING, 7, 255 , 0 , false , false),
		new SchemaColumn("remark2", DataColumn.STRING, 8, 255 , 0 , false , false),
		new SchemaColumn("remark3", DataColumn.STRING, 9, 255 , 0 , false , false),
		new SchemaColumn("createUser", DataColumn.STRING, 10, 255 , 0 , false , false),
		new SchemaColumn("createDate", DataColumn.DATETIME, 11, 0 , 0 , false , false),
		new SchemaColumn("modifyUser", DataColumn.STRING, 12, 255 , 0 , false , false),
		new SchemaColumn("modifyDate", DataColumn.DATETIME, 13, 0 , 0 , false , false),
		new SchemaColumn("url", DataColumn.STRING, 14, 500 , 0 , false , false),
		new SchemaColumn("first", DataColumn.STRING, 15, 500 , 0 , false , false),
		new SchemaColumn("keyword1", DataColumn.STRING, 16, 500 , 0 , false , false),
		new SchemaColumn("keyword2", DataColumn.STRING, 17, 500 , 0 , false , false),
		new SchemaColumn("keyword3", DataColumn.STRING, 18, 500 , 0 , false , false),
		new SchemaColumn("keyword4", DataColumn.STRING, 19, 500 , 0 , false , false),
		new SchemaColumn("remark", DataColumn.STRING, 20, 500 , 0 , false , false),
		new SchemaColumn("templateId", DataColumn.STRING, 21, 100 , 0 , false , false)
	};

	public static final String _TableCode = "SDCacheWXMessage";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into SDCacheWXMessage values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update SDCacheWXMessage set id=?,messageType=?,openId=?,orderSn=?,policyNo=?,sendDate=?,sendCount=?,remark1=?,remark2=?,remark3=?,createUser=?,createDate=?,modifyUser=?,modifyDate=?,url=?,first=?,keyword1=?,keyword2=?,keyword3=?,keyword4=?,remark=?,templateId=? where id=?";

	protected static final String _DeleteSQL = "delete from SDCacheWXMessage  where id=?";

	protected static final String _FillAllSQL = "select * from SDCacheWXMessage  where id=?";

	public SDCacheWXMessageSchema(){
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
		return new SDCacheWXMessageSchema();
	}

	protected SchemaSet newSet(){
		return new SDCacheWXMessageSet();
	}

	public SDCacheWXMessageSet query() {
		return query(null, -1, -1);
	}

	public SDCacheWXMessageSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public SDCacheWXMessageSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public SDCacheWXMessageSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (SDCacheWXMessageSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){messageType = (String)v;return;}
		if (i == 2){openId = (String)v;return;}
		if (i == 3){orderSn = (String)v;return;}
		if (i == 4){policyNo = (String)v;return;}
		if (i == 5){sendDate = (String)v;return;}
		if (i == 6){if(v==null){sendCount = null;}else{sendCount = new Integer(v.toString());}return;}
		if (i == 7){remark1 = (String)v;return;}
		if (i == 8){remark2 = (String)v;return;}
		if (i == 9){remark3 = (String)v;return;}
		if (i == 10){createUser = (String)v;return;}
		if (i == 11){createDate = (Date)v;return;}
		if (i == 12){modifyUser = (String)v;return;}
		if (i == 13){modifyDate = (Date)v;return;}
		if (i == 14){url = (String)v;return;}
		if (i == 15){first = (String)v;return;}
		if (i == 16){keyword1 = (String)v;return;}
		if (i == 17){keyword2 = (String)v;return;}
		if (i == 18){keyword3 = (String)v;return;}
		if (i == 19){keyword4 = (String)v;return;}
		if (i == 20){remark = (String)v;return;}
		if (i == 21){templateId = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return messageType;}
		if (i == 2){return openId;}
		if (i == 3){return orderSn;}
		if (i == 4){return policyNo;}
		if (i == 5){return sendDate;}
		if (i == 6){return sendCount;}
		if (i == 7){return remark1;}
		if (i == 8){return remark2;}
		if (i == 9){return remark3;}
		if (i == 10){return createUser;}
		if (i == 11){return createDate;}
		if (i == 12){return modifyUser;}
		if (i == 13){return modifyDate;}
		if (i == 14){return url;}
		if (i == 15){return first;}
		if (i == 16){return keyword1;}
		if (i == 17){return keyword2;}
		if (i == 18){return keyword3;}
		if (i == 19){return keyword4;}
		if (i == 20){return remark;}
		if (i == 21){return templateId;}
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
	* 获取字段messageType的值，该字段的<br>
	* 字段名称 :消息类型<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getmessageType() {
		return messageType;
	}

	/**
	* 设置字段messageType的值，该字段的<br>
	* 字段名称 :消息类型<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmessageType(String messageType) {
		this.messageType = messageType;
    }

	/**
	* 获取字段openId的值，该字段的<br>
	* 字段名称 :微信openId<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getopenId() {
		return openId;
	}

	/**
	* 设置字段openId的值，该字段的<br>
	* 字段名称 :微信openId<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setopenId(String openId) {
		this.openId = openId;
    }

	/**
	* 获取字段orderSn的值，该字段的<br>
	* 字段名称 :订单号<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getorderSn() {
		return orderSn;
	}

	/**
	* 设置字段orderSn的值，该字段的<br>
	* 字段名称 :订单号<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setorderSn(String orderSn) {
		this.orderSn = orderSn;
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
	* 获取字段sendDate的值，该字段的<br>
	* 字段名称 :发送时间<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getsendDate() {
		return sendDate;
	}

	/**
	* 设置字段sendDate的值，该字段的<br>
	* 字段名称 :发送时间<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setsendDate(String sendDate) {
		this.sendDate = sendDate;
    }

	/**
	* 获取字段sendCount的值，该字段的<br>
	* 字段名称 :发送次数<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getsendCount() {
		if(sendCount==null){return 0;}
		return sendCount.intValue();
	}

	/**
	* 设置字段sendCount的值，该字段的<br>
	* 字段名称 :发送次数<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setsendCount(int sendCount) {
		this.sendCount = new Integer(sendCount);
    }

	/**
	* 设置字段sendCount的值，该字段的<br>
	* 字段名称 :发送次数<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setsendCount(String sendCount) {
		if (sendCount == null){
			this.sendCount = null;
			return;
		}
		this.sendCount = new Integer(sendCount);
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
	* 获取字段createUser的值，该字段的<br>
	* 字段名称 :创建人<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcreateUser() {
		return createUser;
	}

	/**
	* 设置字段createUser的值，该字段的<br>
	* 字段名称 :创建人<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcreateUser(String createUser) {
		this.createUser = createUser;
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
	* 获取字段modifyUser的值，该字段的<br>
	* 字段名称 :修改人<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getmodifyUser() {
		return modifyUser;
	}

	/**
	* 设置字段modifyUser的值，该字段的<br>
	* 字段名称 :修改人<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmodifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
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
	* 获取字段url的值，该字段的<br>
	* 字段名称 :url<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String geturl() {
		return url;
	}

	/**
	* 设置字段url的值，该字段的<br>
	* 字段名称 :url<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void seturl(String url) {
		this.url = url;
    }

	/**
	* 获取字段first的值，该字段的<br>
	* 字段名称 :first<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getfirst() {
		return first;
	}

	/**
	* 设置字段first的值，该字段的<br>
	* 字段名称 :first<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setfirst(String first) {
		this.first = first;
    }

	/**
	* 获取字段keyword1的值，该字段的<br>
	* 字段名称 :keyword1<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getkeyword1() {
		return keyword1;
	}

	/**
	* 设置字段keyword1的值，该字段的<br>
	* 字段名称 :keyword1<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setkeyword1(String keyword1) {
		this.keyword1 = keyword1;
    }

	/**
	* 获取字段keyword2的值，该字段的<br>
	* 字段名称 :keyword2<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getkeyword2() {
		return keyword2;
	}

	/**
	* 设置字段keyword2的值，该字段的<br>
	* 字段名称 :keyword2<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setkeyword2(String keyword2) {
		this.keyword2 = keyword2;
    }

	/**
	* 获取字段keyword3的值，该字段的<br>
	* 字段名称 :keyword3<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getkeyword3() {
		return keyword3;
	}

	/**
	* 设置字段keyword3的值，该字段的<br>
	* 字段名称 :keyword3<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setkeyword3(String keyword3) {
		this.keyword3 = keyword3;
    }

	/**
	* 获取字段keyword4的值，该字段的<br>
	* 字段名称 :keyword4<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getkeyword4() {
		return keyword4;
	}

	/**
	* 设置字段keyword4的值，该字段的<br>
	* 字段名称 :keyword4<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setkeyword4(String keyword4) {
		this.keyword4 = keyword4;
    }

	/**
	* 获取字段remark的值，该字段的<br>
	* 字段名称 :remark<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getremark() {
		return remark;
	}

	/**
	* 设置字段remark的值，该字段的<br>
	* 字段名称 :remark<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setremark(String remark) {
		this.remark = remark;
    }

	/**
	* 获取字段templateId的值，该字段的<br>
	* 字段名称 :templateId<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String gettemplateId() {
		return templateId;
	}

	/**
	* 设置字段templateId的值，该字段的<br>
	* 字段名称 :templateId<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void settemplateId(String templateId) {
		this.templateId = templateId;
    }

}