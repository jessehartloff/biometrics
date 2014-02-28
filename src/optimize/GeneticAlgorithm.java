package optimize;

import java.util.ArrayList;

import settings.AllSettings;
import settings.coordinatorsettings.AllTestGeneratorSettings;
import settings.coordinatorsettings.TestGeneratorFVCTestsSettings;
import settings.fingerprintmethodsettings.AllFingerprintMethodSettings;
import settings.fingerprintmethodsettings.PRINTSettings;
import settings.modalitysettings.AllModalitySettings;
import settings.modalitysettings.FingerprintSettings;
import settings.settingsvariables.SettingsDropDownItem;
import system.allcommonclasses.commonstructures.Results;

public class GeneticAlgorithm {
	//FIXME Matt
	
	public GeneticAlgorithm(FitnessFunction f){
		
	}


	private void optimize() {
		/*
		 * 		AllFingerprintMethodSettings.getInstance().manuallySetComboBox(PRINTSettings.getInstance());
		AllModalitySettings.getInstance().manuallySetComboBox(FingerprintSettings.getInstance());
		AllTestGeneratorSettings.getInstance().manuallySetComboBox(new TestGeneratorFVCTestsSettings());
		FingerprintSettings.getInstance().testingDataset( ).manuallySetComboBox(new SettingsDropDownItem("FVC2002DB1.ser"));
		FingerprintSettings.getInstance().trainingDataset().manuallySetComboBox(new SettingsDropDownItem("FVC2002DB1.ser"));
		//...
		// set values for everything else
		PRINTSettings.getInstance().rotationRegions().setValue(36);
		PRINTSettings.getInstance().n().setValue(3);
		PRINTSettings.getInstance().kClosestMinutia().setValue(4);
		 */
		
		
	}

	
	public static void main(String[] args){
		GeneticAlgorithm ga = new GeneticAlgorithm(new FitnessFunction(){

			@Override
			public Double evaluateFitness(ArrayList<Chromosome> chromosomes) {
				AllSettings settings = AllSettings.getInstance();
				Results results =  settings.runSystemAndGetResults();
				return null;
			}	
		});		
		ga.optimize();
	}
	
}
