package system.coordinator;

import system.allcommonclasses.commonstructures.RawScores;
import system.allcommonclasses.commonstructures.Users;
import system.hasher.Hasher;

public class BaseCoordinator extends Coordinator{

	public BaseCoordinator(Hasher hasher, Users users) {
		super(hasher, users);
	}

	@Override
	public RawScores run() {
		return new RawScores();
	}

}
