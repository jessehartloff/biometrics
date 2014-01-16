package system.base;

import java.util.ArrayList;
import java.util.Collections;

import system.Enumerators.*;
import system.allcommonclasses.*;
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
	 * Does things
	 * 
	 * @param parameters
	 * @return
	 */

	public Results go(Settings settings){
		
		settings.loadToSettingsClasses();
		GlobalSettings globalSettings = GlobalSettings.getInstance();
		
		Results results = new Results();

		FingerPrintEnumerator fpe = FingerPrintEnumerator.valueOf(globalSettings.getFingerprintMethodString());
		switch(fpe){
			case MINUTIAEMETHOD:
				Fingerprint.setFingerprintMethod(new MinutiaeMethod());
				break;
			case PATHSMETHOD:
				Fingerprint.setFingerprintMethod(new PathsMethod());
				break;
			case TRIANGLES:
				Fingerprint.setFingerprintMethod(new Triangles());
				break;
			case TRIPLESOFTRIANGLES:
				Fingerprint.setFingerprintMethod(new TriplesOfTriangles());
				break;
			case TRIPLESOFTRIANGLESALLROTATIONS:
				Fingerprint.setFingerprintMethod(new TriplesOfTrianglesAllRotations());
				break;
			case NGON:
				Fingerprint.setFingerprintMethod(new Ngon());//need to add numerical argument here...Matt M
				break;
			default:
				System.out.println("Hey, you didn't choose a fingerprint method");
				break;
		}
		
		Hasher hasher;
		HasherEnumerator he = HasherEnumerator.valueOf(globalSettings.getHasher());
		switch(he){
			case STRAIGHTHASHER:
				hasher = new StraightHasher();
				break;
			case SHORTCUTFUZZYVAULT:
				hasher = new ShortcutFuzzyVault();// FV and SH don't get exactly the same EER with no chaff points
				break;
			default:
				System.out.println("You didn't provide an appropriate hasher");
				hasher = new StraightHasher();
				break;
		}
		
		TestGenerator testMaker;
		TestGeneratorEnumerator tge = TestGeneratorEnumerator.valueOf(globalSettings.getTestGenerator());
		switch(tge){
			case GENERATEFVCSTYLETESTS:
				testMaker = new GenerateFVCStyleTests();
				break;
			default:
				System.out.println("You did not provide an appropriate test generator");
				testMaker = new GenerateFVCStyleTests();
				break;
		}
		
		Users users = UsersIO.getUsers(globalSettings.getDataset()); 
		
		// this line, "users.computeBins()" has to happen after the methods are set for binning to work
		users.computeBins();
		
		Coordinator coordinator;
		CoordinatorEnumerator ce = CoordinatorEnumerator.valueOf(globalSettings.getCoordinator());
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
		
		Indexable hasherAgain = new ShortcutFuzzyVault();
				
		ArrayList<Fingerprint> fingerprints = new ArrayList<Fingerprint>();
		
		RawScores scores = new RawScores();
				
		
				
		IndexingStructure indexingStructure = new RAMStructure();
		
		Coordinator indexingCoordinator = new IndexTesting(hasher, users, hasherAgain, indexingStructure);
		
		scores = coordinator.run();
//		scores = indexingCoordinator.run();
		
		results = EvaluatePerformance.computeEER(scores);

		Collections.sort(scores.genuineScores);
		Collections.sort(scores.imposterScores);
		
		System.out.println("\nGenuines:\n" + scores.genuineScores + "\n");
		System.out.println("Imposters:\n" + scores.imposterScores + "\n");
		System.out.println("EER:\n" + results.getEer());
		System.out.println("rates:\n" + results.getRates());
		System.out.println("indexing:\n" + scores.indexRankings);

		
		return results;
	}
	
	
}
