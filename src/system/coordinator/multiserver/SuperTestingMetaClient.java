package system.coordinator.multiserver;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import settings.coordinatorsettings.matchingcoordinatorsettings.DefaultTestingPrequantizedSettings;
import settings.coordinatorsettings.multiservercoordinatorsettings.SuperTestingMetaClientSettings;
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
	private HashMap<String, Long> enrollTiming;
	private HashMap<String, Long> testTiming;
	private long numEnrolls;
	private long numTests;
	private long totalEnrollTime;
	private long totalTestTime;

	
	
	public SuperTestingMetaClient(Hasher hasher, Users users, TestGenerator testGenerator) {
		super(hasher, users);
		this.testGenerator = testGenerator;
		this.client = new Client();
		enrollTiming = new HashMap<String, Long>();
		testTiming = new HashMap<String, Long>();
		this.quantizeAllTestTemplates(users.users, true);
		numEnrolls = 0;
		numTests = 0;
		totalEnrollTime = 0;
		totalTestTime = 0;
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
				long start = System.currentTimeMillis();
				this.client.enroll(enrollTemplate, this.computeID(userIndex, readingNumber));
				long stop = System.currentTimeMillis();
				totalEnrollTime += (stop-start);
//				System.out.println(stop-start);
				numEnrolls++;
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
		long start = System.currentTimeMillis();
		Double score = this.client.test(testTemplates, this.computeID(test.enrolledUserID.intValue(), test.enrolledReadingNumber.intValue())); 
		long stop = System.currentTimeMillis();
		totalTestTime += (stop-start);
//		System.out.println(stop-start);
		numTests++;
		return score;
	}



	@Override
	public RawScores run() {
		RawScores scores = this.nextCoordinator.run();
		System.out.println("Generating Tests...");
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
//		System.out.println("Ran tests");
		//ask the client to compile all 3 server times
		enrollTiming = this.client.getAllEnrollTiming();
//		System.out.println("got enroll timing");

		testTiming = this.client.getAllTestTiming();
////		System.out.println("got test timing");
//
//		for (Long val : enrollTiming.values()){
//			val /= numEnrolls;
//			
//		}
//		for (Long val : testTiming.values()){
//			val /= numTests;
//		}
		System.out.println("==== Enroll Timings Averages (out of "+numEnrolls+" ) ====");
		System.out.println("Average total enroll time = "+ totalEnrollTime/numEnrolls);
		for (String key : enrollTiming.keySet()) {
			System.out.println(key+" = "+(enrollTiming.get(key)/numEnrolls));
		}
		System.out.println("==== Test Timings Averages (out of "+testsRan+" ) ====");
		System.out.println("Average total test time = "+ totalTestTime/numTests);
		for (String key : testTiming.keySet()) {
			System.out.println(key+" = "+(testTiming.get(key)/numTests));
		}
	
		return scores;
	}





}
