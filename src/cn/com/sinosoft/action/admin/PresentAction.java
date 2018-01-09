package cn.com.sinosoft.action.admin;

import cn.com.sinosoft.bean.PresentImage;
import cn.com.sinosoft.bean.SystemConfig;
import cn.com.sinosoft.entity.Article;
import cn.com.sinosoft.entity.Brand;
import cn.com.sinosoft.entity.Channel;
import cn.com.sinosoft.entity.Present;
import cn.com.sinosoft.entity.Present.WeightUnit;
import cn.com.sinosoft.entity.PresentCategory;
import cn.com.sinosoft.entity.Stock;
import cn.com.sinosoft.service.ArticleService;
import cn.com.sinosoft.service.BrandService;
import cn.com.sinosoft.service.PresentCategoryService;
import cn.com.sinosoft.service.PresentImageService;
import cn.com.sinosoft.service.PresentService;
import cn.com.sinosoft.service.StockService;
import cn.com.sinosoft.util.CommonUtil;
import cn.com.sinosoft.util.ImageUtil;
import com.opensymphony.oscache.base.Cache;
import com.opensymphony.oscache.web.ServletCacheAdministrator;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.sinosoft.cms.resource.uploader.OSSUploadFile;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;
import javax.servlet.jsp.PageContext;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 后台Action类 - 礼品  
 * ============================================================================
 * KEY:SINOSOFT483FBE0A69F1A3F1D081F3F2D838E89C
 * ============================================================================
 */

@ParentPackage("admin")
public class PresentAction extends BaseAdminAction {

	private static final long serialVersionUID = -4433964283757192334L;

	private File duty; // 新添加礼品保险条款
	private String myDuty;// 保存旧版本duty
	private String dutyFileName;

	private File app; // 新添加礼品保险条款
	private String myApp; // 保存旧版本app
	private String appFileName;

	private String professionalPath;// 礼品专家点评 存储【文章id】
	private String videoPath;// 礼品视频介绍 存储【文章id】
	private File[] presentImages;
	private String[] presentImagesFileName;
	private String[] presentImageParameterTypes;
	private String[] presentImageIds;
	private List<Article> articleTreeList;
	private List<Article> articleTreeListZj;
	private List<Article> articleTreeListSp;
	private Present present;
	private List<Channel> channelList;

	@Resource
	private ArticleService articleService;
	@Resource
	private PresentService presentService;
	@Resource
	private PresentCategoryService presentCategoryService;
	@Resource
	private BrandService brandService;
	@Resource
	private PresentImageService presentImageService;

	@Resource
	private StockService stockService;
	public static final String UPLOAD_IMAGE_DIR_CMS = "/wwwroot/kxb/";// 图片文件根目录
	public static final String UPLOAD_IMAGE_DIR_POINT = "upload/Image/point/";// 积分商城目录

	/**
	 * 以下两个type和attribute要被仿照成： PresentInsuranceTypeService
	 * PresentInsuranceAttributeService
	 */

	// ajax根据渠道名称获取所有渠道下的礼品分类名称
	@SuppressWarnings("unchecked")
	public String findAllChannelpresentCategory() {
		List<PresentCategory> allpresentCategoryName = presentCategoryService.getRootPresentCategoryList();
		// if (presentCategoryChannelList.contains(channelName)) {
		StringBuilder stringBuilder = new StringBuilder();
		String[] methodNameArray = new String[allpresentCategoryName.size()];
		stringBuilder.append("<option value=\"" + "" + "\">" + "顶级分类" + "</option>");
		for (int i = 0; i < allpresentCategoryName.size(); i++) {
			methodNameArray[i] = allpresentCategoryName.get(i).getName();
			if (allpresentCategoryName.get(i).getLevel() == 0) {
				stringBuilder.append("<option value=\"" + allpresentCategoryName.get(i).getId() + "\">" + allpresentCategoryName.get(i).getName() + "</option>");
			} else {
				stringBuilder.append("<option value=\"" + allpresentCategoryName.get(i).getId() + "\">" + "------" + allpresentCategoryName.get(i).getName() + "</option>");
			}

		}
		if (stringBuilder.length() == 0) {
			stringBuilder.append("<option value=\"noValue\">无礼品分类</option>");
		}
		return ajaxText(stringBuilder.toString());
		// }
		// return null;
	}

