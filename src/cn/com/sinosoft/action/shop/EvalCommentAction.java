package cn.com.sinosoft.action.shop;

import cn.com.sinosoft.entity.Member;
import com.sinosoft.cms.dataservice.CommentService;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.CommentPraisedInfoSchema;
import com.sinosoft.schema.ZCCommentSchema;
import com.sinosoft.schema.ZCCommentSet;
import com.sinosoft.schema.ZDColumnValueSchema;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 处理评测评论分页获取数据
 * @author zhangjinquan 11180
 *
 *
 *
 */
@ParentPackage("shop")
public class EvalCommentAction extends BaseShopAction
{
	private static final long serialVersionUID = -4628223182118376233L;
	private String RelaID = null;
	private String CatalogID = null;
	private String CatalogType = null;
	private String SiteID = null;
	private String cmntPageIndex = null;
	private String cmntPageSize = null;
	private String cmntPageNum = null;
	private String title = null;
	private String cmntContent = null;
	private String VerifyCode = null;

	public String getRelaID() {
		return RelaID;
	}

	public void setRelaID(String relaID) {
		RelaID = relaID;
	}

	public String getCatalogID() {
		return CatalogID;
	}

	public void setCatalogID(String catalogID) {
		CatalogID = catalogID;
	}

	public String getCatalogType() {
		return CatalogType;
	}

	public void setCatalogType(String catalogType) {
		CatalogType = catalogType;
	}

	public String getSiteID() {
		return SiteID;
	}

	public void setSiteID(String siteID) {
		SiteID = siteID;
	}

	public String getCmntPageIndex() {
		return cmntPageIndex;
	}

	public void setCmntPageIndex(String cmntPageIndex) {
		this.cmntPageIndex = cmntPageIndex;
	}

	public String getCmntPageSize() {
		return cmntPageSize;
	}

	public void setCmntPageSize(String cmntPageSize) {
		this.cmntPageSize = cmntPageSize;
	}

	public String getCmntPageNum() {
		return cmntPageNum;
	}

