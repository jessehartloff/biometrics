package system.coordinator.tests;

import system.allcommonclasses.User;
import system.allcommonclasses.Users;

/**
 * 
 * Tests every possible genuine and impostor pair.
 *
 */
public class GenerateAllTests extends GenerateTests {

	@Override
	public Tests generateTests(Users users) {
		
		Tests tests = new Tests();
		
		for(User user : users.users){
			int m = user.readings.size();
			for(int i=0; i<m; i++){
				for(int j=i+1; j<m; j++){
					Test testToAdd = new Test();
					testToAdd.enroll = user.readings.get(i);
					testToAdd.test = user.readings.get(j);
					testToAdd.genuine = true;
					tests.tests.add(testToAdd);
				}
			}
		}

		int n = users.users.size();
		for(int i=0; i<n; i++){
			for(int k=0; k<users.users.get(i).readings.size(); k++){
				for(int j=i+1; j<n; j++){
					for(int l=0; l<users.users.get(j).readings.size(); l++){
						Test testToAdd = new Test();
						testToAdd.enroll = users.users.get(i).readings.get(k);
						testToAdd.test = users.users.get(j).readings.get(l);
						testToAdd.genuine = false;
						tests.tests.add(testToAdd);
					}
				}
			}
		}
		
		return tests;
	}
}
