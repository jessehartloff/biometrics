package system.hasher.fuzzyvault;

import java.math.BigInteger;
import java.util.HashMap;

public class FieldSizeMap {
	
	public static BigInteger getPrime(Long numberOfBits){
		return FieldSizeMap.daMap.get(numberOfBits.intValue());
	}
	
	/**
	 * maps the number of bits in the field to next higher prime number that works with it.
	 */
	private static HashMap<Integer, BigInteger> daMap;
    static{
        daMap = new HashMap<Integer, BigInteger>();
        daMap.put(1, BigInteger.valueOf(2L)); // BigInteger = the smallest prime larger than 2^Integer
        daMap.put(2, BigInteger.valueOf(5L));
        daMap.put(3, BigInteger.valueOf(11L));
        daMap.put(4, BigInteger.valueOf(17L));
        //TODO Jim
    }
	
}
