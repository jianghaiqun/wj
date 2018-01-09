package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：撤单退款表<br>
 * 表代码：cancelContRefund<br>
 * 表主键：id<br>
 */
public class CancelContRefundSchema extends Schema {
	private String id;

	private String orderSn;

	private String policyNo;

	private String prem;

	private String payPrem;

	private String status;

	private String cancelFrom;

	private Date repealDate;

	private String backUp1;

	private String backUp2;

	private String backUp3;

	private String backUp4;

	private String backUp5;

	private String backUp6;

	private String backUp7;

	private String backUp8;

	private String backUp9;

	private String backUp10;

	private Date createDate;

	private Date modifyDate;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("orderSn", DataColumn.STRING, 1, 20 , 0 , false , false),
		new SchemaColumn("policyNo", DataColumn.STRING, 2, 100 , 0 , false , false),
		new SchemaColumn("prem", DataColumn.STRING, 3, 100 , 0 , false , false),
		new SchemaColumn("payPrem", DataColumn.STRING, 4, 100 , 0 , false , false),
		new SchemaColumn("status", DataColumn.STRING, 5, 1 , 0 , false , false),
		new SchemaColumn("cancelFrom", DataColumn.STRING, 6, 1 , 0 , false , false),
		new SchemaColumn("repealDate", DataColumn.DATETIME, 7, 0 , 0 , false , false),
		new SchemaColumn("backUp1", DataColumn.STRING, 8, 255 , 0 , false , false),
		new SchemaColumn("backUp2", DataColumn.STRING, 9, 255 , 0 , false , false),
		new SchemaColumn("backUp3", DataColumn.STRING, 10, 255 , 0 , false , false),
		new SchemaColumn("backUp4", DataColumn.STRING, 11, 255 , 0 , false , false),
		new SchemaColumn("backUp5", DataColumn.STRING, 12, 255 , 0 , false , false),
		new SchemaColumn("backUp6", DataColumn.STRING, 13, 255 , 0 , false , false),
		new SchemaColumn("backUp7", DataColumn.STRING, 14, 255 , 0 , false , false),
		new SchemaColumn("backUp8", DataColumn.STRING, 15, 255 , 0 , false , false),
		new SchemaColumn("backUp9", DataColumn.STRING, 16, 255 , 0 , false , false),
		new SchemaColumn("backUp10", DataColumn.STRING, 17, 255 , 0 , false , false),
		new SchemaColumn("createDate", DataColumn.DATETIME, 18, 0 , 0 , false , false),
		new SchemaColumn("modifyDate", DataColumn.DATETIME, 19, 0 , 0 , false , false)
	};

	public static final String _TableCode = "cancelContRefund";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into cancelContRefund values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update cancelContRefund set id=?,orderSn=?,policyNo=?,prem=?,payPrem=?,status=?,cancelFrom=?,repealDate=?,backUp1=?,backUp2=?,backUp3=?,backUp4=?,backUp5=?,backUp6=?,backUp7=?,backUp8=?,backUp9=?,backUp10=?,createDate=?,modifyDate=? where id=?";

	protected static final String _DeleteSQL = "delete from cancelContRefund  where id=?";

	protected static final String _FillAllSQL = "select * from cancelContRefund  where id=?";

	public CancelContRefundSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[20];
	}

	protected Schema newInstance(){
		return new CancelContRefundSchema();
	}

	protected SchemaSet newSet(){
		return new CancelContRefundSet();
	}

	public CancelContRefundSet query() {
		return query(null, -1, -1);
	}

	public CancelContRefundSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public CancelContRefundSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public CancelContRefundSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (CancelContRefundSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){orderSn = (String)v;return;}
		if (i == 2){policyNo = (String)v;return;}
		if (i == 3){prem = (String)v;return;}
		if (i == 4){payPrem = (String)v;return;}
		if (i == 5){status = (String)v;return;}
		if (i == 6){cancelFrom = (String)v;return;}
		if (i == 7){repealDate = (Date)v;return;}
		if (i == 8){backUp1 = (String)v;return;}
		if (i == 9){backUp2 = (String)v;return;}
		if (i == 10){backUp3 = (String)v;return;}
		if (i == 11){backUp4 = (String)v;return;}
		if (i == 12){backUp5 = (String)v;return;}
		if (i == 13){backUp6 = (String)v;return;}
		if (i == 14){backUp7 = (String)v;return;}
		if (i == 15){backUp8 = (String)v;return;}
		if (i == 16){backUp9 = (String)v;return;}
		if (i == 17){backUp10 = (String)v;return;}
		if (i == 18){createDate = (Date)v;return;}
		if (i == 19){modifyDate = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return orderSn;}
		if (i == 2){return policyNo;}
		if (i == 3){return prem;}
		if (i == 4){return payPrem;}
		if (i == 5){return status;}
		if (i == 6){return cancelFrom;}
		if (i == 7){return repealDate;}
		if (i == 8){return backUp1;}
		if (i == 9){return backUp2;}
		if (i == 10){return backUp3;}
		if (i == 11){return backUp4;}
		if (i == 12){return backUp5;}
		if (i == 13){return backUp6;}
		if (i == 14){return backUp7;}
		if (i == 15){return backUp8;}
		if (i == 16){return backUp9;}
		if (i == 17){return backUp10;}
		if (i == 18){return createDate;}
		if (i == 19){return modifyDate;}
		return null;
	}

	/**
	* 获取字段id的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getId() {
		return id;
	}

	/**
	* 设置字段id的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setId(String id) {
		this.id = id;
    }

	/**
	* 获取字段orderSn的值，该字段的<br>
	* 字段名称 :订单号<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getOrderSn() {
		return orderSn;
	}

	/**
	* 设置字段orderSn的值，该字段的<br>
	* 字段名称 :订单号<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
    }

	/**
	* 获取字段policyNo的值，该字段的<br>
	* 字段名称 :保单号<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPolicyNo() {
		return policyNo;
	}

	/**
	* 设置字段policyNo的值，该字段的<br>
	* 字段名称 :保单号<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
    }

	/**
	* 获取字段prem的值，该字段的<br>
	* 字段名称 :保费<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPrem() {
		return prem;
	}

	/**
	* 设置字段prem的值，该字段的<br>
	* 字段名称 :保费<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPrem(String prem) {
		this.prem = prem;
    }

	/**
	* 获取字段payPrem的值，该字段的<br>
	* 字段名称 :实际支付保费<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPayPrem() {
		return payPrem;
	}

	/**
	* 设置字段payPrem的值，该字段的<br>
	* 字段名称 :实际支付保费<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPayPrem(String payPrem) {
		this.payPrem = payPrem;
    }

	/**
	* 获取字段status的值，该字段的<br>
	* 字段名称 :审核状态<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getStatus() {
		return status;
	}

	/**
	* 设置字段status的值，该字段的<br>
	* 字段名称 :审核状态<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setStatus(String status) {
		this.status = status;
    }

	/**
	* 获取字段cancelFrom的值，该字段的<br>
	* 字段名称 :撤单来源<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCancelFrom() {
		return cancelFrom;
	}

	/**
	* 设置字段cancelFrom的值，该字段的<br>
	* 字段名称 :撤单来源<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCancelFrom(String cancelFrom) {
		this.cancelFrom = cancelFrom;
    }

	/**
	* 获取字段repealDate的值，该字段的<br>
	* 字段名称 :撤单时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getRepealDate() {
		return repealDate;
	}

	/**
	* 设置字段repealDate的值，该字段的<br>
	* 字段名称 :撤单时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRepealDate(Date repealDate) {
		this.repealDate = repealDate;
    }

	/**
	* 获取字段backUp1的值，该字段的<br>
	* 字段名称 :备份1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getBackUp1() {
		return backUp1;
	}

	/**
	* 设置字段backUp1的值，该字段的<br>
	* 字段名称 :备份1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setBackUp1(String backUp1) {
		this.backUp1 = backUp1;
    }

	/**
	* 获取字段backUp2的值，该字段的<br>
	* 字段名称 :备份2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getBackUp2() {
		return backUp2;
	}

	/**
	* 设置字段backUp2的值，该字段的<br>
	* 字段名称 :备份2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setBackUp2(String backUp2) {
		this.backUp2 = backUp2;
    }

	/**
	* 获取字段backUp3的值，该字段的<br>
	* 字段名称 :备份3<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getBackUp3() {
		return backUp3;
	}

	/**
	* 设置字段backUp3的值，该字段的<br>
	* 字段名称 :备份3<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setBackUp3(String backUp3) {
		this.backUp3 = backUp3;
    }

	/**
	* 获取字段backUp4的值，该字段的<br>
	* 字段名称 :备份4<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getBackUp4() {
		return backUp4;
	}

	/**
	* 设置字段backUp4的值，该字段的<br>
	* 字段名称 :备份4<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setBackUp4(String backUp4) {
		this.backUp4 = backUp4;
    }

	/**
	* 获取字段backUp5的值，该字段的<br>
	* 字段名称 :备份5<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getBackUp5() {
		return backUp5;
	}

	/**
	* 设置字段backUp5的值，该字段的<br>
	* 字段名称 :备份5<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setBackUp5(String backUp5) {
		this.backUp5 = backUp5;
    }

	/**
	* 获取字段backUp6的值，该字段的<br>
	* 字段名称 :备份6<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getBackUp6() {
		return backUp6;
	}

	/**
	* 设置字段backUp6的值，该字段的<br>
	* 字段名称 :备份6<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setBackUp6(String backUp6) {
		this.backUp6 = backUp6;
    }

	/**
	* 获取字段backUp7的值，该字段的<br>
	* 字段名称 :备份7<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getBackUp7() {
		return backUp7;
	}

	/**
	* 设置字段backUp7的值，该字段的<br>
	* 字段名称 :备份7<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setBackUp7(String backUp7) {
		this.backUp7 = backUp7;
    }

	/**
	* 获取字段backUp8的值，该字段的<br>
	* 字段名称 :备份8<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getBackUp8() {
		return backUp8;
	}

	/**
	* 设置字段backUp8的值，该字段的<br>
	* 字段名称 :备份8<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setBackUp8(String backUp8) {
		this.backUp8 = backUp8;
    }

	/**
	* 获取字段backUp9的值，该字段的<br>
	* 字段名称 :备份9<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getBackUp9() {
		return backUp9;
	}

	/**
	* 设置字段backUp9的值，该字段的<br>
	* 字段名称 :备份9<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setBackUp9(String backUp9) {
		this.backUp9 = backUp9;
    }

	/**
	* 获取字段backUp10的值，该字段的<br>
	* 字段名称 :备份10<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getBackUp10() {
		return backUp10;
	}

	/**
	* 设置字段backUp10的值，该字段的<br>
	* 字段名称 :备份10<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setBackUp10(String backUp10) {
		this.backUp10 = backUp10;
    }

	/**
	* 获取字段createDate的值，该字段的<br>
	* 字段名称 :创建日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getCreateDate() {
		return createDate;
	}

	/**
	* 设置字段createDate的值，该字段的<br>
	* 字段名称 :创建日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
    }

	/**
	* 获取字段modifyDate的值，该字段的<br>
	* 字段名称 :修改日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getModifyDate() {
		return modifyDate;
	}

	/**
	* 设置字段modifyDate的值，该字段的<br>
	* 字段名称 :修改日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
    }

}