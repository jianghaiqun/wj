package cn.com.sinosoft.action.admin;

import cn.com.sinosoft.bean.SystemConfig;
import cn.com.sinosoft.bean.SystemConfig.PointType;
import cn.com.sinosoft.bean.SystemConfig.StoreFreezeTime;
import cn.com.sinosoft.entity.DeliveryCorp;
import cn.com.sinosoft.entity.DeliveryItem;
import cn.com.sinosoft.entity.DeliveryType;
import cn.com.sinosoft.entity.Deposit;
import cn.com.sinosoft.entity.Information;
import cn.com.sinosoft.entity.InformationAppnt;
import cn.com.sinosoft.entity.InformationDuty;
import cn.com.sinosoft.entity.InformationInsured;
import cn.com.sinosoft.entity.InsuredShow;
import cn.com.sinosoft.entity.Order;
import cn.com.sinosoft.entity.Order.OrderStatus;
import cn.com.sinosoft.entity.OrderItem;
import cn.com.sinosoft.entity.OrderLog;
import cn.com.sinosoft.entity.OrderLog.OrderLogType;
import cn.com.sinosoft.entity.Payment;
import cn.com.sinosoft.entity.Payment.PaymentStatus;
import cn.com.sinosoft.entity.Payment.PaymentType;
import cn.com.sinosoft.entity.PaymentConfig;
import cn.com.sinosoft.entity.Product;
import cn.com.sinosoft.entity.Product.WeightUnit;
import cn.com.sinosoft.entity.Refund;
import cn.com.sinosoft.entity.Refund.RefundType;
import cn.com.sinosoft.entity.Reship;
import cn.com.sinosoft.entity.Shipping;
import cn.com.sinosoft.service.AdminService;
import cn.com.sinosoft.service.AreaService;
import cn.com.sinosoft.service.DeliveryCorpService;
import cn.com.sinosoft.service.DeliveryItemService;
import cn.com.sinosoft.service.DeliveryTypeService;
import cn.com.sinosoft.service.DepositService;
import cn.com.sinosoft.service.DictionaryService;
import cn.com.sinosoft.service.HtmlService;
import cn.com.sinosoft.service.InformationAppntService;
import cn.com.sinosoft.service.InformationInsuredService;
import cn.com.sinosoft.service.InformationService;
import cn.com.sinosoft.service.MemberRankService;
import cn.com.sinosoft.service.MemberService;
import cn.com.sinosoft.service.OccupationService;
import cn.com.sinosoft.service.OrderItemService;
import cn.com.sinosoft.service.OrderLogService;
import cn.com.sinosoft.service.OrderService;
import cn.com.sinosoft.service.PaymentConfigService;
import cn.com.sinosoft.service.PaymentService;
import cn.com.sinosoft.service.ProductService;
import cn.com.sinosoft.service.RefundService;
import cn.com.sinosoft.service.ReshipService;
import cn.com.sinosoft.service.ShippingService;
import cn.com.sinosoft.util.CommonUtil;
import com.opensymphony.oscache.base.Cache;
import com.opensymphony.oscache.web.ServletCacheAdministrator;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.utility.StringUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 后台Action类 - 订单
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTCC7D55FE70812A2D98AAB420D628038C
 * ============================================================================
 */

@ParentPackage("admin")
@Results({ 
	@Result(name = "processAction", location = "order!process.action", params = {"order.id", "${order.id}"}, type = "redirect")
})
public class OrderAction extends BaseAdminAction {

	private static final long serialVersionUID = -2080980180511054311L;
	//得到参数
	private HttpServletRequest request = ServletActionContext.getRequest();
	
	private Order order;// 订单
	private Payment payment;
	private Shipping shipping;
	private Refund refund;
	private Reship reship;
	private String orderSn;// 订单号
	
	private String productId;//产品号
	
	private String coutNo;//保单号
	private File electronicCout;//电子保单
	private String electronicCoutFileName;//电子保单
	
	private Information information = new Information();

	private InformationAppnt informationAppnt = new InformationAppnt();
	private List<InformationDuty> showDuty = null;// 订单结果页责任显示集合


	//private List<InformationInsured> showInsureds = new ArrayList<InformationInsured>();
	private List<List<InsuredShow>> showInsureds = new ArrayList<List<InsuredShow>>();//页面显示被保人信息   
	
