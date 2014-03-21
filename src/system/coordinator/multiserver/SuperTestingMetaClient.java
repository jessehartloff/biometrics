package system.coordinator.multiserver;

import system.allcommonclasses.commonstructures.RawScores;
import system.allcommonclasses.commonstructures.Users;
import system.coordinator.Coordinator;
import system.hasher.Hasher;

public class SuperTestingMetaClient extends Coordinator {
	// TODO Matt write SuperTestingMetaClient constructor and run()

	public SuperTestingMetaClient(Hasher hasher, Users users) {
		super(hasher, users);

	}

	@Override
	public RawScores run() {
		return null;
	}

}
