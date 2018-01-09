package com.sinosoft.framework; 

import java.util.regex.Pattern;

public class Constant {
	/**
	 * 正则表达式，匹配形如${FieldName}的字符串
	 */
	public static final Pattern PatternField = Pattern.compile("\\$\\{(\\w+?)\\}");

	/**
	 * 正则表达式，匹配形如${@FieldName}的字符串
	 */
	public static final Pattern PatternSpeicalField = Pattern.compile("\\$\\{[@#](\\w+?)\\}");

	/**
	 * 正则表达式，匹配形如${Table.FieldName}的字符串
	 */
	public static final Pattern PatternPropField = Pattern.compile("\\$\\{(\\w+?)\\.(\\w+?)(\\|(.*?))??\\}");

	/**
	 * Session中的User对象的属性名
	 */
	public static final String UserAttrName = "_ZVING_USER";

	/**
	 * User对象中验证码字符串的属性名
	 */
	public static final String DefaultAuthKey = "_ZVING_AUTHKEY";

	/**
	 * Cookie中SessionID对应的Cookie项名称，不同的中间件可能有所不同
	 */
	public static final String SessionIDCookieName = "JSESSIONID";

	/**
	 * Response对象中需要游览器执行的JS段
	 */
	public static final String ResponseScriptAttr = "_ZVING_SCRIPT";

	/**
	 * Response对象中需要浏览器显示的消息文本
	 */
	public static final String ResponseMessageAttrName = "_ZVING_MESSAGE";// 

	/**
	 * Response对象中反馈给游览器的状态值，一般0表示有错误，1表示执行成功
	 */
	public static final String ResponseStatusAttrName = "_ZVING_STATUS";

	/**
	 * DataGrid中表示SQL的属性名
	 */
	public static final String DataGridSQL = "_ZVING_DATAGRID_SQL";

	/**
	 * DataGrid中的表示当前页数的属性名，从0开始，0表示第一页
	 */
	public static final String DataGridPageIndex = "_ZVING_PAGEINDEX";

	/**
	 * DataGrid中表示记录总数的属性名
	 */
	public static final String DataGridPageTotal = "_ZVING_PAGETOTAL";

	/**
	 * DataGrid中表示排序方式的属性名，其值形如id desc,name asc
	 */
	public static final String DataGridSortString = "_ZVING_SORTSTRING";

	/**
	 * DataGrid中表示当前动作是插入空白行的属性名
	 */
	public static final String DataGridInsertRow = "_ZVING_INSERTROW";

	/**
	 * DataGrid中表示允许多选的属性名
	 */
	public static final String DataGridMultiSelect = "_ZVING_MULTISELECT";

	/**
	 * DataGrid中表示要求自动填充空白行以保持DataGrid高度的属性名
	 */
	public static final String DataGridAutoFill = "_ZVING_AUTOFILL";

	/**
	 * DataGrid中表示允许内容滚动的属性
	 */
	public static final String DataGridScroll = "_ZVING_SCROLL";

	/**
	 * 表示属性值是一个DataTable
	 */
	public static final String DataTable = "_ZVING_DataTable";

	/**
	 * 表示属性值是唯一ID
	 */
	public static final String ID = "_ZVING_ID";

	/**
	 * 表示属性值是一个后台Page类的方法
	 */
	public static final String Method = "_ZVING_METHOD";

	/**
	 * 表示是否允许分页，值为字 符串true和false
	 */
	public static final String Page = "_ZVING_PAGE";

	/**
	 * 表示大小的属性名，例如分页大小
	 */
	public static final String Size = "_ZVING_SIZE";

	/**
	 * 表示控件标签包含的HTML内容的属性名
	 */
	public static final String TagBody = "_ZVING_TAGBODY";

	/**
	 * 表示树形结构中层级的属性名
	 */
	public static final String TreeLevel = "_ZVING_TREE_LEVEL";

	/**
	 * 树形控件中表示是否延迟加载的属性名
	 */
	public static final String TreeLazy = "_ZVING_TREE_LAZY"; // 延迟加载

	/**
	 * 树形控件中表示是否全部展开的属性名
	 */
	public static final String TreeExpand = "_ZVING_TREE_EXPAND"; // 是否在延迟加载是全部展开

	/**
	 * 树形控件中表示css的属性名
	 */
	public static final String TreeStyle = "_ZVING_TREE_STYLE";

	/**
	 * 表示属性值是一个DataCollection对象
	 */
	public static final String Data = "_ZVING_DATA";

	/**
	 * 表示属性值是一个URL
	 */
	public static final String URL = "_ZVING_URL";

	/**
	 * 表示一个空字符串。（某些场合下如果直接传空字符串，会被过滤掉，如URL中）
	 */
	public static final String Null = "_ZVING_NULL";

	/**
	 * 文章分页分隔符，用于一段内容由多个部分组成的场合，如CMS中的多页文章
	 */
	public static final String PAGE_BREAK = "<!--_ZVING_PAGE_BREAK_-->";

	/**
	 * 全局字符集设置，在UTF-8版的应用中值为“UTF-8”，在GBK版的应用中值为“GBK”
	 */
	public static String GlobalCharset = "UTF-8";
	/**
	 * 产品信息
	 */
	public static String PRODUCTINFO_ALIAS="_PRODUCTINFO";
	/**
	 * 保险公司
	 */
	public static String COMPANY_ALIAS="_COMPANYINFO";
	/**
	 * 投保录入页初始化信息
	 */
	public static String BUYINIT_ALIAS = "_BUYINIT";
	/**
	 * 详细页信息
	 */
	public static String PRODUCTDETAIL_ALIAS = "_PRODUCTDETAIL";
	/**
	 * 新增b2b_app通道
	 */
	public static final String B2B_APP = "b2b_app";
	public static final String B2B_HTML5 = "b2b_html5";
	/**
	 * b2c财务流水用b2b_lq通道
	 */
	public static final String B2B_LQ = "b2b_lq";
	
	/**
	 * 浙金所渠道
	 */
	public static final String CHANNELSN_ZJFAE = "zjfae";
	
	/**
	 * 快钱渠道
	 */
	public static final String CHANNELSN_BILL_KQ = "bill_kq";
	
	//yuzj
	/**
	 * 新增接入方编号
	 */
	public static final String method = "order_place";
	public static final String appId = "102";
	public static final String channelType = "27";
	public static final String functionCode = "20";
	public static final String deviceId = "feifanApp";
	public static final String version = "1.0";
	public static final String IP = "172.100.101.210";
	public static final String outOrderType = "10";
	public static final String subMerchantName = "网金集团";
	public static final String format = "0";
	public static final String signType = "RSA";
	
	
	/**
	 * 首页导航缓存
	 */
	public static String WAPHOMENAVLIST = "waphomenavlist_wap_ht";
	
	/**
	 * wap站首页广告缓存
	 */
	public static String HOMEIMAGEPLAYER = "homeimageplayer_wap_ht";
	
	public final static int UPDATE_MEMBER_DEAL_COUNT = 2;
	
	/**
	 * wap站首页每日优惠缓存
	 */
	public static String WAPHOMEPROMOTIONPRODUCT = "waphomepromotionproduct_wap_ht";

	/**
	 * 保险评测栏目名称
	 */
	public static String EVALUATING_NAME = "保险评测";
}
