package cn.com.sinosoft.action.shop;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;

import cn.com.sinosoft.bean.CartItemCookie;
import cn.com.sinosoft.bean.Pager;
import cn.com.sinosoft.bean.Pager.OrderType;
import cn.com.sinosoft.entity.CartItem;
import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.Product;
import cn.com.sinosoft.entity.ProductCategory;
import cn.com.sinosoft.service.ProductCategoryService;
import cn.com.sinosoft.service.ProductService;
import cn.com.sinosoft.util.SystemConfigUtil;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 前台Action类 - 商品
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTD555C0667F7008EC5F71FFFB78A4F6AB
 * ============================================================================
 */

@ParentPackage("shop")
public class ProductAction extends BaseShopAction {

	private static final long serialVersionUID = -4969421249817468001L;

	private String productId;// 产品Id
	private ProductCategory productCategory;
	private String orderType;// 排序类型
	private String viewType;// 查看类型
	private String sky;// 公司简介测试
	private String introduction;// 公司介绍
	
	private List<ProductCategory> rootProductCategoryList;
	
	private List<Product> bestProductList;
	private List<Product> hotProductList;
	private List<Product> newProductList;
	private List<ProductCategory> pathList;
	
	@Resource
	private ProductService productService;
	@Resource
	private ProductCategoryService productCategoryService;
	
//====================公司简介显示====================================
	@InputConfig(resultName = "error")
	public String ajaxList() {
		List<Map<String, String>> jsonList = new ArrayList<Map<String, String>>();
		sky ="公司简介测试朱勇1111";
		
		Product product = new Product();
		product = productService.load(id);
		introduction = product.getBrand().getIntroduction();//产品介绍
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put("sky", sky);
		jsonMap.put("introduction", introduction);
		jsonList.add(0, jsonMap);
		JSONArray jsonArray = JSONArray.fromObject(jsonList);
		return ajaxJson(jsonArray.toString());
	}
//====================公司简介显示====================================

	@InputConfig(resultName = "error")
	public String listmore(){
		bestProductList = productService.getBestProductList(Product.MAX_BEST_PRODUCT_LIST_COUNT);
		
		return "listmore";
	}
	@InputConfig(resultName = "error")
	public String listhome(){
		productCategory = productCategoryService.load(id);
		bestProductList = productService.getBestProductList(productCategory, Product.MAX_BEST_PRODUCT_LIST_COUNT);
		hotProductList = productService.getHotProductList(productCategory, Product.MAX_HOT_PRODUCT_LIST_COUNT);
		newProductList = productService.getNewProductList(productCategory, Product.MAX_NEW_PRODUCT_LIST_COUNT);
		pathList = productCategoryService.getProductCategoryPathList(productCategory);
		
		if (pager == null) {
			pager = new Pager();
			pager.setPageSize(Product.DEFAULT_PRODUCT_LIST_PAGE_SIZE);
		}
		pager.setProperty(null);
		pager.setKeyword(null);
		
		if (StringUtils.equalsIgnoreCase(orderType, "priceAsc")) {
			pager.setOrderBy("price");
			pager.setOrderType(OrderType.asc);
		} else if (StringUtils.equalsIgnoreCase(orderType, "priceDesc")) {
			pager.setOrderBy("price");
			pager.setOrderType(OrderType.desc);
		} else if (StringUtils.equalsIgnoreCase(orderType, "dateAsc")) {
			pager.setOrderBy("createDate");
			pager.setOrderType(OrderType.asc);
		} else {
			pager.setOrderBy(null);
			pager.setOrderType(null);
		}
		
		pagerhome = productService.getProductHomePager(pager);
		if (StringUtils.equalsIgnoreCase(viewType, "tableType")) {
			return "hometable_list";
		} else {
			return "homepicture_list";
		}
	}
	
