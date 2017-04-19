package messaging;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQSenderThread {
	// setting up connection to Inbound Queue
	Connection connection;
	Channel channel;

	public RabbitMQSenderThread() {
		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("localhost");
			connection = factory.newConnection();
			channel = connection.createChannel();
			channel.queueDeclare("Inbound", false, false, false, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendMessage(String message) {
		try {
			channel.basicPublish("", "Inbound", null, message.getBytes());
			// System.out.println("[x] Sent '" + message + "'");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void closeCon() {
		try {
			channel.close();
			connection.close();
			System.exit(0);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	}
}
