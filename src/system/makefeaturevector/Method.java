package system.makefeaturevector;

import java.math.BigInteger;

import system.makefeaturevector.feature.Feature;
import system.vectordistance.Distance;


/**
 * Interacts with the raw biometric data. Converts them into structures that the hasher is expecting.
 * This class is extended by an abstract class for each modality. Every method of every modality
 * should be able compare two BigIntegers which are template points. 
 *
 */
public abstract class Method{



	//Distance distanceFunction; 
	{}// TODO +d-distance function
	
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
	protected Method(Distance<?> distanceFunction){ {}// TODO +d-move distance to a setter method?
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
	public Double distance(BigInteger templatePoint1, BigInteger templatePoint2) {
		Feature f1 = this.getBlankFeatureForTotalBits();
		Feature f2 = this.getBlankFeatureForTotalBits();
		f1.fromBigInt(templatePoint1);
		f2.fromBigInt(templatePoint2);
		return f1.distanceFrom(f2);
	}
	

	public Long getTotalBits() {
		Feature feature = this.getBlankFeatureForBinning();
		return feature.getTotalBits();
	}

	public abstract Feature getBlankFeatureForBinning();
	
	public Feature getBlankFeatureForTotalBits(){
		return this.getBlankFeatureForBinning();
	}
	
}
