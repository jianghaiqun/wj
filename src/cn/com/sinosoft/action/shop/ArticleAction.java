package cn.com.sinosoft.action.shop;

import cn.com.sinosoft.bean.Pager;
import cn.com.sinosoft.entity.Article;
import cn.com.sinosoft.entity.ArticleCategory;
import cn.com.sinosoft.entity.Channel;
import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.service.ArticleCategoryService;
import cn.com.sinosoft.service.ArticleService;
import cn.com.sinosoft.service.ChannelService;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.NumberUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.points.IntegralConstant;
import com.sinosoft.points.PointsCalculate;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 前台Action类 - 文章
 * ============================================================================
 * 
 * 
 * 
 * 
 * 
 * 
 * KEY:SINOSOFT392AA3637D2315A30E492D893AD58FA1
 * ============================================================================
 */

@ParentPackage("shop") 
public class ArticleAction extends BaseShopAction {

	private static final long serialVersionUID = -25541236985328967L;

	private ArticleCategory articleCategory;
	private Channel channel;
	private List<Article> recommendArticleList;
	private List<Article> hotArticleList;
	private List<Article> szhotArticleList;
	private List<Article> newArticleList;
	private List<ArticleCategory> pathList;
	private String riskType;
	private String riskCompany;
	private String riskProduct1;
	private String riskProduct2;
	// ==============================================首页文章布局1,2,3,4,5=================================================================
	/**
	 * 首页文章布局公用查询List
	 */

	private List<Article> homeList1;
	private List<Article> homeList2;
	private List<Article> homeList3;
	private List<Article> homeList4;
	private List<Article> homeList5;
   
	 //ajax 回调方法;
	private String Callback;
	
	public void setCallback(String callback) {
	
		Callback = callback;
	}

	public List<Article> getHomeList1() {

		return homeList1;
	}

	public void setHomeList1(List<Article> homeList1) {

		this.homeList1 = homeList1;
	}

	public List<Article> getHomeList2() {

		return homeList2;
	}

	public void setHomeList2(List<Article> homeList2) {

		this.homeList2 = homeList2;
	}

	public List<Article> getHomeList3() {

		return homeList3;
	}

	public void setHomeList3(List<Article> homeList3) {

		this.homeList3 = homeList3;
	}

	public List<Article> getHomeList4() {

		return homeList4;
	}

	public void setHomeList4(List<Article> homeList4) {

		this.homeList4 = homeList4;
	}

	public List<Article> getHomeList5() {

		return homeList5;
	}

	public void setHomeList5(List<Article> homeList5) {

		this.homeList5 = homeList5;
	}

	// ==============================================首页文章布局1,2,3,4,5=================================================================
	@Resource
	private ArticleService articleService;
	@Resource
	private ArticleCategoryService articleCategoryService;
	@Resource
	private ChannelService channelService;

	// 文章分类列表
	@InputConfig(resultName = "error")
	public String list() {

		articleCategory = articleCategoryService.load(id);
		recommendArticleList = articleService.getRecommendArticleList(
				articleCategory, Article.MAX_RECOMMEND_ARTICLE_LIST_COUNT);
		hotArticleList = articleService.getHotArticleList(articleCategory,
				Article.MAX_HOT_ARTICLE_LIST_COUNT);
		newArticleList = articleService.getNewArticleList(articleCategory,
				Article.MAX_NEW_ARTICLE_LIST_COUNT);
		pathList = articleCategoryService
				.getArticleCategoryPathList(articleCategory);

		if (pager == null) {
			pager = new Pager();
			pager.setPageSize(Article.DEFAULT_ARTICLE_LIST_PAGE_SIZE);
		}
		pager.setProperty(null);
		pager.setKeyword(null);

		pager = articleService.getArticlePager(articleCategory, pager);
		return "list";
	}

