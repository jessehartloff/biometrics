package system.allcommonclasses.settings;

import java.io.Serializable;

import system.makefeaturevector.fingerprintmethods.FingerprintMethod;

public class Settings implements Serializable {
	
	// Not thrilled about this idea, but it should work.
	
	public static final long serialVersionUID = 1L;
	
	
	// GlobalSettings
	public String fingerprintMethod; 
	public String irisMethod;
	public String faceMethod;
	
	public String coordinator;
	public String hasher;
	public String distanceFunction;
	
	
	// MinutiaeSettings
	public Integer bitsForX;
	public Integer bitsForY;
	public Integer bitsForTheta;
	
	// PathSettings
	public Integer bitsForD0;
	public Integer bitsForD1;
	public Integer bitsForD2;
	public Integer bitsForD3;
	public Integer bitsForSigma0;
	public Integer bitsForSigma1;
	public Integer bitsForSigma2;
	public Integer bitsForSigma3;
	public Integer bitsForPhi1;
	public Integer bitsForPhi2;
	public Integer bitsForPhi3;
	
	// TriangleSettings
	public Integer bitsForTheta0;
	public Integer bitsForX1;
	public Integer bitsForY1;
	public Integer bitsForTheta1;
	public Integer bitsForX2;
	public Integer bitsForY2;
	public Integer bitsForTheta2;
	
	public Double rotationStep;
	public Double rotationStart;
	public Double rotationStop;
	
	public void saveSettings(){	
		
		// GlobalSettings
		fingerprintMethod = GlobalSettings.fingerprintMethod;
		irisMethod = GlobalSettings.irisMethod;
		faceMethod = GlobalSettings.faceMethod;

		coordinator = GlobalSettings.coordinator;
		hasher = GlobalSettings.hasher;
		distanceFunction = GlobalSettings.distanceFunction;
		
		// MinutiaeSettings
		bitsForX = MinutiaeSettings.bitsForX;
		bitsForY = MinutiaeSettings.bitsForY;
		bitsForTheta = MinutiaeSettings.bitsForTheta;
		
		// PathSettings
		bitsForD0 = PathSettings.bitsForD0;
		bitsForD1 = PathSettings.bitsForD1;
		bitsForD2 = PathSettings.bitsForD2;
		bitsForD3 = PathSettings.bitsForD3;
		bitsForSigma0 = PathSettings.bitsForSigma0;
		bitsForSigma1 = PathSettings.bitsForSigma1;
		bitsForSigma2 = PathSettings.bitsForSigma2;
		bitsForSigma3 = PathSettings.bitsForSigma3;
		bitsForPhi1 = PathSettings.bitsForPhi1;
		bitsForPhi2 = PathSettings.bitsForPhi2;
		bitsForPhi3 = PathSettings.bitsForPhi3;
		
		// TriangleSettings
		bitsForTheta0 = TriangleSettings.bitsForTheta0;
		bitsForX1 = TriangleSettings.bitsForX1;
		bitsForY1 = TriangleSettings.bitsForY1;
		bitsForTheta1 = TriangleSettings.bitsForTheta1;
		bitsForX2 = TriangleSettings.bitsForX2;
		bitsForY2 = TriangleSettings.bitsForY2;
		bitsForTheta2 = TriangleSettings.bitsForTheta2;
		
		rotationStep = TriangleSettings.rotationStep;
		rotationStart = TriangleSettings.rotationStart;
		rotationStop = TriangleSettings.rotationStop;
		
	}
	
	public void loadSettings(){
		
		// GlobalSettings
		GlobalSettings.fingerprintMethod = fingerprintMethod;
		GlobalSettings.irisMethod = irisMethod;
		GlobalSettings.faceMethod = faceMethod;

		GlobalSettings.coordinator = coordinator;
		GlobalSettings.hasher = hasher;
		GlobalSettings.distanceFunction = distanceFunction;
		
		// MinutiaeSettings
		MinutiaeSettings.bitsForX = bitsForX;
		MinutiaeSettings.bitsForY = bitsForY;
		MinutiaeSettings.bitsForTheta = bitsForTheta;
		
		// PathSettings
		PathSettings.bitsForD0 = bitsForD0;
		PathSettings.bitsForD1 = bitsForD1;
		PathSettings.bitsForD2 = bitsForD2;
		PathSettings.bitsForD3 = bitsForD3;
		PathSettings.bitsForSigma0 = bitsForSigma0;
		PathSettings.bitsForSigma1 = bitsForSigma1;
		PathSettings.bitsForSigma2 = bitsForSigma2;
		PathSettings.bitsForSigma3 = bitsForSigma3;
		PathSettings.bitsForPhi1 = bitsForPhi1;
		PathSettings.bitsForPhi2 = bitsForPhi2;
		PathSettings.bitsForPhi3 = bitsForPhi3;
		
		// TriangleSettings
		TriangleSettings.bitsForTheta0 = bitsForTheta0;
		TriangleSettings.bitsForX1 = bitsForX1;
		TriangleSettings.bitsForY1 = bitsForY1;
		TriangleSettings.bitsForTheta1 = bitsForTheta1;
		TriangleSettings.bitsForX2 = bitsForX2;
		TriangleSettings.bitsForY2 = bitsForY2;
		TriangleSettings.bitsForTheta2 = bitsForTheta2;
		
		TriangleSettings.rotationStep = rotationStep;
		TriangleSettings.rotationStart = rotationStart;
		TriangleSettings.rotationStop = rotationStop;
	}
}
