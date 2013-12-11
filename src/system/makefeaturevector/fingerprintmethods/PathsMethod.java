package system.makefeaturevector.fingerprintmethods;

import java.math.BigInteger;
import java.util.ArrayList;

import system.allcommonclasses.*;
import system.allcommonclasses.modalities.*;
import system.vectordistance.MeasurableDistance;

/**
 * 
 * Determines how Paths are quantized and compared. Paths are... (describe paths here)
 *
 */
public class PathsMethod extends FingerprintMethod {

	private class Path implements MeasurableDistance<Path>{

		int d1;
		{}// TODO variables for Path
		
		@Override
		public Double distanceFrom(Path compareT) {
			{}// TODO distance
			return null;
		}
		

	}

	
	public PathsMethod() {
	}
	
	/**
	 * The getInstance methods are the only way to create FingerprintMethod objects. This ensures that
	 * each run of the system will only have only one FingerprintMethod that is shared by 
	 * every object that needs one. This prevents cases where two readings are being
	 * compared, but were quantized using different methods.
	 * 
	 * If an instance already exists, a check is made to ensure that the existing method matches
	 * the method asked for.
	 *  
	 * @return An instance of a FingerprintMethod
	 */
//	public static FingerprintMethod getInstance(){
//		if(singleFingerprintMethod == null){
//			singleFingerprintMethod = new PathsMethod();
//		}
//		else{
//			FingerprintMethod.checkClass("Paths");
//		}
//		return singleFingerprintMethod;
//	}



	@Override
	public Template quantizeOne(Fingerprint biometric) {
		{}// TODO quantize paths
		return null;
	}


	@Override
	public ArrayList<Template> quantizeAll(Fingerprint biometric) {
		ArrayList<Template> template = new ArrayList<Template>();
		template.add(this.quantizeOne(biometric));
		return template;
	}

	
	@Override
	public Double distance(BigInteger point1, BigInteger point2) {
		{}// TODO path distance
		return null;
	}

	@Override
	public void doAllTheBinning(ArrayList<Template> templates) {
		// TODO Auto-generated method stub
		
	}

	
	
	
}
