package cn.com.sinosoft.action.admin;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.ParentPackage;

import cn.com.sinosoft.entity.Information;
import cn.com.sinosoft.entity.Order;
import cn.com.sinosoft.entity.Product;
import cn.com.sinosoft.service.InformationService;
import cn.com.sinosoft.service.OrderService;
import cn.com.sinosoft.service.ProductService;

/**
 * 后台Action类 - 地区
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTAFBAC38E9CCED8343FEA91C4E57AB4B7
 * ============================================================================
 */
@ParentPackage("admin")
public class InformationAction extends BaseAdminAction {
	private static final long serialVersionUID = -526558193244517863L;
	private String orderId;
	private Information information;
	private Product product;
	private Order order;
	private String productId;
//=======投保人信息开始======================
	private String applicantName;// 投保人姓名
	private String applicantIdentityType;// 投保人证件类型
	private String applicantIdentityId;// 投保人证件号码
	private String applicantSex;// 投保人性别
	private String applicantBirthday;// 投保人出生日期
	private String applicantTel;// 投保人电话
	private String applicantMail;// 投保人电子邮箱
	private String applicantArea;// 投保人所在地
	
//=======投保人信息结束======================
//=======被保人信息开始======================
	private String recognizeeName;// 被保人姓名
	private String recognizeeIdentityType;// 被保人证件类型
	private String recognizeeIdentityId;// 被保人证件号码
	private String recognizeeSex;// 被保人性别
	private String recognizeeBirthday;// 被保人出生日期
	private String recognizeeTel;// 被保人电话
	private String recognizeeMail;// 被保人电子邮箱
	private String recognizeeZipCode;// 被保人邮政编码
	private String occupation1;// 职业1
	private String occupation2;// 职业2
	private String occupation3;// 职业3
	private String recognizeeArea;// 被保人所在地
//=======被保人信息结束======================	
	private String tk;//泰康接口返回
	
	@Resource
	private InformationService informationService;
	@Resource
	private ProductService productService;
	@Resource
	private OrderService orderService;
	
	
	// 根据地区Path值获取下级地区JSON数据
	// 每个产品的投保详情
	public String info() {
		information = informationService.getByProduct(id,orderId);
		return "info";
	}
	public String edit() {
		order = orderService.load(orderId);
		product = productService.load(productId);
		information = informationService.getByProduct(productId,orderId);
		return "edit";
	}
	public String update() {
//		information
		product = productService.load(productId);
		order = orderService.load(orderId);
		information = informationService.getByProduct(productId, orderId);
//		information.setOrderItem(orderItem);//information保存orderItem
		//保存每个产品的投保人和被保人信息开始
//		information.setApplicantName(applicantName);
//		information.setApplicantIdentityType(applicantIdentityType);
//		information.setApplicantIdentityId(applicantIdentityId);
//		information.setApplicantSex(applicantSex);
//		information.setApplicantBirthday(applicantBirthday);
//		information.setApplicantTel(applicantTel);
//		information.setApplicantMail(applicantMail);
//		information.setApplicantArea(applicantArea);
//		
//		information.setRecognizeeName(recognizeeName);
//		information.setRecognizeeIdentityType(recognizeeIdentityType);
//		information.setRecognizeeIdentityId(recognizeeIdentityId);
//		information.setRecognizeeSex(recognizeeSex);
//		information.setRecognizeeBirthday(recognizeeBirthday);
//		information.setRecognizeeTel(recognizeeTel);
//		information.setRecognizeeMail(recognizeeMail);
//		information.setRecognizeeZipCode(recognizeeZipCode);
//		information.setOccupation1(occupation1);
//		information.setOccupation2(occupation2);
//		information.setOccupation3(occupation3);
//		information.setRecognizeeArea(recognizeeArea);
		informationService.update(information);

		return "result";
	}

	public String tkPost() throws IOException {
		order = orderService.load(orderId);
		product = productService.load(productId);
		information = informationService.getByProduct(productId,orderId);
//		 recognizeeName = information.getRecognizeeName();// 被保人姓名
//		 recognizeeIdentityType = information.getRecognizeeIdentityType();// 被保人证件类型
//		 recognizeeIdentityId = information.getApplicantIdentityId();// 被保人证件号码
//		 recognizeeTel = information.getRecognizeeTel();// 被保人电话
//		 recognizeeBirthday = information.getRecognizeeBirthday();// 被保人出生日期
		 String orderSn = order.getOrderSn();//中盛保单流水号
		 Date createDate = order.getCreateDate();//投保时间
		 String productSn = product.getProductSn();//险种编号
		 String productName = product.getName();//险种名称
		 BigDecimal price =product.getPrice();//保费
/////////////		 String infoValidTime = "";//保单生效日期
//		System.out.println("name="+information.getRecognizeeName());
//		System.out.println("时间="+createDate);
//		System.out.println("保费="+price);
		 
//		 TKpost tKpost = new TKpost();
//		 tk = tKpost.readContentFromPost(orderSn,recognizeeName
//				 ,recognizeeIdentityType,recognizeeIdentityId,recognizeeTel
//				 ,productSn,productName,recognizeeBirthday,createDate,price);
		 
//		 System.out.println(tk);
		 return "tkreturn";
	}
	
