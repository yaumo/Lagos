package algorithm;

public class simulatedAnnealing {

	public static Thread listener;

	public static void main(String[] args) {

		Runnable thread = new Optimizer(100, 1);
		Thread optimizer = new Thread(thread);
		optimizer.start();

	}
}