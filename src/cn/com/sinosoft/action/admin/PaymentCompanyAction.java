package cn.com.sinosoft.action.admin;

import cn.com.sinosoft.bean.Pager;
import cn.com.sinosoft.bean.Pager.OrderType;
import cn.com.sinosoft.entity.Brand;
import cn.com.sinosoft.entity.PaymentCompany;
import cn.com.sinosoft.entity.PaymentConfig;
import cn.com.sinosoft.service.BrandService;
import cn.com.sinosoft.service.PaymentCompanyService;
import cn.com.sinosoft.service.PaymentConfigService;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;
import java.util.List;

/**
 * 后台Action类 - 保险公司支付方式管理
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT026BAB39F30F9C838DE0050825B212E1
 * ============================================================================
 */
@ParentPackage("admin")
public class PaymentCompanyAction extends BaseAdminAction{

	private static final long serialVersionUID = 72505250369304978L;
	private PaymentCompany paymentCompany;
	private Brand brand;
	private PaymentConfig paymentConfig;
	private List<PaymentCompany> paymentCompanys;
	@Resource
	private PaymentCompanyService paymentCompanyService;
	@Resource
	private BrandService brandService;
	@Resource
	private PaymentConfigService paymentConfigService;
	
	// 是否已存在 ajax验证
	public String checkName() {
		String oldValue = getParameter("oldValue");
		String newValue = paymentCompany.getPaymentConfig().getId();
		String  result="true";
		List<PaymentCompany> paymentcompanyss =paymentCompanyService.getBrandPaymentCompany(paymentCompany.getBrand().getId());
		if(paymentcompanyss.size()>0){
			for (int i=0;i<paymentcompanyss.size();i++){
				if(paymentcompanyss.get(i).getPaymentConfig().getId().equals(newValue)){
					result="false";
				}
			}
		}else {
			result="true";
		}
		return ajaxText(result);
	}
	// 列表
		public String list() {
			if(pager == null) {
				pager = new Pager();
				pager.setOrderType(OrderType.asc);
				pager.setOrderBy("orderList");
			}
			pager = brandService.findByPager(pager);
			return LIST;
		}

		// 添加
		public String add() {
			return INPUT;
		}
		
		// 编辑
		public String edit() {
			brand = brandService.load(id);
			paymentCompanys=paymentCompanyService.getBrandPaymentCompany(brand.getId());
			if(paymentCompanys.size()>0){
				for(int i=0;i<paymentCompanys.size();i++){
					if(paymentCompanys.get(i).getIsDefault().equals(true)){
						paymentConfig=paymentCompanys.get(i).getPaymentConfig();
					}
				}
			}
			return INPUT;
		}
		public String deletepaymentconfig(){
//			long totalCount = paymentConfigService.getTotalCount();
//			if (ids.length >= totalCount) {
//				return ajaxJsonErrorMessage("删除失败，必须保留至少一个支付方式！");
//			}
			paymentCompanyService.delete(ids);
			return ajaxJsonSuccessMessage("删除成功！");
		}
		// 保存
		@Validations(
			requiredStrings = { 
				@RequiredStringValidator(fieldName = "paymentCompany.brand.id", message = "保险公司不允许为空!"),
				@RequiredStringValidator(fieldName = "paymentCompany.paymentConfig.id", message = "支付银行不允许为空!"),
				
			},
			requiredFields = { 
				@RequiredFieldValidator(fieldName = "paymentCompany.isDefault", message = "是否默认不允许为空!")
			}
		)
		@InputConfig(resultName = "error")
		public String save(){
			if(paymentCompany.getIsDefault().equals(true)){
				List<PaymentCompany> paymentcompanys =paymentCompanyService.getBrandPaymentCompany(paymentCompany.getBrand().getId());
				if(paymentcompanys.size()>0){
					for (int i=0;i<paymentcompanys.size();i++){
						if(paymentcompanys.get(i).getIsDefault().equals(true)){
							paymentcompanys.get(i).setIsDefault(false);
							paymentCompanyService.update(paymentcompanys.get(i));
						}
					}
				}

			}
			paymentCompanyService.save(paymentCompany);
			redirectionUrl = "payment_company!list.action";
			return SUCCESS;
			
		}

		// 更新
		@Validations(
				requiredStrings = { 					
					@RequiredStringValidator(fieldName = "paymentCompany.paymentConfig.id", message = "支付银行不允许为空!"),
					
				}
			)
		@InputConfig(resultName = "error")
		public String update() throws Exception {
//			Article persistent = articleService.load(id);
//			article.setChannelSet(new HashSet<Channel>(channelList));
//			BeanUtils.copyProperties(article, persistent, new String[] {"id", "createDate", "modifyDate", "pageCount", "htmlFilePath", "hits"});
//			paymentCompanyService.update(persistent);
			brand = brandService.load(id);
			List<PaymentCompany> paymentcompanys=paymentCompanyService.getBrandPaymentCompany(brand.getId());
			if(paymentcompanys.size()>0){
				for(int i=0;i<paymentcompanys.size();i++){
					if(paymentcompanys.get(i).getId().equals(paymentCompany.getPaymentConfig().getId())){
						paymentcompanys.get(i).setIsDefault(true);
						paymentCompanyService.update(paymentcompanys.get(i));
					}
					else{
						if(paymentcompanys.get(i).getIsDefault().equals(true)){
							paymentcompanys.get(i).setIsDefault(false);
							paymentCompanyService.update(paymentcompanys.get(i));
						}
					}
				}
			}
			redirectionUrl = "payment_company!list.action";
			return SUCCESS;
		}
		
		// 获取所有品牌保险公司
		public List<Brand> getAllBrand() {
			return brandService.getAll();
		}
		// 获取所有网银的银行支付
		public List<PaymentConfig> getAllBankPaymentConfig() {
			return paymentConfigService.getBankPaymentConfigList();
		}

		public PaymentCompany getPaymentCompany() {
			return paymentCompany;
		}

		public void setPaymentCompany(PaymentCompany paymentCompany) {
			this.paymentCompany = paymentCompany;
		}

		public Brand getBrand() {
			return brand;
		}

		public void setBrand(Brand brand) {
			this.brand = brand;
		}

		public List<PaymentCompany> getPaymentCompanys() {
			return paymentCompanys;
		}

		public void setPaymentCompanys(List<PaymentCompany> paymentCompanys) {
			this.paymentCompanys = paymentCompanys;
		}
		public PaymentConfig getPaymentConfig() {
			return paymentConfig;
		}
		public void setPaymentConfig(PaymentConfig paymentConfig) {
			this.paymentConfig = paymentConfig;
		}
		
		
}
