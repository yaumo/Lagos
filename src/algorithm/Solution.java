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
    String toJSON(){
        JSONObject json = new JSONObject(this);
        return json.toString();
    }
    public String getSolutionCandidateID() {
        return solutionCandidateID;
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
}
