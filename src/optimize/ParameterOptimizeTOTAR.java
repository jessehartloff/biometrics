package optimize;

import java.io.File;
import java.io.FileWriter;
import settings.AllSettings;
import settings.coordinatorsettings.histogramcoordinatorsettings.HistogramSettings;
import settings.coordinatorsettings.testgeneratorsettings.AllTestGeneratorSettings;
import settings.coordinatorsettings.testgeneratorsettings.TestGeneratorFVCTestsSettings;
import settings.fingerprintmethodsettings.AllFingerprintMethodSettings;
import settings.fingerprintmethodsettings.TripletsOfTrianglesAllRotationsSettings;
import settings.modalitysettings.AllModalitySettings;
import settings.modalitysettings.FingerprintSettings;
import settings.settingsvariables.SettingsDropDownItem;
import system.allcommonclasses.commonstructures.Results;

/**  STAND ALONE CLASS **
 * Used to enumerate variable space to get best numbers for SPIE 2014 paper.
 * Will save parameters which achieve best EER, best FRR at ZeroFAR, best EER/ZeroFAR average, and best ZeroFAR - EER.
 * 
 * @author thomaseffland
 *
 */

public class ParameterOptimizeTOTAR {
	
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
	TripletsOfTrianglesAllRotationsSettings totar; //stands for Triplets Of Triangles All Rotations
	AllTestGeneratorSettings testGenerator;
	HistogramSettings histogram;
	
	int binMin, binMax;
	int kMin, kMax;

	/**
	 * Constructor
	 */
	public ParameterOptimizeTOTAR( String trainData, String testData, int bMin, int bMax, int kMin, int kMax) {
		//set datasets
		trainingDataset = trainData;
		testDataset = testData;
		
		//set var ranges
		binMin = bMin; binMax = bMax;
		this.kMin = kMin; this.kMax = kMax;
		
		//set settings variables to their class singletons so we don't have to type getInstance a million times
		settings = AllSettings.getInstance(); // loads all the default values
		
		testGenerator = AllTestGeneratorSettings.getInstance();
		modality = AllModalitySettings.getInstance();
		method = AllFingerprintMethodSettings.getInstance();
		
		//Optimizing on Triplets of Triangles All Rotations
		method.manuallySetComboBox(TripletsOfTrianglesAllRotationsSettings.getInstance());
		totar = TripletsOfTrianglesAllRotationsSettings.getInstance();
		
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
		
		//these remain fixed for the SPIE paper
		totar.kClosestMinutia().setValue(2);
		totar.minimumPointsForTripletOfTriangles().setValue(7);
		//enumerate over values
		Double numCandidates = Math.pow(new Double(binMax-binMin+1), 7.)*(kMax-kMin+1);
		System.out.println("Testing "+numCandidates.longValue()+" Candidates");
		Integer testCount = 0;
		/**
		 * Calculating runtime:
		 * on average, the runtime is exponentially effected by k:
		 */
		
		for( int t0 = binMin; t0 <=binMax; t0++ ) {
			for( int t1 = binMin; t1 <=binMax; t1++ ){
				for( int t2 = binMin; t2 <=binMax; t2++ ){
					for ( int x1=binMin; x1 <=binMax; x1++ ) {
						for ( int x2=binMin; x2 <=binMax; x2++ ){
							for( int y1=binMin; y1 <=binMax; y1++ ){
								for( int y2=binMin; y2 <=binMax; y2++ ){
									for ( int k=kMin; k <=kMax; k++ ){
										long startTime = System.currentTimeMillis();
										totar.theta0().setBins(t0);
										totar.theta1().setBins(t1);
										totar.theta2().setBins(t2);
										totar.y1().setBins(y1);
										totar.y2().setBins(y2);
										totar.x1().setBins(x1);
										totar.x2().setBins(x2);
										totar.kClosestTriangles().setValue(k);;
										
										Results result = settings.runSystemAndGetResults();
										long stopTime = System.currentTimeMillis();
										String paramString = "theta0: "+new Integer(t0)+"\n"
												+"theta1: "+new Integer(t1)+"\n"
												+"theta2: "+new Integer(t2)+"\n"
												+"x1: "+new Integer(x1)+"\n"
												+"x2: "+new Integer(x2)+"\n"
												+"y1: "+new Integer(y1)+"\n"
												+"y2: "+new Integer(y2)+"\n"
												+"kClosestTriangles: " +new Integer(k)+"\n";
										result.setParamString(paramString);
										result.setRunTime((stopTime - startTime)/1000L);
										//System.out.println( result.getAverageEERandZeroFAR() );
										bestResults.commitResult( result );
										testCount++;
										System.out.println("Test #: "+testCount+"/"+numCandidates.longValue() +" ("+testCount/numCandidates*100+"%)");
										//write out to file every 10 tests
										if (/*testCount % 10*/ 0 == 0) {
											
									        try {								      
									        	String currentBest = "Current Params:"+paramString
									        					   + "\nTests Completed: "+testCount+"/"+numCandidates.longValue()
									        					   + bestResults.displayTopNResults(10);
									            String fileName = binMin+"_"+binMax+"_"+kMin+"_"+kMax+"_totar_optimize.txt";
									            File newTextFile = new File(fileName);

									            FileWriter fw = new FileWriter(newTextFile);
									            fw.write(currentBest);
									            fw.close();

									        } catch (Exception e) {
									            //do stuff with exception
									            e.printStackTrace();
									            System.out.println("FATAL ERROR: File writing didn't work...exiting");
									            System.exit(1);
									        }
										}
									}
								}					
							}
						}
					}
				}
			}
		}
		
		
		
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
		int bMin = Integer.valueOf(args[0]).intValue();//6;
		int bMax = Integer.valueOf(args[1]).intValue();//8;
		int kMi = Integer.valueOf(args[2]).intValue();//3;
		int kMa = Integer.valueOf(args[3]).intValue();//6;
		ParameterOptimizeTOTAR test = new ParameterOptimizeTOTAR(trainDB, testDB, bMin, bMax, kMi, kMa);
		OptimizationResults optimumResults = test.optimize();
        try {								      
        	String currentBest = "All tests completed - Final Best Results:\n";
        	currentBest += optimumResults.displayTopNResults(10);
            String fileName = "final_"+bMin+"_"+bMax+"_"+kMi+"_"+kMa+"_totar_optimize.txt";
            File newTextFile = new File(fileName);

            FileWriter fw = new FileWriter(newTextFile);
            fw.write(currentBest);
            fw.close();

        } catch (Exception e) {
            //do stuff with exception
            e.printStackTrace();
            System.out.println("FATAL ERROR: File writing didn't work...exiting");
            System.exit(1);
        }
	}
}//end Optimize class

