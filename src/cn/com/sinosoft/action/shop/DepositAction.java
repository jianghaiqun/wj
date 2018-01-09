package cn.com.sinosoft.action.shop;

import java.util.List;

import javax.annotation.Resource;


import org.apache.struts2.convention.annotation.ParentPackage;

import cn.com.sinosoft.entity.PaymentConfig;
import cn.com.sinosoft.service.DepositService;
import cn.com.sinosoft.service.PaymentConfigService;

/**
 * 前台Action类 - 预存款
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT5FA5DE6C97F725ADBB195DC008BFB613
 * ============================================================================
 */

@ParentPackage("member")
public class DepositAction extends BaseShopAction {

	private static final long serialVersionUID = -3091246496095700007L;
	
	@Resource
	private DepositService depositService;
	@Resource
	private PaymentConfigService paymentConfigService;
	
	// 预存款列表
	public String list() {
		pager = depositService.getDepositPager(getLoginMember(), pager);
		return "list";
	}
	
	// 预存款充值
	public String recharge() {
		return "recharge";
	}
	
	// 获取支付配置（不包含预存款、线下支付方式）
	public List<PaymentConfig> getNonDepositOfflinePaymentConfigList() {
		return paymentConfigService.getNonDepositOfflinePaymentConfigList();
	}

}