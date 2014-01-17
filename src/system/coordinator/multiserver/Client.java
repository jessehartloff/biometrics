package system.coordinator.multiserver;

import system.allcommonclasses.commonstructures.RawScores;
import system.allcommonclasses.commonstructures.Users;
import system.hasher.Hasher;

public class Client extends system.coordinator.Coordinator {

	{}// TODO All - multiserver
	{}// TODO Jen-setup network connections. Maybe in a new class to define common functions
	
	Client(Hasher hasher, Users enrollees) {
		super(hasher, enrollees);
	}

	@Override
	public RawScores run() {
		return null;
	}

}