	private List<OrderItem> orderItemList;
	private List<DeliveryItem> deliveryItemList;

	
	
	
	@Resource
	private OrderService orderService;
	@Resource
	private OrderItemService orderItemService;
	@Resource
	private OrderLogService orderLogService;
	@Resource
	private DeliveryTypeService deliveryTypeService;
	@Resource
	private DeliveryCorpService deliveryCorpService;
	@Resource
	private PaymentConfigService paymentConfigService;
	@Resource
	private PaymentService paymentService;
	@Resource
	private DepositService depositService;
	@Resource
	private AreaService areaService;
	@Resource
	private MemberService memberService;
	@Resource
	private MemberRankService memberRankService;
	@Resource
	private InformationService informationService;
	@Resource
	private AdminService adminService;
	@Resource
	private ProductService productService;
	@Resource
	private DeliveryItemService deliveryItemService;
	@Resource
	private ShippingService shippingService;
	@Resource
	private RefundService refundService;
	@Resource
	private ReshipService reshipService;
	@Resource
	private HtmlService htmlService;
	@Resource
	private InformationAppntService informationAppntService;
	@Resource
	private DictionaryService dictionaryService;
	@Resource
	private OccupationService occupationService;
	@Resource
	private InformationInsuredService informationInsuredService;

	
	// 列表
	public String list() {
		pager = orderService.findByPager(pager);
		return LIST;
	}

	// 删除
	public String delete() {
		try {
			orderService.delete(ids);
		} catch (Exception e) {
			return ajaxJsonErrorMessage("删除失败，订单数据被引用！");
		}
		return ajaxJsonSuccessMessage("删除成功！");
	}

