package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

import java.util.Date;

/**
 * 表名称：SDMarketing<br>
 * 表代码：SDMarketing<br>
 * 表主键：Id<br>
 */
public class SDMarketingSchema extends Schema {
	private String Id;

	private String PictruePath;

	private String PictrueLink;

	private String WordLeft;

	private String WordLeftColor;

	private String WordLeftBack;

	private String WordRight;

	private String WordRightLink;

	private String Color;

	private String PrePicPath;

	private String PrePicHid;

	private String TxtHtml;

	private String NavHtml;

	private Date CreateDate;

	private Date ModifyDate;

	private String Operater;

	private String Prop1;

	private String Prop2;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("Id", DataColumn.STRING, 0, 10 , 0 , true , true),
		new SchemaColumn("PictruePath", DataColumn.STRING, 1, 100 , 0 , false , false),
		new SchemaColumn("PictrueLink", DataColumn.STRING, 2, 100 , 0 , false , false),
		new SchemaColumn("WordLeft", DataColumn.STRING, 3, 100 , 0 , false , false),
		new SchemaColumn("WordLeftColor", DataColumn.STRING, 4, 10 , 0 , false , false),
		new SchemaColumn("WordLeftBack", DataColumn.STRING, 5, 10 , 0 , false , false),
		new SchemaColumn("WordRight", DataColumn.STRING, 6, 100 , 0 , false , false),
		new SchemaColumn("WordRightLink", DataColumn.STRING, 7, 50 , 0 , false , false),
		new SchemaColumn("Color", DataColumn.STRING, 8, 10 , 0 , false , false),
		new SchemaColumn("PrePicPath", DataColumn.STRING, 9, 100 , 0 , false , false),
		new SchemaColumn("PrePicHid", DataColumn.STRING, 10, 2 , 0 , false , false),
		new SchemaColumn("TxtHtml", DataColumn.STRING, 11, 0 , 0 , false , false),
		new SchemaColumn("NavHtml", DataColumn.STRING, 12, 1000 , 0 , false , false),
		new SchemaColumn("CreateDate", DataColumn.DATETIME, 13, 0 , 0 , false , false),
		new SchemaColumn("ModifyDate", DataColumn.DATETIME, 14, 0 , 0 , false , false),
		new SchemaColumn("Operater", DataColumn.STRING, 15, 20 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 16, 200 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 17, 200 , 0 , false , false)
	};

	public static final String _TableCode = "SDMarketing";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into SDMarketing values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update SDMarketing set Id=?,PictruePath=?,PictrueLink=?,WordLeft=?,WordLeftColor=?,WordLeftBack=?,WordRight=?,WordRightLink=?,Color=?,PrePicPath=?,PrePicHid=?,TxtHtml=?,NavHtml=?,CreateDate=?,ModifyDate=?,Operater=?,Prop1=?,Prop2=? where Id=?";

	protected static final String _DeleteSQL = "delete from SDMarketing  where Id=?";

	protected static final String _FillAllSQL = "select * from SDMarketing  where Id=?";

	public SDMarketingSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[18];
	}

	protected Schema newInstance(){
		return new SDMarketingSchema();
	}

	protected SchemaSet newSet(){
		return new SDMarketingSet();
	}

	public SDMarketingSet query() {
		return query(null, -1, -1);
	}

	public SDMarketingSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public SDMarketingSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public SDMarketingSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (SDMarketingSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){Id = (String)v;return;}
		if (i == 1){PictruePath = (String)v;return;}
		if (i == 2){PictrueLink = (String)v;return;}
		if (i == 3){WordLeft = (String)v;return;}
		if (i == 4){WordLeftColor = (String)v;return;}
		if (i == 5){WordLeftBack = (String)v;return;}
		if (i == 6){WordRight = (String)v;return;}
		if (i == 7){WordRightLink = (String)v;return;}
		if (i == 8){Color = (String)v;return;}
		if (i == 9){PrePicPath = (String)v;return;}
		if (i == 10){PrePicHid = (String)v;return;}
		if (i == 11){TxtHtml = (String)v;return;}
		if (i == 12){NavHtml = (String)v;return;}
		if (i == 13){CreateDate = (Date)v;return;}
		if (i == 14){ModifyDate = (Date)v;return;}
		if (i == 15){Operater = (String)v;return;}
		if (i == 16){Prop1 = (String)v;return;}
		if (i == 17){Prop2 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return Id;}
		if (i == 1){return PictruePath;}
		if (i == 2){return PictrueLink;}
		if (i == 3){return WordLeft;}
		if (i == 4){return WordLeftColor;}
		if (i == 5){return WordLeftBack;}
		if (i == 6){return WordRight;}
		if (i == 7){return WordRightLink;}
		if (i == 8){return Color;}
		if (i == 9){return PrePicPath;}
		if (i == 10){return PrePicHid;}
		if (i == 11){return TxtHtml;}
		if (i == 12){return NavHtml;}
		if (i == 13){return CreateDate;}
		if (i == 14){return ModifyDate;}
		if (i == 15){return Operater;}
		if (i == 16){return Prop1;}
		if (i == 17){return Prop2;}
		return null;
	}

	/**
	* 获取字段Id的值，该字段的<br>
	* 字段名称 :Id<br>
	* 数据类型 :VARCHAR(10)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getId() {
		return Id;
	}

	/**
	* 设置字段Id的值，该字段的<br>
	* 字段名称 :Id<br>
	* 数据类型 :VARCHAR(10)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setId(String id) {
		this.Id = id;
    }

	/**
	* 获取字段PictruePath的值，该字段的<br>
	* 字段名称 :PictruePath<br>
	* 数据类型 :VARCHAR(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPictruePath() {
		return PictruePath;
	}

	/**
	* 设置字段PictruePath的值，该字段的<br>
	* 字段名称 :PictruePath<br>
	* 数据类型 :VARCHAR(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPictruePath(String pictruePath) {
		this.PictruePath = pictruePath;
    }

	/**
	* 获取字段PictrueLink的值，该字段的<br>
	* 字段名称 :PictrueLink<br>
	* 数据类型 :VARCHAR(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPictrueLink() {
		return PictrueLink;
	}

	/**
	* 设置字段PictrueLink的值，该字段的<br>
	* 字段名称 :PictrueLink<br>
	* 数据类型 :VARCHAR(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPictrueLink(String pictrueLink) {
		this.PictrueLink = pictrueLink;
    }

	/**
	* 获取字段WordLeft的值，该字段的<br>
	* 字段名称 :WordLeft<br>
	* 数据类型 :VARCHAR(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getWordLeft() {
		return WordLeft;
	}

	/**
	* 设置字段WordLeft的值，该字段的<br>
	* 字段名称 :WordLeft<br>
	* 数据类型 :VARCHAR(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setWordLeft(String wordLeft) {
		this.WordLeft = wordLeft;
    }

	/**
	* 获取字段WordLeftColor的值，该字段的<br>
	* 字段名称 :WordLeftColor<br>
	* 数据类型 :VARCHAR(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getWordLeftColor() {
		return WordLeftColor;
	}

	/**
	* 设置字段WordLeftColor的值，该字段的<br>
	* 字段名称 :WordLeftColor<br>
	* 数据类型 :VARCHAR(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setWordLeftColor(String wordLeftColor) {
		this.WordLeftColor = wordLeftColor;
    }

	/**
	* 获取字段WordLeftBack的值，该字段的<br>
	* 字段名称 :WordLeftBack<br>
	* 数据类型 :VARCHAR(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getWordLeftBack() {
		return WordLeftBack;
	}

	/**
	* 设置字段WordLeftBack的值，该字段的<br>
	* 字段名称 :WordLeftBack<br>
	* 数据类型 :VARCHAR(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setWordLeftBack(String wordLeftBack) {
		this.WordLeftBack = wordLeftBack;
    }

	/**
	* 获取字段WordRight的值，该字段的<br>
	* 字段名称 :WordRight<br>
	* 数据类型 :VARCHAR(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getWordRight() {
		return WordRight;
	}

	/**
	* 设置字段WordRight的值，该字段的<br>
	* 字段名称 :WordRight<br>
	* 数据类型 :VARCHAR(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setWordRight(String wordRight) {
		this.WordRight = wordRight;
    }

	/**
	* 获取字段WordRightLink的值，该字段的<br>
	* 字段名称 :WordRightLink<br>
	* 数据类型 :VARCHAR(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getWordRightLink() {
		return WordRightLink;
	}

	/**
	* 设置字段WordRightLink的值，该字段的<br>
	* 字段名称 :WordRightLink<br>
	* 数据类型 :VARCHAR(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setWordRightLink(String wordRightLink) {
		this.WordRightLink = wordRightLink;
    }

	/**
	* 获取字段Color的值，该字段的<br>
	* 字段名称 :Color<br>
	* 数据类型 :VARCHAR(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getColor() {
		return Color;
	}

	/**
	* 设置字段Color的值，该字段的<br>
	* 字段名称 :Color<br>
	* 数据类型 :VARCHAR(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setColor(String color) {
		this.Color = color;
    }

	/**
	* 获取字段PrePicPath的值，该字段的<br>
	* 字段名称 :PrePicPath<br>
	* 数据类型 :VARCHAR(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPrePicPath() {
		return PrePicPath;
	}

	/**
	* 设置字段PrePicPath的值，该字段的<br>
	* 字段名称 :PrePicPath<br>
	* 数据类型 :VARCHAR(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPrePicPath(String prePicPath) {
		this.PrePicPath = prePicPath;
    }

	/**
	* 获取字段PrePicHid的值，该字段的<br>
	* 字段名称 :PrePicHid<br>
	* 数据类型 :VARCHAR(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPrePicHid() {
		return PrePicHid;
	}

	/**
	* 设置字段PrePicHid的值，该字段的<br>
	* 字段名称 :PrePicHid<br>
	* 数据类型 :VARCHAR(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPrePicHid(String prePicHid) {
		this.PrePicHid = prePicHid;
    }

	/**
	* 获取字段TxtHtml的值，该字段的<br>
	* 字段名称 :TxtHtml<br>
	* 数据类型 :LONGTEXT<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getTxtHtml() {
		return TxtHtml;
	}

	/**
	* 设置字段TxtHtml的值，该字段的<br>
	* 字段名称 :TxtHtml<br>
	* 数据类型 :LONGTEXT<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setTxtHtml(String txtHtml) {
		this.TxtHtml = txtHtml;
    }

	/**
	* 获取字段NavHtml的值，该字段的<br>
	* 字段名称 :NavHtml<br>
	* 数据类型 :VARCHAR(1000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getNavHtml() {
		return NavHtml;
	}

	/**
	* 设置字段NavHtml的值，该字段的<br>
	* 字段名称 :NavHtml<br>
	* 数据类型 :VARCHAR(1000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setNavHtml(String navHtml) {
		this.NavHtml = navHtml;
    }

	/**
	* 获取字段CreateDate的值，该字段的<br>
	* 字段名称 :CreateDate<br>
	* 数据类型 :DATETIME<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getCreateDate() {
		return CreateDate;
	}

	/**
	* 设置字段CreateDate的值，该字段的<br>
	* 字段名称 :CreateDate<br>
	* 数据类型 :DATETIME<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCreateDate(Date createDate) {
		this.CreateDate = createDate;
    }

	/**
	* 获取字段ModifyDate的值，该字段的<br>
	* 字段名称 :ModifyDate<br>
	* 数据类型 :DATETIME<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getModifyDate() {
		return ModifyDate;
	}

	/**
	* 设置字段ModifyDate的值，该字段的<br>
	* 字段名称 :ModifyDate<br>
	* 数据类型 :DATETIME<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyDate(Date modifyDate) {
		this.ModifyDate = modifyDate;
    }

	/**
	* 获取字段Operater的值，该字段的<br>
	* 字段名称 :Operater<br>
	* 数据类型 :VARCHAR(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getOperater() {
		return Operater;
	}

	/**
	* 设置字段Operater的值，该字段的<br>
	* 字段名称 :Operater<br>
	* 数据类型 :VARCHAR(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setOperater(String operater) {
		this.Operater = operater;
    }

	/**
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :Prop1<br>
	* 数据类型 :VARCHAR(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :Prop1<br>
	* 数据类型 :VARCHAR(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* 获取字段Prop2的值，该字段的<br>
	* 字段名称 :Prop2<br>
	* 数据类型 :VARCHAR(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* 设置字段Prop2的值，该字段的<br>
	* 字段名称 :Prop2<br>
	* 数据类型 :VARCHAR(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

}