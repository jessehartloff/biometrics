package system.vectordistance;

import java.util.ArrayList;

public class ExactDistance<T extends Comparable<T>> extends Distance<T> {

	/**
	 * returns 0 for an exact match, 1 otherwise.
	 */
	@Override
	public Double distance(ArrayList<T> v1, ArrayList<T> v2) {

		super.init(v1, v2);

		for (int i = 0; i < arraySize; i++) {
			if (v1.get(i).compareTo(v2.get(i)) != 0) {
				return 1.0; // if any element is different, the vectors don't
							// match
			}
		}
		return 0.0; // exact match
	}

}
