package system.coordinator;

import java.util.ArrayList;

import system.allcommonclasses.Template;
import system.allcommonclasses.User;
import system.allcommonclasses.Users;
import system.coordinator.tests.Test;
import system.coordinator.tests.TestGenerator;
import system.hasher.Hasher;

public class DefaultTestingPrequantized extends DefaultTesting{

	{}// TODO -database
	// database stuff might not belong here, but I had to put the todo somewhere.
	
	public DefaultTestingPrequantized(Hasher hasher, Users users,
			TestGenerator testGenerator) {
		super(hasher, users, testGenerator);
		prequantize();
	}
	
	private void prequantize(){
		Long total = 0L;
		Long completed = 0L;
		Double progress;
		for(User user : users.users){
			total += user.readings.size();
		}
		
		for(User user : users.users){
			int numberOfReadings = user.readings.size();
			for(int i=0; i<numberOfReadings; i++){
				user.prequantizedEnrolledTemplates.add(hasher.makeEnrollTemplate(user.readings.get(i)));
				user.prequantizedTestTemplates.add(hasher.makeTestTemplates(user.readings.get(i)));
				completed++;
				progress = (completed.doubleValue()/total.doubleValue())*100.0;
				System.out.format("prequantizing: %5.2f%%%n", progress);
			}
			progress = (completed.doubleValue()/total.doubleValue())*100.0;
			System.out.format("prequantizing: %5.2f%%%n", progress);
			
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
