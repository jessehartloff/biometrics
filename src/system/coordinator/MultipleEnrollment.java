package system.coordinator;

import java.util.ArrayList;

import system.allcommonclasses.commonstructures.RawScores;
import system.allcommonclasses.commonstructures.Template;
import system.allcommonclasses.commonstructures.Users;
import system.coordinator.testgenerators.Test;
import system.hasher.Hasher;

public class MultipleEnrollment extends Coordinator{

	//TODO Jesse - make this faster
	public MultipleEnrollment(Hasher hasher, Users users) {
		super(hasher, users);
	}

	@Override
	public RawScores run() {

		int numberToEnroll = 4; // settings this
		
		RawScores scores = this.nextCoordinator.run();
		
		Integer numberOfUsers = this.users.users.size();
		
		// Run the tests
		for(int i=0; i<numberOfUsers; i++){
			//genuines
			Template enrolled = new Template();
			for(int q=0; q<numberToEnroll; q++){
				enrolled.hashes.addAll(this.users.users.get(i).readings.get(q).quantizeOne().hashes);
			}
			for(int p=numberToEnroll; p<this.users.users.get(i).readings.size(); p++){
				ArrayList<Template> testTemplates = hasher.makeTestTemplates(
						this.users.users.get(i).readings.get(p));
				scores.genuineScores.add(hasher.compareTemplates(enrolled, testTemplates));
//				System.out.println("gen");
			}
			
			for(int j=i+1; j<numberOfUsers; j++){
				for(int w=numberToEnroll; w<this.users.users.get(i).readings.size(); w++){
					ArrayList<Template> testTemplates = hasher.makeTestTemplates(
							this.users.users.get(j).readings.get(w));
					scores.imposterScores.add(hasher.compareTemplates(enrolled, testTemplates));
//					System.out.println("imp");
				}
			}
			
			Double progress = (((i+1)*1.0)/numberOfUsers.doubleValue())*100.0;
			System.out.format("progress: %5.2f%%", progress );
			System.out.println("");
		}
		
			
		
		return scores; 
	}

}
