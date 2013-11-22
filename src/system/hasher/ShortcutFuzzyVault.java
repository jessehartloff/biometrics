package system.hasher;

import java.util.ArrayList;

import system.allcommonclasses.Template;
import system.allcommonclasses.modalities.Biometric;

/**
 * 
 * This class tests the fuzzy vault, without actually implementing all the functionality of the
 * scheme. This should only be used to test matching scores.
 *
 */
public class ShortcutFuzzyVault extends Hasher {

	@Override
	public Template makeEnrollTemplate(Biometric biometric) {
		// TODO ShortcutFuzzyVault enrolled template
		return null;
	}

	@Override
	public ArrayList<Template> makeTestTemplates(Biometric biometric) {
		// TODO ShortcutFuzzyVault test template
		return null;
	}

	@Override
	public Double compareTemplates(Template enrolledTemplate, ArrayList<Template> testTemplates) {
		// TODO ShortcutFuzzyVault compare templates
		return null;
	}


}
