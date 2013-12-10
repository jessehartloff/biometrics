package system.makefeaturevector.fingerprintmethods;

import java.math.BigInteger;
import java.util.ArrayList;
import system.allcommonclasses.Template;
import system.allcommonclasses.modalities.Fingerprint;


/**
 * 
 * Determines how minutia points are quantized and compared
 *
 */
public class MinutiaeMethod extends FingerprintMethod {

	public MinutiaeMethod() {
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
/*	public static FingerprintMethod getInstance(){
		if(singleFingerprintMethod == null){
			singleFingerprintMethod = new MinutiaeMethod();
		}
		else{
			FingerprintMethod.checkClass("MinutiaeMethod");
		}
		return singleFingerprintMethod;
	}
*/

	@Override
	public Template quantizeOne(Fingerprint fingerprint) {
		{}// TODO quantize minutiae
		return null;
	}


	@Override
	public ArrayList<Template> quantizeAll(Fingerprint fingerprint) {
		{}// TODO brute force minutiae quantization
		return null;
	}


	@Override
	public Double distance(BigInteger point1, BigInteger point2) {
		{}// TODO minutiae distance
		return null;
	}

	@Override
	public void doAllTheBinning(ArrayList<Template> templates) {
		// TODO Auto-generated method stub
		
	}
}
