package system.coordinator;

import java.util.ArrayList;

import settings.coordinatorsettings.matchingcoordinatorsettings.MultipleEnrollmentSettings;
import system.allcommonclasses.commonstructures.RawScores;
import system.allcommonclasses.commonstructures.Template;
import system.allcommonclasses.commonstructures.User;
import system.allcommonclasses.commonstructures.Users;
import system.coordinator.testgenerators.Test;
import system.hasher.Hasher;

public class MultipleEnrollment extends Coordinator{

	//LATER Jesse - multiple enrollment all possible genuine tests
	public MultipleEnrollment(Hasher hasher, Users users) {
		super(hasher, users);
		this.prequantize();
	}
	
	
	private void prequantize(){

		int readingsToEnroll = MultipleEnrollmentSettings.getInstance().readingsForEnrollment().getValue().intValue();
		
		Long total = new Long(users.users.size());
		Long completed = 0L;
		Double progress;
		
		for(User user : users.users){
			int numberOfReadings = user.readings.size();
			if(numberOfReadings < readingsToEnroll){
				System.out.println("Not enough readings for user " + user.id);
				continue;
			}
			
			//make enrolling multi-reading template
			Template enrolled = new Template();
			for(int q=0; q<readingsToEnroll; q++){
				enrolled.getHashes().addAll(user.readings.get(q).quantizeOne().getHashes());
			}
			user.prequantizedEnrolledTemplates.add(hasher.hashEnrollTemplate(enrolled));
			
			//make testing templates
			for(int i=readingsToEnroll; i<numberOfReadings; i++){
				user.prequantizedTestTemplates.add(hasher.makeTestTemplates(user.readings.get(i)));
			}
			
			completed++;
			progress = (completed.doubleValue()/total.doubleValue())*100.0;
			System.out.format("prequantizing: %5.2f%%%n", progress);
		}
	}
	
// 0.0268560606
	
	@Override
	public RawScores run() {
		
		RawScores scores = this.nextCoordinator.run();		
		Integer numberOfUsers = this.users.users.size();
		
		// Run the tests
		for(int i=0; i<numberOfUsers; i++){
			
			User currentUser = this.users.users.get(i);
			if(currentUser.prequantizedEnrolledTemplates.size() != 1){
				System.out.println("No enrolled template for user " + i);
				System.out.println(currentUser.prequantizedEnrolledTemplates.size() + " templates");
				continue;
			}
			
			Template enrolledTemplate = currentUser.prequantizedEnrolledTemplates.get(0);
			
			//run tests
			for(int j=0; j<numberOfUsers; j++){
				for(ArrayList<Template> testTemplates : this.users.users.get(j).prequantizedTestTemplates){
					if(i==j){ //genuine test
						scores.genuineScores.add(hasher.compareTemplates(enrolledTemplate, testTemplates));
					}
					else{ // impostor test
						scores.imposterScores.add(hasher.compareTemplates(enrolledTemplate, testTemplates));
					}
				}
			}//end tests for current user
			
			Double progress = (((i+1)*1.0)/numberOfUsers.doubleValue())*100.0;
			System.out.format("progress: %5.2f%%", progress );
			System.out.println("");
			
		}// end all tests
		
			
		return scores; 
	}

}
