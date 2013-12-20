package system.coordinator;

import system.allcommonclasses.RawScores;
import system.allcommonclasses.Users;
import system.hasher.Hasher;

public class Histogram extends Coordinator{

	{}// TODO -Histogram. This might not need to be it's own coordinator. It depends on how we handle binning
	
	public Histogram(Hasher hasher, Users users) {
		super(hasher, users);
	}

	@Override
	public RawScores run() {
		return null;
	}

}
