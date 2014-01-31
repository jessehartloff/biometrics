package system.allcommonclasses.indexingstructure;

import system.allcommonclasses.indexingstructure.IndexingStructure;
import system.allcommonclasses.indexingstructure.RAMStructure;

public class IndexingStructureFactory {

	public static IndexingStructure makeIndexingStructure() {
		// TODO Jim - IndexingStructureFactory
		return new RAMStructure();
	}

}
