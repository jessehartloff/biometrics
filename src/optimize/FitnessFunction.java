package optimize;

import java.util.ArrayList;

public interface FitnessFunction {
	public Double evaluateFitness(ArrayList<Chromosome> chromosomes);
}