	// 添加
	public String add() {
		return INPUT;
	}

	// 编辑
	public String edit() {
		present = presentService.load(id);
		String flag1=present.getHtmlFilePath();
		String flag2=present.getActCode();
		present.setActCode(flag1+flag2);
		return INPUT;
	}

	// 列表
	public String list() {
		pager = presentService.findByPager(pager);
		return LIST;
	}

	// 礼品统计列表
	public String statisticsList() {
		pager = presentService.findByPager(pager);
		return LIST;
	}

	// 删除
	public String delete() throws Exception {
		presentService.delete(ids);
		// flushCache();
		return ajaxJsonSuccessMessage("删除成功！");
	}

	// 保存
	@Validations(requiredStrings = { @RequiredStringValidator(fieldName = "present.name", message = "礼品名称不允许为空!")}, requiredFields = {
			@RequiredFieldValidator(fieldName = "present.marketPrice", message = "市场价不允许为空!"),
			// @RequiredFieldValidator(fieldName = "present.isMarketable",
			// message = "是否上架不允许为空!"),
			// @RequiredFieldValidator(fieldName = "present.isBest", message =
			// "是否精品不允许为空!"),
			// @RequiredFieldValidator(fieldName = "present.isNew", message =
			// "是否新品不允许为空!"),
			// @RequiredFieldValidator(fieldName = "present.isHot", message =
			// "是否热销不允许为空!"),
			@RequiredFieldValidator(fieldName = "present.presentCategory.id", message = "所属分类不允许为空!") }, intRangeFields = {
			@IntRangeFieldValidator(fieldName = "present.point", min = "0", message = "积分必须为零或正整数!"), @IntRangeFieldValidator(fieldName = "present.store", min = "0", message = "库存必须为零或正整数!") })
	@InputConfig(resultName = "error")
	public String save() throws Exception {
		if (present.getMarketPrice().compareTo(new BigDecimal("0")) < 0) {
			addActionError("市场价不允许小于0");
			return ERROR;
		}
		// if (present.getWeight() < 0) {
		// addActionError("保额只允许为正数或零!");
		// return ERROR;
		// }
		// ===========presentAction========================xia===========1=======================================================//
		if (presentImages != null) {
			String allowedUploadImageExtension = getPresentConfig().getAllowedUploadImageExtension().toLowerCase();
			if (StringUtils.isEmpty(allowedUploadImageExtension)) {
				addActionError("不允许上传图片文件!");
				return ERROR;
			}
			for (int i = 0; i < presentImages.length; i++) {
				String presentImageExtension = StringUtils.substringAfterLast(presentImagesFileName[i], ".").toLowerCase();
				String[] imageExtensionArray = allowedUploadImageExtension.split(SystemConfig.EXTENSION_SEPARATOR);
				if (!ArrayUtils.contains(imageExtensionArray, presentImageExtension)) {
					addActionError("只允许上传图片文件类型: " + allowedUploadImageExtension + "!");
					return ERROR;
				}
				if (getPresentConfig().getUploadLimit() != 0 && presentImages[i].length() > getPresentConfig().getUploadLimit() * 1024) {
					addActionError("此上传文件大小超出限制!");
					return ERROR;
				}
			}
		}

		if (presentImages != null && presentImages.length > 0) {
			for (int i = 0; i < presentImages.length; i++) {
				String sourcePresentImageFormatName = ImageUtil.getImageFormatName(presentImages[i]);
				String uuid = CommonUtil.getUUID();
				String sourcePresentImagePath = SystemConfig.UPLOAD_IMAGE_DIR_CMS + SystemConfig.UPLOAD_IMAGE_DIR_POINT + "" + uuid + "." + sourcePresentImageFormatName;
				File sourcePresentImageFile = new File(ServletActionContext.getServletContext().getRealPath(sourcePresentImagePath));
				File sourcePresentImageParentFile = sourcePresentImageFile.getParentFile();
				if (!sourcePresentImageParentFile.exists()) {
					sourcePresentImageParentFile.mkdirs();
				}
				FileUtils.copyFile(presentImages[i], sourcePresentImageFile);
//				//创建输出流  
//				       FileOutputStream outStream = new FileOutputStream(sourcePresentImageFile);  
//				        //写入数据  
//				        outStream.write(data);  
//				        //关闭输出流  
//				        outStream.close(); 

				present.setImageaddress(SystemConfig.UPLOAD_IMAGE_DIR_POINT + "" + uuid + "." + sourcePresentImageFormatName);
				
				OSSUploadFile.uploadFile(ServletActionContext.getServletContext().getRealPath(sourcePresentImagePath));
			}
		}
		present.setFreezeStore(0);
		present.setFavoriteMemberSet(null);
		present.setWeightUnit((WeightUnit.g));
		// present.setDutyDownLoadPath(dutyDownLoadPath);
		String flag1=present.getActCode().substring(0,1);
		String flag2=present.getActCode().substring(1,present.getActCode().length());
		present.setHtmlFilePath(flag1);
		present.setActCode(flag2);
		presentService.save(present);
		//flushCache();
		redirectionUrl = "present!list.action";
		return SUCCESS;
	}