	public Information getInformation() {
		return information;
	}

	public void setInformation(Information information) {
		this.information = information;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getApplicantName() {
		return applicantName;
	}
	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}
	public String getApplicantIdentityType() {
		return applicantIdentityType;
	}
	public void setApplicantIdentityType(String applicantIdentityType) {
		this.applicantIdentityType = applicantIdentityType;
	}
	public String getApplicantIdentityId() {
		return applicantIdentityId;
	}
	public void setApplicantIdentityId(String applicantIdentityId) {
		this.applicantIdentityId = applicantIdentityId;
	}
	public String getApplicantSex() {
		return applicantSex;
	}
	public void setApplicantSex(String applicantSex) {
		this.applicantSex = applicantSex;
	}
	public String getApplicantBirthday() {
		return applicantBirthday;
	}
	public void setApplicantBirthday(String applicantBirthday) {
		this.applicantBirthday = applicantBirthday;
	}
	public String getApplicantTel() {
		return applicantTel;
	}
	public void setApplicantTel(String applicantTel) {
		this.applicantTel = applicantTel;
	}
	public String getApplicantMail() {
		return applicantMail;
	}
	public void setApplicantMail(String applicantMail) {
		this.applicantMail = applicantMail;
	}
	public String getApplicantArea() {
		return applicantArea;
	}
	public void setApplicantArea(String applicantArea) {
		this.applicantArea = applicantArea;
	}
	public String getRecognizeeName() {
		return recognizeeName;
	}
	public void setRecognizeeName(String recognizeeName) {
		this.recognizeeName = recognizeeName;
	}
	public String getRecognizeeIdentityType() {
		return recognizeeIdentityType;
	}
	public void setRecognizeeIdentityType(String recognizeeIdentityType) {
		this.recognizeeIdentityType = recognizeeIdentityType;
	}
	public String getRecognizeeIdentityId() {
		return recognizeeIdentityId;
	}
	public void setRecognizeeIdentityId(String recognizeeIdentityId) {
		this.recognizeeIdentityId = recognizeeIdentityId;
	}
	public String getRecognizeeSex() {
		return recognizeeSex;
	}
	public void setRecognizeeSex(String recognizeeSex) {
		this.recognizeeSex = recognizeeSex;
	}
	public String getRecognizeeBirthday() {
		return recognizeeBirthday;
	}
	public void setRecognizeeBirthday(String recognizeeBirthday) {
		this.recognizeeBirthday = recognizeeBirthday;
	}
	public String getRecognizeeTel() {
		return recognizeeTel;
	}
	public void setRecognizeeTel(String recognizeeTel) {
		this.recognizeeTel = recognizeeTel;
	}
	public String getRecognizeeMail() {
		return recognizeeMail;
	}
	public void setRecognizeeMail(String recognizeeMail) {
		this.recognizeeMail = recognizeeMail;
	}
	public String getRecognizeeZipCode() {
		return recognizeeZipCode;
	}
	public void setRecognizeeZipCode(String recognizeeZipCode) {
		this.recognizeeZipCode = recognizeeZipCode;
	}
	public String getOccupation1() {
		return occupation1;
	}
	public void setOccupation1(String occupation1) {
		this.occupation1 = occupation1;
	}
	public String getOccupation2() {
		return occupation2;
	}
	public void setOccupation2(String occupation2) {
		this.occupation2 = occupation2;
	}
	public String getOccupation3() {
		return occupation3;
	}
	public void setOccupation3(String occupation3) {
		this.occupation3 = occupation3;
	}
	public String getRecognizeeArea() {
		return recognizeeArea;
	}
	public void setRecognizeeArea(String recognizeeArea) {
		this.recognizeeArea = recognizeeArea;
	}
	public String getTk() {
		return tk;
	}
	public void setTk(String tk) {
		this.tk = tk;
	}

	
}