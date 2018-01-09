package cn.com.sinosoft.entity;

import cn.com.sinosoft.service.GALogService;
import com.sinosoft.framework.utility.StringUtil;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 实体类 - GA日志: GALog
 * ============================================================================
 * KEY:SINOSOFTF75093F3168097AE8B8496F61E075DA3
 * ============================================================================
 */

@Entity
public class GALog extends BaseEntity {

	private static final long serialVersionUID = 6384778759905608627L;
	
	public static final String REGISTER_SUCCESS = "0";/* 注册成功 */
	public static final String PAY_SUCCESS = "1";/* 订单支付成功 */

	/**
	 * GAType: 统计类型: 0-注册成功；1-订单支付成功。
	 */
	private String GAType;
	
	/**
	 * memberId: 注册用户的用户id
	 */
	private String memberId;
	/**
	 * orderSn: 订单支付成功的订单号
	 */
	private String orderSn;
	
	@Column(nullable = false)
	public String getGAType() {
		return GAType;
	}

	public void setGAType(String GAType) {
		this.GAType = GAType;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	
	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}
	
	/* zhangjinquan 11180 进行GA统计记录 */
	public static void saveGALogRecord(GALogService gaLogService, String GAType, String memberId, String orderSn)
	{
		if (null == gaLogService)
		{
			logger.warn("记录GA统计数据时，GALog服务对象为空！");
			return;
		}
		if (StringUtil.isEmpty(GAType))
		{
			logger.warn("记录GA统计数据时，统计点类型为空！");
			return;
		}
		GALog gaLog = new GALog();
		gaLog.setGAType(GAType);
		if (GALog.REGISTER_SUCCESS.equals(GAType))
		{
			if (StringUtil.isEmpty(memberId))
			{
				logger.warn("记录注册成功GA统计数据时，memberId为空！");
			}
			else if (null != gaLogService.getGALogByMemberId(memberId))
			{
				logger.warn("记录注册成功GA统计数据时，memberId=[{}]的记录已经存在！", memberId);
				return;
			}
		}
		else if (GALog.PAY_SUCCESS.equals(GAType))
		{
			if (StringUtil.isEmpty(orderSn))
			{
				logger.warn("记录订单支付成功GA统计数据时，orderSn为空！");
			}
			else if (null != gaLogService.getGALogByOrderSn(orderSn))
			{
				logger.warn("记录订单支付成功GA统计数据时，orderSn=[{}]的记录已经存在！", orderSn);
				return;
			}
		}
		gaLog.setMemberId(memberId);
		gaLog.setOrderSn(orderSn);
		gaLogService.save(gaLog);
	}
}