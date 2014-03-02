package optimize;

import java.util.ArrayList;

public class Candidate implements Comparable<Candidate>{

	private ArrayList<Chromosome> chromosomes;
	private Double fitness;
	private Double normalizedFitness;
	private Double accumlatedNormalizedFitness;
	
	public Candidate(ArrayList<Chromosome> chromosomes){
		this.chromosomes = chromosomes;
		applyConstraints();
	}
	
	public void applyConstraints(){
		//TODO Generalize
		while(chromosomes.get(0).getValue() <= chromosomes.get(1).getValue()){
			chromosomes.get(0).setValue(chromosomes.get(0).getValue()+1);
		}
	}

	public Double evaluate(FitnessFunction f){
		Double fitness =  f.evaluateFitness(this.chromosomes);
		if(fitness == null){
			fitness = 0.0;
		}
		this.fitness = fitness;
		return  fitness;
	}

	public ArrayList<Chromosome> getChromosomes() {
		return chromosomes;
	}
	public void setChromosomes(ArrayList<Chromosome> chromosomes) {
		this.chromosomes = chromosomes;
	}

	
	public Double getFitness(){
		return this.fitness;
	}
	

	public Double getNormalizedFitness() {
		return normalizedFitness;
	}

	public void setNormalizedFitness(Double normalizedFitness) {
		this.normalizedFitness = normalizedFitness;
	}

	public Double getAccumlatedNormalizedFitness() {
		return accumlatedNormalizedFitness;
	}

	public void setAccumlatedNormalizedFitness(Double accumlatedNormalizedFitness) {
		this.accumlatedNormalizedFitness = accumlatedNormalizedFitness;
	}


	@Override
	public int compareTo(Candidate c) {
		return this.getNormalizedFitness().compareTo(c.getNormalizedFitness());
	}
	
	@Override
	public String toString(){
		String s = "< ";
		s += "Fitness: " + this.fitness+", ";
		for(Chromosome c : this.chromosomes){
			s += c.getName() + ": "+c.getValue()+", ";
		}
		return s+">";
	}
	
	
}


