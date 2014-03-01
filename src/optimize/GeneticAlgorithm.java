package optimize;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
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
				for(Chromosome c : chromosomes){
					c.execute();
				}
				Results results =  settings.runSystemAndGetResults();
				System.out.println(results.getEer());
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return results.getEer()*100.0; 			//minimizing EER
			}	
		});		

		ArrayList<Candidate> candidates = ga.intializePopulation(10);

		ga.evolve(candidates);
	}
	
	

	private ArrayList<Candidate> evolve(ArrayList<Candidate> candidates) {
		//Evaluation -- expensive fitness function evaluations
		for(Candidate candidate : candidates)
			candidate.evaluate(f);
		
		//Selection
		//normalizing
		Double norm = 0.0;
		for(Candidate candidate : candidates){
			norm += candidate.getFitness();
		}
		for(int i = 0; i < candidates.size(); i++){
			//Candidate candidate = candidates.get(i);
			candidates.get(i).setNormalizedFitness(candidates.get(i).getFitness()/norm);
		}
		
		Collections.sort(candidates, Collections.reverseOrder());
		
		for(int i = 0; i < candidates.size(); i++){
			Double anf = 0.0;
			for(int j = 0; j <= i; j++){
				anf += candidates.get(j).getNormalizedFitness();
			}
			candidates.get(i).setAccumlatedNormalizedFitness(anf);
		}
		System.out.println("\n\n\n");
		for(Candidate c: candidates){
			System.out.println("ANF: "+c.getAccumlatedNormalizedFitness());
			System.out.println("Normalized: "+c.getNormalizedFitness());
		}
		System.out.println("Norm: "+norm);
		System.exit(0);
		/*for(Double fitness : evaluatedFitness)
			norm += fitness;
		norm /= Double.valueOf(candidates.size());
		ArrayList<Double>
		//find most fit candidates
		ArrayList<Candidate> candidatesSorted = new ArrayList<Candidate>();
		for(Double d : evaluatedFitness)
			candidatesSorted.add(candidateFitness.get(d));*/
		
		
		//Crossover
		
		//Mutation
		
		//Termination
	
		return null;		
	}
	
	private ArrayList<Candidate> intializePopulation(int populationSize){
		ArrayList<Candidate> candidateSolutions = new ArrayList<Candidate>();
		Random r = new Random();
		
		for(int i = 0; i < populationSize; i++){
			ArrayList<Chromosome> chromosomes = getChromosomeList();
			for(int c = 0; c < chromosomes.size(); c++){
				Chromosome chromosome = chromosomes.get(c);
				ArrayList<Long> bounds = chromosome.getBounds();
				Long shiftInterval = bounds.get(1) - bounds.get(0);
				chromosome.setValue(r.nextInt(shiftInterval.intValue())+bounds.get(0));
			}
			candidateSolutions.add(new Candidate(chromosomes));
		}
		return candidateSolutions;
	}
	
	
	public static ArrayList<Chromosome> getChromosomeList(){
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
		

}
