package system.vectordistance;

import java.util.ArrayList;

public abstract class Distance<T extends Comparable<T>> {
	{}// TODO fix distance functionality. Can't use comparable. 
	int arraySize;
	
	public abstract Double distance(ArrayList<T> v1, ArrayList<T> v2);
	
	
	protected void init(ArrayList<T> v1, ArrayList<T> v2){
		arraySize = Math.min(v1.size(), v2.size()); 
		// do this better later. Maybe alert if sizes aren't equal
	}
	
}
