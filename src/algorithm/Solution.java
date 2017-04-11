package algorithm;
import java.util.UUID;
import org.json.*;
public class Solution {
    private String solutionCandidateID;
    private double[] solutionVector;
    private double resultValue=0.0;
    private boolean isFeasible=false;
    private boolean isEvaluated=false;
    
    Solution(double[] solutions){
        solutionCandidateID= UUID.randomUUID().toString();
        this.setSolutionVector(solutions);
    }
    public String toJSON(){
        return new JSONObject(this).toString();
    }
    public String getSolutionCandidateID() {
        return solutionCandidateID;
    }
    public void setSolutionCandidateID(String solutionCandidateID) {
        this.solutionCandidateID = solutionCandidateID;
    }
    public double[] getSolutionVector() {
        return solutionVector;
    }
    public void setSolutionVector(double[] solutions) {
        this.solutionVector = solutions;
    }
    public double getResultValue() {
        return resultValue;
    }
    public void setResultValue(double resultValue) {
        this.resultValue = resultValue;
    }
    public boolean getIsFeasible() {
        return isFeasible;
    }
    public void setIsFeasible(boolean isFeasible) {
        this.isFeasible = isFeasible;
    }
    public boolean getIsEvaluated() {
        return isEvaluated;
    }
    public void setIsEvaluated(boolean isEvaluated) {
        this.isEvaluated = isEvaluated;
    }
    
    public boolean compareSolution(Solution solutionToCompareTo){
    	double[] compareVector = solutionToCompareTo.getSolutionVector();
    	for(int i = 0; i< solutionVector.length;i++){
    		if(solutionVector[i] != compareVector[i])
    			return false;
    	}
    	return true;
    }
}
