package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：责任信息临时表<br>
 * 表代码：SDInformationDutyTemp<br>
 * 表主键：Id<br>
 */
public class SDInformationDutyTempSchema extends Schema {
	private String Id;

	private String DutySerials;

	private Date CreateDate;

	private Date ModifyDate;

	private String OrderSn;

	private String DutySn;

	private String Amnt;

	private String Premium;
	
	private String DiscountRates;
	
	private String DiscountPrice;
	
	private String Remark1;

	private String Remark2;

	private String Remark3;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("Id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("DutySerials", DataColumn.STRING, 1, 32 , 0 , false , false),
		new SchemaColumn("CreateDate", DataColumn.DATETIME, 2, 0 , 0 , false , false),
		new SchemaColumn("ModifyDate", DataColumn.DATETIME, 3, 0 , 0 , false , false),
		new SchemaColumn("OrderSn", DataColumn.STRING, 4, 25 , 0 , false , false),
		new SchemaColumn("DutySn", DataColumn.STRING, 5, 25 , 0 , false , false),
		new SchemaColumn("Amnt", DataColumn.STRING, 6, 20 , 0 , false , false),
		new SchemaColumn("Premium", DataColumn.STRING, 7, 20 , 0 , false , false),
		new SchemaColumn("DiscountRates", DataColumn.STRING, 8, 10 , 0 , false , false),
		new SchemaColumn("DiscountPrice", DataColumn.STRING, 9, 20 , 0 , false , false),
		new SchemaColumn("Remark1", DataColumn.STRING, 10, 100 , 0 , false , false),
		new SchemaColumn("Remark2", DataColumn.STRING, 11, 100 , 0 , false , false),
		new SchemaColumn("Remark3", DataColumn.STRING, 12, 100 , 0 , false , false)
	};

	public static final String _TableCode = "SDInformationDutyTemp";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into SDInformationDutyTemp values(?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update SDInformationDutyTemp set Id=?,Serials=?,CreateDate=?,ModifyDate=?,OrderSn=?,DutySn=?,Amnt=?,Premium=?,DiscountRates=?,DiscountPrice=?,Remark1=?,Remark2=?,Remark3=? where Id=?";

	protected static final String _DeleteSQL = "delete from SDInformationDutyTemp  where Id=?";

	protected static final String _FillAllSQL = "select * from SDInformationDutyTemp  where Id=?";

	public SDInformationDutyTempSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[13];
	}

	protected Schema newInstance(){
		return new SDInformationDutyTempSchema();
	}

	protected SchemaSet newSet(){
		return new SDInformationDutyTempSet();
	}

	public SDInformationDutyTempSet query() {
		return query(null, -1, -1);
	}

	public SDInformationDutyTempSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public SDInformationDutyTempSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public SDInformationDutyTempSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (SDInformationDutyTempSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){Id = (String)v;return;}
		if (i == 1){DutySerials = (String)v;return;}
		if (i == 2){CreateDate = (Date)v;return;}
		if (i == 3){ModifyDate = (Date)v;return;}
		if (i == 4){OrderSn = (String)v;return;}
		if (i == 5){DutySn = (String)v;return;}
		if (i == 6){Amnt = (String)v;return;}
		if (i == 7){Premium = (String)v;return;}
		if (i == 8){DiscountRates = (String)v;return;}
		if (i == 9){DiscountPrice = (String)v;return;}
		if (i == 10){Remark1 = (String)v;return;}
		if (i == 11){Remark2 = (String)v;return;}
		if (i == 13){Remark3 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return Id;}
		if (i == 1){return DutySerials;}
		if (i == 2){return CreateDate;}
		if (i == 3){return ModifyDate;}
		if (i == 4){return OrderSn;}
		if (i == 5){return DutySn;}
		if (i == 6){return Amnt;}
		if (i == 7){return Premium;}
		if (i == 8){return DiscountRates;}
		if (i == 9){return DiscountPrice;}
		if (i == 10){return Remark1;}
		if (i == 11){return Remark2;}
		if (i == 12){return Remark3;}
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
	* 获取字段DutySerials的值，该字段的<br>
	* 字段名称 :序列号<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getDutySerials() {
		return DutySerials;
	}

	/**
	* 设置字段DutySerials的值，该字段的<br>
	* 字段名称 :序列号<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDutySerials(String dutySerials) {
		this.DutySerials = dutySerials;
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

	/**
	* 获取字段OrderSn的值，该字段的<br>
	* 字段名称 :订单编号<br>
	* 数据类型 :varchar(25)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getOrderSn() {
		return OrderSn;
	}

	/**
	* 设置字段OrderSn的值，该字段的<br>
	* 字段名称 :订单编号<br>
	* 数据类型 :varchar(25)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setOrderSn(String orderSn) {
		this.OrderSn = orderSn;
    }

	/**
	* 获取字段DutySn的值，该字段的<br>
	* 字段名称 :责任编码<br>
	* 数据类型 :varchar(25)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getDutySn() {
		return DutySn;
	}

	/**
	* 设置字段DutySn的值，该字段的<br>
	* 字段名称 :责任编码<br>
	* 数据类型 :varchar(25)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDutySn(String dutySn) {
		this.DutySn = dutySn;
    }

	/**
	* 获取字段Amnt的值，该字段的<br>
	* 字段名称 :保额计算值<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAmnt() {
		return Amnt;
	}

	/**
	* 设置字段Amnt的值，该字段的<br>
	* 字段名称 :保额计算值<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAmnt(String amnt) {
		this.Amnt = amnt;
    }

	/**
	* 获取字段Premium的值，该字段的<br>
	* 字段名称 :原保费<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPremium() {
		return Premium;
	}

	/**
	* 设置字段Premium的值，该字段的<br>
	* 字段名称 :原保费<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPremium(String Premium) {
		this.Premium = Premium;
    }
	
	/**
	* 获取字段DiscountRates的值，该字段的<br>
	* 字段名称 :折扣率<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getDiscountRates() {
		return DiscountRates;
	}

	/**
	* 设置字段DiscountRates的值，该字段的<br>
	* 字段名称 :折扣率<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDiscountRates(String DiscountRates) {
		this.DiscountRates = DiscountRates;
    }
	
	/**
	* 获取字段DiscountPrice的值，该字段的<br>
	* 字段名称 :折扣保费<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getDiscountPrice() {
		return DiscountPrice;
	}

	/**
	* 设置字段DiscountPrice的值，该字段的<br>
	* 字段名称 :折扣保费<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDiscountPrice(String DiscountPrice) {
		this.DiscountPrice = DiscountPrice;
    }
	
	/**
	* 获取字段Remark1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getRemark1() {
		return Remark1;
	}

	/**
	* 设置字段Remark1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRemark1(String remark1) {
		this.Remark1 = remark1;
    }

	/**
	* 获取字段Remark2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getRemark2() {
		return Remark2;
	}

	/**
	* 设置字段Remark2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRemark2(String remark2) {
		this.Remark2 = remark2;
    }

	/**
	* 获取字段Remark3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getRemark3() {
		return Remark3;
	}

	/**
	* 设置字段Remark3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRemark3(String remark3) {
		this.Remark3 = remark3;
    }

}