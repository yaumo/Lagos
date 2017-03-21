package messaging;

import java.io.IOException;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQSender {
    //setting up connection to Inbound Queue
    Channel channel;
    private static final RabbitMQSender me = new RabbitMQSender();

    private RabbitMQSender() {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            channel = connection.createChannel();
            channel.queueDeclare("Inbound", false, false, false, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static RabbitMQSender getInstance() {
        return me;
    }

    public void sendMessage(String message) {
        try {
            channel.basicPublish("", "Inbound", null, message.getBytes());
            //System.out.println("[x] Sent '" + message + "'");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
