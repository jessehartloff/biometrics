package system.allcommonclasses.settings;

import java.io.Serializable;

public class TriangleSettings implements Serializable{

	private static final long serialVersionUID = 1L;

	public MethodVariable theta0;
	public MethodVariable x1;
	public MethodVariable y1;
	public MethodVariable theta1;
	public MethodVariable x2;
	public MethodVariable y2;
	public MethodVariable theta2;
	
	//public ArrayList<MethodVariable> variables;
	
	private Double rotationStep;
	private Double rotationStart;
	private Double rotationStop;
	
	private Double thresholdForTriplets;

	private static TriangleSettings instance;
	
	private TriangleSettings(){
		this.theta0 = new MethodVariable();
		this.x1 = new MethodVariable();
		this.y1 = new MethodVariable();
		this.theta1 = new MethodVariable();
		this.x2 = new MethodVariable();
		this.y2 = new MethodVariable();
		this.theta2 = new MethodVariable();
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

	
}
