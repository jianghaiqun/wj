package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;
import java.util.Date;

/**
 * 表名称：站点表<br>
 * 表代码：ZCSite<br>
 * 表主键：ID <br>
 */
public class ZCSiteSchema extends Schema {
	private Long ID;

	private String Name;

	private String Alias;

	private String WapAlias;

	private String Info;

	private String BranchInnerCode;

	private String URL;

	private String WapURL;

	private String WapResourceURL;

	private String RootPath;

	private String IndexTemplate;

	private String ListTemplate;

	private String DetailTemplate;

	private String EditorCss;

	private String Workflow;

	private Long OrderFlag;

	private String LogoFileName;

	private String MessageBoardFlag;

	private String CommentAuditFlag;

	private Long ChannelCount;

	private Long MagzineCount;

	private Long SpecialCount;

	private Long ImageLibCount;

	private Long VideoLibCount;

	private Long AudioLibCount;

	private Long AttachmentLibCount;

	private Long ArticleCount;

	private Long HitCount;

	private String ConfigXML;

	private String AutoIndexFlag;

	private String AutoStatFlag;

	private String HeaderTemplate;

	private String TopTemplate;

	private String BottomTemplate;

	private String AllowContribute;

	private String BBSEnableFlag;

	private String ShopEnableFlag;

	private String Meta_Keywords;

	private String Meta_Description;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	private String Prop4;

	private String Prop5;

	private String Prop6;

	private String StaticResourcePath;

	private String ProductResourcePath;

	private String JsResourcePath;

	private String Index_Keywords;
	
	private String AddUser;

	private Date AddTime;

	private String ModifyUser;

