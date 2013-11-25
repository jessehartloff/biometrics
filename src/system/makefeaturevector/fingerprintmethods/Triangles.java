package system.makefeaturevector.fingerprintmethods;

import java.math.BigInteger;
import java.util.ArrayList;

import system.allcommonclasses.Template;
import system.allcommonclasses.modalities.Fingerprint;


/**
 * 
 * Determines how triangles are formed and quantized. Triangles are triplets of minutiae points and...
 * 
 *
 */
public class Triangles extends FingerprintMethod {

	protected Triangles() {
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
	public static FingerprintMethod getInstance(){
		if(singleFingerprintMethod == null){
			singleFingerprintMethod = new Triangles();
		}
		else{
			FingerprintMethod.checkClass("Triangles");
		}
		return singleFingerprintMethod;
	}
	
	
	@Override
	public Template quantizeOne(Fingerprint fingerprint) {
		{}// TODO quantize triangles
		return null;
	}

	@Override
	public ArrayList<Template> quantizeAll(Fingerprint fingerprint) {
		ArrayList<Template> templates = new ArrayList<Template>(); 
		templates.add(this.quantizeOne(fingerprint));
		return templates;
	}

	@Override
	public Double distance(BigInteger point1, BigInteger point2) {
		{}// TODO triangle distance
		return null;
	}

}
