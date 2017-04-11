package algorithm;

public class simulatedAnnealing {

	public static Thread listener;
	private static int[] maxIterations = {100,200,300,400,500};
	private static double[] jumpingRanges = {0.1,0.5,1,3,5};
	
	public static void main(String[] args) {

		//int maxIterations, double jumpingRange, double temp, double coolingRate, int threadID
		for(int i = 1; i <= 3; i++){
			Runnable thread = new OptimizerThread(100, 5, 10000, 0.03, i);
			Thread optimizer = new Thread(thread);
			optimizer.start();
		}
	}
}