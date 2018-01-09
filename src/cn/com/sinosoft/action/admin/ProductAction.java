package cn.com.sinosoft.action.admin;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.jsp.PageContext;


import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import cn.com.sinosoft.bean.ProductImage;
import cn.com.sinosoft.bean.SystemConfig;
import cn.com.sinosoft.bean.SystemConfig.PointType;
import cn.com.sinosoft.entity.Article;
import cn.com.sinosoft.entity.Brand;
import cn.com.sinosoft.entity.Channel;
import cn.com.sinosoft.entity.OrderItem;
import cn.com.sinosoft.entity.Product;
import cn.com.sinosoft.entity.ProductAttribute;
import cn.com.sinosoft.entity.ProductCategory;
import cn.com.sinosoft.entity.ProductInsAttribute;
import cn.com.sinosoft.entity.ProductInsType;
import cn.com.sinosoft.entity.ProductType;
import cn.com.sinosoft.entity.Order.OrderStatus;
import cn.com.sinosoft.entity.Product.WeightUnit;
import cn.com.sinosoft.entity.ProductAttribute.AttributeType;
import cn.com.sinosoft.entity.ProductInsAttribute.AttributeInsType;
import cn.com.sinosoft.service.ArticleService;
import cn.com.sinosoft.service.BrandService;
import cn.com.sinosoft.service.ProductAttributeService;
import cn.com.sinosoft.service.ProductCategoryService;
import cn.com.sinosoft.service.ProductImageService;
import cn.com.sinosoft.service.ProductInsAttributeService;
import cn.com.sinosoft.service.ProductInsTypeService;
import cn.com.sinosoft.service.ProductService;
import cn.com.sinosoft.service.ProductTypeService;
import cn.com.sinosoft.util.CommonUtil;
import cn.com.sinosoft.util.SerialNumberUtil;

import com.opensymphony.oscache.base.Cache;
import com.opensymphony.oscache.web.ServletCacheAdministrator;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类 - 产品
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT483FBE0A69F1A3F1D081F3F2D838E89C
 * ============================================================================
 */

@ParentPackage("admin")
public class ProductAction extends BaseAdminAction {

	private static final long serialVersionUID = -4433964283757192334L;

	private File duty;	//新添加商品保险条款
	private String myDuty;//保存旧版本duty
	private String dutyFileName;

	private File app;	//新添加商品保险条款
	private String myApp;	//保存旧版本app
	private String appFileName;
	
	private String professionalPath;// 商品专家点评 存储【文章id】
	private String videoPath;// 商品视频介绍 存储【文章id】
	private File[] productImages;
//	private File[] dutyDownLoads;
	private String[] productImagesFileName;
	private String[] productImageParameterTypes;
	private String[] productImageIds;
//	private String[] dutyFileName;
//	private String[] dutyParameterTypes;
//	private String[] dutyIds;
//=======添加文章搜索【用于专家点评和视频介绍】====================================
	private List<Article> articleTreeList;
	private List<Article> articleTreeListZj;
	private List<Article> articleTreeListSp;
//=======添加文章搜索【用于专家点评和视频介绍】====================================
	private Product product;
//=======渠道====================================
	private List<Channel> productCategoryChannelList;
	private List<Channel> channelList;
	
	@Resource
	private ArticleService articleService;
	@Resource
	private ProductService productService;
	@Resource
	private ProductCategoryService productCategoryService;
	@Resource
	private BrandService brandService;
	@Resource
	private ProductImageService productImageService;
	/**
	 * 以下两个type和attribute要被仿照成：
	 * ProductInsuranceTypeService
	 * ProductInsuranceAttributeService
	 */
	@Resource
	ProductInsTypeService productInsTypeService;
	@Resource
	ProductInsAttributeService productInsAttributeService;
	
	@Resource
	private ProductTypeService productTypeService;
	@Resource
	private ProductAttributeService productAttributeService;
	
	
	// ajax根据渠道名称获取所有渠道下的产品分类名称
			@SuppressWarnings("unchecked")
			public String findAllChannelproductCategory()  {
				String channelnames=channelList.get(0).getId().substring(0, channelList.get(0).getId().length()-1);
				String[] channelname=channelnames.split(",");
				List<ProductCategory> allproductCategoryName=productCategoryService.getRootProductCategoryList(channelname);
				//if (productCategoryChannelList.contains(channelName)) {
					StringBuilder stringBuilder = new StringBuilder();
					String[] methodNameArray = new String[allproductCategoryName.size()];
					stringBuilder.append("<option value=\"" + "" + "\">" + "顶级分类"+ "</option>");
					for (int i = 0; i < allproductCategoryName.size(); i++) {
						methodNameArray[i] = allproductCategoryName.get(i).getName();
						if(allproductCategoryName.get(i).getLevel()==0){
							stringBuilder.append("<option value=\"" + allproductCategoryName.get(i).getId() + "\">" + allproductCategoryName.get(i).getName() + "</option>");
						}
						else{
							stringBuilder.append("<option value=\"" + allproductCategoryName.get(i).getId() + "\">" +"------"+ allproductCategoryName.get(i).getName() + "</option>");
						}
						
					}
					if (stringBuilder.length() == 0) {
						stringBuilder.append("<option value=\"noValue\">无产品分类</option>");
					}
					return ajaxText(stringBuilder.toString());
				//}
				//return null;
			}
			

