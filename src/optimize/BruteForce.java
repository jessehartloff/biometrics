package optimize;

import java.util.ArrayList;
import java.util.Arrays;

import settings.AllSettings;
import settings.coordinatorsettings.testgeneratorsettings.AllTestGeneratorSettings;
import settings.coordinatorsettings.testgeneratorsettings.TestGeneratorFVCTestsSettings;
import settings.fingerprintmethodsettings.AllFingerprintMethodSettings;
import settings.fingerprintmethodsettings.PRINTSettings;
import settings.modalitysettings.AllModalitySettings;
import settings.modalitysettings.FingerprintSettings;
import settings.settingsvariables.SettingsDropDownItem;
import system.allcommonclasses.commonstructures.Results;

public class BruteForce {
	FitnessFunction f;
	
	/**
	 * Initialize brute force.
	 * Hard code in objective method here
	 * Hard code in training and testing databases here
	 * 
	 * @param fitness
	 */
	public BruteForce(FitnessFunction fitness){
		AllFingerprintMethodSettings.getInstance().manuallySetComboBox(PRINTSettings.getInstance());
		AllModalitySettings.getInstance().manuallySetComboBox(FingerprintSettings.getInstance());
		AllTestGeneratorSettings.getInstance().manuallySetComboBox(new TestGeneratorFVCTestsSettings());
		FingerprintSettings.getInstance().testingDataset( ).manuallySetComboBox(new SettingsDropDownItem("FVC2002Testing.ser"));
		FingerprintSettings.getInstance().trainingDataset().manuallySetComboBox(new SettingsDropDownItem("FVC2002Training.ser"));
		this.f = fitness;		
	}
	
	
	public static void main(String[] args){
		/**
		 * Define the "Fitness Function" inline to just be a regular run of the system
		 * This returns a Results object, which will be passed into OptimizationResults for sorting
		 */
		FitnessFunction f = new FitnessFunction(){
			@Override
			public Results evaluateFitness(ArrayList<Chromosome> chromosomes) {
				AllSettings settings = AllSettings.getInstance();
				for(Chromosome c : chromosomes){
					c.execute();
				}
				return settings.runSystemAndGetResults();
			}	
		};
		//initialize the system; hard code in databases, and fingerprint method in constructor above
		BruteForce b = new BruteForce(f);
		
		ArrayList<Chromosome> chromosomes = BruteForce.getChromosomeList();
		ArrayList<Candidate> allCandidates = b.getAllParameterSets(chromosomes, new ArrayList<Chromosome>());
		OptimizationResults op = new OptimizationResults(50);
		try {
			System.out.println("Number of Candidates: "+allCandidates.size());
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(Candidate c : allCandidates){
			c.evaluate(f);
			op.commitResult(c);
		}
		op.displayTopNResults(8);
	}
	

	
	
	public ArrayList<Candidate> getAllParameterSets(ArrayList<Chromosome> chromosomesToOptimize,
												ArrayList<Chromosome> chromosomesToCompute){
		if(chromosomesToOptimize.isEmpty()){
			ArrayList<Candidate> baseCase = new ArrayList<Candidate>();
			baseCase.add(new Candidate(chromosomesToCompute));
			return baseCase;	
		} else{
			Chromosome c = chromosomesToOptimize.get(0);
			chromosomesToCompute.add(c);
			chromosomesToOptimize.remove(0);
            ArrayList<Candidate> candidatesToReturn = new ArrayList<Candidate>();
			for(Long i = c.getBounds().get(0); i <= c.getBounds().get(1); i++){
				c.setValue(i);
				ArrayList<Candidate> candidates = getAllParameterSets(chromosomesToOptimize,chromosomesToCompute);
				candidatesToReturn.addAll(candidates);
			}
			chromosomesToOptimize.add(c);
			chromosomesToCompute.remove(chromosomesToCompute.size()-1);		
			return candidatesToReturn;
		}
	}

	
	/**
	 * Define the "Chromosome List" as the set of ranges of values for the search space (I think)
	 * NOTE: Hard code in particular functions parameters
	 * @return
	 */
	
	//TODO - Matt... it'd be really cool if you could put in even just a few comments
	public static ArrayList<Chromosome> getChromosomeList(){
		ArrayList<Chromosome> chromosomes = new ArrayList<Chromosome>();
		PRINTSettings fpm = PRINTSettings.getInstance();
		try {
			Long[] nbounds = {3L,11L};
			Long[] kClosestBounds = {3L,9L};
			Long[] distanceBinBounds = {2L, 11L};
			Long[] sigmaBinBounds = {2L, 11L};
			Long[] phiBinBounds = {2L, 11L};
			Long[] rotationRegionsBounds = {3L, 10L};
			
			chromosomes.add(new Chromosome(fpm.n(), 0L,"N",
							fpm.n().getClass().getMethod("setValue", Long.class),
							new ArrayList<Long>(Arrays.asList(nbounds))));
			
			chromosomes.add(new Chromosome(fpm.kClosestMinutia(), 0L,"k-closest Minutia", 
							fpm.kClosestMinutia().getClass().getMethod("setValue", Long.class), 
							new ArrayList<Long>(Arrays.asList(kClosestBounds))));
			
			chromosomes.add(new Chromosome(fpm.distanceBins(), 0L, "distanceBins",
							fpm.distanceBins().getClass().getMethod("setValue", Long.class),
							new ArrayList<Long>(Arrays.asList(distanceBinBounds))));
			
			chromosomes.add(new Chromosome(fpm.sigmaBins(), 0L, "sigmaBins",
							fpm.sigmaBins().getClass().getMethod("setValue", Long.class),
							new ArrayList<Long>(Arrays.asList(sigmaBinBounds))));
			
			chromosomes.add(new Chromosome(fpm.phiBins(), 0L, "phiBins",
							fpm.phiBins().getClass().getMethod("setValue", Long.class),
							new ArrayList<Long>(Arrays.asList(phiBinBounds))));
			
			chromosomes.add(new Chromosome(fpm.rotationRegions(), 0L, "rotationRegions",
							fpm.rotationRegions().getClass().getMethod("setValue", Long.class),
							new ArrayList<Long>(Arrays.asList(rotationRegionsBounds))));
		} catch (Exception e) {

			e.printStackTrace();
		}
		return chromosomes;
	}
	
	
}
/*
 * 
 * package optimize;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

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
	private FitnessFunction f;
	
	public GeneticAlgorithm(FitnessFunction fitness){
		AllFingerprintMethodSettings.getInstance().manuallySetComboBox(PRINTSettings.getInstance());;
		AllModalitySettings.getInstance().manuallySetComboBox(FingerprintSettings.getInstance());
		AllTestGeneratorSettings.getInstance().manuallySetComboBox(new TestGeneratorFVCTestsSettings());
		FingerprintSettings.getInstance().testingDataset( ).manuallySetComboBox(new SettingsDropDownItem("FVC20022Small.ser"));
		FingerprintSettings.getInstance().trainingDataset().manuallySetComboBox(new SettingsDropDownItem("FVC20022Small.ser"));
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

				return (1-results.getEer())*100.0; 			//minimizing EER
			}	
		});		

		ArrayList<Candidate> candidates = ga.intializePopulation(10);
		ArrayList<Candidate> init = new ArrayList<Candidate>(candidates);
		System.out.println("Initial Population:");
		for(Candidate c : init){
			System.out.println("Candidate: "+c);
		}
		for(int i = 0; i < 4; i++){
			candidates = ga.evolve(candidates);
			for(Candidate c : candidates){
				System.out.println(i+"th Gen Candidate: "+c);
			}
		}
		System.out.println("\n\n\n\n\n\n\n\n\n");
		System.out.println("Initial Population:");
		for(Candidate c : init){
			System.out.println("Candidate: "+c);
		}
		
		System.out.println("Current Generation:");

		for(Candidate c : candidates){
			System.out.println("Candidate: "+c);
		}
		System.exit(0);
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
		
		//constructing breeding population based on accumulated frequency
		int offspringProportion = 2; //make this more clear
		//int numberToSelect = candidates.size()/offspringProportion;
		int numberToSelect = candidates.size()*3/4;
		
		ArrayList<Candidate> breedingPopulation = new ArrayList<Candidate>();
		Random r = new Random();
		while(breedingPopulation.size() < numberToSelect){
			Double R = r.nextDouble();
			for(Candidate c : candidates){
				if(c.getAccumlatedNormalizedFitness() > R){
					breedingPopulation.add(c);
					break;
				}
			}
		}
		System.out.println("\n\n\n");

		
		//Crossover/Mutation
		ArrayList<Candidate> nextGenPopulation = new ArrayList<Candidate>();
		nextGenPopulation.addAll(breedingPopulation);
		Random R = new Random();
 		while(nextGenPopulation.size() != candidates.size()){
			Candidate parentA = breedingPopulation.get(R.nextInt(breedingPopulation.size()));
			Candidate parentB = breedingPopulation.get(R.nextInt(breedingPopulation.size()));
			Candidate child = makeOffspring(parentA, parentB);
			nextGenPopulation.add(child);
		}
	
		return nextGenPopulation;		
	}
	
	private Candidate makeOffspring(Candidate A, Candidate B) {
		Random R = new Random();
		Double crossoverConstant = .7, scalingConstant = .6; //adjust these for varying crossover and magnitude of mutations
		//one point crossover
		//switch to two point crossover if there are enough variables
		int initialParent = R.nextInt(2);
		boolean crossed = false;
		ArrayList<Chromosome> childChromosomes = new ArrayList<Chromosome>();
		for(int i = 0; i < A.getChromosomes().size(); i++){
			Chromosome c;
			if(initialParent == 0){
				c = A.getChromosomes().get(i);
			} else{
				c = B.getChromosomes().get(i);
			}
			if(R.nextDouble() > crossoverConstant && !crossed){
				System.out.println("crossover...");
				initialParent = (initialParent+1) % 2;
				crossed = true;
			}
//			if(R.nextDouble() > scalingConstant){
//				System.out.println("Scaling...");
//				Double scale = R.nextDouble()*c.getBounds().get(1).doubleValue();
//				c.setValue(BigDecimal.valueOf(Math.floor(c.getValue()*scale-c.getBounds().get(0).doubleValue())).longValue());
//			}
			childChromosomes.add(c);
		}
		
		return new Candidate(childChromosomes);
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
		PRINTSettings fpm = PRINTSettings.getInstance();
		try {
			Long[] nbounds = {3L,9L};
			Long[] kClosestBounds = {3L,9L};
			Long[] distanceBinBounds = {2L, 11L};
			Long[] sigmaBinBounds = {2L, 11L};
			Long[] phiBinBounds = {2L, 11L};
			Long[] rotationRegionsBounds = {3L, 30L};
			
			chromosomes.add(new Chromosome(fpm.n(), 0L,"N",
							fpm.n().getClass().getMethod("setValue", Long.class),
							new ArrayList<Long>(Arrays.asList(nbounds))));
			
			chromosomes.add(new Chromosome(fpm.kClosestMinutia(), 0L,"k-closest Minutia", 
							fpm.kClosestMinutia().getClass().getMethod("setValue", Long.class), 
							new ArrayList<Long>(Arrays.asList(kClosestBounds))));
			
			chromosomes.add(new Chromosome(fpm.distanceBins(), 0L, "distanceBins",
							fpm.distanceBins().getClass().getMethod("setValue", Long.class),
							new ArrayList<Long>(Arrays.asList(distanceBinBounds))));
			
			chromosomes.add(new Chromosome(fpm.sigmaBins(), 0L, "sigmaBins",
							fpm.sigmaBins().getClass().getMethod("setValue", Long.class),
							new ArrayList<Long>(Arrays.asList(sigmaBinBounds))));
			
			chromosomes.add(new Chromosome(fpm.phiBins(), 0L, "phiBins",
							fpm.phiBins().getClass().getMethod("setValue", Long.class),
							new ArrayList<Long>(Arrays.asList(phiBinBounds))));
			
			chromosomes.add(new Chromosome(fpm.rotationRegions(), 0L, "rotationRegions",
							fpm.rotationRegions().getClass().getMethod("setValue", Long.class),
							new ArrayList<Long>(Arrays.asList(rotationRegionsBounds))));
		} catch (Exception e) {

			e.printStackTrace();
		}
		return chromosomes;
	}
		

}

 * 
 * 
 * */
