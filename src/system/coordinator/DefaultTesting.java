package system.coordinator;

import java.util.ArrayList;

import system.allcommonclasses.*;
import system.allcommonclasses.commonstructures.RawScores;
import system.allcommonclasses.commonstructures.Template;
import system.allcommonclasses.commonstructures.Users;
import system.coordinator.testgenerators.*;
import system.hasher.Hasher;

/**
 * 
 * Does standard testing. Uses a GenerateTest object to determine what pairs of
 * biometrics(readings) to test based on the users, then finds a matching score
 * for each pair of biometrics.
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
	 * @param testGenerator
	 *            determines which pairs of readings should be tested
	 */
	public DefaultTesting(Hasher hasher, Users users,
			TestGenerator testGenerator) {
		super(hasher, users);
		this.testGenerator = testGenerator;
	}

	protected void generateTests() {
		this.tests = testGenerator.generateTests(users);
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

	/**
	 * Runs a test. This functions can be called on multiple tests in parallel.
	 * 
	 * @param test
	 * @return matching score
	 */
	protected Double runTest(Test test) {
		Template enrolledTemplate = hasher.makeEnrollTemplate(test.enroll);
		ArrayList<Template> testTemplates = hasher.makeTestTemplates(test.test);

		Double score = hasher.compareTemplates(enrolledTemplate, testTemplates);
		return score;
	}

}