	// 苏州移动文章分类列表
	@InputConfig(resultName = "error")
	public String szlist() {

		articleCategory = articleCategoryService.load(id);
		channel = channelService.load("2");
		recommendArticleList = articleService.getRecommendArticleList(
				articleCategory, channel,
				Article.MAX_RECOMMEND_ARTICLE_LIST_COUNT);
		hotArticleList = articleService.getHotArticleList(articleCategory,
				channel, Article.MAX_HOT_ARTICLE_LIST_COUNT);
		newArticleList = articleService.getNewArticleList(articleCategory,
				channel, Article.MAX_NEW_ARTICLE_LIST_COUNT);
		pathList = articleCategoryService
				.getArticleCategoryPathList(articleCategory);
		if (pager == null) {
			pager = new Pager();
			pager.setPageSize(Article.DEFAULT_ARTICLE_LIST_PAGE_SIZE);
		}
		pager.setProperty(null);
		pager.setKeyword(null);
		pager = articleService.getArticlePager(articleCategory, channel, pager);
		return "szlist";
	}

	// 文章搜索
	@Validations(requiredStrings = { @RequiredStringValidator(fieldName = "pager.keyword", message = "搜索关键词不允许为空!") })
	@InputConfig(resultName = "error")
	public String search() throws Exception {

		recommendArticleList = articleService
				.getRecommendArticleList(Article.MAX_RECOMMEND_ARTICLE_LIST_COUNT);
		hotArticleList = articleService
				.getHotArticleList(Article.MAX_HOT_ARTICLE_LIST_COUNT);
		newArticleList = articleService
				.getNewArticleList(Article.MAX_NEW_ARTICLE_LIST_COUNT);
		if (pager == null) {
			pager = new Pager();
			pager.setPageSize(Article.DEFAULT_ARTICLE_LIST_PAGE_SIZE);
		}
		// pager = articleService.search(pager);
		return "search";
	}

	// 文章点击统计
	@Validations(requiredStrings = { @RequiredStringValidator(fieldName = "id", message = "文章ID不允许为空!") })
	@InputConfig(resultName = "error")
	public String ajaxCounter() {

		Article article = articleService.load(id);
		if (!article.getIsPublication()) {
			addActionError("您访问的文章尚未发布！");
			return ERROR;
		}
		Integer hits = article.getHits() + 1;
		article.setHits(hits);
		articleService.update(article);
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put(STATUS, SUCCESS);
		jsonMap.put("hits", hits.toString());
		return ajaxJson(jsonMap);
	}

