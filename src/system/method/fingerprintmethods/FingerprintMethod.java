package system.method.fingerprintmethods;

import java.util.ArrayList;

import system.allcommonclasses.*;
import system.allcommonclasses.commonstructures.Template;
import system.allcommonclasses.modalities.*;
import system.method.Method;
import system.method.feature.Feature;
import system.quantizer.Quantizer;

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
	
	
	public ArrayList<Feature> fingerprintToQuantizedFeatures(Fingerprint fingerprint){
		ArrayList<Feature> featuresToReturn = new ArrayList<Feature>();
		ArrayList<Feature> features = this.fingerprintToFeatures(fingerprint);
		for(Feature feature : features){
			featuresToReturn.add(Quantizer.getQuantizer().quantizeFeature(feature));
//			System.out.println("!@#$%^&*()(*&^%$#@!" + Quantizer.getQuantizer().quantizeFeature(feature).quantizedValues.size());
		}
		return featuresToReturn;
	}


	
}
