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
		
		FingerprintMethodFactory.makeFingerprintMethod();
		RawScores scores = CoordinatorFactory.makeCoordinator(UsersIO.getUsers()).run();
		
		Results results = computeResults(scores);

		printResults(scores, results);
		return results;
	}
	
	
	private Results computeResults(RawScores rawScores){
		Results results = EvaluatePerformance.computeEER(rawScores);
		Collections.sort(rawScores.genuineScores);
		Collections.sort(rawScores.imposterScores);
		return results;
	}
	
	private void printResults(RawScores scores, Results results){
		System.out.println("\nGenuines:\n" + scores.genuineScores + "\n");
		System.out.println("Imposters:\n" + scores.imposterScores + "\n");
		System.out.println("EER:\n" + results.getEer());
		System.out.println("rates:\n" + results.getRates());
		System.out.println("indexing:\n" + scores.indexRankings);
	}

	
}
