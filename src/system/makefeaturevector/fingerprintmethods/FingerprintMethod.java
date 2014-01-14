package system.makefeaturevector.fingerprintmethods;

import java.util.ArrayList;

import system.allcommonclasses.*;
import system.allcommonclasses.commonstructures.Template;
import system.allcommonclasses.modalities.*;
import system.makefeaturevector.Method;
import system.makefeaturevector.feature.Feature;

public abstract class FingerprintMethod extends Method{

	
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
	
	
	
	public abstract ArrayList<Feature> fingerprintToFeatures(Fingerprint fingerprint);


	
}
