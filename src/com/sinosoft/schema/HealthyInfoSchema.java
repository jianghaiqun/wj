package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：健康告知表<br>
 * 表代码：HealthyInfo<br>
 * 表主键：Id<br>
 */
public class HealthyInfoSchema extends Schema {
	private static final long serialVersionUID = -2284308239159830175L;

	private String Id; 

	private Date createDate;

	private Date modifyDate;

	private String insuranceCompany;

	private String productId;

	private Integer showOrder;

	private String showInfo;

	private String UIInfo;

	private String dataType;

	private String IsDisplay;

	private String IsMustInput;

	private String showDistrict;

	private String showInfoType;

	private String showInfoCode;

	private String BackUp1;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("Id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("createDate", DataColumn.DATETIME, 1, 0 , 0 , true , false),
		new SchemaColumn("modifyDate", DataColumn.DATETIME, 2, 0 , 0 , false , false),
		new SchemaColumn("insuranceCompany", DataColumn.STRING, 3, 255 , 0 , false , false),
		new SchemaColumn("productId", DataColumn.STRING, 4, 255 , 0 , false , false),
		new SchemaColumn("showOrder", DataColumn.INTEGER, 5, 0 , 0 , false , false),
		new SchemaColumn("showInfo", DataColumn.STRING, 6, 1000 , 0 , false , false),
		new SchemaColumn("UIInfo", DataColumn.STRING, 7, 255 , 0 , false , false),
		new SchemaColumn("dataType", DataColumn.STRING, 8, 255 , 0 , false , false),
		new SchemaColumn("IsDisplay", DataColumn.STRING, 9, 255 , 0 , false , false),
		new SchemaColumn("IsMustInput", DataColumn.STRING, 10, 255 , 0 , false , false),
		new SchemaColumn("showDistrict", DataColumn.STRING, 11, 255 , 0 , false , false),
		new SchemaColumn("showInfoType", DataColumn.STRING, 12, 255 , 0 , false , false),
		new SchemaColumn("showInfoCode", DataColumn.STRING, 13, 255 , 0 , false , false),
		new SchemaColumn("BackUp1", DataColumn.STRING, 14, 255 , 0 , false , false)
	};

	public static final String _TableCode = "HealthyInfo";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into HealthyInfo values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update HealthyInfo set Id=?,createDate=?,modifyDate=?,insuranceCompany=?,productId=?,showOrder=?,showInfo=?,UIInfo=?,dataType=?,IsDisplay=?,IsMustInput=?,showDistrict=?,showInfoType=?,showInfoCode=?,BackUp1=? where Id=?";

	protected static final String _DeleteSQL = "delete from HealthyInfo  where Id=?";

	protected static final String _FillAllSQL = "select * from HealthyInfo  where Id=?";

	public HealthyInfoSchema(){
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
		return new HealthyInfoSchema();
	}

	protected SchemaSet newSet(){
		return new HealthyInfoSet();
	}

	public HealthyInfoSet query() {
		return query(null, -1, -1);
	}

	public HealthyInfoSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public HealthyInfoSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public HealthyInfoSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (HealthyInfoSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){Id = (String)v;return;}
		if (i == 1){createDate = (Date)v;return;}
		if (i == 2){modifyDate = (Date)v;return;}
		if (i == 3){insuranceCompany = (String)v;return;}
		if (i == 4){productId = (String)v;return;}
		if (i == 5){if(v==null){showOrder = null;}else{showOrder = new Integer(v.toString());}return;}
		if (i == 6){showInfo = (String)v;return;}
		if (i == 7){UIInfo = (String)v;return;}
		if (i == 8){dataType = (String)v;return;}
		if (i == 9){IsDisplay = (String)v;return;}
		if (i == 10){IsMustInput = (String)v;return;}
		if (i == 11){showDistrict = (String)v;return;}
		if (i == 12){showInfoType = (String)v;return;}
		if (i == 13){showInfoCode = (String)v;return;}
		if (i == 14){BackUp1 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return Id;}
		if (i == 1){return createDate;}
		if (i == 2){return modifyDate;}
		if (i == 3){return insuranceCompany;}
		if (i == 4){return productId;}
		if (i == 5){return showOrder;}
		if (i == 6){return showInfo;}
		if (i == 7){return UIInfo;}
		if (i == 8){return dataType;}
		if (i == 9){return IsDisplay;}
		if (i == 10){return IsMustInput;}
		if (i == 11){return showDistrict;}
		if (i == 12){return showInfoType;}
		if (i == 13){return showInfoCode;}
		if (i == 14){return BackUp1;}
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
	* 获取字段createDate的值，该字段的<br>
	* 字段名称 :createDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public Date getcreateDate() {
		return createDate;
	}

