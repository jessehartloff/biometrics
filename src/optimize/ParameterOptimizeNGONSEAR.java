package optimize;

import java.io.File;
import java.io.FileWriter;

import settings.AllSettings;
import settings.coordinatorsettings.histogramcoordinatorsettings.HistogramSettings;
import settings.coordinatorsettings.testgeneratorsettings.AllTestGeneratorSettings;
import settings.coordinatorsettings.testgeneratorsettings.TestGeneratorFVCTestsSettings;
import settings.fingerprintmethodsettings.AllFingerprintMethodSettings;
import settings.fingerprintmethodsettings.NgonAllRotationsSettings;
import settings.fingerprintmethodsettings.NgonsSingleEnrollAllRotationsSettings;
import settings.fingerprintmethodsettings.NgonSettings;
import settings.fingerprintmethodsettings.TripletsOfTrianglesAllRotationsSettings;
import settings.modalitysettings.AllModalitySettings;
import settings.modalitysettings.FingerprintSettings;
import settings.settingsvariables.SettingsDouble;
import settings.settingsvariables.SettingsDropDownItem;
import settings.settingsvariables.SettingsLong;
import system.allcommonclasses.commonstructures.Results;

/**  STAND ALONE CLASS **
 * Used to enumerate variable space to get best numbers for NGONs.
 * Will save parameters which achieve best EER, best FRR at ZeroFAR, best EER/ZeroFAR average, and best ZeroFAR - EER.
 * 
 * @author thomaseffland
 *
 */
public class ParameterOptimizeNGONSEAR {
	
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
	NgonsSingleEnrollAllRotationsSettings ngonsear; //stands for Triplets Of Triangles All Rotations
	AllTestGeneratorSettings testGenerator;
	HistogramSettings histogram;
	
	int nMin, nMax;
	int binMin, binMax;
	int kMin, kMax;

	/**
	 * Constructor
	 */
	public ParameterOptimizeNGONSEAR( String trainData, String testData, int bMin, int bMax, int kMin, int kMax, int nMin, int nMax) {
		//set datasets
		trainingDataset = trainData;
		testDataset = testData;
		
		//set var ranges
		binMin = bMin; binMax = bMax;
		this.kMin = kMin; this.kMax = kMax;
		this.nMin = nMin; this.nMax = nMax;
		
		//set settings variables to their class singletons so we don't have to type getInstance a million times
		settings = AllSettings.getInstance(); // loads all the default values
		
		testGenerator = AllTestGeneratorSettings.getInstance();
		modality = AllModalitySettings.getInstance();
		method = AllFingerprintMethodSettings.getInstance();
		
		//Optimizing on Triplets of Triangles All Rotations
		method.manuallySetComboBox(NgonsSingleEnrollAllRotationsSettings.getInstance());
		ngonsear = NgonsSingleEnrollAllRotationsSettings.getInstance();
		
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
	
		//enumerate over values
		Double numCandidates = Math.pow(new Double(binMax-binMin+1), 3.)*(kMax-kMin+1)*(nMax-nMin+1);
		System.out.println("Testing "+numCandidates.longValue()+" Candidates");
		Integer testCount = 0;
		/**
		 * Calculating runtime:
		 * on average, the runtime is exponentially effected by k:
		 */
//		this.settingsVariables.put("N", new SettingsLong(3));
//		this.settingsVariables.put("kClosestMinutia", new SettingsLong(4));
//		this.settingsVariables.put("thetaBins", new SettingsLong(8));
//		this.settingsVariables.put("xBins", new SettingsLong(8));
//		this.settingsVariables.put("yBins", new SettingsLong(8));
//		this.settingsVariables.put("rotationStep", new SettingsDouble(5.0));
//		this.settingsVariables.put("rotationStart", new SettingsDouble(-50.0));
//		this.settingsVariables.put("rotationStop", new SettingsDouble(50.0));
		for( int n = nMin; n <=nMax; n++ ) {
			for( int t = binMin; t <=binMax; t++ ){
				for( int x = binMin; x <=binMax; x++ ){
					for( int y = binMin; y <=binMax; y++ ){
						for ( int k=kMin; k <=kMax; k++ ){
							//to prevent ngons from erroring out when k is less than n
							if ( k < n) k = n;
							//make FTC work
							FingerprintSettings.getInstance().minimumMinutia().setValue(k+1);
							modality.manuallySetComboBox(FingerprintSettings.getInstance());
							long startTime = System.currentTimeMillis();
							ngonsear.n().setValue(n);
							ngonsear.thetaBins().setValue(t);
							ngonsear.xBins().setValue(x);
							ngonsear.yBins().setValue(y);
							ngonsear.kClosestMinutia().setValue(k);
							
							Results result = settings.runSystemAndGetResults();
							long stopTime = System.currentTimeMillis();
							String paramString = "n: "+new Integer(n)+"\n"
									+"theta: "+new Integer(t)+"\n"
									+"x: "+new Integer(x)+"\n"
									+"y: "+new Integer(y)+"\n"
									+"kClosestMinutia: " +new Integer(k)+"\n";
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
						            String fileName = binMin+"_"+binMax+"_"+kMin+"_"+kMax+"_"+nMin+"_"+nMax+"_"+ "_ngonsear_optimize.txt";
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
		int nMin = Integer.valueOf(args[4]).intValue();//6;
		int nMax = Integer.valueOf(args[5]).intValue();//8;
		
		ParameterOptimizeNGONSEAR test = new ParameterOptimizeNGONSEAR(trainDB, testDB, bMin, bMax, kMi, kMa, nMin, nMax);
		OptimizationResults optimumResults = test.optimize();
        try {								      
        	String currentBest = "All tests completed - Final Best Results:\n";
        	currentBest += optimumResults.displayTopNResults(10);
            String fileName = "final_"+bMin+"_"+bMax+"_"+kMi+"_"+kMa+"_"+nMin+"_"+nMax+"_ngonsear_optimize.txt";
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

