package system.makefeaturevector.fingerprintmethods;

import java.util.ArrayList;

import system.allcommonclasses.*;
import system.allcommonclasses.modalities.*;
import system.makefeaturevector.Method;

public abstract class FingerprintMethod extends Method{

	
	// singleton
	protected static FingerprintMethod singleFingerprintMethod;
	
	
	protected FingerprintMethod() {
	}


	/**
	 * Converts a fingerprint into a Template containing hashable data
	 * 
	 * @return a template
	 */
	public abstract Template quantizeOne(Fingerprint fingerprint); 

	
	/**
	 * Converts a fingerprint into a template containing hashable data including all variations 
	 * (rotations, translations) if applicable. For invariant methods, this should just call 
	 * quantizeOne(fingerprint).
	 * 
	 * @return set of all templates. For invariant methods, this set will be of size 1.
	 */
	public abstract ArrayList<Template> quantizeAll(Fingerprint fingerprint);
	

	/**
	 * Class type check for singleton. There can only be one FingerprintMethod, so this method
	 * is used to check if the method asked for is the one returned.
	 * 
	 * Example: An instance of Paths is created, then an instance of Triangles is asked for.
	 * Since there can only be one, Paths is returned when Triangles is expected.
	 * 
	 * This method is called to check for these cases. It is static so the static subclass methods 
	 * can use it.
	 * 
	 * @param expectedClass Sting representing the expected class without package identifiers. ("Paths")
	 */
	protected static void checkClass(String expectedClass){
		String s = singleFingerprintMethod.getClass().toString();
		if(s.substring(s.lastIndexOf(".")+1).compareTo(expectedClass) != 0){
			{}// TODO make this warning more noticeable. Maybe crash the whole program.
			System.out.println("Not what you were expecting. You got: " + 
					singleFingerprintMethod.getClass().toString());
		}
	}


	
}
