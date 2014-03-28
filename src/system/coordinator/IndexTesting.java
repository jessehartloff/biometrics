package system.coordinator;

import java.util.Collections;

import system.allcommonclasses.commonstructures.RawScores;
import system.allcommonclasses.commonstructures.User;
import system.allcommonclasses.commonstructures.Users;
import system.allcommonclasses.indexingstructure.IndexingStructure;
import system.hasher.Hasher;

public class IndexTesting extends Coordinator {

	public IndexingStructure indexingStructure;

	public IndexTesting(Hasher hasher, Users enrollees,
			IndexingStructure indexingStructure) {
		super(hasher, enrollees);
		this.indexingStructure = indexingStructure;
	}

	@Override
	public RawScores run() {

		// RawScores scores = new RawScores();
		RawScores scores = this.nextCoordinator.run();

		int total = this.users.users.size();

		Long numberEnrolled = 0L;
		// add the first reading of each user to the structure
		for(User user : this.users.users) {
			hasher.addToIndexingStructure(user.readings.get(0), new Long(numberEnrolled), indexingStructure);
//			hasher.addToIndexingStructure(user.readings.get(0), new Long(user.id), indexingStructure);
			numberEnrolled++;
			System.out.println("Building indexing structure: " + (numberEnrolled * 100.0) / (total * 1.0) + "%");
		}

		// test with the rest of the readings
		int indexID=0;
		for (User user : this.users.users) {
			int n = user.readings.size();
			for (int i = 1; i < n; i++) {
				scores.indexRankings.add(hasher.findIndexingRank(user.readings.get(i), new Long(indexID), indexingStructure, numberEnrolled));
//				scores.indexRankings.add(hasher.findIndexingRank(user.readings.get(i), new Long(user.id), indexingStructure, numberEnrolled));
			}
			indexID++;
		}

		Collections.sort(scores.indexRankings);
		return scores;
	}

}
