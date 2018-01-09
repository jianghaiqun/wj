package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

import java.math.BigDecimal;

/**
 * 表名称：orders<br>
 * 表代码：orders<br>
 * 表主键：id<br>
 */
public class ordersSchema extends Schema {
	private String id;

	private Date createDate;

	private Date modifyDate;

	private String eRiskType;

	private String insuranceCompany;

	private String memberId;

	private String orderSn;

	private Integer orderStatus;

	private Boolean orderValid;

	private BigDecimal paidAmount;

	private String paymentConfigName;

	private BigDecimal paymentFee;

	private Integer paymentStatus;

	private Integer point;

	private String pointFrom;

	private String productId;

	private String productName;

	private BigDecimal productTotalPrice;

	private Integer productTotalQuantity;

	private String subRiskTypeCode;

	private BigDecimal totalAmount;

	private String insuranceCompanySn;

	private BigDecimal amntPrice;

	private Integer insureStatus;

	private String policyNumber;

	private String outRiskCode;

	private String pointStatus;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("createDate", DataColumn.DATETIME, 1, 0 , 0 , false , false),
		new SchemaColumn("modifyDate", DataColumn.DATETIME, 2, 0 , 0 , false , false),
		new SchemaColumn("eRiskType", DataColumn.STRING, 3, 255 , 0 , false , false),
		new SchemaColumn("insuranceCompany", DataColumn.STRING, 4, 255 , 0 , false , false),
		new SchemaColumn("memberId", DataColumn.STRING, 5, 255 , 0 , false , false),
		new SchemaColumn("orderSn", DataColumn.STRING, 6, 255 , 0 , false , false),
		new SchemaColumn("orderStatus", DataColumn.INTEGER, 7, 11 , 0 , false , false),
		new SchemaColumn("orderValid", DataColumn.BOOLEAN, 8, 1 , 0 , false , false),
		new SchemaColumn("paidAmount", DataColumn.BIGDECIMAL, 9, 15 , 0 , false , false),
		new SchemaColumn("paymentConfigName", DataColumn.STRING, 10, 255 , 0 , false , false),
		new SchemaColumn("paymentFee", DataColumn.BIGDECIMAL, 11, 15 , 0 , false , false),
		new SchemaColumn("paymentStatus", DataColumn.INTEGER, 12, 11 , 0 , false , false),
		new SchemaColumn("point", DataColumn.INTEGER, 13, 11 , 0 , false , false),
		new SchemaColumn("pointFrom", DataColumn.STRING, 14, 255 , 0 , false , false),
		new SchemaColumn("productId", DataColumn.STRING, 15, 255 , 0 , false , false),
		new SchemaColumn("productName", DataColumn.STRING, 16, 255 , 0 , false , false),
		new SchemaColumn("productTotalPrice", DataColumn.BIGDECIMAL, 17, 15 , 0 , false , false),
		new SchemaColumn("productTotalQuantity", DataColumn.INTEGER, 18, 11 , 0 , false , false),
		new SchemaColumn("subRiskTypeCode", DataColumn.STRING, 19, 255 , 0 , false , false),
		new SchemaColumn("totalAmount", DataColumn.BIGDECIMAL, 20, 15 , 0 , false , false),
		new SchemaColumn("insuranceCompanySn", DataColumn.STRING, 21, 255 , 0 , false , false),
		new SchemaColumn("amntPrice", DataColumn.BIGDECIMAL, 22, 19 , 0 , false , false),
		new SchemaColumn("insureStatus", DataColumn.INTEGER, 23, 11 , 0 , false , false),
		new SchemaColumn("policyNumber", DataColumn.STRING, 24, 255 , 0 , false , false),
		new SchemaColumn("outRiskCode", DataColumn.STRING, 25, 255 , 0 , false , false),
		new SchemaColumn("pointStatus", DataColumn.STRING, 26, 255 , 0 , false , false)
	};

	public static final String _TableCode = "orders";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into orders values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update orders set id=?,createDate=?,modifyDate=?,eRiskType=?,insuranceCompany=?,memberId=?,orderSn=?,orderStatus=?,orderValid=?,paidAmount=?,paymentConfigName=?,paymentFee=?,paymentStatus=?,point=?,pointFrom=?,productId=?,productName=?,productTotalPrice=?,productTotalQuantity=?,subRiskTypeCode=?,totalAmount=?,insuranceCompanySn=?,amntPrice=?,insureStatus=?,policyNumber=?,outRiskCode=?,pointStatus=? where id=?";

	protected static final String _DeleteSQL = "delete from orders  where id=?";

	protected static final String _FillAllSQL = "select * from orders  where id=?";

	public ordersSchema(){
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
		return new ordersSchema();
	}

	protected SchemaSet newSet(){
		return new ordersSet();
	}

	public ordersSet query() {
		return query(null, -1, -1);
	}

	public ordersSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ordersSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ordersSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ordersSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){createDate = (Date)v;return;}
		if (i == 2){modifyDate = (Date)v;return;}
		if (i == 3){eRiskType = (String)v;return;}
		if (i == 4){insuranceCompany = (String)v;return;}
		if (i == 5){memberId = (String)v;return;}
		if (i == 6){orderSn = (String)v;return;}
		if (i == 7){if(v==null){orderStatus = null;}else{orderStatus = new Integer(v.toString());}return;}
		if (i == 8){if(v==null){orderValid = null;}else{orderValid = new Boolean(v.toString());}return;}
		if (i == 9){if(v==null){paidAmount = null;}else{paidAmount =  ((BigDecimal)v) ;}return;}
		if (i == 10){paymentConfigName = (String)v;return;}
		if (i == 11){if(v==null){paymentFee = null;}else{paymentFee =  ((BigDecimal)v) ;}return;}
		if (i == 12){if(v==null){paymentStatus = null;}else{paymentStatus = new Integer(v.toString());}return;}
		if (i == 13){if(v==null){point = null;}else{point = new Integer(v.toString());}return;}
		if (i == 14){pointFrom = (String)v;return;}
		if (i == 15){productId = (String)v;return;}
		if (i == 16){productName = (String)v;return;}
		if (i == 17){if(v==null){productTotalPrice = null;}else{productTotalPrice =  ((BigDecimal)v) ;}return;}
		if (i == 18){if(v==null){productTotalQuantity = null;}else{productTotalQuantity = new Integer(v.toString());}return;}
		if (i == 19){subRiskTypeCode = (String)v;return;}
		if (i == 20){if(v==null){totalAmount = null;}else{totalAmount =  ((BigDecimal)v) ;}return;}
		if (i == 21){insuranceCompanySn = (String)v;return;}
		if (i == 22){if(v==null){amntPrice = null;}else{amntPrice =  ((BigDecimal)v) ;}return;}
		if (i == 23){if(v==null){insureStatus = null;}else{insureStatus = new Integer(v.toString());}return;}
		if (i == 24){policyNumber = (String)v;return;}
		if (i == 25){outRiskCode = (String)v;return;}
		if (i == 26){pointStatus = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return createDate;}
		if (i == 2){return modifyDate;}
		if (i == 3){return eRiskType;}
		if (i == 4){return insuranceCompany;}
		if (i == 5){return memberId;}
		if (i == 6){return orderSn;}
		if (i == 7){return orderStatus;}
		if (i == 8){return orderValid;}
		if (i == 9){return paidAmount;}
		if (i == 10){return paymentConfigName;}
		if (i == 11){return paymentFee;}
		if (i == 12){return paymentStatus;}
		if (i == 13){return point;}
		if (i == 14){return pointFrom;}
		if (i == 15){return productId;}
		if (i == 16){return productName;}
		if (i == 17){return productTotalPrice;}
		if (i == 18){return productTotalQuantity;}
		if (i == 19){return subRiskTypeCode;}
		if (i == 20){return totalAmount;}
		if (i == 21){return insuranceCompanySn;}
		if (i == 22){return amntPrice;}
		if (i == 23){return insureStatus;}
		if (i == 24){return policyNumber;}
		if (i == 25){return outRiskCode;}
		if (i == 26){return pointStatus;}
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
	* 获取字段createDate的值，该字段的<br>
	* 字段名称 :createDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getcreateDate() {
		return createDate;
	}

	/**
	* 设置字段createDate的值，该字段的<br>
	* 字段名称 :createDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcreateDate(Date createDate) {
		this.createDate = createDate;
    }

	/**
	* 获取字段modifyDate的值，该字段的<br>
	* 字段名称 :modifyDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getmodifyDate() {
		return modifyDate;
	}

	/**
	* 设置字段modifyDate的值，该字段的<br>
	* 字段名称 :modifyDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmodifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
    }

	/**
	* 获取字段eRiskType的值，该字段的<br>
	* 字段名称 :eRiskType<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String geteRiskType() {
		return eRiskType;
	}

	/**
	* 设置字段eRiskType的值，该字段的<br>
	* 字段名称 :eRiskType<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void seteRiskType(String eRiskType) {
		this.eRiskType = eRiskType;
    }

	/**
	* 获取字段insuranceCompany的值，该字段的<br>
	* 字段名称 :insuranceCompany<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getinsuranceCompany() {
		return insuranceCompany;
	}

	/**
	* 设置字段insuranceCompany的值，该字段的<br>
	* 字段名称 :insuranceCompany<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setinsuranceCompany(String insuranceCompany) {
		this.insuranceCompany = insuranceCompany;
    }

	/**
	* 获取字段memberId的值，该字段的<br>
	* 字段名称 :memberId<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getmemberId() {
		return memberId;
	}

	/**
	* 设置字段memberId的值，该字段的<br>
	* 字段名称 :memberId<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmemberId(String memberId) {
		this.memberId = memberId;
    }

	/**
	* 获取字段orderSn的值，该字段的<br>
	* 字段名称 :orderSn<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getorderSn() {
		return orderSn;
	}

	/**
	* 设置字段orderSn的值，该字段的<br>
	* 字段名称 :orderSn<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setorderSn(String orderSn) {
		this.orderSn = orderSn;
    }

	/**
	* 获取字段orderStatus的值，该字段的<br>
	* 字段名称 :orderStatus<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getorderStatus() {
		if(orderStatus==null){return 0;}
		return orderStatus.intValue();
	}

	/**
	* 设置字段orderStatus的值，该字段的<br>
	* 字段名称 :orderStatus<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setorderStatus(int orderStatus) {
		this.orderStatus = new Integer(orderStatus);
    }

	/**
	* 设置字段orderStatus的值，该字段的<br>
	* 字段名称 :orderStatus<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setorderStatus(String orderStatus) {
		if (orderStatus == null){
			this.orderStatus = null;
			return;
		}
		this.orderStatus = new Integer(orderStatus);
    }

	/**
	* 获取字段orderValid的值，该字段的<br>
	* 字段名称 :orderValid<br>
	* 数据类型 :bit(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public boolean getorderValid() {
		return orderValid;
	}

	/**
	* 设置字段orderValid的值，该字段的<br>
	* 字段名称 :orderValid<br>
	* 数据类型 :bit(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setorderValid(boolean orderValid) {
		this.orderValid = orderValid;
    }

	/**
	* 获取字段paidAmount的值，该字段的<br>
	* 字段名称 :paidAmount<br>
	* 数据类型 :decimal(15)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public BigDecimal getpaidAmount() {
		return paidAmount;
	}

	/**
	* 设置字段paidAmount的值，该字段的<br>
	* 字段名称 :paidAmount<br>
	* 数据类型 :decimal(15)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
    }

	/**
	* 获取字段paymentConfigName的值，该字段的<br>
	* 字段名称 :paymentConfigName<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getpaymentConfigName() {
		return paymentConfigName;
	}

	/**
	* 设置字段paymentConfigName的值，该字段的<br>
	* 字段名称 :paymentConfigName<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpaymentConfigName(String paymentConfigName) {
		this.paymentConfigName = paymentConfigName;
    }

	/**
	* 获取字段paymentFee的值，该字段的<br>
	* 字段名称 :paymentFee<br>
	* 数据类型 :decimal(15)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public BigDecimal getpaymentFee() {
		return paymentFee;
	}

	/**
	* 设置字段paymentFee的值，该字段的<br>
	* 字段名称 :paymentFee<br>
	* 数据类型 :decimal(15)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpaymentFee(BigDecimal paymentFee) {
		this.paymentFee = paymentFee;
    }

	/**
	* 获取字段paymentStatus的值，该字段的<br>
	* 字段名称 :paymentStatus<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getpaymentStatus() {
		if(paymentStatus==null){return 0;}
		return paymentStatus.intValue();
	}

	/**
	* 设置字段paymentStatus的值，该字段的<br>
	* 字段名称 :paymentStatus<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpaymentStatus(int paymentStatus) {
		this.paymentStatus = new Integer(paymentStatus);
    }

	/**
	* 设置字段paymentStatus的值，该字段的<br>
	* 字段名称 :paymentStatus<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpaymentStatus(String paymentStatus) {
		if (paymentStatus == null){
			this.paymentStatus = null;
			return;
		}
		this.paymentStatus = new Integer(paymentStatus);
    }

	/**
	* 获取字段point的值，该字段的<br>
	* 字段名称 :point<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getpoint() {
		if(point==null){return 0;}
		return point.intValue();
	}

	/**
	* 设置字段point的值，该字段的<br>
	* 字段名称 :point<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpoint(int point) {
		this.point = new Integer(point);
    }

	/**
	* 设置字段point的值，该字段的<br>
	* 字段名称 :point<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpoint(String point) {
		if (point == null){
			this.point = null;
			return;
		}
		this.point = new Integer(point);
    }

	/**
	* 获取字段pointFrom的值，该字段的<br>
	* 字段名称 :pointFrom<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getpointFrom() {
		return pointFrom;
	}

	/**
	* 设置字段pointFrom的值，该字段的<br>
	* 字段名称 :pointFrom<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpointFrom(String pointFrom) {
		this.pointFrom = pointFrom;
    }

	/**
	* 获取字段productId的值，该字段的<br>
	* 字段名称 :productId<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getproductId() {
		return productId;
	}

	/**
	* 设置字段productId的值，该字段的<br>
	* 字段名称 :productId<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setproductId(String productId) {
		this.productId = productId;
    }

	/**
	* 获取字段productName的值，该字段的<br>
	* 字段名称 :productName<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getproductName() {
		return productName;
	}

	/**
	* 设置字段productName的值，该字段的<br>
	* 字段名称 :productName<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setproductName(String productName) {
		this.productName = productName;
    }

	/**
	* 获取字段productTotalPrice的值，该字段的<br>
	* 字段名称 :productTotalPrice<br>
	* 数据类型 :decimal(15)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public BigDecimal getproductTotalPrice() {
		return productTotalPrice;
	}

	/**
	* 设置字段productTotalPrice的值，该字段的<br>
	* 字段名称 :productTotalPrice<br>
	* 数据类型 :decimal(15)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setproductTotalPrice(BigDecimal productTotalPrice) {
		this.productTotalPrice = productTotalPrice;
    }

	/**
	* 获取字段productTotalQuantity的值，该字段的<br>
	* 字段名称 :productTotalQuantity<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getproductTotalQuantity() {
		if(productTotalQuantity==null){return 0;}
		return productTotalQuantity.intValue();
	}

	/**
	* 设置字段productTotalQuantity的值，该字段的<br>
	* 字段名称 :productTotalQuantity<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setproductTotalQuantity(int productTotalQuantity) {
		this.productTotalQuantity = new Integer(productTotalQuantity);
    }

	/**
	* 设置字段productTotalQuantity的值，该字段的<br>
	* 字段名称 :productTotalQuantity<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setproductTotalQuantity(String productTotalQuantity) {
		if (productTotalQuantity == null){
			this.productTotalQuantity = null;
			return;
		}
		this.productTotalQuantity = new Integer(productTotalQuantity);
    }

	/**
	* 获取字段subRiskTypeCode的值，该字段的<br>
	* 字段名称 :subRiskTypeCode<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getsubRiskTypeCode() {
		return subRiskTypeCode;
	}

	/**
	* 设置字段subRiskTypeCode的值，该字段的<br>
	* 字段名称 :subRiskTypeCode<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setsubRiskTypeCode(String subRiskTypeCode) {
		this.subRiskTypeCode = subRiskTypeCode;
    }

	/**
	* 获取字段totalAmount的值，该字段的<br>
	* 字段名称 :totalAmount<br>
	* 数据类型 :decimal(15)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public BigDecimal gettotalAmount() {
		return totalAmount;
	}

	/**
	* 设置字段totalAmount的值，该字段的<br>
	* 字段名称 :totalAmount<br>
	* 数据类型 :decimal(15)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void settotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
    }

	/**
	* 获取字段insuranceCompanySn的值，该字段的<br>
	* 字段名称 :insuranceCompanySn<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getinsuranceCompanySn() {
		return insuranceCompanySn;
	}

	/**
	* 设置字段insuranceCompanySn的值，该字段的<br>
	* 字段名称 :insuranceCompanySn<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setinsuranceCompanySn(String insuranceCompanySn) {
		this.insuranceCompanySn = insuranceCompanySn;
    }

	/**
	* 获取字段amntPrice的值，该字段的<br>
	* 字段名称 :amntPrice<br>
	* 数据类型 :decimal(19)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public BigDecimal getamntPrice() {
		return amntPrice;
	}

	/**
	* 设置字段amntPrice的值，该字段的<br>
	* 字段名称 :amntPrice<br>
	* 数据类型 :decimal(19)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setamntPrice(BigDecimal amntPrice) {
		this.amntPrice = amntPrice;
    }

	/**
	* 获取字段insureStatus的值，该字段的<br>
	* 字段名称 :insureStatus<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getinsureStatus() {
		if(insureStatus==null){return 0;}
		return insureStatus.intValue();
	}

	/**
	* 设置字段insureStatus的值，该字段的<br>
	* 字段名称 :insureStatus<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setinsureStatus(int insureStatus) {
		this.insureStatus = new Integer(insureStatus);
    }

	/**
	* 设置字段insureStatus的值，该字段的<br>
	* 字段名称 :insureStatus<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setinsureStatus(String insureStatus) {
		if (insureStatus == null){
			this.insureStatus = null;
			return;
		}
		this.insureStatus = new Integer(insureStatus);
    }

	/**
	* 获取字段policyNumber的值，该字段的<br>
	* 字段名称 :policyNumber<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getpolicyNumber() {
		return policyNumber;
	}

	/**
	* 设置字段policyNumber的值，该字段的<br>
	* 字段名称 :policyNumber<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
    }

	/**
	* 获取字段outRiskCode的值，该字段的<br>
	* 字段名称 :outRiskCode<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getoutRiskCode() {
		return outRiskCode;
	}

	/**
	* 设置字段outRiskCode的值，该字段的<br>
	* 字段名称 :outRiskCode<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setoutRiskCode(String outRiskCode) {
		this.outRiskCode = outRiskCode;
    }

	/**
	* 获取字段pointStatus的值，该字段的<br>
	* 字段名称 :pointStatus<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getpointStatus() {
		return pointStatus;
	}

	/**
	* 设置字段pointStatus的值，该字段的<br>
	* 字段名称 :pointStatus<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpointStatus(String pointStatus) {
		this.pointStatus = pointStatus;
    }

}