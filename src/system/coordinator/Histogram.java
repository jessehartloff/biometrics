package system.coordinator;

import system.allcommonclasses.commonstructures.RawScores;
import system.allcommonclasses.commonstructures.Users;
import system.hasher.Hasher;

public class Histogram extends Coordinator{

	public Histogram(Hasher hasher, Users users) {
		super(hasher, users);
	}

	@Override
	public RawScores run() {
		RawScores scores = this.nextCoordinator.run();
		// TODO check if histogram is empty
		// do the histogram with progress
		return scores;
	}

}
