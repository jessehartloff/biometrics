package system.allcommonclasses.settings;

import java.io.Serializable;

public class MinutiaeSettings implements Serializable{

	private static final long serialVersionUID = 1L;

	{}//TODO binsFor
	
	private Integer bitsForX;
	private Integer bitsForY;
	private Integer bitsForTheta;
	

	private static MinutiaeSettings instance;
	
	private MinutiaeSettings(){}
	
	public static MinutiaeSettings getInstance(){
		if(instance == null){
			instance = new MinutiaeSettings();
		}
		return instance;
	}
	
	
	// getters and setters
	public Integer getBitsForX() {return bitsForX;}
	public void setBitsForX(Integer bitsForX) {this.bitsForX = bitsForX;}
	public Integer getBitsForY() {return bitsForY;}
	public void setBitsForY(Integer bitsForY) {this.bitsForY = bitsForY;}
	public Integer getBitsForTheta() {return bitsForTheta;}
	public void setBitsForTheta(Integer bitsForTheta) {this.bitsForTheta = bitsForTheta;}
	
}
