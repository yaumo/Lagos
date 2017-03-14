package messaging;

import java.io.IOException;

import com.rabbitmq.client.*;

public class RabbitMQListener {
    Channel channel;
    String queueName;
    private static final RabbitMQListener me=new RabbitMQListener();
    
    private RabbitMQListener() {
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
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                        throws IOException {
                    String message = new String(body, "UTF-8");
                    System.out.println("[x] Received '" + message + "'");
                }
            };
            channel.basicConsume(queueName, true, consumer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static RabbitMQListener getInstance(){
        return me;
    }
}