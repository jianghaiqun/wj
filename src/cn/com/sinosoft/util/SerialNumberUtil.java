package cn.com.sinosoft.util;

import java.util.UUID;


import org.apache.commons.lang.StringUtils;

import cn.com.sinosoft.service.ArticleHomeService;
import cn.com.sinosoft.service.OrderService;
import cn.com.sinosoft.service.PaymentService;
import cn.com.sinosoft.service.RefundService;
import cn.com.sinosoft.service.ReshipService;
import cn.com.sinosoft.service.ShippingService;

/**
 * 工具类 - 编号生成
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT787FFCC36E180DE4F1B69B523E9FA2EE
 * ============================================================================
 */

public class SerialNumberUtil {
	
	public static final String PRODUCT_SN_PREFIX = "SN_";// 货号前缀
	public static final String PRESENT_SN_PREFIX = "SN_";// 货号前缀
	
	public static final String ORDER_SN_PREFIX = "";// 订单编号前缀
	public static final long ORDER_SN_FIRST = 1000000000000000L;// 订单编号起始数
	public static final long HOME_SN_FIRST = 0L;// 首页文章布局编号1,2,3,4,5
	public static final long ORDER_SN_STEP = 1L;// 订单编号步长
	
	public static final String PAYMENT_SN_PREFIX = "";// 支付编号前缀
	public static final long PAYMENT_SN_FIRST = 100000L;// 支付编号起始数
	public static final long PAYMENT_SN_STEP = 1L;// 支付编号步长
	
	public static final String REFUND_SN_PREFIX = "";// 退款编号前缀
	public static final long REFUND_SN_FIRST = 100000L;// 退款编号起始数
	public static final long REFUND_SN_STEP = 1L;// 退款编号步长
	
	public static final String SHIPPING_SN_PREFIX = "";// 发货编号前缀
	public static final long SHIPPING_SN_FIRST = 100000L;// 发货编号起始数
	public static final long SHIPPING_SN_STEP = 1L;// 发货编号步长
	
	public static final String RESHIP_SN_PREFIX = "";// 退货编号前缀
	public static final long RESHIP_SN_FIRST = 100000L;// 退货编号起始数
	public static final long RESHIP_SN_STEP = 1L;// 退货编号步长
	
	public static Long lastOrderSnNumber;
	public static Long lastHomeSnNumber;//首页文章布局编号1,2,3,4,5
	public static Long lastPaymentSnNumber;
	public static Long lastRefundSnNumber;
	public static Long lastShippingSnNumber;
	public static Long lastReshipSnNumber;
	public static Long lastTenpayTransactionIdNumber;

