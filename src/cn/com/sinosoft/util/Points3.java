package cn.com.sinosoft.util;

import com.sinosoft.inter.ActionUtil;
import com.sinosoft.schema.GiftClassifySchema;

import java.util.Map;

/**
 * 积分商城--兑换京东券处理类
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * ============================================================================
 */

/**
 * @author sunny
 *
 */
public class Points3 extends PointExchangeInterface {
	@Override
	public Map<String,String> checkPoint(String cInfo) {
		return super.checkPoint(cInfo);
	}
	
	@Override
	public Map<String, String> Exchange(String cInfo) {
		return super.Exchange(cInfo);
	}
	
	/**
	 * 发送充值信息
	 * @param mobile 手机号
	 * @param point 花费积分数
	 * @param giftPrice 礼品价值
	 * @param giftTitle 礼品名
	 * @param cardno 卡号
	 * @param password 卡密
	 * @return true:发送成功  false:发送失败
	 */
	@Override
	public boolean sendMobileCard(String mobile, GiftClassifySchema mGiftClassifySchema, String cardno, String password) {
		try {
			String sendData = mGiftClassifySchema.getpoints() + ";" + mGiftClassifySchema.getgiftPrice() 
			+ ";" + mGiftClassifySchema.getgiftTitle() + ";" + cardno + ";" + password;
			boolean sendResult = ActionUtil.sendSms("wj00205", mobile, sendData);
			return sendResult;
		} catch(Exception e) {
			logger.error("积分兑换现货发送短信异常！Points3--sendMobileCard" + e.getMessage(), e);
		}
		return false;
	}
}