package system.coordinator;

import system.allcommonclasses.commonstructures.RawScores;
import system.allcommonclasses.commonstructures.Template;
import system.allcommonclasses.commonstructures.User;
import system.allcommonclasses.commonstructures.Users;
import system.allcommonclasses.indexingstructure.IndexingStructure;
import system.hasher.Hasher;
import system.hasher.Indexable;

public class IndexTesting extends Coordinator {
	
	public Indexable indexer;
	public IndexingStructure indexingStructure;
	
	public IndexTesting(Hasher hasher, Users enrollees, Indexable hasherAgain, IndexingStructure indexingStructure) {
		super(hasher, enrollees);
		this.indexer = hasherAgain;
		this.indexingStructure = indexingStructure;
	}

	@Override
	public RawScores run() {
		
		RawScores scores = new RawScores();
		
		int total = this.users.users.size();
		int soFar = 0;
		
		// add the first reading of each user to the structure
		for(User user : this.users.users){
			indexer.addToIndexingStructure(user.readings.get(0), new Long(user.id), indexingStructure);
			soFar++;
			System.out.println("Building indexing structure: " + (soFar*100.0)/(total*1.0) + "%");
		}
		
		// test with the rest of the readings
		for(User user : this.users.users){
			int n = user.readings.size();
			for(int i=1; i<n; i++){
				scores.indexRankings.add(indexer.findIndexingRank(user.readings.get(i), new Long(user.id), indexingStructure));
			}
		}
		
		return scores;
	}

}
