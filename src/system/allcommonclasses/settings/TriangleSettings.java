package system.allcommonclasses.settings;

import java.io.Serializable;

public class TriangleSettings implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer binsForTheta0;
	private Integer binsForX1;
	private Integer binsForY1;
	private Integer binsForTheta1;
	private Integer binsForX2;
	private Integer binsForY2;
	private Integer binsForTheta2;
	
	{}//TODO update bits when bins are set
	private Integer bitsForTheta0;
	private Integer bitsForX1;
	private Integer bitsForY1;
	private Integer bitsForTheta1;
	private Integer bitsForX2;
	private Integer bitsForY2;
	private Integer bitsForTheta2;
	
	private Double rotationStep;
	private Double rotationStart;
	private Double rotationStop;


	private static TriangleSettings instance;
	private TriangleSettings(){}
	
	public static TriangleSettings getInstance(){
		if(instance == null){
			instance = new TriangleSettings();
		}
		return instance;
	}

	
	//getters and setter
	public Integer getBinsForTheta0() {
		return binsForTheta0;
	}

	public void setBinsForTheta0(Integer binsForTheta0) {
		this.binsForTheta0 = binsForTheta0;
	}

	public Integer getBinsForX1() {
		return binsForX1;
	}

	public void setBinsForX1(Integer binsForX1) {
		this.binsForX1 = binsForX1;
	}

	public Integer getBinsForY1() {
		return binsForY1;
	}

	public void setBinsForY1(Integer binsForY1) {
		this.binsForY1 = binsForY1;
	}

	public Integer getBinsForTheta1() {
		return binsForTheta1;
	}

	public void setBinsForTheta1(Integer binsForTheta1) {
		this.binsForTheta1 = binsForTheta1;
	}

	public Integer getBinsForX2() {
		return binsForX2;
	}

	public void setBinsForX2(Integer binsForX2) {
		this.binsForX2 = binsForX2;
	}

	public Integer getBinsForY2() {
		return binsForY2;
	}

	public void setBinsForY2(Integer binsForY2) {
		this.binsForY2 = binsForY2;
	}

	public Integer getBinsForTheta2() {
		return binsForTheta2;
	}

	public void setBinsForTheta2(Integer binsForTheta2) {
		this.binsForTheta2 = binsForTheta2;
	}

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

	public Integer getBitsForTheta0() {
		return bitsForTheta0;
	}

	public void setBitsForTheta0(Integer bitsForTheta0) {
		this.bitsForTheta0 = bitsForTheta0;
	}

	public Integer getBitsForX1() {
		return bitsForX1;
	}

	public void setBitsForX1(Integer bitsForX1) {
		this.bitsForX1 = bitsForX1;
	}

	public Integer getBitsForY1() {
		return bitsForY1;
	}

	public void setBitsForY1(Integer bitsForY1) {
		this.bitsForY1 = bitsForY1;
	}

	public Integer getBitsForTheta1() {
		return bitsForTheta1;
	}

	public void setBitsForTheta1(Integer bitsForTheta1) {
		this.bitsForTheta1 = bitsForTheta1;
	}

	public Integer getBitsForX2() {
		return bitsForX2;
	}

	public void setBitsForX2(Integer bitsForX2) {
		this.bitsForX2 = bitsForX2;
	}

	public Integer getBitsForY2() {
		return bitsForY2;
	}

	public void setBitsForY2(Integer bitsForY2) {
		this.bitsForY2 = bitsForY2;
	}

	public Integer getBitsForTheta2() {
		return bitsForTheta2;
	}

	public void setBitsForTheta2(Integer bitsForTheta2) {
		this.bitsForTheta2 = bitsForTheta2;
	}
	
	
	
}
