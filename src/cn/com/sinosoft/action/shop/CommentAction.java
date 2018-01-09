package cn.com.sinosoft.action.shop;

import cn.com.sinosoft.entity.Member;
import com.sinosoft.cms.dataservice.CommentService;
import com.sinosoft.cms.pub.CMSCache;
import com.sinosoft.cms.pub.SiteUtil;
import com.sinosoft.cms.site.BadWord;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.LogUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.inter.MailAction;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.points.IntegralConstant;
import com.sinosoft.points.PointsCalculate;
import com.sinosoft.schema.CommentPraisedInfoSchema;
import com.sinosoft.schema.SDOrdersSchema;
import com.sinosoft.schema.SDOrdersSet;
import com.sinosoft.schema.ZCCatalogConfigSchema;
import com.sinosoft.schema.ZCCommentSchema;
import com.sinosoft.schema.memberSchema;
import com.sinosoft.schema.memberSet;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 处理评论分页获取数据 
 * @author zhangjinquan 11180
 *
 *
 *
 */
@ParentPackage("shop")
public class CommentAction extends BaseShopAction
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

	//  start 百年康惠保评论区改版 临时区别使用，将来统一可以删除
	private String ProductID = null;
	public String getProductID() {
		return ProductID;
	}

	public void setProductID(String productID) {
		ProductID = productID;
	}
	//  end 百年康惠保评论区改版 临时区别使用，将来统一可以删除

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
	
	public String ajaxGetPage() throws Exception
	{
		Map<String, String> jsonMap = new HashMap<String, String>();
		boolean commentFlag = SiteUtil.getCommentAuditFlag(SiteID);
		String WherePart = "";
		if (commentFlag)
		{
			WherePart = " and verifyflag='Y'"; //评论需要审核
		}
		String url = "";
		File file = new File(super.getApplication().getRealPath("Services/CommentListPage.jsp"));
		String text = FileUtil.readText(file);

		String loopBegin = "<!-- comment loop begin -->";
		String loop = text.substring(text.indexOf(loopBegin) + loopBegin.length(), text.indexOf("<!-- comment loop end -->"));
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
		
		String sql = "select AddUser,DATE_FORMAT(AddTime,'%Y-%m-%d %H:%i') as 'AddTime',Content, zccomment.relaid, ReplyContent,'' as borderClass, "
				+ "'' as style , 'none' as recommflag, score, if(zccomment.praisedNum is null, 0, zccomment.praisedNum) praisedNum, "
				+ "'' as praiseClasss,zccomment.ID, 'false' as praiseAria, IFNULL(Purpose, '') as Purpose, a.vipFlag, a.grade, zccomment.memGrade "
				+ "from zccomment left join member a on zccomment.prop1=a.id where relaid=? and zccomment.isBuy = '1' "+ WherePart;
				
		String orderby = " order by zccomment.AddTime desc, zccomment.praisedNum desc limit ?, ? ";
		if (StringUtil.isNotEmpty(cmntPageSize)&&Integer.parseInt(cmntPageSize)>0)
		{
			int pageIndex = Integer.parseInt(cmntPageIndex)-1;
			int pageSize = Integer.parseInt(this.cmntPageSize);
			int rowcount = 0;
			// 点赞数最多的三个评论
			String sql1 = sql + " order by zccomment.praisedNum desc, zccomment.AddTime desc limit 0, 3 ";
			DataTable dt1 = new QueryBuilder(sql1, Long.parseLong(RelaID)).executeDataTable();
			String commIDs = "";
			if (dt1 != null && dt1.getRowCount() > 0) {
				rowcount = dt1.getRowCount();
				for (int i = 0; i < rowcount; i++) {
					dt1.set(i, "recommflag", "");
					commIDs += (",'" + dt1.getString(i, "ID")+"'");
				}
				commIDs = commIDs.substring(1);
				sql += " and zccomment.ID not in ("+commIDs+") ";
				
			}
			
			QueryBuilder qb = new QueryBuilder(sql + orderby, Long.parseLong(RelaID));
			if (pageIndex == 0) {
				qb.add(0);
				qb.add(pageSize - rowcount);
				pageSize = pageSize - rowcount;
			} else {
				qb.add((pageSize - rowcount) + (pageIndex - 1) * pageSize);
				qb.add(pageSize);
			}
			
			DataTable dt = qb.executeDataTable();
			if (pageIndex == 0 && dt1 != null) {
				if (dt != null) {
					for (int i = 0; i < rowcount; i++) {
						dt.insertRow(dt1.get(i), i);
					}
					
				} else {
					dt = dt1;
				}
			}
			String grade = "";
			String memGrade = "";
			int count = dt.getRowCount();
			for (int i = 0; i < count; i++)
			{
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
				if (mapPraised.containsKey(dt.getString(i, "ID"))) {
					dt.set(i, "praiseClasss", "pressed");
					dt.set(i, "praiseAria", "true");
					
				}
				if (StringUtil.isEmpty(dt.getString(i, "ReplyContent")))
				{
					dt.set(i, "style", "none");
				}
				grade = dt.getString(i, "grade");
				memGrade = dt.getString(i, "memGrade");
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
				if ((i+1) == count) {
					dt.set(i, "borderClass", "clear_btn_bor");
				}
				loopsb.append(HtmlUtil.replacePlaceHolder(loop, dt.getDataRow(i).toMapx(), true));
			}
		}
		jsonMap.put(STATUS, SUCCESS);
		jsonMap.put(MESSAGE, (loopsb.toString()));
		jsonMap.put("pageBarHtml", getPageBar(Integer.parseInt(cmntPageIndex), Integer.parseInt(cmntPageNum)));
		return ajaxJson(jsonMap);
	}

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
	
	
	public String commentSubmit() {
		HashMap<String, String> map = new HashMap<String, String>();
		// 订单号
		String orderSn = getParameter("orderSn");
		// 保障范围评分
		String coverageScore = getParameter("coverageScore");
		// 评论内容
		String content = getParameter("content");
		// 售后服务评分
		String clientScore = getParameter("clientScore");
		// 保障程度评分
		String describeScore = getParameter("describeScore");
		// 保障价格评分
		String policyScore = getParameter("policyScore");
		// 投保目的
		String purpose = getParameter("purpose");
		// 评论来源
		String source = getParameter("source");
		
		HttpServletRequest request = getRequest();
		// 会员ID
		String id = "";
		
		if (StringUtil.isEmpty(orderSn)) {
			map.put(STATUS, ERROR);
			map.put(MESSAGE, "矮油，订单出了点小状况，很快就好了，过一会儿再试试！");
			return ajaxJson(map);
		}
		
		// 取得订单信息
		SDOrdersSchema sdordersSchema = new SDOrdersSchema();
		sdordersSchema.setorderSn(orderSn);
		SDOrdersSet sdordersSets = sdordersSchema.query();
		if (null == sdordersSets || 0 == sdordersSets.size()) {
			map.put(STATUS, ERROR);
			map.put(MESSAGE, "系统中没您要评价的订单哟！请联系客服！");
			return ajaxJson(map);
		}
		sdordersSchema = sdordersSets.get(0);
		
		if ("pay".equals(source)) {
			if (StringUtil.isEmpty(sdordersSchema.getmemberId())) {
				map.put(STATUS, ERROR);
				map.put(MESSAGE, "订单未关联会员，请进入会员中心进行评价！");
				return ajaxJson(map);
			}
			id = sdordersSchema.getmemberId();
		} else {
			// 当前登录会员ID
			id = (String) request.getSession().getAttribute(
					Member.LOGIN_MEMBER_ID_SESSION_NAME);
			if (StringUtil.isEmpty(id)) {
				map.put(STATUS, ERROR);
				map.put(MESSAGE, "非会员，不能评论哦。请先注册、登录吧！");
				return ajaxJson(map);
			}
		}
		
		if (StringUtil.isNotEmpty(sdordersSchema.getchannelsn()) && sdordersSchema.getchannelsn().indexOf("jfsc") > -1) {
			map.put(STATUS, ERROR);
			map.put(MESSAGE, "积分兑换产品不能评价！");
			return ajaxJson(map);
		}
		memberSchema memberSchemas = new memberSchema();
		memberSchemas.setid(id);
		memberSet memberSets = memberSchemas.query();
		if ((null == memberSets) || (0 == memberSets.size())) {
			map.put(STATUS, ERROR);
			map.put(MESSAGE, "非会员，不能评论哦。请先注册、登录吧！");
			return ajaxJson(map);
		}

		memberSchema memberSchemax = memberSets.get(0);
		
		// 校验星级评分
		if (StringUtil.isEmpty(coverageScore) || StringUtil.isEmpty(clientScore) || StringUtil.isEmpty(describeScore) || StringUtil.isEmpty(policyScore)) {
			map.put(STATUS, ERROR);
			map.put(MESSAGE, "您的评分是我们前进的动力！");
			return ajaxJson(map);
		}
		// 计算评分满意程度=（保障范围评分+售后服务评分+保障程度评分+保障价格评分）除4 四舍五入取整
		int sumScore = Integer.valueOf(coverageScore)+Integer.valueOf(clientScore)+Integer.valueOf(describeScore)+Integer.valueOf(policyScore);
		String score = new BigDecimal(sumScore).divide(new BigDecimal(4), 0, BigDecimal.ROUND_HALF_UP).toString();
		
		// 校验评论内容不能少于10个字
		if (StringUtil.isEmpty(content)
				|| StringUtil.getByteLength(content) < 20) {
			map.put(STATUS, ERROR);
			map.put(MESSAGE, "您需要填写10个以上的字哦！");
			return ajaxJson(map);
		}
		// 校验评论内容字数不超过500字
		if (StringUtil.getByteLength(content) > 1000) {
			map.put(STATUS, ERROR);
			map.put(MESSAGE, "字数超了～评论的内容只能写500个字以内哟！");
			return ajaxJson(map);
		}
		
		// 校验订单是否已评价
		if (sdordersSchema.getcommentId() != 0) {
			map.put(STATUS, ERROR);
			map.put(MESSAGE, "您已经评价过了哦！");
			return ajaxJson(map);
		}

		// 撤销的订单不能评价
		if ("9".equals(sdordersSchema.getorderStatus())) {
			map.put(STATUS, ERROR);
			map.put(MESSAGE, "您已经撤销订单，不能评价了哦！");
			return ajaxJson(map);
		}

		// 未支付的订单不能评价
		if ("5".equals(sdordersSchema.getorderStatus())
				|| "4".equals(sdordersSchema.getorderStatus())) {
			map.put(STATUS, ERROR);
			map.put(MESSAGE, "您还未支付，不能评价哦！");
			return ajaxJson(map);
		}

		// 取得会员名
		String user = memberSchemax.getusername();
		if (StringUtil.isEmpty(user)) {
			user = memberSchemax.getmobileNO();
			if (StringUtil.isEmpty(user)) {
				user = memberSchemax.getemail();
			}
		}

		// 取得ip地址
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
			if (ip == null || ip.length() == 0
					|| "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
				if (ip == null || ip.length() == 0
						|| "unknown".equalsIgnoreCase(ip)) {
					ip = request.getRemoteAddr();
				}
			}
		}
		String title = "无";

		// 取得产品相关信息
		String sql = "select b.CatalogID, b.ID, b.SiteID, e.type,a.productId "
				+ "from sdinformation a, zcarticle b, zdcolumnvalue d, zccatalog e "
				+ "where a.orderSn = ? and d.columncode = 'RiskCode' and d.textValue = a.productId "
				+ "and d.RelaID = b.ID and b.cataloginnercode like '002313%' and b.type = '1' "
				+ "and e.ID = b.CatalogID limit 1 ";
		QueryBuilder qb = new QueryBuilder(sql);
		qb.add(orderSn);
		DataTable dt = qb.executeDataTable();
		if (dt == null || dt.getRowCount() == 0) {
			map.put(STATUS, ERROR);
			map.put(MESSAGE, "您评价的产品有点问题，不能评价哦，如有问题请联系客服哦！");
			return ajaxJson(map);
		}
		// 栏目ID
		String catalogID = dt.get(0).getString("CatalogID");
		ZCCatalogConfigSchema catalogConfig = CMSCache
				.getCatalogConfig(catalogID);

		// 产品id
		String productId = dt.get(0).getString("productId");
		// 栏目类型
		String catalogType = dt.get(0).getString("type");
		// 关联ID
		String relaID = dt.get(0).getString("ID");
		// 站点
		String siteID = dt.get(0).getString("SiteID");

		ZCCommentSchema comment = new ZCCommentSchema();
		// 评论ID
		long commentId = NoUtil.getMaxID("CommentID");
		comment.setID(commentId);
		comment.setCatalogID(catalogID);
		comment.setCatalogType(catalogType);
		comment.setRelaID(relaID);
		comment.setSiteID(siteID);
		comment.setTitle(StringUtil.htmlEncode(BadWord.filterBadWord(StringUtil
				.subStringEx(title, 90))));
		comment.setContent(StringUtil.htmlEncode(StringUtil.subStringEx(
				content, 900)));
		comment.setAddUser(user);
		comment.setProp1(id);
		comment.setProp2(content);
		comment.setAddTime(new Date());
		comment.setAddUserIP(ip);
		// 评价
		comment.setScore(score);
		// 星级评分
		comment.setDescribeScore(describeScore);
		comment.setPolicyScore(policyScore);
		comment.setClientScore(clientScore);
		comment.setCoverageScore(coverageScore);
		comment.setPurpose(purpose);
		// 是否购买 1:已购买
		comment.setIsBuy("1");

		String verify = "";
		if (catalogConfig != null) {
			// 取得评论需审核的设置
			verify = catalogConfig.getCommentVerify();
		}

		// 需审核的情况
		if ("Y".equals(verify)) {
			comment.setVerifyFlag("X");

			// 校验敏感词
		} else if ((StringUtil.isNotEmpty(BadWord.checkBadWord(content,
				BadWord.TREELEVEL_1)))
				|| (StringUtil.isNotEmpty(BadWord.checkBadWord(content,
						BadWord.TREELEVEL_2)))
				|| (StringUtil.isNotEmpty(BadWord.checkBadWord(content,
						BadWord.TREELEVEL_3)))) {
			comment.setVerifyFlag("X");

		} else {
			comment.setVerifyFlag("Y");
		}

		// 成功标示
		boolean success = false;
		try {
			success = comment.insert();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			success = false;
		}

		if (success) {
			// 更新订单的评论ID
			sdordersSchema.setcommentId(commentId);
			sdordersSchema.update();
			
			
			PointsCalculate PointsCalculate = new PointsCalculate();
			try {
				
				Map<String, Object> map1 = PointsCalculate.pointsManage(IntegralConstant.POINT_SEARCH,
						IntegralConstant.POINT_SOURCE_COMMENT, null);
				map.put("Points", String.valueOf(map1.get(IntegralConstant.ACTION_POINTS)));
				
			} catch(Exception e) {
				logger.error(e.getMessage(), e);
			}
			
			
			Map<String, Object> data = new HashMap<String, Object>();
			data.put(IntegralConstant.MEM_ID, id);
			data.put(IntegralConstant.BUSINESS_ID, orderSn);
			try {
				Map<String, Object> resultMap = PointsCalculate.pointsManage(IntegralConstant.POINT_SEND,
						IntegralConstant.POINT_SOURCE_COMMENT, data);
				if (IntegralConstant.FAIL.equals(resultMap.get(IntegralConstant.STATUS))) {
					logger.error("评论商品获得积分处理失败！会员id:{}、订单号：{}", id, orderSn);
				}
			} catch (Exception e) {
				logger.error("评论商品获得积分处理异常！会员id:" + id + "、订单号：" + orderSn + e.getMessage(), e);
			}
			
			MailAction.deleteCommentMail(orderSn);
			
			map.put(STATUS, SUCCESS);
			return ajaxJson(map);
			
		} else {
			map.put(STATUS, ERROR);
			map.put(MESSAGE, "矮油，出了点小状况，很快就好了，过一会儿再试试！");
			return ajaxJson(map);
		}
	}
	
	public String submit()
	{
		HashMap<String, String> map = new HashMap<String, String>();
		HttpServletRequest request = getRequest();
		String id = (String) request.getSession().getAttribute(Member.LOGIN_MEMBER_ID_SESSION_NAME);
		if (StringUtil.isEmpty(id))
		{
			map.put(STATUS, ERROR);
			map.put("msgtype", "5");
			map.put(MESSAGE, "非会员，不能评论哦。请先注册、登录吧！");
			return ajaxJson(map);
		}

	    memberSchema memberSchemas = new memberSchema();
		memberSchemas.setid(id);
		memberSet memberSets= memberSchemas.query();
		if ((null == memberSets) || (0 == memberSets.size()))
		{
			map.put(STATUS, ERROR);
			map.put("msgtype", "5");
			map.put(MESSAGE, "非会员，不能评论哦。请先注册、登录吧！");
			return ajaxJson(map);
		}
		
		//Object authCode = User.getValue(Constant.DefaultAuthKey);
//		Object authCode = getRequest().getSession().getAttribute("CommentYZM");
//     	if ((authCode == null) || !authCode.equals(VerifyCode))
//     	{
//			map.put(STATUS, ERROR);
//			map.put("msgtype", "2");
//			map.put(MESSAGE, "验证码没有写对哦！");
//			return ajaxJson(map);
//		}
     	
		String content = cmntContent;
		if (StringUtil.isEmpty(content) || StringUtil.getByteLength(content) < 20)
		{
			map.put(STATUS, ERROR);
			map.put("msgtype", "1");
			map.put(MESSAGE, "内容需要填写10个以上的字哦！");
			return ajaxJson(map);
		}
		
		if (StringUtil.getByteLength(content) > 1000)
		{
			map.put(STATUS, ERROR);
			map.put("msgtype", "5");
			map.put(MESSAGE, "字数超了～评论的内容只能写500个字以内哟！");
			return ajaxJson(map);
		}
		
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getHeader("Proxy-Client-IP");
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
			{
				ip = request.getHeader("WL-Proxy-Client-IP");
				if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
				{
					ip = request.getRemoteAddr();
				}
			}
		}
		
	    String[] str = ip.split(",");
	    if(str!=null && str.length>1)
	    {
	    	ip = str[0];
	    }
		
