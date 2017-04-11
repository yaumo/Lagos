package algorithm;

public class simulatedAnnealing {

	public static Thread listener;

	public static void main(String[] args) {

		//int maxIterations, double jumpingRange, double temp, double coolingRate
		Runnable thread = new Optimizer(100, 5, 10000, 0.03);
		Thread optimizer = new Thread(thread);
		optimizer.start();

	}
}