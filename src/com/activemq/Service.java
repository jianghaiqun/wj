package com.activemq;

import com.sinosoft.framework.utility.StringUtil;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.advisory.DestinationSource;
import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Service {
	private static final Logger logger = LoggerFactory.getLogger(Service.class);

	public static void getDestinations(Connection connection){
		try {
			DestinationSource ds = ((ActiveMQConnection) connection).getDestinationSource();
			Set <ActiveMQQueue> queues = ds.getQueues();

//			connection.close();
		} catch (JMSException e) {
			logger.error(e.getMessage(), e);
			
		}finally{
		}
		return;
		
	}
	public static void send(final Serializable param) {
		class producer implements Runnable {
			public void run() {
				try {
					ConnectionFactory factory = new ActiveMQConnectionFactory(
							"tcp://120.27.192.221:61616");
					Connection connection = factory.createConnection("kxbmail",
							"Aa1qa2ws$$*");
					connection.start();

					ActiveMQSession session = (ActiveMQSession) connection
							.createSession(false, Session.AUTO_ACKNOWLEDGE);
					Queue destination = session.createQueue("Foo.test");


					MessageProducer producer = session
							.createProducer(destination);
					producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);


					if (param instanceof String) {
						TextMessage textMessage = session
								.createTextMessage((String) param);
						textMessage.setJMSReplyTo(destination);
						textMessage.setJMSCorrelationID("1111");
						
						producer.send(textMessage);


					} else if (param instanceof HashMap) {
						MapMessage mapMessage = session.createMapMessage();
						@SuppressWarnings("unchecked")
						HashMap<String, String> paramMap = (HashMap<String, String>) param;
						for (Map.Entry<String, String> entry : paramMap
								.entrySet()) {
							mapMessage.setString(entry.getKey(),
									entry.getValue());
						}
						mapMessage.setJMSReplyTo(destination);
						mapMessage.setJMSCorrelationID("1111");
						producer.send(mapMessage);

					} else {
						ObjectMessage objectMessage = session
								.createObjectMessage(param);
						objectMessage.setJMSReplyTo(destination);
						objectMessage.setJMSCorrelationID("1111");
						producer.send(objectMessage);

					}
					
					session.close();
					connection.close();
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}

			}

		}
		Thread t = new Thread(new producer());
		t.start();
	}

	public static void thread(Runnable r, boolean d) {
		Thread myThread = new Thread(r);
		myThread.setDaemon(d);
		myThread.start();
	}

	public static String consumedStr() {
		class Receiver implements Callable<String>, ExceptionListener {
			String resultStr = "";

			public synchronized void onException(JMSException e) {
				logger.error(e.getMessage());
			}

			@Override
			public String call() {
				try {
					ConnectionFactory factory = new ActiveMQConnectionFactory(
							"tcp://120.27.192.221:61616");
					Connection connection = factory.createConnection("kxbmail",
							"Aa1qa2ws$$*");
					connection.start();

					connection.setExceptionListener(this);

//					ActiveMQSession session = (ActiveMQSession) connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
					ActiveMQSession session = (ActiveMQSession) connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);

					Queue destination = session.createQueue("Foo.test");
					
					getDestinations(connection);

					MessageConsumer consumer = session
							.createConsumer(destination);
					// consumer.setMessageListener(new receiver());

					Message message = consumer.receive(1000);

					if (message instanceof TextMessage) {
						// System.out.println(((TextMessage)
						// message).getText());
						resultStr = ((TextMessage) message).getText();
						message.acknowledge();

					} else {
						// System.out.println(message);
						resultStr = (((ObjectMessage) message).getObject()).toString();
						message.acknowledge();

					}
					consumer.close();
					session.close();
					connection.close();

				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
				if (StringUtil.isNotEmpty(resultStr)) {
					return resultStr;
				} else {
					return null;
				}
			}
		}
		ExecutorService pool = Executors.newFixedThreadPool(1);
		Callable<String> callable = new Receiver();
		Future<String> future = pool.submit(callable);

		try {
			return future.get();
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);

		} catch (ExecutionException e) {
			logger.error(e.getMessage(), e);

		} finally {
			pool.shutdown();
		}
		return null;
	}

	public static void main(String[] args) throws InterruptedException {
		ArrayList <String> al = new ArrayList<String>();
		al.add("thing1");
		al.add("thing2");
		al.add("thing3");
		
		send("testString");
		Thread.sleep(1000);
//		System.out.println(consumedStr());
//		System.out.println("finish");
		return;
	}
}