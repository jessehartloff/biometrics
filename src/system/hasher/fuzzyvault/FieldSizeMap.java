package system.hasher.fuzzyvault;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;


public class FieldSizeMap {

	public static BigInteger getPrime(Long numberOfBits){
		return FieldSizeMap.daMap.get(numberOfBits.intValue());
	}
	
	public static Long getBitsFromPrime(BigInteger prime){
		for(Entry<Integer, BigInteger> entry : daMap.entrySet()){
			if(entry.getValue().equals(prime)){
				return entry.getKey().longValue();
			}
		}
		return (long) Math.floor((Math.log(prime.doubleValue())/Math.log(2.0)) +0.5);
	}
	
	/**
	 * maps the number of bits in the field to next higher prime number that works with it.
	 */
	private static HashMap<Integer, BigInteger> daMap;
    static{
        daMap = new HashMap<Integer, BigInteger>();
        daMap.put(1, BigInteger.valueOf(2L)); // BigInteger = the smallest prime larger than 2^Integer
        daMap.put(2, BigInteger.valueOf(5L)); // BigInteger = the smallest prime larger than 2^Integer
        daMap.put(3, BigInteger.valueOf(11L)); // BigInteger = the smallest prime larger than 2^Integer
        daMap.put(4, BigInteger.valueOf(17L)); // BigInteger = the smallest prime larger than 2^Integer
        daMap.put(5, BigInteger.valueOf(37L)); // BigInteger = the smallest prime larger than 2^Integer
        daMap.put(6, BigInteger.valueOf(67L)); // BigInteger = the smallest prime larger than 2^Integer
        daMap.put(7, BigInteger.valueOf(131L)); // BigInteger = the smallest prime larger than 2^Integer
        daMap.put(8, BigInteger.valueOf(257L)); // BigInteger = the smallest prime larger than 2^Integer
        daMap.put(9, BigInteger.valueOf(521L)); // BigInteger = the smallest prime larger than 2^Integer
        daMap.put(10, BigInteger.valueOf(1034L)); // BigInteger = the smallest prime larger than 2^Integer
        daMap.put(11, BigInteger.valueOf(2053L)); // BigInteger = the smallest prime larger than 2^Integer
        daMap.put(12, BigInteger.valueOf(4099L)); // BigInteger = the smallest prime larger than 2^Integer
        daMap.put(13, BigInteger.valueOf(8209L)); // BigInteger = the smallest prime larger than 2^Integer
        daMap.put(14, BigInteger.valueOf(16411L)); // BigInteger = the smallest prime larger than 2^Integer
        daMap.put(15, BigInteger.valueOf(32771L)); // BigInteger = the smallest prime larger than 2^Integer
        daMap.put(16, BigInteger.valueOf(65537L)); // BigInteger = the smallest prime larger than 2^Integer
        daMap.put(17, BigInteger.valueOf(131101L)); // BigInteger = the smallest prime larger than 2^Integer
        daMap.put(18, BigInteger.valueOf(262147L)); // BigInteger = the smallest prime larger than 2^Integer
        daMap.put(19, BigInteger.valueOf(524309L)); // BigInteger = the smallest prime larger than 2^Integer
        daMap.put(20, BigInteger.valueOf(1048583L)); // BigInteger = the smallest prime larger than 2^Integer
        daMap.put(21, BigInteger.valueOf(2097169L)); // BigInteger = the smallest prime larger than 2^Integer
        daMap.put(22, BigInteger.valueOf(4194319L)); // BigInteger = the smallest prime larger than 2^Integer
        daMap.put(23, BigInteger.valueOf(8388617L)); // BigInteger = the smallest prime larger than 2^Integer
        daMap.put(24, BigInteger.valueOf(16777259L)); // BigInteger = the smallest prime larger than 2^Integer
        daMap.put(25, BigInteger.valueOf(33554467L)); // BigInteger = the smallest prime larger than 2^Integer
        daMap.put(26, BigInteger.valueOf(67108879L)); // BigInteger = the smallest prime larger than 2^Integer
        daMap.put(27, BigInteger.valueOf(134217757L)); // BigInteger = the smallest prime larger than 2^Integer
        daMap.put(28, BigInteger.valueOf(268435459L)); // BigInteger = the smallest prime larger than 2^Integer
        daMap.put(29, BigInteger.valueOf(536870923L)); // BigInteger = the smallest prime larger than 2^Integer
        daMap.put(30, BigInteger.valueOf(1073741827L)); // BigInteger = the smallest prime larger than 2^Integer
        daMap.put(31, BigInteger.valueOf(2147483659L)); // BigInteger = the smallest prime larger than 2^Integer
        daMap.put(32, BigInteger.valueOf(4294967311L)); // BigInteger = the smallest prime larger than 2^Integer
        daMap.put(33, BigInteger.valueOf(8589934609L)); // BigInteger = the smallest prime larger than 2^Integer
        daMap.put(34, BigInteger.valueOf(17179869209L)); // BigInteger = the smallest prime larger than 2^Integer
        daMap.put(35, BigInteger.valueOf(34359738421L)); // BigInteger = the smallest prime larger than 2^Integer
        daMap.put(36, BigInteger.valueOf(68719476767L)); // BigInteger = the smallest prime larger than 2^Integer
        daMap.put(37, BigInteger.valueOf(137438953481L)); // BigInteger = the smallest prime larger than 2^Integer
        daMap.put(38, BigInteger.valueOf(274877906951L)); // BigInteger = the smallest prime larger than 2^Integer
        daMap.put(39, BigInteger.valueOf(549755813911L)); // BigInteger = the smallest prime larger than 2^Integer
        daMap.put(40, BigInteger.valueOf(1099511627791L)); // BigInteger = the smallest prime larger than 2^Integer

    }
}
