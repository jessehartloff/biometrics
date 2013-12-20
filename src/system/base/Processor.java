package system.base;

import java.util.ArrayList;
import java.util.Collections;

import system.allcommonclasses.*;
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

	{}// TODO -Processor. This one will be a bit of a mess. Could benefit from helper classes to divide an conquer.
	
	/**
	 * Does things
	 * 
	 * @param parameters
	 * @return
	 */
	public Results go(Settings settings){
		settings.loadSettings();
		GlobalSettings globalSettings = GlobalSettings.getInstance();
		
		Results results = new Results();
//		GlobalSettings.fingerprintMethod = new MinutiaeMethod();
//		GlobalSettings.fingerprintMethod = new PathsMethod();
		GlobalSettings.fingerprintMethod = new Triangles();
//		GlobalSettings.fingerprintMethod = new TriplesOfTriangles();
//		GlobalSettings.fingerprintMethod = new TriplesOfTrianglesAllRotations();
//		GobalSettings.fingerprintMethod = new Ngon(n);

		Users users = UsersIO.getUsers(globalSettings.getDataset());
		users.computeBins();
		
		Hasher hasher = new StraightHasher();
//		Hasher hasher = new ShortcutFuzzyVault(); {}// FV and SH don't get exactly the same EER with no chaff points
		
		TestGenerator testMaker = new GenerateFVCStyleTests();
		{}// TODO -make hasher
		{}// TODO -make method
		ArrayList<Fingerprint> fingerprints = new ArrayList<Fingerprint>();
		
		{}// TODO -make coordinator(hasher, users)
		RawScores scores = new RawScores();
//		Coordinator coordinator = new DefaultTesting(hasher, users, testMaker);
		Coordinator coordinator = new DefaultTestingPrequantized(hasher, users, testMaker);
		scores = coordinator.run();
		
		results = EvaluatePerformance.computeEER(scores);

		Collections.sort(scores.genuineScores);
		Collections.sort(scores.imposterScores);
		
		System.out.println("\nGenuines:\n" + scores.genuineScores + "\n");
		System.out.println("Imposters:\n" + scores.imposterScores + "\n");
		System.out.println("EER:\n" + results.getEer());
		System.out.println("rates:\n" + results.getRates());

		
		return results;
	}

	
	


	
}
