package cn.com.sinosoft.service.impl;

import cn.com.sinosoft.bean.HtmlConfig;
import cn.com.sinosoft.bean.SystemConfig;
import cn.com.sinosoft.dao.ArticleDao;
import cn.com.sinosoft.dao.ArticleHomeDao;
import cn.com.sinosoft.dao.CustomerDemandDao;
import cn.com.sinosoft.dao.PresentDao;
import cn.com.sinosoft.dao.ProductDao;
import cn.com.sinosoft.entity.Article;
import cn.com.sinosoft.entity.ArticleCategory;
import cn.com.sinosoft.entity.Channel;
import cn.com.sinosoft.entity.Code;
import cn.com.sinosoft.entity.Present;
import cn.com.sinosoft.entity.PresentCategory;
import cn.com.sinosoft.entity.Product;
import cn.com.sinosoft.entity.ProductCategory;
import cn.com.sinosoft.service.ArticleCategoryService;
import cn.com.sinosoft.service.ChannelService;
import cn.com.sinosoft.service.CodeService;
import cn.com.sinosoft.service.FooterService;
import cn.com.sinosoft.service.FriendLinkService;
import cn.com.sinosoft.service.HtmlService;
import cn.com.sinosoft.service.NavigationService;
import cn.com.sinosoft.service.PresentCategoryService;
import cn.com.sinosoft.service.ProductCategoryService;
import cn.com.sinosoft.util.SystemConfigUtil;
import cn.com.sinosoft.util.TemplateConfigUtil;
import com.sinosoft.framework.Config;
import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.ResourceBundleModel;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.views.freemarker.FreemarkerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Service实现类 - 生成静态
 * ============================================================================
 * 
 * 
 * 
 * 
 * 
 * 
 * KEY:SINOSOFTE7656ABEA5A60D076604DDCE20B403C9
 * ============================================================================
 */

@Service
public class HtmlServiceImpl implements HtmlService {
	private static final Logger logger = LoggerFactory.getLogger(HtmlServiceImpl.class);

	public static final String PRESENT_CONTENT = "presentContent";// 礼品内容
	private ArticleCategory articleCategory;
	@Resource
	private CodeService codeService;
	@Resource
	private ChannelService channelService;
	@Resource
	private FreemarkerManager freemarkerManager;
	@Resource
	private NavigationService navigationService;
	@Resource
	private FriendLinkService friendLinkService;
	@Resource
	private FooterService footerService;
	@Resource
	private ArticleCategoryService articleCategoryService;
	@Resource
	private ArticleDao articleDao;
	@Resource
	private ArticleHomeDao articleHomeDao;
	@Resource
	private CustomerDemandDao customerDemandDao;
	@Resource
	ProductCategoryService productCategoryService;
	@Resource
	PresentCategoryService presentCategoryService;
	@Resource
	private ProductDao productDao;
	@Resource
	private PresentDao presentDao;
	private String articleCategoryId;
	private Channel channel;

	public String getArticleCategoryId() {
		return articleCategoryId;
	}

	public void setArticleCategoryId(String articleCategoryId) {
		this.articleCategoryId = articleCategoryId;
	}