	// 更新
	@Validations(requiredStrings = { @RequiredStringValidator(fieldName = "present.name", message = "礼品名称不允许为空!") }, requiredFields = {
			@RequiredFieldValidator(fieldName = "present.marketPrice", message = "市场价不允许为空!"),
			// @RequiredFieldValidator(fieldName = "present.isMarketable",
			// message = "是否上架不允许为空!"),
			// @RequiredFieldValidator(fieldName = "present.isBest", message =
			// "是否精品不允许为空!"),
			// @RequiredFieldValidator(fieldName = "present.isNew", message =
			// "是否新品不允许为空!"),
			// @RequiredFieldValidator(fieldName = "present.isHot", message =
			// "是否热销不允许为空!"),
			@RequiredFieldValidator(fieldName = "present.presentCategory.id", message = "所属分类不允许为空!") }, intRangeFields = {
			@IntRangeFieldValidator(fieldName = "present.point", min = "0", message = "积分必须为零或正整数!"), @IntRangeFieldValidator(fieldName = "present.store", min = "0", message = "库存必须为零或正整数!") })
	@InputConfig(resultName = "error")
	public String update() throws Exception {
		try {
			if (present.getMarketPrice().compareTo(new BigDecimal("0")) < 0) {
				addActionError("市场价不允许小于0");
				return ERROR;
			}
			// if (present.getWeight() < 0) {
			// addActionError("保额只允许为正数或零!");
			// return ERROR;
			// }
			Present persistent = presentService.load(id);
			persistent.setName(present.getName());
			persistent.getPresentCategory().setId(present.getPresentCategory().getId());
			persistent.getStock().setId(present.getStock().getId());
			persistent.setMarketPrice(present.getMarketPrice());
			persistent.setPoint(present.getPoint());
			persistent.setIsNew(present.getIsNew());
			persistent.setMetaDescription(present.getMetaDescription());
			if (presentImages != null && presentImages.length > 0) {
				for (int i = 0; i < presentImages.length; i++) {
					String sourcePresentImageFormatName = ImageUtil.getImageFormatName(presentImages[i]);
					String uuid = CommonUtil.getUUID();
					String sourcePresentImagePath = SystemConfig.UPLOAD_IMAGE_DIR_CMS + SystemConfig.UPLOAD_IMAGE_DIR_POINT + "" + uuid + "." + sourcePresentImageFormatName;
					File sourcePresentImageFile = new File(ServletActionContext.getServletContext().getRealPath(sourcePresentImagePath));
					File sourcePresentImageParentFile = sourcePresentImageFile.getParentFile();
					if (!sourcePresentImageParentFile.exists()) {
						sourcePresentImageParentFile.mkdirs();
					}
					FileUtils.copyFile(presentImages[i], sourcePresentImageFile);
					persistent.setImageaddress(SystemConfig.UPLOAD_IMAGE_DIR_POINT + "" + uuid + "." + sourcePresentImageFormatName);
					
					OSSUploadFile.uploadFile(ServletActionContext.getServletContext().getRealPath(sourcePresentImagePath));
				}
			}
			// List<PresentImage> presentImageList = new
			// ArrayList<PresentImage>();
			// if (presentImageParameterTypes != null) {
			// int index = 0;
			// int presentImageFileIndex = 0;
			// int presentImageIdIndex = 0;
			// for (String parameterType : presentImageParameterTypes) {
			// if (StringUtils.equalsIgnoreCase(parameterType,
			// "presentImageFile")) {
			// //
			// System.out.println("zhuo=="+presentImages[presentImageFileIndex].getName());
			// PresentImage destPresentImage =
			// presentImageService.buildPresentImage(presentImages[presentImageFileIndex]);
			// presentImageList.add(destPresentImage);
			// presentImageFileIndex++;
			// index++;
			// } else if (StringUtils.equalsIgnoreCase(parameterType,
			// "presentImageId")) {
			// PresentImage destPresentImage =
			// persistent.getPresentImage(presentImageIds[presentImageIdIndex]);
			// presentImageList.add(destPresentImage);
			// presentImageIdIndex++;
			// index++;
			// }
			// }
			// }

			if (present.getStore() == null) {
				persistent.setFreezeStore(0);
			} else {
				persistent.setFreezeStore(present.getFreezeStore());
			}

			List<PresentImage> presentImageList = new ArrayList<PresentImage>();
			persistent.setWeightUnit((WeightUnit.g));
//			BeanUtils.copyProperties(present, persistent, new String[] { "id", "createDate", "modifyDate", "htmlFilePath", "favoriteMemberSet", "cartItemSet", "orderItemSet", "deliveryItemSet",
//					"presentArticleID" });
			String flag1=present.getActCode().substring(0,1);
			String flag2=present.getActCode().substring(1,present.getActCode().length());
			persistent.setHtmlFilePath(flag1);
			persistent.setActCode(flag2);
			presentService.update(persistent);
			// flushCache();
			redirectionUrl = "present!list.action";
			return SUCCESS;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ERROR;
		}
	}

