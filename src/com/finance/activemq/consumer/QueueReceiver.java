/**
 * Project Name:wj_sstry
 * File Name:package-info.java
 * Package Name:com.finance.activemq.consumer
 * Date:2016年8月19日下午3:59:23
 * Copyright (c) 2016, www.kaixinbao.com All Rights Reserved.
 *
 */
/**
 * ClassName: package-info <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选,不用就删除). <br/>
 * date: 2016年8月19日 下午3:59:23 <br/>
 *
 * @author "liang.sl"
 * @version 
 */
package com.finance.activemq.consumer;

import cn.com.sinosoft.service.ExchangeGoodsService;
import com.sinosoft.dubbo.interfaces.TbOrderInsureDataSaving;
import com.sinosoft.framework.service.TbOrderInsureDataSavingImpl;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.HashMap;
import java.util.Map;
/**
 * ClassName:QueueReceiver <br/>
 * Function:队列接收器/消费者. <br/>
 * Date:2016年8月11日 下午1:39:47 <br/>
 *
 * @author:xialianfu
 */
@Component
public class QueueReceiver implements MessageListener {

	private static final Logger logger = LoggerFactory.getLogger(QueueReceiver.class);

	@Autowired
	private ExchangeGoodsService mExchangeGoodsService;
	@Autowired
	@Qualifier("tbOrderInsureDataSavingImpl")
	private TbOrderInsureDataSaving tbOrderInsureDataSavingImpl;
	
	@Override
	public void onMessage(Message message) {
		TextMessage textMsg = null;
		if (message instanceof TextMessage) {
			textMsg = (TextMessage) message;
		} else {
			throw new IllegalArgumentException("传输类型不是 javax.jms.MapMessage");
		}
		if (textMsg != null) { 
			try {
				logger.info("积分兑换处理接收信息:\n{}", textMsg.getText());
				String groupId = message.getStringProperty("JMSXGroupID");
				if("kxb.order.tbmsg".equals(groupId)){
					logger.error("该消息属于淘宝消息~");
					String result = tbOrderInsureDataSavingImpl.saveData(textMsg.getText());
					if(!TbOrderInsureDataSavingImpl.SUCCESS.equals(result)){
						throw new RuntimeException("淘宝订单保存失败");
					}
				}else{
					ObjectMapper mapper = new ObjectMapper();
					Map param = mapper.readValue(textMsg.getText(), HashMap.class);
					mExchangeGoodsService.doExchange(param);
				}
			} catch (Exception e) {
				logger.error("积分兑换处理失败{}", e.getMessage());
				// 手动抛出运行异常，触发重新发送
				throw new RuntimeException("积分兑换处理失败");
			}
		}

	}

}