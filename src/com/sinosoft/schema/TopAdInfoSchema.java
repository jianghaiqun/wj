package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：TOP广告信息<br>
 * 表代码：TopAdInfo<br>
 * 表主键：ID<br>
 */
public class TopAdInfoSchema extends Schema {
	private String ID;

	private String AdName;

	private String AdType;

	private String Active;

	private String AdContent;

	private String PicLinkURL;

	private String PicDesc;

	private String PicOpenType;

	private String ParentID;

	private String Author;

	private Date StartTime;

	private Date EndTime;

	private String Prop1;

	private String Prop2;

	private String CreateDate;

	private String ModifyDate;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("AdName", DataColumn.STRING, 1, 100 , 0 , false , false),
		new SchemaColumn("AdType", DataColumn.STRING, 2, 10 , 0 , false , false),
		new SchemaColumn("Active", DataColumn.STRING, 3, 10 , 0 , false , false),
		new SchemaColumn("AdContent", DataColumn.CLOB, 4, 0 , 0 , false , false),
		new SchemaColumn("PicLinkURL", DataColumn.STRING, 5, 100 , 0 , false , false),
		new SchemaColumn("PicDesc", DataColumn.CLOB, 6, 0 , 0 , false , false),
		new SchemaColumn("PicOpenType", DataColumn.STRING, 7, 10 , 0 , false , false),
		new SchemaColumn("ParentID", DataColumn.STRING, 8, 32 , 0 , false , false),
		new SchemaColumn("Author", DataColumn.STRING, 9, 32 , 0 , false , false),
		new SchemaColumn("StartTime", DataColumn.DATETIME, 10, 0 , 0 , false , false),
		new SchemaColumn("EndTime", DataColumn.DATETIME, 11, 0 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.CLOB, 12, 0 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.CLOB, 13, 0 , 0 , false , false),
		new SchemaColumn("CreateDate", DataColumn.STRING, 14, 32 , 0 , false , false),
		new SchemaColumn("ModifyDate", DataColumn.STRING, 15, 32 , 0 , false , false)
	};

	public static final String _TableCode = "TopAdInfo";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into TopAdInfo values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update TopAdInfo set ID=?,AdName=?,AdType=?,Active=?,AdContent=?,PicLinkURL=?,PicDesc=?,PicOpenType=?,ParentID=?,Author=?,StartTime=?,EndTime=?,Prop1=?,Prop2=?,CreateDate=?,ModifyDate=? where ID=?";

	protected static final String _DeleteSQL = "delete from TopAdInfo  where ID=?";

	protected static final String _FillAllSQL = "select * from TopAdInfo  where ID=?";

	public TopAdInfoSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[16];
	}

	protected Schema newInstance(){
		return new TopAdInfoSchema();
	}

	protected SchemaSet newSet(){
		return new TopAdInfoSet();
	}

	public TopAdInfoSet query() {
		return query(null, -1, -1);
	}

	public TopAdInfoSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public TopAdInfoSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public TopAdInfoSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (TopAdInfoSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){ID = (String)v;return;}
		if (i == 1){AdName = (String)v;return;}
		if (i == 2){AdType = (String)v;return;}
		if (i == 3){Active = (String)v;return;}
		if (i == 4){AdContent = (String)v;return;}
		if (i == 5){PicLinkURL = (String)v;return;}
		if (i == 6){PicDesc = (String)v;return;}
		if (i == 7){PicOpenType = (String)v;return;}
		if (i == 8){ParentID = (String)v;return;}
		if (i == 9){Author = (String)v;return;}
		if (i == 10){StartTime = (Date)v;return;}
		if (i == 11){EndTime = (Date)v;return;}
		if (i == 12){Prop1 = (String)v;return;}
		if (i == 13){Prop2 = (String)v;return;}
		if (i == 14){CreateDate = (String)v;return;}
		if (i == 15){ModifyDate = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return AdName;}
		if (i == 2){return AdType;}
		if (i == 3){return Active;}
		if (i == 4){return AdContent;}
		if (i == 5){return PicLinkURL;}
		if (i == 6){return PicDesc;}
		if (i == 7){return PicOpenType;}
		if (i == 8){return ParentID;}
		if (i == 9){return Author;}
		if (i == 10){return StartTime;}
		if (i == 11){return EndTime;}
		if (i == 12){return Prop1;}
		if (i == 13){return Prop2;}
		if (i == 14){return CreateDate;}
		if (i == 15){return ModifyDate;}
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
	* 获取字段AdName的值，该字段的<br>
	* 字段名称 :广告名称<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAdName() {
		return AdName;
	}

	/**
	* 设置字段AdName的值，该字段的<br>
	* 字段名称 :广告名称<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAdName(String adName) {
		this.AdName = adName;
    }

	/**
	* 获取字段AdType的值，该字段的<br>
	* 字段名称 :广告类型<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAdType() {
		return AdType;
	}

	/**
	* 设置字段AdType的值，该字段的<br>
	* 字段名称 :广告类型<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAdType(String adType) {
		this.AdType = adType;
    }

	/**
	* 获取字段Active的值，该字段的<br>
	* 字段名称 :是否有效活动<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getActive() {
		return Active;
	}

	/**
	* 设置字段Active的值，该字段的<br>
	* 字段名称 :是否有效活动<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setActive(String active) {
		this.Active = active;
    }

	/**
	* 获取字段AdContent的值，该字段的<br>
	* 字段名称 :广告内容<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAdContent() {
		return AdContent;
	}

	/**
	* 设置字段AdContent的值，该字段的<br>
	* 字段名称 :广告内容<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAdContent(String adContent) {
		this.AdContent = adContent;
    }

	/**
	* 获取字段PicLinkURL的值，该字段的<br>
	* 字段名称 :图片链接地址<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPicLinkURL() {
		return PicLinkURL;
	}

	/**
	* 设置字段PicLinkURL的值，该字段的<br>
	* 字段名称 :图片链接地址<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPicLinkURL(String picLinkURL) {
		this.PicLinkURL = picLinkURL;
    }

	/**
	* 获取字段PicDesc的值，该字段的<br>
	* 字段名称 :图片描述<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPicDesc() {
		return PicDesc;
	}

	/**
	* 设置字段PicDesc的值，该字段的<br>
	* 字段名称 :图片描述<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPicDesc(String picDesc) {
		this.PicDesc = picDesc;
    }

	/**
	* 获取字段PicOpenType的值，该字段的<br>
	* 字段名称 :图片链接打开方式<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPicOpenType() {
		return PicOpenType;
	}

	/**
	* 设置字段PicOpenType的值，该字段的<br>
	* 字段名称 :图片链接打开方式<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPicOpenType(String picOpenType) {
		this.PicOpenType = picOpenType;
    }

	/**
	* 获取字段ParentID的值，该字段的<br>
	* 字段名称 :所属广告位<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getParentID() {
		return ParentID;
	}

	/**
	* 设置字段ParentID的值，该字段的<br>
	* 字段名称 :所属广告位<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setParentID(String parentID) {
		this.ParentID = parentID;
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
	* 获取字段StartTime的值，该字段的<br>
	* 字段名称 :起始时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getStartTime() {
		return StartTime;
	}

	/**
	* 设置字段StartTime的值，该字段的<br>
	* 字段名称 :起始时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setStartTime(Date startTime) {
		this.StartTime = startTime;
    }

	/**
	* 获取字段EndTime的值，该字段的<br>
	* 字段名称 :结束时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getEndTime() {
		return EndTime;
	}

	/**
	* 设置字段EndTime的值，该字段的<br>
	* 字段名称 :结束时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setEndTime(Date endTime) {
		this.EndTime = endTime;
    }

	/**
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :Prop1<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :Prop1<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* 获取字段Prop2的值，该字段的<br>
	* 字段名称 :Prop2<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* 设置字段Prop2的值，该字段的<br>
	* 字段名称 :Prop2<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
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

}