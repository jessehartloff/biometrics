package system.hasher;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Random;

import system.allcommonclasses.commonstructures.Template;
import system.allcommonclasses.indexingstructure.IndexingPoint;
import system.allcommonclasses.indexingstructure.IndexingStructure;
import system.allcommonclasses.modalities.Biometric;
import system.allcommonclasses.settings.FuzzyVaultSettings;

/**
 * 
 * This class tests the fuzzy vault, without actually implementing all the functionality of the
 * scheme. This should only be used to test matching scores.
 *
 */
public class ShortcutFuzzyVault extends Hasher implements Indexable{

	FuzzyVaultSettings settings;
	IndexingStructure indexingStructure;
	
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

	
	@Override
	public void addToIndexingStructure(Biometric enrollBiometric, Long enrollID, IndexingStructure indexingStructure) {

		Template template = this.makeEnrollTemplate(enrollBiometric);
		for(BigInteger bigInt : template.hashes){
			IndexingPoint pointToAdd = new IndexingPoint();
			pointToAdd.setValue(BigInteger.ZERO);
			pointToAdd.setUserID(enrollID);
			indexingStructure.add(bigInt, pointToAdd);
		}

	}

	
	@Override
	public Long findIndexingRank(Biometric testBiometric, Long testID, IndexingStructure indexingStructure) {
		
		Template template = testBiometric.quantizeOne();
		for(BigInteger bigInt : template.hashes){
			bigInt = bigInt.shiftLeft(1);
		}
	
		Long rank = 0L;
		
		ArrayList<IndexingPoint> indexingPoints = new ArrayList<IndexingPoint>();
		
		
		for(BigInteger bigInt : template.hashes){
			ArrayList<IndexingPoint> binPoints = indexingStructure.getBinContents(bigInt);
			if(binPoints != null){
				indexingPoints.addAll(binPoints);
			}
		}
		
		HashMap<Long, Long> ranks = new HashMap<Long, Long>();
		
		for(IndexingPoint indexingPoint : indexingPoints){
			Long id = indexingPoint.getUserID();
			if(ranks.containsKey(id)){
				ranks.put(id, ranks.get(id)+1);
			}
			else{
				ranks.put(id,  1L);
			}
		}
		
		if(ranks.containsKey(testID)){
			Long genuineHits = ranks.get(testID);
			Collection<Long> allValues = ranks.values();
			for(Long value : allValues){
				if(value >= genuineHits){
					rank++;
				}
			}
		}
		else{
			rank = 100L; // TODO need to remember how many users in the list or do something else here
			
		}
		
		return rank;
	}

}