	// 获取所有品牌
	public List<Brand> getAllBrand() {
		return brandService.getAll();
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

	// 获取礼品分类树
	public List<PresentCategory> getPresentCategoryTreeList() {
		return presentCategoryService.getPresentCategoryTreeList();
	}

	// 获取礼品分类树
	public List<Stock> getStockList() {
		return stockService.getAll();
	}

	public File[] getPresentImages() {
		return presentImages;
	}

	public void setPresentImages(File[] presentImages) {
		this.presentImages = presentImages;
	}

	public String[] getPresentImagesFileName() {
		return presentImagesFileName;
	}

	public void setPresentImagesFileName(String[] presentImagesFileName) {
		this.presentImagesFileName = presentImagesFileName;
	}

	public String[] getPresentImageParameterTypes() {
		return presentImageParameterTypes;
	}

	public void setPresentImageParameterTypes(String[] presentImageParameterTypes) {
		this.presentImageParameterTypes = presentImageParameterTypes;
	}

	public String[] getPresentImageIds() {
		return presentImageIds;
	}

	public void setPresentImageIds(String[] presentImageIds) {
		this.presentImageIds = presentImageIds;
	}

	public Present getPresent() {
		return present;
	}

	public void setPresent(Present present) {
		this.present = present;
	}

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