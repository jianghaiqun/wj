package com.activemq;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ClientMq implements MessageListener {
	private static final Logger logger = LoggerFactory.getLogger(ClientMq.class);
	private static int ackMode;
	private static String connUrl;
	private static String clientQueueName;
	private static String connectionName;
	private static String connectionPass;

	private boolean transacted = false;
	private MessageProducer producer;
	
	private boolean msgReceived = false;
	private Map<String, String> responseMap;

	static {
		clientQueueName = "client.messages";
		ackMode = Session.AUTO_ACKNOWLEDGE;
		connUrl = "tcp://120.27.192.221:61616";
		connectionName = "kxbmail";
		connectionPass = "Aa1qa2ws$$*";
	}

	public ClientMq(final Serializable param) {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				connUrl);
		Connection connection;
		try {
			connection = connectionFactory.createConnection(connectionName,
					connectionPass);
			connection.start();
			Session session = connection.createSession(transacted, ackMode);
			Destination adminQueue = session.createQueue(clientQueueName);

			// producer to send
			this.producer = session.createProducer(adminQueue);
			this.producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

			// Create a temporary queue
			Destination tempDest = session.createTemporaryQueue();
			MessageConsumer responseConsumer = session.createConsumer(tempDest);

			// This class will handle the messages to the temp queue as well
			responseConsumer.setMessageListener(this);

			// create text/map/object msg
			Message msg;

			// reply will come throught this dest
			if (param instanceof String) {
				TextMessage textMessage = session
						.createTextMessage((String) param);
				msg = textMessage;

			} else if (param instanceof HashMap) {
				MapMessage mapMessage = session.createMapMessage();
				@SuppressWarnings("unchecked")
				HashMap<String, Object> paramMap = (HashMap<String, Object>) param;
				for (Entry<String, Object> entry : paramMap.entrySet()) {
					mapMessage.setObject(entry.getKey(), entry.getValue());
				}
				msg = mapMessage;

			} else {
				ObjectMessage objectMessage = session
						.createObjectMessage(param);
				msg = objectMessage;

			}

			sendMsg(this, msg, tempDest);
		} catch (JMSException e) {
			logger.error(e.getMessage());
			// Handle the exception appropriately
		}
	}

	private void sendMsg(ClientMq client, Message msg, Destination tempDest)
			throws JMSException {
		// Correlation id tells response where msg came from
		msg.setJMSCorrelationID("product_id");

		// ReplyTo tells response which destination to reply
		msg.setJMSReplyTo(tempDest);
		client.producer.send(msg);
	}

	public void onMessage(Message message) {
		String messageText = null;
		try {
			if (message instanceof TextMessage) {
				ObjectMapper mapper = new ObjectMapper();
				
				TextMessage textMessage = (TextMessage) message;
				messageText = textMessage.getText();
				
				Map mapResponse = mapper.readValue(messageText, Map.class);
				
				responseMap = mapResponse;
				msgReceived = true;

				logger.info("response = item 1: {} item 2:{}",mapResponse.get("resultCode"),mapResponse.get("resultInfo"));
			}
		} catch (JMSException e) {
			logger.error(e.getMessage());
			
		} catch (JsonParseException e) {
			logger.error(e.getMessage(), e);
			
		} catch (JsonMappingException e) {
			logger.error(e.getMessage(), e);
			
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			
		}
	}
	
	public Map clientResponseMap(){
		while(!msgReceived){
			try {
				Thread.sleep(1000);
				
			} catch (InterruptedException e) {
				logger.error(e.getMessage(), e);
				
			}
		}
		return responseMap;
	}

	public static void main(String[] args) {
	}
}