package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：wap图片上传<br>
 * 表代码：WapPictureUrl<br>
 * 表主键：ID<br>
 */
public class WapPictureUrlSchema extends Schema {
	private String ID;

	private String RiskCode;

	private String Type;

	private String PictureUrl;

	private Long OrderFlag;

	private Date CreateDate;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	private String Prop4;

	private String Prop5;

	private String Prop6;

	private String Prop7;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.STRING, 0, 50 , 0 , true , true),
		new SchemaColumn("RiskCode", DataColumn.STRING, 1, 500 , 0 , true , false),
		new SchemaColumn("Type", DataColumn.STRING, 2, 500 , 0 , true , false),
		new SchemaColumn("PictureUrl", DataColumn.STRING, 3, 500 , 0 , true , false),
		new SchemaColumn("OrderFlag", DataColumn.LONG, 4, 20 , 0 , true , false),
		new SchemaColumn("CreateDate", DataColumn.DATETIME, 5, 0 , 0 , true , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 6, 500 , 0 , true , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 7, 500 , 0 , true , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 8, 500 , 0 , true , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 9, 500 , 0 , true , false),
		new SchemaColumn("Prop5", DataColumn.STRING, 10, 500 , 0 , true , false),
		new SchemaColumn("Prop6", DataColumn.STRING, 11, 500 , 0 , true , false),
		new SchemaColumn("Prop7", DataColumn.STRING, 12, 500 , 0 , true , false)
	};

	public static final String _TableCode = "WapPictureUrl";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into WapPictureUrl values(?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update WapPictureUrl set ID=?,RiskCode=?,Type=?,PictureUrl=?,OrderFlag=?,CreateDate=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,Prop5=?,Prop6=?,Prop7=? where ID=?";

	protected static final String _DeleteSQL = "delete from WapPictureUrl  where ID=?";

	protected static final String _FillAllSQL = "select * from WapPictureUrl  where ID=?";

	public WapPictureUrlSchema(){
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
		return new WapPictureUrlSchema();
	}

	protected SchemaSet newSet(){
		return new WapPictureUrlSet();
	}

	public WapPictureUrlSet query() {
		return query(null, -1, -1);
	}

	public WapPictureUrlSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public WapPictureUrlSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public WapPictureUrlSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (WapPictureUrlSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){ID = (String)v;return;}
		if (i == 1){RiskCode = (String)v;return;}
		if (i == 2){Type = (String)v;return;}
		if (i == 3){PictureUrl = (String)v;return;}
		if (i == 4){if(v==null){OrderFlag = null;}else{OrderFlag = new Long(v.toString());}return;}
		if (i == 5){CreateDate = (Date)v;return;}
		if (i == 6){Prop1 = (String)v;return;}
		if (i == 7){Prop2 = (String)v;return;}
		if (i == 8){Prop3 = (String)v;return;}
		if (i == 9){Prop4 = (String)v;return;}
		if (i == 10){Prop5 = (String)v;return;}
		if (i == 11){Prop6 = (String)v;return;}
		if (i == 12){Prop7 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return RiskCode;}
		if (i == 2){return Type;}
		if (i == 3){return PictureUrl;}
		if (i == 4){return OrderFlag;}
		if (i == 5){return CreateDate;}
		if (i == 6){return Prop1;}
		if (i == 7){return Prop2;}
		if (i == 8){return Prop3;}
		if (i == 9){return Prop4;}
		if (i == 10){return Prop5;}
		if (i == 11){return Prop6;}
		if (i == 12){return Prop7;}
		return null;
	}

	/**
	* 获取字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getID() {
		return ID;
	}

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setID(String iD) {
		this.ID = iD;
    }

	/**
	* 获取字段RiskCode的值，该字段的<br>
	* 字段名称 :产品编码<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getRiskCode() {
		return RiskCode;
	}

	/**
	* 设置字段RiskCode的值，该字段的<br>
	* 字段名称 :产品编码<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setRiskCode(String riskCode) {
		this.RiskCode = riskCode;
    }

	/**
	* 获取字段Type的值，该字段的<br>
	* 字段名称 :类型<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getType() {
		return Type;
	}

	/**
	* 设置字段Type的值，该字段的<br>
	* 字段名称 :类型<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setType(String type) {
		this.Type = type;
    }

	/**
	* 获取字段PictureUrl的值，该字段的<br>
	* 字段名称 :路径<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getPictureUrl() {
		return PictureUrl;
	}

	/**
	* 设置字段PictureUrl的值，该字段的<br>
	* 字段名称 :路径<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setPictureUrl(String pictureUrl) {
		this.PictureUrl = pictureUrl;
    }

	/**
	* 获取字段OrderFlag的值，该字段的<br>
	* 字段名称 :排序<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getOrderFlag() {
		if(OrderFlag==null){return 0;}
		return OrderFlag.longValue();
	}

	/**
	* 设置字段OrderFlag的值，该字段的<br>
	* 字段名称 :排序<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setOrderFlag(long orderFlag) {
		this.OrderFlag = new Long(orderFlag);
    }

	/**
	* 设置字段OrderFlag的值，该字段的<br>
	* 字段名称 :排序<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setOrderFlag(String orderFlag) {
		if (orderFlag == null){
			this.OrderFlag = null;
			return;
		}
		this.OrderFlag = new Long(orderFlag);
    }

	/**
	* 获取字段CreateDate的值，该字段的<br>
	* 字段名称 :创建日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public Date getCreateDate() {
		return CreateDate;
	}

	/**
	* 设置字段CreateDate的值，该字段的<br>
	* 字段名称 :创建日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setCreateDate(Date createDate) {
		this.CreateDate = createDate;
    }

	/**
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* 获取字段Prop2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* 设置字段Prop2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

	/**
	* 获取字段Prop3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getProp3() {
		return Prop3;
	}

	/**
	* 设置字段Prop3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setProp3(String prop3) {
		this.Prop3 = prop3;
    }

	/**
	* 获取字段Prop4的值，该字段的<br>
	* 字段名称 :备用字段4<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getProp4() {
		return Prop4;
	}

	/**
	* 设置字段Prop4的值，该字段的<br>
	* 字段名称 :备用字段4<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setProp4(String prop4) {
		this.Prop4 = prop4;
    }

	/**
	* 获取字段Prop5的值，该字段的<br>
	* 字段名称 :备用字段5<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getProp5() {
		return Prop5;
	}

	/**
	* 设置字段Prop5的值，该字段的<br>
	* 字段名称 :备用字段5<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setProp5(String prop5) {
		this.Prop5 = prop5;
    }

	/**
	* 获取字段Prop6的值，该字段的<br>
	* 字段名称 :备用字段6<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getProp6() {
		return Prop6;
	}

	/**
	* 设置字段Prop6的值，该字段的<br>
	* 字段名称 :备用字段6<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setProp6(String prop6) {
		this.Prop6 = prop6;
    }

	/**
	* 获取字段Prop7的值，该字段的<br>
	* 字段名称 :备用字段7<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getProp7() {
		return Prop7;
	}

	/**
	* 设置字段Prop7的值，该字段的<br>
	* 字段名称 :备用字段7<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setProp7(String prop7) {
		this.Prop7 = prop7;
    }

}