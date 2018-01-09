package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

import java.util.Date;

/**
 * 表名称：客服询单转化信息<br>
 * 表代码：CustomServiceOrderInfo<br>
 * 表主键：id<br>
 */
public class CustomServiceOrderInfoSchema extends Schema {
	private String id;

	private String OrderSn;

	private String ConversionType;

	private String CustomerPhone;

	private String EmployeeNum;

	private String EmployeeName;

	private String TrackDetails;

	private String BuyerWWName;

	private String ServiceWWName;

	private String ChannelOrderSn;

	private Date AddTime;

	private String AddUser;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 100 , 0 , true , true),
		new SchemaColumn("OrderSn", DataColumn.STRING, 1, 100 , 0 , false , false),
		new SchemaColumn("ConversionType", DataColumn.STRING, 2, 100 , 0 , false , false),
		new SchemaColumn("CustomerPhone", DataColumn.STRING, 3, 50 , 0 , false , false),
		new SchemaColumn("EmployeeNum", DataColumn.STRING, 4, 20 , 0 , false , false),
		new SchemaColumn("EmployeeName", DataColumn.STRING, 5, 50 , 0 , false , false),
		new SchemaColumn("TrackDetails", DataColumn.CLOB, 6, 0 , 0 , false , false),
		new SchemaColumn("BuyerWWName", DataColumn.STRING, 7, 100 , 0 , false , false),
		new SchemaColumn("ServiceWWName", DataColumn.STRING, 8, 100 , 0 , false , false),
		new SchemaColumn("ChannelOrderSn", DataColumn.STRING, 9, 100 , 0 , false , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 10, 0 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 11, 100 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 12, 100 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 13, 100 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 14, 100 , 0 , false , false)
	};

	public static final String _TableCode = "CustomServiceOrderInfo";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into CustomServiceOrderInfo values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update CustomServiceOrderInfo set id=?,OrderSn=?,ConversionType=?,CustomerPhone=?,EmployeeNum=?,EmployeeName=?,TrackDetails=?,BuyerWWName=?,ServiceWWName=?,ChannelOrderSn=?,AddTime=?,AddUser=?,Prop1=?,Prop2=?,Prop3=? where id=?";

	protected static final String _DeleteSQL = "delete from CustomServiceOrderInfo  where id=?";

	protected static final String _FillAllSQL = "select * from CustomServiceOrderInfo  where id=?";

	public CustomServiceOrderInfoSchema(){
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
		return new CustomServiceOrderInfoSchema();
	}

	protected SchemaSet newSet(){
		return new CustomServiceOrderInfoSet();
	}

	public CustomServiceOrderInfoSet query() {
		return query(null, -1, -1);
	}

	public CustomServiceOrderInfoSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public CustomServiceOrderInfoSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public CustomServiceOrderInfoSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (CustomServiceOrderInfoSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){OrderSn = (String)v;return;}
		if (i == 2){ConversionType = (String)v;return;}
		if (i == 3){CustomerPhone = (String)v;return;}
		if (i == 4){EmployeeNum = (String)v;return;}
		if (i == 5){EmployeeName = (String)v;return;}
		if (i == 6){TrackDetails = (String)v;return;}
		if (i == 7){BuyerWWName = (String)v;return;}
		if (i == 8){ServiceWWName = (String)v;return;}
		if (i == 9){ChannelOrderSn = (String)v;return;}
		if (i == 10){AddTime = (Date)v;return;}
		if (i == 11){AddUser = (String)v;return;}
		if (i == 12){Prop1 = (String)v;return;}
		if (i == 13){Prop2 = (String)v;return;}
		if (i == 14){Prop3 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return OrderSn;}
		if (i == 2){return ConversionType;}
		if (i == 3){return CustomerPhone;}
		if (i == 4){return EmployeeNum;}
		if (i == 5){return EmployeeName;}
		if (i == 6){return TrackDetails;}
		if (i == 7){return BuyerWWName;}
		if (i == 8){return ServiceWWName;}
		if (i == 9){return ChannelOrderSn;}
		if (i == 10){return AddTime;}
		if (i == 11){return AddUser;}
		if (i == 12){return Prop1;}
		if (i == 13){return Prop2;}
		if (i == 14){return Prop3;}
		return null;
	}

	/**
	* 获取字段id的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getid() {
		return id;
	}

	/**
	* 设置字段id的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setid(String id) {
		this.id = id;
    }

	/**
	* 获取字段OrderSn的值，该字段的<br>
	* 字段名称 :订单号<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getOrderSn() {
		return OrderSn;
	}

	/**
	* 设置字段OrderSn的值，该字段的<br>
	* 字段名称 :订单号<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setOrderSn(String orderSn) {
		this.OrderSn = orderSn;
    }

	/**
	* 获取字段ConversionType的值，该字段的<br>
	* 字段名称 :询单转化类型<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getConversionType() {
		return ConversionType;
	}

	/**
	* 设置字段ConversionType的值，该字段的<br>
	* 字段名称 :询单转化类型<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setConversionType(String conversionType) {
		this.ConversionType = conversionType;
    }

	/**
	* 获取字段CustomerPhone的值，该字段的<br>
	* 字段名称 :投保电话<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCustomerPhone() {
		return CustomerPhone;
	}

	/**
	* 设置字段CustomerPhone的值，该字段的<br>
	* 字段名称 :投保电话<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCustomerPhone(String customerPhone) {
		this.CustomerPhone = customerPhone;
    }

	/**
	* 获取字段EmployeeNum的值，该字段的<br>
	* 字段名称 :座席工号<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getEmployeeNum() {
		return EmployeeNum;
	}

	/**
	* 设置字段EmployeeNum的值，该字段的<br>
	* 字段名称 :座席工号<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setEmployeeNum(String employeeNum) {
		this.EmployeeNum = employeeNum;
    }

	/**
	* 获取字段EmployeeName的值，该字段的<br>
	* 字段名称 :员工名称<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getEmployeeName() {
		return EmployeeName;
	}

	/**
	* 设置字段EmployeeName的值，该字段的<br>
	* 字段名称 :员工名称<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setEmployeeName(String employeeName) {
		this.EmployeeName = employeeName;
    }

	/**
	* 获取字段TrackDetails的值，该字段的<br>
	* 字段名称 :轨迹详情<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getTrackDetails() {
		return TrackDetails;
	}

	/**
	* 设置字段TrackDetails的值，该字段的<br>
	* 字段名称 :轨迹详情<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setTrackDetails(String trackDetails) {
		this.TrackDetails = trackDetails;
    }

	/**
	* 获取字段BuyerWWName的值，该字段的<br>
	* 字段名称 :买家旺旺<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getBuyerWWName() {
		return BuyerWWName;
	}

	/**
	* 设置字段BuyerWWName的值，该字段的<br>
	* 字段名称 :买家旺旺<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setBuyerWWName(String buyerWWName) {
		this.BuyerWWName = buyerWWName;
    }

	/**
	* 获取字段ServiceWWName的值，该字段的<br>
	* 字段名称 :客服旺旺<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getServiceWWName() {
		return ServiceWWName;
	}

	/**
	* 设置字段ServiceWWName的值，该字段的<br>
	* 字段名称 :客服旺旺<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setServiceWWName(String serviceWWName) {
		this.ServiceWWName = serviceWWName;
    }

	/**
	* 获取字段ChannelOrderSn的值，该字段的<br>
	* 字段名称 :渠道订单号<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getChannelOrderSn() {
		return ChannelOrderSn;
	}

	/**
	* 设置字段ChannelOrderSn的值，该字段的<br>
	* 字段名称 :渠道订单号<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setChannelOrderSn(String channelOrderSn) {
		this.ChannelOrderSn = channelOrderSn;
    }

	/**
	* 获取字段AddTime的值，该字段的<br>
	* 字段名称 :AddTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getAddTime() {
		return AddTime;
	}

	/**
	* 设置字段AddTime的值，该字段的<br>
	* 字段名称 :AddTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAddTime(Date addTime) {
		this.AddTime = addTime;
    }

	/**
	* 获取字段AddUser的值，该字段的<br>
	* 字段名称 :AddUser<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAddUser() {
		return AddUser;
	}

	/**
	* 设置字段AddUser的值，该字段的<br>
	* 字段名称 :AddUser<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAddUser(String addUser) {
		this.AddUser = addUser;
    }

	/**
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :备用1<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :备用1<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* 获取字段Prop2的值，该字段的<br>
	* 字段名称 :备用2<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* 设置字段Prop2的值，该字段的<br>
	* 字段名称 :备用2<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

	/**
	* 获取字段Prop3的值，该字段的<br>
	* 字段名称 :备用3<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp3() {
		return Prop3;
	}

	/**
	* 设置字段Prop3的值，该字段的<br>
	* 字段名称 :备用3<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp3(String prop3) {
		this.Prop3 = prop3;
    }

}