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
public class Receiver implements MessageListener {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(Receiver.class);

	@Resource(name = "jmsConnectionFactory")
	ActiveMQConnectionFactory connectionFactory;
	@Resource(name = "respQueue")
	ActiveMQQueue respQueue;

	@Override
	public void onMessage(Message message) {
		TextMessage textMessage = (TextMessage) message;
		Session session = null;
		Connection connection = null;
		try {
			LOGGER.info("Request Received: " + textMessage.getText());
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);
			TextMessage acknowledgement = session.createTextMessage();
			acknowledgement.setText("Acknowledgement for message: "
					+ textMessage.getText());
			MessageProducer producer = session.createProducer(respQueue);
			producer.send(acknowledgement);
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
