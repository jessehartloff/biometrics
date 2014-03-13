package optimize;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import settings.AllSettings;
import settings.coordinatorsettings.AllHistogramCoordinatorSettings;
import settings.coordinatorsettings.AllIndexingCoordinatorSettings;
import settings.coordinatorsettings.AllMatchingCoordinatorSettings;
import settings.coordinatorsettings.AllTestGeneratorSettings;
import settings.coordinatorsettings.HistogramSettings;
import settings.coordinatorsettings.TestGeneratorFVCTestsSettings;
import settings.fingerprintmethodsettings.AllFingerprintMethodSettings;
import settings.fingerprintmethodsettings.PRINTSettings;
import settings.fingerprintmethodsettings.TripletsOfTrianglesSettings;
import settings.fingerprintmethodsettings.TripletsOfTrianglesAllRotationsSettings;
import settings.hashersettings.AllHasherSettings;
import settings.modalitysettings.AllModalitySettings;
import settings.modalitysettings.FingerprintSettings;
import settings.quantizersettings.AllQuantizerSettings;
import settings.settingsvariables.SettingsDropDownItem;
import system.allcommonclasses.commonstructures.Results;

/**  STAND ALONE CLASS **
 * Used to enumerate variable space to get best numbers for SPIE 2014 paper.
 * Will save parameters which achieve best EER, best FRR at ZeroFAR, best EER/ZeroFAR average, and best ZeroFAR - EER.
 * 
 * @author thomaseffland
 *
 */

public class ParameterOptimize {
	
	/**
	 * Member Variables
	 */
	String trainingDataset;
	String testDataset;
// *** Reference ***
//	this.settingsVariables.put("Matching", AllMatchingCoordinatorSettings.getInstance()); //default to all prequantized
//	this.settingsVariables.put("Indexing", AllIndexingCoordinatorSettings.getInstance()); //default to none
//	this.settingsVariables.put("Histogram", AllHistogramCoordinatorSettings.getInstance()); //
//	this.settingsVariables.put("Hasher", AllHasherSettings.getInstance());
//	this.settingsVariables.put("Quantizer", AllQuantizerSettings.getInstance());
//	this.settingsVariables.put("Modality", AllModalitySettings.getInstance())
	AllSettings settings;
	
	AllModalitySettings modality;
	AllFingerprintMethodSettings method;
	AllTestGeneratorSettings testGenerator;
	HistogramSettings histogram;

	/**
	 * Constructor
	 */
	public ParameterOptimize( String trainData, String testData) {
		//set datasets
		trainingDataset = trainData;
		testDataset = testData;
		
		//set settings variables to their class singletons so we don't have to type getInstance a million times
		settings = AllSettings.getInstance(); // loads all the default values
		
		testGenerator = AllTestGeneratorSettings.getInstance();
		modality = AllModalitySettings.getInstance();
		method = AllFingerprintMethodSettings.getInstance();
		
		//Optimizing on Triplets of Triangles All Rotations
		method.manuallySetComboBox(TripletsOfTrianglesAllRotationsSettings.getInstance());
		
		//Test on fingerprints
		modality.manuallySetComboBox(FingerprintSettings.getInstance());
		
		//Use FVC style tests
		testGenerator.manuallySetComboBox(new TestGeneratorFVCTestsSettings());
		
		//Set training and test datasets
		FingerprintSettings.getInstance().trainingDataset().manuallySetComboBox(new SettingsDropDownItem(trainingDataset));
		FingerprintSettings.getInstance().testingDataset( ).manuallySetComboBox(new SettingsDropDownItem(testDataset));
		
	}
	
	/**
	 * Set the search space parameters
	 */
	public void setSearchParams(){
		
	}
	
	/**
	 * Enumerate Search Space (probably will take fucking forever)
	 */
	public OptimizationResults optimize() {
		//TODO Tom - make Max Array Size set in globalSettings
		
		//Initialize optimization results object
		Integer maxArraySize = 10;
		OptimizationResults bestResults = new OptimizationResults( maxArraySize );
		
		//iterate over parameter space
		System.out.println("Running...");
		Results result = settings.runSystemAndGetResults();
		System.out.println( result.getAverageEERandZeroFAR() );
		bestResults.commitResult( result );
		
		return bestResults;
		
		
	}
	
	/**
	 * Checkpoint current results to file, and current progress through total search space
	 */
	//TODO Checkpoint optimization

	/** 
	 * MAIN
	 * @param args
	 */
	public static void main(String[] args){
		String trainDB = "FVC2002Training.ser";
		String testDB = "FVC2002DB2.ser";
		ParameterOptimize test = new ParameterOptimize(trainDB, testDB);
		OptimizationResults optimumResults = test.optimize();
		optimumResults.displayTopNResults(3);
	}
}//end Optimize class