	@InputConfig(resultName = "error")
	public String szListHome(){
		productCategory = productCategoryService.load(id);
		bestProductList = productService.getBestProductList(productCategory, Product.MAX_BEST_PRODUCT_LIST_COUNT);
		hotProductList = productService.getHotProductList(productCategory, Product.MAX_HOT_PRODUCT_LIST_COUNT);
		newProductList = productService.getNewProductList(productCategory, Product.MAX_NEW_PRODUCT_LIST_COUNT);
		pathList = productCategoryService.getProductCategoryPathList(productCategory);
		
		if (pager == null) {
			pager = new Pager();
			pager.setPageSize(Product.DEFAULT_PRODUCT_LIST_PAGE_SIZE);
		}
		pager.setProperty(null);
		pager.setKeyword(null);
		
		if (StringUtils.equalsIgnoreCase(orderType, "priceAsc")) {
			pager.setOrderBy("price");
			pager.setOrderType(OrderType.asc);
		} else if (StringUtils.equalsIgnoreCase(orderType, "priceDesc")) {
			pager.setOrderBy("price");
			pager.setOrderType(OrderType.desc);
		} else if (StringUtils.equalsIgnoreCase(orderType, "dateAsc")) {
			pager.setOrderBy("createDate");
			pager.setOrderType(OrderType.asc);
		} else {
			pager.setOrderBy(null);
			pager.setOrderType(null);
		}
		
		pagerhome = productService.getProductHomePager(pager);
		if (StringUtils.equalsIgnoreCase(viewType, "tableType")) {
			return "hometable_list";
		} else {
			return "szhomepicture_list";
		}
	}
	
	@InputConfig(resultName = "error")
	public String szList() {
		productCategory = productCategoryService.load(id);
		bestProductList = productService.getBestProductList(productCategory, Product.MAX_BEST_PRODUCT_LIST_COUNT);
		hotProductList = productService.getHotProductList(productCategory, Product.MAX_HOT_PRODUCT_LIST_COUNT);
		newProductList = productService.getNewProductList(productCategory, Product.MAX_NEW_PRODUCT_LIST_COUNT);
		pathList = productCategoryService.getProductCategoryPathList(productCategory);
		
		if (pager == null) {
			pager = new Pager();
			pager.setPageSize(Product.DEFAULT_PRODUCT_LIST_PAGE_SIZE);
		}
		pager.setProperty(null);
		pager.setKeyword(null);
		
		if (StringUtils.equalsIgnoreCase(orderType, "priceAsc")) {
			pager.setOrderBy("price");
			pager.setOrderType(OrderType.asc);
		} else if (StringUtils.equalsIgnoreCase(orderType, "priceDesc")) {
			pager.setOrderBy("price");
			pager.setOrderType(OrderType.desc);
		} else if (StringUtils.equalsIgnoreCase(orderType, "dateAsc")) {
			pager.setOrderBy("createDate");
			pager.setOrderType(OrderType.asc);
		} else {
			pager.setOrderBy(null);
			pager.setOrderType(null);
		}
		
		pager = productService.getProductPager(productCategory, pager);
	//	pagerhome = productService.getProductHomePager(pager);
	//	pager = productService.getProductHomePager(pager);
		
		if (StringUtils.equalsIgnoreCase(viewType, "tableType")) {
			return "table_list";
		} else {
			return "szpicture_list";
		}
	}
	
