package cn.com.sinosoft.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import cn.com.sinosoft.entity.PayBase;

/**
 * Service接口 - 支付基础信息管理
 * ============================================================================
 * KEY:SINOSOFT3F6674D6C2E7DF7287EF69622E4F46B5
 * ============================================================================
 */

public interface PayBaseService extends BaseService<PayBase, String> {
	/**
	 * 通过支付类型获取支付基础信息对象
	 * 
	 * @param payType
	 * @return
	 */
	public PayBase getPayBaseByPayType(String payType);

	public List<PayBase> getPayBaseList(String type);

	/**
	 * 
	 * sendPolicyToReceiver:(确认保单回调，发送到第三方消息队列). <br/>
	 *
	 * @author xialianfu
	 * @param strOrderSn 
	 */
	public void sendPolicyToReceiver(String strOrderSn);
	
	/**
	 * 
	 * sendOrderSnToReceiver:(确认支付回调，发送到第三方消息队列). <br/>
	 *
	 * @author xialianfu
	 * @param strPaySn
	 * @param strStatus
	 *            :0000-成功；1111-失败
	 * @param strPayType
	 */
	public void sendOrderSnToReceiver(String strPaySn, String strStatus,String strPayType);

	/**
	 * 
	 * getReturnCallBackStatus:(前台回调--通过交易流水号,查询前台回调信息). <br/>
	 *
	 * @author xialianfu
	 * @param strPaySn 
	 */
	public Map<String, Object> getReturnCallBackStatus(String strPaySn);

	/**
	 * sendLicaiCancalToReceiver:(理财险退保回调，发送到第三方消息队列). <br/>
	 *
	 * @author wwy
	 * @param partnerId
	 */
	public void sendLicaiCancalToReceiver(String partnerId);
	
	/**
	 * sendUnderwritingToReceiver:核保结果回调. <br/>
	 *
	 * @author wwy
	 * @param infoID
	 */
	public void sendUnderwritingToReceiver(String infoID, String desc);

}