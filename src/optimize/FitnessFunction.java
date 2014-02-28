package optimize;

import java.util.ArrayList;

public interface FitnessFunction {
	//TODO Matt: make this an abstract class instead. Interfaces are lame.
	public Double evaluateFitness(ArrayList<Chromosome> chromosomes);
}
