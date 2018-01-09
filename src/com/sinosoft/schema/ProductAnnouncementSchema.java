package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：产品公告信息<br>
 * 表代码：ProductAnnouncement<br>
 * 表主键：ID<br>
 */
public class ProductAnnouncementSchema extends Schema {
	private String ID;

	private String ComCode;

	private String ProductID;

	private String Info;

	private Date StartDate;

	private Date EndDate;

	private String ViewFlag;

	private String Color;

	private Integer InfoOrder;

	private String Remark;

	private Date CreateDate;

	private String CreateUser;

	private Date ModifyDate;

	private String ModifyUser;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("ComCode", DataColumn.STRING, 1, 20 , 0 , false , false),
		new SchemaColumn("ProductID", DataColumn.STRING, 2, 20 , 0 , false , false),
		new SchemaColumn("Info", DataColumn.STRING, 3, 500 , 0 , false , false),
		new SchemaColumn("StartDate", DataColumn.DATETIME, 4, 0 , 0 , false , false),
		new SchemaColumn("EndDate", DataColumn.DATETIME, 5, 0 , 0 , false , false),
		new SchemaColumn("ViewFlag", DataColumn.STRING, 6, 2 , 0 , false , false),
		new SchemaColumn("Color", DataColumn.STRING, 7, 20 , 0 , false , false),
		new SchemaColumn("InfoOrder", DataColumn.INTEGER, 8, 0 , 0 , false , false),
		new SchemaColumn("Remark", DataColumn.STRING, 9, 255 , 0 , false , false),
		new SchemaColumn("CreateDate", DataColumn.DATETIME, 10, 0 , 0 , false , false),
		new SchemaColumn("CreateUser", DataColumn.STRING, 11, 20 , 0 , false , false),
		new SchemaColumn("ModifyDate", DataColumn.DATETIME, 12, 0 , 0 , false , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 13, 20 , 0 , false , false)
	};

	public static final String _TableCode = "ProductAnnouncement";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into ProductAnnouncement values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ProductAnnouncement set ID=?,ComCode=?,ProductID=?,Info=?,StartDate=?,EndDate=?,ViewFlag=?,Color=?,InfoOrder=?,Remark=?,CreateDate=?,CreateUser=?,ModifyDate=?,ModifyUser=? where ID=?";

	protected static final String _DeleteSQL = "delete from ProductAnnouncement  where ID=?";

	protected static final String _FillAllSQL = "select * from ProductAnnouncement  where ID=?";

	public ProductAnnouncementSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[14];
	}

	protected Schema newInstance(){
		return new ProductAnnouncementSchema();
	}

	protected SchemaSet newSet(){
		return new ProductAnnouncementSet();
	}

	public ProductAnnouncementSet query() {
		return query(null, -1, -1);
	}

	public ProductAnnouncementSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ProductAnnouncementSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ProductAnnouncementSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ProductAnnouncementSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){ID = (String)v;return;}
		if (i == 1){ComCode = (String)v;return;}
		if (i == 2){ProductID = (String)v;return;}
		if (i == 3){Info = (String)v;return;}
		if (i == 4){StartDate = (Date)v;return;}
		if (i == 5){EndDate = (Date)v;return;}
		if (i == 6){ViewFlag = (String)v;return;}
		if (i == 7){Color = (String)v;return;}
		if (i == 8){if(v==null){InfoOrder = null;}else{InfoOrder = new Integer(v.toString());}return;}
		if (i == 9){Remark = (String)v;return;}
		if (i == 10){CreateDate = (Date)v;return;}
		if (i == 11){CreateUser = (String)v;return;}
		if (i == 12){ModifyDate = (Date)v;return;}
		if (i == 13){ModifyUser = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return ComCode;}
		if (i == 2){return ProductID;}
		if (i == 3){return Info;}
		if (i == 4){return StartDate;}
		if (i == 5){return EndDate;}
		if (i == 6){return ViewFlag;}
		if (i == 7){return Color;}
		if (i == 8){return InfoOrder;}
		if (i == 9){return Remark;}
		if (i == 10){return CreateDate;}
		if (i == 11){return CreateUser;}
		if (i == 12){return ModifyDate;}
		if (i == 13){return ModifyUser;}
		return null;
	}

	/**
	* 获取字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getID() {
		return ID;
	}

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setID(String iD) {
		this.ID = iD;
    }

	/**
	* 获取字段ComCode的值，该字段的<br>
	* 字段名称 :保险公司<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getComCode() {
		return ComCode;
	}

	/**
	* 设置字段ComCode的值，该字段的<br>
	* 字段名称 :保险公司<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setComCode(String comCode) {
		this.ComCode = comCode;
    }

	/**
	* 获取字段ProductID的值，该字段的<br>
	* 字段名称 :产品ID<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProductID() {
		return ProductID;
	}

	/**
	* 设置字段ProductID的值，该字段的<br>
	* 字段名称 :产品ID<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProductID(String productID) {
		this.ProductID = productID;
    }

	/**
	* 获取字段Info的值，该字段的<br>
	* 字段名称 :公告内容<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getInfo() {
		return Info;
	}

	/**
	* 设置字段Info的值，该字段的<br>
	* 字段名称 :公告内容<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setInfo(String info) {
		this.Info = info;
    }

	/**
	* 获取字段StartDate的值，该字段的<br>
	* 字段名称 :公告开始时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getStartDate() {
		return StartDate;
	}

	/**
	* 设置字段StartDate的值，该字段的<br>
	* 字段名称 :公告开始时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setStartDate(Date startDate) {
		this.StartDate = startDate;
    }

	/**
	* 获取字段EndDate的值，该字段的<br>
	* 字段名称 :公告结束时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getEndDate() {
		return EndDate;
	}

	/**
	* 设置字段EndDate的值，该字段的<br>
	* 字段名称 :公告结束时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setEndDate(Date endDate) {
		this.EndDate = endDate;
    }

	/**
	* 获取字段ViewFlag的值，该字段的<br>
	* 字段名称 :是否启用<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getViewFlag() {
		return ViewFlag;
	}

	/**
	* 设置字段ViewFlag的值，该字段的<br>
	* 字段名称 :是否启用<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setViewFlag(String viewFlag) {
		this.ViewFlag = viewFlag;
    }

	/**
	* 获取字段Color的值，该字段的<br>
	* 字段名称 :字体颜色<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getColor() {
		return Color;
	}

	/**
	* 设置字段Color的值，该字段的<br>
	* 字段名称 :字体颜色<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setColor(String color) {
		this.Color = color;
    }

	/**
	* 获取字段InfoOrder的值，该字段的<br>
	* 字段名称 :显示顺序<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getInfoOrder() {
		if(InfoOrder==null){return 0;}
		return InfoOrder.intValue();
	}

	/**
	* 设置字段InfoOrder的值，该字段的<br>
	* 字段名称 :显示顺序<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setInfoOrder(int infoOrder) {
		this.InfoOrder = new Integer(infoOrder);
    }

	/**
	* 设置字段InfoOrder的值，该字段的<br>
	* 字段名称 :显示顺序<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setInfoOrder(String infoOrder) {
		if (infoOrder == null){
			this.InfoOrder = null;
			return;
		}
		this.InfoOrder = new Integer(infoOrder);
    }

	/**
	* 获取字段Remark的值，该字段的<br>
	* 字段名称 :备用<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getRemark() {
		return Remark;
	}

	/**
	* 设置字段Remark的值，该字段的<br>
	* 字段名称 :备用<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRemark(String remark) {
		this.Remark = remark;
    }

	/**
	* 获取字段CreateDate的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getCreateDate() {
		return CreateDate;
	}

	/**
	* 设置字段CreateDate的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCreateDate(Date createDate) {
		this.CreateDate = createDate;
    }

	/**
	* 获取字段CreateUser的值，该字段的<br>
	* 字段名称 :创建者<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCreateUser() {
		return CreateUser;
	}

	/**
	* 设置字段CreateUser的值，该字段的<br>
	* 字段名称 :创建者<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCreateUser(String createUser) {
		this.CreateUser = createUser;
    }

	/**
	* 获取字段ModifyDate的值，该字段的<br>
	* 字段名称 :修改时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getModifyDate() {
		return ModifyDate;
	}

	/**
	* 设置字段ModifyDate的值，该字段的<br>
	* 字段名称 :修改时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyDate(Date modifyDate) {
		this.ModifyDate = modifyDate;
    }

	/**
	* 获取字段ModifyUser的值，该字段的<br>
	* 字段名称 :修改者<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getModifyUser() {
		return ModifyUser;
	}

	/**
	* 设置字段ModifyUser的值，该字段的<br>
	* 字段名称 :修改者<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyUser(String modifyUser) {
		this.ModifyUser = modifyUser;
    }

}