	/**
	 * 加载产品.
	 * 
	 * @return
	 */
	public String ajaxProduct() {

		StringBuffer result = new StringBuffer();
		try {
			// long start = System.currentTimeMillis();
			List<HashMap<String, String>> resultList = new ArrayList<HashMap<String, String>>();
			if (StringUtil.isNotEmpty(riskProduct1) || StringUtil.isNotEmpty(riskProduct2)) {
				resultList = AjaxProduct.queryProductById(riskProduct1, riskProduct2);
				if (resultList == null || resultList.size() < 1) {
					resultList = AjaxProduct.queryProduct(riskType, riskCompany);
				}
			} else {
				resultList = AjaxProduct.queryProduct(riskType, riskCompany);
			}

			// long end = System.currentTimeMillis();
			// System.out.println("执行时间："+(start - end));

			AjaxPriceAction ap = new AjaxPriceAction();
			String adaptPeopleInfo = "";
			String memberId = "";
			Member member = getLoginMember();
			if (member != null) {
				memberId = member.getId();
			}
			for (Map<String, String> map : resultList) {
				String newPrice = ap.queryAjaxPrice(map.get("ProductID"), memberId);
				adaptPeopleInfo = map.get("AdaptPeopleInfo");
				if (StringUtil.isNotEmpty(adaptPeopleInfo) && adaptPeopleInfo.length() > 50) {
					adaptPeopleInfo = adaptPeopleInfo.substring(0, 50) + "...";
				}
				result.append("<div class=\"shop_desbox\">");
				result.append("<div class=\"shop_imgdes\"><a  target='_blank' href=\""
						+ map.get("URL") + "\" ><img src=\"" + map.get("LogoLink") + "\" " +
						"width=\"170\" height=\"170\" alt=\""
						+ map.get("ProductName") + "\" class=\"lazy\" data-original= \"" + map.get("LogoLink")
						+ "\" style=\"display: inline;\" /></a></div>");
				result.append("<div class=\"shop_descon\"><h2 class=\"f_mi\"><a href=\""
						+ map.get("URL") + "\" target=\"_blank\">" + map.get("ProductName") + "</a></h2>");
				result.append("<div class=\"shop_tj_shrq cf\"><p class=\"shop_shrd_con\">");
				result.append("<span class=\"shop_shrq_bg\">适合人群</span>" + adaptPeopleInfo + "</p></div>");
				result.append(map.get("FEMRiskBrightSpot") + "<div class=\"clear\"></div>");
				result.append("<ul class=\"recommend_list\">" + map.get("DutyHTMLV3") + "</ul>");
				result.append("<div class=\"pay_consf\"><span><em>￥</em>" + newPrice + "</span>");
				result.append("<a href=\"" + map.get("URL") + "\" target=\"_blank\">去看看</a>");
				result.append("</div></div></div>");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		// System.out.println(result.toString());

		Map<String, Object> price = new HashMap<String, Object>();
		price.put("products", result.toString());// 折扣保费
		JSONObject jsonObject = JSONObject.fromObject(price);

		return ajax(jsonObject.toString(), "text/html");
	}

	/**
	 * ajaxWapProduct:(wap站推荐产品). <br/>
	 *
	 * @author miaozhiqiang
	 * @return
	 */
 
	public String ajaxWapProduct() {

		StringBuffer result = new StringBuffer();
		try {
			// long start = System.currentTimeMillis();
			List<HashMap<String, String>> resultList = new ArrayList<HashMap<String, String>>();
			if (StringUtil.isNotEmpty(riskProduct1) || StringUtil.isNotEmpty(riskProduct2)) {
				resultList = AjaxWapProduct.queryProductById(riskProduct1, riskProduct2);
				if (resultList == null || resultList.size() < 1) {
					resultList = AjaxWapProduct.queryProduct(riskType, riskCompany);
				}
			} else {
				resultList = AjaxWapProduct.queryProduct(riskType, riskCompany);
			}
            
			String memberId = "";
			Member member = getLoginMember();
			if (member != null) {
				memberId = member.getId();
			}
			
			// long end = System.currentTimeMillis();
			// System.out.println("执行时间："+(start - end));
			AjaxPriceAction ap = new AjaxPriceAction();
			String adaptPeopleInfo = "";
			float salesVolume = 0;
			String salesVolumeString = "";
			for (Map<String, String> map : resultList) {
				String newPrice = ap.queryAjaxPrice(map.get("ProductID"), memberId);
				adaptPeopleInfo = map.get("AdaptPeopleInfo");
				if (StringUtil.isNotEmpty(adaptPeopleInfo) && adaptPeopleInfo.length() > 50) {
					adaptPeopleInfo = adaptPeopleInfo.substring(0, 50) + "...";
				}
				// 销量10000+ 转成 '1万'
				try {
					salesVolume = Float.parseFloat(map.get("SalesVolume"));

					if (salesVolume < 10000) {
						salesVolumeString = String.valueOf(salesVolume);
					} else {
						salesVolumeString = String.valueOf(salesVolume / 10000) + "万";
					}

				} catch (Exception e) {
					salesVolumeString = "0";
				}
                
				result.append("<li>");
			
				result.append("<a href=\"" + map.get("URL").replaceAll(Config.getValue("FrontServerContextPath"), Config.getValue("WapServerContext")) + "\">");
				result.append("<span class=\"ttl\"> " + map.get("ProductName") + "</span>");
				result.append("<span class=\"txt\">" + adaptPeopleInfo + "</span>");
				result.append("<span class=\"mny\">销量：<b>" + salesVolumeString + "</b></span>");
				result.append("<span class=\"price\">¥" + map.get("InitPrem") + "</span>");
				result.append("<span class=\"event\">优选</span>");
				result.append("</a >");
				result.append("</li>");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		// System.out.println(result.toString());

		Map<String, Object> price = new HashMap<String, Object>();
		price.put("products", result.toString());// 折扣保费
		JSONObject jsonObject = JSONObject.fromObject(price);
        return ajaxJsonp(Callback + "(" + jsonObject.toString() + ");", "text/html");
	}

	/**
	 * 
	 * @return
	 */
	public String ajaxGetAnswerPoints() {

		String result = "";
		Map<String, Object> point = new HashMap<String, Object>();
		PointsCalculate PointsCalculate = new PointsCalculate();
		Map<String, Object> map;
		try {
			map = PointsCalculate.pointsManage(IntegralConstant.POINT_SEARCH, IntegralConstant.POINT_SOURCE_ANSWER, null);
			if (!StringUtil.isEmpty(map.get(IntegralConstant.ACTION_POINTS))
					&& !"0".equals(map.get(IntegralConstant.ACTION_POINTS))) {
				result = "回答成功，如被采纳可获得<b class=\"red\">" + map.get(IntegralConstant.ACTION_POINTS) + "</b>个积分";
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		point.put("answerPoint", result);
		JSONObject jsonObject = JSONObject.fromObject(point);

		return ajax(jsonObject.toString(), "text/html");
	}

	public String mtbdInit() {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, String> param = new HashMap<String, String>();
		getArticleInfo("1", 6, "媒体时讯", param, "mtsx");
		String info = param.get("info");
		String page = param.get("page");
		if (info != null) {
			map.put("mtsxInfo", info);
		}
		if (page != null) {
			map.put("mtsxPage", page);
		}
		
		param = new HashMap<String, String>();
		getArticleInfo("1", 6, "公司动态", param, "gsdt");
		info = param.get("info");
		page = param.get("page");
		if (info != null) {
			map.put("gsdtInfo", info);
		}
		if (page != null) {
			map.put("gsdtPage", page);
		}
		
		JSONObject jsonObject = JSONObject.fromObject(map);
		
		return ajax(jsonObject.toString(), "text/html");
	}
	
	public String mtsxAjax() {
		Map<String, Object> map = new HashMap<String, Object>();
		String pageIndex = getParameter("pageIndex");
		Map<String, String> param = new HashMap<String, String>();
		getArticleInfo(pageIndex, 6, "媒体时讯", param, "mtsx");
		String info = param.get("info");
		String page = param.get("page");
		if (info != null) {
			map.put("info", info);
		}
		if (page != null) {
			map.put("page", page);
		}
		
		JSONObject jsonObject = JSONObject.fromObject(map);
		
		return ajax(jsonObject.toString(), "text/html");
	}
	
	public String gsdtAjax() {
		Map<String, Object> map = new HashMap<String, Object>();
		String pageIndex = getParameter("pageIndex");
		Map<String, String> param = new HashMap<String, String>();
		getArticleInfo(pageIndex, 6, "公司动态", param, "gsdt");
		String info = param.get("info");
		String page = param.get("page");
		if (info != null) {
			map.put("info", info);
		}
		if (page != null) {
			map.put("page", page);
		}
		
		JSONObject jsonObject = JSONObject.fromObject(map);
		
		return ajax(jsonObject.toString(), "text/html");
	}
	
	public String jobsAjax() {
		Map<String, Object> map = new HashMap<String, Object>();
		String pageIndex = getParameter("pageIndex");
		int intPageIndex = 0;
		int pageSize = 8;
		if (StringUtil.isNotEmpty(pageIndex) && NumberUtil.isInt(pageIndex)) {
			intPageIndex = Integer.valueOf(pageIndex) - 1;
			if (intPageIndex < 0) {
				intPageIndex = 0;
			}
			int start = intPageIndex*pageSize;
			int total = new QueryBuilder("SELECT count(1) FROM JobsInfo WHERE isPublish='Y' ").executeInt();
			// 总条数要大于查询的起始条数
			if (total > start) {
				StringBuffer info = new StringBuffer();
				QueryBuilder qb = new QueryBuilder("SELECT jobsName,DATE_FORMAT(publishDate, '%Y-%m-%d') as publishDate,jobsAddress,jobsProperty,jobsDuty,jobsRequirement,hrEmail,jobsType FROM JobsInfo WHERE isPublish='Y' ORDER BY orderFlag DESC,publishDate DESC");
				DataTable dt = qb.executePagedDataTable(pageSize, intPageIndex);
				if (dt != null) {
					int rowCount = dt.getRowCount();
					String jobsName = "";
					String publishDate = "";
					String jobsAddress = "";
					String jobsProperty = "";
					String hrEmail = "";
					String jobsType = "";
					for (int i = 0; i < rowCount; i++) {
						jobsName = dt.getString(i, "jobsName");
						publishDate = dt.getString(i, "publishDate");
						jobsAddress = dt.getString(i, "jobsAddress");
						jobsProperty = dt.getString(i, "jobsProperty");
						hrEmail = dt.getString(i, "hrEmail");
						jobsType = dt.getString(i, "jobsType");
						if (i%2 == 1) {
							info.append("<tr class=\"ood\">");
						} else {
							info.append("<tr>");
						}
						info.append("<td><span dataid=\"work000"+(i+1)+"\" class=\"link_sq\" title=\""+jobsName+"\"><em>"+jobsName+"</em></span>");
						info.append("<div class=\"zw_des\" id=\"work000"+(i+1)+"\" style=\"display:none;\">");
						info.append("<h3 class=\"f_mi\">"+jobsName+"<span>发布时间："+publishDate+"</span></h3>");
						info.append("<p class=\"zw_mac\"><span>工作地点："+jobsAddress+" </span><span>工作性质："+jobsProperty+"</span></p>");
						info.append("<div class=\"gw_cons\"><div class=\"gw_left\"><b>岗位职责</b>");
						info.append("<div class=\"gw_des\"><p>"+splitInfo(dt.getString(i, "jobsDuty"))+"</p></div></div>");
						info.append("<div class=\"gw_right\"><b>任职要求</b><p>"+splitInfo(dt.getString(i, "jobsRequirement"))+"</p></div></div>");
						info.append("<div class=\"clear\"></div><div class=\"gw_flie\"><span>点击“立即申请”发送您的简历</span>");
						info.append("<a class=\"gw_flieinput\" datemail=\""+hrEmail+"\" href=\"mailto:"+hrEmail+"\">立即申请</a></div></div></td>");						
						info.append("<td title=\""+jobsType+"\"><span class=\"ove_text ove_wid\">"+jobsType+"</span></td>");
						info.append("<td title=\""+jobsAddress+"\"><span class=\"ove_text\">"+jobsAddress+"</span></td><td>"+jobsProperty+"</td>");
						info.append("<td><a class=\"link_sk\"  datemail=\""+hrEmail+"\"  href=\"mailto:"+hrEmail+"\">申请</a></td>");
						info.append("<td>"+publishDate+"</td></tr>");
					}
					map.put("info", info.toString());
					int lastpage = ((total + pageSize - 1) / (pageSize));
					if (total <= pageSize || lastpage <= 1) {
						map.put("page", "");
					} else {
						// 取得分页
						String page = pageInfo(lastpage, intPageIndex, "jobs");
						if (StringUtil.isNotEmpty(page)) {
							map.put("page", page);
						}
					}
				}
			}
		}
		JSONObject jsonObject = JSONObject.fromObject(map);
		
		return ajax(jsonObject.toString(), "text/html");
	}
	
	private String splitInfo(String param) {
		StringBuffer sb = new StringBuffer();
		if (StringUtil.isNotEmpty(param)) {
			String[] infos = param.split("\n");
			for (String info : infos) {
				sb.append(info+"<br>");
			}
		}
		return sb.toString();
	}
	
	private void getArticleInfo(String pageIndex, int pageSize, String cataLogName, Map<String, String> map, String flag) {
		int intPageIndex = 0;
		if (StringUtil.isNotEmpty(pageIndex) && NumberUtil.isInt(pageIndex)) {
			intPageIndex = Integer.valueOf(pageIndex) - 1;
			if (intPageIndex < 0) {
				intPageIndex = 0;
			}
			int start = intPageIndex*pageSize;
			int total = new QueryBuilder("SELECT count(1) FROM zcarticle WHERE CatalogID=(SELECT ID FROM ZCCatalog WHERE NAME=?) AND TYPE='1' and status='30' ", cataLogName).executeInt();
			// 总条数要大于查询的起始条数
			if (total > start) {
				StringBuffer info = new StringBuffer();
				String frontPath = Config.getFrontServerContextPath() + "/";
				QueryBuilder qb = new QueryBuilder("SELECT Title,ModifyTime,URL FROM zcarticle WHERE CatalogID=(SELECT ID FROM ZCCatalog WHERE NAME=?) AND TYPE='1' and status='30' ORDER BY topflag DESC,orderflag DESC,ModifyTime DESC, id DESC ", cataLogName);
				DataTable dt = qb.executePagedDataTable(pageSize, intPageIndex);
				if (dt != null) {
					int rowCount = dt.getRowCount();
					String modifyTime = "";
					for (int i = 0; i < rowCount; i++) {
						modifyTime = dt.getString(i, "ModifyTime");
						if (StringUtil.isNotEmpty(modifyTime)) {
							modifyTime = modifyTime.substring(0, 10);
						}
						info.append("<li><a href=\""+frontPath+dt.getString(i, "URL")+"\" target=\"_blank\">"+dt.getString(i, "Title")+"</a><span>"+modifyTime+"</span></li>");
					}
					map.put("info", info.toString());
					int lastpage = ((total + pageSize - 1) / (pageSize));
					if (total <= pageSize || lastpage <= 1) {
						map.put("page", "");
					} else {
						// 取得分页
						String page = pageInfo(lastpage, intPageIndex, flag);
						if (StringUtil.isNotEmpty(page)) {
							map.put("page", page);
						}
					}
				}
			}
		} 
	}
	
	private String pageInfo(int lastpage, int intPageIndex, String flag) {
		List<Map<String, String>> pageList = PageDataList(lastpage, intPageIndex);
		
		if (pageList != null && pageList.size() > 0) {
			StringBuffer page = new StringBuffer();
			page.append("<div id=\"pagination\"><ul>");
			if (intPageIndex == 0) {
				page.append("<li class=\"page_prev\"><a href=\"javascript:void(0);\"><span class=\"default\">上一页</span></a></li>");
				
			} else {
				page.append("<li class=\"page_prev\"><a href=\"javascript:void(0);\" onclick=\"turnPage("+intPageIndex+", '"+flag+"')\"><span>上一页</span></a></li>");
			}
			
			for (Map<String, String> pageMap : pageList) {
				page.append("<li class=\""+pageMap.get("class")+"\">");
				if ("now".equalsIgnoreCase(pageMap.get("class")) || "omit".equalsIgnoreCase(pageMap.get("class"))) {
					page.append("<a href=\"javascript:void(0);\"><span>"+pageMap.get("index")+"</span></a>");
				} else {
					page.append("<a href=\"javascript:void(0);\" onclick=\"turnPage("+pageMap.get("index")+", '"+flag+"')\"><span>"+pageMap.get("index")+"</span></a>");
				}
				
				page.append("</li>");
			}
			if (lastpage == (intPageIndex+1)) {
				page.append("<li class=\"page_next\"><a href=\"javascript:void(0);\"><span class=\"default\">下一页</span></a></li>");
			} else {
				page.append("<li class=\"page_next\"><a href=\"javascript:void(0);\" onclick=\"turnPage("+(intPageIndex+2)+", '"+flag+"')\"><span>下一页</span></a></li>");
			}
			
			page.append("</ul></div>");
			return page.toString();
		}
		return "";
	}
	
	public ArticleCategory getArticleCategory() {

		return articleCategory;
	}

	public void setArticleCategory(ArticleCategory articleCategory) {

		this.articleCategory = articleCategory;
	}

	public List<Article> getRecommendArticleList() {

		return recommendArticleList;
	}

	public void setRecommendArticleList(List<Article> recommendArticleList) {

		this.recommendArticleList = recommendArticleList;
	}

	public List<Article> getHotArticleList() {

		return hotArticleList;
	}

	public void setHotArticleList(List<Article> hotArticleList) {

		this.hotArticleList = hotArticleList;
	}

	public List<Article> getSzhotArticleList() {

		return szhotArticleList;
	}

	public void setSzhotArticleList(List<Article> szhotArticleList) {

		this.szhotArticleList = szhotArticleList;
	}

	public List<Article> getNewArticleList() {

		return newArticleList;
	}

	public void setNewArticleList(List<Article> newArticleList) {

		this.newArticleList = newArticleList;
	}

	public List<ArticleCategory> getPathList() {

		return pathList;
	}

	public void setPathList(List<ArticleCategory> pathList) {

		this.pathList = pathList;
	}

	public String getRiskType() {

		return riskType;
	}

	public void setRiskType(String riskType) {

		this.riskType = riskType;
	}

	public String getRiskCompany() {

		return riskCompany;
	}

	public void setRiskCompany(String riskCompany) {

		this.riskCompany = riskCompany;
	}

	public String getRiskProduct1() {

		return riskProduct1;
	}

	public void setRiskProduct1(String riskProduct1) {

		this.riskProduct1 = riskProduct1;
	}

	public String getRiskProduct2() {

		return riskProduct2;
	}

	public void setRiskProduct2(String riskProduct2) {

		this.riskProduct2 = riskProduct2;
	}

}