	// 编辑
	public String edit() {
		order = orderService.load(order.getId());
		if (order.getPaymentStatus() != cn.com.sinosoft.entity.Order.PaymentStatus.unpaid) {
			addActionError("此订单付款状态无法编辑！");
			return ERROR;
		}
		setResponseNoCache();
		return INPUT;
	}
	@InputConfig(resultName = "error")
	public String update() throws IOException {
		Order persistent = orderService.load(order.getId());
		if (persistent.getPaymentStatus() != cn.com.sinosoft.entity.Order.PaymentStatus.unpaid) {
			addActionError("此订单付款状态无法编辑！");
			return ERROR;
		}
		if (orderItemList == null || orderItemList.size() == 0) {
			addActionError("请保留至少一个商品！");
			return ERROR;
		}
		for (OrderItem orderItem : orderItemList) {
			if (orderItem.getProductQuantity() <= 0) {
				addActionError("商品数量错误！");
				return ERROR;
			}
			if (orderItem.getProductPrice().compareTo(new BigDecimal("0")) < 0) {
				addActionError("商品价格错误！");
				return ERROR;
			}
//			Product product = productService.load(orderItemService.load(orderItem.getId()).getProduct().getId());
			Product product = null;
			if (product.getStore() != null) {
				OrderItem orderItemPersistent = orderItemService.load(orderItem.getId());
				Integer availableStore = 0;
				if ((getSystemConfig().getStoreFreezeTime() == StoreFreezeTime.payment && order.getPaymentStatus() == cn.com.sinosoft.entity.Order.PaymentStatus.unpaid) || 
					(getSystemConfig().getStoreFreezeTime() == StoreFreezeTime.ship)) {
					availableStore = product.getStore() - product.getFreezeStore();
				} else {
					availableStore = product.getStore() - product.getFreezeStore() + orderItemPersistent.getProductQuantity();
				}
				if (orderItem.getProductQuantity() > availableStore) {
					addActionError("商品[" + product.getName() + "]库存不足！");
					return ERROR;
				}
				if (getSystemConfig().getStoreFreezeTime() == StoreFreezeTime.order || (getSystemConfig().getStoreFreezeTime() == StoreFreezeTime.payment && order.getPaymentStatus() != cn.com.sinosoft.entity.Order.PaymentStatus.unpaid) || 
					(getSystemConfig().getStoreFreezeTime() == StoreFreezeTime.ship)) {
					product.setFreezeStore(product.getFreezeStore() - orderItemPersistent.getProductQuantity() + orderItem.getProductQuantity());
					productService.update(product);
				}
			}
		}
		Integer productTotalQuantity = 0;// 商品总数
		BigDecimal productTotalPrice = new BigDecimal("0");// 商品总价格
		BigDecimal totalAmount = new BigDecimal("0");// 订单总金额
		for (OrderItem orderItem : orderItemList) {
			OrderItem orderItemPersistent = orderItemService.load(orderItem.getId());
			productTotalQuantity += orderItem.getProductQuantity();
			productTotalPrice = productTotalPrice.add(orderItem.getProductPrice().multiply(new BigDecimal(orderItem.getProductQuantity().toString())));
			BeanUtils.copyProperties(orderItem, orderItemPersistent, new String[] {"id", "createDate", "modifyDate", "productSn", "productName", "productHtmlFilePath", "deliveryQuantity", "totalDeliveryQuantity", "order", "product","coutNo"});
			orderItemPersistent.setCoutNo(orderItem.getCoutNo());
			//后台电子保单上传开始
			if (orderItem.getElectronic() != null){
				
				String allowedUploadFileExtension = getSystemConfig().getAllowedUploadFileExtension().toLowerCase();
				if (StringUtils.isEmpty(allowedUploadFileExtension)){
					return ajaxJsonErrorMessage("不允许上传文件!");
				}
				String fileExtension =  StringUtils.substringAfterLast(electronicCoutFileName, ".").toLowerCase();
				String[] fileExtensionArray = allowedUploadFileExtension.split(SystemConfig.EXTENSION_SEPARATOR);
				if (!ArrayUtils.contains(fileExtensionArray, fileExtension)) {
					return ajaxJsonErrorMessage("只允许上传文件类型: " + allowedUploadFileExtension + "!");
				}
				int uploadLimit = getSystemConfig().getUploadLimit() * 1024;
				if (uploadLimit != 0) {
					if (orderItem.getElectronic() != null && orderItem.getElectronic().length() > uploadLimit) {
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
				FileUtils.copyFile(orderItem.getElectronic(), file);
				orderItemPersistent.setElectronicCout(uploadFilePath);
			}
			//后台电子保单上传结束
			orderItemService.update(orderItemPersistent);
		}
		for (OrderItem orderItem : persistent.getOrderItemSet()) {
			if (!orderItemList.contains(orderItem)) {
				orderItemService.delete(orderItem);
			}
		}
		totalAmount = productTotalPrice.add(order.getPaymentFee());
		order.setTotalAmount(totalAmount);
		order.setProductTotalPrice(productTotalPrice);
		order.setProductTotalQuantity(productTotalQuantity);
		BeanUtils.copyProperties(order, persistent, new String[] {"id", "createDate", "modifyDate", "orderSn", "orderStatus", "paymentStatus", "shippingStatus", "paidAmount", "memo", "member", "orderItemSet", "orderLogSet", "paymentSet", "refundSet", "shippingSet", "reshipSet"
																	});
		orderService.update(persistent);
		
		// 订单日志
		OrderLog orderLog = new OrderLog();
		orderLog.setOrderLogType(OrderLogType.modify);
		orderLog.setOrderSn(persistent.getOrderSn());
		orderLog.setOperator(adminService.getLoginAdmin().getUsername());
		orderLog.setInfo(null);
		orderLog.setOrder(persistent);
		orderLogService.save(orderLog);
		
		redirectionUrl = "order!list.action";
		return SUCCESS;
	}
	
	// 处理
	public String process() {
		order = orderService.load(order.getId());
		setResponseNoCache();
		return "process";
	}
	
	// 支付
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "payment.paymentConfig.id", message = "支付方式不允许为空！")
		},
		requiredFields = {
			@RequiredFieldValidator(fieldName = "payment.paymentType", message = "支付类型不允许为空！"),
			@RequiredFieldValidator(fieldName = "payment.totalAmount", message = "支付金额不允许为空！")
		}
	)
	@InputConfig(resultName = "error")
	public String payment() {
		order = orderService.load(order.getId());
		if (order.getPaymentStatus() == cn.com.sinosoft.entity.Order.PaymentStatus.paid || order.getPaymentStatus() == cn.com.sinosoft.entity.Order.PaymentStatus.partRefund || order.getPaymentStatus() == cn.com.sinosoft.entity.Order.PaymentStatus.refunded) {
			addActionError("此订单付款状态无法支付！");
			return ERROR;
		}
		if (payment.getPaymentType() == PaymentType.recharge) {
			addActionError("支付类型错误！");
			return ERROR;
		}
		if (payment.getTotalAmount().compareTo(new BigDecimal("0")) < 0) {
			addActionError("支付金额不允许小于0！");
			return ERROR;
		}
		if (payment.getTotalAmount().compareTo(order.getTotalAmount().subtract(order.getPaidAmount())) > 0) {
			addActionError("支付金额超出订单需付金额！");
			return ERROR;
		}
		Deposit deposit = null;
//		if (payment.getPaymentType() == PaymentType.deposit) {
//			Member member = order.getMember();
//			if (payment.getTotalAmount().compareTo(member.getDeposit()) > 0) {
//				addActionError("会员余存款余额不足！");
//				return ERROR;
//			}
//			member.setDeposit(member.getDeposit().subtract(payment.getTotalAmount()));
//			memberService.update(member);
//			deposit = new Deposit();
//			deposit.setDepositType(DepositType.adminPayment);
//			deposit.setCredit(new BigDecimal("0"));
//			deposit.setDebit(payment.getTotalAmount());
//			deposit.setBalance(member.getDeposit());
//			deposit.setMember(member);
//			depositService.save(deposit);
//		}
		PaymentConfig paymentConfig = paymentConfigService.load(payment.getPaymentConfig().getId());
		payment.setPaymentConfigName(paymentConfig.getName());
		payment.setPaymentFee(new BigDecimal("0"));
		payment.setOperator(adminService.getLoginAdmin().getUsername());
		payment.setPaymentStatus(PaymentStatus.success);
		payment.setDeposit(deposit);
		payment.setOrder(order);
		paymentService.save(payment);
		
//		// 库存处理
//		if (getSystemConfig().getStoreFreezeTime() == StoreFreezeTime.payment && order.getPaymentStatus() == cn.com.sinosoft.entity.Order.PaymentStatus.unpaid ) {
//			for (OrderItem orderItem : order.getOrderItemSet()) {
//				Product product = orderItem.getProduct();
//				if (product.getStore() != null) {
//					product.setFreezeStore(product.getFreezeStore() + orderItem.getProductQuantity());
//					if (product.getIsOutOfStock()) {
//						Hibernate.initialize(orderItem.getProduct().getProductattrib());
//					}
//					productService.update(product);
//					if (product.getIsOutOfStock()) {
//						flushCache();
//						htmlService.productContentBuildHtml(product);
//					}
//				}
//			}
//		}
		
//		order.setOrderStatus(OrderStatus.processed);
		if (payment.getTotalAmount().compareTo(order.getTotalAmount().subtract(order.getPaidAmount())) == 0) {
			order.setPaymentStatus(cn.com.sinosoft.entity.Order.PaymentStatus.paid);
		} else {
			order.setPaymentStatus(cn.com.sinosoft.entity.Order.PaymentStatus.partPayment);
		}
		order.setPaidAmount(order.getPaidAmount().add(payment.getTotalAmount()));
		orderService.update(order);
		
		// 订单日志
		OrderLog orderLog = new OrderLog();
		orderLog.setOrderLogType(OrderLogType.payment);
		orderLog.setOrderSn(order.getOrderSn());
		orderLog.setOperator(adminService.getLoginAdmin().getUsername());
		orderLog.setInfo("支付金额：" + payment.getTotalAmount());
		orderLog.setOrder(order);
		orderLogService.save(orderLog);
		
		return "processAction";
	}
	@InputConfig(resultName = "error")
	public String shipping() {
		order = orderService.load(order.getId());
		if (!areaService.isAreaPath(shipping.getShipAreaPath())) {
			addActionError("地区错误！");
			return ERROR;
		}
		Set<OrderItem> orderItemSet = order.getOrderItemSet();
		int totalDeliveryQuantity = 0;// 总发货数
		for (DeliveryItem deliveryItem : deliveryItemList) {
			Product product = productService.load(deliveryItem.getProduct().getId());
			Integer deliveryQuantity = deliveryItem.getDeliveryQuantity();
			if (deliveryQuantity < 0) {
				addActionError("发货数不允许小于0！");
				return ERROR;
			}
			totalDeliveryQuantity += deliveryQuantity;
			boolean isExist = false;
//			for (OrderItem orderItem : orderItemSet) {
//				if (product == orderItem.getProduct()) {
//					if (deliveryQuantity > (orderItem.getProductQuantity() - orderItem.getDeliveryQuantity())) {
//						addActionError("发货数超出订单购买数！");
//						return ERROR;
//					}
//					if (product.getStore() != null) {
//						if ((getSystemConfig().getStoreFreezeTime() == StoreFreezeTime.payment && order.getPaymentStatus() == cn.com.sinosoft.entity.Order.PaymentStatus.unpaid ) || (getSystemConfig().getStoreFreezeTime() == StoreFreezeTime.ship)) {
//							if (deliveryQuantity > (product.getStore() - product.getFreezeStore())) {
//								addActionError("商品[" + orderItem.getProduct().getName() + "]库存不足！");
//								return ERROR;
//							}
//						} else {
//							if (orderItem.getTotalDeliveryQuantity() < orderItem.getProductQuantity() && deliveryQuantity > (product.getStore() - product.getFreezeStore() + orderItem.getProductQuantity() - orderItem.getDeliveryQuantity())) {
//								addActionError("商品[" + orderItem.getProduct().getName() + "]库存不足！");
//								return ERROR;
//							} else if (orderItem.getTotalDeliveryQuantity() >= orderItem.getProductQuantity() && deliveryQuantity > (product.getStore() - product.getFreezeStore())) {
//								addActionError("商品[" + orderItem.getProduct().getName() + "]库存不足！");
//								return ERROR;
//							}
//						}
//					}
//					isExist = true;
//					break;
//				}
//			}
			if (!isExist) {
				addActionError("发货商品未在订单中！");
				return ERROR;
			}
		}
		if (totalDeliveryQuantity < 1) {
			addActionError("发货总数必须大于0！");
			return ERROR;
		}
		DeliveryType deliveryType = deliveryTypeService.load(shipping.getDeliveryType().getId());
		shipping.setShipArea(areaService.getAreaString(shipping.getShipAreaPath()));
		shipping.setOrder(order);
		shipping.setDeliveryTypeName(deliveryType.getName());
		shippingService.save(shipping);
		
		// 库存处理
//		if ((getSystemConfig().getStoreFreezeTime() == StoreFreezeTime.payment && order.getPaymentStatus() == cn.com.sinosoft.entity.Order.PaymentStatus.unpaid ) || (getSystemConfig().getStoreFreezeTime() == StoreFreezeTime.ship )) {
//			for (OrderItem orderItem : order.getOrderItemSet()) {
//				Product product = orderItem.getProduct();
//				if (product.getStore() != null) {
//					product.setFreezeStore(product.getFreezeStore() + orderItem.getProductQuantity());
//					if (product.getIsOutOfStock()) {
//						Hibernate.initialize(orderItem.getProduct().getProductattrib());
//					}
//					productService.update(product);
//					if (product.getIsOutOfStock()) {
//						flushCache();
//						htmlService.productContentBuildHtml(product);
//					}
//				}
//			}
//		}
		
//		for (DeliveryItem deliveryItem : deliveryItemList) {
//			Product product = productService.load(deliveryItem.getProduct().getId());
//			for (OrderItem orderItem : orderItemSet) {
//				if (orderItem.getProduct().getId() == product.getId()) {
//					orderItem.setDeliveryQuantity(orderItem.getDeliveryQuantity() + deliveryItem.getDeliveryQuantity());
//					orderItem.setTotalDeliveryQuantity(orderItem.getTotalDeliveryQuantity() + deliveryItem.getDeliveryQuantity());
//					orderItemService.update(orderItem);
//					// 库存处理
//					if (product.getStore() != null) {
//						if (orderItem.getTotalDeliveryQuantity() <= orderItem.getProductQuantity()) {
//							product.setFreezeStore(product.getFreezeStore() - deliveryItem.getDeliveryQuantity());
//						}
//						product.setStore(product.getStore() - deliveryItem.getDeliveryQuantity());
//						if (product.getIsOutOfStock()) {
//							Hibernate.initialize(product.getProductattrib());
//						}
//						productService.update(product);
//						if (product.getIsOutOfStock()) {
//							flushCache();
//							htmlService.productContentBuildHtml(product);
//						}
//					}
//					break;
//				}
//			}
//			deliveryItem.setProductSn(product.getProductSn());
//			deliveryItem.setProductName(product.getName());
//			deliveryItem.setProductHtmlFilePath(product.getHtmlFilePath());
//			deliveryItem.setShipping(shipping);
//			deliveryItemService.save(deliveryItem);
//		}
		orderService.update(order);
		
		// 订单日志
		OrderLog orderLog = new OrderLog();
		orderLog.setOrderLogType(OrderLogType.shipping);
		orderLog.setOrderSn(order.getOrderSn());
		orderLog.setOperator(adminService.getLoginAdmin().getUsername());
		orderLog.setInfo(null);
		orderLog.setOrder(order);
		orderLogService.save(orderLog);
		return "processAction";
	}
	
	// 退款
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "refund.paymentConfig.id", message = "退款方式不允许为空！")
		},
		requiredFields = {
			@RequiredFieldValidator(fieldName = "refund.refundType", message = "退款类型不允许为空！"),
			@RequiredFieldValidator(fieldName = "refund.totalAmount", message = "退款金额不允许为空！")
		}
	)
	@InputConfig(resultName = "error")
	public String refund() {
		order = orderService.load(order.getId());
		if (order.getPaymentStatus() == cn.com.sinosoft.entity.Order.PaymentStatus.unpaid || order.getPaymentStatus() == cn.com.sinosoft.entity.Order.PaymentStatus.refunded) {
			addActionError("此订单付款状态无法支付！");
			return ERROR;
		}
		if (refund.getTotalAmount().compareTo(new BigDecimal("0")) < 0) {
			addActionError("退款金额不允许小于0！");
			return ERROR;
		}
		if (refund.getTotalAmount().compareTo(order.getPaidAmount()) > 0) {
			addActionError("退款金额超出订单已付金额！");
			return ERROR;
		}
		Deposit deposit = null;
//		if (refund.getRefundType() == RefundType.deposit) {
//			Member member = order.getMember();
//			member.setDeposit(member.getDeposit().add(refund.getTotalAmount()));
//			memberService.update(member);
//			deposit = new Deposit();
//			deposit.setDepositType(DepositType.adminRecharge);
//			deposit.setCredit(refund.getTotalAmount());
//			deposit.setDebit(new BigDecimal("0"));
//			deposit.setBalance(member.getDeposit());
//			deposit.setMember(member);
//			depositService.save(deposit);
//		}
		
//		PaymentConfig paymentConfig = paymentConfigService.load(refund.getPaymentConfig().getId());
//		refund.setPaymentConfigName(paymentConfig.getName());
//		refund.setOperator(adminService.getLoginAdmin().getUsername());
//		refund.setDeposit(deposit);
//		refund.setOrder(order);
//		refundService.save(refund);
		
		if (refund.getTotalAmount().compareTo(order.getPaidAmount()) < 0) {
			order.setPaymentStatus(cn.com.sinosoft.entity.Order.PaymentStatus.partRefund);
		} else {
			order.setPaymentStatus(cn.com.sinosoft.entity.Order.PaymentStatus.refunded);
		}
		order.setPaidAmount(order.getPaidAmount().subtract(refund.getTotalAmount()));
		orderService.update(order);
		
		// 订单日志
		OrderLog orderLog = new OrderLog();
		orderLog.setOrderLogType(OrderLogType.refund);
		orderLog.setOrderSn(order.getOrderSn());
		orderLog.setOperator(adminService.getLoginAdmin().getUsername());
		orderLog.setInfo("退款金额：" + refund.getTotalAmount());
		orderLog.setOrder(order);
		orderLogService.save(orderLog);
		return "processAction";
	}
	
	// 退货
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "reship.deliveryCorpName", message = "物流公司不允许为空！"),
			@RequiredStringValidator(fieldName = "reship.shipName", message = "收货人姓名不允许为空！"),
			@RequiredStringValidator(fieldName = "reship.shipAreaPath", message = "收货人地区不允许为空！"),
			@RequiredStringValidator(fieldName = "reship.shipAddress", message = "收货人地址不允许为空！"),
			@RequiredStringValidator(fieldName = "reship.shipZipCode", message = "邮编不允许为空！"),
			@RequiredStringValidator(fieldName = "reship.deliveryType.id", message = "配送方式不允许为空！")
		},
		requiredFields = {
			@RequiredFieldValidator(fieldName = "reship.deliveryFee", message = "物流费用不允许为空！")
		}
	)
	@InputConfig(resultName = "error")
	public String reship() {
		order = orderService.load(order.getId());
		if (reship.getDeliveryFee().compareTo(new BigDecimal("0")) < 0) {
			addActionError("物流费用不允许小于0！");
			return ERROR;
		}
		if (!areaService.isAreaPath(reship.getShipAreaPath())) {
			addActionError("地区错误！");
			return ERROR;
		}
		order = orderService.load(order.getId());
		Set<OrderItem> orderItemSet = order.getOrderItemSet();
		int totalDeliveryQuantity = 0;// 总退货数
		for (DeliveryItem deliveryItem : deliveryItemList) {
			Product product = productService.load(deliveryItem.getProduct().getId());
			Integer deliveryQuantity = deliveryItem.getDeliveryQuantity();
			if (deliveryQuantity < 0) {
				addActionError("退货数不允许小于0！");
				return ERROR;
			}
			totalDeliveryQuantity += deliveryQuantity;
			boolean isExist = false;
//			for (OrderItem orderItem : orderItemSet) {
//				if (product == orderItem.getProduct()) {
//					if (deliveryQuantity > orderItem.getDeliveryQuantity()) {
//						addActionError("退货数超出已发货数！");
//						return ERROR;
//					}
//					isExist = true;
//					break;
//				}
//			}
			if (!isExist) {
				addActionError("退货商品未在订单中！");
				return ERROR;
			}
		}
		if (totalDeliveryQuantity < 1) {
			addActionError("退货总数必须大于0！");
			return ERROR;
		}
		
		DeliveryType deliveryType = deliveryTypeService.load(reship.getDeliveryType().getId());
		reship.setShipArea(areaService.getAreaString(reship.getShipAreaPath()));
		reship.setOrder(order);
		reship.setDeliveryTypeName(deliveryType.getName());
		reshipService.save(reship);
		
//		for (DeliveryItem deliveryItem : deliveryItemList) {
//			Product product = productService.load(deliveryItem.getProduct().getId());
//			for (OrderItem orderItem : orderItemSet) {
//				if (orderItem.getProduct() == product) {
//					orderItem.setDeliveryQuantity(orderItem.getDeliveryQuantity() - deliveryItem.getDeliveryQuantity());
//					orderItemService.update(orderItem);
//				}
//			}
//			deliveryItem.setProductSn(product.getProductSn());
//			deliveryItem.setProductName(product.getName());
//			deliveryItem.setProductHtmlFilePath(product.getHtmlFilePath());
//			deliveryItem.setReship(reship);
//			deliveryItemService.save(deliveryItem);
//		}
		orderService.update(order);
		
		// 订单日志
		OrderLog orderLog = new OrderLog();
		orderLog.setOrderLogType(OrderLogType.reship);
		orderLog.setOrderSn(order.getOrderSn());
		orderLog.setOperator(adminService.getLoginAdmin().getUsername());
		orderLog.setInfo(null);
		orderLog.setOrder(order);
		orderLogService.save(orderLog);
		return "processAction";
	}
	
	// 完成
	public String completed() {
		order = orderService.load(order.getId());
		if (order.getOrderStatus() == OrderStatus.invalid) {
			return ajaxJsonErrorMessage("此订单已经作废！");
		} else {
			orderService.update(order);
			// 积分处理
			Integer totalPoint = 0;
			if (getSystemConfig().getPointType() == PointType.orderAmount) {
				totalPoint = order.getProductTotalPrice().multiply(new BigDecimal(getSystemConfig().getPointScale().toString())).setScale(0, RoundingMode.DOWN).intValue();
			} else if (getSystemConfig().getPointType() == PointType.productSet) {
				for (OrderItem orderItem : order.getOrderItemSet()) {
//					totalPoint = orderItem.getProduct().getPoint() * orderItem.getProductQuantity() + totalPoint;
					totalPoint = 0;
				}
			}
//			if (totalPoint > 0) {
//				Member member = order.getMember();
//				member.setPoint(member.getPoint() + totalPoint);
//				MemberRank upMemberRank = memberRankService.getUpMemberRankByPoint(member.getPoint());
//				if (upMemberRank != null && member.getPoint() >= upMemberRank.getPoint()) {
//					member.setMemberRank(upMemberRank);
//				}
//				memberService.update(member);
//			}
			
			// 订单日志
			OrderLog orderLog = new OrderLog();
			orderLog.setOrderLogType(OrderLogType.completed);
			orderLog.setOrderSn(order.getOrderSn());
			orderLog.setOperator(adminService.getLoginAdmin().getUsername());
			orderLog.setInfo(null);
			orderLog.setOrder(order);
			orderLogService.save(orderLog);
			
			return ajaxJsonSuccessMessage("您的操作已成功！");
		}
	}
	
	// 作废
	public String invalid() {
		order = orderService.load(order.getId());
		if (order.getPaymentStatus() != cn.com.sinosoft.entity.Order.PaymentStatus.unpaid) {
			addActionError("此订单支付状态无法编辑！");
			return ERROR;
		}
		if (order.getOrderStatus() == OrderStatus.invalid) {
			return ajaxJsonErrorMessage("此订单已经作废！");
		} else if (order.getPaymentStatus() != cn.com.sinosoft.entity.Order.PaymentStatus.unpaid) {
			return ajaxJsonErrorMessage("此订单付款状态无法作废！");
		} else {
			order.setOrderStatus(OrderStatus.invalid);
			orderService.update(order);
			
			// 库存处理
//			if (getSystemConfig().getStoreFreezeTime() == StoreFreezeTime.order || (getSystemConfig().getStoreFreezeTime() == StoreFreezeTime.payment && order.getPaymentStatus() != cn.com.sinosoft.entity.Order.PaymentStatus.unpaid)) {
//				for (OrderItem orderItem : order.getOrderItemSet()) {
//					Product product = orderItem.getProduct();
//					product.setFreezeStore(product.getFreezeStore() - orderItem.getProductQuantity());
//					if (!product.getIsOutOfStock()) {
//						Hibernate.initialize(orderItem.getProduct().getProductattrib());
//					}
//					productService.update(product);
//					if (!product.getIsOutOfStock()) {
//						flushCache();
//						htmlService.productContentBuildHtml(product);
//					}
//				}
//			}
			
			// 订单日志
			OrderLog orderLog = new OrderLog();
			orderLog.setOrderLogType(OrderLogType.invalid);
			orderLog.setOrderSn(order.getOrderSn());
			orderLog.setOperator(adminService.getLoginAdmin().getUsername());
			orderLog.setInfo(null);
			orderLog.setOrder(order);
			orderLogService.save(orderLog);
			
			return ajaxJsonSuccessMessage("您的操作已成功！");
		}
	}
	
	/**
	 * 从订单查询修改连接到订单详细页面      
	 * @author fhz
	 * */ 
	public String linkOrderDetails() {
			
          
			try {
				orderSn = request.getParameter("orderSn");
				String nKey = StringUtil.md5Hex(PubFun.getKeyValue()+orderSn);
				String oKey = request.getParameter("KID");
				if(!nKey.equals(oKey)){
					addActionError("请登录后，进行此操作！");
					return ERROR;
				}
				logger.info("查询的订单号是：{}", orderSn);
				order = orderService.getOrderByOrderSn(orderSn);
				if(order==null)
				{
					addActionError("系统中不存在该订单！");
					return ERROR;
				}
				Set<OrderItem> orderItemSet = order.getOrderItemSet();
				OrderItem orderItem = null;
				for (OrderItem orderItems : orderItemSet) {
					orderItem = orderItems;
				}
				Set<Information> informationSet = orderItem.getInformationSet();
				for (Information infmn : informationSet) {
					information = infmn;
				}
				informationAppnt = information.getInformationAppnt();
				if(StringUtil.isNotEmpty(informationAppnt.getApplicantArea2())){
					informationAppnt.setApplicantArea2(areaService.getAreaName(informationAppnt.getApplicantArea2()));
					
				}
				Set<InformationDuty> informationDutyElementsSet = information.getInformationDutyElementsSet();
				Set<InformationInsured> informationInsuredSet = information.getInformationInsuredSet();
				if(informationInsuredSet!=null && informationInsuredSet.size()>0){
					//modify by cuishg start
					showInsureds = informationInsuredService.createShowInformationInsured(informationInsuredSet,order.getInsuranceCompanySn());   
					/*for(InformationInsured informationied : informationInsuredSet){
						if (informationied.getDestinationCountry() != null && !"".equals(informationied.getDestinationCountry())) {
							String[] destinate = informationied.getDestinationCountry().split(",");
							StringBuffer destinateStr = new StringBuffer();
							if(destinate != null && destinate.length > 0){
								for(String s : destinate){
									String insuranceCompanySn = order.getInsuranceCompanySn();
									String name = dictionaryService.findCountryNameByCode(insuranceCompanySn,s.trim());
									if(name!=null){
										destinateStr.append(name);
									}
								}
								informationied.setDestinationCountryText(destinateStr.toString());
							}
						}
						if(StringUtil.isNotEmpty(informationied.getRecognizeeArea2())){
							informationied.setRecognizeeArea2(areaService.getAreaName(informationied.getRecognizeeArea2())) ;
						}
						if (informationied.getOccupation3() != null && !"".equals(informationied.getOccupation3())) {
							informationied.setOccupation3(occupationService.getOccupationName(informationied.getOccupation3()));
						}
						showInsureds.add(informationied);
					}*/
					//modify by cuishg end
				}
				if(informationDutyElementsSet!=null && informationDutyElementsSet.size()>0){
					showDuty = new ArrayList<InformationDuty>(informationDutyElementsSet);
				}
				return "detail";
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				addActionError("该订单信息不完整，查询订单信息失败！");
				return ERROR;
			}
	}
	


	// 查看
	public String view() {
		order = orderService.load(order.getId());
		setResponseNoCache();
		return "view";
	}
	
	// 更新页面缓存
	private void flushCache() {
		Cache cache = ServletCacheAdministrator.getInstance(getRequest().getSession().getServletContext()).getCache(getRequest(), PageContext.APPLICATION_SCOPE); 
		cache.flushAll(new Date());
	}
	
	// 获取所有重量单位
	public List<WeightUnit> getAllWeightUnit() {
		List<WeightUnit> allWeightUnit = new ArrayList<WeightUnit>();
		for (WeightUnit weightUnit : WeightUnit.values()) {
			allWeightUnit.add(weightUnit);
		}
		return allWeightUnit;
	}
	
	// 获取所有配送方式
	public List<DeliveryType> getAllDeliveryType() {
		return deliveryTypeService.getAll();
	}
	
	// 获取所有支付方式
	public List<PaymentConfig> getAllPaymentConfig() {
		return paymentConfigService.getAll();
	}
	
	// 获取支付类型（不包含在线充值）
	public List<PaymentType> getNonRechargePaymentTypeList() {
		List<PaymentType> paymentTypeList = new ArrayList<PaymentType>();
		for (PaymentType paymentType : PaymentType.values()) {
			if (paymentType != PaymentType.recharge) {
				paymentTypeList.add(paymentType);
			}
		}
		return paymentTypeList;
	}
	
	// 获取退款类型
	public List<RefundType> getRefundTypeList() {
		List<RefundType> refundTypeList = new ArrayList<RefundType>();
		for (RefundType refundType : RefundType.values()) {
			refundTypeList.add(refundType);
		}
		return refundTypeList;
	}
	
	// 获取所有物流公司
	public List<DeliveryCorp> getAllDeliveryCorp() {
		return deliveryCorpService.getAll();
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public Shipping getShipping() {
		return shipping;
	}

	public void setShipping(Shipping shipping) {
		this.shipping = shipping;
	}

	public Refund getRefund() {
		return refund;
	}

	public void setRefund(Refund refund) {
		this.refund = refund;
	}

	public Reship getReship() {
		return reship;
	}

	public void setReship(Reship reship) {
		this.reship = reship;
	}

	public List<OrderItem> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(List<OrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}

	public List<DeliveryItem> getDeliveryItemList() {
		return deliveryItemList;
	}

	public void setDeliveryItemList(List<DeliveryItem> deliveryItemList) {
		this.deliveryItemList = deliveryItemList;
	}

	public String getCoutNo() {
		return coutNo;
	}

	public void setCoutNo(String coutNo) {
		this.coutNo = coutNo;
	}

	public File getElectronicCout() {
		return electronicCout;
	}

	public void setElectronicCout(File electronicCout) {
		this.electronicCout = electronicCout;
	}

	public String getElectronicCoutFileName() {
		return electronicCoutFileName;
	}

	public void setElectronicCoutFileName(String electronicCoutFileName) {
		this.electronicCoutFileName = electronicCoutFileName;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Information getInformation() {
		return information;
	}

	public void setInformation(Information information) {
		this.information = information;
	}

	public InformationAppnt getInformationAppnt() {
		return informationAppnt;
	}

	public void setInformationAppnt(InformationAppnt informationAppnt) {
		this.informationAppnt = informationAppnt;
	}

	public List<InformationDuty> getShowDuty() {
		return showDuty;
	}

	public void setShowDuty(List<InformationDuty> showDuty) {
		this.showDuty = showDuty;
	}
	
	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public List<List<InsuredShow>> getShowInsureds() {
		return showInsureds;
	}

	public void setShowInsureds(List<List<InsuredShow>> showInsureds) {
		this.showInsureds = showInsureds;
	}


	
}