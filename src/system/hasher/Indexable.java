package system.hasher;

import system.allcommonclasses.indexingstructure.IndexingStructure;
import system.allcommonclasses.modalities.Biometric;

/**
 * 
 * Outlines the methods needed for indexing. To be implemented by any Hasher that can be indexed. 
 *
 */
public interface Indexable {
	// TODO should this extend hasher?
	public void addToIndexingStructure(Biometric enrollBiometric, Long enrollID, IndexingStructure indexingStructure);
	public Long findIndexingRank(Biometric testBiometric, Long testID, IndexingStructure indexingStructure);
		
}
