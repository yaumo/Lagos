package messaging;

import java.io.IOException;

import com.google.gson.Gson;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import algorithm.Optimizer;
import algorithm.Solution;
import algorithm.simulatedAnnealing;

public class RabbitMQListener {
	Channel channel;
	String queueName;
	private static final RabbitMQListener me = new RabbitMQListener();
	private static double value;
	private static simulatedAnnealing alg;

	private RabbitMQListener() {
		listen();
	}

	public void setAnnealing(simulatedAnnealing alg) {
		this.alg = alg;
	}

	private void listen() {
		synchronized (Optimizer.obj) {
			try {
				ConnectionFactory factory = new ConnectionFactory();
				factory.setHost("localhost");
				Connection connection = factory.newConnection();
				channel = connection.createChannel();

				channel.exchangeDeclare("results", "fanout");
				queueName = channel.queueDeclare().getQueue();
				channel.queueBind(queueName, "results", "");

				System.out.println("[*] Waiting for messages.");

				Consumer consumer = new DefaultConsumer(channel) {
					@Override
					public synchronized void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
							throws IOException {
						String message = new String(body, "UTF-8");
						// System.out.println("[x] Received '" + message + "'");
						Gson gson = new Gson();
						Solution sol = gson.fromJson(message, Solution.class);
						value = sol.getResultValue();
						Optimizer.obj.notify();
					}
				};
				channel.basicConsume(queueName, true, consumer);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public double getValue() {
		return value;
	}

	public static RabbitMQListener getInstance() {
		return me;
	}
}