package system.hasher;

import java.math.BigInteger;
import java.util.ArrayList;

import system.allcommonclasses.*;
import system.allcommonclasses.commonstructures.Template;
import system.allcommonclasses.modalities.Biometric;
import system.allcommonclasses.transformations.*;

/**
 * 
 * Hashes the set using a standard hash function and scores by set intersection.
 *
 */
public class StraightHasher extends Hasher {

	@Override
	public Template makeEnrollTemplate(Biometric biometric) {
		Template template = biometric.quantizeOne();
		
		Transformation hashFunction = new SHA2();
		for(BigInteger bigInt : template.hashes){
			bigInt = hashFunction.transform(bigInt);
		}
		
		return template;
	}

	@Override
	public ArrayList<Template> makeTestTemplates(Biometric biometric) {
		ArrayList<Template> templates = biometric.quantizeAll();
		
		Transformation hashFunction = new SHA2();
		for(Template template : templates){
			for(BigInteger bigInt : template.hashes){
				bigInt = hashFunction.transform(bigInt);
			}
		}
		
		return templates;
	}

	@Override
	public Double compareTemplates(Template enrolledTemplate, ArrayList<Template> testTemplates) {
	// returns to maximum set intersection between the enrolled template and a test template
		Double maxScore = Double.NEGATIVE_INFINITY;
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


	// TODO Jesse - Straight Hasher Indexing
	
}
