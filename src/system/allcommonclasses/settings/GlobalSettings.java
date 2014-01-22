package system.allcommonclasses.settings;

import java.io.Serializable;


public class GlobalSettings implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//settings
	private String fingerprintMethod;
	private String irisMethod;
	private String faceMethod;
	private String hasher;
	private String distanceFunction;
	private String dataset;
	private String testGenerator;
	private String matchingCoordinator;
	private String indexingCoordinator; 
	private String histogramCoordinator; 
	private Boolean multiserver;
	private String multiserverMatchingCoordinator;
	private String multiserverIndexingCoordinator; 
	private String multiserverHistogramCoordinator;
	private String indexingStructure;
	//
	
	//singleton
	private static GlobalSettings instance;
	private GlobalSettings(){}
	public static GlobalSettings getInstance(){
		if(instance == null){
			instance = new GlobalSettings();
		}
		return instance;
	}
	
	
	//getters and setters
	public String getFingerprintMethodString() {return fingerprintMethod;}
	public void setFingerprintMethodString(String fingerprintMethodString) {
		
		this.fingerprintMethod = fingerprintMethodString;
		}
	public String getIrisMethod() {return irisMethod;}
	public void setIrisMethod(String irisMethod) {this.irisMethod = irisMethod;}
	public String getFaceMethod() {return faceMethod;}
	public void setFaceMethod(String faceMethod) {this.faceMethod = faceMethod;}
	public String getHasher() {return hasher;}
	public void setHasher(String hasher) {this.hasher = hasher;}
	public String getDistanceFunction() {return distanceFunction;}
	public void setDistanceFunction(String distanceFunction) {this.distanceFunction = distanceFunction;}
	public String getDataset() {return dataset;}
	public void setDataset(String dataset) {this.dataset = dataset;}
	public String getTestGenerator() {return testGenerator;}
	public void setTestGenerator(String testGenerator) {this.testGenerator = testGenerator;}
	public String getMatchingCoordinator() {
		return matchingCoordinator;
	}
	public void setMatchingCoordinator(String matchingCoordinator) {
		this.matchingCoordinator = matchingCoordinator;
	}
	public String getIndexingCoordinator() {
		return indexingCoordinator;
	}
	public void setIndexingCoordinator(String indexingCoordinator) {
		this.indexingCoordinator = indexingCoordinator;
	}
	public String getHistogramCoordinator() {
		return histogramCoordinator;
	}
	public void setHistogramCoordinator(String histogramCoordinator) {
		this.histogramCoordinator = histogramCoordinator;
	}
	public Boolean getMultiserver() {
		return multiserver;
	}
	public void setMultiserver(Boolean multiserver) {
		this.multiserver = multiserver;
	}
	public String getMultiserverMatchingCoordinator() {
		return multiserverMatchingCoordinator;
	}
	public void setMultiserverMatchingCoordinator(
			String multiserverMatchingCoordinator) {
		this.multiserverMatchingCoordinator = multiserverMatchingCoordinator;
	}
	public String getMultiserverIndexingCoordinator() {
		return multiserverIndexingCoordinator;
	}
	public void setMultiserverIndexingCoordinator(
			String multiserverIndexingCoordinator) {
		this.multiserverIndexingCoordinator = multiserverIndexingCoordinator;
	}
	public String getMultiserverHistogramCoordinator() {
		return multiserverHistogramCoordinator;
	}
	public void setMultiserverHistogramCoordinator(
			String multiserverHistogramCoordinator) {
		this.multiserverHistogramCoordinator = multiserverHistogramCoordinator;
	}
	public String getIndexingStructure() {
		return indexingStructure;
	}
	public void setIndexingStructure(String indexingStructure) {
		this.indexingStructure = indexingStructure;
	}



}
