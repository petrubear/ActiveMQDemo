package emg.demos.activemq;

import java.util.UUID;

import javax.jms.JMSException;

import emg.demos.activemq.messages.Consumer;
import emg.demos.activemq.messages.Sender;

public class Main {
	private static String _queueName = "SampleQueue";
	private static int _max = 5;

	public static void main(String[] args) {
		System.out.println("[Sender]");

		try {
			Sender sender = new Sender(_queueName);
			sender.startQueue();
			for (int i = 0; i < _max; i++)
				sender.sendMessage("Msg ID:" + UUID.randomUUID(), 3);
			
			System.out.println("Done");
			sender.stopQueue();
			
			System.out.println("[Consumer]");
			Consumer consumer = new Consumer(_queueName);
			consumer.startQueue();
			consumer.readMessage();
			consumer.stopQueue();
			System.out.println("Done");
		} catch (JMSException ex) {
			System.out.println("Error en la cola: " + ex.getMessage());
		}
	}
}
