package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：文章缓存信息表<br>
 * 表代码：ZCArticleCacheInfo<br>
 * 表主键：ID<br>
 */
public class ZCArticleCacheInfoSchema extends Schema {
	private Long ID;

	private String CacheIndex;

	private String CacheFileName;

	private String CacheFilePath;

	private String CacheType;

	private String ParentCacheIndex;

	private String HasChild;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	private String Prop4;

	private String Prop5;

	private Date CreateDate;

	private Date ModifyDate;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 0 , 0 , true , true),
		new SchemaColumn("CacheIndex", DataColumn.STRING, 1, 200 , 0 , false , false),
		new SchemaColumn("CacheFileName", DataColumn.STRING, 2, 2000 , 0 , false , false),
		new SchemaColumn("CacheFilePath", DataColumn.STRING, 3, 2000 , 0 , false , false),
		new SchemaColumn("CacheType", DataColumn.STRING, 4, 20 , 0 , false , false),
		new SchemaColumn("ParentCacheIndex", DataColumn.STRING, 5, 2000 , 0 , false , false),
		new SchemaColumn("HasChild", DataColumn.STRING, 6, 2 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 7, 200 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 8, 200 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 9, 200 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 10, 200 , 0 , false , false),
		new SchemaColumn("Prop5", DataColumn.STRING, 11, 200 , 0 , false , false),
		new SchemaColumn("CreateDate", DataColumn.DATETIME, 12, 0 , 0 , false , false),
		new SchemaColumn("ModifyDate", DataColumn.DATETIME, 13, 0 , 0 , false , false)
	};

	public static final String _TableCode = "ZCArticleCacheInfo";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into ZCArticleCacheInfo values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZCArticleCacheInfo set ID=?,CacheIndex=?,CacheFileName=?,CacheFilePath=?,CacheType=?,ParentCacheIndex=?,HasChild=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,Prop5=?,CreateDate=?,ModifyDate=? where ID=?";

	protected static final String _DeleteSQL = "delete from ZCArticleCacheInfo  where ID=?";

	protected static final String _FillAllSQL = "select * from ZCArticleCacheInfo  where ID=?";

	public ZCArticleCacheInfoSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[14];
	}

	protected Schema newInstance(){
		return new ZCArticleCacheInfoSchema();
	}

	protected SchemaSet newSet(){
		return new ZCArticleCacheInfoSet();
	}

	public ZCArticleCacheInfoSet query() {
		return query(null, -1, -1);
	}

	public ZCArticleCacheInfoSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZCArticleCacheInfoSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZCArticleCacheInfoSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZCArticleCacheInfoSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){CacheIndex = (String)v;return;}
		if (i == 2){CacheFileName = (String)v;return;}
		if (i == 3){CacheFilePath = (String)v;return;}
		if (i == 4){CacheType = (String)v;return;}
		if (i == 5){ParentCacheIndex = (String)v;return;}
		if (i == 6){HasChild = (String)v;return;}
		if (i == 7){Prop1 = (String)v;return;}
		if (i == 8){Prop2 = (String)v;return;}
		if (i == 9){Prop3 = (String)v;return;}
		if (i == 10){Prop4 = (String)v;return;}
		if (i == 11){Prop5 = (String)v;return;}
		if (i == 12){CreateDate = (Date)v;return;}
		if (i == 13){ModifyDate = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return CacheIndex;}
		if (i == 2){return CacheFileName;}
		if (i == 3){return CacheFilePath;}
		if (i == 4){return CacheType;}
		if (i == 5){return ParentCacheIndex;}
		if (i == 6){return HasChild;}
		if (i == 7){return Prop1;}
		if (i == 8){return Prop2;}
		if (i == 9){return Prop3;}
		if (i == 10){return Prop4;}
		if (i == 11){return Prop5;}
		if (i == 12){return CreateDate;}
		if (i == 13){return ModifyDate;}
		return null;
	}

	/**
	* 获取字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public long getID() {
		if(ID==null){return 0;}
		return ID.longValue();
	}

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setID(long iD) {
		this.ID = new Long(iD);
    }

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setID(String iD) {
		if (iD == null){
			this.ID = null;
			return;
		}
		this.ID = new Long(iD);
    }

	/**
	* 获取字段CacheIndex的值，该字段的<br>
	* 字段名称 :缓存索引<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	缓存索引值，根据此索引查找到缓存文件<br>
	*/
	public String getCacheIndex() {
		return CacheIndex;
	}

	/**
	* 设置字段CacheIndex的值，该字段的<br>
	* 字段名称 :缓存索引<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	缓存索引值，根据此索引查找到缓存文件<br>
	*/
	public void setCacheIndex(String cacheIndex) {
		this.CacheIndex = cacheIndex;
    }

	/**
	* 获取字段CacheFileName的值，该字段的<br>
	* 字段名称 :缓存文件名称<br>
	* 数据类型 :varchar(2000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	缓存物理文件名称<br>
	*/
	public String getCacheFileName() {
		return CacheFileName;
	}

	/**
	* 设置字段CacheFileName的值，该字段的<br>
	* 字段名称 :缓存文件名称<br>
	* 数据类型 :varchar(2000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	缓存物理文件名称<br>
	*/
	public void setCacheFileName(String cacheFileName) {
		this.CacheFileName = cacheFileName;
    }

	/**
	* 获取字段CacheFilePath的值，该字段的<br>
	* 字段名称 :缓存文件路径<br>
	* 数据类型 :varchar(2000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCacheFilePath() {
		return CacheFilePath;
	}

	/**
	* 设置字段CacheFilePath的值，该字段的<br>
	* 字段名称 :缓存文件路径<br>
	* 数据类型 :varchar(2000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCacheFilePath(String cacheFilePath) {
		this.CacheFilePath = cacheFilePath;
    }

	/**
	* 获取字段CacheType的值，该字段的<br>
	* 字段名称 :缓存类型<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	article：文章信息缓存 product：产品信息缓存<br>
	*/
	public String getCacheType() {
		return CacheType;
	}

	/**
	* 设置字段CacheType的值，该字段的<br>
	* 字段名称 :缓存类型<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	article：文章信息缓存 product：产品信息缓存<br>
	*/
	public void setCacheType(String cacheType) {
		this.CacheType = cacheType;
    }

	/**
	* 获取字段ParentCacheIndex的值，该字段的<br>
	* 字段名称 :父缓存索引<br>
	* 数据类型 :varchar(2000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getParentCacheIndex() {
		return ParentCacheIndex;
	}

	/**
	* 设置字段ParentCacheIndex的值，该字段的<br>
	* 字段名称 :父缓存索引<br>
	* 数据类型 :varchar(2000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setParentCacheIndex(String parentCacheIndex) {
		this.ParentCacheIndex = parentCacheIndex;
    }

	/**
	* 获取字段HasChild的值，该字段的<br>
	* 字段名称 :是否有子索引<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getHasChild() {
		return HasChild;
	}

	/**
	* 设置字段HasChild的值，该字段的<br>
	* 字段名称 :是否有子索引<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setHasChild(String hasChild) {
		this.HasChild = hasChild;
    }

	/**
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :Prop1<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :Prop1<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* 获取字段Prop2的值，该字段的<br>
	* 字段名称 :Prop2<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* 设置字段Prop2的值，该字段的<br>
	* 字段名称 :Prop2<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

	/**
	* 获取字段Prop3的值，该字段的<br>
	* 字段名称 :Prop3<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp3() {
		return Prop3;
	}

	/**
	* 设置字段Prop3的值，该字段的<br>
	* 字段名称 :Prop3<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp3(String prop3) {
		this.Prop3 = prop3;
    }

	/**
	* 获取字段Prop4的值，该字段的<br>
	* 字段名称 :Prop4<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp4() {
		return Prop4;
	}

	/**
	* 设置字段Prop4的值，该字段的<br>
	* 字段名称 :Prop4<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp4(String prop4) {
		this.Prop4 = prop4;
    }

	/**
	* 获取字段Prop5的值，该字段的<br>
	* 字段名称 :Prop5<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp5() {
		return Prop5;
	}

	/**
	* 设置字段Prop5的值，该字段的<br>
	* 字段名称 :Prop5<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp5(String prop5) {
		this.Prop5 = prop5;
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

}