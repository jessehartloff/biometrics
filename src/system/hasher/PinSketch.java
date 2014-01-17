package system.hasher;

import java.util.ArrayList;

import system.allcommonclasses.commonstructures.Template;
import system.allcommonclasses.modalities.Biometric;

/**
 * 
 * Does PinSketch.
 *
 */
public class PinSketch extends Hasher {
	
	
	@Override
	public Template makeEnrollTemplate(Biometric biometric) {
		return null;
	}

	@Override
	public ArrayList<Template> makeTestTemplates(Biometric biometric) {
		return null;
	}


	@Override
	public Double compareTemplates(Template enrolledTemplate, ArrayList<Template> testTemplates) {
		return null;
	}




}
