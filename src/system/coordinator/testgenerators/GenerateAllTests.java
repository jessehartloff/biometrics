package system.coordinator.testgenerators;

import system.allcommonclasses.commonstructures.User;
import system.allcommonclasses.commonstructures.Users;

/**
 * 
 * Tests every possible genuine and impostor pair.
 *
 */
public class GenerateAllTests extends TestGenerator {

	@Override
	public Tests generateTests(Users users) {
		// FIXME indecies are bad. See GenerateFVCStyleTests
		Tests tests = new Tests();
		

		int n = users.users.size();
		
		//add genuines
		for(Integer q=0; q<n; q++){
			User user = users.users.get(q);
			int m = user.readings.size();
			for(Integer i=0; i<m; i++){
				for(Integer j=i+1; j<m; j++){
					Test testToAdd = new Test();
					testToAdd.enroll = user.readings.get(i);
					testToAdd.test = user.readings.get(j);
					testToAdd.genuine = true;
					
					testToAdd.enrolledUserID = q.longValue(); // the users ID is its index in the array
					testToAdd.enrolledReadingNumber = i.longValue();
					testToAdd.testUserID = q.longValue();
					testToAdd.testReadingNumber = j.longValue();
					
					tests.tests.add(testToAdd);
				}
			}
		}


		//add imposters
		for(Integer i=0; i<n; i++){
			for(Integer k=0; k<users.users.get(i).readings.size(); k++){
				for(Integer j=i+1; j<n; j++){
					for(Integer l=0; l<users.users.get(j).readings.size(); l++){
						Test testToAdd = new Test();
						testToAdd.enroll = users.users.get(i).readings.get(k);
						testToAdd.test = users.users.get(j).readings.get(l);
						testToAdd.genuine = false;

						testToAdd.enrolledUserID = i.longValue(); // the users ID is its index in the array
						testToAdd.enrolledReadingNumber = k.longValue();
						testToAdd.testUserID = j.longValue();
						testToAdd.testReadingNumber = l.longValue();
						
						tests.tests.add(testToAdd);
					}
				}
			}
		}
		
		return tests;
	}
}
