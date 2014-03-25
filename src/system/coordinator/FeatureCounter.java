package system.coordinator;

import java.util.ArrayList;

import system.allcommonclasses.commonstructures.RawScores;
import system.allcommonclasses.commonstructures.Template;
import system.allcommonclasses.commonstructures.Users;
import system.coordinator.testgenerators.Test;
import system.coordinator.testgenerators.TestGenerator;
import system.hasher.Hasher;

/** This is DefaultTestingPrequantized, with a special stuff for the Average hashes pere template
 * This class extends coordinator so it can run tests like a Coordinator, 
 * but will only calculate the average number of hashes per template for the given biometric matching method.
 * Once calculated, it will print out the average per Enroll Template and the average per Test Template.
 * 
 * @author thomaseffland
 *
 */

public class FeatureCounter extends DefaultTestingPrequantized{
	
	private Long totalEnrollHashCount;
	private Long numEnrollTemplates;
	private Long totalTestHashCount;
	private Long numTestTemplates;

	public FeatureCounter(Hasher hasher, Users users, TestGenerator testGenerator) {
		super(hasher, users, testGenerator);
		totalEnrollHashCount = 0L;
		numEnrollTemplates = 0L;
		totalTestHashCount = 0L;
		numTestTemplates = 0L;
		// TODO Auto-generated constructor stub
	}

	@Override
	public RawScores run() {

		RawScores scores = this.nextCoordinator.run();

		// Generate the tests
		super.generateTests();

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
		System.out.println("Total Enroll Samples: "+ numEnrollTemplates);
		System.out.println("Average Number of Hashes per Enroll Sample: "+ totalEnrollHashCount.doubleValue()/numEnrollTemplates.doubleValue());
		System.out.println("Total Test Samples: "+ numTestTemplates);
		System.out.println("Average Number of Hashes per Test Sample: "+ totalTestHashCount.doubleValue()/numTestTemplates.doubleValue());
//		Total Enroll Samples: 7743
//		Average Number of Hashes per Enroll Sample: 192
//		Total Test Samples: 154860
//		Average Number of Hashes per Test Sample: 9
		return scores;
	}

	/**
	 * Runs a test. This functions can be called on multiple tests in parallel.
	 * 
	 * @param test
	 * @return matching score
	 */
	@Override
	protected Double runTest(Test test) {
		Template enrolledTemplate = users.users.get(test.enrolledUserID.intValue()).prequantizedEnrolledTemplates.get(test.enrolledReadingNumber.intValue());
		ArrayList<Template> testTemplates = users.users.get(test.testUserID.intValue()).prequantizedTestTemplates.get(test.testReadingNumber.intValue());
		
		Double score = hasher.compareTemplates(enrolledTemplate, testTemplates);
		//edit the global vars we actually care about
		numEnrollTemplates++;
		totalEnrollHashCount += enrolledTemplate.getHashes().size();
		for( Template t : testTemplates) {
			numTestTemplates++;
			totalTestHashCount += t.getHashes().size();
		}

		return score; 
	}

	
	/**
	 * Getters and setters
	 * @return
	 */
	public Long getTotalEnrollHashCount() {
		return totalEnrollHashCount;
	}

	public void setTotalEnrollHashCount(Long totalEnrollHashCount) {
		this.totalEnrollHashCount = totalEnrollHashCount;
	}

	public Long getNumEnrollTemplates() {
		return numEnrollTemplates;
	}

	public void setNumEnrollTemplates(Long numEnrollTemplates) {
		this.numEnrollTemplates = numEnrollTemplates;
	}

	public Long getTotalTestHashCount() {
		return totalTestHashCount;
	}

	public void setTotalTestHashCount(Long totalTestHashCount) {
		this.totalTestHashCount = totalTestHashCount;
	}

	public Long getNumTestTemplates() {
		return numTestTemplates;
	}

	public void setNumTestTemplates(Long numTestTemplates) {
		this.numTestTemplates = numTestTemplates;
	}

}
