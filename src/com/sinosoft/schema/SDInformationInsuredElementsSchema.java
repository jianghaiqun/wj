package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：产品投保要素表<br>
 * 表代码：SDInformationInsuredElements<br>
 * 表主键：id<br>
 */
public class SDInformationInsuredElementsSchema extends Schema {
	private String id;

	private String recognizeeSn;

	private String informationSn;

	private String orderSn;

	private String elementsType;

	private String elementsValue;

	private String elementsSn;

	private Date createDate;

	private Date modifyDate;

	private String sdinformationInsured_id;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 255 , 0 , true , true),
		new SchemaColumn("recognizeeSn", DataColumn.STRING, 1, 255 , 0 , false , false),
		new SchemaColumn("informationSn", DataColumn.STRING, 2, 255 , 0 , false , false),
		new SchemaColumn("orderSn", DataColumn.STRING, 3, 255 , 0 , false , false),
		new SchemaColumn("elementsType", DataColumn.STRING, 4, 255 , 0 , false , false),
		new SchemaColumn("elementsValue", DataColumn.STRING, 5, 255 , 0 , false , false),
		new SchemaColumn("elementsSn", DataColumn.STRING, 6, 255 , 0 , false , false),
		new SchemaColumn("createDate", DataColumn.DATETIME, 7, 0 , 0 , false , false),
		new SchemaColumn("modifyDate", DataColumn.DATETIME, 8, 0 , 0 , false , false),
		new SchemaColumn("sdinformationInsured_id", DataColumn.STRING, 9, 32 , 0 , false , false)
	};

	public static final String _TableCode = "SDInformationInsuredElements";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into SDInformationInsuredElements values(?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update SDInformationInsuredElements set id=?,recognizeeSn=?,informationSn=?,orderSn=?,elementsType=?,elementsValue=?,elementsSn=?,createDate=?,modifyDate=?,sdinformationInsured_id=? where id=?";

	protected static final String _DeleteSQL = "delete from SDInformationInsuredElements  where id=?";

	protected static final String _FillAllSQL = "select * from SDInformationInsuredElements  where id=?";

	public SDInformationInsuredElementsSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[10];
	}

	protected Schema newInstance(){
		return new SDInformationInsuredElementsSchema();
	}

	protected SchemaSet newSet(){
		return new SDInformationInsuredElementsSet();
	}

	public SDInformationInsuredElementsSet query() {
		return query(null, -1, -1);
	}

	public SDInformationInsuredElementsSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public SDInformationInsuredElementsSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public SDInformationInsuredElementsSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (SDInformationInsuredElementsSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){recognizeeSn = (String)v;return;}
		if (i == 2){informationSn = (String)v;return;}
		if (i == 3){orderSn = (String)v;return;}
		if (i == 4){elementsType = (String)v;return;}
		if (i == 5){elementsValue = (String)v;return;}
		if (i == 6){elementsSn = (String)v;return;}
		if (i == 7){createDate = (Date)v;return;}
		if (i == 8){modifyDate = (Date)v;return;}
		if (i == 9){sdinformationInsured_id = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return recognizeeSn;}
		if (i == 2){return informationSn;}
		if (i == 3){return orderSn;}
		if (i == 4){return elementsType;}
		if (i == 5){return elementsValue;}
		if (i == 6){return elementsSn;}
		if (i == 7){return createDate;}
		if (i == 8){return modifyDate;}
		if (i == 9){return sdinformationInsured_id;}
		return null;
	}

	/**
	* 获取字段id的值，该字段的<br>
	* 字段名称 :产品投保要素id<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getid() {
		return id;
	}

	/**
	* 设置字段id的值，该字段的<br>
	* 字段名称 :产品投保要素id<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setid(String id) {
		this.id = id;
    }

	/**
	* 获取字段recognizeeSn的值，该字段的<br>
	* 字段名称 :被保人编号<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeSn() {
		return recognizeeSn;
	}

	/**
	* 设置字段recognizeeSn的值，该字段的<br>
	* 字段名称 :被保人编号<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeSn(String recognizeeSn) {
		this.recognizeeSn = recognizeeSn;
    }

	/**
	* 获取字段informationSn的值，该字段的<br>
	* 字段名称 :订单详细表编码<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getinformationSn() {
		return informationSn;
	}

	/**
	* 设置字段informationSn的值，该字段的<br>
	* 字段名称 :订单详细表编码<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setinformationSn(String informationSn) {
		this.informationSn = informationSn;
    }

	/**
	* 获取字段orderSn的值，该字段的<br>
	* 字段名称 :订单编号<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getorderSn() {
		return orderSn;
	}

	/**
	* 设置字段orderSn的值，该字段的<br>
	* 字段名称 :订单编号<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setorderSn(String orderSn) {
		this.orderSn = orderSn;
    }

	/**
	* 获取字段elementsType的值，该字段的<br>
	* 字段名称 :投保要素类型<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getelementsType() {
		return elementsType;
	}

	/**
	* 设置字段elementsType的值，该字段的<br>
	* 字段名称 :投保要素类型<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setelementsType(String elementsType) {
		this.elementsType = elementsType;
    }

	/**
	* 获取字段elementsValue的值，该字段的<br>
	* 字段名称 :投保要素值<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getelementsValue() {
		return elementsValue;
	}

	/**
	* 设置字段elementsValue的值，该字段的<br>
	* 字段名称 :投保要素值<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setelementsValue(String elementsValue) {
		this.elementsValue = elementsValue;
    }

	/**
	* 获取字段elementsSn的值，该字段的<br>
	* 字段名称 :投保要素-计划编码<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getelementsSn() {
		return elementsSn;
	}

	/**
	* 设置字段elementsSn的值，该字段的<br>
	* 字段名称 :投保要素-计划编码<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setelementsSn(String elementsSn) {
		this.elementsSn = elementsSn;
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
	* 获取字段sdinformationInsured_id的值，该字段的<br>
	* 字段名称 :被保人信息表ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getsdinformationInsured_id() {
		return sdinformationInsured_id;
	}

	/**
	* 设置字段sdinformationInsured_id的值，该字段的<br>
	* 字段名称 :被保人信息表ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setsdinformationInsured_id(String sdinformationInsured_id) {
		this.sdinformationInsured_id = sdinformationInsured_id;
    }

}