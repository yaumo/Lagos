package algorithm;
import java.util.UUID;
import org.json.*;
public class Solution {
    private String solutionCandidateID;
    private double[] solutionVector = {42.0, 2.0, 8.0, 1.335, 345.0, 3.5, 3.80, 34.5, 5.0, 561.0, 4.0, 3.0, 7.0, 2.0, 7.0, 4.0, 2.0};
    private double resultValue=0.0;
    private boolean isFeasible=false;
    private boolean isEvaluated=false;
    
    Solution(double[] solutions){
        solutionCandidateID= UUID.randomUUID().toString();
        this.setSolutionVector(solutions);
    }
    Solution(){
        solutionCandidateID= UUID.randomUUID().toString();
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
