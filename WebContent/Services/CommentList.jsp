<%@page import="cn.com.sinosoft.action.shop.CommentAction"%>
<%@page import="com.sinosoft.cms.dataservice.CommentService"%>
<%@page import="com.sinosoft.cms.pub.CMSCache"%><%@page import="com.sinosoft.cms.pub.SiteUtil"%>
<%@page import="com.sinosoft.framework.Config"%>
<%@page import="com.sinosoft.framework.data.DataTable"%>
<%@page import="com.sinosoft.framework.data.QueryBuilder"%>
<%@page import="com.sinosoft.framework.utility.FileUtil"%>
<%@page import="com.sinosoft.framework.utility.HtmlUtil"%>
<%@page import="com.sinosoft.framework.utility.Mapx"%>
<%@page import="com.sinosoft.framework.utility.StringUtil"%>
<%@page import="com.sinosoft.member.Login"%>
<%@page import="com.sinosoft.schema.ZCCatalogConfigSchema"%>
<%@ page import="java.io.File" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ taglib uri="controls" prefix="z"%>
 
<%
	String CatalogID = request.getParameter("CatalogID"); 
	if ((null==CatalogID) || (!CatalogID.matches("\\d+")))
	{
		return;
	}
	String relaID = request.getParameter("RelaID");
	if ((null==relaID) || (!relaID.matches("\\d+")))
	{
		return;
	}
	String count = request.getParameter("Count");
	if ((null==count) || (!count.matches("\\d+")))
	{
		return;
	}
	String siteID = request.getParameter("SiteID");
	if ((null==siteID) || (!siteID.matches("\\d+")))
	{
		return;
	}
	String catalogType = request.getParameter("CatalogType");
	if ((null==catalogType) || (!catalogType.matches("\\d+")))
	{
		return;
	}
	String memberId = (String) request.getSession().getAttribute("loginMemberId");
	
	String header = "";
	String loop = "";
	String purpose = "";
	String purposefo = "";
	String info = "";
	String listheader = "";
	String listFooter = "";

	ZCCatalogConfigSchema catalogConfig = CMSCache.getCatalogConfig(CatalogID);
	if(catalogConfig!=null){
		if(!catalogConfig.getAllowComment().equals("Y")){
			return;
		}
	}

	Login.checkAndLogin(request);
	if (Config.isDebugMode() || "".equals(loop)) {
		File file = new File(application.getRealPath("Services/CommentListPage.jsp"));
		String text = FileUtil.readText(file);
		String headerBegin = "<!-- comment header begin -->";
		String headerEnd = "<!-- comment header end -->";
		String purposeBegin = "<!-- comment purpose begin -->";
		String purposeEnd = "<!-- comment purpose end -->";
		String contentBegin = "<!-- comment content begin -->";
		String loopBegin = "<!-- comment loop begin -->";
		String loopEnd = "<!-- comment loop end -->";
		String contentEnd = "<!-- comment content end -->";
		
		header = text.substring(text.indexOf(headerBegin) + headerBegin.length(), text.indexOf(headerEnd));
		header = header.replaceAll("</head>","");
		header = header.replaceAll("<body>","");
		info = text.substring(text.indexOf(headerEnd) + headerEnd.length(), text.indexOf(purposeBegin));
		purpose = text.substring(text.indexOf(purposeBegin) + purposeBegin.length(), text.indexOf(purposeEnd));
		purposefo = text.substring(text.indexOf(purposeEnd) + purposeEnd.length(), text.indexOf(contentBegin));
		listheader = text.substring(text.indexOf(contentBegin) + contentBegin.length(), text.indexOf(loopBegin));
		loop = text.substring(text.indexOf(loopBegin) + loopBegin.length(), text.indexOf(loopEnd));
		listFooter = text.substring(text.indexOf(loopEnd) + loopEnd.length(), text.indexOf(contentEnd));
	}

	boolean commentFlag = SiteUtil.getCommentAuditFlag(siteID);
	String WherePart = "";
	if (commentFlag) {
		WherePart = " and verifyflag='Y'"; //评论需要审核
	}
	String pageSize = Config.getValue("CommentPageRecordNum");//评论每页条数
	pageSize = StringUtil.isEmpty(pageSize)?count:pageSize;
	String strQueryAll = Config.getValue("CommentQueryAllOnce");//是否一次查询出所有相关评论记录
	boolean queryAll = "Y".equalsIgnoreCase(strQueryAll);//是否一次查询所有数据
	QueryBuilder qb = new QueryBuilder();
	int recordCount = 0;
	if (!queryAll)
	{
		String sqlCount = "select count(1) from zccomment where relaid=? and isBuy = '1' " + WherePart;
		qb.setSQL(sqlCount);
		qb.add(Long.parseLong(relaID));
		recordCount = qb.executeInt();
	}
	
	// 取得用户IP
	String ip = request.getHeader("X-Forwarded-For");
	if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		ip = request.getHeader("Proxy-Client-IP");
	}
	if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		ip = request.getHeader("WL-Proxy-Client-IP");
	}
	if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		ip = request.getRemoteAddr();
	}
	
	Map<String, String> mapPraised = new HashMap<String, String>();
	if (StringUtil.isNotEmpty(ip)) {
		DataTable dt = new QueryBuilder("select commentId from CommentPraisedInfo where RelaID=? and isPraised = 'Y' and userIP = ?", Long.parseLong(relaID), ip).executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			int rowCount = dt.getRowCount();
			for (int i = 0; i < rowCount; i++) {
				mapPraised.put(dt.getString(i, 0), "Y");
			}
		}
	}
	
	StringBuffer loopsb = new StringBuffer();
	String sql = "select AddUser,DATE_FORMAT(AddTime,'%Y-%m-%d %H:%i') as 'AddTime',Content, zccomment.relaid, ReplyContent, '' as style, 'none' as recommflag,";
	sql += " score, if(zccomment.praisedNum is null, 0, zccomment.praisedNum) praisedNum, '' as praiseClasss,zccomment.ID, 'false' as praiseAria, '' as borderClass, ";
	sql += " IFNULL(Purpose, '') as Purpose, a.vipFlag, a.grade, zccomment.memGrade from zccomment left join member a on zccomment.prop1=a.id  where zccomment.relaid=? and zccomment.isBuy = '1' "+ WherePart ;
	
	// 点赞数最多的三个评论
	String sql1 = sql + " order by zccomment.praisedNum desc, zccomment.AddTime desc limit 0, 3 ";
	DataTable dt1 = new QueryBuilder(sql1, Long.parseLong(relaID)).executeDataTable();
	String commIDs = "";
	int rowcount = 0;
	if (dt1 != null && dt1.getRowCount() > 0) {
		rowcount = dt1.getRowCount();
		for (int i = 0; i < rowcount; i++) {
			dt1.set(i, "recommflag", "");
			commIDs += (",'" + dt1.getString(i, "ID")+"'");
		}
		commIDs = commIDs.substring(1);
		sql += " and zccomment.ID not in ("+commIDs+") ";
	}
	
	sql += " order by zccomment.AddTime desc, zccomment.praisedNum desc ";

	int iPageSize = StringUtil.isNotEmpty(pageSize) ? Integer.parseInt(pageSize):0;
	if ((queryAll || (recordCount>0)) && (iPageSize > 0)) {
		qb.setSQL(sql);
		DataTable dt = null;
		if (queryAll)
		{
			qb.add(Long.parseLong(relaID));
			dt = qb.executeDataTable();
			if (rowcount > 0) {
				if (dt != null) {
					for (int i = 0; i < rowcount; i++) {
						dt.insertRow(dt1.get(i), i);
					}
					
				} else {
					dt = dt1;
				}
				
			}
			recordCount = dt.getRowCount();
			iPageSize = recordCount;
			
		}
		else
		{
			if (rowcount > 0) {
				dt = qb.executePagedDataTable(iPageSize-rowcount, 0);
				if (dt != null) {
					for (int i = 0; i < rowcount; i++) {
						dt.insertRow(dt1.get(i), i);
					}
					
				} else {
					dt = dt1;
				}
				
			} else {
				dt = qb.executePagedDataTable(iPageSize, 0);
			}
			
		}
		int commCount = dt.getRowCount();
		for (int i = 0; i < commCount; i++) {
			String replyContent = dt.getString(i, "ReplyContent");
			String style = "block";
			if (StringUtil.isEmpty(replyContent)) {
				style = "none";
			}
			dt.set(i, "style", style);
			
			if (mapPraised.containsKey(dt.getString(i, "ID"))) {
				dt.set(i, "praiseClasss", "pressed");
				dt.set(i, "praiseAria", "true");
			}
			
			String addUser = dt.getString(i, "AddUser");
			if (StringUtil.isEmpty(addUser))
			{
				addUser = "匿名用户";
			}
			else if ((!addUser.startsWith("匿名")) && (addUser.length()>3))
			{
				addUser = addUser.substring(0, 3) + "**";
			}
			dt.set(i, "AddUser", addUser);
			
			String grade = dt.getString(i, "grade");
			String memGrade = dt.getString(i, "memGrade");
			if ("Y".equals(dt.getString(i, "vipFlag"))) {
				dt.set(i, "grade", "vip");
			} else if (StringUtil.isNotEmpty(grade)) {
				if ("K0".equals(grade)) {
					dt.set(i, "grade", "star");
				} else {
					dt.set(i, "grade", grade.toLowerCase());
				}
			} else if (StringUtil.isNotEmpty(memGrade)){
				if ("K0".equals(memGrade)) {
					dt.set(i, "grade", "star");
				} else {
					dt.set(i, "grade", memGrade.toLowerCase());
				}
				
			} else {
				dt.set(i, "grade", "k1");
			}
			
			if (StringUtil.isNotEmpty(dt.getString(i, "Purpose"))) {
				dt.set(i, "Purpose", "目的："+dt.getString(i, "Purpose"));
			}
			if ((i+1) == commCount) {
				dt.set(i, "borderClass", "clear_btn_bor");
			}
			
			loopsb.append(HtmlUtil.replacePlaceHolder(loop, dt.getDataRow(i).toMapx(), true));
		}
	}
	
	Mapx map = new Mapx();
	CommentService.init(map);
	map.put("RelaID", relaID);
	map.put("CatalogID", CatalogID);
	map.put("CatalogType", catalogType);
	map.put("SiteID", siteID);
	map.put("pageSize", pageSize);
	map.put("recordCount", String.valueOf(recordCount));
	int iPageCount = iPageSize > 0?((recordCount/iPageSize) + (recordCount%iPageSize==0?0:1)):0;
	map.put("pageCount", String.valueOf(iPageCount));

	// 满意度计算
	sql = "select sum(score), count(1) from zccomment where relaid = ? and isBuy = '1' " + WherePart;
	DataTable dt = new QueryBuilder(sql, Long.parseLong(relaID)).executeDataTable();
	// 总星数
	int sumStarNum = 0;
	// 总评论数
	int sumCommentNum = 0;
	if (dt != null && dt.getRowCount() > 0) {
		sumStarNum = dt.getInt(0, 0);
		sumCommentNum = dt.getInt(0, 1);
	}
	NumberFormat nt = NumberFormat.getPercentInstance();
	//设置百分数精确度2即保留两位小数
	nt.setMinimumFractionDigits(0);
	float fPercent = (float)sumStarNum/(sumCommentNum * 5);
	map.put("percent", nt.format(fPercent));

	// 购买的目的
	StringBuffer purposesb = new StringBuffer();
	dt = new QueryBuilder("select CONCAT(Purpose, '（', COUNT(1), '）') as purpose from zccomment where relaid = ? and Purpose is not null and Purpose != '' and isBuy='1' "+ WherePart + " group by Purpose ORDER BY COUNT(1) DESC ", relaID).executeDataTable();
	if (dt == null || dt.getRowCount() == 0) {
		String productType = new QueryBuilder("SELECT p.ProductType FROM sdproduct p, sdsearchrelaproduct s where s.ProductID=p.ProductID and s.Prop1=?", relaID).executeString();
		if (StringUtil.isNotEmpty(productType)) {
			String codetype = ("Comment."+productType+"Type");
			dt = new QueryBuilder("select CodeName as purpose from zdcode where CodeType=? and ParentCode=?", codetype, codetype).executeDataTable();
		}
	}
	if (dt != null && dt.getRowCount() > 0) {
		for (int i = 0; i < dt.getRowCount(); i++) {
			purposesb.append(HtmlUtil.replacePlaceHolder(purpose, dt.getDataRow(i).toMapx(), true));
		}
	}

	StringBuffer sb = new StringBuffer();
	sb.append(HtmlUtil.replacePlaceHolder(header, map, true));
	sb.append(HtmlUtil.replacePlaceHolder(info, map, true));
	sb.append(purposesb);
	sb.append(purposefo);
	if (recordCount>0)
	{
		sb.append(listheader);
		sb.append(loopsb);
		sb.append(listFooter);
		if (iPageCount > 1) {
			CommentAction commentAction = new CommentAction();
			sb.append("<div class='page_area'><div id='pagination'>");
			sb.append(commentAction.getPageBar(1, iPageCount));
			sb.append("</div></div>\n");
		}
		
	}


	int index = sb.indexOf("\n");
	int lastIndex = -1;
	while (index != -1) {
		String tt = sb.substring(lastIndex + 1, index).trim().replaceAll("'", "\\\\'");
		//System.out.println("----"+tt);
		if (StringUtil.isNotEmpty(tt))
		{
			out.println("document.write(\'" + tt + "\');");
		}
		lastIndex = index;
		index = sb.indexOf("\n", index + 1);
	}
%>