//		String anonymous = request.getParameter("CmntCheckbox");
		String user = null;
		// 评分
		String score = request.getParameter("score");
		// 客户服务评分
		String clientScore = request.getParameter("stars3_input");
		// 产品描述评分
		String describeScore = request.getParameter("stars1_input");
		// 保单服务评分
		String policyScore = request.getParameter("stars2_input");
		// 是否购买
		String isBuy = request.getParameter("isBuy");
		
		memberSchema memberSchemax = memberSets.get(0);
//     	String email = memberSchemax.getemail();

//     	if(StringUtil.isEmpty(email) || !email.endsWith("@kaixinbao.com"))
// 		{
//     		String sql = "select 1 from sdorders s1,SDInformation s2 where s1.ordersn=s2.orderSn  and s1.memberid=? and exists (select 1 from zcarticle z1,zdcolumnvalue z2 where z1.id=z2.relaid and columncode='RiskCode' and  z1.id= ? and z2.textvalue = s2.productid) and s1.orderstatus = '7' limit 1";
//     		QueryBuilder qb = new QueryBuilder(sql);
//     		qb.add(id);
//     		qb.add(RelaID);
//     		if(!"1".equals(qb.executeString()))
//     		{
//     			map.put(STATUS, ERROR);
//    			map.put("msgtype", "3");
//    			map.put(MESSAGE, "有体验更有发言权，请先购买吧，不了解可以咨询客服MM！");
//    			return ajaxJson(map);
//     		}
// 		}
		
		String orderid = "";
		// 购买评价的情况
     	if ("1".equals(isBuy)) {
     		if (StringUtil.isEmpty(score)) {
     			map.put(STATUS, ERROR);
    			map.put("msgtype", "9");
    			map.put(MESSAGE, "没有评分哦！");
    			return ajaxJson(map);
     		}
    		// 校验是否有未评价的订单
    		String sql = "select s1.id from sdorders s1,SDInformation s2,sdsearchrelaproduct c "
    				+ "where s1.ordersn=s2.orderSn and s1.memberid=? and c.Prop1=? "
    				+ "and c.ProductID = s2.productid and s1.orderstatus in ('7','10') "
    				+ "and s1.commentId is null order by s1.modifyDate desc limit 1";
    		
    		QueryBuilder qb = new QueryBuilder(sql);
    		qb.add(id);
    		qb.add(RelaID);
    		orderid = qb.executeString();
    		if (StringUtil.isEmpty(orderid)) {
    			map.put(STATUS, ERROR);
    			map.put("msgtype", "3");
    			map.put(MESSAGE, "有体验更有发言权，请先购买吧，不了解可以咨询客服MM！");
    			return ajaxJson(map);
    		}
    		// 咨询的情况
     	} else {
     		isBuy = "0";
     	}
 		
     	ZCCatalogConfigSchema catalogConfig = CMSCache.getCatalogConfig(CatalogID);
