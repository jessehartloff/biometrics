package system.allcommonclasses.settings;

import java.io.Serializable;

import system.makefeaturevector.fingerprintmethods.FingerprintMethod;

public class GlobalSettings implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String fingerprintMethod;
	private String irisMethod;
	private String faceMethod;
	private String coordinator;
	private String hasher;
	private String distanceFunction;
	
	public static FingerprintMethod singleFingerprintMethod;
	//public static IrisMethod singleIrisMethod;
	//public static FaceMethod singleFaceMethod;
	
	
	private static GlobalSettings instance;
	
	private GlobalSettings(){}
	
	public static GlobalSettings getInstance(){
		if(instance == null){
			instance = new GlobalSettings();
		}
		return instance;
	}
	
	
	//getters and setters
	public String getFingerprintMethod() {return fingerprintMethod;}
	public void setFingerprintMethod(String fingerprintMethod) {this.fingerprintMethod = fingerprintMethod;}
	public String getIrisMethod() {return irisMethod;}
	public void setIrisMethod(String irisMethod) {this.irisMethod = irisMethod;}
	public String getFaceMethod() {return faceMethod;}
	public void setFaceMethod(String faceMethod) {this.faceMethod = faceMethod;}
	public String getCoordinator() {return coordinator;}
	public void setCoordinator(String coordinator) {this.coordinator = coordinator;}
	public String getHasher() {return hasher;}
	public void setHasher(String hasher) {this.hasher = hasher;}
	public String getDistanceFunction() {return distanceFunction;}
	public void setDistanceFunction(String distanceFunction) {this.distanceFunction = distanceFunction;}
}
