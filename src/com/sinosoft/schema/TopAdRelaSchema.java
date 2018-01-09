package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

/**
 * 表名称：TOP广告位信息<br>
 * 表代码：TopAdRela<br>
 * 表主键：ID<br>
 */
public class TopAdRelaSchema extends Schema {
	private String ID;

	private String AdSpaceName;

	private String AdSpaceType;

	private String AdSpaceDesc;

	private String Author;

	private String Prop1;

	private String CreateDate;

	private String ModifyDate;

	private String AdvertiseWidth;

	private String AdvertiseHeight;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("AdSpaceName", DataColumn.STRING, 1, 100 , 0 , false , false),
		new SchemaColumn("AdSpaceType", DataColumn.STRING, 2, 10 , 0 , false , false),
		new SchemaColumn("AdSpaceDesc", DataColumn.CLOB, 3, 0 , 0 , false , false),
		new SchemaColumn("Author", DataColumn.STRING, 4, 32 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 5, 100 , 0 , false , false),
		new SchemaColumn("CreateDate", DataColumn.STRING, 6, 32 , 0 , false , false),
		new SchemaColumn("ModifyDate", DataColumn.STRING, 7, 32 , 0 , false , false),
		new SchemaColumn("AdvertiseWidth", DataColumn.STRING, 8, 10 , 0 , false , false),
		new SchemaColumn("AdvertiseHeight", DataColumn.STRING, 9, 10 , 0 , false , false)
	};

	public static final String _TableCode = "TopAdRela";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into TopAdRela values(?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update TopAdRela set ID=?,AdSpaceName=?,AdSpaceType=?,AdSpaceDesc=?,Author=?,Prop1=?,CreateDate=?,ModifyDate=?,AdvertiseWidth=?,AdvertiseHeight=? where ID=?";

	protected static final String _DeleteSQL = "delete from TopAdRela  where ID=?";

	protected static final String _FillAllSQL = "select * from TopAdRela  where ID=?";

	public TopAdRelaSchema(){
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
		return new TopAdRelaSchema();
	}

	protected SchemaSet newSet(){
		return new TopAdRelaSet();
	}

	public TopAdRelaSet query() {
		return query(null, -1, -1);
	}

	public TopAdRelaSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public TopAdRelaSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public TopAdRelaSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (TopAdRelaSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){ID = (String)v;return;}
		if (i == 1){AdSpaceName = (String)v;return;}
		if (i == 2){AdSpaceType = (String)v;return;}
		if (i == 3){AdSpaceDesc = (String)v;return;}
		if (i == 4){Author = (String)v;return;}
		if (i == 5){Prop1 = (String)v;return;}
		if (i == 6){CreateDate = (String)v;return;}
		if (i == 7){ModifyDate = (String)v;return;}
		if (i == 8){AdvertiseWidth = (String)v;return;}
		if (i == 9){AdvertiseHeight = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return AdSpaceName;}
		if (i == 2){return AdSpaceType;}
		if (i == 3){return AdSpaceDesc;}
		if (i == 4){return Author;}
		if (i == 5){return Prop1;}
		if (i == 6){return CreateDate;}
		if (i == 7){return ModifyDate;}
		if (i == 8){return AdvertiseWidth;}
		if (i == 9){return AdvertiseHeight;}
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
	* 获取字段AdSpaceName的值，该字段的<br>
	* 字段名称 :广告位名称<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAdSpaceName() {
		return AdSpaceName;
	}

	/**
	* 设置字段AdSpaceName的值，该字段的<br>
	* 字段名称 :广告位名称<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAdSpaceName(String adSpaceName) {
		this.AdSpaceName = adSpaceName;
    }

	/**
	* 获取字段AdSpaceType的值，该字段的<br>
	* 字段名称 :广告位类型<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAdSpaceType() {
		return AdSpaceType;
	}

	/**
	* 设置字段AdSpaceType的值，该字段的<br>
	* 字段名称 :广告位类型<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAdSpaceType(String adSpaceType) {
		this.AdSpaceType = adSpaceType;
    }

	/**
	* 获取字段AdSpaceDesc的值，该字段的<br>
	* 字段名称 :广告位描述<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAdSpaceDesc() {
		return AdSpaceDesc;
	}

	/**
	* 设置字段AdSpaceDesc的值，该字段的<br>
	* 字段名称 :广告位描述<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAdSpaceDesc(String adSpaceDesc) {
		this.AdSpaceDesc = adSpaceDesc;
    }

	/**
	* 获取字段Author的值，该字段的<br>
	* 字段名称 :作者<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAuthor() {
		return Author;
	}

	/**
	* 设置字段Author的值，该字段的<br>
	* 字段名称 :作者<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAuthor(String author) {
		this.Author = author;
    }

	/**
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :Prop1<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :Prop1<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* 获取字段CreateDate的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCreateDate() {
		return CreateDate;
	}

	/**
	* 设置字段CreateDate的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCreateDate(String createDate) {
		this.CreateDate = createDate;
    }

	/**
	* 获取字段ModifyDate的值，该字段的<br>
	* 字段名称 :修改时间<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getModifyDate() {
		return ModifyDate;
	}

	/**
	* 设置字段ModifyDate的值，该字段的<br>
	* 字段名称 :修改时间<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyDate(String modifyDate) {
		this.ModifyDate = modifyDate;
    }

	/**
	* 获取字段AdvertiseWidth的值，该字段的<br>
	* 字段名称 :宽<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAdvertiseWidth() {
		return AdvertiseWidth;
	}

	/**
	* 设置字段AdvertiseWidth的值，该字段的<br>
	* 字段名称 :宽<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAdvertiseWidth(String advertiseWidth) {
		this.AdvertiseWidth = advertiseWidth;
    }

	/**
	* 获取字段AdvertiseHeight的值，该字段的<br>
	* 字段名称 :高<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAdvertiseHeight() {
		return AdvertiseHeight;
	}

	/**
	* 设置字段AdvertiseHeight的值，该字段的<br>
	* 字段名称 :高<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAdvertiseHeight(String advertiseHeight) {
		this.AdvertiseHeight = advertiseHeight;
    }

}