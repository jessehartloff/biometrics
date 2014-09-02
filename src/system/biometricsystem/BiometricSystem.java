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
import system.quantizer.Quantizer;
import system.quantizer.QuantizerFactory;

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
		//TODO refactor: use logger

        //TODO refactor: make this generic for method. Will require casting later
		FingerprintMethodFactory.makeFingerprintMethod();

        //TODO refactor: method trains it's quantizer (and maybe other things later)
		this.trainTheSystem();

		Users testingUsers = AllModalitySettings.getTestingUsers();
		Double FTC = testingUsers.removeFailureToCapture();
		System.out.println("Failure to Capture");

		
		Coordinator coordinator = CoordinatorFactory.makeMultiserverCoordinator(testingUsers);
		
		if(coordinator == null){
			coordinator = CoordinatorFactory.makeAllSingleServerCoordinators(testingUsers);
		}
		
		RawScores rawScores = coordinator.run();

		if(rawScores != null){
			System.out.println(rawScores.toString());
			Results results = EvaluatePerformance.processResults(rawScores);
			results.setFailureToCapture(FTC);
			return results;
		}
		
		return null;
	}

	public Results goNoOut(){
		
		FingerprintMethodFactory.makeFingerprintMethod();
		
		this.trainTheSystemNoOut();
		
		Users testingUsers = AllModalitySettings.getTestingUsers();
		Double FTC = testingUsers.removeFailureToCapture();
		
		RawScores rawScores = CoordinatorFactory.makeAllSingleServerCoordinators(testingUsers).run();
		
		Results results = EvaluatePerformance.processResults(rawScores);
		results.setFailureToCapture(FTC);
		
		return results;
	}
	private void trainTheSystem(){ 
		System.out.println("Training");
		QuantizerFactory.makeQuantizer();
		Users trainingSet = AllModalitySettings.getTrainingUsers();
		trainingSet.removeFailureToCapture();
//		trainingSet.computeBins(); 
		Quantizer.getQuantizer().train(trainingSet);
		System.out.println("Done training");
	}
	
	private void trainTheSystemNoOut(){ 
		//System.out.println("Training");
		QuantizerFactory.makeQuantizer();
		Users trainingSet = AllModalitySettings.getTrainingUsers();
		trainingSet.removeFailureToCapture();
//		trainingSet.computeBins(); 
		Quantizer.getQuantizer().train(trainingSet);
		//System.out.println("Done training");
	}
	
}
