/**
 * Project Name:wj_sstry
 * File Name:package-info.java
 * Package Name:com.finance.activemq.producer
 * Date:2016年8月19日下午3:47:23
 * Copyright (c) 2016, www.kaixinbao.com All Rights Reserved.
 *
 */
/**
 * ClassName: package-info <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(队列，调用本方法开启队列). <br/>
 * date: 2016年8月19日 下午3:47:23 <br/>
 *
 * @author "liang.sl"
 * @version 
 */
package com.finance.activemq.producer;

import com.finance.mq.constant.QueueName;
import com.sinosoft.framework.Config;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

@Component
public class QueueSender {
	private static final Logger logger = LoggerFactory.getLogger(QueueSender.class);
	
	@Autowired
	@Qualifier("jmsQueueTemplate")
	private JmsTemplate jmsTemplate;

	
	/**
	 * 
	 * sendToCallBack:(points队列信息发送请求). <br/> 
	 *
	 * @author guanyulong
	 * @param message
	 */
	public void sendToPointBack(String message) {

		Connection connection = null;
		try {
			String connUrl = Config.getValue("clientMQConnUrl");
			String clientQueueName = Config.getValue("clientPointQueueName");//point，在zdconfig中配置消息队列:kxb.order.point2
			String connectionName = Config.getValue("clientMQConnectionName");
			String connectionPass = Config.getValue("clientMQConnectionPass");
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(connUrl);
			connection = connectionFactory.createConnection(connectionName, connectionPass);

			connection.start();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination adminQueue = session.createQueue(clientQueueName);
			MessageProducer producer = session.createProducer(adminQueue);
			producer.setDeliveryMode(DeliveryMode.PERSISTENT);
			TextMessage msg = session.createTextMessage(message);
			producer.send(msg);
			logger.info("积分兑换发送信息:\n{}", msg);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			try {
				if (connection != null)
					connection.close();

			} catch (JMSException e) {
				logger.error(e.getMessage(), e);

			}
		}
	}
	
	/**
	 * sendMessage:发送消息到emai系统. <br/>
	 * @author guozc
	 * @param message
	 */
	public void sendMessage(final String message){
		jmsTemplate.send(QueueName.ORDER_MAIL, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage textMessage = session.createTextMessage(message);
				return textMessage;
			}
		});
	}

	
}
