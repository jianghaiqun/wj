package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

/**
 * 表名称：黑名单表<br>
 * 表代码：FMBlackList<br>
 * 表主键：ID<br>
 */
public class FMBlackListSchema extends Schema {
	private Integer ID;

	private String InsuredCompanySn;

	private String InsuredCompanyName;

	private String ProductId;

	private String ProductName;

	private String RecognizeeName;

	private String RecognizeeAge;

	private String RecognizeeSex;

	private String InsIDType;

	private String InsIDNo;

	private String Remark;

	private String createDate;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.INTEGER, 0, 11 , 0 , true , true),
		new SchemaColumn("InsuredCompanySn", DataColumn.STRING, 1, 10 , 0 , false , false),
		new SchemaColumn("InsuredCompanyName", DataColumn.STRING, 2, 50 , 0 , false , false),
		new SchemaColumn("ProductId", DataColumn.STRING, 3, 50 , 0 , false , false),
		new SchemaColumn("ProductName", DataColumn.STRING, 4, 150 , 0 , false , false),
		new SchemaColumn("RecognizeeName", DataColumn.STRING, 5, 20 , 0 , false , false),
		new SchemaColumn("RecognizeeAge", DataColumn.STRING, 6, 5 , 0 , false , false),
		new SchemaColumn("RecognizeeSex", DataColumn.STRING, 7, 5 , 0 , false , false),
		new SchemaColumn("InsIDType", DataColumn.STRING, 8, 20 , 0 , false , false),
		new SchemaColumn("InsIDNo", DataColumn.STRING, 9, 100 , 0 , false , false),
		new SchemaColumn("Remark", DataColumn.STRING, 10, 600 , 0 , false , false),
		new SchemaColumn("createDate", DataColumn.STRING, 11, 600 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 12, 600 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 13, 600 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 14, 600 , 0 , false , false)
	};

	public static final String _TableCode = "FMBlackList";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into FMBlackList values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update FMBlackList set ID=?,InsuredCompanySn=?,InsuredCompanyName=?,ProductId=?,ProductName=?,RecognizeeName=?,RecognizeeAge=?,RecognizeeSex=?,InsIDType=?,InsIDNo=?,Remark=?,createDate=?,Prop1=?,Prop2=?,Prop3=? where ID=?";

	protected static final String _DeleteSQL = "delete from FMBlackList  where ID=?";

	protected static final String _FillAllSQL = "select * from FMBlackList  where ID=?";

	public FMBlackListSchema(){
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
		return new FMBlackListSchema();
	}

	protected SchemaSet newSet(){
		return new FMBlackListSet();
	}

	public FMBlackListSet query() {
		return query(null, -1, -1);
	}

	public FMBlackListSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public FMBlackListSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public FMBlackListSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (FMBlackListSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Integer(v.toString());}return;}
		if (i == 1){InsuredCompanySn = (String)v;return;}
		if (i == 2){InsuredCompanyName = (String)v;return;}
		if (i == 3){ProductId = (String)v;return;}
		if (i == 4){ProductName = (String)v;return;}
		if (i == 5){RecognizeeName = (String)v;return;}
		if (i == 6){RecognizeeAge = (String)v;return;}
		if (i == 7){RecognizeeSex = (String)v;return;}
		if (i == 8){InsIDType = (String)v;return;}
		if (i == 9){InsIDNo = (String)v;return;}
		if (i == 10){Remark = (String)v;return;}
		if (i == 11){createDate = (String)v;return;}
		if (i == 12){Prop1 = (String)v;return;}
		if (i == 13){Prop2 = (String)v;return;}
		if (i == 14){Prop3 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return InsuredCompanySn;}
		if (i == 2){return InsuredCompanyName;}
		if (i == 3){return ProductId;}
		if (i == 4){return ProductName;}
		if (i == 5){return RecognizeeName;}
		if (i == 6){return RecognizeeAge;}
		if (i == 7){return RecognizeeSex;}
		if (i == 8){return InsIDType;}
		if (i == 9){return InsIDNo;}
		if (i == 10){return Remark;}
		if (i == 11){return createDate;}
		if (i == 12){return Prop1;}
		if (i == 13){return Prop2;}
		if (i == 14){return Prop3;}
		return null;
	}

	/**
	* 获取字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :INT (11)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public int getID() {
		if(ID==null){return 0;}
		return ID.intValue();
	}

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :INT (11)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setID(int iD) {
		this.ID = new Integer(iD);
    }

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :INT (11)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setID(String iD) {
		if (iD == null){
			this.ID = null;
			return;
		}
		this.ID = new Integer(iD);
    }

	/**
	* 获取字段InsuredCompanySn的值，该字段的<br>
	* 字段名称 :保险公司编码<br>
	* 数据类型 :VARCHAR (10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getInsuredCompanySn() {
		return InsuredCompanySn;
	}

	/**
	* 设置字段InsuredCompanySn的值，该字段的<br>
	* 字段名称 :保险公司编码<br>
	* 数据类型 :VARCHAR (10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setInsuredCompanySn(String insuredCompanySn) {
		this.InsuredCompanySn = insuredCompanySn;
    }

	/**
	* 获取字段InsuredCompanyName的值，该字段的<br>
	* 字段名称 :保险公司名称<br>
	* 数据类型 :VARCHAR (50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getInsuredCompanyName() {
		return InsuredCompanyName;
	}

	/**
	* 设置字段InsuredCompanyName的值，该字段的<br>
	* 字段名称 :保险公司名称<br>
	* 数据类型 :VARCHAR (50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setInsuredCompanyName(String insuredCompanyName) {
		this.InsuredCompanyName = insuredCompanyName;
    }

	/**
	* 获取字段ProductId的值，该字段的<br>
	* 字段名称 :产品ID<br>
	* 数据类型 :VARCHAR (50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProductId() {
		return ProductId;
	}

	/**
	* 设置字段ProductId的值，该字段的<br>
	* 字段名称 :产品ID<br>
	* 数据类型 :VARCHAR (50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProductId(String productId) {
		this.ProductId = productId;
    }

	/**
	* 获取字段ProductName的值，该字段的<br>
	* 字段名称 :产品名称<br>
	* 数据类型 :VARCHAR (150)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProductName() {
		return ProductName;
	}

	/**
	* 设置字段ProductName的值，该字段的<br>
	* 字段名称 :产品名称<br>
	* 数据类型 :VARCHAR (150)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProductName(String productName) {
		this.ProductName = productName;
    }

	/**
	* 获取字段RecognizeeName的值，该字段的<br>
	* 字段名称 :被保人姓名<br>
	* 数据类型 :VARCHAR (20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getRecognizeeName() {
		return RecognizeeName;
	}

	/**
	* 设置字段RecognizeeName的值，该字段的<br>
	* 字段名称 :被保人姓名<br>
	* 数据类型 :VARCHAR (20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRecognizeeName(String recognizeeName) {
		this.RecognizeeName = recognizeeName;
    }

	/**
	* 获取字段RecognizeeAge的值，该字段的<br>
	* 字段名称 :被保人年龄<br>
	* 数据类型 :VARCHAR (5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getRecognizeeAge() {
		return RecognizeeAge;
	}

	/**
	* 设置字段RecognizeeAge的值，该字段的<br>
	* 字段名称 :被保人年龄<br>
	* 数据类型 :VARCHAR (5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRecognizeeAge(String recognizeeAge) {
		this.RecognizeeAge = recognizeeAge;
    }

	/**
	* 获取字段RecognizeeSex的值，该字段的<br>
	* 字段名称 :被保人性别<br>
	* 数据类型 :VARCHAR (5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getRecognizeeSex() {
		return RecognizeeSex;
	}

	/**
	* 设置字段RecognizeeSex的值，该字段的<br>
	* 字段名称 :被保人性别<br>
	* 数据类型 :VARCHAR (5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRecognizeeSex(String recognizeeSex) {
		this.RecognizeeSex = recognizeeSex;
    }

	/**
	* 获取字段InsIDType的值，该字段的<br>
	* 字段名称 :证件类型<br>
	* 数据类型 :VARCHAR (20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getInsIDType() {
		return InsIDType;
	}

	/**
	* 设置字段InsIDType的值，该字段的<br>
	* 字段名称 :证件类型<br>
	* 数据类型 :VARCHAR (20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setInsIDType(String insIDType) {
		this.InsIDType = insIDType;
    }

	/**
	* 获取字段InsIDNo的值，该字段的<br>
	* 字段名称 :证件号码<br>
	* 数据类型 :VARCHAR (100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getInsIDNo() {
		return InsIDNo;
	}

	/**
	* 设置字段InsIDNo的值，该字段的<br>
	* 字段名称 :证件号码<br>
	* 数据类型 :VARCHAR (100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setInsIDNo(String insIDNo) {
		this.InsIDNo = insIDNo;
    }

	/**
	* 获取字段Remark的值，该字段的<br>
	* 字段名称 :备注(原因)<br>
	* 数据类型 :varchar(600)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getRemark() {
		return Remark;
	}

	/**
	* 设置字段Remark的值，该字段的<br>
	* 字段名称 :备注(原因)<br>
	* 数据类型 :varchar(600)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRemark(String remark) {
		this.Remark = remark;
    }

	/**
	* 获取字段createDate的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :varchar(600)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcreateDate() {
		return createDate;
	}

	/**
	* 设置字段createDate的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :varchar(600)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcreateDate(String createDate) {
		this.createDate = createDate;
    }

	/**
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(600)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(600)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* 获取字段Prop2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(600)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* 设置字段Prop2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(600)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

	/**
	* 获取字段Prop3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(600)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp3() {
		return Prop3;
	}

	/**
	* 设置字段Prop3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(600)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp3(String prop3) {
		this.Prop3 = prop3;
    }

}