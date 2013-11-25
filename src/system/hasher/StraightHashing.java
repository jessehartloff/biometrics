package system.hasher;

import java.math.BigInteger;
import java.util.ArrayList;

import system.allcommonclasses.Template;
import system.allcommonclasses.modalities.Biometric;

/**
 * 
 * Hashes the set using a standard hash function and scores by set intersection.
 *
 */
public class StraightHashing extends Hasher {

	@Override
	public Template makeEnrollTemplate(Biometric biometric) {
		Template template = biometric.quantizeOne();
		{}// TODO hash these values. Probably make a new base class
		//      for hash functions with a method to hash big ints to big ints. Could be more general
		//      and include permutations.
		return template;
	}

	@Override
	public ArrayList<Template> makeTestTemplates(Biometric biometric) {
		ArrayList<Template> templates = biometric.quantizeAll();
		{}// TODO hash these values.
		return templates;
	}

	@Override
	public Double compareTemplates(Template enrolledTemplate, ArrayList<Template> testTemplates) {
	// returns to maximum set intersection between the enrolled template and a test template
		Double maxScore = Double.MIN_VALUE;
		for (Template template : testTemplates) {
			Double score = 0.0;
			for (BigInteger hash : template.hashes) {
				if(enrolledTemplate.hashes.contains(hash)){
					score += 1.0;
				}
			}
			if(score > maxScore){
				maxScore = score;
			}
		}
		return maxScore;
	}


	
	
}
