package system.coordinator;

import java.util.ArrayList;

import system.allcommonclasses.Template;
import system.allcommonclasses.User;
import system.allcommonclasses.Users;
import system.coordinator.tests.Test;
import system.coordinator.tests.TestGenerator;
import system.hasher.Hasher;

public class DefaultTestingPrequantized extends DefaultTesting{

	public DefaultTestingPrequantized(Hasher hasher, Users users,
			TestGenerator testGenerator) {
		super(hasher, users, testGenerator);
		prequantize();
	}
	
	private void prequantize(){
		for(User user : users.users){
			int numberOfReadings = user.readings.size();
			for(int i=0; i<numberOfReadings; i++){
				user.prequantizedEnrolledTemplates.add(hasher.makeEnrollTemplate(user.readings.get(i)));
				user.prequantizedTestTemplates.add(hasher.makeTestTemplates(user.readings.get(i)));
			}
		}
	}

	@Override
	protected Double runTest(Test test){
		Template enrolledTemplate = users.users.get(test.enrolledUserID).prequantizedEnrolledTemplates.get(test.enrolledReadingNumber);
		ArrayList<Template> testTemplates = users.users.get(test.testUserID).prequantizedTestTemplates.get(test.testReadingNumber);
		
		Double score = hasher.compareTemplates(enrolledTemplate, testTemplates);
		
		return score; 
	}
	
}
