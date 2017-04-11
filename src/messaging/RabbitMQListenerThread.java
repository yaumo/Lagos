package messaging;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.google.gson.Gson;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import algorithm.OptimizerThread;
import algorithm.Solution;

public class RabbitMQListenerThread implements Runnable {
	Channel channel;
	String queueName;
	Connection connection;
	String consumerTag;
	private volatile double value;
	private volatile boolean shutdown = false;
	private Object threadSynchronizeObj;
	private Solution lastSolutionCandidate;
	private int threadID;

	public RabbitMQListenerThread(Object threadSynchronizeObj, int threadID){
		this.threadSynchronizeObj = threadSynchronizeObj;
		this.threadID = threadID;
	}
	
	public void run() {

		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("192.168.99.100");
			connection = factory.newConnection();
			channel = connection.createChannel();

			channel.exchangeDeclare("results", "fanout");
			queueName = channel.queueDeclare().getQueue();
			channel.queueBind(queueName, "results", "");

			//System.out.println("[*] Waiting for messages.");

			Consumer consumer = new DefaultConsumer(channel) {
				@Override
				public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
						throws IOException {
					String message = new String(body, "UTF-8");
					//System.out.println("Thread "+threadID+" Received '" + message + "'");
					Gson gson = new Gson();
					Solution sol = gson.fromJson(message, Solution.class);
					
					if(lastSolutionCandidate.compareSolution(sol)){
						value = sol.getResultValue();
						synchronized (threadSynchronizeObj) {
							threadSynchronizeObj.notify();
						}
					}
				}
			};
			consumerTag = channel.basicConsume(queueName, true, consumer);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	public void shutdown() {
		try {
			channel.basicCancel(consumerTag);
			channel.close();
			connection.close();
			System.out.println("close channel");

			Thread.currentThread().stop();
			System.out.println("killed thread");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}

	}

	public double getValue() {
		return value;
	}
	
	public void setLastSolutionCandidate(Solution newCandidate){
		lastSolutionCandidate = newCandidate;
	}

}
