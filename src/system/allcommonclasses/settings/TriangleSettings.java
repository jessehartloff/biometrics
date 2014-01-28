package system.allcommonclasses.settings;

import java.io.Serializable;
import java.util.HashSet;

import system.allcommonclasses.settings.settingsvariables.SettingsVariable;

public class TriangleSettings implements Serializable{

	private static final long serialVersionUID = 1L;

	private HashSet<SettingsVariable> allVariables;
	
	//settings
	public MethodVariableSettings theta0;
	public MethodVariableSettings x1;
	public MethodVariableSettings y1;
	public MethodVariableSettings theta1;
	public MethodVariableSettings x2;
	public MethodVariableSettings y2;
	public MethodVariableSettings theta2;
	
	//public ArrayList<MethodVariable> variables;? maybe something like this
	private Double rotationStep;
	private Double rotationStart;
	private Double rotationStop;
	
	private Double thresholdForTriplets;
	
	private Long minimumPointsForTripletOfTriangles;

	private Long kClosestMinutia;
	private Long kClosestTriangles;
	//

	
	//singleton
	private static TriangleSettings instance;
	private TriangleSettings(){
		this.theta0 = new MethodVariableSettings();
		this.x1 = new MethodVariableSettings();
		this.y1 = new MethodVariableSettings();
		this.theta1 = new MethodVariableSettings();
		this.x2 = new MethodVariableSettings();
		this.y2 = new MethodVariableSettings();
		this.theta2 = new MethodVariableSettings();
		//this.variables = new ArrayList<MethodVariable>();
	}
	public static TriangleSettings getInstance(){
		if(instance == null){
			instance = new TriangleSettings();
		}
		return instance;
	}

	
	//getters and setter
	public Double getRotationStep() {
		return rotationStep;
	}

	public void setRotationStep(Double rotationStep) {
		this.rotationStep = rotationStep;
	}

	public Double getRotationStart() {
		return rotationStart;
	}

	public void setRotationStart(Double rotationStart) {
		this.rotationStart = rotationStart;
	}

	public Double getRotationStop() {
		return rotationStop;
	}

	public void setRotationStop(Double rotationStop) {
		this.rotationStop = rotationStop;
	}

	public Double getThresholdForTriplets() {
		return thresholdForTriplets;
	}

	public void setThresholdForTriplets(Double thresholdForTriplets) {
		this.thresholdForTriplets = thresholdForTriplets;
	}

	public Long getMinimumPointsForTripletOfTriangles() {
		return minimumPointsForTripletOfTriangles;
	}

	public void setMinimumPointsForTripletOfTriangles(
			Long minimumPointsForTripletOfTriangles) {
		this.minimumPointsForTripletOfTriangles = minimumPointsForTripletOfTriangles;
	}

	public Long getkClosestMinutia() {
		return kClosestMinutia;
	}

	public void setkClosestMinutia(Long kClosestMinutia) {
		this.kClosestMinutia = kClosestMinutia;
	}

	public Long getkClosestTriangles() {
		return kClosestTriangles;
	}

	public void setkClosestTriangles(Long kClosestTriangles) {
		this.kClosestTriangles = kClosestTriangles;
	}

	
	
}
