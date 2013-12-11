package system.allcommonclasses.settings;

import java.io.Serializable;
import system.makefeaturevector.fingerprintmethods.FingerprintMethod;

public class GlobalSettings implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String fingerprintMethodString;
	private String irisMethod;
	private String faceMethod;
	private String coordinator;
	private String hasher;
	private String distanceFunction;
	
	public static FingerprintMethod fingerprintMethod; 
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
	public String getFingerprintMethodString() {return fingerprintMethodString;}
	public void setFingerprintMethodString(String fingerprintMethodString) {this.fingerprintMethodString = fingerprintMethodString;}
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
