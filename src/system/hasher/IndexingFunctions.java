package system.hasher;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import system.allcommonclasses.commonstructures.Template;
import system.allcommonclasses.indexingstructure.IndexingPoint;
import system.allcommonclasses.indexingstructure.IndexingStructure;
import system.allcommonclasses.modalities.Biometric;

public class IndexingFunctions {

	

	public Long findIndexingRank(Template template, Long testID, IndexingStructure indexingStructure, Long numberEnrolled) {
	
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
			rank = numberEnrolled; 
			
		}
		
		return rank;
	}
	

}
