package system.base;

import java.util.ArrayList;
import java.util.Collections;

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

		{}// TODO -Processor. Needs to create all this using the settings
		  //      not commenting/uncommenting code
		
		String fingerMethodString = globalSettings.getFingerprintMethodString();
		globalSettings.getCoordinator();
		globalSettings.getHasher();
		
		
//		GlobalSettings.fingerprintMethod = new MinutiaeMethod();
//		GlobalSettings.fingerprintMethod = new PathsMethod();
//		GlobalSettings.fingerprintMethod = new Triangles();
//		GlobalSettings.fingerprintMethod = new TriplesOfTriangles();
//		GlobalSettings.fingerprintMethod = new TriplesOfTrianglesAllRotations();
//		GlobalSettings.fingerprintMethod = new Ngon(n);
		
		Fingerprint.setFingerprintMethod(new Triangles());

		
		Hasher hasher = new StraightHasher();
//		Hasher hasher = new ShortcutFuzzyVault(); {}// FV and SH don't get exactly the same EER with no chaff points
		
		Indexable hasherAgain = new ShortcutFuzzyVault();
		
		TestGenerator testMaker = new GenerateFVCStyleTests();
		
		ArrayList<Fingerprint> fingerprints = new ArrayList<Fingerprint>();
		
		RawScores scores = new RawScores();
		
		
		// this has to happen after the methods are set for binning to work
		Users users = UsersIO.getUsers(globalSettings.getDataset()); 
		
//		Coordinator coordinator = new DefaultTesting(hasher, users, testMaker);
		Coordinator coordinator = new DefaultTestingPrequantized(hasher, users, testMaker);
		
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
