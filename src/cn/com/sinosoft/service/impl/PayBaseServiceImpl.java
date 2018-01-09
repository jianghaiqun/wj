package cn.com.sinosoft.service.impl;

import cn.com.sinosoft.dao.PartnerInfoDao;
import cn.com.sinosoft.dao.PayBaseDao;
import cn.com.sinosoft.dao.SDOrderDao;
import cn.com.sinosoft.entity.PayBase;
import cn.com.sinosoft.service.PayBaseService;
import com.activemq.ProducerMQ;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service实现类 -支付基础信息管理
 * ============================================================================
 * KEY:SINOSOFT5780B32776CB0A6FF3A3530C4BC96D54
 * ============================================================================
 */

@Service
public class PayBaseServiceImpl extends BaseServiceImpl<PayBase, String> implements PayBaseService {

	@Resource
	PayBaseDao payBaseDao; 
	
	
	@Autowired
	private SDOrderDao sdOrderDao;
	
	@Autowired
	private PartnerInfoDao partnerInfoDao;

	@Resource
	public void setBaseDao(PayBaseDao payBaseDao) {
		super.setBaseDao(payBaseDao);
	}

	/**
	 * 通过支付类型获取支付基础信息对象
	 * 
	 * @param payType
	 * @return
	 */
	@Override
	public PayBase getPayBaseByPayType(String payType ) {
		return payBaseDao.getPayBaseByPayType(payType);
	}

	@Override
	public List<PayBase> getPayBaseList(String type) {
		return payBaseDao.getPayBaseList(type);
	}

	/**
	 * 
	 * sendOrderSnToReceiver:(确认支付回调，发送到第三方消息队列). <br/>
	 *
	 * @author xialianfu
	 * @param strPaySn 
	 * @param strStatus:0000-成功；1111-失败
	 * @param strPayType
	 * 
	 */
	@Override
	public void sendOrderSnToReceiver(String strPaySn, String strStatus,String strPayType) {
		
		int iCount = sdOrderDao.selectPartnerInfoByPaySn(strPaySn);
		if (iCount > 0) {// 存在合作商户信息
			
			try {
				List<Map<String, Object>> detailMap = partnerInfoDao.selectDetailByPaySn(strPaySn);
				 String orderSn = ""; 
				 if(detailMap!=null&&detailMap.size()>0){
						for(Map<String, Object> o : detailMap){ 
							orderSn = (String) o.get("orderSn");
					}
				} 
				// 发送消息队列到消费者
				Map<String, String> map = new HashMap<String, String>();
				map.put("orderSn", orderSn); 
				map.put("status", strStatus);  
				map.put("payType", strPayType); 
				map.put("operator", "paycallback");
				ProducerMQ mq = new ProducerMQ();
				mq.sendToCallBack(JSON.toJSONString(map));
			
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		} 
	}
	/**
	 * 
	 * getReturnCallBackStatus:(前台回调--通过订单号,查询前台回调信息). <br/> 
	 *
	 * @author xialianfu
	 * @param strPaySn 
	 */
	public Map<String,Object> getReturnCallBackStatus(String strPaySn) {
		
		int iCount = sdOrderDao.selectPartnerInfoByPaySn(strPaySn);
		
		Map<String,Object> createMap = new HashMap<String,Object>();
		if (iCount > 0) {// 存在合作商户信息
			List<Map<String, Object>> detailMap = partnerInfoDao.selectDetailByPaySn(strPaySn);
			 Map<String, Object> map = new HashMap<String, Object>();
			 String orderSn = "";
			 String strUrl ="";
			 String strErrorUrl = "";
			 if(detailMap!=null&&detailMap.size()>0){
					for(Map<String, Object> o : detailMap){
						strUrl = (String) o.get("returnUrl");
						strErrorUrl = (String) o.get("returnErrorUrl");
						orderSn = (String) o.get("orderSn");
				}
			} 
	        createMap.put("orderSn",orderSn);  
	        createMap.put("strUrl",strUrl);  
	        createMap.put("strErrorUrl",strErrorUrl);  
	        createMap.put("isHavePartenrInfo","0000");  
		} else{
			createMap.put("isHavePartenrInfo","1111");  
		}
		return createMap;
	}

	@Override
	public void sendPolicyToReceiver(String strOrderSn) {
		
		int iCount = sdOrderDao.selectPartnerInfoByOrderSn(strOrderSn);
		if (iCount > 0) {// 存在合作商户信息
			
			try { 
				// 发送消息队列到消费者
				Map<String, String> map = new HashMap<String, String>();
				map.put("orderSn", strOrderSn);  
				map.put("operator", "policycallback");
				ProducerMQ mq = new ProducerMQ();
				mq.sendToCallBackPolicy(JSON.toJSONString(map));
			
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		} 
		
	}

	@Override
	public void sendLicaiCancalToReceiver(String partnerId) {

		try {
			// 发送消息队列到消费者
			Map<String, String> map = new HashMap<String, String>();
			map.put("partnerId", partnerId);
			map.put("operator", "licaicancalcallback");
			ProducerMQ mq = new ProducerMQ();
			mq.sendToCallBackPolicy(JSON.toJSONString(map));

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public void sendUnderwritingToReceiver(String infoID, String desc) {
		try {
			// 发送消息队列到消费者
			Map<String, String> map = new HashMap<String, String>();
			map.put("infoID", infoID);
			map.put("desc", desc);
			map.put("operator", "underwritingCallback");
			ProducerMQ mq = new ProducerMQ();
			mq.sendToCallBackPolicy(JSON.toJSONString(map));

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}