package system.base;

import system.allcommonclasses.*;
import system.allcommonclasses.parameters.GeneralParameters;
import system.allcommonclasses.settings.Settings;
import system.coordinator.*;
import system.coordinator.tests.*;
import system.hasher.*;

/**
 * 
 * Takes the parameters, sets up the entire system, and processes the raw results. This includes 
 * creating the objects, reading the biometrics from files/databases, and starting the testing.
 * Also determines plots and meaningful results from the raw scores returned by the system.
 *
 */
public class Processor {

	{}// TODO Processor. This one will be a bit of a mess. Could benefit from helper classes to divide an conquer.
	
	/**
	 * Does things
	 * 
	 * @param parameters
	 * @return
	 */
	public Results go(Settings settings){
		Results results = new Results();
		
		settings.loadSettings();

		{}// TODO read biometrics from files
		Users users = new Users();
		
		{}// TODO make all the users
		Hasher hasher = new StraightHasher();
		TestGenerator testMaker = new GenerateFVCStyleTests();
		{}// TODO make hasher
		{}// TODO make method
		{}// TODO make coordinator(hasher, users)
		RawScores scores = new RawScores();
		Coordinator coordinator = new DefaultTesting(hasher, users, testMaker);
		scores = coordinator.run();
		
		results = EvaluatePerformance.computeEER(scores);

		System.out.println("\nGenuines:\n" + scores.genuineScores + "\n\n\n");
		System.out.println("Imposters:\n" + scores.imposterScores + "\n\n\n");
		
		return results;
	}

	
	


	
}
