package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：健康告知表<br>
 * 表代码：SDInsuredHealth<br>
 * 表主键：id<br>
 */
public class SDInsuredHealthSchema extends Schema {
	private String id;

	private Date createDate;

	private Date modifyDate;

	private String orderSn;

	private String informationSn;

	private String recognizeeSn;

	private String productId;

	private String insuranceCompany;

	private Integer showOrder;

	private String showInfo;

	private String uiInfo;

	private String showDistrict;

	private String isMustInput;

	private String isDisplay;

	private String dataType;

	private String showInfoType;

	private String showInfoCode;

	private String backUp1;

	private String selectFlag;

	private String typeShowOrder;

	private String valueTypeFlag;

	private String healthyInfo;

	private Integer healthyInfoNum;

	private String childInfoValue;

	private String healthyInfoId;

	private String sdinformationinsured_id;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("createDate", DataColumn.DATETIME, 1, 0 , 0 , false , false),
		new SchemaColumn("modifyDate", DataColumn.DATETIME, 2, 0 , 0 , false , false),
		new SchemaColumn("orderSn", DataColumn.STRING, 3, 20 , 0 , false , false),
		new SchemaColumn("informationSn", DataColumn.STRING, 4, 20 , 0 , false , false),
		new SchemaColumn("recognizeeSn", DataColumn.STRING, 5, 20 , 0 , false , false),
		new SchemaColumn("productId", DataColumn.STRING, 6, 20 , 0 , false , false),
		new SchemaColumn("insuranceCompany", DataColumn.STRING, 7, 10 , 0 , false , false),
		new SchemaColumn("showOrder", DataColumn.INTEGER, 8, 11 , 0 , false , false),
		new SchemaColumn("showInfo", DataColumn.STRING, 9, 1000 , 0 , false , false),
		new SchemaColumn("uiInfo", DataColumn.STRING, 10, 20 , 0 , false , false),
		new SchemaColumn("showDistrict", DataColumn.STRING, 11, 50 , 0 , false , false),
		new SchemaColumn("isMustInput", DataColumn.STRING, 12, 5 , 0 , false , false),
		new SchemaColumn("isDisplay", DataColumn.STRING, 13, 5 , 0 , false , false),
		new SchemaColumn("dataType", DataColumn.STRING, 14, 5 , 0 , false , false),
		new SchemaColumn("showInfoType", DataColumn.STRING, 15, 20 , 0 , false , false),
		new SchemaColumn("showInfoCode", DataColumn.STRING, 16, 30 , 0 , false , false),
		new SchemaColumn("backUp1", DataColumn.STRING, 17, 5 , 0 , false , false),
		new SchemaColumn("selectFlag", DataColumn.STRING, 18, 30 , 0 , false , false),
		new SchemaColumn("typeShowOrder", DataColumn.STRING, 19, 30 , 0 , false , false),
		new SchemaColumn("valueTypeFlag", DataColumn.STRING, 20, 30 , 0 , false , false),
		new SchemaColumn("healthyInfo", DataColumn.STRING, 21, 30 , 0 , false , false),
		new SchemaColumn("healthyInfoNum", DataColumn.INTEGER, 22, 11 , 0 , false , false),
		new SchemaColumn("childInfoValue", DataColumn.STRING, 23, 30 , 0 , false , false),
		new SchemaColumn("healthyInfoId", DataColumn.STRING, 24, 30 , 0 , false , false),
		new SchemaColumn("sdinformationinsured_id", DataColumn.STRING, 25, 32 , 0 , false , false)
	};

	public static final String _TableCode = "SDInsuredHealth";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into SDInsuredHealth values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update SDInsuredHealth set id=?,createDate=?,modifyDate=?,orderSn=?,informationSn=?,recognizeeSn=?,productId=?,insuranceCompany=?,showOrder=?,showInfo=?,uiInfo=?,showDistrict=?,isMustInput=?,isDisplay=?,dataType=?,showInfoType=?,showInfoCode=?,backUp1=?,selectFlag=?,typeShowOrder=?,valueTypeFlag=?,healthyInfo=?,healthyInfoNum=?,childInfoValue=?,healthyInfoId=?,sdinformationinsured_id=? where id=?";

	protected static final String _DeleteSQL = "delete from SDInsuredHealth  where id=?";

	protected static final String _FillAllSQL = "select * from SDInsuredHealth  where id=?";

	public SDInsuredHealthSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[26];
	}

	protected Schema newInstance(){
		return new SDInsuredHealthSchema();
	}

	protected SchemaSet newSet(){
		return new SDInsuredHealthSet();
	}

	public SDInsuredHealthSet query() {
		return query(null, -1, -1);
	}

	public SDInsuredHealthSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public SDInsuredHealthSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public SDInsuredHealthSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (SDInsuredHealthSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){createDate = (Date)v;return;}
		if (i == 2){modifyDate = (Date)v;return;}
		if (i == 3){orderSn = (String)v;return;}
		if (i == 4){informationSn = (String)v;return;}
		if (i == 5){recognizeeSn = (String)v;return;}
		if (i == 6){productId = (String)v;return;}
		if (i == 7){insuranceCompany = (String)v;return;}
		if (i == 8){if(v==null){showOrder = null;}else{showOrder = new Integer(v.toString());}return;}
		if (i == 9){showInfo = (String)v;return;}
		if (i == 10){uiInfo = (String)v;return;}
		if (i == 11){showDistrict = (String)v;return;}
		if (i == 12){isMustInput = (String)v;return;}
		if (i == 13){isDisplay = (String)v;return;}
		if (i == 14){dataType = (String)v;return;}
		if (i == 15){showInfoType = (String)v;return;}
		if (i == 16){showInfoCode = (String)v;return;}
		if (i == 17){backUp1 = (String)v;return;}
		if (i == 18){selectFlag = (String)v;return;}
		if (i == 19){typeShowOrder = (String)v;return;}
		if (i == 20){valueTypeFlag = (String)v;return;}
		if (i == 21){healthyInfo = (String)v;return;}
		if (i == 22){if(v==null){healthyInfoNum = null;}else{healthyInfoNum = new Integer(v.toString());}return;}
		if (i == 23){childInfoValue = (String)v;return;}
		if (i == 24){healthyInfoId = (String)v;return;}
		if (i == 25){sdinformationinsured_id = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return createDate;}
		if (i == 2){return modifyDate;}
		if (i == 3){return orderSn;}
		if (i == 4){return informationSn;}
		if (i == 5){return recognizeeSn;}
		if (i == 6){return productId;}
		if (i == 7){return insuranceCompany;}
		if (i == 8){return showOrder;}
		if (i == 9){return showInfo;}
		if (i == 10){return uiInfo;}
		if (i == 11){return showDistrict;}
		if (i == 12){return isMustInput;}
		if (i == 13){return isDisplay;}
		if (i == 14){return dataType;}
		if (i == 15){return showInfoType;}
		if (i == 16){return showInfoCode;}
		if (i == 17){return backUp1;}
		if (i == 18){return selectFlag;}
		if (i == 19){return typeShowOrder;}
		if (i == 20){return valueTypeFlag;}
		if (i == 21){return healthyInfo;}
		if (i == 22){return healthyInfoNum;}
		if (i == 23){return childInfoValue;}
		if (i == 24){return healthyInfoId;}
		if (i == 25){return sdinformationinsured_id;}
		return null;
	}

	/**
	* 获取字段id的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getid() {
		return id;
	}

	/**
	* 设置字段id的值，该字段的<br>
	* 字段名称 :ID<br>
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
	* 获取字段orderSn的值，该字段的<br>
	* 字段名称 :订单编号<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getorderSn() {
		return orderSn;
	}

	/**
	* 设置字段orderSn的值，该字段的<br>
	* 字段名称 :订单编号<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setorderSn(String orderSn) {
		this.orderSn = orderSn;
    }

	/**
	* 获取字段informationSn的值，该字段的<br>
	* 字段名称 :订单详细表编码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getinformationSn() {
		return informationSn;
	}

	/**
	* 设置字段informationSn的值，该字段的<br>
	* 字段名称 :订单详细表编码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setinformationSn(String informationSn) {
		this.informationSn = informationSn;
    }

	/**
	* 获取字段recognizeeSn的值，该字段的<br>
	* 字段名称 :被保人编号<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeSn() {
		return recognizeeSn;
	}

	/**
	* 设置字段recognizeeSn的值，该字段的<br>
	* 字段名称 :被保人编号<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeSn(String recognizeeSn) {
		this.recognizeeSn = recognizeeSn;
    }

	/**
	* 获取字段productId的值，该字段的<br>
	* 字段名称 :产品编码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getproductId() {
		return productId;
	}

	/**
	* 设置字段productId的值，该字段的<br>
	* 字段名称 :产品编码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setproductId(String productId) {
		this.productId = productId;
    }

	/**
	* 获取字段insuranceCompany的值，该字段的<br>
	* 字段名称 :保险公司<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getinsuranceCompany() {
		return insuranceCompany;
	}

	/**
	* 设置字段insuranceCompany的值，该字段的<br>
	* 字段名称 :保险公司<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setinsuranceCompany(String insuranceCompany) {
		this.insuranceCompany = insuranceCompany;
    }

	/**
	* 获取字段showOrder的值，该字段的<br>
	* 字段名称 :信息序号<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getshowOrder() {
		if(showOrder==null){return 0;}
		return showOrder.intValue();
	}

	/**
	* 设置字段showOrder的值，该字段的<br>
	* 字段名称 :信息序号<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setshowOrder(int showOrder) {
		this.showOrder = new Integer(showOrder);
    }

	/**
	* 设置字段showOrder的值，该字段的<br>
	* 字段名称 :信息序号<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setshowOrder(String showOrder) {
		if (showOrder == null){
			this.showOrder = null;
			return;
		}
		this.showOrder = new Integer(showOrder);
    }

	/**
	* 获取字段showInfo的值，该字段的<br>
	* 字段名称 :信息描述<br>
	* 数据类型 :varchar(1000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getshowInfo() {
		return showInfo;
	}

	/**
	* 设置字段showInfo的值，该字段的<br>
	* 字段名称 :信息描述<br>
	* 数据类型 :varchar(1000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setshowInfo(String showInfo) {
		this.showInfo = showInfo;
    }

	/**
	* 获取字段uiInfo的值，该字段的<br>
	* 字段名称 :界面显示信息<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getuiInfo() {
		return uiInfo;
	}

	/**
	* 设置字段uiInfo的值，该字段的<br>
	* 字段名称 :界面显示信息<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setuiInfo(String uiInfo) {
		this.uiInfo = uiInfo;
    }

	/**
	* 获取字段showDistrict的值，该字段的<br>
	* 字段名称 :显示区域<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getshowDistrict() {
		return showDistrict;
	}

	/**
	* 设置字段showDistrict的值，该字段的<br>
	* 字段名称 :显示区域<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setshowDistrict(String showDistrict) {
		this.showDistrict = showDistrict;
    }

	/**
	* 获取字段isMustInput的值，该字段的<br>
	* 字段名称 :是否必填<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getisMustInput() {
		return isMustInput;
	}

	/**
	* 设置字段isMustInput的值，该字段的<br>
	* 字段名称 :是否必填<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setisMustInput(String isMustInput) {
		this.isMustInput = isMustInput;
    }

	/**
	* 获取字段isDisplay的值，该字段的<br>
	* 字段名称 :是否显示<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getisDisplay() {
		return isDisplay;
	}

	/**
	* 设置字段isDisplay的值，该字段的<br>
	* 字段名称 :是否显示<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setisDisplay(String isDisplay) {
		this.isDisplay = isDisplay;
    }

	/**
	* 获取字段dataType的值，该字段的<br>
	* 字段名称 :数据类型表<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getdataType() {
		return dataType;
	}

	/**
	* 设置字段dataType的值，该字段的<br>
	* 字段名称 :数据类型表<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setdataType(String dataType) {
		this.dataType = dataType;
    }

	/**
	* 获取字段showInfoType的值，该字段的<br>
	* 字段名称 :健康告知类型<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getshowInfoType() {
		return showInfoType;
	}

	/**
	* 设置字段showInfoType的值，该字段的<br>
	* 字段名称 :健康告知类型<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setshowInfoType(String showInfoType) {
		this.showInfoType = showInfoType;
    }

	/**
	* 获取字段showInfoCode的值，该字段的<br>
	* 字段名称 :健康告知编码<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getshowInfoCode() {
		return showInfoCode;
	}

	/**
	* 设置字段showInfoCode的值，该字段的<br>
	* 字段名称 :健康告知编码<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setshowInfoCode(String showInfoCode) {
		this.showInfoCode = showInfoCode;
    }

	/**
	* 获取字段backUp1的值，该字段的<br>
	* 字段名称 :同个健康告知类型下的数量总和<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getbackUp1() {
		return backUp1;
	}

	/**
	* 设置字段backUp1的值，该字段的<br>
	* 字段名称 :同个健康告知类型下的数量总和<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setbackUp1(String backUp1) {
		this.backUp1 = backUp1;
    }

	/**
	* 获取字段selectFlag的值，该字段的<br>
	* 字段名称 :选择标志<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getselectFlag() {
		return selectFlag;
	}

	/**
	* 设置字段selectFlag的值，该字段的<br>
	* 字段名称 :选择标志<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setselectFlag(String selectFlag) {
		this.selectFlag = selectFlag;
    }

	/**
	* 获取字段typeShowOrder的值，该字段的<br>
	* 字段名称 :类型序号<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String gettypeShowOrder() {
		return typeShowOrder;
	}

	/**
	* 设置字段typeShowOrder的值，该字段的<br>
	* 字段名称 :类型序号<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void settypeShowOrder(String typeShowOrder) {
		this.typeShowOrder = typeShowOrder;
    }

	/**
	* 获取字段valueTypeFlag的值，该字段的<br>
	* 字段名称 :是否有关联下级健康信息<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getvalueTypeFlag() {
		return valueTypeFlag;
	}

	/**
	* 设置字段valueTypeFlag的值，该字段的<br>
	* 字段名称 :是否有关联下级健康信息<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setvalueTypeFlag(String valueTypeFlag) {
		this.valueTypeFlag = valueTypeFlag;
    }

	/**
	* 获取字段healthyInfo的值，该字段的<br>
	* 字段名称 :子集健康信息<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String gethealthyInfo() {
		return healthyInfo;
	}

	/**
	* 设置字段healthyInfo的值，该字段的<br>
	* 字段名称 :子集健康信息<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void sethealthyInfo(String healthyInfo) {
		this.healthyInfo = healthyInfo;
    }

	/**
	* 获取字段healthyInfoNum的值，该字段的<br>
	* 字段名称 :需要填写值的数量<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int gethealthyInfoNum() {
		if(healthyInfoNum==null){return 0;}
		return healthyInfoNum.intValue();
	}

	/**
	* 设置字段healthyInfoNum的值，该字段的<br>
	* 字段名称 :需要填写值的数量<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void sethealthyInfoNum(int healthyInfoNum) {
		this.healthyInfoNum = new Integer(healthyInfoNum);
    }

	/**
	* 设置字段healthyInfoNum的值，该字段的<br>
	* 字段名称 :需要填写值的数量<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void sethealthyInfoNum(String healthyInfoNum) {
		if (healthyInfoNum == null){
			this.healthyInfoNum = null;
			return;
		}
		this.healthyInfoNum = new Integer(healthyInfoNum);
    }

	/**
	* 获取字段childInfoValue的值，该字段的<br>
	* 字段名称 :子集健康告知值<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getchildInfoValue() {
		return childInfoValue;
	}

	/**
	* 设置字段childInfoValue的值，该字段的<br>
	* 字段名称 :子集健康告知值<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setchildInfoValue(String childInfoValue) {
		this.childInfoValue = childInfoValue;
    }

	/**
	* 获取字段healthyInfoId的值，该字段的<br>
	* 字段名称 :关联同步健康告知<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String gethealthyInfoId() {
		return healthyInfoId;
	}

	/**
	* 设置字段healthyInfoId的值，该字段的<br>
	* 字段名称 :关联同步健康告知<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void sethealthyInfoId(String healthyInfoId) {
		this.healthyInfoId = healthyInfoId;
    }

	/**
	* 获取字段sdinformationinsured_id的值，该字段的<br>
	* 字段名称 :sdinformationinsured_id<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getsdinformationinsured_id() {
		return sdinformationinsured_id;
	}

	/**
	* 设置字段sdinformationinsured_id的值，该字段的<br>
	* 字段名称 :sdinformationinsured_id<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setsdinformationinsured_id(String sdinformationinsured_id) {
		this.sdinformationinsured_id = sdinformationinsured_id;
    }

}