package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：微信激活码信息信息<br>
 * 表代码：WxActiveCodeInfo<br>
 * 表主键：ActiveCode<br>
 */
public class WxActiveCodeInfoSchema extends Schema {
	private String ActiveCode;

	private String ReceiveCode;

	private String DestinationCode;

	private String DestinationText;

	private String ReceiveFlag;

	private String OpenID;

	private String Prop1;

	private String Prop2;

	private Date CreateDate;

	private Date ModifyDate;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ActiveCode", DataColumn.STRING, 0, 200 , 0 , true , true),
		new SchemaColumn("ReceiveCode", DataColumn.STRING, 1, 20 , 0 , false , false),
		new SchemaColumn("DestinationCode", DataColumn.STRING, 2, 200 , 0 , false , false),
		new SchemaColumn("DestinationText", DataColumn.STRING, 3, 20 , 0 , false , false),
		new SchemaColumn("ReceiveFlag", DataColumn.STRING, 4, 2 , 0 , false , false),
		new SchemaColumn("OpenID", DataColumn.STRING, 5, 200 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 6, 255 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 7, 255 , 0 , false , false),
		new SchemaColumn("CreateDate", DataColumn.DATETIME, 8, 0 , 0 , false , false),
		new SchemaColumn("ModifyDate", DataColumn.DATETIME, 9, 0 , 0 , false , false)
	};

	public static final String _TableCode = "WxActiveCodeInfo";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into WxActiveCodeInfo values(?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update WxActiveCodeInfo set ActiveCode=?,ReceiveCode=?,DestinationCode=?,DestinationText=?,ReceiveFlag=?,OpenID=?,Prop1=?,Prop2=?,CreateDate=?,ModifyDate=? where ActiveCode=?";

	protected static final String _DeleteSQL = "delete from WxActiveCodeInfo  where ActiveCode=?";

	protected static final String _FillAllSQL = "select * from WxActiveCodeInfo  where ActiveCode=?";

	public WxActiveCodeInfoSchema(){
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
		return new WxActiveCodeInfoSchema();
	}

	protected SchemaSet newSet(){
		return new WxActiveCodeInfoSet();
	}

	public WxActiveCodeInfoSet query() {
		return query(null, -1, -1);
	}

	public WxActiveCodeInfoSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public WxActiveCodeInfoSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public WxActiveCodeInfoSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (WxActiveCodeInfoSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){ActiveCode = (String)v;return;}
		if (i == 1){ReceiveCode = (String)v;return;}
		if (i == 2){DestinationCode = (String)v;return;}
		if (i == 3){DestinationText = (String)v;return;}
		if (i == 4){ReceiveFlag = (String)v;return;}
		if (i == 5){OpenID = (String)v;return;}
		if (i == 6){Prop1 = (String)v;return;}
		if (i == 7){Prop2 = (String)v;return;}
		if (i == 8){CreateDate = (Date)v;return;}
		if (i == 9){ModifyDate = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ActiveCode;}
		if (i == 1){return ReceiveCode;}
		if (i == 2){return DestinationCode;}
		if (i == 3){return DestinationText;}
		if (i == 4){return ReceiveFlag;}
		if (i == 5){return OpenID;}
		if (i == 6){return Prop1;}
		if (i == 7){return Prop2;}
		if (i == 8){return CreateDate;}
		if (i == 9){return ModifyDate;}
		return null;
	}

	/**
	* 获取字段ActiveCode的值，该字段的<br>
	* 字段名称 :激活码<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getActiveCode() {
		return ActiveCode;
	}

	/**
	* 设置字段ActiveCode的值，该字段的<br>
	* 字段名称 :激活码<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setActiveCode(String activeCode) {
		this.ActiveCode = activeCode;
    }

	/**
	* 获取字段ReceiveCode的值，该字段的<br>
	* 字段名称 :领取地编码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getReceiveCode() {
		return ReceiveCode;
	}

	/**
	* 设置字段ReceiveCode的值，该字段的<br>
	* 字段名称 :领取地编码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setReceiveCode(String receiveCode) {
		this.ReceiveCode = receiveCode;
    }

	/**
	* 获取字段DestinationCode的值，该字段的<br>
	* 字段名称 :目的地编码<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getDestinationCode() {
		return DestinationCode;
	}

	/**
	* 设置字段DestinationCode的值，该字段的<br>
	* 字段名称 :目的地编码<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDestinationCode(String destinationCode) {
		this.DestinationCode = destinationCode;
    }

	/**
	* 获取字段DestinationText的值，该字段的<br>
	* 字段名称 :目的地名称<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getDestinationText() {
		return DestinationText;
	}

	/**
	* 设置字段DestinationText的值，该字段的<br>
	* 字段名称 :目的地名称<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDestinationText(String destinationText) {
		this.DestinationText = destinationText;
    }

	/**
	* 获取字段ReceiveFlag的值，该字段的<br>
	* 字段名称 :是否已领取<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getReceiveFlag() {
		return ReceiveFlag;
	}

	/**
	* 设置字段ReceiveFlag的值，该字段的<br>
	* 字段名称 :是否已领取<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setReceiveFlag(String receiveFlag) {
		this.ReceiveFlag = receiveFlag;
    }

	/**
	* 获取字段OpenID的值，该字段的<br>
	* 字段名称 :已发送的粉丝<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getOpenID() {
		return OpenID;
	}

	/**
	* 设置字段OpenID的值，该字段的<br>
	* 字段名称 :已发送的粉丝<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setOpenID(String openID) {
		this.OpenID = openID;
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