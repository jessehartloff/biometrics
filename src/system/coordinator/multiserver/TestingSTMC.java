package system.coordinator.multiserver;

import system.allcommonclasses.commonstructures.RawScores;
import system.allcommonclasses.commonstructures.Users;
import system.coordinator.Coordinator;
import system.hasher.Hasher;

public class TestingSTMC extends Coordinator {
	
	public TestingSTMC(Hasher hasher, Users users) {
		super(hasher, users);
	}
	
	@Override
	public RawScores run(){
		Client client = new Client();
		System.out.println("connected!!");
		client.enroll(hasher.makeEnrollTemplate(users.users.get(0).readings.get(0)), users.users.get(0).id);
		System.out.println("enrolled!!");
		client.test(hasher.makeTestTemplates(users.users.get(0).readings.get(0)), users.users.get(0).id);
		System.out.println("tested!!");

		System.exit(0);
		return null;
	}
	
}
