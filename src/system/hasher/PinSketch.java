package system.hasher;

import java.util.ArrayList;

import system.allcommonclasses.Template;
import system.allcommonclasses.modalities.Biometric;

/**
 * 
 * Does PinSketch.
 *
 */
public class PinSketch extends Hasher {

	@Override
	public Template makeEnrollTemplate(Biometric biometric) {
		{}// TODO PinSketch enrolled template
		return null;
	}

	@Override
	public ArrayList<Template> makeTestTemplates(Biometric biometric) {
		{}// TODO PinSketch test template
		return null;
	}

	@Override
	public Double compareTemplates(Template enrolledTemplate, ArrayList<Template> testTemplates) {
		{}// TODO PinSketch compare templates
		return null;
	}




}
