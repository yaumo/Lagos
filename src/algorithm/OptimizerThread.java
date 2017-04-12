package algorithm;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import messaging.RabbitMQListenerThread;
import messaging.RabbitMQSenderThread;

public class OptimizerThread implements Runnable {
	private double[] solutionVector = new double[17];
	private Solution start;
	private RabbitMQSenderThread send = new RabbitMQSenderThread();
	public Object obj = new Object();
	private RabbitMQListenerThread listen;
	private Thread listener;
	private int n;
	private double u;
	private double temp;
	private double coolingRate;
	private int threadID;

	OptimizerThread(int maxIterations, double jumpingRange, double temp, double coolingRate, int threadID, double[] startSolutionVector) {
		double[] tempVector = new double[startSolutionVector.length];
		for(int i = 0; i < startSolutionVector.length;i++){
			solutionVector[i] = startSolutionVector[i];
			tempVector[i] = startSolutionVector[i];			
		}
		start = new Solution(tempVector);
		this.n = maxIterations;
		this.u = jumpingRange;
		this.temp = temp;
		this.coolingRate = coolingRate;
		this.threadID = threadID;
		listen = new RabbitMQListenerThread(obj, threadID);
		System.out.println("created thread");
		listener = new Thread(listen);
		simulatedAnnealing.listener = listener;
		listener.start();
		System.out.println("started listener");
		System.out.println("Startvector of Thread "+threadID+": "+Arrays.toString(start.getSolutionVector()));
	}

	@SuppressWarnings("deprecation")
	public void run() {
		synchronized (obj) {
			Solution current = start;
			double[] vector = current.getSolutionVector();
			for (int e = 0; e < vector.length; e++) {
				System.out.println("Thread ID:"+threadID+" - Variable Iteration: " + e);
				double optimizedValue = optimizeVariable(vector[e], n, u, e, temp, coolingRate);
				vector[e] = optimizedValue;
				solutionVector[e] = optimizedValue;
			}
			current.setSolutionVector(vector);
			current = updateSolution(current);
			System.out.println("-------Thread " + threadID + " finished:-------");
			System.out.println("Used parameters: Iterations: "+n+" Range: "+u+" Temp: "+temp+" Rate: "+coolingRate);
			System.out.println(current.toJSON());
			listen.shutdown();
			send.closeCon();
			Thread.currentThread().stop();

		}
	}

	public Solution updateSolution(Solution sol) {
		try {
			send.sendMessage(sol.toJSON());
			listen.setLastSolutionCandidate(sol);
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
			Solution sol = new Solution(solutionVector);
			send.sendMessage(sol.toJSON());
			listen.setLastSolutionCandidate(sol);
			obj.wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return listen.getValue();
	}

	public double optimizeVariable(double value, int n, double u, int iteration, double temp, double coolingRate) {
		double localTemp = temp;
		int t = 0;
		while (localTemp > 1) {
			for (int i = 1; i <= n; i++) {
				double newValue = value + ThreadLocalRandom.current().nextDouble(-u, u);
				double ynew = checkValue(newValue, iteration);
				double yold = checkValue(value, iteration);
				if (ynew <= yold) {
					value = newValue;
					break;
				} else if (shouldChange(ynew, yold, localTemp)) {
					value = newValue;
					break;
				} else {
					continue;
				}
			}
			// wenn der Wert sich mehrere male hinterinander verschlechtern würde -> abbrechen weil vorraussichtlich optimum erreicht
			t += 1;
			localTemp *= 1 - coolingRate;
		}
		//System.out.println("X: " + value + " Y: " + checkValue(value, iteration));
		return value;
	}

	// almost always true, no idea why, should work
	public boolean shouldChange(double ynew, double yold, double localTemp) {
		double exp = Math.exp(-(ynew - yold) / localTemp);
		double val = ThreadLocalRandom.current().nextDouble(0, 1);
		//System.out.println(yold + " " + ynew + " " + localTemp + " " + exp + " " + val);
		if (exp >= 1)
			System.exit(0);
		if (val < exp) {
			//System.out.println("Exp: " + exp + ": true");
			return true;
		} else {
			//System.out.println("Exp: " + exp + ": false");
			return false;
		}
	}
}
