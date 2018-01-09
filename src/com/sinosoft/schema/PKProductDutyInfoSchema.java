package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：PK产品保障信息表<br>
 * 表代码：PKProductDutyInfo<br>
 * 表主键：ID<br>
 */
public class PKProductDutyInfoSchema extends Schema {
	private String ID;

	private String ArticleId;

	private String DutyName;

	private String DutyAmnt1;

	private String Percent1;

	private String KeyTips1;

	private String DutyDesc1;

	private String DutyAmnt2;

	private String Percent2;

	private String KeyTips2;

	private String DutyDesc2;

	private Integer OrderFlag;

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
		new SchemaColumn("DutyName", DataColumn.STRING, 2, 100 , 0 , false , false),
		new SchemaColumn("DutyAmnt1", DataColumn.STRING, 3, 50 , 0 , false , false),
		new SchemaColumn("Percent1", DataColumn.STRING, 4, 10 , 0 , false , false),
		new SchemaColumn("KeyTips1", DataColumn.STRING, 5, 50 , 0 , false , false),
		new SchemaColumn("DutyDesc1", DataColumn.STRING, 6, 2000 , 0 , false , false),
		new SchemaColumn("DutyAmnt2", DataColumn.STRING, 7, 50 , 0 , false , false),
		new SchemaColumn("Percent2", DataColumn.STRING, 8, 10 , 0 , false , false),
		new SchemaColumn("KeyTips2", DataColumn.STRING, 9, 50 , 0 , false , false),
		new SchemaColumn("DutyDesc2", DataColumn.STRING, 10, 2000 , 0 , false , false),
		new SchemaColumn("OrderFlag", DataColumn.INTEGER, 11, 11 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 12, 255 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 13, 255 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 14, 255 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 15, 255 , 0 , false , false),
		new SchemaColumn("CreateDate", DataColumn.DATETIME, 16, 0 , 0 , false , false),
		new SchemaColumn("CreateUser", DataColumn.STRING, 17, 50 , 0 , false , false),
		new SchemaColumn("ModifyDate", DataColumn.DATETIME, 18, 0 , 0 , false , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 19, 50 , 0 , false , false)
	};

	public static final String _TableCode = "PKProductDutyInfo";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into PKProductDutyInfo values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update PKProductDutyInfo set ID=?,ArticleId=?,DutyName=?,DutyAmnt1=?,Percent1=?,KeyTips1=?,DutyDesc1=?,DutyAmnt2=?,Percent2=?,KeyTips2=?,DutyDesc2=?,OrderFlag=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,CreateDate=?,CreateUser=?,ModifyDate=?,ModifyUser=? where ID=?";

	protected static final String _DeleteSQL = "delete from PKProductDutyInfo  where ID=?";

	protected static final String _FillAllSQL = "select * from PKProductDutyInfo  where ID=?";

	public PKProductDutyInfoSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[20];
	}

	protected Schema newInstance(){
		return new PKProductDutyInfoSchema();
	}

	protected SchemaSet newSet(){
		return new PKProductDutyInfoSet();
	}

	public PKProductDutyInfoSet query() {
		return query(null, -1, -1);
	}

	public PKProductDutyInfoSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public PKProductDutyInfoSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public PKProductDutyInfoSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (PKProductDutyInfoSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){ID = (String)v;return;}
		if (i == 1){ArticleId = (String)v;return;}
		if (i == 2){DutyName = (String)v;return;}
		if (i == 3){DutyAmnt1 = (String)v;return;}
		if (i == 4){Percent1 = (String)v;return;}
		if (i == 5){KeyTips1 = (String)v;return;}
		if (i == 6){DutyDesc1 = (String)v;return;}
		if (i == 7){DutyAmnt2 = (String)v;return;}
		if (i == 8){Percent2 = (String)v;return;}
		if (i == 9){KeyTips2 = (String)v;return;}
		if (i == 10){DutyDesc2 = (String)v;return;}
		if (i == 11){if(v==null){OrderFlag = null;}else{OrderFlag = new Integer(v.toString());}return;}
		if (i == 12){Prop1 = (String)v;return;}
		if (i == 13){Prop2 = (String)v;return;}
		if (i == 14){Prop3 = (String)v;return;}
		if (i == 15){Prop4 = (String)v;return;}
		if (i == 16){CreateDate = (Date)v;return;}
		if (i == 17){CreateUser = (String)v;return;}
		if (i == 18){ModifyDate = (Date)v;return;}
		if (i == 19){ModifyUser = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return ArticleId;}
		if (i == 2){return DutyName;}
		if (i == 3){return DutyAmnt1;}
		if (i == 4){return Percent1;}
		if (i == 5){return KeyTips1;}
		if (i == 6){return DutyDesc1;}
		if (i == 7){return DutyAmnt2;}
		if (i == 8){return Percent2;}
		if (i == 9){return KeyTips2;}
		if (i == 10){return DutyDesc2;}
		if (i == 11){return OrderFlag;}
		if (i == 12){return Prop1;}
		if (i == 13){return Prop2;}
		if (i == 14){return Prop3;}
		if (i == 15){return Prop4;}
		if (i == 16){return CreateDate;}
		if (i == 17){return CreateUser;}
		if (i == 18){return ModifyDate;}
		if (i == 19){return ModifyUser;}
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
	* 获取字段DutyName的值，该字段的<br>
	* 字段名称 :保障项目名称<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getDutyName() {
		return DutyName;
	}

	/**
	* 设置字段DutyName的值，该字段的<br>
	* 字段名称 :保障项目名称<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDutyName(String dutyName) {
		this.DutyName = dutyName;
    }

	/**
	* 获取字段DutyAmnt1的值，该字段的<br>
	* 字段名称 :PK产品1保障额度<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getDutyAmnt1() {
		return DutyAmnt1;
	}

	/**
	* 设置字段DutyAmnt1的值，该字段的<br>
	* 字段名称 :PK产品1保障额度<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDutyAmnt1(String dutyAmnt1) {
		this.DutyAmnt1 = dutyAmnt1;
    }

	/**
	* 获取字段Percent1的值，该字段的<br>
	* 字段名称 :PK产品1进度百分比<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPercent1() {
		return Percent1;
	}

	/**
	* 设置字段Percent1的值，该字段的<br>
	* 字段名称 :PK产品1进度百分比<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPercent1(String percent1) {
		this.Percent1 = percent1;
    }

	/**
	* 获取字段KeyTips1的值，该字段的<br>
	* 字段名称 :PK产品1重点提示<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getKeyTips1() {
		return KeyTips1;
	}

	/**
	* 设置字段KeyTips1的值，该字段的<br>
	* 字段名称 :PK产品1重点提示<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setKeyTips1(String keyTips1) {
		this.KeyTips1 = keyTips1;
    }

	/**
	* 获取字段DutyDesc1的值，该字段的<br>
	* 字段名称 :PK产品1保障说明<br>
	* 数据类型 :varchar(2000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getDutyDesc1() {
		return DutyDesc1;
	}

	/**
	* 设置字段DutyDesc1的值，该字段的<br>
	* 字段名称 :PK产品1保障说明<br>
	* 数据类型 :varchar(2000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDutyDesc1(String dutyDesc1) {
		this.DutyDesc1 = dutyDesc1;
    }

	/**
	* 获取字段DutyAmnt2的值，该字段的<br>
	* 字段名称 :PK产品2保障额度<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getDutyAmnt2() {
		return DutyAmnt2;
	}

	/**
	* 设置字段DutyAmnt2的值，该字段的<br>
	* 字段名称 :PK产品2保障额度<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDutyAmnt2(String dutyAmnt2) {
		this.DutyAmnt2 = dutyAmnt2;
    }

	/**
	* 获取字段Percent2的值，该字段的<br>
	* 字段名称 :PK产品2进度百分比<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPercent2() {
		return Percent2;
	}

	/**
	* 设置字段Percent2的值，该字段的<br>
	* 字段名称 :PK产品2进度百分比<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPercent2(String percent2) {
		this.Percent2 = percent2;
    }

	/**
	* 获取字段KeyTips2的值，该字段的<br>
	* 字段名称 :PK产品2重点提示<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getKeyTips2() {
		return KeyTips2;
	}

	/**
	* 设置字段KeyTips2的值，该字段的<br>
	* 字段名称 :PK产品2重点提示<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setKeyTips2(String keyTips2) {
		this.KeyTips2 = keyTips2;
    }

	/**
	* 获取字段DutyDesc2的值，该字段的<br>
	* 字段名称 :PK产品2保障说明<br>
	* 数据类型 :varchar(2000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getDutyDesc2() {
		return DutyDesc2;
	}

	/**
	* 设置字段DutyDesc2的值，该字段的<br>
	* 字段名称 :PK产品2保障说明<br>
	* 数据类型 :varchar(2000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDutyDesc2(String dutyDesc2) {
		this.DutyDesc2 = dutyDesc2;
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