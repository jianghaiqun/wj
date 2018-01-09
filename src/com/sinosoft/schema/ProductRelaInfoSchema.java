package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：ProductRelaInfo<br>
 * 表代码：ProductRelaInfo<br>
 * 表主键：ProductID<br>
 */
public class ProductRelaInfoSchema extends Schema {
	private String ProductID;

	private Date MakeDate;

	private Date Modifydate;  

	private String IsPublish;

	private String RecommendFlag;

	private String HotFlag;

	private String Preferential;

	private Date StartDate;

	private Date EndDate;
	
	private String wapPictureUrl;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	private String Prop4;

	private String Prop5;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ProductID", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("MakeDate", DataColumn.DATETIME, 1, 0 , 0 , false , false),
		new SchemaColumn("Modifydate", DataColumn.DATETIME, 2, 0 , 0 , false , false),
		new SchemaColumn("IsPublish", DataColumn.STRING, 3, 10 , 0 , false , false),
		new SchemaColumn("RecommendFlag", DataColumn.STRING, 4, 32 , 0 , false , false),
		new SchemaColumn("HotFlag", DataColumn.STRING, 5, 10 , 0 , false , false),
		new SchemaColumn("Preferential", DataColumn.STRING, 6, 10 , 0 , false , false),
		new SchemaColumn("StartDate", DataColumn.DATETIME, 7, 0 , 0 , false , false),
		new SchemaColumn("EndDate", DataColumn.DATETIME, 8, 0 , 0 , false , false),
		new SchemaColumn("wapPictureUrl", DataColumn.STRING, 9, 255 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 10, 32 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 11, 32 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 12, 32 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 13, 32 , 0 , false , false),
		new SchemaColumn("Prop5", DataColumn.STRING, 14, 32 , 0 , false , false)
	};

	public static final String _TableCode = "ProductRelaInfo";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into ProductRelaInfo values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ProductRelaInfo set ProductID=?,MakeDate=?,Modifydate=?,IsPublish=?,RecommendFlag=?,HotFlag=?,Preferential=?,StartDate=?,EndDate=?,wapPictureUrl=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,Prop5=? where ProductID=?";

	protected static final String _DeleteSQL = "delete from ProductRelaInfo  where ProductID=?";

	protected static final String _FillAllSQL = "select * from ProductRelaInfo  where ProductID=?";

	public ProductRelaInfoSchema(){
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
		return new ProductRelaInfoSchema();
	}

	protected SchemaSet newSet(){
		return new ProductRelaInfoSet();
	}

	public ProductRelaInfoSet query() {
		return query(null, -1, -1);
	}

	public ProductRelaInfoSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ProductRelaInfoSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ProductRelaInfoSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ProductRelaInfoSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){ProductID = (String)v;return;}
		if (i == 1){MakeDate = (Date)v;return;}
		if (i == 2){Modifydate = (Date)v;return;}
		if (i == 3){IsPublish = (String)v;return;}
		if (i == 4){RecommendFlag = (String)v;return;}
		if (i == 5){HotFlag = (String)v;return;}
		if (i == 6){Preferential = (String)v;return;}
		if (i == 7){StartDate = (Date)v;return;}
		if (i == 8){EndDate = (Date)v;return;}
		if (i == 9){wapPictureUrl = (String)v;return;}
		if (i == 10){Prop1 = (String)v;return;}
		if (i == 11){Prop2 = (String)v;return;}
		if (i == 12){Prop3 = (String)v;return;}
		if (i == 13){Prop4 = (String)v;return;}
		if (i == 14){Prop5 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ProductID;}
		if (i == 1){return MakeDate;}
		if (i == 2){return Modifydate;}
		if (i == 3){return IsPublish;}
		if (i == 4){return RecommendFlag;}
		if (i == 5){return HotFlag;}
		if (i == 6){return Preferential;}
		if (i == 7){return StartDate;}
		if (i == 8){return EndDate;}
		if (i == 9){return wapPictureUrl;}
		if (i == 10){return Prop1;}
		if (i == 11){return Prop2;}
		if (i == 12){return Prop3;}
		if (i == 13){return Prop4;}
		if (i == 14){return Prop5;}
		return null;
	}

	/**
	* 获取字段ProductID的值，该字段的<br>
	* 字段名称 :产品ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getProductID() {
		return ProductID;
	}

	/**
	* 设置字段ProductID的值，该字段的<br>
	* 字段名称 :产品ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setProductID(String productID) {
		this.ProductID = productID;
    }

	/**
	* 获取字段MakeDate的值，该字段的<br>
	* 字段名称 :MakeDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getMakeDate() {
		return MakeDate;
	}

	/**
	* 设置字段MakeDate的值，该字段的<br>
	* 字段名称 :MakeDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMakeDate(Date makeDate) {
		this.MakeDate = makeDate;
    }

	/**
	* 获取字段Modifydate的值，该字段的<br>
	* 字段名称 :Modifydate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getModifydate() {
		return Modifydate;
	}

	/**
	* 设置字段Modifydate的值，该字段的<br>
	* 字段名称 :Modifydate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifydate(Date modifydate) {
		this.Modifydate = modifydate;
    }

	/**
	* 获取字段IsPublish的值，该字段的<br>
	* 字段名称 :上线状态<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	wap等第三方平台是否上线 Y--上线 N--下线<br>
	*/
	public String getIsPublish() {
		return IsPublish;
	}

	/**
	* 设置字段IsPublish的值，该字段的<br>
	* 字段名称 :上线状态<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	wap等第三方平台是否上线 Y--上线 N--下线<br>
	*/
	public void setIsPublish(String isPublish) {
		this.IsPublish = isPublish;
    }

	/**
	* 获取字段RecommendFlag的值，该字段的<br>
	* 字段名称 :推荐属性<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	第三方平台推荐<br>
	*/
	public String getRecommendFlag() {
		return RecommendFlag;
	}

	/**
	* 设置字段RecommendFlag的值，该字段的<br>
	* 字段名称 :推荐属性<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	第三方平台推荐<br>
	*/
	public void setRecommendFlag(String recommendFlag) {
		this.RecommendFlag = recommendFlag;
    }

	/**
	* 获取字段HotFlag的值，该字段的<br>
	* 字段名称 :热销属性<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	第三方平台热销<br>
	*/
	public String getHotFlag() {
		return HotFlag;
	}

	/**
	* 设置字段HotFlag的值，该字段的<br>
	* 字段名称 :热销属性<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	第三方平台热销<br>
	*/
	public void setHotFlag(String hotFlag) {
		this.HotFlag = hotFlag;
    }

	/**
	* 获取字段Preferential的值，该字段的<br>
	* 字段名称 :优惠属性<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	第三方平台优惠信息<br>
	*/
	public String getPreferential() {
		return Preferential;
	}

	/**
	* 设置字段Preferential的值，该字段的<br>
	* 字段名称 :优惠属性<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	第三方平台优惠信息<br>
	*/
	public void setPreferential(String preferential) {
		this.Preferential = preferential;
    }

	/**
	* 获取字段StartDate的值，该字段的<br>
	* 字段名称 :优惠起期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	Y--可编辑 N--不可编辑<br>
	*/
	public Date getStartDate() {
		return StartDate;
	}

	/**
	* 设置字段StartDate的值，该字段的<br>
	* 字段名称 :优惠起期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	Y--可编辑 N--不可编辑<br>
	*/
	public void setStartDate(Date startDate) {
		this.StartDate = startDate;
    }

	/**
	* 获取字段EndDate的值，该字段的<br>
	* 字段名称 :优惠止期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getEndDate() {
		return EndDate;
	}

	/**
	* 设置字段EndDate的值，该字段的<br>
	* 字段名称 :优惠止期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br> 
	* 是否必填 :false<br>
	*/
	public void setEndDate(Date endDate) {
		this.EndDate = endDate;
    }

	/**
	* 获取字段wapPictureUrl的值，该字段的<br>
	* 字段名称 :详细页图片<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getWapPictureUrl() {
		return wapPictureUrl;
	}

	/**
	* 设置字段wapPictureUrl的值，该字段的<br>
	* 字段名称 :详细页图片<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setWapPictureUrl(String wapPictureUrl) {
		this.wapPictureUrl = wapPictureUrl;
	}

	/**
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :Prop1<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :Prop1<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* 获取字段Prop2的值，该字段的<br>
	* 字段名称 :Prop2<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* 设置字段Prop2的值，该字段的<br>
	* 字段名称 :Prop2<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

	/**
	* 获取字段Prop3的值，该字段的<br>
	* 字段名称 :Prop3<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp3() {
		return Prop3;
	}

	/**
	* 设置字段Prop3的值，该字段的<br>
	* 字段名称 :Prop3<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp3(String prop3) {
		this.Prop3 = prop3;
    }

	/**
	* 获取字段Prop4的值，该字段的<br>
	* 字段名称 :Prop4<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp4() {
		return Prop4;
	}

	/**
	* 设置字段Prop4的值，该字段的<br>
	* 字段名称 :Prop4<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp4(String prop4) {
		this.Prop4 = prop4;
    }

	/**
	* 获取字段Prop5的值，该字段的<br>
	* 字段名称 :Prop5<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp5() {
		return Prop5;
	}

	/**
	* 设置字段Prop5的值，该字段的<br>
	* 字段名称 :Prop5<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp5(String prop5) {
		this.Prop5 = prop5;
    }

}