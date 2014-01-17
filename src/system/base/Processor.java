package system.base;

import java.util.ArrayList;
import java.util.Collections;

import system.Enumerators.*;
import system.allcommonclasses.commonstructures.RawScores;
import system.allcommonclasses.commonstructures.Results;
import system.allcommonclasses.commonstructures.Users;
import system.allcommonclasses.indexingstructure.IndexingStructure;
import system.allcommonclasses.indexingstructure.RAMStructure;
import system.allcommonclasses.modalities.Fingerprint;
import system.allcommonclasses.settings.GlobalSettings;
import system.allcommonclasses.settings.Settings;
import system.allcommonclasses.utilities.UsersIO;
import system.coordinator.*;
import system.coordinator.tests.*;
import system.hasher.*;
import system.makefeaturevector.fingerprintmethods.*;

/**
 * 
 * Takes the parameters, sets up the entire system, and processes the raw results. This includes 
 * creating the objects, reading the biometrics from files/databases, and starting the testing.
 * Also determines plots and meaningful results from the raw scores returned by the system.
 *
 */
public class Processor {
	
	/**
	 * Actually runs the "real part" of the program.
	 * Processes the choices of the user as set in "Main.java" using enumerators, and runs the appropriate tests.
	 * @param parameters
	 * @return
	 */
	public Results go(Settings settings){
		settings.loadToSettingsClasses();
		GlobalSettings globalSettings = GlobalSettings.getInstance();
		
		Results results = new Results();
		
		FingerprintMethodFactory.makeFingerprintMethod();
		Hasher hasher = HasherFactory.makeHasher();
		TestGenerator testMaker = TestGeneratorFactory.makeTestGenerator();
		Users users = UsersIO.getUsers(globalSettings.getDataset()); 

		// this line, "users.computeBins()" has to happen after the methods are set for binning to work
		users.computeBins();
		
		Coordinator coordinator = setCoordinator(globalSettings.getCoordinator(), hasher, users, testMaker);
		
		ArrayList<Fingerprint> fingerprints = new ArrayList<Fingerprint>();
				
		IndexingStructure indexingStructure = new RAMStructure(); // TODO Jim - IndexingStructureFactory in allcommonclasses/indexingstructure
		
		RawScores scores = coordinator.run();
		
		results = EvaluatePerformance.computeEER(scores);

		Collections.sort(scores.genuineScores);
		Collections.sort(scores.imposterScores);
		printResults(scores, results);
		return results;
	}
	
	private void printResults(RawScores scores, Results results){
		System.out.println("\nGenuines:\n" + scores.genuineScores + "\n");
		System.out.println("Imposters:\n" + scores.imposterScores + "\n");
		System.out.println("EER:\n" + results.getEer());
		System.out.println("rates:\n" + results.getRates());
		System.out.println("indexing:\n" + scores.indexRankings);
	}

	private Coordinator setCoordinator(String coordinatorString, Hasher hasher, Users users, TestGenerator testMaker){
		CoordinatorEnumerator ce = CoordinatorEnumerator.valueOf(coordinatorString);
		Coordinator coordinator;
		switch(ce){
			case DEFAULTTESTING:
				coordinator = new DefaultTesting(hasher,users,testMaker);
				break;
			case DEFAULTTESTINGPREQUANTIZED:
				coordinator = new DefaultTestingPrequantized(hasher, users, testMaker);
				break;
			default:
				System.out.println("You did not provide an appropriate coordinator");
				coordinator = new DefaultTesting(hasher,users,testMaker);
				break;
		}
		return coordinator;
	}	
}
