package com.sinosoft.cms.template;

import com.sinosoft.cms.pub.CatalogUtil;
import com.sinosoft.cms.pub.SiteUtil;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Constant;
import com.sinosoft.framework.User;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.Big5Convert;
import com.sinosoft.framework.utility.Errorx;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.LogUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.platform.pub.OrderUtil;
import com.sinosoft.schema.ZCPageBlockSchema;
import com.sinosoft.schema.ZCPageBlockSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TagParser {
	private static final Logger logger = LoggerFactory.getLogger(TagParser.class);
	// 表单
	private static final Pattern cmsForm = Pattern.compile(
			"<cms:form\\s(.*?)>(.*?)</cms:form>", Pattern.CASE_INSENSITIVE
					| Pattern.DOTALL);

	// 列表
	private static final Pattern cmsList = Pattern.compile(
			"<cms:list\\s(.*?)>(.*?)</cms:List>", Pattern.CASE_INSENSITIVE
					| Pattern.DOTALL);

	// 文档     
	private static final Pattern cmsVar = Pattern.compile(
			"<cms:var\\s(.*?)>(.*?)</cms:var>", Pattern.CASE_INSENSITIVE
					| Pattern.DOTALL);
	
	// 文档      
	private static final Pattern cmsSubVar = Pattern.compile(
			"<cms:subvar\\s(.*?)>(.*?)</cms:subvar>", Pattern.CASE_INSENSITIVE
					| Pattern.DOTALL);

	// 子列表
	private static final Pattern cmsSubList = Pattern.compile(
			"<cms:sublist\\s(.*?)>(.*?)</cms:subList>",
			Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	// 图片播放器
	private static final Pattern cmsImagePlayer = Pattern.compile(
			"<cms:imageplayer\\s(.*?)(/>|>(.*?)</cms:ImagePlayer>)",
			Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	// 广告
	private static final Pattern cmsAD = Pattern.compile(
			"<cms:ad\\s(.*?)(/>|>(.*?)</cms:ad>)", Pattern.CASE_INSENSITIVE
					| Pattern.DOTALL);

	// 调查
	private static final Pattern cmsVote = Pattern.compile(
			"<cms:vote\\s(.*?)>(.*?)</cms:vote>", Pattern.CASE_INSENSITIVE
					| Pattern.DOTALL);

	// 留言
	private static final Pattern cmsComment = Pattern.compile(
			"<cms:comment\\s(.*?)((/>)|(>.*?</cms:comment>))",
			Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	// 链接
	private static final Pattern cmsLink = Pattern.compile(
			"<cms:link\\s(.*?)((/>)|(>.*?</cms:link>))",
			Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	// 页面链接，比如当前页面
	private static final Pattern cmsPage = Pattern.compile(
			"<cms:page\\s(.*?)((/>)|(>.*?</cms:page>))",
			Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	// 树
	private static final Pattern cmsTree = Pattern.compile(
			"<cms:tree\\s(.*?)>(.*?)</cms:tree>", Pattern.CASE_INSENSITIVE
					| Pattern.DOTALL);

	// 文件包含
	private static final Pattern cmsInclude = Pattern.compile(
			"<cms:include\\s(.*?)(/>|(>.*?</cms:include>))",
			Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	public static final Pattern pAttr1 = Pattern.compile(
			"\\s*?(\\w+?)\\s*?=\\s*?(\\\"|\\\')(.*?)\\2",
			Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	public static final Pattern pAttr2 = Pattern.compile(
			"\\s*?(\\w+?)\\s*?=\\s*?([^\\\'\\\"\\s]+)",
			Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	private static final Pattern pField = Pattern
			.compile("\\$\\{(\\w+?)\\.(\\w+?)(\\|(.*?))??\\}");

	private static final Pattern pageBar = Pattern.compile("\\$\\{PageBar\\}",
			Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
	private static final Pattern pageBar1 = Pattern.compile("\\$\\{PageBar1\\}",
			Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
	private static final Pattern pageBar2 = Pattern.compile("\\$\\{PageBar2\\}",
			Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
	private static final Pattern pageCarBar = Pattern.compile("\\$\\{pageCarBar\\}",
			Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
	private static final Pattern pageBarNew = Pattern.compile("\\$\\{pageBarNew\\}",
			Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
	private static final Pattern pageBreakBar = Pattern.compile(
			"\\$\\{PageBreakBar\\}", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	private static final Pattern pageBreakBarEx = Pattern.compile(
			"\\$\\{PageBreakBarEx\\}", Pattern.CASE_INSENSITIVE
					| Pattern.DOTALL);

	private static final Pattern levelPattern = Pattern.compile(
			"\\$\\{Level\\}", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	// 匹配形如 href=../images/test.gif
	// 考虑等号前后有空格的情况
	public static final Pattern resourcePattern1 = Pattern
			.compile(
					"\\s(src|href|background|file|virtual|action|name=\"movie\" value)\\s*?=\\s*?([^\\\"\\\'\\s]+?)(\\s|>|/>)",
					Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	// 匹配形如 href="../images/test.gif" 或者 href="../images/test.gif"
	public static final Pattern resourcePattern2 = Pattern
			.compile(
					"\\s(src|href|background|file|virtual|action|name=\"movie\" value)\\s*?=\\s*?(\\\"|\\\')(.*?)\\2",
					Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	// 匹配css中的 url(images/test.gif)
	public static final Pattern resourcePatternCss = Pattern.compile(
			"(:\\s*?url)\\s*?\\(\\s*?([^\\\"\\\'\\s]+)(\\))",
			Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	// 匹配css中的 url('images/test.gif')或url("images/test.gif")
	public static final Pattern resourcePatternCss2 = Pattern.compile(
			"(:\\s*?url)\\s*?\\(\\s*?(\\\"|\\\')(.*?)\\2(\\))",
			Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
	
	/**
	 * @author zhangjinquan 11180
	 * @creadate 2012-12-26
	 * @description 增加全局数据搜索匹配模式
	 */
	private static final Pattern pGlobalField = Pattern.compile("\\$[gG]\\{(\\w+?)\\.(\\w+?)\\}");

	private String prefix = "%";

	private String templateFileName;

	private String content;

	private String staticDir;

	private String templateDir;

	private long siteID;

	/**
	 * 默认-发布.
	 */
	private final String ssi_current = "current";
	/**
	 * 表示存文件关联.
	 */
	private final String ssi_virtual = "<!--#include virtual=\"${path}\"-->";
	/**
	 * 表示动态文件关联.
	 */
	private final String ssi_file = "<!--#include file=\"${path}\"-->";
	/**
	 * 目录层级
	 */
	private int dirLevel;

	/**
	 * 是否是页面片段
	 */
	private boolean isPageBlock;

	/**
	 * 预览标记
	 */
	private boolean isPreview;

	private Errorx error = new Errorx();

	private Transaction trans = new Transaction();

	private ZCPageBlockSet blockSet = new ZCPageBlockSet();

	private Mapx pageListPrams = new Mapx();

	private ArrayList fileList = new ArrayList();
	/**
	 * @author zhangjinquan 11180
	 * @createdate 2012-12-19
	 * @description 插到最前面的变量定义赋值等的字符串
	 */
	private StringBuffer headStrBuf = new StringBuffer("");

	public TagParser() {
		String contextRealPath = Config.getContextRealPath();
		templateDir = contextRealPath
				+ Config.getValue("Statical.TemplateDir").replace('\\', '/');
		staticDir = contextRealPath
				+ Config.getValue("Statical.TargetDir").replace('\\', '/');
		staticDir = staticDir.replaceAll("/+", "/");
	}

	/**
	 * 图片路径
	 */
	private void processPath() {
		String levelString = getLevelStr();
		content = dealResource(content, levelString);
	}

	/**
	 * 处理图片、包含文件、引用css等文件的路径
	 * 
	 * @param content
	 * @return
	 */
	public String dealResource(String content, String levelStr) {
		if (content == null) {
			return "";
		}
		Matcher m = resourcePattern1.matcher(content);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;

		while (m.find(lastEndIndex)) {
			String dealPath = processText(m.group(1), m.group(2), levelStr);
			sb.append(content.substring(lastEndIndex, m.start()));
			sb.append(" " + m.group(1) + "=" + dealPath + m.group(3));
			lastEndIndex = m.end();
		}
		sb.append(content.substring(lastEndIndex));

		content = sb.toString();
		sb = new StringBuffer();
		m = resourcePattern2.matcher(content);
		lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			String dealPath = processText(m.group(1), m.group(3), levelStr);
			sb.append(content.substring(lastEndIndex, m.start()));
			String separator = m.group(2);
			sb.append(" " + m.group(1) + "=" + separator + dealPath + separator);
			lastEndIndex = m.end();
		}
		sb.append(content.substring(lastEndIndex));

		content = sb.toString();
		sb = new StringBuffer();
		m = resourcePatternCss.matcher(content);
		lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			String dealPath = processText(m.group(1), m.group(2), levelStr);
			sb.append(content.substring(lastEndIndex, m.start()));
			sb.append(m.group(1) + "(" + dealPath + ")");
			lastEndIndex = m.end();
		}
		sb.append(content.substring(lastEndIndex));

		content = sb.toString();
		sb = new StringBuffer();
		m = resourcePatternCss2.matcher(content);
		lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			String dealPath = processText(m.group(1), m.group(3), levelStr);
			sb.append(content.substring(lastEndIndex, m.start()));
			sb.append(m.group(1) + "(" + dealPath + ")");
			lastEndIndex = m.end();
		}
		sb.append(content.substring(lastEndIndex));

		// 当文件是包含文件的源文件时，不解析${level},当生成形如header_0.html时解析${level}
		if (!isPageBlock) {
			content = sb.toString();
			sb = new StringBuffer();
			lastEndIndex = 0;
			m = levelPattern.matcher(content);
			while (m.find(lastEndIndex)) {
				sb.append(content.substring(lastEndIndex, m.start()));
				sb.append(levelStr);
				lastEndIndex = m.end();
			}
			sb.append(content.substring(lastEndIndex));
		}

		return sb.toString();
	}

	/**
	 * 解析各类文件的相对路径
	 * 
	 * @param srcType
	 *            文件类型
	 * @param resourcePath
	 *            源文件路径
	 * @param levelStr
	 *            层级
	 * @return
	 */
	private String processText(String srcType, String resourcePath,
			String levelStr) {
		String strPath = "";
		resourcePath = resourcePath.trim();

		if (resourcePath.startsWith("#")) {
			strPath = resourcePath;
		} else if (resourcePath.toLowerCase().startsWith("/")
				|| resourcePath.toLowerCase().indexOf("${") != -1
				|| resourcePath.toLowerCase().indexOf("<" + prefix + "") != -1
				|| resourcePath.toLowerCase().indexOf("+") != -1
				|| resourcePath.toLowerCase().startsWith("<cms:")
				|| resourcePath.toLowerCase().startsWith("http")
				|| resourcePath.toLowerCase().startsWith("https")
				|| resourcePath.toLowerCase().startsWith("ftp")
				|| resourcePath.toLowerCase().startsWith("javascript")
				|| resourcePath.toLowerCase().startsWith("mailto")) {
			strPath = resourcePath;
		} else {
			int upLevelIndex = resourcePath.lastIndexOf("../");
			if (upLevelIndex != -1) {
				resourcePath = resourcePath.substring(upLevelIndex + 3);
			}
			if (resourcePath.indexOf("//") == -1) {
				// 文件名
				String fileName = resourcePath.indexOf("/") == -1 ? resourcePath
						: resourcePath
								.substring(resourcePath.lastIndexOf("/") + 1);

				if (resourcePath.length() - 1 == resourcePath.lastIndexOf("/")) {
					fileName = resourcePath;
				}
				// 扩展名
				String ext = resourcePath.indexOf(".") == -1 ? ""
						: resourcePath
								.substring(resourcePath.lastIndexOf(".") + 1);
				ext = ext.toLowerCase().trim();

				if ("file".equalsIgnoreCase(srcType)
						|| "virtual".equalsIgnoreCase(srcType)) {
					strPath = levelStr + resourcePath;
				} else if ("url".equalsIgnoreCase(srcType)
						&& "#default#homepage".equalsIgnoreCase(fileName)) {
					strPath = resourcePath;
				} else if ("".equalsIgnoreCase(ext)) {
					strPath = levelStr + resourcePath;
				} else if ("window.external.AddFavorite(location.href,document.title);"
						.equals(fileName)) {
					strPath = resourcePath;
				} else {
					strPath = levelStr + resourcePath;
				}
			} else if (resourcePath.indexOf("//") > 0
					&& resourcePath.indexOf("#default#homepage") != -1) {
				strPath = resourcePath;
			}
		}
		// LogUtil.getLogger().info("原路径："+resourcePath+",处理后路径："+strPath);
		return strPath;
	}

	private void addHeader() {
		content = "<z:config language=\"java\" prefix=\"" + prefix + "\"/>"
				+ content;
	}

	/**
	 * @author zhangjinquan 11180
	 * @description 配置列表数据到顶部定义
	 * @createdate 2012-12-21
	 * @param map 模板中cms:list中定义的属性数据
	 * @param dtStr 前导列表获取方式jsp字符串
	 * @param varIndex 当前解析列表在模板中所处的第几个列表序号
	 * @param jspContent 已经拼接的jsp字符串
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String setListGlobalData(Mapx map, String dtStr, int varIndex, String jspContent)
	{
		//仅当配置为非保存全局变量时才不配置到顶部
		if (!"true".equalsIgnoreCase(map.getString("globaldata")))
		{
			return (jspContent + dtStr);
		}
		String listId = map.getString("dataid");
		String defaultId = "list_" + varIndex;
		if (null != listId)
		{
			listId = listId.toLowerCase();
		}
		else
		{
			listId = defaultId;
		}
		dtStr += " if (dt_" + varIndex + "!=null) { String id=\"" + listId
			+ "\";\n int rdCnt=0;int pgCnt=0; if (TemplateData.getDetailAsList(\"" + defaultId
			+ "\")) { rdCnt=TemplateData.getTotal(); pgCnt = TemplateData.getPageCount(); } else { rdCnt=dt_" + varIndex
			+ ".getRowCount();  int pgSize=" + map.getString("pagesize")
			+ ";\n pgCnt=(pgSize>0?(rdCnt/pgSize+(rdCnt%pgSize==0?0:1)):0);\n } \n TemplateData.addGlobalData(id,\"recordcount\", rdCnt);\n TemplateData.addGlobalData(id,\"pagecount\", pgCnt); } \n ";
		headStrBuf.append(" <").append(prefix).append(dtStr).append(prefix).append("> ");
		return jspContent;
	}
	/**
	 * 处理详细页的列表，处理方式：一个静态文件中通过style的display的属性值配置分页的隐藏与显示
	 * @author zhangjinquan 11180
	 * @createdate 2012-12-18
	 * @param Mapx map
	 * @param String listContent
	 * @param String pageIndexStr
	 * @param int varIndex
	 * @return
	 */
	private String dealDetailPage(Mapx map, String listContent, int varIndex)
	{
		StringBuffer sb = new StringBuffer();
		String pageSizeStr = map.getString("pagesize");
		String pageIdStr = map.getString("pageid");
		String pageClassStr = map.getString("pageclass");
		//如果配置了详细页列表属性，则处理详细页列表
		if (("true".equalsIgnoreCase(map.getString("detailpage")))
			&& (null != pageSizeStr) && pageSizeStr.matches("^[1-9]\\d*$")
			&& (null != pageIdStr) && (null != pageClassStr))
		{
			//是否显示列表内容的属性标记showcontent
			String showContent = map.getString("showcontent");
			if (!"false".equalsIgnoreCase(showContent))
			{
				sb.append("int pgSize=").append(pageSizeStr).append(";\n ");
				sb.append("if (rdIdx%pgSize==0) { if (rdIdx>0) { write(\"</div>\"); } write(\"<div id='");
				sb.append(pageIdStr).append("\"+(rdIdx/pgSize)+\"' class='").append(pageClassStr);
				sb.append("'\"); if (rdIdx>0) { write(\" style='display:none;'\"); }\n");
				sb.append("write(\">\"); } \n").append(prefix).append(">\n").append(listContent);
				sb.append("<").append(prefix).append("\n if(rdIdx==(rdCnt-1)) { write(\"</div>\"); } }\n");
				sb.append(prefix).append(">");

				//是否显示分页条的属性标记showpagebar
				String showPageBar = map.getString("showpagebar");
				if (!"false".equalsIgnoreCase(showPageBar))
				{
					sb.append("<input type='hidden' id='").append(pageIdStr).append("pgIdx' value='0' /><input type='hidden' id='");
					sb.append(pageIdStr);
					sb.append("pgCnt' value='<").append(prefix).append("=pageCnt").append(prefix).append(">' /><input type='hidden' id='");
					sb.append(pageIdStr);
					sb.append("rdCnt' value='<").append(prefix).append("=rdCnt").append(prefix).append(">' />");
					sb.append("<table width='100%' border='0' class='noBorder' align='center'><tr><td height='18' align='center' valign='middle' style='border-width: 0px;color:#525252'>共<");
					sb.append(prefix);
					sb.append("=rdCnt");
					sb.append(prefix);
					sb.append(">条记录，每页").append(pageSizeStr).append("条，当前第<span id=\"").append(pageIdStr).append("dqdjy\" class='fc_ch1'><");
					sb.append(prefix).append("=((pageCnt==0)?0:1)").append(prefix).append("></span>/<span class='fc_ch1'><");
					sb.append(prefix).append("=pageCnt");
					sb.append(prefix).append("></span>页&nbsp;&nbsp;&nbsp;&nbsp;");
					sb.append("<span id=\"").append(pageIdStr).append("dyy\" class='fc_hui2' onclick='getPage(\"").append(pageIdStr).append("\", this);'>第一页</span>|");
					sb.append("<span id=\"").append(pageIdStr).append("syy\" class='fc_hui2' onclick='getPage(\"").append(pageIdStr).append("\", this);'>上一页</span>|");
					sb.append("<span id=\"").append(pageIdStr).append("xyy\" class='<").append(prefix).append("=(pageCnt>1?\"fc_ch1\":\"fc_hui2\")").append(prefix);
					sb.append(">' onclick='getPage(\"").append(pageIdStr).append("\", this);' style='color:<").append(prefix);
					sb.append("=(pageCnt>1?\"#000000;cursor:pointer;\":\"#525252\")").append(prefix);
					sb.append(">'>下一页</span>|");
					sb.append("<span id=\"").append(pageIdStr).append("zmy\" class='<").append(prefix).append("=(pageCnt>1?\"fc_ch1\":\"fc_hui2\")").append(prefix);
					sb.append(">' onclick='getPage(\"").append(pageIdStr).append("\", this);' style='color:<").append(prefix);
					sb.append("=(pageCnt>1?\"#000000;cursor:pointer;\":\"#525252\")").append(prefix);
					sb.append(">'>最末页</span>");
					sb.append("&nbsp;&nbsp;转到第<input id='").append(pageIdStr).append("zddjy' type='text' size='4' style='width:30px' ");
					sb.append("onKeyUp=\"value=value.replace(/\\D/g,'')\">页&nbsp;&nbsp;<input type='button' value='跳转' onclick='gotoPage(\"").append(pageIdStr).append("\");'></td></tr></table>\n");
				}
				
				sb.append("<");
				sb.append(prefix);
				sb.append(" } ");
				sb.append(prefix);
				sb.append(">");
			}
			else
			{
				return ("\n }} " + prefix + ">");
			}
		}
		else
		{
			return (prefix + ">\n" + listContent + "<" + prefix + "}}" + prefix + ">");
		}
		return sb.toString();
	}
	
	/**
	 * 解析形如<cms:list item="aritlce" count="10"></cms:list>
	 * 
	 */
	@SuppressWarnings("unchecked")
	private void parseList() {
		Matcher m = cmsList.matcher(content);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;
		int varIndex = 0;
		while (m.find(lastEndIndex)) {
			sb.append(content.substring(lastEndIndex, m.start()));
			lastEndIndex = m.end();
			Mapx map = getAttrMap(m.group(1));
			String listContent = m.group(2);
	
			String item = (String) map.get("item");
			if (StringUtil.isEmpty(item)) {
				sb.append("模板错误：cms:list未设置item属性");
				varIndex++;
				continue;
			} else {
				item = item.toLowerCase();
			}
			String listId = "list_" + varIndex;
			map.put("listID", listId);
			
			if ("true".equalsIgnoreCase("customtableflag"))
			{
				this.headStrBuf.append("<").append(prefix);
				this.headStrBuf.append(" TemplateData.addGlobalData(\"").append(listId).append("\",\"").append(listId).append("\",\"true\"); ");
				this.headStrBuf.append(prefix).append(">");
			}
			
			sb.append("<").append(prefix).append(" TemplateData.addGlobalData(\"nowListId\",\"nowListId\",\"");
			sb.append(listId).append("\"); ").append(prefix).append(">");
	
			String type = (String) map.get("type");
			String page = (String) map.get("page");
			String countStr = (String) map.get("count");
			String begin = (String) map.get("begin");
			String pagesizeStr = (String) map.get("pagesize");
			String condition = (String) map.get("condition");
			String displayLevel = (String) map.get("level");
			String var = (String) map.get("var");
			String attribute = (String) map.get("hasattribute");
			String tag = (String) map.get("tag");
			String cmsorder = (String) map.get("cmsorder");
			if (StringUtil.isNotEmpty(displayLevel)) {
				displayLevel = displayLevel.toLowerCase();
			} else {
				displayLevel = "current";
			}
			map.put("level", displayLevel);
	
			if (StringUtil.isEmpty(var)) {
				var = item + "_" + varIndex;
				Pattern p = Pattern.compile("(" + item + ")\\.",
						Pattern.CASE_INSENSITIVE);
				Matcher matcher = p.matcher(listContent);
				listContent = matcher.replaceAll(item + "_" + varIndex + ".");
			}
	
			listContent = parseSubList(listContent, System.currentTimeMillis(),
					item, var);
			
			listContent = this.parseSubVar(listContent, System.currentTimeMillis(),
					item, var);
	
			int count = 0;
			if (StringUtil.isNotEmpty(countStr) && StringUtil.isDigit(countStr)) {
				count = Integer.parseInt(countStr);
			} else {
				count = 50;
			}
			map.put("count", count + "");
	
			if (StringUtil.isEmpty(pagesizeStr)
					|| !StringUtil.isDigit(pagesizeStr)) {
				pagesizeStr = "0";
			}
			map.put("pagesize", pagesizeStr);
	
			String beginIndex = null;
			if (StringUtil.isNotEmpty(begin) && StringUtil.isDigit(begin)) {
				beginIndex = begin;
				count += Integer.parseInt(begin);
			} else {
				beginIndex = "0";
			}
	
			if (page != null && "true".equalsIgnoreCase(page)) {
				pageListPrams = map;
				String jspContent = "<" + prefix;
				String dtStr =  " DataTable dt_" + varIndex + " = TemplateData.getListTable();\n ";
				
				//zhangjinquan 11180 增加是否设定全局数据变量标记(总记录数和总页数) 2012-12-21 start
				jspContent = this.setListGlobalData(map, dtStr, varIndex, jspContent);
				//zhangjinquan 11180 增加是否设定全局数据变量标记(总记录数和总页数) 2012-12-21 end
				
				jspContent += "if(null != dt_" + varIndex
						+ ") { for(int i=" + beginIndex + ";i<dt_" + varIndex
						+ ".getRowCount();i++){\n" + "DataRow " + var
						+ "  = dt_" + varIndex + ".getDataRow(i);\n" + ""
						+ prefix + ">\n";
				jspContent += listContent;
				jspContent += "<" + prefix + "} }" + prefix + ">";
				sb.append(jspContent);
			} else {
				if ("article".equalsIgnoreCase(item)
						|| "image".equalsIgnoreCase(item)
						|| "video".equalsIgnoreCase(item)
						|| "audio".equalsIgnoreCase(item)
						|| "attachment".equalsIgnoreCase(item)
						|| "goods".equalsIgnoreCase(item)) { // 文档列表
					String catalog = (String) map.get("name");
					String id = (String) map.get("id");
					if (StringUtil.isNotEmpty(id)) {
						catalog = id;
					}
					String parent = (String) map.get("parent");
	
					// 兼容老式写法
					if (StringUtil.isEmpty(catalog)
							&& StringUtil.isNotEmpty(parent)) {
						catalog = parent;
					}
	
					// 解析Name中的变量表达式
					String catalogStr = null;
					if (StringUtil.isNotEmpty(catalog)
							&& catalog.indexOf("${") != -1) {
						catalogStr = parsePlaceHolderStr(catalog);
					} else {
						catalogStr = "\"" + catalog + "\"";
					}
					// 解析tag中的变量表达式
					String tagStr = null;
					if (StringUtil.isNotEmpty(tag)
							&& tag.indexOf("${") != -1) {
						tagStr = parsePlaceHolderStr(tag);
					} else {
						tagStr = "\"" + tag + "\"";
					}
	
					String conditionStr = null;
					if (StringUtil.isNotEmpty(condition)
							&& ((condition.indexOf("${") != -1)
								|| (condition.toLowerCase().indexOf("$g{") != -1))) {
						conditionStr = parsePlaceHolderStr(condition);
					} else {
						conditionStr = "\"" + condition + "\"";
					}
					//"<!--循环：" + item + " count：" + count + "-->\n" + 
					String jspContent = "<" + prefix;
					
					String dtStr = " DataTable dt_" + varIndex
								+ " = TemplateData.getDocList(\"" + item
								+ "\"," + catalogStr + ",\"" + displayLevel
								+ "\",\"" + attribute + "\",\"" + type + "\",\""
								+ count + "\"," + conditionStr +","+ tagStr + ",\"" + cmsorder + "\");\n ";
					
					//zhangjinquan 11180 增加是否设定全局数据变量标记(总记录数和总页数) 2012-12-21 start
					jspContent = this.setListGlobalData(map, dtStr, varIndex, jspContent);
					//zhangjinquan 11180 增加是否设定全局数据变量标记(总记录数和总页数) 2012-12-21 end
					
					jspContent += " if(dt_" + varIndex + "==null){write(\"没有找到栏目。parent："
								+ map.get("parent") + "  catalog：\"+"
								+ catalogStr + ");}else{\n int rdCnt=dt_"
								+ varIndex + ".getRowCount();\n int pageCnt="
								+ (Integer.parseInt(pagesizeStr)>0 ? "(rdCnt/" + pagesizeStr
								+ " + (rdCnt%" + pagesizeStr + "==0 ? 0 : 1));" : "0;")
								+ "\n for(int i=" + beginIndex + ";i<rdCnt;i++){\n"
								+ "DataRow " + var + "  = dt_" + varIndex
								+ ".getDataRow(i);\n int rdIdx=i;\n";
					
					//zhangjinquan 11180 增加一个文件内分页处理 2012-12-19 start
					jspContent += this.dealDetailPage(map, listContent, varIndex);
					//zhangjinquan 11180 增加一个文件内分页处理 2012-12-19 end
	
					// 生成页面片段
					if (!isPageBlock && !isPreview) {
						if (catalog == null || "relate".equalsIgnoreCase(type)
								|| (catalog.indexOf("${") > -1) ) { // 没有指定栏目
							// 或者
							// 为相关文档
							sb.append(jspContent);
						} else {
							sb.append(getListBlock(item, count, type, catalog,
									parent, jspContent));
						}
					} else {
						sb.append(jspContent);
					}
	
				} else if ("friendlink".equalsIgnoreCase(item)) { // 友情链接
					// group为链接分类
					String group = map.getString("group");
					if (group == null) {
						group = map.getString("name");
					}
	
					if (group != null) {
						group = group.toLowerCase();
						group = parsePlaceHolderStr(group);
					}
					else
					{
						group = "\"" + group + "\"";
					}
	
					String jspContent = "<!--循环：" + item + " count：" + count
							+ "-->\n" + "<" + prefix + " DataTable dt_"
							+ varIndex + " = TemplateData.getFriendLinkList(\""
							+ item + "\"," + group + ",\"" + count
							+ "\",\"" + condition + "\",\"" + cmsorder + "\");\n" + "if(dt_"
							+ varIndex + "==null){write(\"没有找到友情链接：\"+"
							+ group + ");}else{\n"
							+ "for(int i=" + beginIndex + ";i<dt_" + varIndex
							+ ".getRowCount();i++){\n" + "DataRow " + var
							+ "  = dt_" + varIndex + ".getDataRow(i);\n" + ""
							+ prefix + ">\n";
	
					jspContent += listContent;
					jspContent += "<" + prefix + "}}" + prefix + ">";
	
					if (!isPageBlock && this.dirLevel == 0 && !isPreview) {
						String targetFileName = getLinkBlock(item, group,
								count, jspContent);
						String ext = SiteUtil.getExtensionType(siteID);
						if ("jsp".equalsIgnoreCase(ext)) {
							sb.append("<%@include file=\"" + getLevelStr()
									+ targetFileName + "\"%>");
						}else if ("shtml".equalsIgnoreCase(ext)
								|| "html".equalsIgnoreCase(ext)) {
							sb.append( jspContent);
						}else {
							sb.append("<!--#include virtual=\"" + getLevelStr()
									+ targetFileName + "\"-->");
						}
					} else {
						sb.append(jspContent);
					}
				} else if("hotopic".equalsIgnoreCase(item)){
					//热点专题
					String group = map.getString("group");
					if (group == null) {
						group = map.getString("name");
					}
	
					if (group != null) {
						group = group.toLowerCase();
						group = parsePlaceHolderStr(group);
					}
					else
					{
						group = "\"" + group + "\"";
					}
					
					
					String jspContent = "<!--循环：" + item + " count：" + count
										+ "-->\n" + "<" + prefix + " DataTable dt_"
										+ varIndex + " = TemplateData.getHoTopicList(\""
										+ item + "\"," + group + ",\"" + count+ "\",\"" + condition + "\",\"" + cmsorder + "\");\n" + "if(dt_"
										+ varIndex + "==null){write(\"没有找到热点专题：\"+"
										+ group + ");}else{\n"
										+ "for(int i=" + beginIndex + ";i<dt_" + varIndex
										+ ".getRowCount();i++){\n" + "DataRow " + var
										+ "  = dt_" + varIndex + ".getDataRow(i);\n" + ""
										+ prefix + ">\n";
					jspContent += listContent;
					jspContent += "<" + prefix + "}}" + prefix + ">";
					sb.append(jspContent);
				}else if ("block".equalsIgnoreCase(item)) { // block页面区块
					String jspContent = "<!--循环：" + item + " count：" + count
							+ "-->\n" + "<" + prefix + " DataTable dt_"
							+ varIndex + " = TemplateData.getBlockList(\""
							+ item + "\",\"" + count + "\",\"" + condition
							+ "\",\"" + cmsorder + "\");\n" + "for(int i=" + beginIndex + ";i<dt_"
							+ varIndex + ".getRowCount();i++){" + "DataRow "
							+ var + "  = dt_" + varIndex + ".getDataRow(i);"
							+ "" + prefix + ">";
	
					jspContent += listContent;
					jspContent += "<" + prefix + "}" + prefix + ">";
					sb.append(jspContent);
				} else if ("catalog".equalsIgnoreCase(item)) {
					String parent = (String) map.get("parent");
					String parentID = (String) map.get("id");
					String catalog = (String) map.get("name");
					if (StringUtil.isNotEmpty(parentID)) {
						parent = parentID;
					}
	
					// 兼容老式写法
					if (StringUtil.isEmpty(catalog)
							&& StringUtil.isNotEmpty(parent)) {
						catalog = parent;
					}
	
					// 解析变量表达式
					String catalogStr = null;
					if (StringUtil.isNotEmpty(catalog)
							&& catalog.indexOf("${") != -1) {
						catalogStr = parsePlaceHolder(catalog);
					} else {
						catalogStr = "\"" + catalog + "\"";
					}
	
					if (StringUtil.isEmpty(displayLevel)) {
						displayLevel = "\"child\"";
					} else {
						displayLevel = "\"" + displayLevel + "\"";
					}
	
					String jspContent = "<!--循环：" + item + " count：" + count
							+ "-->\n" + "<" + prefix + " DataTable dt_"
							+ varIndex + " = TemplateData.getCatalogList(\""
							+ item + "\",\"" + type + "\"," + catalogStr + ","
							+ displayLevel + ",\"" + count + "\",\""
							+ condition + "\",\"" + cmsorder + "\");\n" + "if(dt_" + varIndex
							+ "==null){write(\"没有找到栏目列表。\");}else{\n"
							+ "for(int i=" + beginIndex + ";i<dt_" + varIndex
							+ ".getRowCount();i++){" + "DataRow " + var
							+ "  = dt_" + varIndex + ".getDataRow(i);\n" + ""
							+ prefix + ">";
					jspContent += listContent;
					jspContent += "<" + prefix + "}}" + prefix + ">";
	
					sb.append(jspContent);
				} else if ("customtable".equalsIgnoreCase(item)) {
					String tableName = (String) map.get("name");
					String jspContent = "<!--循环：" + item + " count：" + count
							+ "-->\n" + "<" + prefix + " DataTable dt_"
							+ varIndex + " = TemplateData.getCustomData(\""
							+ tableName + "\",\"" + count + "\",\"" + condition
							+ "\",\"" + cmsorder + "\");\n" + "if(dt_" + varIndex
							+ "==null){write(\"没有找到自定义数据" + tableName
							+ "。\");}else{\n" + "for(int i=" + beginIndex
							+ ";i<dt_" + varIndex + ".getRowCount();i++){"
							+ "DataRow " + var + "  = dt_" + varIndex
							+ ".getDataRow(i);\n" + "" + prefix + ">";
					jspContent += listContent;
					jspContent += "<" + prefix + "}}" + prefix + ">";
	
					sb.append(jspContent);
				} else if ("keyword".equalsIgnoreCase(item)) {
					String jspContent = "<!--循环：" + item + " count：" + count
							+ "-->\n" + "<" + prefix + " DataTable dt_"
							+ varIndex + " = TemplateData.getKeywords(\""
							+ type + "\",\"" + count + "\"" + ",\"" + cmsorder + "\");\n" + "if(dt_"
							+ varIndex + "==null){write(\"" + "\");}else{\n"
							+ "for(int i=" + beginIndex + ";i<dt_" + varIndex
							+ ".getRowCount();i++){" + "DataRow " + var
							+ "  = dt_" + varIndex + ".getDataRow(i);\n" + ""
							+ prefix + ">";
					jspContent += listContent;
					jspContent += "<" + prefix + "}}" + prefix + ">";
	
					sb.append(jspContent);
				} else if ("magazine".equalsIgnoreCase(item)) {
					String name = (String) map.get("name");
					if (StringUtil.isEmpty(displayLevel)) {
						displayLevel = "\"child\"";
					} else {
						displayLevel = "\"" + displayLevel + "\"";
					}
	
					String jspContent = "<!--循环：" + item + " count：" + count
							+ "-->\n" + "<" + prefix + " DataTable dt_"
							+ varIndex + " = TemplateData.getMagazineList(\""
							+ item + "\",\"" + name + "\",\"" + count + "\",\""
							+ condition + "\",\"" + cmsorder + "\");\n" + "if(dt_" + varIndex
							+ "==null){write(\"没有找到期刊列表。\");}else{\n"
							+ "for(int i=" + beginIndex + ";i<dt_" + varIndex
							+ ".getRowCount();i++){" + "DataRow " + var
							+ "  = dt_" + varIndex + ".getDataRow(i);\n" + ""
							+ prefix + ">";
					jspContent += listContent;
					jspContent += "<" + prefix + "}}" + prefix + ">";
	
					sb.append(jspContent);
				} else if ("magazineissue".equalsIgnoreCase(item)) {
					String name = (String) map.get("name");
					if (StringUtil.isEmpty(displayLevel)) {
						displayLevel = "\"child\"";
					} else {
						displayLevel = "\"" + displayLevel + "\"";
					}
	
					String jspContent = "<!--循环：" + item + " count：" + count
							+ "-->\n" + "<" + prefix + " DataTable dt_"
							+ varIndex
							+ " = TemplateData.getMagazineIssueList(\"" + item
							+ "\",\"" + name + "\",\"" + type + "\",\"" + count
							+ "\",\"" + condition + "\",\"" + cmsorder + "\");\n" + "if(dt_"
							+ varIndex
							+ "==null){write(\"没有找到期刊期数列表。\");}else{\n"
							+ "for(int i=" + beginIndex + ";i<dt_" + varIndex
							+ ".getRowCount();i++){" + "DataRow " + var
							+ "  = dt_" + varIndex + ".getDataRow(i);\n" + ""
							+ prefix + ">";
					jspContent += listContent;
					jspContent += "<" + prefix + "}}" + prefix + ">";
	
					sb.append(jspContent);
				} else if ("page".equalsIgnoreCase(item)) {
					String jspContent = "<!--循环："
							+ item
							+ " count："
							+ count
							+ "-->\n"
							+ "<"
							+ prefix
							+ " DataTable dt_"
							+ varIndex
							+ " = TemplateData.getArticlePages();\n"
							+ "if(dt_"
							+ varIndex
							+ "==null){write(\"<cms:list item='page\'>本标签只能在文章详细页中使用。"
							+ "。\");}else{\n int count = dt_" + varIndex + ".getRowCount();\n"
							+ "for(int i=" + beginIndex
							+ ";i<dt_" + varIndex + ".getRowCount();i++){"
							+ "DataRow " + var + "  = dt_" + varIndex
							+ ".getDataRow(i);\n" + "" + prefix + ">";
					jspContent += listContent;
					jspContent += "<" + prefix + "}}" + prefix + ">";
	
					sb.append(jspContent);
					//详细页 type tag count
				}else if ("hotlabels".equalsIgnoreCase(item)) {
					
					// 解析tag中的变量表达式
					String tagStr = null;
					if (StringUtil.isNotEmpty(tag)
							&& tag.indexOf("${") != -1) {
						tagStr = parsePlaceHolderStr(tag);
					} else {
						tagStr = "\"" + tag + "\"";
					}
					
					String jspContent = "<!--循环：" + item + " count：" + count
					+ "-->\n" + "<" + prefix + " DataTable dt_"
					+ varIndex + " = TemplateData.getHotLabels(\""
					+ type + "\","+tagStr+"," + count +",\"" + cmsorder + "\");\n" + "if(dt_"
					+ varIndex + "==null){write(\"" + "\");}else{\n"
					+ "for(int i=" + beginIndex + ";i < dt_" + varIndex
					+ ".getRowCount();i++){" + "DataRow " + var
					+ "  = dt_" + varIndex + ".getDataRow(i);\n" + ""
					+ prefix + ">";
					
					jspContent += listContent;
					jspContent += "<" + prefix + "}}" + prefix + ">";

					sb.append(jspContent);
				}else if ("attention".equalsIgnoreCase(item)) {

					// 解析tag中的变量表达式
					String tagStr = null;
					if (StringUtil.isNotEmpty(tag)
							&& tag.indexOf("${") != -1) {
						tagStr = parsePlaceHolderStr(tag);
					} else {
						tagStr = "\"" + tag + "\"";
					}

					String jspContent = "<!--循环：" + item + " count：" + count
					+ "-->\n" + "<" + prefix + " DataTable dt_"
					+ varIndex + " = TemplateData.getAttentionArticle(\""
					+ type + "\","+tagStr+"," + count +",\"" + cmsorder + "\");\n" + "if(dt_"
					+ varIndex + "==null){write(\"" + "\");}else{\n"
					+ "for(int i=" + beginIndex + ";i < dt_" + varIndex
					+ ".getRowCount();i++){" + "DataRow " + var
					+ "  = dt_" + varIndex + ".getDataRow(i);\n" + ""
					+ prefix + ">";

					jspContent += listContent;
					jspContent += "<" + prefix + "}}" + prefix + ">";

					sb.append(jspContent);
				} else if ("relazx".equalsIgnoreCase(item)) {
					// 解析tag中的变量表达式
					String tagStr = null;
					if (StringUtil.isNotEmpty(tag)
							&& tag.indexOf("${") != -1) {
						tagStr = parsePlaceHolderStr(tag);
					} else {
						tagStr = "\"" + tag + "\"";
					}

					String jspContent = "<!--循环：" + item + " count：" + count
					+ "-->\n" + "<" + prefix + " DataTable dt_"
					+ varIndex + " = TemplateData.getZxArticle(\""
					+ type + "\","+tagStr+"," + count +",\"" + cmsorder + "\");\n" + "if(dt_"
					+ varIndex + "==null){write(\"" + "\");}else{\n"
					+ "for(int i=" + beginIndex + ";i < dt_" + varIndex
					+ ".getRowCount();i++){" + "DataRow " + var
					+ "  = dt_" + varIndex + ".getDataRow(i);\n" + ""
					+ prefix + ">";

					jspContent += listContent;
					jspContent += "<" + prefix + "}}" + prefix + ">";

					sb.append(jspContent);
				} else if ("ReportCompany".equalsIgnoreCase(item)) {
					String jspContent = "<!--循环：" + item + " count：" + count
							+ "-->\n" + "<" + prefix + " DataTable dt_"
							+ varIndex + " = TemplateData.getReportCompany();\n"
							+ "for(int i=" + beginIndex + ";i<dt_"
							+ varIndex + ".getRowCount();i++){" + "DataRow "
							+ var + "  = dt_" + varIndex + ".getDataRow(i);"
							+ "" + prefix + ">";

					jspContent += listContent;
					jspContent += "<" + prefix + "}" + prefix + ">";
					sb.append(jspContent);
				} else if ("zdcode".equalsIgnoreCase(item)) {
					String jspContent = "<!--循环：" + item + " count：" + count
							+ "-->\n" + "<" + prefix + " DataTable dt_"
							+ varIndex + " = TemplateData.getZdcodeInfo(\""+type +"\");\n"
							+ "for(int i=" + beginIndex + ";i<dt_"
							+ varIndex + ".getRowCount();i++){" + "DataRow "
							+ var + "  = dt_" + varIndex + ".getDataRow(i);"
							+ "" + prefix + ">";

					jspContent += listContent;
					jspContent += "<" + prefix + "}" + prefix + ">";
					sb.append(jspContent);
				}
			}
			varIndex++;
		}
		sb.append(content.substring(lastEndIndex));
		content = sb.toString();
	
		if ("true".equalsIgnoreCase(pageListPrams.getString("page"))) {
			parsePageBar();
			parsePageBar1();
			parsePageBar2();
			parsePageCarBar();
			parsePageBarNew();
		}
	}
	
	
	private String getLinkBlock(String item, String group, int count,
			String jspContent) {
		String catalogID = "0";

		String cacheDir = templateDir + "/" + SiteUtil.getAlias(siteID);
		cacheDir = cacheDir.replace('\\', '/').replaceAll("//", "/");

		File cacheFileDir = new File(cacheDir + "/cache/template");
		if (!cacheFileDir.exists()) {
			cacheFileDir.mkdirs();
		}

		String groupAlias = StringUtil.getChineseFirstAlpha(group);
		String code = catalogID + "_" + item + "_" + count + "_" + groupAlias.replaceAll("[\"\\\\/:\\*\\?<>\\|]", "")
				+ "_" + dirLevel;
		String cacheFileName = "/cache/template/block_" + code + ".html";
		String targetFileName = "cache/page/block_" + code + ".html";

		FileUtil.writeText(cacheDir + cacheFileName, jspContent);

		ZCPageBlockSchema block = new ZCPageBlockSchema();
		long blockID = NoUtil.getMaxID("PageBlockID");
		block.setID(blockID);
		block.setSiteID(siteID);
		// block.setCatalogID(catalogID);
		block.setName(code);
		block.setCode(code);
		block.setFileName(targetFileName);
		block.setTemplate(cacheFileName);
		block.setSortField("PublishDate");

		block.setType(4);
		block.setContent("");
		block.setAddTime(new Date());
		if (User.getCurrent() != null) {
			block.setAddUser(User.getUserName());
		} else {
			block.setAddUser("admin");
		}

		blockSet.add(block);
		return targetFileName;
	}

	private String getListBlock(String item, int count, String type,
			String catalog, String parent, String jspContent) {
		String pageString = null;
		String key = null;
		String catalogID = null;
		if (StringUtil.isNotEmpty(catalog)) {
			if (StringUtil.isDigit(catalog)) {
				catalogID = catalog;
			} else {
				if (StringUtil.isNotEmpty(parent)) {
					catalogID = CatalogUtil
							.getIDByName(siteID, parent, catalog);
				} else {
					try {
						if (catalog.indexOf(",") != -1) {
							catalogID = CatalogUtil.getIDsByName(siteID + "",
									catalog);
						} else if (catalog.indexOf("/") != -1) {
							catalogID = CatalogUtil.getIDByNames(siteID + "",
									catalog);
						} else {
							catalogID = CatalogUtil
									.getIDByName(siteID, catalog);
						}
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}
			}
		}

		if (catalogID != null) {
			String cacheDir = templateDir + "/" + SiteUtil.getAlias(siteID);
			cacheDir = cacheDir.replace('\\', '/').replaceAll("//", "/");

			File cacheFileDir = new File(cacheDir + "/cache/template");
			if (!cacheFileDir.exists()) {
				cacheFileDir.mkdirs();
			}
			if (StringUtil.isEmpty(type) || "null".equals(type)) {
				type = "default";
			}
			key = siteID + "_" + catalogID;
			String hex = StringUtil.md5Hex(jspContent).substring(0, 8);
			String code = catalogID + "_" + item + "_" + type + "_" + count
					+ "_" + hex + "_" + dirLevel;
			String cacheFileName = "/cache/template/block_" + code + ".html";
			String targetFileName = "cache/page/block_" + code + ".html";

			FileUtil.writeText(cacheDir + cacheFileName, jspContent);

			if (catalogID.indexOf(",") != -1) {
				String[] catalogIDs = catalogID.split(",");
				for (int m = 0; m < catalogIDs.length; m++) {
					ZCPageBlockSchema block = new ZCPageBlockSchema();
					long blockID = NoUtil.getMaxID("PageBlockID");
					block.setID(blockID);
					block.setSiteID(siteID);
					block.setCatalogID(catalogIDs[m]);
					block.setName(key);
					block.setCode(code);
					block.setFileName(targetFileName);
					block.setTemplate(cacheFileName);
					block.setSortField("PublishDate");

					block.setType(4);
					block.setContent("");
					block.setAddTime(new Date());
					if (User.getCurrent() != null) {
						block.setAddUser(User.getUserName());
					} else {
						block.setAddUser("admin");
					}
					blockSet.add(block);
				}
			} else {
				key = siteID + "_" + catalog;
				ZCPageBlockSchema block = new ZCPageBlockSchema();
				long blockID = NoUtil.getMaxID("PageBlockID");
				block.setID(blockID);
				block.setSiteID(siteID);
				block.setCatalogID(catalogID);
				block.setName(key);
				block.setCode(code);
				block.setFileName(targetFileName);
				block.setTemplate(cacheFileName);
				block.setSortField("PublishDate");

				block.setType(4);
				block.setContent("");
				block.setAddTime(new Date());
				if (User.getCurrent() != null) {
					block.setAddUser(User.getUserName());
				} else {
					block.setAddUser("admin");
				}
				blockSet.add(block);
			}
			String ext = SiteUtil.getExtensionType(siteID);
			if ("jsp".equalsIgnoreCase(ext)) {
				pageString = "<%@include file=\"" + getLevelStr()
						+ targetFileName + "\"%>";
			} else if ("shtml".equalsIgnoreCase(ext)
					|| "html".equalsIgnoreCase(ext)) {
				pageString = jspContent;
			}else {
				pageString = "<!--#include virtual=\"" + getLevelStr()
						+ targetFileName + "\"-->";
			}
		} else {
			pageString = "没有找到栏目。 parent：" + parent + "  catalog：" + catalog;
		}
		return pageString;
	}

	// 文章列表分页
	private void parsePageBar() {
		Matcher m = pageBar.matcher(content);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;
		int count = 0;
		while (m.find(lastEndIndex)) {
			sb.append(content.substring(lastEndIndex, m.start()));
			String jspContent = "<" + prefix + "write(TemplateData.getPageBar("
					+ count + "));" + prefix + ">";
			sb.append(jspContent);
			lastEndIndex = m.end();
			count++;
		}
		sb.append(content.substring(lastEndIndex));
		content = sb.toString();
	}
	
	// 文章列表分页(新)
	private void parsePageBarNew() {
		Matcher m = pageBarNew.matcher(content);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;
		int count = 0;
		while (m.find(lastEndIndex)) {
			sb.append(content.substring(lastEndIndex, m.start()));
			String jspContent = "<" + prefix + "write(TemplateData.getPageBarNew("
					+ count + "));" + prefix + ">";
			sb.append(jspContent);
			lastEndIndex = m.end();
			count++;
		}
		sb.append(content.substring(lastEndIndex));
		content = sb.toString();
	}
	
	// 文章列表分页
	private void parsePageBar1() {
		Matcher m = pageBar1.matcher(content);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;
		int count = 0;
		while (m.find(lastEndIndex)) {
			sb.append(content.substring(lastEndIndex, m.start()));
			String jspContent = "<" + prefix + "write(TemplateData.getPageBar1("
					+ count + "));" + prefix + ">";
			sb.append(jspContent);
			lastEndIndex = m.end();
			count++;
		}
		sb.append(content.substring(lastEndIndex));
		content = sb.toString();
	}
	
	// 主站列表新改版分页
	private void parsePageBar2() {
		Matcher m = pageBar2.matcher(content);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			sb.append(content.substring(lastEndIndex, m.start()));
			String jspContent = "<" + prefix + "write(TemplateData.getPageBar2());" + prefix + ">";
			sb.append(jspContent);
			lastEndIndex = m.end();
		}
		sb.append(content.substring(lastEndIndex));
		content = sb.toString();
	}
	
	// 车险文章列表分页
			private void parsePageCarBar() {
				Matcher m = pageCarBar.matcher(content);
				StringBuffer sb = new StringBuffer();
				int lastEndIndex = 0;
				int count = 0;
				while (m.find(lastEndIndex)) {
					sb.append(content.substring(lastEndIndex, m.start()));
					String jspContent = "<" + prefix + "write(TemplateData.getPageCarBar("
							+ count + "));" + prefix + ">";
					sb.append(jspContent);
					lastEndIndex = m.end();
					count++;
				}
				sb.append(content.substring(lastEndIndex));
				content = sb.toString();
			}
	// 文章内容分页
	private void parsePageBreakBar() {
		Matcher m = pageBreakBar.matcher(content);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;
		int count = 0;
		while (m.find(lastEndIndex)) {
			sb.append(content.substring(lastEndIndex, m.start()));
			String jspContent = "<" + prefix
					+ "write(TemplateData.getPageBreakBar(" + count + "));"
					+ prefix + ">";
			sb.append(jspContent);
			lastEndIndex = m.end();
			count++;
		}
		sb.append(content.substring(lastEndIndex));
		content = sb.toString();
	}

	// 文章内容分页
	private void parsePageBreakBarEx() {
		Matcher m = pageBreakBarEx.matcher(content);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;
		int count = 0;
		while (m.find(lastEndIndex)) {
			sb.append(content.substring(lastEndIndex, m.start()));
			String jspContent = "<" + prefix
					+ "write(TemplateData.getPageBreakBarEx(" + count + "));"
					+ prefix + ">";
			sb.append(jspContent);
			lastEndIndex = m.end();
			count++;
		}
		sb.append(content.substring(lastEndIndex));
		content = sb.toString();
	}

	public String parsePlaceHolder(String content) {
		if (null == content)
		{
			return null;
		}
		StringBuffer sb = new StringBuffer();
		Matcher m = pField.matcher(content);
		int lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			sb.append(content.substring(lastEndIndex, m.start()));
			String table = m.group(1);
			String field = m.group(2);

			if (table != null) {
				table = table.toLowerCase();
			}
			if (field != null) {
				field = field.toLowerCase();
			}

			sb.append(table + ".getString(\"" + field + "\")");
			lastEndIndex = m.end();
		}
		sb.append(content.substring(lastEndIndex));
		
		//zhangjinquan 11180 增加全局变量解析
		content = sb.toString();
		sb = new StringBuffer();
		m = pGlobalField.matcher(content);
		lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			sb.append(content.substring(lastEndIndex, m.start()));
			String id = m.group(1).toLowerCase();
			String key = m.group(2).toLowerCase();

			sb.append("TemplateData.getGlobalData(\"" + id + "\", \"" + key + "\")");
			lastEndIndex = m.end();
		}
		sb.append(content.substring(lastEndIndex));
		
		return sb.toString();
	}

	public String parsePlaceHolderStr(String content) {
		StringBuffer sb = new StringBuffer();
		sb.append("\"");
		Matcher m = pField.matcher(content);
		int lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			sb.append(content.substring(lastEndIndex, m.start()));
			String table = m.group(1);
			String field = m.group(2);

			if (table != null) {
				table = table.toLowerCase();
			}
			if (field != null) {
				field = field.toLowerCase();
			}

			sb.append("\"+" + table + ".getString(\"" + field + "\")+\"");
			lastEndIndex = m.end();
		}
		sb.append(content.substring(lastEndIndex) + "\"");

		content = sb.toString();
		sb = new StringBuffer();
		m = Constant.PatternField.matcher(content);
		lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			sb.append(content.substring(lastEndIndex, m.start()));
			String field = m.group(1);

			sb.append("\"+" + field + "+\"");
			lastEndIndex = m.end();
		}
		sb.append(content.substring(lastEndIndex));
		
		//zhangjinquan 11180 增加全局变量解析
		content = sb.toString();
		sb = new StringBuffer();
		m = pGlobalField.matcher(content);
		lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			sb.append(content.substring(lastEndIndex, m.start()));
			String id = m.group(1).toLowerCase();
			String key = m.group(2).toLowerCase();

			sb.append("\" + TemplateData.getGlobalData(\"" + id + "\", \"" + key + "\") +\"");
			lastEndIndex = m.end();
		}
		sb.append(content.substring(lastEndIndex));

		return sb.toString();
	}

	// 解析子列表
	private String parseSubList(String subContent, long varIndex,
			String parentItem, String parentVar) {
		Matcher m = cmsSubList.matcher(subContent);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			sb.append(subContent.substring(lastEndIndex, m.start()));
			lastEndIndex = m.end();
			Mapx map = getAttrMap(m.group(1));
			String listContent = m.group(2);

			String item = ((String) map.get("item")).toLowerCase();
			String type = (String) map.get("type");
			String countStr = (String) map.get("count");
			String condition = (String) map.get("condition");
			String begin = (String) map.get("begin");
			String attribute = (String) map.get("hasattribute");
			String cmsorder = (String) map.get("cmsorder");
			// todo 修改子循环的var标记
			// String subVar = (String) map.get("var");

			String relaColumn = ((String) map.get("RelaColumn"));
			String subNewVar = "";
			if (parentItem.equals(item)) {
				parentItem = parentVar;
				subNewVar = parentItem + "_" + varIndex;
			} else {
				subNewVar = item + "_" + varIndex;
			}

			// 替换上层循环过来的变量
			Pattern p = Pattern.compile("(" + parentItem + ")\\.",
					Pattern.CASE_INSENSITIVE);
			Matcher matcher = p.matcher(listContent);
			listContent = matcher.replaceAll(parentItem + "_" + varIndex + ".");

			// 替换本层循环的变量
			p = Pattern.compile("(" + item + ")\\.", Pattern.CASE_INSENSITIVE);
			matcher = p.matcher(listContent);
			listContent = matcher.replaceAll(item + "_" + varIndex + ".");

			int count = 0;
			if (StringUtil.isNotEmpty(countStr) && StringUtil.isDigit(countStr)) {
				count = Integer.parseInt(countStr);
			} else {
				count = 20;
			}

			String beginIndex = null;
			if (StringUtil.isNotEmpty(begin) && StringUtil.isDigit(begin)) {
				beginIndex = begin;
				count += Integer.parseInt(begin);
			} else {
				beginIndex = "0";
			}

			if ("article".equalsIgnoreCase(item)
					|| "image".equalsIgnoreCase(item)
					|| "video".equalsIgnoreCase(item)
					|| "audio".equalsIgnoreCase(item)
					|| "attachment".equalsIgnoreCase(item)) { // 文档列表
				String catalog = (String) map.get("catalog");
				String level = (String) map.get("level");
				if (StringUtil.isNotEmpty(catalog)
						&& catalog.indexOf("${") != -1) {
					catalog = parsePlaceHolder(catalog);
				} else {
					catalog = "\"" + catalog + "\"";
				}

				String catalogID = null;
				if (relaColumn != null) {
					catalogID = parentVar + ".getString(\"" + relaColumn
							+ "\")";
				} else {
					catalogID = parentVar + ".getString(\"ID\")";
				}

				if (StringUtil.isEmpty(level)) {
					level = "\"all\"";
				} else {
					level = "\"" + level + "\"";
				}

				String jspContent = "<!--循环：" + item + " count：" + count
						+ "-->\n" + "<" + prefix + " DataTable dt_" + varIndex
						+ " = TemplateData.getDocList(\"" + item + "\","
						+ catalogID + "," + level + ",\"" + attribute + "\",\""
						+ type + "\",\"" + count + "\",\"" + condition
						+ "\",\"" + cmsorder + "\");\n" + "if(dt_" + varIndex
						+ "==null){write(\"没有找到栏目\");}else{\n" + "for(int i="
						+ beginIndex + ";i<dt_" + varIndex
						+ ".getRowCount();i++){\n" + "DataRow " + subNewVar
						+ "  = dt_" + varIndex + ".getDataRow(i);\n" + ""
						+ prefix + ">\n";

				jspContent += listContent;
				jspContent += "<" + prefix + "}}" + prefix + ">";
				sb.append(jspContent);
			}else if("friendlink".equalsIgnoreCase(item)){
				//热点专题
				String name = parentVar + ".getString(\"name\")";
				String jspContent = "<!--循环：" + item + " count：" + count
						+ "-->\n" + "<" + prefix + " DataTable dt_"
						+ varIndex + " = TemplateData.getFriendLinkList(\""
						+ item + "\"," + name + ",\"" + count
						+ "\",\"" + condition + "\",\"" + cmsorder + "\");\n" + "if(dt_"
						+ varIndex + "==null){write(\"没有找到友情链接：\"+"
						+ name + ");}else{\n"
						+ "for(int i=" + beginIndex + ";i<dt_" + varIndex
						+ ".getRowCount();i++){\n" + "DataRow " + subNewVar
						+ "  = dt_" + varIndex + ".getDataRow(i);\n" + ""
						+ prefix + ">\n";

				jspContent += listContent;
				jspContent += "<" + prefix + "}}" + prefix + ">";
				sb.append(jspContent);
			} else if ("voteitem".equalsIgnoreCase(item)) {
				String parentID = parentVar + ".getString(\"ID\")";
				String inputType = parentVar + ".getString(\"type\")";
				String jspContent = "<!--循环：" + item + " count：" + count
						+ "-->\n" + "<" + prefix + " DataTable dt_" + varIndex
						+ " = TemplateData.getVoteItems(" + parentID + ","
						+ inputType + "," + "" + count + ",\"" + cmsorder + "\");\n"
						+ "for(int j=0;j<dt_" + varIndex
						+ ".getRowCount();j++){" + "DataRow " + subNewVar
						+ "  = dt_" + varIndex + ".getDataRow(j);\n" + ""
						+ prefix + ">";
				jspContent += listContent;
				jspContent += "<" + prefix + "}" + prefix + ">";

				sb.append(jspContent);
			} else {
				String parentID = "\"0\"";
				if (relaColumn != null) {
					parentID = parentVar + ".getString(\"" + relaColumn + "\")";
				} else {
					parentID = parentVar + ".getString(\"ID\")";
				}

				String jspContent = "<!--循环：" + item + " count：" + count
						+ "-->\n" + "<" + prefix + " DataTable dt_" + varIndex
						+ " = TemplateData.getCatalogList(\"" + item + "\",\""
						+ type + "\"," + parentID + ",\"child\"," + "\""
						+ count + "\"," + "null,\"" + cmsorder + "\");\n" + "for(int j=0;j<dt_"
						+ varIndex + ".getRowCount();j++){" + "DataRow "
						+ subNewVar + "  = dt_" + varIndex
						+ ".getDataRow(j);\n" + "" + prefix + ">";
				jspContent += listContent;
				jspContent += "<" + prefix + "}" + prefix + ">";

				sb.append(jspContent);
			}

			varIndex++;
		}
		sb.append(subContent.substring(lastEndIndex));
		return sb.toString();
	}

	private void parseImagePlayer() {
		Matcher m = cmsImagePlayer.matcher(content);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			sb.append(content.substring(lastEndIndex, m.start()));
			lastEndIndex = m.end();
			Mapx map = getAttrMap(m.group(1));

			String name = (String) map.get("name");
			if (StringUtil.isEmpty(name)) {
				name = (String) map.get("code");
			}
			String width = (String) map.get("width");
			String height = (String) map.get("height");
			String count = (String) map.get("count");
			String cw = (String) map.get("charwidth");

			String jspContent = "" + "<" + prefix
					+ "write(TemplateData.getImagePlayer(\"" + name + "\",\""
					+ width + "\",\"" + height + "\",\"" + count + "\",\"" + cw
					+ "\"));" + prefix + ">";
			sb.append(jspContent);
		}
		sb.append(content.substring(lastEndIndex));
		content = sb.toString();
	}

	private void parseTree() {
		Matcher m = cmsTree.matcher(content);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			sb.append(content.substring(lastEndIndex, m.start()));
			lastEndIndex = m.end();
			Mapx map = getAttrMap(m.group(1));
			String id = (String) map.get("id");
			String method = (String) map.get("method");
			String style = (String) map.get("style");
			String levelStr = (String) map.get("level");
			String tagBody = m.group(2);
			tagBody = StringUtil.javaEncode(tagBody);

			String jspContent = "" + "<" + prefix
					+ "write(TemplateData.getTree(\"" + id + "\",\"" + method
					+ "\",\"" + tagBody + "\", \"" + style + "\",\"" + levelStr
					+ "\"));" + prefix + ">";
			sb.append(jspContent);
		}
		sb.append(content.substring(lastEndIndex));
		content = sb.toString();
	}

	/**
	 * 解析形如<cms:AD code=""></cms:AD>的广告标签
	 * 
	 */

	private void parseAD() {
		Matcher m = cmsAD.matcher(content);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			sb.append(content.substring(lastEndIndex, m.start()));
			lastEndIndex = m.end();
			Mapx map = getAttrMap(m.group(1));
			String name = (String) map.get("name");
			String type = (String) map.get("type");
			String jspContent = "" + "<" + prefix
					+ "write(TemplateData.getAD(\"" + name + "\",\"" + type
					+ "\"));" + prefix + ">";
			sb.append(jspContent);
		}
		sb.append(content.substring(lastEndIndex));
		content = sb.toString();
	}

	/**
	 * 解析形如<cms:form code=""></cms:form>的广告标签
	 * 
	 */
	private void parseForm() {
		Matcher m = cmsForm.matcher(content);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			sb.append(content.substring(lastEndIndex, m.start()));
			lastEndIndex = m.end();
			Mapx map = getAttrMap(m.group(1));
			String code = (String) map.get("code");
			String jspContent = "" + "<" + prefix
					+ "write(TemplateData.getForm(\"" + code + "\"));" + prefix
					+ ">";
			sb.append(jspContent);
		}
		sb.append(content.substring(lastEndIndex));
		content = sb.toString();
	}

	private void parseVote() {
		Matcher m = cmsVote.matcher(content);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			sb.append(content.substring(lastEndIndex, m.start()));
			lastEndIndex = m.end();
			Mapx map = getAttrMap(m.group(1));
			String name = (String) map.get("name");
			String id = (String) map.get("id");
			String voteBodyContent = m.group(2);
			// 自定义，可定义样式
			if (voteBodyContent.indexOf("cms:list") > 0) {
				String voteVar = "vote_" + lastEndIndex;
				String jspContent = "" + "<" + prefix + "DataRow " + voteVar
						+ " = TemplateData.getVoteData(\"" + name + "\",\""
						+ id + "\");\n" + "if(" + voteVar
						+ "==null){\n write(\"没有找到对应的投票主题:" + name
						+ "\");\n}else{\n" + prefix + ">";
				sb.append(jspContent);

				voteBodyContent = "<div id='vote_${vote.id}' class='votecontainer' style='text-align:left'><form id='voteForm_${vote.id}' name='voteForm_${vote.id}' action='"
						+ Config.getValue("ServicesContext")
						+ "/VoteResult.jsp' method='post' target='_blank'><input type='hidden' id='ID' name='ID' value='${vote.id}'><input type='hidden' id='VoteFlag' name='VoteFlag' value='Y'>"
						+ voteBodyContent
						+ "<input type='submit' value='提交' onclick='return checkVote(${vote.id});'>&nbsp;&nbsp<input type='button' value='查看' onclick='javascript:window.open(\""
						+ Config.getValue("ServicesContext")
						+ "/VoteResult.jsp?ID=${vote.id}\",\"VoteResult\")'></form></div>";

				// 替换变量
				String item = "vote";
				Pattern p = Pattern.compile("(" + item + ")\\.",
						Pattern.CASE_INSENSITIVE);
				Matcher matcher = p.matcher(voteBodyContent);
				voteBodyContent = matcher.replaceAll(voteVar + ".");
				sb.append(parseVoteList(voteBodyContent, voteVar));
				sb.append("<" + prefix + "}" + prefix + ">");
			} else {// 默认调查样式，调用js
				String jspContent = "" + "<" + prefix
						+ "write(TemplateData.getVote(\"" + name + "\",\"" + id
						+ "\"));" + prefix + ">";
				sb.append(jspContent);
			}

		}
		sb.append(content.substring(lastEndIndex));
		content = sb.toString();
	}

	private String parseVoteList(String parseContent, String voteVar) {
		Matcher m = cmsList.matcher(parseContent);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;
		int varIndex = 0;
		while (m.find(lastEndIndex)) {
			sb.append(parseContent.substring(lastEndIndex, m.start()));
			lastEndIndex = m.end();
			Mapx map = getAttrMap(m.group(1));
			String listContent = m.group(2);

			String item = ((String) map.get("item")).toLowerCase();
			String countStr = (String) map.get("count");
			String begin = (String) map.get("begin");
			String var = (String) map.get("var");

			if (StringUtil.isEmpty(var)) {
				var = item + "_" + varIndex;
				Pattern p = Pattern.compile("(" + item + ")\\.",
						Pattern.CASE_INSENSITIVE);
				Matcher matcher = p.matcher(listContent);
				listContent = matcher.replaceAll(item + "_" + varIndex + ".");
			}

			listContent = parseSubList(listContent, System.currentTimeMillis(),
					item, var);

			int count = 0;
			if (StringUtil.isNotEmpty(countStr) && StringUtil.isDigit(countStr)) {
				count = Integer.parseInt(countStr);
			} else {
				count = 20;
			}

			String beginIndex = null;
			if (StringUtil.isNotEmpty(begin) && StringUtil.isDigit(begin)) {
				beginIndex = begin;
				count += Integer.parseInt(begin);
			} else {
				beginIndex = "0";
			}

			if ("voteSubject".equalsIgnoreCase(item)) {
				String jspContent = "<!--循环：" + item + " count：" + count
						+ "-->\n" + "<" + prefix + " DataTable dt_" + varIndex
						+ " = TemplateData.getVoteSubjects(" + voteVar
						+ ".getString(\"id\")," + count + ");\n" + "if(dt_"
						+ varIndex + "==null){write(\"没有找到投票列表。\");}else{\n"
						+ "for(int i=" + beginIndex + ";i<dt_" + varIndex
						+ ".getRowCount();i++){" + "DataRow " + var + "  = dt_"
						+ varIndex + ".getDataRow(i);\n" + "" + prefix + ">";
				jspContent += listContent;
				jspContent += "<" + prefix + "}}" + prefix + ">";

				sb.append(jspContent);
			}
			varIndex++;
		}
		sb.append(parseContent.substring(lastEndIndex));
		return sb.toString();
	}

	private void parseComment() {

		Matcher m = cmsComment.matcher(content);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			sb.append(content.substring(lastEndIndex, m.start()));
			lastEndIndex = m.end();
			Mapx map = getAttrMap(m.group(1));
			String name = (String) map.get("name");
			String count = (String) map.get("count");
			String jspContent = "";
			if ("evaluating".equals(name)) {
				jspContent = ""
						+ "<"
						+ prefix
						+ " if(\"1\".equals(TemplateData.getDoc().getString(\"CommentFlag\"))){\nwrite(TemplateData.getEvalComment(\""
						+ count + "\"));\n}" + prefix + ">";
			} else {
				jspContent = ""
						+ "<"
						+ prefix
						+ " if(\"1\".equals(TemplateData.getDoc().getString(\"CommentFlag\"))){\nwrite(TemplateData.getComment(\""
						+ count + "\"));\n}" + prefix + ">";
			}
			sb.append(jspContent);
		}
		sb.append(content.substring(lastEndIndex));
		content = sb.toString();
	}

	/**
	 * 解析cms:include标签，查找对应的包含文件，如果存在，则读取文件并处理文件中的层级关系，并生成一个 文件名_level的文件供调用
	 */
	private void parseInclude() {
		Matcher m = cmsInclude.matcher(content);
		StringBuffer sb = new StringBuffer();
		String siteAlias = SiteUtil.getAlias(siteID);

		String sitePath = staticDir + "/" + siteAlias + "/";
		String templateFilePath = sitePath + "template/";
		if (isPreview) {
			sitePath = Config.getContextRealPath();
		}

		int lastEndIndex = 0;

		while (m.find(lastEndIndex)) {
			sb.append(content.substring(lastEndIndex, m.start()));
			lastEndIndex = m.end();
			Mapx map = getAttrMap(m.group(1));
			String file = (String) map.get("file");
			String includetype = (String) map.get("includetype");
			
			file = file.replaceAll("\\.\\./", "").replaceAll("/+", "/");
			if (isPreview && file.startsWith(Config.getContextPath())) {
				file = StringUtil.replaceEx(file, Config.getContextPath(), "/");
			}
			String includeDir = sitePath + file;
			String includePath = file.substring(0, file.lastIndexOf("/") + 1);

			includeDir = includeDir.substring(0,
					includeDir.lastIndexOf("/") + 1);

			String fileName = file.substring(file.lastIndexOf("/") + 1);
			if (StringUtil.isEmpty(fileName)) {
				Errorx.addError("cms:include没有配置file属性，请检查。");
			} else {
				String includeContent = null;
				File includeFile = new File(sitePath + file);
				if (!includeFile.exists()) {
					includeFile = new File(templateFilePath + fileName);
					if (!includeFile.exists()) {
						Errorx.addError("文件" + sitePath + file + "不存在，请检查。");
					} else {
						File includeFileDir = new File(includeDir);
						if (!includeFileDir.exists()) {
							includeFileDir.mkdirs();
						}
						includeContent = FileUtil.readText(includeFile);
						if (includeContent.indexOf("${") != -1) {
							includeContent = "请先发布附带页面片段："
									+ (String) map.get("file");
						}
					}
				} else {
					includeContent = FileUtil.readText(includeFile);
				}
				String levelStr = getLevelStr();
				String fileLevelName = fileName.substring(0,
						fileName.lastIndexOf('.'))
//						+ "_"
//						+ dirLevel
						+ fileName.substring(fileName.lastIndexOf('.'));

				this.isPageBlock = false;
				includeContent = dealResource(includeContent, levelStr);

				if (isPreview) { // 预览直接解析内容
					sb.append(includeContent);
				} else {
					String includeFileName = includeDir + fileLevelName;
					FileUtil.writeText(includeFileName, includeContent);
					fileList.add(includeFileName);

					if ("Y".equals(Config.getValue("Big5ConvertFlag"))) {
						String big5Html = Big5Convert
								.toTradition(includeContent);
						big5Html = big5Html.replaceAll("cn/", "big5/");

						String big5FileName = includeFileName.substring(0,
								includeFileName.lastIndexOf('.'))
								+ "_big5"
								+ includeFileName.substring(includeFileName
										.lastIndexOf('.'));
						FileUtil.writeText(big5FileName, big5Html);
						fileList.add(big5FileName);
					}

					file = levelStr + includePath + fileLevelName;
					String newFilePath = File.separator + includePath + fileLevelName;
					String ext = SiteUtil.getExtensionType(siteID);
					String jspContent = null;
					if ("jsp".equalsIgnoreCase(ext)) {
						jspContent = "<%@include file=\"" + file + "\"%>";
					} else if ("shtml".equalsIgnoreCase(ext)
							|| "html".equalsIgnoreCase(ext)) {
						jspContent = getConfigSSI(includetype,includeContent,newFilePath);
					} else {
						jspContent = "<!--#include virtual=\"" + file + "\"-->";
					}
					sb.append(jspContent);
				}
			}
		}
		sb.append(content.substring(lastEndIndex));
		content = sb.toString();
	}
	
	/**
	 * @Title: getConfigSSI.
	 * @Description: TODO(获取SSI配置).
	 * @param p_IncludeType
	 * @param p_IncludeContent
	 * @param p_FilePath
	 * @return String.
	 * @author CongZN.
	 */
	private String getConfigSSI(String p_IncludeType,String p_IncludeContent,String p_FilePath){
		String result = p_IncludeContent;
		String ssiConfig = Config.getValue("NGINX_SSI_INCLUE");
		
		if(StringUtil.isEmpty(p_IncludeType) || ssi_current.equals(p_IncludeType) || "N".equals(ssiConfig)){
			return result;
		}
		
		if("virtual".equals(p_IncludeType)){
			result = ssi_virtual.replace("${path}", p_FilePath);
		}else if("file".equals(p_IncludeType)){
			result = ssi_file.replace("${path}", p_FilePath);
		}else{
			return result;
		}
		
		return result;
	}

	private String getLevelStr() {
		String levelString = "";
		if (isPreview) {
			levelString = Config.getContextPath()
					+ Config.getValue("Statical.TargetDir") + "/"
					+ SiteUtil.getAlias(siteID) + "/";
			levelString = levelString.replace('\\', '/').replaceAll("/+", "/");
		} else {
			for (int i = 0; i < dirLevel; i++) {
				levelString += "../";
			}
		}

		return levelString;
	}

	private void parseLinkURL() {
		Matcher m = cmsLink.matcher(content);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			sb.append(content.substring(lastEndIndex, m.start()));
			lastEndIndex = m.end();
			Mapx map = getAttrMap(m.group(1));
			String type = (String) map.get("type");
			String code = (String) map.get("code");
			String name = (String) map.get("name");
			String homePageName = (String) map.get("homePageName");
			if (StringUtil.isNotEmpty(homePageName) && StringUtil.isEmpty(name)) {
				name = homePageName;
			}
			String spliter = (String) map.get("spliter");

			String jspContent = "" + "<" + prefix
					+ "write(TemplateData.getLinkURL(\"" + type + "\",\""
					+ code + "\",\"" + name + "\",\"" + spliter + "\"));"
					+ prefix + ">";
			sb.append(jspContent);
		}
		sb.append(content.substring(lastEndIndex));
		content = sb.toString();
	}

	/**
	 * 解析形如<cms:page type="currentPage"/>的标签
	 * 
	 */
	private void parsePage() {
		Matcher m = cmsPage.matcher(content);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			sb.append(content.substring(lastEndIndex, m.start()));
			lastEndIndex = m.end();
			Mapx map = getAttrMap(m.group(1));
			String type = (String) map.get("type");
			String value = (String) map.get("value");
			String name = (String) map.get("name");
			String target = (String) map.get("target");

			String jspContent = "" + "<" + prefix
					+ "write(TemplateData.getPage(\"" + type + "\",\"" + value
					+ "\",\"" + name + "\",\"" + target + "\"));" + prefix
					+ ">";
			sb.append(jspContent);
		}
		sb.append(content.substring(lastEndIndex));
		content = sb.toString();
	}

	private void parseVar() {
		Matcher m = cmsVar.matcher(content);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			sb.append(content.substring(lastEndIndex, m.start()));
			lastEndIndex = m.end();
			Mapx map = getAttrMap(m.group(1));
			String type = (String) map.get("type");
			if (StringUtil.isNotEmpty(type)) {
				type = type.toLowerCase();
			}
			String name = (String) map.get("name");
			String title = (String) map.get("title");

			if (StringUtil.isEmpty(name)) {
				name = title;
			}

			if (StringUtil.isEmpty(name)) {
				sb.append("cms:var标签中的name属性不能为空。");
			} else {
				String listContent = m.group(2);
				long index = OrderUtil.getDefaultOrder();
				String newVar = type + "_" + index;
				Pattern p = Pattern.compile("(" + type + ")\\.",
						Pattern.CASE_INSENSITIVE);
				Matcher matcher = p.matcher(listContent);
				listContent = matcher.replaceAll(type + "_" + index + ".");

				String jspContent = null;
				if ("article".equalsIgnoreCase(type)
						|| "image".equalsIgnoreCase(type)
						|| "video".equalsIgnoreCase(type)
						|| "audio".equalsIgnoreCase(type)
						|| "attachment".equalsIgnoreCase(type)) {
					jspContent = "<" + prefix + " DataRow " + newVar
							+ "=TemplateData.getDocument(\"" + type + "\",\""
							+ name + "\"); if(" + newVar
							+ "==null){write(\"没有找到对象" + name + "+\");}else{"
							+ prefix + ">";
				} else if ("catalog".equalsIgnoreCase(type)) {
					jspContent = "<" + prefix + " DataRow " + newVar
							+ "=TemplateData.getCatalog(\"" + type + "\",\""
							+ name + "\"); if(" + newVar
							+ "==null){write(\"没有找到对象" + name + "+\");}else{"
							+ prefix + ">";
				}

				sb.append(jspContent);
				sb.append(listContent);
				sb.append("<" + prefix + "}" + prefix + ">");
			}
		}
		sb.append(content.substring(lastEndIndex));
		content = sb.toString();
	}
	
	@SuppressWarnings("unchecked")
	private String parseSubVar(String content, long varIndex,
			String parentItem, String parentVar)
	{
		Matcher m = cmsSubVar.matcher(content);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			sb.append(content.substring(lastEndIndex, m.start()));
			lastEndIndex = m.end();
			Mapx map = getAttrMap(m.group(1));
			String type = (String) map.get("type");
			if (StringUtil.isNotEmpty(type)) {
				type = type.toLowerCase();
			}
			String name = (String) map.get("name");
			
			if (StringUtil.isEmpty(name) || StringUtil.isEmpty(type))
			{
				logger.warn("cms:subvar标签中的name和type属性均不能为空。");
				return content;
			}
			
			String newname = this.parsePlaceHolder(name);
			
			String subNewVar = null; 
			if (parentItem.equals(type))
			{
				parentItem = parentVar;
				subNewVar = parentItem + "_" + varIndex;
			}
			else
			{
				subNewVar = type + "_" + varIndex;
			}
			
			if (name.equals(newname))
			{
				name = "\"" + name + "\"";
			}
			else
			{
				name = newname;
			}

			String listContent = m.group(2);
			Pattern p = Pattern.compile("(" + type + ")\\.",
					Pattern.CASE_INSENSITIVE);
			Matcher matcher = p.matcher(listContent);
			listContent = matcher.replaceAll(subNewVar + ".");
			
			//listContent = this.parsePlaceHolderStr(listContent);

			String jspContent = null;
			if ("article".equalsIgnoreCase(type)
					|| "image".equalsIgnoreCase(type)
					|| "video".equalsIgnoreCase(type)
					|| "audio".equalsIgnoreCase(type)
					|| "attachment".equalsIgnoreCase(type)) {
				jspContent = "<" + prefix + " DataRow " + subNewVar
						+ "=TemplateData.getDocument(\"" + type + "\","
						+ name + "); if(" + subNewVar
						+ "==null){write(\"没有找到对象\"+" + name + "+\");}else{"
						+ prefix + ">";
			} else if ("catalog".equalsIgnoreCase(type)) {
				jspContent = "<" + prefix + " DataRow " + subNewVar
						+ "=TemplateData.getCatalog(\"" + type + "\","
						+ name + "); if(" + subNewVar
						+ "==null){write(\"没有找到对象\"+" + name + ");}else{"
						+ prefix + ">";
			}

			sb.append(jspContent);
			sb.append(listContent);
			sb.append("<" + prefix + "}" + prefix + ">");
		}
		sb.append(content.substring(lastEndIndex));
		return sb.toString();
	}

	public boolean parse() {
		if (content == null) {
			return false;
		}

		String ext = SiteUtil.getExtensionType(siteID);
		if ("jsp".equalsIgnoreCase(ext)) {
			prefix = "#";
		}

		//zhangjinquan 11180 注释掉无用代码
		/*
		StringBuffer initContent = new StringBuffer();
		initContent.append(content);
		content = initContent.toString();
		*/

		processPath();
		parseInclude();
		parseImagePlayer();
		parseAD();
		parseForm();
		parseVote();
		parseComment();
		parseLinkURL();
		parsePage();
		parseTree();
		parseVar();
		parseList();
		parsePageBreakBar();
		parsePageBreakBarEx();
		addHeader();
		dealBlock();
		
		//zhangjinquan 11180 将头部字符串插入到最前端 2012-12-19
		content = this.headStrBuf.append(content).toString();

		return true;
	}

	private void dealBlock() {/*
		if (blockSet != null && blockSet.size() > 0) {
			// 删除原有的pageblock
			int index = templateFileName.indexOf(staticDir);
			templateFileName = templateFileName.substring(index
					+ staticDir.length());
			templateFileName += "_" + dirLevel;
			trans.add(new QueryBuilder(
					"delete from zcpageblock where exists (select blockid from  ZCTemplateBlockRela where filename=? and blockid=zcpageblock.id)",
					templateFileName));
			trans.add(new QueryBuilder(
					"delete  from  ZCTemplateBlockRela where filename=?",
					templateFileName));
			trans.add(blockSet, Transaction.INSERT);

			// 保存模板对应的所有block
			ZCTemplateBlockRelaSet relaSet = new ZCTemplateBlockRelaSet();
			for (int i = 0; i < blockSet.size(); i++) {
				ZCTemplateBlockRelaSchema rela = new ZCTemplateBlockRelaSchema();
				rela.setSiteID(siteID);
				rela.setFileName(templateFileName);
				rela.setBlockID(blockSet.get(i).getID());
				rela.setAddTime(new Date());
				if (User.getCurrent() != null && User.getUserName() != null) {
					rela.setAddUser(User.getUserName());
				} else {
					rela.setAddUser("SYSTEM");
				}

				relaSet.add(rela);
			}
			trans.add(relaSet, Transaction.INSERT);

			if (!trans.commit()) {
			}
		}
	*/}

	/**
	 * 转换一个形如 a="1" c="2" 的字符串为Mapx
	 */
	@SuppressWarnings("unchecked")
	public static Mapx getAttrMap(String str) {
		Mapx map = new Mapx();
		
		int lastEndIndex = 0;
		int attr1Num = 0;
		int end = 0;
		ArrayList<int[]> attr1Scope = new ArrayList<int[]>();
		
		Matcher m = pAttr1.matcher(str);
		while (m.find(lastEndIndex)) {
			String value = m.group(3);
			if (value != null) {
				value = value.trim();
			}
			end = m.end();
			attr1Num++;
			attr1Scope.add(new int[]{lastEndIndex, end});
			map.put(m.group(1).toLowerCase(), value);
			lastEndIndex = end;
		}

		m = pAttr2.matcher(str);
		lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			end = m.end();
			//zhangjinquan 11180 增加判断控制第二类属性不能包含在第一类属性值的内部 2012-12-26 start
			boolean putFlag = true;
			for (int i=0; i<attr1Num; i++)
			{
				int[] scope = attr1Scope.get(i);
				if (((scope[0]<=lastEndIndex) && (lastEndIndex<=scope[1]))
					||((scope[0]<=end) && (end<=scope[1])))
				{
					putFlag = false;
					break;
				}
			}
			if (putFlag)
			{
				String value = m.group(2);
				if (value != null) {
					value = value.trim();
				}
				map.put(m.group(1).toLowerCase(), value);
			}
			//zhangjinquan 11180 增加判断控制第二类属性不能包含在第一类属性值的内部 2012-12-26 end
			lastEndIndex = end;
		}
		return map;
	}

	public String getTemplateFileName() {
		return templateFileName;
	}

	public void setTemplateFileName(String templateFileName) {
		this.templateFileName = templateFileName;
	}

	public Errorx getError() {
		return error;
	}

	public void setError(Errorx error) {
		this.error = error;
	}

	public int getDirLevel() {
		return dirLevel;
	}

	public void setDirLevel(int dirLevel) {
		this.dirLevel = dirLevel;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isPagelock() {
		return isPageBlock;
	}

	public void setPageBlock(boolean isPageBlock) {
		this.isPageBlock = isPageBlock;
	}

	public ZCPageBlockSet getBlockSet() {
		return blockSet;
	}

	public void setBlockSet(ZCPageBlockSet blockSet) {
		this.blockSet = blockSet;
	}

	public long getSiteID() {
		return siteID;
	}

	public void setSiteID(long siteID) {
		this.siteID = siteID;
	}

	public ArrayList getFileList() {
		return fileList;
	}

	public Mapx getPageListPrams() {
		return pageListPrams;
	}

	public void setPageListPrams(Mapx pageListPrams) {
		this.pageListPrams = pageListPrams;
	}

	public boolean isPreview() {
		return isPreview;
	}

	public void setPreview(boolean isPreview) {
		this.isPreview = isPreview;
	}
}
