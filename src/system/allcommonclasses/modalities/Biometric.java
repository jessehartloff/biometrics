package system.allcommonclasses.modalities;

import java.io.Serializable;

/**
 * 
 * Ensures every biometrics has functions for quantization for template
 * generation.
 * 
 */
public abstract class Biometric implements Serializable {

	private static final long serialVersionUID = 1L;

//	public static Method method;

	/**
	 * converts this biometric into hashable data
	 * 
	 * @return feature vector to be hashed
	 */
//	public abstract Template quantizeOne();

	/**
	 * converts this biometric into hashable data including all variations
	 * (rotations, translations) if applicable
	 * 
	 * @return ArrayList of feature vectors to be hashed
	 */
//	public abstract ArrayList<Template> quantizeAll();
//
//	public abstract ArrayList<Feature> toFeatures();
//
//	public abstract ArrayList<Feature> toQuantizedFeatures();

	public boolean isFailure() {
		return false;
	}

}
