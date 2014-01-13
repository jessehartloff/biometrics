package system.coordinator.multiserver;

import system.allcommonclasses.RawScores;
import system.allcommonclasses.Users;
import system.hasher.Hasher;

public class Client extends system.coordinator.Coordinator {

	{}// TODO -++Client
	{}// TODO Jen-setup network connections. Maybe in new class to define common functions
	
	Client(Hasher hasher, Users enrollees) {
		super(hasher, enrollees);
	}

	@Override
	public RawScores run() {
		return null;
	}

}