	private Date ModifyTime;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 20 , 0 , true , true),
		new SchemaColumn("Name", DataColumn.STRING, 1, 100 , 0 , true , false),
		new SchemaColumn("Alias", DataColumn.STRING, 2, 100 , 0 , true , false),
		new SchemaColumn("WapAlias", DataColumn.STRING, 3, 100 , 0 , true , false),
		new SchemaColumn("Info", DataColumn.STRING, 4, 100 , 0 , false , false),
		new SchemaColumn("BranchInnerCode", DataColumn.STRING, 5, 100 , 0 , true , false),
		new SchemaColumn("URL", DataColumn.STRING, 6, 100 , 0 , true , false),
		new SchemaColumn("WapURL", DataColumn.STRING, 7, 100 , 0 , true , false),
		new SchemaColumn("WapResourceURL", DataColumn.STRING, 8, 100 , 0 , false , false),
		new SchemaColumn("RootPath", DataColumn.STRING, 9, 100 , 0 , false , false),
		new SchemaColumn("IndexTemplate", DataColumn.STRING, 10, 100 , 0 , false , false),
		new SchemaColumn("ListTemplate", DataColumn.STRING, 11, 100 , 0 , false , false),
		new SchemaColumn("DetailTemplate", DataColumn.STRING, 12, 100 , 0 , false , false),
		new SchemaColumn("EditorCss", DataColumn.STRING, 13, 100 , 0 , false , false),
		new SchemaColumn("Workflow", DataColumn.STRING, 14, 100 , 0 , false , false),
		new SchemaColumn("OrderFlag", DataColumn.LONG, 15, 20 , 0 , false , false),
		new SchemaColumn("LogoFileName", DataColumn.STRING, 16, 100 , 0 , false , false),
		new SchemaColumn("MessageBoardFlag", DataColumn.STRING, 17, 2 , 0 , false , false),
		new SchemaColumn("CommentAuditFlag", DataColumn.STRING, 18, 1 , 0 , false , false),
		new SchemaColumn("ChannelCount", DataColumn.LONG, 19, 20 , 0 , true , false),
		new SchemaColumn("MagzineCount", DataColumn.LONG, 20, 20 , 0 , true , false),
		new SchemaColumn("SpecialCount", DataColumn.LONG, 21, 20 , 0 , true , false),
		new SchemaColumn("ImageLibCount", DataColumn.LONG, 22, 20 , 0 , true , false),
		new SchemaColumn("VideoLibCount", DataColumn.LONG, 23, 20 , 0 , true , false),
		new SchemaColumn("AudioLibCount", DataColumn.LONG, 24, 20 , 0 , true , false),
		new SchemaColumn("AttachmentLibCount", DataColumn.LONG, 25, 20 , 0 , true , false),
		new SchemaColumn("ArticleCount", DataColumn.LONG, 26, 20 , 0 , true , false),
		new SchemaColumn("HitCount", DataColumn.LONG, 27, 20 , 0 , true , false),
		new SchemaColumn("ConfigXML", DataColumn.CLOB, 28, 0 , 0 , false , false),
		new SchemaColumn("AutoIndexFlag", DataColumn.STRING, 29, 2 , 0 , false , false),
		new SchemaColumn("AutoStatFlag", DataColumn.STRING, 30, 2 , 0 , false , false),
		new SchemaColumn("HeaderTemplate", DataColumn.STRING, 31, 100 , 0 , false , false),
		new SchemaColumn("TopTemplate", DataColumn.STRING, 32, 100 , 0 , false , false),
		new SchemaColumn("BottomTemplate", DataColumn.STRING, 33, 100 , 0 , false , false),
		new SchemaColumn("AllowContribute", DataColumn.STRING, 34, 2 , 0 , false , false),
		new SchemaColumn("BBSEnableFlag", DataColumn.STRING, 35, 2 , 0 , false , false),
		new SchemaColumn("ShopEnableFlag", DataColumn.STRING, 36, 2 , 0 , false , false),
		new SchemaColumn("Meta_Keywords", DataColumn.STRING, 37, 1000 , 0 , false , false),
		new SchemaColumn("Meta_Description", DataColumn.STRING, 38, 1000 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 39, 100 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 40, 100 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 41, 100 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 42, 100 , 0 , false , false),
		new SchemaColumn("Prop5", DataColumn.STRING, 43, 100 , 0 , false , false),
		new SchemaColumn("Prop6", DataColumn.STRING, 44, 100 , 0 , false , false),
		new SchemaColumn("StaticResourcePath", DataColumn.STRING, 45, 100 , 0 , false , false),
		new SchemaColumn("ProductResourcePath", DataColumn.STRING, 46, 100 , 0 , false , false),
		new SchemaColumn("JsResourcePath", DataColumn.STRING, 47, 100 , 0 , false , false),
		new SchemaColumn("Index_Keywords", DataColumn.STRING, 48, 1000 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 49, 200 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 50, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 51, 200 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 52, 0 , 0 , false , false)
	};

	public static final String _TableCode = "ZCSite";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into ZCSite values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZCSite set ID=?,Name=?,Alias=?,WapAlias=?,Info=?,BranchInnerCode=?,URL=?,WapURL=?,WapResourceURL=?,RootPath=?,IndexTemplate=?,ListTemplate=?,DetailTemplate=?,EditorCss=?,Workflow=?,OrderFlag=?,LogoFileName=?,MessageBoardFlag=?,CommentAuditFlag=?,ChannelCount=?,MagzineCount=?,SpecialCount=?,ImageLibCount=?,VideoLibCount=?,AudioLibCount=?,AttachmentLibCount=?,ArticleCount=?,HitCount=?,ConfigXML=?,AutoIndexFlag=?,AutoStatFlag=?,HeaderTemplate=?,TopTemplate=?,BottomTemplate=?,AllowContribute=?,BBSEnableFlag=?,ShopEnableFlag=?,Meta_Keywords=?,Meta_Description=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,Prop5=?,Prop6=?,StaticResourcePath=?,ProductResourcePath=?,JsResourcePath=?,Index_Keywords=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where ID=?";

	protected static final String _DeleteSQL = "delete from ZCSite  where ID=?";

	protected static final String _FillAllSQL = "select * from ZCSite  where ID=?";

	public ZCSiteSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[53];
	}

	protected Schema newInstance(){
		return new ZCSiteSchema();
	}

	protected SchemaSet newSet(){
		return new ZCSiteSet();
	}

	public ZCSiteSet query() {
		return query(null, -1, -1);
	}

	public ZCSiteSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZCSiteSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZCSiteSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZCSiteSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){Name = (String)v;return;}
		if (i == 2){Alias = (String)v;return;}
		if (i == 3){WapAlias = (String)v;return;}
		if (i == 4){Info = (String)v;return;}
		if (i == 5){BranchInnerCode = (String)v;return;}
		if (i == 6){URL = (String)v;return;}
		if (i == 7){WapURL = (String)v;return;}
		if (i == 8){WapResourceURL = (String)v;return;}
		if (i == 9){RootPath = (String)v;return;}
		if (i == 10){IndexTemplate = (String)v;return;}
		if (i == 11){ListTemplate = (String)v;return;}
		if (i == 12){DetailTemplate = (String)v;return;}
		if (i == 13){EditorCss = (String)v;return;}
		if (i == 14){Workflow = (String)v;return;}
		if (i == 15){if(v==null){OrderFlag = null;}else{OrderFlag = new Long(v.toString());}return;}
		if (i == 16){LogoFileName = (String)v;return;}
		if (i == 17){MessageBoardFlag = (String)v;return;}
		if (i == 18){CommentAuditFlag = (String)v;return;}
		if (i == 19){if(v==null){ChannelCount = null;}else{ChannelCount = new Long(v.toString());}return;}
		if (i == 20){if(v==null){MagzineCount = null;}else{MagzineCount = new Long(v.toString());}return;}
		if (i == 21){if(v==null){SpecialCount = null;}else{SpecialCount = new Long(v.toString());}return;}
		if (i == 22){if(v==null){ImageLibCount = null;}else{ImageLibCount = new Long(v.toString());}return;}
		if (i == 23){if(v==null){VideoLibCount = null;}else{VideoLibCount = new Long(v.toString());}return;}
		if (i == 24){if(v==null){AudioLibCount = null;}else{AudioLibCount = new Long(v.toString());}return;}
		if (i == 25){if(v==null){AttachmentLibCount = null;}else{AttachmentLibCount = new Long(v.toString());}return;}
		if (i == 26){if(v==null){ArticleCount = null;}else{ArticleCount = new Long(v.toString());}return;}
		if (i == 27){if(v==null){HitCount = null;}else{HitCount = new Long(v.toString());}return;}
		if (i == 28){ConfigXML = (String)v;return;}
		if (i == 29){AutoIndexFlag = (String)v;return;}
		if (i == 30){AutoStatFlag = (String)v;return;}
		if (i == 31){HeaderTemplate = (String)v;return;}
		if (i == 32){TopTemplate = (String)v;return;}
		if (i == 33){BottomTemplate = (String)v;return;}
		if (i == 34){AllowContribute = (String)v;return;}
		if (i == 35){BBSEnableFlag = (String)v;return;}
		if (i == 36){ShopEnableFlag = (String)v;return;}
		if (i == 37){Meta_Keywords = (String)v;return;}
		if (i == 38){Meta_Description = (String)v;return;}
		if (i == 39){Prop1 = (String)v;return;}
		if (i == 40){Prop2 = (String)v;return;}
		if (i == 41){Prop3 = (String)v;return;}
		if (i == 42){Prop4 = (String)v;return;}
		if (i == 43){Prop5 = (String)v;return;}
		if (i == 44){Prop6 = (String)v;return;}
		if (i == 45){StaticResourcePath = (String)v;return;}
		if (i == 46){ProductResourcePath = (String)v;return;}
		if (i == 47){JsResourcePath = (String)v;return;}
		if (i == 48){Index_Keywords = (String)v;return;}
		if (i == 49){AddUser = (String)v;return;}
		if (i == 50){AddTime = (Date)v;return;}
		if (i == 51){ModifyUser = (String)v;return;}
		if (i == 52){ModifyTime = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return Name;}
		if (i == 2){return Alias;}
		if (i == 3){return WapAlias;}
		if (i == 4){return Info;}
		if (i == 5){return BranchInnerCode;}
		if (i == 6){return URL;}
		if (i == 7){return WapURL;}
		if (i == 8){return WapResourceURL;}
		if (i == 9){return RootPath;}
		if (i == 10){return IndexTemplate;}
		if (i == 11){return ListTemplate;}
		if (i == 12){return DetailTemplate;}
		if (i == 13){return EditorCss;}
		if (i == 14){return Workflow;}
		if (i == 15){return OrderFlag;}
		if (i == 16){return LogoFileName;}
		if (i == 17){return MessageBoardFlag;}
		if (i == 18){return CommentAuditFlag;}
		if (i == 19){return ChannelCount;}
		if (i == 20){return MagzineCount;}
		if (i == 21){return SpecialCount;}
		if (i == 22){return ImageLibCount;}
		if (i == 23){return VideoLibCount;}
		if (i == 24){return AudioLibCount;}
		if (i == 25){return AttachmentLibCount;}
		if (i == 26){return ArticleCount;}
		if (i == 27){return HitCount;}
		if (i == 28){return ConfigXML;}
		if (i == 29){return AutoIndexFlag;}
		if (i == 30){return AutoStatFlag;}
		if (i == 31){return HeaderTemplate;}
		if (i == 32){return TopTemplate;}
		if (i == 33){return BottomTemplate;}
		if (i == 34){return AllowContribute;}
		if (i == 35){return BBSEnableFlag;}
		if (i == 36){return ShopEnableFlag;}
		if (i == 37){return Meta_Keywords;}
		if (i == 38){return Meta_Description;}
		if (i == 39){return Prop1;}
		if (i == 40){return Prop2;}
		if (i == 41){return Prop3;}
		if (i == 42){return Prop4;}
		if (i == 43){return Prop5;}
		if (i == 44){return Prop6;}
		if (i == 45){return StaticResourcePath;}
		if (i == 46){return ProductResourcePath;}
		if (i == 47){return JsResourcePath;}
		if (i == 48){return Index_Keywords;}
		if (i == 49){return AddUser;}
		if (i == 50){return AddTime;}
		if (i == 51){return ModifyUser;}
		if (i == 52){return ModifyTime;}
		return null;
	}

	/**
	* 获取字段ID的值，该字段的<br>
	* 字段名称 :站点ID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public long getID() {
		if(ID==null){return 0;}
		return ID.longValue();
	}

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :站点ID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setID(long iD) {
		this.ID = new Long(iD);
    }

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :站点ID<br>
	* 数据类型 :bigint(20)<br>
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
	* 获取字段Name的值，该字段的<br>
	* 字段名称 :站点名称<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getName() {
		return Name;
	}

	/**
	* 设置字段Name的值，该字段的<br>
	* 字段名称 :站点名称<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setName(String name) {
		this.Name = name;
    }

	/**
	* 获取字段Alias的值，该字段的<br>
	* 字段名称 :站点代码<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getAlias() {
		return Alias;
	}

	/**
	* 设置字段Alias的值，该字段的<br>
	* 字段名称 :站点代码<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setAlias(String alias) {
		this.Alias = alias;
    }

	/**
	* 获取字段WapAlias的值，该字段的<br>
	* 字段名称 :Wap站点代码<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getWapAlias() {
		return WapAlias;
	}

	/**
	* 设置字段WapAlias的值，该字段的<br>
	* 字段名称 :Wap站点代码<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setWapAlias(String wapAlias) {
		this.WapAlias = wapAlias;
    }

	/**
	* 获取字段Info的值，该字段的<br>
	* 字段名称 :站点描述<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getInfo() {
		return Info;
	}

	/**
	* 设置字段Info的值，该字段的<br>
	* 字段名称 :站点描述<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setInfo(String info) {
		this.Info = info;
    }

	/**
	* 获取字段BranchInnerCode的值，该字段的<br>
	* 字段名称 :所属机构内部编码<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getBranchInnerCode() {
		return BranchInnerCode;
	}

	/**
	* 设置字段BranchInnerCode的值，该字段的<br>
	* 字段名称 :所属机构内部编码<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setBranchInnerCode(String branchInnerCode) {
		this.BranchInnerCode = branchInnerCode;
    }

	/**
	* 获取字段URL的值，该字段的<br>
	* 字段名称 :站点URL<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getURL() {
		return URL;
	}

	/**
	* 设置字段URL的值，该字段的<br>
	* 字段名称 :站点URL<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setURL(String uRL) {
		this.URL = uRL;
    }

	/**
	* 获取字段WapURL的值，该字段的<br>
	* 字段名称 :Wap站点URL<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getWapURL() {
		return WapURL;
	}

	/**
	* 设置字段WapURL的值，该字段的<br>
	* 字段名称 :Wap站点URL<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setWapURL(String wapURL) {
		this.WapURL = wapURL;
    }

	/**
	* 获取字段WapResourceURL的值，该字段的<br>
	* 字段名称 :Wap静态站点URL<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getWapResourceURL() {
		return WapResourceURL;
	}

	/**
	* 设置字段WapResourceURL的值，该字段的<br>
	* 字段名称 :Wap静态站点URL<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setWapResourceURL(String wapResourceURL) {
		this.WapResourceURL = wapResourceURL;
    }

	/**
	* 获取字段RootPath的值，该字段的<br>
	* 字段名称 :根目录<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getRootPath() {
		return RootPath;
	}

	/**
	* 设置字段RootPath的值，该字段的<br>
	* 字段名称 :根目录<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRootPath(String rootPath) {
		this.RootPath = rootPath;
    }

	/**
	* 获取字段IndexTemplate的值，该字段的<br>
	* 字段名称 :首页模板<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getIndexTemplate() {
		return IndexTemplate;
	}

	/**
	* 设置字段IndexTemplate的值，该字段的<br>
	* 字段名称 :首页模板<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setIndexTemplate(String indexTemplate) {
		this.IndexTemplate = indexTemplate;
    }

	/**
	* 获取字段ListTemplate的值，该字段的<br>
	* 字段名称 :默认列表模板<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getListTemplate() {
		return ListTemplate;
	}

	/**
	* 设置字段ListTemplate的值，该字段的<br>
	* 字段名称 :默认列表模板<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setListTemplate(String listTemplate) {
		this.ListTemplate = listTemplate;
    }

	/**
	* 获取字段DetailTemplate的值，该字段的<br>
	* 字段名称 :默认详细页模板<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getDetailTemplate() {
		return DetailTemplate;
	}

	/**
	* 设置字段DetailTemplate的值，该字段的<br>
	* 字段名称 :默认详细页模板<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDetailTemplate(String detailTemplate) {
		this.DetailTemplate = detailTemplate;
    }

	/**
	* 获取字段EditorCss的值，该字段的<br>
	* 字段名称 :编辑器样式<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getEditorCss() {
		return EditorCss;
	}

	/**
	* 设置字段EditorCss的值，该字段的<br>
	* 字段名称 :编辑器样式<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setEditorCss(String editorCss) {
		this.EditorCss = editorCss;
    }

	/**
	* 获取字段Workflow的值，该字段的<br>
	* 字段名称 :工作流名称<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getWorkflow() {
		return Workflow;
	}

	/**
	* 设置字段Workflow的值，该字段的<br>
	* 字段名称 :工作流名称<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setWorkflow(String workflow) {
		this.Workflow = workflow;
    }

	/**
	* 获取字段OrderFlag的值，该字段的<br>
	* 字段名称 :排序权值<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getOrderFlag() {
		if(OrderFlag==null){return 0;}
		return OrderFlag.longValue();
	}

	/**
	* 设置字段OrderFlag的值，该字段的<br>
	* 字段名称 :排序权值<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setOrderFlag(long orderFlag) {
		this.OrderFlag = new Long(orderFlag);
    }

	/**
	* 设置字段OrderFlag的值，该字段的<br>
	* 字段名称 :排序权值<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setOrderFlag(String orderFlag) {
		if (orderFlag == null){
			this.OrderFlag = null;
			return;
		}
		this.OrderFlag = new Long(orderFlag);
    }

	/**
	* 获取字段LogoFileName的值，该字段的<br>
	* 字段名称 :站点Logo<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getLogoFileName() {
		return LogoFileName;
	}

	/**
	* 设置字段LogoFileName的值，该字段的<br>
	* 字段名称 :站点Logo<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setLogoFileName(String logoFileName) {
		this.LogoFileName = logoFileName;
    }

	/**
	* 获取字段MessageBoardFlag的值，该字段的<br>
	* 字段名称 :留言板标志<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	0-无留言板<br>
	1-有留言板<br>
	*/
	public String getMessageBoardFlag() {
		return MessageBoardFlag;
	}

	/**
	* 设置字段MessageBoardFlag的值，该字段的<br>
	* 字段名称 :留言板标志<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	0-无留言板<br>
	1-有留言板<br>
	*/
	public void setMessageBoardFlag(String messageBoardFlag) {
		this.MessageBoardFlag = messageBoardFlag;
    }

	/**
	* 获取字段CommentAuditFlag的值，该字段的<br>
	* 字段名称 :评论是否需审核<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCommentAuditFlag() {
		return CommentAuditFlag;
	}

	/**
	* 设置字段CommentAuditFlag的值，该字段的<br>
	* 字段名称 :评论是否需审核<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCommentAuditFlag(String commentAuditFlag) {
		this.CommentAuditFlag = commentAuditFlag;
    }

	/**
	* 获取字段ChannelCount的值，该字段的<br>
	* 字段名称 :频道数<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getChannelCount() {
		if(ChannelCount==null){return 0;}
		return ChannelCount.longValue();
	}

	/**
	* 设置字段ChannelCount的值，该字段的<br>
	* 字段名称 :频道数<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setChannelCount(long channelCount) {
		this.ChannelCount = new Long(channelCount);
    }

	/**
	* 设置字段ChannelCount的值，该字段的<br>
	* 字段名称 :频道数<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setChannelCount(String channelCount) {
		if (channelCount == null){
			this.ChannelCount = null;
			return;
		}
		this.ChannelCount = new Long(channelCount);
    }

	/**
	* 获取字段MagzineCount的值，该字段的<br>
	* 字段名称 :期刊数<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getMagzineCount() {
		if(MagzineCount==null){return 0;}
		return MagzineCount.longValue();
	}

	/**
	* 设置字段MagzineCount的值，该字段的<br>
	* 字段名称 :期刊数<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setMagzineCount(long magzineCount) {
		this.MagzineCount = new Long(magzineCount);
    }

	/**
	* 设置字段MagzineCount的值，该字段的<br>
	* 字段名称 :期刊数<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setMagzineCount(String magzineCount) {
		if (magzineCount == null){
			this.MagzineCount = null;
			return;
		}
		this.MagzineCount = new Long(magzineCount);
    }

	/**
	* 获取字段SpecialCount的值，该字段的<br>
	* 字段名称 :专题数<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getSpecialCount() {
		if(SpecialCount==null){return 0;}
		return SpecialCount.longValue();
	}

	/**
	* 设置字段SpecialCount的值，该字段的<br>
	* 字段名称 :专题数<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setSpecialCount(long specialCount) {
		this.SpecialCount = new Long(specialCount);
    }

	/**
	* 设置字段SpecialCount的值，该字段的<br>
	* 字段名称 :专题数<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setSpecialCount(String specialCount) {
		if (specialCount == null){
			this.SpecialCount = null;
			return;
		}
		this.SpecialCount = new Long(specialCount);
    }

	/**
	* 获取字段ImageLibCount的值，该字段的<br>
	* 字段名称 :图片库数<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getImageLibCount() {
		if(ImageLibCount==null){return 0;}
		return ImageLibCount.longValue();
	}

	/**
	* 设置字段ImageLibCount的值，该字段的<br>
	* 字段名称 :图片库数<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setImageLibCount(long imageLibCount) {
		this.ImageLibCount = new Long(imageLibCount);
    }

	/**
	* 设置字段ImageLibCount的值，该字段的<br>
	* 字段名称 :图片库数<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setImageLibCount(String imageLibCount) {
		if (imageLibCount == null){
			this.ImageLibCount = null;
			return;
		}
		this.ImageLibCount = new Long(imageLibCount);
    }

	/**
	* 获取字段VideoLibCount的值，该字段的<br>
	* 字段名称 :视频库数<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getVideoLibCount() {
		if(VideoLibCount==null){return 0;}
		return VideoLibCount.longValue();
	}

	/**
	* 设置字段VideoLibCount的值，该字段的<br>
	* 字段名称 :视频库数<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setVideoLibCount(long videoLibCount) {
		this.VideoLibCount = new Long(videoLibCount);
    }

	/**
	* 设置字段VideoLibCount的值，该字段的<br>
	* 字段名称 :视频库数<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setVideoLibCount(String videoLibCount) {
		if (videoLibCount == null){
			this.VideoLibCount = null;
			return;
		}
		this.VideoLibCount = new Long(videoLibCount);
    }

	/**
	* 获取字段AudioLibCount的值，该字段的<br>
	* 字段名称 :音频库数<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getAudioLibCount() {
		if(AudioLibCount==null){return 0;}
		return AudioLibCount.longValue();
	}

	/**
	* 设置字段AudioLibCount的值，该字段的<br>
	* 字段名称 :音频库数<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setAudioLibCount(long audioLibCount) {
		this.AudioLibCount = new Long(audioLibCount);
    }

	/**
	* 设置字段AudioLibCount的值，该字段的<br>
	* 字段名称 :音频库数<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setAudioLibCount(String audioLibCount) {
		if (audioLibCount == null){
			this.AudioLibCount = null;
			return;
		}
		this.AudioLibCount = new Long(audioLibCount);
    }

	/**
	* 获取字段AttachmentLibCount的值，该字段的<br>
	* 字段名称 :附件库数<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getAttachmentLibCount() {
		if(AttachmentLibCount==null){return 0;}
		return AttachmentLibCount.longValue();
	}

	/**
	* 设置字段AttachmentLibCount的值，该字段的<br>
	* 字段名称 :附件库数<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setAttachmentLibCount(long attachmentLibCount) {
		this.AttachmentLibCount = new Long(attachmentLibCount);
    }

	/**
	* 设置字段AttachmentLibCount的值，该字段的<br>
	* 字段名称 :附件库数<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setAttachmentLibCount(String attachmentLibCount) {
		if (attachmentLibCount == null){
			this.AttachmentLibCount = null;
			return;
		}
		this.AttachmentLibCount = new Long(attachmentLibCount);
    }

	/**
	* 获取字段ArticleCount的值，该字段的<br>
	* 字段名称 :文章数<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	1-是<br>
	0-不是<br>
	*/
	public long getArticleCount() {
		if(ArticleCount==null){return 0;}
		return ArticleCount.longValue();
	}

	/**
	* 设置字段ArticleCount的值，该字段的<br>
	* 字段名称 :文章数<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	1-是<br>
	0-不是<br>
	*/
	public void setArticleCount(long articleCount) {
		this.ArticleCount = new Long(articleCount);
    }

	/**
	* 设置字段ArticleCount的值，该字段的<br>
	* 字段名称 :文章数<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	1-是<br>
	0-不是<br>
	*/
	public void setArticleCount(String articleCount) {
		if (articleCount == null){
			this.ArticleCount = null;
			return;
		}
		this.ArticleCount = new Long(articleCount);
    }

	/**
	* 获取字段HitCount的值，该字段的<br>
	* 字段名称 :点击数<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getHitCount() {
		if(HitCount==null){return 0;}
		return HitCount.longValue();
	}

	/**
	* 设置字段HitCount的值，该字段的<br>
	* 字段名称 :点击数<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setHitCount(long hitCount) {
		this.HitCount = new Long(hitCount);
    }

	/**
	* 设置字段HitCount的值，该字段的<br>
	* 字段名称 :点击数<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setHitCount(String hitCount) {
		if (hitCount == null){
			this.HitCount = null;
			return;
		}
		this.HitCount = new Long(hitCount);
    }

	/**
	* 获取字段ConfigXML的值，该字段的<br>
	* 字段名称 :站点配置<br>
	* 数据类型 :mediumtext<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getConfigXML() {
		return ConfigXML;
	}

	/**
	* 设置字段ConfigXML的值，该字段的<br>
	* 字段名称 :站点配置<br>
	* 数据类型 :mediumtext<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setConfigXML(String configXML) {
		this.ConfigXML = configXML;
    }

	/**
	* 获取字段AutoIndexFlag的值，该字段的<br>
	* 字段名称 :是否自动生成索引<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAutoIndexFlag() {
		return AutoIndexFlag;
	}

	/**
	* 设置字段AutoIndexFlag的值，该字段的<br>
	* 字段名称 :是否自动生成索引<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAutoIndexFlag(String autoIndexFlag) {
		this.AutoIndexFlag = autoIndexFlag;
    }

	/**
	* 获取字段AutoStatFlag的值，该字段的<br>
	* 字段名称 :是否自动添加统计<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAutoStatFlag() {
		return AutoStatFlag;
	}

	/**
	* 设置字段AutoStatFlag的值，该字段的<br>
	* 字段名称 :是否自动添加统计<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAutoStatFlag(String autoStatFlag) {
		this.AutoStatFlag = autoStatFlag;
    }

	/**
	* 获取字段HeaderTemplate的值，该字段的<br>
	* 字段名称 :头部模板<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getHeaderTemplate() {
		return HeaderTemplate;
	}

	/**
	* 设置字段HeaderTemplate的值，该字段的<br>
	* 字段名称 :头部模板<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setHeaderTemplate(String headerTemplate) {
		this.HeaderTemplate = headerTemplate;
    }

	/**
	* 获取字段TopTemplate的值，该字段的<br>
	* 字段名称 :顶部模板<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getTopTemplate() {
		return TopTemplate;
	}

	/**
	* 设置字段TopTemplate的值，该字段的<br>
	* 字段名称 :顶部模板<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setTopTemplate(String topTemplate) {
		this.TopTemplate = topTemplate;
    }

	/**
	* 获取字段BottomTemplate的值，该字段的<br>
	* 字段名称 :底部模板<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getBottomTemplate() {
		return BottomTemplate;
	}

	/**
	* 设置字段BottomTemplate的值，该字段的<br>
	* 字段名称 :底部模板<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setBottomTemplate(String bottomTemplate) {
		this.BottomTemplate = bottomTemplate;
    }

	/**
	* 获取字段AllowContribute的值，该字段的<br>
	* 字段名称 :是否允许投稿<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAllowContribute() {
		return AllowContribute;
	}

	/**
	* 设置字段AllowContribute的值，该字段的<br>
	* 字段名称 :是否允许投稿<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAllowContribute(String allowContribute) {
		this.AllowContribute = allowContribute;
    }

	/**
	* 获取字段BBSEnableFlag的值，该字段的<br>
	* 字段名称 :是否启用论坛<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getBBSEnableFlag() {
		return BBSEnableFlag;
	}

	/**
	* 设置字段BBSEnableFlag的值，该字段的<br>
	* 字段名称 :是否启用论坛<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setBBSEnableFlag(String bBSEnableFlag) {
		this.BBSEnableFlag = bBSEnableFlag;
    }

	/**
	* 获取字段ShopEnableFlag的值，该字段的<br>
	* 字段名称 :是否启用商城<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getShopEnableFlag() {
		return ShopEnableFlag;
	}

	/**
	* 设置字段ShopEnableFlag的值，该字段的<br>
	* 字段名称 :是否启用商城<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setShopEnableFlag(String shopEnableFlag) {
		this.ShopEnableFlag = shopEnableFlag;
    }

	/**
	* 获取字段Meta_Keywords的值，该字段的<br>
	* 字段名称 :meta关键字<br>
	* 数据类型 :varchar(1000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMeta_Keywords() {
		return Meta_Keywords;
	}

	/**
	* 设置字段Meta_Keywords的值，该字段的<br>
	* 字段名称 :meta关键字<br>
	* 数据类型 :varchar(1000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMeta_Keywords(String meta_Keywords) {
		this.Meta_Keywords = meta_Keywords;
    }

	/**
	* 获取字段Meta_Description的值，该字段的<br>
	* 字段名称 :Meta描述<br>
	* 数据类型 :varchar(1000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMeta_Description() {
		return Meta_Description;
	}

	/**
	* 设置字段Meta_Description的值，该字段的<br>
	* 字段名称 :Meta描述<br>
	* 数据类型 :varchar(1000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMeta_Description(String meta_Description) {
		this.Meta_Description = meta_Description;
    }

	/**
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* 获取字段Prop2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* 设置字段Prop2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

	/**
	* 获取字段Prop3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp3() {
		return Prop3;
	}

	/**
	* 设置字段Prop3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp3(String prop3) {
		this.Prop3 = prop3;
    }

	/**
	* 获取字段Prop4的值，该字段的<br>
	* 字段名称 :备用字段4<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp4() {
		return Prop4;
	}

	/**
	* 设置字段Prop4的值，该字段的<br>
	* 字段名称 :备用字段4<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp4(String prop4) {
		this.Prop4 = prop4;
    }

	/**
	* 获取字段Prop5的值，该字段的<br>
	* 字段名称 :备用字段5<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp5() {
		return Prop5;
	}

	/**
	* 设置字段Prop5的值，该字段的<br>
	* 字段名称 :备用字段5<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp5(String prop5) {
		this.Prop5 = prop5;
    }

	/**
	* 获取字段Prop6的值，该字段的<br>
	* 字段名称 :备用字段6<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp6() {
		return Prop6;
	}

	/**
	* 设置字段Prop6的值，该字段的<br>
	* 字段名称 :备用字段6<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp6(String prop6) {
		this.Prop6 = prop6;
    }

	/**
	* 获取字段StaticResourcePath的值，该字段的<br>
	* 字段名称 :静态资源访问路径<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getStaticResourcePath() {
		return StaticResourcePath;
	}

	/**
	* 设置字段StaticResourcePath的值，该字段的<br>
	* 字段名称 :静态资源访问路径<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setStaticResourcePath(String staticResourcePath) {
		this.StaticResourcePath = staticResourcePath;
    }

	/**
	* 获取字段ProductResourcePath的值，该字段的<br>
	* 字段名称 :产品中心资源访问路径<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProductResourcePath() {
		return ProductResourcePath;
	}

	/**
	* 设置字段ProductResourcePath的值，该字段的<br>
	* 字段名称 :产品中心资源访问路径<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProductResourcePath(String productResourcePath) {
		this.ProductResourcePath = productResourcePath;
    }

	/**
	* 获取字段JsResourcePath的值，该字段的<br>
	* 字段名称 :Js静态资源访问路径<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getJsResourcePath() {
		return JsResourcePath;
	}

	/**
	* 设置字段JsResourcePath的值，该字段的<br>
	* 字段名称 :Js静态资源访问路径<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setJsResourcePath(String jsResourcePath) {
		this.JsResourcePath = jsResourcePath;
    }

	/**
	* 获取字段Index_Keywords的值，该字段的<br>
	* 字段名称 :首页关键词<br>
	* 数据类型 :varchar(1000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getIndex_Keywords() {
		return Index_Keywords;
	}

	/**
	* 设置字段Index_Keywords的值，该字段的<br>
	* 字段名称 :首页关键词<br>
	* 数据类型 :varchar(1000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setIndex_Keywords(String index_Keywords) {
		this.Index_Keywords = index_Keywords;
    }
	
	/**
	* 获取字段AddUser的值，该字段的<br>
	* 字段名称 :添加者<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getAddUser() {
		return AddUser;
	}

	/**
	* 设置字段AddUser的值，该字段的<br>
	* 字段名称 :添加者<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setAddUser(String addUser) {
		this.AddUser = addUser;
    }

	/**
	* 获取字段AddTime的值，该字段的<br>
	* 字段名称 :添加时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public Date getAddTime() {
		return AddTime;
	}

	/**
	* 设置字段AddTime的值，该字段的<br>
	* 字段名称 :添加时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setAddTime(Date addTime) {
		this.AddTime = addTime;
    }

	/**
	* 获取字段ModifyUser的值，该字段的<br>
	* 字段名称 :最后修改人<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getModifyUser() {
		return ModifyUser;
	}

	/**
	* 设置字段ModifyUser的值，该字段的<br>
	* 字段名称 :最后修改人<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyUser(String modifyUser) {
		this.ModifyUser = modifyUser;
    }

	/**
	* 获取字段ModifyTime的值，该字段的<br>
	* 字段名称 :最后修改时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getModifyTime() {
		return ModifyTime;
	}

	/**
	* 设置字段ModifyTime的值，该字段的<br>
	* 字段名称 :最后修改时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyTime(Date modifyTime) {
		this.ModifyTime = modifyTime;
    }

}