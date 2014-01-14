package system.coordinator;

import system.allcommonclasses.commonstructures.RawScores;
import system.allcommonclasses.commonstructures.Users;
import system.hasher.Hasher;

public class Histogram extends Coordinator{

	{}// TODO -Histogram. This might not need to be it's own coordinator.
	
	public Histogram(Hasher hasher, Users users) {
		super(hasher, users);
	}

	@Override
	public RawScores run() {
		return null;
	}

}
