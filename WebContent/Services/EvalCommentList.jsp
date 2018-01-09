<%@page import="cn.com.sinosoft.action.shop.EvalCommentAction"%>
<%@page import="com.sinosoft.cms.pub.CMSCache"%>
<%@page import="com.sinosoft.framework.Config"%>
<%@page import="com.sinosoft.framework.data.DataTable"%>
<%@page import="com.sinosoft.framework.data.QueryBuilder"%>
<%@page import="com.sinosoft.framework.utility.FileUtil"%>
<%@page import="com.sinosoft.framework.utility.HtmlUtil"%>
<%@page import="com.sinosoft.framework.utility.Mapx"%>
<%@page import="com.sinosoft.framework.utility.StringUtil"%>
<%@ page import="com.sinosoft.schema.ZCCatalogConfigSchema" %>
<%@ page import="java.io.File" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ taglib uri="controls" prefix="z"%>
 
<%
	String CatalogID = request.getParameter("CatalogID"); 
	if ((null==CatalogID) || (!CatalogID.matches("\\d+")))
	{
		return;
	}
	// 文章ID
	String relaID = request.getParameter("RelaID");
	if ((null==relaID) || (!relaID.matches("\\d+")))
	{
		return;
	}
	// 一页显示评论的个数
	String count = request.getParameter("Count");
	if ((null==count) || (!count.matches("\\d+")))
	{
		return;
	}
	int pageSize = Integer.valueOf(count);
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

	//如果用户登录，获取用户对象
	String memberId = (String) request.getSession().getAttribute("loginMemberId");

	String jsPart = "";
	String part1 = "";
	String loop = "";
	String part2 = "";
	String pagePart = "";
	String hiddenPart = "";

	// 判断是否允许评论
	ZCCatalogConfigSchema catalogConfig = CMSCache.getCatalogConfig(CatalogID);
	if(catalogConfig!=null){
		if(!catalogConfig.getAllowComment().equals("Y")){
			return;
		}
	}

	// 从CommentListPage.jsp中截取各个代码模块，方便后续处理
	if (Config.isDebugMode() || "".equals(loop)) {
		File file = new File(application.getRealPath("Services/EvalCommentListPage.jsp"));
		String text = FileUtil.readText(file);
		String jsBegin = "<!-- comment js begin -->";
		String jsEnd = "<!-- comment js end -->";
		String loopBegin = "<!-- comment loop begin -->";
		String loopEnd = "<!-- comment loop end -->";
		String pageDiv = "<!-- comment page -->";
		String hiddenBegin = "<!-- comment all hidden begin -->";
		String hiddenEnd = "<!-- comment all hidden end -->";

		jsPart = text.substring(text.indexOf(jsBegin) + jsBegin.length(), text.indexOf(jsEnd));
		part1 = text.substring(text.indexOf(jsEnd) + jsEnd.length(), text.indexOf(loopBegin));
		loop = text.substring(text.indexOf(loopBegin) + loopBegin.length(), text.indexOf(loopEnd));
		part2 = text.substring(text.indexOf(loopEnd) + loopEnd.length(), text.indexOf(pageDiv));
		pagePart = text.substring(text.indexOf(pageDiv) + pageDiv.length(), text.indexOf(hiddenBegin));
		hiddenPart = text.substring(text.indexOf(hiddenBegin) + hiddenBegin.length(), text.indexOf(hiddenEnd));
	}

	QueryBuilder qb = new QueryBuilder();

	// 根据文章ID，取得评论总数
	String sqlCount = "select count(1) from zccomment where relaid=? and verifyflag='Y'";
	qb.setSQL(sqlCount);
	qb.add(Long.parseLong(relaID));
	int commentSumCount = qb.executeInt();

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

	// 根据当前用户登录的IP，查看被点赞的评论ID
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

	// 取得评论相关SQL
	StringBuffer loopsb = new StringBuffer();
	StringBuffer hiddensb = new StringBuffer();

	String sql = "select AddUser,AddUserIP,DATE_FORMAT(AddTime,'%Y-%m-%d %H:%i') as 'AddTime',Content, zccomment.relaid, ReplyContent, '' as replyStyle, '' as StaticServerContext, ";
	sql += " if(zccomment.praisedNum is null, 0, zccomment.praisedNum) praisedNum, '' as praiseClasss, '' as hot_mes, zccomment.ID,a.headPicPath, 'none' as delStyle, zccomment.prop1 as memberId ";
	sql += " from zccomment left join member a on zccomment.prop1=a.id  where zccomment.relaid=? and verifyflag='Y'";
	
	// 点赞数最多的三个评论
	String sql1 = sql + " order by zccomment.praisedNum desc, zccomment.AddTime desc limit 0, 3 ";
	DataTable dt1 = new QueryBuilder(sql1, Long.parseLong(relaID)).executeDataTable();
	String commIDs = "";
	int topRaisedCount = 0;
	if (dt1 != null && dt1.getRowCount() > 0) {
		topRaisedCount = dt1.getRowCount();
		for (int i = 0; i < topRaisedCount; i++) {
			commIDs += (",'" + dt1.getString(i, "ID")+"'");
		}
		// 增加sql条件，去除前三评论
		commIDs = commIDs.substring(1);
		sql += " and zccomment.ID not in ("+commIDs+") ";
	}
	
	sql += " order by zccomment.AddTime desc, zccomment.praisedNum desc ";

	// 如果有评论
	if (commentSumCount > 0) {
		qb = new QueryBuilder(sql);
		DataTable dt = null;
		qb.add(Long.parseLong(relaID));
		// 查询全部评论，不包括点赞前三评论
		dt = qb.executeDataTable();
		if (topRaisedCount > 0) {
			// 在总评论数大于点赞前三评论数时候，把点赞前三评论加入数据集合
			if (dt != null) {
				for (int i = 0; i < topRaisedCount; i++) {
					dt.insertRow(dt1.get(i), i);
				}
			} else {
				// 在评论数等于点赞前三评论数时候，把点赞前三评论加入数据集合
				dt = dt1;
			}
		}

		// 取得当前页的实际评论数
		int currentComCount = commentSumCount > pageSize ? pageSize : commentSumCount;

		// 添加第一页的评论数据
		for (int i = 0; i < currentComCount; i++) {
		    // 热评
            String id = dt.getString(i, "id");
            if (commIDs.contains(id)) {
                dt.set(i, "hot_mes", "hot_mes");
            }

			// 是否有回复，如果有回复，显示
			String replyContent = dt.getString(i, "ReplyContent");
			String style = "block";
			if (StringUtil.isEmpty(replyContent)) {
				style = "none";
			}
			dt.set(i, "replyStyle", style);

			// 根据当前IP，设置点赞状态
			if (mapPraised.containsKey(dt.getString(i, "ID"))) {
				dt.set(i, "praiseClasss", "pressed");
			}

			String addUser = dt.getString(i, "AddUser");
			// 评论者名称处显示,没有登录的评论用户名显示
			if (StringUtil.isNotEmpty(addUser) && "游客".equals(addUser))
			{
				String addUserIP = dt.getString(i, "AddUserIP");
				if (StringUtil.isNotEmpty(addUserIP)) {
					addUserIP = addUserIP.substring(0,addUserIP.indexOf(".")) + ".***.***" + addUserIP.lastIndexOf(".");
				}
				addUser = addUser + "&nbsp;" + addUserIP;
			}
			// 登录后的评论用户名显示
			else if (StringUtil.isNotEmpty(addUser) && !"游客".equals(addUser)) {
				// 如果是手机号
				if (StringUtil.isMobileNO(addUser)) {
					addUser = "用户：" + addUser.replace(addUser.substring(3,7), "****");
				}
				// 如果是邮箱
				else if (StringUtil.isMail(addUser)) {
					String beforePart = addUser.substring(3, addUser.indexOf("@"));
					String afterPart = addUser.substring(addUser.indexOf("@")+1, addUser.indexOf("."));
					addUser = "用户：" + addUser.replace(beforePart, "**").replace(afterPart, "***");
				}
			}
			dt.set(i, "AddUser", addUser);

			// 时间显示设置
			String addTime = dt.getString(i, "AddTime");
			// 小时差计算
			SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			long lAddTime = simpleFormat.parse(addTime).getTime();
			long nowSystemTime = System.currentTimeMillis();
			int hours = new Double(Math.floor((nowSystemTime - lAddTime) * 1.0/(1000 * 60 * 60))).intValue();

			if (hours == 0) {
				addTime = "刚刚";
			} else if ( hours > 0 &&  hours < 24) {
				addTime = hours + "小时前";
			}
			dt.set(i, "AddTime", addTime);

			// 删除按钮显示
			String addMemberId = dt.getString(i, "memberId");
			if (StringUtil.isNotEmpty(memberId) && memberId.equals(addMemberId)) {
				dt.set(i, "delStyle", "block");
			}

			// StaticServerContext
			dt.set(i, "StaticServerContext", Config.getValue("StaticResourcePath"));

			// 头像
			String headPicPath = dt.getString(i, "headPicPath");
			if (StringUtil.isEmpty(headPicPath)) {
				headPicPath = "/images/redesign/user_headr_03.png";
			}
			dt.set(i, "headPicPath", Config.getValue("StaticResourcePath") + "/" + headPicPath);

			loopsb.append(HtmlUtil.replacePlaceHolder(loop, dt.getDataRow(i).toMapx(), true));
		}

		// 添加全部评论数据到隐藏域，方便SEO抓取
		for (int i = 0; i < commentSumCount; i ++) {
			hiddensb.append(HtmlUtil.replacePlaceHolder(hiddenPart, dt.getDataRow(i).toMapx(), true));
		}
	}
	
	Mapx map = new Mapx();
	map.put("RelaID", relaID);
	map.put("CatalogID", CatalogID);
    map.put("CatalogType", catalogType);
    map.put("SiteID", siteID);
	map.put("ServerContext", Config.getValue("ServerContext"));
	map.put("StaticServerContext", Config.getValue("StaticResourcePath"));
	map.put("pageSize", pageSize);
	map.put("recordCount", commentSumCount);
	int pageCount = new Double(Math.ceil(commentSumCount * 1.0 / pageSize)).intValue();
	map.put("pageCount", pageCount);
	
	StringBuffer sb = new StringBuffer();
	sb.append(jsPart);
	sb.append(HtmlUtil.replacePlaceHolder(part1, map, true));
	if (commentSumCount>0) {
		sb.append(loopsb);
	}
	sb.append(part2);
	// 添加分页部分
	if (commentSumCount>0 && pageCount > 1) {
		EvalCommentAction commentAction = new EvalCommentAction();
		sb.append("<div class='plpage'><div class='page_area'><div id='pagination1' class='pagination'>");
		sb.append(commentAction.getPageBar(1, pageCount));
		sb.append("</div></div></div>\n");
	}
	sb.append(pagePart);
	sb.append(hiddensb);
	sb.append("</div>");

	int index = sb.indexOf("\n");
	int lastIndex = -1;
	while (index != -1) {
		String tt = sb.substring(lastIndex + 1, index).trim().replaceAll("'", "\\\\'");
		if (StringUtil.isNotEmpty(tt))
		{
			out.println("document.write(\'" + tt + "\');");
		}
		lastIndex = index;
		index = sb.indexOf("\n", index + 1);
	}
%>