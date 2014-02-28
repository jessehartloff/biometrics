package optimize;

import java.util.ArrayList;

import settings.AllSettings;
import settings.coordinatorsettings.AllTestGeneratorSettings;
import settings.coordinatorsettings.TestGeneratorFVCTestsSettings;
import settings.fingerprintmethodsettings.FingerprintMethodSettings;
import settings.fingerprintmethodsettings.NgonSettings;
import settings.modalitysettings.AllModalitySettings;
import settings.modalitysettings.FingerprintSettings;
import settings.settingsvariables.SettingsDropDownItem;
import system.allcommonclasses.commonstructures.Results;

public class GeneticAlgorithm {
	//FIXME Matt
	private FitnessFunction f;
	public GeneticAlgorithm(FitnessFunction fitness){
		AllModalitySettings.getInstance().manuallySetComboBox(FingerprintSettings.getInstance());
		AllTestGeneratorSettings.getInstance().manuallySetComboBox(new TestGeneratorFVCTestsSettings());
		FingerprintSettings.getInstance().testingDataset( ).manuallySetComboBox(new SettingsDropDownItem("FVC2002DB1.ser"));
		FingerprintSettings.getInstance().trainingDataset().manuallySetComboBox(new SettingsDropDownItem("FVC2002DB1.ser"));
		this.f = fitness;
		
	}


	private void evolve(ArrayList<Chromosome> chromosomes) {
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
	
	public static ArrayList<Chromosome> getMatchingChromosomeList(){
		ArrayList<Chromosome> chromosomes = new ArrayList<Chromosome>();
		NgonSettings fpm = NgonSettings.getInstance();
		try {
			chromosomes.add(new Chromosome(fpm.n(), 6L,"N", fpm.n().getClass().getMethod("setValue", Long.class)));
			chromosomes.add(new Chromosome(fpm.kClosestMinutia(), 8L,"k-closest Minutia", fpm.kClosestMinutia().getClass().getMethod("setValue", Long.class)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return chromosomes;
	}
	
	public static void main(String[] args){
		ArrayList<Chromosome> chromosomes = getMatchingChromosomeList();

		GeneticAlgorithm ga = new GeneticAlgorithm(new FitnessFunction(){
			//minimizing EER
			@Override
			public Double evaluateFitness(ArrayList<Chromosome> chromosomes) {
				AllSettings settings = AllSettings.getInstance();
				Results results =  settings.runSystemAndGetResults();
				return results.getEer();
			}	
		});		
		ga.evolve(chromosomes);
	}
	
}
