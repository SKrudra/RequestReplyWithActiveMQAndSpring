package com.myactivemq.requestreplyapp;

import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Requestor implements MessageListener {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(Requestor.class);

	@Resource(name = "jmsConnectionFactory")
	ActiveMQConnectionFactory connectionFactory;
	@Resource(name = "reqQueue")
	ActiveMQQueue reqQueue;

	@Override
	public void onMessage(Message message) {
		TextMessage textMessage = (TextMessage) message;
		try {
			LOGGER.info("Response Received: " + textMessage.getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public void dropMessage(String message) {
		Session session = null;
		Connection connection = null;
		try {
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageProducer producer = session.createProducer(reqQueue);
			TextMessage requestMessage = session.createTextMessage(message);
			producer.send(requestMessage);
		} catch (JMSException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
					session.close();
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
}
