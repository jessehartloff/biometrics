package system.biometricsystem;


import settings.AllSettings;
import settings.UsersIO;
import settings.modalitysettings.AllModalitySettings;
import system.allcommonclasses.commonstructures.Histogram;
import system.allcommonclasses.commonstructures.RawScores;
import system.allcommonclasses.commonstructures.Results;
import system.coordinator.*;
import system.method.fingerprintmethods.*;

/**
 * 
 * Takes the parameters, sets up the entire system, and processes the raw results. This includes 
 * creating the objects, reading the biometrics from files/databases, and starting the testing.
 * Also determines plots and meaningful results from the raw scores returned by the system.
 *
 */
public class BiometricSystem {
	
	/**
	 * Actually runs the "real part" of the program.
	 * Processes the choices of the user as set in "Main.java" using enumerators, and runs the appropriate tests.
	 * @param parameters
	 * @return
	 */
	public Results go(){
//		settings.loadToSettingsClasses(); TODO
		
		FingerprintMethodFactory.makeFingerprintMethod();
		
		AllModalitySettings.getTrainingUsers().computeBins();

		RawScores rawScores = CoordinatorFactory.makeCoordinator(AllModalitySettings.getTestingUsers()).run();
		
		Results results = EvaluatePerformance.processResults(rawScores);

	
		return results;
	}


	
}
