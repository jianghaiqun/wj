package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：营销推广配置表<br>
 * 表代码：MarketingConfig<br>
 * 表主键：Serial<br>
 */
public class MarketingConfigSchema extends Schema {
	private String Serial;

	private String LandingPage;

	private String MarketingCode;

	private Date AddTime;

	private Date ModifyTime;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("Serial", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("LandingPage", DataColumn.STRING, 1, 50 , 0 , false , false),
		new SchemaColumn("MarketingCode", DataColumn.STRING, 2, 20 , 0 , false , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 3, 0 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 4, 0 , 0 , false , false)
	};

	public static final String _TableCode = "MarketingConfig";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into MarketingConfig values(?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update MarketingConfig set Serial=?,LandingPage=?,MarketingCode=?,AddTime=?,ModifyTime=? where Serial=?";

	protected static final String _DeleteSQL = "delete from MarketingConfig  where Serial=?";

	protected static final String _FillAllSQL = "select * from MarketingConfig  where Serial=?";

	public MarketingConfigSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[5];
	}

	protected Schema newInstance(){
		return new MarketingConfigSchema();
	}

	protected SchemaSet newSet(){
		return new MarketingConfigSet();
	}

	public MarketingConfigSet query() {
		return query(null, -1, -1);
	}

	public MarketingConfigSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public MarketingConfigSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public MarketingConfigSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (MarketingConfigSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){Serial = (String)v;return;}
		if (i == 1){LandingPage = (String)v;return;}
		if (i == 2){MarketingCode = (String)v;return;}
		if (i == 3){AddTime = (Date)v;return;}
		if (i == 4){ModifyTime = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return Serial;}
		if (i == 1){return LandingPage;}
		if (i == 2){return MarketingCode;}
		if (i == 3){return AddTime;}
		if (i == 4){return ModifyTime;}
		return null;
	}

	/**
	* 获取字段Serial的值，该字段的<br>
	* 字段名称 :序列号<br>
	* 数据类型 :VARCHAR2(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getSerial() {
		return Serial;
	}

	/**
	* 设置字段Serial的值，该字段的<br>
	* 字段名称 :序列号<br>
	* 数据类型 :VARCHAR2(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setSerial(String serial) {
		this.Serial = serial;
    }

	/**
	* 获取字段LandingPage的值，该字段的<br>
	* 字段名称 :着陆地址<br>
	* 数据类型 :VARCHAR2(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getLandingPage() {
		return LandingPage;
	}

	/**
	* 设置字段LandingPage的值，该字段的<br>
	* 字段名称 :着陆地址<br>
	* 数据类型 :VARCHAR2(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setLandingPage(String landingPage) {
		this.LandingPage = landingPage;
    }

	/**
	* 获取字段MarketingCode的值，该字段的<br>
	* 字段名称 :所属营销活动编码<br>
	* 数据类型 :VARCHAR2(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMarketingCode() {
		return MarketingCode;
	}

	/**
	* 设置字段MarketingCode的值，该字段的<br>
	* 字段名称 :所属营销活动编码<br>
	* 数据类型 :VARCHAR2(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMarketingCode(String marketingCode) {
		this.MarketingCode = marketingCode;
    }

	/**
	* 获取字段AddTime的值，该字段的<br>
	* 字段名称 :配置时间<br>
	* 数据类型 :DATE<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getAddTime() {
		return AddTime;
	}

	/**
	* 设置字段AddTime的值，该字段的<br>
	* 字段名称 :配置时间<br>
	* 数据类型 :DATE<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAddTime(Date addTime) {
		this.AddTime = addTime;
    }

	/**
	* 获取字段ModifyTime的值，该字段的<br>
	* 字段名称 :修改时间<br>
	* 数据类型 :DATE<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getModifyTime() {
		return ModifyTime;
	}

	/**
	* 设置字段ModifyTime的值，该字段的<br>
	* 字段名称 :修改时间<br>
	* 数据类型 :DATE<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyTime(Date modifyTime) {
		this.ModifyTime = modifyTime;
    }

}