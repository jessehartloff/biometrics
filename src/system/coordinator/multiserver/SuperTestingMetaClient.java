package system.coordinator.multiserver;

import system.allcommonclasses.commonstructures.RawScores;
import system.allcommonclasses.commonstructures.Users;
import system.coordinator.Coordinator;
import system.hasher.Hasher;

public class SuperTestingMetaClient extends Coordinator {
	// TODO Jim write SuperTestingMetaClient constructor and run()

	public SuperTestingMetaClient(Hasher hasher, Users users) {
		super(hasher, users);
	}

	
	
	
	
	@Override
	public RawScores run() {
		//instantiate a client Client client = new Client()
		//for each user in users
			//enroll the fingerprint //client.enroll(...)
		//for each user in users
			//test the fingerprint  // client.test(...)
		return null;
	}

}
