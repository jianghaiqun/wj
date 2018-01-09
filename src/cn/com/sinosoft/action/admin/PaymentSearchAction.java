package cn.com.sinosoft.action.admin;

import cn.com.sinosoft.bean.Pager;
import cn.com.sinosoft.entity.Order.PaymentStatus;
import cn.com.sinosoft.entity.Payment;
import cn.com.sinosoft.entity.Payment.PaymentType;
import cn.com.sinosoft.service.PaymentService;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 后台Action类 - 支付管理——查询
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT3D84C91974390915B764F72E0D09A3AA
 * ============================================================================
 */

@ParentPackage("admin")
public class PaymentSearchAction extends BaseAdminAction {

	private static final long serialVersionUID = -5020717407507341428L;
	private Payment payment;
	@Resource
	private PaymentService paymentService;
	
	// 列表
	public String list() {
		pager = paymentService.findByPager(pager);
		return LIST;
	}
	//带查询条件的列表
	public String searchlist(){
		if (pager == null) {
			pager = new Pager();
			pager.setPageSize(10);
		}
		pager.setProperty(null);
		pager.setKeyword(null);
		pager = paymentService.getPaymentPager(payment, pager);
		return LIST;
	}
	// 获取所有支付类型  
	public List<PaymentType> getAllPaymentType() {
		List<PaymentType> allPaymentType = new ArrayList<PaymentType>();
		for (PaymentType paymentType : PaymentType.values()) {
			allPaymentType.add(paymentType);
		}
		return allPaymentType;
	}
	// 获取所有支付状态
	public List<PaymentStatus> getAllPaymentStatus() {
		List<PaymentStatus> allPaymentStatus = new ArrayList<PaymentStatus>();
		for (PaymentStatus paymentStatus : PaymentStatus.values()) {
			allPaymentStatus.add(paymentStatus);
		}
		return allPaymentStatus;
	}
	public Payment getPayment() {
		return payment;
	}
	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	
}
