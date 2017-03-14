package algorithm;

import messaging.RabbitMQListener;
import messaging.RabbitMQSender;

public class simulatedAnnealing {
    private final static String QUEUE_NAME = "Inbound";
    public static void main(String[] args) {
            RabbitMQSender send = RabbitMQSender.getInstance();
            RabbitMQListener listen = RabbitMQListener.getInstance();
        
            Solution sl = new Solution();
            send.sendMessage(sl.toJSON());
    }
}
