package system.biometricsystem;


import settings.AllSettings;
import settings.UsersIO;
import settings.modalitysettings.AllModalitySettings;
import system.allcommonclasses.commonstructures.Histogram;
import system.allcommonclasses.commonstructures.RawScores;
import system.allcommonclasses.commonstructures.Results;
import system.allcommonclasses.commonstructures.Users;
import system.coordinator.*;
import system.method.fingerprintmethods.*;
import system.method.quantizers.Quantizer;
import system.method.quantizers.QuantizerFactory;

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
		
		FingerprintMethodFactory.makeFingerprintMethod();
		
		this.trainTheSystem();
		
		Users testingUsers = AllModalitySettings.getTestingUsers();
		Double FTC = testingUsers.removeFailureToCapture();
		
		RawScores rawScores = CoordinatorFactory.makeCoordinator(testingUsers).run();
		
		Results results = EvaluatePerformance.processResults(rawScores);
		results.setFailureToCapture(FTC);
		
		return results;
	}


	private void trainTheSystem(){ 
		QuantizerFactory.makeQuantizer();
		Users trainingSet = AllModalitySettings.getTrainingUsers();
		trainingSet.removeFailureToCapture();
//		trainingSet.computeBins(); 
		Quantizer.getQuantizer().train(trainingSet);
	}
	
}
