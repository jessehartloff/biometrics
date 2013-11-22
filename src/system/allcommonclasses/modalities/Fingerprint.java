package system.allcommonclasses.modalities;

import java.util.ArrayList;
import system.allcommonclasses.Template;
import system.makefeaturevector.fingerprintmethods.*;

/**
 * A fingerprint.
 *
 */
public class Fingerprint extends Biometric{

	public class Minutia{

		public Integer x;
		public Integer y;
		public Integer theta;
		public Integer confidence; 
		
	}

	private FingerprintMethod theFingerprintMethod; // The fingerprints carry their method with them.
	public ArrayList<Minutia> minutiae;
	
	/**
	 * When creating a fingerprint, it's quantization method must be specified.
	 * 
	 * @param method the method determining how the print will be quantized for template generation.
	 */
	public Fingerprint(FingerprintMethod method){
		this.theFingerprintMethod = method;
		this.minutiae = new ArrayList<Minutia>();
	}
	

	public Template quantizeOne(){
		return theFingerprintMethod.quantizeOne(this);
	}

	

	public ArrayList<Template> quantizeAll(){
		return theFingerprintMethod.quantizeAll(this);
	}
	

	// TODO translate/rotate. Should they go here?
	
}
