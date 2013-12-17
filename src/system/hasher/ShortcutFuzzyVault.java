package system.hasher;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

import system.allcommonclasses.Template;
import system.allcommonclasses.modalities.Biometric;
import system.allcommonclasses.settings.FuzzyVaultSettings;

/**
 * 
 * This class tests the fuzzy vault, without actually implementing all the functionality of the
 * scheme. This should only be used to test matching scores.
 *
 */
public class ShortcutFuzzyVault extends Hasher {

	FuzzyVaultSettings settings;
	
	public ShortcutFuzzyVault(){
		settings = FuzzyVaultSettings.getInstance();
	}
	
	@Override
	public Template makeEnrollTemplate(Biometric biometric) {
		Template template = biometric.quantizeOne();
		for(BigInteger bigInt : template.hashes){
			bigInt = bigInt.shiftLeft(1);
		}
		
		this.addChaffPoints(template);
		
		return template;
	}

	@Override
	public ArrayList<Template> makeTestTemplates(Biometric biometric) {
		ArrayList<Template> templates = biometric.quantizeAll();
		for(Template template : templates){
			for(BigInteger bigInt : template.hashes){
				bigInt = bigInt.shiftLeft(1);
			}
		}
		return templates;
	}

	@Override
	public Double compareTemplates(Template enrolledTemplate, ArrayList<Template> testTemplates) {
		Double maxScore = Double.NEGATIVE_INFINITY;
		for (Template template : testTemplates) {
			Double score = 0.0;
			for (BigInteger hash : template.hashes) {
				if(enrolledTemplate.hashes.contains(hash)){
					score += 1.0;
				}else if(enrolledTemplate.hashes.contains(hash.add(BigInteger.valueOf(1)))){
					score -= 1.0;
				}
			}
			if(score > maxScore){
				maxScore = score;
			}
		}
		return maxScore;
	}

	//not the best. Does not check if values are already in the set. could do this easy for existing chaff, 
	//but to check for collisions with genuines would need to handle shifts that way this is set up.
	private void addChaffPoints(Template template){
		for(int i=0; i<settings.getNumberOfChaffPoints(); i++){
			Random random = new Random();
			BigInteger chaff = new BigInteger(settings.getNumberOfBitsForTheField().intValue(),random);
			chaff = chaff.shiftLeft(1).add(BigInteger.valueOf(1));
			template.hashes.add(chaff);
		}
	}

}
