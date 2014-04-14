package system.coordinator.multiserver;

import java.util.ArrayList;

import system.allcommonclasses.commonstructures.RawScores;
import system.allcommonclasses.commonstructures.Template;
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
		Template enrollT = hasher.makeEnrollTemplate(users.users.get(0).readings.get(0));
		ArrayList<Template> testT = hasher.makeTestTemplates(users.users.get(0).readings.get(1));
		client.enroll(enrollT, users.users.get(0).id);
		System.out.println("enrolled!!");
		Double score = client.test(testT, users.users.get(0).id);
		System.out.println("tested!!");
		System.out.println("Sizes:"+ enrollT.getHashes().size()+", "+testT.get(0).getHashes().size());
		System.out.println(score + ", "+hasher.compareTemplates(enrollT, testT));
		
		System.exit(0);
		return null;
	}
	
}
