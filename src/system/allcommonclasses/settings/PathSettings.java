package system.allcommonclasses.settings;

import java.io.Serializable;

public class PathSettings implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer binsForD0;
	private Integer binsForD1;
	private Integer binsForD2;
	private Integer binsForD3;
	private Integer binsForSigma0;
	private Integer binsForSigma1;
	private Integer binsForSigma2;
	private Integer binsForSigma3;
	private Integer binsForPhi1;
	private Integer binsForPhi2;
	private Integer binsForPhi3;
	
	{}//TODO bitsFor...
	

	private static PathSettings instance;
	private PathSettings(){}
	
	public static PathSettings getInstance(){
		if(instance == null){
			instance = new PathSettings();
		}
		return instance;
	}

	
	//getters and setters
	public Integer getBinsForD0() {
		return binsForD0;
	}

	public void setBinsForD0(Integer binsForD0) {
		this.binsForD0 = binsForD0;
	}

	public Integer getBinsForD1() {
		return binsForD1;
	}

	public void setBinsForD1(Integer binsForD1) {
		this.binsForD1 = binsForD1;
	}

	public Integer getBinsForD2() {
		return binsForD2;
	}

	public void setBinsForD2(Integer binsForD2) {
		this.binsForD2 = binsForD2;
	}

	public Integer getBinsForD3() {
		return binsForD3;
	}

	public void setBinsForD3(Integer binsForD3) {
		this.binsForD3 = binsForD3;
	}

	public Integer getBinsForSigma0() {
		return binsForSigma0;
	}

	public void setBinsForSigma0(Integer binsForSigma0) {
		this.binsForSigma0 = binsForSigma0;
	}

	public Integer getBinsForSigma1() {
		return binsForSigma1;
	}

	public void setBinsForSigma1(Integer binsForSigma1) {
		this.binsForSigma1 = binsForSigma1;
	}

	public Integer getBinsForSigma2() {
		return binsForSigma2;
	}

	public void setBinsForSigma2(Integer binsForSigma2) {
		this.binsForSigma2 = binsForSigma2;
	}

	public Integer getBinsForSigma3() {
		return binsForSigma3;
	}

	public void setBinsForSigma3(Integer binsForSigma3) {
		this.binsForSigma3 = binsForSigma3;
	}

	public Integer getBinsForPhi1() {
		return binsForPhi1;
	}

	public void setBinsForPhi1(Integer binsForPhi1) {
		this.binsForPhi1 = binsForPhi1;
	}

	public Integer getBinsForPhi2() {
		return binsForPhi2;
	}

	public void setBinsForPhi2(Integer binsForPhi2) {
		this.binsForPhi2 = binsForPhi2;
	}

	public Integer getBinsForPhi3() {
		return binsForPhi3;
	}

	public void setBinsForPhi3(Integer binsForPhi3) {
		this.binsForPhi3 = binsForPhi3;
	}
	
	
}
