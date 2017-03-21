package algorithm;

import java.util.concurrent.ThreadLocalRandom;

import messaging.RabbitMQListener;
import messaging.RabbitMQSender;

public class simulatedAnnealing {
    private final static String QUEUE_NAME = "Inbound";
    private static simulatedAnnealing alg = new simulatedAnnealing();
    private static RabbitMQSender send = RabbitMQSender.getInstance();
    private static RabbitMQListener listen = RabbitMQListener.getInstance();
    private static double[] solutionVector = new double[17];

    public static void main(String[] args) {
        listen.setAnnealing(alg);
        Solution start = generateRandomSolution();

        Solution end = alg.optimizeSolution(start, 100, 1);

        //alg.optimizeVariable(5,10,0.1, 0);

        System.out.println("------------------------------------------------------------------------");
        System.out.println(end.toJSON());

        
    }

    public static Solution generateRandomSolution() {
        double[] vector = new double[17];
        for (int i = 0; i < 17; i++) {
            double tmp = ThreadLocalRandom.current().nextDouble(-5, 5);
            vector[i] = tmp;
            solutionVector[i] = tmp;
        }
        return new Solution(vector);
    }

    //start = Startsolution, n = maximum Number of Iterations, u= Umgebungsbegriff
    public Solution optimizeSolution(Solution start, int n, double u) {
        Solution current = start;
        double[] vector = current.getSolutionVector();
        for (int e = 0; e < vector.length; e++) {
            double optimizedValue = optimizeVariable(vector[e], n, u, e);
            vector[e] = optimizedValue;
            solutionVector[e]=optimizedValue;
        }
        current.setSolutionVector(vector);
        return current;
    }
    
    public double checkValue(double value, int i) {
        solutionVector[i] = value;
        send.sendMessage(new Solution(solutionVector).toJSON());

        double dbl = 111;
        while (dbl == 111) {
            dbl = listen.getValue();
            System.out.print("");
        }
        return dbl;
        //send.sendMessage(new Solution(solutionVector).toJSON());

    }
    public double optimizeVariable(double value, int n, double u, int iteration) {
        double temp = 10000;
        double coolingRate = 0.03;
        int t = 0;
        while (temp > 1) {
            for (int i = 1; i <= n; i++) {
                double newValue = value + ThreadLocalRandom.current().nextDouble(-u, u);
                double ynew = checkValue(newValue, iteration);
                double yold = checkValue(value, iteration);
                if ( ynew<=yold) {
                    value = newValue;
                    break;
                } else if (shouldChange(ynew, yold, temp)) {
                    value = newValue;
                    break;
                } else {
                    continue;
                }
            }
            //wenn der Wert sich mehrere male hinterinander verschlechtern würde -> abbrechen weil vorraussichtlich optimum erreicht
            t += 1;
            temp *= 1 - coolingRate;
            System.out.println("X: " + value + " Y: " + checkValue(value, iteration));
        }
        return value;
    }

    //almost always true, no idea why, should work
    public boolean shouldChange(double ynew, double yold, double temp) {
        
        double exp = Math.exp(-(ynew - yold) / temp);
        double val = ThreadLocalRandom.current().nextDouble(0, 1);
        System.out.println(yold + " " + ynew +" " + temp + " " + exp + " " + val);
        if (exp>=1)
            System.exit(0);
        if (val < exp) {
            System.out.println("Exp: " + exp + ": true");
            return true;
        } else {
            System.out.println("Exp: " + exp + ": false");
            return false;
        }
    }
}
