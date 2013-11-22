package system.allcommonclasses.modalities;

import java.util.ArrayList;
import system.allcommonclasses.Template;

/**
 * 
 * Ensures every biometrics has functions for quantization for template generation.
 *
 */
public abstract class Biometric {

	/**
	 * converts this biometric into hashable data
	 * 
	 * @return feature vector to be hashed
	 */
	public abstract Template quantizeOne();

	
	/**
	 * converts this biometric into hashable data including all variations (rotations, translations) 
	 * if applicable
	 * 
	 * @return ArrayList of feature vectors to be hashed
	 */
	public abstract ArrayList<Template> quantizeAll();
}
