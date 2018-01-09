package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

/**
 * 表名称：邮件模板图片<br>
 * 表代码：ZDMailConfigImage<br>
 * 表主键：ID<br>
 */
public class ZDMailConfigImageSchema extends Schema {
	private String ID;

	private String EmailType;

	private String ImageName;

	private String ImageLink;

	private String ImageDesc;

	private String ImagePath;

	private String Operator;

	private String CreateDate;

	private String ModifyDate;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	private String Prop4;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("EmailType", DataColumn.STRING, 1, 20 , 0 , false , false),
		new SchemaColumn("ImageName", DataColumn.STRING, 2, 50 , 0 , false , false),
		new SchemaColumn("ImageLink", DataColumn.STRING, 3, 200 , 0 , false , false),
		new SchemaColumn("ImageDesc", DataColumn.STRING, 4, 200 , 0 , false , false),
		new SchemaColumn("ImagePath", DataColumn.STRING, 5, 500 , 0 , false , false),
		new SchemaColumn("Operator", DataColumn.STRING, 6, 20 , 0 , false , false),
		new SchemaColumn("CreateDate", DataColumn.STRING, 7, 20 , 0 , false , false),
		new SchemaColumn("ModifyDate", DataColumn.STRING, 8, 20 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 9, 200 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 10, 200 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 11, 200 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 12, 200 , 0 , false , false)
	};

	public static final String _TableCode = "ZDMailConfigImage";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into ZDMailConfigImage values(?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZDMailConfigImage set ID=?,EmailType=?,ImageName=?,ImageLink=?,ImageDesc=?,ImagePath=?,Operator=?,CreateDate=?,ModifyDate=?,Prop1=?,Prop2=?,Prop3=?,Prop4=? where ID=?";

	protected static final String _DeleteSQL = "delete from ZDMailConfigImage  where ID=?";

	protected static final String _FillAllSQL = "select * from ZDMailConfigImage  where ID=?";

	public ZDMailConfigImageSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[13];
	}

	protected Schema newInstance(){
		return new ZDMailConfigImageSchema();
	}

	protected SchemaSet newSet(){
		return new ZDMailConfigImageSet();
	}

	public ZDMailConfigImageSet query() {
		return query(null, -1, -1);
	}

	public ZDMailConfigImageSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZDMailConfigImageSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZDMailConfigImageSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZDMailConfigImageSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){ID = (String)v;return;}
		if (i == 1){EmailType = (String)v;return;}
		if (i == 2){ImageName = (String)v;return;}
		if (i == 3){ImageLink = (String)v;return;}
		if (i == 4){ImageDesc = (String)v;return;}
		if (i == 5){ImagePath = (String)v;return;}
		if (i == 6){Operator = (String)v;return;}
		if (i == 7){CreateDate = (String)v;return;}
		if (i == 8){ModifyDate = (String)v;return;}
		if (i == 9){Prop1 = (String)v;return;}
		if (i == 10){Prop2 = (String)v;return;}
		if (i == 11){Prop3 = (String)v;return;}
		if (i == 12){Prop4 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return EmailType;}
		if (i == 2){return ImageName;}
		if (i == 3){return ImageLink;}
		if (i == 4){return ImageDesc;}
		if (i == 5){return ImagePath;}
		if (i == 6){return Operator;}
		if (i == 7){return CreateDate;}
		if (i == 8){return ModifyDate;}
		if (i == 9){return Prop1;}
		if (i == 10){return Prop2;}
		if (i == 11){return Prop3;}
		if (i == 12){return Prop4;}
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
	* 获取字段EmailType的值，该字段的<br>
	* 字段名称 :邮件模板类型<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getEmailType() {
		return EmailType;
	}

	/**
	* 设置字段EmailType的值，该字段的<br>
	* 字段名称 :邮件模板类型<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setEmailType(String emailType) {
		this.EmailType = emailType;
    }

	/**
	* 获取字段ImageName的值，该字段的<br>
	* 字段名称 :图片名称<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getImageName() {
		return ImageName;
	}

	/**
	* 设置字段ImageName的值，该字段的<br>
	* 字段名称 :图片名称<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setImageName(String imageName) {
		this.ImageName = imageName;
    }

	/**
	* 获取字段ImageLink的值，该字段的<br>
	* 字段名称 :图片链接<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getImageLink() {
		return ImageLink;
	}

	/**
	* 设置字段ImageLink的值，该字段的<br>
	* 字段名称 :图片链接<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setImageLink(String imageLink) {
		this.ImageLink = imageLink;
    }

	/**
	* 获取字段ImageDesc的值，该字段的<br>
	* 字段名称 :图片描述<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getImageDesc() {
		return ImageDesc;
	}

	/**
	* 设置字段ImageDesc的值，该字段的<br>
	* 字段名称 :图片描述<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setImageDesc(String imageDesc) {
		this.ImageDesc = imageDesc;
    }

	/**
	* 获取字段ImagePath的值，该字段的<br>
	* 字段名称 :图片地址<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getImagePath() {
		return ImagePath;
	}

	/**
	* 设置字段ImagePath的值，该字段的<br>
	* 字段名称 :图片地址<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setImagePath(String imagePath) {
		this.ImagePath = imagePath;
    }

	/**
	* 获取字段Operator的值，该字段的<br>
	* 字段名称 :操作员<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getOperator() {
		return Operator;
	}

	/**
	* 设置字段Operator的值，该字段的<br>
	* 字段名称 :操作员<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setOperator(String operator) {
		this.Operator = operator;
    }

	/**
	* 获取字段CreateDate的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCreateDate() {
		return CreateDate;
	}

	/**
	* 设置字段CreateDate的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCreateDate(String createDate) {
		this.CreateDate = createDate;
    }

	/**
	* 获取字段ModifyDate的值，该字段的<br>
	* 字段名称 :修改时间<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getModifyDate() {
		return ModifyDate;
	}

	/**
	* 设置字段ModifyDate的值，该字段的<br>
	* 字段名称 :修改时间<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyDate(String modifyDate) {
		this.ModifyDate = modifyDate;
    }

	/**
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* 获取字段Prop2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* 设置字段Prop2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

	/**
	* 获取字段Prop3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp3() {
		return Prop3;
	}

	/**
	* 设置字段Prop3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp3(String prop3) {
		this.Prop3 = prop3;
    }

	/**
	* 获取字段Prop4的值，该字段的<br>
	* 字段名称 :备用字段4<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp4() {
		return Prop4;
	}

	/**
	* 设置字段Prop4的值，该字段的<br>
	* 字段名称 :备用字段4<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp4(String prop4) {
		this.Prop4 = prop4;
    }

}