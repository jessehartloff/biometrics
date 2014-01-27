package system.base;


import system.allcommonclasses.commonstructures.Histogram;
import system.allcommonclasses.commonstructures.RawScores;
import system.allcommonclasses.commonstructures.Results;
import system.allcommonclasses.settings.Settings;
import system.allcommonclasses.utilities.UsersIO;
import system.coordinator.*;
import system.method.fingerprintmethods.*;

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

		RawScores rawScores = CoordinatorFactory.makeCoordinator(UsersIO.getUsersAndTrain()).run();
		
		Results results = EvaluatePerformance.processResults(rawScores);

		System.out.print(results.rawScores);
		System.out.println(results);
	
		return results;
	}
	
}
