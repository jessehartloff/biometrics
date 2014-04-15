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
//		Template enrollT = hasher.makeEnrollTemplate(users.users.get(0).readings.get(0));
		Template enrollT = users.users.get(0).readings.get(0).quantizeOne();

//		ArrayList<Template> testT = users.users.get(0).readings.get(1).quantizeAll();
		ArrayList<Template> testT = hasher.makeTestTemplates(users.users.get(0).readings.get(1));

		System.out.println(enrollT.getHashes());
		System.out.println(testT.size()+" .. "+testT.get(0).getHashes());
		Template FV = hasher.hashEnrollTemplate(enrollT);
		System.out.println(hasher.compareTemplates(FV, testT));
		client.enroll(enrollT, users.users.get(0).id);
		System.out.println("enrolled!!");
		Double score = client.test(testT, users.users.get(0).id);
		System.out.println("tested!!");
		System.out.println("Sizes:"+ enrollT.getHashes().size()+", "+testT.get(0).getHashes().size());
//		hasher.hashEnrollTemplate(enrollT);
		System.out.println(score );
		
		System.exit(0);
		return null;
	}
	
}