	// 添加
	public String add() {
		return INPUT;
	}

	// 编辑
	public String edit() {
		product = productService.load(id);
		return INPUT;
	}

	// 列表
	public String list() {
		pager = productService.findByPager(pager);
		return LIST;
	}

	// 删除
	public String delete() throws Exception {
//		for (String id : ids) {
//			Product product = productService.load(id);
//			Set<OrderItem> orderItemSet = product.getOrderItemSet();
//			for (OrderItem orderItem : orderItemSet) {
////				if (orderItem.getOrder().getOrderStatus() != OrderStatus.completed && orderItem.getOrder().getOrderStatus() != OrderStatus.invalid) {
////					return ajaxJsonErrorMessage("产品[" + product.getName() + "]订单处理未完成，删除失败！");
////				}
//			}
//		}
		productService.delete(ids);
		flushCache();
		return ajaxJsonSuccessMessage("删除成功！");
	}

	// 保存
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "product.name", message = "产品名称不允许为空!") 
		}, 
		requiredFields = {
			@RequiredFieldValidator(fieldName = "product.price", message = "销售价不允许为空!"),
			@RequiredFieldValidator(fieldName = "product.marketPrice", message = "市场价不允许为空!"),
			@RequiredFieldValidator(fieldName = "product.weight", message = "保额不允许为空!"),
			@RequiredFieldValidator(fieldName = "product.isMarketable", message = "是否上架不允许为空!"),
			@RequiredFieldValidator(fieldName = "product.isBest", message = "是否精品不允许为空!"),
			@RequiredFieldValidator(fieldName = "product.isNew", message = "是否新品不允许为空!"),
			@RequiredFieldValidator(fieldName = "product.isHot", message = "是否热销不允许为空!"),
			@RequiredFieldValidator(fieldName = "product.productCategory.id", message = "所属分类不允许为空!")
		},
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "product.point", min = "0", message = "积分必须为零或正整数!"),
			@IntRangeFieldValidator(fieldName = "product.store", min = "0", message = "库存必须为零或正整数!")
		}
	)
	@InputConfig(resultName = "error")
	public String save() throws Exception {

		if (product.getPrice().compareTo(new BigDecimal("0")) < 0) {
			addActionError("销售价不允许小于0");
			return ERROR;
		}
		if (product.getMarketPrice().compareTo(new BigDecimal("0")) < 0) {
			addActionError("市场价不允许小于0");
			return ERROR;
		}
		if (product.getWeight() < 0) {
			addActionError("保额只允许为正数或零!");
			return ERROR;
		}
		if (StringUtils.isNotEmpty(product.getProductSn())) {
			if (productService.isExist("productSn", product.getProductSn())) {
				addActionError("产品号重复,请重新输入!");
				return ERROR;
			}
		} else {
			String productSn = SerialNumberUtil.buildProductSn();
			product.setProductSn(productSn);
		}
		product.setChannelSet(new HashSet<Channel>(channelList));
		
		
		ProductType productType = product.getProductType();
		if (productType != null && StringUtils.isNotEmpty(productType.getId())) {
			productType = productTypeService.load(productType.getId());
		} else {
			productType = null;
		}
		
		if (productType != null) {
			Map<ProductAttribute, List<String>> productAttributeMap = new HashMap<ProductAttribute, List<String>>();
			List<ProductAttribute> enabledProductAttributeList = productAttributeService.getEnabledProductAttributeList(productType);
			for (ProductAttribute productAttribute : enabledProductAttributeList) {
				String[] parameterValues = getParameterValues(productAttribute.getId());
				if (productAttribute.getIsRequired() && (parameterValues == null || parameterValues.length == 0 || StringUtils.isEmpty(parameterValues[0]))) {
					addActionError(productAttribute.getName() + "不允许为空!");
					return ERROR;
				}
				if (parameterValues != null && parameterValues.length > 0 && StringUtils.isNotEmpty(parameterValues[0])) {
					if (productAttribute.getAttributeType() == AttributeType.number) {
						Pattern pattern = Pattern.compile("^-?(?:\\d+|\\d{1,3}(?:,\\d{3})+)(?:\\.\\d+)?");
						Matcher matcher = pattern.matcher(parameterValues[0]);
						if (!matcher.matches()) {
							addActionError(productAttribute.getName() + "只允许输入数字!");
							return ERROR;
						}
					}
					if (productAttribute.getAttributeType() == AttributeType.alphaint) {
						Pattern pattern = Pattern.compile("[a-zA-Z]+");
						Matcher matcher = pattern.matcher(parameterValues[0]);
						if (!matcher.matches()) {
							addActionError(productAttribute.getName() + "只允许输入字母!");
							return ERROR;
						}
					}
					if (productAttribute.getAttributeType() == AttributeType.date) {
						Pattern pattern = Pattern.compile("\\d{4}[\\/-]\\d{1,2}[\\/-]\\d{1,2}");
						Matcher matcher = pattern.matcher(parameterValues[0]);
						if (!matcher.matches()) {
							addActionError(productAttribute.getName() + "日期格式错误!");
							return ERROR;
						}
					}
					if (productAttribute.getAttributeType() == AttributeType.select || productAttribute.getAttributeType() == AttributeType.checkbox) {
						List<String> attributeOptionList = productAttribute.getAttributeOptionList();
						for (String parameterValue : parameterValues) {
							if (!attributeOptionList.contains(parameterValue)) {
								addActionError("参数错误!");
								return ERROR;
							}
						}
					}
					productAttributeMap.put(productAttribute, Arrays.asList(parameterValues));
				}//endIf
			}//endFor
			product.setProductAttributeMap(productAttributeMap);
		}//endIf
		else {
			product.setProductAttributeMap(null);
		}
		product.setProductType(productType);//save()文件中的


		//=============productAction================================shang=====1===================================================//
		
		
		
		ProductInsType productInsType = product.getProductInsType();
		if (productInsType != null && StringUtils.isNotEmpty(productInsType.getId())) {
			productInsType = productInsTypeService.load(productInsType.getId());
		} else {
			productInsType = null;
		}
		
		if (productInsType != null) {
			Map<ProductInsAttribute, List<String>> productInsAttributeMap = new HashMap<ProductInsAttribute, List<String>>();
			List<ProductInsAttribute> enabledProductInsAttributeList = productInsAttributeService.getEnabledProductInsAttributeList(productInsType);
			for (ProductInsAttribute productInsAttribute : enabledProductInsAttributeList) {
				String[] parameterValues = getParameterValues(productInsAttribute.getId());
				if (productInsAttribute.getIsRequired() && (parameterValues == null || parameterValues.length == 0 || StringUtils.isEmpty(parameterValues[0]))) {
					addActionError(productInsAttribute.getName() + "不允许为空!");
					return ERROR;
				}
				if (parameterValues != null && parameterValues.length > 0 && StringUtils.isNotEmpty(parameterValues[0])) {
					if (productInsAttribute.getAttributeInsType() == AttributeInsType.number) {
						
						Pattern pattern = Pattern.compile("^-?(?:\\d+|\\d{1,3}(?:,\\d{3})+)(?:\\.\\d+)?");
						Matcher matcher = pattern.matcher(parameterValues[0]);
						if (!matcher.matches()) {
							addActionError(productInsAttribute.getName() + "只允许输入数字!");
							return ERROR;
						}
					}
					if (productInsAttribute.getAttributeInsType() == AttributeInsType.alphaint) {
						Pattern pattern = Pattern.compile("[a-zA-Z]+");
						Matcher matcher = pattern.matcher(parameterValues[0]);
						if (!matcher.matches()) {
							addActionError(productInsAttribute.getName() + "只允许输入字母!");
							return ERROR;
						}
					}
					if (productInsAttribute.getAttributeInsType() == AttributeInsType.date) {
						Pattern pattern = Pattern.compile("\\d{4}[\\/-]\\d{1,2}[\\/-]\\d{1,2}");
						Matcher matcher = pattern.matcher(parameterValues[0]);
						if (!matcher.matches()) {
							addActionError(productInsAttribute.getName() + "日期格式错误!");
							return ERROR;
						}
					}
					if (productInsAttribute.getAttributeInsType() == AttributeInsType.select || productInsAttribute.getAttributeInsType() == AttributeInsType.checkbox) {
						List<String> attributeOptionList = productInsAttribute.getAttributeOptionList();
						for (String parameterValue : parameterValues) {
							if (!attributeOptionList.contains(parameterValue)) {
								addActionError("参数错误!");
								return ERROR;
							}
						}
					}
					productInsAttributeMap.put(productInsAttribute, Arrays.asList(parameterValues));
				}
			}
			product.setProductInsAttributeMap(productInsAttributeMap);
		} else {
			product.setProductInsAttributeMap(null);
		}
		product.setProductInsType(productInsType);
		
		
		//===========productAction========================xia===========1=======================================================//
		
		
		if (productImages != null) {
			String allowedUploadImageExtension = getSystemConfig().getAllowedUploadImageExtension().toLowerCase();
			if (StringUtils.isEmpty(allowedUploadImageExtension)) {
				addActionError("不允许上传图片文件!");
				return ERROR;
			}
			for(int i = 0; i < productImages.length; i ++) {
				String productImageExtension =  StringUtils.substringAfterLast(productImagesFileName[i], ".").toLowerCase();
				String[] imageExtensionArray = allowedUploadImageExtension.split(SystemConfig.EXTENSION_SEPARATOR);
				if (!ArrayUtils.contains(imageExtensionArray, productImageExtension)) {
					addActionError("只允许上传图片文件类型: " + allowedUploadImageExtension + "!");
					return ERROR;
				}
				if (getSystemConfig().getUploadLimit() != 0 && productImages[i].length() > getSystemConfig().getUploadLimit() * 1024) {
					addActionError("此上传文件大小超出限制!");
					return ERROR;
				}
			}
		}
		
		if (StringUtils.isEmpty(product.getBrand().getId())) {
			product.setBrand(null);
		}
		
		if (getSystemConfig().getPointType() == PointType.productSet) {
			if (product.getPoint() == null) {
				addActionError("积分不允许为空!");
				return ERROR;
			}
		} else {
			product.setPoint(0);
		}
		
		List<ProductImage> productImageList = new ArrayList<ProductImage>();
		if (productImages != null && productImages.length > 0) {
			for(int i = 0; i < productImages.length; i ++) {
				ProductImage productImage = productImageService.buildProductImage(productImages[i]);
				productImageList.add(productImage);
			}
		}
		product.setProductImageList(productImageList);
//======================9.21条款======================================================================
/*/1.		
		if (duty != null) {
			UploadAction tUploadAction = new UploadAction();
			tUploadAction.setUpload(duty);
			tUploadAction.setUploadFileName(dutyFileName);
			System.out.println("duty="+duty.getName());
			System.out.println("dutyFileName="+dutyFileName);
			tUploadAction.file();
		
			
			 String file = tUploadAction.file();
		//	String PathDown = tUploadAction.file();
			System.out.println("PathDown="+file);
			product.setDuty(file);
		}
*/
		if (duty != null){
			
			String allowedUploadFileExtension = getSystemConfig().getAllowedUploadFileExtension().toLowerCase();
			if (StringUtils.isEmpty(allowedUploadFileExtension)){
				return ajaxJsonErrorMessage("不允许上传文件!");
			}
			String fileExtension =  StringUtils.substringAfterLast(dutyFileName, ".").toLowerCase();
			String[] fileExtensionArray = allowedUploadFileExtension.split(SystemConfig.EXTENSION_SEPARATOR);
			if (!ArrayUtils.contains(fileExtensionArray, fileExtension)) {
				return ajaxJsonErrorMessage("只允许上传文件类型: " + allowedUploadFileExtension + "!");
			}
			int uploadLimit = getSystemConfig().getUploadLimit() * 1024;
			if (uploadLimit != 0) {
				if (duty != null && duty.length() > uploadLimit) {
					return ajaxJsonErrorMessage("文件大小超出限制!");
				}
			}
			File uploadFileDir = new File(ServletActionContext.getServletContext().getRealPath(SystemConfig.UPLOAD_FILE_DIR));
			if (!uploadFileDir.exists()) {
				uploadFileDir.mkdirs();
			}
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
			String dateString = simpleDateFormat.format(new Date());
			String uploadFilePath = SystemConfig.UPLOAD_FILE_DIR + dateString + "/" + CommonUtil.getUUID() + "." + fileExtension;
			File file = new File(ServletActionContext.getServletContext().getRealPath(uploadFilePath));
			FileUtils.copyFile(duty, file);
			product.setDuty(uploadFilePath);
		}
//======================9.21条款======================================================================
//============================12.27理赔申请书下载======================================================		
		if (app != null){
			
			String allowedUploadFileExtension = getSystemConfig().getAllowedUploadFileExtension().toLowerCase();
			if (StringUtils.isEmpty(allowedUploadFileExtension)){
				return ajaxJsonErrorMessage("不允许上传文件!");
			}
			String fileExtension =  StringUtils.substringAfterLast(appFileName, ".").toLowerCase();
			String[] fileExtensionArray = allowedUploadFileExtension.split(SystemConfig.EXTENSION_SEPARATOR);
			if (!ArrayUtils.contains(fileExtensionArray, fileExtension)) {
				return ajaxJsonErrorMessage("只允许上传文件类型: " + allowedUploadFileExtension + "!");
			}
			int uploadLimit = getSystemConfig().getUploadLimit() * 1024;
			if (uploadLimit != 0) {
				if (app != null && app.length() > uploadLimit) {
					return ajaxJsonErrorMessage("文件大小超出限制!");
				}
			}
			File uploadFileDir = new File(ServletActionContext.getServletContext().getRealPath(SystemConfig.UPLOAD_FILE_DIR));
			if (!uploadFileDir.exists()) {
				uploadFileDir.mkdirs();
			}
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
			String dateString = simpleDateFormat.format(new Date());
			String uploadFilePath = SystemConfig.UPLOAD_FILE_DIR + dateString + "/" + CommonUtil.getUUID() + "." + fileExtension;
			File file = new File(ServletActionContext.getServletContext().getRealPath(uploadFilePath));
			FileUtils.copyFile(app, file);
			product.setApp(uploadFilePath);
		}

//============================12.27理赔申请书下载======================================================		
		product.setFreezeStore(0);
		product.setFavoriteMemberSet(null);
		product.setWeightUnit((WeightUnit.g));
//		product.setDutyDownLoadPath(dutyDownLoadPath);
		productService.save(product);
		flushCache();
		redirectionUrl = "product!list.action";
		return SUCCESS;
	}

	// 更新
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "product.name", message = "产品名称不允许为空!") 
		}, 
		requiredFields = {
			@RequiredFieldValidator(fieldName = "product.price", message = "销售价不允许为空!"),
			@RequiredFieldValidator(fieldName = "product.marketPrice", message = "市场价不允许为空!"),
			@RequiredFieldValidator(fieldName = "product.weight", message = "保额不允许为空!"),
			@RequiredFieldValidator(fieldName = "product.isMarketable", message = "是否上架不允许为空!"),
			@RequiredFieldValidator(fieldName = "product.isBest", message = "是否精品不允许为空!"),
			@RequiredFieldValidator(fieldName = "product.isNew", message = "是否新品不允许为空!"),
			@RequiredFieldValidator(fieldName = "product.isHot", message = "是否热销不允许为空!"),
			@RequiredFieldValidator(fieldName = "product.productCategory.id", message = "所属分类不允许为空!")
		},
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "product.point", min = "0", message = "积分必须为零或正整数!"),
			@IntRangeFieldValidator(fieldName = "product.store", min = "0", message = "库存必须为零或正整数!")
		}
	)
	@InputConfig(resultName = "error")
	public String update() throws Exception {
		if (product.getPrice().compareTo(new BigDecimal("0")) < 0) {
			addActionError("销售价不允许小于0");
			return ERROR;
		}
		if (product.getMarketPrice().compareTo(new BigDecimal("0")) < 0) {
			addActionError("市场价不允许小于0");
			return ERROR;
		}
		if (product.getWeight() < 0) {
			addActionError("保额只允许为正数或零!");
			return ERROR;
		}
		Product persistent = productService.load(id);
		if (StringUtils.isNotEmpty(product.getProductSn())) {
			if (!productService.isUnique("productSn", persistent.getProductSn(), product.getProductSn())) {
				addActionError("货号重复,请重新输入!");
				return ERROR;
			}
		} else {
			String productSn = SerialNumberUtil.buildProductSn();
			product.setProductSn(productSn);
		}
		
		
		product.setChannelSet(new HashSet<Channel>(channelList));
		ProductType productType = product.getProductType();
		if (productType != null && StringUtils.isNotEmpty(productType.getId())) {
			productType = productTypeService.load(productType.getId());
		} else {
			productType = null;
		}
		
		if (productType != null) {
			Map<ProductAttribute, List<String>> productAttributeMap = new HashMap<ProductAttribute, List<String>>();
			List<ProductAttribute> enabledProductAttributeList = productAttributeService.getEnabledProductAttributeList(productType);
			for (ProductAttribute productAttribute : enabledProductAttributeList) {
				String[] parameterValues = getParameterValues(productAttribute.getId());
				if (productAttribute.getIsRequired() && (parameterValues == null || parameterValues.length == 0 || StringUtils.isEmpty(parameterValues[0]))) {
					addActionError(productAttribute.getName() + "不允许为空!");
					return ERROR;
				}
				if (parameterValues != null && parameterValues.length > 0 && StringUtils.isNotEmpty(parameterValues[0])) {
					if (productAttribute.getAttributeType() == AttributeType.number) {
						Pattern pattern = Pattern.compile("^-?(?:\\d+|\\d{1,3}(?:,\\d{3})+)(?:\\.\\d+)?");
						Matcher matcher = pattern.matcher(parameterValues[0]);
						if (!matcher.matches()) {
							addActionError(productAttribute.getName() + "只允许输入数字!");
							return ERROR;
						}
					}
					if (productAttribute.getAttributeType() == AttributeType.alphaint) {
						Pattern pattern = Pattern.compile("[a-zA-Z]+");
						Matcher matcher = pattern.matcher(parameterValues[0]);
						if (!matcher.matches()) {
							addActionError(productAttribute.getName() + "只允许输入字母!");
							return ERROR;
						}
					}
					if (productAttribute.getAttributeType() == AttributeType.date) {
						Pattern pattern = Pattern.compile("\\d{4}[\\/-]\\d{1,2}[\\/-]\\d{1,2}");
						Matcher matcher = pattern.matcher(parameterValues[0]);
						if (!matcher.matches()) {
							addActionError(productAttribute.getName() + "日期格式错误!");
							return ERROR;
						}
					}
					if (productAttribute.getAttributeType() == AttributeType.select || productAttribute.getAttributeType() == AttributeType.checkbox) {
						List<String> attributeOptionList = productAttribute.getAttributeOptionList();
						for (String parameterValue : parameterValues) {
							if (!attributeOptionList.contains(parameterValue)) {
								addActionError("参数错误!");
								return ERROR;
							}
						}
					}
					productAttributeMap.put(productAttribute, Arrays.asList(parameterValues));
				}
			}
			product.setProductAttributeMap(productAttributeMap);
		} else {
			product.setProductAttributeMap(null);
		}
		product.setProductType(productType);
		
		//========================================shang===============================2==================================================//
		
		
		
		
		ProductInsType productInsType = product.getProductInsType();
		if (productInsType != null && StringUtils.isNotEmpty(productInsType.getId())) {
			productInsType = productInsTypeService.load(productInsType.getId());
		} else {
			productInsType = null;
		}
		
		
		if (productInsType != null) {
			Map<ProductInsAttribute, List<String>> productInsAttributeMap = new HashMap<ProductInsAttribute, List<String>>();
			List<ProductInsAttribute> enabledProductInsAttributeList = productInsAttributeService.getEnabledProductInsAttributeList(productInsType);
			for (ProductInsAttribute productInsAttribute : enabledProductInsAttributeList) {
				String[] parameterValues = getParameterValues(productInsAttribute.getId());
				if (productInsAttribute.getIsRequired() && (parameterValues == null || parameterValues.length == 0 || StringUtils.isEmpty(parameterValues[0]))) {
					addActionError(productInsAttribute.getName() + "不允许为空!");
					return ERROR;
				}
				if (parameterValues != null && parameterValues.length > 0 && StringUtils.isNotEmpty(parameterValues[0])) {
					if (productInsAttribute.getAttributeInsType() == AttributeInsType.number) {
						Pattern pattern = Pattern.compile("^-?(?:\\d+|\\d{1,3}(?:,\\d{3})+)(?:\\.\\d+)?");
						Matcher matcher = pattern.matcher(parameterValues[0]);
						if (!matcher.matches()) {
							addActionError(productInsAttribute.getName() + "只允许输入数字!");
							return ERROR;
						}
					}
					if (productInsAttribute.getAttributeInsType() == AttributeInsType.alphaint) {
						Pattern pattern = Pattern.compile("[a-zA-Z]+");
						Matcher matcher = pattern.matcher(parameterValues[0]);
						if (!matcher.matches()) {
							addActionError(productInsAttribute.getName() + "只允许输入字母!");
							return ERROR;
						}
					}
					if (productInsAttribute.getAttributeInsType() == AttributeInsType.date) {
						Pattern pattern = Pattern.compile("\\d{4}[\\/-]\\d{1,2}[\\/-]\\d{1,2}");
						Matcher matcher = pattern.matcher(parameterValues[0]);
						if (!matcher.matches()) {
							addActionError(productInsAttribute.getName() + "日期格式错误!");
							return ERROR;
						}
					}
					if (productInsAttribute.getAttributeInsType() == AttributeInsType.select || productInsAttribute.getAttributeInsType() == AttributeInsType.checkbox) {
						List<String> attributeOptionList = productInsAttribute.getAttributeOptionList();
						for (String parameterValue : parameterValues) {
							if (!attributeOptionList.contains(parameterValue)) {
								addActionError("参数错误!");
								return ERROR;
							}
						}
					}
					productInsAttributeMap.put(productInsAttribute, Arrays.asList(parameterValues));
				}
			}
			product.setProductInsAttributeMap(productInsAttributeMap);
		} else {
			product.setProductInsAttributeMap(null);
		}
		product.setProductInsType(productInsType);
		
		//=========================================xia=================================2===============================================//
		
		
		
		
		
		if (productImages != null) {
			String allowedUploadImageExtension = getSystemConfig().getAllowedUploadImageExtension().toLowerCase();
			if (StringUtils.isEmpty(allowedUploadImageExtension)) {
				addActionError("不允许上传图片文件!");
				return ERROR;
			}
			for(int i = 0; i < productImages.length; i ++) {
				String productImageExtension =  StringUtils.substringAfterLast(productImagesFileName[i], ".").toLowerCase();
				String[] imageExtensionArray = allowedUploadImageExtension.split(SystemConfig.EXTENSION_SEPARATOR);
				if (!ArrayUtils.contains(imageExtensionArray, productImageExtension)) {
					addActionError("只允许上传图片文件类型: " + allowedUploadImageExtension + "!");
					return ERROR;
				}
				if (getSystemConfig().getUploadLimit() != 0 && productImages[i].length() > getSystemConfig().getUploadLimit() * 1024) {
					addActionError("此上传文件大小超出限制!");
					return ERROR;
				}
			}
		}
		List<ProductImage> productImageList = new ArrayList<ProductImage>();
		if (productImageParameterTypes != null) {
			int index = 0;
			int productImageFileIndex = 0;
			int productImageIdIndex = 0;
			for (String parameterType : productImageParameterTypes) {
				if (StringUtils.equalsIgnoreCase(parameterType, "productImageFile")) {
//					System.out.println("zhuo=="+productImages[productImageFileIndex].getName());
					ProductImage destProductImage = productImageService.buildProductImage(productImages[productImageFileIndex]);
					productImageList.add(destProductImage);
					productImageFileIndex ++;
					index ++;
				} else if (StringUtils.equalsIgnoreCase(parameterType, "productImageId")) {
					ProductImage destProductImage = persistent.getProductImage(productImageIds[productImageIdIndex]);
					productImageList.add(destProductImage);
					productImageIdIndex ++;
					index ++;
				}
			}
		}
	
		if (StringUtils.isEmpty(product.getBrand().getId())) {
			product.setBrand(null);
		}
		if (getSystemConfig().getPointType() == PointType.productSet) {
			if (product.getPoint() == null) {
				addActionError("积分不允许为空!");
				return ERROR;
			}
		} else {
			product.setPoint(0);
		}
		if (product.getStore() == null) {
			product.setFreezeStore(0);
		} else {
			product.setFreezeStore(persistent.getFreezeStore());
		}
		
		List<ProductImage> persistentProductImageList = persistent.getProductImageList();
		if (persistentProductImageList != null) {
			for (ProductImage persistentProductImage : persistentProductImageList) {
				if (!productImageList.contains(persistentProductImage)) {
					productImageService.deleteFile(persistentProductImage);
				}
			}
		}
		product.setProductImageList(productImageList);
//=======update 条款=============
		if (duty != null){
			
			String allowedUploadFileExtension = getSystemConfig().getAllowedUploadFileExtension().toLowerCase();
			if (StringUtils.isEmpty(allowedUploadFileExtension)){
				return ajaxJsonErrorMessage("不允许上传文件!");
			}
			String fileExtension =  StringUtils.substringAfterLast(dutyFileName, ".").toLowerCase();
			String[] fileExtensionArray = allowedUploadFileExtension.split(SystemConfig.EXTENSION_SEPARATOR);
			if (!ArrayUtils.contains(fileExtensionArray, fileExtension)) {
				return ajaxJsonErrorMessage("只允许上传文件类型: " + allowedUploadFileExtension + "!");
			}
			int uploadLimit = getSystemConfig().getUploadLimit() * 1024;
			if (uploadLimit != 0) {
				if (duty != null && duty.length() > uploadLimit) {
					return ajaxJsonErrorMessage("文件大小超出限制!");
				}
			}
			File uploadFileDir = new File(ServletActionContext.getServletContext().getRealPath(SystemConfig.UPLOAD_FILE_DIR));
			if (!uploadFileDir.exists()) {
				uploadFileDir.mkdirs();
			}
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
			String dateString = simpleDateFormat.format(new Date());
			String uploadFilePath = SystemConfig.UPLOAD_FILE_DIR + dateString + "/" + CommonUtil.getUUID() + "." + fileExtension;
			File file = new File(ServletActionContext.getServletContext().getRealPath(uploadFilePath));
			FileUtils.copyFile(duty, file);
			product.setDuty(uploadFilePath);
		}else{
			product.setDuty(myDuty);
		}
//=======update 条款=============
//=======update 理赔申请书=============
		if (app != null){
			
			String allowedUploadFileExtension = getSystemConfig().getAllowedUploadFileExtension().toLowerCase();
			if (StringUtils.isEmpty(allowedUploadFileExtension)){
				return ajaxJsonErrorMessage("不允许上传文件!");
			}
			String fileExtension =  StringUtils.substringAfterLast(appFileName, ".").toLowerCase();
			String[] fileExtensionArray = allowedUploadFileExtension.split(SystemConfig.EXTENSION_SEPARATOR);
			if (!ArrayUtils.contains(fileExtensionArray, fileExtension)) {
				return ajaxJsonErrorMessage("只允许上传文件类型: " + allowedUploadFileExtension + "!");
			}
			int uploadLimit = getSystemConfig().getUploadLimit() * 1024;
			if (uploadLimit != 0) {
				if (app != null && app.length() > uploadLimit) {
					return ajaxJsonErrorMessage("文件大小超出限制!");
				}
			}
			File uploadFileDir = new File(ServletActionContext.getServletContext().getRealPath(SystemConfig.UPLOAD_FILE_DIR));
			if (!uploadFileDir.exists()) {
				uploadFileDir.mkdirs();
			}
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
			String dateString = simpleDateFormat.format(new Date());
			String uploadFilePath = SystemConfig.UPLOAD_FILE_DIR + dateString + "/" + CommonUtil.getUUID() + "." + fileExtension;
			File file = new File(ServletActionContext.getServletContext().getRealPath(uploadFilePath));
			FileUtils.copyFile(app, file);
			product.setApp(uploadFilePath);
		}else{
			product.setApp(myApp);
		}
//=======update 理赔申请书=============
		product.setWeightUnit((WeightUnit.g));
		BeanUtils.copyProperties(product, persistent, new String[] {"id", "createDate", "modifyDate", "htmlFilePath", "favoriteMemberSet", "cartItemSet", "orderItemSet", "deliveryItemSet"});
		productService.update(persistent);
		flushCache();
		redirectionUrl = "product!list.action";
		return SUCCESS;
	}
	
	// 获取所有品牌
	public List<Brand> getAllBrand() {
		return brandService.getAll();
	}

	// 获取所有产品类型
	public List<ProductType> getAllProductType() {
		return productTypeService.getAll();
	}
	// 获取所有产品类型Ins
	public List<ProductInsType> getAllProductInsType() {
		return productInsTypeService.getAll();
	}

	// 获取所有重量单位
	public List<WeightUnit> getAllWeightUnit() {
		List<WeightUnit> allWeightUnit = new ArrayList<WeightUnit>();
		for (WeightUnit weightUnit : WeightUnit.values()) {
			allWeightUnit.add(weightUnit);
		}
		return allWeightUnit;
	}
	
	// 更新页面缓存
	private void flushCache() {
		Cache cache = ServletCacheAdministrator.getInstance(getRequest().getSession().getServletContext()).getCache(getRequest(), PageContext.APPLICATION_SCOPE); 
		cache.flushAll(new Date());
	}
	
	// 获取产品分类树
	public List<ProductCategory> getProductCategoryTreeList() {
		return productCategoryService.getProductCategoryTreeList();
	}
	// 获取产品渠道
	public List<Channel> getProductCategoryChannelList() {
			 productCategoryChannelList=productCategoryService.getProductCategoryChannelList();
			return productCategoryChannelList;
		}
	
	public void setProductCategoryChannelList(
			List<Channel> productCategoryChannelList) {
		this.productCategoryChannelList = productCategoryChannelList;
	}

	public File[] getProductImages() {
		return productImages;
	}

	public void setProductImages(File[] productImages) {
		this.productImages = productImages;
	}

	public String[] getProductImagesFileName() {
		return productImagesFileName;
	}

	public void setProductImagesFileName(String[] productImagesFileName) {
		this.productImagesFileName = productImagesFileName;
	}

	public String[] getProductImageParameterTypes() {
		return productImageParameterTypes;
	}

	public void setProductImageParameterTypes(String[] productImageParameterTypes) {
		this.productImageParameterTypes = productImageParameterTypes;
	}
	
	public String[] getProductImageIds() {
		return productImageIds;
	}

	public void setProductImageIds(String[] productImageIds) {
		this.productImageIds = productImageIds;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
//==============专家点评+视频介绍====================
	public List<Article> getArticleTreeListZj() {
		articleTreeListZj = articleService.getAllZj();
		return articleTreeListZj;
	}

	public void setArticleTreeListZj(List<Article> articleTreeListZj) {
		this.articleTreeListZj = articleTreeListZj;
	}
	
	
	public List<Article> getArticleTreeListSp() {
		articleTreeListSp = articleService.getAllSp();
		return articleTreeListSp;
	}
	
	public void setArticleTreeListSp(List<Article> articleTreeListSp) {
		this.articleTreeListSp = articleTreeListSp;
	}
//==============专家点评+视频介绍====================

	public String getProfessionalPath() {
		return professionalPath;
	}

	public void setProfessionalPath(String professionalPath) {
		this.professionalPath = professionalPath;
	}

	public String getVideoPath() {
		return videoPath;
	}

	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
	}

	public File getDuty() {
		return duty;
	}

	public void setDuty(File duty) {
		this.duty = duty;
	}

	public String getDutyFileName() {
		return dutyFileName;
	}

	public void setDutyFileName(String dutyFileName) {
		this.dutyFileName = dutyFileName;
	}

	
	public List<Article> getArticleTreeList() {
		articleTreeList = articleService.getAll();
		return articleTreeList;
	}

	public void setArticleTreeList(List<Article> articleTreeList) {
		this.articleTreeList = articleTreeList;
	}

	public List<Channel> getChannelList() {
		return channelList;
	}

	public void setChannelList(List<Channel> channelList) {
		this.channelList = channelList;
	}

	public File getApp() {
		return app;
	}

	public void setApp(File app) {
		this.app = app;
	}

	public String getAppFileName() {
		return appFileName;
	}

	public void setAppFileName(String appFileName) {
		this.appFileName = appFileName;
	}

	public String getMyDuty() {
		return myDuty;
	}

	public void setMyDuty(String myDuty) {
		this.myDuty = myDuty;
	}

	public String getMyApp() {
		return myApp;
	}

	public void setMyApp(String myApp) {
		this.myApp = myApp;
	}	

	
	
	
	

}