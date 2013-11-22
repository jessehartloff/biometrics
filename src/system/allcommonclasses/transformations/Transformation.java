package system.allcommonclasses.transformations;

import java.math.BigInteger;

/**
 * 
 * For whenever you need something transformed. Could be a hash function, permutation, encryption,
 * or anything else the future needs for transformation.
 *
 */
public abstract class Transformation {

	/**
	 * Transforms.
	 * 
	 * @param bigInt
	 * @return
	 */
	public abstract BigInteger transform(BigInteger bigInt);
	
}
