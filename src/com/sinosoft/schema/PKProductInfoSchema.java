package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

import java.util.Date;

/**
 * 表名称：PK产品信息表<br>
 * 表代码：PKProductInfo<br>
 * 表主键：ID<br>
 */
public class PKProductInfoSchema extends Schema {
	private String ID;

	private String ArticleId;

	private String ProductId1;

	private String ProductName1;

	private String DiscountPrice1;

	private String Prem1;

	private String ProductUrl1;

	private String ProductPeriod1;

	private String ProductId2;

	private String ProductName2;

	private String DiscountPrice2;

	private String Prem2;

	private String ProductUrl2;

	private String ProductPeriod2;

	private String InitPeriod;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	private String Prop4;

	private Date CreateDate;

	private String CreateUser;

	private Date ModifyDate;

	private String ModifyUser;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("ArticleId", DataColumn.STRING, 1, 32 , 0 , false , false),
		new SchemaColumn("ProductId1", DataColumn.STRING, 2, 32 , 0 , false , false),
		new SchemaColumn("ProductName1", DataColumn.STRING, 3, 255 , 0 , false , false),
		new SchemaColumn("DiscountPrice1", DataColumn.STRING, 4, 10 , 0 , false , false),
		new SchemaColumn("Prem1", DataColumn.STRING, 5, 10 , 0 , false , false),
		new SchemaColumn("ProductUrl1", DataColumn.STRING, 6, 50 , 0 , false , false),
		new SchemaColumn("ProductPeriod1", DataColumn.STRING, 7, 10 , 0 , false , false),
		new SchemaColumn("ProductId2", DataColumn.STRING, 8, 32 , 0 , false , false),
		new SchemaColumn("ProductName2", DataColumn.STRING, 9, 255 , 0 , false , false),
		new SchemaColumn("DiscountPrice2", DataColumn.STRING, 10, 10 , 0 , false , false),
		new SchemaColumn("Prem2", DataColumn.STRING, 11, 10 , 0 , false , false),
		new SchemaColumn("ProductUrl2", DataColumn.STRING, 12, 50 , 0 , false , false),
		new SchemaColumn("ProductPeriod2", DataColumn.STRING, 13, 10 , 0 , false , false),
		new SchemaColumn("InitPeriod", DataColumn.STRING, 14, 10 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 15, 255 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 16, 255 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 17, 255 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 18, 255 , 0 , false , false),
		new SchemaColumn("CreateDate", DataColumn.DATETIME, 19, 0 , 0 , false , false),
		new SchemaColumn("CreateUser", DataColumn.STRING, 20, 50 , 0 , false , false),
		new SchemaColumn("ModifyDate", DataColumn.DATETIME, 21, 0 , 0 , false , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 22, 50 , 0 , false , false)
	};

	public static final String _TableCode = "PKProductInfo";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into PKProductInfo values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update PKProductInfo set ID=?,ArticleId=?,ProductId1=?,ProductName1=?,DiscountPrice1=?,Prem1=?,ProductUrl1=?,ProductPeriod1=?,ProductId2=?,ProductName2=?,DiscountPrice2=?,Prem2=?,ProductUrl2=?,ProductPeriod2=?,InitPeriod=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,CreateDate=?,CreateUser=?,ModifyDate=?,ModifyUser=? where ID=?";

	protected static final String _DeleteSQL = "delete from PKProductInfo  where ID=?";

	protected static final String _FillAllSQL = "select * from PKProductInfo  where ID=?";

	public PKProductInfoSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[23];
	}

	protected Schema newInstance(){
		return new PKProductInfoSchema();
	}

	protected SchemaSet newSet(){
		return new PKProductInfoSet();
	}

	public PKProductInfoSet query() {
		return query(null, -1, -1);
	}

	public PKProductInfoSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public PKProductInfoSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public PKProductInfoSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (PKProductInfoSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){ID = (String)v;return;}
		if (i == 1){ArticleId = (String)v;return;}
		if (i == 2){ProductId1 = (String)v;return;}
		if (i == 3){ProductName1 = (String)v;return;}
		if (i == 4){DiscountPrice1 = (String)v;return;}
		if (i == 5){Prem1 = (String)v;return;}
		if (i == 6){ProductUrl1 = (String)v;return;}
		if (i == 7){ProductPeriod1 = (String)v;return;}
		if (i == 8){ProductId2 = (String)v;return;}
		if (i == 9){ProductName2 = (String)v;return;}
		if (i == 10){DiscountPrice2 = (String)v;return;}
		if (i == 11){Prem2 = (String)v;return;}
		if (i == 12){ProductUrl2 = (String)v;return;}
		if (i == 13){ProductPeriod2 = (String)v;return;}
		if (i == 14){InitPeriod = (String)v;return;}
		if (i == 15){Prop1 = (String)v;return;}
		if (i == 16){Prop2 = (String)v;return;}
		if (i == 17){Prop3 = (String)v;return;}
		if (i == 18){Prop4 = (String)v;return;}
		if (i == 19){CreateDate = (Date)v;return;}
		if (i == 20){CreateUser = (String)v;return;}
		if (i == 21){ModifyDate = (Date)v;return;}
		if (i == 22){ModifyUser = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return ArticleId;}
		if (i == 2){return ProductId1;}
		if (i == 3){return ProductName1;}
		if (i == 4){return DiscountPrice1;}
		if (i == 5){return Prem1;}
		if (i == 6){return ProductUrl1;}
		if (i == 7){return ProductPeriod1;}
		if (i == 8){return ProductId2;}
		if (i == 9){return ProductName2;}
		if (i == 10){return DiscountPrice2;}
		if (i == 11){return Prem2;}
		if (i == 12){return ProductUrl2;}
		if (i == 13){return ProductPeriod2;}
		if (i == 14){return InitPeriod;}
		if (i == 15){return Prop1;}
		if (i == 16){return Prop2;}
		if (i == 17){return Prop3;}
		if (i == 18){return Prop4;}
		if (i == 19){return CreateDate;}
		if (i == 20){return CreateUser;}
		if (i == 21){return ModifyDate;}
		if (i == 22){return ModifyUser;}
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
	* 获取字段ProductId1的值，该字段的<br>
	* 字段名称 :PK产品1编码<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProductId1() {
		return ProductId1;
	}

	/**
	* 设置字段ProductId1的值，该字段的<br>
	* 字段名称 :PK产品1编码<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProductId1(String productId1) {
		this.ProductId1 = productId1;
    }

	/**
	* 获取字段ProductName1的值，该字段的<br>
	* 字段名称 :PK产品1名称<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProductName1() {
		return ProductName1;
	}

	/**
	* 设置字段ProductName1的值，该字段的<br>
	* 字段名称 :PK产品1名称<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProductName1(String productName1) {
		this.ProductName1 = productName1;
    }

	/**
	* 获取字段DiscountPrice1的值，该字段的<br>
	* 字段名称 :PK产品1折扣价<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getDiscountPrice1() {
		return DiscountPrice1;
	}

	/**
	* 设置字段DiscountPrice1的值，该字段的<br>
	* 字段名称 :PK产品1折扣价<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDiscountPrice1(String discountPrice1) {
		this.DiscountPrice1 = discountPrice1;
    }

	/**
	* 获取字段Prem1的值，该字段的<br>
	* 字段名称 :PK产品1原价<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPrem1() {
		return Prem1;
	}

	/**
	* 设置字段Prem1的值，该字段的<br>
	* 字段名称 :PK产品1原价<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPrem1(String prem1) {
		this.Prem1 = prem1;
    }

	/**
	* 获取字段ProductUrl1的值，该字段的<br>
	* 字段名称 :PK产品1详细页链接<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProductUrl1() {
		return ProductUrl1;
	}

	/**
	* 设置字段ProductUrl1的值，该字段的<br>
	* 字段名称 :PK产品1详细页链接<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProductUrl1(String productUrl1) {
		this.ProductUrl1 = productUrl1;
    }

	/**
	* 获取字段ProductPeriod1的值，该字段的<br>
	* 字段名称 :PK产品1默认保障期限<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProductPeriod1() {
		return ProductPeriod1;
	}

	/**
	* 设置字段ProductPeriod1的值，该字段的<br>
	* 字段名称 :PK产品1默认保障期限<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProductPeriod1(String productPeriod1) {
		this.ProductPeriod1 = productPeriod1;
    }

	/**
	* 获取字段ProductId2的值，该字段的<br>
	* 字段名称 :PK产品2编码<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProductId2() {
		return ProductId2;
	}

	/**
	* 设置字段ProductId2的值，该字段的<br>
	* 字段名称 :PK产品2编码<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProductId2(String productId2) {
		this.ProductId2 = productId2;
    }

	/**
	* 获取字段ProductName2的值，该字段的<br>
	* 字段名称 :PK产品2名称<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProductName2() {
		return ProductName2;
	}

	/**
	* 设置字段ProductName2的值，该字段的<br>
	* 字段名称 :PK产品2名称<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProductName2(String productName2) {
		this.ProductName2 = productName2;
    }

	/**
	* 获取字段DiscountPrice2的值，该字段的<br>
	* 字段名称 :PK产品2折扣价<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getDiscountPrice2() {
		return DiscountPrice2;
	}

	/**
	* 设置字段DiscountPrice2的值，该字段的<br>
	* 字段名称 :PK产品2折扣价<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDiscountPrice2(String discountPrice2) {
		this.DiscountPrice2 = discountPrice2;
    }

	/**
	* 获取字段Prem2的值，该字段的<br>
	* 字段名称 :PK产品2原价<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPrem2() {
		return Prem2;
	}

	/**
	* 设置字段Prem2的值，该字段的<br>
	* 字段名称 :PK产品2原价<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPrem2(String prem2) {
		this.Prem2 = prem2;
    }

	/**
	* 获取字段ProductUrl2的值，该字段的<br>
	* 字段名称 :PK产品2详细页链接<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProductUrl2() {
		return ProductUrl2;
	}

	/**
	* 设置字段ProductUrl2的值，该字段的<br>
	* 字段名称 :PK产品2详细页链接<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProductUrl2(String productUrl2) {
		this.ProductUrl2 = productUrl2;
    }

	/**
	* 获取字段ProductPeriod2的值，该字段的<br>
	* 字段名称 :PK产品2默认保障期限<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProductPeriod2() {
		return ProductPeriod2;
	}

	/**
	* 设置字段ProductPeriod2的值，该字段的<br>
	* 字段名称 :PK产品2默认保障期限<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProductPeriod2(String productPeriod2) {
		this.ProductPeriod2 = productPeriod2;
    }

	/**
	* 获取字段InitPeriod的值，该字段的<br>
	* 字段名称 :初始行程<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getInitPeriod() {
		return InitPeriod;
	}

	/**
	* 设置字段InitPeriod的值，该字段的<br>
	* 字段名称 :初始行程<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setInitPeriod(String initPeriod) {
		this.InitPeriod = initPeriod;
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
	* 获取字段Prop4的值，该字段的<br>
	* 字段名称 :备用字段4<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp4() {
		return Prop4;
	}

	/**
	* 设置字段Prop4的值，该字段的<br>
	* 字段名称 :备用字段4<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp4(String prop4) {
		this.Prop4 = prop4;
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