	@InputConfig(resultName = "error")
	public String list() {
		productCategory = productCategoryService.load(id);
		bestProductList = productService.getBestProductList(productCategory, Product.MAX_BEST_PRODUCT_LIST_COUNT);
		hotProductList = productService.getHotProductList(productCategory, Product.MAX_HOT_PRODUCT_LIST_COUNT);
		newProductList = productService.getNewProductList(productCategory, Product.MAX_NEW_PRODUCT_LIST_COUNT);
		pathList = productCategoryService.getProductCategoryPathList(productCategory);
		
		if (pager == null) {
			pager = new Pager();
			pager.setPageSize(Product.DEFAULT_PRODUCT_LIST_PAGE_SIZE);
		}
		pager.setProperty(null);
		pager.setKeyword(null);
		
		if (StringUtils.equalsIgnoreCase(orderType, "priceAsc")) {
			pager.setOrderBy("price");
			pager.setOrderType(OrderType.asc);
		} else if (StringUtils.equalsIgnoreCase(orderType, "priceDesc")) {
			pager.setOrderBy("price");
			pager.setOrderType(OrderType.desc);
		} else if (StringUtils.equalsIgnoreCase(orderType, "dateAsc")) {
			pager.setOrderBy("createDate");
			pager.setOrderType(OrderType.asc);
		} else {
			pager.setOrderBy(null);
			pager.setOrderType(null);
		}
		
		pager = productService.getProductPager(productCategory, pager);
	//	pagerhome = productService.getProductHomePager(pager);
	//	pager = productService.getProductHomePager(pager);
		
		if (StringUtils.equalsIgnoreCase(viewType, "tableType")) {
			return "table_list";
		} else {
			return "picture_list";
		}
	}
	
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "pager.keyword", message = "搜索关键词不允许为空!") 
		}
	)
	@InputConfig(resultName = "error")
	public String search() throws Exception {
		bestProductList = productService.getBestProductList(Product.MAX_BEST_PRODUCT_LIST_COUNT);
		hotProductList = productService.getHotProductList(Product.MAX_HOT_PRODUCT_LIST_COUNT);
		newProductList = productService.getNewProductList(Product.MAX_NEW_PRODUCT_LIST_COUNT);
		
		if (pager == null) {
			pager = new Pager();
			pager.setPageSize(Product.DEFAULT_PRODUCT_LIST_PAGE_SIZE);
		}
		
		if (StringUtils.equalsIgnoreCase(orderType, "priceAsc")) {
			pager.setOrderBy("price");
			pager.setOrderType(OrderType.asc);
		} else if (StringUtils.equalsIgnoreCase(orderType, "priceDesc")) {
			pager.setOrderBy("price");
			pager.setOrderType(OrderType.desc);
		} else if (StringUtils.equalsIgnoreCase(orderType, "dateAsc")) {
			pager.setOrderBy("createDate");
			pager.setOrderType(OrderType.asc);
		} else {
			pager.setOrderBy(null);
			pager.setOrderType(null);
		}
		
//		pager = productService.search(pager);
		
		if (StringUtils.equalsIgnoreCase(viewType, "tableType")) {
			return "table_search";
		} else {
			return "picture_search";
		}
	}

	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getViewType() {
		return viewType;
	}

	public void setViewType(String viewType) {
		this.viewType = viewType;
	}
	
	public List<ProductCategory> getRootProductCategoryList() {
		rootProductCategoryList = productCategoryService.getRootProductCategoryList();
		return rootProductCategoryList;
	}

	public void setRootProductCategoryList(List<ProductCategory> rootProductCategoryList) {
		this.rootProductCategoryList = rootProductCategoryList;
	}

	public List<Product> getBestProductList() {
		return bestProductList;
	}

	public void setBestProductList(List<Product> bestProductList) {
		this.bestProductList = bestProductList;
	}

	public List<Product> getHotProductList() {
		return hotProductList;
	}

	public void setHotProductList(List<Product> hotProductList) {
		this.hotProductList = hotProductList;
	}

	public List<Product> getNewProductList() {
		return newProductList;
	}

	public void setNewProductList(List<Product> newProductList) {
		this.newProductList = newProductList;
	}

	public List<ProductCategory> getPathList() {
		return pathList;
	}

	public void setPathList(List<ProductCategory> pathList) {
		this.pathList = pathList;
	}

	public String getSky() {
		return sky;
	}

	public void setSky(String sky) {
		this.sky = sky;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	
	
}