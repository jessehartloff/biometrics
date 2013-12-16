package system.coordinator.multiserver;

import system.allcommonclasses.RawScores;
import system.allcommonclasses.Users;
import system.hasher.Hasher;

public class Server1 extends system.coordinator.Coordinator {

	{}// TODO +Server1. Preferably with a different name.
	
	Server1(Hasher hasher, Users enrollees) {
		super(hasher, enrollees);
	}

	@Override
	public RawScores run() {
		return null;
	}

}
