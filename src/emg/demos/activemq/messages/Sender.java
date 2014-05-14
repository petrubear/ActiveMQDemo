package emg.demos.activemq.messages;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Sender {
	private String _queueName;
	private String _url = ActiveMQConnection.DEFAULT_BROKER_URL;
	private Connection _connection;

	public Sender(String queueName) throws JMSException {
		_queueName = queueName;
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				_url);
		_connection = connectionFactory.createConnection();
	}

	public String getQueueName() {
		return _queueName;
	}

	public void setQueueName(String queueName) {
		this._queueName = queueName;
	}

	public void stopQueue() throws JMSException {
		_connection.close();
	}

	public void startQueue() throws JMSException {
		_connection.start();
	}

	public void sendMessage(String message, int priority) throws JMSException {
		// Los mensajes se transmiten usando un objeto Session
		Session session = _connection.createSession(false,
				Session.AUTO_ACKNOWLEDGE);

		// Destination representa la Cola
		Destination destination = session.createQueue(_queueName);

		// MessageProducer para enviar mensajes
		MessageProducer producer = session.createProducer(destination);

		// Envia el mensaje
		TextMessage textMessage = session.createTextMessage(message);
		producer.setPriority(priority);
		producer.send(textMessage);
	}

}
