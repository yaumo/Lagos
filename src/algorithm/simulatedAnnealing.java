package algorithm;

import java.util.concurrent.ThreadLocalRandom;

import messaging.RabbitMQListener;
import messaging.RabbitMQSender;

public class simulatedAnnealing {
    private final static String QUEUE_NAME = "Inbound";
    public static void main(String[] args) {
            RabbitMQSender send = RabbitMQSender.getInstance();
            RabbitMQListener listen = RabbitMQListener.getInstance();
        
            Solution start = generateRandomSolution();
            send.sendMessage(start.toJSON());
    }
    public static Solution generateRandomSolution(){
        double [] vector = new double [17];
        for(int i=0; i<17;i++){
          vector[i]= ThreadLocalRandom.current().nextDouble(-5, 5);
        }
        return new Solution(vector);
    }
}