	public void setCmntPageNum(String cmntPageNum) {
		this.cmntPageNum = cmntPageNum;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCmntContent() {
		return cmntContent;
	}

	public void setCmntContent(String cmntContent) {
		this.cmntContent = cmntContent;
	}

	public String getVerifyCode() {
		return VerifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		VerifyCode = verifyCode;
	}

	public String ajaxGetPageBar() {
		int pageIndex = Integer.valueOf(getParameter("pageIndex"));
		int total = Integer.valueOf(getParameter("total"));
		int pagesize = Integer.valueOf(getParameter("pagesize"));
		
		return ajaxJsonSuccessMessage(CommentService.getPageBar(total,pagesize,pageIndex));
	}

	/**
	 * 获取当前页的评论内容
	 *
	 * @return
	 * @throws Exception
	 */
	public String ajaxGetPage() throws Exception
	{
		Map<String, String> jsonMap = new HashMap<String, String>();

		QueryBuilder qb = new QueryBuilder();

		// 获取当前用户对象信息
		Member member = getLoginMember();
		String memberId = null;
		if (member != null) {
			memberId = member.getId();
		}

		// 根据文章ID，取得评论总数
		String sqlCount = "select count(1) from zccomment where relaid=? and verifyflag='Y'";
		qb.setSQL(sqlCount);
		qb.add(Long.parseLong(RelaID));
		int sumCount = qb.executeInt();  // 总评论数
		int pageSize = Integer.parseInt(this.cmntPageSize); // 一页显示评论数
		int pageCount = new Double(Math.ceil(sumCount * 1.0 / pageSize)).intValue(); // 总页数
		int pageIndex = Integer.parseInt(cmntPageIndex); // 指定显示第几页数据

		File file = new File(super.getApplication().getRealPath("Services/EvalCommentListPage.jsp"));
		String text = FileUtil.readText(file);
		
		String loopBegin = "<!-- comment loop begin -->";
		String loopEnd = "<!-- comment loop end -->";
		String loop = text.substring(text.indexOf(loopBegin) + loopBegin.length(), text.indexOf(loopEnd));
		StringBuffer loopsb = new StringBuffer();


		// 取得用户IP
		String ip = getRequest().getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = getRequest().getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = getRequest().getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = getRequest().getRemoteAddr();
		}

		// 根据当前用户登录的IP，查看被点赞的评论ID
		Map<String, String> mapPraised = new HashMap<String, String>();
		if (StringUtil.isNotEmpty(ip)) {
			DataTable dt = new QueryBuilder("select commentId from CommentPraisedInfo where RelaID=? and isPraised = 'Y' and userIP = ?", Long.parseLong(RelaID), ip).executeDataTable();
			if (dt != null && dt.getRowCount() > 0) {
				int rowCount = dt.getRowCount();
				for (int i = 0; i < rowCount; i++) {
					mapPraised.put(dt.getString(i, 0), "Y");
				}
			}
		}

		String sql = "select AddUser,AddUserIP,DATE_FORMAT(AddTime,'%Y-%m-%d %H:%i') as 'AddTime',Content, zccomment.relaid, ReplyContent, '' as replyStyle, '' as StaticServerContext, ";
		sql += " if(zccomment.praisedNum is null, 0, zccomment.praisedNum) praisedNum, '' as praiseClasss, '' as hot_mes, zccomment.ID,a.headPicPath, 'none' as delStyle, zccomment.prop1 as memberId ";
		sql += " from zccomment left join member a on zccomment.prop1=a.id  where zccomment.relaid=? and verifyflag='Y'";

		// 点赞数最多的三个评论
		String sql1 = sql + " order by zccomment.praisedNum desc, zccomment.AddTime desc limit 0, 3 ";
		DataTable dt1 = new QueryBuilder(sql1, Long.parseLong(RelaID)).executeDataTable();
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
				
		String orderby = " order by zccomment.AddTime desc, zccomment.praisedNum desc limit ?, ? ";
		qb = new QueryBuilder(sql + orderby, Long.parseLong(RelaID));
		// 如何是第一页数据
		if (pageIndex == 1) {
			qb.add(0);
			qb.add(pageSize - topRaisedCount);
			pageSize = pageSize - topRaisedCount;
		}
		// 如果非第一页
		else {
			// 如果指定页数大于最大页数，删除的评论时候会发生这种情况
			if (pageIndex > pageCount) {
				pageIndex = pageCount;
			}
			qb.add((pageIndex - 1) * pageSize - topRaisedCount);
			qb.add(pageSize);
		}

		DataTable dt = qb.executeDataTable();

		// 如果是第一页，加载置顶评论
		if (pageIndex == 1 && dt1 != null) {
			if (dt != null) {
				for (int i = 0; i < topRaisedCount; i++) {
					dt.insertRow(dt1.get(i), i);
				}
			} else {
				dt = dt1;
			}
		}
		// 添加第一页的评论数据
		for (int i = 0; i < dt.getRowCount(); i++) {
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

			// 评论者名称处显示
			String addUser = dt.getString(i, "AddUser");
			// 没有登录的评论用户名显示
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
		jsonMap.put(STATUS, SUCCESS);
		jsonMap.put(MESSAGE, loopsb.toString());
		jsonMap.put("pageBarHtml", getPageBar(Integer.parseInt(cmntPageIndex), pageCount));
		return ajaxJson(jsonMap);
	}

	/**
	 * 添加翻页工具栏
	 *
	 * @param pageIndex 指定页数
	 * @param pageCount 最大页数
	 * @return
	 */
	public String getPageBar(int pageIndex, int pageCount) {
		
		if (pageIndex < 1 || pageIndex > pageCount) {
			return "";
		}
		List<Map<String, String>> pageList = PageDataList(pageCount, pageIndex - 1);
		if (pageList != null && pageList.size() > 0) {
			StringBuffer sb = new StringBuffer();
			sb.append("<ul><li class='page_prev'>");
			if (pageIndex == 1) {
				sb.append("<a href='javascript:void(0);'><span class='default'>上一页</span></a>");
			} else {
				sb.append("<a href='javascript:getPlPage(" + (pageIndex - 1)
						+ ");'><span>上一页</span></a>");
			}
			sb.append("</li>");

			for (int i = 0; i < pageList.size(); i++) {
				sb.append("<li class='" + pageList.get(i).get("class")+"'>");
				if ("...".equals(pageList.get(i).get("index"))) {
					sb.append("<a href='javascript:void(0);'>");
				} else {
					sb.append("<a href='javascript:getPlPage("+ pageList.get(i).get("index") + ");'>");
				}
				sb.append("<span>" + pageList.get(i).get("index") + "</span></a></li>");
			}
			sb.append("<li class='page_next'>");
			if (pageIndex == pageCount) {
				sb.append("<a href='javascript:void(0);'><span class='default'>下一页</span></a>");
			} else {
				sb.append("<a href='javascript:getPlPage(" + (pageIndex + 1)
						+ ");'><span>下一页</span></a>");
			}
			sb.append("</li></ul>");
			return sb.toString();
		}

		return "";
	}

	/**
	 * 评论提交
	 *
	 * @return
	 */
	public String subComment() {

		Map<String, String> result = new HashMap<String, String>();
		// 评论内容
		String content = getParameter("comContent");
		// 文章ID
		String relaID = getParameter("relaID");
		// siteID
		String siteID = getParameter("siteID");
		// catalogID
		String catalogID = getParameter("catalogID");
		// catalogType
		String catalogType = getParameter("catalogType");
		// title
		String title = getParameter("title");
		// 评论ID
		long commentId = NoUtil.getMaxID("CommentID");

		// 获取当前用户对象信息
		Member member = getLoginMember();
		String memberId = null;
		String addUser = "游客";
		if (member != null) {
			addUser = StringUtil.isEmpty(member.getMobileNO()) ? member.getEmail() : member.getMobileNO();
			memberId = member.getId();
		}

		// 取得ip地址
		String ip = getRequest().getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = getRequest().getHeader("Proxy-Client-IP");
			if (ip == null || ip.length() == 0
					|| "unknown".equalsIgnoreCase(ip)) {
				ip = getRequest().getHeader("WL-Proxy-Client-IP");
				if (ip == null || ip.length() == 0
						|| "unknown".equalsIgnoreCase(ip)) {
					ip = getRequest().getRemoteAddr();
				}
			}
		}

		ZCCommentSchema comment = new ZCCommentSchema();

		comment.setID(commentId);
		comment.setCatalogID(catalogID);
		comment.setCatalogType(catalogType);
		comment.setRelaID(relaID);
		comment.setSiteID(siteID);
		comment.setTitle(title);
		comment.setContent(StringUtil.htmlEncode(StringUtil.subStringEx(
				content, 900)));
		comment.setAddUser(addUser);
		comment.setProp1(memberId);
		comment.setProp2(content);
		comment.setAddTime(new Date());
		comment.setAddUserIP(ip);
		comment.setVerifyFlag("X");

		try {
			if (comment.insert()) {
				result.put(STATUS, SUCCESS);
				return ajaxJson(result);
			}
		} catch (Exception e) {
			result.put(STATUS, ERROR);
			logger.error(e.getMessage(), e);
		}

		return ajaxJson(result);
	}

