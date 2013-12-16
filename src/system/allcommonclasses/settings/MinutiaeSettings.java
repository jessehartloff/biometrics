package system.allcommonclasses.settings;

import java.io.Serializable;

public class MinutiaeSettings implements Serializable{

	private static final long serialVersionUID = 1L;

	public MethodVariable x;
	public MethodVariable y;
	public MethodVariable theta;
	
	private Double rotationStep;
	private Double rotationStart;
	private Double rotationStop;
	
	private Long xStep;
	private Long xStart;
	private Long xStop;
	
	private Long yStep;
	private Long yStart;
	private Long yStop;
	
	private static MinutiaeSettings instance;
	
	private MinutiaeSettings(){
		this.x = new MethodVariable();
		this.y = new MethodVariable();
		this.theta = new MethodVariable();
		}
	
	public static MinutiaeSettings getInstance(){
		if(instance == null){
			instance = new MinutiaeSettings();
		}
		return instance;
	}

	//getters and setters
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

	public Long getxStep() {
		return xStep;
	}

	public void setxStep(Long xStep) {
		this.xStep = xStep;
	}

	public Long getxStart() {
		return xStart;
	}

	public void setxStart(Long xStart) {
		this.xStart = xStart;
	}

	public Long getxStop() {
		return xStop;
	}

	public void setxStop(Long xStop) {
		this.xStop = xStop;
	}

	public Long getyStep() {
		return yStep;
	}

	public void setyStep(Long yStep) {
		this.yStep = yStep;
	}

	public Long getyStart() {
		return yStart;
	}

	public void setyStart(Long yStart) {
		this.yStart = yStart;
	}

	public Long getyStop() {
		return yStop;
	}

	public void setyStop(Long yStop) {
		this.yStop = yStop;
	}
	
}
