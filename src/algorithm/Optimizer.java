package algorithm;

import java.util.concurrent.ThreadLocalRandom;

import messaging.RabbitMQListenerThread;
import messaging.RabbitMQSender;

public class Optimizer implements Runnable {
	private double[] solutionVector = new double[17];
	private Solution start = generateRandomSolution();
	private static RabbitMQSender send = RabbitMQSender.getInstance();
	private static RabbitMQListenerThread listen = RabbitMQListenerThread.getInstance();
	private static Thread listener;
	private int n;
	private double u;
	public static final Object obj = new Object();

	Optimizer(int n, double u) {
		this.n = n;
		this.u = u;
		System.out.println("created thread");
		listener = new Thread(listen);
		simulatedAnnealing.listener = listener;
		listener.start();
		System.out.println("started listener");
	}

	@SuppressWarnings("deprecation")
	public void run() {
		synchronized (obj) {
			Solution current = start;
			double[] vector = current.getSolutionVector();
			for (int e = 0; e < vector.length; e++) {
				System.out.println("Variable Iteration: " + e);
				double optimizedValue = optimizeVariable(vector[e], n, u, e);
				vector[e] = optimizedValue;
				solutionVector[e] = optimizedValue;
			}
			current.setSolutionVector(vector);
			current = updateSolution(current);
			System.out.println("---------------------------------------");
			System.out.println(current.toJSON());
			listen.shutdown();
			send.closeCon();
			Thread.currentThread().stop();

		}
	}

	public Solution generateRandomSolution() {
		double[] vector = new double[17];
		for (int i = 0; i < 17; i++) {
			double tmp = ThreadLocalRandom.current().nextDouble(-5, 5);
			vector[i] = tmp;
			solutionVector[i] = tmp;
		}
		return new Solution(vector);
	}

	public Solution updateSolution(Solution sol) {
		try {
			send.sendMessage(sol.toJSON());
			obj.wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		sol.setResultValue(listen.getValue());
		sol.setIsFeasible(true);
		sol.setIsEvaluated(true);
		return sol;
	}

	public double checkValue(double value, int i) {
		try {
			solutionVector[i] = value;
			send.sendMessage(new Solution(solutionVector).toJSON());
			obj.wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return listen.getValue();
	}

	public double optimizeVariable(double value, int n, double u, int iteration) {
		double temp = 100;
		double coolingRate = 0.03;
		int t = 0;
		while (temp > 1) {
			for (int i = 1; i <= n; i++) {
				double newValue = value + ThreadLocalRandom.current().nextDouble(-u, u);
				double ynew = checkValue(newValue, iteration);
				double yold = checkValue(value, iteration);
				if (ynew <= yold) {
					value = newValue;
					break;
				} else if (shouldChange(ynew, yold, temp)) {
					value = newValue;
					break;
				} else {
					continue;
				}
			}
			// wenn der Wert sich mehrere male hinterinander verschlechtern würde -> abbrechen weil vorraussichtlich optimum erreicht
			t += 1;
			temp *= 1 - coolingRate;
		}
		System.out.println("X: " + value + " Y: " + checkValue(value, iteration));
		return value;
	}

	// almost always true, no idea why, should work
	public boolean shouldChange(double ynew, double yold, double temp) {
		double exp = Math.exp(-(ynew - yold) / temp);
		double val = ThreadLocalRandom.current().nextDouble(0, 1);
		System.out.println(yold + " " + ynew + " " + temp + " " + exp + " " + val);
		if (exp >= 1)
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