	/**
	 * 点赞处理
	 *
	 * @return
	 */
	public String dealPraised() {
		HashMap<String, String> map = new HashMap<String, String>();
		HttpServletRequest request = getRequest();
		String commentId = request.getParameter("commentId");
		String flag = request.getParameter("flag");

		// 获得当前IP
		String ip = getRequest().getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = getRequest().getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = getRequest().getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = getRequest().getRemoteAddr();
		}
		
		if (StringUtil.isEmpty(ip)) {
			map.put(STATUS, ERROR);
			map.put(MESSAGE, "未获取您的IP地址哟");
			return ajaxJson(map);
		}
		
		ZCCommentSchema zcComment = new ZCCommentSchema();
		zcComment.setID(commentId);
		if (zcComment.fill()) {
			// 获取评论点赞数
			int praisedNum = zcComment.getpraisedNum();
			Transaction transaction = new Transaction();
			Date now = new Date();
			// 用IP判断用户是否点赞过
			DataTable dt = new QueryBuilder("select id, isPraised from CommentPraisedInfo where commentId = ? and userIP = ?", commentId, ip).executeDataTable();
			if (dt != null && dt.getRowCount() > 0) {
				// 点赞
				if ("P".equals(flag)) {
					transaction.add(new QueryBuilder("update CommentPraisedInfo set isPraised = 'Y', praisedDate = ? where id = ?", now, dt.getString(0, 0)));
					praisedNum = praisedNum + 1;
				} else {
					// 取消点赞
					transaction.add(new QueryBuilder("update CommentPraisedInfo set isPraised = 'N', cancelDate = ? where id = ?", now, dt.getString(0, 0)));
					praisedNum = praisedNum - 1;
				}
				
			} else {
				// 新增一条点赞数据
				if ("P".equals(flag)) {
					CommentPraisedInfoSchema schema = new CommentPraisedInfoSchema();
					schema.setid(NoUtil.getMaxNo("CommentPraisedID", 15));
					schema.setcommentId(commentId);
					schema.setRelaID(zcComment.getRelaID());
					schema.setuserIP(ip);
					schema.setisPraised("Y");
					schema.setpraisedDate(now);
					schema.setCreateUser("system");
					schema.setCreateDate(now);
					transaction.add(schema, Transaction.INSERT);

					praisedNum = praisedNum + 1;
				}
			}
			zcComment.setpraisedNum(praisedNum);
			transaction.add(zcComment, Transaction.UPDATE);
			if (transaction.commit()) {
				map.put(STATUS, SUCCESS);
				map.put(MESSAGE, praisedNum+"");
			} else {
				map.put(STATUS, ERROR);
				map.put(MESSAGE, "提交数据失败");
			}
			
		} else {
			map.put(STATUS, ERROR);
			map.put(MESSAGE, "未获取该评论信息，不能点赞哦");
			return ajaxJson(map);
		}
		return ajaxJson(map);
	}
	
	/**
	 * 评测删除评论
	 */
	public String delComment() {
		HashMap<String, String> map = new HashMap<String, String>();
		String commentId = getParameter("commentId");

		Transaction trans = new Transaction();
		ZCCommentSchema task = new ZCCommentSchema();
		ZCCommentSet set = task.query(new QueryBuilder("where id = '"+ commentId +"'"));
		trans.add(set, Transaction.DELETE);
		if (trans.commit()) {
			map.put(STATUS, SUCCESS);
		} else {
			map.put(STATUS, ERROR);
		}
		return ajaxJson(map);
	}

	/**
	 * 获取评论数
	 */
	public String getCommentNum() {
		Map<String, String> map = new HashMap<String, String>();
		long relaId = Long.valueOf(getParameter("relaID"));

		String sql = "select count(*) from zccomment where relaid=?";
		QueryBuilder queryBuilder = new QueryBuilder(sql,relaId);
		int commentNum = queryBuilder.executeInt();

		map.put(STATUS, SUCCESS);
		map.put(MESSAGE, String.valueOf(commentNum));

		return ajaxJson(map);
	}

	/**
	 * 获取阅读数
	 */
	public String autoReadCount() {
		Map<String, String> map = new HashMap<String, String>();
		long relaId = Long.valueOf(getParameter("relaID"));

		String sql = "select id,columnid,textvalue from zdcolumnvalue where columncode='readCount' and relaid=?";
		QueryBuilder queryBuilder = new QueryBuilder(sql,relaId);
		DataTable dt = queryBuilder.executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			String id = dt.getString(0, "id");
			String columnid = dt.getString(0, "columnid");
			int readCount = dt.getInt(0, "textvalue");

			Transaction trans = new Transaction();
			ZDColumnValueSchema schema = new ZDColumnValueSchema();
			schema.setID(id);
			schema.setColumnID(columnid);
			schema.setRelaID(String.valueOf(relaId));
			schema.setColumnCode("readCount");
			schema.setTextValue(String.valueOf(readCount + 1));
			schema.setRelaType("2");
			trans.add(schema, Transaction.UPDATE);
			if (trans.commit()) {
				map.put(STATUS, SUCCESS);
				map.put(MESSAGE, String.valueOf(readCount+1));
			} else {
				map.put(STATUS, ERROR);
				map.put(MESSAGE, "提交数据失败");
			}
		} else {
			map.put(STATUS, ERROR);
			map.put(MESSAGE, "提交数据失败");
		}

		return ajaxJson(map);
	}
}
