package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：PK产品优势信息表<br>
 * 表代码：PKProductAdvantageInfo<br>
 * 表主键：ID<br>
 */
public class PKProductAdvantageInfoSchema extends Schema {
	private String ID;

	private String ArticleId;

	private String Info1;

	private String Info2;

	private Integer OrderFlag;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	private Date CreateDate;

	private String CreateUser;

	private Date ModifyDate;

	private String ModifyUser;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("ArticleId", DataColumn.STRING, 1, 32 , 0 , false , false),
		new SchemaColumn("Info1", DataColumn.STRING, 2, 1000 , 0 , false , false),
		new SchemaColumn("Info2", DataColumn.STRING, 3, 1000 , 0 , false , false),
		new SchemaColumn("OrderFlag", DataColumn.INTEGER, 4, 11 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 5, 255 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 6, 255 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 7, 255 , 0 , false , false),
		new SchemaColumn("CreateDate", DataColumn.DATETIME, 8, 0 , 0 , false , false),
		new SchemaColumn("CreateUser", DataColumn.STRING, 9, 50 , 0 , false , false),
		new SchemaColumn("ModifyDate", DataColumn.DATETIME, 10, 0 , 0 , false , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 11, 50 , 0 , false , false)
	};

	public static final String _TableCode = "PKProductAdvantageInfo";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into PKProductAdvantageInfo values(?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update PKProductAdvantageInfo set ID=?,ArticleId=?,Info1=?,Info2=?,OrderFlag=?,Prop1=?,Prop2=?,Prop3=?,CreateDate=?,CreateUser=?,ModifyDate=?,ModifyUser=? where ID=?";

	protected static final String _DeleteSQL = "delete from PKProductAdvantageInfo  where ID=?";

	protected static final String _FillAllSQL = "select * from PKProductAdvantageInfo  where ID=?";

	public PKProductAdvantageInfoSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[12];
	}

	protected Schema newInstance(){
		return new PKProductAdvantageInfoSchema();
	}

	protected SchemaSet newSet(){
		return new PKProductAdvantageInfoSet();
	}

	public PKProductAdvantageInfoSet query() {
		return query(null, -1, -1);
	}

	public PKProductAdvantageInfoSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public PKProductAdvantageInfoSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public PKProductAdvantageInfoSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (PKProductAdvantageInfoSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){ID = (String)v;return;}
		if (i == 1){ArticleId = (String)v;return;}
		if (i == 2){Info1 = (String)v;return;}
		if (i == 3){Info2 = (String)v;return;}
		if (i == 4){if(v==null){OrderFlag = null;}else{OrderFlag = new Integer(v.toString());}return;}
		if (i == 5){Prop1 = (String)v;return;}
		if (i == 6){Prop2 = (String)v;return;}
		if (i == 7){Prop3 = (String)v;return;}
		if (i == 8){CreateDate = (Date)v;return;}
		if (i == 9){CreateUser = (String)v;return;}
		if (i == 10){ModifyDate = (Date)v;return;}
		if (i == 11){ModifyUser = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return ArticleId;}
		if (i == 2){return Info1;}
		if (i == 3){return Info2;}
		if (i == 4){return OrderFlag;}
		if (i == 5){return Prop1;}
		if (i == 6){return Prop2;}
		if (i == 7){return Prop3;}
		if (i == 8){return CreateDate;}
		if (i == 9){return CreateUser;}
		if (i == 10){return ModifyDate;}
		if (i == 11){return ModifyUser;}
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
	* 获取字段ArticleId的值，该字段的<br>
	* 字段名称 :文章ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getArticleId() {
		return ArticleId;
	}

	/**
	* 设置字段ArticleId的值，该字段的<br>
	* 字段名称 :文章ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setArticleId(String articleId) {
		this.ArticleId = articleId;
    }

	/**
	* 获取字段Info1的值，该字段的<br>
	* 字段名称 :产品1优势信息<br>
	* 数据类型 :varchar(1000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getInfo1() {
		return Info1;
	}

	/**
	* 设置字段Info1的值，该字段的<br>
	* 字段名称 :产品1优势信息<br>
	* 数据类型 :varchar(1000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setInfo1(String info1) {
		this.Info1 = info1;
    }

	/**
	* 获取字段Info2的值，该字段的<br>
	* 字段名称 :产品2优势信息<br>
	* 数据类型 :varchar(1000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getInfo2() {
		return Info2;
	}

	/**
	* 设置字段Info2的值，该字段的<br>
	* 字段名称 :产品2优势信息<br>
	* 数据类型 :varchar(1000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setInfo2(String info2) {
		this.Info2 = info2;
    }

	/**
	* 获取字段OrderFlag的值，该字段的<br>
	* 字段名称 :排序<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getOrderFlag() {
		if(OrderFlag==null){return 0;}
		return OrderFlag.intValue();
	}

	/**
	* 设置字段OrderFlag的值，该字段的<br>
	* 字段名称 :排序<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setOrderFlag(int orderFlag) {
		this.OrderFlag = new Integer(orderFlag);
    }

	/**
	* 设置字段OrderFlag的值，该字段的<br>
	* 字段名称 :排序<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setOrderFlag(String orderFlag) {
		if (orderFlag == null){
			this.OrderFlag = null;
			return;
		}
		this.OrderFlag = new Integer(orderFlag);
    }

	/**
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* 获取字段Prop2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* 设置字段Prop2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

	/**
	* 获取字段Prop3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp3() {
		return Prop3;
	}

	/**
	* 设置字段Prop3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp3(String prop3) {
		this.Prop3 = prop3;
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
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCreateUser() {
		return CreateUser;
	}

	/**
	* 设置字段CreateUser的值，该字段的<br>
	* 字段名称 :创建者<br>
	* 数据类型 :varchar(50)<br>
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
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getModifyUser() {
		return ModifyUser;
	}

	/**
	* 设置字段ModifyUser的值，该字段的<br>
	* 字段名称 :修改者<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyUser(String modifyUser) {
		this.ModifyUser = modifyUser;
    }

}