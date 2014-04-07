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
	// THIS IS RIDICULOUSLY NOT CORRECT
	
	public SuperTestingMetaClient(Hasher hasher, Users users) {
		super(hasher, users);
	}
	@Override
	public RawScores run(){
		Client client = new Client(hasher, users);
		client.enroll(hasher.makeEnrollTemplate(users.users.get(0).readings.get(0)), users.users.get(0).id);
		client.test(hasher.makeTestTemplates(users.users.get(0).readings.get(0)), users.users.get(0).id);

		System.exit(0);
		return null;
		
	}
//	@Override
//	public RawScores run() {
//		RawScores scores = new RawScores();
//		Client client = new Client(hasher, users);
//		for(User user: this.users.users){
//			for(int i = 0; i < user.readings.size(); i++){
//				client.enroll(hasher.makeEnrollTemplate(user.readings.get(i)),user.id);
//			}
//		}
//		for(User user: this.users.users){
//			for(int i = 0; i < user.readings.size(); i++){
//				ArrayList<Template> testTemplates = hasher.makeTestTemplates(user.readings.get(i));
//				Double testResult = client.test(testTemplates,user.id);
//			}
//		}
//		return scores;
//	}

}