	static {
		// 订单编号
		OrderService orderService = (OrderService) SpringUtil.getBean("orderServiceImpl");
//		String lastOrderSn = orderService.getLastOrderSn();
//		if (StringUtils.isNotEmpty(lastOrderSn)) {
//			lastOrderSnNumber = Long.parseLong(StringUtils.removeStartIgnoreCase(lastOrderSn, ORDER_SN_PREFIX));
//		} else {
//			lastOrderSnNumber = ORDER_SN_FIRST;
//		}
		// 首页文章布局编号1,2,3,4,5
		ArticleHomeService articleHomeService = (ArticleHomeService) SpringUtil.getBean("articleHomeServiceImpl");
		String lastHomeSn = articleHomeService.getLastHomeSn();
		if (StringUtils.isNotEmpty(lastHomeSn)) {
			lastHomeSnNumber = Long.parseLong(StringUtils.removeStartIgnoreCase(lastHomeSn, ""));
		} else {
			lastHomeSnNumber = HOME_SN_FIRST;
		}
		
		// 支付编号
		PaymentService paymentService = (PaymentService) SpringUtil.getBean("paymentServiceImpl");
		String lastPaymentSn = paymentService.getLastPaymentSn();
		if (StringUtils.isNotEmpty(lastPaymentSn)) {
			lastPaymentSnNumber = Long.parseLong(StringUtils.removeStartIgnoreCase(lastPaymentSn, PAYMENT_SN_PREFIX));
		} else {
			lastPaymentSnNumber = PAYMENT_SN_FIRST;
		}
		
		// 退款编号
		RefundService refundService = (RefundService) SpringUtil.getBean("refundServiceImpl");
		String lastRefundSn = refundService.getLastRefundSn();
		if (StringUtils.isNotEmpty(lastRefundSn)) {
			lastRefundSnNumber = Long.parseLong(StringUtils.removeStartIgnoreCase(lastRefundSn, REFUND_SN_PREFIX));
		} else {
			lastRefundSnNumber = REFUND_SN_FIRST;
		}
		
		// 发货编号
		ShippingService shippingService = (ShippingService) SpringUtil.getBean("shippingServiceImpl");
		String lastShippingSn = shippingService.getLastShippingSn();
		if (StringUtils.isNotEmpty(lastShippingSn)) {
			lastShippingSnNumber = Long.parseLong(StringUtils.removeStartIgnoreCase(lastShippingSn, SHIPPING_SN_PREFIX));
		} else {
			lastShippingSnNumber = SHIPPING_SN_FIRST;
		}
		
		// 退货编号
		ReshipService reshipService = (ReshipService) SpringUtil.getBean("reshipServiceImpl");
		String lastReshipSn = reshipService.getLastReshipSn();
		if (StringUtils.isNotEmpty(lastReshipSn)) {
			lastReshipSnNumber = Long.parseLong(StringUtils.removeStartIgnoreCase(lastReshipSn, RESHIP_SN_PREFIX));
		} else {
			lastReshipSnNumber = RESHIP_SN_FIRST;
		}
	}
	
	/**
	 * 生成货号
	 * 
	 * @return 货号
	 */
	public static String buildProductSn() {
		String uuid = UUID.randomUUID().toString();
		return PRODUCT_SN_PREFIX + (uuid.substring(0, 8) + uuid.substring(9, 13)).toUpperCase();
	}
	
	/**
	 * 生成礼品货号
	 * 
	 * @return 货号
	 */
	public static String buildPresentSn() {
		String uuid = UUID.randomUUID().toString();
		return PRESENT_SN_PREFIX + (uuid.substring(0, 8) + uuid.substring(9, 13)).toUpperCase();
	}
	
	/**
	 * 生成订单编号
	 * 
	 * @return 订单编号
	 */
//	public synchronized static String buildOrderSn() {
//		lastOrderSnNumber += ORDER_SN_STEP;
//		return ORDER_SN_PREFIX + lastOrderSnNumber;
//	}
//=======================生成首页文章布局编号1,2,3,4,5=================================================
	public synchronized static String buildHomeSn() {
		lastHomeSnNumber += ORDER_SN_STEP;
		return "" + lastHomeSnNumber;
	}
//=======================生成首页文章布局编号1,2,3,4,5=================================================
	/**
	 * 生成支付编号
	 * 
	 * @return 支付编号
	 */
	public synchronized static String buildPaymentSn() {
		lastPaymentSnNumber += PAYMENT_SN_STEP;
		return PAYMENT_SN_PREFIX + lastPaymentSnNumber;
	}
	
	/**
	 * 生成退款编号
	 * 
	 * @return 退款编号
	 */
	public synchronized static String buildRefundSn() {
		lastRefundSnNumber += REFUND_SN_STEP;
		return REFUND_SN_PREFIX + lastRefundSnNumber;
	}
	
	/**
	 * 生成发货编号
	 * 
	 * @return 发货编号
	 */
	public synchronized static String buildShippingSn() {
		lastShippingSnNumber += SHIPPING_SN_STEP;
		return SHIPPING_SN_PREFIX + lastShippingSnNumber;
	}
	
	/**
	 * 生成退货编号
	 * 
	 * @return 退货编号
	 */
	public synchronized static String buildReshipSn() {
		lastReshipSnNumber += RESHIP_SN_STEP;
		return RESHIP_SN_PREFIX + lastReshipSnNumber;
	}

}