	/**
	* 设置字段createDate的值，该字段的<br>
	* 字段名称 :createDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
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
	* 获取字段insuranceCompany的值，该字段的<br>
	* 字段名称 :保险公司<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getinsuranceCompany() {
		return insuranceCompany;
	}

	/**
	* 设置字段insuranceCompany的值，该字段的<br>
	* 字段名称 :保险公司<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setinsuranceCompany(String insuranceCompany) {
		this.insuranceCompany = insuranceCompany;
    }

	/**
	* 获取字段productId的值，该字段的<br>
	* 字段名称 :产品编码<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getproductId() {
		return productId;
	}

	/**
	* 设置字段productId的值，该字段的<br>
	* 字段名称 :产品编码<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setproductId(String productId) {
		this.productId = productId;
    }

	/**
	* 获取字段showOrder的值，该字段的<br>
	* 字段名称 :信息序号<br>
	* 数据类型 :int<br>
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
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setshowOrder(int showOrder) {
		this.showOrder = new Integer(showOrder);
    }

	/**
	* 设置字段showOrder的值，该字段的<br>
	* 字段名称 :信息序号<br>
	* 数据类型 :int<br>
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
	* 获取字段UIInfo的值，该字段的<br>
	* 字段名称 :界面显示信息<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getUIInfo() {
		return UIInfo;
	}

	/**
	* 设置字段UIInfo的值，该字段的<br>
	* 字段名称 :界面显示信息<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setUIInfo(String uIInfo) {
		this.UIInfo = uIInfo;
    }

	/**
	* 获取字段dataType的值，该字段的<br>
	* 字段名称 :数据类型表<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getdataType() {
		return dataType;
	}

	/**
	* 设置字段dataType的值，该字段的<br>
	* 字段名称 :数据类型表<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setdataType(String dataType) {
		this.dataType = dataType;
    }

	/**
	* 获取字段IsDisplay的值，该字段的<br>
	* 字段名称 :是否显示<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getIsDisplay() {
		return IsDisplay;
	}

	/**
	* 设置字段IsDisplay的值，该字段的<br>
	* 字段名称 :是否显示<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setIsDisplay(String isDisplay) {
		this.IsDisplay = isDisplay;
    }

	/**
	* 获取字段IsMustInput的值，该字段的<br>
	* 字段名称 :是否必填<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getIsMustInput() {
		return IsMustInput;
	}

	/**
	* 设置字段IsMustInput的值，该字段的<br>
	* 字段名称 :是否必填<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setIsMustInput(String isMustInput) {
		this.IsMustInput = isMustInput;
    }

	/**
	* 获取字段showDistrict的值，该字段的<br>
	* 字段名称 :显示区域<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getshowDistrict() {
		return showDistrict;
	}

	/**
	* 设置字段showDistrict的值，该字段的<br>
	* 字段名称 :显示区域<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setshowDistrict(String showDistrict) {
		this.showDistrict = showDistrict;
    }

	/**
	* 获取字段showInfoType的值，该字段的<br>
	* 字段名称 :健康告知类型<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getshowInfoType() {
		return showInfoType;
	}

	/**
	* 设置字段showInfoType的值，该字段的<br>
	* 字段名称 :健康告知类型<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setshowInfoType(String showInfoType) {
		this.showInfoType = showInfoType;
    }

	/**
	* 获取字段showInfoCode的值，该字段的<br>
	* 字段名称 :健康告知编码<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getshowInfoCode() {
		return showInfoCode;
	}

	/**
	* 设置字段showInfoCode的值，该字段的<br>
	* 字段名称 :健康告知编码<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setshowInfoCode(String showInfoCode) {
		this.showInfoCode = showInfoCode;
    }

	/**
	* 获取字段BackUp1的值，该字段的<br>
	* 字段名称 :同个健康告知类型下的数量总和<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getBackUp1() {
		return BackUp1;
	}

	/**
	* 设置字段BackUp1的值，该字段的<br>
	* 字段名称 :同个健康告知类型下的数量总和<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setBackUp1(String backUp1) {
		this.BackUp1 = backUp1;
    }
}