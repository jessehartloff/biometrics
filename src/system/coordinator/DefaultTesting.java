package system.coordinator;

import java.util.ArrayList;

import system.allcommonclasses.*;
import system.coordinator.tests.*;
import system.hasher.Hasher;

/**
 * 
 * Does standard testing. Uses a GenerateTest object to determine what pairs of biometrics(readings)
 * to test based on the users, then finds a matching score for each pair of biometrics.
 *
 */
public class DefaultTesting extends Coordinator {

	TestGenerator testGenerator;
	Tests tests;
	
	/**
	 * Constructor 
	 * 
	 * @param hasher
	 * @param users
	 * @param testGenerator determines which pairs of readings should be tested
	 */
	public DefaultTesting(Hasher hasher, Users users, TestGenerator testGenerator){
		super(hasher, users);
		this.testGenerator = testGenerator;
	}
	
	
	private void generateTests(){
		this.tests = testGenerator.generateTests(users);
	}

	
	@Override
	public RawScores run() {
		
		RawScores scores = new RawScores();
		
		super.doTheBinning();
		
		// Generate the tests
		this.generateTests();
		
		
		// Run the tests
		for(Test test : tests.tests){
			Double score = this.runTest(test);
//			System.out.print(score + " ");
			if(test.genuine){
				scores.genuineScores.add(score);
			}
			else{
				scores.imposterScores.add(score);
			}
		}

		
		return scores; 
	}
	
	
	/**
	 * Runs a test. This functions can be called on multiple tests in parallel.
	 * 
	 * @param test
	 * @return matching score
	 */
	private Double runTest(Test test){
		Template enrolledTemplate = hasher.makeEnrollTemplate(test.enroll);
		ArrayList<Template> testTemplates = hasher.makeTestTemplates(test.test);
		
//		System.out.println("template: " + enrolledTemplate.hashes);
		
		Double score1 = hasher.compareTemplateWithBiometric(enrolledTemplate, test.test);
		Double score = hasher.compareTemplates(enrolledTemplate, testTemplates);
		
		assert(Math.abs(score1 - score) < 0.001); {}// TODO test this in unit test
		
		return score; 
	}
	

}
