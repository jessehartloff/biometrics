package system.coordinator.multiserver;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

import settings.coordinatorsettings.matchingcoordinatorsettings.DefaultTestingPrequantizedSettings;
import system.allcommonclasses.commonstructures.RawScores;
import system.allcommonclasses.commonstructures.Template;
import system.allcommonclasses.commonstructures.User;
import system.allcommonclasses.commonstructures.Users;
import system.allcommonclasses.transformations.SHA2;
import system.coordinator.Coordinator;
import system.coordinator.DefaultTesting;
import system.coordinator.testgenerators.Test;
import system.coordinator.testgenerators.TestGenerator;
import system.coordinator.testgenerators.Tests;
import system.hasher.Hasher;

public class SuperTestingMetaClient extends Coordinator {

	private TestGenerator testGenerator;
	private Tests tests;
	private Client client;


	public SuperTestingMetaClient(Hasher hasher, Users users, TestGenerator testGenerator) {
		super(hasher, users);
		this.client = new Client();
		this.quantizeAllTestTemplates(users.users, true);
		this.enrollAll();
	}

	protected void generateTests() {
		this.tests = testGenerator.generateTests(users);
	}

	private void enrollAll() {
		int numberOfUsers = this.users.users.size();
		
		for(int userIndex = 0; userIndex<numberOfUsers; userIndex++){
			User currentUser = this.users.users.get(userIndex);
			int numberOfReadings = currentUser.readings.size();
			for(int readingNumber=0; readingNumber<numberOfReadings; readingNumber++){
				Template enrollTemplate = currentUser.readings.get(readingNumber).quantizeOne();
				this.client.enroll(enrollTemplate, this.computeID(userIndex, readingNumber));
//				completed++;
//				progress = (completed.doubleValue()/total.doubleValue())*100.0;

			}
//			progress = (completed.doubleValue()/total.doubleValue())*100.0;
//			System.out.format("prequantizing: %5.2f%%%n", progress);
		}
	}

	private Long computeID(int userID, int readingNumber){
		return new Long( (userID << 5) + readingNumber );
	}

	public void quantizeAllTestTemplates(ArrayList<User> users, boolean print){	
		Long total = 0L;
		Long completed = 0L;
		Double progress;
		System.out.println("Prequantizing test templates...");
		
		for(User user : users){
			total += user.readings.size();
		}

		for(User user : users){
			int numberOfReadings = user.readings.size();
			for(int i=0; i<numberOfReadings; i++){
				ArrayList<Template> test = user.readings.get(i).quantizeAll();
				user.prequantizedTestTemplates.add(test);
				completed++;
				progress = (completed.doubleValue()/total.doubleValue())*100.0;

			}
			progress = (completed.doubleValue()/total.doubleValue())*100.0;
			System.out.format("prequantizing: %5.2f%%%n", progress);
		}
	}


	protected Double runTest(Test test){
		ArrayList<Template> testTemplates = users.users.get(test.testUserID.intValue()).prequantizedTestTemplates.get(test.testReadingNumber.intValue());
		return this.client.test(testTemplates, this.computeID(test.enrolledUserID.intValue(), test.enrolledReadingNumber.intValue())); 
	}



	@Override
	public RawScores run() {

		RawScores scores = this.nextCoordinator.run();

		// Generate the tests
		this.generateTests();

		Integer numberOfTests = tests.tests.size();
		Integer testsRan = 0;
		// Run the tests
		for (Test test : tests.tests) {
			Double score = this.runTest(test);

			testsRan++;
			Double progress = (testsRan.doubleValue() / numberOfTests
					.doubleValue()) * 100.0;
			System.out.format("score:%3.0f   progress: %5.2f%%", score,
					progress);
			if (test.genuine) {
				System.out.println("   genuine");
				scores.genuineScores.add(score);
			} else {
				System.out.println("   imposter");
				scores.imposterScores.add(score);
			}
		}

		return scores;
	}



}
