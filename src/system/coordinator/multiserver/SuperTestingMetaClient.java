package system.coordinator.multiserver;

import java.util.ArrayList;

import system.allcommonclasses.commonstructures.RawScores;
import system.allcommonclasses.commonstructures.Template;
import system.allcommonclasses.commonstructures.User;
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
		RawScores scores = new RawScores();
		Client client = new Client(hasher, users);
		for(User user: this.users.users){
			for(int i = 0; i < user.readings.size(); i++){
				client.enroll(hasher.makeEnrollTemplate(user.readings.get(i)),user.id);
			}
		}
		for(User user: this.users.users){
			for(int i = 0; i < user.readings.size(); i++){
				ArrayList<Template> testTemplates = hasher.makeTestTemplates(user.readings.get(i));
				Double testResult = client.test(testTemplates,user.id);
			}
		}
		return scores;
	}

}
