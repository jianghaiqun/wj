package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：微信图文信息<br>
 * 表代码：WxResponseInfo<br>
 * 表主键：ID<br>
 */
public class WxResponseInfoSchema extends Schema {
	private String ID;

	private String MenuID;

	private String MenuAttribute;

	private String MenuURL;

	private String ResponseType;

	private String TextContent;

	private String PicTitle;

	private String PicDesc;

	private String PicURL;

	private String piclinkurl;

	private String MusicTitle;

	private String MusicDesc;

	private String MusicURL;

	private String HpMusicURL;

	private Integer TextOrder;

	private Date CreateDate;

	private Date ModifyDate;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("MenuID", DataColumn.STRING, 1, 32 , 0 , false , false),
		new SchemaColumn("MenuAttribute", DataColumn.STRING, 2, 32 , 0 , false , false),
		new SchemaColumn("MenuURL", DataColumn.STRING, 3, 100 , 0 , false , false),
		new SchemaColumn("ResponseType", DataColumn.STRING, 4, 20 , 0 , false , false),
		new SchemaColumn("TextContent", DataColumn.CLOB, 5, 0 , 0 , false , false),
		new SchemaColumn("PicTitle", DataColumn.STRING, 6, 255 , 0 , false , false),
		new SchemaColumn("PicDesc", DataColumn.CLOB, 7, 0 , 0 , false , false),
		new SchemaColumn("PicURL", DataColumn.STRING, 8, 100 , 0 , false , false),
		new SchemaColumn("piclinkurl", DataColumn.STRING, 9, 200 , 0 , false , false),
		new SchemaColumn("MusicTitle", DataColumn.STRING, 10, 255 , 0 , false , false),
		new SchemaColumn("MusicDesc", DataColumn.CLOB, 11, 0 , 0 , false , false),
		new SchemaColumn("MusicURL", DataColumn.STRING, 12, 100 , 0 , false , false),
		new SchemaColumn("HpMusicURL", DataColumn.STRING, 13, 100 , 0 , false , false),
		new SchemaColumn("TextOrder", DataColumn.INTEGER, 14, 10 , 0 , false , false),
		new SchemaColumn("CreateDate", DataColumn.DATETIME, 15, 0 , 0 , false , false),
		new SchemaColumn("ModifyDate", DataColumn.DATETIME, 16, 0 , 0 , false , false)
	};

	public static final String _TableCode = "WxResponseInfo";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into WxResponseInfo values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update WxResponseInfo set ID=?,MenuID=?,MenuAttribute=?,MenuURL=?,ResponseType=?,TextContent=?,PicTitle=?,PicDesc=?,PicURL=?,piclinkurl=?,MusicTitle=?,MusicDesc=?,MusicURL=?,HpMusicURL=?,TextOrder=?,CreateDate=?,ModifyDate=? where ID=?";

	protected static final String _DeleteSQL = "delete from WxResponseInfo  where ID=?";

	protected static final String _FillAllSQL = "select * from WxResponseInfo  where ID=?";

	public WxResponseInfoSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[17];
	}

	protected Schema newInstance(){
		return new WxResponseInfoSchema();
	}

	protected SchemaSet newSet(){
		return new WxResponseInfoSet();
	}

	public WxResponseInfoSet query() {
		return query(null, -1, -1);
	}

	public WxResponseInfoSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public WxResponseInfoSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public WxResponseInfoSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (WxResponseInfoSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){ID = (String)v;return;}
		if (i == 1){MenuID = (String)v;return;}
		if (i == 2){MenuAttribute = (String)v;return;}
		if (i == 3){MenuURL = (String)v;return;}
		if (i == 4){ResponseType = (String)v;return;}
		if (i == 5){TextContent = (String)v;return;}
		if (i == 6){PicTitle = (String)v;return;}
		if (i == 7){PicDesc = (String)v;return;}
		if (i == 8){PicURL = (String)v;return;}
		if (i == 9){piclinkurl = (String)v;return;}
		if (i == 10){MusicTitle = (String)v;return;}
		if (i == 11){MusicDesc = (String)v;return;}
		if (i == 12){MusicURL = (String)v;return;}
		if (i == 13){HpMusicURL = (String)v;return;}
		if (i == 14){if(v==null){TextOrder = null;}else{TextOrder = new Integer(v.toString());}return;}
		if (i == 15){CreateDate = (Date)v;return;}
		if (i == 16){ModifyDate = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return MenuID;}
		if (i == 2){return MenuAttribute;}
		if (i == 3){return MenuURL;}
		if (i == 4){return ResponseType;}
		if (i == 5){return TextContent;}
		if (i == 6){return PicTitle;}
		if (i == 7){return PicDesc;}
		if (i == 8){return PicURL;}
		if (i == 9){return piclinkurl;}
		if (i == 10){return MusicTitle;}
		if (i == 11){return MusicDesc;}
		if (i == 12){return MusicURL;}
		if (i == 13){return HpMusicURL;}
		if (i == 14){return TextOrder;}
		if (i == 15){return CreateDate;}
		if (i == 16){return ModifyDate;}
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
	* 获取字段MenuID的值，该字段的<br>
	* 字段名称 :MenuID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMenuID() {
		return MenuID;
	}

	/**
	* 设置字段MenuID的值，该字段的<br>
	* 字段名称 :MenuID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMenuID(String menuID) {
		this.MenuID = menuID;
    }

	/**
	* 获取字段MenuAttribute的值，该字段的<br>
	* 字段名称 :MenuAttribute<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMenuAttribute() {
		return MenuAttribute;
	}

	/**
	* 设置字段MenuAttribute的值，该字段的<br>
	* 字段名称 :MenuAttribute<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMenuAttribute(String menuAttribute) {
		this.MenuAttribute = menuAttribute;
    }

	/**
	* 获取字段MenuURL的值，该字段的<br>
	* 字段名称 :MenuURL<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMenuURL() {
		return MenuURL;
	}

	/**
	* 设置字段MenuURL的值，该字段的<br>
	* 字段名称 :MenuURL<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMenuURL(String menuURL) {
		this.MenuURL = menuURL;
    }

	/**
	* 获取字段ResponseType的值，该字段的<br>
	* 字段名称 :ResponseType<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getResponseType() {
		return ResponseType;
	}

	/**
	* 设置字段ResponseType的值，该字段的<br>
	* 字段名称 :ResponseType<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setResponseType(String responseType) {
		this.ResponseType = responseType;
    }

	/**
	* 获取字段TextContent的值，该字段的<br>
	* 字段名称 :TextContent<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getTextContent() {
		return TextContent;
	}

	/**
	* 设置字段TextContent的值，该字段的<br>
	* 字段名称 :TextContent<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setTextContent(String textContent) {
		this.TextContent = textContent;
    }

	/**
	* 获取字段PicTitle的值，该字段的<br>
	* 字段名称 :PicTitle<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPicTitle() {
		return PicTitle;
	}

	/**
	* 设置字段PicTitle的值，该字段的<br>
	* 字段名称 :PicTitle<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPicTitle(String picTitle) {
		this.PicTitle = picTitle;
    }

	/**
	* 获取字段PicDesc的值，该字段的<br>
	* 字段名称 :PicDesc<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPicDesc() {
		return PicDesc;
	}

	/**
	* 设置字段PicDesc的值，该字段的<br>
	* 字段名称 :PicDesc<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPicDesc(String picDesc) {
		this.PicDesc = picDesc;
    }

	/**
	* 获取字段PicURL的值，该字段的<br>
	* 字段名称 :PicURL<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPicURL() {
		return PicURL;
	}

	/**
	* 设置字段PicURL的值，该字段的<br>
	* 字段名称 :PicURL<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPicURL(String picURL) {
		this.PicURL = picURL;
    }

	/**
	* 获取字段piclinkurl的值，该字段的<br>
	* 字段名称 :piclinkurl<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getpiclinkurl() {
		return piclinkurl;
	}

	/**
	* 设置字段piclinkurl的值，该字段的<br>
	* 字段名称 :piclinkurl<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpiclinkurl(String piclinkurl) {
		this.piclinkurl = piclinkurl;
    }

	/**
	* 获取字段MusicTitle的值，该字段的<br>
	* 字段名称 :MusicTitle<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMusicTitle() {
		return MusicTitle;
	}

	/**
	* 设置字段MusicTitle的值，该字段的<br>
	* 字段名称 :MusicTitle<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMusicTitle(String musicTitle) {
		this.MusicTitle = musicTitle;
    }

	/**
	* 获取字段MusicDesc的值，该字段的<br>
	* 字段名称 :MusicDesc<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMusicDesc() {
		return MusicDesc;
	}

	/**
	* 设置字段MusicDesc的值，该字段的<br>
	* 字段名称 :MusicDesc<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMusicDesc(String musicDesc) {
		this.MusicDesc = musicDesc;
    }

	/**
	* 获取字段MusicURL的值，该字段的<br>
	* 字段名称 :MusicURL<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMusicURL() {
		return MusicURL;
	}

	/**
	* 设置字段MusicURL的值，该字段的<br>
	* 字段名称 :MusicURL<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMusicURL(String musicURL) {
		this.MusicURL = musicURL;
    }

	/**
	* 获取字段HpMusicURL的值，该字段的<br>
	* 字段名称 :HpMusicURL<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getHpMusicURL() {
		return HpMusicURL;
	}

	/**
	* 设置字段HpMusicURL的值，该字段的<br>
	* 字段名称 :HpMusicURL<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setHpMusicURL(String hpMusicURL) {
		this.HpMusicURL = hpMusicURL;
    }

	/**
	* 获取字段TextOrder的值，该字段的<br>
	* 字段名称 :TextOrder<br>
	* 数据类型 :int(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getTextOrder() {
		if(TextOrder==null){return 0;}
		return TextOrder.intValue();
	}

	/**
	* 设置字段TextOrder的值，该字段的<br>
	* 字段名称 :TextOrder<br>
	* 数据类型 :int(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setTextOrder(int textOrder) {
		this.TextOrder = new Integer(textOrder);
    }

	/**
	* 设置字段TextOrder的值，该字段的<br>
	* 字段名称 :TextOrder<br>
	* 数据类型 :int(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setTextOrder(String textOrder) {
		if (textOrder == null){
			this.TextOrder = null;
			return;
		}
		this.TextOrder = new Integer(textOrder);
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