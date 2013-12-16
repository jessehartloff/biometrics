package system.makefeaturevector;

import java.math.BigInteger;
import java.util.ArrayList;

import system.allcommonclasses.Template;
import system.vectordistance.Distance;


/**
 * Interacts with the raw biometric data. Converts them into structures that the hasher is expecting.
 * This class is extended by an abstract class for each modality. Every method of every modality
 * should be able compare two BigIntegers which are template points. 
 *
 */
public abstract class Method{



	//Distance distanceFunction; 
	{}// TODO +distance function
	
	/**
	 * This constructor defaults to ExactDistance for the distance function.
	 */
	protected Method(){
	//	this.distanceFunction = new ExactDistance();
	}	
	
	/**
	 * Used to define a specific distance function.
	 * 
	 * @param distanceFunction
	 */
	protected Method(Distance<?> distanceFunction){ {}// TODO +move distance to a setter method?
	//	this.distanceFunction = distanceFunction;
	}
	
	
	/**
	 * Computes the distance between two template points. The distance is determined by the 
	 * method and its distance function.
	 * 
	 * @param templatePoint1 
	 * @param templatePoint2
	 * @return a distance determined by the method and its distance function
	 */
	public abstract Double distance(BigInteger templatePoint1, BigInteger templatePoint2);
	
	
	
}
