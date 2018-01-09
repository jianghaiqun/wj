package com.activemq;

import com.sinosoft.framework.Config;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

//import com.alibaba.fastjson.JSONObject;

public class ProducerMQ {
	private static final Logger logger = LoggerFactory.getLogger(ProducerMQ.class);

	public void sendToJDT(String message) {

		Connection connection = null;
		try {
			String connUrl = Config.getValue("clientMQConnUrl");
			String clientQueueName = Config.getValue("clientMQQueueName");
			String connectionName = Config.getValue("clientMQConnectionName");
			String connectionPass = Config.getValue("clientMQConnectionPass");
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(connUrl);
			connection = connectionFactory.createConnection(connectionName, connectionPass);

			connection.start();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination adminQueue = session.createQueue(clientQueueName);
			MessageProducer producer = session.createProducer(adminQueue);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			TextMessage msg = session.createTextMessage(message);
			producer.send(msg);

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
	 * 
	 * sendToCallBack:(生产者发送消息队列到第三方URL). <br/> 
	 *
	 * @author xialianfu
	 * @param message
	 */
	public void sendToCallBack(String message) {

		Connection connection = null;
		try {
			String connUrl = Config.getValue("clientMQConnUrl");
			String clientQueueName = Config.getValue("clientCallBackQueueName");//回调URL，在zdconfig中配置消息队列:kxb.order.sendCallbackurl
			String connectionName = Config.getValue("clientMQConnectionName");
			String connectionPass = Config.getValue("clientMQConnectionPass");
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(connUrl);
			connection = connectionFactory.createConnection(connectionName, connectionPass);

			connection.start();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination adminQueue = session.createQueue(clientQueueName);
			MessageProducer producer = session.createProducer(adminQueue);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			TextMessage msg = session.createTextMessage(message);
			producer.send(msg);

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
	 * 
	 * sendToCallBack:(生产者发送消息队列到第三方保单回调). <br/> 
	 *
	 * @author xialianfu
	 * @param message
	 */
	public void sendToCallBackPolicy(String message) {

		Connection connection = null;
		try {
			String connUrl = Config.getValue("clientMQConnUrl");
			String clientQueueName = Config.getValue("clientCallBackPolicy");//回调URL，在zdconfig中配置消息队列:kxb.order.sendCallbackpolicy
			String connectionName = Config.getValue("clientMQConnectionName");
			String connectionPass = Config.getValue("clientMQConnectionPass");
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(connUrl);
			connection = connectionFactory.createConnection(connectionName, connectionPass);

			connection.start();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination adminQueue = session.createQueue(clientQueueName);
			MessageProducer producer = session.createProducer(adminQueue);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			TextMessage msg = session.createTextMessage(message);
			producer.send(msg);

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


}
