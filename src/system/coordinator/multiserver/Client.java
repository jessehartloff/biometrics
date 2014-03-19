package system.coordinator.multiserver;

import system.allcommonclasses.commonstructures.RawScores;
import system.allcommonclasses.commonstructures.Users;
import system.hasher.Hasher;

public class Client extends system.coordinator.Coordinator {

	
	Client(Hasher hasher, Users enrollees) {
		super(hasher, enrollees);
	}

	@Override
	public RawScores run() {
		return null;
	}

}
