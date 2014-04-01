package system.hasher;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import settings.hashersettings.ShortcutFuzzyVaultSettings;
import system.allcommonclasses.commonstructures.Template;
import system.allcommonclasses.indexingstructure.IndexingPoint;
import system.allcommonclasses.indexingstructure.IndexingStructure;
import system.allcommonclasses.modalities.Biometric;

/**
 * 
 * This class tests the fuzzy vault, without actually implementing all the
 * functionality of the scheme. This should only be used to test matching
 * scores.
 * 
 */
public class ShortcutFuzzyVault extends Hasher {

	ShortcutFuzzyVaultSettings settings;
	IndexingStructure indexingStructure;
	
	public ShortcutFuzzyVault(){
		settings = ShortcutFuzzyVaultSettings.getInstance();

	}

	@Override
	public Template hashEnrollTemplate(Template template) {
		Template toReturn = new Template();
		for(BigInteger bigInt : template.getHashes()){
			toReturn.getHashes().add(bigInt.shiftLeft(1));

		}
		
		this.addChaffPoints(toReturn);
		
		return toReturn;
	}

	@Override
	public ArrayList<Template> hashTestTemplates(ArrayList<Template> templates) {
		ArrayList<Template> toReturn = new ArrayList<Template>();
		for(Template template : templates){
			Template tempTemplate = new Template();
			for(BigInteger bigInt : template.getHashes()){
				tempTemplate.getHashes().add(bigInt.shiftLeft(1));
			}
			toReturn.add(tempTemplate);
		}
		return toReturn;
	}

	@Override
	public Double compareTemplates(Template enrolledTemplate,
			ArrayList<Template> testTemplates) {
		Double maxScore = Double.NEGATIVE_INFINITY;
		for (Template template : testTemplates) {
			Double score = 0.0;
			for (BigInteger hash : template.getHashes()) {
				if (enrolledTemplate.getHashes().contains(hash)) {
					score += 1.0;
				} else if (enrolledTemplate.getHashes().contains(hash.add(BigInteger.valueOf(1)))){
					score -= 1.0;
				}
			}
			if (score > maxScore) {
				maxScore = score;
			}
		}
		return maxScore;
	}

	// not the best. Does not check if values are already in the set. could do
	// this easy for existing chaff,
	// but to check for collisions with genuines would need to handle shifts
	// that way this is set up.
	private void addChaffPoints(Template template) {
		for (int i = 0; i < settings.numberOfChaffPoints().getValue(); i++) {
			Random random = new Random();
			BigInteger chaff = new BigInteger(settings
					.getNumberOfBitsForTheField().intValue(), random);
			chaff = chaff.shiftLeft(1).add(BigInteger.valueOf(1));
			template.getHashes().add(chaff);
		}
	}

	@Override
	public void addToIndexingStructure(Biometric enrollBiometric, Long enrollID, IndexingStructure indexingStructure) {

		Template template = this.makeEnrollTemplate(enrollBiometric);
		for (BigInteger bigInt : template.getHashes()) {
			IndexingPoint pointToAdd = new IndexingPoint();
			pointToAdd.setValue(BigInteger.ZERO);
			pointToAdd.setUserID(enrollID);
			indexingStructure.add(bigInt, pointToAdd);
		}

	}

	@Override
	public Long findIndexingRank(Biometric testBiometric, Long testID, IndexingStructure indexingStructure, Long numberEnrolled) {

		Template template = testBiometric.quantizeOne();
		for (BigInteger bigInt : template.getHashes()) {
			bigInt = bigInt.shiftLeft(1);
		}

		Long rank = 0L;

		ArrayList<IndexingPoint> indexingPoints = new ArrayList<IndexingPoint>();

		for (BigInteger bigInt : template.getHashes()) {
			ArrayList<IndexingPoint> binPoints = indexingStructure.getBinContents(bigInt);
			if (binPoints != null) {
				indexingPoints.addAll(binPoints);
			}
		}
		
		//.107
		HashMap<Long, Long> ranks = new HashMap<Long, Long>();

		for (IndexingPoint indexingPoint : indexingPoints) {
			Long id = indexingPoint.getUserID();
			if (ranks.containsKey(id)) {
				ranks.put(id, ranks.get(id) + 1);
			} else {
				ranks.put(id, 1L);
			}
		}

		if (ranks.containsKey(testID)) {
			Long genuineHits = ranks.get(testID);
			Collection<Long> allValues = ranks.values();
			int n = allValues.size();
			long[] allValuesArray = new long[n];
			int i=0;
			for(Long l : allValues){
				allValuesArray[i] = l;
				i++;
			}
			Arrays.sort(allValuesArray);
			int j=0;
			while(j<n && allValuesArray[j] < genuineHits){
				j++;
			}
			int q=j;
			while(q<n && allValuesArray[q] < genuineHits+1){
				q++;
			}
			rank = new Long((j+q)/2);
		}else{
			rank = (ranks.size()+numberEnrolled)/2;
		}
		
		return rank;
	}

}

// TODO advanced chaff points in shortcut fuzzy vault