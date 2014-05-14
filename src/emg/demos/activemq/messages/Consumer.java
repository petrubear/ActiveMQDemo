package emg.demos.activemq.messages;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Consumer {
	private String _queueName;
	private String _url = ActiveMQConnection.DEFAULT_BROKER_URL;
	private Connection _connection;

	public Consumer(String queueName) throws JMSException {
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

	public void readMessage() throws JMSException {
		// Los mensajes se transmiten usando un objeto Session
		Session session = _connection.createSession(false,
				Session.AUTO_ACKNOWLEDGE);

		// Destination representa la Cola
		Destination destination = session.createQueue(_queueName);

		// MessageConsumer para leer mensajes
		MessageConsumer consumer = session.createConsumer(destination);

		// Recibe el mensaje, receive() se bloquea!
		// Message message = consumer.receive();

		while (true) {
			Message message = consumer.receive(1000);
			if (message == null) break;
			if (message instanceof TextMessage) {
				TextMessage textMessage = (TextMessage) message;
				System.out.println("[Out] " + textMessage.getText());
			}
		}
	}

}
