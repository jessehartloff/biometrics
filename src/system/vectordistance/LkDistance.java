package system.vectordistance;

import java.util.ArrayList;

// TODO +d-can't use comparable
public class LkDistance<T extends Comparable<T>> extends Distance<T>{
	
	int k;
	
	/**
	 * default is L2-distance
	 */
	public LkDistance(){
		k=2;
	}
	
	public LkDistance(int k){
		this.k = k;
	}
	
	@Override
	public Double distance(ArrayList<T> v1, ArrayList<T> v2) {
		
		super.init(v1, v2);
		Double dist = 0.0;
		
		for (int i = 0; i < arraySize; i++) {
			dist += Math.pow(v1.get(i).compareTo(v2.get(i)), k); // change to use MeasurableDistance
		}
	
		return Math.pow(dist, 1.0/k);
	}

}
