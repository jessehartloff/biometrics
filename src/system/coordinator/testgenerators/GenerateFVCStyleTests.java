package system.coordinator.testgenerators;

import system.allcommonclasses.*;
import system.allcommonclasses.commonstructures.User;
import system.allcommonclasses.commonstructures.Users;

/**
 * 
 * Tests the pairs of readings according to the FVC competition. This tests every possible 
 * genuine pair and the impostors from using the first reading of every user.
 *
 */
public class GenerateFVCStyleTests extends TestGenerator{

	@Override
	public Tests generateTests(Users users) {
		
		Tests tests = new Tests();
		
		for(User user : users.users){
			int m = user.readings.size();
			for(Integer i=0; i<m; i++){
				for(Integer j=i+1; j<m; j++){
					Test testToAdd = new Test();
					testToAdd.enroll = user.readings.get(i);
					testToAdd.test = user.readings.get(j);
					testToAdd.genuine = true;
					
					testToAdd.enrolledUserID = new Long(user.id);
					testToAdd.enrolledReadingNumber = i.longValue();
					testToAdd.testUserID = new Long(user.id);
					testToAdd.testReadingNumber = j.longValue();
					
					tests.tests.add(testToAdd);
				}
			}
		}
		

		int n = users.users.size();
		for(int i=0; i<n; i++){
			for(int j=i+1; j<n; j++){
				Test testToAdd = new Test();
				testToAdd.enroll = users.users.get(i).readings.get(0);
				testToAdd.test = users.users.get(j).readings.get(0);
				testToAdd.genuine = false;
				
				testToAdd.enrolledUserID = new Long(users.users.get(i).id);
				testToAdd.enrolledReadingNumber = 0L;
				testToAdd.testUserID = new Long(users.users.get(j).id);
				testToAdd.testReadingNumber = 0L;
				
				tests.tests.add(testToAdd);
			}
		}
		
		
		return tests;
	}

}
