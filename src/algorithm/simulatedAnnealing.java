package algorithm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class simulatedAnnealing {

	//defaults
	static int n = 500; //max iterations per round
    static double u = 5; //number jumping size
	static double temp = 5000;
    static double coolingRate = 0.05;
    //defaults
	
	public static Thread listener;
	private static int[] maxIterations = {100,200,300,400,500,600,700,800,900,1000};
	private static double[] jumpingRanges = {0.1,0.5,1,3,5,7,10,15,20,50,100};
	private static double[] temps = {100,500,1000,2500,5000,10000,100000};
	private static double[] coolingRates = {0.01, 0.03,0.07,0.1,0.3,0.5,1};
	
	private static double[] startSolution;
	
	public static void main(String[] args) {
		readConfigFile();
		startSolution = generateRandomSolution(17);
		
		if(!(args.length == 0) &&!args[0].equals(""))
		{
			System.out.println("Inputmode: " + args[0]);
			switch (args[0]) {
			case "iterations":
				for(int i = 0; i < maxIterations.length; i++){
					Runnable thread = new OptimizerThread(maxIterations[i],u,temp,coolingRate, i+1,startSolution);
					Thread optimizer = new Thread(thread);
					optimizer.start();
				}
				break;
			case "ranges":
				for(int i = 0; i < jumpingRanges.length; i++){
					Runnable thread = new OptimizerThread(n,jumpingRanges[i],temp,coolingRate, i+1,startSolution);
					Thread optimizer = new Thread(thread);
					optimizer.start();
				}
				break;
			case "temps":
				for(int i = 0; i < temps.length; i++){
					Runnable thread = new OptimizerThread(n,u,temps[i],coolingRate, i+1,startSolution);
					Thread optimizer = new Thread(thread);
					optimizer.start();
				}
				break;
			case "rates":
				for(int i = 0; i < coolingRates.length; i++){
					Runnable thread = new OptimizerThread(n,u,temp,coolingRates[i], i+1,startSolution);
					Thread optimizer = new Thread(thread);
					optimizer.start();
				}
				break;
			case "custom":
				try{
					Runnable threadCust = new OptimizerThread(Integer.parseInt(args[1]),Double.parseDouble(args[2]),Double.parseDouble(args[3]),Double.parseDouble(args[4]), 1,startSolution);
					Thread optimizerCust = new Thread(threadCust);
					optimizerCust.start();
				}catch(Exception e){
					System.out.println("Please input valid parameters.");
				}
				break;
			default:
				System.out.println("Given mode is not available.");
				break;
			}
		} else{
			System.out.println("Running with default parameters.");
			Runnable thread = new OptimizerThread(n,u,temp,coolingRate, 1,startSolution);
			Thread optimizer = new Thread(thread);
			optimizer.start();
		}
	}
	
	public static double[] generateRandomSolution(int solutionLength) {
		double[] vector = new double[solutionLength];
		for (int i = 0; i < solutionLength; i++) {
			double tmp = ThreadLocalRandom.current().nextDouble(-3, 3);
			vector[i] = tmp;
		}
		return vector;
	}
	
	public static void readConfigFile(){
		String currentLine = null;
		String inputFileLocation = (new File("").getAbsolutePath()+"/input.txt").replaceAll("\\\\", "/");
		System.out.println("Loading inputfile from: "+inputFileLocation);
	      try {
	    	 int tempN =  0;
	    	 int[] tempNs = null;
	    	 double tempU = 0;
	    	 double[] tempUs = null;
	    	 double tempTemp = 0;
	    	 double[] tempTemps = null;
	    	 double tempRate = 0;
	    	 double[] tempRates = null;
	    	 
	    	  
	    	 File file = new File(inputFileLocation);
	         BufferedReader br = new BufferedReader(new FileReader(file));
	         
	         int i = 0;
	         while ((currentLine = br.readLine()) != null) {
	        	 i++;
	        	 switch (i) {
				case 2:
					tempN = Integer.parseInt(currentLine);
					break;
				case 3:
					tempNs = Arrays.stream(currentLine.split(",")).mapToInt(Integer::parseInt).toArray();
					break;
				case 5:
					tempU = Double.parseDouble(currentLine);
					break;
				case 6:
					tempUs = Arrays.stream(currentLine.split(",")).mapToDouble(Double::parseDouble).toArray();
					break;
				case 8:
					tempTemp = Double.parseDouble(currentLine);
					break;
				case 9:
					tempTemps = Arrays.stream(currentLine.split(",")).mapToDouble(Double::parseDouble).toArray();
					break;
				case 11:
					tempRate = Double.parseDouble(currentLine);
					break;
				case 12:
					tempRates = Arrays.stream(currentLine.split(",")).mapToDouble(Double::parseDouble).toArray();
					break;
				}
	         }
	         
	         n = tempN;
	         maxIterations = tempNs;
	         u = tempU;
	         jumpingRanges = tempUs;
	         temp = tempTemp;
	         temps = tempTemps;
	         coolingRate = tempRate;
	         coolingRates = tempRates;
	         
	         br.close();
	         System.out.println("Input Config successfully loaded.");
	         
	      } catch(Exception e) {
	         System.out.println("Input Config is not valid. Running with hardcoded variables.");
	      }
	}
}