	public void buildHtml(String templateFilePath, String htmlFilePath, Map<String, Object> data) {
		try {
			ServletContext servletContext = ServletActionContext.getServletContext();
			Configuration configuration = freemarkerManager.getConfiguration(servletContext);
			Template template = configuration.getTemplate(templateFilePath);
			File htmlFile = new File(servletContext.getRealPath(htmlFilePath));
			File htmlDirectory = htmlFile.getParentFile();
			if (!htmlDirectory.exists()) {
				htmlDirectory.mkdirs();
			}
			Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(htmlFile), "UTF-8"));
			template.process(data, out);
			out.flush();
			out.close();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public String buildHtml(String templateFilePath, Map<String, Object> data) {
		try {
			ServletContext servletContext = ServletActionContext.getServletContext();
			Configuration configuration = freemarkerManager.getConfiguration(servletContext);
			Template template = configuration.getTemplate(templateFilePath);
			StringWriter writer = new StringWriter();
			template.process(data, writer);

			return writer.toString();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	// 获取公共数据
	public Map<String, Object> getCommonData() {
		Map<String, Object> commonData = new HashMap<String, Object>();
		// ServletContext servletContext =
		// ServletActionContext.getServletContext();
		ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n");
		ResourceBundleModel resourceBundleModel = new ResourceBundleModel(resourceBundle, new BeansWrapper());
		SystemConfig systemConfig = SystemConfigUtil.getSystemConfig();

		String priceCurrencyFormat = SystemConfigUtil.getPriceCurrencyFormat();
		String priceUnitCurrencyFormat = SystemConfigUtil.getPriceUnitCurrencyFormat();

		String orderCurrencyFormat = SystemConfigUtil.getOrderCurrencyFormat();
		String orderUnitCurrencyFormat = SystemConfigUtil.getOrderUnitCurrencyFormat();

		// 获取服务器应用地址
		commonData.put("base", Config.getValue("ServerContext"));
		commonData.put("front", Config.getValue("FrontServerContextPath"));
		
		commonData.put("staticPath", Config.getValue("StaticResourcePath"));
		commonData.put("jsPath", Config.getValue("JsResourcePath"));
		commonData.put("cssPath", Config.getValue("StaticResourcePath"));
		
		commonData.put("bundle", resourceBundleModel);
		commonData.put("systemConfig", systemConfig);
		commonData.put("priceCurrencyFormat", priceCurrencyFormat);
		commonData.put("priceUnitCurrencyFormat", priceUnitCurrencyFormat);
		commonData.put("orderCurrencyFormat", orderCurrencyFormat);
		commonData.put("orderUnitCurrencyFormat", orderUnitCurrencyFormat);
		commonData.put("topNavigationList", navigationService.getTopNavigationList());
		commonData.put("middleNavigationList", navigationService.getMiddleNavigationList());
		commonData.put("bottomNavigationList", navigationService.getBottomNavigationList());
		commonData.put("friendLinkList", friendLinkService.getAll());
		commonData.put("pictureFriendLinkList", friendLinkService.getPictureFriendLinkList());
		commonData.put("textFriendLinkList", friendLinkService.getTextFriendLinkList());
		commonData.put("footer", footerService.getFooter());
		return commonData;
	}

	public void baseJavascriptBuildHtml() {
		Map<String, Object> data = getCommonData();
		HtmlConfig htmlConfig = TemplateConfigUtil.getHtmlConfig(HtmlConfig.BASE_JAVASCRIPT);
		String htmlFilePath = htmlConfig.getHtmlFilePath();
		String templateFilePath = htmlConfig.getTemplateFilePath();
		buildHtml(templateFilePath, htmlFilePath, data);
	}

	public void indexBuildHtml() {
		HtmlConfig htmlConfig = TemplateConfigUtil.getHtmlConfig(HtmlConfig.INDEX);
		Map<String, Object> data = getCommonData();
		/**
		 * 产品静态提供信息
		 */
		data.put("rootProductCategoryList", productCategoryService.getRootProductCategoryList());
		data.put("bestProductList", productDao.getBestProductList(Product.MAX_BEST_PRODUCT_LIST_COUNT));
		data.put("hotProductList", productDao.getHotProductList(Product.MAX_HOT_PRODUCT_LIST_COUNT));
		data.put("newProductList", productDao.getNewProductList(Product.MAX_NEW_PRODUCT_LIST_COUNT));
		List<ProductCategory> allProductCategory = productCategoryService.getAll();
		data.put("allProductCategoryList", allProductCategory);
		Map<String, List<ProductCategory>> productCategoryMap = new HashMap<String, List<ProductCategory>>();
		Map<String, List<Product>> bestProductMap = new HashMap<String, List<Product>>();
		Map<String, List<Product>> hotProductMap = new HashMap<String, List<Product>>();
		Map<String, List<Product>> newProductMap = new HashMap<String, List<Product>>();
		for (ProductCategory productCategory : allProductCategory) {
			productCategoryMap.put(productCategory.getId(), productCategoryService.getChildrenProductCategoryList(productCategory));
			bestProductMap.put(productCategory.getId(), productDao.getBestProductList(productCategory, Product.MAX_BEST_PRODUCT_LIST_COUNT));
			hotProductMap.put(productCategory.getId(), productDao.getHotProductList(productCategory, Product.MAX_HOT_PRODUCT_LIST_COUNT));
			newProductMap.put(productCategory.getId(), productDao.getNewProductList(productCategory, Product.MAX_NEW_PRODUCT_LIST_COUNT));
		}
		data.put("productCategoryMap", productCategoryMap);
		data.put("bestProductMap", bestProductMap);
		data.put("hotProductMap", hotProductMap);
		data.put("newProductMap", newProductMap);

		/**
		 * 文章静态提供信息
		 */
		data.put("rootArticleCategoryList", articleCategoryService.getRootArticleCategoryList());
		data.put("recommendArticleList", articleDao.getRecommendArticleList(Article.MAX_RECOMMEND_ARTICLE_LIST_COUNT));
		data.put("hotArticleList", articleDao.getHotArticleList(Article.MAX_HOT_ARTICLE_LIST_COUNT));
		data.put("newArticleList", articleDao.getNewArticleList(Article.MAX_NEW_ARTICLE_LIST_COUNT));
		// ==================首页文章布局1,2,3,4,5==================================

		articleCategoryId = articleHomeDao.findByHomeSn("1");
		articleCategory = articleCategoryService.load(articleCategoryId);
		data.put("homeList1", articleDao.getHomeArticleList(articleCategory));
		articleCategoryId = null;
		articleCategory = null;
		articleCategoryId = articleHomeDao.findByHomeSn("2");
		articleCategory = articleCategoryService.load(articleCategoryId);
		data.put("homeList2", articleDao.getHomeArticleList(articleCategory));
		articleCategoryId = null;
		articleCategory = null;

		articleCategoryId = articleHomeDao.findByHomeSn("3");
		articleCategory = articleCategoryService.load(articleCategoryId);
		data.put("homeList3", articleDao.getHomeArticleList(articleCategory));
		articleCategoryId = null;
		articleCategory = null;

		articleCategoryId = articleHomeDao.findByHomeSn("4");
		articleCategory = articleCategoryService.load(articleCategoryId);
		data.put("homeList4", articleDao.getHomeArticleList(articleCategory));
		articleCategoryId = null;
		articleCategory = null;

		articleCategoryId = articleHomeDao.findByHomeSn("5");
		articleCategory = articleCategoryService.load(articleCategoryId);
		data.put("homeList5", articleDao.getHomeArticleList(articleCategory));

		// ==================首页文章布局1,2,3,4,5==================================
		List<ArticleCategory> allArticleCategory = articleCategoryService.getAll();
		data.put("allArticleCategoryList", allArticleCategory);
		Map<String, List<ArticleCategory>> articleCategoryMap = new HashMap<String, List<ArticleCategory>>();
		Map<String, List<Article>> recommendArticleMap = new HashMap<String, List<Article>>();
		Map<String, List<Article>> hotArticleMap = new HashMap<String, List<Article>>();
		Map<String, List<Article>> newArticleMap = new HashMap<String, List<Article>>();
		for (ArticleCategory articleCategory : allArticleCategory) {
			articleCategoryMap.put(articleCategory.getId(), articleCategoryService.getChildrenArticleCategoryList(articleCategory));
			recommendArticleMap.put(articleCategory.getId(), articleDao.getRecommendArticleList(articleCategory, Article.MAX_RECOMMEND_ARTICLE_LIST_COUNT));
			hotArticleMap.put(articleCategory.getId(), articleDao.getHotArticleList(articleCategory, Article.MAX_HOT_ARTICLE_LIST_COUNT));
			newArticleMap.put(articleCategory.getId(), articleDao.getNewArticleList(articleCategory, Article.MAX_NEW_ARTICLE_LIST_COUNT));
		}
		data.put("articleCategoryMap", articleCategoryMap);
		data.put("recommendArticleMap", recommendArticleMap);
		data.put("hotArticleMap", hotArticleMap);
		data.put("newArticleMap", newArticleMap);

		// ================================================用户需求=====================================================================================
		/**
		 * 用户需求静态提供信息
		 */
		List<Code> list = new ArrayList<Code>();
		list = codeService.getList("paramENDescription", "forWhomInsurance");
		List<String> allInsuranceName = new ArrayList<String>();
		for (Code code : list) {
			allInsuranceName.add(code.getParamValue());
		}
		data.put("allInsuranceName", allInsuranceName);
		list = null;
		list = codeService.getList("paramENDescription", "insuranceType");
		List<String> allInsuranceType = new ArrayList<String>();
		for (Code code : list) {
			allInsuranceType.add(code.getParamValue());
		}
		data.put("allInsuranceType", allInsuranceType);
		list = null;
		list = codeService.getList("paramENDescription", "protectionPeriod");
		List<String> allGuaranteePeriod = new ArrayList<String>();
		for (Code code : list) {
			allGuaranteePeriod.add(code.getParamValue());
		}
		data.put("allGuaranteePeriod", allGuaranteePeriod);

		// ================================================用户需求=====================================================================================
		String htmlFilePath = htmlConfig.getHtmlFilePath();
		String templateFilePath = htmlConfig.getTemplateFilePath();
		buildHtml(templateFilePath, htmlFilePath, data);
	}

	public void indexSzBuildHtml() {
		HtmlConfig htmlConfig = TemplateConfigUtil.getHtmlConfig(HtmlConfig.INDEX_SZ);
		Map<String, Object> data = getCommonData();
		/**
		 * 产品静态提供信息
		 */
		data.put("rootProductCategoryList", productCategoryService.getRootProductCategoryList());
		data.put("bestProductList", productDao.getBestProductList(Product.MAX_BEST_PRODUCT_LIST_COUNT));
		data.put("hotProductList", productDao.getHotProductList(Product.MAX_HOT_PRODUCT_LIST_COUNT));
		data.put("newProductList", productDao.getNewProductList(Product.MAX_NEW_PRODUCT_LIST_COUNT));
		// 无用的去除，这里加入获得商城渠道下所有产品
		List<ProductCategory> allProductCategory = productCategoryService.getAll();
		data.put("allProductCategoryList", allProductCategory);
		Map<String, List<ProductCategory>> productCategoryMap = new HashMap<String, List<ProductCategory>>();
		Map<String, List<Product>> bestProductMap = new HashMap<String, List<Product>>();
		Map<String, List<Product>> hotProductMap = new HashMap<String, List<Product>>();
		Map<String, List<Product>> newProductMap = new HashMap<String, List<Product>>();
		for (ProductCategory productCategory : allProductCategory) {
			productCategoryMap.put(productCategory.getId(), productCategoryService.getChildrenProductCategoryList(productCategory));
			bestProductMap.put(productCategory.getId(), productDao.getBestProductList(productCategory, Product.MAX_BEST_PRODUCT_LIST_COUNT));
			hotProductMap.put(productCategory.getId(), productDao.getHotProductList(productCategory, Product.MAX_HOT_PRODUCT_LIST_COUNT));
			newProductMap.put(productCategory.getId(), productDao.getNewProductList(productCategory, Product.MAX_NEW_PRODUCT_LIST_COUNT));
		}
		data.put("productCategoryMap", productCategoryMap);
		data.put("bestProductMap", bestProductMap);
		data.put("hotProductMap", hotProductMap);
		data.put("newProductMap", newProductMap);

		/**
		 * 文章静态提供信息
		 */
		data.put("rootArticleCategoryList", articleCategoryService.getRootArticleCategoryList());
		data.put("recommendArticleList", articleDao.getRecommendArticleList(Article.MAX_RECOMMEND_ARTICLE_LIST_COUNT));
		data.put("hotArticleList", articleDao.getHotArticleList(Article.MAX_HOT_ARTICLE_LIST_COUNT));
		data.put("newArticleList", articleDao.getNewArticleList(Article.MAX_NEW_ARTICLE_LIST_COUNT));
		// ==================首页文章布局1,2,3,4,5==================================

		articleCategoryId = articleHomeDao.findByHomeSn("1");
		articleCategory = articleCategoryService.load(articleCategoryId);
		data.put("homeList1", articleDao.getHomeArticleList(articleCategory));
		articleCategoryId = null;
		articleCategory = null;
		articleCategoryId = articleHomeDao.findByHomeSn("2");
		articleCategory = articleCategoryService.load(articleCategoryId);
		data.put("homeList2", articleDao.getHomeArticleList(articleCategory));
		articleCategoryId = null;
		articleCategory = null;

		articleCategoryId = articleHomeDao.findByHomeSn("3");
		articleCategory = articleCategoryService.load(articleCategoryId);
		data.put("homeList3", articleDao.getHomeArticleList(articleCategory));
		articleCategoryId = null;
		articleCategory = null;

		articleCategoryId = articleHomeDao.findByHomeSn("4");
		articleCategory = articleCategoryService.load(articleCategoryId);
		data.put("homeList4", articleDao.getHomeArticleList(articleCategory));
		articleCategoryId = null;
		articleCategory = null;

		articleCategoryId = articleHomeDao.findByHomeSn("5");
		articleCategory = articleCategoryService.load(articleCategoryId);
		data.put("homeList5", articleDao.getHomeArticleList(articleCategory));

		// ==================首页文章布局1,2,3,4,5==================================
		List<ArticleCategory> allArticleCategory = articleCategoryService.getAll();
		data.put("allArticleCategoryList", allArticleCategory);
		Map<String, List<ArticleCategory>> articleCategoryMap = new HashMap<String, List<ArticleCategory>>();
		Map<String, List<Article>> recommendArticleMap = new HashMap<String, List<Article>>();
		Map<String, List<Article>> hotArticleMap = new HashMap<String, List<Article>>();
		Map<String, List<Article>> newArticleMap = new HashMap<String, List<Article>>();
		for (ArticleCategory articleCategory : allArticleCategory) {
			articleCategoryMap.put(articleCategory.getId(), articleCategoryService.getChildrenArticleCategoryList(articleCategory));
			recommendArticleMap.put(articleCategory.getId(), articleDao.getRecommendArticleList(articleCategory, Article.MAX_RECOMMEND_ARTICLE_LIST_COUNT));
			hotArticleMap.put(articleCategory.getId(), articleDao.getHotArticleList(articleCategory, Article.MAX_HOT_ARTICLE_LIST_COUNT));
			newArticleMap.put(articleCategory.getId(), articleDao.getNewArticleList(articleCategory, Article.MAX_NEW_ARTICLE_LIST_COUNT));
		}
		data.put("articleCategoryMap", articleCategoryMap);
		data.put("recommendArticleMap", recommendArticleMap);
		data.put("hotArticleMap", hotArticleMap);
		data.put("newArticleMap", newArticleMap);

		// ================================================用户需求=====================================================================================
		/**
		 * 用户需求静态提供信息
		 */
		List<Code> list = new ArrayList<Code>();
		list = codeService.getList("paramENDescription", "forWhomInsurance");
		List<String> allInsuranceName = new ArrayList<String>();
		for (Code code : list) {
			allInsuranceName.add(code.getParamValue());
		}
		data.put("allInsuranceName", allInsuranceName);
		list = null;
		list = codeService.getList("paramENDescription", "insuranceType");
		List<String> allInsuranceType = new ArrayList<String>();
		for (Code code : list) {
			allInsuranceType.add(code.getParamValue());
		}
		data.put("allInsuranceType", allInsuranceType);
		list = null;
		list = codeService.getList("paramENDescription", "protectionPeriod");
		List<String> allGuaranteePeriod = new ArrayList<String>();
		for (Code code : list) {
			allGuaranteePeriod.add(code.getParamValue());
		}
		data.put("allGuaranteePeriod", allGuaranteePeriod);

		// ================================================用户需求=====================================================================================
		String htmlFilePath = htmlConfig.getHtmlFilePath();
		String templateFilePath = htmlConfig.getTemplateFilePath();
		buildHtml(templateFilePath, htmlFilePath, data);
	}

	public void loginBuildHtml() {
		HtmlConfig htmlConfig = TemplateConfigUtil.getHtmlConfig(HtmlConfig.LOGIN);
		Map<String, Object> data = getCommonData();
		String htmlFilePath = htmlConfig.getHtmlFilePath();
		String templateFilePath = htmlConfig.getTemplateFilePath();
		buildHtml(templateFilePath, htmlFilePath, data);
	}

	@Transactional
	public void articleContentBuildHtml(Article article) {
		HtmlConfig htmlConfig = TemplateConfigUtil.getHtmlConfig(HtmlConfig.ARTICLE_CONTENT);
		ArticleCategory articleCategory = article.getArticleCategory();
		Map<String, Object> data = getCommonData();
		data.put("article", article);
		data.put("pathList", articleCategoryService.getArticleCategoryPathList(article));
		data.put("rootArticleCategoryList", articleCategoryService.getRootArticleCategoryList());
		data.put("recommendArticleList", articleDao.getRecommendArticleList(articleCategory, Article.MAX_RECOMMEND_ARTICLE_LIST_COUNT));
		String szchannel = ServletActionContext.getRequest().getScheme() + "://" + ServletActionContext.getRequest().getServerName() + ":" + ServletActionContext.getRequest().getServerPort() + "/";
		if (szchannel.equals("http://127.0.0.1:8080/")) {
			channel = channelService.load("2");
			data.put("hotArticleList", articleDao.getHotArticleList(articleCategory, channel, Article.MAX_HOT_ARTICLE_LIST_COUNT));
		} else {
			data.put("hotArticleList", articleDao.getHotArticleList(articleCategory, Article.MAX_HOT_ARTICLE_LIST_COUNT));
		}
		data.put("newArticleList", articleDao.getNewArticleList(articleCategory, Article.MAX_NEW_ARTICLE_LIST_COUNT));
		String htmlFilePath = article.getHtmlFilePath();
		String prefix = StringUtils.substringBeforeLast(htmlFilePath, ".");
		String extension = StringUtils.substringAfterLast(htmlFilePath, ".");
		List<String> pageContentList = article.getPageContentList();
		article.setPageCount(pageContentList.size());
		articleDao.update(article);
		articleDao.flush();
		for (int i = 0; i < pageContentList.size(); i++) {
			data.put("content", pageContentList.get(i));
			data.put("pageNumber", i + 1);
			data.put("pageCount", pageContentList.size());
			String templateFilePath = htmlConfig.getTemplateFilePath();
			String currentHtmlFilePath = null;
			if (i == 0) {
				currentHtmlFilePath = htmlFilePath;
			} else {
				currentHtmlFilePath = prefix + "_" + (i + 1) + "." + extension;
			}
			buildHtml(templateFilePath, currentHtmlFilePath, data);
		}
	}

	public void productContentBuildHtml(Product product) {
		HtmlConfig htmlConfig = TemplateConfigUtil.getHtmlConfig(HtmlConfig.PRODUCT_CONTENT);
		ProductCategory productCategory = product.getProductCategory();
		Map<String, Object> data = getCommonData();
		data.put("product", product);
		data.put("pathList", productCategoryService.getProductCategoryPathList(product));
		data.put("rootProductCategoryList", productCategoryService.getRootProductCategoryList());
		data.put("bestProductList", productDao.getBestProductList(productCategory, Product.MAX_BEST_PRODUCT_LIST_COUNT));
		data.put("hotProductList", productDao.getHotProductList(productCategory, Product.MAX_HOT_PRODUCT_LIST_COUNT));
		data.put("newProductList", productDao.getNewProductList(productCategory, Product.MAX_NEW_PRODUCT_LIST_COUNT));
		String htmlFilePath = product.getHtmlFilePath();
		String templateFilePath = htmlConfig.getTemplateFilePath();
		buildHtml(templateFilePath, htmlFilePath, data);
	}

	public void presentContentBuildHtml(Present present) {
		HtmlConfig htmlConfig = TemplateConfigUtil.getHtmlConfig(PRESENT_CONTENT);
		PresentCategory presentCategory = present.getPresentCategory();
		Map<String, Object> data = getCommonData();
		data.put("present", present);
		data.put("pathList", presentCategoryService.getPresentCategoryPathList(present));
		data.put("rootPresentCategoryList", presentCategoryService.getRootPresentCategoryList());
		data.put("bestPresentList", presentDao.getBestPresentList(presentCategory, Present.MAX_BEST_PRESENT_LIST_COUNT));
		data.put("hotPresentList", presentDao.getHotPresentList(presentCategory, Present.MAX_HOT_PRESENT_LIST_COUNT));
		data.put("newPresentList", presentDao.getNewPresentList(presentCategory, Present.MAX_NEW_PRESENT_LIST_COUNT));
		String htmlFilePath = present.getHtmlFilePath();
		String templateFilePath = htmlConfig.getTemplateFilePath();
		buildHtml(templateFilePath, htmlFilePath, data);
	}

	public String presentContentListBuildHtml(Present present) {
		try {
			HtmlConfig htmlConfig = TemplateConfigUtil.getHtmlConfig("presentContentList");
			Map<String, Object> data = getCommonData();
			data.put("present", present);
			String templateFilePath = htmlConfig.getTemplateFilePath();
			return buildHtml(templateFilePath, data);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	// public void productContentBuildHtmlSz(Product product) {
	// HtmlConfig htmlConfig =
	// TemplateConfigUtil.getHtmlConfig(HtmlConfig.PRODUCT_CONTENT_SZ);
	// ProductCategory productCategory = product.getProductCategory();
	// Map<String, Object> data = getCommonData();
	// data.put("product", product);
	// data.put("pathList",
	// productCategoryService.getProductCategoryPathList(product));
	// data.put("rootProductCategoryList",
	// productCategoryService.getRootProductCategoryList());
	// data.put("bestProductList",
	// productDao.getBestProductList(productCategory,
	// Product.MAX_BEST_PRODUCT_LIST_COUNT));
	// data.put("hotProductList", productDao.getHotProductList(productCategory,
	// Product.MAX_HOT_PRODUCT_LIST_COUNT));
	// data.put("newProductList", productDao.getNewProductList(productCategory,
	// Product.MAX_NEW_PRODUCT_LIST_COUNT));
	// String szHtmlFilePath = product.getSzHtmlFilePath();
	// String templateFilePath = htmlConfig.getTemplateFilePath();
	// buildHtml(templateFilePath, szHtmlFilePath, data);
	// }

	public void errorPageBuildHtml() {
		HtmlConfig htmlConfig = TemplateConfigUtil.getHtmlConfig(HtmlConfig.ERROR_PAGE);
		Map<String, Object> data = getCommonData();
		data.put("errorContent", "系统出现异常，请与管理员联系！");
		String htmlFilePath = htmlConfig.getHtmlFilePath();
		String templateFilePath = htmlConfig.getTemplateFilePath();
		buildHtml(templateFilePath, htmlFilePath, data);
	}

	public void errorPageAccessDeniedBuildHtml() {
		HtmlConfig htmlConfig = TemplateConfigUtil.getHtmlConfig(HtmlConfig.ERROR_PAGE);
		Map<String, Object> data = getCommonData();
		data.put("errorContent", "您无此访问权限！");
		String htmlFilePath = htmlConfig.getHtmlFilePath();
		String templateFilePath = htmlConfig.getTemplateFilePath();
		buildHtml(templateFilePath, htmlFilePath, data);
	}

	public void errorPage500BuildHtml() {
		HtmlConfig htmlConfig = TemplateConfigUtil.getHtmlConfig(HtmlConfig.ERROR_PAGE_500);
		Map<String, Object> data = getCommonData();
		data.put("errorContent", "系统出现异常，请与管理员联系！");
		String htmlFilePath = htmlConfig.getHtmlFilePath();
		String templateFilePath = htmlConfig.getTemplateFilePath();
		buildHtml(templateFilePath, htmlFilePath, data);
	}

	public void errorPage404BuildHtml() {
		HtmlConfig htmlConfig = TemplateConfigUtil.getHtmlConfig(HtmlConfig.ERROR_PAGE_404);
		Map<String, Object> data = getCommonData();
		data.put("errorContent", "您访问的页面不存在！");
		String htmlFilePath = htmlConfig.getHtmlFilePath();
		String templateFilePath = htmlConfig.getTemplateFilePath();
		buildHtml(templateFilePath, htmlFilePath, data);
	}

	public void errorPage403BuildHtml() {
		HtmlConfig htmlConfig = TemplateConfigUtil.getHtmlConfig(HtmlConfig.ERROR_PAGE_403);
		Map<String, Object> data = getCommonData();
		data.put("errorContent", "系统出现异常，请与管理员联系！");
		String htmlFilePath = htmlConfig.getHtmlFilePath();
		String templateFilePath = htmlConfig.getTemplateFilePath();
		buildHtml(templateFilePath, htmlFilePath, data);
	}

}