package system.hasher.fuzzyvault;

import java.math.BigInteger;
import java.util.ArrayList;

import system.allcommonclasses.commonstructures.Template;
import system.allcommonclasses.indexingstructure.IndexingStructure;
import system.allcommonclasses.modalities.Biometric;
import system.allcommonclasses.transformations.Transformation;
import system.hasher.Hasher;

/**
 * Implements fuzzy vault.
 */
public class FuzzyVault extends Hasher{
	
	private boolean chaffInjection;
	
	public FuzzyVault(){
		this.chaffInjection = false;
	}
	
	public FuzzyVault(boolean chaffInjection){
		this.chaffInjection = true;
	}
	
	@Override
	public Template hashEnrollTemplate(Template template) {
		Vault vault = new Vault();
		if(this.chaffInjection){
			vault.lockWithChaffInjection(template);
		}else{
			vault.lock(template);
		}
		return vault.toTemplate();
	}
	
	
	@Override
	public ArrayList<Template> hashTestTemplates(ArrayList<Template> templates) {		
		return templates;
	}
	
	
	@Override
	public Double compareTemplates(Template enrolledTemplate, ArrayList<Template> testTemplates) {
		
		Vault lockedVault = new Vault(enrolledTemplate);
		
//		Double maxScore = Double.NEGATIVE_INFINITY;
		for (Template template : testTemplates) {
			if(lockedVault.unlock(template)){
				return 1.0;
			}
			
//			Double score = 0.0;
//			for (BigInteger hash : template.hashes) {
//				{}// todo =fv-try to unlock and get a score. Will involve bigIntToPoint.
//				//      Not sure what to really return yet. ShortcutFuzzyVault can be 
//				//      used to get scores, this should return the polynomial from decoding.
//				//      It might be a long time(if ever) before we need this, but it should
//				//      'probably' be figured out. Also, how do we find the max polynomial?
//			}
//			if(score > maxScore){
//				maxScore = score;
//			}
			
		}
		return 0.0;
//		return maxScore;
	}





	@Override
	public void addToIndexingStructure(Biometric enrollBiometric, Long enrollID, IndexingStructure indexingStructure) {
		// TODO fv-indexing
		
	}

	@Override
	public Long findIndexingRank(Biometric testBiometric, Long testID, IndexingStructure indexingStructure, Long numberEnrolled) {
		// TODO fv-indexing
		return null;
	}
	
}
