package optimize;

import java.util.ArrayList;

import system.allcommonclasses.commonstructures.Results;

public interface FitnessFunction {
	public Results evaluateFitness(ArrayList<Chromosome> chromosomes);
}