//     	if ("on".equals(anonymous))
//     	{
//			user = "匿名用户";
//			if ((null!=catalogConfig) && ("Y".equalsIgnoreCase(catalogConfig.getCommentAnonymous())))
//			{
//				map.put(STATUS, ERROR);
//    			map.put("msgtype", "7");
//    			map.put(MESSAGE, "做好事怎么能不留名呢，请您不要匿名评论！");
//    			return ajaxJson(map);
//			}
//		}
//     	else
//		{
		user = memberSchemax.getusername();
		if (StringUtil.isEmpty(user)) {
			user = memberSchemax.getmobileNO();
			if (StringUtil.isEmpty(user)) {
				user = memberSchemax.getemail();
			}
		}
//		}
        
		if (StringUtil.isEmpty(title))
		{
			title = "无";
		}
		
		ZCCommentSchema comment = new ZCCommentSchema();
		long commentId = NoUtil.getMaxID("CommentID");
		comment.setID(commentId);
		comment.setCatalogID(CatalogID);
		comment.setCatalogType(CatalogType);
		comment.setRelaID(RelaID);
		comment.setSiteID(SiteID);
		comment.setTitle(StringUtil.htmlEncode(BadWord.filterBadWord(StringUtil.subStringEx(title,90))));
		comment.setContent(StringUtil.htmlEncode(StringUtil.subStringEx(content,900)));
		comment.setAddUser(user);
		//===吴高强添加====
		comment.setProp1(id);
		comment.setProp2(content);
		//===吴高强添加====
		comment.setAddTime(new Date());
		comment.setAddUserIP(ip);
		// 评分
		comment.setScore(score);
		comment.setDescribeScore(describeScore);
		comment.setPolicyScore(policyScore);
		comment.setClientScore(clientScore);
		// 是否购买
		comment.setIsBuy(isBuy);
		
		String verify = "";
		if (catalogConfig != null) {
			// 已购买的情况、取得评论需审核的设置
			if ("1".equals(isBuy)) {
				verify = catalogConfig.getCommentVerify();
				
				// 未购买的情况、取得咨询需审核的设置
			} else {
				verify = catalogConfig.getConsultationVerify();
			}
		}

		// 需审核的情况
		if ("Y".equals(verify)) {
			comment.setVerifyFlag("X");

			// 校验敏感词
		} else if ((StringUtil.isNotEmpty(BadWord.checkBadWord(content,
				BadWord.TREELEVEL_1)))
				|| (StringUtil.isNotEmpty(BadWord.checkBadWord(content,
						BadWord.TREELEVEL_2)))
				|| (StringUtil.isNotEmpty(BadWord.checkBadWord(content,
						BadWord.TREELEVEL_3)))) {
			comment.setVerifyFlag("X");
			
		} else {
			comment.setVerifyFlag("Y");
		}
		
		boolean success = false;
		try
		{
			success = comment.insert();
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			success = false;
		}
		
		if (success)
		{
			map.put(STATUS, SUCCESS);
			map.put("msgtype", "4");
			StringBuffer message = new StringBuffer();
			// 购买评论
			if (StringUtil.isNotEmpty(orderid)) {
				// 订单关联评论id
				SDOrdersSchema sdOrdersSchema = new SDOrdersSchema();
				sdOrdersSchema.setid(orderid);
				if (sdOrdersSchema.fill()) {
					sdOrdersSchema.setcommentId(commentId);
					sdOrdersSchema.update();
				}
				// 查询评论送积分的积分数值
				String sql = "select points from zdPointInfo where PointsType='01' limit 1 ";
				QueryBuilder qb = new QueryBuilder(sql);
				String points = qb.executeString();
				int intPoints = 0;
				if (StringUtil.isNotEmpty(points) && !"null".equals(points)) {
					try {
						intPoints = Integer.parseInt(points.trim());
					} catch (NumberFormatException e) {
						intPoints = 0;
					}
				}
				
				// 不送积分的提示
				if (intPoints <= 0) {
					message.append("您的评论已成功提交，感谢对开心保的支持与信任！");

					// 送积分的处理
				} else {
					message.append("您的评论已成功提交，感谢对开心保的支持与信任，<br>已将"+intPoints+"积分发送至您的账号，可前往会员中心进行查询！");
					Map<String, Object> data = new HashMap<String, Object>();
					Member member=new Member();
					member.setId(id);
					data.put("Member", member);
					data.put("Points", intPoints);
					data.put("orderSn", sdOrdersSchema.getorderSn());
					ActionUtil.dealAction("wj00042", data, request);
				}
				
			} else {
				message.append("您的咨询已成功提交，客服mm会尽快给您答复，感谢对开心保的支持与信任！");
			}

//			SDExpCalendarSchema tSDExpCalendarSchema=new SDExpCalendarSchema();
//			SDExpCalendarSet tSDExpCalendarSet = tSDExpCalendarSchema.query(new QueryBuilder("where memberid ='"+id+"' "));
//			SDExpCalendarSchema tSDExpCalendarSchema1=tSDExpCalendarSet.get(tSDExpCalendarSet.size()-1);
//			String exp=tSDExpCalendarSchema1.getExperience();
//			long expVal = 0;
//			try
//			{
//				expVal = Long.parseLong(exp);
//			}
//			catch(Exception e) {}
			
			/*if(expVal>0)
			{
				message.append("恭喜您本次获得" + exp + "个开心值！");
			}*/
			map.put(MESSAGE, message.toString());
			return ajaxJson(map);
		}
		else
		{
			map.put(STATUS, ERROR);
			map.put("msgtype", "8");
			map.put(MESSAGE, "矮油，出了点小状况，很快就好了，过一会儿再试试！");
			return ajaxJson(map);
		}
	}
	
	public String dealPraised() {
		HashMap<String, String> map = new HashMap<String, String>();
		HttpServletRequest request = getRequest();
		String commentId = request.getParameter("commentId");
		String flag = request.getParameter("flag");
		String praisedNum = request.getParameter("praisedNum");
		
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
		
		ZCCommentSchema ZCComment = new ZCCommentSchema();
		ZCComment.setID(commentId);
		if (ZCComment.fill()) {
			Transaction transaction = new Transaction();
			Date now = new Date();
			// 用IP判断用户是否点赞过
			DataTable dt = new QueryBuilder("select id, isPraised from CommentPraisedInfo where commentId = ? and userIP = ?", commentId, ip).executeDataTable();
			if (dt != null && dt.getRowCount() > 0) {
				// 点赞
				if ("true".equals(flag)) {
					transaction.add(new QueryBuilder("update CommentPraisedInfo set isPraised = 'Y', praisedDate = ? where id = ?", now, dt.getString(0, 0)));

				} else {
					// 取消点赞
					transaction.add(new QueryBuilder("update CommentPraisedInfo set isPraised = 'N', cancelDate = ? where id = ?", now, dt.getString(0, 0)));
				}
				
			} else {
				// 订单号
				String orderSn = "";
				// 产品编码
				String productId = "";
				// 产品名称
				String productName = "";
				dt = new QueryBuilder("select o.orderSn, i.productId, i.productName from sdorders o, sdinformation i where o.commentId = ? and o.orderSn = i.orderSn", commentId).executeDataTable();
				if (dt != null && dt.getRowCount() > 0) {
					orderSn = dt.getString(0, 0);
					productId = dt.getString(0, 1);
					productName = dt.getString(0, 2);
				}
				if (StringUtil.isEmpty(productId)) {
					dt = new QueryBuilder("SELECT Prop4,Title FROM zcarticle WHERE ID = ?", ZCComment.getRelaID()).executeDataTable();
					if (dt != null && dt.getRowCount() > 0) {
						productId = dt.getString(0, 0);
						productName = dt.getString(0, 1);
					}
				}
				// 新增一条点赞数据
				CommentPraisedInfoSchema schema = new CommentPraisedInfoSchema();
				schema.setid(NoUtil.getMaxNo("CommentPraisedID", 15));
				schema.setcommentId(commentId);
				schema.setRelaID(ZCComment.getRelaID());
				schema.setorderSn(orderSn);
				schema.setproductId(productId);
				schema.setproductName(productName);
				schema.setuserIP(ip);
				schema.setisPraised("Y");
				schema.setpraisedDate(now);
				schema.setCreateUser("system");
				schema.setCreateDate(now);
				transaction.add(schema, Transaction.INSERT);
				map.put("isPraised", "Y");
			}
			ZCComment.setpraisedNum(praisedNum);
			transaction.add(ZCComment, Transaction.UPDATE);
			if (transaction.commit()) {
				map.put(STATUS, SUCCESS);
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
	
	public String getType() {
		HashMap<String, String> map = new HashMap<String, String>();
		HttpServletRequest request = getRequest();
		String relaID = request.getParameter("RelaID");
		// 当前登录会员ID
		String id = (String) request.getSession().getAttribute(
				Member.LOGIN_MEMBER_ID_SESSION_NAME);
		if (StringUtil.isEmpty(id)) {
			map.put(STATUS, ERROR);
			map.put(MESSAGE, "非会员，不能评论哦。请先注册、登录吧！");
			return ajaxJson(map);
		}

		memberSchema memberSchemas = new memberSchema();
		memberSchemas.setid(id);
		memberSet memberSets = memberSchemas.query();
		if ((null == memberSets) || (0 == memberSets.size())) {
			map.put(STATUS, ERROR);
			map.put(MESSAGE, "非会员，不能评论哦。请先注册、登录吧！");
			return ajaxJson(map);
		}
		
		// 查询是否有未评价的产品
		StringBuffer sb = new StringBuffer();
		sb.append(" select 1 from sdorders s, sdinformation b, zdcolumnvalue d");
		sb.append(" where s.orderStatus in ('7', '10') and s.memberId= ?");
		sb.append(" and s.orderSn = b.orderSn  and d.columncode='RiskCode'");
		sb.append(" and d.textValue = b.productId and d.RelaID = ? and s.commentId is null ");
		
		QueryBuilder qb = new QueryBuilder(sb.toString());
		qb.add(id);
		qb.add(relaID);
		String isBuy = qb.executeString();
		map.put(STATUS, SUCCESS);
		// 有未评价的可进行评价
		if ("1".equals(isBuy)) {
			map.put("isBuy", "1");
			
			// 没有未评价的只能进行咨询
		} else {
			map.put("isBuy", "0");
		}
		return ajaxJson(map);
	}
}
