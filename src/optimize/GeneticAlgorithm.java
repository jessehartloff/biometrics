package optimize;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import settings.AllSettings;
import settings.coordinatorsettings.AllTestGeneratorSettings;
import settings.coordinatorsettings.TestGeneratorFVCTestsSettings;
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

	public static void main(String[] args){
		GeneticAlgorithm ga = new GeneticAlgorithm(new FitnessFunction(){
			@Override
			public Double evaluateFitness(ArrayList<Chromosome> chromosomes) {
				AllSettings settings = AllSettings.getInstance();
				Results results =  settings.runSystemAndGetResults();
				return results.getEer(); 			//minimizing EER
			}	
		});		

		ArrayList<Candidate> candidates = ga.intializePopulation();
//		for(Candidate c : candidates){
//			for(Chromosome ch : c.getChromosomes()){
//				System.out.println(ch.getName()+": "+ch.getValue());
//			}
//		}
		ga.evolve(candidates);
	}
	
	

	private ArrayList<Candidate> evolve(ArrayList<Candidate> candidates) {
		//Selection
		
		//Crossover
		
		//Mutation
		
		//Termination
	
		return null;		
	}
	
	private ArrayList<Candidate> intializePopulation(){
		int populationSize = 20;
		ArrayList<Candidate> candidateSolutions = new ArrayList<Candidate>();
		Random r = new Random();
		
		for(int i = 0; i < populationSize; i++){
			ArrayList<Chromosome> chromosomes = getMatchingChromosomeList();
			for(int c = 0; c < chromosomes.size(); c++){
				Chromosome chromosome = chromosomes.get(c);
				ArrayList<Long> bounds = chromosome.getBounds();
				Long shiftInterval = bounds.get(1) - bounds.get(0);
				chromosome.setValue(r.nextInt(shiftInterval.intValue())+bounds.get(0));
				chromosome.execute();
			}
			candidateSolutions.add(new Candidate(chromosomes));
		}
		return candidateSolutions;
	}
	
	
	public static ArrayList<Chromosome> getMatchingChromosomeList(){
		ArrayList<Chromosome> chromosomes = new ArrayList<Chromosome>();
		NgonSettings fpm = NgonSettings.getInstance();
		try {
			Long[] nbounds = {1L,9L};
			Long[] kClosestBounds = {1L,9L};
			
			chromosomes.add(new Chromosome(fpm.n(), 0L,"N", fpm.n().getClass().getMethod("setValue", Long.class),
							new ArrayList<Long>(Arrays.asList(nbounds))));
			chromosomes.add(new Chromosome(fpm.kClosestMinutia(), 0L,"k-closest Minutia", 
							fpm.kClosestMinutia().getClass().getMethod("setValue", Long.class), 
							new ArrayList<Long>(Arrays.asList(kClosestBounds))));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return chromosomes;
	}
		
	private class Candidate{
	
		private ArrayList<Chromosome> chromosomes;
		private BigInteger bitRep;
		
		public Candidate(ArrayList<Chromosome> chromosomes){
			bitRep = BigInteger.valueOf(0);
			this.chromosomes = chromosomes;
			updateBitRep();
		}
		
		public void updateBitRep(){
			for(Chromosome c: chromosomes){
				c.getValue();
				int shift = Long.SIZE;
				bitRep.shiftLeft(shift);
				bitRep.add(BigInteger.valueOf(c.getValue()));
			}
		}
	
		public ArrayList<Chromosome> getChromosomes() {
			return chromosomes;
		}
		
		public BigInteger getBitRep() {
			return bitRep;
		}

		public void setBitRep(BigInteger bitRep) {
			this.bitRep = bitRep;
		}		
	}
}
